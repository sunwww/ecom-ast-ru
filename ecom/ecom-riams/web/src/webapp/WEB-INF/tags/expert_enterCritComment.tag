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
    <textarea id="commentText" name="commentText"  rows="5" > </textarea><br>
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
     var mark ;
     var the${name}CritCommentDialog = new msh.widget.Dialog($('${name}CritCommentDialog')) ;
   
     function save${name}CritComment() {
    	 var txt = $('commentText').value;
    	 if (txt.trim()==""){
    		 alert ("Введите текст");
    		 return;
    	 }
    //	 if ($("criterion"+mark+"Comment")){
    		 $(mark+"Comment").value = txt.trim();
    		 updateCriterions() ;
    		 
    //	 }
    	 the${name}CritCommentDialog.hide() ;
     }
     
     
     // Отмена
     function cancel${name}CritComment() {
    	 if ($(mark)){
    		 $(mark).value="";
        	 $(mark+'Name').value=""; 
    	 }
    	 
         the${name}CritCommentDialog.hide() ;
     }

     // инициализация диалогового окна
     function show${name}CritComment(aMark) {
    	 mark = aMark;
         the${name}CritCommentDialog.show() ;
     }
</script>
</msh:ifInRole>