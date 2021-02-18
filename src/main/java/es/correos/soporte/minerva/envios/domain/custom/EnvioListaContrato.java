package es.correos.soporte.minerva.envios.domain.custom;

import java.util.List;
import javax.persistence.Id;
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
public class EnvioListaContrato {

  @Id private String id;

  List<EnvioContrato> contratos;

  private long totalRegistros;
}
