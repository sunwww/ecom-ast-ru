<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Template" title="Загруженные документы" />
  </tiles:put>
  <tiles:put name="side" type="string">
    
    <msh:sideMenu title="Добавить">
      <msh:sideLink roles="/Policy/Mis/Template/Category/Create" key="ALT+N" action="/templateDocument-import.do" name="Добавить файл" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
  <ecom:webQuery name="docList" nativeSql="select id, referenceto from document where dtype='TemplateExternalDocument' order by createDate desc" />
    <msh:table name="docList" action="entityView-doc_externalDocument.do" idField="1">
      <msh:tableColumn columnName="Файл" property="2" />
    </msh:table>
  </tiles:put>
</tiles:insert>

