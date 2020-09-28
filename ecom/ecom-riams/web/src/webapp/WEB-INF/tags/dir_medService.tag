<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>
<%@ attribute name="title" required="true" description="Заголовок" %>
<%@ attribute name="table" required="true" description="Заголовок" %>
<%@ attribute name="addParam" description="Заголовок" %>
<%@ attribute name="functionAdd" description="Функция для добавления" %>
<%@ attribute name="clearDirParam" description="Функция очистки" %>


<style type="text/css">
    #${name}DirMedServiceDialog {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
    .dir0Div {background-color: #ddddff; }
    .dir1Div {background-color: #88ffaa;}
    .dir2Div {background-color: #ffddbb;}
    .dir3Div {background-color: #bbff55;}
    .dir4Div {background-color: #ddff77;}
    .dir5Div {background-color: #ffffff;}
    .dirV {border-bottom: 1px solid; }
    .dirN {background-color: white;border-bottom: 0;}
    
</style>

<div id='${name}DirMedServiceDialog' class='dialog'>
    <h2>${title}. <a href='javascript:void(0)' onclick='save${name}DirMedService()'>Закрыть</a></h2>
    <div class='rootPane'>
    <input type="hidden" name='${name}IsViewButton' id='${name}IsViewButton' value=''/>
    <input type="hidden" name='${name}ListIds' id='${name}ListIds' value=''/>
<form action="javascript:void(0)">
    <msh:panel>
    	<msh:row>
    		<td>
    		<span style="font-size: 18px" id="${name}DirMedServiceMainMenu"></span>
    		</td>
    	</msh:row>
    </msh:panel>
        <msh:row>
            <td colspan="6">
                <input type="button" value='ОК'  onclick="javascript:save${name}DirMedService()"/>
            </td>
        </msh:row>
</form>

</div>
</div>

<script type='text/javascript' src='./dwr/interface/CategoryTreeService.js'></script>
<script type="text/javascript">
     var theIs${name}DirMedServiceDialogInitialized = false ;
     var theIs${name}DirMedServiceAddParam = 0 ;
     var the${name}DirMedServiceDialog = new msh.widget.Dialog($('${name}DirMedServiceDialog')) ;
     // Показать
     function show${name}DirMedService() {
         // устанавливается инициализация для диалогового окна
         if (!theIs${name}DirMedServiceDialogInitialized) {
        	var idAddParam = +$('${addParam}').value ;
        	theIs${name}DirMedServiceAddParam = idAddParam;
         	init${name}DirMedServiceDialog() ;
          } else {
         var idAddParam = '' ;
			if ('${addParam}'!='') {
				idAddParam = +$('${addParam}').value ;
				if (theIs${name}DirMedServiceAddParam!=idAddParam) {
					try {eval('${clearDirParam}') ;} catch(e){}
					theIs${name}DirMedServiceDialogInitialized = false;
					show${name}DirMedService() ;
					return ;
				}
				
			}
          }
         the${name}DirMedServiceDialog.show() ;
         

     }

     // Отмена
     function cancel${name}DirMedService() {
         the${name}DirMedServiceDialog.hide() ;
         msh.effect.FadeEffect.pushFadeAll();
     }

     // Сохранение данных
     function save${name}DirMedService() {
    	 cancel${name}DirMedService() ;
     }

     // инициализация диалогового окна
     function init${name}DirMedServiceDialog() {
    	 $("${name}DirMedServiceMainMenu").innerHTML='' ;
    	 get${name}Category("${name}DirMedServiceMainMenu",0,0) ;
     	 
     }
     function clear${name}DirMedServiceDialog() {
    	 $("${name}DirMedServiceMainMenu").innerHTML="" ;
    	 theIs${name}DirMedServiceDialogInitialized = false
     }
     function get${name}CategoryAdd(aId,aName) {
    	 if ('${functionAdd}'!='') {
    		${functionAdd}(aId,aName);
    		$('${name}DirMedService'+aId+'m').style.color='blue' ;
    	 }
     }
     
     function get${name}Category(aDiv,aParent,aLevel,aAddParam,aViewButton,aListIdCheck) {
   		if ($(aDiv).innerHTML=="") {
   			
    		 CategoryTreeService.getCategoryMedService('${name}DirMedService','get${name}Category', '${table}', +aParent,+aLevel
    				 ,theIs${name}DirMedServiceAddParam ,$('${name}IsViewButton').value==""?null:$('${name}IsViewButton').value
    						 ,$('${name}ListIds').value==""?null:$('${name}ListIds').value, {
	    		 callback: function(aResult) {
	    			 //the${name}DirMedServiceDialog.hide() ;
	    			 $(aDiv).innerHTML = aResult ; 
	    			 if (+aParent>0) {
	    				 $(aDiv+"V").innerHTML="-" ;
	    				 
	    			 } else {
	    				 the${name}DirMedServiceDialog.hide() ;
	    				 the${name}DirMedServiceDialog.show() ;
	    			 }
	    			 theIs${name}DirMedServiceDialogInitialized = true ;
	    			 //the${name}DirMedServiceDialog.show() ;
	    			 
	    		 }
	    	 }) ;
   		} else {
   			
   			if ($(aDiv+"V").innerHTML==" + ") {
   				$(aDiv+"").style.display = "inline" ;
   				$(aDiv+"V").innerHTML=" - " ;
   			} else {
   				$(aDiv+"").style.display = "none" ;
   				$(aDiv+"V").innerHTML=" + " ;
   			}
   			
		}
     }
</script>
