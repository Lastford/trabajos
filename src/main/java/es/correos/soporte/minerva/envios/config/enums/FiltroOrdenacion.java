package es.correos.soporte.minerva.envios.config.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FiltroOrdenacion {
  id("_id"),
  codupu("codupu"),
  currency_code("DETAILS.coddivisa"),
  recipient_code("DETAILS.codrecipiente"),
  has_receipt_acknowledgement("DETAILS.indAcuseRecibo"),
  is_returned("DETAILS.idenviodevolucion"),
  consignment("DETAILS.remesa"),
  origin_country_code("codpaisorigen"),
  destination_country_code("codpaisorigen"),
  is_international("flag_int"),
  admission_date("DETAILS.fechaadmision"),
  reference1("DETAILS.referencia1"),
  reference2("DETAILS.referencia2"),
  reference3("DETAILS.referencia3"),
  delivery_counter("DETAILS.numentregas"),
  weight("DETAILS.peso"),
  length("DETAILS.longitud"),
  height("DETAILS.altura"),
  width("DETAILS.anchura"),
  comments("DETAILS.observaciones"),
  estimated_delivery_date("DOC_ENV.fechacomprometidaentregacalculada"),
  provided_delivery_date("DOC_ENV.fecha_comprometidaentregasuministrada"),
  agreed_delivery_date("DETAILS.estado.fechaentregaconcertada"),
  postal_office_id("DETAILS.iddespachopostal"),
  shipment_type("DETAILS.tipoenvionacido"),
  creation_date("DETAILS.fechaadmision"),
  categorization("categorizacion"),
  exclusive_delivery("entregaexclusiva"),
  reason_code("codrazon"),
  codired("codired"),
  unity_center_code("codired"),
  unity_center_name("DOC_ENV.UD_Specific"),
  date("fechaultimoevento"),
  code("codevento"),
  type("DETAILS.tipoenvionacido"),
  elements_number("DETAILS.fechaadmision");

  private String nombreCampo;
}
