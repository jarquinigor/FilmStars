package pe.edu.upc.spring.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "MovieGenre")
public class MovieGenre implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idMovieGenre;

	@ManyToOne
	@JoinColumn(name = "idMovie", nullable = false)
	private Movie movie;
	
	@ManyToOne
	@JoinColumn(name = "idGenre", nullable = false)
	private Genre genre;

	public MovieGenre() {
		super();
	}

	public MovieGenre(int idMovieGenre, Movie movie, Genre genre) {
		super();
		this.idMovieGenre = idMovieGenre;
		this.movie = movie;
		this.genre = genre;
	}

	public int getIdMovieGenre() {
		return idMovieGenre;
	}

	public void setIdMovieGenre(int idMovieGenre) {
		this.idMovieGenre = idMovieGenre;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}
}
