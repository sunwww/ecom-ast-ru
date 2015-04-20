<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

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
</style>
<msh:sideMenu >
	<msh:sideLink name="Забор биомат." styleId="pres_intake" action="/pres_journal_intake" roles="/Policy/Mis/Journal/Prescription/LabSurvey/IntakeByCurrentDepartment" />
	<msh:sideLink name="Передача биомат. в лаборатории" styleId="pres_transfer" action="/pres_journal_intake_transfer" roles="/Policy/Mis/Journal/Prescription/LabSurvey/TransferToLaboratory" />
	<msh:sideLink name="Рабочее место" styleId="pres_cabinet" action="/pres_journal_prescript_cab_lab" roles="/Policy/Mis/Journal/Prescription/LabSurvey/DoctorLaboratory" />
</msh:sideMenu>
<msh:sideMenu title="Статистика">
	<msh:sideLink name="Журнал назначений" styleId="pres_journal" action="/pres_journal" roles="/Policy/Mis/Journal/Prescription/LabSurvey/JournalPrescript" />
	<msh:sideLink name="Список отчетов" 
	action="/javascript:getDefinition('riams_journals.do?short=Short',null)" styleId="viewShort" />

</msh:sideMenu>
    <msh:sideMenu title="Поиск">
      <msh:sideLink key="CTRL+7" params="" action="/pres_prescription_find" name="⇧ Назначений" 
      	styleId="pres_find"
      	guid="e9346501-00ea-4b6b-bd25-7fdab4803413" />
    </msh:sideMenu>
