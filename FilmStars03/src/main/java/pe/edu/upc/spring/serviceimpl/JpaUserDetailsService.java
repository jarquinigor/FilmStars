package pe.edu.upc.spring.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upc.spring.model.Role;
import pe.edu.upc.spring.model.Users;
import pe.edu.upc.spring.repository.IUserRepository;

@Service
public class JpaUserDetailsService implements UserDetailsService { //CARGA TODOS LOS ROLES DE UN USUARIO ENCONTRADO EN OBJETO USER SPRING
	@Autowired
	private IUserRepository usuarioRepository;
	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users usuario = usuarioRepository.findByUsername(username); //USUARIO ENCONTRADO
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for(Role role : usuario.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
		}
		return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(),true,true,true, 
						authorities);
	}

}
