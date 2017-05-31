<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>
<%@ attribute name="documentId" required="true" description="Поле Id документа" %>

<style type="text/css">
    #${name}FSSJournal {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>

<div id='${name}FSSJournal' class='dialog'>
    <h2>Журнал экспорта документа в фонд</h2>
    <div class='rootPane'>
        <h3>Журнал экспорта документа в фонд</h3>
        <form action="javascript:void(0)" id="frmFond" name="frmFond">
            <msh:panel>

                <msh:row>
                    <span id='${name}FSSJournalText'/>
                </msh:row>
            </msh:panel>
            <msh:row><td colspan="6">
               <input type="button" value='Закрыть окно' onclick='javascript:cancel${name}FSSJournal()'/>
            </td></msh:row>
        </form>
    </div>
</div>

<script type='text/javascript' src='./dwr/interface/DisabilityService.js'></script>
<script type="text/javascript">
var theIs${name}FSSJournalInitialized = false ;
var the${name}FSSJournal = new msh.widget.Dialog($('${name}FSSJournal')) ;
// Показать
function show${name}FSSJournal() {
DisabilityService.getExportJournalById('${documentId}', {
    callback: function (res) {
        if (res!=null) {
            $('${name}FSSJournalText').innerHTML = res ;
        } else {
            $('${name}FSSJournalText').innerHTML = "ДАННЫЕ ПО ЭКСПОРТУ НЕ НАЙДЕНЫ" ;
        }
    }
});



    the${name}FSSJournal.show() ;

}

// Отмена
function cancel${name}FSSJournal() {
    the${name}FSSJournal.hide() ;
    msh.effect.FadeEffect.pushFadeAll();
}

</script>