package pe.edu.upc.spring.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import pe.edu.upc.spring.model.Orders;
import pe.edu.upc.spring.model.Users;
import pe.edu.upc.spring.service.IOrderService;
import pe.edu.upc.spring.service.IUserService;

@Controller
@RequestMapping("/welcome")
public class WelcomeController {
	
	@Autowired
	private IOrderService oService;
	@Autowired
	private IUserService uService;

	@RequestMapping("/bienvenido")
	public String irPaginaBienvenida(Authentication auth, HttpSession session, HttpServletRequest request) {
		
		
		//PONER LA ORDEN QUE ESTÁ PENDIENTEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE
		
		
		if (auth.getName().isEmpty() == false) { // SI SE ESTÁ AUTENTICADO
			
			session.setAttribute("userlogged", new Users());
			String username = auth.getName();
			Users user = uService.findByUsername(username);

			user.setPassword(null);
			session.setAttribute("userlogged", user);
			
			if(oService.findIncompleteOrder(user.getIdUser())!=null) {//SI HAY UNA ORDEN INCOMPLETA
				Orders order = oService.findIncompleteOrder(user.getIdUser());
				session.setAttribute("order", order);
				
				System.out.println(order.getStatusOrder());
				System.out.println(order.getStatusOrder());
				System.out.println(order.getStatusOrder());
				System.out.println(order.getStatusOrder());
			}
			else {
				session.setAttribute("order", new Orders());
			}
			
			
			
			if (request.isUserInRole("ROLE_ADMIN")) {
	            return "welcomeAdmin";
	        }
			else { //ROLE_USER
				return "welcomeUser";
			}
		}  //NO ESTÁ AUTENTICADO
		else {
			return "welcome";
		}
	}

	@RequestMapping("/bienvenidoN")
	public String irPaginaBienvenidaN(Principal principal) {

		if (principal != null) { //ESTÁ LOGEADO O ESTÁ AUTENTICADO
			
			//VERIFICAR
			return "redirect:/welcome/bienvenido";
		}

		return "welcome";
	}

}
