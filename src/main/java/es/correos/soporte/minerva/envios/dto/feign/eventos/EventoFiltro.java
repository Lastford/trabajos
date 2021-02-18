package es.correos.soporte.minerva.envios.dto.feign.eventos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventoFiltro {
  @JsonIgnore private String codigoEnvio;
  @JsonIgnore private String codigoAgrupacion;

  @JsonProperty(value = "ids")
  private List<String> listaIdsEvento;

  @JsonProperty(value = "is_shipment")
  private Boolean isShipment;

  @JsonProperty(value = "all_data")
  private Boolean allData;
}
