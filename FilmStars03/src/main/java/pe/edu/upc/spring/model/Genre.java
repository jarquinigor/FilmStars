package pe.edu.upc.spring.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Genre")
public class Genre implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idGenre;

	@Size(min = 2, max=30)
	@NotEmpty(message = "Debe ingresar un nombre de género de película")
	@Column(name = "nameGenre", length = 30, nullable = false)
	private String nameGenre;

	public Genre() {
		super();
	}

	public Genre(int idGenre, String nameGenre) {
		super();
		this.idGenre = idGenre;
		this.nameGenre = nameGenre;
	}

	public int getIdGenre() {
		return idGenre;
	}

	public void setIdGenre(int idGenre) {
		this.idGenre = idGenre;
	}

	public String getNameGenre() {
		return nameGenre;
	}

	public void setNameGenre(String nameGenre) {
		this.nameGenre = nameGenre;
	}
}
