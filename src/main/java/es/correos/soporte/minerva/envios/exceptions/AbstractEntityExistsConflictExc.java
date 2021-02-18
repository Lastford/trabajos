/** */
package es.correos.soporte.minerva.envios.exceptions;

import es.correos.arq.ex.CorreosDAOException;

public abstract class AbstractEntityExistsConflictExc extends CorreosDAOException {
  private static final long serialVersionUID = 5978383078782134306L;

  public AbstractEntityExistsConflictExc() {
    super();
  }

  public AbstractEntityExistsConflictExc(String message) {
    super(message);
  }

  public AbstractEntityExistsConflictExc(Exception newOriginalException) {
    super(newOriginalException);
  }

  public AbstractEntityExistsConflictExc(String message, Exception newOriginalException) {
    super(message, newOriginalException);
  }
}
