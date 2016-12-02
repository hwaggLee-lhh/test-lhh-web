package com.test.blog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * 
 * 读取http://www.cnblogs.com/hwaggLee/上面的内容
 * @author huage
 *
 */
public class ReadCnblogsUtils {

	public static void main(String[] args) {
		ReadCnblogsUtils u = new ReadCnblogsUtils();
		List<CnblogsArticle> list = u.parserXML();
	}
	
	public List<CnblogsArticle> parserXML(){
		String str = "C:\\Users\\huage\\Desktop\\test\\CNBlogs_BlogBackup_131_201504_201607.xml";
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null ;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} 
		if( builder == null )return null;
		Document document = null;
		try {
			document = builder.parse(str);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		if( document == null )return null;
		Node nodes = document.getFirstChild();
		if( nodes == null )return null;
		NodeList nodeList = nodes.getChildNodes();
		if( nodeList == null || nodeList.getLength() == 0 ) return null;
		Node nodesFirstChild = nodeList.item(0);
		List<CnblogsArticle> list = convertNodeList(nodesFirstChild);
		return list;
	}
	
	private List<CnblogsArticle> convertNodeList(Node node){
		if( node == null )return null;
		NodeList nodeList = node.getChildNodes();
		if( nodeList == null || nodeList.getLength() == 0 ) return null;
		List<CnblogsArticle> list = new ArrayList<ReadCnblogsUtils.CnblogsArticle>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node n = nodeList.item(i);
			if( n == null || !"item".equals(n.getNodeName()))continue;
			CnblogsArticle cn = converNode(n);
			if(cn == null )continue;
			list.add(cn);
		}
		return list;
	}
	
	private CnblogsArticle converNode(Node node){
		if( node == null )return null;
		NodeList nodeList = node.getChildNodes();
		if( nodeList == null || nodeList.getLength() == 0 ) return null;
		CnblogsArticle cn = new ReadCnblogsUtils().new CnblogsArticle();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node n = nodeList.item(i);
			if( n == null )continue;
			String name = n.getNodeName();
			String content = n.getTextContent();
			if( StringUtils.isBlank(name) || StringUtils.isBlank(content))continue;
			switch(name){
			case "title":
				cn.setTitle(content);
				break;
			case "link":
				cn.setLink(content);
				break;
			case "author":
				cn.setAuthor(content);
				break;
			case "dc:creator":
				cn.setCreator(content);
				break;
			case "pubDate":
				cn.setPubDate(content);
				break;
			case "guid":
				cn.setGuid(content);
				break;
			case "description":
				cn.setDescription(content);
				break;
			default:
				break;
			}
		}
		return cn;
	}
	
	
	public class CnblogsArticle{
		private String title;
		private String link;
		private String author;
		private String creator;
		private String pubDate;
		private String guid;
		private String description;
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getLink() {
			return link;
		}
		public void setLink(String link) {
			this.link = link;
		}
		public String getAuthor() {
			return author;
		}
		public void setAuthor(String author) {
			this.author = author;
		}
		public String getCreator() {
			return creator;
		}
		public void setCreator(String creator) {
			this.creator = creator;
		}
		public String getPubDate() {
			return pubDate;
		}
		public void setPubDate(String pubDate) {
			this.pubDate = pubDate;
		}
		public String getGuid() {
			return guid;
		}
		public void setGuid(String guid) {
			this.guid = guid;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
	}
}
