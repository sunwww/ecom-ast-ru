<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh"%>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis"%>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

	<tiles:put name='title' type='string'>
		<msh:title></msh:title>
	</tiles:put>

	<tiles:put name='body' type='string'>
		<table class='mainMenu'>
			<tr>
				<td class='col1'>
				<div class='menu'>
				<h2>Что появилось нового в ПО "МедОС"?</h2>
				<ul>
					<% if (1==2) { //Добавляем по мер необходимости %>
					<li><msh:link action="ecom_release-201905.do">ноябрь 2019 года </msh:link></li>
					<li><msh:link action="ecom_release-201905.do">октябрь 2019 года </msh:link></li>
					<% } %>

					<li><msh:link action="ecom_release-201905.do">май 2019 года </msh:link></li>
					<li><msh:link action="ecom_release-201906.do">июнь 2019 года </msh:link></li>
					<li><msh:link action="ecom_release-201907.do">июль 2019 года </msh:link></li>
					<li><msh:link action="ecom_release-201908.do">август 2019 года </msh:link></li>
					<li><msh:link action="ecom_release-201909.do">сентябрь 2019 года </msh:link></li>
					<li><msh:link action="ecom_release-201910.do">октябрь 2019 года </msh:link></li>
					<li><msh:link action="ecom_release-201911.do">ноябрь 2019 года </msh:link></li>
					<li><msh:link action="ecom_release-201912.do">декабрь 2019 года </msh:link></li>
					<li><msh:link action="ecom_release-202001.do">январь 2020 года </msh:link></li>


					<% if (1==2) { %>
					<li><msh:link  action="ecom_release-201712.do">
						декабрь 2017 года
					</msh:link></li>
					<li><msh:link  action="ecom_release-201711.do">
						ноябрь 2017 года
					</msh:link></li>
					<li><msh:link  action="ecom_release-201710.do">
						октябрь 2017 года
					</msh:link></li>
					<li><msh:link  action="ecom_release-201709.do">
						сентябрь 2017 года
					</msh:link></li>
					<li><msh:link  action="ecom_release-201708.do">
						август 2017 года
					</msh:link></li>
					<li><msh:link  action="ecom_release-201707.do">
						июль 2017 года
					</msh:link></li>
					<li><msh:link  action="ecom_release-201705.do">
						май 2017 года
						</msh:link></li>
					<li><msh:link  action="ecom_release-201702.do">
						февраль 2017 года
						</msh:link></li>
					<li><msh:link  action="ecom_release-201701.do">
						январь 2017 года
						</msh:link></li>
					<li><msh:link  action="ecom_release-201612.do">
						декабрь 2016 года
						</msh:link></li>
					<li><msh:link  action="ecom_release-201611.do">
						 ноябрь 2016 года
                      	</msh:link></li>
					<li><msh:link  action="ecom_release-201610.do">
						 октябрь 2016 года
                      	</msh:link></li>
					<li><msh:link  action="ecom_release-201609.do">
						 сентябрь 2016 года
                      	</msh:link></li>
					<li><msh:link  action="ecom_release-201608.do">
						 август 2016 года
                        </msh:link></li>
					<li><msh:link  action="ecom_release-201607.do">
						 июль 2016 года
                        </msh:link></li>
					<li><msh:link  action="ecom_release-201606.do">
						 июнь 2016 года
                        </msh:link></li>
					<li><msh:link  action="ecom_release-201605.do">
						 май 2016 года
                        </msh:link></li>
					<li><msh:link  action="ecom_release-201604.do">
						 апрель 2016 года
                        </msh:link></li>
					<li><msh:link  action="ecom_release-201603.do">
						 март 2016 года
                        </msh:link></li>
					<li><msh:link  action="ecom_release-20160216.do">
					 февраль 2016 года
                        </msh:link></li>
                      </ul>  
					<h2>Дополнительная информация</h2>
					<ul>
					<li><msh:link  action="ecom_release-201501mkb.do">
					Изменения в МКБ январь 2015 г.
                        </msh:link></li>
					<% } %>
				</ul>
				</div>
			</tr>

			
		</table>
	</tiles:put>
</tiles:insert>
