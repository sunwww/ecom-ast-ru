<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Medcard">Поиск медицинских карт</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
    
        <msh:sideMenu title="Печать">
        	<msh:sideLink action="/print-ticketfirst.do?s=PrintTicketService&amp;m=back" name="1-ая часть талона" 
        		params="" title="1-ая часть талона"/>
        	<msh:sideLink action="/print-ticketback.do?s=PrintTicketService&amp;m=back" name="2-ая часть талона" 
        		params="" title="2-ая часть талона"/>
        </msh:sideMenu>
        <msh:sideMenu>
                <tags:ticket_finds currentAction="medcardfind"/>
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='body' type='string'>
            <msh:form action="/poly_medcards.do" defaultField="number" method="GET">
                <msh:panel>
				    <msh:row>
					    <td colspan="1" class='label'>
					    <input type="hidden" name="exactMatchH" id="exactMatchH">
					    <input type='checkbox' name='exactMatch' id='exactMatch' onClick='javascript:document.location.href="poly_medcards.do?number="+$("number").value+"&exactMatch="+$("exactMatch").checked'>
					    </td>
					    <td colspan=3 class='onlyYear'>
						    <label id='exactMatchLabel' for="exactMatch"> Полное совпадение номера</label>
					    
					    </td>
				    </msh:row>
				    <msh:row>
                        <msh:textField property="number" label="Номер мед. карты" size="30"/>
                        <td><input type='submit' value='Найти'></td>
                    </msh:row>
                </msh:panel>
            </msh:form>
           <msh:form action="/smo_goingToSmo.do" defaultField="number" >
                <msh:panel>
                    <msh:row>
                    <msh:hidden property="id"/>
                        <msh:textField property="number" label="Номер СМО" size="30"/>
                        <td>        
							<input type="button" title="Поиск [CTRL+ENTER]" onclick="this.value=&quot;Поиск...&quot;;  this.form.action=&quot;entityView-smo_ticket.do&quot; ;this.form.id.value=this.form.number.value ; this.form.target=&quot;&quot; ; this.form.submit(); return true ;" value="Перейти" class="default" id="submitButton" autocomplete="off">
							<input type="button" value="Отменить" title="Отменить изменения [SHIFT+ESC]" onclick="this.disabled=true; window.history.back()" id="cancelButton">
						</td>
                    </msh:row>
                </msh:panel>
            </msh:form>
          <%
          if (request.getAttribute("fndNumber")!=null) {
          %>
          
            <msh:section title='Результат поиска'>
            <ecom:webQuery maxResult="150" name="list" nativeSql="
            select mc.id,mc.number as mcnumber,p.lastname,p.firstname,p.middlename,p.birthday 

,list(case when att.dateto is null then to_char(att.datefrom,'dd.mm.yyyy')||' ('||vat.code||') '||ml.name else null end) as lpuname 
,list(case when att.dateto is null then ma.number else null end) as areaname 
,(select case when pf1.id is null then '-' else 'от '||to_char(pf1.checkdate,'dd.mm.yyyy') || coalesce(' дата смерти: '||to_char(pf1.deathdate,'dd.mm.yyyy'),'')  
||case 
when pf1.lpuattached!='${default_lpu}' then ' прикреплен к ЛПУ ' ||pf1.lpuattached ||' с '||to_char(pf1.attacheddate,'dd.mm.yyyy')  
when pf1.lpuattached='${default_lpu}' then ' прикреплен к ЛПУ с '||to_char(pf1.attacheddate,'dd.mm.yyyy')  
else '' end end from PatientFond pf1  
where pf1.id=(select max(pf.id) from PatientFond pf where pf.lastname=p.lastname and pf.firstname=p.firstname and pf.middlename=p.middlename and pf.birthday=p.birthday )  ) as fondinfo  
from Medcard mc 
left join Patient p on mc.person_id = p.id 
left join LpuAttachedByDepartment att on att.patient_id=p.id 
left join Mislpu ml on ml.id=att.lpu_id 
left join lpuarea ma on ma.id=att.area_id 
left join VocAttachedType vat on vat.id=att.AttachedType_id 
where ${findMedcardnumberSql}
group by mc.id,mc.number, p.id,p.lastname,p.firstname,p.middlename,p.birthday,p.patientSync, p.colorType 
order by p.lastname, p.firstname 
            "/>
                <msh:table 
                	viewUrl="entityShortView-poly_medcard.do"
                	 editUrl="entityParentEdit-poly_medcard.do" deleteUrl="entityParentDeleteGoParentView-poly_medcard.do" name="list" action="entityView-poly_medcard.do" idField="1" disableKeySupport="true">
                    <msh:tableColumn columnName="№мед.карты" property="2"/>                    
                    <msh:tableColumn columnName="Фамилия" property="3"/>
                    <msh:tableColumn columnName="Имя" property="4"/>
                    <msh:tableColumn columnName="Отчество" property="5"/>
                    <msh:tableColumn columnName="Дата рождения" property="6"/>
		            <msh:tableColumn columnName="Дата прикрепления (тип) и ЛПУ" property="7" />
		            <msh:tableColumn columnName="Участок" property="8" />
		            <msh:tableColumn columnName="Проверка по базе фонда" property="9" />
                </msh:table>
            </msh:section>
        <%} %>
    </tiles:put>
    <tiles:put name="javascript" type="string">
    <script type="text/javascript">
    	
    	$('number').focus() ;
    	$('number').select() ;

  		if ((+'${exactMatch}')==1) {
    		$('exactMatch').checked='checked' ;
    		$('exactMatchH').value='1' ;
    	} else {
    		$('exactMatch').checked='' ;
    		$('exactMatchH').value='0' ;
    	}
  	
  	</script>
  
     </tiles:put>

</tiles:insert>