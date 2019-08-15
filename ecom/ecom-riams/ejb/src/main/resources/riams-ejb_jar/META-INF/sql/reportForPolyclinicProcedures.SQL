1565850748#
    CREATE OR REPLACE FUNCTION sqluser.selectrecordpatientsbywfanddate(wfId bigint, recdate text, sstr bigint)
--функция возвращает время, фио, район, адрес проживания, дату последнего визита (не платный и не ДМС)
--пациента, записанного к врачу с рабочей функцией wf на дату recdate, поток обслуживания любой или sstr
 RETURNS TABLE(numinnerselect bigint, timeFrom varchar(5), pfio text,rayon varchar(30),realaddr varchar(100),lastvisdate text, patId bigint, visId bigint)
 LANGUAGE plpgsql
AS $function$
BEGIN
RETURN QUERY
select row_number() over(ORDER by wct.timefrom)
	    ,cast(wct.timeFrom as varchar(5)) as timeFrom
	    ,p.lastname||' '||p.firstname||' '||p.middlename||' г.р.'||to_char(p.birthday,'DD.MM.YYYY') as pfio
	    ,vr.name as rayon
	    ,addr.fullname as realaddr
	    ,(select to_char(mc.dateStart,'dd.mm.yyyy')
from medcase mc
left join vocservicestream sstr on sstr.id=mc.servicestream_id
where mc.patient_id=v.patient_id and (mc.DTYPE='Visit' or mc.DTYPE='ShortMedCase')
and mc.dateStart is not null and (mc.noActuality is null or mc.noActuality='0')
and sstr.code not in ('PRIVATEINSURANCE','CHARGED')
order by mc.dateStart desc,mc.timeExecute desc limit 1) as lastvisdate
,v.patient_id as patId
,v.id as visId
from medcase v
left join patient p on p.id=v.patient_id
left join WorkCalendarDay wcd on wcd.id=v.datePlan_id
left join WorkCalendarTime wct on wct.id=v.timePlan_id
left join vocrayon vr on p.realrayon_id=vr.id
left join address2 addr on addr.addressid=p.realaddress_addressid
left join workFunction wf on wf.id=v.workFunctionExecute_id
left join vocservicestream str on str.id=v.servicestream_id
where  wcd.calendardate=to_date(recdate,'dd.mm.yyyy') and wf.id=wfId
and (v.DTYPE='Visit' or v.DTYPE='ShortMedCase') and (v.noActuality is null or v.noActuality='0')
and case when sstr is not null and sstr<>0  then str.id=sstr else 1=1 end
order by wct.timefrom;
END;
$function$;

CREATE OR REPLACE FUNCTION sqluser.selectotherrecordsbywfanddateexceptvis(patId bigint, recdate text, sstr bigint, exceptVis bigint)
--функция возвращает другие записи пациента patId (время и врача) на дату recdate
--с потоком обслуживания sstr кроме визита exceptVis
 RETURNS TABLE(visits text)
 LANGUAGE plpgsql
AS $function$
BEGIN
RETURN QUERY
select list(cast(wct.timeFrom as varchar(5))||' '||upper(substring(replace(replace(vwf.name,'Врач-',''),'Врач ','') from 1 for 1))
|| substring(replace(replace(vwf.name,'Врач-',''),'Врач ','') from 2 for length(replace(vwf.name,'Врач-','')))||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename)
from medcase v
left join patient p on p.id=v.patient_id
left join WorkCalendarDay wcd on wcd.id=v.datePlan_id
left join WorkCalendarTime wct on wct.id=v.timePlan_id
left join vocrayon vr on p.realrayon_id=vr.id
left join address2 addr on addr.addressid=p.realaddress_addressid
left join workFunction wf on wf.id=v.workFunctionExecute_id
left join vocservicestream str on str.id=v.servicestream_id
left join worker w on w.id=wf.worker_id
left join Patient wp on wp.id=w.person_id
left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
where  wcd.calendardate=to_date(recdate,'dd.mm.yyyy')
and (v.DTYPE='Visit' or v.DTYPE='ShortMedCase') and (v.noActuality is null or v.noActuality='0')
and case when sstr is not null and sstr<>0  then str.id=sstr else 1=1 end
and v.id<>exceptVis and p.id=patId
group by wct.timefrom
order by wct.timefrom;
END;
$function$;

CREATE OR REPLACE FUNCTION sqluser.selectinfoforkdcrecordreport(recdate text, onlyWfId bigint, sstr bigint)
--функция возврашает информацию по записанным пациентам на дату recdate
--цикл по всем рабочим функциям врачей, к которым есть запись на дату
--и запуск функции, возвращающей инфу по пациенту
--параметр onlyWfId - только по врачу с раб. ф-ей onlyWfId
--параметр sstr - только по потоку обслуживания sstr
 RETURNS TABLE(rownumber bigint, wfId bigint, doc text, num bigint, timeFrom varchar(5), pfio text,rayon varchar(30),realaddr varchar(100),lastvisdate text, patId bigint,othervisits text)
 LANGUAGE plpgsql
