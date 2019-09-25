1569408508#
    CREATE OR REPLACE FUNCTION sqluser.cancelscg()
 RETURNS text
 LANGUAGE plpgsql
AS $function$
declare
arow record;
scgid bigint;
count integer;
msg text;
--Процедура для отмены невыполненных консультаций пациентов, которые уже выписаны
--использовать так: select * from cancelscg()
BEGIN
count:=0;
msg:=' consultations canceled.';
DROP TABLE IF EXISTS tableC;
 CREATE  TABLE  tableC AS
select distinct scg.id	as scgid
from prescription scg
left join PrescriptionList pl on pl.id=scg.prescriptionList_id
left join medcase slo on slo.id=pl.medcase_id
left join medcase sls on sls.id=slo.parent_id or sls.id=pl.medcase_id
where scg.diary_id is null and scg.canceldate is null
and   scg.dtype='WfConsultation'
and sls.datefinish is not null
order by scg.id;
 for arow in select * from tableC
 loop
      scgid := arow.scgid;
      if ((select intakedate from prescription where id=scgid) is null) then
     update prescription set canceldate=current_date,canceltime=current_time,cancelusername='admin',CancelReasonText='Выписан ' where id=scgid; end if;
      count:=count+1;
    end loop;
DROP TABLE IF EXISTS tableC;
msg:=count::text||msg;
return msg;
END;
$function$
;