<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
    
   
    <tiles:put name='body' type='string'>

        
        	<input type='hidden' name ='statusState'  id='statusState'>
        	<input type='hidden' id='id' name='id' value='${param.id}'>
              
         <msh:ifInRole roles="/Policy/Mis/Claim/Operator">
        <table>
        <tr> <td>	
    	<input type='button' value='Начал делать' onclick="show('startWork')">
    	</td></tr><tr> <td>
    	<input type='button' value='Сделано' onclick="show('finish')">
    	</td></tr><tr> <td>
    	<input type='button' value='Отменить' onclick="show('cancel')">
    	</td></tr></table>
	</msh:ifInRole>
    <ecom:webQuery name="claimInfo" nameFldSql="claimInfoSql" nativeSql="
    select cl.id as clid,  upat.lastname ||' '|| upat.firstname||' '||upat.middlename, cl.description
,case when cl.canceldate is not null then 'Отменена ' || to_char(cl.canceldate, 'dd.MM.yyyy')||' '||to_char(cl.canceltime,'HH24:MI')
 when cl.finishdate is not null then 'Выполнена ' || to_char(cl.finishdate, 'dd.MM.yyyy')||' '||to_char(cl.finishtime,'HH24:MI')
 when cl.startworkdate is not null then 'В работе ' || to_char(cl.startworkdate, 'dd.MM.yyyy')||' '||to_char(cl.startworktime,'HH24:MI') ||' '||cl.startworkusername
 when cl.viewdate is not null then 'В процессе назначения ' || to_char(cl.viewdate, 'dd.MM.yyyy')||' '||to_char(cl.viewtime,'HH24:MI')
 when cl.createdate is not null then 'Новая c ' ||to_char(cl.createdate, 'dd.MM.yyyy')
 else 'ВАХВАХ' end as status
 ,cl.id as idvocid
, cl.phone
,cl.address as address
,coalesce(cl.executorcomment,'') as comment
from claim cl
left join workfunction uwf on uwf.id=cl.workfunction
left join vocworkfunction vwf on vwf.id=uwf.workfunction_id
left join worker uw on uw.id=uwf.worker_id
left join patient upat on upat.id=uw.person_id

where cl.id=${param.id}

"/>
 <msh:table styleRow="12" name="claimInfo" action="entityView-mis_claim.do" idField="1">
            <msh:tableColumn columnName="Заявка" property="3" />
            <msh:tableColumn columnName="Пользователь" property="2" />
            <msh:tableColumn columnName="Телефон" property="6" />
            <msh:tableColumn columnName="Место" property="7" />
            <msh:tableColumn columnName="Статус" property="4" />
            <msh:tableColumn columnName="Комментарий" property="8" />
      </msh:table>
    
      <tags:mis_claimStart name="New" status="id" type='claimType'/>
       <script type='text/javascript' src='./dwr/interface/ClaimService.js'></script>
   
	  	<script type="text/javascript">
	  	function show(status) {
	  		$('NewClaimId').value = '${param.id}';
	  		$('NewClaimStatus').value = status;
	  		$('statusState').value=status;
	  		showNewClaimStart();
	  	}
		  	</script>
        
    </tiles:put>

</tiles:insert>