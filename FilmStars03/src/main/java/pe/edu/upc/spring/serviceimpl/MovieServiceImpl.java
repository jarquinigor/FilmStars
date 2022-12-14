package pe.edu.upc.spring.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.spring.model.Movie;
import pe.edu.upc.spring.repository.IMovieRepository;
import pe.edu.upc.spring.service.IMovieService;

@Service
public class MovieServiceImpl implements IMovieService {

	@Autowired
	private IMovieRepository dMovie;

	@Override
	@Transactional
	public int save(Movie movie, int baseMovieId, int opt) {
		Movie baseMovie = dMovie.findById(baseMovieId).get();
		int rpta = 0;
		if(opt == 2) {
			//SI NO EDITÓ NADA (SI SON EXACTAMENTE IGUALES - MENSAJE: NO SE REALIZÓ NINGÚN CAMBIO)
			if(movie.getDirector().getIdDirector()==baseMovie.getDirector().getIdDirector() && movie.getNameMovie().equals(baseMovie.getNameMovie()) && movie.getPriceMovie()==baseMovie.getPriceMovie() &&
					movie.getSynopsisMovie().equals(baseMovie.getSynopsisMovie()) && movie.getPlotMovie().equals(baseMovie.getPlotMovie()) && movie.getCoverMovie().equals(baseMovie.getCoverMovie())
					&& movie.getImgMovie().equals(baseMovie.getImgMovie()) && movie.getImgCarrMovie1().equals(baseMovie.getImgCarrMovie1()) && movie.getImgCarrMovie2().equals(baseMovie.getImgCarrMovie2())
					&& movie.getImgCarrMovie3().equals(baseMovie.getImgCarrMovie3())) {
				rpta = 0;				
			}
			else {//SI EDITÓ ALGO 
				if(movie.getNameMovie().equals(baseMovie.getNameMovie()) || movie.getNameMovie().equalsIgnoreCase(baseMovie.getNameMovie())) {							
					dMovie.save(movie);  //MENSAJE: SE ACTUALIZÓ UNA PELÍCULA CORRECTAMENTE
					rpta = 1;
				}
				else {
					if(dMovie.findMovieByName(movie.getNameMovie())==1) { //MENSAJE: YA EXISTE ESTE NOMBRE DE PELÍCULA CORRECTAMENTE
						rpta = 2;
					}
					else {
						rpta = 3;  //MENSAJE: SE ACTUALIZÓ UNA PELÍCULA CORRECTAMENTE
						dMovie.save(movie);
					}
				}
			}
		}
		System.out.println("movie Name: "+movie.getNameMovie());
		System.out.println("base Movie Name: " + baseMovie.getNameMovie());
		
		return rpta;
	}	
	
	@Override
	@Transactional
	public int register(Movie movie, int opt) {
		int rptaMovie = dMovie.findMovieByName(movie.getNameMovie());
		int rpta = 0;
		if (opt == 1) {	
			if(rptaMovie == 0) {
				dMovie.save(movie);
				rpta = 0;
			}		
			if(rptaMovie == 1) { //YA EXISTE
				rpta = 1;
			}
		}	
		return rpta;
	}
	
	@Override
	@Transactional
	public void delete(int idMovie) {
		dMovie.deleteById(idMovie);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Movie> findAllSortNameAsc() {
		return dMovie.findAll(Sort.by(Sort.Direction.ASC,"nameMovie"));
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Movie> findAllSortIdAsc() {
		return dMovie.findAll(Sort.by(Sort.Direction.ASC,"idMovie"));
	}
	
	@Override
	@Transactional(readOnly = true)
	public Optional<Movie> findById(int idMovie) {
		return dMovie.findById(idMovie);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Movie> findByName(String nameMovie) {
		return dMovie.findByName(nameMovie);
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public int findMovieByName(String nameMovie) {
		return dMovie.findMovieByName(nameMovie);
	}
	@Override
	@Transactional(readOnly = true)
	public int findMovieByCover(String coverMovie) {
		return dMovie.findMovieByCover(coverMovie);
	}
	@Override
	@Transactional(readOnly = true)
	public int findMovieByImg(String imgMovie) {
		return dMovie.findMovieByImg(imgMovie);
	}
	@Override
	@Transactional(readOnly = true)
	public int findMovieByImgCarr1(String imgCarrMovie1) {
		return dMovie.findMovieByImgCarr1(imgCarrMovie1);
	}
	@Override
	@Transactional(readOnly = true)
	public int findMovieByImgCarr2(String imgCarrMovie2) {
		return dMovie.findMovieByImgCarr2(imgCarrMovie2);
	}
	@Override
	@Transactional(readOnly = true)
	public int findMovieByImgCarr3(String imgCarrMovie3) {
		return dMovie.findMovieByImgCarr3(imgCarrMovie3);
	}
	
}
