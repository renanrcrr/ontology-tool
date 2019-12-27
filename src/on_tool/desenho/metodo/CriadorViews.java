package on_tool.desenho.metodo;

import java.awt.event.MouseEvent;

import on_tool.desenho.AreaDesenho;
import on_tool.desenho.elemento.Conceito;
import on_tool.desenho.elemento.SetaEsquerda;
import on_tool.desenho.elemento.SetaDireita;
import on_tool.desenho.view.ViewConceito;
import on_tool.desenho.view.ViewPortPadrao;
import on_tool.desenho.view.ViewSetaEsquerda;
import on_tool.desenho.view.ViewSetaDireita;
import on_tool.desenho.view.ViewVertice;

import org.jgraph.graph.CellHandle;
import org.jgraph.graph.DefaultCellViewFactory;
import org.jgraph.graph.EdgeView;
import org.jgraph.graph.GraphContext;
import org.jgraph.graph.PortView;
import org.jgraph.graph.VertexView;


public class CriadorViews extends DefaultCellViewFactory {
	
	AreaDesenho area;
	
	public CriadorViews(AreaDesenho area) {
		super();
		this.area = area;
	}
	
	public EdgeView createEdgeView(Object obj) {
		return new EdgeView(obj) {
			
			public CellHandle getHandle(GraphContext context) {
                return new EdgeView.EdgeHandle(this, context) {
                	
                    public boolean isAddPointEvent(MouseEvent evt) {
                        return false;
                    }
                    
                    public boolean isRemovePointEvent(MouseEvent evt) {
                        return false;
                    }
                    
                };
            } 
			
		};
	}
	
	public VertexView createVertexView(Object obj) {
		if (obj instanceof Conceito)

			return new ViewConceito(obj);
		else
			return new ViewVertice(obj);
	}
	
	protected PortView createPortView(Object obj) {
		if (obj instanceof SetaEsquerda)
			return new ViewSetaEsquerda(obj);
		if (obj instanceof SetaDireita)
			return new ViewSetaDireita(obj);
		return new ViewPortPadrao(obj);
	}
	
}
