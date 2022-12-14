package pe.edu.upc.spring.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "CriticReview")
public class CriticReview implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idCriticReview;

	@ManyToOne
	@JoinColumn(name = "idCritic", nullable = false)
	private Critic critic;
	
	@ManyToOne
	@JoinColumn(name = "idMovie", nullable = false)
	private Movie movie;
	
	@Column(name="quantityCriticReview", nullable=false)
	private int quantityCriticReview;
	
	@Column(name="textCriticReview", nullable=false, length=400)
	private String textCriticReview;
	
	@Temporal(TemporalType.DATE)
	@Column(name="dateCriticReview")
	@DateTimeFormat(pattern="yyyy-MM-dd")         
	private Date dateCriticReview;


	public CriticReview() {
		super();
	}


	public CriticReview(int idCriticReview, Critic critic, Movie movie, int quantityCriticReview,
			String textCriticReview, Date dateCriticReview) {
		super();
		this.idCriticReview = idCriticReview;
		this.critic = critic;
		this.movie = movie;
		this.quantityCriticReview = quantityCriticReview;
		this.textCriticReview = textCriticReview;
		this.dateCriticReview = dateCriticReview;
	}


	public int getIdCriticReview() {
		return idCriticReview;
	}


	public void setIdCriticReview(int idCriticReview) {
		this.idCriticReview = idCriticReview;
	}


	public Critic getCritic() {
		return critic;
	}


	public void setCritic(Critic critic) {
		this.critic = critic;
	}


	public Movie getMovie() {
		return movie;
	}


	public void setMovie(Movie movie) {
		this.movie = movie;
	}


	public int getQuantityCriticReview() {
		return quantityCriticReview;
	}


	public void setQuantityCriticReview(int quantityCriticReview) {
		this.quantityCriticReview = quantityCriticReview;
	}


	public String getTextCriticReview() {
		return textCriticReview;
	}


	public void setTextCriticReview(String textCriticReview) {
		this.textCriticReview = textCriticReview;
	}


	public Date getDateCriticReview() {
		return dateCriticReview;
	}


	public void setDateCriticReview(Date dateCriticReview) {
		this.dateCriticReview = dateCriticReview;
	}
}
