# BUSINESS ARCHETYPE

Arquetipo para crear microservicios de integración y lógica de negocio.

## Installación

```
mvn clean install
```

## Uso

```
mvn archetype:generate -DarchetypeGroupId=com.claro.projectBusiness -DarchetypeArtifactId=PROJECT-BUSINESS -DarchetypeVersion=1.0-SNAPSHOT 
```
Se crea el proyecto en modo interactivo. Sera necesario proveer:
   * groupId
   * artifactId
   * classPrefix