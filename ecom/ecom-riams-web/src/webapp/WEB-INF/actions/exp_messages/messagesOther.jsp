<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
       
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='body' type='string' >
    	<msh:section title='Общая информация'>
    			<table ><tr><td>
           <ecom:webQuery name='test' 
             hql="
     select 'Без полисов',count(*), sum(e.casePrice) from RegistryEntry e where time=${param.id}
      and (spolis is null or spolis='' or npolis is null or npolis='' or insuranceCompany is null or insuranceCompany='')
              " />
	           <msh:table name="test" action="exp_messageView.do" idField="1"> 
	           	<msh:tableColumn columnName="№" property="sn"/>
	           	<msh:tableColumn columnName="" property="1"/>
	           	<msh:tableColumn columnName="Количество" property="2"/>
	           	<msh:tableColumn columnName="Сумма" property="3"/>
           </msh:table>  
			</td><td style='padding-left: 1em'>
           <ecom:webQuery name='test' 
             hql="
     select 'Все',count(*), sum(e.casePrice) from RegistryEntry e where time=${param.id}
              " />
	           <msh:table name="test" action="exp_messageView.do" idField="1"> 
	           	<msh:tableColumn columnName="№" property="sn"/>
	           	<msh:tableColumn columnName="" property="1"/>
	           	<msh:tableColumn columnName="Количество" property="2"/>
	           	<msh:tableColumn columnName="Сумма" property="3"/>
           </msh:table>  
			</td><td style='padding-left: 1em'>
           <ecom:webQuery name='test' 
             hql="
     select 'Без цены',count(*), sum(e.casePrice) from RegistryEntry e where time=${param.id}
     and (casePrice is null or casePrice = 0)
              " />
	           <msh:table name="test" action="exp_messageView.do" idField="1"> 
	           	<msh:tableColumn columnName="№" property="sn"/>
	           	<msh:tableColumn columnName="Без цены" property="1"/>
	           	<msh:tableColumn columnName="Количество" property="2"/>
	           	<msh:tableColumn columnName="Сумма" property="3"/>
           </msh:table>  
           </td></tr></table>
    	</msh:section>
    	
    	<msh:section title='По реестрам'>
           <ecom:webQuery name='test' 
             hql="select e.insuranceCompany, e.billNumber, e.registryNumber,  sum(casePrice), count(*) from RegistryEntry e where time=${param.id}
              group by e.insuranceCompany, e.billNumber, e.registryNumber 
              order by e.insuranceCompany, e.billNumber, e.registryNumber " />
	           <msh:table name="test" action="exp_messageView.do" idField="1"> 
	           	<msh:tableColumn columnName="№" property="sn"/>
	           	<msh:tableColumn columnName="Страховая компания" property="1"/>
	           	<msh:tableColumn columnName="Счет" property="2"/>
	           	<msh:tableColumn columnName="Реестр" property="3"/>
	           	<msh:tableColumn columnName="Сумма" property="4"/>
           </msh:table>  
        </msh:section>
        
    	<msh:section title='По счетам'>
           <ecom:webQuery name='test' 
             hql="select e.insuranceCompany, e.billNumber,  sum(casePrice), count(*) from RegistryEntry e where time=${param.id}
              group by e.insuranceCompany, e.billNumber 
              order by e.insuranceCompany, e.billNumber" />
	           <msh:table name="test" action="exp_messageView.do" idField="1"> 
	           	<msh:tableColumn columnName="№" property="sn"/>
	           	<msh:tableColumn columnName="Страховая компания" property="1"/>
	           	<msh:tableColumn columnName="Счет" property="2"/>
	           	<msh:tableColumn columnName="Сумма" property="3"/>
	           	<msh:tableColumn columnName="Количество" property="4"/>
           </msh:table>  
        </msh:section>
        
    	<msh:section title='По страховым компаниям'>
           <ecom:webQuery name='test' 
             hql="select e.insuranceCompany, count(*), sum(casePrice) from RegistryEntry e where time=${param.id}
              group by insuranceCompany" />
	           <msh:table name="test" action="exp_messageView.do" idField="1"> 
	           	<msh:tableColumn columnName="№" property="sn"/>
	           	<msh:tableColumn columnName="Страховая компания" property="1"/>
	           	<msh:tableColumn columnName="Количество случаев" property="2"/>
	           	<msh:tableColumn columnName="Сумма" property="3"/>
           </msh:table>  
    	</msh:section>

    	<msh:section title='Страховая компания и социальная группа'>
           <ecom:webQuery name='test' 
             hql="select e.insuranceCompany, e.sgroup, count(*), sum(casePrice) from RegistryEntry e where time=${param.id}
              group by insuranceCompany,sgroup" />
	           <msh:table name="test" action="exp_messageView.do" idField="1"> 
	           	<msh:tableColumn columnName="№" property="sn"/>
	           	<msh:tableColumn columnName="Страховая компания" property="1"/>
	           	<msh:tableColumn columnName="Социальная группа" property="2"/>
	           	<msh:tableColumn columnName="Количество случаев" property="3"/>
	           	<msh:tableColumn columnName="Сумма" property="4"/>
           </msh:table>  
    	</msh:section>

    	<msh:section title='По социальным группам'>
           <ecom:webQuery name='test' 
             hql="select e.sgroup, count(*), sum(casePrice) from RegistryEntry e where time=${param.id}
              group by sgroup" />
	           <msh:table name="test" action="exp_messageView.do" idField="1"> 
	           	<msh:tableColumn columnName="№" property="sn"/>
	           	<msh:tableColumn columnName="Социальная группа" property="1"/>
	           	<msh:tableColumn columnName="Количество случаев" property="2"/>
	           	<msh:tableColumn columnName="Сумма" property="3"/>
           </msh:table>  
    	</msh:section>

    	<msh:section title='По отделениям'>
           <ecom:webQuery name='test' 
             hql="select e.depType, count(*), sum(casePrice) from RegistryEntry e where time=${param.id}
              group by depType" />
	           <msh:table name="test" action="exp_messageView.do" idField="1"> 
	           	<msh:tableColumn columnName="№" property="sn"/>
	           	<msh:tableColumn columnName="Отделение" property="1"/>
	           	<msh:tableColumn columnName="Количество случаев" property="2"/>
	           	<msh:tableColumn columnName="Сумма" property="3"/>
           </msh:table>  
    	</msh:section>

    	<msh:section title='По отделениям (внутренний код)'>
           <ecom:webQuery name='test' 
             hql="select e.internalDepCode, count(*), sum(casePrice), sum(bedDays) from RegistryEntry e where time=${param.id}
              group by internalDepCode" />
	           <msh:table name="test" action="exp_messageView.do" idField="1"> 
	           	<msh:tableColumn columnName="№" property="sn"/>
	           	<msh:tableColumn columnName="Отделение" property="1"/>
	           	<msh:tableColumn columnName="Количество случаев" property="2"/>
	           	<msh:tableColumn columnName="Сумма" property="3"/>
	           	<msh:tableColumn columnName="Койко-дней" property="4"/>
           </msh:table>  
    	</msh:section>

	<msh:section title='Нулевая цена по отделениям'>
       <ecom:webQuery name='test' 
         hql="select e.depType, count(*), sum(casePrice) from RegistryEntry e 
          where time=${param.id} and (casePrice = 0 or casePrice is null)
          group by depType" />
        <msh:table name="test" action="exp_messageView.do" idField="1"> 
        	<msh:tableColumn columnName="№" property="sn"/>
        	<msh:tableColumn columnName="Отделение" property="1"/>
        	<msh:tableColumn columnName="Количество случаев" property="2"/>
        	<msh:tableColumn columnName="Сумма" property="3"/>
       </msh:table>  
	</msh:section>

	<msh:section title='Нулевая цена по услугам'>
       <ecom:webQuery name='test' 
         hql="select e.depType, e.render, count(*), sum(casePrice) from RegistryEntry e 
          where time=${param.id} and (casePrice = 0 or casePrice is null)
          group by depType,render" />
        <msh:table name="test" action="exp_messageView.do" idField="1"> 
        	<msh:tableColumn columnName="№" property="sn"/>
        	<msh:tableColumn columnName="Отделение" property="1"/>
        	<msh:tableColumn columnName="Услуга" property="2"/>
        	<msh:tableColumn columnName="Количество случаев" property="3"/>
        	<msh:tableColumn columnName="Сумма" property="4"/>
       </msh:table>  
	</msh:section>

	<msh:section title='Нулевая цена по диагнозам МКБ'>
       <ecom:webQuery name='test' 
         hql="select e.render, e.diagnosisMain, count(*), sum(casePrice) from RegistryEntry e 
          where time=${param.id} and (casePrice = 0 or casePrice is null)
          group by render, diagnosisMain" />
        <msh:table name="test" action="exp_messageView.do" idField="1"> 
        	<msh:tableColumn columnName="№" property="sn"/>
        	<msh:tableColumn columnName="Услуга" property="1"/>
        	<msh:tableColumn columnName="МКБ" property="2"/>
        	<msh:tableColumn columnName="Количество случаев" property="3"/>
        	<msh:tableColumn columnName="Сумма" property="4"/>
       </msh:table>  
	</msh:section>

	<msh:section title='Неработающие с кодом предприятия'>
       <ecom:webQuery name='test' 
         hql="select sgroup, worksOldCode, count(*) from RegistryEntry 
where 
time=67 and (sgroup='55'  or sgroup='50' or sgroup='00' or sgroup='20') and worksOldCode<>'Д-055555'
 group by sgroup, worksOldCode 
         " />
        <msh:table name="test" action="exp_messageView.do" idField="1"> 
        	<msh:tableColumn columnName="№" property="sn"/>
        	<msh:tableColumn columnName="Социальная группа" property="1"/>
        	<msh:tableColumn columnName="Код предприятия" property="2"/>
        	<msh:tableColumn columnName="Количество случаев" property="3"/>
       </msh:table>  
	</msh:section>

    </tiles:put>


</tiles:insert>