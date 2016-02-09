<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Имя" %>
<%@ attribute name="type" required="true" description="Группа" %>
<%@ attribute name="status" required="false" description="tttt" %>

<msh:ifInRole roles="${roles}">

<style type="text/css">
    #${name}ClaimStartDialog {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>

<div id='${name}ClaimStartDialog' class='dialog'>
    <h2>Печать документов</h2>
    <input type='hidden' id='${name}ClaimType' name='${name}ClaimType'>
    <input type='hidden' id='${name}ClaimId' name='${name}ClaimId'>
    <input type='hidden' id='${name}ClaimStatus' name='${name}ClaimStatus'>
     <div class='rootPane'>
    
<form action="javascript:void(0)">
    <msh:panel>
   
    	    <table id = 'tableDocuments'>
	</table>
    </msh:panel>
        <msh:row>
            <td colspan="6">
                <input type="button" value='Закрыть' onclick='javascript:cancel${name}ClaimStart()'/>
            </td>
        </msh:row>
</form>

</div>
</div>

<script type="text/javascript"><!--
     var theIs${name}ClaimStartDialogInitialized = false ;
     var claimId =0;
     var the${name}ClaimStartDialog = new msh.widget.Dialog($('${name}ClaimStartDialog')) ;
     // Показать
     function show${name}ClaimStart() {
         // устанавливается инициализация для диалогового окна
       //  if (!theIs${name}ClaimStartDialogInitialized) {
         	init${name}ClaimStartDialog() ;
        //  }
         
       

     }

     // Отмена
     function cancel${name}ClaimStart() {
         the${name}ClaimStartDialog.hide() ;
     }

    function goPrint(aFileName, aId) {
    	var url = "print-"+aFileName+".do?id="+aId+"&s=PatientPrintService&m=printInfo";
    	
    	window.location=url;
    }
     
         // инициализация диалогового окна
     function init${name}ClaimStartDialog() {
    	 PatientService.getUserDocumentList('${type}', {
      		callback: function (a) {
      			if (a!=null&&a!='') {
      				 var tbody = document.getElementById('tableDocuments');
      				 tbody.innerHTML='';
      				var rows = a.split("#");
      				for (var i=0;i<rows.length;i++) {
      					var radio='';
      					var row = document.createElement("TR");
 						var param = rows[i].split(":");
 						var tId = ""+param[0];
 						var tName = ""+param[1];
 						var tFileName = ""+param[2]; 
 						//var checkbox = "<td id='tdDocument"+i+"'  onclick='this.childNodes[0].checked=\"checked\";'>";
 						radio +="<input type='hidden' id = 'document"+i+"' name='document' value='"+tFileName+"' onclick=''>"+tName+"</td>";
 						radio +="<td> <input type='button' value = 'Печать' onclick = \"goPrint('"+tFileName+"','"+${param.id}+"')\" /></td>";
 						tbody.appendChild(row);
 						row.innerHTML=radio;
      				}
      				 //theIs${name}ClaimStartDialogInitialized = true ;
      				the${name}ClaimStartDialog.show() ;
      			} else {
      				//theIs${name}ClaimStartDialogInitialized = false ;
      				cancel${name}ClaimStart();
      				alert ('Не найдено документов в группе ${type}');
      				return;
      			}
      			
      		}
      	});
     	
    	
     	 

     }
</script>
</msh:ifInRole>