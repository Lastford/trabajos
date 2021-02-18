package es.correos.soporte.minerva.envios.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnvioListaDTO {

  @Valid
  @NotNull
  @JsonProperty("data")
  private List<EnvioDTO> data;

  @JsonProperty("totalrecords")
  private Long totalrecords;
}
