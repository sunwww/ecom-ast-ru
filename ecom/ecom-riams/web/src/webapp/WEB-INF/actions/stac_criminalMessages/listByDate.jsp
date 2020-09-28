<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Journals" title="Журнал сообщений в полицию" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <tags:style_currentMenu currentAction="stac_criminalMessages" />
    <tags:mis_journal />
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:section>
      <msh:sectionTitle>Список сообщений<msh:link action="journal_militiaMessage.do">Выбрать другую дату</msh:link></msh:sectionTitle>
      <msh:sectionContent>
        <ecom:webQuery name="journal_militia" nativeSql="
    select pm.id, pm.phoneDate
    ,vpht.name||coalesce(' '||vpmst.name,'')
    ,to_char(pm.whenDateEventOccurred,'dd.mm.yyyy')||' '||cast(pm.whenTimeEventOccurred as varchar(5)) as whenevent
    ,pm.place as pmplace
    ,coalesce(vpme.name,pm.recieverFio) as reciever
    ,vpmo.name as vphoname,wp.lastname as wplastname
    ,p.lastname||' '||p.firstname||' '||p.middlename||' г.р.'||to_char(p.birthday,'dd.mm.yyyy') as fiopat
    ,coalesce(vpmorg.name,pm.phone,pm.recieverOrganization) as organization
    from PhoneMessage pm 
    left join VocPhoneMessageType vpht on vpht.id=pm.phoneMessageType_id
    left join VocPhoneMessageSubType vpmst on vpmst.id=pm.phoneMessageSubType_id
    left join VocPhoneMessageOrganization vpmorg on vpmorg.id=pm.organization_id
    left join VocPhoneMessageEmploye vpme on vpme.id=pm.recieverEmploye_id
    left join VocPhoneMessageOutcome vpmo on vpmo.id=pm.outcome_id
    left join WorkFunction wf on wf.id=pm.workFunction_id
    left join Worker w on w.id=wf.worker_id
    left join Patient wp on wp.id=w.person_id
    left join medcase m on m.id=pm.medCase_id
    left join Patient p on p.id=m.patient_id
    left join mislpu ml on ml.id=m.department_id
    left join mislpu ml1 on ml1.id=w.lpu_id
        where pm.dtype='CriminalPhoneMessage' ${paramsPeriod} 
        ${addParam}
        ${emergency} ${department} ${pigeonHole}
        ${messageType} ${messageSubType}
        " 
        />
    <msh:table name="journal_militia"
    viewUrl="entityShortView-stac_criminalMessages.do" 
     action="entityParentView-stac_criminalMessages.do" idField="1" >
     <msh:tableColumn property="sn" columnName="#"/>
      <msh:tableColumn columnName="Дата" property="2" />
      <msh:tableColumn property="9" columnName="Пациент"/>
      <msh:tableColumn columnName="Тип" property="3" />
      <msh:tableColumn columnName="Когда" property="4" />
      <msh:tableColumn columnName="Место" property="5" />
      <msh:tableColumn columnName="Фамилия принявшего" property="6" />
      <msh:tableColumn columnName="Фамилия передавшего" property="8" />
      <msh:tableColumn columnName="Исход" property="7" />
    </msh:table>      </msh:sectionContent>
    </msh:section>
  </tiles:put>
</tiles:insert>

