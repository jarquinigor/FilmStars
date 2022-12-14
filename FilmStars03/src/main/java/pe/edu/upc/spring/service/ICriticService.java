package pe.edu.upc.spring.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.spring.model.Critic;

public interface ICriticService {
	public int save(Critic critic, int opt);
	public void delete(int idCritic);
	public List<Critic> findAllSortNameAsc();
	public List<Critic> findAllSortIdAsc();
	public Optional<Critic>findById(int idCritic);
	public List<Critic> findByName(String nameCritic);
}
