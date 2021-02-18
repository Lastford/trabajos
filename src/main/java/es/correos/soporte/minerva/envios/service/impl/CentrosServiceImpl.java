package es.correos.soporte.minerva.envios.service.impl;

import es.correos.arch.boot.feign.exceptions.FeignCommunicationException;
import es.correos.soporte.minerva.envios.dto.feign.centros.CentroDTO;
import es.correos.soporte.minerva.envios.dto.feign.centros.CentroListaDTO;
import es.correos.soporte.minerva.envios.service.CentrosService;
import es.correos.soporte.minerva.envios.service.client.feign.CentrosClient;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class CentrosServiceImpl implements CentrosService {

  @Autowired private CentrosClient centrosClient;

  @Override
  public List<CentroDTO> getCentrosByCodired(String codired) throws FeignCommunicationException {
    log.info("Entrando método centrosService.getCentrosByCodired");

    if (codired.isEmpty()) {
      return new ArrayList<>();
    }
    CentroListaDTO centroLista =
        centrosClient.getCentros(codired, MediaType.APPLICATION_JSON_UTF8_VALUE);

    List<CentroDTO> listaCentros = centroLista.getListData();

    log.info("Saliendo método centrosService.getCentrosByCodired");
    return listaCentros;
  }
}
