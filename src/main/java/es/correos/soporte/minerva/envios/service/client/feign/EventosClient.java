package es.correos.soporte.minerva.envios.service.client.feign;

import es.correos.arch.boot.feign.exceptions.FeignCommunicationException;
import es.correos.soporte.minerva.envios.dto.feign.eventos.EventoFiltro;
import es.correos.soporte.minerva.envios.dto.feign.eventos.EventoListaDTO;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
    name = "eventos-service",
    url = "${eventos.schema}://${eventos.host}:${eventos.port}/events",
    configuration = FeignAutoConfiguration.class)
public interface EventosClient {

  @PostMapping(
      value = "/events/query/detail",
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  EventoListaDTO getEventos(@RequestBody EventoFiltro filterEvent)
      throws FeignCommunicationException;
}
