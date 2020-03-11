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
	    <msh:sideMenu title="Тарифы" >
			<msh:sideLink styleId="e2_vocBaseTariff_st" action="/entityList-e2_vocBaseTariff.do" name="Справочник базовых тарифов" title="Справочник базовых тарифов" roles="/Policy/E2"/>
			<msh:sideLink styleId="e2_vocCofficientLpuLevel_st" action="/entityList-e2_vocCofficientLpuLevel.do" name="Коэффициент уровня ЛПУ (отделения)" title="Коэффициент уровня ЛПУ (отделения)" roles="/Policy/E2"/>
			<msh:sideLink styleId="e2_vocMedServiceCost_st" action="/entityList-e2_vocMedServiceCost.do" name="Цены услуг" title="Цены услуг" roles="/Policy/E2"/>
		</msh:sideMenu>
		<msh:sideMenu title="Справочники" >
			<msh:sideLink styleId="e2_vocMedHelpProfile_st" action="/entityList-e2_vocMedHelpProfile.do" name="Справочник профилей мед. помощи" title="Справочник профилей мед. помощи" roles="/Policy/E2"/>
			<msh:sideLink styleId="e2_vocFondV009_st" action="/entityList-e2_vocFondV009.do" name="Справочник V009" title="Справочник настроек экспертизы" roles="/Policy/E2" />
			<msh:sideLink styleId="e2_vocFondV012_st" action="/entityList-e2_vocFondV012.do" name="Справочник V012" title="Исход случая" roles="/Policy/E2" />
			<msh:sideLink styleId="e2_vocFondV020_st" action="/entityList-e2_vocFondV020.do" name="Справочник V020" title="Результат обращения" roles="/Policy/E2" />
			<msh:sideLink styleId="e2_vocFondV021_st" action="/entityList-e2_vocFondV021.do" name="Справочник V021" title="Медицинская специальность" roles="/Policy/E2" />
			<msh:sideLink styleId="e2_vocFondV025_st" action="/entityList-e2_vocFondV025.do" name="Справочник V025" title="Цель посещения" roles="/Policy/E2" />
			<msh:sideLink styleId="e2_extDispPrice_st" action="/entityList-e2_extDispPrice.do" name="Цены ДД" title="Цены ДД" roles="/Policy/E2" />
			<msh:sideLink styleId="e2_vocEntrySubType_st" action="/entityList-e2_vocEntrySubType.do" name="Подтипы случая" title="Подтипы случая" roles="/Policy/E2" />
			<msh:sideLink styleId="e2_vocKsg_st" action="/entityList-e2_vocKsg.do" name="Справочник КСГ" title="Справочник КСГ" roles="/Policy/E2" />
			<msh:sideLink styleId="e2_vocKdp_st" action="/entityList-e2_vocKdp.do" name="Справочник КДП(DEL)" title="Справочник КДП" roles="/Policy/E2" />
		</msh:sideMenu>
		<msh:sideMenu title="Отчеты">
			<msh:sideLink styleId="e2_report_paid_st" action="/e2_report_paid.do" name="Отчет по оплаченным случаям" title="Отчет по оплаченным случаям" roles="/Policy/E2/View"/>
		</msh:sideMenu>
		<msh:sideMenu title="Настройки">
			<msh:sideLink styleId="e2_config_st" action="/entityList-e2_config.do" name="Справочник настроек экспертизы" title="Справочник настроек экспертизы" roles="/Policy/E2"/>
			<msh:sideLink styleId="PolyclinicFinancePlan_st" action="/e2_stacFinancePlan.do?type=PolyclinicFinancePlan" name="Финансовый планы по пол-ке" title="Финансовый планы по пол-ке" roles="/Policy/E2" />
			<msh:sideLink styleId="VmpFinancePlan_st" action="/e2_stacFinancePlan.do?type=VmpFinancePlan" name="Финансовый планы по ВМП" title="Финансовый планы по ВМП" roles="/Policy/E2" />
			<msh:sideLink styleId="HospitalFinancePlan_st" action="/e2_stacFinancePlan.do?type=HospitalFinancePlan" name="Финансовый планы по стационару" title="Финансовый планы по стационару" roles="/Policy/E2" />
			<msh:sideLink styleId="e2_litteAmountMonth_st" action="/entityList-e2_litteAmountMonth.do" name="Настроечная табличка" title="Настроечная табличка" roles="/Policy/E2" />
			<msh:sideLink styleId="e2_bill_st" action="/entityList-e2_bill.do" name="Список счетов" title="Список счетов" roles="/Policy/E2" />
			<msh:sideLink action="/javascript:findPersonByCommonnumber()" name="Поиск по RZ (оплаченые счета)" title="Поиск по RZ" roles="/Policy/E2" />
	    </msh:sideMenu>

<script type="text/javascript" >
	function findPersonByCommonnumber() {
	    var txt = prompt("Введите RZ или ФИО_ДР");
	    if (txt) {
            txt = txt.split(" ");
            var href;
            if (txt.length>3) {
                href="lastname:"+txt[0]+" "+txt[1]+" "+txt[2]+" "+txt[3];
            } else {
                href="commonnumber:"+txt[0];
            }
            window.open("entityList-e2_entry.do?id=0&filter="+href);
		}
	}
</script>
