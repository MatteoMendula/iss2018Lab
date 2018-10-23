package it.unibo.bls.devices.mongo;

import java.util.Arrays;
import java.util.List;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class MongoTest {
protected MongoClient mongoClient;
protected DBObject person ;

	public MongoTest() {
		mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
	}
	
	public void doJob() {
		System.out.println( "MongoTest START"  );
		//createADb();
 		inspectDb("test","users" );
 		inspectDb("Examples","people" );
	}
	
	protected void inspectDb(String dbName, String collToInspect ) {
		System.out.println( "MongoTest inspectDb START"  );
		DB database = mongoClient.getDB( dbName );
		System.out.println( "MongoTest database=" + database );
		DBCollection collection = database.getCollection( collToInspect  );
		System.out.println( "MongoTest collection=" + collection );
		DBObject obj = collection.findOne();
		System.out.println( "MongoTest RESULT=" + obj  );		
	}
	
	protected void createADb() {
 		DB database = mongoClient.getDB("Examples");
		DBCollection collection = database.getCollection("people");
 		List<Integer> books = Arrays.asList(27464, 747854);
		person = new BasicDBObject("_id", "jo")
		                            .append("name", "Jo Bloggs")
		                            .append("address", new BasicDBObject("street", "123 Fake St")
		                                                         .append("city", "Faketon")
		                                                         .append("state", "MA")
		                                                         .append("zip", 12345))
		                            .append("books", books);	
		
		collection.insert(person);
		System.out.println( "MongoTest createADb done"    );		
	}
	
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
		new MongoTest().doJob();
	}
}
