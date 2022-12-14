package pe.edu.upc.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.spring.model.OrderDetail;

@Repository
public interface IOrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
	@Query("from OrderDetail od where od.order.idOrder = :idOrder and od.order.user.idUser = :idUser")
	List<OrderDetail> findByUserOrderId(@Param("idOrder") int idOrder, @Param("idUser") int idUser);
	
	@Query("from OrderDetail od where LOWER(od.order.user.nameUser) like LOWER(concat('%',:nameUser,'%')) order by od.order.idOrder DESC")
	List<OrderDetail> findByUserName(@Param("nameUser") String nameUser);
	
	@Query("from OrderDetail od where LOWER(od.movie.nameMovie) like LOWER(concat('%',:nameMovie,'%')) order by od.order.idOrder DESC")
	List<OrderDetail> findByMovieName(@Param("nameMovie") String nameMovie);
	
	@Query("from OrderDetail od where od.order.idOrder = :idOrder order by od.order.idOrder DESC")
	List<OrderDetail> findByOrderId(@Param("idOrder") int idOrder);
	
	//COMBINADOS
	@Query("from OrderDetail od where od.order.idOrder = :idOrder and LOWER(od.movie.nameMovie) like LOWER(concat('%',:nameMovie,'%')) and LOWER(od.order.user.nameUser) like LOWER(concat('%',:nameUser,'%'))")
	List<OrderDetail> findFullComb(@Param("nameUser") String nameUser, @Param("nameMovie") String nameMovie, @Param("idOrder") int idOrder);
	
	@Query("from OrderDetail od where LOWER(od.movie.nameMovie) like LOWER(concat('%',:nameMovie,'%')) and LOWER(od.order.user.nameUser) like LOWER(concat('%',:nameUser,'%')) order by od.order.idOrder DESC")
	List<OrderDetail> findComb1(@Param("nameUser") String nameUser, @Param("nameMovie") String nameMovie);
	
	@Query("from OrderDetail od where od.order.idOrder = :idOrder and LOWER(od.order.user.nameUser) like LOWER(concat('%',:nameUser,'%')) order by od.order.idOrder DESC")
	List<OrderDetail> findComb2(@Param("nameUser") String nameUser, @Param("idOrder") int idOrder);
	
	@Query("from OrderDetail od where od.order.idOrder = :idOrder and LOWER(od.movie.nameMovie) like LOWER(concat('%',:nameMovie,'%')) order by od.order.idOrder DESC")
	List<OrderDetail> findComb3(@Param("nameMovie") String nameMovie, @Param("idOrder") int idOrder);
	//FINAL
	@Query("from OrderDetail od where od.order.user.idUser = :idUser order by od.order.idOrder DESC")
	List<OrderDetail> findByUserId(@Param("idUser") int idUser);
}
