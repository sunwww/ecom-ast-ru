<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
    <tiles:put name="side" type="string">  
    </tiles:put>
  <tiles:put name='body' type='string'>
  <style type="text/css" media="screen">
  .print {
  	display: none ;
  	
  }
  .cntInfo {
  	list-style: disc;
  	padding-left:20px;
  }
  </style>
  <style type="text/css" media="print">
  .button{
  	display: none ;
  } 
  .fb {
  	display: none ;
  }
  .cntInfo {
  	list-style: disc;
  	margin-left:20px;
  }
  .print {
  display: block ;
  }
  	#header {
  		display: none ;
  	}
  	img {
  		display: none ;
  	}
  	body {
  		background: white;
  	}
  	#footer {
  	display: none ;
  	}
  	#side {
  		display: none ;
  	}
  	#content {
  	 margin-left: 0px;
  	}
  </style>
      <style type="text/css">
div.button {
    height: 100px ;
    width: 500px ;  
    font: bold 1.6em/2.2em Arial,Helvetica ;
}
    </style>
  <div class="print">
  	 <p>Запись №${wctInfo} от ${currentDate}</p>
  	 <p>Пациент: <b>${patientInfo}</b></p>
  	 <p>Специалист: <b>${specialistInfo} &nbsp;${specialistFioInfo}</b></p>
  	 <p>Дата приема <b>${dateInfo}</b> время <b>${timeInfo}</b></p>
  	 <p>Вам необходимо подойти за <b>10-20</b> минут до приема в регистратуру со следующими документами:</p>
    <ul>
    <li class="cntInfo"><b>направление</b> с результатами анализов и обследованиями (<b>при отсутствие направления приём оказан не будет</b>);</li>
    <li class="cntInfo">паспорт;</li>
    <li class="cntInfo">полис ОМС;</li>
    <li class="cntInfo">СНИЛС.</li>
    </ul>
    <p></p>
  </div>
	    <div class="button fb " onclick="next_pre_new_rec()">
	    	<p class="label">Оформить запись к другому специалисту</p>
	    	<p class="info">Сохранится информация о пациенте</p>
	    </div>
	    <br/>
	    <div class="button fb " onclick="next_pre_new_pat()">
	    	<p class="label">Выход в основное меню</p>
	    	<p class="info">Запись нового пациента</p>
	    </div>
	    <br>
  		<img src="img/new_year4.png"/>
  		<script type="text/javascript">
  		window.print() ;
  		function next_pre_new_rec() {
  			window.location = "${path_rec}1.do?tmp=${addParam1}" ;
  		}
  		function next_pre_new_pat() {
  			window.location = "start.do" ;
  		}
  		</script>
  </tiles:put>
</tiles:insert>
