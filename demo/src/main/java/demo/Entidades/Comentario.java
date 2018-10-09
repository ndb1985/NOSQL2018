package demo.Entidades;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Comentario")
public class Comentario {
	
	@Id
	private int id;
	private String email;
	@Size(min = 1, max = 256)
	private String textoComentario;
	private List<Comentario> comentarios = new ArrayList<>(); 
	
	public Comentario() {}
	
	public Comentario(String email, String textoComentario, Comentario comentario) {
		this.email = email;
	
		this.textoComentario = textoComentario;
		this.comentarios.add(comentario);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTextoComentario() {
		return textoComentario;
	}

	public void setTextoComentario(String textoComentario) {
		this.textoComentario = textoComentario;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public List<Comentario>  getComentarios() {
		return comentarios;
	}

	public void setComentarios(Comentario comentarios) {
		this.comentarios.add(comentarios);
	}

	@Override
	public String toString() {
		return "Comentario [email=" + email + ", textoComentario=" + textoComentario + "]";
	}
	
	
	
}
