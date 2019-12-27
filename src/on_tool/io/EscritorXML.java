package on_tool.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Vector;

import on_tool.desenho.AreaDesenho;
import on_tool.desenho.elemento.Arco;
import on_tool.desenho.elemento.Conceito;
import on_tool.desenho.elemento.RelacaoBinaria;
import on_tool.desenho.elemento.Legenda;
import on_tool.desenho.elemento.Vertice;
import on_tool.desenho.metodo.TratamentoStrings;
import on_tool.gui.dialogo.PainelEstiloLinha;

import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.DefaultPort;
import org.jgraph.graph.GraphConstants;


public class EscritorXML 
{	

	public static boolean salvarArquivo(String strArquivo, AreaDesenho area) 
	{
		boolean retorno = true;
		if (!strArquivo.endsWith(".xml"))
			strArquivo += ".xml";
		File arquivo = new File(strArquivo);
		try {
			BufferedWriter saida = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(arquivo), "UTF-8"));
			String strArea = lerAreaDesenho(area);
			for (int i = 0; i < strArea.length(); i++) 
			{
				if (strArea.charAt(i) == '\n')
					saida.newLine();
				else
					saida.write(strArea.charAt(i));
			}
			saida.flush();
			saida.close();
		}
		catch (IOException exc) 
		{
			exc.printStackTrace();
			retorno = false;
		}
		return retorno;
	}
	

	private static String lerAreaDesenho(AreaDesenho area) {
		String retorno = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
				"<!DOCTYPE ontologia SYSTEM \"src#on_tool#io#Ontologia.dtd\">\n" +
				"<!--<!DOCTYPE ontologia SYSTEM \"Ontologia.dtd\">-->\n\n" +
				"<ontologia>\n<dominio>dominio</dominio>\n";
		
		Vector verticesOrdenados = pegaVerticesOrdenados(area);
		
		retorno += pegaRelacoesBinarias(verticesOrdenados, area) + "\n"	+
				pegaConceitos(verticesOrdenados, area) + "\n" +
				pegaLegendas(verticesOrdenados, area) + "\n" +
				pegaArcos(area, verticesOrdenados) + "\n" +
				"</ontologia>";
		return retorno;
	}
	

	private static Vector pegaVerticesOrdenados(AreaDesenho area) {
		Vector retorno = new Vector();
		Object[] vertices = area.getGraphLayoutCache().getCells(false,
				true, false, false);
		for (int i = 0; i < vertices.length; i++) {
			if (vertices[i] instanceof RelacaoBinaria)
				retorno.add(vertices[i]);
		}
		for (int i = 0; i < vertices.length; i++) {
			if (vertices[i] instanceof Conceito)
				retorno.add(vertices[i]);
		}
		return retorno;
	}
	
	private static String pegaArcos(AreaDesenho area, Vector vertices) {
		String retorno = "";
		Object[] arcos = area.getGraphLayoutCache().getCells(
				false, false, false, true);
		for (int i = 0; i < arcos.length; i++) {
			retorno += "<arco ";
			for (int j = 0; j < vertices.size(); j++)
				if (((DefaultEdge)arcos[i]).getSource() ==
						((Vertice)vertices.get(j)).pegaPortPadrao()) {
					retorno += "origem=\"" + j + "\" ";
					break;
				}
			for (int j = 0; j < vertices.size(); j++)
				if (((DefaultEdge)arcos[i]).getTarget() ==
						((Vertice)vertices.get(j)).pegaPortPadrao()) {
					retorno += "alvo=\"" + j + "\" ";
					break;
				}
			retorno += "corlinha=\"" + GraphConstants.getLineColor(
					area.getAttributes(arcos[i])).getRGB() + "\" ";
			if (GraphConstants.getRouting(area.getAttributes(arcos[i]))
					== GraphConstants.ROUTING_SIMPLE
					&&
					GraphConstants.getLineStyle(area.getAttributes(arcos[i]))
					== GraphConstants.STYLE_ORTHOGONAL) {
				retorno += "estilo=\"" + PainelEstiloLinha.ORTOGONAL + "\" ";
			}
			else if (GraphConstants.getRouting(area.getAttributes(arcos[i]))
					== GraphConstants.ROUTING_SIMPLE
					&&
					GraphConstants.getLineStyle(area.getAttributes(arcos[i]))
					== GraphConstants.STYLE_SPLINE) {
				retorno += "estilo=\"" + PainelEstiloLinha.CURVA + "\" ";
			}
			else {
				retorno += "estilo=\"" + PainelEstiloLinha.NORMAL + "\" ";
			}
			retorno += "/>\n";
		}
		return retorno;
	}
	
	private static String pegaConceitos(Vector v, AreaDesenho area) {
		String retorno = "";
		for (int i = 0; i < v.size(); i++)
			if (v.get(i) instanceof Conceito) {
				String s = "<conceito id=\"" + i + "\" ";
				s += "texto=\"" +
						TratamentoStrings.converteStringParaJava((String)((Vertice)v.get(i)).getUserObject()).replace("\n", "\\n") +
						"\" ";
				s += "x=\"" +
						area.getCellBounds(v.get(i)).getX() + "\" ";
				s += "y=\"" +
						area.getCellBounds(v.get(i)).getY() + "\" ";
				s += "corborda=\"" + GraphConstants.getBorderColor(
						((Vertice)v.get(i)).getAttributes()).getRGB() +
						"\" ";
				s += "corpreenchimento=\"" + GraphConstants.getBackground(
						((Vertice)v.get(i)).getAttributes()).getRGB() +
						"\" ";
				s += "nomeletra=\"" + ((Vertice)v.get(i)).pegaFonte().getFontName() +
						"\" ";
				s += "estiloletra=\"" + ((Vertice)v.get(i)).pegaFonte().getStyle() +
						"\" ";
				s += "tamanholetra=\"" + ((Vertice)v.get(i)).pegaFonte().getSize() +
						"\" ";
				s += "corletra=\"" + ((Vertice)v.get(i)).pegaFonteCor().getRGB() +
						"\" ";
				s += "/>\n";
				retorno += s;
			}
		return retorno;
	}
	
	private static String pegaLegendas(Vector v, AreaDesenho area) {
		String retorno = "";
		for (int i = 0; i < v.size(); i++)
			if (v.get(i) instanceof Legenda) {
				String s = "<legenda id=\"" + i + "\" ";
				s += "texto=\"" +
						TratamentoStrings.converteStringParaJava((String)((Vertice)v.get(i)).getUserObject()).replace("\n", "\\n") +
						"\" ";
				s += "x=\"" +
						area.getCellBounds(v.get(i)).getX() + "\" ";
				s += "y=\"" +
						area.getCellBounds(v.get(i)).getY() + "\" ";
				s += "nomeletra=\"" + ((Vertice)v.get(i)).pegaFonte().getFontName() +
						"\" ";
				s += "estiloletra=\"" + ((Vertice)v.get(i)).pegaFonte().getStyle() +
						"\" ";
				s += "tamanholetra=\"" + ((Vertice)v.get(i)).pegaFonte().getSize() +
						"\" ";
				s += "corletra=\"" + ((Vertice)v.get(i)).pegaFonteCor().getRGB() +
						"\" ";
				s += "/>\n";
				retorno += s;
			}
		return retorno;
	}
	
	private static String pegaRelacoesBinarias(Vector v, AreaDesenho area) {
		String retorno = "";
		Vector relacoes = new Vector();
		for (int i = 0; i < v.size(); i++)
			if (v.get(i) instanceof RelacaoBinaria) {
				RelacaoBinaria rb = (RelacaoBinaria)v.get(i);
				retorno += "<valor-relacao cod=\"" + i + "\" ";
				retorno += "x=\"" + area.getCellBounds(rb).getX() + "\" ";
				retorno += "y=\"" +	area.getCellBounds(rb).getY() + "\" ";
				retorno += "nomeletra=\"" + rb.pegaFonte().getFontName() + "\" ";
				retorno += "estiloletra=\"" + rb.pegaFonte().getStyle() + "\" ";
				retorno += "tamanholetra=\"" + rb.pegaFonte().getSize() + "\" ";
				retorno += "corletra=\"" + rb.pegaFonteCor().getRGB() + "\" ";
				retorno += ">\n";
				retorno += "<supertipo cadeia=\"" + rb.pegaSupertipo() + "\">\n";
				Vector frases = rb.pegaFrases();
				for (int j = 0; j < frases.size(); j++) {
					String frase = (String)frases.get(j);
					retorno += "<frase>" + frase + "</frase>\n";
				}
				retorno += "</supertipo>\n";
				retorno += "</valor-relacao>\n";
				relacoes.add(rb);
			}
		retorno += "\n";

		int seq = 0;
		for (int i = 0; i < relacoes.size(); i++) {
			Object[] arcosEntrando = DefaultGraphModel.getIncomingEdges(area.getModel(), relacoes.get(i));
			Object[] arcosSaindo = DefaultGraphModel.getOutgoingEdges(area.getModel(), relacoes.get(i));
			for (int j = 0; j < arcosEntrando.length; j++) {
				for (int k = 0; k < arcosSaindo.length; k++) {
					retorno += "<relacao-binaria cod=\"" + seq + "\">\n";
					Object port = ((Arco)arcosEntrando[j]).getSource();
					Vertice conceito = (Vertice)((DefaultPort)port).getParent();
					retorno += "<conceito-origem>" +
							TratamentoStrings.converteStringParaJava((String)conceito.getUserObject()).replace("\n", " ") +
							"</conceito-origem>\n";
					port = ((Arco)arcosSaindo[k]).getTarget();
					conceito = (Vertice)((DefaultPort)port).getParent();
					retorno += "<conceito-destino>" +
							TratamentoStrings.converteStringParaJava((String)conceito.getUserObject()).replace("\n", " ") +
							"</conceito-destino>\n";
					retorno += "<cod-valor-relacao>" + i + "</cod-valor-relacao>\n";
					retorno += "</relacao-binaria>\n";
					seq++;
				}
			}
		}
		return retorno;
	}

}
