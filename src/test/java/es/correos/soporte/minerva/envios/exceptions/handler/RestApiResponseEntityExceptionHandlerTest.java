package es.correos.soporte.minerva.envios.exceptions.handler;

import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import es.correos.arch.boot.core.exception.ApiError;
import es.correos.arch.boot.core.properties.CoreApplicationProperties;
import es.correos.soporte.minerva.envios.exceptions.EnvioBadRequestExc;
import es.correos.soporte.minerva.envios.exceptions.EnvioExistsConflictExc;
import es.correos.soporte.minerva.envios.exceptions.EnvioNotFoundExc;
import java.util.Objects;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

@RunWith(SpringRunner.class)
public class RestApiResponseEntityExceptionHandlerTest {

  private RestApiResponseEntityExceptionHandler restApiResponseEntityExceptionHandler;

  private WebRequest request;

  @Mock private HttpInputMessage httpInputMessage;

  protected static ObjectMapper om = new ObjectMapper();

  @Before
  public void setup() {
    MockHttpServletRequest servletRequest = new MockHttpServletRequest("GET", "/");
    MockHttpServletResponse servletResponse = new MockHttpServletResponse();
    CoreApplicationProperties coreApplicationProperties = new CoreApplicationProperties();
    this.request = new ServletWebRequest(servletRequest, servletResponse);
    this.restApiResponseEntityExceptionHandler =
        new RestApiResponseEntityExceptionHandler(coreApplicationProperties);
    om.registerModule(new JavaTimeModule());
    om.enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
  }

  @Test
  public void testHandleElementNotFoundException() {

    es.correos.soporte.minerva.envios.exceptions.AbstractEntityNotFoundExc ex =
        new EnvioNotFoundExc("Player not found");
    ResponseEntity<Object> responseEntity =
        this.restApiResponseEntityExceptionHandler.handleElementNotFoundException(ex, this.request);

    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    assertEquals(
        ex.getMessage(),
        ((ApiError) Objects.requireNonNull(responseEntity.getBody())).getMessage());
  }

  @Test
  public void testhandleExistsConflictException() {

    es.correos.soporte.minerva.envios.exceptions.AbstractEntityExistsConflictExc ex =
        new EnvioExistsConflictExc("Player not found");
    ResponseEntity<Object> responseEntity =
        this.restApiResponseEntityExceptionHandler.handleExistsConflictException(ex, request);

    assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
  }

  @Test
  public void testhandleProgramaBadRequestException() {

    es.correos.soporte.minerva.envios.exceptions.EnvioBadRequestExc ex =
        new EnvioBadRequestExc("Player not found");
    ResponseEntity<Object> responseEntity =
        this.restApiResponseEntityExceptionHandler.handleProgramaBadRequestException(ex, request);

    assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    assertEquals(
        ex.getMessage(),
        ((ApiError) Objects.requireNonNull(responseEntity.getBody())).getMessage());
  }
}
