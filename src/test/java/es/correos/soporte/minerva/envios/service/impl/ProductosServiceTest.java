package es.correos.soporte.minerva.envios.service.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import es.correos.arch.boot.feign.exceptions.FeignCommunicationException;
import es.correos.soporte.minerva.envios.dto.feign.productos.ProductoDTO;
import es.correos.soporte.minerva.envios.dto.feign.productos.ProductoListaDTO;
import es.correos.soporte.minerva.envios.helpers.ProductoServiceHelper;
import es.correos.soporte.minerva.envios.service.client.feign.ProductosClient;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class ProductosServiceTest {

  @Mock private ProductosClient productosClient;

  @InjectMocks private ProductosServiceImpl productosService;

  @Test
  public void testGetProductos() throws FeignCommunicationException {

    ProductoListaDTO productoListaDTO = ProductoServiceHelper.crearProductoListaDTO();

    when(productosClient.getProductos(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .thenReturn(productoListaDTO);

    List<ProductoDTO> resultado = productosService.getProductos();

    assertNotNull(resultado);
    assertFalse(resultado.isEmpty());
  }
}
