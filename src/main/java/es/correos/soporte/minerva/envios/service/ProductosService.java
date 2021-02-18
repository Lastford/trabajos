package es.correos.soporte.minerva.envios.service;

import es.correos.arch.boot.feign.exceptions.FeignCommunicationException;
import es.correos.soporte.minerva.envios.dto.feign.productos.ProductoDTO;
import java.util.List;

public interface ProductosService {

  List<ProductoDTO> getProductos() throws FeignCommunicationException;
}
