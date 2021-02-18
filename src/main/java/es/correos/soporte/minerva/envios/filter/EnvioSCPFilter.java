package es.correos.soporte.minerva.envios.filter;

import es.correos.soporte.minerva.envios.config.enums.FilterOrderEnum;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnvioSCPFilter {

  @Default private Integer offset = 0;

  @Default private Integer size = Integer.MAX_VALUE;

  @Default private FilterOrderEnum order = FilterOrderEnum.desc;

  private String sort;

  private List<String> id;

  private String fechaReg;

  private String fechaValidez;

  private String codTipoMensaje;

  private String codPaisOrigen;

  private String cp;

  private String codPaisDestino;

  private String modo;

  private String errorPrerregistro;

  private String producto;

  private String cliente;

  private String modalidadEntrega;

  private String retenidoAduanas;

  private String valorDeclarado;

  private String circuitoAduana;

  private String codMoneda;

  private String despacho;

  private String recipiente;
}
