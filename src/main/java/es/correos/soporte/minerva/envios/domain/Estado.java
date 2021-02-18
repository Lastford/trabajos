package es.correos.soporte.minerva.envios.domain;

import java.util.Date;
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
public class Estado {

  @Field("ofipostalorigen")
  private String ofiPostalOrigen;

  @Field("fechaentregaconcertada")
  private Date fechaEntregaConcertada;
  // private String fechaEntregaConcertada;

  @Field("descnodoreportador")
  private String descNodoReportador;
}
