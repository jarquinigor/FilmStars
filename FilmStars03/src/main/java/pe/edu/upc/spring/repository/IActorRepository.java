package pe.edu.upc.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.spring.model.Actor;

@Repository
public interface IActorRepository extends JpaRepository<Actor, Integer> {
	@Query("from Actor a where LOWER(a.nameActor) like LOWER(concat('%',:nameActor,'%')) order by a.idActor ASC")
	List<Actor> findByName(@Param("nameActor") String nameActor); //PARA LAS BÚSQUEDAS
	
	@Query("select count(a.nameActor) from Actor a where a.nameActor = :nameActor")
	public int findActorByName(@Param("nameActor") String nameActor);  //PARA LOS REGISTROS Y QUE NO SE REPITAN LOS DATOS
	
	@Query("select count(a.imgActor) from Actor a where a.imgActor = :imgActor")
	public int findActorByImg(@Param("imgActor") String imgActor);  //PARA LOS REGISTROS Y QUE NO SE REPITAN LOS DATOS
}
