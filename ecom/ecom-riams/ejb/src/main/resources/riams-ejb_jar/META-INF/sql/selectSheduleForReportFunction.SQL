1558585390#
    CREATE OR REPLACE FUNCTION  selectSheduleForReport()
  RETURNS TABLE(num text,wfname text,fio text,cab character varying,per text) AS
$BODY$
declare
arow record;
numvar integer;
i integer;
wfnamevar text;
numOfRows integer;
BEGIN
CREATE TEMP TABLE  tableShedule ON COMMIT DROP AS
select distinct '' as num
,upper(substring(replace(replace(vwf.name,'Врач-',''),'Врач ','') from 1 for 1)) || substring(replace(replace(vwf.name,'Врач-',''),'Врач ','') from 2 for length(replace(vwf.name,'Врач-',''))) as wfname
,wp.lastname||' '||wp.firstname||' '||wp.middlename as fio
, wpl.name as cab
, (select to_char(min(timefrom),'HH24:MM') from workcalendartime where workcalendarday_id=wcd.id)||'-'||(select to_char(max(timefrom),'HH24:MM') from workcalendartime where  workcalendarday_id=wcd.id) as per
from worker w
left join workFunction wf on w.id=wf.worker_id
left join Patient wp on wp.id=w.person_id
left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
left join workplace_workfunction wpwf on wpwf.workfunctions_id=wf.id
left join workplace wpl on wpl.id=wpwf.workplace_id
left join workcalendar wc on wc.workfunction_id=wf.id
left join workcalendarday wcd on wcd.workcalendar_id=wc.id and wcd.calendardate=current_date
left join workcalendartime wct on wct.workcalendarday_id=wcd.id
where w.lpu_id=180 and (wf.archival is null or wf.archival='0')
and wf.workFunction_id is not null
and substring(vwf.name from 1 for 4)='Врач'
and wcd.id is not null
order by upper(substring(replace(replace(vwf.name,'Врач-',''),'Врач ','') from 1 for 1)) || substring(replace(replace(vwf.name,'Врач-',''),'Врач ','') from 2 for length(replace(vwf.name,'Врач-','')))
,wp.lastname||' '||wp.firstname||' '||wp.middlename;
numvar:=1;
i:=1;
wfnamevar:='';
select keyvalue from softconfig where key='numOfRowsInPolyScheduleReport' into numOfRows;
for arow in select * from tableShedule
 loop
      if (wfnamevar <> arow.wfname or (i-1)%numOfRows=0)
      then
      if ((i-1)%numOfRows=0 and (wfnamevar = arow.wfname or arow.wfname='')) then numvar:=numvar-1; end if;
      update tableShedule ts set num=cast(numvar as character varying)||'.' where ts.fio=arow.fio and ts.wfname=arow.wfname;
      numvar:=numvar+1;
      wfnamevar :=arow.wfname;
      else
      update tableShedule ts set wfname='',num='' where ts.fio=arow.fio and ts.wfname=arow.wfname;
      end if;
      i:=i+1;
    end loop;
RETURN QUERY
select * from tableShedule;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION selectSheduleForReport()
  OWNER TO postgres;