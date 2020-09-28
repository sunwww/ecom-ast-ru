<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
    <link rel='stylesheet' type='text/css' href='/skin/css/bootstrap.css' />
<tiles:put name='body' type='string'>

<msh:form action="entitySaveGoView-mis_dynamicDocument.do" defaultField="type" method="POST">
   <msh:hidden property="id"/>
   <msh:hidden property="type"/>
   <msh:hidden property="medCase"/>
   <msh:hidden property="saveType"/>
   <msh:hidden property="content"/>
</msh:form>
    <form id="dynamicForm"></form>
    <div id="res" class="alert"></div>
</tiles:put>

<tiles:put name='side' type='string'>
    <msh:sideMenu>

    </msh:sideMenu>
</tiles:put>

    <tiles:put name="javascript" type="string">
        <script type="text/javascript" src="./dwr/interface/DynamicService.js"></script>
    <script type="text/javascript">
        onload = function fillForm() {
            if (+'${param.type}'>0) $('type').value='${param.type}';
            var typeId = $('type').value ;
            if (typeId) {
                DynamicService.getContentFromType(typeId, {
                    callback: function(jsForm) {
                        jsForm = JSON.parse(jsForm);
                        if ($('content').value) {
                            jsForm.value = JSON.parse($('content').value);
                        }
                        jsForm.onSubmit=function(errs, vals) {
                            if (errs) {
                                alert(errs);
                            }
                            $('content').value = JSON.stringify(vals);
                            if (confirm("save?")) document.forms[0].submit();
                        };
                        jQuery('#dynamicForm').jsonForm(jsForm);
                    }
                });
            }
        };

    </script>
    </tiles:put>

</tiles:insert>