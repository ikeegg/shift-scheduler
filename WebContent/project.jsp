<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%--Set the page name --%>
<%
String systemName = "PIL Project Initiation";
String pageName = "Project Startup";
%>
<c:set var="pageName" value="PTO Home Page" />
<%--These are used for calculating the base URI --%>
<c:set var="req" value="${pageContext.request}" />
<c:set var="uri" value="${req.requestURI}" />
<c:set var="url">${req.requestURL}</c:set>
<c:set var="base" value="${fn:substring(url, 0, fn:length(url) - fn:length(req.requestURI))}${req.contextPath}/" />
<%
    // put any parameter grabs here
%>
<!DOCTYPE html>
<html class="no-js">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title><%=systemName %> - <%=pageName %></title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <base href="${base}" />
        <link href="${base}css/screen.css" title="base" media="screen, projection" rel="stylesheet" type="text/css" />
            
        <script src="${base}js/vendor/modernizr.js"></script>
        <script src="${base}js/vendor/jquery.js"></script>
        <script src="${base}js/general.js"></script>

        <!-- Add any page specific javaScripts here -->
        <script src="${base}js/project.js"></script>
    </head>

    <body>
        <div id="header">
        </div>
        <div id="body">
            <div class="row">
                <div class="small-12 column">
                    <input type="text" id="title" />
                </div>
            </div>
            <div class="row">
                <div class="small-4 column">
                    <label>Project</label>
                    <select id="projectID"></select>
                </div>
                <div class="small-4 column">
                    <label>Page Count</label>
                    <input type="text" id="pageCount" disabled />
                </div>
                <div class="small-4 column">
                    <label>Item Number</label>
                    <input type="text" id="itemNumber" disabled />
                </div>
            </div>
            <div class="row">
                <div class="small-4 column">
                    <label>Editor</label>
                    <input type="text" id="editorID" disabled />
                </div>
                <div class="small-4 column">
                    <label>Trim Size</label>
                    <input type="text" id="trimSize" disabled />
                </div>
                <div class="small-4 column">
                    <label>ISBN-13</label>
                    <input type="text" id="isbn13" disabled />
                </div>
            </div>
            <div class="row">
                <div class="small-4 column">
                    <label>Digital Processing</label>
                    <input type="text" id="digitalProcessing" disabled />
                </div>
                <div class="small-4 column">
                    <label>Colors</label>
                    <input type="text" id="colors" disabled />
                </div>
                <div class="small-4 column">
                    <label for="developedFor">Developed for</label>
                    <input type="text" id="develpedFor" />
                </div>
            </div>
            <div class="row">
                <div class="small-4 column">
                    <label>Art Director</label>
                    <input type="text" id="artDirector" disabled />
                </div>
                <div class="small-4 column">
                    <label>Binding</label>
                    <input type="text" id="binding" disabled />
                </div>
                <div class="small-4 column">
                    <label>Exclusive for</label>
                    <input type="text" id="exclusiveFor" />
                </div>
            </div>
            <div class="row">
                <div class="small-4 column">
                </div>
                <div class="small-4 column">
                </div>
                <div class="small-4 column">
                </div>
            </div>
            <div class="row">
                <div class="small-4 column">
                </div>
                <div class="small-4 column">
                </div>
                <div class="small-4 column">
                </div>
            </div>
        </div>
        
        <script src="${base}js/foundation.min.js"></script>
    </body>
</html>
