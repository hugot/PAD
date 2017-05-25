<%@ page contentType="text/html;Charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="" tagdir="/WEB-INF/tags"%>

<html lang="en">
<head>
	<meta charset="UTF-8">
	<title></title>
	<link href="${context}/webroot/style/mainstylesheet.css" rel="stylesheet" type="text/css" media="screen" />
</head>
<body>
	<%@include file="header.jsp"%>
	
	<%-- ------- DE AUDIO FILES'S ------ --%>
				 ${audios}

	<%@include file="footer.jsp"%>
</body>
</html>