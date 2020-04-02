package br.com.topicosespeciais.suites;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runners.Suite.SuiteClasses;

import br.com.topicosespeciais.servicos.CalculadoraTest;
import br.com.topicosespeciais.servicos.CalculoValorLocacaoTest;
import br.com.topicosespeciais.servicos.LocacaoServiceTest;

//@RunWith(Suite.class)
@SuiteClasses({
	CalculadoraTest.class,
	CalculoValorLocacaoTest.class,
	LocacaoServiceTest.class
})
public class SuiteExecucao {
	//Remova se puder!
	@BeforeClass
	public static void before() {
		System.out.println("before");
	}
	
	@AfterClass
	public static void after() {
		System.out.println("after");
	}
	
}
