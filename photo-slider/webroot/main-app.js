/*
 * main-app.js
 * * Copyright (C) 2017 hugo <hugo@hugo-PC>
 *
 * Distributed under terms of the MIT license.
 * 
 * Dit script bevat alle hoofd-functionaliteit van de user interface.
 */
  'use strict';

  var selectedThemeId;
  var shownImage;
  var addPhotoDiv;
  // Deze functie maakt de applicatie klaar voor gebruik bij het klikken op een knop.
  function getReady(page){
	  // Beweeg de welkomst sectie omhoog met deze functie.
	  function moveUp(callback, id, oldTop, newTop){
		  var element = document.getElementById(id);
		  var tp = parseInt(element.style.top);
		  if(isNaN(tp)){
			  tp = oldTop;
		  }
		  console.log(tp + " is tp");
		  var interval = setInterval(move,100);
		  function move(){
			  if(tp <= newTop ){
				  clearInterval(interval);
				  if(typeof callback === 'function'){
					  callback();
				  }
			  }
			  else {
				  element.style.top = tp + "%";
				  tp-=5;
			  }
		  }
	  }
	  moveUp(function(){
		  var welcomeSection = document.getElementById('welcome-section');
		  var wrapper = document.createElement('SECTION'); 
		  var title = document.createElement('H1');
		  var buttonCollection = document.getElementsByClassName('top-button');
		  var buttons = Array.prototype.slice.call(buttonCollection);
		  var appFrame = document.getElementById('hidden-app-frame');
		  wrapper.id = 'top-section';
		  title.textContent = 'PhotoSlider';
		  wrapper.appendChild(title);
		  for(var i in buttons){
			  var button = buttons[i];
			  wrapper.appendChild(button);
		  }
		  document.body.appendChild(wrapper);
		  document.body.appendChild(appFrame);
		  welcomeSection.remove();
		  appFrame.id = 'app-frame';
		  loadPage(page);
	  }, 'welcome-section', 0, -90);
  }


  function getPage(){
	  var form = document.createElement('FORM');
	  document.body.appendChild(form);
	  form.setAttribute('action', '/thememanagement');
	  form.setAttribute('method', 'get');
	  form.setAttribute('target', 'app-frame');
	  form.submit();
  }
  
  // Laad een nieuwe pagina in de appframe
  function loadPage(page){
	  if(document.getElementById('welcome-section') != null){
		  getReady(page);
		  console.log('getting ready');
	  }
	  else {
		  console.log('loading page');
		  var xhr= new XMLHttpRequest();
		  xhr.open('GET', page, true);
		  xhr.onreadystatechange= function() {
			  if (this.readyState!==4) return;
			  if (this.status!==200) return; 
			  var frame = document.getElementById('app-frame');
			  frame.innerHtml= null;
			  frame.innerHTML= this.responseText;
		  };
		  xhr.send();
	  }
  }

  // Laad de conten van een httpresponse in een div
  function loadContentTo(contentUrl, method,  params, element, standardContent){
	  console.log('loading content');
	  var xhr= new XMLHttpRequest();
	  element.innerHTML = null;
	  if(standardContent != null){
		  element.appendChild(standardContent);
	  }
	  xhr.open(method, contentUrl, true);
	  xhr.onreadystatechange= function() {
		  if (this.readyState!==4) return;
		  if (this.status!==200) return; 
		  element.innerHTML += this.responseText;
	  }
	  xhr.send(params);
  }

  // Laad de foto's voor een thema.
  function setActiveTheme(themeId){
	  var element = document.getElementById('photo-section');
	  if(addPhotoDiv == null){
		  addPhotoDiv = element.firstChild;
	  }
	  loadContentTo('/getphotos', 'POST', 'selectedThemeId='+themeId, element, addPhotoDiv);
	  if(selectedThemeId != null){
		  document.getElementById('selected-theme').id = selectedThemeId;
	  }
	  var theme = document.getElementById(themeId);
	  theme.id = 'selected-theme';
	  document.getElementById('photo-section-header-text').innerText = 'Foto\'s in ' + theme.innerText;
	  selectedThemeId = themeId;
  }

  function showPhotoSelection(){
	  var photoSection = document.getElementById('photo-section');
	  addPhotoDiv = photoSection.firstChild;
	  loadContentTo('/photoselection', 'GET', '', photoSection, null);
  }

// Voeg een thema toe 
  function addTheme(){
	  var themeName = document.getElementById('theme-name');
	  console.log(themeName.value);
	  loadContentTo('/addtheme', 'POST', 'theme=' + themeName.value, 'app-frame');
	  document.getElementById('photo-section-header-text').innerText = 'kies foto\'s om toe te voegen aan thema';
  }

//Toon een foto vergroot in een popup.
  function showImage(imageId){
	  var image =  document.getElementById('image'+imageId);
	  var imagePopup = document.getElementById('show-image-popup');
	  var imageDisplay = document.getElementById('image-display');
	  if(imageDisplay.firstChild != null){
		  imageDisplay.removeChild(imageDisplay.firstChild);
	  }
	  imageDisplay.appendChild(image.cloneNode());
	  imagePopup.className = 'popup';
	  shownImage = imageId;
  }

//(function(){
//	loadPage('/thememanagement');
//}())