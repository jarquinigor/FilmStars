package pe.edu.upc.spring.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.spring.model.Actor;

public interface IActorService {
	public int save(Actor actor, String baseActor, int opt);
	public void update(Actor actor);
	public void delete(int idACtor);
	public List<Actor> findAllSortNameAsc();
	public List<Actor> findAllSortIdAsc();
	public Optional<Actor>findById(int idActor);
	public List<Actor> findByName(String nameActor);
}
