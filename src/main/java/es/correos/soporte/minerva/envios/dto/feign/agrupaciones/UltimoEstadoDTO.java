package es.correos.soporte.minerva.envios.dto.feign.agrupaciones;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class UltimoEstadoDTO implements Serializable {
  /** */
  private static final long serialVersionUID = -2004440174929362136L;

  @JsonProperty("unique_code")
  private String codigoUnico;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonProperty("situation_code")
  private String codigoSituacion;

  @JsonProperty("situation_summary")
  private String resumenSituacion;

  @JsonProperty("operation_code")
  private String codigoOperacion;

  @JsonProperty("date")
  private String fecha;
}
