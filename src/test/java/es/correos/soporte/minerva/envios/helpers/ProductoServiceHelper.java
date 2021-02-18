package es.correos.soporte.minerva.envios.helpers;

import es.correos.soporte.minerva.envios.dto.feign.productos.ProductoDTO;
import es.correos.soporte.minerva.envios.dto.feign.productos.ProductoListaDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductoServiceHelper {

  public static ProductoDTO crearProductoDTO() {
    ProductoDTO productoDTO = new ProductoDTO();
    productoDTO.setCodNom("PQ - PAQ Estandar PQ (entrega en Domicilio)");
    productoDTO.setD("PAQ Estandar PQ (entrega en Domicilio)");
    productoDTO.setDa("Nacional");
    productoDTO.setDs("ESTANDAR");
    productoDTO.setEstado("Activo");
    productoDTO.setFamilia("PAQUETERIA");
    productoDTO.setId("PQ");
    productoDTO.setNomAgrupacion("");
    productoDTO.setOrdenProducto(48);
    productoDTO.setSubFamilia("PAQUETERIA");
    return productoDTO;
  }

  public static List<ProductoDTO> crearListaProductoDTO() {
    List<ProductoDTO> productosDTO = new ArrayList<>(Arrays.asList(crearProductoDTO()));
    return productosDTO;
  }

  public static ProductoListaDTO crearProductoListaDTO() {
    ProductoListaDTO productoListaDTO = new ProductoListaDTO(crearListaProductoDTO());
    return productoListaDTO;
  }
}
