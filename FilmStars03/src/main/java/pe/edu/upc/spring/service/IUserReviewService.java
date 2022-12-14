package pe.edu.upc.spring.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.spring.model.UserReview;

public interface IUserReviewService {
	public boolean save(UserReview userReview);
	public void delete(int idUserReview);
	public List<UserReview> findAllSortIdDesc();
	public List<UserReview> findAllSortIdAsc();
	public Optional<UserReview>findById(int idUserReview);
	public List<UserReview> findByText(String textUserReview);
	public List<UserReview> findByMovieId(int idMovie);
	public String findFilmstarsRate(int idMovie);
	public List<UserReview> findByMovieUserId(int idMovie, int idUser);
	public UserReview findByURId(int idUserReview);
}
