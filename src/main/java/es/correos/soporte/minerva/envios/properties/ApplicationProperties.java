package es.correos.soporte.minerva.envios.properties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/** The Class ApplicationProperties. */
@ConfigurationProperties(prefix = "application")
@Component
@NoArgsConstructor
@Getter
@Setter
public class ApplicationProperties {

  /** propiedad ejemplo */
  public String propiedad;
}
