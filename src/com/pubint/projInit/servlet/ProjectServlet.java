package com.pubint.projInit.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.pubint.projInit.base.BaseServlet;
import com.pubint.projInit.session.KsProjectBean;
import com.pubint.projInit.session.ProjectBean;

@WebServlet(value = "/Project")
public class ProjectServlet extends BaseServlet {
	static final long serialVersionUID = 1L;

	private static String servletName = "ProjectServlet";

	private XPathFactory xpf = XPathFactory.newInstance();
	private DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	private TransformerFactory tpf = TransformerFactory.newInstance();

	@javax.ejb.EJB
	ProjectBean projectBean;

	@javax.ejb.EJB
	KsProjectBean ksProjectBean;

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doDelete(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doPost(request, response);

		@SuppressWarnings("unused") HttpSession session = request.getSession();

		response.setContentType("text/xml");

		PrintWriter writer = response.getWriter();

		Document document = getXML(request, dbf);
		NodeList commands = getCommands(document);
		showXML(document, tpf);

		try {
			String action = "";

			for (Node commandNode = commands.item(0); commandNode != null;) {
				Node nextCommand = commandNode.getNextSibling();

				Element command = (Element) commandNode.cloneNode(true);

				command.appendChild(createElement(document, "sourceIP", request.getRemoteHost()));
				command.appendChild(createElement(document, "servletName", servletName));

				XPath xpath = xpf.newXPath();

				action = xpath.evaluate("//action", command);

				System.out.println("Action:" + action);

				if (action.equalsIgnoreCase("getXmlForID")) {
					String projectXML = projectBean.getXML(getLong(command, "id", xpath));

					writer.write(projectXML);
				} else if (action.equalsIgnoreCase("getXmlForProjectID")) {
					String projectXML = projectBean.getXML(getString(command, "projectID", xpath));

					writer.write(projectXML);
				} else if (action.equalsIgnoreCase("getKsProjectXML")) {
					String projectXML = ksProjectBean.getXML(getString(command, "projectID", xpath));

					writer.write(projectXML);
				} else if (action.equalsIgnoreCase("update")) {
					String statusXML = projectBean.updateProject(command, xpath);

					writer.write(statusXML);
				} else if (action.equalsIgnoreCase("delete")) {
					String statusXML = projectBean.deleteProject(command, xpath);

					writer.write(statusXML);
				} else if (action.equalsIgnoreCase("getCookbookList")) {
					String projectListXML = projectBean.selectList(1);

					writer.write(projectListXML);
				} else if (action.equalsIgnoreCase("getList")) {
					String projectListXML = projectBean.selectList();

					writer.write(projectListXML);
				} else {
					System.out.println("Unsupported function");
				}

				commandNode = nextCommand;
			}

			writer.flush();
		} catch (Exception e) {
			// package up the exception and send it back to the
			// client as an error message to be presented to
			// the user
			System.out.println("Exception: " + e.getMessage());

			e.printStackTrace();
		}
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPut(req, resp);
	}
}
