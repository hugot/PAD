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
		<link href="${context}/webroot/style/ThemeSettings.css" rel="stylesheet" type="text/css" />
		<title>Maintain themes</title>
	</head>
	
	<body>
		<%@include file="header.jsp"%>
		<div id="theme_settings">
		
			<div id="add">
				<h3>Voeg een Thema toe</h3>
				Naam: <label for="name"></label> <input id="name" type="text">
				<input type="button" value="Voeg toe">
				
				<div id="table">
					<select name="themelist" size="5" id="tablewidth">
					</select>
				</div>
				
			</div>
			
			<div id="photos">
				<h3>Thema X | Foto's</h3>
				Voeg een Foto toe: <input type="button" value="Voeg toe">
				<ul>
					<li>Foto1 <input type="button" value="on/off"> <input type="button" value="Add Sound"> <input type="button" value="Mute Sound"> <input type="button" value="Delete"> </li>
					<li>Foto2 <input type="button" value="on/off"> <input type="button" value="Add Sound"> <input type="button" value="Mute Sound"> <input type="button" value="Delete"> </li>
				</ul>
			</div>
			
			<div id="settings">
				<h3>Thema X | Options</h3>
				<ul>
					<li>Zet thema aan/uit <input type="button" value="X"> </li>
					<li>Voeg muziek toe aan het thema <input type="button" value="Voeg toe"> </li>
					<li>Zet muziek aan/uit <input type="button" value="X"> </li>
					<li>Verwijder muziek van thema <input type="button" value="X"> </li>
					<li>Volume: <input type="button" value="-">X <input type="button" value="+"> </li>
					<li>Slideshow timer <input type="text" id="timer"> </li>
					
				</ul>
			</div>
			
		</div>
	</body>
</html>

