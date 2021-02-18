package es.correos.soporte.minerva.envios.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import es.correos.arch.boot.feign.exceptions.FeignCommunicationException;
import es.correos.soporte.minerva.envios.dto.feign.centros.CentroDTO;
import es.correos.soporte.minerva.envios.dto.feign.centros.CentroListaDTO;
import es.correos.soporte.minerva.envios.service.client.feign.CentrosClient;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class CentrosServiceTest {

  @Mock private CentrosClient centroCliente;

  @InjectMocks private CentrosServiceImpl centroService;

  @Test
  public void testGetCoordenadasByCodired() throws FeignCommunicationException {
    String codired = "123456";
    List<CentroDTO> centros = new ArrayList<CentroDTO>();
    CentroDTO centro = CentroDTO.builder().id("id").codiRedU("123456").build();
    centros.add(centro);
    CentroListaDTO lista = new CentroListaDTO(centros);
    when(centroCliente.getCentros(codired, MediaType.APPLICATION_JSON_UTF8_VALUE))
        .thenReturn(lista);
    assertNotNull(centroService.getCentrosByCodired(codired));
  }

  @Test
  public void testGetCoordenadasByCodired_Vacio() throws FeignCommunicationException {
    String codired = "";

    assertNotNull(centroService.getCentrosByCodired(codired));
  }
}
