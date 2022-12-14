package pe.edu.upc.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.spring.model.Genre;

@Repository
public interface IGenreRepository extends JpaRepository<Genre, Integer> {
	@Query("from Genre g where LOWER(g.nameGenre) like LOWER(concat('%',:nameGenre,'%')) order by g.idGenre ASC")
	List<Genre> findByName(@Param("nameGenre") String nameGenre);
	
	@Query("select count(g.nameGenre) from Genre g where g.nameGenre = :nameGenre")
	public int findGenreByName(@Param("nameGenre") String nameGenre);
}
