/** */
package es.correos.soporte.minerva.envios.exceptions;

import es.correos.arq.ex.CorreosDAOException;

public abstract class AbstractEntityNotFoundExc extends CorreosDAOException {
  private static final long serialVersionUID = 5978383078782134306L;

  public AbstractEntityNotFoundExc() {
    super();
  }

  public AbstractEntityNotFoundExc(String message) {
    super(message);
  }

  public AbstractEntityNotFoundExc(Exception newOriginalException) {
    super(newOriginalException);
  }

  public AbstractEntityNotFoundExc(String message, Exception newOriginalException) {
    super(message, newOriginalException);
  }
}
