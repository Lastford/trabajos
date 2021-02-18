package es.correos.soporte.minerva.envios.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnvioFileDTO implements Serializable {

  /** */
  private static final long serialVersionUID = 1L;

  protected String file;

  protected String formatovisual;
}
