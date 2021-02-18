package es.correos.soporte.minerva.envios.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
public class Envio {

  @Id private String id;

  @Field("codupu")
  private String codUpu;

  @Field("flag_int")
  private String flagInt;

  @Field("codired")
  private String codired;

  @Field("nomcodired")
  private String nomcodired;

  @Field("cpdestino")
  private String cpDestino; // * cp

  @Field("fechaultimoevento")
  private Date fechaUltimoEvento;

  @Field("codevento")
  private String codEvento;

  @Field("codrazon")
  private String codRazon;

  @Field("codpaisdestino")
  private String codPaisDestino; // * codPaisDestino

  @Field("codpaisorigen")
  private String codPaisOrigen; // * codPaisOrigen

  @Field("cod_pregrabado")
  private String codPregrabado;

  @Field("codexpedicion")
  private String codExpedicion;

  @Field("timestamp")
  private Date timestamp;
  // private String timestamp;

  @Field("tipoMensaje")
  private String tipoMensaje; // * tipo

  @Field("unidadadmision")
  private String unidadAdmision;

  @Field("estado")
  private String estado;

  @Field("ambito")
  private String ambito;

  @Field("schema_version")
  private String schemaVersion;

  @Default
  @Field("DETAILS")
  private Detalle detalles = new Detalle();

  @Default
  @Field("DOC_ENV")
  private Doc docEnv = new Doc();

  @Default
  @Field("PANEL")
  private Panel panel = new Panel();

  @Field("COD_EVENTO_UNICO")
  private String unicoEvento;

  @Field("EVENTO_RESUMEN_ES")
  private String resumenUltimo;

  @Field("entregaexclusiva")
  private String entregaExclusiva;

  @Field("codagrupacion")
  private String codAgrupacion;

  @Field("fecharegistro")
  private String fechaRegistro;

  @Field("categorizacion")
  private String categorizacion;

  @Default
  @Field("reetiquetado_padre")
  private List<Envio> reetiquetadoPadre = new ArrayList<>();

  @Default
  @Field("reetiquetado_hijo")
  private List<Envio> reetiquetadoHijo = new ArrayList<>();

  @Field("depth")
  private Long nivelProfundidad;

  private String idEnvioAsociado;
}
