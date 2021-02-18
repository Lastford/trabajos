package es.correos.soporte.minerva.envios.exceptions;

public class EnvioExistsConflictExc extends AbstractEntityExistsConflictExc {
  private static final long serialVersionUID = 5978383078782134306L;

  public EnvioExistsConflictExc() {}

  public EnvioExistsConflictExc(String message) {
    super(message);
  }

  public EnvioExistsConflictExc(Exception newOriginalException) {
    super(newOriginalException);
  }

  public EnvioExistsConflictExc(String message, Exception newOriginalException) {
    super(message, newOriginalException);
  }
}
