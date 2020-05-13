<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="psych_suicideMessageForm" title="Список протоколов по суицидальной попытке"/>
  </tiles:put>
  <tiles:put name="side" type="string">

  </tiles:put>
  <tiles:put name="body" type="string">
  	<ecom:webQuery name="listd" nativeSql="select s.id,pcc.cardNumber
  	,s.fulfilmentDate,s.notes
  	from Suicide s
  	left join PsychiatricCareCard pcc on pcc.id=s.careCard_id   	where suiMessage_id=${param.id}"/>
    <msh:table name="listd" action="entityParentView-psych_suicide.do" idField="1">
              <msh:tableColumn property="sn" columnName="#"/>
              <msh:tableColumn property="1" columnName="ИД"/>
              <msh:tableColumn property="2" columnName="№карты"/>
              <msh:tableColumn property="3" columnName="Дата совершения"/>
              <msh:tableColumn property="4" columnName="Описание" />
    </msh:table>
  </tiles:put>
</tiles:insert>

