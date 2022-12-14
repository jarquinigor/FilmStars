package pe.edu.upc.spring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pe.edu.upc.spring.model.OrderDetail;

public interface IOrderDetailService {
	public boolean save(OrderDetail orderDetail);
	public void delete(int idOrderDetail);
	public List<OrderDetail> findAllSortIdDesc();
	public Optional<OrderDetail>findById(int idOrderDetail);
	public List<OrderDetail> findByUserOrderId(int idUser, int idOrder);
	//movieName
	public List<OrderDetail> findByMovieName(String nameMovie);
	//userName
	public List<OrderDetail> findByUserName(String nameUser);
	//idOrder
	public List<OrderDetail> findByOrderId(int idOrder);
	
	//COMBINADAS
	public List<OrderDetail> findFullComb(String nameUser, String nameMovie, int idOrder);
	public List<OrderDetail> findComb1(String nameUser, String nameMovie);
	public List<OrderDetail> findComb2(String nameUser, int idOrder);
	public List<OrderDetail> findComb3(String nameMovie, int idOrder);
	
	//FINAL
	public List<OrderDetail> findByUserId(int idUser);
}
