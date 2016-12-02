<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<body>
<h2>Hello World!</h2>
<%=request.getContextPath() %>
<a href="<%=request.getContextPath() %>/会2.docx" target="bank">开始</a>
<div id="div1">fdsaaaaaaaaaaaaaa</div>
<input type="button" value="这是什么" 
onclick="window.document.getElementById('div1').setAttribute('src',encodeURI('<%=request.getContextPath() %>/会2.docx'));" />
</body>
</html>
