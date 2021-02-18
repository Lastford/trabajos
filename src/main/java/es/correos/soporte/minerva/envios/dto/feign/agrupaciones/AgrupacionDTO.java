package es.correos.soporte.minerva.envios.dto.feign.agrupaciones;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.ArrayList;
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
public class AgrupacionDTO implements Serializable {

  private static final long serialVersionUID = 2004440174929362136L;

  @JsonProperty("id")
  private String id;

  @JsonProperty("type")
  private String tipo;

  @JsonProperty("modification_date")
  private String fechaModificacion;

  @JsonProperty("elements_number")
  private Integer numeroElementos;

  @JsonProperty("unity_center_code")
  private String codigoCentroUnidad;

  @JsonProperty("unity_center_name")
  private String nombreCentroUnidad;

  @JsonProperty("first_elements_number")
  private Integer numeroElementosPrimerNivel;

  @JsonProperty("elements_number_with_notices")
  private Integer numeroElementosConAviso;

  @Default
  @JsonProperty("last_status")
  private UltimoEstadoDTO ultimoEstado = new UltimoEstadoDTO();

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @Default
  @JsonProperty("contained_groups")
  private List<ContenidoAgrupacionesDTO> contenidoAgrupaciones = new ArrayList<>();

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @Default
  @JsonProperty("historical_groups")
  private List<ContenidoAgrupacionesHistoricoDTO> contenidoAgrupacionesHistorico =
      new ArrayList<>();

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @Default
  @JsonProperty("contained_shipments")
  private List<ContenidoEnviosDTO> contenidoEnvios = new ArrayList<>();

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @Default
  @JsonProperty("historical_shipments")
  private List<ContenidoEnviosHistoricoDTO> contenidoEnviosHistorico = new ArrayList<>();

  @JsonProperty("shipments_number")
  private Integer numeroEnvios;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonProperty("parent_id")
  private String idAgrupacionPadre;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonProperty("parent_type")
  private String tipoAgrupacionPadre;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonProperty("number_plate")
  private String matricula;

  @JsonProperty("program")
  private String programa;

  @JsonProperty("destination_id")
  private String destino;

  @JsonProperty("destination_postal_code")
  private String codigoPostalDestino;

  @JsonProperty("postal_code_sender")
  private String codigoPostalOrigin;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonProperty("route_id")
  private String codigoRuta;

  @JsonProperty("application")
  private String aplicacion;

  @JsonProperty("generator_point")
  private String puntoGenerador;

  @JsonProperty("user_id")
  private String idUsuario;

  @JsonProperty("creation_date")
  private String fechaCreacion;

  @JsonProperty("schema_version")
  private String schemaVersion;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonProperty("child_hierarchy")
  private List<AgrupacionDTO> jerarquiaHijos;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonProperty("parent_hierarchy")
  private List<AgrupacionDTO> jerarquiaPadres;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @Default
  @JsonProperty("events")
  private List<AgrupacionEventoDTO> eventos = new ArrayList<>();

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @Default
  @JsonProperty("contained_groups_with_notices")
  private List<ContenidoAgrupacionesConAvisoDTO> contenidoAgrupacionesConAviso = new ArrayList<>();
}
