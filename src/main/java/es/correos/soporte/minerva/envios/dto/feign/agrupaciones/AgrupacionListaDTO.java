package es.correos.soporte.minerva.envios.dto.feign.agrupaciones;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgrupacionListaDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  @JsonProperty("data")
  private List<AgrupacionDTO> listData;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonProperty("totalrecords")
  private Long totalregistros;
}
