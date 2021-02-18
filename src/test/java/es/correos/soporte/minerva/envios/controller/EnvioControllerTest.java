package es.correos.soporte.minerva.envios.controller;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import es.correos.soporte.minerva.envios.dto.AdicionesRequestDTO;
import es.correos.soporte.minerva.envios.dto.AdicionesResponseDTO;
import es.correos.soporte.minerva.envios.dto.EnvioDTO;
import es.correos.soporte.minerva.envios.dto.EnvioImagenDTO;
import es.correos.soporte.minerva.envios.dto.EnvioListaCustomClientCodEtiqFilterDTO;
import es.correos.soporte.minerva.envios.dto.EnvioListaDTO;
import es.correos.soporte.minerva.envios.dto.EnvioSCPListaDTO;
import es.correos.soporte.minerva.envios.dto.EnvioTmpDTO;
import es.correos.soporte.minerva.envios.filter.EnvioFilter;
import es.correos.soporte.minerva.envios.service.AgrupacionesService;
import es.correos.soporte.minerva.envios.service.CentrosService;
import es.correos.soporte.minerva.envios.service.EnvioService;
import es.correos.soporte.minerva.envios.service.EventosService;
import es.correos.soporte.minerva.envios.service.ProductosService;
import es.correos.soporte.minerva.envios.util.FechaUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(EnvioController.class)
public class EnvioControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private EnvioService envioService;

  @MockBean private EventosService eventosService;

  @MockBean private AgrupacionesService agrupacionesService;

  @MockBean private CentrosService centrosService;

  @MockBean private ProductosService productosService;

  public static final String MEDIA_TYPE_CSV = "text/csv";

  protected static ObjectMapper om = new ObjectMapper();

  @Before
  public void setUp() {
    ObjectMapper om = new ObjectMapper();
    om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    JacksonTester.initFields(this, om);
  }

  @Test
  public void getEnviosTest1() throws Exception {

    List<EnvioDTO> lista = new ArrayList<EnvioDTO>();
    EnvioDTO response = new EnvioDTO();
    response.setId("1");
    lista.add(response);
    EnvioDTO response2 = new EnvioDTO();
    response2.setId("2");
    lista.add(response2);
    EnvioListaDTO listaenvios = new EnvioListaDTO(lista, null);

    when(envioService.getEnvios(Mockito.any())).thenReturn(listaenvios);

    mockMvc
        .perform(
            get("/shipments/query")
                .param("codired", "3")
                .param("ambit", "1")
                .param("reported-node", "1")
                .param("date-in", "2020-01-01T08:30:00+02:00")
                .param("date-fin", "2020-01-01T08:30:00+02:00")
                .param("unique-code", "PTE00003")
                .param("unique-event-code", "PTE00003")
                .param("ids", "3,4,1")
                .param("consignment", "123")
                .param("postal-office", "123")
                .param("size", "40")
                .param("order", "asc")
                .param("id-group", "2")
                .param("family", "PAQUETERIA")
                .param("subfamily", "ESTANDAR")
                .param("ids", "1,2")
                .param("sort", "_id")
                .param("product-ids", "1")
                .param("offset", "0")
                .param("reference", "123"))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void getEnviosTest2() throws Exception {

    List<EnvioDTO> lista = new ArrayList<EnvioDTO>();
    EnvioDTO response = new EnvioDTO();
    response.setId("1");
    lista.add(response);
    EnvioDTO response2 = new EnvioDTO();
    response2.setId("2");
    lista.add(response2);
    EnvioListaDTO listaenvios = new EnvioListaDTO(lista, null);

    when(envioService.getEnvios(Mockito.any())).thenReturn(listaenvios);

    mockMvc
        .perform(
            get("/shipments/query")
                .header(HttpHeaders.ACCEPT, "text/csv")
                .param("codired", "3")
                .param("ambit", "1")
                .param("reported-node", "1")
                .param("date-in", "2020-01-01T08:30:00+02:00")
                .param("date-fin", "2020-01-01T08:30:00+02:00")
                .param("status-code", "P100001V")
                .param("unique-code", "PTE00003")
                .param("ids", "3,4,1")
                .param("consignment", "123")
                .param("postal-office", "123")
                .param("size", "40")
                .param("order", "asc")
                .param("sort", "_id")
                .param("offset", "0")
                .param("client-union", "123"))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void getEnviosTest3() throws Exception {

    ArrayList<EnvioDTO> lista = new ArrayList<EnvioDTO>();
    EnvioDTO response = new EnvioDTO();
    response.setId("3");
    lista.add(response);
    EnvioDTO response2 = new EnvioDTO();
    response2.setId("3");
    lista.add(response2);
    EnvioListaDTO listaenvios = new EnvioListaDTO(lista, null);
    when(envioService.getEnvios(Mockito.any())).thenReturn(listaenvios);

    mockMvc
        .perform(
            get("/shipments/query")
                .param("codired", "3")
                .param("ambit", "1")
                .param("reported-node", "1")
                .param("date-in", "2020-01-01T08:30:00+02:00")
                .param("date-fin", "2020-01-01T08:30:00+02:00")
                .param("status-code", "P100001V")
                .param("unique-code", "PTE00003")
                .param("ids", "3,4,1")
                .param("consignment", "123")
                .param("postal-office", "123")
                .param("size", "40")
                .param("order", "asc")
                .param("sort", "_id")
                .param("offset", "0"))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void getEnvioIdTest() throws Exception {

    String codigo = "5f06681d6db101482463c217";
    EnvioDTO response = EnvioDTO.builder().id("5f06681d6db101482463c217").build();

    when(envioService.getEnvioById(codigo)).thenReturn(response);

    mockMvc
        .perform(get("/shipments/query/{codEnvio}", "5f06681d6db101482463c217"))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void altaAdicionesXLSXTest() throws Exception {

    AdicionesRequestDTO request =
        AdicionesRequestDTO.builder()
            .fileType("XLSX")
            .aditionsFile("base64File")
            .deliveryMode("1")
            .build();
    AdicionesResponseDTO response = new AdicionesResponseDTO();

    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
    String requestJson = ow.writeValueAsString(request);

    when(envioService.altaAdicion("XLSX", request.getAditionsFile(), request.getDeliveryMode()))
        .thenReturn(response);

    assertNotNull(
        envioService.altaAdicion("XLSX", request.getAditionsFile(), request.getDeliveryMode()));

    mockMvc
        .perform(put("/shipments").contentType(MediaType.APPLICATION_JSON).content(requestJson))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void altaAdicionesXLSest() throws Exception {

    AdicionesRequestDTO request =
        AdicionesRequestDTO.builder()
            .fileType("xls")
            .aditionsFile("base64File")
            .deliveryMode("1")
            .build();
    AdicionesResponseDTO response = new AdicionesResponseDTO();

    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
    String requestJson = ow.writeValueAsString(request);

    when(envioService.altaAdicion("XLS", request.getAditionsFile(), request.getDeliveryMode()))
        .thenReturn(response);

    assertNotNull(
        envioService.altaAdicion("XLS", request.getAditionsFile(), request.getDeliveryMode()));

    mockMvc
        .perform(put("/shipments").contentType(MediaType.APPLICATION_JSON).content(requestJson))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void testGetImage() throws Exception {

    mockMvc
        .perform(get("/shipments/query/{cio}/images", "090184543ba7fac7"))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void getTunelIdTest() throws Exception {

    EnvioTmpDTO request = new EnvioTmpDTO();
    request.setId("id");

    EnvioListaDTO response = new EnvioListaDTO();

    when(envioService.getEnvios(Mockito.any(EnvioFilter.class))).thenReturn(response);

    mockMvc
        .perform(
            post("/shipments/query")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(request)))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void getTunelIdCsvTest() throws Exception {

    EnvioTmpDTO request = new EnvioTmpDTO();
    request.setId("id");

    EnvioListaDTO response = new EnvioListaDTO();

    when(envioService.getEnvios(Mockito.any(EnvioFilter.class))).thenReturn(response);

    mockMvc
        .perform(
            post("/shipments/query")
                .header(HttpHeaders.ACCEPT, "text/csv")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(request)))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void getImageDownloadTest() throws Exception {
    EnvioImagenDTO imagenEnvio = new EnvioImagenDTO();
    imagenEnvio.setCodigo("UX6PZG0467032490119161W");
    imagenEnvio.setFechaAlta(FechaUtil.dateToStringDateOutput(new Date()));
    imagenEnvio.setImagenBase64("VVg2UFpHMDQ2NzAzMjQ5MDExOTE2MVc=");
    imagenEnvio.setTipoImagen("image/jpeg");

    String cio = "UX6PZG0467032490119161W";
    String imageCode = "imageCode";

    when(envioService.getEnvioImagen(cio, imageCode)).thenReturn(imagenEnvio);

    mockMvc
        .perform(get("/shipments/query/{cio}/images/{imageCode}", cio, imageCode))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void getEnviosTablaTest() throws Exception {

    EnvioTmpDTO request = new EnvioTmpDTO();
    request.setId("id");
    request.setId_group("id_group");
    request.setIds("1,2,3");

    EnvioListaDTO response = new EnvioListaDTO();

    when(envioService.getEnvios(Mockito.any(EnvioFilter.class))).thenReturn(response);

    mockMvc
        .perform(
            post("/shipments/query/table")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(request)))
        .andDo(print())
        .andExpect(status().isOk());
  }

  /*
   * @Test public void getEnviosCsvTest() throws Exception {
   *
   * EnvioTmpDTO request = new EnvioTmpDTO(); request.setId("id");
   * request.setId_group("id_group"); request.setIds("1,2,3");
   *
   * String csv = "csv";
   *
   * when(servicio.getEnviosCsv(Mockito.any(EnvioFilter.class))).thenReturn(csv);
   *
   * mockMvc .perform( post("/shipments/query/csv")
   * .contentType(MediaType.APPLICATION_JSON)
   * .content(om.writeValueAsString(request))) .andDo(print())
   * .andExpect(status().isOk()); }
   */

  @Test
  public void getEnviosTablaSCPTest() throws Exception {

    EnvioTmpDTO request = new EnvioTmpDTO();
    request.setId("id");
    request.setId_group("id_group");
    request.setIds("1,2,3");

    EnvioSCPListaDTO response = new EnvioSCPListaDTO();

    when(envioService.getEnviosSCP(Mockito.any(EnvioFilter.class))).thenReturn(response);

    mockMvc
        .perform(
            post("/shipments/query/tableSCP")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(request)))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void getEnviosCsvSCPTest() throws Exception {

    EnvioTmpDTO request = new EnvioTmpDTO();
    request.setId("id");
    request.setId_group("id_group");
    request.setIds("1,2,3");

    String csv = "csv";

    when(envioService.getEnviosCsvSCP(Mockito.any(EnvioFilter.class))).thenReturn(csv);

    mockMvc
        .perform(
            post("/shipments/query/csvSCP")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(request)))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void testGetEnviosCustomFilters() throws Exception {

    EnvioListaCustomClientCodEtiqFilterDTO envioListaCustomClientCodEtiqFilterDTO =
        new EnvioListaCustomClientCodEtiqFilterDTO();

    when(envioService.getEnviosCustomFilters(
            Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.anyInt()))
        .thenReturn(envioListaCustomClientCodEtiqFilterDTO);

    mockMvc
        .perform(
            get("/shipments/query/filters")
                .param("field", "client")
                .param("search", "1")
                .param("codEtiq", "2")
                .param("client", "1#2")
                .param("offset", "0")
                .param("size", "20"))
        .andDo(print())
        .andExpect(status().isOk());
  }
}
