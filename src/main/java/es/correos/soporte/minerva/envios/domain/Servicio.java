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
public class Servicio {

  @Field("bolGenerarPEElectronica")
  private String bolGenerarPEElectronica;

  @Field("importegirado")
  private String importeGirado;

  @Field("reembolso")
  private String reembolso;

  @Field("asegurado")
  private String asegurado;

  @Field("indemnizacion")
  private String indemnizacion;

  @Field("bolGenerarPEFisica")
  private String bolGenerarPEFisica;

  @Field("custodiaPEE")
  private String custodiaPEE;

  @Field("custodiaPEF")
  private String custodiaPEF;

  @Field("bolGenerarPEMixta")
  private String bolGenerarPEMixta;

  @Field("custodiaPEM")
  private String custodiaPEM;

  @Field("numintentos")
  private String numIntentos;

  @Field("numdiaslista")
  private String numDiasLista;

  @Field("indavisorecibido")
  private String indAvisoRecibido;

  @Field("mesesCustodia")
  private String mesesCustodia;

  @Field("importetotal")
  private String importeTotal;

  @Field("importepagado")
  private String importePagado;

  @Field("indentregarecogida")
  private String indEntregaRecogida;
}
