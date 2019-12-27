package on_tool.io;

import javax.swing.*;
import javax.swing.tree.*;

import java.awt.Component;
import java.io.*;
import java.util.Enumeration;
import java.util.Vector;

import org.w3c.dom.*;
import javax.xml.parsers.*;

/**
 * Given a filename or a name and an input stream,
 * this class generates a JTree representing the
 * XML structure contained in the file or stream.
 * Parses with DOM then copies the tree structure
 * (minus text and comment nodes).
 */
public class ArvoreTaxonomia extends JTree 
{	
	
	public ArvoreTaxonomia(String filename) throws IOException 
	{
		this(filename, new FileInputStream(new File(filename)));
	}
	
	public ArvoreTaxonomia(String filename, InputStream in) {
		super(criaNodoRaiz(in));
		this.setCellRenderer(new Renderizador());
	}
	
	
	private static DefaultMutableTreeNode criaNodoRaiz(InputStream in) 
	{
		try 
		{
			// Use JAXP's DocumentBuilderFactory so that there
			// is no code here that is dependent on a particular
			// DOM parser. Use the system property
			// javax.xml.parsers.DocumentBuilderFactory (set either
			// from Java code or by using the -D option to "java").
			// or jre_dir/lib/jaxp.properties to specify this.
			DocumentBuilderFactory builderFactory =
				DocumentBuilderFactory.newInstance();
			DocumentBuilder builder =
				builderFactory.newDocumentBuilder();
			// Standard DOM code from hereon. The "parse"
			// method invokes the parser and returns a fully parsed
			// Document object. We'll then recursively descend the
			// tree and copy non-text nodes into JTree nodes.
			Document document = builder.parse(in);
			document.getDocumentElement().normalize();
			Element rootElement = document.getDocumentElement();
			DefaultMutableTreeNode rootTreeNode =
				constroiArvore(rootElement);
			return(rootTreeNode);
		} 
		catch(Exception e) 
		{
			String errorMessage =
				"Error making root node: " + e;
			System.err.println(errorMessage);
			e.printStackTrace();
			return(new DefaultMutableTreeNode(errorMessage));
		}
	}
	
	
	private static DefaultMutableTreeNode constroiArvore(Element rootElement) {
		// Make a JTree node for the root, then make JTree
		// nodes for each child and add them to the root node.
		// The addChildren method is recursive.
		DefaultMutableTreeNode rootTreeNode =
			new DefaultMutableTreeNode("Taxonomia");
		adicFilhos(rootTreeNode, rootElement);
		return(rootTreeNode);
	}
	
	
	private static void adicFilhos(DefaultMutableTreeNode parentTreeNode,
			Node parentXMLElement) {
		// Recursive method that finds all the child elements
		// and adds them to the parent node. We have two types
		// of nodes here: the ones corresponding to the actual
		// XML structure and the entries of the graphical JTree.
		// The convention is that nodes corresponding to the
		// graphical JTree will have the word "tree" in the
		// variable name. Thus, "childElement" is the child XML
		// element whereas "childTreeNode" is the JTree element.
		// This method just copies the non-text and non-comment
		// nodes from the XML structure to the JTree structure.
		NodeList childElements =
			parentXMLElement.getChildNodes();
		for (int i = 0; i < childElements.getLength(); i++) {
			Node childElement = childElements.item(i);
			if (childElement.getNodeName() == "supertipo") {
				DefaultMutableTreeNode childTreeNode =
					new DefaultMutableTreeNode(pegaSupertipo(childElement));
				parentTreeNode.add(childTreeNode);
				adicFilhos(childTreeNode, childElement);
			}
		}
	}
	
	private static Supertipo pegaSupertipo(Node childElement) {
		String nome = "";
		String descricao = "";
		Vector frases = new Vector();
		Node nodo = childElement.getAttributes().item(0);
		nome = nodo.getNodeValue();
		NodeList lista = childElement.getChildNodes();
		for (int i = 0; i < lista.getLength(); i++) {
			nodo = lista.item(i);
			if (nodo.getNodeName() == "desc")
				descricao = nodo.getChildNodes().item(0).getNodeValue();
			if (nodo.getNodeName() == "frase")
				frases.add(nodo.getChildNodes().item(0).getNodeValue());
		}
		Supertipo supertipo = new Supertipo(nome, descricao);
		supertipo.frases = frases;
		return supertipo;
	}
	
	public boolean testaESelecionaSupertipo(String supertipo) {
		Vector v = new Vector();
		v.add("Taxonomia");
		String temp = "";
		for (int i = 0; i < supertipo.length(); i++) {
			if (supertipo.charAt(i) == '.') {
				v.add(temp);
				temp = "";
			}
			else
				temp += supertipo.charAt(i);
		}
		if (temp.length() > 0)
			v.add(temp);
		TreePath caminhoBusca = busca(new TreePath((TreeNode)this.getModel().getRoot()),
				v,
				0);
		this.setSelectionPath(caminhoBusca);
		if (this.getSelectionCount() > 0)
			return true;
		else
			return false;
	}
	
	
	private TreePath busca(TreePath pai, Vector nodos, int profundidade) {
		TreeNode node = (TreeNode)pai.getLastPathComponent();
		Object o = node.toString();
	
		if (o.equals(nodos.get(profundidade))) {
	
			if (profundidade == nodos.size() - 1)
				return pai;
	
			if (node.getChildCount() >= 0)
				for (Enumeration e = node.children(); e.hasMoreElements();) {
					TreeNode n = (TreeNode)e.nextElement();
					TreePath path = pai.pathByAddingChild(n);
					TreePath resultado = busca(path, nodos, profundidade + 1);
	
					if (resultado != null)
						return resultado;
                }
		}
	
		return null;
	}
	
	
	public String pegaSupertipoComoString() {
		if (this.getSelectionCount() == 0)
			return "";
		String retorno = "";
		TreePath caminho = this.getSelectionPath();
		for (int i = 1; i < caminho.getPathCount(); i++) {
			DefaultMutableTreeNode nodoTemp =
				(DefaultMutableTreeNode)caminho.getPathComponent(i);
			if (i > 1)
				retorno += ".";
			retorno += nodoTemp.toString();
		}
		return retorno;
	}
	
	
	class Renderizador extends DefaultTreeCellRenderer {
		
		/* (non-Javadoc)
		 * @see javax.swing.tree.TreeCellRenderer#getTreeCellRendererComponent(javax.swing.JTree, java.lang.Object, boolean, boolean, boolean, int, boolean)
		 */
		public Component getTreeCellRendererComponent(
				JTree tree,
				Object value,
				boolean sel,
				boolean expanded,
				boolean leaf,
				int row,
				boolean hasFocus) {
            super.getTreeCellRendererComponent(
            		tree, value, sel,
					expanded, leaf, row,
					hasFocus);
            setIcon(null);
            return this;
        }
	}	
}