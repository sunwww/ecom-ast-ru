<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form action="/entitySaveGoView-mis_bedFundCapacity.do" defaultField="hello">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:panel>
       <msh:row>
					<msh:textField property="startDate" label="Дата начала"/>
					<msh:textField property="finishDate" label="окончания"/>
		</msh:row>
		<msh:row>
			<msh:autoComplete property="bedType" vocName="vocBedType" label='Профиль коек' size="50" fieldColSpan="5" />
		</msh:row>
		<msh:row>
			<msh:autoComplete property="bedSubType" vocName="vocBedSubType" label='Тип коек' size="50" fieldColSpan="5" />
		</msh:row>
		<msh:row>
			<msh:autoComplete property="department" vocName="vocLpuHospOtdAll" label='Отделение' size="50" fieldColSpan="5" />
		</msh:row>
		<msh:row>
			<msh:autoComplete property="financeSource" vocName="vocFinanceSource" label='Источник финансирования' size="50" fieldColSpan="5"/>
		</msh:row>
		<msh:row>
		<msh:textField property="actualBedCount" size="20" />
		</msh:row>
		<msh:row>
		<msh:textField property="planTreatmentDuration" size="20" />
		</msh:row> <msh:row>
		<msh:textField property="workingDays" size="20" />
		</msh:row> <msh:row>
		<msh:textField property="planBedCount" size="20" />
		</msh:row> <msh:row>
		<msh:textField property="countDays" size="20" />
		</msh:row> <msh:row>
		<msh:textField property="countHospitals" size="20" />
		</msh:row>  
<!-- 				<hello>
					
	
	
	/** Средняя длительность лечения */
	private Long thePlanTreatmentDuration;
	
	/** Количество дней работы в году (план) */
	private Long theWorkingDays;
	
	/** Фактическое количество коек */
	private Long thePlanBedCount;
	
	/** Количество койкодней (план) */
	private Long theCountDays;
	
	/** Количество госпитализаций */
	private Long theCountHospitals;
				
				
				
				</hello> -->
        
        <msh:submitCancelButtonsRow colSpan="4" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="side" type="string">
  <msh:sideMenu>
				<msh:sideLink  action="/js-mis_bedFundCapacity-listAll.do" name="Перейти к списку" title="Список" roles="/Policy/Mis/BedFund"/>
			</msh:sideMenu>
		<msh:ifFormTypeAreViewOrEdit formName="mis_bedFundCapacityForm">
			
			<msh:sideMenu>
				<msh:sideLink key="ALT+2" params="id" action="/entityEdit-mis_bedFundCapacity.do" name="Изменить" title="Изменить" roles="/Policy/Mis/BedFund/Edit"/>
				<msh:sideLink confirm="Удалить?" key="ALT+DEL" params="id" action="/entityDelete-mis_bedFundCapacity.do" name="Удалить" title="Удалить" roles="/Policy/Mis/BedFund/Delete"/>
			</msh:sideMenu>
		</msh:ifFormTypeAreViewOrEdit>
	</tiles:put>
</tiles:insert>

