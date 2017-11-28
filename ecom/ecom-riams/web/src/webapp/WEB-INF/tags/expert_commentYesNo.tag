<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>


<style type="text/css">
    #CloseDisDocument {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>
<div id='${name}CommentYesNoDialog' class='dialog'>
    <h2>Введите пояснение изменению значения оценки</h2>
    <div class='rootPane'>
        <form action="javascript:void(0)">
            <msh:row>
                <textarea rows="8" cols="35" class="area" required id="reasonYesNo"></textarea>
            </msh:row>
            <msh:row>
            <table width="100%">
                <tr><td></td></tr>
                <tr>
                <td align="center"><input type="button" value='Сохранить' onclick='javascript:save${name}CommentYesNo()'/></td>
                <td align="center"><input type="button" value='Отмена' onclick='javascript:cancel${name}CommentYesNo()'/></td>
                </tr>
                <tr><td></td></tr>
            </table>
            </msh:row>
        </form>
    </div>
</div>
<script type="text/javascript">
    var the${name}Mark ;
    var the${name}Comment;
    var  the${name}Ii;
    var the${name}Type;
    var the${name}CommentYesNoDialog = new msh.widget.Dialog($('${name}CommentYesNoDialog')) ;

    function save${name}CommentYesNo() {
        the${name}Comment=document.getElementById("reasonYesNo");
        if (the${name}Comment!=null && the${name}Comment.value!='') {
        the${name}CommentYesNoDialog.hide();
        if (the${name}Type=='BranchManager') {
            $('criterion'+the${name}Ii+'CommentYesNo').value=the${name}Comment.value+";;";
        }
        else if (the${name}Type=='Expert') {
            $('criterion'+the${name}Ii+'CommentYesNo').value=";"+the${name}Comment.value+";";
        }
        else if (the${name}Type=='Coeur') {
            $('criterion'+the${name}Ii+'CommentYesNo').value=";;"+the${name}Comment.value;
        }
//alert( $('criterion'+the${name}Ii+'CommentYesNo').value);
        updateCriterions();
        }
        else alert('Введите пояснение изменению значения оценки!');
    }

    // Отмена
    function cancel${name}CommentYesNo() {
        the${name}CommentYesNoDialog.hide() ;
        $('criterion'+the${name}Ii+'Name').value='';
    }

    // инициализация диалогового окна
    function show${name}CommentYesNo(comment, mark,i,type) {
        the${name}Mark=mark;
        the${name}Comment=comment;
        the${name}Ii=i;
        the${name}Type=type;
        the${name}CommentYesNoDialog.show() ;
        document.getElementById('reasonYesNo').value=the${name}Comment;
    }
</script>
</script>