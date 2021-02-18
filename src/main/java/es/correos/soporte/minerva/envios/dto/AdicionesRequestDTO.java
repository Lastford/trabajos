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
public class AdicionesRequestDTO implements Serializable {

  private static final long serialVersionUID = -2602087094739287971L;

  public String aditionsFile;

  public String fileType;

  public String deliveryMode;
}
