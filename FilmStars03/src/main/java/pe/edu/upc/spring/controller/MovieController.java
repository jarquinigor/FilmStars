package pe.edu.upc.spring.controller;

import java.util.ArrayList;
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
import pe.edu.upc.spring.model.Genre;
import pe.edu.upc.spring.model.Movie;
import pe.edu.upc.spring.model.MovieActor;
import pe.edu.upc.spring.model.MovieGenre;
import pe.edu.upc.spring.model.OrderDetail;
import pe.edu.upc.spring.service.ICriticReviewService;
import pe.edu.upc.spring.service.IDirectorService;
import pe.edu.upc.spring.service.IGenreService; //Para listar géneros
import pe.edu.upc.spring.service.IMovieActorService;
import pe.edu.upc.spring.service.IMovieGenreService;
import pe.edu.upc.spring.service.IMovieService;
import pe.edu.upc.spring.service.IUserReviewService;

@Controller
@RequestMapping("/pelicula")
public class MovieController {
	@Autowired
	private IMovieService mService;
	
	@Autowired
	private IDirectorService dService;
	
	//Para listar géneros y actores
	@Autowired
	private IGenreService gService;
	@Autowired
	private IMovieGenreService mgService;
	@Autowired
	private IMovieActorService maService;
	
	//Para mostrar el FilmstarsRating
	@Autowired
	private IUserReviewService urService;
	@Autowired
	private ICriticReviewService crService;
	
	@RequestMapping("/registrar")
	public String register(@Valid @ModelAttribute("movie") Movie movie, BindingResult binRes, @RequestParam(value="opt") Integer opt, Model model) 
		throws Exception
	{
		if (binRes.hasErrors()) {
			model.addAttribute("moviebusqueda", new Movie()); //importante
			model.addAttribute("listDirectors",dService.findAllSortIdAsc());
			model.addAttribute("listMovies", mService.findAllSortIdAsc());
			model.addAttribute("opt", 1);
			return "listMovie";
		}		
		else {
			int rpta = mService.register(movie, opt);
			if(opt == 1) {
				if(rpta == 0) {
					return "redirect:/pelicula/listarSuccess";
				}
				if(rpta == 1) {
					model.addAttribute("msjName", "Ya existe este nombre de película");			
					model.addAttribute("movie", movie); //SI SE REPITIÓ ALGUN VALOR, NO SE REGISTRA	
					model.addAttribute("listDirectors",dService.findAllSortIdAsc());
					model.addAttribute("listMovies", mService.findAllSortIdAsc());
					model.addAttribute("moviebusqueda", new Movie()); 
					model.addAttribute("opt", 1);			
				}				
			}
			return "listMovie";
		}
	}
	
	@RequestMapping("/actualizar")
	public String update(@Valid @ModelAttribute("movie") Movie movie, BindingResult binRes, @RequestParam(value="opt") Integer opt, 
			@RequestParam(value="baseMovieId") Integer baseMovieId, Model model) 
		throws Exception
	{
		if (binRes.hasErrors()) {
			model.addAttribute("moviebusqueda", new Movie()); //importante
			model.addAttribute("listDirectors",dService.findAllSortIdAsc());
			model.addAttribute("listMovies", mService.findAllSortIdAsc());
			model.addAttribute("opt", 1);
			return "listMovie";
		}		
		else {
			int rpta = mService.save(movie, baseMovieId, opt);
			if(opt==2) {
				if(rpta == 0) { //NO SE EDITÓ NADA
					model.addAttribute("mensaje", "No se realizó ningún cambio");
					model.addAttribute("listDirectors",dService.findAllSortIdAsc());
					model.addAttribute("listMovies", mService.findAllSortIdAsc());
					model.addAttribute("movie", new Movie()); 
					model.addAttribute("moviebusqueda", new Movie()); 
					model.addAttribute("opt", 1);				
				}
				if(rpta == 1 || rpta ==3) { //SE ACTUALIZÓ LA PELICULA
					model.addAttribute("mensaje", "Se actualizó una película correctamente");
					model.addAttribute("listDirectors",dService.findAllSortIdAsc());
					model.addAttribute("listMovies", mService.findAllSortIdAsc());
					model.addAttribute("movie", new Movie()); 
					model.addAttribute("moviebusqueda", new Movie()); 
					model.addAttribute("opt", 1);
				}
				if(rpta == 2) { //YA EXISTE ESTE NOMBRE DE PELICULA
					model.addAttribute("msjName", "Ya existe este nombre de película");						
					model.addAttribute("listDirectors",dService.findAllSortIdAsc());
					model.addAttribute("listMovies", mService.findAllSortIdAsc());
					model.addAttribute("movie", movie);
					model.addAttribute("moviebusqueda", new Movie()); 
					model.addAttribute("opt", 2);
				}					
			}
			return "listMovie";	
		}
	}
	
