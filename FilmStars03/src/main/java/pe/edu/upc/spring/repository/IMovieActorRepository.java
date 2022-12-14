package pe.edu.upc.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.spring.model.MovieActor;

@Repository
public interface IMovieActorRepository extends JpaRepository<MovieActor, Integer> {
	@Query("from MovieActor ma where LOWER(ma.movie.nameMovie) like LOWER(concat('%',:nameMovie,'%')) order by ma.idMovieActor ASC")
	List<MovieActor> findByMovieName(@Param("nameMovie") String nameMovie);
	
	@Query("from MovieActor ma where LOWER(ma.actor.nameActor) like LOWER(concat('%',:nameActor,'%')) order by ma.idMovieActor ASC")
	List<MovieActor> findByActorName(@Param("nameActor") String nameActor);
	
	@Query("from MovieActor ma where ma.movie.idMovie = :idMovie order by ma.idMovieActor ASC")
	List<MovieActor> findByMovieId(@Param("idMovie") int idMovie);
	//
	@Query("select count(ma.idMovieActor) from MovieActor ma where ma.movie.idMovie = :idMovie and ma.actor.idActor = :idActor")
	int repeatedActor(@Param("idMovie") int idMovie, @Param("idActor") int idActor);
}
