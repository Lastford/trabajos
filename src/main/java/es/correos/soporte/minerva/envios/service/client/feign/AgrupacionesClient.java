package es.correos.soporte.minerva.envios.service.client.feign;

import es.correos.arch.boot.feign.exceptions.FeignCommunicationException;
import es.correos.soporte.minerva.envios.dto.feign.agrupaciones.AgrupacionDTO;
import es.correos.soporte.minerva.envios.dto.feign.agrupaciones.AgrupacionDataDTO;
import es.correos.soporte.minerva.envios.dto.feign.agrupaciones.AgrupacionListaDTO;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
    name = "agrupaciones-service",
    url = "${agrupaciones.schema}://${agrupaciones.host}:${agrupaciones.port}/groups/groups",
    configuration = FeignAutoConfiguration.class)
public interface AgrupacionesClient {

  @GetMapping("/query")
  public AgrupacionListaDTO getAgrupacionesByIdEnvio(
      @RequestParam(name = "shipment-id", required = false, defaultValue = "false")
          String shipmentId)
      throws FeignCommunicationException;

  @GetMapping("/query/{id}")
  public AgrupacionDataDTO getAgrupacionById(
      @PathVariable(name = "id", required = false) String id,
      @RequestParam(name = "children-hierarchy", required = false, defaultValue = "false")
          Boolean childrenHierarchy)
      throws FeignCommunicationException;

  @PostMapping(
      value = "/query/parentHierarchy",
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  AgrupacionDTO getParentHierarchy(@RequestBody AgrupacionDTO agrupacionDTO)
      throws FeignCommunicationException;
}
