package es.correos.soporte.minerva.envios.dto.feign.eventos;

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
public class EventoListaDTO implements Serializable {

  private static final long serialVersionUID = 4599989757665513810L;

  @NotNull
  @Valid
  @JsonProperty("data")
  private List<EventoDTO> data;
}
