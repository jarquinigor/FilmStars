package pe.edu.upc.spring.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

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

import pe.edu.upc.spring.model.Actor;
import pe.edu.upc.spring.service.IActorService;

@Controller
@RequestMapping("/actor")
public class ActorController {
	@Autowired
	private IActorService aService;
	
	@RequestMapping("/registrar")
	public String register(@Valid @ModelAttribute("actor") Actor actor, BindingResult binRes, @RequestParam(value="opt") Integer opt, 
			@RequestParam(value="baseActor") String baseActor, Model model) 
		throws Exception
	{
		if (binRes.hasErrors()){
			model.addAttribute("listActors", aService.findAllSortIdAsc());
			model.addAttribute("actorbusqueda", new Actor());
			model.addAttribute("opt", 1);
			return "listActor";
		}	
		else {
			int rpta = aService.save(actor, baseActor, opt);
			if(opt == 1) {				
				if(rpta == 0) {
					model.addAttribute("mensaje", "Se registró un actor correctamente");
					model.addAttribute("actor", new Actor()); //NEW ACTOR
				}
				if(rpta == 1) {
					model.addAttribute("msjName", "Ya existe este nombre de actor");
					model.addAttribute("actor", actor); //NO NEW ACTOR
				}					
				model.addAttribute("listActors", aService.findAllSortIdAsc());
				model.addAttribute("actorbusqueda", new Actor()); 
				model.addAttribute("opt", 1);
				return "listActor";
			}		
			else {
				if(rpta == 0) {
					model.addAttribute("mensaje", "No se realizó ningún cambio");
					model.addAttribute("actor", new Actor());
					model.addAttribute("opt", 1);
				}
				if(rpta == 1 || rpta==2) {
					model.addAttribute("mensaje", "Se actualizó un actor correctamente");
					model.addAttribute("actor", new Actor());
					model.addAttribute("opt", 1);
				}
				if(rpta == 3) {
					model.addAttribute("msjName", "Ya existe este nombre de actor");
					model.addAttribute("actor", actor);
					model.addAttribute("opt", 2);
				}			
				model.addAttribute("listActors", aService.findAllSortIdAsc());
				model.addAttribute("actorbusqueda", new Actor());				
				return "listActor";
			}
		}
	}
	
	@RequestMapping("/modificar/{id}")
	public String modify(@PathVariable int id, Model model, RedirectAttributes objRedir) 
		throws ParseException
	{
		Optional<Actor> objActor = aService.findById(id);
		if (objActor == null) {
			objRedir.addFlashAttribute("mensaje", "Ocurrio un roche, LUZ ROJA");
			return "redirect:/actor/listar";
		}
		else {
			model.addAttribute("actor", objActor);
			model.addAttribute("baseActor", objActor.get().getNameActor());
			model.addAttribute("actorbusqueda", new Actor());
			model.addAttribute("listActors",aService.findAllSortIdAsc());
			model.addAttribute("opt", 2);
			return "listActor";                   
		}
	}
	
	@RequestMapping("/eliminar")
	public String delete(Map<String, Object> model, @RequestParam(value="id") Integer id) {
		try {
			if(id!=null && id>0) {
				aService.delete(id);
				model.put("actor",new Actor()); //importante
				model.put("actorbusqueda", new Actor()); //importante
				model.put("listActors", aService.findAllSortIdAsc());
				model.put("mensaje", "Se eliminó un actor correctamente");
				model.put("opt", 1);
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			model.put("mensaje", "No se puede eliminar, debido a que este actor se encuentra registrado en una o más películas");
			model.put("listActors", aService.findAllSortIdAsc());
			model.put("actor", new Actor());
			model.put("actorbusqueda", new Actor());
			model.put("opt", 1);
		}
		return "listActor";
	}
	
	@RequestMapping("/listar")
	public String list(Map<String, Object> model) {
		model.put("listActors", aService.findAllSortIdAsc());
		model.put("baseActor", new Actor());
		model.put("actor",new Actor());
		model.put("actorbusqueda", new Actor()); 
		model.put("opt", 1);
		return "listActor";
	}
	
	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model,@ModelAttribute("actorbusqueda") Actor actor) 
		throws ParseException
	{
		List<Actor>listActors;
		listActors = aService.findByName(actor.getNameActor());
		
		model.put("actor", new Actor());
		model.put("listActors", listActors);
		model.put("opt", 1);
		return "listActor";
	}
}
