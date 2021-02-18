package es.correos.soporte.minerva.envios.util;

public final class LogHelper {

  private LogHelper() {}

  public static String getMensaje(String metodo, String mensaje) {
    return String.format("%s: %s", metodo, mensaje);
  }

  public static String getMensajeError(String metodo, String mensaje) {
    return String.format("Error en %s: %s", metodo, mensaje);
  }

  public static String getMensajeError(String metodo, String mensaje, String error) {
    return String.format("Error en %s: %s [%s]", metodo, mensaje, error);
  }
}
