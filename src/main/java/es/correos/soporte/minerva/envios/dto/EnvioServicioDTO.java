package es.correos.soporte.minerva.envios.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnvioServicioDTO implements Serializable {

  private static final long serialVersionUID = 1264951730757973706L;

  @JsonProperty("has_PEE")
  private String bolGenerarPEElectronica;

  @JsonProperty("amount_bank_giro")
  private String importeGirado;

  @JsonProperty("refund")
  private String reembolso;

  @JsonProperty("is_insured")
  private String asegurado;

  @JsonProperty("has_compensation")
  private String indemnizacion;

  @JsonProperty("has_PEF")
  private String bolGenerarPEFisica;

  @JsonProperty("safekeeping_PEE")
  private String custodiaPEE;

  @JsonProperty("safekeeping_PEF")
  private String custodiaPEF;

  @JsonProperty("has_PEM")
  private String bolGenerarPEMixta;

  @JsonProperty("safekeeping_PEM")
  private String custodiaPEM;

  @JsonProperty("delivery_attempts")
  private String numIntentos;

  @JsonProperty("list_days")
  private String numDiasLista;

  @JsonProperty("has_recived_alerts")
  private String indAvisoRecibido;

  @JsonProperty("safekeeping_months")
  private String mesesCustodia;

  @JsonProperty("total")
  private String importeTotal;

  @JsonProperty("amount_paid")
  private String importePagado;

  @JsonProperty("delivery_collect")
  private String indEntregaRecogida;
}
