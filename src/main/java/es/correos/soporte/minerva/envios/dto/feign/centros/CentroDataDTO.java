package es.correos.soporte.minerva.envios.dto.feign.centros;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CentroDataDTO {

  @NotEmpty
  @NotNull
  @Valid
  @JsonProperty("data")
  private CentroDTO data;
}
