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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "Orders")
public class Orders implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idOrder;
	
	@Column(name="formOrder", nullable=true)
	private int formOrder;
	
	@Column(name="methodOrder", nullable=true)
	private int methodOrder;
	
	@Temporal(TemporalType.DATE) //TIMESTAMP
	@Column(name="dateOrder", nullable = true)
	@DateTimeFormat(pattern="d MMM, yyyy")  //@DateTimeFormat(pattern="d MMM, yyyy HH:mm:ss" )
	private Date dateOrder;
	
	@Temporal(TemporalType.TIME)
	@Column(name="timeOrder", nullable = true)
	@DateTimeFormat(pattern="HH:mm:ss")  //@DateTimeFormat(pattern="d MMM, yyyy HH:mm:ss" )
	private Date timeOrder;
	
	@ManyToOne
	@JoinColumn(name = "idUser", nullable = false)
	private Users user;
	                                             //    0 Y 1 PRINCIPALES
	@Column(name="statusOrder", nullable = false)  // (0:NO TERMINADA - 1: NO ATENDIDA - 2:ATENDIDA - 3:ENVIO EXITOSO)
	private int statusOrder;
	
	//TARJETA (4 DATOS)
	@Column(name = "numCardOrder", length = 16, nullable = true)
	private String numCardOrder;
	
	@Column(name="monthCardOrder", nullable=true)
	private int monthCardOrder;
	
	@Column(name="yearCardOrder", nullable=true)
	private int yearCardOrder;
	
	@Column(name = "cvvCardOrder", length = 3, nullable = true)
	private String cvvCardOrder;

	public Orders() {
		super();
	}

	public Orders(int idOrder, int formOrder, int methodOrder, Date dateOrder, Date timeOrder, Users user,
			int statusOrder, String numCardOrder, int monthCardOrder, int yearCardOrder, String cvvCardOrder) {
		super();
		this.idOrder = idOrder;
		this.formOrder = formOrder;
		this.methodOrder = methodOrder;
		this.dateOrder = dateOrder;
		this.timeOrder = timeOrder;
		this.user = user;
		this.statusOrder = statusOrder;
		this.numCardOrder = numCardOrder;
		this.monthCardOrder = monthCardOrder;
		this.yearCardOrder = yearCardOrder;
		this.cvvCardOrder = cvvCardOrder;
	}

	public int getIdOrder() {
		return idOrder;
	}

	public void setIdOrder(int idOrder) {
		this.idOrder = idOrder;
	}

	public int getFormOrder() {
		return formOrder;
	}

	public void setFormOrder(int formOrder) {
		this.formOrder = formOrder;
	}

	public int getMethodOrder() {
		return methodOrder;
	}

	public void setMethodOrder(int methodOrder) {
		this.methodOrder = methodOrder;
	}

	public Date getDateOrder() {
		return dateOrder;
	}

	public void setDateOrder(Date dateOrder) {
		this.dateOrder = dateOrder;
	}

	public Date getTimeOrder() {
		return timeOrder;
	}

	public void setTimeOrder(Date timeOrder) {
		this.timeOrder = timeOrder;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public int getStatusOrder() {
		return statusOrder;
	}

	public void setStatusOrder(int statusOrder) {
		this.statusOrder = statusOrder;
	}

	public String getNumCardOrder() {
		return numCardOrder;
	}

	public void setNumCardOrder(String numCardOrder) {
		this.numCardOrder = numCardOrder;
	}

	public int getMonthCardOrder() {
		return monthCardOrder;
	}

	public void setMonthCardOrder(int monthCardOrder) {
		this.monthCardOrder = monthCardOrder;
	}

	public int getYearCardOrder() {
		return yearCardOrder;
	}

	public void setYearCardOrder(int yearCardOrder) {
		this.yearCardOrder = yearCardOrder;
	}

	public String getCvvCardOrder() {
		return cvvCardOrder;
	}

	public void setCvvCardOrder(String cvvCardOrder) {
		this.cvvCardOrder = cvvCardOrder;
	}
}
