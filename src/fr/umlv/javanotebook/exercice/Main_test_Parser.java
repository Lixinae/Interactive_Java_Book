package fr.umlv.javanotebook.exercice;

import org.pegdown.Extensions;
import org.pegdown.PegDownProcessor;
import org.pegdown.ast.*;
import org.parboiled.common.FileUtils;
import org.parboiled.common.Preconditions;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class Main_test_Parser {

	private static String getTextContent(Node node) {
		if (node instanceof TextNode) {
			return getTextContent((TextNode)node);
		} else if (node instanceof HeaderNode) {
			HeaderNode headerNode = (HeaderNode) node;
			return getTextContent((TextNode) headerNode.getChildren().get(0));
		} else if (node instanceof ParaNode) {
			ParaNode paraNode = (ParaNode) node;
			Node firstChildNode = paraNode.getChildren().get(0);
			if (firstChildNode instanceof SuperNode) {
				return getTextContent((SuperNode) firstChildNode);
			} else if (firstChildNode instanceof TextNode) {
				return getTextContent((TextNode) firstChildNode);
			}
		} else if (node instanceof ListItemNode) {
			ListItemNode listItemNode = (ListItemNode) node;
			RootNode rootNode = (RootNode) listItemNode.getChildren().get(0);
			Node firstChildNode = rootNode.getChildren().get(0);
			if (firstChildNode instanceof SuperNode) {
				return getTextContent((SuperNode) firstChildNode);
			} else if (firstChildNode instanceof TextNode) {
				return getTextContent((TextNode) firstChildNode);
			}
		}
		return null;
	}

	private static String getTextContent(SuperNode node) {
		List<Node> nodes = node.getChildren();
		StringBuilder content = new StringBuilder();
		for (Node child : nodes) {
			if (child instanceof TextNode) {
				content.append(getTextContent((TextNode)child));
			} else if (child instanceof SpecialTextNode) {
				content.append(getTextContent((SpecialTextNode)child));
			}
		}
		return content.toString();
	}

	private static String getTextContent(TextNode node) {
		return node.getText();
	}


	/* TODO
	 * Help 
	 * 
	 * Liste des noms de noeud possible:
	 * 
	 * 		ParaNode : paragraphe -> <p></p> en html
	 * 		BulletListNode : Liste d'element avec * devant . Liste en html <li><ul></ul></li>
	 * 		HeaderNode : header <h1></h1> , h2 etc....
	 * 		VerbatimNode : <code></code>
	 * 
	 */


	public static void main(String[] args) throws FileNotFoundException {
		String fichier = "./exercice/test_2.MARKDOWN";
		InputStream input = new FileInputStream(fichier);    	
		char[] markdown = FileUtils.readAllChars(input);
		Preconditions.checkNotNull(markdown, "The specified file isn't found - "+fichier);
		PegDownProcessor processor = new PegDownProcessor(Extensions.ALL);

		RootNode rootNode = processor.parseMarkdown(markdown);

		// recup�re la liste des noeud
		List<Node> nodes = rootNode.getChildren();

		// Affiche ce qu'il y a chaque noeud
		for ( Node node : nodes){
			if (node instanceof HeaderNode) {
				HeaderNode headerNode = (HeaderNode) node;
				String text = getTextContent(node);
				System.out.println(text);
			}
			else if(node instanceof HeaderNode){

			}
			System.out.println(node);
		}
	}
}
