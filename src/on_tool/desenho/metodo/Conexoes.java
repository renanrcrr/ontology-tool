package on_tool.desenho.metodo;

import java.awt.geom.Point2D;

import on_tool.desenho.AreaDesenho;
import on_tool.desenho.elemento.Arco;
import on_tool.desenho.elemento.Conceito;
import on_tool.desenho.elemento.RelacaoBinaria;
import on_tool.desenho.elemento.SetaEsquerda;
import on_tool.desenho.elemento.SetaDireita;
import on_tool.desenho.elemento.Vertice;
import on_tool.gui.dialogo.Dialogos;

import org.jgraph.graph.DefaultPort;
import org.jgraph.graph.Port;

public class Conexoes {
	
	public static void conectar(AreaDesenho area, Port origem, Port alvo) {
		if (origem instanceof SetaEsquerda) {
			SetaEsquerda sc = (SetaEsquerda)origem;
			origem = ((Vertice)sc.getParent()).pegaPortPadrao();
			Arco arco = new Arco();
			if (alvo instanceof SetaEsquerda ||
					alvo instanceof SetaDireita) {
				DefaultPort dp = (DefaultPort)alvo;
				alvo = ((Vertice)dp.getParent()).pegaPortPadrao();
			}
			if (area.getModel().acceptsSource(arco, origem) &&
					area.getModel().acceptsTarget(arco, alvo) &&
					!area.testaConexaoJaExistente(origem, alvo)) {

				area.getGraphLayoutCache().insertEdge(arco,
						origem, alvo);
			}
		}
		else if (origem instanceof SetaDireita) {
			SetaDireita st = (SetaDireita)origem;
			origem = ((Vertice)st.getParent()).pegaPortPadrao();
			Arco arco = new Arco();
			if (alvo instanceof SetaEsquerda ||
					alvo instanceof SetaDireita) {
				DefaultPort dp = (DefaultPort)alvo;
				alvo = ((Vertice)dp.getParent()).pegaPortPadrao();
			}
			if (area.getModel().acceptsSource(arco, origem) &&
					area.getModel().acceptsTarget(arco, alvo) &&
					!area.testaConexaoJaExistente(origem, alvo)) {

				area.getGraphLayoutCache().insertEdge(arco, origem, alvo);
			}
		}
	}

	public static void conectarAtingirBranco(AreaDesenho area,
			Object objPortOrigem, double x, double y) {
		DefaultPort portOrigem = (DefaultPort)objPortOrigem, portAlvo = null;
		Object objTemp = InsercaoElementos.inserirConceito(area,
				x / area.getScale(),
				y / area.getScale());


		if (objTemp != null) {
			area.getGraphLayoutCache().insert(objTemp);
			portAlvo =((Vertice)objTemp).pegaPortPadrao();

			if (portOrigem.getParent() instanceof Conceito) {
				inserirFraseEnlaceEntreElementos(area, portOrigem, portAlvo);
			}
		
			else
				conectar(area, portOrigem, portAlvo);
		}
	}
	

	public static void conectarAtingirConceito(AreaDesenho area,
			Object objPortOrigem, Object objPortAlvo) {
		DefaultPort portOrigem = (DefaultPort)objPortOrigem;
		DefaultPort portAlvo = (DefaultPort)objPortAlvo;

		if (portOrigem.getParent() == portAlvo.getParent())
			Dialogos.mostrarDialogoErroConexao(area);

		else if (portOrigem.getParent() instanceof Conceito) {
			inserirFraseEnlaceEntreElementos(area, portOrigem, portAlvo);
		}

		else
			conectar(area, portOrigem, portAlvo);
	}
	
	public static void conectarAtingirRelacaoBinaria(AreaDesenho area,
			Object objPortOrigem, Object objPortAlvo) {
		DefaultPort portOrigem = (DefaultPort)objPortOrigem;
		DefaultPort portAlvo = (DefaultPort)objPortAlvo;

		if (portOrigem.getParent() instanceof Conceito)
			conectar(area, portOrigem, portAlvo);
		else
			Dialogos.mostrarDialogoErroConexao(area);
	}
	
	private static void inserirFraseEnlaceEntreElementos(AreaDesenho area,
			DefaultPort portOrigem, DefaultPort portAlvo) {
		Point2D medio = calcularPontoMedio(
				area.getCellBounds(((Vertice)portOrigem.getParent()).pegaPortPadrao()).getX(),
				area.getCellBounds(((Vertice)portOrigem.getParent()).pegaPortPadrao()).getY(),
				area.getCellBounds(((Vertice)portAlvo.getParent()).pegaPortPadrao()).getX(),
				area.getCellBounds(((Vertice)portAlvo.getParent()).pegaPortPadrao()).getY());
		RelacaoBinaria rel = (RelacaoBinaria)InsercaoElementos.inserirRelacaoBinaria(area,
				medio.getX(), medio.getY());
		area.getGraphLayoutCache().insert(rel);
		conectar(area, rel.pegaSetaContinua(), portAlvo);
		conectar(area, portOrigem, rel.pegaPortPadrao());
	}
	

	private static Point2D calcularPontoMedio(double x1,
			double y1, double x2, double y2) {
		return new Point2D.Double((x1 + x2) / 2, (y1 + y2) / 2);
	}
	
}
