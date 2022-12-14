package pe.edu.upc.spring.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.spring.model.MovieGenre;

@Repository
public interface IMovieGenreRepository extends JpaRepository<MovieGenre, Integer> {
	@Query("from MovieGenre mg where LOWER(mg.movie.nameMovie) like LOWER(concat('%',:nameMovie,'%')) order by mg.idMovieGenre ASC")
	List<MovieGenre> findByMovieName(@Param("nameMovie") String nameMovie);
	
	@Query("from MovieGenre mg where LOWER(mg.genre.nameGenre) like LOWER(concat('%',:nameGenre,'%')) order by mg.idMovieGenre ASC")
	List<MovieGenre> findByGenreName(@Param("nameGenre") String nameGenre);
	
	@Query("from MovieGenre mg where mg.movie.idMovie = :idMovie order by mg.idMovieGenre ASC")
	List<MovieGenre> findByMovieId(@Param("idMovie") int idMovie);
	
	@Query("from MovieGenre mg where mg.genre.idGenre = :idGenre")
	List<MovieGenre> findByGenreId(@Param("idGenre") int idGenre, Sort sort);
	
	//
	@Query("select count(mg.idMovieGenre) from MovieGenre mg where mg.movie.idMovie = :idMovie and mg.genre.idGenre = :idGenre")
	int repeatedGenre(@Param("idMovie") int idMovie, @Param("idGenre") int idGenre);
}
