<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Voc">Список справочников</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
    	<tags:voc_menu currentAction="main"/>
    </tiles:put>
      <tiles:put name="style" type="string">
    <style type="text/css">tr.systemVoc td {
            color: red ;
        }</style>
  </tiles:put>
    
    <tiles:put name='body' type='string' >
        <msh:table name="list" action="js-ecom_vocEntity-edit.do" decorator="decorator">
            <msh:tableColumn columnName="<a href='js-ecom_vocEntity-list.do?sort=comment'>Комментарий</a>" property="comment" />
            <msh:tableColumn columnName="<a href='js-ecom_vocEntity-list.do?sort=classname'>Класс</a>" property="classname" />
            <msh:tableColumn columnName="<a href='js-ecom_vocEntity-list.do?sort=count'>Количество</a>" property="count" cssClass="countColumn"/>
        </msh:table>
        <msh:tableNotEmpty name="list">
      <div class="h3">
        <h3>Легенда</h3>
      </div>
      <table class="tabview">
        <tr class="systemVoc">
          <td>―</td>
          <td>Системный класс</td>
        </tr>
        <tr class="current">
          <td />
          <td>Текущий справочник</td>
        </tr>
      </table>
    </msh:tableNotEmpty>
    </tiles:put>
</tiles:insert>