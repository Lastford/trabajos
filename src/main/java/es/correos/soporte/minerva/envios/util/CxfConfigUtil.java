package es.correos.soporte.minerva.envios.util;

import es.correos.soporte.minerva.envios.properties.WsClientProperties;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;

/**
 * @author everis
 *     <p>Clase de utilidades para configurar temas comunes a los clientes de CXF. Se va a
 *     configurar el proxy y la comunicación segura SSL para el objeto de entrada
 */
public class CxfConfigUtil {

  /** constructor privado por ser una clase de utilidad */
  private CxfConfigUtil() {}

  public static void configureProxyAndSSL(Object cxfClient, WsClientProperties wsClientProperties) {
    Client client = ClientProxy.getClient(cxfClient);
    HTTPConduit http = (HTTPConduit) client.getConduit();
    http.getClient().setReceiveTimeout(120000);
    if (wsClientProperties.getProxy() != null && wsClientProperties.getProxy().getEnabled()) {
      // se configura el proxy
      http.getClient().setProxyServer(wsClientProperties.getProxy().getHost());
      http.getClient().setProxyServerPort(wsClientProperties.getProxy().getPort()); // your
      // proxy-port
      if (!StringUtils.isEmpty(wsClientProperties.getProxy().getNonProxyHost())) {
        http.getClient().setNonProxyHosts(wsClientProperties.getProxy().getNonProxyHost());
      }
      if (!StringUtils.isEmpty(wsClientProperties.getProxy().getUsername())) {
        http.getProxyAuthorization().setUserName(wsClientProperties.getProxy().getUsername());
        http.getProxyAuthorization().setPassword(wsClientProperties.getProxy().getPassword());
      }
    }
    if (wsClientProperties.getSsl() != null && wsClientProperties.getSsl().getDisableCNCheck()) {
      TLSClientParameters tlsCP = new TLSClientParameters();
      tlsCP.setDisableCNCheck(true);
      tlsCP.setHostnameVerifier(
          new HostnameVerifier() {
            @Override
            public boolean verify(String arg0, SSLSession arg1) {
              return true;
            }
          });
      http.setTlsClientParameters(tlsCP);
    }
  }
}
