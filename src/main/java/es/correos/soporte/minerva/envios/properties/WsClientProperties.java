package es.correos.soporte.minerva.envios.properties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@NoArgsConstructor
@ConfigurationProperties(prefix = "ws.client")
public class WsClientProperties {

  private String edocUrl;
  private Proxy proxy;
  private Ssl ssl;
  private Integer delay = 5000;
  private Integer maxAttempts = 1;
  private Integer auditLength;

  @NoArgsConstructor
  @Getter
  @Setter
  public static class Proxy {

    private Boolean enabled = Boolean.FALSE;
    private String host;
    private int port;
    private String nonProxyHost;
    private String username;
    private String password;
  }

  @NoArgsConstructor
  @Getter
  @Setter
  public static class Ssl {

    private Boolean disableCNCheck = Boolean.TRUE;
  }
}
