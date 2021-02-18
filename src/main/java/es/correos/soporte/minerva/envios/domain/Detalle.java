package es.correos.soporte.minerva.envios.domain;

import com.querydsl.core.annotations.QueryEntity;
import java.util.Date;
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
public class Detalle {

  @Field("coddivisa")
  private String codDivisa; // * codMoneda

  @Field("codrecipiente")
  private String codRecipiente; // * recipiente

  @Field("ofipostaldestino")
  private String ofiPostalDestino;

  @Field("idenviodevolucion")
  private String idEnvioDevolucion;

  @Field("remesa")
  private String remesa;

  @Field("codupu")
  private String codUpu;

  @Field("fechaadmision")
  private Date fechaAdmision;

  @Field("referencia1")
  private String referencia1;

  @Field("referencia2")
  private String referencia2;

  @Field("referencia3")
  private String referencia3;

  @Field("indAcuseRecibo")
  private String indAcuseRecibo;

  @Field("fechacaducidadestacionado")
  private Date fechaCaducidadEstacionado;
  // private String fechaCaducidadEstacionado;

  @Field("numentregas")
  private String numEntregas;

  @Field("peso")
  private Integer peso;

  @Field("longitud")
  private String longitud;

  @Field("altura")
  private String altura;

  @Field("anchura")
  private String anchura;

  @Field("observaciones")
  private String observaciones;

  @Field("iddespachopostal")
  private String idDespachoPostal; // * despacho

  @Field("envioasociado")
  private String envioAsociado;

  @Field("tipoenvionacido")
  private String tipoEnvioNacido;

  @Default
  @Field("destinatario")
  private Datos destinatario = new Datos();

  @Default
  @Field("remitente")
  private Datos remitente = new Datos();

  @Default
  @Field("servicios")
  private Servicio servicios = new Servicio();

  @Default
  @Field("estado")
  private Estado estado = new Estado();

  @Field("etiquetadohijo")
  private String etiquetadoHijo;
}
