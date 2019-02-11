<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>

<%@  attribute name="currentAction" required="true" description="Текущее меню" %>

<style type="text/css">
a#${currentAction}, #side ul li a#${currentAction}, #side ul li a#${currentAction}  {
    color: black ;
    background-color: rgb(195,217,255) ;
    cursor: text ;
    border: none ;
    text-decoration: none ;
    background-image: url("/skin/images/main/sideSelected.gif");
    background-repeat: no-repeat;
    background-position: center left; ;
	font-weight: bold ;
    -moz-border-radius-topleft: 4px ;
    -moz-border-radius-bottomleft: 4px ;
}
#side ul li a#deleteDischarge {
	color: red ;
	background-color: silver;
}

</style>
<msh:sideMenu></msh:sideMenu>
	    <msh:sideMenu title="Перейти">
		 <msh:sideLink styleId="org" action="/pharm_voc" name="Отчет по аптеке" roles="/Policy/Mis/Pharmacy/PharmReport" title="Отчет по аптеке" />
	    <msh:sideLink styleId="org" action="/entityPrepareCreate-directory_department" name="Отделения" roles="/Policy/Mis/Directory/Department" title="Перейти к списку отделений"/>
	    <msh:sideLink styleId="org" action="/entityPrepareCreate-directory_entry" name="Персоны" roles="/Policy/Mis/Directory/Department" title="Перейти к списку персон"/>
	    <msh:sideLink styleId="org" action="/entityPrepareCreate-directory_telephonenumber" name="Номера" roles="/Policy/Mis/Directory/Department" title="Перейти к списку персон"/>

	    	<msh:sideLink styleId="org" action="/entityList-mis_assessmentCardTemplate" name="Карты оценки" roles="/Policy/Mis/AssessmentCard" title="Перейти к списку карт оценки"/>
	    	<msh:sideLink styleId="org" action="/entityList-mis_patientList" name="Списки пациентов" roles="/Policy/Mis/Patient/PatientList" title="Перейти к спискам пациентов"/>
	    	<msh:sideLink styleId="org" action="/entityList-mis_userDocument" name="Пользовательские документы" roles="/Policy/Mis/UserDocument" title="Перейти к справочнику пользовательских документов"/>
	    	<msh:sideLink styleId="org" action="/js-mis_bedFundCapacity-listAll.do" name="Объемы коечного фонда" roles="/Policy/Mis/BedFund" title="Перейти к справочнику объемов коечного фонда"/>
	    	<msh:sideLink styleId="org" action="/js-mis_medicalEquipment-listAll.do" name="Мед. оборудования" roles="/Policy/Voc/VocTypeEquip/View" title="Перейти к справочнику стандартов"/>
	    	<msh:sideLink styleId="org" action="/entityList-mis_medicalStandard" name="Мед. стандарты" roles="/Policy/Voc/VocTypeEquip/View" title="Перейти к справочнику стандартов"/>
	    	<msh:sideLink styleId="org" action="/entityList-voc_typeEquip" name="Типы оборудования" roles="/Policy/Voc/VocTypeEquip/View" title="Перейти к справочнику типов оборудования"/>
	    	<msh:sideLink styleId="org" action="/voc_orgs" name="Организации" roles="/Policy/Voc/VocOrg/View" title="Перейти к справочнику организаций"/>
	    	<msh:sideLink styleId="operation" action="/voc_operations" name="Хир.операции" roles="/Policy/Voc/VocOperation/View" title="Перейти к справочнику хир. операций"/>
	    	<msh:sideLink styleId="medService" action="/entityParentList-mis_medServiceGroup.do?id=0" name="Услуги" roles="/Policy/Mis/MedService/View" title="Перейти к справочнику услуг"/>
	    	<msh:sideLink styleId="drugUN" action="/voc_drugUNs" name="Лек.Ср (непатен.)" roles="/Policy/Voc/VocDrugUnlicensedName/View" title="Перейти к справочнику Лек.Ср"/>
	    	<msh:sideLink styleId="drugLN" action="/voc_drugLNs" name="Лек.Ср (патен.)" roles="/Policy/Voc/VocDrugLicensedName/View" title="Перейти к справочнику Лек.Ср"/>
	    	<msh:sideLink styleId="drugTrade" action="/voc_drugs" name="Лек.Ср (торг.)" roles="/Policy/Voc/VocDrugUnlicensedName/View" title="Перейти к справочнику Лек.Ср"/>
	    	<msh:sideLink styleId="drugClassif" action="/entityParentList-drug_classificator.do?id=0" name="Классификатор" roles="/Policy/Mis/Drug/Classificator/View" title="Перейти к классификатору лек.средств"/>
	    	<msh:sideLink styleId="exp_voc_kind" action="/entityList-exp_vocKind.do" name="Виды экспертизы" roles="/Policy/Voc/VocQualityEstimationKind/View" title="Перейти к видам экспертизы"/>
	    	<msh:sideLink styleId="post" action="/entityList-voc_post.do" name="Должности" roles="/Policy/Voc/VocPost/View" title="Перейти к должностям"/>
	    	<msh:sideLink styleId="mnWorkFunction" action="/entityList-voc_workFunction.do" name="Рабочие функции" roles="/Policy/Voc/VocWorkFunction/View" title="Перейти к рабочим функциям"/>
	    	<msh:sideLink styleId="extDisp" action="/entityList-extDisp_voc.do" name="Виды доп. диспансеризации" roles="/Policy/Mis/ExtDisp/Card/Voc/View" title="Перейти к списку видов доп. диспансеризации"/>
			<msh:sideLink styleId="analisisMedServices"
				action="/contact_analisis_by_medServices.do" 
				name="Анализ услуг " roles="/Policy/Mis/Contract/Journals/AnalisisMedServices"
				title="Анализ услуг "
				/>
			<msh:sideLink styleId="diary_param_list"
				action="/js-rep_parameter-report_list.do" 
				name="Параметры отчетов" roles="/Policy/Voc/ReportConfig/View"
				title="Параметры отчетов"
				/>
				
				        <msh:sideLink 
        	action="/entityList-diary_userDomain.do" 
        	name="⇧Пользов. справочники" 
        	title="Просмотр пользовательских справочников" 
        	styleId="diary_user_voc"
        	roles="/Policy/Diary/User/Domain/View"
        	/>
        <msh:sideLink 
        	action="/entityParentList-diary_parameterGroup.do?id=-1" 
        	name="⇧Группы параметров" 
        	title="Просмотр групп доступных параметров" 
        	styleId="diary_parameterGroup"
        	roles="/Policy/Diary/ParameterGroup/View"
        	/>
			<msh:sideLink
					action="/listCrits203"
					name="Приказ 203"
					title="Приказ 203"
					styleId="diary_parameterGroup"
					roles="/Policy/Mis/Order203/EditVocs"
			/>
			<msh:sideLink
					action="/entityList-calc_calculator.do"
					name="Калькуляторы"
					title="Калькуляторы"
					styleId="diary_parameterGroup"
					roles="/Policy/Mis/Calc/Calculator"
			/>
	    </msh:sideMenu>
