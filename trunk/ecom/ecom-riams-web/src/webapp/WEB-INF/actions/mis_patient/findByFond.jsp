<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainShortLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
<style type="text/css">
    #${name}FindPatientByFond {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>

<div id='${name}FindPatientByFond' class='dialog'>
    <h2>Поиск по базе фонда</h2>
    <div class='rootPane'>
    <h3>Найденные данные по базе фонда</h3>
<form action="javascript:">
    <msh:panel>
    
    	<msh:row>
    		<span id='${name}FindPatientByFondText'/>
    	</msh:row>
    </msh:panel>
        <msh:row>
            <td colspan="6">
                <input type="button" value='Создать нового пациента' onclick='javascript:next${name}FindPatientByFond()'/>
                <input type="button" value='Обновить данные пациента' onclick='javascript:next${name}FindPatientByFond()'/>
                <input type="button" value='Закрыть окно' onclick='javascript:cancel${name}FindPatientByFond()'/>
            </td>
        </msh:row>
</form>

</div>
</div>

<script type='text/javascript' src='./dwr/interface/HospitalMedCaseService.js'></script>
<script type="text/javascript"><!--
     var theIs${name}FindPatientByFondInitialized = false ;
     var the${name}FindPatientByFond = new msh.widget.Dialog($('${name}FindPatientByFond')) ;
     // Показать
     function show${name}FindPatientByFond(aText) {
         // устанавливается инициализация для диалогового окна
         if (!theIs${name}FindPatientByFondInitialized) {
         	init${name}FindPatientByFond() ;
          }
         $('${name}FindPatientByFondText').innerHTML = aText ;
         the${name}FindPatientByFond.show() ;

     }

     // Изменение существующего
     function next${name}FindPatientByFond() {
    	 
    	 
		document.forms[0].submit() ;
     }
     // Отмена
     function cancel${name}FindPatientByFond() {
        the${name}FindPatientByFond.hide() ;
     }

     

     // инициализация диалогового окна
     function init${name}FindPatientByFond() {
     	theIs${name}FindPatientByFondInitialized = true ;
     	
     }
</script>
  </tiles:put>
  
</tiles:insert>