package es.correos.soporte.minerva.envios.repository;

import es.correos.soporte.minerva.envios.domain.Envio;
import es.correos.soporte.minerva.envios.domain.EnvioXsl;
import es.correos.soporte.minerva.envios.domain.custom.EnvioCSV;
import es.correos.soporte.minerva.envios.domain.custom.EnvioListaClienteUnion;
import es.correos.soporte.minerva.envios.domain.custom.EnvioListaContrato;
import es.correos.soporte.minerva.envios.filter.EnvioFilter;
import java.util.List;
import org.springframework.data.domain.Page;

public interface EnvioRepositoryCustom {

  Page<Envio> busquedaParametros(EnvioFilter filtro);

  List<EnvioXsl> busquedaParametrosCSV(EnvioFilter filtro);
  
  

  Page<Envio> busquedaParametrosSCP(EnvioFilter filtro);

  List<Envio> busquedaParametrosCSVSCP(EnvioFilter filtro);

  Envio getEnviosPadresHijos(String id);

  EnvioListaContrato getEnviosAgrupadosPorNumContrato(
      String numContratoBuscado,
      List<String> clientesUnionSeleccionados,
      Integer offset,
      Integer size);

  EnvioListaClienteUnion getEnviosAgrupadosPorClienteUnion(
      String clienteUnionBuscado,
      List<String> clientesUnionSeleccionados,
      Integer offset,
      Integer size);
}
