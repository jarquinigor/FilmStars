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
@Table(name = "UserReview")
public class UserReview implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idUserReview;

	@ManyToOne
	@JoinColumn(name = "idMovie", nullable = false)
	private Movie movie;

	@ManyToOne
	@JoinColumn(name = "idUser", nullable = false)
	private Users user;
	
	@Column(name="quantityUserReview", nullable=false)
	private int quantityUserReview;
	
	@Column(name="textUserReview", nullable=false, length=400)
	private String textUserReview;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dateUserReview")
	@DateTimeFormat(pattern="d MMM, yyyy" )         
	private Date dateUserReview;

	public UserReview() {
		super();
		dateUserReview = new Date();
	}

	public UserReview(int idUserReview, Movie movie, Users user, int quantityUserReview, String textUserReview,
			Date dateUserReview) {
		super();
		this.idUserReview = idUserReview;
		this.movie = movie;
		this.user = user;
		this.quantityUserReview = quantityUserReview;
		this.textUserReview = textUserReview;
		this.dateUserReview = dateUserReview;
	}

	public int getIdUserReview() {
		return idUserReview;
	}

	public void setIdUserReview(int idUserReview) {
		this.idUserReview = idUserReview;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public int getQuantityUserReview() {
		return quantityUserReview;
	}

	public void setQuantityUserReview(int quantityUserReview) {
		this.quantityUserReview = quantityUserReview;
	}

	public String getTextUserReview() {
		return textUserReview;
	}

	public void setTextUserReview(String textUserReview) {
		this.textUserReview = textUserReview;
	}

	public Date getDateUserReview() {
		return dateUserReview;
	}

	public void setDateUserReview(Date dateUserReview) {
		this.dateUserReview = dateUserReview;
	}
}
