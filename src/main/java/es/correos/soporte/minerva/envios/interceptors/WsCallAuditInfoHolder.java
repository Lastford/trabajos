package es.correos.soporte.minerva.envios.interceptors;

/**
 * Clase para almacencar los xml de llamada/respuesta en las llamadas a WS, siempre que se active.
 * Servirá para almacenar en el sistema la información a modo de auditoría. Es responsabilidad del
 * llamante al WS eliminar la información del hilo para evitar problemas de memoria
 *
 * <p>Los interceptores configurados en el sistema miraran a ver si en esta clase se ha indicado que
 * se debe guardar la petición y respuesta en xml a través del flag SAVE_REQUEST_RESPONSE_XML En ese
 * caso, guardarán los datos para el invocante.
 *
 * <p>Es una clase thread safe.
 *
 * @author everis
 */
public final class WsCallAuditInfoHolder {

  private static final ThreadLocal<String> REQUEST_INFO_XML_TL = new ThreadLocal<String>();
  private static final ThreadLocal<String> RESPONSE_INFO_XML_TL = new ThreadLocal<String>();
  private static final ThreadLocal<Boolean> SAVE_REQUEST_RESPONSE_XML_TL =
      new ThreadLocal<Boolean>();

  /** Constructor protected. Es una clase de utilidad. */
  protected WsCallAuditInfoHolder() {}

  /** @return el contenido de la petición XML almacenada en la variable thread local. */
  public static String getCurrentRequestInfoXML() {
    return REQUEST_INFO_XML_TL.get();
  }

  /**
   * almancena el valor de la petición XML en la variable thread local.
   *
   * @param perfInfo
   */
  public static void setCurrentRequestInfoXML(String requestInfoXML) {
    REQUEST_INFO_XML_TL.set(requestInfoXML);
  }

  /** elimina el contenido de la petición XML de la variable thread local */
  public static void removeCurrentRequestInfoXML() {
    REQUEST_INFO_XML_TL.remove();
  }

  /** @return el contenido de la respuesta XML almacenada en la variable thread local. */
  public static String getCurrentResponseInfoXML() {
    return RESPONSE_INFO_XML_TL.get();
  }

  /**
   * almancena el valor de la respuesta XML en la variable thread local.
   *
   * @param perfInfo
   */
  public static void setCurrentResponseInfoXML(String responseInfoXML) {
    RESPONSE_INFO_XML_TL.set(responseInfoXML);
  }

  /** elimina el contenido de la respuesta XML de la variable thread local */
  public static void removeCurrentResponseInfoXML() {
    RESPONSE_INFO_XML_TL.remove();
  }

  /** @return el valor boolean almacenado en la variable ThreadLocal */
  public static Boolean getGuardarDatosPetitionRespuestaSOAP() {
    return SAVE_REQUEST_RESPONSE_XML_TL.get();
  }

  /**
   * Almacena el valor boolean en la variable thread local. Indica si se debe guardar o no el
   * contenido de la llamada SOAP
   *
   * @param isStarted
   */
  public static void setGuardarDatosPetitionRespuestaSOAP(
      Boolean guardarDatosPetitionRespuestaSOAP) {
    SAVE_REQUEST_RESPONSE_XML_TL.set(guardarDatosPetitionRespuestaSOAP);
  }

  /**
   * Elimina la información que indica si se debe o no guardar la información de la petición en la
   * variable thread local.
   */
  public static void removeGuardarDatosPetitionRespuestaSOAP() {
    SAVE_REQUEST_RESPONSE_XML_TL.remove();
  }

  public static void removeAll() {
    removeCurrentRequestInfoXML();
    removeCurrentResponseInfoXML();
    removeGuardarDatosPetitionRespuestaSOAP();
  }
}
