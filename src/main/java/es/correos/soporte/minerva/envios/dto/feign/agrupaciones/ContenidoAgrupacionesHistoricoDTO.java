package es.correos.soporte.minerva.envios.dto.feign.agrupaciones;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class ContenidoAgrupacionesHistoricoDTO implements Serializable {
  /** */
  private static final long serialVersionUID = -2004440174929362136L;

  @JsonProperty("id")
  private String id;

  @JsonProperty("start_date")
  private String fechaIni;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonProperty("end_date")
  private String fechaFin;
}
