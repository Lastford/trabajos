package es.correos.soporte.minerva.envios.dto.feign.eventos;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventoDTO implements Serializable {

  private static final long serialVersionUID = 6833875404876318089L;

  @JsonProperty("date")
  private String fechaHora;

  @JsonProperty("code")
  private String codEvento;

  @JsonProperty("reason")
  private String codRazon;

  @JsonProperty("consignee_name")
  private String nomDestinatario;

  @JsonProperty("province_code")
  private String codProvincia;

  @JsonProperty("transport_type")
  private String codTipoTransporte;

  @JsonProperty("agent")
  private String idagente;

  @JsonProperty("agent_type")
  private String tipoAgente;

  @JsonProperty("application")
  private String aplicacion;

  @JsonProperty("shift")
  private String turno;

  @JsonProperty("section")
  private String seccion;

  @JsonProperty("section_type")
  private String tipoSeccion;

  @JsonProperty("delivery_relation")
  private String codRelacionReparto;

  @JsonProperty("delivery_comments")
  private String observacionesReparto;

  @JsonProperty("comments")
  private String observaciones;

  @Default
  @JsonProperty("coordinate")
  private EventoCoordenadaDTO coordenada = new EventoCoordenadaDTO();

  @JsonProperty("group_id")
  private String idAgrupacion;

  @JsonProperty("des_event")
  private String desEvento;

  @JsonProperty("location")
  private String ubicacion;

  @JsonProperty("codired")
  private String codired;

  @JsonProperty("sender_postal_code")
  private String codPostalOrigen;

  @JsonProperty("destination_postal_code")
  private String codPostalDestino;

  @JsonProperty("phase")
  private String fase;

  @JsonProperty("color")
  private String color;

  @JsonProperty("des_summary_text")
  private String resumen;

  @JsonProperty("des_extended_text")
  private String resumenExtendido;

  @JsonProperty("unity")
  private String unidad;

  @JsonProperty("id")
  private String id;

  @JsonProperty("cod_evento_unico")
  private String codEventoUnico;
}
