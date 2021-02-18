package es.correos.soporte.minerva.envios.util;

import es.correos.soporte.minerva.envios.domain.Envio;
import java.util.List;

public class CSVString {

  private CSVString() {}

  public static final String HEADERS =
      "Envio;Tipo (Producto);Ultimo Evento;Fecha y hora;Codired del evento;Ubicacion del evento;Envio asociado;Referencia 1;Referencia 2;Referencia 3";
  private static final String HEADERSSCP =
      "id;codPaisOrigen;cp;codPaisDestino;modo;errorPrerregistro;producto;cliente;retenidoAduanas;modalidadEntrega;valorDeclarado;circuitoAduana;codMoneda;despacho;recipiente";
  private static final String NEW_LINE = "\n";
  private static final String SEMICOLON = ";";
  private static final String BLANK = "";

  //  public static String getString(List<EnvioDTO> list) {
  //
  //    if (!list.isEmpty()) {
  //      StringBuilder result = new StringBuilder(HEADERS);
  //      for (EnvioDTO envio : list) {
  //
  //        result
  //            .append(NEW_LINE)
  //            .append(changeNull(envio.getId()))
  //            .append(SEMICOLON)
  //            .append(envio.getNomProducto() != null ? changeNull(envio.getNomProducto()) : BLANK)
  //            .append(SEMICOLON)
  //            .append(
  //                envio.getEstado().getResumenSituacion() != null
  //                    ? changeNull(envio.getEstado().getResumenSituacion())
  //                    : BLANK)
  //            .append(SEMICOLON)
  //            .append(
  //                envio.getEstado().getFecha() != null
  //                    ? changeNull(envio.getEstado().getFecha())
  //                    : BLANK)
  //            .append(SEMICOLON)
  //            .append(envio.getCodired() != null ? changeNull(envio.getCodired()) : BLANK)
  //            .append(SEMICOLON)
  //            .append(envio.getNomred() != null ? changeNull(envio.getNomred()) : BLANK)
  //            .append(SEMICOLON)
  //            .append(
  //                envio.getIdEnvioAsociado() != null ? changeNull(envio.getIdEnvioAsociado()) :
  // BLANK)
  //            .append(SEMICOLON)
  //            .append(envio.getReferencia1() != null ? changeNull(envio.getReferencia1()) : BLANK)
  //            .append(SEMICOLON)
  //            .append(envio.getReferencia2() != null ? changeNull(envio.getReferencia2()) : BLANK)
  //            .append(SEMICOLON)
  //            .append(envio.getReferencia3() != null ? changeNull(envio.getReferencia3()) :
  // BLANK);
  //      }
  //
  //      return result.toString();
  //    }
  //    return BLANK;
  //  }

  public static String getStringSCP(List<Envio> list) {

    if (!list.isEmpty()) {
      StringBuilder result = new StringBuilder(HEADERSSCP);
      for (Envio envio : list) {
        result
            .append(NEW_LINE)
            .append(changeNull(envio.getId()))
            .append(SEMICOLON)
            .append(changeNull(envio.getCodPaisOrigen()))
            .append(SEMICOLON)
            .append(changeNull(envio.getCpDestino()))
            .append(SEMICOLON)
            .append(changeNull(envio.getCodPaisDestino()))
            .append(SEMICOLON)
            .append(BLANK)
            .append(SEMICOLON)
            .append(BLANK)
            .append(SEMICOLON)
            .append(changeNull(envio.getPanel().getP()))
            .append(SEMICOLON)
            .append(changeNull(envio.getDocEnv().getIdCliente()))
            .append(SEMICOLON)
            .append(BLANK)
            .append(SEMICOLON)
            .append(BLANK)
            .append(SEMICOLON)
            .append(BLANK)
            .append(SEMICOLON)
            .append(BLANK)
            .append(SEMICOLON)
            .append(changeNull(envio.getDetalles().getCodDivisa()))
            .append(SEMICOLON)
            .append(changeNull(envio.getDetalles().getIdDespachoPostal()))
            .append(SEMICOLON)
            .append(changeNull(envio.getDetalles().getCodRecipiente()));
      }

      return result.toString();
    }
    return BLANK;
  }

  private static Object changeNull(Object property) {
    if (property != null) {
      if (String.valueOf(property).equalsIgnoreCase("null")) {
        return BLANK;
      }
      return property;
    }
    return BLANK;
  }
}
