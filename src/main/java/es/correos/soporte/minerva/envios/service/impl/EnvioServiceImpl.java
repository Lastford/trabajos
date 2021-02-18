package es.correos.soporte.minerva.envios.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import es.correos.arch.boot.feign.exceptions.FeignCommunicationException;
import es.correos.arq.ex.CorreosBusinessException;
import es.correos.soporte.minerva.envios.domain.Envio;
import es.correos.soporte.minerva.envios.domain.EnvioXsl;
import es.correos.soporte.minerva.envios.domain.custom.EnvioListaClienteUnion;
import es.correos.soporte.minerva.envios.domain.custom.EnvioListaContrato;
import es.correos.soporte.minerva.envios.dto.AdicionesDTO;
import es.correos.soporte.minerva.envios.dto.AdicionesResponseDTO;
import es.correos.soporte.minerva.envios.dto.EnvioDTO;
import es.correos.soporte.minerva.envios.dto.EnvioImagenDTO;
import es.correos.soporte.minerva.envios.dto.EnvioListaCustomClientCodEtiqFilterDTO;
import es.correos.soporte.minerva.envios.dto.EnvioListaDTO;
import es.correos.soporte.minerva.envios.dto.EnvioSCPDTO;
import es.correos.soporte.minerva.envios.dto.EnvioSCPListaDTO;
import es.correos.soporte.minerva.envios.dto.feign.agrupaciones.AgrupacionDTO;
import es.correos.soporte.minerva.envios.dto.feign.agrupaciones.ContenidoEnviosDTO;
import es.correos.soporte.minerva.envios.dto.feign.centros.CentroDTO;
import es.correos.soporte.minerva.envios.dto.feign.eventos.EventoCoordenadaDTO;
import es.correos.soporte.minerva.envios.dto.feign.eventos.EventoDTO;
import es.correos.soporte.minerva.envios.dto.feign.eventos.EventoFiltro;
import es.correos.soporte.minerva.envios.dto.soap.imagenes.ImagenSOAPDTO;
import es.correos.soporte.minerva.envios.exceptions.EnvioNotFoundExc;
import es.correos.soporte.minerva.envios.filter.EnvioCustomFilter;
import es.correos.soporte.minerva.envios.filter.EnvioFilter;
import es.correos.soporte.minerva.envios.mapper.EnvioFiltroClienteMapper;
import es.correos.soporte.minerva.envios.mapper.EnvioMapper;
import es.correos.soporte.minerva.envios.mapper.EventoMapper;
import es.correos.soporte.minerva.envios.repository.EnvioRepository;
import es.correos.soporte.minerva.envios.service.AgrupacionesService;
import es.correos.soporte.minerva.envios.service.CentrosService;
import es.correos.soporte.minerva.envios.service.EnvioService;
import es.correos.soporte.minerva.envios.service.EventosService;
import es.correos.soporte.minerva.envios.service.ImagenEdocService;
import es.correos.soporte.minerva.envios.service.ProductosService;
import es.correos.soporte.minerva.envios.util.CSVString;
import es.correos.soporte.minerva.envios.util.EnvioFechaFaseComparator;
import es.correos.soporte.minerva.envios.util.EnviosUtils;
import es.correos.soporte.minerva.envios.util.FechaUtil;
import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@Log4j2
public class EnvioServiceImpl implements EnvioService {

  @Autowired private ImagenEdocService imagenEdocService;

  @Autowired private EnvioRepository envioRepository;

  @Autowired private EnvioMapper envioMapper;

  @Autowired private EventoMapper eventoMapper;

  @Autowired private AgrupacionesService agrupacionesService;

  @Autowired private CentrosService centrosService;

  @Autowired private EventosService eventosService;

  @Autowired private EnvioFiltroClienteMapper envioFiltroClienteMapper;

  @Autowired private ProductosService productosService;

  @Override
  public EnvioListaDTO getEnvios(EnvioFilter filtro) throws FeignCommunicationException {

    // SGIE
    AgrupacionDTO agrupacionDTO = getAgrupacionIfIdAgrupacionExistsInFiltro(filtro);

    log.info("*Entrada -> busquedaParametros*");
    Page<Envio> response = envioRepository.busquedaParametros(filtro);
    log.info("*Entrada -> envioToEnvioDTO*");

    List<EnvioDTO> enviosDTO = envioMapper.envioToEnvioDTO(response.getContent());

    System.out.println("-->" + enviosDTO);
    // SGIE
    log.info("*Entrada -> setIdAgrupacionToEnviosDtoIfIdAgrupacionExistsInFiltro*");

    setIdAgrupacionToEnviosDtoIfIdAgrupacionExistsInFiltro(filtro, enviosDTO, agrupacionDTO);

    System.out.println("-->" + enviosDTO);

    log.info("*Entrada -> setUltimoEstadoToListaEnviosDTO*");
    // setUltimoEstadoToListaEnviosDTO(enviosDTO);

    log.info("*Entrada -> setEnriquecerEnvios*");
    // setEnriquecerEnvios(enviosDTO);

    System.out.println("-->" + enviosDTO);

    log.info("*Entrada -> return new EnvioListaDTO*");
    return new EnvioListaDTO(enviosDTO, response.getTotalElements());
  }

