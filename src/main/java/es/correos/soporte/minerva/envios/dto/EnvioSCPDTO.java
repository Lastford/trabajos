package es.correos.soporte.minerva.envios.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnvioSCPDTO implements Serializable {

  private static final long serialVersionUID = 2164350317830020889L;

  @JsonProperty("id")
  private String id;

  @JsonProperty("registration_date")
  private String fechaReg;

  @JsonProperty("validity_date")
  private String fechaValidez;

  @JsonProperty("message_type_code")
  private String codTipoMensaje;

  @JsonProperty("origin_country_code")
  private String codPaisOrigen;

  @JsonProperty("postal_code")
  private String cp;

  @JsonProperty("destination_country_code")
  private String codPaisDestino;

  @JsonProperty("mode")
  private String modo;

  @JsonProperty("preregistration_error")
  private String errorPrerregistro;

  @JsonProperty("product")
  private String producto;

  @JsonProperty("client")
  private String cliente;

  @JsonProperty("delivery_mode")
  private String modalidadEntrega;

  @JsonProperty("customs_hold")
  private String retenidoAduanas;

  @JsonProperty("declared_value")
  private String valorDeclarado;

  @JsonProperty("customs_circuit")
  private String circuitoAduana;

  @JsonProperty("currency_code")
  private String codMoneda;

  @JsonProperty("postal_office")
  private String despacho;

  @JsonProperty("recipient")
  private String recipiente;
}
