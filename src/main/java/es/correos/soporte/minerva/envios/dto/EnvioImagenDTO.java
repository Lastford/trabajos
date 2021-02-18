package es.correos.soporte.minerva.envios.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnvioImagenDTO {
  @NotNull
  @Valid
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonProperty("imageCode")
  public String codigo;

  @NotNull
  @Valid
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonProperty("verified")
  public String verificada;

  @NotNull
  @Valid
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonProperty("imageType")
  public String tipoImagen;

  @NotNull
  @Valid
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonProperty("digitalizationDate")
  public String fechaDigitacion;

  @NotNull
  @Valid
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonProperty("creationDate")
  public String fechaAlta;

  @NotNull
  @Valid
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonProperty("content")
  public String imagenBase64;
}
