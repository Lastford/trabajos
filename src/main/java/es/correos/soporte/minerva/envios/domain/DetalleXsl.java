package es.correos.soporte.minerva.envios.domain;

import com.querydsl.core.annotations.QueryEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
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
@QueryEntity
public class DetalleXsl {

  @Field("referencia1")
  private String referencia1;

  @Field("referencia2")
  private String referencia2;

  @Field("referencia3")
  private String referencia3;

  @Field("iddespachopostal")
  private String idDespachoPostal; // * despacho

  @Field("remesa")
  private String remesa;

  @Field("codupu")
  private String codUpu;

  @Default
  @Field("estado")
  private EstadoXsl estado = new EstadoXsl();

  @Field("etiquetadohijo")
  private String etiquetadoHijo;
}
