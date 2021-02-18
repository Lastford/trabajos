package es.correos.soporte.minerva.envios.service.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import es.correos.arch.boot.feign.exceptions.FeignCommunicationException;
import es.correos.arq.ex.CorreosBusinessException;
import es.correos.soporte.minerva.envios.domain.Envio;
import es.correos.soporte.minerva.envios.domain.custom.EnvioListaClienteUnion;
import es.correos.soporte.minerva.envios.domain.custom.EnvioListaContrato;
import es.correos.soporte.minerva.envios.dto.EnvioDTO;
import es.correos.soporte.minerva.envios.dto.EnvioEventoDTO;
import es.correos.soporte.minerva.envios.dto.EnvioListaCustomClientCodEtiqFilterDTO;
import es.correos.soporte.minerva.envios.dto.EnvioListaDTO;
import es.correos.soporte.minerva.envios.dto.EnvioSCPDTO;
import es.correos.soporte.minerva.envios.dto.EnvioSCPListaDTO;
import es.correos.soporte.minerva.envios.dto.feign.agrupaciones.AgrupacionDTO;
import es.correos.soporte.minerva.envios.dto.feign.agrupaciones.ContenidoEnviosDTO;
import es.correos.soporte.minerva.envios.dto.feign.centros.CentroDTO;
import es.correos.soporte.minerva.envios.dto.feign.centros.CentroListaDTO;
import es.correos.soporte.minerva.envios.dto.feign.eventos.EventoDTO;
import es.correos.soporte.minerva.envios.dto.feign.eventos.EventoFiltro;
import es.correos.soporte.minerva.envios.dto.feign.eventos.EventoListaDTO;
import es.correos.soporte.minerva.envios.dto.feign.productos.ProductoDTO;
import es.correos.soporte.minerva.envios.dto.soap.imagenes.ImagenSOAPDTO;
import es.correos.soporte.minerva.envios.exceptions.EnvioNotFoundExc;
import es.correos.soporte.minerva.envios.filter.EnvioCustomFilter;
import es.correos.soporte.minerva.envios.filter.EnvioFilter;
import es.correos.soporte.minerva.envios.helpers.AgrupacionServiceHelper;
import es.correos.soporte.minerva.envios.helpers.Base64Files;
import es.correos.soporte.minerva.envios.helpers.ContenidoEnvioHelper;
import es.correos.soporte.minerva.envios.helpers.EnvioServiceHelper;
import es.correos.soporte.minerva.envios.helpers.EventoServiceHelper;
import es.correos.soporte.minerva.envios.helpers.ProductoServiceHelper;
import es.correos.soporte.minerva.envios.mapper.EnvioFiltroClienteMapper;
import es.correos.soporte.minerva.envios.mapper.EnvioMapper;
import es.correos.soporte.minerva.envios.mapper.EventoMapper;
import es.correos.soporte.minerva.envios.repository.EnvioRepository;
import es.correos.soporte.minerva.envios.service.AgrupacionesService;
import es.correos.soporte.minerva.envios.service.CentrosService;
import es.correos.soporte.minerva.envios.service.EventosService;
import es.correos.soporte.minerva.envios.service.ImagenEdocService;
import es.correos.soporte.minerva.envios.service.ProductosService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@RunWith(SpringRunner.class)
public class EnvioServiceImplTest {

  @Mock private ImagenEdocService imagenEdocService;

  @Mock private EnvioRepository envioRepository;

  @Mock private EnvioMapper envioMapper;

  @Mock private EventoMapper eventoMapper;

  @Mock private AgrupacionesService agrupacionesService;

  @Mock private CentrosService centrosService;

  @Mock private EventosService eventosService;

  @Mock private EnvioFiltroClienteMapper envioFiltroClienteMapper;

  @Mock private ProductosService productosService;

  @InjectMocks private EnvioServiceImpl envioService;