	@RequestMapping("/aplicarStock")
	public String stock(@Valid @ModelAttribute("movie") Movie movie, BindingResult binRes, @RequestParam(value="opt") Integer opt, 
			@RequestParam(value="baseMovieId") Integer baseMovieId, Model model) 
		throws Exception
	{				
		if (binRes.hasErrors()) {
			model.addAttribute("moviebusqueda", new Movie()); //importante
			model.addAttribute("listDirectors",dService.findAllSortIdAsc());
			model.addAttribute("listMovies", mService.findAllSortIdAsc());
			
			System.out.println("HUBO ERROR CON EL STOCK 0");
			
			return "movieSupply";
		}		
		else {
			int incrementoStock = movie.getStockMovie();
			Optional<Movie> objMovie = mService.findById(movie.getIdMovie());
			Movie movie1 = objMovie.get();
			int stockActual = movie1.getStockMovie();
			
			System.out.println("STOCK ACTUAL: "+ stockActual);
			
			
			if(stockActual + incrementoStock > 200) {
				model.addAttribute("mensaje", "Se ha excedido en el stock máximo de 200 blu-rays disponibles");
				model.addAttribute("movie", movie);
				return "movieSupply";
			}
			else {
				movie1.setStockMovie(movie1.getStockMovie()+incrementoStock);
				System.out.println("STOCK SUMINISTRADO: "+ (stockActual+incrementoStock));
				System.out.println("SI LLEGÓ BIEN");
				
				mService.save(movie1,baseMovieId,opt);	
				model.addAttribute("mensaje", "Se ha actualizado stock de esta película");
				movie.setStockMovie(0);
				model.addAttribute("movie", movie);
				return "movieSupply";
			}			
			
		}
	}
	
	@RequestMapping("/suministrar/{id}")
	public String provide(@PathVariable int id, Model model, RedirectAttributes objRedir) 
		throws ParseException
	{
		Optional<Movie> objMovie = mService.findById(id);
		if (objMovie == null) {
			objRedir.addFlashAttribute("mensaje", "Ocurrio un roche, LUZ ROJA");
			return "redirect:/pelicula/listar";
		}
		else {
			Movie movie = objMovie.get();
			movie.setStockMovie(0);
			model.addAttribute("movie", movie);
			model.addAttribute("opt", 1);
			model.addAttribute("baseMovieId", movie.getIdMovie());
			return "movieSupply";                   
		}
	}	
	
	@RequestMapping("/modificar/{id}")
	public String modify(@PathVariable int id, Model model, RedirectAttributes objRedir) 
		throws ParseException
	{
		Optional<Movie> objMovie = mService.findById(id);
		if (objMovie == null) {
			objRedir.addFlashAttribute("mensaje", "Ocurrio un roche, LUZ ROJA");
			return "redirect:/pelicula/listar";
		}
		else {
			model.addAttribute("moviebusqueda", new Movie());
			model.addAttribute("listDirectors", dService.findAllSortIdAsc());
			model.addAttribute("listMovies", mService.findAllSortIdAsc());

			model.addAttribute("movie", objMovie.get());
			model.addAttribute("opt", 2);
			
			model.addAttribute("baseMovieId", objMovie.get().getIdMovie());
			return "listMovieUpdate";                   
		}
	}
	
