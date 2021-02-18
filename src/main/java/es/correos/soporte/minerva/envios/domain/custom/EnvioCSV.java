package es.correos.soporte.minerva.envios.domain.custom;

import java.util.Date;
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
public class EnvioCSV {

  @Id private String id;

  private String codProducto;

  private String nomProducto;

  private String resumenSituacion;

  private Date fechaUltimoEvento;

  private String codiRed;

  private String nomCodiRed;

  private String etiquetadoHijo;

  private String referencia1;

  private String referencia2;

  private String referencia3;
}
