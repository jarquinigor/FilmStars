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

import pe.edu.upc.spring.model.Director;
import pe.edu.upc.spring.service.IDirectorService;

@Controller
@RequestMapping("/director")
public class DirectorController {
	@Autowired
	private IDirectorService dService;
	
	@RequestMapping("/registrar")
	public String register(@Valid @ModelAttribute("director") Director director, BindingResult binRes, @RequestParam(value="opt") Integer opt, 
			@RequestParam(value="baseDirector") String baseDirector, Model model) 
		throws Exception
	{
		if (binRes.hasErrors()) {
			model.addAttribute("listDirectors", dService.findAllSortIdAsc());
			model.addAttribute("directorbusqueda", new Director());
			return "listDirector";
		}		
		else {
			int rpta = dService.save(director, baseDirector, opt);
			if(opt == 1) {				
				if(rpta == 0) {
					model.addAttribute("mensaje", "Se registró un director correctamente");
					model.addAttribute("director", new Director());
				}
				if(rpta == 1) {
					model.addAttribute("msjName", "Ya existe este nombre de director");
					model.addAttribute("director", director);
				}					
				model.addAttribute("listDirectors", dService.findAllSortIdAsc());
				model.addAttribute("directorbusqueda", new Director()); 
				model.addAttribute("opt", 1);
				return "listDirector";
			}		
			else {
				if(rpta == 0) {
					model.addAttribute("mensaje", "No se realizó ningún cambio");
					model.addAttribute("director", new Director());
					model.addAttribute("opt", 1);
				}
				if(rpta == 1 || rpta==2) {
					model.addAttribute("mensaje", "Se actualizó un director correctamente");
					model.addAttribute("director", new Director());
					model.addAttribute("opt", 1);
				}
				if(rpta == 3) {
					model.addAttribute("msjName", "Ya existe este nombre de director");
					model.addAttribute("director", director);
					model.addAttribute("opt", 2);
				}			
				model.addAttribute("listDirectors", dService.findAllSortIdAsc());
				model.addAttribute("directorbusqueda", new Director());		
				
				System.out.println("opt: "+opt);
				System.out.println("rpta: " + rpta);
				System.out.println("Director: " + director.getNameDirector());
				System.out.println("baseDirector: " + baseDirector);
				return "listDirector";
			}
		}
	}
	
	@RequestMapping("/modificar/{id}")
	public String modify(@PathVariable int id, Model model, RedirectAttributes objRedir) 
		throws ParseException
	{
		Optional<Director> objDirector = dService.findById(id);
		if (objDirector == null) {
			objRedir.addFlashAttribute("mensaje", "Rochezaso");
			return "redirect:/director/listar";
		}
		else {
			model.addAttribute("director", objDirector);
			model.addAttribute("baseDirector", objDirector.get().getNameDirector());
			model.addAttribute("directorbusqueda", new Director());
			model.addAttribute("listDirectors", dService.findAllSortIdAsc());
			model.addAttribute("opt", 2);
			return "listDirector";
		}
	}
	
	@RequestMapping("/eliminar")
	public String delete(Map<String, Object> model, @RequestParam(value="id") Integer id) {
		try {
			if(id!=null && id>0) {
				dService.delete(id);
				model.put("director",new Director());
				model.put("directorbusqueda", new Director());
				model.put("listDirectors", dService.findAllSortIdAsc());
				model.put("mensaje", "Se eliminó un director correctamente");
				model.put("opt", 1);
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			model.put("mensaje", "No se puede eliminar, debido a que este director se encuentra registrado en una o más películas");
			model.put("director",new Director());
			model.put("directorbusqueda", new Director());
			model.put("listDirectors", dService.findAllSortIdAsc());
			model.put("opt", 1);
		}
		
		return "listDirector";
	}
	
	@RequestMapping("/listar")
	public String list(Map<String, Object> model) {
		model.put("listDirectors", dService.findAllSortIdAsc());
		model.put("director",new Director());
		model.put("directorbusqueda", new Director());
		model.put("opt", 1);
		return "listDirector";
	}
	
	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model,@ModelAttribute("directorbusqueda") Director director) 
		throws ParseException
	{
		List<Director>listDirectors;
		listDirectors = dService.findByName(director.getNameDirector());
		
		model.put("director", new Director());
		model.put("listDirectors", listDirectors);
		model.put("opt", 1);
		return "listDirector";
	}
}
