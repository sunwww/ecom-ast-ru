<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh"%>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis"%>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

	<tiles:put name='title' type='string'>
		<msh:title mainMenu="Config">Настройки</msh:title>
	</tiles:put>

	<tiles:put name='body' type='string'>
		<table class='mainMenu'>
			<tr>
				<td class='col1'>
					<div class='menu'>
						<h2>Работа с данными</h2>
						<ul>
							<li><msh:link roles='/Policy/Mis/Patient' action="/javascript:sendDiary(this)">
								Сформировать файл об оказанной помощи иногородним или иностранцам
								<div id="miacFileLinkDiv"></div>
							</msh:link></li>
							<li><msh:link roles='/Policy/Mis/Patient' action="/javascript:test()">
								Test
								<div id="miacFileLinkDiasdv"></div>
							</msh:link></li>
							<li><msh:link roles='/Policy/Mis/Disability/Case/Document/ExportDocument' action="/javascript:getLNNumberRange()">
								Получить номера электронных больничных листов (ЭЛН)
							</msh:link></li>
							<li><msh:link roles='/Policy/Mis/Patient' action="templateDocument-import.do">
								Загрузить файл
							</msh:link></li>
							<li><msh:link roles='/Policy/Mis/Patient' action="/javascript:deleteAllTalons()">
								Удалить все открытые талоны
							</msh:link></li>
							<li><msh:link roles='/Policy/Mis/Patient' action="mis_patientFondCheckList.do">
								Автоматическая проверка по базе ФОМС
							</msh:link></li>
							<li><msh:link roles='/Policy/Mis/Patient' action="/javascript:syncRecordTomorrow()">
								Синхронизация с базой ФОМС пациентов, записанных на завтра
							</msh:link></li>
							<li><msh:link roles='/Policy/Exp/Document/View' action="js-ecom_vocEntity-list.do">
								Редактор справочников
							</msh:link></li>
							<li><msh:link roles="/Policy/Exp/Document/Edit" action="entityList-exp_importdocument.do">
								Импорт
							</msh:link></li>
							<li><msh:link roles='/Policy/Exp/FillTime/View' action="entityList-exp_fillTime.do">
								Заполнения
							</msh:link></li>
							<li><msh:link roles='/Policy/Exp/FillTime/View' action="entityList-exp_iterate.do">
								Переборы
							</msh:link></li>

							<li><msh:link roles='/Policy/Exp/Sequence/View' action="entityList-exp_sequenceInfo.do">
								Последовательности
							</msh:link></li>

							<msh:ifInRole roles="/Policy/Stac/CustomizeMode/Edit">
								<li>
									<a href="formCustomizeServletConfig">
										Текущие настройки форм
									</a>
								</li>
							</msh:ifInRole>
							<msh:ifInRole roles="/Policy/Mis/Worker/WorkCalendar/Edit">
								<li>
									<msh:link roles='/Policy/Mis/Worker/WorkCalendar/Edit'
											  action="js-mis_worker-autogenerate.do">
										Списки раб.функций, у которых установлена автогенерация
									</msh:link>
								</li>
							</msh:ifInRole>
							<li>
								<mis:linkCsp roles='/Policy/Mis/Worker/WorkCalendar/Edit'
											 action="/impdoclist.csp">
									Экспертиза
								</mis:linkCsp>
							</li>
							<li><msh:link action="ecom_hibernateCacheConfig.do">
								Кэш для persistence.properties
							</msh:link></li>

							<li><msh:link roles='/Policy/Jaas/SecPolicy/Create'
										  action="mis_lpuUpdateJaasPolicy.do">
								Перекрепить все население
							</msh:link></li>
							<li>
								<msh:link action="js-ecom_deleteJournal-listNext.do">Журнал удаленных данных</msh:link>
							</li>
							<li>
								<msh:link roles='/Policy/Jaas/SecPolicy/Create'
										  action="config_addressUpdate.do?clear=1">
									Адреса очис+запол
								</msh:link>
								<msh:link roles='/Policy/Jaas/SecPolicy/Create'
										  action="config_addressUpdate.do?clear=0">
									Адреса заполнить
								</msh:link>
							</li>
							<li><msh:link action="entityPrepareCreate-mis_customMessage.do" roles="/Policy/Mis/CustomMessage/Create">Создать информационное сообщение</msh:link></li>
							<li>
								<msh:link action="js-riams-sync_parus.do" >Отчет по синхронизации с Парусом</msh:link>
							</li>

						</ul>
					</div>

				</td><td style='padding-left: 5em'>
				<msh:ifInRole roles="/Policy/Jaas/SecUser/View">
					<div class='menu'>
						<h2>Безопасность</h2>
						<ul>
							<li><msh:link action="entityList-sec_softConfig.do" roles="/Policy/Config/Soft/View">
								Настройки приложения
							</msh:link></li>
							<li><msh:link action="entityList-secuser.do" roles="/Policy/Jaas/SecUser/View">
								Пользователи
							</msh:link></li>
							<li><msh:link action="entityList-secrole.do" roles="/Policy/Jaas/SecRole/View">
								Роли
							</msh:link></li>
							<li><msh:link action="entityParentList-secpolicy.do?id=1" roles="/Policy/Jaas/SecPolicy/View">
								Политики
							</msh:link></li>
							<li><msh:link action="entityList-secgroup.do" roles="/Policy/Jaas/SecGroup/View">
								Группы
							</msh:link></li>
							<li><msh:link action="serviceExport.do" roles="/Policy/Jaas/Activation">
								Активация политик
							</msh:link></li>
							<li><msh:link action="js-sec_userPermission-listNext.do" roles="/Policy/Jaas/Permission/User/View">
								Разрешения
							</msh:link></li>
						</ul>
					</div>
				</msh:ifInRole>
			</td>
			</tr>
			<tr>
				<td style='padding-left: 5em'>
					<msh:ifInRole roles="/Policy/IdeMode/Hello/View">
						<div class='menu'>
							<h2>Шаблоны</h2>
							<ul>
								<li><msh:link action="entityList-ecom_hello.do" roles="/Policy/IdeMode/Hello/View">
									list.jsp
								</msh:link></li>
								<li><msh:link action="entityEdit-ecom_hello.do?id=1" roles="/Policy/IdeMode/Hello/View">
									edit.jsp
								</msh:link></li>
								<li><msh:link action="js-ecom_forms-listAllStrutsForms.do" roles="/Policy/IdeMode/Hello/View">
									Список всех форм
								</msh:link></li>
							</ul>
						</div>
					</msh:ifInRole>
				</td>

				<td style='padding-left: 5em'>
					<msh:ifInRole roles="/Policy/Config/Hospital/View">
						<div class='menu'>
							<h2>Стационар</h2>
							<ul>
								<li>
									<msh:link action="entityList-stac_statisticStubNew.do" roles="/Policy/Config/Hospital/StatisticStubNew/View">
										Стат.карты
									</msh:link>
								</li>
							</ul>
						</div>
					</msh:ifInRole>
					<msh:ifInRole roles="/Policy/Config/Update">
						<div class='menu'>
							<h2>Обновление</h2>
							<ul>
								<li>
									<msh:link action="js-riams-update_postgres.do">
										Для postgres
									</msh:link>
								</li>
							</ul>
						</div>
					</msh:ifInRole>
				</td>
			</tr>

		</table>
	</tiles:put>
	<tiles:put name="javascript" type="string">
		<script type="text/javascript" src="./dwr/interface/TicketService.js"></script>
		<script type='text/javascript' src='./dwr/interface/TemplateProtocolService.js'></script>
		<script type='text/javascript' src='./dwr/interface/DisabilityService.js'></script>
		<script type='text/javascript' src='./dwr/interface/ContractService.js'></script>
		<script type='text/javascript' src='./dwr/interface/HospitalMedCaseService.js'></script>
		<script type="text/javascript">
            function test() {
                for (var i=0;i<4;i++) {
                    jQuery.toast({
                        title: 'My message '+i
                        , text: 'this is text'
                        , beforeShow: function () {
                            msh.effect.FadeEffect.putFade();
                        }
                        , beforeHide: function () {
                            msh.effect.FadeEffect.pushFade();
                        }
                        , hideAfter: false
                    });
                }
            }
            function getLNNumberRange() {
                var num = prompt("Укажите количество требуемых номеров",20);
                if (+num>0) {
                    DisabilityService.getLNNumberRange(num, {
                        callback:function (ret) {
                            if (ret!=null&&ret!='') {
                                alert ("Номера ЭЛН в количестве "+num+" штук успешно получены.");
                            } else {
                                alert ("Произошла ошибка при получении номеров ЭЛН! Попробуйте еще раз.")
                            }

                        }
                    });
                }
            }
            function sendDiary(htm) {
                var dateFrom = prompt("Дата начала",'01.01.2017');
                var dateTo = prompt("Дата окончания",'31.10.2017');
                var type = prompt("Тип файла (inog, inos)",'inog');
                HospitalMedCaseService.getMedcaseCost(dateFrom,dateTo,type, {
                    callback: function (fileUrl) {
                        var str = "<a href='"+fileUrl+"'>Скачать файл</a>";
                        alert(str);
                        jQuery(htm).html(str);
                    }
                });
                jQuery('#miacFileLinkDiv').html("Подождите, идет формирование файла");


            }
            function deleteAllTalons () {
                var date = prompt('Введите дату, до которой были выданы талоны','01.01.2016');
                if (date!=null&&date!='') {
                    TicketService.deleteTalons('',date,{
                        callback:function(a) {alert(a);}
                    });
                }
            }
            function getTomorowDate() {
                var tomorrow = new Date();
                tomorrow.setDate(tomorrow.getDate() + 1);
                var dd = tomorrow.getDate();
                var mm = tomorrow.getMonth() + 1;
                var yyyy = tomorrow.getFullYear();
                if (dd < 10) {
                    dd = '0' + dd;
                }
                if (mm < 10) {
                    mm = '0' + mm;
                }
                return dd + '.' + mm + '.' + yyyy;
			}
            function syncRecordTomorrow () {
                var date = prompt('Введите дату направления',getTomorowDate());
                if (date!=null && date!='' && date!='dd.mm.yyyy') window.open('api/foncCheck/syncRecordTomorrow?dateStart='+date);
                else window.open('api/foncCheck/syncRecordTomorrow');
            }
		</script>
	</tiles:put>
</tiles:insert>
