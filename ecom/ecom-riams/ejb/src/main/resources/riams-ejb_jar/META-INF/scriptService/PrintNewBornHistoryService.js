var map = new java.util.HashMap();

function printNewBornHistory(aCtx, aParams) {
    var newBornHistory = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.birth.NewBorn
        , new java.lang.Long(aParams.get("id")));
    var medCase = newBornHistory.medCase;

    recordPatient(newBornHistory);

    return map;

}

//данные матери
function recordPatient(newBornHistory) {
    //Фамилия имя отчество
    map.put("pat", newBornHistory.patient);
}

function getAge(aKey, aBirthday, aDate) {
    if (aDate != null && aBirthday != null) {
        var calenB = java.util.Calendar.getInstance();
        calenB.setTime(aBirthday);
        var calenE = java.util.Calendar.getInstance();
        calenE.setTime(aDate);
        var year = calenE.get(java.util.Calendar.YEAR) - calenB.get(java.util.Calendar.YEAR);
        if (year > 0) {
            map.put(aKey, "" + year);
        } else {
            var month = calenE.get(java.util.Calendar.MONTH) - calenB.get(java.util.Calendar.MONTH);
            if (month > 0) {
                map.put(aKey, "" + month);
            } else {
                map.put(aKey, "" + (calenE.get(java.util.Calendar.DAY_OF_MONTH) - calenB.get(java.util.Calendar.DAY_OF_MONTH) + 1));
            }
        }
    } else {
        map.put(aKey, "");
    }
}

function getAddress(aKey, aAddress) {
    if (aAddress != null) {
        if (aAddress.addressIsCity != null && aAddress.addressIsCity == true) {
            map.put(aKey + ".CityOrVillage", "город");
        } else {
            if (aAddress.addressIsVillage != null && aAddress.addressIsVillage == true) {
                map.put(aKey + ".CityOrVillage", "село");
            } else {
                map.put(aKey + ".CityOrVillage", "город, село (нужное подчеркнуть)");
            }
        }
        map.put(aKey + ".info", aAddress.addressInfo);
    } else {
        map.put(aKey + ".CityOrVillage", "город, село (нужное подчеркнуть)");
        map.put(aKey + ".info", "");
    }
}

/**
 * Печать формы кардиоскрининга новорождённых.
 *
 * @params содержит slo
 */