  @Override
  public EnvioSCPListaDTO getEnviosSCP(EnvioFilter filtro) throws FeignCommunicationException {

    log.info("*getEnviosSCP -> filtro: " + filtro + "*");

    log.info("*Entrada -> busquedaParametros*");
    Page<Envio> response = envioRepository.busquedaParametrosSCP(filtro);

    log.info("*Entrada -> envioToEnvioSCPDTO*");
    List<EnvioSCPDTO> enviosSCPDTO = envioMapper.envioToEnvioSCPDTO(response.getContent());

    log.info("*getEnviosSCP -> enviosSCPDTO: " + enviosSCPDTO + "*");

    log.info("*Entrada -> return new EnvioListaDTO*");
    return new EnvioSCPListaDTO(enviosSCPDTO, response.getTotalElements());
  }

  private void setEnriquecerEnvios(List<EnvioDTO> enviosDTO) throws FeignCommunicationException {
    if (enviosDTO != null && !enviosDTO.isEmpty()) {

      List<CentroDTO> centrosDTO =
          centrosService.getCentrosByCodired(EnviosUtils.getCodiredsFromListEnviosDTO(enviosDTO));

      for (CentroDTO centroDTO : centrosDTO) {
        enviosDTO.stream()
            .filter(envioDTO -> centroDTO.getCodiRedU().equals(envioDTO.getCodired()))
            .forEach(envioDTO -> envioDTO.setNomred(centroDTO.getUnityName()));
      }
    }
  }

  /* @Override
    public String getEnviosCsv(EnvioFilter filtro) throws FeignCommunicationException {
      log.info("EnvioService.getEnviosCsv(EnvioFilter filtro)");

      AgrupacionDTO agrupacionDTO = getAgrupacionIfIdAgrupacionExistsInFiltro(filtro);

      log.info("*Entrada -> busquedaParametros*");

      List<Envio> response = envioRepository.busquedaParametrosCSV(filtro);
      // Page<Envio> response = envioRepository.busquedaParametros(filtro);

      System.out.println("response" + response);

      List<EnvioDTO> enviosDTO = envioMapper.envioToEnvioDTO(response);

      // SGIE
      log.info("*Entrada -> setIdAgrupacionToEnviosDtoIfIdAgrupacionExistsInFiltro*");

      setIdAgrupacionToEnviosDtoIfIdAgrupacionExistsInFiltro(filtro, enviosDTO, agrupacionDTO);

      log.info("*Entrada -> setUltimoEstadoToListaEnviosDTO*");
      // setUltimoEstadoToListaEnviosDTO(enviosDTO);

      log.info("*Entrada -> setEnriquecerEnvios*");
      setEnriquecerEnvios(enviosDTO);

      System.out.println("enviosDTO" + enviosDTO);

      return CSVString.getString(enviosDTO);
    }
  */

