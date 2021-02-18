package es.correos.soporte.minerva.envios.dto.feign.productos;

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
public class ProductoListaDTO {
  @Valid
  @NotNull
  @JsonProperty("data")
  List<ProductoDTO> listData;
}
