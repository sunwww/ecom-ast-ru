<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Config" title="Экспорт ролей" guid="0fa02f36-8027-4a76-9b45-ff338ea37f71" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="8b1ff9d5-6bba-41ae-a98d-f7d7d21aaf85" />
        <tags:menuJaas currentAction="roles"/>

  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:section guid="a63b5b8e-d93b-4e1c-b6f6-3998ae39f820">
      <msh:sectionTitle guid="44a16937-4bf3-4963-9f54-c123e149cfe3">Список доступных ролей</msh:sectionTitle>
      <msh:sectionContent guid="3366e502-6bdf-447f-bb3e-66f842124923">
        <msh:table selection="multiply" name="roles" action="roleView.do" idField="id" noDataMessage="Нет ролей" guid="ee35223c-5c9a-443e-98ce-638bef36ba16">
          <msh:tableNotEmpty guid="d3b057f3-5d21-44d9-9a7b-4080a6bfc8a7">
            <tr>
              <th colspan="4">
                <msh:toolbar guid="2693efd9-7a0a-42a8-a1d0-9fd0d17be899">
                  <a href="javascript:exportRole()">Экспорт</a>
                </msh:toolbar>
              </th>
            </tr>
          </msh:tableNotEmpty>
          <msh:tableColumn columnName="Ключ" property="key" guid="965ef6c7-18aa-4a56-9ca9-7daea032a55f" />
          <msh:tableColumn columnName="Название" property="name" guid="71fd4705-1c89-4264-a53a-0d2d015a488d" />
          <msh:tableColumn columnName="Комментарий" property="comment" guid="10a4b741-e1c1-4407-89f3-a9a1d4bb567d" />
        </msh:table>
      </msh:sectionContent>
    </msh:section>
  </tiles:put>
  <tiles:put name="javascript" type="string">
    <script type="text/javascript">function exportRole() {
                var ids = theTableArrow.getInsertedIdsAsParams("id") ;
                if (ids) {
                	//alert(ids) ;
                	var frm = document.forms[0] ;
                    frm.action = 'serviceRole-export.do?'+ids;
                    frm.method="post" ;
                    frm.target="_blank";
                    frm.submit() ;
                } else {
                    alert("Нет выделенных ролей");
                }
            }</script>
  </tiles:put>
</tiles:insert>

