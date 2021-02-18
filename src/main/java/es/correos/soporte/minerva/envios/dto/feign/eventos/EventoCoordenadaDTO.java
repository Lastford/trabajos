package es.correos.soporte.minerva.envios.dto.feign.eventos;

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
public class EventoCoordenadaDTO implements Serializable {

  private static final long serialVersionUID = 8066845376205483909L;

  @JsonProperty("x")
  private String x;

  @JsonProperty("y")
  private String y;
}
