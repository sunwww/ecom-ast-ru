<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

    <tiles:put name="title" type="string">
        <ecom:titleTrail beginForm="smo_visitForm" mainMenu="Patient" title="Визит" />
    </tiles:put>
    <tiles:put name="body" type="string">
        <ecom:webQuery name="cardList" nameFldSql="cardList_sql" nativeSql="select ac.id, act.name, to_char(ac.startDate,'dd.MM.yyyy') as priemDate
  ,ac.ballsum as f4_ballsum
  from assessmentCard ac
  left join assessmentcardtemplate act on act.id=ac.template
  where ac.medcase_id=${param.id} order by ac.startDate desc"/>
        <msh:section createRoles="/Policy/Mis/AssessmentCard/Create" createUrl="entityParentPrepareCreate-mis_assessmentCard.do?id=${param.patient}&visit=${param.id}"
                     title="Карты оценки">
            <msh:table name="cardList" action="entityParentView-mis_assessmentCard.do" idField="1">

                <msh:tableColumn columnName="Название" property="2" />
                <msh:tableColumn columnName="Дата приема" property="3" />
                <msh:tableColumn columnName="Сумма баллов" property="4" />
            </msh:table>
        </msh:section>
    </tiles:put>
</tiles:insert>