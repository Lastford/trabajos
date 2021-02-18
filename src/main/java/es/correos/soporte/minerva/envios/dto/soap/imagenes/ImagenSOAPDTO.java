package es.correos.soporte.minerva.envios.dto.soap.imagenes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImagenSOAPDTO {
  //	private static final long serialVersionUID = 7316778853264850345L;
  //  @JsonProperty("imageCode")
  public String codigo;
  //  @JsonProperty("verified")
  public String verificada;
  //  @JsonProperty("imageType")
  public String tipoImagen;
  //  @JsonProperty("digitalizationDate")
  public String fechaDigitacion;
  //  @JsonProperty("creationDate")
  public String fechaAlta;
  //  @JsonProperty("content")
  public String imagenBase64;
}
