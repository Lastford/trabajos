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
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnvioEventoDTO implements Serializable {

  private static final long serialVersionUID = 7490592060685978275L;

  @JsonProperty("group_code")
  private String idGrupo;

  @JsonProperty("resume_description")
  private String descripcionCorta;

  @JsonProperty("extended_description")
  private String descripcionLarga;

  @JsonProperty("date")
  private String fecha;

  @JsonProperty("location")
  private String localidad;

  @JsonProperty("codired")
  private String codired;

  @JsonProperty("sender_postal_code")
  private String codPostalOrigen;

  @JsonProperty("destination_postal_code")
  private String codPostalDestino;

  @JsonProperty("agrupation")
  private String agrupacion;

  @JsonProperty("unity")
  private String unidad;

  @JsonProperty("coordenate_x")
  private String coordenadaX;

  @JsonProperty("coordenate_y")
  private String coordenadaY;

  @JsonProperty("phase")
  private String fase;

  @JsonProperty("color")
  private String color;

  @JsonProperty("id")
  private String idEnvio;

  @JsonProperty("cod_evento_unico")
  private String codEventoUnico;
}
