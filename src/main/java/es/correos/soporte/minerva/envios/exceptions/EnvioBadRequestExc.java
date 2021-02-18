package es.correos.soporte.minerva.envios.exceptions;

public class EnvioBadRequestExc extends AbstractEntityExistsConflictExc {

  private static final long serialVersionUID = 1611593027693988376L;

  public EnvioBadRequestExc() {
    super();
  }

  public EnvioBadRequestExc(String message) {
    super(message);
  }

  public EnvioBadRequestExc(Exception newOriginalException) {
    super(newOriginalException);
  }

  public EnvioBadRequestExc(String message, Exception newOriginalException) {
    super(message, newOriginalException);
  }
}
