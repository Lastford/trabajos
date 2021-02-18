package es.correos.soporte.minerva.envios.util;

import es.correos.soporte.minerva.envios.config.enums.FiltroOrdenacion;
import es.correos.soporte.minerva.envios.dto.EnvioDTO;
import es.correos.soporte.minerva.envios.dto.EnvioEventoDTO;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class EnviosUtils {

  private EnviosUtils() {}

  public static final String DATE_FORMAT_NOW = "yyyy_MM_dd_HH_mm_ss";

  public static String now() {
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
    return sdf.format(cal.getTime());
  }

  /**
   * Convierte una cadena de String concatenada a un arreglo de String. por defecto con los
   * siguientes separadores ",|;"
   *
   * @param strConcatenado
   * @return List<String>
   */
  public static List<String> splitCadenaToArrayBySeparator(String strConcatenado) {
    return new ArrayList<>(Arrays.asList(strConcatenado.split(",|;")));
  }

  /**
   * Convierte una cadena de String concatenada a un arreglo de String. por separador personalizado
   *
   * @param strConcatenado
   * @param separator Separador
   * @return List<String>
   */
  public static List<String> splitCadenaToArrayBySeparator(
      String strConcatenado, String separator) {
    return new ArrayList<>(Arrays.asList(strConcatenado.split(separator)));
  }

  public static List<String> obtenerCodEvento(String codigoEstado) {
    List<String> listaCodigos = new ArrayList<>();
    List<String> codigosEstados = splitCadenaToArrayBySeparator(codigoEstado, ";");
    for (String codigos : codigosEstados) {
      List<String> codigosEstadosConcatenados = splitCadenaToArrayBySeparator(codigos, ",");
      listaCodigos.add(codigosEstadosConcatenados.get(0));
    }
    return listaCodigos;
  }

  public static String getValorFiltroSort(String sort) {
    if (sort != null && !sort.isEmpty()) {
      FiltroOrdenacion[] filtros = FiltroOrdenacion.values();
      for (FiltroOrdenacion filtro : filtros) {
        if (filtro.name().equalsIgnoreCase(sort)) {
          return filtro.getNombreCampo();
        }
      }
    }
    return null;
  }

  public static String getCodiredsFromListEnvioEventosDTO(
      List<EnvioEventoDTO> listaEnvioEventosDTO) {
    List<String> listaCodired = new ArrayList<>();
    for (EnvioEventoDTO envioEventoDTO : listaEnvioEventosDTO) {
      if (!listaCodired.contains(envioEventoDTO.getCodired())) {
        listaCodired.add(envioEventoDTO.getCodired());
      }
    }
    return String.join(",", listaCodired);
  }

  public static String getCodiredsFromListEnviosDTO(List<EnvioDTO> listaEnviosDTO) {
    List<String> listaCodired = new ArrayList<>();
    for (EnvioDTO envioDTO : listaEnviosDTO) {
      if (!listaCodired.contains(envioDTO.getCodired())) {
        listaCodired.add(envioDTO.getCodired());
      }
    }
    return String.join(",", listaCodired);
  }

  public static String getStringConcatenatedByCommaFromArrayString(List<String> lista) {
    return String.join(",", lista);
  }

  public static String getProductsCodeFromListEnviosDTO(List<EnvioDTO> listaEnviosDTO) {
    List<String> listaCodigoProductos = new ArrayList<>();
    for (EnvioDTO envioDTO : listaEnviosDTO) {
      if (!listaCodigoProductos.contains(envioDTO.getCodProducto())) {
        listaCodigoProductos.add(envioDTO.getCodProducto());
      }
    }
    return String.join(",", listaCodigoProductos);
  }
}
