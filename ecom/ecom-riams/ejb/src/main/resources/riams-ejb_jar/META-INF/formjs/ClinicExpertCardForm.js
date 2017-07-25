/**
 * При сохранении
 */
function onSave(aForm, aEntity, aCtx) {
	var date = new java.util.Date() ;
	aEntity.setEditDate(new java.sql.Date(date.getTime())) ;
	aEntity.setEditTime(new java.sql.Time (date.getTime())) ;
	aEntity.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
	if (aEntity.getNumberInJournal()==null||aEntity.getNumberInJournal()=="") {
		if (aEntity.getExpertDate!=null) {
			//Проставляем порядковый номер в журнале. номер уникальный в пределах типа ВК и года-месяца экспертизы
			var code = aEntity.getType().getCode();
            var cal = java.util.Calendar.getInstance() ;
            var format = new java.text.SimpleDateFormat("yyyy-MM") ;
            cal.setTime(aEntity.getExpertDate());
			code="clinicexpert#"+code+"#"+ format.format(cal.getTime());
			var helper = Packages.ru.ecom.ejb.sequence.service.SequenceHelper.getInstance();
			var num = helper.startUseNextValueNoCheck(code,"",aCtx.manager);
			aEntity.setNumberInJournal(""+num);
			aCtx.manager.persist(aEntity);

		}
	}

}
/**
 * При создании
 */
function onCreate(aForm, aEntity, aCtx) {
	var date = new java.util.Date() ;
	aEntity.setCreateDate(new java.sql.Date(date.getTime())) ;
	aEntity.setCreateTime(new java.sql.Time (date.getTime())) ;
	aEntity.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;

}