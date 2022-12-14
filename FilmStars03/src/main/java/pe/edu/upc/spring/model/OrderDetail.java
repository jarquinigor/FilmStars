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
@Table(name = "OrderDetail")
public class OrderDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idOrderDetail;

	@ManyToOne
	@JoinColumn(name = "idMovie", nullable = false)
	private Movie movie;
	
	@ManyToOne
	@JoinColumn(name = "idOrder", nullable = true)
	private Orders order;
	
	@Column(name="nameCharacter", nullable = false)
	private int quantityOrderDetail;

	public OrderDetail() {
		super();
	}

	public OrderDetail(int idOrderDetail, Movie movie, Orders order, int quantityOrderDetail) {
		super();
		this.idOrderDetail = idOrderDetail;
		this.movie = movie;
		this.order = order;
		this.quantityOrderDetail = quantityOrderDetail;
	}

	public int getIdOrderDetail() {
		return idOrderDetail;
	}

	public void setIdOrderDetail(int idOrderDetail) {
		this.idOrderDetail = idOrderDetail;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public Orders getOrder() {
		return order;
	}

	public void setOrder(Orders order) {
		this.order = order;
	}

	public int getQuantityOrderDetail() {
		return quantityOrderDetail;
	}

	public void setQuantityOrderDetail(int quantityOrderDetail) {
		this.quantityOrderDetail = quantityOrderDetail;
	}
}
