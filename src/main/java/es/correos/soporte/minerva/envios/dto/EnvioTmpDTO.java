package es.correos.soporte.minerva.envios.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import es.correos.soporte.minerva.envios.config.enums.FilterOrderEnum;
import javax.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnvioTmpDTO {

  private String id;
  private String date_in;
  private String date_fin;
  private String codired;
  private String unique_code;
  private String status_code;
  private String ids;
  private String reported_node;
  private String ambito;
  private String idDespachoPostal;
  private String remesa;
  private String id_group;
  private String message_type;
  private String product_ids;

  @PositiveOrZero private Integer offset;

  private Integer size;
  private FilterOrderEnum order;
  private String sort;
  private String reference;
  private String prerecorded_code;
  private String expedition_code;
  private String unique_event_code;

  @JsonProperty("family")
  private String familia;

  @JsonProperty("subfamily")
  private String subFamilia;

  @JsonProperty("union_client")
  private String clienteUnion;
}
