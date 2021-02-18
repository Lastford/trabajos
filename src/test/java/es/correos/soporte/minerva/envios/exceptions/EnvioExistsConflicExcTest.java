package es.correos.soporte.minerva.envios.exceptions;

import static org.junit.Assert.assertNotNull;

import es.correos.arq.ex.CorreosBusinessException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class EnvioExistsConflicExcTest {

  @Test
  public void testDefaultContructorShouldWork() {
    EnvioExistsConflictExc ex = new EnvioExistsConflictExc();
    assertNotNull(ex);
  }

  @Test
  public void testMessageContructorShouldWork() {
    EnvioExistsConflictExc ex = new EnvioExistsConflictExc("message");
    assertNotNull(ex);
  }

  @Test
  public void testExceptionContructorShouldWork() {
    CorreosBusinessException originalEx = new CorreosBusinessException("mensaje Business");
    EnvioExistsConflictExc ex = new EnvioExistsConflictExc(originalEx);
    assertNotNull(ex);
  }

  @Test
  public void testExceptionMessageContructorShouldWork() {
    CorreosBusinessException originalEx =
        new CorreosBusinessException("mensaje Business", new Exception());
    EnvioExistsConflictExc ex = new EnvioExistsConflictExc("message", originalEx);
    assertNotNull(ex);
  }
}
