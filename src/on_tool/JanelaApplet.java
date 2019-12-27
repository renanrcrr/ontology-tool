package on_tool;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class JanelaApplet extends JApplet implements ActionListener
{
	JFrame fFrame;
	JMenuItem fMenuNovo;
	JMenuItem fMenuAbrir;
	JMenuItem fMenuGravar;
	JMenuItem fMenuGravarComo;
	JMenuItem fMenuClose;
	
	public void init () 
	{
	    JMenuBar mb = new JMenuBar ();
	    JMenu m = new JMenu ("Arquivo");
	    fMenuNovo = new JMenuItem ("Novo");
	    fMenuAbrir = new JMenuItem ("Abrir");
	    fMenuGravar = new JMenuItem ("Gravar");
	    fMenuGravarComo = new JMenuItem("Gravar Como...");
	    fMenuClose = new JMenuItem ("Close");
	    
	    m.add(fMenuNovo);
	    m.add(fMenuAbrir);
	    m.add(fMenuGravar);
	    m.add(fMenuGravarComo);
	    m.add (fMenuClose);
	    
	    fMenuNovo.addActionListener(this);
	    fMenuAbrir.addActionListener(this);
	    fMenuGravar.addActionListener(this);
	    fMenuGravarComo.addActionListener(this);
	    fMenuClose.addActionListener (this);
	    
	    mb.add(m);

	    setJMenuBar(mb);

	    //fFrame = new On_Tool(this);
	    
	    fFrame.setVisible (true);
	    fMenuAbrir.setEnabled (false);
	    fMenuClose.setEnabled (true);

	  }
	

	  void close () 
	  {
	    fFrame.dispose ();
	    fFrame = null;
	    fMenuAbrir.setEnabled (true);
	    fMenuGravar.setEnabled (true);
	    fMenuGravarComo.setEnabled (true);
	    fMenuClose.setEnabled (false);
	  } 

	@Override
	public void actionPerformed(ActionEvent arg0) {

		
	}
	

}