  @Test
  public void testGetEnvios_SinIdAgrupacion() throws FeignCommunicationException {
    EnvioFilter filtro =
        EnvioFilter.builder().idAgrupacion("0028219200272Q7G302001F").offset(2).build();

    List<Envio> envios = EnvioServiceHelper.crearListaEnvio();
    List<EnvioDTO> enviosDTO = EnvioServiceHelper.crearListaEnvioDTO();
    Pageable pageable = PageRequest.of(filtro.getOffset(), 2);
    Page<Envio> pagina = PageableExecutionUtils.getPage(envios, pageable, () -> 2);
    List<EventoDTO> eventosEnvios = EventoServiceHelper.crearListaEventoDTO();
    EventoListaDTO eventoListaDTO = new EventoListaDTO();
    eventoListaDTO.setData(eventosEnvios);

    CentroDTO centro =
        CentroDTO.builder()
            .codiRedU("123")
            .unityName("unityName")
            .longitude(10F)
            .latitude(11F)
            .build();
    List<CentroDTO> listaCentros = new ArrayList<>();
    listaCentros.add(centro);
    CentroListaDTO centroListaDTO = CentroListaDTO.builder().listData(listaCentros).build();

    when(agrupacionesService.getAgrupacionById(Mockito.anyString(), Mockito.anyBoolean()))
        .thenReturn(null);
    when(envioRepository.busquedaParametros(Mockito.any(EnvioFilter.class))).thenReturn(pagina);
    when(envioMapper.envioToEnvioDTO(Mockito.anyList())).thenReturn(enviosDTO);
    when(eventosService.getEventosByFilterEvent(Mockito.any(EventoFiltro.class)))
        .thenReturn(eventoListaDTO.getData());
    when(centrosService.getCentrosByCodired(Mockito.anyString()))
        .thenReturn(centroListaDTO.getListData());

    EnvioListaDTO resultado = envioService.getEnvios(filtro);

    assertNotNull(resultado);
  }

  @Test
  public void testGetEnvios_ConIdAgrupacion_ConIdEnvios() throws FeignCommunicationException {
    EnvioFilter filtro =
        EnvioFilter.builder()
            .id(new ArrayList<>(Arrays.asList("UX6PZG0467032490119161W")))
            .idAgrupacion("0028219200272Q7G302001F")
            .offset(2)
            .build();

    List<Envio> envios = EnvioServiceHelper.crearListaEnvio();
    List<EnvioDTO> enviosDTO = EnvioServiceHelper.crearListaEnvioDTO();
    Pageable pageable = PageRequest.of(filtro.getOffset(), 2);
    Page<Envio> pagina = PageableExecutionUtils.getPage(envios, pageable, () -> 2);
    AgrupacionDTO agrupacionDTO = AgrupacionServiceHelper.crearAgrupacionDTO();
    List<EventoDTO> eventosEnvios = EventoServiceHelper.crearListaEventoDTO();
    EventoListaDTO eventoListaDTO = new EventoListaDTO();
    eventoListaDTO.setData(eventosEnvios);

    CentroDTO centro =
        CentroDTO.builder()
            .codiRedU("codired")
            .unityName("unityName")
            .longitude(10F)
            .latitude(11F)
            .build();
    List<CentroDTO> listaCentros = new ArrayList<>();
    listaCentros.add(centro);
    CentroListaDTO centroListaDTO = CentroListaDTO.builder().listData(listaCentros).build();

    when(agrupacionesService.getAgrupacionById(Mockito.anyString(), Mockito.anyBoolean()))
        .thenReturn(agrupacionDTO);
    when(envioRepository.busquedaParametros(Mockito.any(EnvioFilter.class))).thenReturn(pagina);
    when(envioMapper.envioToEnvioDTO(Mockito.anyList())).thenReturn(enviosDTO);
    when(eventosService.getEventosByFilterEvent(Mockito.any(EventoFiltro.class)))
        .thenReturn(eventoListaDTO.getData());
    when(centrosService.getCentrosByCodired(Mockito.anyString()))
        .thenReturn(centroListaDTO.getListData());

    EnvioListaDTO resultado = envioService.getEnvios(filtro);

    assertNotNull(resultado);
  }

  @Test
  public void testAltaAdicionXLSX() throws IOException {
    String fichero = Base64Files.XLSX;
    String deliveryMode = "1";
    Optional<Envio> mockEnvio =
        Optional.of(Envio.builder().id("33000000035307101288219").cpDestino("00000").build());

    when(envioRepository.findById("33000000035307101288219")).thenReturn(mockEnvio);
    envioService.altaAdicion("XLSX", fichero, deliveryMode);

    assertNotNull(envioService.altaAdicion("XLSX", fichero, deliveryMode));
  }

  @Test
  public void testAltaAdicionXLS() throws IOException {
    String fichero = Base64Files.XLS;
    String deliveryMode = "1";
    Optional<Envio> mockEnvio =
        Optional.of(Envio.builder().id("33000000035307101288219").cpDestino("00000").build());

    when(envioRepository.findById(Mockito.any())).thenReturn(mockEnvio);

    assertNotNull(envioService.altaAdicion("XLS", fichero, deliveryMode));
  }

