package on_tool.desenho.elemento;

import on_tool.desenho.AreaDesenho;
import on_tool.gui.dialogo.PainelEstiloLinha;

import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.GraphConstants;

public class Arco extends DefaultEdge {
	
	public Arco() {
		super();

		GraphConstants.setLineColor(getAttributes(),
				AreaDesenho.corLinha);

		GraphConstants.setLineEnd(getAttributes(),
				GraphConstants.ARROW_SIMPLE);

		GraphConstants.setLabelAlongEdge(getAttributes(),
				false);

		switch (AreaDesenho.estiloLinha) {
			case PainelEstiloLinha.NORMAL: {
				GraphConstants.setRouting(getAttributes(),
						GraphConstants.ROUTING_DEFAULT);
				GraphConstants.setLineStyle(getAttributes(),
						GraphConstants.STYLE_ORTHOGONAL);
			} break;
			case PainelEstiloLinha.ORTOGONAL: {
				GraphConstants.setRouting(getAttributes(),
						GraphConstants.ROUTING_SIMPLE);
				GraphConstants.setLineStyle(getAttributes(),
						GraphConstants.STYLE_ORTHOGONAL);
			} break;
			case PainelEstiloLinha.CURVA: {
				GraphConstants.setRouting(getAttributes(),
						GraphConstants.ROUTING_SIMPLE);
				GraphConstants.setLineStyle(getAttributes(),
						GraphConstants.STYLE_SPLINE);
			} break;
		}
	}
	
}
