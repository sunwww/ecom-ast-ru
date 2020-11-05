<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@ attribute name="name" required="true" description="Название" %>
<%@ attribute name="field" required="true" description="Поле, куда сохранять" %>

<style type="text/css">
    #createParameter {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>

<div id='${name}CreateParameterDialog' class='dialog'>
    <h2 id="h2_title"></h2>
    <div class='rootPane'>
    
<form>
    <msh:panel>
        <msh:row><td align="left">
        	<input type='button' value='Да' onclick="set${name}YesNo('YES')">
        	</td><td align="right">
        	<input type='button' value='Нет' onclick="set${name}YesNo('NO')">
        	</td>
        </msh:row>
    </msh:panel>
</form>

</div>
</div>

<script type="text/javascript">
     var theIs${name}CreateParameterDialogInitialized = false ;
     var the${name}CreateParameterDialog = new msh.widget.Dialog($('${name}CreateParameterDialog')) ;
	var the${name}Id = "";
     // Показать
     function show${name}CreateType(aId, aTitle) {
    	 $('h2_title').innerHTML=""+aTitle;
    	 if (aId!=null) {
    		 the${name}Id=""+aId;
    	 }
         the${name}CreateParameterDialog.show() ;
       }

     // Отмена
     function cancel${name}CreateType() {
         the${name}CreateParameterDialog.hide() ;
     }

     // Сохранение данных
     function set${name}YesNo(arg) {
     	$('${field}'+the${name}Id).value=arg;
   
     	cancel${name}CreateType();
     	checkForm ();
     }

</script>
