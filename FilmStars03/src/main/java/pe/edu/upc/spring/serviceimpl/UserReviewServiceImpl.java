package pe.edu.upc.spring.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.spring.model.UserReview;
import pe.edu.upc.spring.repository.IUserReviewRepository;
import pe.edu.upc.spring.service.IUserReviewService;

@Service
public class UserReviewServiceImpl implements IUserReviewService {

	@Autowired
	private IUserReviewRepository dUserReview;

	@Override
	@Transactional
	public boolean save(UserReview userReview) {
		UserReview objUserReview = dUserReview.save(userReview);
		if (objUserReview == null)
			return false;
		else
			return true;
	}

	@Override
	@Transactional
	public void delete(int idUserReview) {
		dUserReview.deleteById(idUserReview);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<UserReview> findAllSortIdDesc() {
		return dUserReview.findAll(Sort.by(Sort.Direction.DESC,"idUserReview"));
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<UserReview> findAllSortIdAsc() {
		return dUserReview.findAll(Sort.by(Sort.Direction.ASC,"idUserReview"));
	}
	
	@Override
	@Transactional(readOnly = true)
	public Optional<UserReview> findById(int idUserReview) {
		return dUserReview.findById(idUserReview);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<UserReview> findByText(String textUserReview) {
		return dUserReview.findByText(textUserReview);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<UserReview> findByMovieId(int idMovie){
		return dUserReview.findByMovieId(idMovie);
	}
	
	@Override
	@Transactional(readOnly = true)
	public String findFilmstarsRate(int idMovie) {
		
		double quantityReview = 0.0;
		
		if(dUserReview.findByMovieId(idMovie).isEmpty()) {
			return "0.0";
		}
		else {  
			for (int i = 0; i < dUserReview.findByMovieId(idMovie).size(); i++) {
				quantityReview = quantityReview + dUserReview.findByMovieId(idMovie).get(i).getQuantityUserReview();
			}
			quantityReview = quantityReview/dUserReview.findByMovieId(idMovie).size();
		}
		
		return String.format("%.1f", quantityReview);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<UserReview> findByMovieUserId(int idMovie, int idUser){
		return dUserReview.findByMovieUserId(idMovie, idUser);
	}
	
	@Override
	@Transactional(readOnly = true)
	public UserReview findByURId(int idUserReview) {
		return dUserReview.findByURId(idUserReview);
	}
}
