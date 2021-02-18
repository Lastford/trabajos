package es.correos.soporte.minerva.envios.dto.feign.agrupaciones;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContenidoEnviosDTO implements Serializable {

  private static final long serialVersionUID = 1406836359581941285L;

  @JsonProperty("id")
  private String id;

  @JsonProperty("start_date")
  private String fechaIni;

  @JsonProperty("type")
  private String tipo;

  @JsonProperty("unity_center_code")
  private String codigoCentroUnidad;

  @JsonProperty("unity_center_name")
  private String nombreCentroUnidad;

  @Default
  @JsonProperty("status")
  private UltimoEstadoDTO ultimoEstado = new UltimoEstadoDTO();
}
