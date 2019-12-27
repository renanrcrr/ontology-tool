
package on_tool.desenho.metodo;

import java.util.Vector;

import on_tool.desenho.AreaDesenho;
import on_tool.desenho.elemento.Arco;
import on_tool.desenho.elemento.Conceito;
import on_tool.desenho.elemento.Legenda;
import on_tool.desenho.elemento.RelacaoBinaria;
import on_tool.desenho.elemento.Vertice;

import org.jgraph.graph.GraphConstants;


public class AreaTransferencia {
	
	private static Vector pegaDados(AreaDesenho area) {

		Vector dados = new Vector();

		Object[] selecionados = area.getSelectionCells();

		for (int i = 0; i < selecionados.length; i++) {
			if (selecionados[i] instanceof Conceito) {
				String s = (String)((Conceito)selecionados[i]).getUserObject();

				Conceito obj = new Conceito(s, 0, 0);

				GraphConstants.setBounds(obj.getAttributes(),
						GraphConstants.getBounds(
								((Conceito)selecionados[i]).getAttributes()));

				GraphConstants.setBorderColor(obj.getAttributes(),
						GraphConstants.getBorderColor(
								((Conceito)selecionados[i]).getAttributes()));

				GraphConstants.setBackground(obj.getAttributes(),
						GraphConstants.getBackground(
								((Conceito)selecionados[i]).getAttributes()));

				obj.mudaFonte(((Conceito)selecionados[i]).pegaFonte());

				obj.mudaFonteCor(((Conceito)selecionados[i]).pegaFonteCor());
				dados.add(obj);
			}
			else if (selecionados[i] instanceof RelacaoBinaria) {
				String supertipo = ((RelacaoBinaria)selecionados[i]).pegaSupertipo();
				Vector frases = ((RelacaoBinaria)selecionados[i]).pegaFrases();
				
				RelacaoBinaria obj = new RelacaoBinaria(supertipo, frases, 0, 0);

				GraphConstants.setBounds(obj.getAttributes(),
						GraphConstants.getBounds(
								((RelacaoBinaria)selecionados[i]).getAttributes()));

				obj.mudaFonte(((RelacaoBinaria)selecionados[i]).pegaFonte());

				obj.mudaFonteCor(((RelacaoBinaria)selecionados[i]).pegaFonteCor());
				obj.atualizaRotulo();
				dados.add(obj);
			}
			else if (selecionados[i] instanceof Legenda) {
				String s = (String)((Legenda)selecionados[i]).getUserObject();
				
				Legenda obj = new Legenda(s, 0, 0);

				GraphConstants.setBounds(obj.getAttributes(),
						GraphConstants.getBounds(
								((Legenda)selecionados[i]).getAttributes()));

				obj.mudaFonte(((Legenda)selecionados[i]).pegaFonte());

				obj.mudaFonteCor(((Legenda)selecionados[i]).pegaFonteCor());
				dados.add(obj);
			}
			else
				dados.add(null);
		}

		for (int i = 0; i < selecionados.length; i++) {
			if (selecionados[i] instanceof Arco &&
					((Arco)selecionados[i]).getSource() != null &&
					((Arco)selecionados[i]).getTarget() != null) {

				int j = 1, origem = 0, alvo = 0;
				while (j < selecionados.length) {
					if (j == i || !(selecionados[j] instanceof Vertice))
						j++;
					else if (((Arco)selecionados[i]).getSource()
							== ((Vertice)selecionados[j]).pegaPortPadrao()) {
						origem = j;
						j++;
					} else if (((Arco)selecionados[i]).getTarget()
							== ((Vertice)selecionados[j]).pegaPortPadrao()) {
						alvo = j;
						j++;
					} else
						j++;
				}
				Arco obj = new Arco();

				GraphConstants.setLineColor(obj.getAttributes(),
						GraphConstants.getLineColor(
								((Arco)selecionados[i]).getAttributes()));

				GraphConstants.setRouting(obj.getAttributes(),
						GraphConstants.getRouting(
								((Arco)selecionados[i]).getAttributes()));
				GraphConstants.setLineStyle(obj.getAttributes(),
						GraphConstants.getLineStyle(
								((Arco)selecionados[i]).getAttributes()));

				obj.setSource(((Vertice)dados.get(origem)).pegaPortPadrao());
				obj.setTarget(((Vertice)dados.get(alvo)).pegaPortPadrao());

				dados.set(i, obj);
			}
		}
		return dados;
	}
	
	public static void recortar(AreaDesenho area) {
		copiar(area);
		EdicaoElementos.excluirSelecionados(area);
	}
	
	public static void copiar(AreaDesenho area) {
		area.mudaAreaTransferencia(pegaDados(area));
	}
	
	public static void colar(AreaDesenho area) {
		area.getGraphLayoutCache().insert(area.pegaAreaTransferencia().toArray());
		copiar(area);
	}
	
}
