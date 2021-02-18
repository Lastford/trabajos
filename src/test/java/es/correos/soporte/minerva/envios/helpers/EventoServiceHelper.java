package es.correos.soporte.minerva.envios.helpers;

import es.correos.soporte.minerva.envios.dto.feign.eventos.EventoCoordenadaDTO;
import es.correos.soporte.minerva.envios.dto.feign.eventos.EventoDTO;
import es.correos.soporte.minerva.envios.dto.feign.eventos.EventoListaDTO;
import java.util.ArrayList;
import java.util.List;

public class EventoServiceHelper {

  public static List<EventoDTO> crearListaEventoDTO() {
    List<EventoDTO> lista = new ArrayList<>();
    EventoDTO eventodto1 =
        EventoDTO.builder()
            .desEvento("DESEVENTO1")
            .codired("123")
            .fechaHora("09/12/2020 10:34:02")
            .fase("1")
            .coordenada(new EventoCoordenadaDTO("", ""))
            .id("UX6PZG0467032490119161W")
            .build();
    EventoDTO eventodto2 =
        EventoDTO.builder()
            .desEvento("DESEVENTO2")
            .codired("123")
            .fechaHora("09/12/2020 10:34:02")
            .fase("1")
            .coordenada(new EventoCoordenadaDTO("", ""))
            .id("DPMSAI2MEPADMDOWAXPWAOM2")
            .build();
    EventoDTO eventodto3 =
        EventoDTO.builder()
            .desEvento("DESEVENTO3")
            .codired("123")
            .fechaHora("09/12/2020 10:34:02")
            .fase("2")
            .coordenada(new EventoCoordenadaDTO("", ""))
            .id("UX6PZG0467032490119161W")
            .build();
    EventoDTO eventodto4 =
        EventoDTO.builder()
            .desEvento("DESEVENTO4")
            .codired("123")
            .fechaHora("09/12/2020 10:34:02")
            .fase("3")
            .coordenada(new EventoCoordenadaDTO("", ""))
            .id("UX6PZG0467032490119161W")
            .build();
    EventoDTO eventodto5 =
        EventoDTO.builder()
            .desEvento("DESEVENTO5")
            .codired("123")
            .fechaHora("09/12/2020 10:34:02")
            .fase("4")
            .coordenada(new EventoCoordenadaDTO("--", ""))
            .id("UX6PZG0467032490119161W")
            .build();
    EventoDTO eventodto6 =
        EventoDTO.builder()
            .desEvento("DESEVENTO6")
            .codired("123")
            .fechaHora("09/12/2020 10:34:02")
            .fase("4")
            .coordenada(new EventoCoordenadaDTO("--", ""))
            .build();
    lista.add(eventodto1);
    lista.add(eventodto2);
    lista.add(eventodto3);
    lista.add(eventodto4);
    lista.add(eventodto5);
    lista.add(eventodto6);
    return lista;
  }

  public static EventoListaDTO crearEventoListaDTO() {
    EventoListaDTO eventoListaDTO = new EventoListaDTO();
    eventoListaDTO.setData(crearListaEventoDTO());
    return eventoListaDTO;
  }
}