  @Test
  public void testAltaAdicionXLS2() throws IOException {
    String fichero = Base64Files.XLS;
    String deliveryMode = "1";
    Optional<Envio> mockEnvio =
        Optional.of(Envio.builder().id("33000000035307101288219").cpDestino("00000").build());

    when(envioRepository.findById("33000000035307101288219")).thenReturn(mockEnvio);

    assertNotNull(envioService.altaAdicion("XLS", fichero, deliveryMode));
  }

  @Test
  public void testGetEnvioImagenes() throws CorreosBusinessException {

    ImagenSOAPDTO imagen =
        ImagenSOAPDTO.builder()
            .codigo("cio")
            .verificada("verificada")
            .fechaAlta("fechaAlta")
            .tipoImagen("tipoImagen")
            .fechaDigitacion("2020-09-10")
            .build();
    List<ImagenSOAPDTO> lstImagenSOAP = new ArrayList<>();
    lstImagenSOAP.add(imagen);

    when(imagenEdocService.obtenerImagenes("cio")).thenReturn(lstImagenSOAP);

    assertNotNull(envioService.getEnvioImagenes("cio"));
  }

  @Test
  public void testGetEnvioImagen() throws CorreosBusinessException {

    ImagenSOAPDTO imagen =
        ImagenSOAPDTO.builder()
            .codigo("cio")
            .verificada("verificada")
            .fechaAlta("fechaAlta")
            .tipoImagen("tipoImagen")
            .fechaDigitacion("2020-09-10")
            .build();
    List<ImagenSOAPDTO> lstImagenSOAP = new ArrayList<>();
    lstImagenSOAP.add(imagen);

    when(imagenEdocService.obtenerImagenes("cio")).thenReturn(lstImagenSOAP);

    assertNotNull(envioService.getEnvioImagen("cio", "cio"));
  }

  @Test
  public void testGetEnviosSCP() throws FeignCommunicationException {
    EnvioFilter filtro =
        EnvioFilter.builder().idAgrupacion("0028219200272Q7G302001F").offset(2).build();
    List<Envio> envios = EnvioServiceHelper.crearListaEnvio();
    List<EnvioSCPDTO> enviosDTO = EnvioServiceHelper.crearListaEnvioSCPDTO();
    Pageable pageable = PageRequest.of(filtro.getOffset(), 2);
    Page<Envio> pagina = PageableExecutionUtils.getPage(envios, pageable, () -> 2);

    when(envioRepository.busquedaParametrosSCP(Mockito.any(EnvioFilter.class))).thenReturn(pagina);
    when(envioMapper.envioToEnvioSCPDTO(Mockito.anyList())).thenReturn(enviosDTO);

    EnvioSCPListaDTO resultado = envioService.getEnviosSCP(filtro);

    assertNotNull(resultado);
  }

  /* @Test
  public void testGetEnviosCsv() throws FeignCommunicationException {
    EnvioFilter filtro =
        EnvioFilter.builder().idAgrupacion("0028219200272Q7G302001F").offset(2).build();
    List<Envio> envios = EnvioServiceHelper.crearListaEnvio();
    Pageable pageable = PageRequest.of(filtro.getOffset(), 2);
    Page<Envio> pagina = PageableExecutionUtils.getPage(envios, pageable, () -> 2);
    List<EnvioSCPDTO> enviosDTO = EnvioServiceHelper.crearListaEnvioSCPDTO();

    when(envioRepository.busquedaParametros(Mockito.any(EnvioFilter.class))).thenReturn(pagina);
    when(envioMapper.envioToEnvioSCPDTO(Mockito.anyList())).thenReturn(enviosDTO);

    String resultado = envioService.getEnviosCsv(filtro);

    assertNotNull(resultado);
  }*/

  @Test
  public void testGetEnviosCsvSCP() {
    EnvioFilter filtro =
        EnvioFilter.builder().idAgrupacion("0028219200272Q7G302001F").offset(2).build();

    List<Envio> envios = EnvioServiceHelper.crearListaEnvio();

    when(envioRepository.busquedaParametrosCSVSCP(Mockito.any(EnvioFilter.class)))
        .thenReturn(envios);

    String resultado = envioService.getEnviosCsvSCP(filtro);

    assertNotNull(resultado);
    assertFalse(resultado.isEmpty());
  }

