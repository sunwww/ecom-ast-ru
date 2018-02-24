<%@ page import="ru.ecom.web.util.ActionUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
	<tiles:put name="title" type="string">
	</tiles:put>

	<tiles:put name="body" type="string">
		<msh:form  action="/extDispPlanView.do" defaultField="lastname" disableFormDataConfirm="true">
			<msh:hidden property="id" />
			<msh:panel>
				<msh:row>
					<td class="label" title="Отображать" colspan="1"><label for="typeViewName" id="typeViewLabel">Отображать:</label></td>
					<td onclick="this.childNodes[1].checked='checked';">
						<input type="radio" name="typeView" value="1">  План по кварталам
					</td>
					<td onclick="this.childNodes[1].checked='checked';" colspan="2">
						<input type="radio" name="typeView" value="2">  План по месяцам
					</td>
					<td onclick="this.childNodes[1].checked='checked';" colspan="2">
						<input type="radio" name="typeView" value="3">  Список пациентов
					</td>
				</msh:row>	<msh:row>

			<msh:autoComplete property="lpu" label="Отделение" vocName="lpu" size="100"/>
			</msh:row>	<msh:row>
			<msh:autoComplete property="area" vocName="lpuAreaWithParent" parentAutocomplete="lpu" label="Участок" size="80"/>
			</msh:row>	<msh:row>
			<msh:autoComplete property="sex" vocName="vocSex" label="Пол"/>
			</msh:row>
			<msh:row>
				<msh:textField property="period" label="Дата начала периода" />
				<msh:textField property="periodTo" label="Дата окончания периода" />
			</msh:row>
			<msh:row>
				<msh:textField property="lastname" label="Поиск по фамилии" size="100"/>
			</msh:row>
				<msh:row><td>
			<input type="submit" value="Поиск">
				</td>
				</msh:row>
			</msh:panel>
		</msh:form>
