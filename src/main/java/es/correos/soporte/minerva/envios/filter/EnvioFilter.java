package es.correos.soporte.minerva.envios.filter;

import es.correos.soporte.minerva.envios.config.enums.FilterOrderEnum;
import java.util.Date;
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
public class EnvioFilter {

  @Default private Integer offset = 0;

  @Default private Integer size = Integer.MAX_VALUE;

  @Default private FilterOrderEnum order = FilterOrderEnum.desc;

  private String sort;

  private List<String> id;

  private String cio;

  private String idAgrupacion;

  private String codired;

  private String tipoMensaje;

  private Date fechaInicial;

  private Date fechaFinal;

  private List<String> codigoEstado;

  private List<String> codigoUnico;

  private String nodoReportador;

  private String ambito;

  private List<String> productIds;

  private String idDespachoPostal; // SCP

  private String remesa;

  private List<String> referencia;

  private String codigoPregrabado;

  private String codigoExpedicion;

  private List<String> eventoUnico;

  private List<String> familia;

  private List<String> subFamilia;

  private List<String> clienteUnion;

  /////////////// SCP///////////////

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

  private String recipiente;
}
