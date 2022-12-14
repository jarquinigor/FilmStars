package pe.edu.upc.spring.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Users")
public class Users implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idUser;
	
	@Size(min = 6, max=18) // el tamaño debe estar entre 6 y 18
	@NotEmpty(message = "Por favor, ingrese su nombre de usuario")
	@Column(name = "nameUser", nullable = false, length = 18, unique = true)
	private String nameUser; //Nombre de Usuario
	
	@Size(min = 12, max=36)
	@NotEmpty(message = "Por favor, ingrese su correo electrónico de usuario")
	@Column(name = "emailUser", nullable = false, length = 36, unique = true)
	private String username; //Correo
	
	@NotEmpty(message = "Por favor, ingrese su contraseña de usuario")
	@Column(name = "password", nullable = false, length = 90)
	private String password;
	
	private Boolean enabled;
	
	//DATOS EXTRAS
	@Column(name = "districtUser", nullable = true, length = 36)
	private String districtUser;
	
	@Size(min = 15, max=85)
	@NotEmpty(message = "Por favor, ingrese su dirección de usuario")
	@Column(name = "addressUser", nullable = true, length = 85)
	private String addressUser;
	
	@Column(name = "phoneNumberUser", nullable = true, length = 9)
	private String phoneNumberUser;
	
	@OneToMany(fetch= FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="user_id")
	private List<Role> roles;

	public Users() {
		super();
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public String getNameUser() {
		return nameUser;
	}

	public void setNameUser(String nameUser) {
		this.nameUser = nameUser;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getDistrictUser() {
		return districtUser;
	}

	public void setDistrictUser(String districtUser) {
		this.districtUser = districtUser;
	}

	public String getAddressUser() {
		return addressUser;
	}

	public void setAddressUser(String addressUser) {
		this.addressUser = addressUser;
	}

	public String getPhoneNumberUser() {
		return phoneNumberUser;
	}

	public void setPhoneNumberUser(String phoneNumberUser) {
		this.phoneNumberUser = phoneNumberUser;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
}
