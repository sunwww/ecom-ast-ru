<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="ecom" uri="http://www.ecom-ast.ru/tags/ecom" %>

<%@ attribute name="name" required="true" description="Название" %>

<div id='${name}Dialog' class='dialog'>
    <h2>Лист наблюдения пациента</h2>
    <table width="100%" cellspacing="10" cellpadding="10" id="sheetTable${name}" border="1" >
    </table>
    <div>
        <form  id="${name}">
            <msh:panel>
                <msh:row>
                    <msh:comboBox size='300' horizontalFill="true" property='${name}vocObservationResult' vocName="vocObservationResult" label='Выберите:'/>
                </msh:row>
            </msh:panel>
        </form>
    </div>
    <div>
        <table width="100%" cellspacing="10" cellpadding="10">
            <tr>
                <td align="center"><input type="button" value='Закрыть лист наблюдения' onclick='javascript:closeObservSheet${name}()'/></td>
            </tr>
            <tr><td></td></tr>
            <tr>
                <td align="right"><input type="button"  style="font-weight:bold" value='Отмена' id="${name}Cancel" onclick=' the${name}Dialog.hide() ;'/></td>
            </tr>
        </table>
    </div>
</div>
<script type="text/javascript">
    var aPatId${name};
    var loc${name};
    var the${name}Dialog = new msh.widget.Dialog($('${name}Dialog')) ;

    // Показать
    function show${name}(id,loc) {
        aPatId${name}=id;
        loc${name}=loc;
        the${name}Dialog.show();
    }
    // Закрыть лист наблюдения
    function closeObservSheet${name}() {
        if ($('${name}vocObservationResult').value!='') {
        PatientService.closeObservSheet(aPatId${name},$('${name}vocObservationResult').value, {
            callback: function(res) {
                if (res=='1')
                    //window.location.reload();
                    window.location.href=loc${name}; //лучше сбросить пациента
                else
                    showToastMessage('Не найдено открытых листов наблюдения!',null,true);
            }
        });
        the${name}Dialog.hide() ;
        }
        else
            showToastMessage('Выберите исход наблюдения!',null,true);
    }
</script>