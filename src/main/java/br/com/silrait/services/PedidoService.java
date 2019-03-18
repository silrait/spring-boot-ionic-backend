package br.com.silrait.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.silrait.domain.Pedido;
import br.com.silrait.repositories.PedidoRepository;
import br.com.silrait.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	@Autowired
	private PedidoRepository repo;
	
	public Pedido buscar(Integer id) {
		Optional<Pedido> optional = repo.findById(id);
		
		return optional.orElseThrow(() -> new ObjectNotFoundException("Objeto Não encontrado! Id:" + id 
				+ ", Tipo: " + Pedido.class.getName()));
	}
}
