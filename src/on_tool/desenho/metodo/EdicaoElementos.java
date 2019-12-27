package on_tool.desenho.metodo;

import java.awt.Color;
import java.awt.Font;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import on_tool.desenho.AreaDesenho;
import on_tool.desenho.elemento.RelacaoBinaria;
import on_tool.desenho.elemento.Vertice;
import on_tool.gui.dialogo.Dialogos;
import on_tool.gui.dialogo.PainelEstiloLinha;

import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.Edge;
import org.jgraph.graph.GraphConstants;

import on_tool.desenho.elemento.Conceito;
import on_tool.desenho.metodo.TratamentoStrings;


public class EdicaoElementos 
{	
	public static void editarCorLinha(Object objeto,
			AreaDesenho area) {
		Color cor = GraphConstants.getLineColor(
				((Edge)objeto).getAttributes());
		cor = Dialogos.mostraDialogoCor(area,
				cor);
		if (cor != null) {
			Map atribs = new Hashtable();
			GraphConstants.setLineColor(atribs, cor);
			Object[] objetos = area.getSelectionCells();
			for (int i = 0; i < objetos.length; i++)
				if (objetos[i] instanceof Edge)
					area.getGraphLayoutCache().editCell(objetos[i], atribs);
		}
	}
	
	public static void editarEstiloLinha(Object objeto,
			AreaDesenho area) {
		int estilo = 0;
		if (GraphConstants.getRouting(((Edge)objeto).getAttributes())
				== GraphConstants.ROUTING_DEFAULT)
			estilo = PainelEstiloLinha.NORMAL;
		else if (GraphConstants.getLineStyle(((Edge)objeto).getAttributes())
				== GraphConstants.STYLE_ORTHOGONAL)
			estilo = PainelEstiloLinha.ORTOGONAL;
		else if (GraphConstants.getLineStyle(((Edge)objeto).getAttributes())
				== GraphConstants.STYLE_SPLINE)
			estilo = PainelEstiloLinha.CURVA;
		int estiloNovo = Dialogos.mostraDialogoEstiloLinha(
				area, estilo);
		if (estiloNovo == PainelEstiloLinha.NORMAL) {
			Map atribs = new Hashtable();
			GraphConstants.setRouting(atribs,
					GraphConstants.ROUTING_DEFAULT);
			GraphConstants.setLineStyle(atribs,
					GraphConstants.STYLE_ORTHOGONAL);
			Object[] objetos = area.getSelectionCells();
			for (int i = 0; i < objetos.length; i++)
				if (objetos[i] instanceof Edge)
					area.getGraphLayoutCache().editCell(objetos[i], atribs);
		}
		else if (estiloNovo == PainelEstiloLinha.ORTOGONAL) {
			Map atribs = new Hashtable();
			GraphConstants.setRouting(atribs,
					GraphConstants.ROUTING_SIMPLE);
			GraphConstants.setLineStyle(atribs,
					GraphConstants.STYLE_ORTHOGONAL);
			Object[] objetos = area.getSelectionCells();
			for (int i = 0; i < objetos.length; i++)
				if (objetos[i] instanceof Edge)
					area.getGraphLayoutCache().editCell(objetos[i], atribs);
		}
		else if (estiloNovo == PainelEstiloLinha.CURVA) {
			Map atribs = new Hashtable();
			GraphConstants.setRouting(atribs,
					GraphConstants.ROUTING_SIMPLE);
			GraphConstants.setLineStyle(atribs,
					GraphConstants.STYLE_SPLINE);
			Object[] objetos = area.getSelectionCells();
			for (int i = 0; i < objetos.length; i++)
				if (objetos[i] instanceof Edge)
					area.getGraphLayoutCache().editCell(objetos[i], atribs);
		}
	}
	
	public static void editarCorBorda(Object objeto,
			AreaDesenho area) {
		Color cor = GraphConstants.getBorderColor(
				((Vertice)objeto).getAttributes());
		cor = Dialogos.mostraDialogoCor(area,
				cor);
		if (cor != null) {
			Map atribs = new Hashtable();
			GraphConstants.setBorderColor(atribs, cor);
			Object[] objetos = area.getSelectionCells();
			for (int i = 0; i < objetos.length; i++)
				if (objetos[i] instanceof Conceito)
					area.getGraphLayoutCache().editCell(objetos[i], atribs);
		}
	}
	
	public static void editarCorPreenchimento(Object objeto,
			AreaDesenho area) {
		Color cor = GraphConstants.getBackground(
				((Vertice)objeto).getAttributes());
		cor = Dialogos.mostraDialogoCor(area,
				cor);
		if (cor != null) {
			Map atribs = new Hashtable();
			GraphConstants.setBackground(atribs, cor);
			Object[] objetos = area.getSelectionCells();
			for (int i = 0; i < objetos.length; i++)
				if (objetos[i] instanceof Conceito)
					area.getGraphLayoutCache().editCell(objetos[i], atribs);
		}
	}
	
