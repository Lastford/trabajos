package es.correos.soporte.minerva.envios.exceptions;

import static org.junit.Assert.assertNotNull;

import es.correos.arq.ex.CorreosBusinessException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class EnvioNotFoundExcTest {

  @Test
  public void testDefaultContructorShouldWork() {
    EnvioNotFoundExc ex = new EnvioNotFoundExc();
    assertNotNull(ex);
  }

  @Test
  public void testMessageContructorShouldWork() {
    EnvioNotFoundExc ex = new EnvioNotFoundExc("message");
    assertNotNull(ex);
  }

  @Test
  public void testExceptionContructorShouldWork() {
    CorreosBusinessException originalEx = new CorreosBusinessException("mensaje Business");
    EnvioNotFoundExc ex = new EnvioNotFoundExc(originalEx);
    assertNotNull(ex);
  }

  @Test
  public void testExceptionMessageContructorShouldWork() {
    CorreosBusinessException originalEx =
        new CorreosBusinessException("mensaje Business", new Exception());
    EnvioNotFoundExc ex = new EnvioNotFoundExc("message", originalEx);
    assertNotNull(ex);
  }
}
