package es.correos.soporte.minerva.envios.helpers;

import es.correos.soporte.minerva.envios.domain.Envio;
import es.correos.soporte.minerva.envios.domain.Panel;
import es.correos.soporte.minerva.envios.dto.EnvioDTO;
import es.correos.soporte.minerva.envios.dto.EnvioEventoDTO;
import es.correos.soporte.minerva.envios.dto.EnvioFileDTO;
import es.correos.soporte.minerva.envios.dto.EnvioSCPDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class EnvioServiceHelper {

  public static EnvioDTO crearShippingTypeDTO() {

    EnvioDTO objshippingDTO = new EnvioDTO();
    objshippingDTO.setId("cio");

    return objshippingDTO;
  }

  public static EnvioFileDTO crearFileDTO() {

    EnvioFileDTO objFileDTO = new EnvioFileDTO();
    objFileDTO.setFile("lista");
    objFileDTO.setFormatovisual("csv");

    return objFileDTO;
  }

  public static Envio crearEnvio() {
    Envio envio = new Envio();
    envio.setFechaRegistro("2020-01-01T08:30:00+02:00");
    envio.setFechaUltimoEvento(new Date());
    envio.setId("UX6PZG0467032490119161W");
    envio.setCodired("123");
    envio.setPanel(new Panel());
    envio.setReetiquetadoHijo(
        new ArrayList<>(Arrays.asList(Envio.builder().id("UX6PZG0467032490119161X").build())));
    envio.setReetiquetadoPadre(
        new ArrayList<>(Arrays.asList(Envio.builder().id("UX6PZG0467032490119161Z").build())));
    return envio;
  }

  public static EnvioDTO crearEnvioDTO() {
    EnvioDTO envio = new EnvioDTO();
    envio.setId("UX6PZG0467032490119161W");
    envio.setCodired("123");
    envio.setCodProducto("PQ");
    envio.setEventos(
        new ArrayList<>(Arrays.asList(EnvioEventoDTO.builder().codired("123").build())));
    envio.setIdEnvioAsociado("UX6PZG0467032490119161X");
    envio.setReetiquetadoPadre(
        new ArrayList<>(Arrays.asList(EnvioDTO.builder().id("UX6PZG0467032490119161Z").build())));
    envio.setReetiquetadoHijo(
        new ArrayList<>(Arrays.asList(EnvioDTO.builder().id("UX6PZG0467032490119161X").build())));
    return envio;
  }

  public static List<Envio> crearListaEnvio() {
    List<Envio> envios = new ArrayList<>(Arrays.asList(crearEnvio()));
    return envios;
  }

  public static List<EnvioDTO> crearListaEnvioDTO() {
    List<EnvioDTO> envios = new ArrayList<>(Arrays.asList(crearEnvioDTO()));
    return envios;
  }

  public static EnvioSCPDTO crearEnvioSCPDTO() {
    EnvioSCPDTO envioSCPDTO = new EnvioSCPDTO();
    envioSCPDTO.setId("UX6PZG0467032490119161W");
    return envioSCPDTO;
  }

  public static List<EnvioSCPDTO> crearListaEnvioSCPDTO() {
    List<EnvioSCPDTO> listaEnviosSCPDTO = new ArrayList<>(Arrays.asList(crearEnvioSCPDTO()));
    return listaEnviosSCPDTO;
  }
}
