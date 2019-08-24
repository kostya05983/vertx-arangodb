### Project Goal

* Provide better compatibility of vert.x with arangodb [https://github.com/arangodb/arangodb-java-driver-async]
* Give an opportunity to use arangodb with common interfaces and reactivex in vert.x with code gen

### Fast Start

```java
JsonObject config = new JsonObject();
config.put("host", "localhost");
config.put("port", 8529);
ArangoClient.ArangoBuilderDecorator arangoBuilderDecorator = new ArangoClient.ArangoBuilderDecorator(config);
ArangoClient<BaseDocument> arangoClient = ArangoClient.createNonShared(vertx, arangoBuilderDecorator, DB_NAME);        
```


#### Examples 
```java
collection.rxGetDocument("collection_name", "key",
 BaseDocument.class).subscribe({},{})
```

### Contributing 

* github pull requests

* branch names start with gh-number_ticket

* please add a number of issue in following format gh-number_ticket

