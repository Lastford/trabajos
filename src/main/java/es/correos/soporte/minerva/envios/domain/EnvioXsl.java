package es.correos.soporte.minerva.envios.domain;

import java.util.Date;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "#{@environment.getProperty('mongo.collection')}")
public class EnvioXsl {

  @Id private String id;

  @Field("EVENTO_RESUMEN_ES")
  private String resumenUltimo;

  @Field("nomcodired")
  private String nomcodired;

  @Field("fechaultimoevento")
  private Date fechaUltimoEvento;

  private String idEnvioAsociado;

  @Field("tipoMensaje")
  private String tipoMensaje;

  @Field("codired")
  private String codired;

  @Field("codevento")
  private String codEvento;

  @Field("COD_EVENTO_UNICO")
  private String unicoEvento;

  @Field("ambito")
  private String ambito;

  @Field("cod_pregrabado")
  private String codPregrabado;

  @Field("codexpedicion")
  private String codExpedicion;

  @Default
  @Field("DETAILS")
  private DetalleXsl detalles = new DetalleXsl();

  @Default
  @Field("DOC_ENV")
  private DocXsl docEnv = new DocXsl();

  @Default
  @Field("PANEL")
  private PanelXsl panel = new PanelXsl();
}
