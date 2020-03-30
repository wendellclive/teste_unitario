package br.com.topicosespeciais.servicos;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.com.topicosespeciais.entidades.Filme;
import br.com.topicosespeciais.entidades.Locacao;
import br.com.topicosespeciais.entidades.Usuario;
import br.com.topicosespeciais.exceptions.FilmesSemEstoqueException;
import br.com.topicosespeciais.exceptions.LocadoraException;
import br.com.topicosespeciais.utils.DataUtils;

public class LocacaoServiceTest {

	private LocacaoService service;

	@Rule
	public ErrorCollector error = new ErrorCollector();

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void setup() {
		service = new LocacaoService();
	}

	@After
	public void teraDown() {
		// System.out.println("After");
	}

	@BeforeClass
	public static void setupClass() {
		// System.out.println("BeforeClass");
	}

	@AfterClass
	public static void teraDownClass() {
		// System.out.println("AfterClass");
	}

	@Test
	public void deveAlugarFilme() throws Exception {
		
		assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		
		// cenario
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 5.0));

		// acao

		Locacao locacao = service.alugarFilme(usuario, filmes);

		// verificacao
		error.checkThat(locacao.getValor(), is(equalTo(5.0)));
		error.checkThat(locacao.getValor(), is(not(6.0)));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)),
				is(true));

	}

	// Forma Elegante
	@Test(expected = FilmesSemEstoqueException.class)
	public void naoDeveAlugarFilmesSemEstoque() throws Exception {

		// cenario
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 0, 4.0));

		// acao
		service.alugarFilme(usuario, filmes);

	}

	// Forma Robusta
	@Test
	public void naoDeveAlugarFilmeSemUsuario() throws FilmesSemEstoqueException {

		// cenario
		LocacaoService service = new LocacaoService();
		List<Filme> filmes = Arrays.asList(new Filme("Filme 2", 2, 4.0));
		// Usuario usuario = new Usuario("Usuario 1");

		// acao
		try {
			service.alugarFilme(null, filmes);
			Assert.fail();
		} catch (LocadoraException e) {
			// TODO Auto-generated catch block
			Assert.assertThat(e.getMessage(), is("Usuario vazio"));
		}
	}

	// Forma Nova
	@Test
	public void naoDeveAlugarFilmeSemFilme() throws FilmesSemEstoqueException, LocadoraException {

		// cenario
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");

		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio");

		// acao
		service.alugarFilme(usuario, null);

	}

	@Test
	public void devePagar75pctNoFilme3() throws FilmesSemEstoqueException, LocadoraException {
		// cenario
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("FIlme 1", 2, 4.0), new Filme("FIlme 2", 2, 4.0),
				new Filme("FIlme 3", 2, 4.0));

		// acao
		Locacao resultado = service.alugarFilme(usuario, filmes);

		// verificacao
		assertThat(resultado.getValor(), is(11.0));

	}

	@Test
	public void devePagar50pctNoFilme4() throws FilmesSemEstoqueException, LocadoraException {
		// cenario
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("FIlme 1", 2, 4.0), new Filme("FIlme 2", 2, 4.0),
				new Filme("FIlme 3", 2, 4.0), new Filme("FIlme 4", 2, 4.0));

		// acao
		Locacao resultado = service.alugarFilme(usuario, filmes);

		// verificacao
		assertThat(resultado.getValor(), is(13.0));

	}

	@Test
	public void devePagar25pctNoFilme5() throws FilmesSemEstoqueException, LocadoraException {
		// cenario
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("FIlme 1", 2, 4.0), new Filme("FIlme 2", 2, 4.0),
				new Filme("FIlme 3", 2, 4.0), new Filme("FIlme 4", 2, 4.0), new Filme("FIlme 5", 2, 4.0));

		// acao
		Locacao resultado = service.alugarFilme(usuario, filmes);

		// verificacao
		assertThat(resultado.getValor(), is(14.0));

	}

	@Test
	public void devePagar0pctNoFilme6() throws FilmesSemEstoqueException, LocadoraException {
		// cenario
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("FIlme 1", 2, 4.0), new Filme("FIlme 2", 2, 4.0),
				new Filme("FIlme 3", 2, 4.0), new Filme("FIlme 4", 2, 4.0), new Filme("FIlme 5", 2, 4.0),
				new Filme("FIlme 6", 2, 4.0));

		// acao
		Locacao resultado = service.alugarFilme(usuario, filmes);

		// verificacao
		assertThat(resultado.getValor(), is(14.0));

	}

	@Test
	public void deveDevolverNaSegundaAoAlugarNoSabado() throws FilmesSemEstoqueException, LocadoraException {
	
		assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		
		//cenario
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 1, 5.0));
		
		//acao
		Locacao retorno = service.alugarFilme(usuario, filmes);
		
		//verificacao
		boolean ehSegunda = DataUtils.verificarDiaSemana(retorno.getDataRetorno(), Calendar.MONDAY);
		assertTrue(ehSegunda);
		
	}
	/*
	 * // Forma Robusta
	 * 
	 * @Test public void testLocacao_filmesSemEstoque2() {
	 * 
	 * // cenario LocacaoService service = new LocacaoService(); Usuario usuario =
	 * new Usuario("Usuario 1"); Filme filme = new Filme("Filme 1", 0, 5.0);
	 * 
	 * // acao try { service.alugarFilme(usuario, filme);
	 * Assert.fail("Deveria ter lançado uma exceção"); } catch (Exception e) {
	 * Assert.assertThat(e.getMessage(), is("Filme sem estoque")); }
	 * 
	 * }
	 * 
	 * // Forma Nova
	 * 
	 * @Test public void testLocacao_filmesSemEstoque3() throws Exception {
	 * 
	 * // cenario LocacaoService service = new LocacaoService(); Usuario usuario =
	 * new Usuario("Usuario 1"); Filme filme = new Filme("Filme 1", 0, 5.0);
	 * 
	 * exception.expect(Exception.class);
	 * exception.expectMessage("Filme sem estoque");
	 * 
	 * // acao service.alugarFilme(usuario, filme);
	 * 
	 * }
	 */
}
