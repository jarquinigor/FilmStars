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

import pe.edu.upc.spring.model.Genre;
import pe.edu.upc.spring.service.IGenreService;

@Controller
@RequestMapping("/genero")
public class GenreController {
	@Autowired
	private IGenreService gService;
	
	@RequestMapping("/registrar")
	public String register(@Valid @ModelAttribute("genre") Genre genre, BindingResult binRes, @RequestParam(value="opt") Integer opt, 
			@RequestParam(value="baseGenre") String baseGenre, Model model) 
		throws Exception
	{
		if (binRes.hasErrors()) {
			model.addAttribute("listGenres", gService.findAllSortIdAsc());
			model.addAttribute("genrebusqueda", new Genre());
			return "listGenre";
		}
		else {
			int rpta = gService.save(genre, baseGenre, opt);
			if(opt == 1) {				
				if(rpta == 0) {
					model.addAttribute("mensaje", "Se registró un género correctamente");
					model.addAttribute("genre", new Genre());
				}
				if(rpta == 1) {
					model.addAttribute("msjName", "Ya existe este nombre de género");
					model.addAttribute("genre", genre);
				}					
				model.addAttribute("listGenres", gService.findAllSortIdAsc());
				model.addAttribute("genrebusqueda", new Genre()); 
				model.addAttribute("opt", 1);
				
				System.out.println("opt: "+opt);
				System.out.println("rpta: " + rpta);
				return "listGenre";
			}		
			else {
				if(rpta == 0) {
					model.addAttribute("mensaje", "No se realizó ningún cambio");
					model.addAttribute("genre", new Genre());
					model.addAttribute("opt", 1);
				}
				if(rpta == 1 || rpta==2) {
					model.addAttribute("mensaje", "Se actualizó un género correctamente");
					model.addAttribute("genre", new Genre());
					model.addAttribute("opt", 1);
				}
				if(rpta == 3) {
					model.addAttribute("msjName", "Ya existe este nombre de género");
					model.addAttribute("genre", genre);
					model.addAttribute("opt", 2);
				}
				
				model.addAttribute("listGenres", gService.findAllSortIdAsc());
				model.addAttribute("genrebusqueda", new Genre());
				
				
				System.out.println("opt: "+opt);
				System.out.println("rpta: " + rpta);
				System.out.println("Genre: " + genre.getNameGenre());
				System.out.println("baseGenre: " + baseGenre);
				return "listGenre";
			}
		}
	}
	
	@RequestMapping("/modificar/{id}")
	public String modify(@PathVariable int id, Model model, RedirectAttributes objRedir) 
		throws ParseException
	{
		Optional<Genre> objGenre = gService.findById(id);
		if (objGenre == null) {
			objRedir.addFlashAttribute("mensaje", "Rochezaso");
			return "redirect:/genero/listar";
		}
		else {
			model.addAttribute("genre", objGenre);
			model.addAttribute("baseGenre", objGenre.get().getNameGenre());
			model.addAttribute("genrebusqueda", new Genre());
			model.addAttribute("listGenres", gService.findAllSortNameAsc());
			model.addAttribute("opt", 2);
			return "listGenre";
		}
	}
	
	@RequestMapping("/eliminar")
	public String delete(Map<String, Object> model, @RequestParam(value="id") Integer id) {
		try {
			if(id!=null && id>0) {
				gService.delete(id);
				model.put("genre", new Genre());
				model.put("genrebusqueda", new Genre());
				model.put("listGenres", gService.findAllSortNameAsc());
				model.put("opt", 1);
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			model.put("mensaje", "No se puede eliminar, debido a que este género se encuentra registrado en una o más películas");
			model.put("genre", new Genre());
			model.put("genrebusqueda", new Genre());
			model.put("listGenres", gService.findAllSortNameAsc());
			model.put("opt", 1);
		}
		return "listGenre";
	}
	
	@RequestMapping("/listar")
	public String list(Map<String, Object> model) {
		model.put("listGenres", gService.findAllSortNameAsc());
		model.put("genre",new Genre());
		model.put("baseGenre", new Genre());
		model.put("genrebusqueda", new Genre());
		model.put("opt", 1);
		return "listGenre";
	}
	
	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model,@ModelAttribute("genrebusqueda") Genre genre) 
		throws ParseException
	{
		List<Genre>listGenres;
		listGenres = gService.findByName(genre.getNameGenre());
		
		model.put("genre", new Genre());
		model.put("listGenres", listGenres);
		model.put("opt", 1);
		return "listGenre";
	}
}
