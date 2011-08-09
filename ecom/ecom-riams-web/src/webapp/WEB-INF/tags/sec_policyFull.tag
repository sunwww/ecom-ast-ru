<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>
<%@ attribute name="roles" required="true" description="Роли" %>
<%@ attribute name="title" required="true" description="Заголовок" %>
<%@ attribute name="key" required="true" description="Горячие клавиши" %>

<msh:sideLink roles="${roles}" name="${title}" action=" javascript:show${name}Policy('.do') " 
	key="${key}" />

<style type="text/css">
    #${name}PolicyDialog {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>

<div id='${name}PolicyDialog' class='dialog'>
    <h2>Дата</h2>
    <div class='rootPane'>
    
<form action="javascript:">
    <msh:panel>
    	<msh:row>
    		<msh:textField property="${name}Policy" label="Политика" size="120"/>
    	</msh:row>
     	<msh:row>
    		<msh:textField property="${name}Name" label="Название" size="120"/>
    	</msh:row>
    </msh:panel>
        <msh:row>
            <td colspan="6">
                <input type="button" value='OK'  onclick="javascript:save${name}Policy()"/>
                <input type="button" value='Отменить' onclick='javascript:cancel${name}Policy()'/>
            </td>
        </msh:row>
</form>

</div>
</div>

<script type='text/javascript' src='./dwr/interface/RolePoliciesService.js'></script>
<script type="text/javascript"><!--
     var theIs${name}PolicyDialogInitialized = false ;
     var the${name}PolicyDialog = new msh.widget.Dialog($('${name}PolicyDialog')) ;
     // Показать
     function show${name}Policy() {
         // устанавливается инициализация для диалогового окна
         if (!theIs${name}PolicyDialogInitialized) {
         	init${name}PolicyDialog() ;
          }
         the${name}PolicyDialog.show() ;
         $("${name}Policy").focus() ;

     }

     // Отмена
     function cancel${name}Policy() {
         the${name}PolicyDialog.hide() ;
     }

     // Сохранение данных
     function save${name}Policy() {
     	if ($('${name}Policy').value=="") {
     		alert("Поле политика является обязательным") ;
     		$("${name}Policy").focus() ;
     	//} else if ($('${name}Name').value=="") {
     		//alert("Поле название является обязательным") ;
     		//$("${name}Name").focus() ;
     	}  else {
	     	RolePoliciesService.addPolicy(
	     		$('${name}Policy').value, $('${name}Name').value
	     		 ,{
	                   callback: function(aString) {
	                   		window.location.href='entityParentView-secpolicy.do?id='+aString ;
	                    }
	                }
	         ) ;

         }
     }

     // инициализация диалогового окна
     function init${name}PolicyDialog() {

     	
     	theIs${name}PolicyDialogInitialized = true ;
     }
</script>