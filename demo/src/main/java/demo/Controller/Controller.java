/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo.Controller;

import demo.Repositorio.ComentarioRepositorio;
import demo.Repositorio.UsuarioRepositorio;
import demo.Entidades.Comentario;
import demo.Repositorio.ServicioContador;
import demo.Entidades.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

@RestController 
public class Controller {
	
    @Autowired
	private UsuarioRepositorio repoUsuario;
    @Autowired
	private ComentarioRepositorio repoComentario;
    @Autowired
    private ServicioContador sequenceService;
    @Autowired 
    private MongoOperations mongo;
	
	
    //1)Crear Usuario  
    
    /* JSON de ejemplo
     {
     "email": "user@mail.com"
     }
     */
  
  	@PostMapping("/addUsuario")
  	
  	public String saveUsuario(@RequestBody Usuario usuario) {
  		
  		//Consulta de busqueda con el email ingresado
  		Query query = new Query(Criteria.where("email").is(usuario.getEmail()));
  		
  		// Realiza la busqueda del email en la clase usuario
  		if(mongo.exists(query, Usuario.class)) {
  			
  			return "Usuario Ya Ingresado";
  		
  		}else {
  			
  			//asigna el id autoincrementado
  			usuario.setId(sequenceService.getNextSequence("Usuario"));
  			
  			//guarda el usuario
  			repoUsuario.save(usuario);
  			
  			return "Usuario Agregado con id: " + usuario.getId();
  		}
  		
  	}
  	
  	//2)Crear Comentario
  	/* JSON de ejemplo
    {
    "email": "user@mail.com",
    "textoComentario": "Lorem ipsum dolor asimet."
    }
    */
  	
  //agrega un comentario a la base si existe el usuario
  	@PostMapping("/addComentario")
  	
  	public String saveComentario(@RequestBody @Valid Comentario comentario, BindingResult result) {
  				
  		//Consulta de busqueda con el email ingresado
  		Query query = new Query(Criteria.where("email").is(comentario.getEmail()));
  		
  		// Realizo la busqueda del email en la clase usuario
  		if(mongo.exists(query, Usuario.class)) {
  			
  			if(result.hasErrors()==false) {
  				
  				//asigna el id autoincrementado
  			    comentario.setId(sequenceService.getNextSequence("Comentario"));
  			    
  			    //guarda el comentario
  				repoComentario.save(comentario);
  				
  				return "Comentario Agregado con id: " + comentario.getId();	
  			
  			}else {
  				
  				return "El texto es demasiado largo";
  			}
  			
  			
  		}else {
  			return "Email no ingresado en el sistema";
  		}
  	}
	
  	  	
    //3)Listar Comentario
  	
  	@GetMapping("/comentariosUsuario/{email}")
  	
  	public List<Comentario> getComentariosUsuario(@PathVariable String email){
  	
  		Query query = new Query((Criteria.where("email").is(email)));
  		
  		return  mongo.find(query, Comentario.class);
  	}		


  	//4)Comentar  Comentario
  	/* JSON de ejemplo
    {
    "email": "user@mail.com",
    "textoComentario": "Lorem ipsum dolor asimet."
    }
    */
	//Funcion para comentar comentarios
	@PostMapping("/ComentarComentario/{id}")
	
	public String ComentarComentario(@PathVariable int id,@RequestBody Comentario comentarioNuevo) {
		
		Query query = new Query(Criteria.where("email").is(comentarioNuevo.getEmail()));
		
		if(mongo.exists(query, Usuario.class)) {
			
			//agrego secuencia al id
			comentarioNuevo.setId(sequenceService.getNextSequence("Comentario"));
			
			//Guardo nuevo comentario
			repoComentario.save(comentarioNuevo);
					
			//Obtengo comentario por el id ingresado
			Comentario comentario=repoComentario.findById(id).get();
			
			//Seteo el nuevo comentario
			comentario.setComentarios(comentarioNuevo);
			
			//Actualizo el comentario existente
			repoComentario.save(comentario);
			
			return "Comentario agregado con id: "+ comentarioNuevo.getId();
			
		}else {
			return "Verifique su Email.";
		}
		
	}
	
	
	//5)Leer Comentario
	@GetMapping("/leerComentario/{id}")
  	
	public Optional<Comentario> getComentario(@PathVariable int id){
		
		return repoComentario.findById(id);
		
		}
		
	
	//FUNCIONES AUXILIARES
		/*
		//Busca todos los usuarios
		@GetMapping("/findAllUsuarios")
		public List<Usuario> getUsuarios(){
			return repoUsuario.findAll();
		}
		
		//Busca un usuario por Id
		@GetMapping("/findAllUsuarios/{id}")
		public Optional<Usuario> getUsuario(@PathVariable int id){
			return repoUsuario.findById(id);
		}
		
		//Borra un usuario
		@DeleteMapping
		public String deleteUsuario(@PathVariable int id) {
			repoUsuario.deleteById(id);
			return "Usuario eliminado con id: " + id;
		}
		
		
		*/
	
}
