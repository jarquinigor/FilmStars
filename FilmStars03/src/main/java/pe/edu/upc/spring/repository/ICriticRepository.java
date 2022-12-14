package pe.edu.upc.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.spring.model.Critic;

@Repository
public interface ICriticRepository extends JpaRepository<Critic, Integer> {
	@Query("from Critic c where LOWER(c.nameCritic) like LOWER(concat('%',:nameCritic,'%')) order by c.idCritic ASC")
	List<Critic> findByName(@Param("nameCritic") String nameCritic);
	
	@Query("select count(c.nameCritic) from Critic c where c.nameCritic = :nameCritic")
	public int findCritic(@Param("nameCritic") String nameCritic);
	
	
}
