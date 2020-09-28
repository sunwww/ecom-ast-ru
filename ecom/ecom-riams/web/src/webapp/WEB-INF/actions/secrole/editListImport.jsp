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

    <msh:section>
      <msh:sectionTitle>Список доступных ролей для импорта</msh:sectionTitle>
      <msh:sectionContent>
        <msh:table selection="multiply" name="roles" action="alert" idField="idInFile" noDataMessage="Нет ролей">
          <msh:tableColumn columnName="Ключ" property="key" />
          <msh:tableColumn columnName="Название" property="name" />
          <msh:tableColumn columnName="Комментарий" property="comment" />
          <msh:tableColumn columnName="Существует" property="IsExist" />
        </msh:table>
      </msh:sectionContent>
    </msh:section>
        
        
<script type='text/javascript'>


// class ru.nuzmsh.web.tags.TableTag
 var theTableArrow ;
 window.onload = function () {
  theTableArrow = new tablearrow.TableArrow('tab1') ;
 }


</script>


    <!-- Additional Javascript -->
    <script type="text/javascript">function importRole() {
                var ids = theTableArrow.getInsertedIdsAsParams("id") ;
                if (ids) {
                	return ids ;
                } else {
                    alert("Нет выделенных ролей");
                    return null ;
                }
            }</script>



    </body>

</html>        