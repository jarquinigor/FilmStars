package pe.edu.upc.spring.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.spring.model.CriticReview;
import pe.edu.upc.spring.repository.ICriticReviewRepository;
import pe.edu.upc.spring.service.ICriticReviewService;

@Service
public class CriticReviewServiceImpl implements ICriticReviewService {

	@Autowired
	private ICriticReviewRepository dCriticReview;

	@Override
	@Transactional
	public boolean save(CriticReview CriticReview) {
		CriticReview objCriticReview = dCriticReview.save(CriticReview);
		if (objCriticReview == null)
			return false;
		else
			return true;
	}

	@Override
	@Transactional
	public void delete(int idCriticReview) {
		dCriticReview.deleteById(idCriticReview);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<CriticReview> findAllSortIdAsc() {
		return dCriticReview.findAll(Sort.by(Sort.Direction.ASC,"idCriticReview"));
	}
	
	@Override
	@Transactional(readOnly = true)
	public Optional<CriticReview> findById(int idCriticReview) {
		return dCriticReview.findById(idCriticReview);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<CriticReview> findByCriticName(String nameCritic) {
		return dCriticReview.findByCriticName(nameCritic);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<CriticReview> findByMovieName(String nameMovie) {
		return dCriticReview.findByMovieName(nameMovie);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<CriticReview> findByMovieId(int idMovie){
		return dCriticReview.findByMovieId(idMovie);
	}
}
