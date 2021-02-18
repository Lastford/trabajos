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
public class AdicionesDTO implements Serializable {

  private static final long serialVersionUID = 7803932579368132229L;

  public String cio;

  public String cp;
}
