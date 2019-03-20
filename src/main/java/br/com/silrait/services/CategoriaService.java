package br.com.silrait.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.silrait.domain.Categoria;
import br.com.silrait.repositories.CategoriaRepository;
import br.com.silrait.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	@Autowired
	private CategoriaRepository repo;
	
	public Categoria find(Integer id) {
		Optional<Categoria> optional = repo.findById(id);
		
		return optional.orElseThrow(() -> new ObjectNotFoundException("Objeto Não encontrado! Id:" + id 
				+ ", Tipo: " + Categoria.class.getName()));
	}

	public Categoria insert(Categoria categoria) {
		categoria.setId(null);
		return repo.save(categoria);
	}

	public Categoria update(Categoria categoria) {
		find(categoria.getId());
		return repo.save(categoria);
	}
}
