package es.correos.soporte.minerva.envios.config;

import es.correos.soporte.minerva.envios.interceptors.CustomAuditInInterceptor;
import es.correos.soporte.minerva.envios.interceptors.CustomAuditMessageInterceptor;
import es.correos.soporte.minerva.envios.interceptors.CustomAuditOutInterceptor;
import es.correos.soporte.minerva.envios.properties.WsClientProperties;
import es.correos.soporte.minerva.envios.service.ImagenEdocService;
import es.correos.soporte.minerva.envios.service.client.soap.EdocumentImagenSoapClient;
import es.correos.soporte.minerva.envios.service.impl.ImagenEdocServiceImpl;
import es.correos.soporte.minerva.envios.util.CxfConfigUtil;
import es.correos.soporte.minerva.recursos.edocumento.wsdl.ConsultaWS;
import org.apache.cxf.clustering.FailoverFeature;
import org.apache.cxf.clustering.RetryStrategy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableConfigurationProperties(value = {WsClientProperties.class})
public class ImagenEnvioEdocConfig implements WebMvcConfigurer {

  private WsClientProperties wsClientProperties;

  public ImagenEnvioEdocConfig(WsClientProperties wsClientProperties) {
    this.wsClientProperties = wsClientProperties;
  }

  @Bean(name = "llamadaEdocProxy")
  public ConsultaWS consultarDocumentosProxy() {
    JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
    jaxWsProxyFactoryBean.setServiceClass(ConsultaWS.class);
    jaxWsProxyFactoryBean.setAddress(wsClientProperties.getEdocUrl());
    jaxWsProxyFactoryBean.getInInterceptors().add(new CustomAuditInInterceptor());
    jaxWsProxyFactoryBean.getOutInterceptors().add(new CustomAuditMessageInterceptor());
    jaxWsProxyFactoryBean.getOutInterceptors().add(new CustomAuditOutInterceptor());

    FailoverFeature failoverFeature = new FailoverFeature();
    RetryStrategy sameEndpointRetryStrategy = new RetryStrategy();

    sameEndpointRetryStrategy.setMaxNumberOfRetries(wsClientProperties.getMaxAttempts() - 1);
    sameEndpointRetryStrategy.setDelayBetweenRetries(wsClientProperties.getDelay());
    failoverFeature.setStrategy(sameEndpointRetryStrategy);
    jaxWsProxyFactoryBean.getFeatures().add(failoverFeature);
    ConsultaWS consultaWS = (ConsultaWS) jaxWsProxyFactoryBean.create();
    CxfConfigUtil.configureProxyAndSSL(consultaWS, wsClientProperties);
    return consultaWS;
  }

  public EdocumentImagenSoapClient edocumentoCertClient(ConsultaWS llamadaEdocProxy) {
    return new EdocumentImagenSoapClient(llamadaEdocProxy);
  }

  public ImagenEdocService edocService(EdocumentImagenSoapClient edocumentoCertClient) {
    return new ImagenEdocServiceImpl(edocumentoCertClient);
  }
}
