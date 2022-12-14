package pe.edu.upc.spring.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "MovieActor")
public class MovieActor implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idMovieActor;

	@ManyToOne
	@JoinColumn(name = "idMovie", nullable = false)
	private Movie movie;
	
	@ManyToOne
	@JoinColumn(name = "idActor", nullable = false)
	private Actor actor;
	
	@Column(name="nameCharacter", nullable = false, length=60)
	private String nameCharacter;

	public MovieActor() {
		super();
	}

	public MovieActor(int idMovieActor, Movie movie, Actor actor, String nameCharacter) {
		super();
		this.idMovieActor = idMovieActor;
		this.movie = movie;
		this.actor = actor;
		this.nameCharacter = nameCharacter;
	}

	public int getIdMovieActor() {
		return idMovieActor;
	}

	public void setIdMovieActor(int idMovieActor) {
		this.idMovieActor = idMovieActor;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public Actor getActor() {
		return actor;
	}

	public void setActor(Actor actor) {
		this.actor = actor;
	}

	public String getNameCharacter() {
		return nameCharacter;
	}

	public void setNameCharacter(String nameCharacter) {
		this.nameCharacter = nameCharacter;
	}
}
