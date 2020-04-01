<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ru" xml:lang="ru"><head>
   <title>МедОС</title>
   <meta http-equiv="content-type" content="text/html; charset=utf-8"/>

<!-- CSS  -->
<link rel='stylesheet' type='text/css' href='/skin/css/css/main/main-CA11fcb11d2c8.css' /><style type="text/css">
	@import url("/skin/css/css/main/main_fo-CA11fcb11d2c8.css") all;
</style>
<!--[if IE]>
	<link rel='stylesheet' type='text/css' href='/skin/css/css/main/main_ie-CA11fcb11d2c8.css' /><![endif]-->

<script type="text/javascript">
try {
  switch(screen.width) {
      case 800: document.write("<link rel='stylesheet' type='text/css' href='/skin/css/css/main/main_800-CA11fcb11d2c8.css' />") ; break ;
      case 1024: document.write("<link rel='stylesheet' type='text/css' href='/skin/css/css/main/main_1024-CA11fcb11d2c8.css' />") ; break ;
  }
} catch (e) {
}
</script>

<script type='text/javascript' src='/skin/js/ac-CA11fcb11d2c8.js'></script><script type='text/javascript' src='/skin/js/engine-CA11fcb11d2c8.js'></script><script type='text/javascript' src='./dwr/interface/VocService-CA11fcb11d2c8.js'></script><script type='text/javascript' src='./dwr/interface/VocEditService-CA11fcb11d2c8.js'></script><script type='text/javascript' src='./dwr/interface/RemoteMonitorService-CA11fcb11d2c8.js'></script><!-- Дополнительное определение стиля -->
<!-- Дополнительное определение стиля END -->


 </head>

<body>
    <div id="divResult"  class="formInfoMessage" style="display: none"></div>
    <form action="javascript:void(0)" name="mainForm" id="mainForm">
    <input type="hidden" name="url_image_tmp" id="url_image_tmp" value="${url_image}"/>
    <input type="hidden" name="url_image_tmp_comp" id="url_image_tmp_comp" value="${url_image_comp}"/>
    <input type="hidden" name="objectId" id="objectId" value="${objectId}"/>
    <input type="hidden" name="objectType" id="objectType" value="${objectType}"/>
    <div id='divImport' style="display:none;">
    /docmis/${url_image} <input type="button" name="run_import" value="Импорт документов"  onclick="getRunImport('${url_image_full}')" />
    </div>
    </form>
    <br><img  src='/docmis/${url_image}' width="100%" />
        <script type="text/javascript">
        	if ('${result_image}'!='') {
        		$('divResult').style.display = "block" ;
        		$('divResult').innerHTML = '${result_image}' ;
        	} else {
        		$('divImport').style.display = "block" ;
        	}
        	function cancelImport() {
        		window.location = "riams_config.do";
        	}
        	
        	
        	
        	function getRunImport() {
        		$('mainForm').action ='externalDocument-viewImage.do' ;
	        	//$('mainForm').target="fileinfo" ;
	        	//$('mainForm').enctype ='multipart/form-data' ;
	        	//$('tmpFile').value='0' ;
	        	$('mainForm').submit() ;
        		
        	}
           // Element.addClassName($('mainMenuService'), "selected");
        </script>
    </body>

</html>        