  @Test
  public void testGetEnvioId() throws FeignCommunicationException, EnvioNotFoundExc {
    String id = "UX6PZG0467032490119161W";
    Envio envio = EnvioServiceHelper.crearEnvio();
    EnvioDTO envioDTO = EnvioServiceHelper.crearEnvioDTO();
    List<EventoDTO> eventosEnvios = EventoServiceHelper.crearListaEventoDTO();
    List<EnvioEventoDTO> eventos =
        new ArrayList<>(Arrays.asList(EnvioEventoDTO.builder().codired("123").build()));

    ContenidoEnviosDTO contenidoEnvio = ContenidoEnvioHelper.crearContenidoEnviosHelper();
    CentroDTO centro = CentroDTO.builder().codiRedU("123").longitude(10F).latitude(11F).build();
    List<CentroDTO> listaCentros = new ArrayList<CentroDTO>();
    listaCentros.add(centro);

    List<AgrupacionDTO> listagroups = new ArrayList<>();
    AgrupacionDTO group1 =
        AgrupacionDTO.builder()
            .id("idAgrupacion1")
            .jerarquiaPadres(
                new ArrayList<>(Arrays.asList(AgrupacionDTO.builder().id("idAgrupacion1").build())))
            .jerarquiaHijos(new ArrayList<>())
            .build();

    AgrupacionDTO group2 =
        AgrupacionDTO.builder()
            .id("idAgrupacion2")
            .jerarquiaPadres(
                new ArrayList<>(Arrays.asList(AgrupacionDTO.builder().id("idAgrupacion2").build())))
            .jerarquiaHijos(new ArrayList<>())
            .build();

    List<ContenidoEnviosDTO> listaContenidoEnvios = new ArrayList<ContenidoEnviosDTO>();
    listaContenidoEnvios.add(contenidoEnvio);
    group1.setContenidoEnvios(listaContenidoEnvios);
    group2.setContenidoEnvios(listaContenidoEnvios);

    listagroups.add(group1);
    listagroups.add(group2);

    EventoListaDTO eventoListaDTO = EventoListaDTO.builder().data(eventosEnvios).build();

    when(envioRepository.getEnviosPadresHijos(Mockito.anyString())).thenReturn(envio);
    when(envioMapper.envioToEnvioDTO(Mockito.any(Envio.class))).thenReturn(envioDTO);
    when(eventosService.getEventosByFilterEvent(Mockito.any(EventoFiltro.class)))
        .thenReturn(eventoListaDTO.getData());
    when(agrupacionesService.getAgrupacionesByIdEnvio(Mockito.anyString())).thenReturn(listagroups);
    when(agrupacionesService.getAgrupacionParentHierarchyById(Mockito.anyString()))
        .thenReturn(group1);
    when(centrosService.getCentrosByCodired(Mockito.anyString())).thenReturn(listaCentros);
    when(eventoMapper.eventoDtoToEnvioEventoDTO(Mockito.anyList())).thenReturn(eventos);

    EnvioDTO resultado = envioService.getEnvioById(id);

    assertNotNull(resultado);
  }

  @Test
  public void testGetEnvioIdNull() throws FeignCommunicationException, EnvioNotFoundExc {
    String id = "UX6PZG0467032490119161W";
    Envio envio = EnvioServiceHelper.crearEnvio();
    EnvioDTO envioDTO = EnvioServiceHelper.crearEnvioDTO();
    List<EventoDTO> eventosEnvios = EventoServiceHelper.crearListaEventoDTO();
    List<EnvioEventoDTO> eventos =
        new ArrayList<>(Arrays.asList(EnvioEventoDTO.builder().codired("123").build()));

    CentroDTO centro = CentroDTO.builder().codiRedU("123").longitude(10F).latitude(11F).build();
    List<CentroDTO> listaCentros = new ArrayList<CentroDTO>();
    listaCentros.add(centro);

    List<AgrupacionDTO> listagroups = null;
    AgrupacionDTO group1 =
        AgrupacionDTO.builder()
            .id("idAgrupacion1")
            .jerarquiaPadres(
                new ArrayList<>(Arrays.asList(AgrupacionDTO.builder().id("idAgrupacion1").build())))
            .jerarquiaHijos(new ArrayList<>())
            .build();

    EventoListaDTO eventoListaDTO = EventoListaDTO.builder().data(eventosEnvios).build();

    when(envioRepository.getEnviosPadresHijos(Mockito.anyString())).thenReturn(envio);
    when(envioMapper.envioToEnvioDTO(Mockito.any(Envio.class))).thenReturn(envioDTO);
    when(eventosService.getEventosByFilterEvent(Mockito.any(EventoFiltro.class)))
        .thenReturn(eventoListaDTO.getData());
    when(agrupacionesService.getAgrupacionesByIdEnvio(Mockito.anyString())).thenReturn(listagroups);
    when(agrupacionesService.getAgrupacionParentHierarchyById(Mockito.anyString()))
        .thenReturn(group1);
    when(centrosService.getCentrosByCodired(Mockito.anyString())).thenReturn(listaCentros);
    when(eventoMapper.eventoDtoToEnvioEventoDTO(Mockito.anyList())).thenReturn(eventos);

    EnvioDTO resultado = envioService.getEnvioById(id);

    assertNotNull(resultado);
  }

