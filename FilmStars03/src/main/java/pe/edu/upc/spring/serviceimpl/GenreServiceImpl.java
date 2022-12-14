package pe.edu.upc.spring.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.spring.model.Genre;
import pe.edu.upc.spring.repository.IGenreRepository;
import pe.edu.upc.spring.service.IGenreService;

@Service
public class GenreServiceImpl implements IGenreService {

	@Autowired
	private IGenreRepository dGenre;

	@Override
	@Transactional
	public int save(Genre genre, String baseGenre, int opt) {
		int rptaGenre = dGenre.findGenreByName(genre.getNameGenre());
		int rpta = 0;
		if (opt == 1) {	
			if(rptaGenre == 0) {
				dGenre.save(genre);
				rpta = 0;
			}		
			if(rptaGenre == 1) {
				rpta = 1;
			}
		}
		if(opt == 2) {
			//SI NO EDITÓ NADA (SI SON EXACTAMENTE IGUALES - MENSAJE: NO SE REALIZÓ NINGÚN CAMBIO)
			if(genre.getNameGenre().equals(baseGenre)==true) {
				rpta = 0;
			}
			else {//SI EDITÓ ALGO
				if(genre.getNameGenre().equalsIgnoreCase(baseGenre)==true) { //SI CAMBIO DE MAYUSCULAS (MENSAJE: SE ACTUALIZÓ EL GÉNERO CORRECTAMENTE)
					dGenre.save(genre);
					rpta = 1;
				}
				else {//SI CAMBIO LA ESTRUCTURA 
					if(rptaGenre == 0) {
						dGenre.save(genre); //(MENSAJE: SE ACTUALIZÓ EL GÉNERO CORRECTAMENTE)
						rpta = 2;
					}		
					if(rptaGenre == 1) { //(MENSAJE:YA EXISTE ESTE NOMBRE DE GÉNERO)
						rpta = 3;
					}
				}
			}
	
		}
		return rpta;
	}

	@Override
	@Transactional
	public void delete(int idGenre) {
		dGenre.deleteById(idGenre);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Genre> findAllSortNameAsc() {
		return dGenre.findAll(Sort.by(Sort.Direction.ASC,"nameGenre"));
	}

	@Override
	@Transactional(readOnly = true)
	public List<Genre> findAllSortIdAsc() {
		return dGenre.findAll(Sort.by(Sort.Direction.ASC,"idGenre"));
	}
	
	@Override
	@Transactional(readOnly = true)
	public Optional<Genre> findById(int idGenre) {
		return dGenre.findById(idGenre);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Genre> findByName(String nameGenre) {
		return dGenre.findByName(nameGenre);
	}
}
