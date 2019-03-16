package br.com.silrait;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.silrait.domain.Categoria;
import br.com.silrait.domain.Cidade;
import br.com.silrait.domain.Estado;
import br.com.silrait.domain.Produto;
import br.com.silrait.repositories.CategoriaRepository;
import br.com.silrait.repositories.CidadeRepository;
import br.com.silrait.repositories.EstadoRepository;
import br.com.silrait.repositories.ProdutoRepository;

@SpringBootApplication
public class EstudoSringBootApplication implements CommandLineRunner{
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	
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
		
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		c1.setEstado(est1);
		
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		c2.setEstado(est2);
		
		Cidade c3 = new Cidade(null, "Campinas", est2);
		c3.setEstado(est2);
		
		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));
	}

}
