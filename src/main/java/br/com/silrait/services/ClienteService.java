package br.com.silrait.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.silrait.domain.Cidade;
import br.com.silrait.domain.Cliente;
import br.com.silrait.domain.Endereco;
import br.com.silrait.domain.enums.TipoCliente;
import br.com.silrait.dto.ClienteDTO;
import br.com.silrait.dto.ClienteNewDTO;
import br.com.silrait.repositories.ClienteRepository;
import br.com.silrait.repositories.EnderecoRepository;
import br.com.silrait.services.exceptions.DataIntegrityException;
import br.com.silrait.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepository;

	public Cliente find(Integer id) {
		Optional<Cliente> optional = repo.findById(id);

		return optional.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto Não encontrado! Id:" + id + ", Tipo: " + Cliente.class.getName()));
	}

	@Transactional
	public Cliente insert(Cliente cli) {
		cli.setId(null);
		cli = repo.save(cli);
		enderecoRepository.saveAll(cli.getEnderecos());
		return cli;
}

	public Cliente update(Cliente cliente) {
		Cliente cliDB = find(cliente.getId());
		updateData(cliDB, cliente);
		return repo.save(cliDB);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um cliente referenciado por outras entidades");
		}
	}

	public List<Cliente> findAll() {
		return repo.findAll();
	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	public Cliente fromDTO(ClienteDTO dto) {
		return new Cliente(dto.getId(), dto.getNome(), dto.getEmail(), null, null);
	}

	public Cliente fromDTO(ClienteNewDTO dto) {
		Cliente cli = new Cliente(null, dto.getNome(), dto.getEmail(), dto.getCpfOuCnpj(),
				TipoCliente.toEnum(dto.getTipo()));
		Cidade cid = new Cidade(dto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, dto.getLogradouro(), dto.getNumero(), dto.getComplemento(), dto.getBairro(),
				dto.getCep(), cli, cid);
		cli.addEndereco(end);
		cli.addTelefone(dto.getTelefone1());
		if (dto.getTelefone2() != null)
			cli.addTelefone(dto.getTelefone2());
		if (dto.getTelefone3() != null)
			cli.addTelefone(dto.getTelefone3());
		return cli;
	}

	private void updateData(Cliente cliDB, Cliente cliUPD) {
		cliDB.setNome(cliUPD.getNome());
		cliDB.setEmail(cliUPD.getEmail());
	}
}
