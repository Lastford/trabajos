package es.correos.soporte.minerva.envios.config.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FilterOrderEnum {
  asc("asc"),
  desc("desc");

  private String nombre;
}
