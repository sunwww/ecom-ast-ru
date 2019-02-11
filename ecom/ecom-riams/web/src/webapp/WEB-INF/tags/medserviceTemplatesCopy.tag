<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="ecom" uri="http://www.ecom-ast.ru/tags/ecom" %>

<%@ attribute name="name" required="true" description="Название" %>


<script type="text/javascript" src="./dwr/interface/ContractService.js">/**/</script>
<div id='${name}CloseMsTemplDialog' class='dialog'>
    <h2>Выберите услугу, в которую скопировать шаблоны</h2>
    <table width="100%" cellspacing="10" cellpadding="10" id="diagnTable" border="1" >
    </table>
    <div>
        <form id="${name}">
            <msh:panel>
                <msh:row>
                    <msh:autoComplete property="ms${name}" fieldColSpan="7" horizontalFill="true" label="Мед. услуга" vocName="medServiceAll" size="100"/>
                </msh:row>
            </msh:panel>
        </form>
    </div>
    <br>
    <div>
        <table width="100%" cellspacing="10" cellpadding="10">
            <tr>
                <td align="center"><input type="button" value='Скопировать' onclick='javascript:copyTemplates${name}()'/></td>
                <td align="center"><input type="button"  style="font-weight:bold" id="${name}203" value='Закрыть' id="${name}Cancel" onclick='javascript:cancel${name}()'/></td>
            </tr>
        </table>
    </div>
    <div>
        <table width="100%" cellspacing="10" cellpadding="10">
            <tr>
            <tr>

            <tr><td></td></tr>
        </table>
    </div>
</div>
<script type="text/javascript">
    var ID;
    var theIs${name}CloseMsTemplDialogInitialized = false ;
    var the${name}CloseMsTemplDialog = new msh.widget.Dialog($('${name}CloseMsTemplDialog')) ;
    // Показать

    function show${name}(id) {
        ID=id;
        theTableArrow = null ;
        the${name}CloseMsTemplDialog.show() ;
    }
    // Отмена
    function cancel${name}() {
        the${name}CloseMsTemplDialog.hide() ;
    }
    //Копирование шаблонов
    function copyTemplates${name}() {
        ContractService.copyTemplatesToMedService(ID, $('ms${name}').value, {
                callback: function (res) {
                    showToastMessage(res,null,true);
                    window.open("entityParentView-mis_medService.do?id="+$('ms${name}').value);
                    the${name}CloseMsTemplDialog.hide();
                }});
    }
</script>