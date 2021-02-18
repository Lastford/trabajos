package es.correos.soporte.minerva.envios.util;

import es.correos.soporte.minerva.envios.dto.feign.eventos.EventoDTO;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;

public class EnvioFechaFaseComparator implements Comparator<EventoDTO> {

  @Override
  public int compare(EventoDTO o1, EventoDTO o2) {
    if (o1.getFechaHora() != null && o2.getFechaHora() != null) {
      try {
        DateFormat DATEFORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return DATEFORMAT.parse(o1.getFechaHora()).compareTo(DATEFORMAT.parse(o2.getFechaHora()));
      } catch (ParseException e) {
        return -1;
      }
    }
    return -1;
  }
}
