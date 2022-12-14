package pe.edu.upc.spring.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.spring.model.Director;
import pe.edu.upc.spring.repository.IDirectorRepository;
import pe.edu.upc.spring.service.IDirectorService;

@Service
public class DirectorServiceImpl implements IDirectorService {

	@Autowired
	private IDirectorRepository dDirector;

	@Override
	@Transactional
	public int save(Director director, String baseDirector, int opt) {
		int rptadirector = dDirector.findDirectorByName(director.getNameDirector());
		int rpta = 0;
		if (opt == 1) {	
			if(rptadirector == 0) {
				dDirector.save(director);
				rpta = 0;
			}		
			if(rptadirector == 1) {
				rpta = 1;
			}
		}
		if(opt == 2) {
			//SI NO EDITÓ NADA (SI SON EXACTAMENTE IGUALES - MENSAJE: NO SE REALIZÓ NINGÚN CAMBIO)
			if(director.getNameDirector().equals(baseDirector)==true) {
				rpta = 0;
			}
			else {//SI EDITÓ ALGO
				if(director.getNameDirector().equalsIgnoreCase(baseDirector)==true) { //SI CAMBIO DE MAYUSCULAS (MENSAJE: SE ACTUALIZÓ EL GÉNERO CORRECTAMENTE)
					dDirector.save(director);
					rpta = 1;
				}
				else {//SI CAMBIO LA ESTRUCTURA 
					if(rptadirector == 0) {
						dDirector.save(director); //(MENSAJE: SE ACTUALIZÓ EL GÉNERO CORRECTAMENTE)
						rpta = 2;
					}		
					if(rptadirector == 1) { //(MENSAJE:YA EXISTE ESTE NOMBRE DE GÉNERO)
						rpta = 3;
					}
				}
			}
		}
		return rpta;
	}

	@Override
	@Transactional
	public void delete(int idDirector) {
		dDirector.deleteById(idDirector);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Director> findAllSortNameAsc() {
		return dDirector.findAll(Sort.by(Sort.Direction.ASC,"nameDirector"));
	}

	@Override
	@Transactional(readOnly = true)
	public List<Director> findAllSortIdAsc() {
		return dDirector.findAll(Sort.by(Sort.Direction.ASC,"idDirector"));
	}
	
	@Override
	@Transactional(readOnly = true)
	public Optional<Director> findById(int idDirector) {
		return dDirector.findById(idDirector);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Director> findByName(String nameDirector) {
		return dDirector.findByName(nameDirector);
	}
}
