<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

<tiles:put name='body' type='string'>
<msh:form action="entitySaveGoView-mis_vaccination.do" defaultField="planDate">
   <msh:hidden property="id"/>
   <msh:section title="Вакцинация"> 
     <msh:panel colsWidth='5%, 20%, 10%'>
        <msh:row>
            <msh:textField property="planDate" label="Дата по плану"/>
            <msh:autoComplete property="reasonType" label="Основание проведения" size="20" vocName="vocVaccinationReasonType"/>
	    </msh:row>
        <msh:row>
            <msh:autoComplete property="permitWorker" label="Разрешил"  vocName="vocWorker" horizontalFill="true" fieldColSpan="5"/>
	    </msh:row>
        <msh:row>
            <msh:autoComplete property="executeWorker" label="Выполнил"  vocName="vocWorker" horizontalFill="true" fieldColSpan="5"/>
	    </msh:row>
        <msh:row>
            <msh:textField property="executeDate" label="Дата исполнения"/>
            <msh:textField property="executeTime" label="Время исполнения"/>
	    </msh:row>
        <msh:row>
            <msh:autoComplete property="vaccine" label="Вакцина" size="20" vocName="vocVaccine" />
            <msh:autoComplete property="material" label="Вакцинный материал" size="20" vocName="vocVaccinationMaterial"/>
            <msh:autoComplete property="method" label="Метод вакцинации" size="10" vocName="vocVaccinationMethod"/>            
	    </msh:row>
        <msh:row>
            <msh:textField property="series" label="Серия" size="10" />
            <msh:textField property="controlNumber" label="Контрольный номер" size="10" />
            <msh:textField property="expirationDate" label="Годен до"/>            
	    </msh:row>
        <msh:submitCancelButtonsRow colSpan="3"/>
    </msh:panel>        
  </msh:section>
</msh:form>
</tiles:put>

<tiles:put name='side' type='string'>
    <msh:sideMenu>
        <msh:sideLink key="ALT+1" params="" action="/mis_patients" name="⇧ Список персон"/>

        <msh:ifFormTypeIsView formName="mis_vaccinationForm">
            <msh:sideLink roles="/Policy/Mis/Vaccination/Edit" key="ALT+2" params="id" action="/entityEdit-mis_vaccination"
                          name="Изменить"/>
        </msh:ifFormTypeIsView>
    </msh:sideMenu>
</tiles:put>

</tiles:insert>