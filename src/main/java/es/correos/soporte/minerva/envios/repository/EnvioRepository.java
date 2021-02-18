package es.correos.soporte.minerva.envios.repository;

import es.correos.soporte.minerva.envios.domain.Envio;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnvioRepository extends MongoRepository<Envio, String>, EnvioRepositoryCustom {}
