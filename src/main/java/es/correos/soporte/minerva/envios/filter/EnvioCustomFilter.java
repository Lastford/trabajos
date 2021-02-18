package es.correos.soporte.minerva.envios.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnvioCustomFilter {
  client("CLIENT"),
  codEtiq("COD_ETIQ");

  private String nombreFiltro;
}
