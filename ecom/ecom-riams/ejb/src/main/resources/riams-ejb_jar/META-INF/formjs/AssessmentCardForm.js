
function onPreCreate(aForm, aCtx) {
	var date = new java.util.Date() ;
	aForm.setCreateDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	aForm.setCreateUsername(aCtx.getUsername()) ;
}

function onCreate(aForm, aEntity, aCtx) {
	saveParameters (aForm, aEntity, aCtx.getUsername(),aCtx);
	//lastrelease milamesher 09.04.2018 #97 (при создании карты в СЛО надо создать протокол)
    if (aForm.getMedcase()!=null && aForm.getMedcase()!='' && aForm.getMedcase()!='0' && aForm.getTemplate()=='7') {
        var asCardDiaryText = "Протокол карты оценки риска ВТЭО во время родов и в послеродовом (послеоперационном) периоде пациентки ";
        var patient = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.patient.Patient,aForm.getPatient());
        asCardDiaryText +=patient.getPatientInfo() + "\n";
        var jsonStr=aForm.getParams();
        var obj=new Packages.org.json.JSONObject(jsonStr);
        var ar = obj.getJSONArray("params");
        asCardDiaryText+="\nПараметр__________________________________________________________Значение и балл\n";
        var count=1;
        for (var i=0; i<ar.length(); i++) {
            if (asCardDiaryText.indexOf(ar.get(i).get("groupName"))==-1) {
                asCardDiaryText += count + ". " + java.lang.String.valueOf(ar.get(i).get("groupName")) + ":\n";
                count++;
            }
            asCardDiaryText+=java.lang.String.valueOf(ar.get(i).get("name"))+"\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+java.lang.String.valueOf(ar.get(i).get("valueVoc"));
            var bal=aCtx.manager.createNativeQuery("select cast(cntball as integer) from uservalue where id='"+java.lang.String.valueOf(ar.get(i).get("value") + "'")).getResultList();
            if (bal.size()>0) asCardDiaryText+="\t("+bal.get(0)+")\n";
        }
        asCardDiaryText+="Общая сумма баллов: "+aEntity.getBallSum()+"\n";
        if (aEntity.getComment()!=null && aEntity.getComment()!="") asCardDiaryText+="Комментарий: " + aEntity.getComment() +"\n";
        /*if (aEntity.getBallSum()<=1) asCardDiaryText+="Низкий риск.";
        else if (aEntity.getBallSum()==2) asCardDiaryText+="Средний риск.";
        else if (aEntity.getBallSum()>=3) asCardDiaryText+="Высокий риск.";*/
        if (asCardDiaryText!='') {
            var currentDate = new java.util.Date();
            var prot = new Packages.ru.ecom.poly.ejb.domain.protocol.Protocol;
            prot.setDate(new java.sql.Date(currentDate.getTime()));
            prot.setType (aCtx.manager.find(Packages.ru.ecom.poly.ejb.domain.voc.VocTypeProtocol,java.lang.Long(4)));
            prot.setState (aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.voc.VocPhoneMessageState,java.lang.Long(1)));
            prot.setTime(new java.sql.Time (currentDate.getTime()));
            if (aEntity.getStartDate()!=null) prot.setDateRegistration(aEntity.getStartDate());
            else prot.setDateRegistration(new java.sql.Date(currentDate.getTime()));
            prot.setTimeRegistration(new java.sql.Time (currentDate.getTime()));
            prot.setUsername(aCtx.getUsername());
            prot.setMedCase(aEntity.getMedcase());
            prot.setSpecialist(aCtx.serviceInvoke("WorkerService", "findLogginedWorkFunction"));
            prot.setRecord(asCardDiaryText);
            aCtx.manager.persist(prot);
        }
    }
}

function onSave (aForm, aEntity, aCtx) {
	saveParameters (aForm, aEntity, aCtx.getUsername(),aCtx);
}
function saveParameters (aForm, aEntity, aUsername, aCtx) {
	var wf = aCtx.serviceInvoke("WorkerService", "findLogginedWorkFunction") ;
	aEntity.setWorkFunction (wf.getId());
	Packages.ru.ecom.diary.ejb.service.assessmentcard.AssessmentCardServiceBean.saveParametersByCard(aForm.getPatient(),aEntity,aForm.getParams(), aCtx.manager);
}

function onPreDelete(aId, aCtx) {
	aCtx.manager.createNativeQuery("delete from forminputprotocol where assessmentCard='"+aId+"'").executeUpdate() ;
	
}
