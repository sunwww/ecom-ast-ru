<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="ecom" uri="http://www.ecom-ast.ru/tags/ecom" %>

<%@ attribute name="name" required="true" description="Название" %>


<script type="text/javascript" src="./dwr/interface/ContractService.js">/**/</script>
<div id='${name}CloseMsTemplDialog' class='dialog'>
    <h2>Выберите услугу, в которую скопировать</h2>
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
                <td align="center"><input type="button" value='Скопировать' onclick='javascript:copy${name}()'/></td>
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
    //par=1 - шаблоны
    //par=2 - прикрепления к рабочим ф-ям
    //par=3 - полный дубль
    var par${name};
    var theIs${name}CloseMsTemplDialogInitialized = false ;
    var the${name}CloseMsTemplDialog = new msh.widget.Dialog($('${name}CloseMsTemplDialog')) ;
    // Показать

    function show${name}(id,par) {
        ID=id;
        par${name}=par;
        theTableArrow = null ;
        if (par${name}!=3)
            the${name}CloseMsTemplDialog.show() ;
        else
            copyDouble${name}();
    }

    // Отмена
    function cancel${name}() {
        the${name}CloseMsTemplDialog.hide() ;
    }

    //Создание дубля
    function copyDouble${name}() {
        ContractService.copyDoubleMedService(ID, {
            callback: function (resId) {
                if (resId!=-1) {
                    showToastMessage('Дубль услуги создан',null,true,false,2000);
                    window.location.href="entityParentEdit-mis_medService.do?id="+resId;
                }
            }});
    }

    //Копирование шаблонов
    function copyTemplates${name}() {
        ContractService.copyTemplatesToMedService(ID, $('ms${name}').value, {
                callback: function (res) {
                    showToastMessage(res,null,true,false,2000);
                    window.location.href="entityParentView-mis_medService.do?id="+$('ms${name}').value;
                    the${name}CloseMsTemplDialog.hide();
                }});
    }

    //Копирование прикреплений
    function copyWFuncs${name}() {
        ContractService.copyWFuncsToMedService(ID, $('ms${name}').value, {
            callback: function (res) {
                showToastMessage(res,null,true,false,2000);
                window.location.href="entityParentView-mis_medService.do?id="+$('ms${name}').value;
                the${name}CloseMsTemplDialog.hide();
            }});
    }

    //копирование
    function copy${name}() {
        if (par${name}==1)
            copyTemplates${name}();
        else if (par${name}==2)
            copyWFuncs${name}();
    }
</script>