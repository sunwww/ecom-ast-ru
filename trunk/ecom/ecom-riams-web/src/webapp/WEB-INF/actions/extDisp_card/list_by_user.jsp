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
	String dateChange =ActionUtil.updateParameter("ExtDispAction","dateChange","1", request) ;
 	String usernameChange =ActionUtil.updateParameter("ExtDispAction","usernameChange","1", request) ;
if (request.getParameter("short")==null) {
%>
 <msh:form action="extDisp_journal_user.do" defaultField="beginDate" method="GET"> 
	<msh:panel>
		<msh:row>
			<msh:textField property="beginDate" label="c"/>
			<msh:textField property="finishDate" label="по"/>
		</msh:row>
		<msh:row>
			<msh:autoComplete property="dispType" label="Тип доп. диспансеризации" vocName="vocExtDisp" horizontalFill="true" fieldColSpan="3"/>
		</msh:row>
		 <msh:row>
        <td class="label" title="Пользователь (usernameChange)" colspan="1"><label for="usernameChangeName" id="usernameChangeLabel">Пользователь:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="usernameChange" value="1">  создавший
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="usernameChange" value="2">  редактирующий
        </td>
      </msh:row>
            <msh:row>
        <td class="label" title="Дата (dateChange)" colspan="1"><label for="dateChangeName" id="dateChangeLabel">Дата:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="dateChange" value="1">  создания
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="dateChange" value="2">  редактирования
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="dateChange" value="3">  начала
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="dateChange" value="4">  окончания
        </td>

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
}		String beginDate = request.getParameter("beginDate") ;
		if (beginDate!=null && !beginDate.equals("")) {
		String finishDate = request.getParameter("finishDate") ;
		String dispType = request.getParameter("dispType") ;
		if (finishDate==null || finishDate.equals("")) {
			finishDate=beginDate ;
		}
		request.setAttribute("beginDate", beginDate);
		request.setAttribute("finishDate", finishDate) ;
		String idPar = request.getParameter("id");
		String dateSearch="";
		String usernameSearch="";
		if (dateChange!=null) {
			if (dateChange.equals("1")) {
				dateSearch = "edc.createDate" ;
			} else if (dateChange.equals("2")) {
				dateSearch = "edc.editDate" ;
			} else if (dateChange.equals("3")) {
				dateSearch = "edc.startDate" ;
			} else if (dateChange.equals("4")) {
				dateSearch = "edc.finishDate" ;
			} 
		}
			if (usernameChange.equals("1")) {
				usernameSearch = "edc.createUsername" ;
			} else if (usernameChange.equals("2")) {
				usernameSearch = "edc.editUsername" ;
			}  
			request.setAttribute("usernameSearch", usernameSearch) ;
		
		if (idPar!=null&&!idPar.equals("")) { 			
			StringBuilder sqlAdd = new StringBuilder() ;
			if (idPar.equals("-")) {
				sqlAdd.append("(").append(usernameSearch).append(" is null or ").append(usernameSearch).append("='')");
			} else {
				sqlAdd.append(usernameSearch).append("='").append(idPar).append("'");
			}
			
			sqlAdd.append(" and ").append(dateSearch).append(" between to_date('").append(beginDate).append("','dd.mm.yyyy') and to_date('").append(finishDate).append("','dd.mm.yyyy') ") ;
			sqlAdd.append(ActionUtil.setParameterFilterSql("dispType","edc.dispType_id", request)) ;
			request.setAttribute("sqlAdd", sqlAdd.toString()) ;
		%>
			<msh:section>
			<ecom:webQuery name="reestrExtDispCardId" nameFldSql="reestrExtDispCard_sqlId" nativeSql="select edc.id, p.patientinfo,edc.startdate, edc.finishdate from extdispcard edc left join patient p on p.id=edc.patient_id 
 where  ${sqlAdd} "/>
<msh:sectionTitle>
Отчет по картам доп.диспансеризации (по пользователю) за период ${param.beginDate}-${param.finishDate}
</msh:sectionTitle>
<msh:sectionContent>
				<msh:table name="reestrExtDispCardId" action="entityView-extDisp_card.do" viewUrl="entityView-extDisp_card.do?short=Short" 
				idField="1"  >
				 <msh:tableColumn columnName="#" property="sn"/>
					<msh:tableColumn columnName="Пациент" property="2" />
					<msh:tableColumn columnName="Дата начала" property="3" />
					<msh:tableColumn columnName="Дата окончания" property="4" />
				</msh:table>
				</msh:sectionContent>
			</msh:section>
		<%} else {
			StringBuilder sqlAdd = new StringBuilder() ;
			sqlAdd.append(dateSearch).append(" between to_date('").append(beginDate).append("','dd.mm.yyyy') and to_date('").append(finishDate).append("','dd.mm.yyyy') ") ;
			request.setAttribute("sqlAdd", sqlAdd.toString()) ;
		if (dispType==null || dispType.equals("")||dispType.equals("0")){ %>
			<msh:section> 																		<!--Группировка по типам ДД  -->
			<ecom:webQuery name="reestrExtDispCardByType" nameFldSql="reestrExtDispCard_sqlByType" nativeSql="
select coalesce(case when ${usernameSearch}='' then null else ${usernameSearch} end,'-')||'&dispType='||edc.disptype_id as idPar, ved.name, coalesce(${usernameSearch},'-'), count(edc.id)
from ExtDispCard edc
left join VocExtDisp ved on ved.id=edc.dispType_id
where ${sqlAdd} 
group  by idPar,ved.name, ${usernameSearch}
order  by ved.name, ${usernameSearch}
			"/>
<msh:sectionTitle>
Отчет по картам доп.диспансеризации (по типам ДД) за период ${param.beginDate}-${param.finishDate}
</msh:sectionTitle>
<msh:sectionContent>
				<msh:table name="reestrExtDispCardByType"
				viewUrl="extDisp_journal_user.do?beginDate=${param.beginDate}&finishDate=${param.finishDate}&short=Short"
				action="extDisp_journal_user.do?beginDate=${param.beginDate}&finishDate=${param.finishDate}"
				 idField="1">
				 <msh:tableColumn columnName="#" property="sn"/>
					<msh:tableColumn columnName="Тип диспансеризации" property="2" />
					<msh:tableColumn columnName="Пользователь" property="3" />
					<msh:tableColumn columnName="Количество карт" property="4" isCalcAmount="true"/>
				</msh:table>
				</msh:sectionContent>
			</msh:section>	
			
			<msh:section> 											<!--Без группировки по типам, общее кол-во по пользователям  -->
			<ecom:webQuery name="reestrExtDispCardAll" nameFldSql="reestrExtDispCard_sqlAdd" nativeSql="
select ${usernameSearch}, count(edc.id)
from ExtDispCard edc
where ${sqlAdd} 
group by ${usernameSearch}
order by ${usernameSearch}
			"/>
<msh:sectionTitle>
Отчет по картам доп.диспансеризации за период ${param.beginDate}-${param.finishDate}
</msh:sectionTitle>
<msh:sectionContent>
				<msh:table name="reestrExtDispCardAll" action="javascript:void(0)" idField="1">
				 <msh:tableColumn columnName="#" property="sn"/>
					<msh:tableColumn columnName="Пользователь" property="1" />
					<msh:tableColumn columnName="Количество карт" property="2" isCalcAmount="true"/>
					
				</msh:table>
				</msh:sectionContent>
			</msh:section>		
		<% } else  {		
		sqlAdd.append(ActionUtil.setParameterFilterSql("dispType","edc.dispType_id", request)) ;
		request.setAttribute("sqlAdd", sqlAdd.toString()) ;
		%>
		
			<msh:section> 																		<!-- По заданному типу ДД -->
			<ecom:webQuery name="reestrExtDispCard" nameFldSql="reestrExtDispCard_sql" nativeSql="
select ${usernameSearch}||'&dispType='||edc.disptype_id as idPar, ved.name, ${usernameSearch}, count(edc.id)
from ExtDispCard edc
left join VocExtDisp ved on ved.id=edc.dispType_id
where ${sqlAdd} 
group by idPar, ved.name, ${usernameSearch}
order by ved.name, ${usernameSearch}
			"/>
<msh:sectionTitle>
Отчет по картам доп.диспансеризации за период ${param.beginDate}-${param.finishDate}
</msh:sectionTitle>
<msh:sectionContent>
				<msh:table name="reestrExtDispCard" 
				viewUrl="extDisp_journal_user.do?beginDate=${param.beginDate}&finishDate=${param.finishDate}&short=Short" idField="1"
				action="extDisp_journal_user.do?beginDate=${param.beginDate}&finishDate=${param.finishDate}"  >
				 <msh:tableColumn columnName="#" property="sn"/>
					<msh:tableColumn columnName="Тип диспансеризации" property="2" />
					<msh:tableColumn columnName="Пользователь" property="3" />
					<msh:tableColumn columnName="Количество карт" property="4" />
				</msh:table>
				</msh:sectionContent>
			</msh:section>
		
	<%} %>
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
 checkFieldUpdate('dateChange','${dateChange}',1) ;  
 checkFieldUpdate('usernameChange','${usernameChange}',1) ;  
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