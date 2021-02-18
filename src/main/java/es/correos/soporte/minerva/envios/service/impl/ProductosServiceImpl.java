package es.correos.soporte.minerva.envios.service.impl;

import es.correos.arch.boot.feign.exceptions.FeignCommunicationException;
import es.correos.soporte.minerva.envios.dto.feign.productos.ProductoDTO;
import es.correos.soporte.minerva.envios.dto.feign.productos.ProductoListaDTO;
import es.correos.soporte.minerva.envios.service.ProductosService;
import es.correos.soporte.minerva.envios.service.client.feign.ProductosClient;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ProductosServiceImpl implements ProductosService {

  @Autowired private ProductosClient productosClient;

  @Override
  public List<ProductoDTO> getProductos() throws FeignCommunicationException {
    log.info("Entrando método productosService.getProductosByIds");

    ProductoListaDTO productoListaDTO =
        productosClient.getProductos(MediaType.APPLICATION_JSON_UTF8_VALUE);

    List<ProductoDTO> productosDTO = productoListaDTO.getListData();

    log.info("Saliendo método productosService.getProductosByIds");
    return productosDTO;
  }
}
