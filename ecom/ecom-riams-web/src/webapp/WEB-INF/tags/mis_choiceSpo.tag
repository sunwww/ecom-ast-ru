<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>
<%@ attribute name="roles" required="true" description="Роли" %>
<%@ attribute name="title" required="true" description="Заголовок" %>
<%@ attribute name="service" required="true" description="Сервис" %>
<%@ attribute name="method" required="true" description="Сервис" %>
<%@ attribute name="methodGetPatientByPatient" required="true" description="Сервис" %>
<%@ attribute name="hiddenNewSpo" required="true" description="Сервис" %>

<msh:ifInRole roles="${roles}">
<msh:sideLink name="${title}" action=" javascript:show${name}ChoiceSpo('.do') " 
	 />

<style type="text/css">
    #${name}ChoiceSpoDialog {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>

<div id='${name}ChoiceSpoDialog' class='dialog'>
    <h2>${title}</h2>
    <div class='rootPane'>
    
<form action="javascript:void(0)" id="${name}frmSpo" name="${name}frmSpo">
    <msh:panel>
    	<msh:row>
    		<td colspan="6">
    			<div id="${name}ChoiceSpoLoadDiv">Загрузка данных...</div>
    		</td>
    	</msh:row>
    </msh:panel>
        <msh:row>
            <td colspan="6">
                <input type="button" value='Перенести в выбранный СПО'  onclick="javascript:save${name}ChoiceSpo(1)"/>
                <input id="${name}InNewSpo" name="${name}InNewSpo" type="button" value='Перенести в новый СПО'  onclick="javascript:save${name}ChoiceSpo(0)"/>
                <input type="button" value='Отменить' onclick='javascript:cancel${name}ChoiceSpo()'/>
            </td>
        </msh:row>
</form>

</div>
</div>

<script type="text/javascript"><!--
     var theIs${name}ChoiceSpoDialogInitialized = false ;
     var the${name}ChoiceSpoDialog = new msh.widget.Dialog($('${name}ChoiceSpoDialog')) ;
     // Показать
     function show${name}ChoiceSpo() {
         // устанавливается инициализация для диалогового окна
         if (!theIs${name}ChoiceSpoDialogInitialized) {
        	 the${name}ChoiceSpoDialog.show() ;
        	 if (+'${hiddenNewSpo}'>0) $('${name}InNewSpo').style.display='none' ;
        	 ${service}.${methodGetPatientByPatient} (
              		'${param.id}', {
              			callback: function(aString) {
              				the${name}ChoiceSpoDialog.hide() ;
              				$('${name}ChoiceSpoLoadDiv').innerHTML=aString ;
              				the${name}ChoiceSpoDialog.show() ;
              			}
              		}
              	);
         	init${name}ChoiceSpoDialog() ;
          }
         the${name}ChoiceSpoDialog.show() ;
    
     }

     // Отмена
     function cancel${name}ChoiceSpo() {
    	 the${name}ChoiceSpoDialog.hide() ;
     }

     // Сохранение данных
     function save${name}ChoiceSpo(aNewSpo) {
     	if (+aNewSpo<1 && !confirm('Вы хотите перенести визит в новый СПО?')) {
     		return ;
     	}  else {
     		if (confirm('Вы хотите точно хотите продолжить?')) {
     			var newSpo = null ;
     			if (+aNewSpo>0) {
     				var frm = document.forms['${name}frmSpo'] ;
         	    	newSpo = getCheckedRadio(frm,"rbSpo");
         	    	if (+newSpo<1) {
         	    		alert('Необходимо выбрать СПО!!!') ;
         	    		return ;
         	    	}
     			}
         		${service}.${method}(
             		'${param.id}', newSpo, {
             			callback: function(aString) {
             				var info=aString.split('#') ;
             				alert(info[1]) ;
             				window.location='entitySubclassView-mis_medCase.do?id='+info[0] ;
             			}
             		}
             	);
     		}
         }
     }

     // инициализация диалогового окна
     function init${name}ChoiceSpoDialog() {
     	theIs${name}ChoiceSpoDialogInitialized = true ;
     }
</script>
</msh:ifInRole>