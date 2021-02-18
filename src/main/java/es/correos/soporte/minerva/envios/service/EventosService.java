package es.correos.soporte.minerva.envios.service;

import es.correos.arch.boot.feign.exceptions.FeignCommunicationException;
import es.correos.soporte.minerva.envios.dto.feign.eventos.EventoDTO;
import es.correos.soporte.minerva.envios.dto.feign.eventos.EventoFiltro;
import java.util.List;

public interface EventosService {

  List<EventoDTO> getEventosByFilterEvent(EventoFiltro filtroEventos)
      throws FeignCommunicationException;
}
