package es.correos.soporte.minerva.envios.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class FechaUtilTest {

  @Test
  public void testNow() {
    String now = FechaUtil.now();
    assertNotNull(now);
    assertFalse(now.isEmpty());
  }

  @Test
  public void testStringIsoOffsetDateTimeToDate() {
    Date resultado = FechaUtil.stringIsoOffsetDateTimeToDate("2020-07-22T20:50:55.378-05:00");

    assertNotNull(resultado);
  }
  /*
   @Test(expected = DateTimeParseException.class)
   public void testStringIsoOffsetDateTimeToDate_FormatoIncorrecto() {
     FechaUtil.stringIsoOffsetDateTimeToDate("YYYY-MM-DDTHH:mm:ssZ");
   }
  */

  @Test
  public void testDateToStringIsoOffsetDateTime() {
    String resultado = FechaUtil.dateToStringDateOutput(new Date());

    assertNotNull(resultado);
  }

  @Test
  public void testDateToStringIsoOffsetDateTime_FechaNula() {
    String resultado = FechaUtil.dateToStringDateOutput(null);

    assertNull(resultado);
  }
}
