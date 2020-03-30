package br.com.topicosespeciais.servicos;

import static br.com.topicosespeciais.utils.DataUtils.adicionarDias;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.topicosespeciais.entidades.Filme;
import br.com.topicosespeciais.entidades.Locacao;
import br.com.topicosespeciais.entidades.Usuario;
import br.com.topicosespeciais.exceptions.FilmesSemEstoqueException;
import br.com.topicosespeciais.exceptions.LocadoraException;
import br.com.topicosespeciais.utils.DataUtils;

public class LocacaoService {

	public Locacao alugarFilme(Usuario usuario, List<Filme> filmes)
			throws FilmesSemEstoqueException, LocadoraException {

		if (usuario == null) {
			throw new LocadoraException("Usuario vazio");
		}

		if (filmes == null) {
			throw new LocadoraException("Filme vazio");
		}

		for (Filme filme : filmes) {
			if (filme.getEstoque() == 0) {
				throw new FilmesSemEstoqueException();
			}
		}

		Locacao locacao = new Locacao();
		locacao.setFilme(filmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());

		Double valorTotal = 0d;

		for (int i = 0; i < filmes.size(); i++) {
			Filme filme = filmes.get(i);
			Double valorFilme = filme.getPrecoLocacao();
			switch (i) {
				case 2: valorFilme = valorFilme * 0.75;	break;
				case 3: valorFilme = valorFilme * 0.50;	break;
				case 4: valorFilme = valorFilme * 0.25; break;
				case 5: valorFilme = valorFilme * 0d; break;
			}
			valorTotal += valorFilme;
		}

		locacao.setValor(valorTotal);

		// Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		if(DataUtils.verificarDiaSemana(dataEntrega, Calendar.SUNDAY)) {
			dataEntrega = adicionarDias(dataEntrega, 1);
		}
		locacao.setDataRetorno(dataEntrega);

		// Salvando a locacao...
		// TODO adicionar método para salvar

		return locacao;
	}

}