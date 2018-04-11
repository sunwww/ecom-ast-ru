
function onPreCreate(aForm, aCtx) {
	var date = new java.util.Date() ;
	aForm.setCreateDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	aForm.setCreateUsername(aCtx.getUsername()) ;
}

function onCreate(aForm, aEntity, aCtx) {
	saveParameters (aForm, aEntity, aCtx.getUsername(),aCtx);
	//lastrelease milamesher 09.04.2018 #97 (при создании карты в СЛО надо создать протокол)
    if (aForm.getDepMedcase()!=null && aForm.getDepMedcase()!='' && aForm.getDepMedcase()!='0') {
        var asCardDiaryText = "Протокол карты оценки риска пациента ";
        var patient = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.patient.Patient,aForm.getPatient());
        asCardDiaryText +=patient.getPatientInfo() + "\n";
        var jsonStr=aForm.getParams();
        var obj=new Packages.org.json.JSONObject(jsonStr);
        var ar = obj.getJSONArray("params");
        if (ar.length()>0) {
            asCardDiaryText+="\t\tБаллы\nГруппа " + java.lang.String.valueOf(ar.get(0).get("groupName")) + ":\n";
            for (var i=0; i<ar.length(); i++) {
                asCardDiaryText+=java.lang.String.valueOf(ar.get(i).get("name"))+" : "+java.lang.String.valueOf(ar.get(i).get("valueVoc"));
                var bal=aCtx.manager.createNativeQuery("select cntball from uservalue where id='"+java.lang.String.valueOf(ar.get(i).get("value") + "'")).getResultList();
                if (bal.size()>0) asCardDiaryText+="("+bal.get(0)+")\n";
            }
        }
        asCardDiaryText+="Общая сумма баллов: "+aEntity.getBallSum();
        if (aEntity.getComment()!=null && aEntity.getComment()!="") asCardDiaryText+="\nКомментарий: " + aEntity.getComment();
        if (asCardDiaryText!='') {
            var currentDate = new java.util.Date();
            var prot = new Packages.ru.ecom.poly.ejb.domain.protocol.Protocol;
            prot.setDate(new java.sql.Date(currentDate.getTime()));
            prot.setType (aCtx.manager.find(Packages.ru.ecom.poly.ejb.domain.voc.VocTypeProtocol,java.lang.Long(4)));
            prot.setState (aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.voc.VocPhoneMessageState,java.lang.Long(1)));
            prot.setTime(new java.sql.Time (currentDate.getTime()));
            prot.setDateRegistration(aEntity.getStartDate());
            prot.setTimeRegistration(null);
            prot.setUsername(aCtx.getUsername());
            prot.setMedCase(aEntity.getDepMedcase());
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
