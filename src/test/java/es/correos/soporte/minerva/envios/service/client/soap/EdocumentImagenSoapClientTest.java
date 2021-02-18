package es.correos.soporte.minerva.envios.service.client.soap;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import es.correos.arq.ex.CorreosBusinessException;
import es.correos.soporte.minerva.envios.dto.soap.imagenes.ImagenSOAPDTO;
import es.correos.soporte.minerva.recursos.edocumento.wsdl.ConsultaWS;
import javax.xml.ws.WebServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class EdocumentImagenSoapClientTest {

  @Mock private ConsultaWS llamadaEdocProxy;

  @InjectMocks EdocumentImagenSoapClient edocumentImagenSoapClient;

  @Test
  public void testConsultarImagenes() throws CorreosBusinessException {
    String idEnvio = "PK2AKH0438937000129110Z";

    String xml =
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <documentReturn cod_justificante=\"PK2AKH0438937000129110Z\" codigo_retorno=\"0\" num_imagenes=\"1\"> <correcto> <document content_type=\"image/png\" /> </correcto> <content> imagen </content> </documentReturn>";

    when(llamadaEdocProxy.consultarDocumentos(Mockito.anyString())).thenReturn(xml);

    ImagenSOAPDTO resultado = edocumentImagenSoapClient.consultarImagenes(idEnvio);

    assertNotNull(resultado);
  }

  @Test
  public void testConsultarImagenJustif() throws CorreosBusinessException {
    String idEnvio = "PK2AKH0438937000129110Z";

    String xml =
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <documentReturn cod_justificante=\"PK2AKH0438937000129110Z\" codigo_retorno=\"0\" num_imagenes=\"1\"> <correcto> <document content_type=\"image/png\" /> </correcto> <content> imagen </content> </documentReturn>";

    when(llamadaEdocProxy.consultarDocumentos(Mockito.anyString())).thenReturn(xml);

    ImagenSOAPDTO resultado = edocumentImagenSoapClient.consultarImagenJustif(idEnvio);

    assertNotNull(resultado);
  }

  @Test(expected = CorreosBusinessException.class)
  public void testInvokeConsultarImagenes_llamadaEdocFallo() throws CorreosBusinessException {
    String xmlEntrada = "xml";

    when(llamadaEdocProxy.consultarDocumentos(Mockito.anyString()))
        .thenThrow(WebServiceException.class);

    edocumentImagenSoapClient.invokeConsultarImagenes(xmlEntrada);
  }

  @Test(expected = CorreosBusinessException.class)
  public void testInvokeConsultarImagenes_ConError() throws CorreosBusinessException {
    String xmlEntrada = "xml";

    String xmlRespuesta =
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <documentReturn codigo_retorno=\"1\" num_imagenes=\"0\"> <erroneo> <cod_error>5</cod_error> <des_error>No se permite contenido en el prólogo.</des_error> </erroneo> </documentReturn>";

    when(llamadaEdocProxy.consultarDocumentos(Mockito.anyString())).thenReturn(xmlRespuesta);

    edocumentImagenSoapClient.invokeConsultarImagenes(xmlEntrada);
  }

  @Test(expected = CorreosBusinessException.class)
  public void testInvokeConsultarImagenes_NoContent() throws CorreosBusinessException {
    String xmlEntrada = "xml";

    String xmlRespuesta =
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <documentReturn cod_justificante=\"PK2AKH0438937000129110Z\" codigo_retorno=\"0\" num_imagenes=\"0\"> <correcto /> </documentReturn>";

    when(llamadaEdocProxy.consultarDocumentos(Mockito.anyString())).thenReturn(xmlRespuesta);

    edocumentImagenSoapClient.invokeConsultarImagenes(xmlEntrada);
  }

  @Test(expected = CorreosBusinessException.class)
  public void testInvokeConsultarImagenes_Content_SinImagen() throws CorreosBusinessException {
    String xmlEntrada = "xml";

    String xmlRespuesta =
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <documentReturn cod_justificante=\"PK2AKH0438937000129110Z\" codigo_retorno=\"0\" num_imagenes=\"0\"> <correcto /> <content /> </documentReturn>";

    when(llamadaEdocProxy.consultarDocumentos(Mockito.anyString())).thenReturn(xmlRespuesta);

    edocumentImagenSoapClient.invokeConsultarImagenes(xmlEntrada);
  }
}
