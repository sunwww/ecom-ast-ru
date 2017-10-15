/**
 * При сохранении
 */
function onSave(aForm, aEntity, aCtx) {
	var date = new java.util.Date() ;
	aEntity.setEditDate(new java.sql.Date(date.getTime())) ;
	aEntity.setEditTime(new java.sql.Time (date.getTime())) ;
	aEntity.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
    setJournalNumber(aEntity, aCtx);
}
/**
 * При создании
 */
function onCreate(aForm, aEntity, aCtx) {
	var date = new java.util.Date() ;
	aEntity.setCreateDate(new java.sql.Date(date.getTime())) ;
	aEntity.setCreateTime(new java.sql.Time (date.getTime())) ;
	aEntity.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
    setJournalNumber(aEntity, aCtx);
}

//Проставляем порядковый номер в журнале. номер уникальный в пределах типа ВК и года-месяца экспертизы
function setJournalNumber(aEntity, aCtx) {
    if ((aEntity.getNumberInJournal()==null||aEntity.getNumberInJournal()=="") && aEntity.getExpertDate!=null) {
        var expertType =aEntity.getType();
        var numberTemplate =expertType.getNumberGenerateTemplate();
        if (numberTemplate !=null&&numberTemplate!="") {
            var code = expertType.getCode();
            try {
                var cal = java.util.Calendar.getInstance() ;
                var format = new java.text.SimpleDateFormat(numberTemplate);//"yyyy-MM") ;
                cal.setTime(aEntity.getExpertDate());
                code="clinicexpert#"+code+"#"+ format.format(cal.getTime());
                var helper = Packages.ru.ecom.ejb.sequence.service.SequenceHelper.getInstance();
                var num = helper.startUseNextValueNoCheck(code,"",aCtx.manager);
                aEntity.setNumberInJournal(""+num);
                aCtx.manager.persist(aEntity);

                var change = new Packages.ru.ecom.mis.ejb.domain.prescription.AdminChangeJournal();
                var date = new java.util.Date();
                change.setCreateDate(new java.sql.Date(date.getTime()));
                change.setCreateTime(new java.sql.Time(date.getTime()));
                change.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString());
                change.setCType("EXPERT_CARD_AUTOJOURNALNUMBER");
                change.setAnnulRecord("ВК с ИД: "+aEntity.getId()+" присвоен номер в журнале: "+num+", код дня: "+code);
                aCtx.manager.persist(change);
            } catch (e) {
                throw "Произошла ошибка при фомировании номера, обратитесь к разработчикам: "+e;

            }


        }


    }
}