package on_tool.io;

import java.awt.Color;
import java.awt.Font;
import java.util.Vector;

import on_tool.desenho.elemento.Arco;
import on_tool.desenho.elemento.Conceito;
import on_tool.desenho.elemento.RelacaoBinaria;
import on_tool.desenho.elemento.Legenda;
import on_tool.desenho.metodo.TratamentoStrings;
import on_tool.gui.dialogo.PainelEstiloLinha;

import org.jgraph.graph.GraphConstants;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class ManipuladorXML extends DefaultHandler {
	
	Ontologia ontologia;
	RelacaoBinaria relacaoBinaria;
	String charsInternos;
	
	public ManipuladorXML() {
		super();
	}
	
	public void startDocument() {
		ontologia = new Ontologia();
		charsInternos = "";
	}
	
	public void startElement(String uri,
			String localName,
			String qName,
			Attributes attributes) {
		if (qName == "dominio") {

		}
		else if (qName == "valor-relacao") {
			relacaoBinaria = tratarRelacaoBinaria(attributes);
		}
		else if (qName == "supertipo") {
			for (int i = 0; i < attributes.getLength(); i++)
				if (attributes.getQName(i) == "cadeia") {
					relacaoBinaria.mudaSupertipo(attributes.getValue(i));
					relacaoBinaria.atualizaRotulo();
					break;
				}
		}
		else if (qName == "conceito") {
			ontologia.vertices.add(
					tratarConceito(attributes));
		}
		else if (qName == "legenda") {
			ontologia.vertices.add(
					tratarConceito(attributes));
		}
		else if (qName == "arco") {
			ontologia.arcos.add(tratarArco(attributes));
		}
	}
	
	public void characters(char[] ch,
			int inicio,
			int compr) {
		charsInternos += " " +
				String.valueOf(ch, inicio, compr);
	}
	
	public void endElement(String uri,
			String localName,
			String qName) {
		if (qName == "dominio") {

			charsInternos = "";
		}
		else if (qName == "valor-relacao") {
			ontologia.vertices.add(relacaoBinaria);
		}
		else if (qName == "frase") {
			relacaoBinaria.pegaFrases().add(charsInternos);
			charsInternos = "";
		}
	}
	
	private Conceito tratarConceito(Attributes atribs) {
		String texto = "";
		String x = "";
		String y = "";
		String corBorda = "";
		String corPreenchimento  = "";
		String nomeLetra  = "";
		String estiloLetra = "";
		String tamanhoLetra = "";
		String corLetra  = "";
		for (int i = 0; i < atribs.getLength(); i++) {
			if (atribs.getQName(i) == "texto")
				texto = atribs.getValue(i);
			else if (atribs.getQName(i) == "x")
				x = atribs.getValue(i);
			else if (atribs.getQName(i) == "y")
				y = atribs.getValue(i);
			else if (atribs.getQName(i) == "corborda")
				corBorda = atribs.getValue(i);
			else if (atribs.getQName(i) == "corpreenchimento")
				corPreenchimento = atribs.getValue(i);
			else if (atribs.getQName(i) == "nomeletra")
				nomeLetra = atribs.getValue(i);
			else if (atribs.getQName(i) == "estiloletra")
				estiloLetra = atribs.getValue(i);
			else if (atribs.getQName(i) == "tamanholetra")
				tamanhoLetra = atribs.getValue(i);
			else if (atribs.getQName(i) == "corletra")
				corLetra = atribs.getValue(i);
		}
		if (texto != "" &&
				x != "" &&
				y != "" &&
				corBorda != "" &&
				corPreenchimento != "" &&
				nomeLetra != "" &&
				estiloLetra != "" &&
				tamanhoLetra != "" &&
				corLetra != "") {
			texto = texto.replace("\\n", "\n");
			Conceito c = new Conceito(TratamentoStrings.converteStringParaJGraph(
							texto, nomeLetra,
							Integer.parseInt(estiloLetra),
							Integer.parseInt(tamanhoLetra),
							new Color(Integer.parseInt(corLetra))),
					Double.parseDouble(x),
					Double.parseDouble(y));
			GraphConstants.setBorderColor(c.getAttributes(),
					new Color(Integer.parseInt(corBorda)));
			GraphConstants.setBackground(c.getAttributes(),
					new Color(Integer.parseInt(corPreenchimento)));
			c.mudaFonte(new Font(nomeLetra,
					Integer.parseInt(estiloLetra),
					Integer.parseInt(tamanhoLetra)));
			c.mudaFonteCor(new Color(Integer.parseInt(corLetra)));
			return c;
		}
		else
			return new Conceito("", 0, 0);
	}
	
	private Legenda tratarLegenda(Attributes atribs) {
		String texto = "";
		String x = "";
		String y = "";
		String nomeLetra  = "";
		String estiloLetra = "";
		String tamanhoLetra = "";
		String corLetra  = "";
		for (int i = 0; i < atribs.getLength(); i++) {
			if (atribs.getQName(i) == "texto")
				texto = atribs.getValue(i);
			else if (atribs.getQName(i) == "x")
				x = atribs.getValue(i);
			else if (atribs.getQName(i) == "y")
				y = atribs.getValue(i);
			else if (atribs.getQName(i) == "nomeletra")
				nomeLetra = atribs.getValue(i);
			else if (atribs.getQName(i) == "estiloletra")
				estiloLetra = atribs.getValue(i);
			else if (atribs.getQName(i) == "tamanholetra")
				tamanhoLetra = atribs.getValue(i);
			else if (atribs.getQName(i) == "corletra")
				corLetra = atribs.getValue(i);
		}
		if (texto != "" &&
				x != "" &&
				y != "" &&
				nomeLetra != "" &&
				estiloLetra != "" &&
				tamanhoLetra != "" &&
				corLetra != "") {
			texto = texto.replace("\\n", "\n");
			Legenda l = new Legenda(TratamentoStrings.converteStringParaJGraph(
							texto, nomeLetra,
							Integer.parseInt(estiloLetra),
							Integer.parseInt(tamanhoLetra),
							new Color(Integer.parseInt(corLetra))),
					Double.parseDouble(x),
					Double.parseDouble(y));
			l.mudaFonte(new Font(nomeLetra,
					Integer.parseInt(estiloLetra),
					Integer.parseInt(tamanhoLetra)));
			l.mudaFonteCor(new Color(Integer.parseInt(corLetra)));
			return l;
		}
		else
			return new Legenda("", 0, 0);
	}
	
	private RelacaoBinaria tratarRelacaoBinaria(Attributes atribs) {
		String x = "";
		String y = "";
		String nomeLetra  = "";
		String estiloLetra = "";
		String tamanhoLetra = "";
		String corLetra  = "";
		for (int i = 0; i < atribs.getLength(); i++) {
			if (atribs.getQName(i) == "x")
				x = atribs.getValue(i);
			else if (atribs.getQName(i) == "y")
				y = atribs.getValue(i);
			else if (atribs.getQName(i) == "nomeletra")
				nomeLetra = atribs.getValue(i);
			else if (atribs.getQName(i) == "estiloletra")
				estiloLetra = atribs.getValue(i);
			else if (atribs.getQName(i) == "tamanholetra")
				tamanhoLetra = atribs.getValue(i);
			else if (atribs.getQName(i) == "corletra")
				corLetra = atribs.getValue(i);
		}
		if (x != "" &&
				y != "" &&
				nomeLetra != "" &&
				estiloLetra != "" &&
				tamanhoLetra != "" &&
				corLetra != "") {
			RelacaoBinaria rel = new RelacaoBinaria(TratamentoStrings.converteStringParaJGraph(
							"????", nomeLetra,
							Integer.parseInt(estiloLetra),
							Integer.parseInt(tamanhoLetra),
							new Color(Integer.parseInt(corLetra))),
					new Vector(),
					Double.parseDouble(x),
					Double.parseDouble(y));
			rel.mudaFonte(new Font(nomeLetra,
					Integer.parseInt(estiloLetra),
					Integer.parseInt(tamanhoLetra)));
			rel.mudaFonteCor(new Color(Integer.parseInt(corLetra)));
			return rel;
		}
		else
			return new RelacaoBinaria("????", new Vector(), 0, 0);
	}
	
	private Arco tratarArco(Attributes atribs) {
		String origem = "";
		String alvo = "";
		String corLinha = "";
		String estilo = "";
		for (int i = 0; i < atribs.getLength(); i++) {
			if (atribs.getQName(i) == "origem")
				origem = atribs.getValue(i);
			else if (atribs.getQName(i) == "alvo")
				alvo = atribs.getValue(i);
			else if (atribs.getQName(i) == "corlinha")
				corLinha = atribs.getValue(i);
			else if (atribs.getQName(i) == "estilo")
				estilo = atribs.getValue(i);
		}
		if (origem != "" &&
				alvo != "") {
			Arco ac = new Arco();
			Vector ligacao = new Vector();
			ligacao.add(new Integer(origem));
			ligacao.add(new Integer(alvo));
			ontologia.ligacoesArcos.add(ligacao);
			if (corLinha != "")
				GraphConstants.setLineColor(ac.getAttributes(),
						new Color(Integer.parseInt(corLinha)));
			if (Integer.parseInt(estilo) == PainelEstiloLinha.ORTOGONAL) {
				GraphConstants.setRouting(ac.getAttributes(),
						GraphConstants.ROUTING_SIMPLE);
				GraphConstants.setLineStyle(ac.getAttributes(),
						GraphConstants.STYLE_ORTHOGONAL);
			}
			else if (Integer.parseInt(estilo) == PainelEstiloLinha.CURVA) {
				GraphConstants.setRouting(ac.getAttributes(),
						GraphConstants.ROUTING_SIMPLE);
				GraphConstants.setLineStyle(ac.getAttributes(),
						GraphConstants.STYLE_SPLINE);
			}
			return ac;
		}
		else
			return new Arco();
	}
	
}
