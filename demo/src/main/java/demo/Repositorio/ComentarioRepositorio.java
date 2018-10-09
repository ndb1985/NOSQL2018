package demo.Repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;
import demo.Entidades.Comentario;

/**
 *
 * @author Natalie
 */
public interface ComentarioRepositorio extends MongoRepository<Comentario, Integer>{
	
	

}
