<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='body' type='string'>
    	<ecom:webQuery nativeSql="select d.id,ri.name,d.series, d.number,d.startActualDate,d.endActualDate, (select count(*) from MedPolicy as m where d.insuranceCode=m.company_id and d.number=m.polNumber and (d.series is null or d.series=m.series)) from DmcExistMedicalPolicy as d,Patient as p left join REG_IC as ri on ri.omccode=d.insurancecode where p.id=${param.id} and d.lastname=p.lastname and d.firstname=p.firstname and d.middlename=p.middlename and (d.birthday is null or d.birthday=p.birthday) and (d.snils is null or d.snils='' or d.snils=p.snils)"
    	 name="list_existPatient"/>
        <msh:table name="list_existPatient"  action="js-mis_medPolicyDmc-createExist.do?pat=${param.id}" idField="1">
        	<msh:tableColumn property="sn" columnName="#"/>
        	<msh:tableColumn property="1" columnName="Id"/>
        	<msh:tableColumn property="2" columnName="Страховая компания"/>
        	<msh:tableColumn property="3" columnName="Серия"/>
        	<msh:tableColumn property="4" columnName="Номер"/>
        	<msh:tableColumn property="5" columnName="Дата начала"/>
        	<msh:tableColumn property="6" columnName="Дата окончания"/>
        	<msh:tableColumn property="7" columnName="Есть ли полис в базе?"/>
        </msh:table>


    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>

        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Patient" beginForm="mis_patientForm" title="Выбор полиса ДМС"/>
    </tiles:put>


</tiles:insert>