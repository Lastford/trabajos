package es.correos.soporte.minerva.envios.exceptions;

import static org.junit.Assert.assertNotNull;

import es.correos.arq.ex.CorreosBusinessException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class EnvioBadRequestExcTest {

  @Test
  public void testDefaultContructorShouldWork() {
    EnvioBadRequestExc ex = new EnvioBadRequestExc();
    assertNotNull(ex);
  }

  @Test
  public void testMessageContructorShouldWork() {
    EnvioBadRequestExc ex = new EnvioBadRequestExc("message");
    assertNotNull(ex);
  }

  @Test
  public void testExceptionContructorShouldWork() {
    CorreosBusinessException originalEx = new CorreosBusinessException("mensaje Business");
    EnvioBadRequestExc ex = new EnvioBadRequestExc(originalEx);
    assertNotNull(ex);
  }

  @Test
  public void testExceptionMessageContructorShouldWork() {
    CorreosBusinessException originalEx =
        new CorreosBusinessException("mensaje Business", new Exception());
    EnvioBadRequestExc ex = new EnvioBadRequestExc("message", originalEx);
    assertNotNull(ex);
  }
}
