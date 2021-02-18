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
public class EnvioDatoDTO implements Serializable {

  private static final long serialVersionUID = 5259733660305510471L;

  @JsonProperty("country_code")
  private String codPais;

  @JsonProperty("province_code")
  private String codProvincia;

  @JsonProperty("locality")
  private String localidad;

  @JsonProperty("postal_code")
  private String codPostal;

  @JsonProperty("address")
  private String direccion;

  @JsonProperty("business_name")
  private String razonSocial;

  @JsonProperty("nif_cif")
  private String nifcif;

  @JsonProperty("last_name")
  private String apellidos;

  @JsonProperty("telephone_number")
  private String telefono;

  @JsonProperty("email")
  private String email;

  @JsonProperty("first_name")
  private String nombre;

  @JsonProperty("customer_id")
  private String idCliente;
}
