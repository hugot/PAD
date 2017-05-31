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
		<section id="main-section">
			<section id="selection-bar">
				<header> 
					<h3>Thema selectie</h3>
					<form class="big-button-form" style="float:right;">
						<button type="button" class="big-button" onclick="showPopup('theme-creation-popup');">Nieuw thema</button>
					</form>
				</header>
				<%-- --------------------------- LIJST MET THEMA'S -------------------------------- --%>
													${themes}
			</section>

			<section id="middle-section">
				<header>
					<h3> ${theme} | Foto's</h3>
					<form class="big-button-form" style="float: right;" action="/photoselection" method="get">
						<button class="big-button">Voeg een foto toe</button>
					</form>
				</header>
					<%-- ---------------------- LIJST MET FOTO'S --------------------- --%>
													${photos}
			</section>

			<section id="right-section">
				<header>
					<h3> ${theme} | Options</h3>
				</header>
				<ul>
					<li>
						<form class="small-button-form" style="float: center;" action="/deleteTheme" method="post">
							<button class="small-button">Verwijder het thema.</button>
						</form></li>
					<li>
						<form class="small-button-form" style="float: center;" action="/setThemeOnOff" method="get">
							<button class="small-button">Zet het thema aan/uit.</button>
						</form></li>
					<li>
						<form class="small-button-form" style="float: center;" action="/audioselection" method="get">
							<button class="small-button">Voeg muziek toe aan het thema.</button>
						</form> 
					</li>
					<li>
						<form class="small-button-form" style="float: center;" action="/setMusicOnOff" method="get">
							<button class="small-button">Zet de muziek aan/uit.</button>
						</form> 
					</li>
					<li>
						<form class="small-button-form" style="float: center;" action="/turnAudioOnOff" method="post">
							<button class="small-button">Zet het geluid aan/uit.</button>
						</form> 
					</li>
					<li>
						<form class="small-button-form" style="float: center;" action="/removeMusicFromTheme" method="get">
							<button class="small-button">Verwijder muziek van het thema.</button>
						</form> </li>
					<li>Volume: $AudioVolume
						<form class="small-button-form" style="float: left;" action="/LowerMusicVolume" method="get">
							<button class="small-button">-</button>
						</form> 
						<form class="small-button-form" style="float: right;" action="/UpperMusicVolume" method="get">
							<button class="small-button">+</button>
						</form> 
					</li> 
					<li>Slideshow timer <input type="text" id="timer"> </li>
				</ul>
				<%-- -------- MUZIEK ------- --%>
							${music}
			</section>
		<form id="theme-creation-popup" class="hidden-popup" method="POST" action="/addtheme">
			<header>
				<h3>Maak een thema aan</h3>
			</header>
			<p>Geef het thema een naam:</p>
			<input name="name" id="name" placeholder="Thema-naam" type="text"/>
			<br><br>
			<input type="submit" value="Maak thema" name="maak-thema" id="maak-thema" />
			<button name="annuleer" type="button" id="annuleer" onclick="hidePopup('theme-creation-popup');">Annuleer</button>
		</form>
	</section>
	<script> var selectedtheme = document.getElementById('${selectedThemeId}');
										 selectedtheme.id = "selected-theme";
	</script>
	<%@include file="footer.jsp"%>
</body>
</html>

