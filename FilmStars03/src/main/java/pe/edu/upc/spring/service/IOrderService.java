package pe.edu.upc.spring.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.spring.model.Orders;

public interface IOrderService {
	public boolean save(Orders order);
	//public void update(Orders order);
	public void delete(int idOrder);
	public List<Orders> findByUserSortDateDesc(int idUser);
	public Optional<Orders>findById(int idOrder);
	public Orders findIncompleteOrder (int idUser);
}
