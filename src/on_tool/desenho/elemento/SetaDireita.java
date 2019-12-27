package on_tool.desenho.elemento;

import java.awt.Point;

import org.jgraph.graph.GraphConstants;

public class SetaDireita extends Seta {
	
	public SetaDireita() {
		super();
		GraphConstants.setOffset(getAttributes(), new Point(
				GraphConstants.PERMILLE, GraphConstants.PERMILLE/2));
	}
	
}
