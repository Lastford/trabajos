package es.correos.soporte.minerva.envios.dto;

import javax.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnvioSCPTmpDTO {

  private String id;
  private String ids;
  private String registration_date;
  private String validity_date;
  private String message_type_code;
  private String origin_country_code;
  private String postal_code;
  private String destination_country_code;
  private String mode;
  private String preregistration_error;
  private String product;
  private String client;
  private String delivery_mode;
  private String customs_hold;
  private String declared_value;
  private String customs_circuit;
  private String currency_code;
  private String postal_office;
  private String recipient;

  private String date_in;
  private String date_fin;

  @PositiveOrZero private Integer offset;
  private Integer size;
  // private FilterOrderEnum order;
  // private String sort;

}
