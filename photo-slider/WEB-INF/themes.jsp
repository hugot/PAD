<?PHP
header("Refresh:0");
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
			Naam:
			<input type="text" name="name" id="name"/>
			<input type="submit" value="voeg_toe" name="voeg_toe" id="voeg_toe" />
			
			</br>
			
<div id=themaStyle>
  <select name="Thema's" size="5" id="tabelwidth">
    <option value="2010" >Fightme1v1_IRL</option>
    <option value="2011" >Hippopotomonstrosesquippedaliofobie</option>
    <option value="2012" >2012</option>
    <option value="2013" >2013</option>
    <option value="2014" >2014</option>
    <option value="2010" >2010</option>
    <option value="2011" >2011</option>
    <option value="2012" >2012</option>
    <option value="2013" >2013</option>
    <option value="2014" >2014</option>
    <option value="2010" >2010</option>
    <option value="2011" >2011</option>
    <option value="2012" >2012</option>
    <option value="2013" >2013</option>
    <option value="2014" >2014</option>	
   </select>
	</div>
		</form>
		
	</body>

</html>
