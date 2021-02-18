package es.correos.soporte.minerva.envios.controller;

import es.correos.arch.boot.feign.exceptions.FeignCommunicationException;
import es.correos.arq.ex.CorreosBusinessException;
import es.correos.soporte.minerva.envios.config.enums.FilterOrderEnum;
import es.correos.soporte.minerva.envios.dto.AdicionesRequestDTO;
import es.correos.soporte.minerva.envios.dto.AdicionesResponseDTO;
import es.correos.soporte.minerva.envios.dto.EnvioDataDTO;
import es.correos.soporte.minerva.envios.dto.EnvioImagenDTO;
import es.correos.soporte.minerva.envios.dto.EnvioListaCustomClientCodEtiqFilterDTO;
import es.correos.soporte.minerva.envios.dto.EnvioListaDTO;
import es.correos.soporte.minerva.envios.dto.EnvioListaImagenDTO;
import es.correos.soporte.minerva.envios.dto.EnvioSCPListaDTO;
import es.correos.soporte.minerva.envios.dto.EnvioSCPTmpDTO;
import es.correos.soporte.minerva.envios.dto.EnvioTmpDTO;
import es.correos.soporte.minerva.envios.exceptions.EnvioNotFoundExc;
import es.correos.soporte.minerva.envios.filter.EnvioCustomFilter;
import es.correos.soporte.minerva.envios.filter.EnvioFilter;
import es.correos.soporte.minerva.envios.service.EnvioService;
import es.correos.soporte.minerva.envios.util.EnviosUtils;
import es.correos.soporte.minerva.envios.util.FechaUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import lombok.extern.log4j.Log4j2;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "/", tags = "Endpoints de envios SCP")
@RequestMapping("/shipments")
@Validated
@Log4j2
public class EnvioController {

  @Autowired private EnvioService enviosService;

  public static final String MEDIA_TYPE_CSV = "text/csv";
  public static final String DATE_FORMAT_NOW = "yyyy_MM_dd_HH_mm_ss";

