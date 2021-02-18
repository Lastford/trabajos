package es.correos.soporte.minerva.envios.repository;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;

import es.correos.soporte.minerva.envios.domain.Envio;
import es.correos.soporte.minerva.envios.domain.EnvioXsl;
import es.correos.soporte.minerva.envios.domain.custom.EnvioCSV;
import es.correos.soporte.minerva.envios.domain.custom.EnvioClienteUnion;
import es.correos.soporte.minerva.envios.domain.custom.EnvioContrato;
import es.correos.soporte.minerva.envios.domain.custom.EnvioListaClienteUnion;
import es.correos.soporte.minerva.envios.domain.custom.EnvioListaContrato;
import es.correos.soporte.minerva.envios.filter.EnvioFilter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.extern.log4j.Log4j2;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;

@Log4j2
public class EnvioRepositoryCustomImpl implements EnvioRepositoryCustom {

  private final MongoTemplate mongoTemplate;
  private static final String STARTWITHPADRES = "$_id";
  private static final String CONNECTFROMPADRES = "_id";
  private static final String CONNECTTOPADRES = "DETAILS.etiquetadohijo";
  private static final String ASPADRES = "reetiquetado_padre";
  private static final String DEPTHFIELDS = "depth";
  private static final String STARTWITHHIJOS = "$DETAILS.etiquetadohijo";
  private static final String CONNECTFROMHIJOS = "DETAILS.etiquetadohijo";
  private static final String CONNECTTOHIJOS = "_id";
  private static final String ASHIJOS = "reetiquetado_hijo";

  @Autowired
  public EnvioRepositoryCustomImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  private Direction getDirection(EnvioFilter filtro) {
    Direction direccion = Sort.Direction.DESC;
    if (filtro.getOrder().getNombre().equalsIgnoreCase("ASC")) {
      direccion = Sort.Direction.ASC;
    }
    return direccion;
  }

  private Sort getSort(EnvioFilter filtro) {
    Direction direccion = getDirection(filtro);
    Sort sort = new Sort(direccion, "fechaultimoevento");
    if (filtro.getSort() != null) {
      sort = new Sort(direccion, filtro.getSort());
    }
    return sort;
  }

  private Pageable getPageable(Query query, EnvioFilter filtro) {
    Sort sort = getSort(filtro);
    Pageable pageable = PageRequest.of(filtro.getOffset(), filtro.getSize(), sort);
    query.with(sort);
    query.with(pageable);

    return pageable;
  }

  @Override
  public Page<Envio> busquedaParametros(EnvioFilter filtro) {

    Query query = new Query();

    // OPTIDIS
    if (filtro.getTipoMensaje() != null) {
      query.addCriteria(Criteria.where("tipoMensaje").is(filtro.getTipoMensaje()));
      if (filtro.getFechaInicial() != null && filtro.getFechaFinal() != null) {
        // DOC_ENV.fecha_comprometidaentregasuministrada
        // DOC_ENV.fechacomprometidaentregacalculada
        query.addCriteria(
            Criteria.where("fechaultimoevento")
                .gte(filtro.getFechaInicial())
                .lte(filtro.getFechaFinal())
                .orOperator(
                    Criteria.where("fechaultimoevento")
                        .gte(filtro.getFechaInicial())
                        .lte(filtro.getFechaFinal())));
        filtro.setFechaInicial(null);
        filtro.setFechaFinal(null);
      }
    }

    setFiltros(filtro, query);

    Pageable pageable = getPageable(query, filtro);

    long count = mongoTemplate.count(query, Envio.class);

    List<Envio> lista = mongoTemplate.find(query, Envio.class);
    return PageableExecutionUtils.getPage(lista, pageable, () -> count);
  }

  @Override
  public Page<Envio> busquedaParametrosSCP(EnvioFilter filtro) {

    Query query = new Query();
    setFiltrosSCP(filtro, query);

    Pageable pageable = getPageable(query, filtro);

    long count = mongoTemplate.count(query, Envio.class);

    List<Envio> lista = mongoTemplate.find(query, Envio.class);
    return PageableExecutionUtils.getPage(lista, pageable, () -> count);
  }

