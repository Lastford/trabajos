package es.correos.soporte.minerva.envios.helpers;

import es.correos.soporte.minerva.envios.dto.feign.agrupaciones.AgrupacionDTO;
import es.correos.soporte.minerva.envios.dto.feign.agrupaciones.ContenidoEnviosDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AgrupacionServiceHelper {

  public static AgrupacionDTO crearAgrupacionDTO() {
    AgrupacionDTO agrupacionDTO = new AgrupacionDTO();
    AgrupacionDTO agrupacionDTOHijo = new AgrupacionDTO();
    agrupacionDTO.setId("0028219200272Q7G302001F");
    agrupacionDTOHijo.setId("0028319200271KYH329003E");
    agrupacionDTOHijo.setJerarquiaHijos(new ArrayList<>());
    agrupacionDTOHijo.setJerarquiaPadres(new ArrayList<>());
    List<AgrupacionDTO> agrupacionesHijos = new ArrayList<>(Arrays.asList(agrupacionDTOHijo));
    agrupacionDTO.setContenidoEnvios(
        new ArrayList<>(
            Arrays.asList(ContenidoEnviosDTO.builder().id("UX6PZG0467032490119161W").build())));
    agrupacionDTOHijo.setContenidoEnvios(
        new ArrayList<>(
            Arrays.asList(ContenidoEnviosDTO.builder().id("PQ6SEH0415676830113600Q").build())));
    agrupacionDTO.setJerarquiaHijos(agrupacionesHijos);
    agrupacionDTO.setJerarquiaPadres(new ArrayList<>());
    return agrupacionDTO;
  }
}
