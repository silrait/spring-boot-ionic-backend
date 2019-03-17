package br.com.silrait;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.silrait.domain.Categoria;
import br.com.silrait.domain.Cidade;
import br.com.silrait.domain.Cliente;
import br.com.silrait.domain.Endereco;
import br.com.silrait.domain.Estado;
import br.com.silrait.domain.Produto;
import br.com.silrait.domain.enums.TipoCliente;
import br.com.silrait.repositories.CategoriaRepository;
import br.com.silrait.repositories.CidadeRepository;
import br.com.silrait.repositories.ClienteRepository;
import br.com.silrait.repositories.EnderecoRepository;
import br.com.silrait.repositories.EstadoRepository;
import br.com.silrait.repositories.ProdutoRepository;

@SpringBootApplication
public class EstudoSringBootApplication implements CommandLineRunner {
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;

	public static void main(String[] args) {
		SpringApplication.run(EstudoSringBootApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));

		Produto p1 = new Produto(null, "Computador", 2000.00);
		p1.addCategoria(cat1);

		Produto p2 = new Produto(null, "Impressora", 800.00);
		p2.addCategoria(cat1);
		p2.addCategoria(cat2);

		Produto p3 = new Produto(null, "Mouse", 80.00);
		p3.addCategoria(cat1);

		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");

		estadoRepository.saveAll(Arrays.asList(est1, est2));

		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		c1.setEstado(est1);

		Cidade c2 = new Cidade(null, "São Paulo", est2);
		c2.setEstado(est2);

		Cidade c3 = new Cidade(null, "Campinas", est2);
		c3.setEstado(est2);

		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOAFISICA);
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);		
		
		cli1.addEndereco(e1);
		cli1.addEndereco(e2);
		cli1.getTelefones().addAll( Arrays.asList("27363323","93838393"));
		
		clienteRepository.save(cli1);
		
		enderecoRepository.saveAll( Arrays.asList(e1, e2));
	}

}
