package es.correos.soporte.minerva.envios.mapper;

import es.correos.soporte.minerva.envios.dto.EnvioEventoDTO;
import es.correos.soporte.minerva.envios.dto.feign.eventos.EventoDTO;
import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface EventoMapper {
  @Mapping(source = "idAgrupacion", target = "idGrupo", defaultValue = "")
  @Mapping(source = "desEvento", target = "descripcionCorta", defaultValue = "")
  @Mapping(source = "resumenExtendido", target = "descripcionLarga", defaultValue = "")
  @Mapping(source = "fechaHora", target = "fecha", defaultValue = "")
  @Mapping(source = "ubicacion", target = "localidad", defaultValue = "")
  @Mapping(source = "codired", target = "codired", defaultValue = "")
  @Mapping(source = "codPostalOrigen", target = "codPostalOrigen", defaultValue = "")
  @Mapping(source = "codPostalDestino", target = "codPostalDestino", defaultValue = "")
  @Mapping(source = "idAgrupacion", target = "agrupacion", defaultValue = "")
  @Mapping(source = "codired", target = "unidad", defaultValue = "")
  @Mapping(source = "coordenada.x", target = "coordenadaX", defaultValue = "")
  @Mapping(source = "coordenada.y", target = "coordenadaY", defaultValue = "")
  @Mapping(source = "fase", target = "fase", defaultValue = "")
  @Mapping(source = "color", target = "color", defaultValue = "")
  @Mapping(source = "id", target = "idEnvio", defaultValue = "")
  @Mapping(source = "codEventoUnico", target = "codEventoUnico", defaultValue = "")
  EnvioEventoDTO eventoDtoToEnvioEventoDTO(EventoDTO eventoDTO);

  @IterableMapping(elementTargetType = EnvioEventoDTO.class)
  List<EnvioEventoDTO> eventoDtoToEnvioEventoDTO(List<EventoDTO> eventoDTO);
}
