/**
 * При создании
 */
function onCreate(aForm, aEntity, aCtx) {

	if (aEntity.isNoActuality!=null &&true==aEntity.isNoActuality) {
        aCtx.manager.createNativeQuery("update WorkPlace set isNoActuality='1'  where parent_id='"+aEntity.id+"' and dtype='HospitalBed'").executeUpdate() ;
	} else {
        var cnt=aEntity.countBed!=null?aEntity.countBed.code:0 ;
        cnt= +cnt ;
        if (cnt>0) {
            var sql = "select count(*),count(*)-count(case when isNoActuality='1' then null else id end) from WorkPlace where parent_id='"+aEntity.id+"' and dtype='HospitalBed'" ;
            var obj = aCtx.manager.createNativeQuery(sql).setMaxResults(1).getSingleResult() ;

            if (obj!=null) {
                if (+obj[1]==cnt) {
                } else if (+obj[0]>cnt) {
                    //throw ""+obj[0]+" "+obj[1]+" "+cnt ;
                    aCtx.manager.createNativeQuery("update WorkPlace set isNoActuality='1'  where parent_id='"+aEntity.id+"' and dtype='HospitalBed' and cast(name as integer)>"+cnt).executeUpdate() ;
                    aCtx.manager.createNativeQuery("update WorkPlace set isNoActuality='0'  where parent_id='"+aEntity.id+"' and dtype='HospitalBed' and cast(name as integer)<="+cnt).executeUpdate() ;
                } else if(+obj[0]<cnt){
                    //	throw ""+ obj[0]+"<<>>"+cnt;
                    aCtx.manager.createNativeQuery("update WorkPlace set isNoActuality='0'  where parent_id='"+aEntity.id+"' and dtype='HospitalBed'").executeUpdate() ;
                    //throw ""+obj[0]+" "+obj[1]+" "+cnt ;
                    for (var i=(+obj[0]+1) ; i<=cnt; i++) {
                        var wp = new Packages.ru.ecom.mis.ejb.domain.lpu.HospitalBed()
                        wp.setName(""+i);
                        wp.setParent(aEntity) ;
                        aCtx.manager.persist(wp) ;
                    }
                }
            }
        }
	}

}

/**
 * Перед созданием
 */
function onPreCreate(aForm, aContext) {
}

/**
 * При просмотре
 */
function onView(aForm, aEntity, aContext) {
}

/**
 * При сохранении
 */
function onSave(aForm, aEntity, aCxt) {
    onCreate(aForm, aEntity, aCxt) ;
}


/**
 * Перед сохранением
 */
function onPreSave(aForm, aEntity, aCxt) {

}

/**
 * Перед удалением
 */
function onPreDelete(aEntityId, aContext) {
}

/**
 * При удалении
 */
function onDelete(aEntityId, aContext) {
}