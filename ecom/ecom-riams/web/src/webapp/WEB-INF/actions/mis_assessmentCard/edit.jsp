<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.util.List"%>
<%@page import="ru.ecom.ejb.services.query.WebQueryResult"%>
<%@page import="ru.ecom.ejb.services.query.IWebQueryService"%>
<%@page import="java.util.Collection"%>
<%@page import="ru.ecom.web.util.Injection"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form guid="formHello" action="/entityParentSaveGoView-mis_assessmentCard.do" defaultField="template">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden guid="hiddenParent" property="patient" />
      <msh:hidden guid="hidden" property="params" />
      <msh:panel guid="panel">
      
        <msh:row guid="row1">
          <msh:textField property="startDate" label="Дата приема" size="10" guid="a5-4caf-4e14-aa70-287c" />
        </msh:row>
      
      
        <msh:row >
          <msh:autoComplete vocName="vocAssessmentCard" property="template" label="Тип карты" guid="a5-4caf-4e14-aa70-287c" size='50' horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        
      
        <msh:row guid="f53-2cae-4795-93e8-9cd1">
          <msh:textField property="ballSum" label="Сумма баллов" size="20" fieldColSpan="3"  guid="e4d1b-8802-467d-b205-70" />
        </msh:row>
                <msh:row guid="f53-2cae-4795-93e8-9cd1">
          <msh:textArea property="comment" label="Описание" fieldColSpan="3" size='50' guid="e4d1b-8802-467d-b205-70" />
        </msh:row>
        <msh:ifFormTypeIsView formName="mis_assessmentCardForm">
        <msh:row>
        <msh:textField property="createUsername" label="Пользователь" size="20" fieldColSpan="3"  guid="e4d1b-8802-467d-b205-70" />
        <msh:textField property="createDate" label="Дата создания" size="20" fieldColSpan="3"  guid="e4d1b-8802-467d-b205-70" />
        </msh:row>
        <msh:row>
        <msh:autoComplete property="workFunction" label="Создал" size="100" vocName="workFunction" guid="c53e6f53-cc1b-44ec-967b-dc6ef09134fc" fieldColSpan="3" viewOnlyField="true" horizontalFill="true"  />
        </msh:row>
        </msh:ifFormTypeIsView>
      
        <msh:row guid="855de982-5baf-46f1-9f8b-f48" />
          
        <msh:submitCancelButtonsRow functionSubmit="saveIntakeInfo();" guid="submitCancel" colSpan="4" />
      </msh:panel>
      <div id='dataFieldTitle'> </div>
        <div id='dataField'> </div>
      <%
      String id = request.getParameter("id");
      if (id!=null&&!id.equals("")) {
    	  
       %>
      
       <ecom:webQuery name="groupList" nativeSql="select distinct pg.id
	,pg.name as f2_groupName
	from assessmentCard ac 
	left join forminputprotocol fip on fip.assessmentCard=ac.id
	left join parameter p on p.id=fip.parameter_id
	left join parametergroup pg on pg.id=p.group_id
	where ac.id='${param.id}'" />
	
	<%
	List groupList = (List) request.getAttribute("groupList");
	if (!groupList.isEmpty()){
		
		out.print("<table border='1'>");
		out.print("<th>Название</th><th>Значение параметра</th><th>Кол-во баллов</th>");
		IWebQueryService service = (IWebQueryService) Injection.find(request,null).getService(IWebQueryService.class) ;
		for (int i=0;i<groupList.size();i++) {
			WebQueryResult r = (WebQueryResult) groupList.get(i);
			out.print("<tr><td colspan='3'><b>"+r.get2()+"</b></td>");
			String sql = "select p.id "+
					" ,p.name as f3_parName, p.shortname as f4_parShortName"+ 
					" ,case when p.type = '4' then cast (fip.valuebd  as varchar) when p.type='3' then fip.valuetext" 
					+" when p.type='6' then fip.valuetext  when p.type='7' then coalesce(uv.name, '')||' '||coalesce(fip.valuetext,'') when p.type='2' then uv.name else 'WFT '||p.type end as f5_value"+
					" ,uv.cntball as f6_ball"+
					" from assessmentCard ac "+
					" left join forminputprotocol fip on fip.assessmentCard=ac.id"+
					" left join parameter p on p.id=fip.parameter_id"+
					" left join uservalue uv on uv.id=valuevoc_id"+
					" where ac.id='"+request.getParameter("id")+"' and p.group_id="+r.get1();
					List rows = (List)service.executeNativeSql(sql);
					if (!rows.isEmpty()) {
						for (int j=0;j<rows.size();j++) {
							WebQueryResult tds = (WebQueryResult) rows.get(j);
							out.print("<tr>");
							out.print("<td>"+tds.get2()+"</td>");
							out.print("<td>"+(tds.get4()!=null?tds.get4():"")+"</td>");
							out.print("<td>"+(tds.get5()!=null?tds.get5():"")+"</td>");
							out.print("</tr>");
							
						}
					}
					out.print("</tr>");
					
			
		}
		out.print("</table>");
	}
	%>
	

				 <msh:ifFormTypeIsView formName="mis_assessmentCardForm">
				</msh:ifFormTypeIsView>
				<%} %>
    </msh:form>
      <form action="print-assessment_card.do" method="post" target="_blank">
                <input type="hidden" name="SSsqlText" id="SSsqlText" value="select p.id as pid
		 ,pg.id as pgId
		 ,pg.name as f2_groupName
		 ,p.name as f3_parName, p.shortname as f4_parShortName
		 ,case when p.type = '4' then cast (fip.valuebd  as varchar) when p.type='3' then fip.valuetext when p.type='2' then uv.name else 'WFT '||p.type end as f5_value
		 ,uv.cntball as f6_ball
		 ,(select sum( case when p1.group_id=p.group_id then uv1.cntBall else null end)
			 from forminputprotocol fip1
			 left join parameter p1 on p1.id=fip1.parameter_id
			 left join uservalue uv1 on uv1.id=fip1.valuevoc_id
			 where  fip1.assessmentCard=ac.id 	)
		 from assessmentCard ac
		 left join forminputprotocol fip on fip.assessmentCard=ac.id
		 left join parameter p on p.id=fip.parameter_id
		 left join parametergroup pg on pg.id=p.group_id
		 left join uservalue uv on uv.id=fip.valuevoc_id
		 where ac.id='${param.id}'"/> 
                
    	    <input type='hidden' name="sqlInfo" id="sqlInfo" value='Список пациентов за ${beginDate}-${endDate} по отделению '>
	    <input type='hidden' name="id" id="id" value="${param.id}">
	    <input type='hidden' name="s" id="s" value="PrintService">
	    <input type='hidden' name="m" id="m" value="printAssessmentCard">
	    <input type='hidden' name="SSgroupField" id="SSgroupField" value="2,1">
                           
    </form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Lpu" beginForm="mis_assessmentCardForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
  <msh:ifFormTypeIsView formName="mis_assessmentCardForm">
    <msh:sideMenu guid="sideMenu-123">
      <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityEdit-mis_assessmentCard" name="Изменить" roles="/Policy/Mis/AssessmentCard/Edit" />
      <msh:sideLink guid="sideLinkDelete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-mis_assessmentCard" name="Удалить" roles="/Policy/Mis/AssessmentCard/Delete" />
      <msh:sideLink guid="sideLinkDelete" key="ALT+E"  action="/javascript:printCard()" name="Печать" roles="/Policy/Mis/AssessmentCard/View" />
    </msh:sideMenu>
    
    <msh:sideMenu title="Перейти" guid="sideMenu-123">
      
    </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name = 'javascript' type='string'>
  <msh:ifFormTypeIsView formName="mis_assessmentCardForm">
  <script type = 'text/javascript'>
  function printCard() {
  document.forms[1].submit();
		//window.document.location="print-ass_card1.do?s=PrintService&m=printAssessmentCard&id=${param.id}&groupFields="+groupFields+"&sqlText"+sql;
  }
  </script>
  </msh:ifFormTypeIsView>
  <msh:ifFormTypeIsNotView formName="mis_assessmentCardForm">
  <script type="text/javascript" src="./dwr/interface/TemplateProtocolService.js"></script>
  
  <script type = 'text/javascript'> 
  
  templateAutocomplete.addOnChangeCallback(function() {fillDataDiv();$('ballSum').value='';});
  //eventutil.addEventListener($('template'),'change',function(){fillDataDiv();}) ;
 //var oldaction = document.forms['mis_assessmentCardForm'].action ;
		//document.forms['mis_assessmentCardForm'].action="javascript:saveIntakeInfo()";
  var lastGroupId=0;
  fillDataDiv ();
  var listIds = new Array();
  function fillDataDiv () {
	    	    	 	 if (!$('template').value>0) {return;}
	  TemplateProtocolService.getParameterByObject($('id').value, $('template').value, 'AssessmentCard', {
	    					callback: function (aResults) {
	    						
	    						var arr = aResults.split("#");
	    						var aResult = arr[0];
	    						var editable = arr[1];
	    						listIds = new Array();	    					
	    					      //  $('BioList').value=aSmoId;
	    					      if ($('params')&&$('params').value!='') {
	    					    	fldJson = JSON.parse($('params').value);  
	    					      }  else {
	    					      fldJson = JSON.parse(aResult) ;
	    					      }
	    					  //    $('templateProtocol').value = +aTemplateId;
	    					        var cnt = +fldJson.params.length ;
	    					    	
	    					        if (cnt<=0) return;
	    					        var txt = "<form><table id='tblParamProtocol'>" ;
	    					      
	    					        for (var ind=0;ind<cnt;ind++) {
	    					        	var param = fldJson.params[ind] ;
	    					        	txt += getFieldTxtByParam(param) ;
	    					        }
	    					        txt+= createSumRow(lastGroupId);
	    					        txt += "</table></form>" ;	    						
	    					       $('dataField').innerHTML =txt ;
	    					      // 	cancel${name}TemplateProtocol();
	    					       
	    					   //    	the${name}IntakeInfoDialog.show() ;
	    			             	
	    			             if (fldJson.errors.length==0) {
	    			            	
	    					        for (var ind=0;ind<fldJson.params.length;ind++) {
	    					        	var param1 = fldJson.params[ind] ;
	    					        	
	    					        	if (ind<cnt-1) {
	    					        		var param2 = fldJson.params[ind+1] ;
	    					        		eventutil.addEnterSupport("param"+param1.idEnter,"param"+param2.idEnter) ;
	    					        	} else {
	    					        		eventutil.addEnterSupport("param"+param1.idEnter,"paramOK") ;
	    					        		
	    					        	}
	    					        	if (+param1.type==2) {
	    					        		eval("param"+param1.id+"Autocomlete = new msh_autocomplete.Autocomplete() ;")
	    					        		eval("param"+param1.id+"Autocomlete.setUrl('simpleVocAutocomplete/userValue') ;")
	    					        		eval("param"+param1.id+"Autocomlete.setIdFieldId('param"+param1.id+"') ;")
	    					        		eval("param"+param1.id+"Autocomlete.setNameFieldId('param"+param1.idEnter+"') ;")
	    					        		eval("param"+param1.id+"Autocomlete.setDivId('param"+param1.id+"Div') ;")
	    					        		eval("param"+param1.id+"Autocomlete.setVocKey('userValue') ;")
	    					        		eval("param"+param1.id+"Autocomlete.setVocTitle('"+param1.vocname+"') ;")
	    					        		eval("param"+param1.id+"Autocomlete.build() ;")
	    					        		eval("param"+param1.id+"Autocomlete.setParentId('"+param1.vocid+"') ;")
	    					        		eval("param"+param1.id+"Autocomlete.addOnChangeCallback(function(){getBall("+param1.id+") });");
	    					        		listIds.push ("param"+param1.id);
	    					        	}
	    					        } 
	    			             } else {
	    			            	var txt ="<form name='frmTemplate' id='frmTemplate'>" ;
	    			 				txt += "<a target='_blank' href='diary_templateView.do?id="+fldJson.template+"'>НЕПРАВИЛЬНО ЗАПОЛНЕНЫ ДАННЫЕ ПАРАМЕНТОВ ШАБЛОНА</a>" ;
	    			 				txt +="</form>" ;
	    			 				$('dataField').innerHTML =txt 
	    					       	+ "<input type=\"button\" value=\"ОК\" onclick=\"cancelBioIntakeInfo()\">";
	    					     }
	    			             
	    			    /*         if (+fldJson.isdoctoredit>0) {

	    		            		 $("param"+fldJson.params[0].idEnter).select() ;
	    				        	 $("param"+fldJson.params[0].idEnter).focus() ;
	    		           	 } else {
	    		            		 $("param"+p+"Name").select() ;
	    				        		$("param"+p+"Name").focus() ;
	    				        		$('param'+p+'Name').className="autocomplete horizontalFill required" ;
	    				        		eventutil.addEnterSupport("param"+p+"Name","param"+fldJson.params[0].idEnter) ;
	    				        		
	    		            	 } */ 
	    					}
	    				}) ;
	    			
	     }
  function createSumRow( aGroupId) {
	  if (aGroupId>0) {
		  return "<tr ><td colspan='3'><label style='font-size:15px;' align='center' id='Group"+aGroupId+"Sum'></label></td></tr>";
	  }
	  return "";
  }
  function getFieldTxtByParam(aParam) {
	  var txt='';
		if (aParam.groupId!=lastGroupId) {
			//Отспут перед след. группой 
			txt +=createSumRow(lastGroupId);
			txt+="<tr style='height: 14px'></tr>"; //<td colspan='2'><label id='Group"+aParam.groupId+"Label'></label></td><td><label id='Group"+aParam.groupId+"Sum'></label></td></tr>";
			txt+="<tr ><td width='40px'></td><td colspan = '2' style='font-size:20px;' align='center' >"+aParam.groupName+"</td><td><label style='font-size:20px;' align='center' id='Group"+aParam.groupId+"Sums'></label></td></tr>";
			lastGroupId =aParam.groupId; 
		}
		 txt += "<tr>" ;
		var type=+aParam.type;
		 txt+="<td class='label' width='40px'><label id='Param"+aParam.id+"Ball'></label></td>";
		txt += "<td>" ;
	      if (type==2) {
		        txt += "<input id=\"param"+aParam.id+"\" name=\"param"+aParam.id+"\" type=\"hidden\" value=\""+aParam.value+"\" title=\"\" autocomplete=\"\">";
	      	txt += "<div>";
		        txt += "<input id=\"param"+aParam.idEnter+"\" name=\"param"+aParam.idEnter+"\" type=\"text\" value=\""+aParam.valueVoc+"\" title=\""+aParam.vocname+"\" class=\"autocomplete horizontalFill\" autocomplete=\"on\">";
	      	txt += "<div id=\"param"+aParam.id+"Div\">";
	      	txt += "<span></span>";
		        //txt += ;
		        txt += "</div>";
		        txt += "</div>";
	      } else {
		        txt += "<input id=\"param"+aParam.id+"\" name=\"param"+aParam.id+"\" type=\"text\" value=\""+aParam.value+"\" title=\""+aParam.name+"\" autocomplete=\"off\">";
		        
	      }
	      // Кол-во баллов 
	     
	      txt += "</td>" ;
		
      txt += "<td align='left' ><label id=\"param"+aParam.id+"Label\" for=\"param"+aParam.idEnter+"\" title='"+aParam.name ;
      if (type==1) {
      	txt += '(число)' ;
      } else if (type==3) {
      	txt += '(текст)' ;
      } else if (type==4) {
      	txt += '(число зн. '+aParam.cntdecimal+')' ;
      } else if (type==5) {
      	txt += '(текст с огр. '+aParam.cntdecimal+')' ;
      	
      } 
      txt += "'>" ;
      txt += aParam.shortname ;
      txt += "</label></td>" ;
      
      txt += "<td>" ;
      if (+aParam.type==4) {txt += " ("+aParam.cntdecimal+") ";}
      txt += aParam.unitname ;
      txt += "</td>" ;
      txt += "</tr>" ;
      if (aParam.groupId!=lastGroupId) {
			//Отспут перед след. группой
		//	txt+="<tr style='height: 14px'><td colspan='2'><label id='Group"+aParam.groupId+"Label'></label></td><td><label id='Group"+aParam.groupId+"Sum'></label></td></tr>";
			
		//	lastGroupId =aParam.groupId; 
		}
		return txt ;
	}
  function saveIntakeInfo() {
  	var regexp = /^[\d+.]+$/;
		var isError = false ;
		for (var ind=0;ind<fldJson.params.length;ind++) {
			
			var val = $('param'+fldJson.params[ind].id).value ;
			var par = fldJson.params[ind] ; 
			var err ="";
			errorutil.HideError($('param'+par.idEnter)) ;
			/*if (val=="") {
				errorutil.ShowFieldError($('param'+par.idEnter),"Пустое значение") ;
				isError= true ;
			} */
			
			if (+par.type==2) {
				if (+val<1) {val='0' ;} else {
					par.valueVoc = $('param'+fldJson.params[ind].id+'Name').value ;
				}
			}
			 if (par.type=='4') {
				val = val.replace(",",".") ;
				val = val.replace("-",".") ;
				var v = val.split(".") ;
				var cntdecimal = +par.cntdecimal
				
				
				if (val!="") {
					if (!regexp.test(val)){
						err+="Допускается ввод только чисел! " ;						
						isError=true;
					}
					if (v.length==2 && v[1].length!=cntdecimal) {
						errorutil.ShowFieldError($('param'+par.idEnter),err+"Необходимо ввести "+cntdecimal+" знаков после запятой") ;
						isError= true ;
					}
					
	  					if (cntdecimal>0 ) {
	  						if (v.length==1) {
	  							errorutil.ShowFieldError($('param'+par.idEnter),err+"Должна быть 1 точка или запятая") ;
	  							isError= true ;
	  						} else if (v.length>1 && !isNaN(v[2])) {
	 							errorutil.ShowFieldError($('param'+par.idEnter),err+"Должна быть 1 точка или запятая") ;
	 							isError= true ;
							}
	  					} else {
	  						
	  					}
					}
			} 
			 if (par.type==1) {
				if (val!='' &&!regexp.test(val)){
					errorutil.ShowFieldError($('param'+par.idEnter),"Допускается ввод только чисел!") ;	
					isError=true;
				}
			}
			
			par.value = val ;
			
			
		}
		if (+fldJson.isdoctoredit==0) {
			if (+$('paramWF').value==0) {
				isError=true ;
				errorutil.ShowFieldError($('paramWFName'),"Обязательное поле") ;
			} else {
				fldJson.workFunction=$('paramWF').value
			}
			
		}
		var str = JSON.stringify(fldJson);
		//alert(str) ;
		$('params').value = str;
		if (!isError) {
			
			document.forms['mis_assessmentCardForm'].action='entityParentSaveGoView-mis_assessmentCard.do';
			document.forms['mis_assessmentCardForm'].submit();
			
		}
	} 
  
  function getBall (aParameterId) {
	  if (+$('param'+aParameterId).value>0) {} else {
		  $('Param'+aParameterId+'Ball').innerHTML ="";
		  getSummaryBalls();
		  return;
		}
	  TemplateProtocolService.getParameterBallId($('param'+aParameterId).value, {
		 callback: function (a){
			  if (a && a!='') {
				  a = a.split("#");
				  $('Param'+aParameterId+'Ball').innerHTML =a[0]+" б.";
				  if (a[1]!=null&&a[1]!='') {
					  alert ("ВНИМАНИЕ!!! "+a[1]);
				  }
			  } else {
				  $('Param'+aParameterId+'Ball').innerHTML ="";
			  }
			  getSummaryBalls();
		  }
	  });
	  
  }
  function createParams() {
	  var str = "";
	  for (var i=0;i<listIds.length;i++) {
		  if (+$(listIds[i]).value>0) {
			  if (str!='') {str+=","}
			  str+=$(listIds[i]).value;
		  }
	  }
	  return str;
  }
  function getSummaryBalls() {
	  var par = createParams();
	  if (par==''){par='0';}
		  TemplateProtocolService.getSummaryBallsByNewCard($('template').value,par,{
			  callback: function (a) {
				  var rows = a.split("@");
				  var sum = 0;
				  for (var i=0;i<rows.length;i++) {
					  var p = rows[i].split("#");
					  if ($('Group'+p[0]+'Sum')) {
						  $('Group'+p[0]+'Sum').innerHTML = "Итого баллов: "+p[2];
					}
					  sum+=(+p[2]);
					  
				  }
				  $('ballSum').value = sum;
				//  alert (a);
			  }
		  }); 
	  
	  
  }
  </script>
  </msh:ifFormTypeIsNotView>
  </tiles:put>
</tiles:insert>

