package pe.edu.upc.spring.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
//import java.util.Map;
import java.util.Optional;

import javax.persistence.criteria.Order;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//import com.sun.el.parser.ParseException;

import pe.edu.upc.spring.model.Actor;
import pe.edu.upc.spring.model.Genre;
import pe.edu.upc.spring.model.Movie;
import pe.edu.upc.spring.model.Orders;
import pe.edu.upc.spring.model.Users;
import pe.edu.upc.spring.model.MovieActor;
import pe.edu.upc.spring.model.MovieGenre;
import pe.edu.upc.spring.model.OrderDetail;
import pe.edu.upc.spring.service.ICriticReviewService;
//import pe.edu.upc.spring.service.IDirectorService;
//import pe.edu.upc.spring.service.IGenreService; //Para listar géneros
import pe.edu.upc.spring.service.IMovieActorService;
import pe.edu.upc.spring.service.IMovieGenreService;
import pe.edu.upc.spring.service.IMovieService;
import pe.edu.upc.spring.service.IOrderDetailService;
import pe.edu.upc.spring.service.IOrderService;
import pe.edu.upc.spring.service.IUserReviewService;
import pe.edu.upc.spring.service.IUserService;

@Controller
@RequestMapping("/orden")
public class OrderController {
	@Autowired
	private IOrderService oService;
	@Autowired
	private IOrderDetailService odService;
	@Autowired
	private IMovieService mService;
	@Autowired
	private IUserService uService;

	// @Autowired
	// private IDirectorService dService;

	// Para listar géneros y actores
	// @Autowired
	// private IGenreService gService;
	@Autowired
	private IMovieGenreService mgService;
	@Autowired
	private IMovieActorService maService;

	// Para mostrar el FilmstarsRating
	@Autowired
	private IUserReviewService urService;
	@Autowired
	private ICriticReviewService crService;

	public void movieData(Model model, int id, int idUser) {
		// Actors y Genres
		List<Actor> listActors = new ArrayList<Actor>();
		List<Genre> listGenres = new ArrayList<Genre>();

		List<MovieActor> listMovieActors = maService.findByMovieId(id);
		List<MovieGenre> listMovieGenres = mgService.findByMovieId(id);
		for (int i = 0; i < listMovieActors.size(); i++) {
			listActors.add(listMovieActors.get(i).getActor());
		}
		for (int i = 0; i < listMovieGenres.size(); i++) {
			listGenres.add(listMovieGenres.get(i).getGenre());
		}
		// Final
		Optional<Movie> objMovie = mService.findById(id);
		if (objMovie.isPresent())
			objMovie.ifPresent(o -> model.addAttribute("movie", o));

		model.addAttribute("listActors", listActors);
		model.addAttribute("listGenres", listGenres);
		model.addAttribute("listMovieActors", listMovieActors);
		// LISTAR REVIEWS
		model.addAttribute("filmstarsRate", urService.findFilmstarsRate(id));
		model.addAttribute("userReviews", urService.findByMovieId(id).size());
		model.addAttribute("criticReviews", crService.findByMovieId(id).size());
		//
		if (urService.findByMovieUserId(id, idUser).isEmpty())
			model.addAttribute("userReview", "..");
		else
			model.addAttribute("userReview", urService.findByMovieUserId(id, idUser).get(0).getQuantityUserReview());
	}

	@RequestMapping("/carritoCompras")
	public String shoppingCart(Model model, @RequestParam(value = "idUser") Integer idUser,
			@RequestParam(value = "idOrder") Integer idOrder) {
		model.addAttribute("listOrderDetails", odService.findByUserOrderId(idUser, idOrder));
		return "shoppingCart";
	}

