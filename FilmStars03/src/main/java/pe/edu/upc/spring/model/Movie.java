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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

@Entity
@Table(name = "Movie")
public class Movie implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idMovie;
	
	@NotNull(message="Debe ingresar el stock de la película")
	@Column(name="stockMovie", nullable=false)
	@Range(min = 0, max = 200)
	private int stockMovie;
	
	@NotNull(message="Debe ingresar el precio de la película")
	@Column(name="priceMovie", nullable=true)
	@Range(min = 20, max = 125)
	private double priceMovie;
	
	@ManyToOne
	@JoinColumn(name = "idDirector", nullable = false)
	private Director director;
	
	@Size(min = 2, max=60)
	@NotEmpty(message = "Debe ingresar el nombre de la película")
	@Column(name="nameMovie", nullable=false, unique = true, length=60)
	private String nameMovie;
	
	@Size(min = 60, max=400)
	@NotEmpty(message = "Debe ingresar la sinopsis de la película")
	@Column(name="synopsisMovie", nullable=false, length=400)
	private String synopsisMovie;
	
	@Size(min = 400, max=2000)
	@NotEmpty(message = "Debe ingresar el argumento de la película")
	@Column(name="plotMovie", nullable=false, length=2000)
	private String plotMovie;
	
	@Size(min = 20, max=100)
	@NotEmpty(message = "Debe ingresar una portada para la película")
	@Column(name="coverMovie", nullable=false, length=100)
	private String coverMovie;
	
	@Size(min = 20, max=100)
	@NotEmpty(message = "Debe ingresar una imagen para la película")
	@Column(name="imgMovie", nullable=false, length=100) //unique = true,
	private String imgMovie;
	
	@Size(min = 20, max=100)
	@NotEmpty(message = "Debe ingresar una primera imagen para el carrusel")
	@Column(name="imgCarrMovie1", nullable=false, length=100)
	private String imgCarrMovie1;
	
	@Size(min = 20, max=100)
	@NotEmpty(message = "Debe ingresar una segunda imagen para el carrusel")
	@Column(name="imgCarrMovie2", nullable=false, length=100)
	private String imgCarrMovie2;
	
	@Size(min = 20, max=100)
	@NotEmpty(message = "Debe ingresar una tercera imagen para el carrusel")
	@Column(name="imgCarrMovie3", nullable=false, length=100)
	private String imgCarrMovie3;

	public Movie() {
		super();
	}

	public Movie(int idMovie, int stockMovie, double priceMovie, Director director,
			@NotEmpty(message = "Debe ingresar el nombre de la película") String nameMovie,
			@NotEmpty(message = "Debe ingresar la sinopsis de la película") String synopsisMovie,
			@NotEmpty(message = "Debe ingresar la trama de la película") String plotMovie,
			@NotEmpty(message = "Debe ingresar una portada para la película") String coverMovie,
			@NotEmpty(message = "Debe ingresar una imagen para la película") String imgMovie,
			@NotEmpty(message = "Debe ingresar una primera imagen para el carrusel") String imgCarrMovie1,
			@NotEmpty(message = "Debe ingresar una segunda imagen para el carrusel") String imgCarrMovie2,
			@NotEmpty(message = "Debe ingresar una tercera imagen para el carrusel") String imgCarrMovie3) {
		super();
		this.idMovie = idMovie;
		this.stockMovie = stockMovie;
		this.priceMovie = priceMovie;
		this.director = director;
		this.nameMovie = nameMovie;
		this.synopsisMovie = synopsisMovie;
		this.plotMovie = plotMovie;
		this.coverMovie = coverMovie;
		this.imgMovie = imgMovie;
		this.imgCarrMovie1 = imgCarrMovie1;
		this.imgCarrMovie2 = imgCarrMovie2;
		this.imgCarrMovie3 = imgCarrMovie3;
	}

	public int getIdMovie() {
		return idMovie;
	}

	public void setIdMovie(int idMovie) {
		this.idMovie = idMovie;
	}

	public int getStockMovie() {
		return stockMovie;
	}

	public void setStockMovie(int stockMovie) {
		this.stockMovie = stockMovie;
	}

	public double getPriceMovie() {
		return priceMovie;
	}

	public void setPriceMovie(double priceMovie) {
		this.priceMovie = priceMovie;
	}

	public Director getDirector() {
		return director;
	}

	public void setDirector(Director director) {
		this.director = director;
	}

	public String getNameMovie() {
		return nameMovie;
	}

	public void setNameMovie(String nameMovie) {
		this.nameMovie = nameMovie;
	}

	public String getSynopsisMovie() {
		return synopsisMovie;
	}

	public void setSynopsisMovie(String synopsisMovie) {
		this.synopsisMovie = synopsisMovie;
	}

	public String getPlotMovie() {
		return plotMovie;
	}

	public void setPlotMovie(String plotMovie) {
		this.plotMovie = plotMovie;
	}

	public String getCoverMovie() {
		return coverMovie;
	}

	public void setCoverMovie(String coverMovie) {
		this.coverMovie = coverMovie;
	}

	public String getImgMovie() {
		return imgMovie;
	}

	public void setImgMovie(String imgMovie) {
		this.imgMovie = imgMovie;
	}

	public String getImgCarrMovie1() {
		return imgCarrMovie1;
	}

	public void setImgCarrMovie1(String imgCarrMovie1) {
		this.imgCarrMovie1 = imgCarrMovie1;
	}

	public String getImgCarrMovie2() {
		return imgCarrMovie2;
	}

	public void setImgCarrMovie2(String imgCarrMovie2) {
		this.imgCarrMovie2 = imgCarrMovie2;
	}

	public String getImgCarrMovie3() {
		return imgCarrMovie3;
	}

	public void setImgCarrMovie3(String imgCarrMovie3) {
		this.imgCarrMovie3 = imgCarrMovie3;
	}
}
