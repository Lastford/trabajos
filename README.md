Aplicación ejemplo (Arquetipo) para proyectos realizados con:
* Spring boot 2.1.x
* Spring data JPA

**Tecnologías** que se incluyen para ser utilizadas dentro del proyecto y facilitar los desarrollos:
* [Lombok](https://projectlombok.org/)
  Librería para reducir el código que se incluye en los proyectos mediante el uso de anotaciones. El código se autogenera en tiempo de compilación y así se facilita la legibilidad y mantenimiento del código.

  Algunas de las **anotaciones** mas usadas serán:
  
  @Data: que genera metodos get y set para todos los campos de la clase, así como los métodos toString, hashCode y equals.
  
  @Getter: sólo genera métodos get
  
  @Setter: sólo genera métodos set
  
  @NoArgsConstructor: genera el constructor por defecto
  
  @AllArgsConstructor: genera un constructor con todos los campos de la clase en el mismo orden que están declarados.
  
  @RequiredArgsConstructor: genera un constructor para los campos de la clase que son obligatorios (declarados como final, o @NonNull), y en el mismo orden que están declarados.
  
  @Log4j2: que incluye en la clase una variable con el nombre "log" inicializada para utilizar log4j2.
  
  Se pueden consultar todas las anotaciones en la página oficial del proyecto.

* [Mapstruct](http://mapstruct.org/)
  Solución para el mapeo entre entity/Dtos y viceversa. De las soluciones analizadas se ha encontrado como la solución mas eficiente.


* [Swagger](https://swagger.io/)
  Permite documentar los servicios expuestos por el proyecto (API) y probarlos de una manera sencilla mediante anotaciones en las clases.


* [JaCoCo](https://www.eclemma.org/jacoco/)
  Librería para controlar la cobertura del código desarrollado. Permite definir umbrales mínimos que se deben cumplir con los test.


* [Mockito](https://site.mockito.org/)
  Testing unitario para el mocking de objetos en los tests (utilizado junto a spring-test y junit). Incluido por defecto al utilizar spring-boot-test


* [H2 Database](https://www.h2database.com) 
  Base de datos en memoria para utilizar en los tests unitarios y no ser necesario conexión con una base de datos real.


* [Spring Cloud Sleuth](https://spring.io/projects/spring-cloud-sleuth) 
  Permite la trazabilidad de peticiones entre servicios (trazabilidad distribuida). Incluye identificadores de cada petición, que se pasan entre servicios para incluírlas en los ficheros de log. Así se puede hacer el seguimiento de una petición a medida que va pasando por los diferentes servicios utilizados para completar la petición. Esta información podría ser enviada a sistemas de tratamiento de logs.
  Se apoya la librería  openzipkinbrave que es la encargada de la generación de los identificadores.
  
  
**Propiedades de configuración:**
Aunque en los ficheros de propiedades se han incluido todas aquellas propiedades que permiten parametrizar el proyecto, en este apartado se van a resaltar aquellas que se consideran mas importantes, por permitir activar o desctivar principalmente módulos comunes.

* **application.audit.enabled=false**
a través de esta propiedad se puede activar la auditoría de operaciones dentro de la aplicación. El módulo de auditoría está incluido por defecto, pero su funcionalidad no se activará por defecto.


* **application.cors.enabled=false** 
activar o no el filtro CORS. Si se activa, se tendrán en cuenta todas las configuraciones específicas que se hagan en el proyecto. Si no se activa, todas esas propiedades de urls/permisos no serán tenidas en cuenta y se cofigurará un acceso por defecto para cualquier origen.


* **es.correos.jwt-validation-enabled=false**
activa o desactiva el filtro que verifica la existencia de un token jwt válido para poder acceder a cualquier endpoint publicado en el proyecto. Por defecto se encuentra desactivado. Si se activa, es necesario incluir la clave pública a utilizar para validar los tokens. **es.correos.public-key**


* **feign.client.custom.default.interceptors.oauth2-interceptor-enabled=false**
a través de esta propiedad se activa/desactiva la transmisión de token jwt cuando se hagan llamadas rest a otros microservicios, usando feign para la comunicación. Por defecto se encuentra desactivada esta transmisión, pero no el módulo que permite hacer uso de feign como cliente REST.


* **es.correos.read.replicas.tracer.enabled=false**
permite activar un aspecto para trazar los métodos de spring-data-jpa para indicar si usan ReadOnly o ReadWrite. Esto en producción, aunque no afecte mucho al rendimiento, mejor desactivar. En desarrollo se recomienda activar para controlar que todo aquello que se quiere ejecutar en solo lectura, realmente se ejecuta así.


**Configuración de replicas de BBDD:**
Si la aplicación hace uso de un modelo de BBDD para su funcionalidad, dispone de la capacidad de utilización de replicas de lectura. Estas réplicas permiten a la aplicación utiliar una BBDD para las operaciones de Lectura/escritura, y otra diferente para las operaciones de Solo Lectura. El sistema de BBDD se encargará automáticamente de replicar la información entre ellas, consiguiendo que la aplicación pueda mejorar el rendimiento.
Para esta configuración se van a generar dos DataSources diferentes, conectados cada uno de ellos a la BBDD que corresponda. Para activar cada una de las conexiones se dispone de dos bloques de propiedades de configuración, que dada su presencia, automáticamente se van a configurar todos los componentes necesarios.

Para la configuración de Lectura/escritura, estas son las propiedades implicadas:

_spring.datasource.url=jdbc:postgresql://localhost:5432/postgres_ <br>
_spring.datasource.username=postgres_ <br>
_spring.datasource.password=postgres_ <br>
_spring.datasource.driver-class-name=org.postgresql.Driver_ <br>
_spring.datasource.initialization-mode= always_ <br>
_spring.entitymanager.packages-to-scan= es.correos.arquetipo.domain, es.correos.arquetipo.repository_ <br>
_spring.jpa.open-in-view=false_ <br>
_spring.jpa.properties.hibernate.format&#95;sql=true_ <br>
_spring.jpa.properties.hibernate.temp.use&#95;jdbc&#95;metadata&#95;defaults=false_ <br>
_spring.jpa.hibernate.ddl-auto=validate_ <br>
_spring.jpa.show-sql=true_ <br>
_spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect_ <br>

Su equivalente para lectura será:

_spring.read.datasource.url=jdbc:postgresql://localhost:5432/postgres_ <br>
_spring.read.datasource.username=postgres_ <br>
_spring.read.datasource.password=postgres_ <br>
_spring.read.datasource.driver-class-name=org.postgresql.Driver_ <br>
_spring.read.datasource.initialization-mode= always_ <br>
_spring.read.entitymanager.packages-to-scan= ${spring.entitymanager.packages-to-scan}_ <br>
_spring.read.jpa.properties.hibernate.format&#95;sql=true_ <br>
_spring.read.jpa.properties.hibernate.temp.use&#95;jdbc&#95;metadata&#95;defaults=false_ <br>
_spring.read.jpa.hibernate.ddl-auto=validate_ <br>
_spring.read.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect_ <br>

Con esta configuración por defecto (modificable al estar dentro de la aplicación), se van a configurar todos los repositorios de acceso a la BBDD como lectura/escritura que estén dentro del paquete indicado en la clase `DatasourceConfig` y que **NO** estén anotados como `@ReadOnlyRepository`. Se cofigurarán como solo lectura aquellos repositorios que esten en el paquete indicado en la clase `ReadOnlyDatasourceConfig` y que estén anotados como ` @ReadOnlyRepository`.

Para facilitar el trazado de la aplicación (especialmente en desarrollo y recomendable eliminarlo en producción), se dispone de un móudulo que irá trazando al log todas las operaciones que se realicen dentro de estos repositorios, indicando especialmente si se ha ejecutado contra la BBDD con acceso de lectura/escritura o contra la de solo lectura.


** Conexión IBM **
Las aplicaciones pueden requerir comunicación con IBM MQ bien para auditar accesos, o bien porque publiquen/consuman mensajes de este sistema de mensajería. Por defecto en el pom.xml de la aplicación se ha excluído la dependencia hacia ibmmq para evitar la autoconfiguración que hace por defecto y que el endpoint /health de spring-boot marque la aplicación como DOWN si no puede conectar con IBM MQ.
Si se tiene la necesidad, hay que modificar el pom.xml y eliminar la exclusión de la dependencia arq-core


`<dependency>`<br>
&ensp;`	<groupId>es.correos.arq.boot</groupId>`<br>
&ensp;`	<artifactId>arq-core</artifactId>`<br>
&ensp;`	<!-- esta exclusión se debe quitar en el caso `<br>
&ensp;`	de utilizar la auditoria de ARQ o necesidad de conexión`<br>
&ensp;`	contra IBM MQ -->`<br>
&ensp;`	<exclusions>`<br>
&ensp;&ensp;`		<exclusion>`<br>
&ensp;&ensp;&ensp;`			<groupId>com.ibm.mq</groupId>`<br>
&ensp;&ensp;&ensp;`			<artifactId>mq-jms-spring-boot-starter</artifactId>`<br>
&ensp;&ensp;`		</exclusion>`<br>
&ensp;`	</exclusions>`<br>
`</dependency>`<br>
