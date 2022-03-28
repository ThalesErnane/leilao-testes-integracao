package br.com.alura.leilao.dao;

import java.time.LocalDate;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;
import br.com.alura.leilao.util.builder.LanceBuilder;
import br.com.alura.leilao.util.builder.LeilaoBuilder;
import br.com.alura.leilao.util.builder.UsuarioBuilder;

class LanceDaoTest {

	private LanceDao dao;
	private EntityManager em;

	@BeforeEach
	public void beforeEach() {
		em = JPAUtil.getEntityManager();
		this.dao = new LanceDao(em);
		this.em.getTransaction().begin();
	}

	@AfterEach
	public void afterEach() {
		em.getTransaction().rollback();
	}

	@Test
	void deveriaBuscarMaiorLanceDoLeilao() {
		Usuario usuario = new UsuarioBuilder()
				.comNome("Fulano")
				.comEmail("fulano@email.com")
				.comSenha("12345678")
				.criar();

		em.persist(usuario);

		Leilao leilao = new LeilaoBuilder()
				.comNome("Mochila")
				.comValorInicial("500")
				.comData(LocalDate.now())
				.comUsuario(usuario)
				.criar();

		em.persist(leilao);

		Lance lance = new LanceBuilder()
				.comUsuario(usuario)
				.comValor("600")
				.comLeilao(leilao)
				.criar();

		em.persist(lance);

		Lance acheiMaiorLance = this.dao.buscarMaiorLanceDoLeilao(leilao);
		Assert.assertNotNull(acheiMaiorLance);

	}
	
	@Test
	void deveriaBuscarMenorLanceDoLeilao() {
		Usuario usuario = new UsuarioBuilder()
				.comNome("Fulano")
				.comEmail("fulano@email.com")
				.comSenha("12345678")
				.criar();

		em.persist(usuario);
		
		Usuario usuario2 = new UsuarioBuilder()
				.comNome("Apolo")
				.comEmail("apolo@email.com")
				.comSenha("123456")
				.criar();

		em.persist(usuario2);

		Leilao leilao = new LeilaoBuilder()
				.comNome("Mochila")
				.comValorInicial("500")
				.comData(LocalDate.now())
				.comUsuario(usuario)
				.criar();

		em.persist(leilao);

		Lance lance = new LanceBuilder()
				.comUsuario(usuario)
				.comValor("600")
				.comLeilao(leilao)
				.criar();
	
		em.persist(lance);
		
		Lance lanceUsuario2 = new LanceBuilder()
				.comUsuario(usuario)
				.comValor("900")
				.comLeilao(leilao)
				.criar();

		em.persist(lanceUsuario2);

		String acheiMenorLance = this.dao.buscarMenorLanceDoLeilao(leilao);
		System.out.println("Menor lance é " + acheiMenorLance );
		Assert.assertNotNull(acheiMenorLance);

	}
	
	@Test
	void deveriaCadastrarLance() {
		Usuario usuario = new UsuarioBuilder()
				.comNome("Fulano")
				.comEmail("fulano@email.com")
				.comSenha("12345678")
				.criar();

		em.persist(usuario);

		Leilao leilao = new LeilaoBuilder()
				.comNome("Mochila")
				.comValorInicial("500")
				.comData(LocalDate.now())
				.comUsuario(usuario)
				.criar();

		em.persist(leilao);

		Lance lance = new LanceBuilder()
				.comUsuario(usuario)
				.comValor("600")
				.comLeilao(leilao)
				.criar();

		em.persist(lance);
		
		Lance usuarioEncontrado = this.dao.buscarPorUsername(usuario.getNome());
		System.out.println("usuarioEncontrado "+ usuarioEncontrado);
		Assert.assertNotNull(usuarioEncontrado);
		
	}

	@Test
	void validarSeLeilaoPossuiUmlanceCadastradoPeloUsuario() {
		Usuario usuario = new UsuarioBuilder()
				.comNome("Fulano")
				.comEmail("fulano@email.com")
				.comSenha("12345678")
				.criar();

		em.persist(usuario);

		Leilao leilao = new LeilaoBuilder()
				.comNome("Mochila")
				.comValorInicial("500")
				.comData(LocalDate.now())
				.comUsuario(usuario)
				.criar();

		em.persist(leilao);

		Lance lance = new LanceBuilder()
				.comUsuario(usuario)
				.comValor("600")
				.comLeilao(leilao)
				.criar();

		em.persist(lance);
		
		Leilao usuarioEncontrado = this.dao.buscarPorUsernameLeilao(usuario.getNome());
		Assert.assertNotNull(usuarioEncontrado);
		
	}
	
	@Test
	void deveriaRemoverUmLanceDoLeilao() {
		Usuario usuario = new UsuarioBuilder()
				.comNome("Fulano")
				.comEmail("fulano@email.com")
				.comSenha("12345678")
				.criar();

		em.persist(usuario);

		Leilao leilao = new LeilaoBuilder()
				.comNome("Mochila")
				.comValorInicial("500")
				.comData(LocalDate.now())
				.comUsuario(usuario)
				.criar();

		em.persist(leilao);

		Lance lance = new LanceBuilder()
				.comUsuario(usuario)
				.comValor("600")
				.comLeilao(leilao)
				.criar();

		em.persist(lance);
		
		em.remove(lance);
		Assert.assertThrows(NoResultException.class, () -> this.dao.buscarPorUsername(usuario.getNome()));
	}
	
}