  @Test(expected = EnvioNotFoundExc.class)
  public void getEnvioId_EnvioNotFoundExc() throws FeignCommunicationException, EnvioNotFoundExc {
    String id = "123456";
    Optional<Envio> envioOpt = Optional.ofNullable(null);
    when(envioRepository.findById(id)).thenReturn(envioOpt);
    envioService.getEnvioById(id);
  }

  @Test
  public void testCreateCsvFile() throws FeignCommunicationException, IOException {
    EnvioFilter filtro =
        EnvioFilter.builder().idAgrupacion("0028219200272Q7G302001F").offset(2).build();
    List<Envio> envios = EnvioServiceHelper.crearListaEnvio();
    Pageable pageable = PageRequest.of(filtro.getOffset(), 2);
    Page<Envio> pagina = PageableExecutionUtils.getPage(envios, pageable, () -> 2);
    List<EnvioDTO> enviosDTO = EnvioServiceHelper.crearListaEnvioDTO();

    List<ProductoDTO> productosDTO = ProductoServiceHelper.crearListaProductoDTO();

    final File file = File.createTempFile("Prueba", ".csv");
    final OutputStream writer = new FileOutputStream(file);

    when(envioRepository.busquedaParametros(Mockito.any(EnvioFilter.class))).thenReturn(pagina);
    when(envioMapper.envioXslToEnvioDTO(Mockito.anyList())).thenReturn(enviosDTO);
    when(productosService.getProductos()).thenReturn(productosDTO);

    envioService.createCsvFile(filtro, writer);
  }

  @Test
  public void testGetEnviosCustomFilters_FieldCodEtiq() {

    EnvioCustomFilter campoBuscar = EnvioCustomFilter.codEtiq;
    MultiValueMap<String, String> filtros = new LinkedMultiValueMap<String, String>();
    filtros.add("client", "1#2");
    filtros.add("search", "1");
    filtros.add("codEtiq", "2");
    ;
    Integer offset = 0;
    Integer size = 20;

    when(envioRepository.getEnviosAgrupadosPorClienteUnion(
            Mockito.any(), Mockito.anyList(), Mockito.anyInt(), Mockito.anyInt()))
        .thenReturn(EnvioListaClienteUnion.builder().totalRegistros(0).build());
    when(envioFiltroClienteMapper.envioListaClienteUnionToEnvioListaCustomClientCodEtiqFilterDto(
            Mockito.any(EnvioListaClienteUnion.class)))
        .thenReturn(EnvioListaCustomClientCodEtiqFilterDTO.builder().totalRegistros(0).build());

    EnvioListaCustomClientCodEtiqFilterDTO resultado =
        envioService.getEnviosCustomFilters(campoBuscar, filtros, offset, size);

    assertNotNull(resultado);
  }

  @Test
  public void testGetEnviosCustomFilters_FieldClient() {

    EnvioCustomFilter campoBuscar = EnvioCustomFilter.client;
    MultiValueMap<String, String> filtros = new LinkedMultiValueMap<String, String>();
    filtros.add("client", "1#2");
    filtros.add("search", "1");
    filtros.add("codEtiq", "2");
    Integer offset = 0;
    Integer size = 20;

    when(envioRepository.getEnviosAgrupadosPorNumContrato(
            Mockito.any(), Mockito.anyList(), Mockito.anyInt(), Mockito.anyInt()))
        .thenReturn(EnvioListaContrato.builder().totalRegistros(0).build());
    when(envioFiltroClienteMapper.envioListaContratoToEnvioListaCustomClientCodEtiqFilterDto(
            Mockito.any(EnvioListaContrato.class)))
        .thenReturn(EnvioListaCustomClientCodEtiqFilterDTO.builder().totalRegistros(0).build());

    EnvioListaCustomClientCodEtiqFilterDTO resultado =
        envioService.getEnviosCustomFilters(campoBuscar, filtros, offset, size);

    assertNotNull(resultado);
  }
}
