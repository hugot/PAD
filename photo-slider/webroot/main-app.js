/*
 * main-app.js
 * * Copyright (C) 2017 hugo <hugo@hugo-PC>
 *
 * Distributed under terms of the MIT license.
 * 
 * Dit script bevat alle hoofd-functionaliteit van de user interface.
 */
  'use strict';

  // Het frame waarin de onderdelen van de applicatie geladen worden
  var appFrame;
  // De onderdelen van de applicatie.
  var appParts = new Object();

  var ready = false;
  var selectedThemeId;
  var shownImage;
  var addPhotoDiv;
  

/**
 * Stuur een httpRequest naar de server doe vervolgens iets met de
 * response text.
 * @param {*} address 
 * @param {*} readyStateAction 
 */
  function ajaxCall(method, address, readyStateAction){
	  var xhr = new XMLHttpRequest();
	  if(method == null){
		method = 'GET';
	  }
	  xhr.open(method, address, true);
	  xhr.onreadystatechange = function () {
		  if (this.readyState !== 4) return; 
		  if (this.status !== 200){
			  console.log('response geeft errorcode' + this.status);
			  return;
		  } 
		  readyStateAction(this.responseText);
	  };
	  xhr.send();
  }

/**
 * Dit is een object voor het aanmaken en beheren van het 
 * frame waarin de applicatie getoond wordt.
 * @param {*} appFrameId 
 */
  function AppFrame(appFrameId){
	  var me = this;
	  this.frameElement = document.getElementById(appFrameId);

	  /** 
	   * Maak het frame zichtbaar.
	   */
	  this.setVisible = function(){
		  this.frameElement.id = 'app-frame';
	  }

	  /**
	   * Laad de inhoud van een httpResponse in het frame.
	   */
	  this.loadPage = function(page, callback){
		  ajaxCall(null, page, function(responseText){
			   me.frameElement.innerHTML = null;
			   me.frameElement.innerHTML = responseText;
			   if(typeof callback === 'function'){
				   callback();
			   }
		  });
	  };
	  
	  /**
	   * Laad een appPart object in het frame.
	   */
	  this.loadObject = function(myObject){
		  this.loadPage(myObject.url, myObject.onLoadCallback);
	  };
  }


/**
 * Gedeelte van de applicatie dat geladen kan worden.
 * @param {*} url 
 * @param {*} onLoadCallBack 
 */
  function AppPart(url, onLoadCallback){
	  this.url = url;
	  this.onLoadCallback = onLoadCallback;
	  
	  this.load = function(loadMe){
		  loadMe(this);
	  };
  }


/**
 * Maak het appFrame klaar voor gebruik en schuif welkomstsectie omhoog.
 * @param {*} appPart 
 */
  function getReady(appPart){
	  // Als de welkomstsetie al omhoog bewogen is, dan wrdt gewoon de pagina geladen.
	  if(ready == true ){
		  appFrame.loadObject(appPart);
		  return;
	  }
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
		  appFrame = document.getElementById('hidden-app-frame');
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
		  appFrame = new AppFrame('hidden-app-frame');
		  appFrame.setVisible();
		  appFrame.loadObject(appPart);
		  ready = true;
	  }, 'welcome-section', 0, -90);
  }

  // Laad de conten van een httpresponse in een div
  function loadContentTo(contentUrl, method, params, element, standardContent) {
	  console.log('loading content');
	  var xhr = new XMLHttpRequest();
	  element.innerHTML = null;
	  if (standardContent != null) {
		  element.appendChild(standardContent);
	  }
	  ajaxCall(method, contentUrl, function(responseText) {
		  element.innerHTML += responseText;
	  });
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
	  var imagePopupWrapper = document.getElementById('popup-wrapper');
	  var imagePopup = document.getElementById('show-image-popup');
	  var imageDisplay = document.getElementById('image-display');
	  if (imageDisplay.firstChild != null) {
		  imageDisplay.removeChild(imageDisplay.firstChild);
	  }
	  imageDisplay.appendChild(image.cloneNode());
	  imagePopup.className = 'popup';
	  imagePopupWrapper.style.visibility = 'visible';
	  shownImage = imageId;
}

  // Maakt een dropzone aan voor het uploaden van foto's
  var photoDropzoneConfig = function () {
		  Dropzone.autoDiscover = false;
		  var dropzoneOptions = {
			  dictDefaultMessage: "Sleep foto's naar dit vlak om ze te uploaden",
			  paramName: "photo",
			  acceptedFiles: "image/*"
		  };
		  var myDropzone = new Dropzone("form#uploadForm", dropzoneOptions);
		  myDropzone.on('sending', function (file, xhr, formData) {
			  formData.append("sessionId", sessionId.toString());
		  });
  }

  // Maakt een dropzone aan voor het uploaden van audio.
  var audioDropzoneConfig = function(){
		Dropzone.autoDiscover = false;
		var dropzoneOptions = {
				dictDefaultMessage: "Sleep audiobestanden naar dit vlak om ze te uploaden", 
				paramName: "sound",
				acceptedFiles: ".mp3",
		}
		var myDropzone = new Dropzone("form#uploadForm", dropzoneOptions);
		myDropzone.on('sending', function(file, xhr, formData){
						formData.append("sessionId", sessionId.toString());
		});
  }

// De pagina's van de applicatie.
  appParts['uploadPhoto'] 			= new AppPart('/uploadphoto', photoDropzoneConfig);
  appParts['themeManagement'] 		= new AppPart('/thememanagement', null);
  appParts['uploadAudio'] 			= new AppPart('/uploadaudio', audioDropzoneConfig);
  appParts['settings'] 				= new AppPart('/settingmanagement', null);
  appParts['photoDeletion'] 		= new AppPart('/deletephotos', null);

//(function(){
//	loadPage('/thememanagement');
//}())