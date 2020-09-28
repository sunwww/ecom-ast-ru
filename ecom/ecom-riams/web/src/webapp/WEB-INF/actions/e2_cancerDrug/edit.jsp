<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name="body" type="string">
        <msh:form action="/entityParentSaveGoParentView-e2_cancerDrug.do" defaultField="maybeCancer">
            <msh:hidden property="id" />
            <msh:hidden property="saveType" />
            <msh:hidden property="cancerEntry" />
            <msh:panel>
                <msh:separator label="Лек. препарат" colSpan="4"/>
                    <msh:row>
                        <msh:autoComplete property="drug" vocName="vocOncologyN020" size="50"/>
                    </msh:row>
                <msh:row>
                    <msh:textField property="date" label="Дата приема (нажать добавить дату)"/>
                    <td>
                        <input type="button" value="Добавить дату" onclick="addDrugDate()">
                    </td>
                </msh:row>
                <msh:submitCancelButtonsRow colSpan="1" />
            </msh:panel>
        </msh:form>
        <msh:ifFormTypeAreViewOrEdit formName="e2_cancerDrugForm">
            <msh:section title="Даты введения лекарств" >
                <ecom:webQuery name="directionList" nativeSql="select dd.id, dd.date
        from e2cancerDrugDate dd
  where dd.drug_id=${param.id} "/>
                <msh:tableNotEmpty  name="directionList"  >
                    <msh:table deleteUrl="entityParentDeleteGoParentView-e2_cancerDrugDate.do" idField="1" name="directionList" action="entityEdit-e2_cancerDrugDate.do" >
                        <msh:tableColumn columnName="Дата направления" property="2"/>
                    </msh:table>
                </msh:tableNotEmpty>
            </msh:section>
        </msh:ifFormTypeAreViewOrEdit>
    </tiles:put>
    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="Expert2" beginForm="e2_cancerDrugForm" />
    </tiles:put>
    <tiles:put name="javascript" type="string">
        <msh:ifFormTypeAreViewOrEdit formName="e2_cancerDrugForm">
            <script type="text/javascript" src="./dwr/interface/Expert2Service.js"></script>

            <script type="text/javascript">
                function addDrugDate() {
                    if ($('date').value) {
                        var url = 'entityParentSaveGoParentView-e2_cancerDrugDate.do';
                        jQuery.ajax({
                            url:url
                            ,data: {
                                id:0 //При создании всегда ноль
                                ,saveType:1 //1 значит создать, 2 - изменить существующее

                                ,drug:${param.id} //Если есть родитель
                                ,date:$('date').value //Все остальные поля заполняем как  на форме
                            }
                        }).done (function() {alert("Добавлено!")});
                    }

                }

            </script>

        </msh:ifFormTypeAreViewOrEdit>
        <msh:ifFormTypeIsCreate formName="e2_cancerDrugForm">
            <script type="text/javascript">
                jQuery('#date').addClass('required');
            function addDrugDate() {alert("Сначала создайте лекарство, потом вводите даты введения!");}
            </script>
        </msh:ifFormTypeIsCreate>



    </tiles:put>

    <tiles:put name="side" type="string">
        <msh:ifFormTypeAreViewOrEdit formName="e2_cancerDrugForm">
            <msh:sideMenu>
                <msh:sideLink params="id" action="/entityParentEdit-e2_cancerDrug" name="Изменить" roles="/Policy/E2/Edit" />
                <msh:sideLink params="id" action="/entityParentDelete-e2_cancerDrug" name="Удалить" roles="/Policy/E2/Delete" />
            </msh:sideMenu>
        </msh:ifFormTypeAreViewOrEdit>
    </tiles:put>
</tiles:insert>

