package on_tool.desenho.metodo;

import java.util.Vector;

import on_tool.desenho.AreaDesenho;
import on_tool.desenho.elemento.Conceito;
import on_tool.desenho.elemento.Legenda;
import on_tool.desenho.elemento.RelacaoBinaria;
import on_tool.gui.dialogo.Dialogos;

public class InsercaoElementos {
	
	public static Object inserirConceitoLegenda(AreaDesenho area,
			double x,
			double y) {
		switch (Dialogos.mostraDialogoInsercaoElemento(area)) {
			case 0: {
				Conceito con = new Conceito(
						TratamentoStrings.converteStringParaJGraph("????",
								AreaDesenho.fonte.getName(),
								AreaDesenho.fonte.getStyle(),
								AreaDesenho.fonte.getSize(),
								AreaDesenho.corLetra),
						x, y);
				area.getGraphLayoutCache().insert(con);
				return con;
			}
			case 1: {
				Legenda leg = new Legenda(
						TratamentoStrings.converteStringParaJGraph("????",
								AreaDesenho.fonte.getName(),
								AreaDesenho.fonte.getStyle(),
								AreaDesenho.fonte.getSize(),
								AreaDesenho.corLetra),
						x, y);
				area.getGraphLayoutCache().insert(leg);
				return leg;
			}
		}
		return null;
	}
	
	public static Object inserirConceito(AreaDesenho area,
			double x,
			double y) {
		Conceito con = new Conceito(
				TratamentoStrings.converteStringParaJGraph("????",
						AreaDesenho.fonte.getName(),
						AreaDesenho.fonte.getStyle(),
						AreaDesenho.fonte.getSize(),
						AreaDesenho.corLetra),
				x, y);
		area.getGraphLayoutCache().insert(con);
		return con;
	}
	
	public static Object inserirRelacaoBinaria(AreaDesenho area,
			double x,
			double y) {
		RelacaoBinaria rel = new RelacaoBinaria(
					"????", new Vector(), x, y);
			area.getGraphLayoutCache().insert(rel);
			return rel;
	}
	
}
