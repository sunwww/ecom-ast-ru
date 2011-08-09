<%@ page import="ru.ecom.expomc.ejb.services.form.check.CheckPropertyForm"%>
<%@ page import="ru.ecom.expomc.web.actions.checkproperty.TableCheckPropertiesHelper"%>
<%@ page import="ru.ecom.expomc.ejb.services.form.check.ChecksTableForm"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Document" beginForm="exp_formatForm" title="Таблица проверок"/>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:sideLink key="ALT+1" params="id" action="/entityParentEdit-exp_format" name="⇧ Формат" />
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='body' type='string'>
        <%
            ChecksTableForm form = (ChecksTableForm) request.getAttribute("exp_checksTableForm") ;
            TableCheckPropertiesHelper.printForm(form, out);
        %>
    </tiles:put>

    <tiles:put name="style" type="string">
        <style type="text/css">
            h3 {
                margin-top: 1em ;
                background-color: #ddd ;
                border-top: 1px solid black ;
                border-left: 1px solid black ;
                padding-left: 1em ;
            }
            h3 span.prop {
                color: #a9a9a9;
            }
            h3 span.dbf {
                color: #a52a2a; ;
            }

            input  {
                margin-top: 0.5em ;
            }
            input.on, label.on {
                background-color: #deb887;
            }

        </style>
    </tiles:put>

    <tiles:put name="javascript" type="string">
        <script type="text/javascript">
            function onChange(aKey) {
                var input = $(aKey) ;
                var label = $(aKey+"Label") ;
                var on = input.checked ? "on":"off";
                label.className = on ;
                input.className = on ;
            }
        </script>
    </tiles:put>



</tiles:insert>