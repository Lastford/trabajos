package es.correos.soporte.minerva.envios.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnvioListaCustomClientCodEtiqFilterDTO {

  @JsonProperty("data")
  private List<EnvioCustomClientCodEtiqFilterDTO> registros;

  @JsonProperty("totalrecords")
  private long totalRegistros;
}
