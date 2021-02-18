package es.correos.soporte.minerva.envios.dto.feign.agrupaciones;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgrupacionEventoDTO implements Serializable {

  private static final long serialVersionUID = 1119305624285002249L;

  @JsonProperty("resume_description")
  private String resumen;

  @JsonProperty("extended_description")
  private String resumenExtendido;

  @JsonProperty("date")
  private String fechaHora;

  @JsonProperty("location")
  private String ubicacion;

  @JsonProperty("codired")
  private String codired;

  @JsonProperty("sender_postal_code")
  private String codPostalOrigen;

  @JsonProperty("destination_postal_code")
  private String codPostalDestino;

  @JsonProperty("cod_evento_unico")
  private String codEventoUnico;
}
