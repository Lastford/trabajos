package es.correos.soporte.minerva.envios.exceptions.handler;

import es.correos.arch.boot.core.exception.ArchErrorCode;
import es.correos.arch.boot.core.exception.handler.CoreRestApiResponseEntityExceptionHandler;
import es.correos.arch.boot.core.properties.CoreApplicationProperties;
import es.correos.soporte.minerva.envios.exceptions.AbstractEntityNotFoundExc;
import java.util.Arrays;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class RestApiResponseEntityExceptionHandler
    extends CoreRestApiResponseEntityExceptionHandler {

  public RestApiResponseEntityExceptionHandler(
      CoreApplicationProperties coreApplicationProperties) {
    super(coreApplicationProperties);
  }

  @ExceptionHandler(es.correos.soporte.minerva.envios.exceptions.EnvioBadRequestExc.class)
  public ResponseEntity<Object> handleProgramaBadRequestException(
      final es.correos.soporte.minerva.envios.exceptions.EnvioBadRequestExc ex,
      final WebRequest request) {

    return getTechnicalExceptionResponseEntity(
        request,
        ArchErrorCode.CORREOS_GENERIC_ERROR,
        ex.getMessage(),
        Arrays.asList(ex.getMessage()),
        getStackTrace(ex),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(es.correos.soporte.minerva.envios.exceptions.EnvioNotFoundExc.class)
  public ResponseEntity<Object> handleElementNotFoundException(
      final AbstractEntityNotFoundExc ex, final WebRequest request) {

    return getTechnicalExceptionResponseEntity(
        request,
        ArchErrorCode.ENTITY_NOT_FOUND,
        ex.getMessage(),
        Arrays.asList(ex.getMessage()),
        getStackTrace(ex),
        HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(
      es.correos.soporte.minerva.envios.exceptions.AbstractEntityExistsConflictExc.class)
  public ResponseEntity<Object> handleExistsConflictException(
      final es.correos.soporte.minerva.envios.exceptions.AbstractEntityExistsConflictExc ex,
      final WebRequest request) {

    return getTechnicalExceptionResponseEntity(
        request,
        ApplicationErrorCode.ENTITY_EXISTS_CONFLICT,
        ex.getMessage(),
        Arrays.asList(ex.getMessage()),
        getStackTrace(ex),
        HttpStatus.CONFLICT);
  }
}
