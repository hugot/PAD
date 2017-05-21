<%@ page contentType="text/html;Charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="" tagdir="/WEB-INF/tags"%>

<%-- 

Author: Hugo Thunnissen, Marco Bergsma

Via deze view kunnen de thema's beheerd worden

--%>

<html lang="en">

	<head>
		<link href="${context}/webroot/style/mainstylesheet.css" rel="stylesheet" type="text/css" media="screen" />
		<title>Maintain themes</title>
	</head>

	<body>
		<%@include file="header.jsp"%>
		<script src="${context}/webroot/make-popup.js"></script>
		<section id="main-section">
			<section id="selection-bar">
				<header> 
					<h3 style="display: inline-block;">Thema selectie</h3>
					<button style="float: right; height: 60px;" onclick="showPopup('theme-creation-popup');">Nieuw thema</button>
				</header>
				<%-- --------------------------- LIJST MET THEMA'S -------------------------------- --%>
													${themes}
			</section>

			<section id="middle-section">
				<header>
					<h3> ${theme} | Foto's</h3>
				</header>
				Voeg een Foto toe: <input type="button" value="Voeg toe">
					<%-- ---------------------- LIJST MET FOTO'S --------------------- --%>
													${photos}
			</section>

			<section id="right-section">
				<header>
					<h3>Thema X | Options</h3>
				</header>
				<ul>
					<li>Zet thema aan/uit <input type="button" value="X"> </li>
					<li>Voeg muziek toe aan het thema <input type="button" value="Voeg toe"> </li>
					<li>Zet muziek aan/uit <input type="button" value="X"> </li>
					<li>Verwijder muziek van thema <input type="button" value="X"> </li>
					<li>Volume: <input type="button" value="-">X <input type="button" value="+"> </li>
				<li>Slideshow timer <input type="text" id="timer"> </li>
			</ul>
		</section>
		<%-- POPUP --%>
		   ${popup}
		<form id="theme-creation-popup" class="hidden-popup" method="POST" action="/addtheme">
			<header>
				<h3>Maak een thema aan</h3>
			</header>
			<p>Geef het thema een naam:</p>
			<input name="name" id="name" placeholder="Thema-naam" type="text"/>
			<br><br>
			<input type="submit" value="Maak thema" name="maak-thema" id="maak-thema" />
			<button name="annuleer" id="annuleer" onclick="hidePopup('theme-creation-popup');">Annuleer</button>
		</form>
	</section>
	<script> var selectedtheme = document.getElementById('${selectedThemeId}');
										 selectedtheme.id = "selected-theme";
	</script>
</body>
</html>