  @Override
  public void createCsvFile(EnvioFilter filtro, final OutputStream outputStream)
      throws FeignCommunicationException {

    log.info("EnvioService.createCsvFile(EnvioFilter filtro,OutputStream outputStream)");

    // AgrupacionDTO agrupacionDTO = getAgrupacionIfIdAgrupacionExistsInFiltro(filtro);

    log.info("*Entrada -> busquedaParametrosCSV*");

    List<EnvioXsl> bbddResponse = envioRepository.busquedaParametrosCSV(filtro);
    
    System.out.println("bbddResponse : " + bbddResponse);
    
    List<EnvioDTO> enviosDTO = envioMapper.envioXslToEnvioDTO(bbddResponse);

//    TODO: descomentar cuando se tenga maestro productos desplegado
//    List<ProductoDTO> productosDTO = productosService.getProductos();
//    
//    enviosDTO.forEach(envioDTO -> 
//    	productosDTO.stream().
//    	filter( productoDTO -> productoDTO.getId()
//    			.equalsIgnoreCase(envioDTO.getCodProducto() )).findFirst()
//    	.ifPresent(productoDTO -> envioDTO.setNomProducto(productoDTO.getD()))
//    );
    
    // SGIE
    // log.info("*Entrada -> setIdAgrupacionToEnviosDtoIfIdAgrupacionExistsInFiltro*");

    // setIdAgrupacionToEnviosDtoIfIdAgrupacionExistsInFiltro(filtro, enviosDTO, agrupacionDTO);

    log.info("*Entrada -> setUltimoEstadoToListaEnviosDTO*");
    // setUltimoEstadoToListaEnviosDTO(enviosDTO);

    // log.info("*Entrada -> setEnriquecerEnvios*");
    // setEnriquecerEnvios(enviosDTO);

    try {
      final PrintWriter printWriter = getPrintWriterInitialized(outputStream);

      writeInCSV(enviosDTO, printWriter);

      printWriter.flush();
      printWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private PrintWriter getPrintWriterInitialized(final OutputStream outputStream)
      throws IOException {
    final PrintWriter printWriter = getCSVPrintWriter(outputStream);
    final ByteBuffer buffer = StandardCharsets.UTF_8.encode(CSVString.HEADERS);

    printWriter.append(StandardCharsets.UTF_8.decode(buffer).toString());

    return printWriter;
  }

  private PrintWriter getCSVPrintWriter(final OutputStream outputStream) throws IOException {
    final byte[] bom = new byte[] {(byte) 239, (byte) 187, (byte) 191};
    outputStream.write(bom);
    return new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
  }

  private void writeInCSV(final List<EnvioDTO> enviosDTO, final PrintWriter detailsWriter) {
    log.info("Writing in CSV...");
    final long start = System.currentTimeMillis();
    enviosDTO.stream().forEach(envioDTO -> detailsWriter.write(envioDTO.toStringForCSV()));
    final long end = System.currentTimeMillis();
    log.info("Write finished in CSV. Time: " + (end - start) / 1000f + " seconds");
  }

  @Override
  public String getEnviosCsvSCP(EnvioFilter filtro) {
    log.info("EnvioService.getEnviosCsv(EnvioFilter filtro)");
    return CSVString.getStringSCP(envioRepository.busquedaParametrosCSVSCP(filtro));
  }

  @Override
  public AdicionesResponseDTO altaAdicion(String documento, String fichero, String modalidadEntrega)
      throws IOException {

    List<AdicionesDTO> listaAdiciones = new ArrayList<>();
    AdicionesResponseDTO response = new AdicionesResponseDTO();
    byte[] decodedBytes = Base64.getDecoder().decode(fichero);
    InputStream inputStream = new ByteArrayInputStream(decodedBytes);

    if (documento.equalsIgnoreCase("XLS")) {

      HSSFWorkbook workbook = new HSSFWorkbook(inputStream);

      return contenedor(workbook, listaAdiciones, response, inputStream);

    } else {

      XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

      return contenedor(workbook, listaAdiciones, response, inputStream);
    }
  }

  private AdicionesDTO llenarInfo(AdicionesDTO adicion, EnvioRepository envioRepository) {

    Optional<Envio> envioOpt = envioRepository.findById(adicion.getCio());

    if (envioOpt.isPresent()) {

      Envio envio = envioOpt.get();
      envio.setCpDestino(adicion.getCp());

      envioRepository.save(envio);

      return null;
    }

    return adicion;
  }

  private AdicionesResponseDTO contenedor(
      Workbook workbook,
      List<AdicionesDTO> listaAdiciones,
      AdicionesResponseDTO response,
      InputStream inputStream)
      throws IOException {

    Sheet sheet = workbook.getSheetAt(0);
    int rowIndex = 0;
    int cellIndex = 0;
    for (Row row : sheet) {
      if (rowIndex > 2) {
        AdicionesDTO adicion = new AdicionesDTO();
        Iterator<Cell> cellIterator = row.iterator();
        while (cellIterator.hasNext()) {
          Cell cell = cellIterator.next();
          if (cellIndex == 0) {
            adicion.setCio(cell.toString());
            cellIndex++;
          } else if (cellIndex == 1) {
            adicion.setCp(cell.toString());
            cellIndex = 0;
          }
        }
        AdicionesDTO adixion = llenarInfo(adicion, envioRepository);

        if (adixion != null) {
          listaAdiciones.add(adixion);
        }
      }
      rowIndex++;
    }
    workbook.close();
    inputStream.close();
    response.setAditions(listaAdiciones);

    return response;
  }

  @Override
  public EnvioDTO getEnvioById(String id) throws EnvioNotFoundExc, FeignCommunicationException {

    List<String> totalReetiquetados = new ArrayList<>();

    Envio envio = envioRepository.getEnviosPadresHijos(id);

    if (envio == null) {
      throw new EnvioNotFoundExc(String.format("Envío con id %s no fue encontrado.", id));
    }

    if (envio.getReetiquetadoPadre() != null) {
      envio
          .getReetiquetadoPadre()
          .forEach(reetiquetadoPadre -> totalReetiquetados.add(reetiquetadoPadre.getId()));
    }

    if (envio.getReetiquetadoHijo() != null) {
      envio
          .getReetiquetadoHijo()
          .forEach(reetiquetadoHijo -> totalReetiquetados.add(reetiquetadoHijo.getId()));
    }

    List<String> idEnviosConsultarEventos = new ArrayList<>(Arrays.asList(envio.getId()));

    if (!totalReetiquetados.isEmpty()) {
      idEnviosConsultarEventos.addAll(totalReetiquetados);
    }

    EnvioDTO envioDTO = envioMapper.envioToEnvioDTO(envio);

    List<EventoDTO> eventosEnvios =
        eventosService.getEventosByFilterEvent(
            EventoFiltro.builder()
                .listaIdsEvento(idEnviosConsultarEventos)
                .isShipment(true)
                .allData(Boolean.valueOf(true))
                .build());

    List<AgrupacionDTO> agrupacionesDTO =
        agrupacionesService.getAgrupacionesByIdEnvio(envioDTO.getId());

    if (agrupacionesDTO != null && !agrupacionesDTO.isEmpty()) {

      AgrupacionDTO agrpFechaMasAlta = null;
      Date fechaAux = new Date(Long.MIN_VALUE);

      for (int index = 0; index < agrupacionesDTO.size(); index++) {

        ContenidoEnviosDTO envioDevuelto =
            agrupacionesDTO.get(index).getContenidoEnvios().stream()
                .filter(e -> e.getId().equals(envioDTO.getId()))
                .collect(Collectors.toList())
                .get(0);

        Date envioDevueltoDate = FechaUtil.convertStringToDate(envioDevuelto.getFechaIni());

        if (envioDevueltoDate.after(fechaAux)) {
          fechaAux = envioDevueltoDate;
          agrpFechaMasAlta = agrupacionesDTO.get(index);
        }
      }
      ;

      AgrupacionDTO agrupacionPadre = agrpFechaMasAlta;

      System.out.println("agrupacionPadre [ " + agrupacionPadre);

      AgrupacionDTO agrupacionDTO =
          agrupacionesService.getAgrupacionParentHierarchyById(agrupacionPadre.getId());

      List<AgrupacionDTO> padres = agrupacionDTO.getJerarquiaPadres();

      if (agrupacionDTO != null) {

        padres.add(
            AgrupacionDTO.builder()
                .id(agrupacionDTO.getId())
                .tipo(agrupacionDTO.getTipo())
                .build());
      }
      envioDTO.setJerarquiaPadres(padres);

      List<String> idsAgrupacionesConsultarEventos = new ArrayList<>();

      agrupacionesDTO.forEach(
          agrupacion -> idsAgrupacionesConsultarEventos.add(agrupacion.getId()));

      List<EventoDTO> eventosAgrupaciones =
          eventosService.getEventosByFilterEvent(
              EventoFiltro.builder()
                  .listaIdsEvento(idsAgrupacionesConsultarEventos)
                  .isShipment(false)
                  .allData(Boolean.valueOf(true))
                  .build());

      if (eventosAgrupaciones != null) {
        eventosAgrupaciones.stream()
            .forEach(
                agrp -> {
                  agrp.setId(id);
                });

        eventosEnvios.addAll(eventosAgrupaciones);
      }
      envioDTO.setCodAgrupacion(agrupacionesDTO.get(agrupacionesDTO.size() - 1).getId());
    }

    setFasesToEventosEnvios(eventosEnvios);

    setUltimoEstadoToEnvioDtoFromListaEventosEnvios(envioDTO, eventosEnvios);

    if (eventosEnvios != null && !eventosEnvios.isEmpty()) {

      Collections.sort(eventosEnvios, new EnvioFechaFaseComparator());
    }

    envioDTO.setEventos(eventoMapper.eventoDtoToEnvioEventoDTO(eventosEnvios));

    envioDTO.setIdEnvioAsociado(
        EnviosUtils.getStringConcatenatedByCommaFromArrayString(totalReetiquetados));

    setEnriquecerEventos(envioDTO);

    return envioDTO;
  }

  private void setEnriquecerEventos(EnvioDTO envioDTO) throws FeignCommunicationException {
    if (envioDTO != null && envioDTO.getEventos() != null) {

      List<CentroDTO> centrosDTO =
          centrosService.getCentrosByCodired(
              EnviosUtils.getCodiredsFromListEnvioEventosDTO(envioDTO.getEventos()));

      envioDTO
          .getEventos()
          .forEach(
              x ->
                  centrosDTO.stream()
                      .filter(y -> y.getCodiRedU().equals(x.getCodired()))
                      .findFirst()
                      .ifPresent(
                          z -> {
                            x.setUnidad(z.getUnityName());
                            x.setLocalidad(z.getUnityName());
                          }));
    }
  }

  private EventoDTO obtenerEventoJson(EnvioDTO envioDTO, List<EventoDTO> listaEventos)
      throws FeignCommunicationException {

    System.out.println("listaEventos --- [ " + listaEventos);

    if (listaEventos != null && !listaEventos.isEmpty()) {
      EventoDTO eventofase = listaEventos.get(0);
      System.out.println("* primer eventofase -> " + eventofase + "*");

      log.info("*eventofase -> " + eventofase + "*");
      Date primerafecha = FechaUtil.stringDateOutputToDate(listaEventos.get(0).getFechaHora());
      log.info("*primerafecha -> " + primerafecha + "*");
      for (EventoDTO eventoDTO : listaEventos) {
        Date fechaevento = FechaUtil.stringDateOutputToDate(eventoDTO.getFechaHora());
        log.info("*fechaevento -> " + fechaevento + "*");

        System.out.println(
            !eventoDTO.getDesEvento().isEmpty()
                + " == estado = "
                + eventoDTO.getDesEvento()
                + " [ "
                + eventoDTO.getDesEvento().length());

        if ((!eventoDTO.getDesEvento().isEmpty()
                && !eventoDTO.getDesEvento().equalsIgnoreCase("Enviado acuse de recibo"))
            && fechaevento != null
            && primerafecha != null
            && fechaevento.after(primerafecha)) {

          System.out.println(
              !eventoDTO.getDesEvento().isEmpty()
                  + " == estado dentro = "
                  + eventoDTO.getDesEvento()
                  + " [ "
                  + eventoDTO.getDesEvento().length());

          eventofase = eventoDTO;
          primerafecha = fechaevento;
        }

        log.info("*eventofase -> " + eventofase + "*");
        log.info("*primerafecha -> " + primerafecha + "*");
      }

      eventofase.setCodired(envioDTO.getCodired());
      // eventofase.setFechaHora(envioDTO.getEstado().getFecha());

      if (eventofase != null
          && eventofase.getCodired() != null
          && !eventofase.getCodired().isEmpty()) {

        List<CentroDTO> listacentro = centrosService.getCentrosByCodired(eventofase.getCodired());

        if (!listacentro.isEmpty()) {
          log.info("*eventofaseDentroDelIf -> " + eventofase + "*");
          EventoCoordenadaDTO coordenada = new EventoCoordenadaDTO();
          if (listacentro.get(0) != null && listacentro.get(0).getLongitude() != null) {
            coordenada.setX(listacentro.get(0).getLongitude().toString());
          }
          if (listacentro.get(0) != null && listacentro.get(0).getLatitude() != null) {
            coordenada.setY(listacentro.get(0).getLatitude().toString());
          }
          eventofase.setCoordenada(coordenada);
        }
      }

      System.out.println("FAKE---> eventofase -->" + eventofase);

      return eventofase;
    }
    return null;
  }

  private EventoDTO buscarUltimoEvento(List<EventoDTO> listaEventos)
      throws FeignCommunicationException {

    System.out.println("listaEventos --- [ " + listaEventos);

    if (listaEventos != null && !listaEventos.isEmpty()) {
      EventoDTO eventofase = listaEventos.get(0);
      System.out.println("* primer eventofase -> " + eventofase + "*");

      log.info("*eventofase -> " + eventofase + "*");
      Date primerafecha = FechaUtil.stringDateOutputToDate(listaEventos.get(0).getFechaHora());
      log.info("*primerafecha -> " + primerafecha + "*");
      for (EventoDTO eventoDTO : listaEventos) {
        Date fechaevento = FechaUtil.stringDateOutputToDate(eventoDTO.getFechaHora());
        log.info("*fechaevento -> " + fechaevento + "*");

        System.out.println(
            !eventoDTO.getDesEvento().isEmpty()
                + " == estado = "
                + eventoDTO.getDesEvento()
                + " [ "
                + eventoDTO.getDesEvento().length());

        if ((!eventoDTO.getDesEvento().isEmpty()
                && !eventoDTO.getDesEvento().equalsIgnoreCase("Enviado acuse de recibo"))
            && fechaevento != null
            && primerafecha != null
            && fechaevento.after(primerafecha)) {

          System.out.println(
              !eventoDTO.getDesEvento().isEmpty()
                  + " == estado dentro = "
                  + eventoDTO.getDesEvento()
                  + " [ "
                  + eventoDTO.getDesEvento().length());

          eventofase = eventoDTO;
          primerafecha = fechaevento;
        }

        log.info("*eventofase -> " + eventofase + "*");
        log.info("*primerafecha -> " + primerafecha + "*");
      }

      System.out.println("*eventofase ult -> " + eventofase + "*");
      System.out.println("*primerafecha ult -> " + primerafecha + "*");

      log.info("*eventofaseFueraDelFor -> " + eventofase + "*");
      if (eventofase != null
          && eventofase.getCodired() != null
          && !eventofase.getCodired().isEmpty()) {

        List<CentroDTO> listacentro = centrosService.getCentrosByCodired(eventofase.getCodired());

        if (!listacentro.isEmpty()) {
          log.info("*eventofaseDentroDelIf -> " + eventofase + "*");
          EventoCoordenadaDTO coordenada = new EventoCoordenadaDTO();
          if (listacentro.get(0) != null && listacentro.get(0).getLongitude() != null) {
            coordenada.setX(listacentro.get(0).getLongitude().toString());
          }
          if (listacentro.get(0) != null && listacentro.get(0).getLatitude() != null) {
            coordenada.setY(listacentro.get(0).getLatitude().toString());
          }
          eventofase.setCoordenada(coordenada);
        }
      }

      System.out.println("FAKE---> eventofase -->" + eventofase);

      return eventofase;
    }
    return null;
  }

  private void asignarCoordenada(EventoDTO eventodto, EventoDTO ultimoevento) {
    if (ultimoevento != null
        && eventodto.getFase() != null
        && ultimoevento.getFase() != null
        && eventodto.getFechaHora() != null
        && ultimoevento.getFechaHora() != null
        && eventodto.getFechaHora().equals(ultimoevento.getFechaHora())
        && eventodto.getFase().equals(ultimoevento.getFase())) {
      eventodto.setCoordenada(ultimoevento.getCoordenada());
    }
  }

  private String getEnviosIdsFromAgrupacion(AgrupacionDTO agrupacion, String ids) {
    System.out.println("agrupacionDTO -> " + agrupacion);

    StringBuilder bld = new StringBuilder();
    bld.append(ids);
    for (ContenidoEnviosDTO contenidoEnviosDTO : agrupacion.getContenidoEnvios()) {
      log.info("*getEnviosIdsFromAgrupacion -> contenidoEnviosDTO:" + contenidoEnviosDTO + "*");
      if (contenidoEnviosDTO != null) {
        bld.append(String.format("%s;", contenidoEnviosDTO.getId()));
      }
    }
    for (AgrupacionDTO agrupacionHijosDTO : agrupacion.getJerarquiaHijos()) {
      log.info("*getEnviosIdsFromAgrupacion -> agrupacionHijosDTO:" + agrupacionHijosDTO + "*");
      bld.append(String.format("%s;", getEnviosIdsFromAgrupacion(agrupacionHijosDTO, ids)));
    }
    return bld.toString();
  }

  private String getAgrupacionPadre(AgrupacionDTO agrupacion, String idEnvio) {
    String idAgrupacion = "";
    if (idEnvio != null && !idEnvio.equals("")) {
      for (ContenidoEnviosDTO contenidoEnviosDTO : agrupacion.getContenidoEnvios()) {
        if (idEnvio.equals(contenidoEnviosDTO.getId())) {
          idAgrupacion = agrupacion.getId();
          if (!idAgrupacion.equals("")) {
            break;
          }
        }
      }
      for (AgrupacionDTO agrupacionHijosDTO : agrupacion.getJerarquiaHijos()) {
        idAgrupacion = getAgrupacionPadre(agrupacionHijosDTO, idEnvio);
        if (!idAgrupacion.equals("")) {
          break;
        }
      }
    }
    return idAgrupacion;
  }

  @Override
  public EnvioImagenDTO getEnvioImagen(String cio, String codeImage)
      throws CorreosBusinessException {
    EnvioImagenDTO imageDTO = new EnvioImagenDTO();

    try {
      List<ImagenSOAPDTO> lstImagenSOAP = imagenEdocService.obtenerImagenes(cio);
      if (lstImagenSOAP != null) {
        for (ImagenSOAPDTO imagenSOAP : lstImagenSOAP) {
          if (codeImage.equalsIgnoreCase(imagenSOAP.getCodigo())) {
            imageDTO.setCodigo(imagenSOAP.getCodigo());
            imageDTO.setTipoImagen(imagenSOAP.getTipoImagen());
            imageDTO.setImagenBase64(imagenSOAP.getImagenBase64());
          }
        }
      }
    } catch (CorreosBusinessException cbe) {
      imageDTO = new EnvioImagenDTO();
    }

    return imageDTO;
  }

  @Override
  public List<EnvioImagenDTO> getEnvioImagenes(String cio) throws CorreosBusinessException {
    List<EnvioImagenDTO> lstImagenes = new ArrayList<>();
    try {
      List<ImagenSOAPDTO> lstImagenSOAP = imagenEdocService.obtenerImagenes(cio);
      if (lstImagenSOAP != null) {
        for (ImagenSOAPDTO imagenSOAP : lstImagenSOAP) {
          EnvioImagenDTO imageDTO = new EnvioImagenDTO();
          imageDTO.setCodigo(imagenSOAP.getCodigo());
          imageDTO.setVerificada(imagenSOAP.getVerificada());
          imageDTO.setFechaAlta(imagenSOAP.getFechaAlta());
          imageDTO.setTipoImagen(imagenSOAP.getTipoImagen());
          imageDTO.setFechaDigitacion(imagenSOAP.getFechaDigitacion());
          lstImagenes.add(imageDTO);
        }
      }
    } catch (CorreosBusinessException cbe) {
      lstImagenes = new ArrayList<>();
    }

    return lstImagenes;
  }

  private void setIdEnviosFromAgrupacionToFiltro(EnvioFilter filtro, AgrupacionDTO agrupacionDTO) {
    String ids = getEnviosIdsFromAgrupacion(agrupacionDTO, "");

    if (!ids.equals("")) {

      if (filtro.getId() != null) {
        filtro.setId(
            EnviosUtils.splitCadenaToArrayBySeparator(String.format("%s,%s", filtro.getId(), ids)));
      } else {
        filtro.setId(EnviosUtils.splitCadenaToArrayBySeparator(ids));
      }
    }
  }

  private AgrupacionDTO getAgrupacionIfIdAgrupacionExistsInFiltro(EnvioFilter filtro)
      throws FeignCommunicationException {
    // Operacion SGIE
    AgrupacionDTO agrupacionDTO = null;
    if (filtro.getIdAgrupacion() != null) {
      agrupacionDTO = agrupacionesService.getAgrupacionById(filtro.getIdAgrupacion(), true);

      if (agrupacionDTO != null) {
        setIdEnviosFromAgrupacionToFiltro(filtro, agrupacionDTO);
      }
    }
    return agrupacionDTO;
  }

  private void setIdAgrupacionToEnviosDtoIfIdAgrupacionExistsInFiltro(
      EnvioFilter filtro, List<EnvioDTO> enviosDTO, AgrupacionDTO agrupacionDTO) {
    // Operacion SGIE
    if (filtro.getIdAgrupacion() != null) {
      if (filtro.getId() != null) {
        for (EnvioDTO envioDTO : enviosDTO) {
          if (envioDTO.getCodAgrupacion() == null || envioDTO.getCodAgrupacion().equals("")) {
            envioDTO.setCodAgrupacion(getAgrupacionPadre(agrupacionDTO, envioDTO.getId()));
          }
        }
      } else {
        enviosDTO = new ArrayList<>();
      }
    }
  }

  // private void setUltimoEstadoToListaEnviosDTO(List<EnvioDTO> enviosDTO) throws
  // FeignCommunicationException {
  // List<String> ids = new ArrayList<>();
  // enviosDTO.forEach(x -> {
  // ids.add(x.getId());
  // });
  // System.out.println(ids);
  // if (!ids.isEmpty()) {
  // EventoListaDTO eventoListaDTO = eventosClient
  // .getEventos(FilterEvent.builder().listaIdsEvento(ids).isShipment(true).build());
  // log.info("*EventoListaDTO -> " + eventoListaDTO + "*");
  //
  //
  //
  // List<EventoDTO> eventosEnvios = eventoListaDTO.getData();
  //
  // if (eventosEnvios != null && !eventosEnvios.isEmpty()) {
  //
  // for (EnvioDTO envioDTO : enviosDTO) {
  //
  // setUltimoEstadoToEnvioDtoFromListaEventosEnvios(envioDTO,
  // eventosEnvios.stream().filter(
  // x -> x.getId() != null && !x.getId().isEmpty() &&
  // x.getId().equals(envioDTO.getId()))
  // .collect(Collectors.toList()));
  //
  // }
  // }
  // }
  // }

  private void setUltimoEstadoToEnvioDtoFromListaEventosEnvios(
      EnvioDTO envioDTO, List<EventoDTO> eventosEnvios) throws FeignCommunicationException {
    log.info(
        "*setUltimoEstadoToEnvioDtoFromListaEventosEnvios.eventosEnvios-> " + eventosEnvios + "*");

    EventoDTO ultimoEvento = obtenerEventoJson(envioDTO, eventosEnvios);

    if (envioDTO != null && envioDTO.getEstado() != null && ultimoEvento != null) {
      log.info(
          "*setUltimoEstadoToEnvioDtoFromListaEventosEnvios.ultimoEvento -> " + ultimoEvento + "*");
      envioDTO.getEstado().setCodigo(ultimoEvento.getCodEvento());
      envioDTO.getEstado().setCodRazon(ultimoEvento.getCodRazon());
      envioDTO.getEstado().setFecha(ultimoEvento.getFechaHora());
      envioDTO.getEstado().setNodoReportador(ultimoEvento.getCodired());
      envioDTO.getEstado().setOfiPostalDestino(ultimoEvento.getCodPostalDestino());
      envioDTO.setCodired(ultimoEvento.getCodired());
      envioDTO.setNomred(ultimoEvento.getUbicacion());
    }
  }

  private void setFasesToEventosEnvios(List<EventoDTO> eventosEnvios)
      throws FeignCommunicationException {
    if (eventosEnvios != null && !eventosEnvios.isEmpty()) {
      List<EventoDTO> eventosfase1 = new ArrayList<>();
      List<EventoDTO> eventosfase2 = new ArrayList<>();
      List<EventoDTO> eventosfase3 = new ArrayList<>();
      List<EventoDTO> eventosfase4 = new ArrayList<>();
      for (EventoDTO eventoDTO : eventosEnvios) {
        setEventoCoordenadaIfNotExists(eventoDTO);
        setFasesToListFasesEventosByTipoFaseEvento(
            eventoDTO, eventosfase1, eventosfase2, eventosfase3, eventosfase4);
      }

      EventoDTO ultimofase1 = buscarUltimoEvento(eventosfase1);
      EventoDTO ultimofase2 = buscarUltimoEvento(eventosfase2);
      EventoDTO ultimofase3 = buscarUltimoEvento(eventosfase3);
      EventoDTO ultimofase4 = buscarUltimoEvento(eventosfase4);

      for (EventoDTO eventoDTO : eventosEnvios) {
        asignarCoordenada(eventoDTO, ultimofase1);
        asignarCoordenada(eventoDTO, ultimofase2);
        asignarCoordenada(eventoDTO, ultimofase3);
        asignarCoordenada(eventoDTO, ultimofase4);
      }
    }
  }

  private void setEventoCoordenadaIfNotExists(EventoDTO eventoDTO) {
    if (eventoDTO != null
        && eventoDTO.getCoordenada() != null
        && (eventoDTO.getCoordenada().getX() == null
            || eventoDTO.getCoordenada().getX().equals("")
            || eventoDTO.getCoordenada().getX().equals("0.0"))) {
      eventoDTO.setCoordenada(new EventoCoordenadaDTO("", ""));
    }
  }

  private void setFasesToListFasesEventosByTipoFaseEvento(
      EventoDTO eventoDTO,
      List<EventoDTO> eventosfase1,
      List<EventoDTO> eventosfase2,
      List<EventoDTO> eventosfase3,
      List<EventoDTO> eventosfase4) {
    if (eventoDTO != null && eventoDTO.getFase() != null) {
      if (eventoDTO.getFase().equals("1")) {
        eventosfase1.add(eventoDTO);
      } else if (eventoDTO.getFase().equals("2")) {
        eventosfase2.add(eventoDTO);
      } else if (eventoDTO.getFase().equals("3")) {
        eventosfase3.add(eventoDTO);
      } else if (eventoDTO.getFase().equals("4")) {
        eventosfase4.add(eventoDTO);
      }
    }
  }

  @Override
  public EnvioListaCustomClientCodEtiqFilterDTO getEnviosCustomFilters(
      EnvioCustomFilter campoBuscar,
      MultiValueMap<String, String> filtros,
      Integer offset,
      Integer size) {
    List<String> clientesUnionSeleccionados = new ArrayList<>();
    List<String> filtroSearch = filtros.get("search");
    List<String> filtroClientes = filtros.get("client");
    List<String> filtroCodEtiqs = filtros.get("codEtiq");
    String valorBuscado = null;

    if (filtroSearch != null) {
      valorBuscado = filtroSearch.get(0);
    }

    if (filtroClientes != null) {
      filtroClientes.forEach(
          filtroCliente -> {
            List<String> numContratoConClientesUnion =
                new ArrayList<>(Arrays.asList(filtroCliente.split("#")));
            if (numContratoConClientesUnion.size() > 1) {
              clientesUnionSeleccionados.addAll(
                  numContratoConClientesUnion.subList(1, numContratoConClientesUnion.size()));
            }
          });
    }

    if (filtroCodEtiqs != null) {
      clientesUnionSeleccionados.addAll(filtroCodEtiqs);
    }

    if (campoBuscar.equals(EnvioCustomFilter.client)) {
      EnvioListaContrato enviosContratos =
          envioRepository.getEnviosAgrupadosPorNumContrato(
              valorBuscado, clientesUnionSeleccionados, offset, size);

      return envioFiltroClienteMapper.envioListaContratoToEnvioListaCustomClientCodEtiqFilterDto(
          enviosContratos);
    }

    EnvioListaClienteUnion enviosClienteUnion =
        envioRepository.getEnviosAgrupadosPorClienteUnion(
            valorBuscado, clientesUnionSeleccionados, offset, size);

    return envioFiltroClienteMapper.envioListaClienteUnionToEnvioListaCustomClientCodEtiqFilterDto(
        enviosClienteUnion);
  }
  
  
}
