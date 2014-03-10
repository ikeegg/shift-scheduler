package com.pubint.projInit.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.Writer;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Base base;

	public BaseServlet() {
		super();

		base = new Base();
	}

	public boolean notEmptyString(String value) {
		return base.notEmptyString(value);
	}

	public long getLong(Element node, String elementName, XPath xpath) {
		return base.getLong(node, elementName, xpath);
	}

	public int getInt(Element node, String elementName, XPath xpath) {
		return base.getInt(node, elementName, xpath);
	}

	public String getString(Element node, String elementName, XPath xpath) {
		return base.getString(node, elementName, xpath);
	}

	public double getReal(Element node, String elementName, XPath xpath) {
		return base.getReal(node, elementName, xpath);
	}

	public Date getDate(Element node, String attribute, XPath xpath) {
		return base.getDate(node, attribute, xpath);
	}

	public Date shiftDate(Date fromDate, String offset) {
		Date newDate = new Date(fromDate.getTime() + (Long.parseLong(offset) * 24 * 60 * 60 * 1000));

		return newDate;
	}

	public java.sql.Date now() {
		return new java.sql.Date((new java.util.Date()).getTime());
	}

	public String rjlzf(String data, int length) {
		return base.rjlzf(data, length);
	}

	public void logError(Exception e) {
		System.out.println("Exception: " + e.getMessage());
		System.out.println("StackTrace:");
		e.printStackTrace();
	}

	public void sendError(Writer writer, String message) throws IOException {
		writer.write("<error msg='" + message + "' />");
		writer.flush();
	}

	protected void sendMessage(HashMap<String, String> contents, String route, String action, ConnectionFactory connectionFactory, Queue queue) {
		if (connectionFactory == null) {
			System.out.println("Connection factory lookup failed!");
		} else {
			System.out.println("Here are the factory details:");
			System.out.println(connectionFactory.toString());
			System.out.println();
		}

		if (queue == null) {
			System.out.println("Queue lookup has failed!");
		} else {
			System.out.println("Here are the queue details:");
			System.out.println(queue.toString());
			System.out.println();
		}

		Connection connection = null;

		try {
			connection = connectionFactory.createConnection();
			connection.start();

			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer producer = session.createProducer(queue);

			ObjectMessage message = session.createObjectMessage();

			message.setStringProperty("route", route);
			message.setStringProperty("action", action);

			message.setObject(contents);

			System.out.println("About to send the following message to the queue...");
			System.out.println("Message: " + message.toString());

			for (int loop = 0; loop < 1; loop++) {
				producer.send(message);
			}

			System.out.println("Message sent!");

			producer.close();

			session.close();

			connection.close();
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (JMSException jmse) {
					System.out.println("Problem closing connection...");

					jmse.printStackTrace();
				}
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");

		String userLine =
			"Servlet Processing for: " + this.getClass().getName() +
				" (" + serialVersionUID + ") by: " +
				session.getAttribute("userFirstName") + " " +
				session.getAttribute("userLastName") + " " +
				" (" + session.getAttribute("userID") + ")\n" +
				"Accessed from: " + request.getRemoteAddr() +
				" at " + format.format(new java.util.Date());

		System.out.println(userLine);
	}

	protected Document getXML(HttpServletRequest request, DocumentBuilderFactory dbf) {
		Document document = null;

		try {
			DocumentBuilder db = dbf.newDocumentBuilder();

			BufferedReader in = request.getReader();

			String input = "";
			String xmlText = "";

			while ((input = in.readLine()) != null) {
				xmlText = xmlText + input;
			}

			System.out.println("Received: " + xmlText);

			InputSource source = new InputSource();
			source.setCharacterStream(new StringReader(xmlText));

			document = db.parse(source);
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());

			e.printStackTrace();
		}

		return document;
	}

	protected NodeList getCommands(Document document) {
		NodeList commands = null;

		try {
			commands = document.getElementsByTagName("command");
		} catch (Exception e) {
			System.out.println("Exception trying to parse out command list: " + e.getMessage());
			e.printStackTrace();
		}

		return commands;
	}

	protected void showXML(Node document, TransformerFactory tpf) {
		Transformer serializer;

		try {
			serializer = tpf.newTransformer();
			// Setup indenting to "pretty print"
			serializer.setOutputProperty(OutputKeys.INDENT, "yes");
			serializer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

			serializer.transform(new DOMSource(document), new StreamResult(System.out));
		} catch (TransformerException e) {
			// this is fatal, just dump the stack and throw a runtime exception
			e.printStackTrace();

			throw new RuntimeException(e);
		}
	}

	protected org.w3c.dom.Element createElement(Document document, String elementName, String elementValue) {
		org.w3c.dom.Element element = null;

		element = document.createElement(elementName);
		element.appendChild(document.createTextNode(elementValue));

		return element;
	}

	protected org.w3c.dom.Element createElement(Document document, String elementName, long elementValue) {
		org.w3c.dom.Element element = null;

		element = document.createElement(elementName);
		element.appendChild(document.createTextNode("" + elementValue));

		return element;
	}
}