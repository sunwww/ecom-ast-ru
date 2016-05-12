<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>
<%@ attribute name="roles" required="true" description="Роли" %>
<%@ attribute name="title" required="true" description="Заголовок" %>
<%@ attribute name="field" required="true" description="Название поля" %>

<msh:ifInRole roles="${roles}">
<msh:sideLink name="${title}" action=" javascript:show${name}NewDiary('.do') " 
	 />

<style type="text/css">
    #${name}NewDiaryDialog {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>

<div id='${name}NewDiaryDialog' class='dialog'>
    <h2>Дата</h2>
    <div class='rootPane'>
    
<form action="javascript:void(0)">
    <msh:panel>
    	<msh:row>
    		<msh:textField property="${name}TitleDiary" label="Заголовок" fieldColSpan="5" horizontalFill="true"/>
    	</msh:row>
    	<msh:row>
    		<msh:textArea property="${name}DataDiary" label="Текст" fieldColSpan="5" />
    	</msh:row>
        <msh:row>
            <td colspan="6">
                <input type="button" value='OK'  onclick="javascript:save${name}NewDiary()"/>
                <input type="button" value='Отменить' onclick='javascript:cancel${name}NewDiary()'/>
            </td>
        </msh:row>
     </msh:panel>
</form>

</div>
</div>
<script type='text/javascript' src='./dwr/interface/HospitalMedCaseService.js'></script>
<script type="text/javascript">
     var theIs${name}NewDiaryDialogInitialized = false ;
     var the${name}NewDiaryDialog = new msh.widget.Dialog($('${name}NewDiaryDialog')) ;
     // Показать
     function show${name}NewDiary() {
         // устанавливается инициализация для диалогового окна
         if (!theIs${name}NewDiaryDialogInitialized) {
         	init${name}NewDiaryDialog() ;
          }
         the${name}NewDiaryDialog.show() ;
         $("${name}TitleDiary").focus() ;
         $('${name}DataDiary').value=$('${field}').value
     }

     // Отмена
     function cancel${name}NewDiary() {
         the${name}NewDiaryDialog.hide() ;
     }

     // Сохранение данных
     function save${name}NewDiary() {
     	if ($('${name}TitleDiary').value==0) {
     		alert("Поле дата является обязательным") ;
     		$("$${name}TitleDiary").focus() ;
     	} else if ($('${name}DataDiary').value=="") {
     		alert("Поле номер является обязательным") ;
     		$("${name}DataDiary").focus() ;
     	}  else {
	     	HospitalMedCaseService.createNewDiary(
	     		$('${name}TitleDiary').value, $('${name}DataDiary').value
	     		 ,{
	                   callback: function(aString) {
	                       alert(aString) ;
	                	   cancel${name}NewDiary() ;
	                    }
	                }
	         ) ;

         }
     }

     // инициализация диалогового окна
     function init${name}NewDiaryDialog() {
     	theIs${name}NewDiaryDialogInitialized = true ;
     }
     
</script>
</msh:ifInRole>