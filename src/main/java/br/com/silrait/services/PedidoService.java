package br.com.silrait.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.silrait.domain.PagamentoComBoleto;
import br.com.silrait.domain.Pedido;
import br.com.silrait.domain.enums.EstadoPagamento;
import br.com.silrait.repositories.ItemPedidoRepository;
import br.com.silrait.repositories.PagamentoRepository;
import br.com.silrait.repositories.PedidoRepository;
import br.com.silrait.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	@Autowired
	private PedidoRepository repo;
	@Autowired
	private BoletoService boletoService;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ProdutoService produtoService;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public Pedido find(Integer id) {
		Optional<Pedido> optional = repo.findById(id);
		
		return optional.orElseThrow(() -> new ObjectNotFoundException("Objeto Não encontrado! Id:" + id 
				+ ", Tipo: " + Pedido.class.getName()));
	}

	@Transactional
	public Pedido insert(Pedido pedido) {
		pedido.setId(null);
		pedido.setInstante(new Date());
		pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		pedido.getPagamento().setPedido(pedido);
		if(pedido.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pgto = (PagamentoComBoleto) pedido.getPagamento();
			boletoService.preencherPagamentoComBoleto(pgto, pedido.getInstante());
		}
		Pedido pedidoRepo = repo.save(pedido);
		pagamentoRepository.save(pedidoRepo.getPagamento());
		pedidoRepo.getItens().forEach(item -> {
			item.setDesconto(0.0);
			item.setPreco(produtoService.find(item.getProduto().getId()).getPreco());
			item.setPedido(pedidoRepo);
		});
		itemPedidoRepository.saveAll(pedidoRepo.getItens());
		return pedidoRepo;
	}
}
