<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>
<%@ attribute name="title" required="true" description="Заголовок" %>
<%@ attribute name="table" required="true" description="Заголовок" %>
<%@ attribute name="addWhere" description="Заголовок" %>


<style type="text/css">
    #${name}DirMedServiceDialog {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>

<div id='${name}DirMedServiceDialog' class='dialog'>
    <h2>${title}</h2>
    <div class='rootPane'>
    
<form action="javascript:void(0)">
    <msh:panel>
    	<msh:row>
    		<td>
    		<span id="${name}DirMedServiceMainMenu"></span>
    		</td>
    	</msh:row>
    </msh:panel>
        <msh:row>
            <td colspan="6">
                <input type="button" value='OK'  onclick="javascript:save${name}DirMedService()"/>
                <input type="button" value='Отменить' onclick='javascript:cancel${name}DirMedService()'/>
            </td>
        </msh:row>
</form>

</div>
</div>

<script type='text/javascript' src='./dwr/interface/CategoryTreeService.js'></script>
<script type="text/javascript"><!--
     var theIs${name}DirMedServiceDialogInitialized = false ;
     var the${name}DirMedServiceDialog = new msh.widget.Dialog($('${name}DirMedServiceDialog')) ;
     // Показать
     function show${name}DirMedService() {
         // устанавливается инициализация для диалогового окна
         if (!theIs${name}DirMedServiceDialogInitialized) {
         	init${name}DirMedServiceDialog() ;
          }
         the${name}DirMedServiceDialog.show() ;
         $("${name}DirMedService").focus() ;

     }

     // Отмена
     function cancel${name}DirMedService() {
         the${name}DirMedServiceDialog.hide() ;
     }

     // Сохранение данных
     function save${name}DirMedService() {
    	 cancel${name}DirMedService() ;
     }

     // инициализация диалогового окна
     function init${name}DirMedServiceDialog() {
    	 get${name}Category("${name}DirMedServiceMainMenu",0) ;
     	theIs${name}DirMedServiceDialogInitialized = true ;
     }
     function get${name}Category(aDiv,aParent) {
    	 CategoryTreeService.getCategoryMedService('${name}DirMedService','get${name}Category', "PRICEPOSITION", 0, {
    		 callback: function() {
    			 
    		 }
    	 })
     }
</script>
