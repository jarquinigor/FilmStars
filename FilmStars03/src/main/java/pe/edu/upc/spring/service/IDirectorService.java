package pe.edu.upc.spring.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.spring.model.Director;

public interface IDirectorService {
	public int save(Director director, String baseDirector, int opt);
	public void delete(int idDirector);
	public List<Director> findAllSortNameAsc();
	public List<Director> findAllSortIdAsc();
	public Optional<Director>findById(int idDirector);
	public List<Director> findByName(String nameDirector);
}
