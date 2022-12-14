package pe.edu.upc.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.spring.model.Orders;

@Repository
public interface IOrderRepository extends JpaRepository<Orders, Integer> {
	@Query("from Orders o where o.user.idUser = :idUser order by o.dateOrder DESC")
	List<Orders> findByUserSortDateDesc(@Param("idUser") int idUser);
	
	@Query("from Orders o where o.user.idUser = :idUser and o.statusOrder = 0")
	Orders findIncompleteOrder(@Param("idUser") int idUser);
}
