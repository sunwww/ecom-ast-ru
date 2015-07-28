<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='body' type='string'>
        <msh:form action="entitySaveGoView-mis_medicalStandard.do" defaultField="name">
            <msh:hidden property="id"/>
            <msh:hidden property="saveType"/>
            <msh:panel>
             <msh:row>
                   <msh:textField property="name" label="Название" horizontalFill="true" size="50"/>
              </msh:row>
              <msh:row>
                    <msh:textField property="code" label="Код" horizontalFill="true" size="20"/>
               </msh:row>
              <msh:row>
                    <msh:textField property="startDate" label="Дата начала действия" horizontalFill="true"/>
               </msh:row>
              <msh:row>
                    <msh:textField property="finishDate" label="Дата окончания действия" horizontalFill="true"  />
               </msh:row>
	            <msh:submitCancelButtonsRow colSpan="4"/>
            </msh:panel>
        </msh:form>
<msh:ifFormTypeIsView formName="">
	<msh:section>
	<ecom:webQuery name="listEquip" nativeSql="select vte.name, mep.amount from MedicalEquipmentPosition mep
	left hoin VocTypeEquip vte on vte.id=mep.equipmentType_id
	where standard_id=${param.id}"/>
	</msh:section>
</msh:ifFormTypeIsView>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
            
            <msh:ifFormTypeIsView formName="mis_medicalStandardForm">
<msh:sideLink params="id" action="/entityParentPrepareCreate-mis_medicalEquipmentPosition" name="Добавить оборудование"/>
                <msh:sideLink key="ALT+2" params="id" action="/entityEdit-mis_medicalStandard" name="Изменить"/>
            </msh:ifFormTypeIsView>
            <hr/>
            <msh:ifFormTypeAreViewOrEdit formName="mis_medicalStandardForm">
                <msh:sideLink key='ALT+DEL' params="id" action="/entityDelete-mis_medicalStandard" name="Удалить" confirm="Удалить стандарт?"/>
            </msh:ifFormTypeAreViewOrEdit>
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Lpu" beginForm="mis_medicalStandardForm" />
    </tiles:put>
<tiles:put name="javascript" type="string">
      <script type="text/javascript">
      
      </script>
      </tiles:put>

</tiles:insert>
