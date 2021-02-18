package es.correos.soporte.minerva.envios.dto.feign.centros;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CentroDTO {

  @JsonProperty("id")
  private String id;

  @JsonProperty("fecha")
  private String fecha;

  @NotNull
  @NotEmpty
  @JsonProperty("codi_red_u")
  private String codiRedU;

  @NotNull
  @NotEmpty
  @JsonProperty("unity_name")
  private String unityName;

  @NotNull
  @NotEmpty
  @JsonProperty("des_tipo_unidad")
  private String unityType;

  @JsonProperty("longitude")
  private Float longitude;

  @JsonProperty("latitude")
  private Float latitude;

  @NotNull
  @NotEmpty
  @JsonProperty("code_locality")
  private String codeLocality;

  @JsonProperty("postal_code")
  private String postalCode;
}
