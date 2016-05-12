<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='body' type='string'>
        <msh:form action="entitySaveGoView-mis_medicalEquipmentPosition.do" defaultField="equipmentType" >
            <msh:hidden property="id"/>
            <msh:hidden property="standard"/>
            <msh:hidden property="saveType"/>
            <msh:panel>
             <msh:row>
             <msh:autoComplete vocName="vocMedStandard" property="standard" label="Стандарт" size="50"/>
             </msh:row>
             <msh:row>
                    <msh:autoComplete  vocName="vocEquipmentType" property="equipmentType" label="Оборудование" size="50"/>
                    <msh:textField property="amount" label="Количество" horizontalFill="true" fieldColSpan="4"/>
               
		</msh:row>
	            <msh:submitCancelButtonsRow colSpan="4"/>
            </msh:panel>
        </msh:form>

    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:ifFormTypeIsView formName="mis_medicalEquipmentPositionForm">
                <msh:sideLink key="ALT+2" params="id" action="/entityEdit-mis_medicalEquipmentPosition" name="Изменить"/>
            </msh:ifFormTypeIsView>
            <hr/>
            <msh:ifFormTypeAreViewOrEdit formName="mis_medicalEquipmentPositionForm">
                <msh:sideLink key='ALT+DEL' params="id" action="/entityDelete-mis_medicalEquipmentPosition" name="Удалить" confirm="Удалить?"/>
            </msh:ifFormTypeAreViewOrEdit>
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Lpu" beginForm="mis_medicalEquipmentPositionForm" />
    </tiles:put>
<tiles:put name="javascript" type="string">
      <script type="text/javascript">
      
      </script>
      </tiles:put>

</tiles:insert>
