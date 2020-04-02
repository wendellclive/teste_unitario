package br.com.topicosespeciais.servicos;


import static br.com.topicosespeciaias.builder.FilmeBuilder.umFilme;
import static br.com.topicosespeciaias.builder.FilmeBuilder.umFilmeSemEstoque;
import static br.com.topicosespeciaias.builder.UsuarioBuilder.umUsuario;
import static br.com.topicosespeciais.matchers.MatchersProprios.caiNumaSegunda;
import static br.com.topicosespeciais.matchers.MatchersProprios.ehHoje;
import static br.com.topicosespeciais.matchers.MatchersProprios.ehHojeComDiferencaDias;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
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
import buildermaster.BuilderMaster;

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
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = Arrays.asList(umFilme().comValor(5.0).agora());

		// acao

		Locacao locacao = service.alugarFilme(usuario, filmes);

		// verificacao
		error.checkThat(locacao.getValor(), is(equalTo(5.0)));
		error.checkThat(locacao.getValor(), is(not(6.0)));
		error.checkThat(locacao.getDataLocacao(), ehHoje());
		error.checkThat(locacao.getDataLocacao(), ehHojeComDiferencaDias(1));
	}

	// Forma Elegante
	@Test(expected = FilmesSemEstoqueException.class)
	public void naoDeveAlugarFilmesSemEstoque() throws Exception {

		// cenario
		LocacaoService service = new LocacaoService();
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = Arrays.asList(umFilmeSemEstoque().agora());

		// acao
		service.alugarFilme(usuario, filmes);

	}

	// Forma Robusta
	@Test
	public void naoDeveAlugarFilmeSemUsuario() throws FilmesSemEstoqueException {

		// cenario
		LocacaoService service = new LocacaoService();
		List<Filme> filmes = Arrays.asList(umFilme().agora());
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
		Usuario usuario = umUsuario().agora();

		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio");

		// acao
		service.alugarFilme(usuario, null);

	}

	@Test
	public void devePagar75pctNoFilme3() throws FilmesSemEstoqueException, LocadoraException {
		// cenario
		Usuario usuario = umUsuario().agora();
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
		Usuario usuario = umUsuario().agora();
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
		Usuario usuario = umUsuario().agora();
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
		Usuario usuario = umUsuario().agora();
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
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 1, 5.0));
		
		//acao
		Locacao retorno = service.alugarFilme(usuario, filmes);
		
		//verificacao
		//assertThat(retorno.getDataRetorno(), new DiaSemanaMatcher(Calendar.MONDAY));
		//assertThat(retorno.getDataRetorno(), caiEm(Calendar.SUNDAY));
		assertThat(retorno.getDataRetorno(), caiNumaSegunda());
				
	}

	public static void main(String[] args) {
		new BuilderMaster().gerarCodigoClasse(Locacao.class);
	}
}