  private void setFiltros(EnvioFilter filtro, Query query) {
    setFiltroId(filtro, query);
    setFiltroFechaAdmision(filtro, query);
    setFiltroDespacho(filtro, query);
    setFiltroRemesa(filtro, query);
    setFiltroCodired(filtro, query);
    setFiltroEstado(filtro, query);
    setFiltroEventoUnico(filtro, query);
    setFiltroCodigoUnico(filtro, query);
    setFiltroNodoReportador(filtro, query);
    setFiltroCodigoPregrabado(filtro, query);
    setFiltroCodigoExpedicion(filtro, query);
    setFIltroProductos(filtro, query);
    setFiltroReferencia(filtro, query);
    setFiltroAmbito(filtro, query);
    setFiltroFamilia(filtro, query);
    setFiltroSubFamilia(filtro, query);
    setFiltroClientesUnion(filtro, query);
  }

  private void setFiltrosSCP(EnvioFilter filtro, Query query) {
    setFiltroId(filtro, query);
    setFiltroFechaAdmision(filtro, query);
    setFiltroCircuitoAduana(filtro, query);
    setFiltroCliente(filtro, query);
    setFiltroCodMoneda(filtro, query);
    setFiltroCodPaisDestino(filtro, query);
    setFiltroCodPaisOrigen(filtro, query);
    setFiltroCodTipoMensaje(filtro, query);
    setFiltroCodigoPostal(filtro, query);
    setFiltroDespacho(filtro, query);
    setFiltroErrorPrerregistro(filtro, query);
    setFiltroFechaRegistro(filtro, query);
    setFiltroFechaValidez(filtro, query);
    setFiltroModalidadEntrega(filtro, query);
    setFiltroModo(filtro, query);
    setFiltroProducto(filtro, query);
    setFiltroRecipiente(filtro, query);
    setFiltroRetenidoAduanas(filtro, query);
    setFiltroValorDeclarado(filtro, query);
    setFiltroReferencia(filtro, query);
  }

  private void setFiltroValorDeclarado(EnvioFilter filtro, Query query) {
    // TODO Auto-generated method stub

  }

  private void setFiltroRetenidoAduanas(EnvioFilter filtro, Query query) {
    // TODO Auto-generated method stub

  }

  private void setFiltroRecipiente(EnvioFilter filtro, Query query) {
    if (filtro.getRecipiente() != null && !filtro.getRecipiente().isEmpty()) {
      query.addCriteria(Criteria.where("DETAILS.codrecipiente").is(filtro.getRecipiente()));
    }
  }

  private void setFiltroProducto(EnvioFilter filtro, Query query) {
    if (filtro.getProducto() != null && !filtro.getProducto().isEmpty()) {
      query.addCriteria(Criteria.where("PANEL.P").is(filtro.getProducto()));
    }
  }

  private void setFiltroModo(EnvioFilter filtro, Query query) {
    // TODO Auto-generated method stub

  }

  private void setFiltroModalidadEntrega(EnvioFilter filtro, Query query) {
    // TODO Auto-generated method stub

  }

  private void setFiltroFechaValidez(EnvioFilter filtro, Query query) {
    // TODO Auto-generated method stub

  }

  private void setFiltroFechaRegistro(EnvioFilter filtro, Query query) {
    // TODO Auto-generated method stub

  }

  private void setFiltroErrorPrerregistro(EnvioFilter filtro, Query query) {
    // TODO Auto-generated method stub

  }

  private void setFiltroCodigoPostal(EnvioFilter filtro, Query query) {
    if (filtro.getCp() != null && !filtro.getCp().isEmpty()) {
      query.addCriteria(Criteria.where("cpdestino").is(filtro.getCp()));
    }
  }

  private void setFiltroCodTipoMensaje(EnvioFilter filtro, Query query) {
    if (filtro.getCodTipoMensaje() != null && !filtro.getCodTipoMensaje().isEmpty()) {
      query.addCriteria(Criteria.where("tipoMensaje").is(filtro.getCodTipoMensaje()));
    }
  }

  private void setFiltroCodPaisOrigen(EnvioFilter filtro, Query query) {
    if (filtro.getCodPaisOrigen() != null && !filtro.getCodPaisOrigen().isEmpty()) {
      query.addCriteria(Criteria.where("codpaisorigen").is(filtro.getCodPaisOrigen()));
    }
  }

  private void setFiltroCodPaisDestino(EnvioFilter filtro, Query query) {
    if (filtro.getCodPaisDestino() != null && !filtro.getCodPaisDestino().isEmpty()) {
      query.addCriteria(Criteria.where("codpaisdestino").is(filtro.getCodPaisDestino()));
    }
  }

