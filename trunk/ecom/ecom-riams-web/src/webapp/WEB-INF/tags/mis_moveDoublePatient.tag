<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>
<%@ attribute name="roles" required="true" description="Роли" %>
<%@ attribute name="title" required="true" description="Заголовок" %>

<msh:sideLink roles="${roles}" name="${title}" action=" javascript:show${name}DoublePatient('.do') " 
	 />

<style type="text/css">
    #${name}DoublePatientDialog {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>

<div id='${name}DoublePatientDialog' class='dialog'>
    <h2>Перенос данных по пациентам</h2>
    <div class='rootPane'>
    
<form action="javascript:">
    <msh:panel>
    	<msh:row>
    		<msh:autoComplete size="150" property="${name}DoublePat"  vocName="patient" label="В какую персону переносить"/>
    	</msh:row>
    </msh:panel>
        <msh:row>
            <td colspan="6">
                <input type="button" value='OK'  onclick="javascript:save${name}DoublePatient()"/>
                <input type="button" value='Отменить' onclick='javascript:cancel${name}DoublePatient()'/>
            </td>
        </msh:row>
</form>

</div>
</div>

<script type='text/javascript' src='./dwr/interface/PatientService.js'></script>
<script type="text/javascript">
     var theIs${name}DoublePatientDialogInitialized = false ;
     var the${name}DoublePatientDialog = new msh.widget.Dialog($('${name}DoublePatientDialog')) ;
     // Показать
     function show${name}DoublePatient() {
         // устанавливается инициализация для диалогового окна
         if (!theIs${name}DoublePatientDialogInitialized) {
         	init${name}DoublePatientDialog() ;
          }
         the${name}DoublePatientDialog.show() ;
         $("${name}DoublePatName").focus() ;

     }

     // Отмена
     function cancel${name}DoublePatient() {
         the${name}DoublePatientDialog.hide() ;
     }

     // Сохранение данных
     function save${name}DoublePatient() {
     	if ($('${name}DoublePat').value==0) {
     		alert("Поле дата является обязательным") ;
     		$("${name}DoublePat").focus() ;
     	} else {
	     	PatientService.movePatientDoubleData(
	     		$('${name}DoublePat').value,'${param.id}'
	     		 ,{
	                   callback: function(aString) {
	                      alert("Данные перемещены!!!") ;
	                       window.document.location.reload();
	                    }
	                }
	         ) ;

         }
     }

     // инициализация диалогового окна
     function init${name}DoublePatientDialog() {
     	
     	theIs${name}DoublePatientDialogInitialized = true ;
     }
     
</script>