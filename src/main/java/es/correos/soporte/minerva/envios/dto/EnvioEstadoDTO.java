package es.correos.soporte.minerva.envios.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnvioEstadoDTO implements Serializable {

  private static final long serialVersionUID = 8596319171369089858L;

  @JsonProperty("date")
  private String fecha;

  @JsonProperty("code")
  private String codigo;

  @JsonProperty("reason_code")
  private String codRazon;

  @JsonProperty("origin_postal_office")
  private String idDespachoPostal;

  @JsonProperty("destination_postal_office")
  private String ofiPostalDestino;

  @JsonProperty("expiration_date")
  private String fechaCaducidadEstacionado;

  @JsonProperty("reporter_node")
  private String nodoReportador;

  @JsonProperty("situation_summary")
  private String resumenSituacion;
}
