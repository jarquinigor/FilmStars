package pe.edu.upc.spring.service;

import java.util.Optional;

import pe.edu.upc.spring.model.Users;

public interface IUserService {
	public Optional<Users>findById(int idUser);
	public Users findByUsername(String username);
	public Users registrar(Users u);
}
