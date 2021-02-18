package es.correos.soporte.minerva.envios.service.client.soap;

import es.correos.arq.ex.CorreosBusinessException;
import es.correos.soporte.minerva.envios.dto.soap.imagenes.ImagenSOAPDTO;
import es.correos.soporte.minerva.envios.interceptors.WsCallAuditInfoHolder;
import es.correos.soporte.minerva.envios.util.LogHelper;
import es.correos.soporte.minerva.recursos.edocumento.wsdl.ConsultaWS;
import java.io.IOException;
import java.io.StringReader;
import java.util.Base64;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.ws.WebServiceException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

@Log4j2
@RequiredArgsConstructor
@Service
public class EdocumentImagenSoapClient {

  private static final String MSG_ENTRADA = "entrando en el metodo";
  private static final String CONTENT_TYPE_TAG = "xml";
  private static final String TIPO_DOCUMENTO_PEE = "correos_pentrega_electronica";
  private static final String TIPO_DOCUMENTO_CE = "correos_certificacion_electronic";
  private static final String TAG_NAME_CONTENT = "content";
  private static final String TAG_NAME_COD_ERROR = "cod_error";
  private static final String TAG_NAME_DES_ERROR = "des_error";
  private static final String XPATH_CONTENT_TYPE =
      "/documentReturn/correcto/document/attribute[name='content_type']/value/text()";
  private static final String XPATH_COD_ENVIO =
      "/documentReturn/correcto/document/attribute[name='r_object_id']/value/text()";
  private static final String XPATH_IMG_VERFICADO =
      "/documentReturn/correcto/document/attribute[name='vigencia']/value/text()";
  private static final String XPATH_IMG_FECHA_DIGITALIZACION =
      "/documentReturn/correcto/document/attribute[name='fecha_dig_imagen']/value/text()";
  private static final String XPATH_IMG_FECHA_ALTA =
      "/documentReturn/correcto/document/attribute[name='fecha_alta']/value/text()";

  private static final String XPATH_CONTENT_IMAGEN = "/Signature/Object/objetoFirma/imagen";
  private static final String XPATH_CONTENT_IMAGEN_TIPO =
      "/Signature/Object/objetoFirma/content_type";

  private final ConsultaWS llamadaEdocProxy;

  public ImagenSOAPDTO consultarImagenes(String sXmlEntrada) throws CorreosBusinessException {
    log.info(LogHelper.getMensaje("metodooo", MSG_ENTRADA));
    String xmlRequetSOAP =
        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
            + "<document cod_app=\"MINERVA\"><attributes><cod_envio>"
            + sXmlEntrada
            + "</cod_envio></attributes></document>";
    return invokeConsultarImagenes(xmlRequetSOAP);
  }

  public ImagenSOAPDTO consultarImagenJustif(String sXmlEntrada) throws CorreosBusinessException {
    log.info(LogHelper.getMensaje("metodooo", MSG_ENTRADA));
    String xmlRequetSOAP =
        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\r\n"
            + "<document cod_app=\"MINERVA\"><attributes><cod_justificante>"
            + sXmlEntrada
            + "</cod_justificante></attributes></document>";
    return invokeConsultarImagenes(xmlRequetSOAP);
  }