	public static void editarCorLetra(Object objeto,
			AreaDesenho area) {
		Color cor = ((Vertice)objeto).pegaFonteCor();
		cor = Dialogos.mostraDialogoCor(area,
				cor);
		if (cor != null) {
			Object[] objetos = area.getSelectionCells();
			for (int i = 0; i < objetos.length; i++)				
				if (objetos[i] instanceof Vertice) {
					((Vertice)objetos[i]).mudaFonteCor(cor);
					String rotulo = (String)((Vertice)objetos[i]).getUserObject();
					rotulo = TratamentoStrings.converteStringParaJava(rotulo);
					Map atribs = new Hashtable();
					GraphConstants.setValue(atribs,
							TratamentoStrings.converteStringParaJGraph(rotulo,
									((Vertice)objetos[i]).pegaFonte().getName(),
									((Vertice)objetos[i]).pegaFonte().getStyle(),
									((Vertice)objetos[i]).pegaFonte().getSize(),
									((Vertice)objetos[i]).pegaFonteCor()));
					area.getGraphLayoutCache().editCell(objetos[i], atribs);
				}
		}
	}
	
	public static void editarFonte(Object objeto,
			AreaDesenho area) {
		Font fonte = ((Vertice)objeto).pegaFonte();
		fonte = Dialogos.mostraDialogoFonte(area,
				fonte);
		if (fonte != null) {
			Object[] objetos = area.getSelectionCells();
			for (int i = 0; i < objetos.length; i++)
				if (objetos[i] instanceof Vertice) {
					((Vertice)objetos[i]).mudaFonte(fonte);
					String rotulo = (String)((Vertice)objetos[i]).getUserObject();
					rotulo = TratamentoStrings.converteStringParaJava(rotulo);
					Map atribs = new Hashtable();
					GraphConstants.setValue(atribs,
							TratamentoStrings.converteStringParaJGraph(rotulo,
									((Vertice)objetos[i]).pegaFonte().getName(),
									((Vertice)objetos[i]).pegaFonte().getStyle(),
									((Vertice)objetos[i]).pegaFonte().getSize(),
									((Vertice)objetos[i]).pegaFonteCor()));
					area.getGraphLayoutCache().editCell(objetos[i], atribs);
				}
		}
	}
	
	public static void editarRotulo(Object objeto,
			AreaDesenho area) {
		String rotulo = (String)((Vertice)objeto).getUserObject();
		rotulo = TratamentoStrings.converteStringParaJava(rotulo);
		String resposta = Dialogos.mostraDialogoEdicaoRotulo(area,
				rotulo);
		if (resposta != null) {
			Map atribs = new Hashtable();
			GraphConstants.setValue(atribs,
					TratamentoStrings.converteStringParaJGraph(resposta,
							((Vertice)objeto).pegaFonte().getName(),
							((Vertice)objeto).pegaFonte().getStyle(),
							((Vertice)objeto).pegaFonte().getSize(),
							((Vertice)objeto).pegaFonteCor()));
			area.getGraphLayoutCache().editCell(objeto, atribs);
		}
	}
	
	public static void editarRelacaoBinaria(RelacaoBinaria objeto,
			AreaDesenho area) {
		
		Vector v = Dialogos.mostrarDialogoRelacaoBinaria(
				area, objeto);
		if (v != null) 
		{
			String supertipo = (String)v.get(0);
			objeto.mudaSupertipo(supertipo);
			objeto.pegaFrases().removeAllElements();
			for (int i = 1; i < v.size(); i++)
				objeto.pegaFrases().add((String)v.get(i));

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
			Map atribs = new Hashtable();
			GraphConstants.setValue(atribs,
					TratamentoStrings.converteStringParaJGraph(valor,
							((Vertice)objeto).pegaFonte().getName(),
							((Vertice)objeto).pegaFonte().getStyle(),
							((Vertice)objeto).pegaFonte().getSize(),
							((Vertice)objeto).pegaFonteCor()));
			area.getGraphLayoutCache().editCell(objeto, atribs);
		}
	}
	
	public static void excluirSelecionados(AreaDesenho area) {
		if (!area.isSelectionEmpty()) {
			area.getGraphLayoutCache().remove(
					area.getSelectionCells(), true, true);
			area.getGraphLayoutCache().remove(
					pegaFrasesInuteis(area), true, true);
		}
	}
	
	public static Object[] pegaFrasesInuteis(AreaDesenho area) {
		Object[] cells = area.getGraphLayoutCache().getCells(
				false, true, false, false);
		Vector v = new Vector();
		for (int i = 0; i < cells.length; i++) {
			if (cells[i] instanceof RelacaoBinaria) {
				if (DefaultGraphModel.getIncomingEdges(area.getModel(),
						((Vertice)cells[i]).pegaPortPadrao()).length == 0 &&
						DefaultGraphModel.getOutgoingEdges(area.getModel(),
						((Vertice)cells[i]).pegaPortPadrao()).length == 0)
					v.add(cells[i]);
			}
		}
		return v.toArray();
	}
	
}
