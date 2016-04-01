<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>
<%@ attribute name="patientField" required="true" description="Поле Id пациента" %>

<style type="text/css">
    #${name}ByFondHistory {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>

<div id='${name}ByFondHistory' class='dialog'>
    <h2>Журнал проверок по базе ФОМС</h2>
    <div class='rootPane'>
    <h3>Найденные данные по базе фонда</h3>
<form action="javascript:void(0)" id="frmFond" name="frmFond">
    <msh:panel>
    
    	<msh:row>
    		<span id='${name}ByFondHistoryText'/>
    	</msh:row>
    </msh:panel>
    	
        <msh:row>
            <td colspan="8" align="center">
              <input type="button" value='Закрыть окно' onclick='javascript:cancel${name}ByFondHistory()'/>
            </td>
        </msh:row>
</form>

</div>
</div>


<script type="text/javascript"><!--
     var theIs${name}ByFondHistoryInitialized = false ;
     var the${name}ByFondHistory = new msh.widget.Dialog($('${name}ByFondHistory')) ;
     // Показать
     
     function show${name}ByFondHistory(aType) {
         // устанавливается инициализация для диалогового окна
        PatientService.showPatientCheckByFondHistory($('${patientField}').value,""+aType,{
    		callback: function (a) {
    			if (a!=null&&a!='') {
    			 $('${name}ByFondHistoryText').innerHTML = a ;
    			} else {
    				$('${name}ByFondHistoryText').innerHTML = "Проверок пациента по базе ФОМС не было" ;	
    			}
    		} 
    	 });
             
         the${name}ByFondHistory.show() ;

     }


     // Отмена
     function cancel${name}ByFondHistory() {
        the${name}ByFondHistory.hide() ;
        msh.effect.FadeEffect.pushFadeAll();
     }
     

     // инициализация диалогового окна
     function init${name}ByFondHistory(aType) {
    	 
     	theIs${name}ByFondHistoryInitialized = true ;
     	
     }
</script>