  private void setFiltroCodMoneda(EnvioFilter filtro, Query query) {
    if (filtro.getCodMoneda() != null && !filtro.getCodMoneda().isEmpty()) {
      query.addCriteria(Criteria.where("DETAILS.coddivisa").is(filtro.getCodMoneda()));
    }
  }

  private void setFiltroCliente(EnvioFilter filtro, Query query) {
    if (filtro.getCliente() != null && !filtro.getCliente().isEmpty()) {
      query.addCriteria(Criteria.where("DOC_ENV.idcliente").is(filtro.getCliente()));
    }
  }

  private void setFiltroCircuitoAduana(EnvioFilter filtro, Query query) {
    // TODO Auto-generated method stub

  }

  private void setFiltroId(EnvioFilter filtro, Query query) {
    if (filtro.getId() != null && !filtro.getId().isEmpty()) {
      query.addCriteria(Criteria.where("_id").in(filtro.getId()));
    }
  }

  private void setFiltroReferencia(EnvioFilter filtro, Query query) {
    if (filtro.getReferencia() != null && !filtro.getReferencia().isEmpty()) {
      query.addCriteria(
          new Criteria()
              .orOperator(
                  Criteria.where("DETAILS.referencia1").is(filtro.getReferencia().get(0)),
                  Criteria.where("DETAILS.referencia2").is(filtro.getReferencia().get(0)),
                  Criteria.where("DETAILS.referencia3").is(filtro.getReferencia().get(0))));
    }
  }

  private void setFiltroFamilia(EnvioFilter filtro, Query query) {
    if (filtro.getFamilia() != null && !filtro.getFamilia().isEmpty()) {
      query.addCriteria(
          Criteria.where("PANEL.FAMILIA")
              .regex("^(" + String.join("|", filtro.getFamilia()) + ")$", "i"));
    }
  }

  private void setFiltroSubFamilia(EnvioFilter filtro, Query query) {
    if (filtro.getSubFamilia() != null && !filtro.getSubFamilia().isEmpty()) {
      query.addCriteria(
          Criteria.where("PANEL.SUBFAMILIA")
              .regex("^(" + String.join("|", filtro.getSubFamilia()) + ")$", "i"));
    }
  }

  private void setFiltroCodigoExpedicion(EnvioFilter filtro, Query query) {
    if (filtro.getCodigoExpedicion() != null) {
      query.addCriteria(Criteria.where("codexpedicion").is(filtro.getCodigoExpedicion()));
    }
  }

  private void setFiltroCodigoPregrabado(EnvioFilter filtro, Query query) {
    if (filtro.getCodigoPregrabado() != null) {
      query.addCriteria(Criteria.where("cod_pregrabado").is(filtro.getCodigoPregrabado()));
    }
  }

  private void setFiltroAmbito(EnvioFilter filtro, Query query) {
    if (filtro.getAmbito() != null) {
      query.addCriteria(Criteria.where("ambito").is(filtro.getAmbito()));
    }
  }

  private void setFiltroNodoReportador(EnvioFilter filtro, Query query) {
    if (filtro.getNodoReportador() != null) {
      query.addCriteria(
          Criteria.where("DETAILS.estado.descnodoreportador").is(filtro.getNodoReportador()));
    }
  }

  private void setFiltroCodigoUnico(EnvioFilter filtro, Query query) {
    if (filtro.getCodigoUnico() != null && !filtro.getCodigoUnico().isEmpty()) {
      query.addCriteria(Criteria.where("DETAILS.estado.COD_UNICO").in(filtro.getCodigoUnico()));
    }
  }

  private void setFiltroEventoUnico(EnvioFilter filtro, Query query) {
    if (filtro.getEventoUnico() != null && !filtro.getEventoUnico().isEmpty()) {
      query.addCriteria(Criteria.where("COD_EVENTO_UNICO").in(filtro.getEventoUnico()));
    }
  }

  private void setFiltroEstado(EnvioFilter filtro, Query query) {
    if (filtro.getCodigoEstado() != null && !filtro.getCodigoEstado().isEmpty()) {
      query.addCriteria(Criteria.where("codevento").in(filtro.getCodigoEstado()));
    }
  }

  private void setFiltroCodired(EnvioFilter filtro, Query query) {
    if (filtro.getCodired() != null) {
      query.addCriteria(Criteria.where("codired").is(filtro.getCodired()));
    }
  }

