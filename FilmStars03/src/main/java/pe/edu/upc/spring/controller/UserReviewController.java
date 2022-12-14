package pe.edu.upc.spring.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sun.el.parser.ParseException;

import pe.edu.upc.spring.model.Movie;
import pe.edu.upc.spring.model.Users;
import pe.edu.upc.spring.model.UserReview;
import pe.edu.upc.spring.service.IMovieService;
import pe.edu.upc.spring.service.IUserReviewService;
import pe.edu.upc.spring.service.IUserService;

@Controller
@RequestMapping("/usuarioReview")
public class UserReviewController {
	@Autowired
	private IUserReviewService urService;
	
	//PARA REGISTRAR USER REVIEWS
	@Autowired
	private IUserService uService;
	@Autowired
	private IMovieService mService;
	
	@RequestMapping("/reviews")
	public String goMovieUserReviewPage(Model model, @RequestParam(value="idMovie") Integer idMovie) {
		
		List<UserReview>objReview = urService.findByMovieId(idMovie);
		
		model.addAttribute("listUserReviews",objReview);
		model.addAttribute("userReview", new UserReview());
		
		Optional <Movie>objMovie = mService.findById(idMovie); 
		if(objMovie.isPresent())
			objMovie.ifPresent(o -> model.addAttribute("movie", o));
		
		return "movieUserReview";
	}
	
	@RequestMapping("/eliminar")
	public String goMovieUserReviewPage(Model model, @RequestParam(value="idUserReview") Integer idUserReview, 
			@RequestParam("idUserLogged") Integer idUserLogged, RedirectAttributes redirectAttribute) {
		
		try {
			UserReview userReview = urService.findByURId(idUserReview);
			//Se elimina
			if(userReview.getUser().getIdUser() == idUserLogged) {
				urService.delete(idUserReview);
			}
			//
			List<UserReview>objReview = urService.findByMovieId(userReview.getMovie().getIdMovie());
			model.addAttribute("listUserReviews",objReview);
			model.addAttribute("userReview", new UserReview());
			
			Optional <Movie>objMovie = mService.findById(userReview.getMovie().getIdMovie()); 
			if(objMovie.isPresent())
				objMovie.ifPresent(o -> model.addAttribute("movie", o));
			
			return "movieUserReview";
		} catch (Exception e) {
			redirectAttribute.addAttribute("idUserReview",idUserReview);
			redirectAttribute.addAttribute("idUserLogged",idUserLogged);
			return "redirect:/usuarioReview/reviews";
		}
		
		
	}
	
	@RequestMapping("/registrar")
	public String register(@ModelAttribute("userReview") UserReview objUserReview,
			@RequestParam(value = "idUser") Integer idUser, @RequestParam(value = "idMovie") Integer idMovie, BindingResult binRes,
			Model model, RedirectAttributes redirectAttributes) throws ParseException {
		if (binRes.hasErrors())
			return "movieUser";
		else {
			
			if(urService.findByMovieUserId(idMovie, idUser).isEmpty()) 
			{
				//Para registrar un objeto UserReview
				Optional<Users>objUser = uService.findById(idUser);   
				if(objUser.isPresent())
					objUser.ifPresent(o -> objUserReview.setUser(o));
				
				Optional<Movie>objMovie = mService.findById(idMovie);
				if(objMovie.isPresent())
					objMovie.ifPresent(o -> objUserReview.setMovie(o));
			
				boolean flag = urService.save(objUserReview); //Se registra el objeto
				if (flag)
				{	
					//Cargando el model
					Optional <Movie>movie = mService.findById(idMovie);
					if(movie.isPresent())
						movie.ifPresent(o -> redirectAttributes.addAttribute("idMovie", o.getIdMovie()));
				
					return "redirect:/usuarioReview/reviews";
				}
					
				else {
					model.addAttribute("mensaje", "Rochezaso");
					return "redirect:/usuarioReview/reviews";
				}
			}
			else {
				List<UserReview>objReview = urService.findByMovieId(idMovie);
				
				model.addAttribute("listUserReviews",objReview); //1
				model.addAttribute("userReview", new UserReview());//2
				
				Optional<Movie>objMovie = mService.findById(idMovie); 
				if(objMovie.isPresent())
					objMovie.ifPresent(o -> model.addAttribute("movie", o));//3

				model.addAttribute("mensaje", "Ya tienes un review en esta película registrada");
				return "movieUserReview";
			}			
		}
	}
}
