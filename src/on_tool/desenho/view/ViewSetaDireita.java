package on_tool.desenho.view;

import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.ImageIcon;

import on_tool.desenho.elemento.Seta;

import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.PortRenderer;
import org.jgraph.graph.PortView;


public class ViewSetaDireita extends PortView {
	
	Renderer renderer;
	
	public ViewSetaDireita(Object cell) {
		super(cell);
		renderer = new Renderer();
	}
	
	public Rectangle2D getBounds() {
		Point2D p = (Point2D)getLocation().clone();
		double lar = renderer.imagem.getIconWidth();
		double alt = renderer.imagem.getIconHeight();
		return new Rectangle2D.Double(
				p.getX() - 4,
				p.getY() - alt / 2, lar, alt);
	}
	
	public CellViewRenderer getRenderer() {
		return renderer;
	}
	
	class Renderer extends PortRenderer implements CellViewRenderer {
		
		public ImageIcon imagem;
		
		public Renderer() {
			super();
			imagem = new ImageIcon(
					this.getClass().getClassLoader().getResource("on_tool/imagens/Dotted22.gif"));
		}
		
		public void paint(Graphics g) {
			if (((Seta)cell).testaVisivel())
				g.drawImage(imagem.getImage(),
						0, 0, this);
		}
	}
	
}
