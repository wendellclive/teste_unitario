package br.com.topicosespeciais.matchers;

import java.util.Calendar;

public class MatchersProprios {

	public static DiaSemanaMatcher caiEm(Integer diaSemana) {
		return new DiaSemanaMatcher(diaSemana);
	}
	
	public static DiaSemanaMatcher caiNumaSegunda() {
		return new DiaSemanaMatcher(Calendar.SUNDAY);
	}
	
	public static DiaSemanaMatcher ehHojeComDiferencaDias(Integer qtdDias) {
		return new DiaSemanaMatcher(qtdDias);
	}
	
	public static DiaSemanaMatcher ehHoje() {
		return new DiaSemanaMatcher(0);
	}
	
}
