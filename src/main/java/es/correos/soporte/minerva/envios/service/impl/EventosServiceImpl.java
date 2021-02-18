package es.correos.soporte.minerva.envios.service.impl;

import es.correos.arch.boot.feign.exceptions.FeignCommunicationException;
import es.correos.soporte.minerva.envios.dto.feign.eventos.EventoDTO;
import es.correos.soporte.minerva.envios.dto.feign.eventos.EventoFiltro;
import es.correos.soporte.minerva.envios.dto.feign.eventos.EventoListaDTO;
import es.correos.soporte.minerva.envios.service.EventosService;
import es.correos.soporte.minerva.envios.service.client.feign.EventosClient;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class EventosServiceImpl implements EventosService {

  @Autowired private EventosClient eventosClient;

  @Override
  public List<EventoDTO> getEventosByFilterEvent(EventoFiltro filtroEventos)
      throws FeignCommunicationException {
    log.info("Entrando método eventosService.getEventosByFilterEvent");

    EventoListaDTO eventos = eventosClient.getEventos(filtroEventos);

    List<EventoDTO> listaEventos = eventos.getData();

    log.info("Saliendo método eventosService.getEventosByFilterEvent");
    return listaEventos;
  }
}
