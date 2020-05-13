<%@page import="ru.ecom.mis.web.action.medcase.journal.AdmissionJournalForm"%>
<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="StacJournal" title="Пересчет отчетов по стационару"/>
  </tiles:put>
  <tiles:put name="side" type="string">

  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:form action="/stac_report_refresh_save.do"  defaultField="dateBegin" method="GET">
    <msh:panel>
      <msh:row>
        <msh:separator label="Параметры запуска" colSpan="5"/>
        
      </msh:row>
      <msh:row>
      <msh:textField property="refreshType"/>
        <msh:textField property="dateBegin" label="Период с" />
        <msh:textField property="dateEnd" label="по" />
           <td>
            <input type="submit" value="Пересчет" />
            
          </td>
      </msh:row>
    </msh:panel>
    </msh:form>
  </tiles:put>
</tiles:insert>