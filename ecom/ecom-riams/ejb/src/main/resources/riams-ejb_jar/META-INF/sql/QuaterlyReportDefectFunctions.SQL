1562572857#
    CREATE OR REPLACE FUNCTION selectdefectpercent(
   IN veqcdId integer, --vocqualityestimationcritdefect (дефект)
   total integer, --общее количество карт
   wmlId integer, --mislpu (отделение)
   crDate varchar,
   fDate varchar,
   kindId integer)
  RETURNS text AS
$BODY$
declare
res text;
--Процедура возвращает процент конкретного дефекта по отношению к общему числу карт
BEGIN
select count(vqecd.id)||' ('||round(cast(count(vqecd.id)/cast(total as real)*100 as numeric),2)||'%) - '||vqecd.name||'.'
from qualityestimationcard qec
left join workfunction wf on wf.id=qec.doctorcase_id
left join worker w on w.id=wf.worker_id
left join mislpu wml on wml.id=w.lpu_id
left join qualityestimation qe on qe.card_id=qec.id
left join qualityestimationcrit qecr on qecr.estimation_id=qe.id
left join vocqualityestimationmark vqem on vqem.id=qecr.mark_id
left join vocqualityestimationcrit vqec on vqec.kind_id = qec.kind_id and vqec.id=qecr.criterion_id
left join qualityestimationcritdefect qecd on qecd.criterion=qecr.id
left join vocqualityestimationcritdefect vqecd on vqecd.id=qecd.defect
 where  qec.createDate between to_date(crDate,'dd.MM.yyyy') and to_date(fDate,'dd.MM.yyyy')
and qec.kind_id=kindId
 and wml.id=wmlId
and vqecd.id=veqcdId
group by vqecd.name into res;
return res;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION selectdefectpercent(integer, integer, integer,varchar,varchar,integer)
  OWNER TO postgres;

CREATE OR REPLACE FUNCTION selectalldefectspercent(
IN veqcId integer, --vocqualityestimationcrit (критерий)
   total integer, --общее количество карт
   wmlId integer, --mislpu (отделение)
   crDate varchar,
   fDate varchar,
   kindId integer)
  RETURNS text AS
$BODY$
declare
arow record;
res text;
resRow text;
--Процедура возвращает проценты по всем дефектам критерия
BEGIN
drop table if exists tabledefects;
CREATE TEMP TABLE  tableDefects ON COMMIT DROP AS
select vqecd.id from vocqualityestimationcritdefect vqecd where vqecd.criterion=ANY(
select vqec.id from vocqualityestimationcrit vqec where vqec.kind_id=3 and vqec.id=veqcId);
res:='';
 for arow in select * from tableDefects
 loop
      select * from selectdefectpercent(cast(arow.id as integer),total,wmlId,crDate,fDate,kindId) into resRow;
      if (resRow is not null) then res:=res||' '||resRow; end if;
    end loop;
return res;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION selectalldefectspercent(integer, integer, integer,varchar,varchar,integer)
  OWNER TO postgres;