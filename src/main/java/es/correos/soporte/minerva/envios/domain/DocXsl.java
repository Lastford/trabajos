package es.correos.soporte.minerva.envios.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocXsl {

  /* @Field("fechahora")
  private Date fechahora;
  */
  @Field("CTA_dest_CODIRED")
  private String ctaDestCodired;
}
