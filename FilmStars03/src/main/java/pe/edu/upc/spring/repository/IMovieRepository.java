package pe.edu.upc.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.spring.model.Movie;

@Repository
public interface IMovieRepository extends JpaRepository<Movie, Integer> {
	@Query("from Movie m where LOWER(m.nameMovie) like LOWER(concat('%',:nameMovie,'%')) order by m.idMovie ASC")
	List<Movie> findByName(@Param("nameMovie") String nameMovie);
	
	@Query("select count(m.nameMovie) from Movie m where m.nameMovie = :nameMovie")
	public int findMovieByName(@Param("nameMovie") String nameMovie);  //PARA LOS REGISTROS Y QUE NO SE REPITAN LOS DATOS
	
	@Query("select count(m.coverMovie) from Movie m where m.coverMovie = :coverMovie")
	public int findMovieByCover(@Param("coverMovie") String coverMovie);  //PARA LOS REGISTROS Y QUE NO SE REPITAN LOS DATOS
	
	@Query("select count(m.imgMovie) from Movie m where m.imgMovie = :imgMovie")
	public int findMovieByImg(@Param("imgMovie") String imgMovie);  //PARA LOS REGISTROS Y QUE NO SE REPITAN LOS DATOS
	
	@Query("select count(m.imgCarrMovie1) from Movie m where m.imgCarrMovie1 = :imgCarrMovie1")
	public int findMovieByImgCarr1(@Param("imgCarrMovie1") String imgCarrMovie1);  //PARA LOS REGISTROS Y QUE NO SE REPITAN LOS DATOS
	
	@Query("select count(m.imgCarrMovie2) from Movie m where m.imgCarrMovie2 = :imgCarrMovie2")
	public int findMovieByImgCarr2(@Param("imgCarrMovie2") String imgCarrMovie2);  //PARA LOS REGISTROS Y QUE NO SE REPITAN LOS DATOS
	
	@Query("select count(m.imgCarrMovie3) from Movie m where m.imgCarrMovie3 = :imgCarrMovie3")
	public int findMovieByImgCarr3(@Param("imgCarrMovie3") String imgCarrMovie3);  //PARA LOS REGISTROS Y QUE NO SE REPITAN LOS DATOS

}
