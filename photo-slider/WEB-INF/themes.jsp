<?PHP

?>
<%@ page contentType="text/html;Charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="" tagdir="/WEB-INF/tags"%>

<%-- 

	Author: Hugo Thunnissen, Marco Bergsma

	Via deze view kunnen de thema's beheerd worden
	
--%>

<html lang="en">

	<head>
		<link href="${context}/webroot/style/mainstylesheet.css" rel="stylesheet" type="text/css" media="screen" />
		<link href="${context}/webroot/style/thematabel.css" rel="stylesheet" type="text/css" />
		<link href="${context}/webroot/style/themaStyle.css" rel="stylesheet" type="text/css" />
		<title>Maintain themes</title>
	
	</head>


	<body>
		<%@include file="header.jsp"%>
		<h1>Op deze pagina kunnen de Thema's beheerd worden</h1>
		<form method="POST" action="${context}/addTheme" enctype="multipart/form-data">			
			Voeg een thema toe:
			<label for="name"></label> <input id="name" type="text" > 
			<input type="button" value="Save" onclick="insert()"/>
			<div>
				<select name="Thema's" size="5" id="tablewidth">
				</select> 
			</div>
		</form>
		
	</body>

	<script>
		var themes = new Array();
		function insert(){
			themes[themes.length]="<option>" + document.getElementById("name").value + "</option>";
			var content="<br>";
			for(var i = 0; i < themes.length; i++) {
			content +=themes[i]+"<br>";
			}
			document.getElementById('tablewidth').innerHTML = content;
		}
		
		function show(){
			
		}
	</script>
</html>

