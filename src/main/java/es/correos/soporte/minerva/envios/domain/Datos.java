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
public class Datos {

  @Field("codpais")
  private String codPais;

  @Field("codprovincia")
  private String codProvincia;

  @Field("localidad")
  private String localidad;

  @Field("codpostal")
  private String codPostal;

  @Field("direccion")
  private String direccion;

  @Field("razonsocial")
  private String razonSocial;

  @Field("numcontrato")
  private String numContrato;

  @Field("nifcif")
  private String nifcif;

  @Field("apellidos")
  private String apellidos;

  @Field("telefono")
  private String telefono;

  @Field("email")
  private String email;

  @Field("nombre")
  private String nombre;
}
