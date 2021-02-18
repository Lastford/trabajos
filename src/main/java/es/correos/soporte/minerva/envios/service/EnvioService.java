package es.correos.soporte.minerva.envios.service;

import es.correos.arch.boot.feign.exceptions.FeignCommunicationException;
import es.correos.arq.ex.CorreosBusinessException;
import es.correos.soporte.minerva.envios.dto.AdicionesResponseDTO;
import es.correos.soporte.minerva.envios.dto.EnvioDTO;
import es.correos.soporte.minerva.envios.dto.EnvioImagenDTO;
import es.correos.soporte.minerva.envios.dto.EnvioListaCustomClientCodEtiqFilterDTO;
import es.correos.soporte.minerva.envios.dto.EnvioListaDTO;
import es.correos.soporte.minerva.envios.dto.EnvioSCPListaDTO;
import es.correos.soporte.minerva.envios.exceptions.EnvioNotFoundExc;
import es.correos.soporte.minerva.envios.filter.EnvioCustomFilter;
import es.correos.soporte.minerva.envios.filter.EnvioFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import org.springframework.util.MultiValueMap;

public interface EnvioService {

  EnvioDTO getEnvioById(String id) throws EnvioNotFoundExc, FeignCommunicationException;

  EnvioListaDTO getEnvios(EnvioFilter filtro) throws FeignCommunicationException;

  //  String getEnviosCsv(EnvioFilter filtro) throws FeignCommunicationException;

  void createCsvFile(EnvioFilter filtro, final OutputStream outputStream)
      throws FeignCommunicationException;

  AdicionesResponseDTO altaAdicion(String documento, String fichero, String modalidadEntrega)
      throws IOException;

  EnvioImagenDTO getEnvioImagen(String cio, String codeImage) throws CorreosBusinessException;

  List<EnvioImagenDTO> getEnvioImagenes(String cio) throws CorreosBusinessException;

  EnvioSCPListaDTO getEnviosSCP(EnvioFilter envioFilter) throws FeignCommunicationException;

  String getEnviosCsvSCP(EnvioFilter filtro);

  EnvioListaCustomClientCodEtiqFilterDTO getEnviosCustomFilters(
      EnvioCustomFilter campoBuscar,
      MultiValueMap<String, String> filtros,
      Integer offset,
      Integer size);
  
  
}
