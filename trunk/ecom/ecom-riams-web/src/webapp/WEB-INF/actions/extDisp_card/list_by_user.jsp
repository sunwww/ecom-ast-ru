<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@page import="ru.ecom.ejb.services.query.WebQueryResult"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >
	<tiles:put name='title' type='string'>
		<msh:title mainMenu="Journals" >Диспансеризация</msh:title>
	</tiles:put>
	<tiles:put name='side' type='string'>
	</tiles:put>
	<tiles:put name='body' type='string' >
  <%
  	String typeGroup =ActionUtil.updateParameter("ExtDispAction","typeGroup","1", request) ;
  	String typePaid =ActionUtil.updateParameter("ExtDispAction","typePaid","3", request) ;
%>
 <msh:form action="extDisp_journal_user.do" defaultField="beginDate"> 
			<msh:panel>
			<msh:row>
				<msh:textField property="beginDate" label="c"/>
				<msh:textField property="finishDate" label="по"/>
			</msh:row>
			<msh:row>
				<msh:autoComplete property="dispType" label="Тип доп. диспансеризации" vocName="vocExtDisp" horizontalFill="true" fieldColSpan="3"/>
			</msh:row>
			<%-- <msh:row>
				<msh:autoComplete property="ageGroup" label="Возрастная группа" vocName="vocExtDispAgeGroupByDispType" parentAutocomplete="dispType" horizontalFill="true" fieldColSpan="3"/>
			</msh:row>
			<msh:row>
				<msh:autoComplete property="healthGroup" label="Группа здоровья" parentAutocomplete="dispType" vocName="vocExtDispHealthGroupByDispType" horizontalFill="true" fieldColSpan="3"/>
			</msh:row>
			<msh:row>
				<msh:autoComplete property="risk" label="Риск" vocName="vocExtDispRisk" fieldColSpan="3" horizontalFill="true"/>
			</msh:row>
			<msh:row>
				<msh:autoComplete property="service" label="Услуга" vocName="vocExtDispService" fieldColSpan="3" horizontalFill="true"/>
			</msh:row>
			<msh:row>
				<msh:autoComplete property="workFunction" label="Рабочая функция" vocName="workFunction" fieldColSpan="3" horizontalFill="true"/>
			</msh:row>
			<msh:row>
				<msh:autoComplete property="lpu" label="ЛПУ" vocName="lpu" fieldColSpan="3" horizontalFill="true"/>
			</msh:row> --%>

        <msh:row>
        	<msh:submitCancelButtonsRow labelSave="Сформировать" doNotDisableButtons="cancel" labelSaving="Формирование..." colSpan="4"/>
        </msh:row>
        </msh:panel>			
		</msh:form>
