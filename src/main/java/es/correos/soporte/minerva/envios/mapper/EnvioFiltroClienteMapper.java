package es.correos.soporte.minerva.envios.mapper;

import es.correos.soporte.minerva.envios.domain.custom.EnvioClienteUnion;
import es.correos.soporte.minerva.envios.domain.custom.EnvioContrato;
import es.correos.soporte.minerva.envios.domain.custom.EnvioListaClienteUnion;
import es.correos.soporte.minerva.envios.domain.custom.EnvioListaContrato;
import es.correos.soporte.minerva.envios.dto.EnvioCustomClientCodEtiqFilterDTO;
import es.correos.soporte.minerva.envios.dto.EnvioListaCustomClientCodEtiqFilterDTO;
import java.util.ArrayList;
import java.util.List;
import org.mapstruct.AfterMapping;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EnvioFiltroClienteMapper {

  @Mapping(target = "codigo", ignore = true)
  EnvioCustomClientCodEtiqFilterDTO envioContratoToEnvioCustomClientCodEtiqFilterDto(
      EnvioContrato envioContrato);

  @IterableMapping(elementTargetType = EnvioCustomClientCodEtiqFilterDTO.class)
  List<EnvioCustomClientCodEtiqFilterDTO> envioContratoToEnvioCustomClientCodEtiqFilterDto(
      List<EnvioContrato> enviosContratos);

  @Mapping(source = "contratos", target = "registros")
  EnvioListaCustomClientCodEtiqFilterDTO envioListaContratoToEnvioListaCustomClientCodEtiqFilterDto(
      EnvioListaContrato enviosContratos);

  @Mapping(source = "id", target = "codigo")
  EnvioCustomClientCodEtiqFilterDTO envioClienteUnionToEnvioCustomClientCodEtiqFilterDto(
      EnvioClienteUnion envioClienteUnion);

  @IterableMapping(elementTargetType = EnvioCustomClientCodEtiqFilterDTO.class)
  List<EnvioCustomClientCodEtiqFilterDTO> envioClienteUnionToEnvioCustomClientCodEtiqFilterDto(
      List<EnvioClienteUnion> enviosClientesUnion);

  @Mapping(source = "clientesUnion", target = "registros")
  EnvioListaCustomClientCodEtiqFilterDTO
      envioListaClienteUnionToEnvioListaCustomClientCodEtiqFilterDto(
          EnvioListaClienteUnion enviosClientesUnion);

  @AfterMapping
  default void setCodeFromNumContratoWithClientesUnion(
      EnvioContrato envioContrato,
      @MappingTarget EnvioCustomClientCodEtiqFilterDTO envioCustomClientCodEtiqFilterDTO) {
    List<String> codigoFinal = new ArrayList<>();
    codigoFinal.add(envioContrato.getId());
    if (envioContrato.getClientesUnion() != null) {
      envioContrato.getClientesUnion().forEach(clienteUnion -> codigoFinal.add(clienteUnion));
    }
    envioCustomClientCodEtiqFilterDTO.setCodigo(String.join("#", codigoFinal));
  }
}
