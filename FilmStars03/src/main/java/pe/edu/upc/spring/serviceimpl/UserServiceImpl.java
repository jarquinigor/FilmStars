package pe.edu.upc.spring.serviceimpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.spring.model.Users;
import pe.edu.upc.spring.repository.IUserRepository;
import pe.edu.upc.spring.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private IUserRepository dUser;
	
	@Override
	@Transactional(readOnly = true)
	public Optional<Users> findById(int idUser) {
		return dUser.findById(idUser);
	}
	
	public Users findByUsername(String username) {
		return dUser.findByUsername(username);
	}

	@Override
	public Users registrar(Users u) {
		u.setPassword(passwordEncoder.encode(u.getPassword()));
		return dUser.save(u);
	}

}
