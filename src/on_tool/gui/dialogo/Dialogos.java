
package on_tool.gui.dialogo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.io.IOException;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import javax.swing.text.Position;

import on_tool.desenho.AreaDesenho;
import on_tool.desenho.elemento.RelacaoBinaria;
import on_tool.gui.Acoes;
import sun.swing.ImageIconUIResource;


public class Dialogos 
{	

	public static boolean mostrarDialogoBoasVindas(Component c, Acoes a) 
	{
		JPanel painel = new JPanel();
		painel.setLayout(new BorderLayout());
		
		painel.add(new JLabel(new ImageIcon("on_tool/imagens/Welcome.gif")),
				BorderLayout.NORTH);
		ButtonGroup grupo = new ButtonGroup();
		JRadioButton[] botoes = new JRadioButton[2];
		botoes[0] = new JRadioButton("Desejo criar uma ontologia");
		botoes[0].setMnemonic('L');
		botoes[0].setSelected(true);
		botoes[1] = new JRadioButton("Desejo abrir uma ontologia");
		botoes[1].setMnemonic('A');
		JPanel p = new JPanel();
		p.setBorder(BorderFactory.createTitledBorder(
				"Selecione uma op\u00e7\u00e3o"));
		p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
		for (int i = 0; i < botoes.length; i++) {
			grupo.add(botoes[i]);
			p.add(botoes[i]);
		}
		painel.add(p, BorderLayout.SOUTH);
		if (JOptionPane.showOptionDialog(c, painel, "Bem-vindo ao On_Tool",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
				null, new Object[] {"OK", "Sair do On_Tool"}, null)
				== JOptionPane.OK_OPTION) {
			if (botoes[0].isSelected()) {
				((AreaDesenho)c).mudaCaminhoArquivo("Sem t\u00edtulo");
				((AreaDesenho)c).mudaNomeArquivo("Sem t\u00edtulo");
				return true;
			}
			else if (botoes[1].isSelected())
				return a.abrir();
			else
				return false;
		}
		else
			return false;
	}
	
	public static boolean mostrarDialogoNovaOntologia(Component c) {
		return true;
	}
	
	
	public static int mostrarDialogoSalvar(Component c,
			String arquivo) {
		String mensagem = "Deseja salvar as altera\u00e7\u00f5oes em \""
				+ arquivo + "\"?";
		return JOptionPane.showConfirmDialog(c,
				mensagem,
				"Confirma\u00e7\u00e3o",
				JOptionPane.YES_NO_CANCEL_OPTION);
	}
	