<%
	String typeView =ActionUtil.updateParameter("ExtDispPlanView","typeView","1", request) ;
	ActionUtil.setParameterFilterSql("lpu","area.lpu_id", request) ;
	ActionUtil.setParameterFilterSql("area","att.area_id", request) ;
	ActionUtil.setParameterFilterSql("sex","pat.sex_id", request) ;
	ActionUtil.setUpperLikeSql("lastname","pat.lastname", request);
	String startDate = request.getParameter("period");
	String finishDate = request.getParameter("periodTo");
	String dateSql="";
	if (startDate!=null&&!startDate.equals("")) {
	    if (finishDate==null||finishDate.equals("")) {finishDate=startDate;}
	    dateSql=" and rec.planDispDate between to_date('01."+startDate.substring(3)+"','dd.MM.yyyy') and to_date('01."+finishDate.substring(3)+"','dd.MM.yyyy') ";
	}
	//deleteUrl="entityParentDeleteGoParentView-extDispPlan_record.do"
	request.setAttribute("dateSql",dateSql);
	if (typeView.equals("3")) {

%>
			<msh:separator colSpan="8" label="Записи в плане"/>
			<msh:section title="Пациенты в плане" >
				<ecom:webQuery name="records" nameFldSql="recordsSql" nativeSql=" select rec.id as f1_id
				,pat.patientinfo as f2_pat
				,to_char(rec.planDispDate,'MM.yyyy') as planDispDate

				,area.number||' ' ||vat.name as f_area
				,rec.isExtDispMade as isDispMade
				from extdispplanpopulationrecord rec
				left join patient pat on pat.id=rec.patient_id
				left join lpuattachedbydepartment att on att.patient_id=pat.id and att.dateto is null
				left join lpuarea area on area.id=att.area_id
				left join vocareatype vat on vat.id=area.type_id
				where rec.plan_id=${param.id} and (rec.isDeleted is null or rec.isDeleted='0') ${dateSql} ${lastnameSql} ${lpuSql} ${areaSql} ${sexSql}"/>
			<msh:table name="records" action="entityParentEdit-extDispPlan_record.do" idField="1"
					    printToExcelButton="Сохранить в excel" >
				<msh:tableColumn property="2" columnName="ФИО пациента"/>
				<msh:tableColumn property="3" columnName="Месяц прохождения ДД"/>
				<msh:tableColumn property="4" columnName="Участок прикрепления"/>
				<msh:tableColumn property="5" columnName="ДД пройдена"/>
${recordsSql}
			</msh:table>
			</msh:section>
<%  } else  if (typeView.equals("2")){  //Группировка по месяцам %>
		<msh:separator colSpan="8" label="Записи в плане"/>
		<msh:section title="Свод по месяцам" >
		<ecom:webQuery name="records" nameFldSql="recordsSql" nativeSql=" select
		 count(case when to_char(rec.planDispDate,'MM')='01' then rec.id end) as cnt1
		,count(case when to_char(rec.planDispDate,'MM')='02' then rec.id end) as cnt2
		,count(case when to_char(rec.planDispDate,'MM')='03' then rec.id end) as cnt3
		,count(case when to_char(rec.planDispDate,'MM')='04' then rec.id end) as cnt4
		,count(case when to_char(rec.planDispDate,'MM')='05' then rec.id end) as cnt5
		,count(case when to_char(rec.planDispDate,'MM')='06' then rec.id end) as cnt6
		,count(case when to_char(rec.planDispDate,'MM')='07' then rec.id end) as cnt7
		,count(case when to_char(rec.planDispDate,'MM')='08' then rec.id end) as cnt8
		,count(case when to_char(rec.planDispDate,'MM')='09' then rec.id end) as cnt9
		,count(case when to_char(rec.planDispDate,'MM')='10' then rec.id end) as cnt10
		,count(case when to_char(rec.planDispDate,'MM')='11' then rec.id end) as cnt11
		,count(case when to_char(rec.planDispDate,'MM')='12' then rec.id end) as cnt12
				from extdispplanpopulationrecord rec
				left join patient pat on pat.id=rec.patient_id
				left join lpuattachedbydepartment att on att.patient_id=pat.id and att.dateto is null
				left join lpuarea area on area.id=att.area_id
				where rec.plan_id=${param.id} and (rec.isDeleted is null or rec.isDeleted='0') ${dateSql} ${lastnameSql} ${lpuSql} ${areaSql} ${sexSql}

"/>
		<msh:table name="records" action="javascript:void()" idField="1"
				   deleteUrl="entityParentDeleteGoParentView-extDispPlan_record.do" printToExcelButton="Сохранить в excel" >
		<msh:tableColumn property="1" columnName="Январь"/>
		<msh:tableColumn property="2" columnName="Февраль"/>
		<msh:tableColumn property="3" columnName="Март"/>
		<msh:tableColumn property="4" columnName="Апрель"/>
		<msh:tableColumn property="5" columnName="Май"/>
		<msh:tableColumn property="6" columnName="Июнь"/>
		<msh:tableColumn property="7" columnName="Июль"/>
		<msh:tableColumn property="8" columnName="Август"/>
		<msh:tableColumn property="9" columnName="Сентябрь"/>
		<msh:tableColumn property="10" columnName="Октябрь"/>
		<msh:tableColumn property="11" columnName="Ноябрь"/>
		<msh:tableColumn property="12" columnName="Декабрь"/>
		</msh:table>
		</msh:section>


<%  }  else if (typeView.equals("1")){ %>
		<msh:separator colSpan="8" label="Записи в плане"/>
		<msh:section title="Свод по кварталам" >
			<ecom:webQuery name="records" nameFldSql="recordsSql" nativeSql=" select
		 count(case when to_char(rec.planDispDate,'MM') in ('01','02','03') then rec.id end) as cnt1
		,count(case when to_char(rec.planDispDate,'MM') in ('04','05','06') then rec.id end) as cnt2
		,count(case when to_char(rec.planDispDate,'MM') in ('07','08','09') then rec.id end) as cnt3
		,count(case when to_char(rec.planDispDate,'MM') in ('10','11','12') then rec.id end) as cnt4
				from extdispplanpopulationrecord rec
				left join patient pat on pat.id=rec.patient_id
				left join lpuattachedbydepartment att on att.patient_id=pat.id and att.dateto is null
				left join lpuarea area on area.id=att.area_id

				where rec.plan_id=${param.id} and (rec.isDeleted is null or rec.isDeleted='0') ${dateSql} ${lastnameSql} ${lpuSql} ${areaSql} ${sexSql}"/>
			<msh:table name="records" action="javascript:void()" idField="1"
					   deleteUrl="entityParentDeleteGoParentView-extDispPlan_record.do" printToExcelButton="Сохранить в excel" >
				<msh:tableColumn property="1" columnName="I кв"/>
				<msh:tableColumn property="2" columnName="II кв"/>
				<msh:tableColumn property="3" columnName="III кв"/>
				<msh:tableColumn property="4" columnName="IV кв"/>
			</msh:table>
		</msh:section>
<%}%>
	</tiles:put>
	<tiles:put name="javascript" type="string">
    <script type="text/javascript" src="./dwr/interface/PatientService.js"></script>
    <script type="text/javascript" src="./dwr/interface/ExtDispService.js"></script>
		
		<script type="text/javascript">
			checkFieldUpdate('typeView','${typeView}','1');
            function checkFieldUpdate(aField,aValue,aDefaultValue) {
                eval('var chk =  document.forms[0].'+aField) ;
                var aMax=chk.length ;
                //alert(aField+" "+aValue+" "+aMax+" "+chk) ;
                if ((+aValue)==0 || (+aValue)>(+aMax)) {
                    chk[+aDefaultValue-1].checked='checked' ;
                } else {
                    chk[+aValue-1].checked='checked' ;
                }
            }
		</script>


	</tiles:put>


	<tiles:put name="side" type="string">
			<msh:sideMenu>
				<msh:sideLink params="id" action="/extDispPlanAdd" name="Добавить в план" title="Добавить население в план" roles="/Policy/Mis/ExtDisp/Card/Edit"/>
				<msh:sideLink confirm="Удалить план ДД?" key="ALT+DEL" params="id" action="/entityDelete-extDispPlan_plan" name="Удалить" title="Удалить" roles="/Policy/Mis/ExtDisp/Card/Delete"/>

			</msh:sideMenu>
			<msh:sideMenu title="Добавить" >
			
			</msh:sideMenu>
	</tiles:put>
</tiles:insert>
