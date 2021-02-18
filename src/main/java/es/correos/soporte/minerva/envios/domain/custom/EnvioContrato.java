package es.correos.soporte.minerva.envios.domain.custom;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnvioContrato {

  private String id;

  private String descripcion;

  private List<String> clientesUnion;
}