function printCardiacScreeningForm(aCtx, aParams) {
    var slo = aParams.get("id");
    var sql = " select mother.lastname||' '||mother.firstname||' '||mother.middlename||' '||EXTRACT(YEAR from AGE(mother.birthday))||cast(' лет' as varchar(4)) as fioagemother," +
        " to_char(child.birthday,'dd.mm.yyyy') as drchild," +
        " sex.name as sex," +
        " round(chb.durationPregnancy,0) as dur," +
        " born.birthWeight as weight," +
        " to_char(scrI.createdate,'dd.mm.yyyy') as date1," +
        " to_char(scrII.createdate,'dd.mm.yyyy') as date2," +
        " sk1.name as skin1," +
        " sk2.name as skin2," +
        " vsap1.name as rightHandAP1," +
        " vsap2.name as rightHandAP2," +
        " vsap1_l.name as legAP1," +
        " vsap2_l.name as legAP2," +
        " scrI.rightHandPulse as rightHandPulse1," +
        " scrII.rightHandPulse as rightHandPulse2," +
        " scrI.LegPulse as legPulse1," +
        " scrII.LegPulse as legPulse2," +
        " scrII.RightHandAD as rightHandAD2," +
        " scrII.LegAD as legAD2," +
        " scrI.BreathingRate as breathingRate1," +
        " scrII.BreathingRate as breathingRate2," +
        " case when scrI.RetractionIntercostalGaps=true then 'Да' else 'Нет' end as retractionIntercostalGaps1," +
        " case when scrII.RetractionIntercostalGaps=true then 'Да' else 'Нет' end as retractionIntercostalGaps2," +
        " case when scrI.noseWingsMovement=true then 'Да' else 'Нет' end as noseWingsMovement1," +
        " case when scrII.noseWingsMovement=true then 'Да' else 'Нет' end as noseWingsMovement2," +
        " case when scrI.noisyBreathing=true then 'Да' else 'Нет' end as noisyBreathing1," +
        " case when scrII.noisyBreathing=true then 'Да' else 'Нет' end as noisyBreathing2," +
        " case when scrI.wheezing=true then 'Да' else 'Нет' end as wheezing1," +
        " case when scrII.wheezing=true then 'Да' else 'Нет' end as wheezing2," +
        " cns1.name as cns1," +
        " cns2.name as cns2," +
        " vsl_ap.name as apicalImpulseLocal2," +
        " vsl_l.name as liverEdgeLocal2," +
        " scrI.heartRate as heartRate1," +
        " scrII.heartRate as heartRate2," +
        " vsca1.name as cardiacActivity1," +
        " vsca2.name as cardiacActivity2," +
        " case when scrI.noisePresence=true then 'Да' else 'Нет' end as noisePresence1," +
        " case when scrII.noisePresence=true then 'Да' else 'Нет' end as noisePresence2," +
        " vsd.name as diuresis2," +
        " scrII.ECG as ECG2," +
        " scrI.extraInfo||' '||scrII.extraInfo as extraInfo2," +
        " vwf1.name||' '||wp1.lastname||' '||wp1.firstname||' '||wp1.middlename as fio1," +
        " vwf2.name||' '||wp2.lastname||' '||wp2.firstname||' '||wp2.middlename as fio2" +
        " from medcase slo " +
        " left join patient child on child.id=slo.patient_id" +
        " left join Kinsman k on k.person_id=child.id" +
        " left join patient mother on mother.id=k.kinsman_id " +
        " left join VocKinsmanRole vk on vk.id=k.KinsmanRole_id" +
        " left join VocSex sex on sex.id=child.sex_id" +
        " left join newborn born on born.patient_id=child.id" +
        " left join childbirth chb on chb.id=born.childbirth_id" +
        " left join screeningcardiac scrI on scrI.medcase_id=slo.id and scrI.dtype='ScreeningCardiacFirst'" +
        " left join screeningcardiac scrII on scrII.medcase_id=slo.id and scrII.dtype='ScreeningCardiacSecond'" +
        " left join VocScreeningSkin sk1 on sk1.id=scrI.skin_id" +
        " left join VocScreeningSkin sk2 on sk2.id=scrII.skin_id" +
        " left join VocScreeningArterialPulsation vsap1 on vsap1.id=scrI.RightHandAP_id" +
        " left join VocScreeningArterialPulsation vsap2 on vsap2.id=scrII.RightHandAP_id" +
        " left join VocScreeningArterialPulsation vsap1_l on vsap1_l.id=scrI.LegAP_id" +
        " left join VocScreeningArterialPulsation vsap2_l on vsap2_l.id=scrII.LegAP_id" +
        " left join VocScreeningCNS cns1 on cns1.id=scrI.cns_id" +
        " left join VocScreeningCNS cns2 on cns2.id=scrII.cns_id" +
        " left join VocScreeningLocalization vsl_ap on vsl_ap.id=scrII.apicalImpulseLocal_id" +
        " left join VocScreeningLocalization vsl_l on vsl_l.id=scrII.liverEdgeLocal_id" +
        " left join VocScreeningCardiacActivity vsca1 on vsca1.id=scrI.cardiacActivity_id" +
        " left join VocScreeningCardiacActivity vsca2 on vsca2.id=scrII.cardiacActivity_id" +
        " left join VocScreeningDiuresis vsd on vsd.id=scrII.diuresis_id" +
        " left join secUser secUser1 on secUser1.login=scrI.createusername" +
        " left join secUser secUser2 on secUser2.login=scrII.createusername" +
        " left join WorkFunction wf1 on wf1.secUser_id=secUser1.id" +
        " left join WorkFunction wf2 on wf2.secUser_id=secUser2.id" +
        " left join Worker w1 on w1.id=wf1.worker_id" +
        " left join Worker w2 on w2.id=wf2.worker_id" +
        " left join Patient wp1 on wp1.id=w1.person_id" +
        " left join Patient wp2 on wp2.id=w2.person_id" +
        " left join VocWorkFunction vwf1 on vwf1.id=wf1.workFunction_id" +
        " left join VocWorkFunction vwf2 on vwf2.id=wf2.workFunction_id" +
        " where slo.id='" + slo + " ' and vk.code='38' limit 1";
    var list = aCtx.manager.createNativeQuery(sql).getResultList();
    var obj = list.size() > 0 ? list.get(0) : null;
    if (obj != null) {
        map.put("fioagemother", (obj[0] != null) ? obj[0] : "");
        map.put("drchild", (obj[1] != null) ? obj[1] : "");
        map.put("sex", (obj[2] != null) ? obj[2] : "");
        map.put("dur", (obj[3] != null) ? obj[3] : "");
        map.put("weight", (obj[4] != null) ? obj[4] : "");
        map.put("date1", (obj[5] != null) ? obj[5] : "");
        map.put("date2", (obj[6] != null) ? obj[6] : "");
        map.put("skin1", (obj[7] != null) ? obj[7] : "");
        map.put("skin2", (obj[8] != null) ? obj[8] : "");
        map.put("rightHandAP1", (obj[9] != null) ? obj[9] : "");
        map.put("rightHandAP2", (obj[10] != null) ? obj[10] : "");
        map.put("legAP1", (obj[11] != null) ? obj[11] : "");
        map.put("legAP2", (obj[12] != null) ? obj[12] : "");
        map.put("rightHandPulse1", (obj[13] != null) ? obj[13] : "");
        map.put("rightHandPulse2", (obj[14] != null) ? obj[14] : "");
        map.put("legPulse1", (obj[15] != null) ? obj[15] : "");
        map.put("legPulse2", (obj[16] != null) ? obj[16] : "");
        map.put("rightHandAD2", (obj[17] != null) ? obj[17] : "");
        map.put("legAD2", (obj[18] != null) ? obj[18] : "");
        map.put("breathingRate1", (obj[19] != null) ? obj[19] : "");
        map.put("breathingRate2", (obj[20] != null) ? obj[20] : "");
        map.put("retractionIntercostalGaps1", (obj[21] != null) ? obj[21] : "");
        map.put("retractionIntercostalGaps2", (obj[22] != null) ? obj[22] : "");
        map.put("noseWingsMovement1", (obj[23] != null) ? obj[23] : "");
        map.put("noseWingsMovement2", (obj[24] != null) ? obj[24] : "");
        map.put("noisyBreathing1", (obj[25] != null) ? obj[25] : "");
        map.put("noisyBreathing2", (obj[26] != null) ? obj[26] : "");
        map.put("wheezing1", (obj[27] != null) ? obj[27] : "");
        map.put("wheezing2", (obj[28] != null) ? obj[28] : "");
        map.put("CNS1", (obj[29] != null) ? obj[29] : "");
        map.put("CNS2", (obj[30] != null) ? obj[30] : "");
        map.put("apicalImpulseLocal2", (obj[31] != null) ? obj[31] : "");
        map.put("liverEdgeLocal2", (obj[32] != null) ? obj[32] : "");
        map.put("heartRate1", (obj[33] != null) ? obj[33] : "");
        map.put("heartRate2", (obj[34] != null) ? obj[34] : "");
        map.put("cardiacActivity1", (obj[35] != null) ? obj[35] : "");
        map.put("cardiacActivity2", (obj[36] != null) ? obj[36] : "");
        map.put("noisePresence1", (obj[37] != null) ? obj[37] : "");
        map.put("noisePresence2", (obj[38] != null) ? obj[38] : "");
        map.put("diuresis2", (obj[39] != null) ? obj[39] : "");
        map.put("ECG2", (obj[40] != null) ? obj[40] : "");
        map.put("extraInfo", (obj[41] != null) ? obj[41] : "");
        map.put("fio1", (obj[42] != null) ? obj[42] : "");
        map.put("fio2", ('' + obj[43] != '' + obj[42]) ? obj[43] : "");
        map.put("provod", ('' + obj[43] != '' + obj[42] && obj[43] != null && obj[42] != null) ? "проводивших" : "проводившего");
    }
    return map;
}