	@RequestMapping("/eliminar")
	public String delete(Map<String, Object> model, @RequestParam(value="id") Integer id) {
		try {
			if(id!=null && id>0) {
				mService.delete(id);
				model.put("mensaje", "Se ha eliminado una película correctamente");
				model.put("movie",new Movie()); //importante
				model.put("moviebusqueda", new Movie()); //importante
				model.put("listDirectors",dService.findAllSortIdAsc());
				model.put("listMovies", mService.findAllSortIdAsc());
				model.put("opt", 1);
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			model.put("mensaje", "No se puede eliminar, debido a que esta película se encuentra registrado en una o más órdenes");
			model.put("movie",new Movie()); //importante
			model.put("moviebusqueda", new Movie()); //importante
			model.put("listDirectors",dService.findAllSortIdAsc());
			model.put("listMovies", mService.findAllSortIdAsc());
			model.put("opt", 1);
		}
		return "listMovie";
	}
	
	@RequestMapping("/listar")
	public String list(Map<String, Object> model) {
		model.put("listDirectors",dService.findAllSortIdAsc());
		model.put("listMovies", mService.findAllSortIdAsc());
		model.put("movie",new Movie());
		model.put("moviebusqueda", new Movie()); 
		model.put("opt", 1);
		return "listMovie";
	}
	
	@RequestMapping("/listarSuccess")
	public String listSuccess(Model model) {
		model.addAttribute("mensaje", "Se registró una película correctamente");
		model.addAttribute("listDirectors", dService.findAllSortIdAsc());
		model.addAttribute("listMovies", mService.findAllSortIdAsc());
		model.addAttribute("movie", new Movie());
		model.addAttribute("moviebusqueda", new Movie());
		model.addAttribute("opt", 1);
		return "listMovie";
	}
	
	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model,@ModelAttribute("moviebusqueda") Movie movie) 
		throws ParseException
	{
		List<Movie>listMovies;
		listMovies = mService.findByName(movie.getNameMovie());
		
		model.put("movie", new Movie());
		model.put("listDirectors",dService.findAllSortIdAsc());
		model.put("listMovies", listMovies);
		model.put("opt", 1);
		return "listMovie";
	}
	
	@RequestMapping("/buscarPelicula")//ESTE ES
	public String findMovie(Model model,@ModelAttribute("moviebusqueda") Movie movie) 
			throws ParseException
		{
			List<Movie>listMovies;
			listMovies = mService.findByName(movie.getNameMovie());
			if(listMovies.size()>0) {
				model.addAttribute("mensaje1","Resultados obtenidos");
			}else {
				model.addAttribute("mensaje2","No se encontraron resultados");
			}
			
			model.addAttribute("listMovies", listMovies);
			model.addAttribute("listGenres", gService.findAllSortNameAsc());
			return "moviesUser";
		}
	
	@RequestMapping("/verPeliculas") //USUARIO
	public String moviesUser(Model model) {
		model.addAttribute("listMovies", mService.findAllSortNameAsc());
		model.addAttribute("listGenres", gService.findAllSortNameAsc());
		model.addAttribute("moviebusqueda", new Movie());
		return "moviesUser";
	}
	
	@RequestMapping("/verPelicula")  //USUARIO
	public String movieUser(Model model, @RequestParam(value="id") Integer id, @RequestParam(value="idUser") Integer idUser) {
		//Actors y Genres
		List<Actor>listActors = new ArrayList<Actor>();
		List<Genre>listGenres = new ArrayList<Genre>();
		
		List<MovieActor>listMovieActors = maService.findByMovieId(id);
		List<MovieGenre>listMovieGenres = mgService.findByMovieId(id);
		
		for (int i = 0; i < listMovieActors.size(); i++) {
			listActors.add(listMovieActors.get(i).getActor());
		}
		for (int i = 0; i < listMovieGenres.size(); i++) {
			listGenres.add(listMovieGenres.get(i).getGenre());
		}

		//Final
		Optional<Movie> objMovie = mService.findById(id);
		if(objMovie.isPresent())
			objMovie.ifPresent(o -> model.addAttribute("movie",o));
		
		model.addAttribute("moviebusqueda", new Movie()); //NUEVO
		
		
		model.addAttribute("listActors",listActors);
		model.addAttribute("listGenres",listGenres);
		model.addAttribute("listMovieActors",listMovieActors);
		model.addAttribute("orderDetail", new OrderDetail());
		//LISTAR REVIEWS 
		model.addAttribute("filmstarsRate",urService.findFilmstarsRate(id));
		model.addAttribute("userReviews",urService.findByMovieId(id).size());
		model.addAttribute("criticReviews",crService.findByMovieId(id).size());
		//
		
		if(urService.findByMovieUserId(id, idUser).isEmpty())
		{
			model.addAttribute("userReview","..");
		}
		else {
			model.addAttribute("userReview",urService.findByMovieUserId(id, idUser).get(0).getQuantityUserReview());
		}

		return "movieUser";
	}
}
