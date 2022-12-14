package pe.edu.upc.spring.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.spring.model.Movie;

public interface IMovieService {
	public int save(Movie movie, int baseMovieId, int opt);
	public int register(Movie movie, int opt);
	public void delete(int idMovie);
	public List<Movie> findAllSortNameAsc();
	public List<Movie> findAllSortIdAsc();
	public Optional<Movie>findById(int idMovie);
	public List<Movie> findByName(String nameMovie);
	
	public int findMovieByName(String nameMovie);
	public int findMovieByCover(String coverMovie);
	public int findMovieByImg(String imgMovie);
	public int findMovieByImgCarr1(String imgCarrMovie1);
	public int findMovieByImgCarr2(String imgCarrMovie2);
	public int findMovieByImgCarr3(String imgCarrMovie3);
}
