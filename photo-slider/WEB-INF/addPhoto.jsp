<%@ page contentType="text/html;Charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="" tagdir="/WEB-INF/tags"%>

<html lang="en">
<head>
	<link href="${context}/webroot/style/mainstylesheet.css" rel="stylesheet" type="text/css" media="screen" />
	<meta charset="UTF-8">
	<title></title>
</head>


	<body>
		<%@include file="header.jsp"%>
			
		<section id="main">
			<h1>Voeg een foto toe via deze pagina</h1>	
			<script type="text/javascript" src="${context}/webroot/dropzone.js"></script>
			<script type=text/javascript">
//				Dropzone.options.uploadForm = {
//					dictDefaultMessage: "Sleep foto's naar dit vlak om ze te uploaden",
//					paramName: "photo",
//					acceptedFiles: 'image/*'
//				};
//
				alert("wtf");
				//var uploader = document.querySelector('#upload-form');
				$(document).ready(function(){var uploadform = new Dropzone('form#upload-form', { method: "POST",
				dictDefaultMessage: "Sleep foto's naar dit vlak om ze te uploaden",
				paramName: "photo",
				acceptedFiles: 'image/*'
				});
				});
			</script>

			<div action="${context}/uploadphoto" class="dropzone needsclick dz-clickable" id="upload-form">
				<span id="explanation">Sleep foto's naar dit vlak om ze te uploaden</span>
			</div>


		</section>
		<c:if test="${message != null}">
			<p>${message}</p>
		</c:if>
	</body>

</html>
