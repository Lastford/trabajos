package es.correos.soporte.minerva.envios.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
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
public class EnvioListaImagenDTO implements Serializable {

  private static final long serialVersionUID = -8100054305819939253L;

  @NotNull
  @Valid
  @JsonProperty("data")
  private List<EnvioImagenDTO> data;
}
