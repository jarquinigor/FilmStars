package pe.edu.upc.spring.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.spring.model.OrderDetail;
import pe.edu.upc.spring.repository.IOrderDetailRepository;
import pe.edu.upc.spring.service.IOrderDetailService;

@Service
public class OrderDetailServiceImpl implements IOrderDetailService {

	@Autowired
	private IOrderDetailRepository dOrderDetail;

	@Override
	@Transactional
	public boolean save(OrderDetail orderDetail) {
		OrderDetail objOrderDetail = dOrderDetail.save(orderDetail);
		if (objOrderDetail == null)
			return false;
		else
			return true;
	}

	@Override
	@Transactional
	public void delete(int idOrderDetail) {
		dOrderDetail.deleteById(idOrderDetail);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<OrderDetail> findAllSortIdDesc() {
		return dOrderDetail.findAll(Sort.by(Sort.Direction.DESC,"idOrderDetail"));
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<OrderDetail> findByMovieName(String nameMovie) {
		return dOrderDetail.findByMovieName(nameMovie);
	}
	
	
	//
	@Override
	@Transactional(readOnly = true)
	public List<OrderDetail> findByUserName(String nameUser) {
		return dOrderDetail.findByUserName(nameUser);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<OrderDetail> findByOrderId(int idOrder) {
		return dOrderDetail.findByOrderId(idOrder);
	}
	//
	
	@Override
	@Transactional(readOnly = true)
	public Optional<OrderDetail> findById(int idOrderDetail) {
		return dOrderDetail.findById(idOrderDetail);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<OrderDetail> findByUserOrderId(int idUser, int idOrder) {
		return dOrderDetail.findByUserOrderId(idOrder, idUser);
	}
	//COMBINADAS
	@Override
	@Transactional(readOnly = true)
	public List<OrderDetail> findFullComb(String nameUser, String nameMovie, int idOrder) {
		return dOrderDetail.findFullComb(nameUser, nameMovie, idOrder);
	}
	@Override
	@Transactional(readOnly = true)
	public List<OrderDetail> findComb1(String nameUser, String nameMovie) {
		return dOrderDetail.findComb1(nameUser, nameMovie);
	}
	@Override
	@Transactional(readOnly = true)
	public List<OrderDetail> findComb2(String nameUser, int idOrder) {
		return dOrderDetail.findComb2(nameUser, idOrder);
	}
	@Override
	@Transactional(readOnly = true)
	public List<OrderDetail> findComb3(String nameMovie, int idOrder) {
		return dOrderDetail.findComb3(nameMovie, idOrder);
	}
	
	//FINAL
	@Override
	@Transactional(readOnly = true)
	public List<OrderDetail> findByUserId(int idUser){
		return dOrderDetail.findByUserId(idUser);
	}
}
