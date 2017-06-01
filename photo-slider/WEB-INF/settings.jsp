<%@ page contentType="text/html;Charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="" tagdir="/WEB-INF/tags"%>

<%-- 

Author: Hugo Thunnissen

Dit is een template voor het maken van nieuwe views

--%>

<html lang="en">
	<head>
		<link href="${context}/webroot/style/mainstylesheet.css" rel="stylesheet" type="text/css" media="screen" />
		<meta charset="UTF-8">
			<title>TEMPLATE</title>
		</head>
		<body>
			<%@include file="header.jsp"%>

			<section id="main-section">

				<header><h2> De instellingen van de slideshow</h2></header>
				<form id="settings" action="settingmanagement" method="post">
					<input type="hidden" value="reset" id="reset" name="reset">
					Het is mogelijk om het apparaat te resetten naar de nieuwstaat.<br>
					Druk hiervoor op de onderstaande knop:<br><br>
					<button type="button" onclick="showPopup('reset-confirmation');">reset</button>
				</form>

				<form id="reset-confirmation" class="hidden-popup">
					<header><h3>Weet u het zeker?</h3></header>
					<p> Als u op doorgaan klikt, wordt alle media van het apparaat verwijderd
						en wordt het apparaat teruggebracht naar de nieuwstaat. </p>
					<button type="button" onclick="document.getElementById('settings').submit();hidePopup('reset-confirmation');">Doorgaan</button>
					<button type="button" onclick="hidePopup('reset-confirmation');">annuleer</button>
				</form>
			</section>

			<%@include file="footer.jsp"%>
		</body>
	</html>
