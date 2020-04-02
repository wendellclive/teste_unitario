package br.com.topicosespeciais.servicos;

import static br.com.topicosespeciaias.builder.FilmeBuilder.umFilme;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import br.com.topicosespeciais.entidades.Filme;
import br.com.topicosespeciais.entidades.Locacao;
import br.com.topicosespeciais.entidades.Usuario;
import br.com.topicosespeciais.exceptions.FilmesSemEstoqueException;
import br.com.topicosespeciais.exceptions.LocadoraException;

@RunWith(Parameterized.class)
public class CalculoValorLocacaoTest {

	private LocacaoService service;

	@Parameter
	public List<Filme> filmes;
	
	@Parameter(value=1)
	public Double valorLocacao;

	@Parameter(value = 2)
	public String cenario;

	@Before
	public void setup() {
		service = new LocacaoService();
	}

	private static Filme filme1 = umFilme().agora();
	private static Filme filme2 = umFilme().agora();
	private static Filme filme3 = umFilme().agora();
	private static Filme filme4 = umFilme().agora();
	private static Filme filme5 = umFilme().agora();
	private static Filme filme6 = umFilme().agora();
	private static Filme filme7 = umFilme().agora();
	
	@Parameters(name="{2}")
	public static Collection<Object[]> getParametros() {
		return Arrays.asList(new Object[][] {
			{Arrays.asList(filme1, filme2), 8.0, "2Filmes: Sem desconto"},
			{Arrays.asList(filme1, filme2, filme3), 11.0, "3Filmes: 25%"},
			{Arrays.asList(filme1, filme2, filme3, filme4), 13.0, "4Filmes: 50%"},
			{Arrays.asList(filme1, filme2, filme3, filme4, filme5), 14.0, "5Filmes: 75%"},
			{Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6), 14.0, "6Filmes: 100%"},
			{Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6, filme7), 18.0, "7Filmes: Sem Desconto"},
		});
	}

	@Test
	public void deveCalcularValorLocacaoConsiderandoDescontos() throws FilmesSemEstoqueException, LocadoraException {

		// cenario
		Usuario usuario = new Usuario("Usuario 1");

		// acao
		Locacao resultado = service.alugarFilme(usuario, filmes);

		// verificacao
		assertThat(resultado.getValor(), is(valorLocacao));
		
		System.out.println("!");

	}

	@Test
	public void print()  {

		System.out.println(valorLocacao);

	}

}
