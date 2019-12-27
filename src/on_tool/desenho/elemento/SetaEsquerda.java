package on_tool.desenho.elemento;

import java.awt.Point;

import org.jgraph.graph.GraphConstants;

public class SetaEsquerda extends Seta {
	
	public SetaEsquerda() {
		super();
		GraphConstants.setOffset(getAttributes(),
				new Point(0, GraphConstants.PERMILLE/2));
	}
	
}
