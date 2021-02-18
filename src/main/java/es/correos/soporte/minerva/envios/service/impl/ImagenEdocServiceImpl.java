package es.correos.soporte.minerva.envios.service.impl;

import es.correos.arq.ex.CorreosBusinessException;
import es.correos.soporte.minerva.envios.dto.soap.imagenes.ImagenSOAPDTO;
import es.correos.soporte.minerva.envios.service.ImagenEdocService;
import es.correos.soporte.minerva.envios.service.client.soap.EdocumentImagenSoapClient;
import es.correos.soporte.minerva.envios.util.LogHelper;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class ImagenEdocServiceImpl implements ImagenEdocService {

  private static final String MSG_ENTRADA = "entrando en el metodo";
  private final EdocumentImagenSoapClient eDocumentImagenClient;

  @Override
  public List<ImagenSOAPDTO> obtenerImagenes(String codigoEnvio) throws CorreosBusinessException {

    String nomMetodo = "obtenerImagenes";
    log.info(LogHelper.getMensaje(nomMetodo, MSG_ENTRADA));
    List<ImagenSOAPDTO> lstImagenesDTOSOAP = new ArrayList<>();
    try {
      ImagenSOAPDTO imagenFromDesEnvio = eDocumentImagenClient.consultarImagenes(codigoEnvio);
      if (imagenFromDesEnvio != null) {
        lstImagenesDTOSOAP.add(imagenFromDesEnvio);
      }
    } catch (CorreosBusinessException e) {
      log.info(LogHelper.getMensaje(nomMetodo, "Error al obtener imagenFromDesEnvio"));
    }

    try {
      ImagenSOAPDTO imagenFromDesJUSTI = eDocumentImagenClient.consultarImagenJustif(codigoEnvio);
      if (imagenFromDesJUSTI != null) {
        lstImagenesDTOSOAP.add(imagenFromDesJUSTI);
      }
    } catch (Exception e) {
      log.info(LogHelper.getMensaje(nomMetodo, "Error al obtener imagenFromDesJUSTI"));
    }

    //
    //		} catch (CorreosBusinessException e) {
    //			log.error(e.getMessage(), e);
    //			throw new CorreosBusinessException(LogHelper.getMensajeError(nomMetodo,
    //					"No se pudo la certificación en fichero .pdf desde Edocumento.", e.getMessage()));
    //		}
    return lstImagenesDTOSOAP;
  }

  @Override
  public ImagenSOAPDTO obtenerImagen(String codigoEnvio) throws CorreosBusinessException {
    String nomMetodo = "obtenerImagen-DOWNLOAD";
    log.info(LogHelper.getMensaje(nomMetodo, MSG_ENTRADA));
    ImagenSOAPDTO imagenFromDesEnvio = new ImagenSOAPDTO();
    try {
      imagenFromDesEnvio = eDocumentImagenClient.consultarImagenes(codigoEnvio);
    } catch (CorreosBusinessException e) {
      log.error(e.getMessage(), e);
      throw new CorreosBusinessException(
          LogHelper.getMensajeError(
              nomMetodo,
              "No se pudo la certificación en fichero .pdf desde Edocumento.",
              e.getMessage()));
    }
    return imagenFromDesEnvio;
  }
}
