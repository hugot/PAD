	<form id="theme-creation-popup" class="hidden-popup" method="POST" action="/addtheme">
		<header>
			<h3>Maak een thema aan</h3>
		</header>
		<p>Geef het thema een naam:</p>
		<input name="theme-name" id="theme-name" placeholder="Thema-naam" type="text"/>
		<br><br>
		<button type="button" value="Maak thema" name="maak-thema" id="maak-thema" onclick="addTheme();" >Maak thema</button>
		<button name="annuleer" type="button" id="annuleer" onclick="hidePopup('theme-creation-popup');">Annuleer</button>
	</form>
    <div id="image-popup-wrapper">
    	<div id="show-image-popup" class="hidden-popup">
        <header> Foto beheer <button class="closing-button" onclick="hidePopup('show-image-popup');">X</button></header>
    		<div id="image-display">
    		</div>
    		<p id="photo-name">Dikkieee</p>
        	<button class="big-button" onclick=" " >Verwijder foto </button>
        	<button class="big-button" onclick=" " >Andere optie </button>
        	<button class="big-button" onclick=" " >Andere optie </button>
        	<button class="big-button" onclick=" " >Andere optie </button>
        	<button class="big-button" onclick=" " >Andere optie </button>
    	</div>
    </div>