package es.correos.soporte.minerva.envios.service;

import es.correos.arch.boot.feign.exceptions.FeignCommunicationException;
import es.correos.soporte.minerva.envios.dto.feign.agrupaciones.AgrupacionDTO;
import java.util.List;

public interface AgrupacionesService {
  List<AgrupacionDTO> getAgrupacionesByIdEnvio(String idEnvio) throws FeignCommunicationException;

  AgrupacionDTO getAgrupacionById(String id, Boolean childrenHierarchy)
      throws FeignCommunicationException;

  AgrupacionDTO getAgrupacionParentHierarchyById(String id) throws FeignCommunicationException;
}
