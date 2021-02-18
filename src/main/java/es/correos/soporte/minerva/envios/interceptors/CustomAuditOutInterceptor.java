package es.correos.soporte.minerva.envios.interceptors;

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
import org.apache.cxf.binding.soap.saaj.SAAJOutInterceptor;
import org.apache.cxf.common.i18n.BundleUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.phase.PhaseInterceptor;

@Log4j2
public class CustomAuditOutInterceptor extends AbstractSoapInterceptor {

  private static final ResourceBundle BUNDLE = BundleUtils.getBundle(SAAJOutInterceptor.class);

  private List<PhaseInterceptor<? extends org.apache.cxf.message.Message>> extras =
      new ArrayList<>(1);

  public CustomAuditOutInterceptor() {
    super(Phase.SEND);
    extras.add(new SAAJOutInterceptor());
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
      String requestXML = getMessageXMLContent(message);
      WsCallAuditInfoHolder.setCurrentRequestInfoXML(requestXML);
    }
  }
}
