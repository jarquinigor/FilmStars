package pe.edu.upc.spring.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.spring.model.Orders;
import pe.edu.upc.spring.repository.IOrderRepository;
import pe.edu.upc.spring.service.IOrderService;

@Service
public class OrderServiceImpl implements IOrderService {

	@Autowired
	private IOrderRepository dOrder;

	@Override
	@Transactional
	public boolean save(Orders order) {
		Orders objMovie = dOrder.save(order);
		if (objMovie == null)
			return true;
		else
			return false;
	}

	@Override
	@Transactional
	public void delete(int idOrder) {
		dOrder.deleteById(idOrder);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Orders> findByUserSortDateDesc(int idUser) {
		return dOrder.findByUserSortDateDesc(idUser);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Optional<Orders> findById(int idOrder) {
		return dOrder.findById(idOrder);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Orders findIncompleteOrder(int idUser) {
		return dOrder.findIncompleteOrder(idUser);
	}

}
