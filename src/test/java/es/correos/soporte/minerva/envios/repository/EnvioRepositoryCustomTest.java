package es.correos.soporte.minerva.envios.repository;

import static org.junit.Assert.assertNotNull;

import es.correos.soporte.minerva.envios.config.enums.FilterOrderEnum;
import es.correos.soporte.minerva.envios.domain.Envio;
import es.correos.soporte.minerva.envios.dto.EnvioDTO;
import es.correos.soporte.minerva.envios.filter.EnvioFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class EnvioRepositoryCustomTest {

  @Mock private MongoTemplate mongoTemplate;

  @InjectMocks private EnvioRepositoryCustomImpl envioRepository;

  @Test
  public void busquedaCombinadaTest() {
    Date date = new Date();
    Envio envio = Envio.builder().id("1").codUpu("1").build();
    Envio envio2 = Envio.builder().id("1").codUpu("1").build();
    List<Envio> lista = new ArrayList<Envio>();
    lista.add(envio);
    lista.add(envio2);
    EnvioDTO envioDTO = EnvioDTO.builder().id("1").codProducto("1").build();
    ArrayList<EnvioDTO> listaDTO = new ArrayList<EnvioDTO>();
    listaDTO.add(envioDTO);
    EnvioFilter filtro =
        EnvioFilter.builder()
            .fechaInicial(date)
            .fechaFinal(date)
            .codired("1")
            .codigoEstado(new ArrayList<>(Arrays.asList("F")))
            .id(
                new ArrayList<>(
                    Arrays.asList("5ef35fa4fc39600958adbaa7", "5ef35fa4fc39600958adbaa7")))
            .nodoReportador("123")
            .ambito("123")
            .idDespachoPostal("despacho")
            .remesa("remesa")
            .referencia(new ArrayList<>(Arrays.asList("referencia")))
            .tipoMensaje("Mensaje")
            .sort("40")
            .productIds(new ArrayList<>(Arrays.asList("porductosid")))
            .offset(5)
            .size(10)
            .codigoUnico(new ArrayList<>(Arrays.asList("codigosUnicos")))
            .eventoUnico(new ArrayList<>(Arrays.asList("eventosUnicos")))
            .codigoPregrabado("codigoPregrabado")
            .codigoExpedicion("codigoExpedicion")
            .familia(new ArrayList<>(Arrays.asList("familia")))
            .subFamilia(new ArrayList<>(Arrays.asList("subFamilia")))
            .build();

    List<String> productsids = new ArrayList<String>();
    productsids.add("pr");
    productsids.add("nu");

    assertNotNull(envioRepository.busquedaParametros(filtro));
    EnvioFilter filtro2 =
        EnvioFilter.builder()
            .id(new ArrayList<String>())
            .productIds(productsids)
            .order(FilterOrderEnum.asc)
            .build();
    envioRepository.busquedaParametros(filtro2);
  }

  @Test
  public void setFiltrosSCPTest() {
    Date date = new Date();
    EnvioFilter filtro =
        EnvioFilter.builder()
            .fechaInicial(date)
            .fechaFinal(date)
            .codired("1")
            .codigoEstado(new ArrayList<>(Arrays.asList("F")))
            .id(
                new ArrayList<>(
                    Arrays.asList("5ef35fa4fc39600958adbaa7", "5ef35fa4fc39600958adbaa7")))
            .nodoReportador("123")
            .ambito("123")
            .idDespachoPostal("despacho")
            .remesa("remesa")
            .referencia(new ArrayList<>(Arrays.asList("referencia")))
            .tipoMensaje("Mensaje")
            .sort("40")
            .productIds(new ArrayList<>(Arrays.asList("porductosid")))
            .clienteUnion(new ArrayList<>(Arrays.asList("clienteUnion1")))
            .offset(5)
            .size(10)
            .codigoUnico(new ArrayList<>(Arrays.asList("codigosUnicos")))
            .eventoUnico(new ArrayList<>(Arrays.asList("eventosUnicos")))
            .codigoPregrabado("codigoPregrabado")
            .codigoExpedicion("codigoExpedicion")
            .recipiente("recipiente")
            .producto("PD")
            .cp("cp")
            .tipoMensaje("INT")
            .codMoneda("EU")
            .cliente("cliente")
            .codPaisOrigen("origen")
            .codPaisDestino("destino")
            .codTipoMensaje("MS")
            .build();
    envioRepository.busquedaParametrosSCP(filtro);
    envioRepository.busquedaParametrosCSVSCP(filtro);
  }

  @Test
  public void busquedaParametrosTest() {
    Date date = new Date();
    EnvioFilter filtro =
        EnvioFilter.builder()
            .fechaInicial(date)
            .fechaFinal(date)
            .codired("1")
            .codigoEstado(new ArrayList<>(Arrays.asList("F")))
            .id(
                new ArrayList<>(
                    Arrays.asList("5ef35fa4fc39600958adbaa7", "5ef35fa4fc39600958adbaa7")))
            .nodoReportador("123")
            .ambito("123")
            .idDespachoPostal("despacho")
            .remesa("remesa")
            .referencia(new ArrayList<>(Arrays.asList("referencia")))
            .tipoMensaje("Mensaje")
            .sort("40")
            .productIds(new ArrayList<>(Arrays.asList("porductosid")))
            .clienteUnion(new ArrayList<>(Arrays.asList("clienteUnion1")))
            .offset(5)
            .size(10)
            .codigoUnico(new ArrayList<>(Arrays.asList("codigosUnicos")))
            .eventoUnico(new ArrayList<>(Arrays.asList("eventosUnicos")))
            .codigoPregrabado("codigoPregrabado")
            .codigoExpedicion("codigoExpedicion")
            .recipiente("recipiente")
            .producto("PD")
            .cp("cp")
            .tipoMensaje("INT")
            .codMoneda("EU")
            .cliente("cliente")
            .codPaisOrigen("origen")
            .codPaisDestino("destino")
            .codTipoMensaje("MS")
            .build();
    envioRepository.busquedaParametrosCSV(filtro);
  }

  @Test(expected = NullPointerException.class)
  public void getEnviosPadresHijosTest() {
    envioRepository.getEnviosPadresHijos("id");
  }

  @Test(expected = NullPointerException.class)
  public void getEnviosAgrupadosPorNumContratoTest_NotContratosSeleccionados() {
    List<String> numContratos = new ArrayList<>();

    envioRepository.getEnviosAgrupadosPorNumContrato(null, numContratos, 0, 20);
  }

  @Test(expected = NullPointerException.class)
  public void getEnviosAgrupadosPorNumContratoTestSinValorBuscado() {
    List<String> numContratos = new ArrayList<>(Arrays.asList("1234"));

    envioRepository.getEnviosAgrupadosPorNumContrato(null, numContratos, 0, 20);
  }

  @Test(expected = NullPointerException.class)
  public void getEnviosAgrupadosPorNumContratoTestConValorBuscadoSinNumContratosSeleccionados() {
    List<String> numContratos = new ArrayList<>();

    envioRepository.getEnviosAgrupadosPorNumContrato("1", numContratos, 0, 20);
  }

  @Test(expected = NullPointerException.class)
  public void getEnviosAgrupadosPorNumContratoTestConValorBuscado() {
    List<String> numContratos = new ArrayList<>(Arrays.asList("1234"));

    envioRepository.getEnviosAgrupadosPorNumContrato("1", numContratos, 0, 20);
  }

  @Test(expected = NullPointerException.class)
  public void getEnviosAgrupadosPorClienteUnionTest_NotClientesUnionSeleccionados() {
    List<String> clientesUnion = new ArrayList<>();

    envioRepository.getEnviosAgrupadosPorClienteUnion(null, clientesUnion, 0, 20);
  }

  @Test(expected = NullPointerException.class)
  public void getEnviosAgrupadosPorClienteUnionTestSinValorBuscado() {
    List<String> clientesUnion = new ArrayList<>(Arrays.asList("1234"));

    envioRepository.getEnviosAgrupadosPorClienteUnion(null, clientesUnion, 0, 20);
  }

  @Test(expected = NullPointerException.class)
  public void
      getEnviosAgrupadosPorClienteUnionTestConValorBuscadoSinNumClientesUnionSeleccionados() {
    List<String> clientesUnion = new ArrayList<>();

    envioRepository.getEnviosAgrupadosPorClienteUnion("1", clientesUnion, 0, 20);
  }

  @Test(expected = NullPointerException.class)
  public void getEnviosAgrupadosPorClienteUnionTestConValorBuscado() {
    List<String> clientesUnion = new ArrayList<>(Arrays.asList("1234"));

    envioRepository.getEnviosAgrupadosPorClienteUnion("1", clientesUnion, 0, 20);
  }
}
