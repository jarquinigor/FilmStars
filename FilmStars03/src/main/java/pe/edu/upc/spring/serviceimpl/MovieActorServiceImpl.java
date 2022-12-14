package pe.edu.upc.spring.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.spring.model.MovieActor;
import pe.edu.upc.spring.repository.IMovieActorRepository;
import pe.edu.upc.spring.service.IMovieActorService;

@Service
public class MovieActorServiceImpl implements IMovieActorService {

	@Autowired
	private IMovieActorRepository dMovieActor;

	@Override
	@Transactional
	public boolean save(MovieActor movieActor) {

		if(dMovieActor.repeatedActor(movieActor.getMovie().getIdMovie(), movieActor.getActor().getIdActor()) > 0){
			return false;
		}
		else {
			dMovieActor.save(movieActor);
			return true;
		}

	}

	@Override
	@Transactional
	public void delete(int idMovieActor) {
		dMovieActor.deleteById(idMovieActor);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<MovieActor> findAllSortIdAsc() {
		return dMovieActor.findAll(Sort.by(Sort.Direction.ASC,"idMovieActor"));
	}
	
	@Override
	@Transactional(readOnly = true)
	public Optional<MovieActor> findById(int idMovieActor) {
		return dMovieActor.findById(idMovieActor);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<MovieActor> findByMovieName(String nameMovie) {
		return dMovieActor.findByMovieName(nameMovie);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<MovieActor> findByActorName(String nameActor) {
		return dMovieActor.findByActorName(nameActor);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<MovieActor> findByMovieId(int idMovie){
		return dMovieActor.findByMovieId(idMovie);
	}
}
