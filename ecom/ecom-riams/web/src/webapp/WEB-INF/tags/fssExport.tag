<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>
<%@ attribute name="documentId" required="true" description="Поле Id документа" %>

<style type="text/css">
    #${name}fssExport {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>

<div id='${name}fssExport' class='dialog'>
    <h2>Подписание документа нетрудоспособности</h2>
    <div class='rootPane'>
        <h3>Подписание документа нетрудоспособности</h3>
        <form action="javascript:void(0)" id="frmFond" name="frmFond">
            <msh:panel>
                <msh:row>
                    <span id='${name}fssExportText'/>
                </msh:row>
            </msh:panel>
            <msh:row>
                <td colspan="6">
                    <br>
                    <input type="button" value='Закрыть окно' onclick='javascript:cancel${name}fssExport()'/>
                </td>
            </msh:row>
        </form>
    </div>
</div>

<script type='text/javascript' src='./dwr/interface/DisabilityService.js'></script>
<script type="text/javascript">


    var theIs${name}fssExportInitialized = false ;
    var the${name}fssExport = new msh.widget.Dialog($('${name}fssExport')) ;


    // Показать
    function show${name}fssExport(recordid,doctype) {
        the${name}fssExport.show();
        //TODO LINK
        $('${name}fssExportText')
            .innerHTML="<iframe src='http://192.168.2.45:8800/riams/api/disabilitySign/sendDisabilityRecordJson?disRecId="+recordid+"&docType="+doctype+"' width='600' height='500' ></iframe>";
    }

    // Отмена
    function cancel${name}fssExport() {
        //theTableArrow = new tablearrow.TableArrow('prolongation') ;
        the${name}fssExport.hide() ;
        msh.effect.FadeEffect.pushFadeAll();

        jQuery("#prolongation").load("entityParentView-dis_document.do?id=${param.id} #prolongation");
    }

</script>