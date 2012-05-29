<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>
<%@ attribute name="roles" required="true" description="Роли" %>
<%@ attribute name="title" required="true" description="Заголовок" %>
<%@ attribute name="key" required="key" description="Горячие клавиши" %>
<%@ attribute name="confirm" required="false" description="Сообщение" %>
<%@ attribute name="date" required="true" description="Дата" %>
<%@ attribute name="number" required="true" description="Номер" %>

<msh:sideLink roles="${roles}" name="${title}" action=" javascript:show${name}RW('.do') " 
	key="${key}" />

<style type="text/css">
    #${name}RWDialog {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>

<div id='${name}RWDialog' class='dialog'>
    <h2>Дата</h2>
    <div class='rootPane'>
    
<form action="javascript:">
    <msh:panel>
    	<msh:row>
    		<msh:textField property="${name}Date" label="Дата"/>
    		<msh:textField property="${name}Number" label="Номер"/>
    	</msh:row>
    </msh:panel>
        <msh:row>
            <td colspan="6">
                <input type="button" value='OK'  onclick="javascript:save${name}RW()"/>
                <input type="button" value='Отменить' onclick='javascript:cancel${name}RW()'/>
            </td>
        </msh:row>
</form>

</div>
</div>

<script type="text/javascript"><!--
     var theIs${name}RWDialogInitialized = false ;
     var the${name}RWDialog = new msh.widget.Dialog($('${name}RWDialog')) ;
     // Показать
     function show${name}RW() {
         // устанавливается инициализация для диалогового окна
         if (!theIs${name}RWDialogInitialized) {
         	init${name}RWDialog() ;
          }
         the${name}RWDialog.show() ;
         $("${name}ReasonName").focus() ;

     }

     // Отмена
     function cancel${name}RW() {
         the${name}RWDialog.hide() ;
     }

     // Сохранение данных
     function save${name}RW() {
     	if ($('${name}Date').value==0) {
     		alert("Поле дата является обязательным") ;
     		$("${name}Date").focus() ;
     	} else if ($('${name}Number').value=="") {
     		alert("Поле номер является обязательным") ;
     		$("${name}Number").focus() ;
     	}  else {
	     	HospitalMedCaseService.setRW(
	     		'${param.id}',$('${name}Date').value, $('${name}Number').value
	     		 ,{
	                   callback: function(aString) {
	                       if ($('${date}')) $('${date}').value=$('${name}Date').value ;
	                       if ($('${date}ReadOnly')) $('${date}ReadOnly').value=$('${name}Date').value ;
	                       if ($('${number}')) $('${number}').value=$('${name}Number').value ;
	                       if ($('${number}ReadOnly')) $('${number}ReadOnly').value=$('${name}Number').value ;
	                       cancel${name}RW() ;
	                    }
	                }
	         ) ;

         }
     }

     // инициализация диалогового окна
     function init${name}RWDialog() {
     	HospitalMedCaseService.getRW(
     		'${param.id}', {
     			callback: function(aString) {
     				
     				//Date
					var cnt_s = aString.indexOf("#") ;
					if ($('${name}Date')) $('${name}Date').value = aString.substring(0,cnt_s) ;
					//number
     				if ($('${name}Number')) $('${name}Number').value =  aString.substring(cnt_s+1)  ;
     			}
     		}
     	);
     	new dateutil.DateField($('${name}Date')) ;
     	
     	theIs${name}RwDialogInitialized = true ;
     }
</script>