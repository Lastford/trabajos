package es.correos.soporte.minerva.envios.service.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import es.correos.arq.ex.CorreosBusinessException;
import es.correos.soporte.minerva.envios.dto.soap.imagenes.ImagenSOAPDTO;
import es.correos.soporte.minerva.envios.service.client.soap.EdocumentImagenSoapClient;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class ImagenEdocServiceTest {

  @Mock private EdocumentImagenSoapClient eDocumentImagenClient;

  @InjectMocks private ImagenEdocServiceImpl imagenEdocService;

  @Test
  public void testObtenerImagenes() throws CorreosBusinessException {
    String idEnvio1 = "PK2AKH0438937000129110Z";
    String idEnvio2 = "ZK2AKH0438937000129110X";
    ImagenSOAPDTO imagenFromDesEnvio = ImagenSOAPDTO.builder().codigo(idEnvio1).build();
    ImagenSOAPDTO imagenFromDesJUSTI = ImagenSOAPDTO.builder().codigo(idEnvio2).build();

    when(eDocumentImagenClient.consultarImagenes(Mockito.anyString()))
        .thenReturn(imagenFromDesEnvio);
    when(eDocumentImagenClient.consultarImagenJustif(Mockito.anyString()))
        .thenReturn(imagenFromDesJUSTI);

    List<ImagenSOAPDTO> resultado = imagenEdocService.obtenerImagenes(idEnvio1);

    assertNotNull(resultado);
    assertFalse(resultado.isEmpty());
  }

  @Test
  public void testObtenerImagenes_consultarImagenes_CorreosBusinessException()
      throws CorreosBusinessException {
    String idEnvio1 = "PK2AKH0438937000129110Z";
    String idEnvio2 = "ZK2AKH0438937000129110X";
    ImagenSOAPDTO imagenFromDesJUSTI = ImagenSOAPDTO.builder().codigo(idEnvio2).build();

    when(eDocumentImagenClient.consultarImagenes(Mockito.anyString()))
        .thenThrow(CorreosBusinessException.class);
    when(eDocumentImagenClient.consultarImagenJustif(Mockito.anyString()))
        .thenReturn(imagenFromDesJUSTI);

    List<ImagenSOAPDTO> resultado = imagenEdocService.obtenerImagenes(idEnvio1);

    assertNotNull(resultado);
    assertFalse(resultado.isEmpty());
  }

  @Test
  public void testObtenerImagenes_consultarImagenJustif_CorreosBusinessException()
      throws CorreosBusinessException {
    String idEnvio1 = "PK2AKH0438937000129110Z";
    ImagenSOAPDTO imagenFromDesEnvio = ImagenSOAPDTO.builder().codigo(idEnvio1).build();

    when(eDocumentImagenClient.consultarImagenes(Mockito.anyString()))
        .thenReturn(imagenFromDesEnvio);
    when(eDocumentImagenClient.consultarImagenJustif(Mockito.anyString()))
        .thenThrow(CorreosBusinessException.class);

    List<ImagenSOAPDTO> resultado = imagenEdocService.obtenerImagenes(idEnvio1);

    assertNotNull(resultado);
    assertFalse(resultado.isEmpty());
  }

  @Test
  public void testObtenerImagen() throws CorreosBusinessException {
    String idEnvio = "PK2AKH0438937000129110Z";
    ImagenSOAPDTO imagenFromDesEnvio = ImagenSOAPDTO.builder().codigo(idEnvio).build();

    when(eDocumentImagenClient.consultarImagenes(Mockito.anyString()))
        .thenReturn(imagenFromDesEnvio);

    ImagenSOAPDTO resultado = imagenEdocService.obtenerImagen(idEnvio);

    assertNotNull(resultado);
  }

  @Test(expected = CorreosBusinessException.class)
  public void testObtenerImagen_CorreosBusinessException() throws CorreosBusinessException {
    String idEnvio = "PK2AKH0438937000129110Z";

    when(eDocumentImagenClient.consultarImagenes(Mockito.anyString()))
        .thenThrow(CorreosBusinessException.class);

    imagenEdocService.obtenerImagen(idEnvio);
  }
}
