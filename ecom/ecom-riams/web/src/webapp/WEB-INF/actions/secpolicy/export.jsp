<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
  </tiles:put>
  <tiles:put name="side" type="string">
 <pre> 
 ${result}
 </pre>
  </tiles:put>
  <tiles:put name="javascript" type="string">
  </tiles:put>
</tiles:insert>

