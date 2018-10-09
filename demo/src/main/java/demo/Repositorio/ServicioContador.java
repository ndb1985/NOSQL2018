package demo.Repositorio;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.FindAndModifyOptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import demo.Entidades.Contador;

@Service
public class ServicioContador {

	

	  @Autowired private MongoOperations mongo;
	   
	  public int getNextSequence(String collectionName) {
	 
	    Contador counter = mongo.findAndModify(
	      query(where("_id").is(collectionName)), 
	      new Update().inc("seq", 1),
	      options().returnNew(true).upsert(true),
	      Contador.class);
	       
	    return counter.getSeq();
	  }
	/*  new Update().inc(“value”,1) incrementará el campo “value”.

	  La opción returnNew(true) hará que la función devuelva el nuevo documento en lugar del original.

	  La opción upsert(true) creará un nuevo documento si la secuencia indicada no existe previamente.*/
}