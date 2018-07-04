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
			<msh:sideLink action="/entityList-e2_vocCofficientLpuLevel.do" name="Коэффициент уровня ЛПУ (отделения)" title="Коэффициент уровня ЛПУ (отделения)" roles="/Policy/E2"/>
			<msh:sideLink action="/entityList-e2_vocMedHelpProfile.do" name="Справочник профилей мед. помощи" title="Справочник профилей мед. помощи" roles="/Policy/E2"/>
			<msh:sideLink action="/entityList-e2_vocBaseTariff.do" name="Справочник базовых тарифов" title="Справочник базовых тарифов" roles="/Policy/E2"/>
			<msh:sideLink action="/entityList-e2_vocPolyclinicCoefficient.do" name="Справочник коэффициентов по поликлинике" title="Справочник коэффициентов по поликлинике" roles="/Policy/E2"/>
			<msh:sideLink action="/entityList-e2_config.do" name="Справочник настроек экспертизы" title="Справочник настроек экспертизы" roles="/Policy/E2"/>
			<msh:sideLink action="/entityList-e2_vocFondV012.do" name="Справочник V012" title="Справочник настроек экспертизы" roles="/Policy/E2" />
			<msh:sideLink action="/entityList-e2_vocFondV009.do" name="Справочник V009" title="Справочник настроек экспертизы" roles="/Policy/E2" />
			<msh:sideLink action="/entityList-e2_vocFondV015.do" name="Справочник V015" title="Справочник V015" roles="/Policy/E2" />
			<msh:sideLink action="/entityList-e2_vocEntrySubType.do" name="Подтипы случая" title="Подтипы случая" roles="/Policy/E2" />
			<msh:sideLink action="/entityList-e2_polFinancePlan.do" name="Финансовый планы по пол-ке" title="Финансовый планы по пол-ке" roles="/Policy/E2" />
			<msh:sideLink action="/entityList-e2_stacFinancePlan.do" name="Финансовый планы по стационару" title="Финансовый планы по стационару" roles="/Policy/E2" />
			<msh:sideLink action="/entityList-e2_litteAmountMonth.do" name="Настроечная табличка" title="Настроечная табличка" roles="/Policy/E2" />
			<msh:sideLink action="/entityList-e2_vocKsg.do" name="Справочник КСГ" title="Справочник КСГ" roles="/Policy/E2" />
	    </msh:sideMenu>
