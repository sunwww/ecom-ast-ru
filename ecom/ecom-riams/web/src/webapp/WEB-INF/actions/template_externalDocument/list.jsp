<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Template" guid="65127a6f-d6d3-4b8e-b436-c6aeeaea35ae" title="Загруженные документы" />
  </tiles:put>
  <tiles:put name="side" type="string">
    
    <msh:sideMenu title="Добавить" guid="60616958-11ef-48b0-bec7-f6b1d0b8463f">
      <msh:sideLink roles="/Policy/Mis/Template/Category/Create" key="ALT+N" action="/templateDocument-import.do" name="Добавить файл" guid="1faa5477-419b-4f77-8379-232e33a61922" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
  <ecom:webQuery name="docList" nativeSql="select id, referenceto from document where dtype='TemplateExternalDocument' order by createDate desc" />
    <msh:table name="docList" action="entityView-doc_externalDocument.do" idField="1" guid="3c4adc65-cfce-4205-a2dd-91ba8ba87543">
      <msh:tableColumn columnName="Файл" property="2" guid="715694de-3af4-4395-b777-2cb19bdbcf62" />
    </msh:table>
  </tiles:put>
</tiles:insert>

