package es.correos.soporte.minerva.envios.service;

import es.correos.arch.boot.feign.exceptions.FeignCommunicationException;
import es.correos.soporte.minerva.envios.dto.feign.centros.CentroDTO;
import java.util.List;

public interface CentrosService {

  List<CentroDTO> getCentrosByCodired(String codired) throws FeignCommunicationException;
}
