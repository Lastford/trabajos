package es.correos.soporte.minerva.envios.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import es.correos.arch.boot.feign.exceptions.FeignCommunicationException;
import es.correos.soporte.minerva.envios.dto.feign.agrupaciones.AgrupacionDTO;
import es.correos.soporte.minerva.envios.dto.feign.agrupaciones.AgrupacionDataDTO;
import es.correos.soporte.minerva.envios.dto.feign.agrupaciones.AgrupacionListaDTO;
import es.correos.soporte.minerva.envios.service.client.feign.AgrupacionesClient;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class AgrupacionesServiceImplTest {

  @Mock private AgrupacionesClient agrupacionesClient;

  @InjectMocks private AgrupacionesServiceImpl agrupacionesService;

  @Test
  public void testGetAgrupacionesByIdEnvio() throws FeignCommunicationException {

    String shipmentId = "00000000000000000000001";
    AgrupacionListaDTO agrulista = new AgrupacionListaDTO();
    List<AgrupacionDTO> lista = new ArrayList<AgrupacionDTO>();
    AgrupacionDTO agrupacion = AgrupacionDTO.builder().id("00000000000000000000001").build();
    lista.add(agrupacion);
    lista.set(0, agrupacion);

    when(agrupacionesClient.getAgrupacionesByIdEnvio(shipmentId)).thenReturn(agrulista);

    assertNull(agrupacionesService.getAgrupacionesByIdEnvio(shipmentId));
  }

  @Test
  public void testGetAgrupacionesById() throws FeignCommunicationException {

    String shipmentId = "00000000000000000000001";
    AgrupacionDataDTO data = new AgrupacionDataDTO();
    AgrupacionDTO agrupacion = AgrupacionDTO.builder().id("00000000000000000000001").build();
    data.setData(agrupacion);
    Boolean estado = true;

    when(agrupacionesClient.getAgrupacionById(shipmentId, estado)).thenReturn(data);

    assertNotNull(agrupacionesService.getAgrupacionById(shipmentId, estado));
  }

  @Test
  public void testGetAgrupacionParentHierarchyById() throws FeignCommunicationException {
    String id = "00000000000000000000001";
    AgrupacionDTO agrupacion = AgrupacionDTO.builder().id("00000000000000000000001").build();

    when(agrupacionesClient.getParentHierarchy(Mockito.any(AgrupacionDTO.class)))
        .thenReturn(agrupacion);

    assertNotNull(agrupacionesService.getAgrupacionParentHierarchyById(id));
  }
}