AS $function$
declare
arow record;
res record;
res2 text;
rownumbervar integer;
wfidvar integer;
inneri integer;
BEGIN
CREATE TEMP TABLE  tableRecWf ON COMMIT DROP AS
select row_number() over(order by
upper(substring(replace(replace(vwf.name,'Врач-',''),'Врач ','') from 1 for 1))
|| substring(replace(replace(vwf.name,'Врач-',''),'Врач ','') from 2 for length(replace(vwf.name,'Врач-','')))
,wp.lastname||' '||wp.firstname||' '||wp.middlename,wct.timeFrom
) as rownumber, wf.id as wfId,
cast (upper(substring(replace(replace(vwf.name,'Врач-',''),'Врач ','') from 1 for 1))
|| substring(replace(replace(vwf.name,'Врач-',''),'Врач ','') from 2 for length(replace(vwf.name,'Врач-','')))||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename as text) as doc
,cast(null as bigint) as num,cast(null as varchar(5)) as timeFrom,
cast(null as text) as pfio,cast(null as varchar(30)) as rayon,cast(null as varchar(100)) as realaddr,cast(null as text) as lastvisdate, cast(null as bigint) as patId
,cast(null as text) as othervisits
from medcase v
left join patient p on p.id=v.patient_id
left join WorkCalendarDay wcd on wcd.id=v.datePlan_id
left join WorkCalendarTime wct on wct.id=v.timePlan_id
left join vocrayon vr on p.realrayon_id=vr.id
left join address2 addr on addr.addressid=p.realaddress_addressid
left join workFunction wf on wf.id=v.workFunctionExecute_id
left join worker w on w.id=wf.worker_id
left join Patient wp on wp.id=w.person_id
left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
left join workplace_workfunction wpwf on wpwf.workfunctions_id=wf.id
left join workplace wpl on wpl.id=wpwf.workplace_id
left join workcalendar wc on wc.workfunction_id=wf.id
left join vocservicestream str on str.id=v.servicestream_id
where  wcd.calendardate=to_date(recdate,'dd.mm.yyyy')
and (v.DTYPE='Visit' or v.DTYPE='ShortMedCase') and (v.noActuality is null or v.noActuality='0')
and wf.id is not null
and case when sstr is not null and sstr<>0  then str.id=sstr else 1=1 end
and case when onlyWfId is not null and onlyWfId<>0  then wf.id=onlyWfId else 1=1 end
order by upper(substring(replace(replace(vwf.name,'Врач-',''),'Врач ','') from 1 for 1))
|| substring(replace(replace(vwf.name,'Врач-',''),'Врач ','') from 2 for length(replace(vwf.name,'Врач-','')))
,wp.lastname||' '||wp.firstname||' '||wp.middlename,wct.timeFrom;
rownumbervar:=0;
inneri:=1;
wfidvar:=0;
for arow in select * from tableRecWf
 	loop
 		if (wfidvar=0) then
 			CREATE TEMP TABLE  tablwLoop ON COMMIT DROP as
 			select * from sqluser.selectrecordpatientsbywfanddate(arow.wfId, recdate,sstr);
 		end if;
 		select * from  tablwLoop twl where twl.numinnerselect=inneri into res;
 		update tableRecWf tr set num=cast(res.numinnerselect as bigint), timeFrom =res.timeFrom, pfio=res.pfio, rayon = res.rayon
 		, realaddr=res.realaddr,lastvisdate=res.lastvisdate,patId=res.patId
     	where tr.rownumber=rownumbervar;
    		update tableRecWf tr set num=inneri where tr.rownumber=rownumbervar;
 		select list(visits) from sqluser.selectotherrecordsbywfanddateexceptvis(res.patId, recdate,sstr,res.visId) into res2;
 		update tableRecWf tr set othervisits=res2 where tr.rownumber=rownumbervar;
    	if (arow.wfid=wfidvar) then
    		inneri:=inneri+1;
    	else
 			inneri:=1;
 			drop table if exists tablwLoop;
 			CREATE TEMP TABLE  tablwLoop ON COMMIT DROP as
 			select * from sqluser.selectrecordpatientsbywfanddate(arow.wfId, recdate,sstr);
 		end if;
 		rownumbervar:=rownumbervar+1;
 		wfidvar:=arow.wfid;
	end loop;
    		update tableRecWf tr set doc=null where tr.num<>1;
RETURN QUERY
select * from tableRecWf tr order by tr.rownumber;
END;
$function$
;