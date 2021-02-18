package es.correos.soporte.minerva.envios.service.impl;

import es.correos.arch.boot.feign.exceptions.FeignCommunicationException;
import es.correos.soporte.minerva.envios.dto.feign.agrupaciones.AgrupacionDTO;
import es.correos.soporte.minerva.envios.dto.feign.agrupaciones.AgrupacionDataDTO;
import es.correos.soporte.minerva.envios.dto.feign.agrupaciones.AgrupacionListaDTO;
import es.correos.soporte.minerva.envios.service.AgrupacionesService;
import es.correos.soporte.minerva.envios.service.client.feign.AgrupacionesClient;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class AgrupacionesServiceImpl implements AgrupacionesService {

  @Autowired private AgrupacionesClient agrupacionesClient;

  @Override
  public List<AgrupacionDTO> getAgrupacionesByIdEnvio(String idEnvio)
      throws FeignCommunicationException {
    log.info("Entrando método agrupacionesService.getAgrupacionesByIdEnvio");

    AgrupacionListaDTO agrupaciones = agrupacionesClient.getAgrupacionesByIdEnvio(idEnvio);

    List<AgrupacionDTO> listaAgrupaciones = agrupaciones.getListData();

    log.info("Saliendo método agrupacionesService.getAgrupacionesByIdEnvio");
    return listaAgrupaciones;
  }

  @Override
  public AgrupacionDTO getAgrupacionById(String id, Boolean childrenHierarchy)
      throws FeignCommunicationException {
    log.info("Entrando método agrupacionesService.getAgrupacionById");

    AgrupacionDataDTO agrupacionData = agrupacionesClient.getAgrupacionById(id, childrenHierarchy);

    AgrupacionDTO agrupacionDTO = agrupacionData.getData();

    log.info("Saliendo método agrupacionesService.getAgrupacionById");
    return agrupacionDTO;
  }

  @Override
  public AgrupacionDTO getAgrupacionParentHierarchyById(String id)
      throws FeignCommunicationException {
    log.info("Entrando método agrupacionesService.getAgrupacionParentHierarchyById");

    AgrupacionDTO agrupacionDTO =
        agrupacionesClient.getParentHierarchy(AgrupacionDTO.builder().id(id).build());

    log.info("Saliendo método agrupacionesService.getAgrupacionParentHierarchyById");
    return agrupacionDTO;
  }
}
