package es.correos.soporte.minerva.envios.exceptions.handler;

import es.correos.arch.boot.core.exception.ErrorCode;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ApplicationErrorCode implements ErrorCode {
  ENTITY_EXISTS_CONFLICT("Entity exists - conflict");

  private final String reasonPhrase;
}
