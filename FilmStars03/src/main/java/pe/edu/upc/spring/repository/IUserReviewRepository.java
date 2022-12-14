package pe.edu.upc.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.spring.model.UserReview;

@Repository
public interface IUserReviewRepository extends JpaRepository<UserReview, Integer> {
	@Query("from UserReview ur where LOWER(ur.textUserReview) like LOWER(concat('%',:textUserReview,'%')) order by ur.idUserReview ASC")
	List<UserReview> findByText(@Param("textUserReview") String textUserReview);
	
	@Query("from UserReview ur where ur.movie.idMovie = :idMovie order by ur.idUserReview DESC")
	List<UserReview> findByMovieId(@Param("idMovie") int idMovie);
	
	@Query("from UserReview ur where ur.movie.idMovie = :idMovie and ur.user.idUser = :idUser")
	List<UserReview> findByMovieUserId(@Param("idMovie") int idMovie, @Param("idUser") int idUser);
	
	@Query("from UserReview ur where ur.idUserReview = :idUserReview")
	UserReview findByURId(@Param("idUserReview") int idUserReview);
}
