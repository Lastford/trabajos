package es.correos.soporte.minerva.envios.dto.feign.agrupaciones;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ContenidoAgrupacionesConAvisoDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  @NotNull
  @NotEmpty
  @JsonProperty("id")
  private String id;

  @NotNull
  @NotEmpty
  @JsonProperty("messages")
  private String mensajes;

  @NotNull
  @NotEmpty
  @JsonProperty("date")
  private String fecha;

  @NotNull
  @NotEmpty
  @JsonProperty("shipment_id")
  private String idenvio;

  @JsonProperty("operation")
  private String operacion;
}
