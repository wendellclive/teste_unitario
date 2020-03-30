package br.com.topicosespeciais.servicos;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import br.com.topicosespeciais.exceptions.NaoPodeDividirPorZeroException;


public class CalculadoraTest {

	private Calculadora calc;
	
	@Before
	public void setup() {
		calc = new Calculadora();
	}
	
	@Test
	public void deveSomarDoisValores() {
		
		//cenario
		int a = 5;
		int b = 3;
		
		// acao
		int resultado = calc.somar(a, b);
		
		//verificacao
		assertEquals(8, resultado);
	}

	@Test
	public void deveSubtrairDoisValores() {
		
		//cenario
		int a = 8;
		int b = 5;
		
		// acao
		int resultado = calc.subtrair(a, b);
		
		//verificacao
		assertEquals(3, resultado);
	}

	@Test
	public void deveDividirDoisValores() throws NaoPodeDividirPorZeroException {
		
		//cenario
		int a = 6;
		int b = 3;
		
		// acao
		int resultado = calc.dividir(a, b);
		
		//verificacao
		assertEquals(2, resultado);
	}

	@Test(expected = NaoPodeDividirPorZeroException.class)
	public void deveLancarExcecaoAoDividirPorZero() throws NaoPodeDividirPorZeroException {

		int a = 10;
		int b = 0;
		Calculadora calc = new Calculadora();
		
		calc.dividir(a, b);
		
	}
	

}
