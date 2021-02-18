package es.correos.soporte.minerva.envios.domain;

import java.util.Date;
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
public class Doc {

  @Field("idcliente")
  private String idCliente; // * cliente

  @Field("CTA_orig")
  private String ctaOrig;

  @Field("CTA_orig_CODIRED")
  private String ctaOrigCodired;

  @Field("CTA_dest")
  private String ctaDest;

  @Field("CTA_dest_CODIRED")
  private String ctaDestCodired;

  @Field("UD_Specific")
  private String udSpecific;

  @Field("nodoreportador")
  private String nodoReportador;

  @Field("nodoreportador_nombre")
  private String nodoReportadorNombre;

  @Field("cporigen")
  private String cpOrigen;

  @Field("fechacomprometidaentregacalculada")
  private Date fechaComprometidaEntregaCalculada;

  @Field("fecha_comprometidaentregasuministrada")
  private Date fechaComprometidaEntregaSuministrada;

  @Field("fechahora")
  private Date fechahora;
  // private String fechahora;

  @Field("timestamp")
  private Date timestamp;
  // private String timestamp;

  @Field("COD_EVENTO_UNICO")
  private String unicoEvento;
}
