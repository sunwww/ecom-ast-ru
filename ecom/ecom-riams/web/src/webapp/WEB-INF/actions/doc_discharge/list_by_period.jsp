<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >
	<tiles:put name='title' type='string'>
		<msh:title mainMenu="Journals" >Выписки</msh:title>
	</tiles:put>
	<tiles:put name='side' type='string'>
	</tiles:put>
	<tiles:put name='body' type='string' >
  <%
%>
		<msh:form action="doc_list_by_period.do" defaultField="beginDate">
			<msh:panel>
			<msh:row>
				<msh:textField property="beginDate" label="c"/>
				<msh:textField property="finishDate" label="по"/>
			</msh:row>
			<msh:row>
				<msh:autoComplete property="patient" label="Пациент" vocName="patient" fieldColSpan="3" horizontalFill="true"/>
			</msh:row>
			<msh:row>
				<msh:autoComplete property="workFunction" label="Рабочая функция" vocName="workFunction" fieldColSpan="3" horizontalFill="true"/>
			</msh:row>

        <msh:row>
	        <td class="label" title="Оплата (typePaid)" colspan="1"><label for="typeGroupName" id="typeGroupLabel">Группировки общие:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typePaid" value="1"> ОМС
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typePaid" value="2"> другие
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typePaid" value="3"> все
	        </td>
        </msh:row>

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
		%>
		<ecom:webQuery name="listDischarge" nativeSql="
			select d.id as did,d.history
      		, case when d.dtype='DirectionDocument' then 'Направление' 
      		when d.dtype='DischargeDocument' then 'Выписка'
      		when d.dtype='DischargeDiagnostDocument' then 'Выписка диагностическая'
      		when d.dtype='BaseMedicalExamination' then 'Паспорт здоровья/Медосмотр'
      		when d.dtype='DirectionToMicrobiologAnalysis' then 'Направление на микробиологическое исследование'
      		else '-' end,d.diagnosis
      		from Document d 
      		left join MedCase v on v.id=d.medCase_id
      		where v.dateStart between to_date('${beginDate}','dd.mm.yyyy') and to_date('${finishDate}','dd.mm.yyyy')
      		${sqlAdd}
		"/>
			<msh:table name="docum" action="entitySubclassView-doc_document.do" 
      	 	 viewUrl="entitySubclassShortView-doc_document.do" idField="1" hideTitle="true">
      			<msh:tableColumn property="3"/>
      		</msh:table>
			<%} %>
	</tiles:put>
  <tiles:put name="javascript" type="string">
  	<script type="text/javascript">

    checkFieldUpdate('typeGroup','${typeGroup}',1) ;
    checkFieldUpdate('typeView','${typeView}',1) ;
    checkFieldUpdate('typeDtype','${typeDtype}',3) ;
    checkFieldUpdate('typeDate','${typeDate}',2) ;
    
    
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