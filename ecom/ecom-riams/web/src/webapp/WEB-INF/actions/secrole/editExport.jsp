<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Config" title="Экспорт ролей" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu />
        <tags:menuJaas currentAction="roles"/>

  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:section>
      <msh:sectionTitle>Список доступных ролей</msh:sectionTitle>
      <msh:sectionContent>
        <msh:table selection="multiply" name="roles" action="roleView.do" idField="id" noDataMessage="Нет ролей">
          <msh:tableNotEmpty>
            <tr>
              <th colspan="4">
                <msh:toolbar>
                  <a href="javascript:exportRole()">Экспорт</a>
                </msh:toolbar>
              </th>
            </tr>
          </msh:tableNotEmpty>
          <msh:tableColumn columnName="Ключ" property="key" />
          <msh:tableColumn columnName="Название" property="name" />
          <msh:tableColumn columnName="Комментарий" property="comment" />
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