  private void setFiltroRemesa(EnvioFilter filtro, Query query) {
    if (filtro.getRemesa() != null) {
      query.addCriteria(Criteria.where("DETAILS.remesa").is(filtro.getRemesa()));
    }
  }

  private void setFiltroDespacho(EnvioFilter filtro, Query query) {
    if (filtro.getIdDespachoPostal() != null) {
      query.addCriteria(
          Criteria.where("DETAILS.iddespachopostal").is(filtro.getIdDespachoPostal()));
    }
  }

  private void setFiltroFechaAdmision(EnvioFilter filtro, Query query) {
    if (filtro.getFechaInicial() != null && filtro.getFechaFinal() != null) {
      query.addCriteria(
          Criteria.where("fechaultimoevento")
              .gte(filtro.getFechaInicial())
              .lte(filtro.getFechaFinal()));
    }
  }

  private void setFIltroProductos(EnvioFilter filtro, Query query) {
    if (filtro.getProductIds() != null && !filtro.getProductIds().isEmpty()) {
      query.addCriteria(Criteria.where("PANEL.P").in(filtro.getProductIds()));
    }
  }

  private void setFiltroClientesUnion(EnvioFilter filtro, Query query) {
    if (filtro.getClienteUnion() != null && !filtro.getClienteUnion().isEmpty()) {
      query.addCriteria(Criteria.where("PANEL.ClienteUnion").in(filtro.getClienteUnion()));
    }
  }

  @Override
  public List<EnvioXsl> busquedaParametrosCSV(EnvioFilter filtro) {
	    log.info("+busquedaParametrosCSV+");
	    Query query = new Query();

	    if (filtro.getTipoMensaje() != null) {
	      query.addCriteria(Criteria.where("tipoMensaje").is(filtro.getTipoMensaje()));
	      if (filtro.getFechaInicial() != null && filtro.getFechaFinal() != null) {
	        // DOC_ENV.fecha_comprometidaentregasuministrada
	        // DOC_ENV.fechacomprometidaentregacalculada
	        query.addCriteria(
	            Criteria.where("fechaultimoevento")
	                .gte(filtro.getFechaInicial())
	                .lte(filtro.getFechaFinal())
	                .orOperator(
	                    Criteria.where("fechaultimoevento")
	                        .gte(filtro.getFechaInicial())
	                        .lte(filtro.getFechaFinal())));
	        filtro.setFechaInicial(null);
	        filtro.setFechaFinal(null);
	      }
	    }

	    setFiltros(filtro, query);

	    // Sort sort = getSort(filtro);

	    // query.with(sort);

	    log.info("\tejecutando query...");
	    
	    return mongoTemplate.find(query.limit(100000), EnvioXsl.class);
	  }

  @Override
  public List<Envio> busquedaParametrosCSVSCP(EnvioFilter filtro) {
    log.info("+busquedaParametrosCSV+");
    Query query = new Query();

    setFiltrosSCP(filtro, query);
    log.info("\tejecutando query...");
    return mongoTemplate.find(query.limit(50000), Envio.class);
  }

  private Envio getEnvioByAggregation(List<AggregationOperation> lisAggregationOperation) {
    Aggregation aggregation = Aggregation.newAggregation(lisAggregationOperation);

    AggregationResults<Envio> result =
        mongoTemplate.aggregate(
            aggregation, mongoTemplate.getCollectionName(Envio.class), Envio.class);

    return result.getUniqueMappedResult();
  }

  private AggregationOperation getGraphLookupPadres(
      String from, Number maxDepth, String depthField, Document restrictSearchWithMatch) {
    return context -> {
      Document graphLookup = new Document();
      graphLookup.put("from", from);
      graphLookup.put("startWith", STARTWITHPADRES);
      graphLookup.put("connectFromField", CONNECTFROMPADRES);
      graphLookup.put("connectToField", CONNECTTOPADRES);
      graphLookup.put("as", ASPADRES);
      if (maxDepth != null) {
        graphLookup.put("maxDepth", maxDepth);
      }

      if (depthField != null) {
        graphLookup.put("depthField", depthField);
      }

      if (restrictSearchWithMatch != null) {
        graphLookup.put(
            "restrictSearchWithMatch", context.getMappedObject(restrictSearchWithMatch));
      }

      return new Document("$graphLookup", graphLookup);
    };
  }

