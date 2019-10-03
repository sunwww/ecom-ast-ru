1569399170#
    	CREATE OR REPLACE FUNCTION sqluser.updatescg()
 RETURNS text
 LANGUAGE plpgsql
AS $function$
declare
arow record;
scgid bigint;
d text;
t text;
wvfid bigint;
did bigint;
count integer;
msg text;
--Процедура для обновления консультаций (если найдёт те, которые должны были проставиться
--но не проставились, т.к. юзер не был настроен, проставит
--использовать так: select * from updatescg()
BEGIN
count:=0;
msg:=' consultations updated.';
DROP TABLE IF EXISTS tableT;
 CREATE  TABLE  tableT AS
select scg.id as scgid
,to_char(d.dateregistration,'dd.mm.yyyy') as d
,to_char(d.timeregistration,'HH24:MI:SS') as t
,wf3.id as wvfid
,d.id as did
 from prescription scg
        left join PrescriptionList pl on pl.id=scg.prescriptionList_id
        left join medcase slo on slo.id=pl.medcase_id
        left join medcase sls on sls.id=slo.parent_id or sls.id=pl.medcase_id
        left join workfunction wf on wf.id=scg.prescriptcabinet_id
        left join workfunction gwf on gwf.group_id=wf.id
        left join diary d on d.specialist_id=gwf.id and (d.medcase_id=slo.id or d.medcase_id=ANY(select id from medcase where parent_id=sls.id))
left join worker w on w.id = wf.worker_id
left join patient wp on wp.id=w.person_id
left join patient pat on slo.patient_id=pat.id
left join vocconsultingtype vtype on vtype.id=scg.vocconsultingtype_id
left join mislpu dep on dep.id=slo.department_id
left join workfunction wf2 on wf2.id=scg.intakespecial_id
left join vocworkFunction vwf2 on vwf2.id=wf2.workFunction_id
left join worker w2 on w2.id = wf2.worker_id
left join patient wp2 on wp2.id=w2.person_id
left join workfunction wf3 on wf3.id=d.specialist_id
left join vocworkFunction vwf3 on vwf3.id=wf3.workFunction_id
left join worker w3 on w3.id = wf3.worker_id
left join patient wp3 on wp3.id=w3.person_id
        where scg.diary_id is null and scg.canceldate is null and d.id is not null
	 and   scg.dtype='WfConsultation' and d.dtype<>'RoughDraft'
	 and ((scg.transferdate<d.dateregistration or (scg.transferdate=d.dateregistration and scg.transfertime<d.timeregistration))
or (scg.createdate<d.dateregistration or (scg.createdate=d.dateregistration and scg.createtime<d.timeregistration)))
	 group by scg.id,wf.id,d.id,gwf.id,vwf3.id,wp3.id,wf3.id
	 order by scg.id;
 for arow in select * from tableT
 loop
      scgid := arow.scgid;
      d := arow.d;
      t := arow.t;
      wvfid := arow.wvfid;
      did := arow.did;
      if ((select intakedate from prescription where id=scgid) is null) then update prescription set intakedate=to_date(d,'dd.mm.yyyy'),intaketime=t::time,intakespecial_id=wvfid,diary_id=did where id=scgid; end if;
      count:=count+1;
    end loop;
DROP TABLE IF EXISTS tableT;
msg:=count::text||msg;
return msg;
END;