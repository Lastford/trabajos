/** */
package es.correos.soporte.minerva.envios.exceptions;

public class EnvioNotFoundExc extends AbstractEntityNotFoundExc {
  private static final long serialVersionUID = 5978383078782134306L;

  public EnvioNotFoundExc() {
    super();
  }

  public EnvioNotFoundExc(String message) {
    super(message);
  }

  public EnvioNotFoundExc(Exception newOriginalException) {
    super(newOriginalException);
  }

  public EnvioNotFoundExc(String message, Exception newOriginalException) {
    super(message, newOriginalException);
  }
}
