package a5812070.action2d;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
public class XML
{
	private Node node;
	private String nodeName;	//	for debug

	public XML(String fileName) {
		load(fileName);
	}

	public XML(InputStream is) {
		load(is);
	}

	private XML(Node node) {
		this.node = node;
		nodeName = node.getNodeName();
		removeTextNode();
	}

	public void load(String fileName)
	{
		try {
			load(new FileInputStream(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void load(InputStream is) {
		Document document = null;
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder;
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			document = documentBuilder.parse(is);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (document.hasChildNodes()) {
			node = document.getFirstChild();
			nodeName = node.getNodeName();
			removeTextNode();
		} else {
			System.out.println("This xml is a wrong format.");
		}
	}

	private void removeTextNode() {	//	テキストノードは<tag>ここ</tag>とその他空白
		Node next = node.getFirstChild();
		while (next != null) {
			if (next.getNodeType() == Node.TEXT_NODE) {
				if (next.getNextSibling() != null) {
					next = next.getNextSibling();
					node.removeChild(next.getPreviousSibling());
				} else {
					node.removeChild(next);
					break;
				}
			} else
				next = next.getNextSibling();
		}
	}

	public String getName() {
		return node.getNodeName();
	}

	public XML getChild() {
		if (node.hasChildNodes())
			return new XML(node.getFirstChild());
		return null;
	}

	public XML getChild(String nodeName) {
		NodeList nl = node.getChildNodes();
		for (int i = 0; i < nl.getLength(); ++i) {
			if (nl.item(i).getNodeName().equals(nodeName)) return new XML(nl.item(i));
		}
		return null;
	}

	public XML[] getChildren() {
		NodeList nl = node.getChildNodes();
		XML[] nodes = new XML[nl.getLength()];
		for (int i = 0; i < nl.getLength(); ++i) {
			nodes[i] = new XML(nl.item(i));
		}
		return nodes;
	}

	public XML[] getChildren(String nodeName) {
		NodeList nl = node.getChildNodes();
		XML[] nodes = new XML[nl.getLength()];
		int count = 0;
		for (int i = 0; i < nl.getLength(); ++i) {
			if (nl.item(i).getNodeName().equals(nodeName))
				nodes[count++] = new XML(nl.item(i));
		}
		XML[] ret = new XML[count];
		System.arraycopy(nodes, 0, ret, 0, count);
		return ret;
	}

	public String[] getAttributeNames() {
		NamedNodeMap attr = node.getAttributes();
		String[] ret = new String[attr.getLength()];
		for (int i = 0; i < attr.getLength(); ++i) {
			ret[i] = attr.item(i).getNodeName();
		}
		return ret;
	}

	public String getStr(String attrName) {
		return getStr(attrName, null);
	}

	public String getStr(String attrName, String def) {
		NamedNodeMap attr = node.getAttributes();
		if (attr != null) {
			Node w = attr.getNamedItem(attrName);
			if (w != null)
				return w.getNodeValue();
		}
		return def;
	}

	public int getInt(String attrName) {
		return getInt(attrName, 0);
	}

	public int getInt(String attrName, int def) {
		String attr = getStr(attrName);
		if (attr == null)
			return def;
		return Integer.parseInt(attr);
	}

	public float getFloat(String attrName) {
		return getFloat(attrName, 0);
	}

	public float getFloat(String attrName, float def) {
		String attr = getStr(attrName);
		if (attr == null)
			return def;
		return Float.parseFloat(attr);
	}

}