	public static int mostraDialogoInsercaoElemento(Component c) {
		ButtonGroup grupo = new ButtonGroup();
		JRadioButton[] botoes = new JRadioButton[2];
		botoes[0] = new JRadioButton("Conceito");
		botoes[0].setMnemonic('C');
		botoes[0].setSelected(true);
		botoes[1] = new JRadioButton("Legenda");
		botoes[1].setMnemonic('L');
		JPanel p = new JPanel();
		p.setBorder(BorderFactory.createTitledBorder(
				"Objeto a ser inserido:"));
		p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
		for (int i = 0; i < botoes.length; i++) {
			grupo.add(botoes[i]);
			p.add(botoes[i]);
		}
		int retorno = -1;
		if (JOptionPane.showConfirmDialog(c, p,
				"Inser\u00e7\u00e3o de elemento",
				JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION)
			for (int i = 0; i < botoes.length; i++)
				if (botoes[i].isSelected()) {
					retorno = i;
					break;
				}
		return retorno;
	}
	
	public static String mostraDialogoEdicaoRotulo(Component c,
			String s) {
		final JTextArea txRotulo = new JTextArea(5, 10);
	
		txRotulo.setDocument(new PlainDocument() {
			
			public void insertString(int offset,
					String str,
					AttributeSet attr)
			throws BadLocationException {
				if (str == null)
					return;
				String tmp = "";
				for (int i = 0; i < str.length(); i++)
					if (str.charAt(i) != '<' &&
							str.charAt(i) != '>')
						tmp += str.charAt(i);
				str = tmp;
				super.insertString(offset, str, attr);
			}
			
		});
	
		txRotulo.addHierarchyListener(new HierarchyListener() {
			
			public void hierarchyChanged(HierarchyEvent evt) {
				if ((evt.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0) {
					if (txRotulo.isShowing()) {
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								txRotulo.requestFocus();
							}
						});
					}
				}
			}
			
		});
		txRotulo.append(s);
		txRotulo.selectAll();
		JScrollPane pane = new JScrollPane(txRotulo);
		int resposta = JOptionPane.showOptionDialog(
				c,
				new Object[] {"Escreva o r\u00f3tulo do elemento:", pane},
				"Edi\u00e7\u00e3o de r\u00f3tulo",
				JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null, null, null);
		if (resposta == JOptionPane.OK_OPTION)
			return txRotulo.getText();
		else
			return null;
	}
	
	
	public static Vector mostrarDialogoRelacaoBinaria(Component c,
			RelacaoBinaria rel) 
	{
		Vector retorno = new Vector();
	
		String raiz = System.getProperty("user.home");
		 DialogoTaxonomia d = new DialogoTaxonomia(raiz + "/WebOn_Tool/taxonomia.xml");
	
		if (d.arvTaxonomia.testaESelecionaSupertipo(rel.pegaSupertipo())) 
		{
			Vector v = rel.pegaFrases();
			Vector vIndices = new Vector();
	
			for (int i = 0; i < v.size(); i++) 
			{
				int pos = d.lstFrases.getNextMatch((String)v.get(i),
						0,
						Position.Bias.Forward);
				if (pos >= 0)
					vIndices.add(new Integer(pos));
			}
	
			if (vIndices.size() > 0) 
			{
				int[] indices = new int[vIndices.size()];
				for (int i = 0; i < indices.length; i++)
					indices[i] = ((Integer)vIndices.get(i)).intValue();
				d.lstFrases.setSelectedIndices(indices);
			}
		}
		boolean acerto = true;
		int i = 0;
		do {
			i = JOptionPane.showConfirmDialog(c,
					d,
					"Edi\u00e7\u00e3o de rela\u00e7\u00e3o bin\u00e1ria",
					JOptionPane.OK_CANCEL_OPTION);
	
			if (i == JOptionPane.OK_OPTION &&
					d.lstFrases.getSelectedValues().length == 0) {
				JOptionPane.showMessageDialog(null,
						"Selecione ao menos uma frase de liga\u00e7\u00e3o",
						"Erro",
						JOptionPane.ERROR_MESSAGE);
				acerto = false;
			}
			else
				acerto = true;
		} while (!acerto);
		if (i == JOptionPane.OK_OPTION) {
			retorno.add(d.arvTaxonomia.pegaSupertipoComoString());
			Object[] frases = d.lstFrases.getSelectedValues();
			for (i = 0; i < frases.length; i++) {
				retorno.add((String)frases[i]);
			}
			return retorno;
		}
		return null;
	}
	
	public static Color mostraDialogoCor(Component c,
			Color cor) {
		PainelCor p = new PainelCor(cor);
		int resposta = JOptionPane.showConfirmDialog(c,
				new Object[] {"Selecione uma cor e clique em OK:", p},
				"Edi\u00e7\u00e3o de cor",
				JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE,
				null);
		if (resposta == JOptionPane.OK_OPTION)
			return p.pegaCorEscolhida();
		else
			return null;
	}
	
	public static Font mostraDialogoFonte(Component c,
			Font fonte) {
		PainelFonte p = new PainelFonte(fonte);
		int resposta = JOptionPane.showConfirmDialog(
				c,
				new Object[] {"", p},
				"Edi\u00e7\u00e3o de fonte",
				JOptionPane.OK_CANCEL_OPTION,
				 JOptionPane.PLAIN_MESSAGE,
				 null);
		if (resposta == JOptionPane.OK_OPTION)
			return p.pegaFonte();
		else
			return null;
	}
	
	public static int mostraDialogoEstiloLinha(Component c,
			int estilo) {
		PainelEstiloLinha p = new PainelEstiloLinha(estilo);
		int i = JOptionPane.showConfirmDialog(c,
				new Object[] {"", p},
				"Edi\u00e7\u00e3o de estilo de linha",
				JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE,
				null);
		if (i == JOptionPane.OK_OPTION)
			return p.pegaEstiloEscolhido();
		else
			return 99;
	}
	
	public static void mostrarDialogoPreferencias(AreaDesenho area) {
		PainelPreferencias p = new PainelPreferencias(AreaDesenho.corLinha,
				AreaDesenho.corBorda,
				AreaDesenho.corPreenchimento,
				AreaDesenho.corLetra,
				AreaDesenho.fonte,
				AreaDesenho.estiloLinha);
		int i = JOptionPane.showConfirmDialog(area, p, "Prefer\u00eancias",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null);
		if (i == JOptionPane.OK_OPTION) 
		{
			try 
			{
				p.salvarPreferencias();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			AreaDesenho.corLinha = p.txCorLinha.getBackground();
			AreaDesenho.corBorda = p.txCorBorda.getBackground();
			AreaDesenho.corPreenchimento = p.txCorPreenchimento.getBackground();
			AreaDesenho.corLetra = p.txCorLetra.getBackground();
			AreaDesenho.fonte = p.txFonte.getFont();
			AreaDesenho.estiloLinha = p.estilosLinha[p.estiloLinha.getSelectedIndex()];
		}
	}
	
	public static void mostrarDialogoSobre(Component c) 
	{
		JPanel p = new JPanel();
		JLabel l = new JLabel(new ImageIcon("on_tool/imagens/Welcome.gif"));
		l.setText(DialogoSobre.pegaMensagem());
		l.setVerticalTextPosition(JLabel.BOTTOM);
		l.setHorizontalTextPosition(JLabel.CENTER);
		p.add(l);
		JOptionPane.showMessageDialog(c, p, "Sobre o On_Tool",
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void mostrarDialogoErroConexao(Component c) {
		JOptionPane.showMessageDialog(c,
				"N\u00e3o \u00e9 permitido fazer esse tipo de liga\u00e7\u00e3o.",
				"Erro", JOptionPane.ERROR_MESSAGE);
	}
	
	public static void mostrarDialogoErroAbrir(Component c) {
		JOptionPane.showMessageDialog(c,
				"N\u00e3o foi poss\u00edvel abrir este arquivo.\n" +
				"Ele \u00e9 inv\u00e1lido ou est\u00e1 corrompido.",
				"Erro", JOptionPane.ERROR_MESSAGE);
	}
	
	public static void mostrarDialogoErroSalvar(Component c) 
	{
		JOptionPane.showMessageDialog(c,
				"O arquivo n\u00e3o foi salvo.",
				"Erro", JOptionPane.ERROR_MESSAGE);
	}
	
}
