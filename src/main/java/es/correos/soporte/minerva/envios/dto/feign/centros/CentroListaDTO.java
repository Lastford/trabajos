package es.correos.soporte.minerva.envios.dto.feign.centros;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CentroListaDTO {

  @NotNull
  @Valid
  @JsonProperty("data")
  private List<CentroDTO> listData;
}
