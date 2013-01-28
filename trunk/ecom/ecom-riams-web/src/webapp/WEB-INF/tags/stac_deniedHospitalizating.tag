<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>
<%@ attribute name="roles" required="true" description="Роли" %>
<%@ attribute name="title" required="true" description="Заголовок" %>

<msh:ifInRole roles="${roles}">
<msh:sideLink name="${title}" action=" javascript:show${name}DeniedHospitalizating('.do') " 
	 />

<style type="text/css">
    #${name}DeniedHospitalizatingDialog {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>

<div id='${name}DeniedHospitalizatingDialog' class='dialog'>
    <h2>Оформить отказ от госпитализации</h2>
    <div class='rootPane'>
    
<form action="javascript:">
    <msh:panel>
    	<msh:row>
    		<msh:autoComplete property="${name}DeniedHospitalizating" label="Отказ от госпитализации" size="100" vocName="vocDeniedHospitalizating"/>
    	</msh:row>
    </msh:panel>
        <msh:row>
            <td colspan="6">
                <input type="button" value='OK'  onclick="javascript:save${name}DeniedHospitalizating()"/>
                <input type="button" value='Отменить' onclick='javascript:cancel${name}DeniedHospitalizating()'/>
            </td>
        </msh:row>
</form>

</div>
</div>

<script type="text/javascript"><!--
     var theIs${name}DeniedHospitalizatingDialogInitialized = false ;
     var the${name}DeniedHospitalizatingDialog = new msh.widget.Dialog($('${name}DeniedHospitalizatingDialog')) ;
     // Показать
     function show${name}DeniedHospitalizating() {
         // устанавливается инициализация для диалогового окна
         if (!theIs${name}DeniedHospitalizatingDialogInitialized) {
         	init${name}DeniedHospitalizatingDialog() ;
          }
         the${name}DeniedHospitalizatingDialog.show() ;
         $("${name}DeniedHospitalizatingName").focus() ;

     }

     // Отмена
     function cancel${name}DeniedHospitalizating() {
         the${name}DeniedHospitalizatingDialog.hide() ;
     }

     // Сохранение данных
     function save${name}DeniedHospitalizating() {
     	if (+$('${name}DeniedHospitalizating').value==0) {
     		alert("Поле дата является обязательным") ;
     		$("${name}DeniedHospitalizatingName").focus() ;
     	}  else {
         	HospitalMedCaseService.deniedHospitalizatingSls(
             		'${param.id}', $('${name}DeniedHospitalizating').value, {
             			callback: function(aString) {
             				alert(aString) ;
             				window.location.reload() ;
             			}
             		}
             	);
         }
     }

     // инициализация диалогового окна
     function init${name}DeniedHospitalizatingDialog() {
     	theIs${name}DeniedHospitalizatingDialogInitialized = true ;
     }
</script>
</msh:ifInRole>