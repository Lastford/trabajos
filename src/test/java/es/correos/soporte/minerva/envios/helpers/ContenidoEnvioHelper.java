package es.correos.soporte.minerva.envios.helpers;


import es.correos.soporte.minerva.envios.dto.feign.agrupaciones.ContenidoEnviosDTO;
import es.correos.soporte.minerva.envios.dto.feign.agrupaciones.UltimoEstadoDTO;

public class ContenidoEnvioHelper {

  public static ContenidoEnviosDTO crearContenidoEnviosHelper() {

    ContenidoEnviosDTO contenidoEnvio = new ContenidoEnviosDTO();

    contenidoEnvio.setId("UX6PZG0467032490119161W");
    contenidoEnvio.setFechaIni("01/01/2021 01:30:00");
    contenidoEnvio.setTipo("TIPO_CONTENIDO");
    contenidoEnvio.setCodigoCentroUnidad("CODIGO_UNIDAD");
    contenidoEnvio.setNombreCentroUnidad("NOMBRE_UNIDAD");
    contenidoEnvio.setUltimoEstado(new UltimoEstadoDTO());

    return contenidoEnvio;
  }
}
