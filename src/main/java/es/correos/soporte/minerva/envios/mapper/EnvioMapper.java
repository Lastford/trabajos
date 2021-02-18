package es.correos.soporte.minerva.envios.mapper;

import es.correos.soporte.minerva.envios.domain.Envio;
import es.correos.soporte.minerva.envios.domain.EnvioXsl;
import es.correos.soporte.minerva.envios.domain.FileEnvio;
import es.correos.soporte.minerva.envios.domain.custom.EnvioCSV;
import es.correos.soporte.minerva.envios.dto.EnvioDTO;
import es.correos.soporte.minerva.envios.dto.EnvioFileDTO;
import es.correos.soporte.minerva.envios.dto.EnvioSCPDTO;
import es.correos.soporte.minerva.envios.util.FechaUtil;
import java.util.Date;
import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface EnvioMapper {

  FileEnvio convertFileEnvioDTOToFileEnvio(EnvioFileDTO envioDTO);

  EnvioFileDTO convertFileEnvioToFileEnvioDTO(FileEnvio fileEnvio);

  @Mapping(source = "docEnv.ctaDestCodired", target = "ctaDestCodired", defaultValue = "")
  @Mapping(source = "docEnv.ctaDest", target = "ctaDest", defaultValue = "")
  @Mapping(source = "panel.nombreProducto", target = "nomProducto", defaultValue = "")
  @Mapping(source = "panel.p", target = "codProducto", defaultValue = "")
  @Mapping(source = "detalles.codDivisa", target = "codDivisa", defaultValue = "")
  @Mapping(source = "detalles.codRecipiente", target = "codRecipiente", defaultValue = "")
  @Mapping(source = "detalles.indAcuseRecibo", target = "indAcuseRecibo", defaultValue = "")
  @Mapping(source = "detalles.idEnvioDevolucion", target = "devuelto", defaultValue = "")
  @Mapping(source = "detalles.remesa", target = "remesa", defaultValue = "")
  @Mapping(source = "flagInt", target = "internacional", defaultValue = "")
  @Mapping(source = "codPaisOrigen", target = "codPaisOrigen", defaultValue = "")
  @Mapping(source = "codPaisDestino", target = "codPaisDestino", defaultValue = "")
  @Mapping(
      source = "detalles.fechaAdmision",
      target = "fechaAdmision",
      qualifiedByName = "formatDateToStringFecha",
      defaultValue = "")
  @Mapping(source = "detalles.referencia1", target = "referencia1", defaultValue = "")
  @Mapping(source = "detalles.referencia2", target = "referencia2", defaultValue = "")
  @Mapping(source = "detalles.referencia3", target = "referencia3", defaultValue = "")
  @Mapping(source = "detalles.numEntregas", target = "numEntregas", defaultValue = "")
  @Mapping(source = "codPregrabado", target = "codPregrabado", defaultValue = "")
  @Mapping(source = "detalles.peso", target = "peso", defaultValue = "0")
  @Mapping(source = "detalles.longitud", target = "longitud", defaultValue = "")
  @Mapping(source = "detalles.altura", target = "altura", defaultValue = "")
  @Mapping(source = "detalles.anchura", target = "anchura", defaultValue = "")
  @Mapping(source = "detalles.observaciones", target = "observaciones", defaultValue = "")
  @Mapping(
      source = "docEnv.fechaComprometidaEntregaCalculada",
      target = "fechaComprometidaEntregaCalculada",
      qualifiedByName = "formatDateToStringFecha",
      defaultValue = "")
  @Mapping(
      source = "docEnv.fechaComprometidaEntregaSuministrada",
      target = "fechaComprometidaEntregaSuministrada",
      qualifiedByName = "formatDateToStringFecha",
      defaultValue = "")
  @Mapping(source = "unicoEvento", target = "unicoEvento", defaultValue = "")
  @Mapping(
      source = "detalles.estado.fechaEntregaConcertada",
      target = "fechaEntregaConcertada",
      defaultValue = "")
  @Mapping(source = "detalles.idDespachoPostal", target = "idDespachoPostal", defaultValue = "")
  @Mapping(source = "idEnvioAsociado", target = "idEnvioAsociado", defaultValue = "")
  @Mapping(source = "detalles.tipoEnvioNacido", target = "tipoEnvioNacido", defaultValue = "")
  @Mapping(source = "codAgrupacion", target = "codAgrupacion", defaultValue = "")
  @Mapping(
      source = "detalles.fechaAdmision",
      target = "fechaRegistro",
      qualifiedByName = "formatDateToStringFecha",
      defaultValue = "")
  @Mapping(source = "categorizacion", target = "categorizacion", defaultValue = "")
  @Mapping(source = "entregaExclusiva", target = "entregaExclusiva", defaultValue = "")
  @Mapping(source = "codired", target = "codired", defaultValue = "")
  @Mapping(source = "nomcodired", target = "nomred", defaultValue = "")
  @Mapping(
      source = "fechaUltimoEvento",
      target = "estado.fecha",
      qualifiedByName = "formatDateToStringFecha",
      defaultValue = "")
  @Mapping(source = "codEvento", target = "estado.codigo", defaultValue = "")
  @Mapping(source = "codRazon", target = "estado.codRazon", defaultValue = "")
  @Mapping(
      source = "detalles.estado.ofiPostalOrigen",
      target = "estado.idDespachoPostal",
      defaultValue = "")
  @Mapping(
      source = "detalles.ofiPostalDestino",
      target = "estado.ofiPostalDestino",
      defaultValue = "")
  @Mapping(
      source = "detalles.fechaCaducidadEstacionado",
      target = "estado.fechaCaducidadEstacionado",
      defaultValue = "")
  @Mapping(
      source = "detalles.estado.descNodoReportador",
      target = "estado.nodoReportador",
      defaultValue = "")
  @Mapping(source = "resumenUltimo", target = "estado.resumenSituacion", defaultValue = "")
  @Mapping(source = "detalles.remitente.codPais", target = "remitente.codPais", defaultValue = "")
  @Mapping(
      source = "detalles.remitente.codProvincia",
      target = "remitente.codProvincia",
      defaultValue = "")
  @Mapping(
      source = "detalles.remitente.localidad",
      target = "remitente.localidad",
      defaultValue = "")
  @Mapping(
      source = "detalles.remitente.codPostal",
      target = "remitente.codPostal",
      defaultValue = "")
  @Mapping(
      source = "detalles.remitente.direccion",
      target = "remitente.direccion",
      defaultValue = "")
  @Mapping(
      source = "detalles.remitente.razonSocial",
      target = "remitente.razonSocial",
      defaultValue = "")
  @Mapping(source = "detalles.remitente.nifcif", target = "remitente.nifcif", defaultValue = "")
  @Mapping(
      source = "detalles.remitente.apellidos",
      target = "remitente.apellidos",
      defaultValue = "")
  @Mapping(source = "detalles.remitente.telefono", target = "remitente.telefono", defaultValue = "")
  @Mapping(source = "detalles.remitente.nombre", target = "remitente.nombre", defaultValue = "")
  @Mapping(source = "detalles.remitente.email", target = "remitente.email", defaultValue = "")
  @Mapping(source = "docEnv.idCliente", target = "remitente.idCliente", defaultValue = "")
  @Mapping(
      source = "detalles.destinatario.codPais",
      target = "destinatario.codPais",
      defaultValue = "")
  @Mapping(
      source = "detalles.destinatario.codProvincia",
      target = "destinatario.codProvincia",
      defaultValue = "")
  @Mapping(
      source = "detalles.destinatario.localidad",
      target = "destinatario.localidad",
      defaultValue = "")
  @Mapping(
      source = "detalles.destinatario.direccion",
      target = "destinatario.direccion",
      defaultValue = "")
  @Mapping(
      source = "detalles.destinatario.razonSocial",
      target = "destinatario.razonSocial",
      defaultValue = "")
  @Mapping(
      source = "detalles.destinatario.nombre",
      target = "destinatario.nombre",
      defaultValue = "")
  @Mapping(source = "detalles.destinatario.email", target = "destinatario.email", defaultValue = "")
  @Mapping(
      source = "detalles.destinatario.nifcif",
      target = "destinatario.nifcif",
      defaultValue = "")
  @Mapping(
      source = "detalles.destinatario.apellidos",
      target = "destinatario.apellidos",
      defaultValue = "")
  @Mapping(
      source = "detalles.destinatario.telefono",
      target = "destinatario.telefono",
      defaultValue = "")
  @Mapping(source = "cpDestino", target = "destinatario.codPostal", defaultValue = "")
  @Mapping(target = "destinatario.idCliente", constant = "")
  @Mapping(
      source = "detalles.servicios.bolGenerarPEElectronica",
      target = "servicios.bolGenerarPEElectronica",
      defaultValue = "")
  @Mapping(
      source = "detalles.servicios.importeGirado",
      target = "servicios.importeGirado",
      defaultValue = "")
  @Mapping(
      source = "detalles.servicios.reembolso",
      target = "servicios.reembolso",
      defaultValue = "")
  @Mapping(
      source = "detalles.servicios.asegurado",
      target = "servicios.asegurado",
      defaultValue = "")
  @Mapping(
      source = "detalles.servicios.indemnizacion",
      target = "servicios.indemnizacion",
      defaultValue = "")
  @Mapping(
      source = "detalles.servicios.bolGenerarPEFisica",
      target = "servicios.bolGenerarPEFisica",
      defaultValue = "")
  @Mapping(
      source = "detalles.servicios.custodiaPEE",
      target = "servicios.custodiaPEE",
      defaultValue = "")
  @Mapping(
      source = "detalles.servicios.custodiaPEF",
      target = "servicios.custodiaPEF",
      defaultValue = "")
  @Mapping(
      source = "detalles.servicios.bolGenerarPEMixta",
      target = "servicios.bolGenerarPEMixta",
      defaultValue = "")
  @Mapping(
      source = "detalles.servicios.custodiaPEM",
      target = "servicios.custodiaPEM",
      defaultValue = "")
  @Mapping(
      source = "detalles.servicios.numIntentos",
      target = "servicios.numIntentos",
      defaultValue = "")
  @Mapping(
      source = "detalles.servicios.numDiasLista",
      target = "servicios.numDiasLista",
      defaultValue = "")
  @Mapping(
      source = "detalles.servicios.indAvisoRecibido",
      target = "servicios.indAvisoRecibido",
      defaultValue = "")
  @Mapping(
      source = "detalles.servicios.mesesCustodia",
      target = "servicios.mesesCustodia",
      defaultValue = "")
  @Mapping(
      source = "detalles.servicios.importeTotal",
      target = "servicios.importeTotal",
      defaultValue = "")
  @Mapping(
      source = "detalles.servicios.importePagado",
      target = "servicios.importePagado",
      defaultValue = "")
  @Mapping(
      source = "detalles.servicios.indEntregaRecogida",
      target = "servicios.indEntregaRecogida",
      defaultValue = "")
  EnvioDTO envioToEnvioDTO(Envio envio);

  @IterableMapping(elementTargetType = EnvioDTO.class)
  List<EnvioDTO> envioToEnvioDTO(List<Envio> envio);

  @Mapping(source = "docEnv.ctaDestCodired", target = "ctaDestCodired", defaultValue = "")
  @Mapping(source = "panel.nombreProducto", target = "nomProducto", defaultValue = "")
  @Mapping(source = "detalles.remesa", target = "remesa", defaultValue = "")
  @Mapping(source = "detalles.referencia1", target = "referencia1", defaultValue = "")
  @Mapping(source = "detalles.referencia2", target = "referencia2", defaultValue = "")
  @Mapping(source = "detalles.referencia3", target = "referencia3", defaultValue = "")
  @Mapping(source = "codPregrabado", target = "codPregrabado", defaultValue = "")
  @Mapping(source = "unicoEvento", target = "unicoEvento", defaultValue = "")
  @Mapping(source = "detalles.idDespachoPostal", target = "idDespachoPostal", defaultValue = "")
  @Mapping(source = "detalles.etiquetadoHijo", target = "idEnvioAsociado", defaultValue = "")
  @Mapping(source = "codired", target = "codired", defaultValue = "")
  @Mapping(source = "nomcodired", target = "nomred", defaultValue = "")
  @Mapping(
      source = "fechaUltimoEvento",
      target = "estado.fecha",
      qualifiedByName = "formatDateToStringFecha",
      defaultValue = "")
  @Mapping(source = "codEvento", target = "estado.codigo", defaultValue = "")
  @Mapping(source = "resumenUltimo", target = "estado.resumenSituacion", defaultValue = "")
  @Mapping(source = "panel.p", target = "codProducto", defaultValue = "")
  EnvioDTO envioXslToEnvioDTO(EnvioXsl envio);
  
  @IterableMapping(elementTargetType = EnvioDTO.class)
  List<EnvioDTO> envioXslToEnvioDTO(List<EnvioXsl> envio);
  
  @Mapping(source = "codProducto", target = "codProducto", defaultValue = "")
  @Mapping(source = "nomProducto", target = "nomProducto", defaultValue = "")
  @Mapping(source = "resumenSituacion", target = "estado.resumenSituacion", defaultValue = "")
  @Mapping(
      source = "fechaUltimoEvento",
      target = "estado.fecha",
      qualifiedByName = "formatDateToStringFecha",
      defaultValue = "")
  @Mapping(source = "codiRed", target = "codired", defaultValue = "")
  @Mapping(source = "nomCodiRed", target = "nomred", defaultValue = "")
  @Mapping(source = "etiquetadoHijo", target = "idEnvioAsociado", defaultValue = "")
  @Mapping(source = "referencia1", target = "referencia1", defaultValue = "")
  @Mapping(source = "referencia2", target = "referencia2", defaultValue = "")
  @Mapping(source = "referencia3", target = "referencia3", defaultValue = "")
  EnvioDTO envioCsvToEnvioDTO(EnvioCSV envioCsv);

  @IterableMapping(elementTargetType = EnvioDTO.class)
  List<EnvioDTO> envioCsvToEnvioDTO(List<EnvioCSV> enviosCsv);

  // TODO: Terminar mapeos cuando esten disponibles los campos
  @Mapping(source = "id", target = "id", defaultValue = "")
  @Mapping(source = "", target = "fechaReg", defaultValue = "")
  @Mapping(source = "", target = "fechaValidez", defaultValue = "")
  @Mapping(source = "tipoMensaje", target = "codTipoMensaje", defaultValue = "")
  @Mapping(source = "codPaisOrigen", target = "codPaisOrigen", defaultValue = "")
  @Mapping(source = "cpDestino", target = "cp", defaultValue = "")
  @Mapping(source = "codPaisDestino", target = "codPaisDestino", defaultValue = "")
  @Mapping(source = "", target = "modo", defaultValue = "")
  @Mapping(source = "", target = "errorPrerregistro", defaultValue = "")
  @Mapping(source = "panel.p", target = "producto", defaultValue = "")
  @Mapping(source = "docEnv.idCliente", target = "cliente", defaultValue = "")
  @Mapping(source = "", target = "modalidadEntrega", defaultValue = "")
  @Mapping(source = "", target = "retenidoAduanas", defaultValue = "")
  @Mapping(source = "", target = "valorDeclarado", defaultValue = "")
  @Mapping(source = "", target = "circuitoAduana", defaultValue = "")
  @Mapping(source = "detalles.codDivisa", target = "codMoneda", defaultValue = "")
  @Mapping(source = "detalles.idDespachoPostal", target = "despacho", defaultValue = "")
  @Mapping(source = "detalles.codRecipiente", target = "recipiente", defaultValue = "")
  EnvioSCPDTO envioToEnvioSCPDTO(Envio envio);

  @IterableMapping(elementTargetType = EnvioSCPDTO.class)
  List<EnvioSCPDTO> envioToEnvioSCPDTO(List<Envio> listaEnvios);

  @Named("formatDateToStringFecha")
  default String formatDateToStringFecha(Date fecha) {
    return FechaUtil.dateToStringDateOutput(fecha);
  }
}
