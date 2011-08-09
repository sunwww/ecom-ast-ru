<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<tiles:insert page="/WEB-INF/tiles/graphLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
    <ecom:titleTrail beginForm="mis_medCaseForm" mainMenu="Patient" title="Температурные листы" guid="c951cf449-0ed2-489d-9163-fa3" />
		<div class='titleTrail'>
			<span> Температурный лист </span>
		</div>    
    </tiles:put>

    <tiles:put name='side' type='string'>
    	<msh:sideMenu guid="9ec15353-1f35-4c18-b99d-e2b63ecc9" title="Добавить">
    	    <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/TemperatureCurve/Create" name="Температурный лист" params="id" action="/entityParentPrepareCreate-stac_temperatureCurve" title="Добавить параметр" guid="df23-45a-43cc-826d-5hfd" />
    	</msh:sideMenu>
        <msh:sideMenu title="Просмотр" guid="c65476c8-6c6a-43c4-a70a-84f40bda76e1">
	        <msh:sideLink name="Список параметров" action="/entityParentList-stac_temperatureCurve" title="Показать все температурные параметры" guid="df23-45ca-43cc-826d-5hf5dd" params="id" />
        </msh:sideMenu>
        <msh:sideMenu title="Печать" >
        	<msh:sideLink params="id" action="/js-stac_temperatureCurve-printgraph" name="Печать" title="температурного листа"/>
        </msh:sideMenu>
    </tiles:put>
    
    <tiles:put name='body' type='string' >
<div id="plot" style="width:90%;height:600px"> &mdash;</div>

    <p id="choices">Показать:</p>
    
    
    </tiles:put>
    
    <tiles:put name='javascript' type='string'>

  <!--[if IE]><script type="text/javascript" src="/skin/ext/flot/js/excanvas.js"></script><![endif]-->
 <script type="text/javascript" src="/skin/ext/flot/js/jquery.js"></script>
 <script type="text/javascript" src="/skin/ext/flot/js/jquery.flot.js"></script>
<script type='text/javascript' src='./dwr/interface/HospitalMedCaseService.js'></script>

<script language="javascript" type="text/javascript">
	
	var jsonar ;
	
	HospitalMedCaseService.getListTemperatureCurve(
			'${param.id}'
	 		,{
	                   callback: function(aString) {
	                   	 
	                   	 var d_degree = new Array() ;
	                   	 var d_bloodPressureDown = new Array() ;
	                   	 var d_bloodPressureUp = new Array() ;
	                   	 var d_weight = new Array() ;
	                   	 var d_respirationRate = new Array() ;
	                   	 var d_pulse = new Array() ;
	                   	 var minDate,maxDate ;
	                       //alert(eval('(' + aString + ')')) ;
							$(function () {
								//var MinDate,MaxDate ;
								var jsonar =  eval('(' + aString + ')') ;
								var childs = jsonar.childs ;
						        for (var i = 0; i < childs.length; i++) {
						            var itemar=childs[i] ;
						            var dateCurrent = new Date(itemar.date) ;
						        	if (i==0) {
						        		minDate = dateCurrent.getTime() ;
						        		maxDate = dateCurrent.getTime() ;
						        	} else {
						        		if (minDate>dateCurrent.getTime()) minDate = dateCurrent.getTime() ;
						        		if (maxDate<dateCurrent.getTime()) maxDate = dateCurrent.getTime() ;
						        	}
						           // alert(dateCurrent.getTime()) ;
						            d_degree.push([dateCurrent.getTime(), itemar.degree]) ;
						            d_bloodPressureDown.push([dateCurrent.getTime(), itemar.bloodPressureDown]) ;
						            d_bloodPressureUp.push([dateCurrent.getTime(), itemar.bloodPressureUp]) ;
						            d_weight.push([dateCurrent.getTime(), itemar.weight]) ;
						            d_respirationRate.push([dateCurrent.getTime(), itemar.respirationRate]) ;
						            d_pulse.push([dateCurrent.getTime(), itemar.pulse]) ;
						            
						        }

								var datasets = [
															{
																label: "АД нижнее",
																data: d_bloodPressureDown
															},
															{
																label: "АД верхнее",
																data: d_bloodPressureUp
															},
															{
																label: "Температура",
																data: d_degree
															},
															{
																label: "Вес",
																data: d_weight
															},
															{
																label: "Частота дыхания",
																data: d_respirationRate
															},
															{
																label: "Пульс",
																data: d_pulse
															}
															
														] ;
	
							var options = {
							        lines: { show: true },
							        points: { show: true },
							        legend: { noColumns: 2 },
							        xaxis: { mode: "time",minTickSize: [1, "hour"], tickSize: [4, "hour"],   timeformat: "%d д %h ч"
							        	,min:minDate, max: maxDate},
//							        yaxis: { min: 0 },
							        selection: { mode: "x" }
							    };
							
							
							var i = 0;
							$.each(datasets, function(key, val) {
							    val.color = i;
							    ++i;
							});
							
							// insert checkboxes 
							var choiceContainer = $("#choices");
							$.each(datasets, function(key, val) {
							    choiceContainer.append('<br/><input type="checkbox" name="' + key +
							                           '" checked="checked" >' + val.label + '</input>');
							});
							choiceContainer.find("input").click(plotAccordingToChoices);
							
							
							function plotAccordingToChoices() {
							    var data = [];
							
							    choiceContainer.find("input:checked").each(function () {
							        var key = $(this).attr("name");
							        if (key && datasets[key])
							            data.push(datasets[key]);
							    });
							
							    if (data.length > 0)
							        $.plot($("#plot"), data,options);
							}
							
							plotAccordingToChoices();
							
						});	                       
	
	
	
	                    }
	   		});
xaxis: {
    mode: "time"
    timeformat: "%d.%m.%y %H:%M"
  }

</script>

    </tiles:put>
    
	
	    
</tiles:insert>