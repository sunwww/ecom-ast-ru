<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>
<%@ attribute name="username" required="true" description="Пользователь" %>

<style type="text/css">
    #${name}fssSign {
        visibility: hidden;
        display: none;
        position: absolute;
    }
</style>

<div id='${name}fssSign' class='dialog'>
    <h2>Подписание документа нетрудоспособности</h2>
    <div class='rootPane'>
        <h3>Подписание документа нетрудоспособности</h3>
        <form action="javascript:void(0)" id="frmFond" name="frmFond">
            <msh:panel>
                <msh:row>
                    <span id='${name}fssSignText'/>
                </msh:row>
            </msh:panel>
            <msh:row>
                <td colspan="6">
                    <br>
                    <input type="button" value='Закрыть окно' onclick='javascript:cancel${name}fssSign()'/>
                </td>
            </msh:row>
        </form>
    </div>
</div>

<script type='text/javascript' src='./dwr/interface/DisabilityService.js'></script>
<script type="text/javascript">


    var theIs${name}fssSignInitialized = false;
    var the${name}fssSign = new msh.widget.Dialog($('${name}fssSign'));


    // Показать
    function show${name}fssSign(recordid, doctype) {
        the${name}fssSign.show();
        $('${name}fssSignText')
            .innerHTML = "<iframe src='/riams/api/disabilitySign/sendDisabilityRecordJson?disRecId=" + recordid + "&docType=" + doctype + "&username=${username}' width='600' height='500' ></iframe>";
    }

    // Отмена
    function cancel${name}fssSign() {
        the${name}fssSign.hide();
        msh.effect.FadeEffect.pushFadeAll();

        jQuery("#prolongation").load("entityParentView-dis_document.do?id=${param.id} #prolongation");
    }

</script>