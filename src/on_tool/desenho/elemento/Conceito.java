package on_tool.desenho.elemento;

import on_tool.desenho.AreaDesenho;

import org.jgraph.graph.GraphConstants;


public class Conceito extends Vertice {
	
	SetaEsquerda setaContinua;
	SetaDireita setaTracejada;
	
	public Conceito(String texto, double x, double y) {
		super(texto, x, y);

		GraphConstants.setAutoSize(getAttributes(),
				true);

		GraphConstants.setOpaque(getAttributes(),
				true);

		GraphConstants.setBorderColor(getAttributes(),
				AreaDesenho.corBorda);

		GraphConstants.setBackground(getAttributes(),
				AreaDesenho.corPreenchimento);

		setaContinua = new SetaEsquerda();
		this.add(setaContinua);

		setaTracejada = new SetaDireita();
		this.add(setaTracejada);
	}
	
	public SetaEsquerda pegaSetaContinua() {
		return setaContinua;
	}
	
	public SetaDireita pegaSetaTracejada() {
		return setaTracejada;
	}
	
}
