<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Имя" %>

<msh:ifInRole roles="${roles}">

<style type="text/css">
    #${name}ProtocolTemplateDialog {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>

<div id='${name}ProtocolTemplateDialog' class='dialog'>
    <h2>Печать документов</h2>
     <div class='rootPane'>
    
<form action="javascript:void(0)">
    <msh:panel>
   
    	    <table id = 'tableDocuments'>
	</table>
    </msh:panel>
        <msh:row>
            <td colspan="6">
                <input type="button" value='Закрыть' onclick='javascript:cancel${name}ProtocolTemplate()'/>
            </td>
        </msh:row>
</form>

</div>
</div>
<script type='text/javascript' src='./dwr/interface/TemplateProtocolService.js'></script>
<script type="text/javascript"><!--
     var theIs${name}ProtocolTemplateDialogInitialized = false ;
     var claimId =0;
     var the${name}ProtocolTemplateDialog = new msh.widget.Dialog($('${name}ProtocolTemplateDialog')) ;
     // Показать
     function show${name}ProtocolTemplate() {
         // устанавливается инициализация для диалогового окна
       //  if (!theIs${name}ProtocolTemplateDialogInitialized) {
         	init${name}ProtocolTemplateDialog() ;
        //  }
         
       

     }

     // Отмена
     function cancel${name}ProtocolTemplate() {
         the${name}ProtocolTemplateDialog.hide() ;
     }

    function goPrint(aFileName, aId) {
    	var url = "print-"+aFileName+".do?id="+aId+"&s=HospitalPrintService&m=printProtocolTemplate";
    	
    	window.location=url;
    }
     
         // инициализация диалогового окна
     function init${name}ProtocolTemplateDialog() {
    	 TemplateProtocolService.getProtocolTemplatesPrintForms($('id').value, {
      		callback: function (a) {
      			if (a!=null&&a!='') {
      				 var tbody = document.getElementById('tableDocuments');
      				 tbody.innerHTML='';
      				var rows = a.split("#");
      				var radio='';
  					
      				for (var i=0;i<rows.length;i++) {
      					var row = document.createElement("TR");
      					radio = '';
 						var param = rows[i].split("@");
 						var tId = ""+param[0];
 						var tName = ""+param[1];
 						var tFileName = ""+param[2]; 
 						//var checkbox = "<td id='tdDocument"+i+"'  onclick='this.childNodes[0].checked=\"checked\";'>";
 						radio +="<input type='hidden' id = 'document"+i+"' name='document' value='"+tFileName+"' onclick=''>"+tName+"</td>";
 						radio +="<td> <input type='button' value = 'Печать' onclick = \"goPrint('"+tFileName+"','"+${param.id}+"')\" /></td>";
 						
 						tbody.appendChild(row);
 						row.innerHTML=radio;
      				}
      				// Печать шаблона по умолчанию 
      				var row = document.createElement("TR");
      				radio = "<td colspan='2'> <input type='button' value = 'Печать протокола по умолчанию' onclick = \"printProtocol()\" /></td>";
      				tbody.appendChild(row);
      				row.innerHTML=radio;
      				 //theIs${name}ProtocolTemplateDialogInitialized = true ;
      				the${name}ProtocolTemplateDialog.show() ;
      			} else {
      				printProtocol();
      				//theIs${name}ProtocolTemplateDialogInitialized = false ;
      				cancel${name}ProtocolTemplate();
      				//alert ('Не найдено документов в группе ');
      			}
      			
      		}
      	});
     	
    	
     	 

     }
</script>
</msh:ifInRole>