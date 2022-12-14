package pe.edu.upc.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.spring.model.CriticReview;

@Repository
public interface ICriticReviewRepository extends JpaRepository<CriticReview, Integer> {
	@Query("from CriticReview cr where LOWER(cr.critic.nameCritic) like LOWER(concat('%',:nameCritic,'%')) order by cr.idCriticReview ASC")
	List<CriticReview> findByCriticName(@Param("nameCritic") String nameCritic);

	@Query("from CriticReview cr where LOWER(cr.movie.nameMovie) like LOWER(concat('%',:nameMovie,'%')) order by cr.idCriticReview ASC")
	List<CriticReview> findByMovieName(@Param("nameMovie") String nameMovie);
	
	@Query("from CriticReview cr where cr.movie.idMovie = :idMovie order by cr.idCriticReview DESC")
	List<CriticReview> findByMovieId(@Param("idMovie") int idMovie);
	
	@Query("from CriticReview cr where cr.movie.director.idDirector = :idDirector order by cr.idCriticReview DESC")
	List<CriticReview> findByDirectorId(@Param("idDirector") int idMovie);
}
