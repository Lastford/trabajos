package es.correos.soporte.minerva.envios.service;

import es.correos.arq.ex.CorreosBusinessException;
import es.correos.soporte.minerva.envios.dto.soap.imagenes.ImagenSOAPDTO;
import java.util.List;

public interface ImagenEdocService {

  /**
   * Obtener listado de iamgens que se encuentran asociadas al envio a través de Edocumento.
   *
   * @param codigoEnvio Código de envío que se desea buscar sus imagenes.
   * @return List<ImagenSOAPDTO> Lista DTO con información básica de las imagenes y con el fichero
   *     de la imagen codificado en Base64.
   * @throws CorreosBusinessException
   */
  List<ImagenSOAPDTO> obtenerImagenes(String codigoEnvio) throws CorreosBusinessException;

  ImagenSOAPDTO obtenerImagen(String cio) throws CorreosBusinessException;

  // ImagenSOAPDTO obtenerImagen(String codigoEnvio, String imageCode);

}
