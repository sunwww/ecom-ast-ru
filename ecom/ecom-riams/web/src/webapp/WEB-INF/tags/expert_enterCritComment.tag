<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Имя" %>

<msh:ifInRole roles="${roles}">

<style type="text/css">
    #${name}CritCommentDialog {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>

<div id='${name}CritCommentDialog' class='dialog'>
    <h2>Введите примечание для оценки</h2>
     <div class='rootPane'>
    
<form action="javascript:void(0)">
    <msh:row>
    <td>
    	<div id='defectDiv' style="display: block">
    	<table id="defect${name}Tbl"  >

    	</table>
    	</div>
    </td>
    </msh:row>
        <msh:row>

            <td colspan="6">
                <input type="button" value='Сохранить' onclick='javascript:save${name}CritComment()'/>
                <input type="button" value='Отмена' onclick='javascript:cancel${name}CritComment()'/>
            </td>
            
        </msh:row>
</form>

</div>
</div>

<script type="text/javascript">
     var the${name}Mark ;
     var the${name}MarkComment;
     var the${name}CritCommentDialog = new msh.widget.Dialog($('${name}CritCommentDialog')) ;
   
     function save${name}CritComment() {
    	 var ${name}Defects = "";
    	 var cntChecked =0;
    	 var rows = $('defect${name}Tbl').children;
    	 for (var i=0;i<rows.length;i++){
    		 var chk = rows[i].children[0].children[0].children[0].children[0];
    		// alert (chk);

    		 if (chk.checked==true) {
    			 cntChecked++;
    			 if (${name}Defects.length>0){
    				 ${name}Defects+=",";
    			 }
    				 ${name}Defects+=chk.id;
    		 }

    		 $(the${name}Mark+'Comment').value = ${name}Defects;
    	 }
    	 if (cntChecked==0) {
			 alert ("Выберите причину снижения оценки!");
			 return;
		 }
    	 the${name}CritCommentDialog.hide() ;
    	 updateCriterions();
     }
     
     
     // Отмена
     function cancel${name}CritComment() {
    	 if ($(the${name}Mark)){
    		 $(the${name}Mark).value="";
        	 $(the${name}Mark+'Name').value=""; 
    	 }    	 
         the${name}CritCommentDialog.hide() ;
     }

     // инициализация диалогового окна
     function show${name}CritComment(res, mark) {
    	 the${name}Mark=mark;
    			 if (""+res!=""){
    					 cleanDiv();
    					 var rows = res.split("#");
    					 for (var i=0;i<rows.length;i++){
    						 createRow(rows[i],i);
    					 }
    					 showDiv();
    				 }
    			 
    		 
    	 
    	 
         the${name}CritCommentDialog.show() ;
     }
     function cleanDiv(){
    	 $('defectDiv').innerHTML="";
    	 $('defectDiv').innerHTML="<table id=\"defect${name}Tbl\"></table>";
 		
     	
     }

     function showDiv(){
    	 $('defectDiv').style.display="block";
     }
     function createRow(aData,cnt){
    	 var tds = aData.split(":");
    	 var row = "<tr><td>"
    	 row +="<label><input type='checkbox' name='chkDefect' id='"+tds[0]+"' value='"+tds[1]+"'";
    	 
    	 if ($(the${name}Mark+'Comment').value!="") {
    		 var defs = $(the${name}Mark+'Comment').value.split (",");
    		 for (var i=0;i<defs.length;i++) {
    			 if (+(""+defs[i].trim())==+tds[0]) {
    				 row +=" checked='checked'";
    			 }
    		 }
    	 }
    	 row+=">"+tds[1]+"</label><br></td></tr>";
    	// alert("el"+$("defect${name}Tbl"));
    	 $('defect${name}Tbl').innerHTML +=row;
    		 
     }
</script>
</msh:ifInRole>