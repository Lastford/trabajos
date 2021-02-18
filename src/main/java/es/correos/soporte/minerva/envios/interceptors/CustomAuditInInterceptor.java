package es.correos.soporte.minerva.envios.interceptors;

import es.correos.arch.boot.cxf.interceptor.WsCallAuditInfoHolder;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import javax.xml.soap.SOAPMessage;
import lombok.extern.log4j.Log4j2;
import org.apache.cxf.binding.soap.SoapFault;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.binding.soap.saaj.SAAJInInterceptor;
import org.apache.cxf.common.i18n.BundleUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.phase.PhaseInterceptor;

@Log4j2
public class CustomAuditInInterceptor extends AbstractSoapInterceptor {

  // copia de lo que hay en el parent para que se llame a este interceptor.
  class LoggingInFaultInterceptor extends AbstractSoapInterceptor {
    LoggingInFaultInterceptor() {
      super(Phase.RECEIVE);
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {
      // no se hace nada
    }

    @Override
    public void handleFault(SoapMessage message) throws Fault {
      CustomAuditInInterceptor.this.handleMessage(message);
    }
  }

  private static final ResourceBundle BUNDLE = BundleUtils.getBundle(SAAJInInterceptor.class);

  private List<PhaseInterceptor<? extends org.apache.cxf.message.Message>> extras =
      new ArrayList<>(1);

  public CustomAuditInInterceptor() {
    super(Phase.POST_PROTOCOL);
    extras.add(SAAJInInterceptor.INSTANCE);
  }

  @Override
  public Collection<PhaseInterceptor<? extends org.apache.cxf.message.Message>>
      getAdditionalInterceptors() {
    return extras;
  }

  private String getMessageXMLContent(SoapMessage message) {
    SOAPMessage msg = message.getContent(SOAPMessage.class);
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try {
      msg.writeTo(out);
      return new String(out.toByteArray());
    } catch (Exception e) {
      throw new SoapFault(
          new org.apache.cxf.common.i18n.Message("SOAPEXCEPTION", BUNDLE, e.getMessage(), e),
          e,
          message.getVersion().getSender());
    }
  }

  @Override
  public void handleMessage(SoapMessage message) throws Fault {
    if (WsCallAuditInfoHolder.getGuardarDatosPetitionRespuestaSOAP() != null
        && WsCallAuditInfoHolder.getGuardarDatosPetitionRespuestaSOAP()) {
      String responseXML = getMessageXMLContent(message);
      WsCallAuditInfoHolder.setCurrentResponseInfoXML(responseXML);
    }
  }
}
