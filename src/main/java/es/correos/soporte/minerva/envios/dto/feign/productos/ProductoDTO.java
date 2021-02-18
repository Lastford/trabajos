package es.correos.soporte.minerva.envios.dto.feign.productos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {

  @JsonProperty("id")
  private String id;

  @JsonProperty("d")
  private String d;

  @JsonProperty("da")
  private String da;

  @JsonProperty("family")
  private String familia;

  @JsonProperty("sub_family")
  private String subFamilia;

  @JsonProperty("ds")
  private String ds;

  @JsonProperty("product_order")
  private int ordenProducto;

  @JsonProperty("status")
  private String estado;

  @JsonProperty("agrupation_name")
  private String nomAgrupacion;

  @JsonProperty("code_name")
  private String codNom;
}
