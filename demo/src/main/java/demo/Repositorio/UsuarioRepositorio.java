
package demo.Repositorio;
import org.springframework.data.mongodb.repository.MongoRepository;
import demo.Entidades.Usuario;

/**
 *
 * @author Natalie
 */
public interface UsuarioRepositorio extends MongoRepository<Usuario, Integer>{
	
	

}
