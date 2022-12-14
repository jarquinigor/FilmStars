package pe.edu.upc.spring.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.spring.model.Actor;
import pe.edu.upc.spring.repository.IActorRepository;
import pe.edu.upc.spring.service.IActorService;

@Service
public class ActorServiceImpl implements IActorService {

	@Autowired
	private IActorRepository dActor;

	@Override
	@Transactional
	public int save(Actor actor, String baseActor, int opt) { // 1 : register, 2 : update
		int rpta = 0;
		int rptaActor = dActor.findActorByName(actor.getNameActor());
		if (opt == 1) {	
			if(rptaActor == 0) {
				dActor.save(actor);
				rpta = 0;
			}		
			if(rptaActor == 1) {
				rpta = 1;
			}
		}
		if(opt == 2) {
			//SI NO EDITÓ NADA (SI SON EXACTAMENTE IGUALES - MENSAJE: NO SE REALIZÓ NINGÚN CAMBIO)
			if(actor.getNameActor().equals(baseActor)==true) {
				rpta = 0;
			}
			else {//SI EDITÓ ALGO
				if(actor.getNameActor().equalsIgnoreCase(baseActor)==true) { //SI CAMBIO DE MAYUSCULAS (MENSAJE: SE ACTUALIZÓ EL GÉNERO CORRECTAMENTE)
					dActor.save(actor);
					rpta = 1;
				}
				else {//SI CAMBIO LA ESTRUCTURA 
					if(rptaActor == 0) {
						dActor.save(actor); //(MENSAJE: SE ACTUALIZÓ EL GÉNERO CORRECTAMENTE)
						rpta = 2;
					}		
					if(rptaActor == 1) { //(MENSAJE:YA EXISTE ESTE NOMBRE DE GÉNERO)
						rpta = 3;
					}
				}
			}
		}
		return rpta;
	}
	
	@Override
	@Transactional
	public void update(Actor race) {
		dActor.save(race);
	}
	
	@Override
	@Transactional
	public void delete(int idActor) {
		dActor.deleteById(idActor);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Actor> findAllSortNameAsc() {
		return dActor.findAll(Sort.by(Sort.Direction.ASC,"nameActor"));//AGREGADO
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Actor> findAllSortIdAsc() {
		return dActor.findAll(Sort.by(Sort.Direction.ASC,"idActor"));
	}
	
	@Override
	@Transactional(readOnly = true)
	public Optional<Actor> findById(int idActor) {
		return dActor.findById(idActor);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Actor> findByName(String nameActor) {
		return dActor.findByName(nameActor);
	}
}
