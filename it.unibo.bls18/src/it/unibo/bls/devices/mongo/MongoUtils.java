package it.unibo.bls.devices.mongo;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class MongoUtils {
protected MongoClient mongoClient;
protected DBObject person ;


	public static MongoClient connectToADb( String dbUri ) { //"mongodb://localhost:27017"
		return new MongoClient(new MongoClientURI( dbUri ));
	}
	
	public static DB getMondoDB( String dbUri, String dbName  ) {
		MongoClient mongoClient = connectToADb(dbUri);
		return mongoClient.getDB( dbName );
	}

	public static DBObject findOne( MongoClient mongoClient, String dbName, String collToInspect ) {
		DBCollection collection = getCollection( mongoClient,   dbName,    collToInspect );
 		System.out.println( "MongoTest findOne=" + collection );
		DBObject obj = collection.findOne();
		System.out.println( "MongoTest findOne RESULT=" + obj  );	
		return obj;
	}
	public static DBObject findOne( DB database, String collName  ) {
		return database.getCollection( collName  ).findOne();
	}
	public static Set<String> getCollectionNames( DB database   ) {
		return database.getCollectionNames();
	}
	
 	public static DBCollection getCollection(  MongoClient mongoClient, String dbName, String collName ) {
 		DB database = mongoClient.getDB( dbName );
 		DBCollection collection = database.getCollection( collName  );
 		return collection;
	}
	
	public static void addToDb(MongoClient mongoClient, String dbName, String collToUse, BasicDBObject basicObj ) {
 		DB database = mongoClient.getDB( dbName ); //"Examples"
		DBCollection collection = database.getCollection(collToUse);
		collection.insert(basicObj);
	}

 
	
	public static BasicDBObject createTestObject() {
 		List<Integer> books = Arrays.asList(27464, 747854);
 		BasicDBObject person = new BasicDBObject("_id", "jo")
		                            .append("name", "Jo Bloggs")
		                            .append("address", new BasicDBObject("street", "123 Fake St")
		                                                         .append("city", "Faketon")
		                                                         .append("state", "MA")
		                                                         .append("zip", 12345))
		                            .append("books", books);	
		
		return person;		
	}
//	public static void createADb() {
// 		DB database = mongoClient.getDB("Examples");
//		DBCollection collection = database.getCollection("people");
// 		List<Integer> books = Arrays.asList(27464, 747854);
//		person = new BasicDBObject("_id", "jo")
//		                            .append("name", "Jo Bloggs")
//		                            .append("address", new BasicDBObject("street", "123 Fake St")
//		                                                         .append("city", "Faketon")
//		                                                         .append("state", "MA")
//		                                                         .append("zip", 12345))
//		                            .append("books", books);	
//		
//		collection.insert(person);
//		System.out.println( "MongoTest createADb done"    );		
//	}
	
	/*
  person = {
	  _id: "jo",
	  name: "Jo Bloggs",
	  age: 34,
	  address: {
	    street: "123 Fake St",
	    city: "Faketon",
	    state: "MA",
	    zip: &#x201C;12345&#x201D;
	  }
	  books: [ 27464, 747854, ...]
  }  
	 */
	public static void main(String[] args) {
		MongoClient mongoclient = MongoUtils.connectToADb(  "mongodb://localhost:27017" );
		DB database = MongoUtils.getMondoDB( "mongodb://localhost:27017", "Examples" );
		
		System.out.println( "collections"+ MongoUtils.getCollectionNames(database) );
		
		//database.createCollection("students", options)
		
		DBCollection school = database.getCollection("college"); //creates if it does not exist
		
		school.save( new BasicDBObject("key" , "value"));
		
		System.out.println( "collections"+ MongoUtils.getCollectionNames(database) );

		DBObject obj = MongoUtils.findOne(   database,   "people"  );
		
		System.out.println( "MongoTest findOne RESULT=" + obj  );
		
		System.out.println( "MongoTest findOne RESULT=" + MongoUtils.findOne(   database,   "college"  )  );
	}
}
