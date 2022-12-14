package pe.edu.upc.spring.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "Critic")
public class Critic implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idCritic;
	
	@NotEmpty(message = "Debe ingresar un nombre de crítico")
	@Column(name = "nameCritic", length = 60, nullable = false)
	private String nameCritic;

	public Critic() {
		super();
	}

	public Critic(int idCritic, String nameCritic) {
		super();
		this.idCritic = idCritic;
		this.nameCritic = nameCritic;
	}

	public int getIdCritic() {
		return idCritic;
	}

	public void setIdCritic(int idCritic) {
		this.idCritic = idCritic;
	}

	public String getNameCritic() {
		return nameCritic;
	}

	public void setNameCritic(String nameCritic) {
		this.nameCritic = nameCritic;
	}
}
