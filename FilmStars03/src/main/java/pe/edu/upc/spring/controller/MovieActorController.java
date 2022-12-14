package pe.edu.upc.spring.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sun.el.parser.ParseException;

import pe.edu.upc.spring.model.MovieActor;
import pe.edu.upc.spring.service.IMovieService;
import pe.edu.upc.spring.service.IActorService;
import pe.edu.upc.spring.service.IMovieActorService;

@Controller
@RequestMapping("/peliculaActor")
public class MovieActorController {
	@Autowired
	private IMovieService mService;
	
	@Autowired
	private IActorService aService;
	
	@Autowired
	private IMovieActorService maService;
	
	@RequestMapping("/bienvenido")
	public String goWelcomePage() {
		return "welcome"; 
	}
	
	@RequestMapping("/registrar")
	public String register(@ModelAttribute("movieActor") MovieActor objMovieActor, BindingResult binRes, Model model) 
		throws ParseException
	{
		if (binRes.hasErrors())
			return "movieActor";
		else {
			boolean flag = maService.save(objMovieActor);
			if(flag) {
				return "redirect:/peliculaActor/listarSuccess";
			}
				
			else {
				return "redirect:/peliculaActor/listarFailed";
			}
		}
	}
	
	@RequestMapping("/modificar/{id}")
	public String modify(@PathVariable int id, Model model, RedirectAttributes objRedir) 
		throws ParseException
	{
		Optional<MovieActor> objMovieActor = maService.findById(id);
		if (objMovieActor == null) {
			objRedir.addFlashAttribute("mensaje", "Ocurrio un roche, LUZ ROJA");
			return "redirect:/peliculaGenero/listar";
		}
		else {
			model.addAttribute("movieActorbusqueda", new MovieActor());
			model.addAttribute("listMovies",mService.findAllSortNameAsc());
			model.addAttribute("listActors",aService.findAllSortNameAsc());
			model.addAttribute("listMovieActors", maService.findAllSortIdAsc());
			if(objMovieActor.isPresent())
				objMovieActor.ifPresent(o -> model.addAttribute("movieActor",o));
			
			return "listMovieActor";                   
		}
	}
	
	@RequestMapping("/eliminar")
	public String delete(Map<String, Object> model, @RequestParam(value="id") Integer id) {
		try {
			if(id!=null && id>0) {
				maService.delete(id);
				model.put("movieActor",new MovieActor()); //importante
				model.put("movieActorbusqueda", new MovieActor()); //importante
				model.put("listMovies",mService.findAllSortNameAsc());
				model.put("listActors",aService.findAllSortNameAsc());
				model.put("listMovieActors", maService.findAllSortIdAsc());
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			model.put("Mensaje", "Ocurrio un error");
			model.put("movieActor",new MovieActor()); //importante
			model.put("movieActorbusqueda", new MovieActor()); //importante
			model.put("listMovies",mService.findAllSortNameAsc());
			model.put("listActors",aService.findAllSortNameAsc());
			model.put("listMovieActors", maService.findAllSortIdAsc());
		}
		return "listMovieActor";
	}
	
	@RequestMapping("/listar")
	public String list(Map<String, Object> model) {
		model.put("movieActor",new MovieActor());
		model.put("movieActorbusqueda", new MovieActor());
		model.put("listMovies",mService.findAllSortNameAsc());
		model.put("listActors",aService.findAllSortNameAsc());
		model.put("listMovieActors", maService.findAllSortIdAsc());
		return "listMovieActor";
	}
	
	@RequestMapping("/listarSuccess")
	public String listSuccess(Map<String, Object> model) {
		model.put("mensaje", "Se ha registrado un actor en una película correctamente");
		model.put("movieActor",new MovieActor());
		model.put("movieActorbusqueda", new MovieActor());
		model.put("listMovies",mService.findAllSortNameAsc());
		model.put("listActors",aService.findAllSortNameAsc());
		model.put("listMovieActors", maService.findAllSortIdAsc());
		return "listMovieActor";
	}
	
	@RequestMapping("/listarFailed")
	public String listFailed(Map<String, Object> model) {
		model.put("msjActor", "Este actor ya se encuentra registrado en la película");
		model.put("movieActor",new MovieActor());
		model.put("movieActorbusqueda", new MovieActor());
		model.put("listMovies",mService.findAllSortNameAsc());
		model.put("listActors",aService.findAllSortNameAsc());
		model.put("listMovieActors", maService.findAllSortIdAsc());
		return "listMovieActor";
	}
	
	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model,@ModelAttribute("movieActorbusqueda") MovieActor movieActor) 
		throws ParseException
	{
		List<MovieActor>listMovieActors;
		listMovieActors = maService.findByMovieName(movieActor.getMovie().getNameMovie());
		
		if(listMovieActors.isEmpty()) {
			listMovieActors = maService.findByActorName(movieActor.getMovie().getNameMovie());
		}
		model.put("movieActor", new MovieActor());
		model.put("listMovies",mService.findAllSortNameAsc());
		model.put("listActors",aService.findAllSortNameAsc());
		model.put("listMovieActors", listMovieActors);
		
		return "listMovieActor";
	}
}