  private AggregationOperation getGraphLookupHijos(
      String from, Number maxDepth, String depthField, Document restrictSearchWithMatch) {
    return context -> {
      Document graphLookup = new Document();
      graphLookup.put("from", from);
      graphLookup.put("startWith", STARTWITHHIJOS);
      graphLookup.put("connectFromField", CONNECTFROMHIJOS);
      graphLookup.put("connectToField", CONNECTTOHIJOS);
      graphLookup.put("as", ASHIJOS);
      if (maxDepth != null) {
        graphLookup.put("maxDepth", maxDepth);
      }

      if (depthField != null) {
        graphLookup.put("depthField", depthField);
      }

      if (restrictSearchWithMatch != null) {

        graphLookup.put(
            "restrictSearchWithMatch", context.getMappedObject(restrictSearchWithMatch));
      }
      return new Document("$graphLookup", graphLookup);
    };
  }

  private AggregationOperation getAggregationById(String id) {
    return context -> {
      Document match = new Document();
      match.put("_id", id);
      return new Document("$match", match);
    };
  }

  @Override
  public Envio getEnviosPadresHijos(String id) {
    List<AggregationOperation> lisAggregationOperation = new ArrayList<>();

    String from = mongoTemplate.getCollectionName(Envio.class);
    Number maxDepth = null;
    Document restrictSearchWithMatch = null;
    lisAggregationOperation.add(getAggregationById(id));
    lisAggregationOperation.add(
        getGraphLookupPadres(from, maxDepth, DEPTHFIELDS, restrictSearchWithMatch));
    lisAggregationOperation.add(
        getGraphLookupHijos(from, maxDepth, DEPTHFIELDS, restrictSearchWithMatch));

    return getEnvioByAggregation(lisAggregationOperation);
  }

  private Bson getMatchOperationEnviosContratosAgrupadosMatchNumContratos(
      String numContratoBuscado, List<String> clientesUnionSeleccionados) {

    if (numContratoBuscado != null) {
      List<Bson> filtros = new ArrayList<>();

      Bson filtroClienteUnion =
          Filters.regex(
              "DETAILS.remitente.numcontrato",
              Pattern.compile(String.format("^%s", numContratoBuscado)));

      if (!clientesUnionSeleccionados.isEmpty()) {
        filtros.add(filtroClienteUnion);
        filtros.add(Filters.in("PANEL.ClienteUnion", clientesUnionSeleccionados));
        return Filters.and(filtros);
      }

      return filtroClienteUnion;
    }

    return Filters.regex("DETAILS.remitente.numcontrato", Pattern.compile("^\\S"));
  }

  private Bson getMatchOperationEnviosContratosAgrupadosMatchClientesUnion(
      String clienteUnionBuscado, List<String> clientesUnionSeleccionados) {

    if (clienteUnionBuscado != null) {
      List<Bson> filtros = new ArrayList<>();

      Bson filtroClienteUnion =
          Filters.regex(
              "PANEL.ClienteUnion", Pattern.compile(String.format("^%s", clienteUnionBuscado)));

      if (!clientesUnionSeleccionados.isEmpty()) {
        filtros.add(filtroClienteUnion);
        filtros.add(Filters.in("PANEL.ClienteUnion", clientesUnionSeleccionados));
        return Filters.and(filtros);
      }

      return filtroClienteUnion;
    }

   return Filters.regex("PANEL.ClienteUnion", Pattern.compile("^\\S"));
  }

