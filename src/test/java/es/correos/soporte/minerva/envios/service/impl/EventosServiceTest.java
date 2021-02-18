package es.correos.soporte.minerva.envios.service.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import es.correos.arch.boot.feign.exceptions.FeignCommunicationException;
import es.correos.soporte.minerva.envios.dto.feign.eventos.EventoDTO;
import es.correos.soporte.minerva.envios.dto.feign.eventos.EventoFiltro;
import es.correos.soporte.minerva.envios.dto.feign.eventos.EventoListaDTO;
import es.correos.soporte.minerva.envios.helpers.EventoServiceHelper;
import es.correos.soporte.minerva.envios.service.client.feign.EventosClient;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class EventosServiceTest {

  @Mock private EventosClient eventosClient;

  @InjectMocks EventosServiceImpl eventosService;

  @Test
  public void testGetEventosByFilterEvent() throws FeignCommunicationException {
    EventoFiltro filtroEventos = new EventoFiltro();
    filtroEventos.setAllData(false);
    filtroEventos.setIsShipment(true);
    filtroEventos.setListaIdsEvento(Arrays.asList("UX6PZG0467032490119161W"));

    EventoListaDTO eventos = EventoServiceHelper.crearEventoListaDTO();

    when(eventosClient.getEventos(Mockito.any(EventoFiltro.class))).thenReturn(eventos);

    List<EventoDTO> resultado = eventosService.getEventosByFilterEvent(filtroEventos);

    assertNotNull(resultado);
    assertFalse(resultado.isEmpty());
  }
}
