package on_tool.desenho.elemento;

import java.util.Vector;

import on_tool.desenho.metodo.TratamentoStrings;

import org.jgraph.graph.GraphConstants;

public class RelacaoBinaria extends Vertice {
	
	private SetaEsquerda setaContinua;
	private SetaDireita setaTracejada;
	String supertipo;
	Vector frases;
	
	public RelacaoBinaria(String supertipo,
			Vector frases,
			double x,
			double y) {
		super("", x, y);

		GraphConstants.setAutoSize(getAttributes(),
				true);
		setaContinua = new SetaEsquerda();
		setaTracejada = new SetaDireita();
		this.add(setaContinua);
		this.add(setaTracejada);
		this.mudaSupertipoAtualizaRotulo(supertipo);
		this.frases = frases;
	}
	
	public SetaEsquerda pegaSetaContinua() {
		return setaContinua;
	}
	
	public SetaDireita pegaSetaTracejada() {
		return setaTracejada;
	}
	
	public void mudaSupertipoAtualizaRotulo(String s) {
		supertipo = s;
		String inv = "";
		int i = s.length() - 1;
		while (i >= 0 &&
				s.charAt(i) != '.') {
			inv += s.charAt(i);
			i--;
		}
		String valor = "";
		for (i = inv.length() - 1; i >= 0; i--)
			valor += inv.charAt(i);
		this.setUserObject(TratamentoStrings.converteStringParaJGraph(valor,
				pegaFonte().getName(),
				pegaFonte().getStyle(),
				pegaFonte().getSize(),
				pegaFonteCor()));
	}
	
	public void mudaSupertipo(String s) {
		supertipo = s;
	}
	
	public String pegaSupertipo() {
		return supertipo;
	}
	
	public void atualizaRotulo() {
		String inv = "";
		int i = supertipo.length() - 1;
		while (i >= 0 &&
				supertipo.charAt(i) != '.') {
			inv += supertipo.charAt(i);
			i--;
		}
		String valor = "";
		for (i = inv.length() - 1; i >= 0; i--)
			valor += inv.charAt(i);
		this.setUserObject(TratamentoStrings.converteStringParaJGraph(valor,
				pegaFonte().getName(),
				pegaFonte().getStyle(),
				pegaFonte().getSize(),
				pegaFonteCor()));
	}
	
	public Vector pegaFrases() {
		return frases;
	}
	
}
