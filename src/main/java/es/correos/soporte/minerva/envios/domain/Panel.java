package es.correos.soporte.minerva.envios.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Panel {

  @Field("P")
  private String p; // * PRODUCTO

  @Field("NOM_PRODUCTO_DES")
  private String nombreProducto;

  @Field("FAMILIA")
  private String familia;

  @Field("SUBFAMILIA")
  private String subFamilia;
}
