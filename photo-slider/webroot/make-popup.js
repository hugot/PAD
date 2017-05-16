/*
 * make-popup.js
 * Copyright (C) 2017 hugo <hugo@supersudomachine>
 *
 * Distributed under terms of the MIT license.
 */
  'use strict';
  
  function createThemePopup(){
    var popup = document.getElementById("hiddenpopup");
    popup.id = "popup";
  }
  
  function hideCreateThemePopup(){
    var popup = document.getElementById("popup");
    popup.id = "hiddenpopup";
  }
    
