package on_tool.gui.dialogo;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

import on_tool.io.ArvoreTaxonomia;
import on_tool.io.Supertipo;

import java.io.*;

public class DialogoTaxonomia
extends JPanel
implements TreeSelectionListener {
	
	ArvoreTaxonomia arvTaxonomia;
	public JList lstFrases;
	JTextArea txtDescricao;
	JButton btnSelecionarTodas;
	
	public DialogoTaxonomia(String filename) 
	{
		this.setLayout(new BorderLayout());
		JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p.setBorder(BorderFactory.createEmptyBorder(
				2, 2, 2, 2));
		p.add(new JLabel("<html>Selecione um ramo da taxonomia.<br>" +
				"Em seguida, segurando a tecla \"Ctrl\" no teclado, selecione<br>" +
				"com o mouse as frases de liga\u00e7\u00e3o desejadas.</html>"));
		this.add(p, BorderLayout.PAGE_START);
		p = new JPanel();
		p.setBorder(BorderFactory.createTitledBorder(
				"Taxonomia"));
		try {
			arvTaxonomia = new ArvoreTaxonomia(filename);
		} catch(IOException ioe) {
			System.out.println("Error creating tree: " + ioe);
		}
		arvTaxonomia.getSelectionModel().setSelectionMode(
				DefaultTreeSelectionModel.SINGLE_TREE_SELECTION);
		arvTaxonomia.addTreeSelectionListener(this);
		JScrollPane rolagem = new JScrollPane(arvTaxonomia);
		rolagem.setPreferredSize(
				new Dimension(160, 180));
		p.add(rolagem);
		this.add(p, BorderLayout.WEST);
		
		p = new JPanel(new BorderLayout());
		p.setBorder(BorderFactory.createTitledBorder(
				"Frases de liga\u00e7\u00e3o"));
		lstFrases = new JList();
		rolagem = new JScrollPane(lstFrases);
		rolagem.setPreferredSize(
				new Dimension(160, 160));
		p.add(rolagem, BorderLayout.NORTH);
		btnSelecionarTodas = new JButton(
				"Selecionar todas");
		btnSelecionarTodas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				lstFrases.setSelectionInterval(0,
						lstFrases.getModel().getSize() - 1);
			}
		});
		p.add(btnSelecionarTodas, BorderLayout.SOUTH);
		this.add(p, BorderLayout.EAST);
		
		p = new JPanel();
		p.setBorder(BorderFactory.createTitledBorder(
				"Descri\u00e7\u00e3o"));
		txtDescricao = new JTextArea(4, 30);
		txtDescricao.setEditable(false);
		rolagem = new JScrollPane(txtDescricao);
		p.add(rolagem);
		this.add(p, BorderLayout.SOUTH);
	}
	
	public void valueChanged(TreeSelectionEvent evt) {
		TreePath caminho = evt.getPath();
		DefaultMutableTreeNode nodo =
			(DefaultMutableTreeNode)caminho.getLastPathComponent();
		if (nodo.isRoot()) {
			arvTaxonomia.setSelectionPath(null);
		}
		else if (nodo.getUserObject() instanceof Supertipo) {
			Supertipo supertipo = (Supertipo)nodo.getUserObject();
			lstFrases.setListData(supertipo.frases);
			String descricao = "";
			String tmp = "";
			int i = 0;
			while (i < supertipo.descricao.length()) {
				if (tmp.length() > 40 &&
						supertipo.descricao.charAt(i) == ' ') {
					descricao += tmp + '\n';
					tmp = "";
				}
				else
					tmp += supertipo.descricao.charAt(i);
				i++;
			}
			txtDescricao.setText(descricao);
			txtDescricao.setCaretPosition(0);
			lstFrases.setSelectionInterval(0,
					lstFrases.getModel().getSize() - 1);
		}
	}
	
	public static void main(String[] args) {
		String jaxpPropertyName =
			"javax.xml.parsers.DocumentBuilderFactory";
		
		DialogoTaxonomia d = new DialogoTaxonomia("src/on_tool/io/taxonomia.xml");
		boolean acerto = true;
		do {
			int i = JOptionPane.showConfirmDialog(null,
					d,
					"Frases de liga\u00e7\u00e3o",
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
		}
		while (!acerto);
	}
	
	
}