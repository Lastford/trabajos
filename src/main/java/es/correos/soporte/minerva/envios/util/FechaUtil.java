package es.correos.soporte.minerva.envios.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class FechaUtil {

  public static final String DATE_FORMAT_NOW = "yyyy_MM_dd_HH_mm_ss";
  public static final String TIME_ZONE = "Europe/Madrid";
  public static final String DATE_FORMAT_OUTPUT_STRING = "dd/MM/yyyy HH:mm:ss";
  public static final String DATE_FORMAT_INPUT1_STRING = "yyyy-MM-dd'T'HH:mm:ssX";

  private FechaUtil() {}

  public static String now() {
    TimeZone.setDefault(TimeZone.getTimeZone(TIME_ZONE));
    Calendar calendario = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
    return sdf.format(calendario.getTime());
  }

  public static Date stringIsoOffsetDateTimeToDateISO(String strFechaISO) {
    try {
      if (strFechaISO != null) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_INPUT1_STRING);
        return sdf.parse(strFechaISO);
      }
      return null;
    } catch (ParseException e) {
      log.error(e.getStackTrace());
      return null;
    }
  }

  public static Date convertStringToDate(String strFecha) {
    try {

      if (strFecha != null) {

        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_OUTPUT_STRING);

        return formatter.parse(strFecha);
      }
      return null;
    } catch (Exception e) {
      log.error(e.getStackTrace());
      return null;
    }
  }

  public static Date stringIsoOffsetDateTimeToDate(String strFechaISO) {
    try {
      if (strFechaISO != null && !strFechaISO.isEmpty()) {
        TimeZone.setDefault(TimeZone.getTimeZone(TIME_ZONE));
        TemporalAccessor ta = DateTimeFormatter.ISO_OFFSET_DATE_TIME.parse(strFechaISO);
        Instant instantDate = Instant.from(ta);
        return Date.from(instantDate);
      }
      return null;
    } catch (DateTimeParseException e) {
      log.error(e.getStackTrace());
      return null;
    }
  }

  public static Date stringDateOutputToDate(String stringDateOutput) {
    try {

      if (stringDateOutput != null && !stringDateOutput.isEmpty()) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_OUTPUT_STRING);

        return formatter.parse(stringDateOutput);
      }
      return null;
    } catch (ParseException e) {
      log.error(e.getStackTrace());
      return null;
    }
  }

  public static String dateToStringDateOutput(Date fecha) {
    if (fecha != null) {
      TimeZone.setDefault(TimeZone.getTimeZone(TIME_ZONE));
      SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_OUTPUT_STRING);
      return sdf.format(fecha);
    }
    return null;
  }
}
