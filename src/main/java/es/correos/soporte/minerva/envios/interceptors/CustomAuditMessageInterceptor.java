package es.correos.soporte.minerva.envios.interceptors;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.logging.Logger;
import javax.xml.soap.SOAPMessage;
import javax.xml.stream.XMLStreamWriter;
import lombok.extern.log4j.Log4j2;
import org.apache.cxf.binding.soap.SoapFault;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.interceptor.AttachmentOutInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.staxutils.StaxUtils;

@Log4j2
public class CustomAuditMessageInterceptor extends AbstractSoapInterceptor {
  public CustomAuditMessageInterceptor() {
    super(Phase.PRE_STREAM);
    getAfter().add(AttachmentOutInterceptor.class.getName());
  }

  private String getMessageXMLContent(SoapMessage message) {
    SOAPMessage msg = message.getContent(SOAPMessage.class);
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try {
      msg.writeTo(out);
      return new String(out.toByteArray());
    } catch (Exception e) {
      Logger BUNDLE = null;
      throw new SoapFault(
          new org.apache.cxf.common.i18n.Message("SOAPEXCEPTION", BUNDLE, e.getMessage(), e),
          e,
          message.getVersion().getSender());
    }
  }

  @Override
  public void handleMessage(SoapMessage message) {
    try {
      message.put("disable.outputstream.optimization", Boolean.TRUE);
      XMLStreamWriter writer =
          StaxUtils.createXMLStreamWriter(message.getContent(OutputStream.class));

      CDataXMLStreamWriter cDataXMLStreamWriter = new CDataXMLStreamWriter(writer);
      message.setContent(XMLStreamWriter.class, cDataXMLStreamWriter);
    } catch (Exception exception) {
      log.error(exception);
    }
  }
}
