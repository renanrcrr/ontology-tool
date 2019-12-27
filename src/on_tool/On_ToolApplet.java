package on_tool;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.*;

import javax.swing.JApplet;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.text.AbstractDocument.Content;

import on_tool.desenho.AreaDesenho;
import on_tool.gui.Acoes;
import on_tool.gui.BarraFerramentas;
import on_tool.gui.BarraMenus;
import on_tool.gui.dialogo.Dialogos;
import on_tool.gui.dialogo.PainelPreferencias;

@SuppressWarnings("serial")
public class On_ToolApplet extends JApplet
{
	Acoes acoes;
	AreaDesenho areaDesenho;
	BarraFerramentas barraFerramentas;
	BarraMenus barraMenus;
	
	On_Tool editor;
	
	On_ToolApplet windowApplet = this;
	
	Container container = getContentPane();
	
	public void init() 
	{
		try 
		{
			UIManager.setLookAndFeel(
					UIManager.getSystemLookAndFeelClassName());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		UIManager.put("OptionPane.yesButtonText", "Sim");
		UIManager.put("OptionPane.yesButtonMnemonic",
				String.valueOf(KeyEvent.VK_S));
		UIManager.put("OptionPane.noButtonText", "N\u00e3o");
		UIManager.put("OptionPane.noButtonMnemonic",
				String.valueOf(KeyEvent.VK_N));
		UIManager.put("OptionPane.okButtonText", "OK");
		UIManager.put("OptionPane.okButtonMnemonic",
				String.valueOf(KeyEvent.VK_O));
		UIManager.put("OptionPane.cancelButtonText", "Cancelar");
		UIManager.put("OptionPane.cancelButtonMnemonic",
				String.valueOf(KeyEvent.VK_C));

		UIManager.put("FileChooser.cancelButtonText", "Cancelar");
		UIManager.put("FileChooser.cancelButtonMnemonic",
				String.valueOf(KeyEvent.VK_C));
				
		On_Tool f = new On_Tool();

		f.setVisible(true);
		f.setEnabled(false);
		if (Dialogos.mostrarDialogoBoasVindas(f.areaDesenho, f.acoes))
			f.setEnabled(true);
		else
			System.exit(0);

	}
	
	
	
	public void start()
	{
		
	}
	
	public void destroy()
	{
		
	}



	}