  @ApiOperation(
      value = "Check Images of shipments by ID..",
      nickname = "Check a shipments by id.",
      response = EnvioImagenDTO.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 404, message = "Records Not Found"),
        @ApiResponse(code = 500, message = "Internal error")
      })
  @GetMapping(
      value = "/query/{cio}/images",
      produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  public ResponseEntity<Object> getImage(
      @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authorizationHeader,
      @PathVariable String cio)
      throws CorreosBusinessException {

    List<EnvioImagenDTO> lstImagenEnvio = enviosService.getEnvioImagenes(cio);

    return new ResponseEntity<>(new EnvioListaImagenDTO(lstImagenEnvio), HttpStatus.OK);
  }

  @ApiOperation(
      value = "Check Images of shipments by ID.",
      nickname = "Check a shipments by id.",
      response = EnvioImagenDTO.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 404, message = "Records Not Found"),
        @ApiResponse(code = 500, message = "Internal error")
      })
  @GetMapping(
      value = "/query/{cio}/images/{imageCode}",
      produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  public ResponseEntity<Object> getImageDownload(
      @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authorizationHeader,
      @PathVariable String cio,
      @PathVariable String imageCode)
      throws CorreosBusinessException {
    EnvioImagenDTO imagenDownload = enviosService.getEnvioImagen(cio, imageCode);
    return new ResponseEntity<>(imagenDownload, HttpStatus.OK);
  }

  @ApiOperation(
      value = "Check the shipments.",
      nickname = "Check the shipments.",
      response = EnvioDataDTO.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 404, message = "Records Not Found"),
        @ApiResponse(code = 500, message = "Internal error")
      })
  @PostMapping(
      value = "/query",
      produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MEDIA_TYPE_CSV})
  public ResponseEntity<Object> getTunelId(
      @RequestBody EnvioTmpDTO envio,
      @RequestHeader(
              name = HttpHeaders.ACCEPT,
              required = false,
              defaultValue = MediaType.APPLICATION_JSON_UTF8_VALUE)
          String acceptHeader,
      @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authorizationHeader,
      HttpServletResponse response)
      throws FeignCommunicationException, IOException {

    ResponseEntity<Object> listaEnviosDTO =
        getEnvios(
            envio.getDate_in(),
            envio.getDate_fin(),
            envio.getCodired(),
            envio.getUnique_code(),
            envio.getStatus_code(),
            envio.getId(),
            envio.getReported_node(),
            envio.getAmbito(),
            envio.getIdDespachoPostal(),
            envio.getRemesa(),
            envio.getId_group(),
            envio.getMessage_type(),
            envio.getProduct_ids(),
            envio.getOffset(),
            envio.getSize(),
            envio.getOrder(),
            envio.getSort(),
            envio.getReference(),
            envio.getPrerecorded_code(),
            envio.getExpedition_code(),
            envio.getUnique_event_code(),
            envio.getFamilia(),
            envio.getSubFamilia(),
            envio.getClienteUnion(),
            acceptHeader,
            authorizationHeader,
            response);

    if (acceptHeader.contains(MediaType.parseMediaType(MEDIA_TYPE_CSV).toString())) {
      String nombreFichero = "INFORME_ENVIOS_" + FechaUtil.now() + ".csv";
      return ResponseEntity.ok()
          .contentType(MediaType.parseMediaType(MEDIA_TYPE_CSV))
          .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + nombreFichero + "\"")
          .body(listaEnviosDTO.getBody());
    } else {
      return new ResponseEntity<>(listaEnviosDTO.getBody(), HttpStatus.OK);
    }
  }

  @ApiOperation(
      value = "Check the shipments.",
      nickname = "Check a shipment by id.",
      response = EnvioDataDTO.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 404, message = "Records Not Found."),
        @ApiResponse(code = 500, message = "Internal error.")
      })
  @GetMapping(
      value = "/query/{codEnvio}",
      produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  public ResponseEntity<Object> getEnvioById(
      @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authorizationHeader,
      @NotNull @PathVariable String codEnvio)
      throws FeignCommunicationException, EnvioNotFoundExc {

    return new ResponseEntity<>(
        new EnvioDataDTO(enviosService.getEnvioById(codEnvio)), HttpStatus.OK);
  }

  @ApiOperation(
      response = EnvioDataDTO.class,
      value = "Check the shipments.",
      nickname = "Check a list of shipments.")
  @GetMapping(
      value = "/query",
      produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MEDIA_TYPE_CSV})
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 400, message = "Bad request."),
        @ApiResponse(code = 500, message = "Internal Error."),
        @ApiResponse(code = 404, message = "Records Not Found.")
      })
  public ResponseEntity<Object> getEnvios(
      @RequestParam(name = "date-in", required = false) String fechaCreacionIni,
      @RequestParam(name = "date-fin", required = false) String fechaCreacionFin,
      @RequestParam(name = "codired", required = false) String codired,
      @RequestParam(name = "unique-code", required = false) String codigoUnico,
      @RequestParam(name = "status-code", required = false) String codigoEstado,
      @RequestParam(name = "ids", required = false) String ids,
      @RequestParam(name = "reported-node", required = false) String nodoreportador,
      @RequestParam(name = "ambit", required = false) String ambito,
      @RequestParam(name = "postal-office", required = false) String idDespachoPostal,
      @RequestParam(name = "consignment", required = false) String remesa,
      @RequestParam(name = "id-group", required = false) String idAgrupacion,
      @RequestParam(name = "message-type", required = false) String tipoMensaje,
      @RequestParam(name = "product-ids", required = false) String productIds,
      @RequestParam(name = "offset", required = false) @PositiveOrZero Integer offset,
      @RequestParam(name = "size", required = false) @Positive @Max(value = 200) Integer size,
      @RequestParam(name = "order", required = false) FilterOrderEnum orden,
      @RequestParam(name = "sort", required = false) String sort,
      @RequestParam(name = "reference", required = false) String referencia,
      @RequestParam(name = "prerecorded-code", required = false) String codPregrabado,
      @RequestParam(name = "expedition-code", required = false) String codExpedicion,
      @RequestParam(name = "unique-event-code", required = false) String eventoUnico,
      @RequestParam(name = "family", required = false) String familia,
      @RequestParam(name = "subfamily", required = false) String subFamilia,
      @RequestParam(name = "union-client", required = false) String clienteUnion,
      @RequestHeader(
              name = HttpHeaders.ACCEPT,
              required = false,
              defaultValue = MediaType.APPLICATION_JSON_UTF8_VALUE)
          String acceptHeader,
      @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authorizationHeader,
      HttpServletResponse response)
      throws FeignCommunicationException, IOException {
    EnvioFilter filtro =
        EnvioFilter.builder()
            .codired(codired)
            .idAgrupacion(idAgrupacion)
            .tipoMensaje(tipoMensaje)
            .nodoReportador(nodoreportador)
            .ambito(ambito)
            .codigoPregrabado(codPregrabado)
            .codigoExpedicion(codExpedicion)
            .sort(EnviosUtils.getValorFiltroSort(sort))
            .remesa(remesa)
            .idDespachoPostal(idDespachoPostal)
            .build();

    setPaginationIfExists(filtro, offset, size, orden);
    setIdsFilterIfExists(filtro, null, ids);
    setReferenciaFilterIfExists(filtro, referencia);
    setProductoFilterIfExists(filtro, productIds);
    setCodigoEventoUnicoIfExists(filtro, eventoUnico);
    setCodigoUnicoCodigoEstadoIfExists(filtro, codigoUnico, codigoEstado);
    setFechaFiltroIfExists(filtro, fechaCreacionIni, fechaCreacionFin);
    setFamiliaFilterIfExists(filtro, familia);
    setSubFamiliaFilterIfExists(filtro, subFamilia);
    setClienteUnionFilterIfExists(filtro, clienteUnion);

    if (acceptHeader.contains(MediaType.parseMediaType(MEDIA_TYPE_CSV).toString())) {
      EnvioTmpDTO envioFiltroCsv =
          EnvioTmpDTO.builder()
              .ambito(ambito)
              .codired(codired)
              .remesa(remesa)
              .date_fin(fechaCreacionFin)
              .date_in(fechaCreacionIni)
              .expedition_code(codExpedicion)
              .id_group(idAgrupacion)
              .ids(ids)
              .message_type(tipoMensaje)
              .offset(offset)
              .order(orden)
              .idDespachoPostal(idDespachoPostal)
              .prerecorded_code(codPregrabado)
              .product_ids(productIds)
              .reference(referencia)
              .reported_node(nodoreportador)
              .size(size)
              .sort(sort)
              .status_code(codigoEstado)
              .unique_code(codigoUnico)
              .unique_event_code(eventoUnico)
              .build();

      response.setContentType(MEDIA_TYPE_CSV);
      response.setCharacterEncoding(StandardCharsets.UTF_8.name());
      response.setHeader(
          HttpHeaders.CONTENT_DISPOSITION,
          "attachment; filename=\"" + "Envios_" + FechaUtil.now() + ".csv\"");

      enviosService.createCsvFile(crearFiltroBusquedas(envioFiltroCsv), response.getOutputStream());

      return new ResponseEntity<>(HttpStatus.OK);
    }

    EnvioListaDTO listaEnviosDTO = enviosService.getEnvios(filtro);

    return new ResponseEntity<>(listaEnviosDTO, HttpStatus.OK);
  }

  @ApiOperation(
      response = AdicionesResponseDTO.class,
      value = "Alta adiciones.",
      notes = "Alta adiciones.")
  @PutMapping(produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Petición mal formada.")})
  public ResponseEntity<AdicionesResponseDTO> altaAdiciones(
      @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authorizationHeader,
      @RequestBody AdicionesRequestDTO request)
      throws IOException {
    AdicionesResponseDTO response = new AdicionesResponseDTO();
    if (request.getFileType().equalsIgnoreCase("XLSX")) {
      response =
          enviosService.altaAdicion("XLSX", request.getAditionsFile(), request.getDeliveryMode());

    } else if (request.getFileType().equalsIgnoreCase("xls")) {
      response =
          enviosService.altaAdicion("xls", request.getAditionsFile(), request.getDeliveryMode());
    }
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  private void setFechaFiltroIfExists(
      EnvioFilter filtro, String fechaCreacionIni, String fechaCreacionFin) {

    if (fechaCreacionIni != null) {
      filtro.setFechaInicial(FechaUtil.stringIsoOffsetDateTimeToDate(fechaCreacionIni));
    }

    if (fechaCreacionFin != null) {
      filtro.setFechaFinal(FechaUtil.stringIsoOffsetDateTimeToDate(fechaCreacionFin));
    }
  }

  private void setIdsFilterIfExists(EnvioFilter filtro, String id, String ids) {
    List<String> listIds = new ArrayList<>();
    if (ids != null) {
      listIds = EnviosUtils.splitCadenaToArrayBySeparator(ids);
    }
    if (filtro.getId() != null) {
      listIds.add(id);
    }
    filtro.setId(listIds);
  }

  private void setReferenciaFilterIfExists(EnvioFilter filtro, String referencia) {
    List<String> listReferencias = new ArrayList<>();
    if (referencia != null) {
      listReferencias = EnviosUtils.splitCadenaToArrayBySeparator(referencia);
    }
    filtro.setReferencia(listReferencias);
  }

  private void setProductoFilterIfExists(EnvioFilter filtro, String productIds) {
    List<String> listProductos = new ArrayList<>();
    if (productIds != null) {
      listProductos = EnviosUtils.splitCadenaToArrayBySeparator(productIds);
    }
    filtro.setProductIds(listProductos);
  }

  private void setCodigoEventoUnicoIfExists(EnvioFilter filtro, String eventoUnico) {
    List<String> eventosUnicos = new ArrayList<>();
    if (eventoUnico != null) {
      eventosUnicos = EnviosUtils.splitCadenaToArrayBySeparator(eventoUnico);
    }
    filtro.setEventoUnico(eventosUnicos);
  }

  private void setCodigoUnicoCodigoEstadoIfExists(
      EnvioFilter filtro, String codigoUnico, String codigoSituacion) {
    List<String> listaCodigosUnicos = new ArrayList<>();
    List<String> listaCodigoSituaciones = new ArrayList<>();

    if (codigoSituacion != null) {
      listaCodigoSituaciones = EnviosUtils.splitCadenaToArrayBySeparator(codigoSituacion);
    }

    if (codigoUnico != null) {
      listaCodigosUnicos = EnviosUtils.splitCadenaToArrayBySeparator(codigoUnico);
    }

    filtro.setCodigoUnico(listaCodigosUnicos);
    filtro.setCodigoEstado(listaCodigoSituaciones);
  }

  private void setPaginationIfExists(
      EnvioFilter filtro, Integer offset, Integer size, FilterOrderEnum orden) {
    if (size != null) {
      filtro.setSize(size);
    }
    if (offset != null) {
      filtro.setOffset(offset);
    }
    if (orden != null) {
      filtro.setOrder(orden);
    }
  }

  private void setFamiliaFilterIfExists(EnvioFilter filtro, String familia) {
    List<String> listaFamilias = new ArrayList<>();
    if (familia != null) {
      listaFamilias = EnviosUtils.splitCadenaToArrayBySeparator(familia);
    }
    filtro.setFamilia(listaFamilias);
  }

  private void setSubFamiliaFilterIfExists(EnvioFilter filtro, String subFamilia) {
    List<String> listaSubFamilias = new ArrayList<>();
    if (subFamilia != null) {
      listaSubFamilias = EnviosUtils.splitCadenaToArrayBySeparator(subFamilia);
    }
    filtro.setSubFamilia(listaSubFamilias);
  }

  private void setClienteUnionFilterIfExists(EnvioFilter filtro, String clienteUnion) {
    List<String> listaClientesUnion = new ArrayList<>();
    if (clienteUnion != null) {
      listaClientesUnion = EnviosUtils.splitCadenaToArrayBySeparator(clienteUnion);
    }
    filtro.setClienteUnion(listaClientesUnion);
  }

  // consultar metodo post
  @ApiOperation(
      value = "Check the shipments.",
      nickname = "Check a shipment by id.",
      response = EnvioTmpDTO.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 404, message = "Records Not Found"),
        @ApiResponse(code = 500, message = "Internal error")
      })
  @PostMapping(
      value = "/query/table",
      produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  @ResponseBody
  public EnvioListaDTO getEnviosTabla(
      @RequestBody EnvioTmpDTO envio,
      @RequestHeader(
              name = HttpHeaders.ACCEPT,
              required = false,
              defaultValue = MediaType.APPLICATION_JSON_UTF8_VALUE)
          String acceptHeader,
      @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authorizationHeader)
      throws FeignCommunicationException {
    return enviosService.getEnvios(crearFiltroBusquedas(envio));
  }

  private EnvioFilter crearFiltroBusquedas(EnvioTmpDTO envio) {

    EnvioFilter filtro =
        EnvioFilter.builder()
            .codired(envio.getCodired())
            .idAgrupacion(envio.getId_group())
            .tipoMensaje(envio.getMessage_type())
            .nodoReportador(envio.getReported_node())
            .ambito(envio.getAmbito())
            .codigoPregrabado(envio.getPrerecorded_code())
            .codigoExpedicion(envio.getExpedition_code())
            .sort(EnviosUtils.getValorFiltroSort(envio.getSort()))
            .remesa(envio.getRemesa())
            .idDespachoPostal(envio.getIdDespachoPostal())
            .build();

    setPaginationIfExists(filtro, envio.getOffset(), envio.getSize(), envio.getOrder());
    setIdsFilterIfExists(filtro, envio.getId(), envio.getIds());
    setReferenciaFilterIfExists(filtro, envio.getReference());
    setProductoFilterIfExists(filtro, envio.getProduct_ids());
    setCodigoEventoUnicoIfExists(filtro, envio.getUnique_event_code());
    setCodigoUnicoCodigoEstadoIfExists(filtro, envio.getUnique_code(), envio.getStatus_code());
    setFechaFiltroIfExists(filtro, envio.getDate_in(), envio.getDate_fin());
    setFamiliaFilterIfExists(filtro, envio.getFamilia());
    setSubFamiliaFilterIfExists(filtro, envio.getSubFamilia());

    return filtro;
  }

  @ApiOperation(
      value = "Check the shipments.",
      nickname = "Check a shipment by id.",
      response = EnvioTmpDTO.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 404, message = "Records Not Found"),
        @ApiResponse(code = 500, message = "Internal error")
      })
  @PostMapping(
      value = "/query/csv",
      produces = {MEDIA_TYPE_CSV})
  @ResponseBody
  public void getEnviosCsv(
      @RequestBody EnvioTmpDTO envio,
      @RequestHeader(name = HttpHeaders.ACCEPT, required = false, defaultValue = MEDIA_TYPE_CSV)
          String acceptHeader,
      @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authorizationHeader,
      HttpServletResponse response)
      throws FeignCommunicationException, JSONException, IOException {
    log.info("POST /query/csv");
    envio.setSort(null);

    response.setContentType(MEDIA_TYPE_CSV);
    response.setCharacterEncoding(StandardCharsets.UTF_8.name());
    response.setHeader(
        HttpHeaders.CONTENT_DISPOSITION,
        "attachment; filename=\"" + "Envios_" + FechaUtil.now() + ".csv\"");

    enviosService.createCsvFile(crearFiltroBusquedas(envio), response.getOutputStream());
  }

  // consultar metodo post
  @ApiOperation(
      value = "Check the shipments.",
      nickname = "Check a shipment by id.",
      response = EnvioTmpDTO.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 404, message = "Records Not Found"),
        @ApiResponse(code = 500, message = "Internal error")
      })
  @PostMapping(
      value = "/query/tableSCP",
      produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  @ResponseBody
  public EnvioSCPListaDTO getEnviosTablaSCP(
      @RequestBody EnvioSCPTmpDTO envio,
      @RequestHeader(
              name = HttpHeaders.ACCEPT,
              required = false,
              defaultValue = MediaType.APPLICATION_JSON_UTF8_VALUE)
          String acceptHeader,
      @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authorizationHeader)
      throws FeignCommunicationException {
    return enviosService.getEnviosSCP(crearFiltroBusquedasSCP(envio));
  }

  @ApiOperation(
      value = "Check the shipments.",
      nickname = "Check a shipment by id.",
      response = EnvioTmpDTO.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 404, message = "Records Not Found"),
        @ApiResponse(code = 500, message = "Internal error")
      })
  @PostMapping(
      value = "/query/csvSCP",
      produces = {MEDIA_TYPE_CSV})
  @ResponseBody
  public ResponseEntity<Object> getEnviosCsvSCP(
      @RequestBody EnvioSCPTmpDTO envio,
      @RequestHeader(name = HttpHeaders.ACCEPT, required = false, defaultValue = MEDIA_TYPE_CSV)
          String acceptHeader,
      @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authorizationHeader)
      throws FeignCommunicationException, JSONException, IOException {
    log.info("POST /query/csv");
    String out = enviosService.getEnviosCsvSCP(crearFiltroBusquedasSCP(envio));
    log.info("CSV lenght: " + out.length());
    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(MEDIA_TYPE_CSV))
        .header(
            HttpHeaders.CONTENT_DISPOSITION,
            "attachment;filename=\"Envios_" + FechaUtil.now() + ".csv\"")
        .body(out);
  }

  private EnvioFilter crearFiltroBusquedasSCP(EnvioSCPTmpDTO envio) {

    EnvioFilter filtro =
        EnvioFilter.builder()
            .circuitoAduana(envio.getCustoms_circuit())
            .cliente(envio.getClient())
            .codMoneda(envio.getCurrency_code())
            .codPaisDestino(envio.getDestination_country_code())
            .codPaisOrigen(envio.getOrigin_country_code())
            .codTipoMensaje(envio.getMessage_type_code())
            .cp(envio.getPostal_code())
            .idDespachoPostal(envio.getPostal_office())
            .errorPrerregistro(envio.getPreregistration_error())
            .fechaReg(envio.getRegistration_date())
            .fechaValidez(envio.getValidity_date())
            .modalidadEntrega(envio.getDelivery_mode())
            .modo(envio.getMode())
            .offset(envio.getOffset())
            .producto(envio.getProduct())
            .recipiente(envio.getRecipient())
            .retenidoAduanas(envio.getCustoms_hold())
            .size(envio.getSize())
            .valorDeclarado(envio.getDeclared_value())
            .build();

    setFechaFiltroIfExists(filtro, envio.getDate_in(), envio.getDate_fin());
    setIdsFilterIfExists(filtro, envio.getId(), envio.getIds());

    return filtro;
  }

  @ApiOperation(
      response = EnvioListaCustomClientCodEtiqFilterDTO.class,
      value = "Get Shipments by Custom Filters",
      nickname = "Get Shipments by Custom Filters",
      notes =
          "Returns a paginated list of Shipments by custom filters: \n\t&bull; **codEtiq** (filtered by **client**)\n\t&bull; **client** (filtered by **codEtiq**) Format: contractCode#codEtiq1#codEtiq2 \n\t&bull; **search** (value to find)")
  @GetMapping(
      value = "/query/filters",
      produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 400, message = "Bad request."),
        @ApiResponse(code = 500, message = "Internal Error."),
        @ApiResponse(code = 404, message = "Records Not Found.")
      })
  public ResponseEntity<Object> getEnviosCustomFilters(
      @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authorizationHeader,
      @RequestParam(name = "field", required = true) @NotNull EnvioCustomFilter campoBuscar,
      @RequestParam(required = false) MultiValueMap<String, String> filters,
      @RequestParam(name = "offset", required = false, defaultValue = "0") @PositiveOrZero
          Integer offset,
      @RequestParam(name = "size", required = false, defaultValue = "20")
          @Positive
          @Max(value = 200)
          Integer size) {

    return new ResponseEntity<>(
        enviosService.getEnviosCustomFilters(campoBuscar, filters, offset, size), HttpStatus.OK);
  }
  

  
}
