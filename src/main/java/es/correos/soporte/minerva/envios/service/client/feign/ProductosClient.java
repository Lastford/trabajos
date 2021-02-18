package es.correos.soporte.minerva.envios.service.client.feign;

import es.correos.arch.boot.feign.exceptions.FeignCommunicationException;
import es.correos.soporte.minerva.envios.dto.feign.productos.ProductoListaDTO;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
    name = "productos-service",
    url = "${productos.schema}://${productos.host}:${productos.port}/products/products",
    configuration = FeignAutoConfiguration.class)
public interface ProductosClient {

  @GetMapping(
      value = "/query",
      produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  public ProductoListaDTO getProductos(
      @RequestHeader(
              name = HttpHeaders.ACCEPT,
              required = false,
              defaultValue = MediaType.APPLICATION_JSON_UTF8_VALUE)
          String acceptHeader)
      throws FeignCommunicationException;
}
