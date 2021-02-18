package es.correos.soporte.minerva.envios.service.client.feign;

import es.correos.arch.boot.feign.exceptions.FeignCommunicationException;
import es.correos.soporte.minerva.envios.dto.feign.centros.CentroListaDTO;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
    name = "centros-service",
    url = "${centros.schema}://${centros.host}:${centros.port}/centers/centers",
    configuration = FeignAutoConfiguration.class)
public interface CentrosClient {

  @GetMapping(value = "/query")
  public CentroListaDTO getCentros(
      @RequestParam(name = "codi-red-u", required = false) String codiredu,
      @RequestHeader(
              name = HttpHeaders.ACCEPT,
              required = false,
              defaultValue = MediaType.APPLICATION_JSON_UTF8_VALUE)
          String acceptHeader)
      throws FeignCommunicationException;
}
