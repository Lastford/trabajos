package es.correos.soporte.minerva.envios.domain;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "")
public class FileEnvio {

  @Id
  @Field("id")
  @NotNull
  private String id;

  @Field("file")
  private String file;

  @Field("formatovisual")
  private String formatovisual;
}
