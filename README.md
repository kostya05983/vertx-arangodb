### Project Goal

* Provide better compatibility of vert.x with arangodb [https://github.com/arangodb/arangodb-java-driver-async]
* Give an opportunity to use arangodb with common interfaces and reactivex in vert.x with code gen

#### Examples 
```java
collection.rxGetDocument("collection_name", "key",
 BaseDocument.class).subscribe({},{})
```

### Contributing 

* github pull requests

* branch names start with gh-number_ticket

* please add a number of issue in following format gh-number_ticket