<%
		String beginDate = request.getParameter("beginDate") ;
		if (beginDate!=null && !beginDate.equals("")) {
		String finishDate = request.getParameter("finishDate") ;
		String dispType = request.getParameter("dispType") ;
		if (finishDate==null || finishDate.equals("")) {
			finishDate=beginDate ;
		}
		request.setAttribute("beginDate", beginDate) ;
		request.setAttribute("finishDate", finishDate) ;
		if (dispType==null || dispType.equals("")||dispType.equals("0")){ %>
			<msh:section>
			<ecom:webQuery name="reestrExtDispCard" nameFldSql="reestrExtDispCard_sql" nativeSql="
select ved.name, createusername, count(edc.id)
from ExtDispCard edc
left join VocExtDisp ved on ved.id=edc.dispType_id
where edc.finishDate between to_date('${beginDate}','dd.mm.yyyy') and to_date('${finishDate}','dd.mm.yyyy')
${sqlAppend} 
group by ved.name, createusername
order by ved.name, createusername
			"/>
<msh:sectionTitle>
    <form action="print-extDisp_journal_period_reestr.do" method="post" target="_blank">
Отчет по созданым картам доп.диспансеризации за период ${param.beginDate}-${param.finishDate}
    <input type='hidden' name="sqlText" id="sqlText" value="${reestrExtDispCard_sql}"> 
    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Реестр карт по доп.диспансеризации за период с ${param.dateBegin} по ${param.dateEnd}.">
    <input type='hidden' name="sqlColumn" id="sqlColumn" value="">
    <input type='hidden' name="s" id="s" value="PrintService">
    <input type='hidden' name="m" id="m" value="printNativeQuery">
    <input type="submit" value="Печать"> 
    </form>

</msh:sectionTitle>
<msh:sectionContent>
				<msh:table name="reestrExtDispCard" 
				action="entityView-extDisp_card.do" viewUrl="entityView-extDisp_card.do?short=Short"
				idField="1">
					<msh:tableColumn columnName="Тип диспансеризации" property="1" />
					<msh:tableColumn columnName="Пользователь" property="2" />
					<msh:tableColumn columnName="Количество карт" property="3" />
				</msh:table>
				</msh:sectionContent>
			</msh:section>
	

		
		
		
			
		<% } else  {
		StringBuilder sqlAdd = new StringBuilder() ;
		sqlAdd.append(ActionUtil.setParameterFilterSql("dispType","edc.dispType_id", request)) ;
		request.setAttribute("sqlAppend", sqlAdd.toString()) ;
		%>
		
			<msh:section>
			<ecom:webQuery name="reestrExtDispCard" nameFldSql="reestrExtDispCard_sql" nativeSql="
select ved.name, createusername, count(edc.id)
from ExtDispCard edc
left join VocExtDisp ved on ved.id=edc.dispType_id
where edc.finishDate between to_date('${beginDate}','dd.mm.yyyy') and to_date('${finishDate}','dd.mm.yyyy')
${sqlAppend} 
group by ved.name, createusername
order by ved.name, createusername
			"/>
<msh:sectionTitle>
    <form action="print-extDisp_journal_period_reestr.do" method="post" target="_blank">
Отчет по созданым картам доп.диспансеризации за период ${param.beginDate}-${param.finishDate}
    <input type='hidden' name="sqlText" id="sqlText" value="${reestrExtDispCard_sql}"> 
    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Реестр карт по доп.диспансеризации за период с ${param.dateBegin} по ${param.dateEnd}.">
    <input type='hidden' name="sqlColumn" id="sqlColumn" value="">
    <input type='hidden' name="s" id="s" value="PrintService">
    <input type='hidden' name="m" id="m" value="printNativeQuery">
    <input type="submit" value="Печать"> 
    </form>

</msh:sectionTitle>
<msh:sectionContent>
				<msh:table name="reestrExtDispCard" 
				action="entityView-extDisp_card.do" viewUrl="entityView-extDisp_card.do?short=Short"
				idField="1">
					<msh:tableColumn columnName="Тип диспансеризации" property="2" />
					<msh:tableColumn columnName="Пользователь" property="3" />
					
				</msh:table>
				</msh:sectionContent>
			</msh:section>
		
	<%} %>
	<%} %>

	</tiles:put>
  <tiles:put name="javascript" type="string">
  	<script type="text/javascript" src="./dwr/interface/ExtDispService.js"></script>
  	<script type="text/javascript">

  //  checkFieldUpdate('typeGroup','${typeGroup}',1) ;
  //  checkFieldUpdate('typePaid','${typePaid}',1) ;
 //   checkFieldUpdate('typeDtype','${typeDtype}',3) ;
  //  checkFieldUpdate('typeDate','${typeDate}',2) ;
   
    function checkFieldUpdate(aField,aValue,aDefault) {
    	
    	eval('var chk =  document.forms[0].'+aField) ;
    	var max = chk.length ;
    	if ((+aValue)>max) {
    		chk[+aDefault-1].checked='checked' ;
    	} else {
    		chk[+aValue-1].checked='checked' ;
    	}
    }
    </script>
    </tiles:put>
</tiles:insert>