	@RequestMapping("/eliminar")
	public String deleteOrderDetail(Map<String, Object> model, @RequestParam(value = "id") Integer id,
			@RequestParam(value = "idUser") Integer idUser, @RequestParam(value = "idOrder") Integer idOrder) {
		try {
			if (id != null && id > 0) {
				OrderDetail orderDetail = odService.findById(id).get();
				Movie movie = new Movie();
				movie = mService.findById(orderDetail.getMovie().getIdMovie()).get();
				movie.setStockMovie(movie.getStockMovie() + orderDetail.getQuantityOrderDetail());

				odService.delete(id);
				model.put("listOrderDetails", odService.findByUserOrderId(idUser, idOrder));
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			model.put("Mensaje", "Ocurrio un error");
			model.put("listOrderDetails", odService.findByUserOrderId(idUser, idOrder));
		}
		return "shoppingCart";
	}

	@RequestMapping("/formaPago")
	public String wayToPay(Map<String, Object> model) {
		return "wayPay";
	}

	@RequestMapping("/formaPago/online")
	public String onlinePayment(Model model, HttpSession session) {

		model.addAttribute("order", session.getAttribute("order"));
		return "onlinePayment";
	}
	
	@RequestMapping("/reporte")
	public String report(Map<String, Object> model) {
		model.put("listOrderDetails", odService.findAllSortIdDesc());
		model.put("orderdetailbusqueda", new OrderDetail()); 
		return "reports";
	}
	
	@RequestMapping("/historial")
	public String userOrderHistory(Map<String, Object> model, @RequestParam(value = "idUser") Integer idUser) {
		model.put("listOrderDetails", odService.findByUserId(idUser));
		return "userOrderHistory";
	}
	
	@RequestMapping("/buscar")
	public String find(@ModelAttribute("orderdetailbusqueda") OrderDetail orderdetailbusqueda, Map<String, Object> model) {
		
		List<OrderDetail>resultado;

		if( orderdetailbusqueda.getOrder().getUser().getNameUser() != "" && orderdetailbusqueda.getMovie().getNameMovie()!="" 
				&& orderdetailbusqueda.getOrder().getIdOrder() != 0) {
			resultado = odService.findFullComb(orderdetailbusqueda.getOrder().getUser().getNameUser(), orderdetailbusqueda.getMovie().getNameMovie(), orderdetailbusqueda.getOrder().getIdOrder());
			model.put("listOrderDetails", resultado);
			if(resultado.size()>0)
				model.put("mensaje1", "Resultados obtenidos");
			else
				model.put("mensaje2", "No se encontraron resultados");
		}
		else {
			if(orderdetailbusqueda.getOrder().getUser().getNameUser() != "" && orderdetailbusqueda.getMovie().getNameMovie()!="" ) {
				resultado = odService.findComb1(orderdetailbusqueda.getOrder().getUser().getNameUser(), orderdetailbusqueda.getMovie().getNameMovie());
				model.put("listOrderDetails", resultado);
				if(resultado.size()>0)
					model.put("mensaje1", "Resultados obtenidos");
				else
					model.put("mensaje2", "No se encontraron resultados");
			}
			else {
				if(orderdetailbusqueda.getOrder().getUser().getNameUser() != "" && orderdetailbusqueda.getOrder().getIdOrder() != 0) {
					resultado = odService.findComb2(orderdetailbusqueda.getOrder().getUser().getNameUser(), orderdetailbusqueda.getOrder().getIdOrder());
					model.put("listOrderDetails", resultado);
					if(resultado.size()>0)
						model.put("mensaje1", "Resultados obtenidos");
					else
						model.put("mensaje2", "No se encontraron resultados");
				}
				else {
					if(orderdetailbusqueda.getMovie().getNameMovie()!="" && orderdetailbusqueda.getOrder().getIdOrder() != 0) {
						resultado = odService.findComb3(orderdetailbusqueda.getMovie().getNameMovie(), orderdetailbusqueda.getOrder().getIdOrder());
						model.put("listOrderDetails", resultado);
						if(resultado.size()>0)
							model.put("mensaje1", "Resultados obtenidos");
						else
							model.put("mensaje2", "No se encontraron resultados");
					}
					else {
						if(orderdetailbusqueda.getMovie().getNameMovie() != "" ) {
							resultado = odService.findByMovieName(orderdetailbusqueda.getMovie().getNameMovie());
							model.put("listOrderDetails", resultado);
							if(resultado.size()>0)
								model.put("mensaje1", "Resultados obtenidos");
							else
								model.put("mensaje2", "No se encontraron resultados");
						}
						else{
							if(orderdetailbusqueda.getOrder().getUser().getNameUser() != "") {
								resultado = odService.findByUserName(orderdetailbusqueda.getOrder().getUser().getNameUser());
								model.put("listOrderDetails", resultado);
								if(resultado.size()>0)
									model.put("mensaje1", "Resultados obtenidos");
								else
									model.put("mensaje2", "No se encontraron resultados");
							} 
							else{
								resultado = odService.findByOrderId(orderdetailbusqueda.getOrder().getIdOrder());
								model.put("listOrderDetails", resultado);
								if(resultado.size()>0)
									model.put("mensaje1", "Resultados obtenidos");
								else
									model.put("mensaje2", "No se encontraron resultados");
							} 
						}
					}
				}
			}
		}
		
		
		return "reports";
	}
	
	
	

	@RequestMapping("/formaPago/online/comprar")
	public String buy(Model model, @RequestParam(value = "idUser") Integer idUser,
			@RequestParam(value = "idOrder") Integer idOrder, HttpSession session) {
		Orders order = (Orders) session.getAttribute("order");
		oService.save(order); //SE GUARDA POR FIN
		
		List<OrderDetail> listOD = odService.findByUserOrderId(idUser, idOrder);

		double importe = 0;

		for (int i = 0; i < listOD.size(); i++) {
			importe = importe + listOD.get(i).getQuantityOrderDetail() * listOD.get(i).getMovie().getPriceMovie();
		}
		
		SimpleDateFormat fecha = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat hora = new SimpleDateFormat("HH:mm:ss");

		model.addAttribute("importe", importe);
		model.addAttribute("codigoOrden", order.getIdOrder());
		model.addAttribute("tarjeta", order.getNumCardOrder());
		model.addAttribute("fecha", fecha.format(order.getDateOrder()));
		model.addAttribute("hora", hora.format(order.getTimeOrder()));

		return "onlinePaymentSummary";
	}
	
	@RequestMapping("/formaPago/onDelivery/efectivo/comprar")
	public String buy2(Model model, @RequestParam(value = "idUser") Integer idUser,
			@RequestParam(value = "idOrder") Integer idOrder, HttpSession session) {
		Orders order = (Orders) session.getAttribute("order");
		oService.save(order); //SE GUARDA POR FIN
		
		List<OrderDetail> listOD = odService.findByUserOrderId(idUser, idOrder);

		double importe = 0;

		for (int i = 0; i < listOD.size(); i++) {
			importe = importe + listOD.get(i).getQuantityOrderDetail() * listOD.get(i).getMovie().getPriceMovie();
		}
		
		SimpleDateFormat fecha = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat hora = new SimpleDateFormat("HH:mm:ss");
		
		model.addAttribute("importe", importe);
		model.addAttribute("codigoOrden", order.getIdOrder());
		model.addAttribute("fecha", fecha.format(order.getDateOrder()));
		model.addAttribute("hora", hora.format(order.getTimeOrder()));

		return "onDeliveryPaymentSummary";
	}
	@RequestMapping("/formaPago/onDelivery/tarjeta/comprar")
	public String buy3(Model model, @RequestParam(value = "idUser") Integer idUser,
			@RequestParam(value = "idOrder") Integer idOrder, HttpSession session) {
		Orders order = (Orders) session.getAttribute("order");
		oService.save(order); //SE GUARDA POR FIN
		
		List<OrderDetail> listOD = odService.findByUserOrderId(idUser, idOrder);

		double importe = 0;

		for (int i = 0; i < listOD.size(); i++) {
			importe = importe + listOD.get(i).getQuantityOrderDetail() * listOD.get(i).getMovie().getPriceMovie();
		}
		
		SimpleDateFormat fecha = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat hora = new SimpleDateFormat("HH:mm:ss");

		model.addAttribute("importe", importe);
		model.addAttribute("codigoOrden", order.getIdOrder());
		model.addAttribute("fecha", fecha.format(order.getDateOrder()));
		model.addAttribute("hora", hora.format(order.getTimeOrder()));

		return "onDeliveryPaymentSummary";
	}
	

	@RequestMapping("/formaPago/online/procesarTarjeta")
	public String onlinePaymentCardProcess(@Valid @ModelAttribute("order") Orders order, BindingResult binRes,
			@RequestParam(value = "idUser") Integer idUser, @RequestParam(value = "idOrder") Integer idOrder,
			Model model, HttpSession session) throws Exception {
		if (binRes.hasErrors()) {
			
			return "onlinePayment";
		}
		else {
			System.out.println("ORDER ID: " + order.getIdOrder());
			System.out.println("ORDER ID: " + order.getIdOrder());
			System.out.println("ORDER ID: " + order.getIdOrder());

			List<OrderDetail> listOD = odService.findByUserOrderId(idUser, idOrder);

			double importe = 0;

			for (int i = 0; i < listOD.size(); i++) {
				importe = importe + listOD.get(i).getQuantityOrderDetail() * listOD.get(i).getMovie().getPriceMovie();
			}

			// AGREGAR TIPO DE PAGO

			Date date = new Date();
			Date time = new Date();
			order.setFormOrder(1);
			order.setDateOrder(date);
			order.setTimeOrder(time);
			order.setStatusOrder(1);
			
			SimpleDateFormat fecha = new SimpleDateFormat("dd-MM-yyyy");
			SimpleDateFormat hora = new SimpleDateFormat("HH:mm:ss");
			
			// oService.save(order); //NO GUARDARLO
			session.setAttribute("order", order);

			model.addAttribute("importe", importe);
			model.addAttribute("codigoOrden", order.getIdOrder());
			model.addAttribute("tarjeta", order.getNumCardOrder());
			model.addAttribute("fecha", fecha.format(order.getDateOrder()));
			model.addAttribute("hora", hora.format(order.getTimeOrder()));

			return "onlinePaymentConfirmation";
		}
		
	}

	@RequestMapping("/formaPago/onDelivery")
	public String onDeliveryPayment(Model model, HttpSession session) {
		model.addAttribute("order", session.getAttribute("order"));
		return "onDeliveryPayment";
	}
	
	
	@RequestMapping("/formaPago/onDelivery/tarjeta")
	public String onDeliveryPaymentTarjeta(Model model, @RequestParam(value = "idUser") Integer idUser, 
			@RequestParam(value = "idOrder") Integer idOrder, HttpSession session) {
		//model.addAttribute("order", session.getAttribute("order"));
		
		Orders order = (Orders)session.getAttribute("order");
		
		List<OrderDetail> listOD = odService.findByUserOrderId(idUser, idOrder);

		double importe = 0;

		for (int i = 0; i < listOD.size(); i++) {
			importe = importe + listOD.get(i).getQuantityOrderDetail() * listOD.get(i).getMovie().getPriceMovie();
		}

		// AGREGAR TIPO DE PAGO

		Date date = new Date();
		Date time = new Date();
		order.setFormOrder(2);  // 0 - 1 - 2
		order.setMethodOrder(2); //TARJETA: 2
		order.setDateOrder(date);
		order.setTimeOrder(time);
		order.setStatusOrder(1);

		// oService.save(order); //NO GUARDARLO
		session.setAttribute("order", order);

		SimpleDateFormat fecha = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat hora = new SimpleDateFormat("HH:mm:ss");
		
		model.addAttribute("importe", importe);
		model.addAttribute("codigoOrden", order.getIdOrder());
		model.addAttribute("fecha", fecha.format(order.getDateOrder()));
		model.addAttribute("hora", hora.format(order.getTimeOrder()));

		return "onDeliveryPaymentConfirmation2";
	}
	
	@RequestMapping("/formaPago/onDelivery/efectivo")
	public String onDeliveryPaymentEfectivo(Model model, @RequestParam(value = "idUser") Integer idUser, 
			@RequestParam(value = "idOrder") Integer idOrder, HttpSession session) {
		
		Orders order = (Orders)session.getAttribute("order");
		
		List<OrderDetail> listOD = odService.findByUserOrderId(idUser, idOrder);

		double importe = 0;

		for (int i = 0; i < listOD.size(); i++) {
			importe = importe + listOD.get(i).getQuantityOrderDetail() * listOD.get(i).getMovie().getPriceMovie();
		}

		// AGREGAR TIPO DE PAGO

		Date date = new Date();
		Date time = new Date();
		order.setFormOrder(2);  // 0 - 1 - 2
		order.setMethodOrder(1);//EFECTIVO: 1
		order.setDateOrder(date);
		order.setTimeOrder(time);
		order.setStatusOrder(1);

		// oService.save(order); //NO GUARDARLO
		session.setAttribute("order", order);

		SimpleDateFormat fecha = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat hora = new SimpleDateFormat("HH:mm:ss");
		
		model.addAttribute("importe", importe);
		model.addAttribute("codigoOrden", order.getIdOrder());
		model.addAttribute("fecha", fecha.format(order.getDateOrder()));
		model.addAttribute("hora", hora.format(order.getTimeOrder()));

		return "onDeliveryPaymentConfirmation";
	}
	

	@RequestMapping("/registrar")
	public String register(@Valid @ModelAttribute("orderDetail") OrderDetail orderDetail, BindingResult binRes,
			@RequestParam(value = "idMovie") Integer idMovie, @RequestParam(value = "idUser") Integer idUser,
			HttpSession session, Model model) throws Exception {
		if (binRes.hasErrors()) {

			System.out.println("HUBO ERROR");
			movieData(model, idMovie, idUser);
			return "movieUser";
		} else {
			System.out.println("NOOOO HUBO ERROR");
			Movie objMovie = mService.findById(idMovie).get();
			int purchaseStock = orderDetail.getQuantityOrderDetail();

			if (purchaseStock > objMovie.getStockMovie()) { // SI SE PASÓ DEL STOCK
				//model.addAttribute("mensaje", "Ha excedido del stock disponible");
				movieData(model, idMovie, idUser);
				return "movieUser";
			} else {// SI NO SE PASÓ DEL STOCK, PROCEDEMOS

				// VERIFICAMOS SI HAY UNA ORDEN CON ESTADO 0 PARA EL USUARIO..!!!

				if (oService.findIncompleteOrder(idUser) == null) { // NO HAY UNA ORDEN PENDIENTE, ENTONCES CREAMOS UNA
																	// PARA ESTE USUARIO
					// ENCONTRAMOS AL USUARIO
					Users user = new Users();
					user = uService.findById(idUser).get();
					// CREAMOS UNA ORDEN
					Orders order = new Orders();
					order.setStatusOrder(0);
					order.setUser(user);
					oService.save(order); // SE GUARDA LA ORDEN
					session.setAttribute("order", order); // LO MANDAMOS A LA SESIÓN, DESPUÉS LO CARGAREMOS AL INICIAR
															// SESIÓN
					// SE AÑADE A ORDER DETAIL
					OrderDetail objOrderDetail = new OrderDetail();
					objOrderDetail.setMovie(objMovie);
					objOrderDetail.setOrder(order);
					objOrderDetail.setQuantityOrderDetail(purchaseStock);
					Movie movie = new Movie();
					movie = mService.findById(idMovie).get();
					movie.setStockMovie(movie.getStockMovie() - purchaseStock);
					odService.save(objOrderDetail);

					model.addAttribute("mensaje", "Se añadió correctamente al carrito");
					movieData(model, idMovie, idUser);
					
					model.addAttribute("moviebusqueda", new Movie());//OJITO
					return "movieUser";
				} else { // HAY UNA ORDEN PENDIENTE
					Orders order = oService.findIncompleteOrder(idUser);
					OrderDetail objOrderDetail = new OrderDetail();
					objOrderDetail.setMovie(objMovie);
					objOrderDetail.setOrder(order);
					objOrderDetail.setQuantityOrderDetail(purchaseStock);
					Movie movie = new Movie();
					movie = mService.findById(idMovie).get();
					movie.setStockMovie(movie.getStockMovie() - purchaseStock);
					odService.save(objOrderDetail);

					session.setAttribute("order", order); // LO MANDAMOS A LA SESIÓN, DESPUÉS LO CARGAREMOS AL INICIAR
															// SESIÓN

					model.addAttribute("mensaje", "Se añadió correctamente al carrito");
					movieData(model, idMovie, idUser);
					
					model.addAttribute("moviebusqueda", new Movie());//OJITO
					return "movieUser";
				}
			}
		}
	}
}
