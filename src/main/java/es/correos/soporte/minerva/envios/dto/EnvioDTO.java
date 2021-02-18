package es.correos.soporte.minerva.envios.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import es.correos.soporte.minerva.envios.dto.feign.agrupaciones.AgrupacionDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnvioDTO implements Serializable {

  private static final long serialVersionUID = 8192701872898276714L;

  @JsonProperty("id")
  private String id;

  @JsonProperty("product_code")
  private String codProducto;

  @JsonProperty("product_name")
  private String nomProducto;

  @JsonProperty("cta_des")
  private String ctaDest;

  @JsonProperty("cta_desnom")
  private String ctaDestCodired;

  @JsonProperty("currency_code")
  private String codDivisa;

  @JsonProperty("recipient_code")
  private String codRecipiente;

  @JsonProperty("has_receipt_acknowledgement")
  private String indAcuseRecibo;

  @JsonProperty("is_returned")
  private String devuelto;

  @JsonProperty("consignment")
  private String remesa;

  @JsonProperty("origin_country_code")
  private String codPaisOrigen;

  @JsonProperty("destination_country_code")
  private String codPaisDestino;

  @JsonProperty("is_international")
  private String internacional;

  @JsonProperty("admission_date")
  private String fechaAdmision;

  @JsonProperty("reference1")
  private String referencia1;

  @JsonProperty("reference2")
  private String referencia2;

  @JsonProperty("reference3")
  private String referencia3;

  @JsonProperty("delivery_counter")
  private String numEntregas;

  @JsonProperty("weight")
  private Integer peso;

  @JsonProperty("length")
  private String longitud;

  @JsonProperty("height")
  private String altura;

  @JsonProperty("width")
  private String anchura;

  @JsonProperty("comments")
  private String observaciones;

  @JsonProperty("estimated_delivery_date")
  private String fechaComprometidaEntregaCalculada;

  @JsonProperty("provided_delivery_date")
  private String fechaComprometidaEntregaSuministrada;

  @JsonProperty("agreed_delivery_date")
  private String fechaEntregaConcertada;

  @JsonProperty("postal_office_id")
  private String idDespachoPostal;

  @JsonProperty("associated_shipment_id")
  private String idEnvioAsociado;

  @JsonProperty("shipment_type")
  private String tipoEnvioNacido;

  @JsonProperty("group_id")
  private String codAgrupacion;

  @JsonProperty("creation_date")
  private String fechaRegistro;

  @JsonProperty("categorization")
  private String categorizacion;

  @JsonProperty("exclusive_delivery")
  private String entregaExclusiva;

  @JsonProperty("type_message")
  private String tipoMensaje;

  @JsonProperty("prerecorded_code")
  private String codPregrabado;

  @JsonProperty("expedition_code")
  private String codExpedicion;

  @JsonProperty("unique_code")
  private String unicoEvento;

  @Default
  @JsonProperty("status")
  private EnvioEstadoDTO estado = new EnvioEstadoDTO();

  @Default
  @JsonProperty("sender")
  private EnvioDatoDTO remitente = new EnvioDatoDTO();

  @Default
  @JsonProperty("destination")
  private EnvioDatoDTO destinatario = new EnvioDatoDTO();

  @Default
  @JsonProperty("services")
  private EnvioServicioDTO servicios = new EnvioServicioDTO();

  @Default
  @JsonProperty("events")
  private List<EnvioEventoDTO> eventos = new ArrayList<>();

  @JsonProperty("codired")
  private String codired;

  @JsonProperty("codired_name")
  private String nomred;

  @Default
  @JsonProperty("parent_hierarchy")
  private List<AgrupacionDTO> jerarquiaPadres = new ArrayList<>();

  @Default
  @JsonProperty("relabelled_parent_hierarchy")
  private List<EnvioDTO> reetiquetadoPadre = new ArrayList<>();

  @Default
  @JsonProperty("relabelled_child_hierarchy")
  private List<EnvioDTO> reetiquetadoHijo = new ArrayList<>();

  public String toStringForCSV() {
    StringBuilder result = new StringBuilder();

    result
        .append("\n")
        .append(changeNull(getId()))
        .append(";")
        .append(getNomProducto() != null ? changeNull(getNomProducto()) : "")
        .append(";")
        .append(
            getEstado().getResumenSituacion() != null
                ? changeNull(getEstado().getResumenSituacion())
                : "")
        .append(";")
        .append(getEstado().getFecha() != null ? changeNull(getEstado().getFecha()) : "")
        .append(";")
        .append(getCodired() != null ? changeNull(getCodired()) : "")
        .append(";")
        .append(getNomred() != null ? changeNull(getNomred()) : "")
        .append(";")
        .append(getIdEnvioAsociado() != null ? changeNull(getIdEnvioAsociado()) : "")
        .append(";")
        .append(getReferencia1() != null ? changeNull(getReferencia1()) : "")
        .append(";")
        .append(getReferencia2() != null ? changeNull(getReferencia2()) : "")
        .append(";")
        .append(getReferencia3() != null ? changeNull(getReferencia3()) : "");
    return result.toString();
  }

  private static Object changeNull(Object property) {
    if (property != null) {
      if (String.valueOf(property).equalsIgnoreCase("null")) {
        return "";
      }
      return property;
    }
    return "";
  }
}
