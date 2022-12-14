package pe.edu.upc.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.spring.model.Director;

@Repository
public interface IDirectorRepository extends JpaRepository<Director, Integer> {
	@Query("from Director d where LOWER(d.nameDirector) like LOWER(concat('%',:nameDirector,'%')) order by d.idDirector ASC")
	List<Director> findByName(@Param("nameDirector") String nameDirector);
	
	@Query("select count(d.nameDirector) from Director d where d.nameDirector = :nameDirector")
	public int findDirectorByName(@Param("nameDirector") String nameDirector);
}