  public ImagenSOAPDTO invokeConsultarImagenes(String xmlRequetSOAP)
      throws CorreosBusinessException {
    String nomMetodo = "invokeConsultarImagenes";
    log.info(LogHelper.getMensaje(nomMetodo, MSG_ENTRADA));
    WsCallAuditInfoHolder.setGuardarDatosPetitionRespuestaSOAP(true);

    ImagenSOAPDTO imagenEnvioDTO = new ImagenSOAPDTO();
    try {
      String respuestaXML = llamadaEdocProxy.consultarDocumentos(xmlRequetSOAP);
      DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      Document doc = db.parse(new InputSource(new StringReader(respuestaXML)));
      NodeList nodoImagenBaseXML = doc.getElementsByTagName(TAG_NAME_CONTENT);
      if (nodoImagenBaseXML.getLength() == 0) {
        NodeList nodesCodError = doc.getElementsByTagName(TAG_NAME_COD_ERROR);
        NodeList nodesDesError = doc.getElementsByTagName(TAG_NAME_DES_ERROR);
        if (nodesDesError.getLength() == 0) {
          throw new CorreosBusinessException(
              LogHelper.getMensajeError(nomMetodo, "101", "MALFORMED XML ERROR SOAP"));
        }
        throw new CorreosBusinessException(
            LogHelper.getMensajeError(
                nomMetodo,
                nodesDesError.item(0).getTextContent(),
                nodesCodError.item(0).getTextContent()));
      } else {
        if (!nodoImagenBaseXML.item(0).getTextContent().isBlank()
            && !nodoImagenBaseXML.item(0).getTextContent().isEmpty()) {
          String tipoContenidoRespuesta = evaluateXPath(doc, XPATH_CONTENT_TYPE);

          if (tipoContenidoRespuesta.toLowerCase().indexOf(CONTENT_TYPE_TAG.toLowerCase()) != -1) {
            String imagenRecuperada =
                recuperarImagenContenido(nodoImagenBaseXML.item(0).getTextContent());
            String tipoImagen =
                recuperarTipoImagenContenido(nodoImagenBaseXML.item(0).getTextContent());
            imagenEnvioDTO.setCodigo(evaluateXPath(doc, XPATH_COD_ENVIO));
            imagenEnvioDTO.setImagenBase64(imagenRecuperada);
            imagenEnvioDTO.setTipoImagen(tipoImagen);
            imagenEnvioDTO.setVerificada(evaluateXPath(doc, XPATH_IMG_VERFICADO));
            imagenEnvioDTO.setFechaAlta(evaluateXPath(doc, XPATH_IMG_FECHA_ALTA));
            imagenEnvioDTO.setFechaDigitacion(evaluateXPath(doc, XPATH_IMG_FECHA_DIGITALIZACION));
          } else {
            imagenEnvioDTO.setCodigo(evaluateXPath(doc, XPATH_COD_ENVIO));
            imagenEnvioDTO.setImagenBase64(nodoImagenBaseXML.item(0).getTextContent());
            imagenEnvioDTO.setTipoImagen(tipoContenidoRespuesta);
            imagenEnvioDTO.setVerificada(evaluateXPath(doc, XPATH_IMG_VERFICADO));
            imagenEnvioDTO.setFechaAlta(evaluateXPath(doc, XPATH_IMG_FECHA_ALTA));
            imagenEnvioDTO.setFechaDigitacion(evaluateXPath(doc, XPATH_IMG_FECHA_DIGITALIZACION));
          }
        } else {
          throw new CorreosBusinessException(
              LogHelper.getMensajeError(nomMetodo, "El XML de eDocumento no contiene una Imagen."));
        }
      }
    } catch (WebServiceException | ParserConfigurationException | SAXException | IOException e) {
      log.error(LogHelper.getMensajeError(nomMetodo, e.getMessage()), e);
      throw new CorreosBusinessException(LogHelper.getMensajeError(nomMetodo, e.getMessage()), e);
    }
    return imagenEnvioDTO;
  }

  private String evaluateXPath(Document document, String xpathExpression)
      throws CorreosBusinessException {
    String nomMetodo = "evaluateXPath";
    log.info(LogHelper.getMensaje(nomMetodo, MSG_ENTRADA));
    try {
      XPathFactory xpathFactory = XPathFactory.newInstance();
      XPath xpath = xpathFactory.newXPath();
      XPathExpression expr = xpath.compile(xpathExpression);
      return (String) expr.evaluate(document, XPathConstants.STRING);
    } catch (XPathExpressionException e) {
      throw new CorreosBusinessException(
          LogHelper.getMensajeError("evaluateXPath", "Error en expresion XPath"), e);
    }
  }

  private String recuperarImagenContenido(String content) throws CorreosBusinessException {
    String decodedContent = new String(Base64.getDecoder().decode(content.getBytes()));
    String imagen;
    try {
      DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      Document doc = db.parse(new InputSource(new StringReader(decodedContent)));
      imagen = evaluateXPath(doc, XPATH_CONTENT_IMAGEN);

    } catch (ParserConfigurationException | IOException | SAXException e) {
      LogHelper.getMensajeError("recuperarFirmaContent", e.getMessage());
      throw new CorreosBusinessException(
          LogHelper.getMensajeError("recuperarFirmaContent", e.getMessage()));
    }
    return imagen;
  }

  private String recuperarTipoImagenContenido(String content) throws CorreosBusinessException {
    String decodedContent = new String(Base64.getDecoder().decode(content.getBytes()));
    String tipoImagenContenido;
    try {
      DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      Document doc = db.parse(new InputSource(new StringReader(decodedContent)));
      tipoImagenContenido = evaluateXPath(doc, XPATH_CONTENT_IMAGEN_TIPO);

    } catch (ParserConfigurationException | IOException | SAXException e) {
      LogHelper.getMensajeError("recuperarFirmaContent", e.getMessage());
      throw new CorreosBusinessException(
          LogHelper.getMensajeError("recuperarFirmaContent", e.getMessage()));
    }
    return tipoImagenContenido;
  }
}
