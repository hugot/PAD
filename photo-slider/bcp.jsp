<%@ page contentType="text/html;Charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="" tagdir="/WEB-INF/tags"%>

<html lang="en">
<head>
	<meta charset="UTF-8">
	<title></title>
</head>
	<head>
		<link href="${context}/webroot/style/mainstylesheet.css" rel="stylesheet" type="text/css" media="screen" />
		<title>Maintain themes</title>
	</head>
			<%--<header id="options-section">
				<button class="big-button">Toon dit thema nu in de Slideshow.</button><%--
				--%><button class="big-button">Verwijder het thema.</button><%--
				--%><button class="big-button">Zet het thema aan/uit.</button><%--
				--%><button class="big-button">Voeg muziek toe aan het thema.</button><%--
				--%><button class="big-button">Zet de muziek aan/uit.</button><%--
				--%><button class="big-button">Zet het geluid aan/uit.</button><%--
				--%><button class="big-button">Verwijder muziek van het thema.</button><%--
					<%--<li>Volume: $AudioVolume
						<form class="small-button-form" style="float: left;" action="/LowerMusicVolume" method="get">
							<button class="big-button">-</button>
						</form> 
						<form class="small-button-form" style="float: right;" action="/UpperMusicVolume" method="get">
							<button class="big-button">+</button>
						</form> 
					</li> 
					<li>Slideshow timer <input type="text" id="timer"> </li>--%> --%>
			</header>
<body>
	
						<form class="small-button-form" style="float: center;" action="/showSelectedTheme" method="post">
							<button class="big-button">Toon dit thema nu in de Slideshow.</button>
						</form>
						<form class="small-button-form" style="float: center;" action="/deleteTheme" method="post">
							<button class="big-button">Verwijder het thema.</button>
						</form>
						<form class="small-button-form" style="float: center;" action="/setThemeOnOff" method="get">
							<button class="big-button">Zet het thema aan/uit.</button>
						</form>
						<form class="small-button-form" style="float: center;" action="/audioselection" method="get">
							<button class="big-button">Voeg muziek toe aan het thema.</button>
						</form> 
					
					
						<form class="small-button-form" style="float: center;" action="/setMusicOnOff" method="get">
							<button class="big-button">Zet de muziek aan/uit.</button>
						</form> 
					
					
						<form class="small-button-form" style="float: center;" action="/turnAudioOnOff" method="post">
							<button class="big-button">Zet het geluid aan/uit.</button>
						</form> 
					
					
						<form class="small-button-form" style="float: center;" action="/removeMusicFromTheme" method="get">
							<button class="big-button">Verwijder muziek van het thema.</button>
						</form> 
					<%--<li>Volume: $AudioVolume
						<form class="small-button-form" style="float: left;" action="/LowerMusicVolume" method="get">
							<button class="big-button">-</button>
						</form> 
						<form class="small-button-form" style="float: right;" action="/UpperMusicVolume" method="get">
							<button class="big-button">+</button>
						</form> 
					</li> 
					<li>Slideshow timer <input type="text" id="timer"> </li>--%>
</body>
</html>
