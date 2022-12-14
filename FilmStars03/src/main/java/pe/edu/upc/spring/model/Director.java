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
@Table(name = "Director")
public class Director implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idDirector;

	@Size(min = 5, max=50)
	@NotEmpty(message = "Debe ingresar un nombre de director")
	@Column(name = "nameDirector", length = 50, nullable = false)
	private String nameDirector;

	public Director() {
		super();
	}

	public Director(int idDirector, String nameDirector) {
		super();
		this.idDirector = idDirector;
		this.nameDirector = nameDirector;
	}

	public int getIdDirector() {
		return idDirector;
	}

	public void setIdDirector(int idDirector) {
		this.idDirector = idDirector;
	}

	public String getNameDirector() {
		return nameDirector;
	}

	public void setNameDirector(String nameDirector) {
		this.nameDirector = nameDirector;
	}
}
