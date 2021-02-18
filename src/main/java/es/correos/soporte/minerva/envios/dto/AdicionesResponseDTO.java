package es.correos.soporte.minerva.envios.dto;

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
public class AdicionesResponseDTO implements Serializable {

  private static final long serialVersionUID = -3480423201341459862L;

  private List<AdicionesDTO> aditions;
}