  @SuppressWarnings("unchecked")
  @Override
  public EnvioListaContrato getEnviosAgrupadosPorNumContrato(
      String numContratoBuscado,
      List<String> clientesUnionSeleccionados,
      Integer offset,
      Integer size) {

    MongoCollection<Document> collection =
        mongoTemplate.getCollection(mongoTemplate.getCollectionName(Envio.class));

    Bson filtros =
        Aggregates.match(
            getMatchOperationEnviosContratosAgrupadosMatchNumContratos(
                numContratoBuscado, clientesUnionSeleccionados));

    Bson agrupacionCampos =
        Aggregates.group(
            "$DETAILS.remitente.numcontrato",
            Accumulators.first("descripcion", "$DETAILS.remitente.numcontrato"),
            Accumulators.addToSet("clientesUnion", "$PANEL.ClienteUnion"));

    Bson ordenacion = Aggregates.sort(Sorts.ascending("id"));
    
    Bson agrupacionResultados =
        Aggregates.group(
            null,
            Accumulators.push("contratos", Aggregation.ROOT),
            Accumulators.sum("totalRegistros", 1));

    Bson proyeccionResultados =
        Aggregates.project(
            Projections.fields(
                Projections.include("totalRegistros"),
                Projections.computed(
                    "contratos",
                    Filters.eq(
                        "$slice", new ArrayList<>(Arrays.asList("$contratos", offset, size))))));

    AggregateIterable<Document> resultado =
        collection
            .aggregate(
                new ArrayList<>(
                    Arrays.asList(
                        filtros, agrupacionCampos, ordenacion, agrupacionResultados, proyeccionResultados)))
            .batchSize(size);

    EnvioListaContrato envioListaContrato = new EnvioListaContrato("", new ArrayList<>(), 0L);

    List<EnvioContrato> enviosContratos = envioListaContrato.getContratos();

    List<Document> documentosRoot =
        StreamSupport.stream(resultado.spliterator(), false).collect(Collectors.toList());

    if (!documentosRoot.isEmpty()) {
      List<Document> documentos = documentosRoot.get(0).get("contratos", List.class);
      documentos.forEach(
          documento -> {
            EnvioContrato envioContrato = new EnvioContrato();
            envioContrato.setId(documento.getString("_id"));
            envioContrato.setDescripcion(documento.getString("descripcion"));
            envioContrato.setClientesUnion(documento.get("clientesUnion", List.class));
            enviosContratos.add(envioContrato);
          });
      envioListaContrato.setTotalRegistros(
          documentosRoot.get(0).getInteger("totalRegistros").longValue());
    }

    return envioListaContrato;
  }

  @SuppressWarnings("unchecked")
  @Override
  public EnvioListaClienteUnion getEnviosAgrupadosPorClienteUnion(
      String clienteUnionBuscado,
      List<String> clientesUnionSeleccionados,
      Integer offset,
      Integer size) {

    MongoCollection<Document> collection =
        mongoTemplate.getCollection(mongoTemplate.getCollectionName(Envio.class));

    Bson filtros =
        Aggregates.match(
            getMatchOperationEnviosContratosAgrupadosMatchClientesUnion(
                clienteUnionBuscado, clientesUnionSeleccionados));

    Bson agrupacionCampos =
        Aggregates.group(
            "$PANEL.ClienteUnion", Accumulators.first("descripcion", "$PANEL.ClienteUnion"));

    Bson ordenacion = Aggregates.sort(Sorts.ascending("id"));
    
    Bson agrupacionResultados =
        Aggregates.group(
            null,
            Accumulators.push("clientesUnion", Aggregation.ROOT),
            Accumulators.sum("totalRegistros", 1));

    Bson proyeccionResultados =
        Aggregates.project(
            Projections.fields(
                Projections.include("totalRegistros"),
                Projections.computed(
                    "clientesUnion",
                    Filters.eq(
                        "$slice",
                        new ArrayList<>(Arrays.asList("$clientesUnion", offset, size))))));

    AggregateIterable<Document> resultado =
        collection
            .aggregate(
                new ArrayList<>(
                    Arrays.asList(
                        filtros, agrupacionCampos, ordenacion, agrupacionResultados, proyeccionResultados)))
            .batchSize(size);

    EnvioListaClienteUnion envioListaClienteUnion =
        new EnvioListaClienteUnion("", new ArrayList<>(), 0L);

    List<EnvioClienteUnion> enviosClientesUnion = envioListaClienteUnion.getClientesUnion();

    List<Document> documentosRoot =
        StreamSupport.stream(resultado.spliterator(), false).collect(Collectors.toList());

    if (!documentosRoot.isEmpty()) {
      List<Document> documentos = documentosRoot.get(0).get("clientesUnion", List.class);
      documentos.forEach(
          documento -> {
            EnvioClienteUnion envioClienteUnion = new EnvioClienteUnion();
            envioClienteUnion.setId(documento.getString("_id"));
            envioClienteUnion.setDescripcion(documento.getString("descripcion"));
            enviosClientesUnion.add(envioClienteUnion);
          });
      envioListaClienteUnion.setTotalRegistros(
          documentosRoot.get(0).getInteger("totalRegistros").longValue());
    }

    return envioListaClienteUnion;
  }
  

    

}
