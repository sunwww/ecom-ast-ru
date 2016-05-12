<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

<tiles:put name='body' type='string'>
    <msh:form action="entitySaveGoView-diary_templateword" defaultField="reduction">
        <msh:hidden property="id"/>
        <msh:hidden property="saveType"/>


        <!--<input type="hidden" name="regionPk" value="" id="regionPk">-->
        <!--<input type="hidden" name="flatNumber" value="" id="flatNumber">-->
        <!--<input type="hidden" name="houseBuilding" value="" id="houseBuilding">-->
        <!--<input type="hidden" name="houseNumber" value="" id="houseNumber">-->


        <msh:panel>
            <msh:ifFormTypeIsNotView formName="diary_templatewordForm" >
                <msh:row>
                    <msh:textField property="reduction" label="Ключевое слово" horizontalFill="true"/>
                </msh:row>
            </msh:ifFormTypeIsNotView>
                <msh:row>
                    <msh:textArea property="decryption" label="Расшифрока" horizontalFill="true"/>
                </msh:row>
                        <msh:row guid="b9051979-4115-40c0-8d63-4fce097d9a72">
          <ecom:oneToManyOneAutocomplete  viewAction="entityView-secgroup.do" label="Довер. группы" vocName="secGroup" property="secGroups" colSpan="4" />
        </msh:row>
        <msh:row guid="fdcf0100-ab1c-4900-b7d6-cb08c77924b0">
          <msh:textField property="createUsername" label="Пользователь" viewOnlyField="true" guid="b3fd6145-7072-4065-accc-73fc37fb20ac" />
          <msh:textField property="createDate" label="Дата создания" viewOnlyField="true" guid="7162d626-b2a7-4928-ab70-adb244c07d5d" />
        </msh:row>
        <msh:row guid="fdcf0100-ab1c-4900-b7d6-cb08c77924b0">
          <msh:textField property="editDate" label="Дата редактирование" viewOnlyField="true" guid="7162d626-b2a7-4928-ab70-adb244c07d5d" />
        </msh:row>
            <msh:submitCancelButtonsRow colSpan="6"/>
        </msh:panel>

    </msh:form>


</tiles:put>

<tiles:put name='side' type='string'>
    <msh:sideMenu>
        <msh:sideLink key="ALT+1" params="" action="/entityList-diary_templateword" name="⇧ Список ключевых полей"/>

        <msh:ifFormTypeIsView formName="diary_templatewordForm">
            <msh:sideLink key="ALT+2" params="id" action="/entityEdit-diary_templateword" name="Изменить"/>


        </msh:ifFormTypeIsView>

        <hr/>
        <msh:ifFormTypeAreViewOrEdit formName="diary_templatewordForm">
            <msh:sideLink key='ALT+DEL' params="id" action="/entityDelete-diary_templateword" name="Удалить"
                          confirm="Удалить ключевое слово?"/>
        </msh:ifFormTypeAreViewOrEdit>

    </msh:sideMenu>
</tiles:put>

<tiles:put name="javascript" type="string">
    <msh:ifFormTypeIsView formName="diary_templatewordForm">

        <script type="text/javascript">
//            $('buttonShowAddress').style.display = 'none';
        </script>
    </msh:ifFormTypeIsView>

</tiles:put>


<tiles:put name='title' type='string'>
    <ecom:titleTrail mainMenu="Diary" beginForm="diary_templatewordForm"/>
</tiles:put>


</tiles:insert>