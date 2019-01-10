1547037212#
    CREATE OR REPLACE FUNCTION select_CostSertificateJasper(
hn text,
fy int
)
  RETURNS TABLE (
lastname   character varying(255)
, firstname   character varying(255)
, middlename   character varying(255)
, birthdate date
, historynumber character varying(255)
, depname text
, startdate date
, finishdate date
, beddays bigint
, mkb text
, ksg text
, oper text
, cost numeric
, sstr character varying(255)
, bill text
) AS
$func$
BEGIN
RETURN QUERY
select e2.lastname,e2.firstname,e2.middlename,e2.birthdate,e2.historynumber,e2.departmentname||','||
ve2fv.name as depname,e2.startdate,e2.finishdate,e2.beddays,
case when (e2.mainMkb is not null and e2.mainMkb<>'')  then  e2.mainMkb||' '||(select name from vocidc10 where code=e2.mainMkb)
 else list(idc.code||' '||idc.name) end as mkb,
ksg.code||' '||ksg.name as ksg,list(vms.code||' '||vms.name) as oper,e2.cost as cost,e2.servicestream as sstr
,CAST('№' as varchar(5))||e2.billnumber||CAST(' от ' as varchar(5))||to_char(e2.billdate, 'DD.MM.YYYY') as bill
from e2entry e2
left join VocE2FondV010 ve2fv on e2.idsp_id=ve2fv.id
left join vocksg ksg on e2.ksg_id=ksg.id
left join entrydiagnosis ed on ed.entry_id=e2.id
left join vocidc10 idc on ed.mkb_id=idc.id
left join vocdiagnosisregistrationtype dreg on ed.registrationtype_id=dreg.id
left join entrymedservice em  on em.entry_id=e2.id
left join vocmedservice vms on vms.id=em.medservice_id
where (dreg.code='3' or dreg.code='4') and e2.historynumber =  hn  and EXTRACT(YEAR from e2.finishdate)=fy
 and (e2.isDeleted is null or e2.isdeleted ='0') and e2.cost  is not null and e2.servicestream<>'COMPLEXCASE'
group by e2.id,ve2fv.id,ksg.id,e2.servicestream
limit 1;
END;
$func$
  LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION select_RegistryForPaymentForContractsJasper(
Psstream text,
Pdstart text,
Pdfin text,
Ptype text
)
  RETURNS TABLE (
row_number   bigint
, fio   text
, birthdate date
, dep text
, period text
, beddays bigint
, mkb text
, ksgall text
, basetarif numeric
, totalcoefficient numeric
, costformulastring character varying(255)
, cost numeric
, sstr text
, total numeric
) AS
$func$
BEGIN
RETURN QUERY
select row_number() OVER(),e2.lastname||' ' ||e2.firstname||' ' ||e2.middlename as fio,e2.birthdate
,case when e2.stactype is not null then e2.departmentname||','||ve2fv.name
else
vef6.name||' '||vef15.name
end as dep
,to_char(e2.startdate, 'DD.MM.YYYY')||' - '||to_char(e2.finishdate, 'DD.MM.YYYY') as period,e2.beddays,
case when (e2.mainMkb is not null and e2.mainMkb<>'')  then  e2.mainMkb||' '||(select list(name) from vocidc10 where code=e2.mainMkb)
 else list(idc.code||' '||idc.name) end as mkb,
case when e2.stactype is null then '' else ksg.code||' '||ksg.name end as ksgall
,e2.basetarif,e2.totalCoefficient,e2.costformulastring,e2.cost,max(sstr.name) as sstr
,
(select sum(e2.cost) from e2entry e2
where servicestream like Psstream and e2.finishdate between to_date(Pdstart,'dd.mm.yyy') and to_date(Pdfin,'dd.mm.yyy')
and case when (Ptype is not null and '2'='2') then stactype is not null else
case when (Ptype is not null and '2'='3') then  stactype is null else
1=1 end end and (e2.isdeleted is null or e2.isdeleted=false) and (donotsend is null or e2.donotsend=false))
as total
from e2entry e2
left join VocE2FondV010 ve2fv on e2.idsp_id=ve2fv.id
left join vocksg ksg on e2.ksg_id=ksg.id
left join entrydiagnosis ed on ed.entry_id=e2.id
left join vocidc10 idc on ed.mkb_id=idc.id
left join vocdiagnosisregistrationtype dreg on ed.registrationtype_id=dreg.id
left join entrymedservice em  on em.entry_id=e2.id
left join medservice ms on ms.id=em.medservice_id
left join vocmedservice vms on vms.id=ms.vocmedservice_id
left join voce2fondv006 vef6 on e2.medhelpusl_id=vef6.id
left join voce2fondv015 vef15 on e2.fonddoctorspec_id=vef15.id
left join vocservicestream sstr on sstr.code=e2.servicestream
left join e2bill bill on e2.bill_id=bill.id
where servicestream like Psstream and e2.finishdate between to_date(Pdstart,'dd.mm.yyy') and to_date(Pdfin,'dd.mm.yyy')
and (e2.isdeleted is null or e2.isdeleted=false) and (donotsend is null or e2.donotsend=false)
and case when Ptype='2' then stactype is not null else
case when Ptype='3' then  stactype is null else
1=1 end end
group by e2.id,ve2fv.id,ksg.id,vef6.name,vef15.name;
END;
$func$
  LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION select_generalFinPlanJasper(
Pdstart text,
Pdfin text,
Pbedtype bigint,
Pprname bigint,
Plpu text,
Ptype text,
Pfpplan text
)
  RETURNS TABLE (
ffname character varying(255)
) AS
$func$
BEGIN
RETURN QUERY
select distinct case when (Ptype='1' or Pfpplan='PolyclinicFinancePlan') then  profile.name else lpu.name end as ffname
from aggregatevolumesfinanceplan agr
left join vocbedsubtype bdtype on bdtype.id=agr.bedsubtype
left join voce2medhelpprofile  profile on profile.id=agr.medhelpprofile
left join mislpu lpu on lpu.id=agr.department
where agr.month>=cast((substring(Pdstart from 4 for 2)) as int)
and agr.month<=cast((substring(Pdfin from 4 for 2)) as int)
and agr.year=cast((substring(Pdstart from 7 for 4)) as int)
and case when Pbedtype is null then 1=1 else bdtype.code=cast(Pbedtype as varchar) end
and case when Pprname is null then 1=1 else profile.id=Pprname end
and  case when Plpu is null then 1=1  else lpu.id=cast(Plpu as int) end
and  case when Pfpplan is null then agr.type is not null else agr.type= Pfpplan end;
END;
$func$
  LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION select_generalSub1Jasper(
Pdstart text,
Pdfin text,
Pbedtype bigint,
Pffname text,
Ptype text,
Pfpplan text
)
  RETURNS TABLE (
ksg text,
factcount numeric,
plancount numeric,
numper text,
plancost numeric,
factcost numeric,
costper text
) AS
$func$
BEGIN
RETURN QUERY
select ksg.code||' '||ksg.name as ksg,sum(agr.factcount) as factCount, sum(agr.plancount) as planCount,
   case when sum(agr.plancount)!=0 then round(cast(sum(agr.factcount) as numeric)/cast(sum(agr.plancount) as numeric)*100.0,2)||'%' else '0%' end as numper
  ,cast(sum(agr.plancost) as numeric) as planCost,cast(sum(agr.factcost) as numeric) as factCost,
case when sum(agr.plancost)!=0 then round(cast(sum(agr.factcost) as numeric)/cast(sum(agr.plancost) as numeric)*100.0,2)||'%' else '0%' end
 as costper
 from aggregatevolumesfinanceplan agr
left join vocksg ksg on ksg.id=agr.ksg
left join voce2medhelpprofile  profile on profile.id=agr.medhelpprofile
left join vocbedsubtype bdtype on bdtype.id=agr.bedsubtype
left join mislpu lpu on lpu.id=agr.department
where  case when Ptype='2' then lpu.name like  Pffname else 1=1 end
and case when  Ptype='1' then profile.name like  Pffname else 1=1 end
and agr.month>=cast((substring(Pdstart from 4 for 2)) as int)
and agr.month<=cast((substring(Pdfin from 4 for 2)) as int)
and agr.year=cast((substring(Pdstart from 7 for 4)) as int)
and case when Pbedtype is null then 1=1 else bdtype.code=Pbedtype::char end
and  case when Pfpplan is null then 1=1 else agr.type= Pfpplan end
group by ksg.id
order by cast(ksg.code as int);
END;
$func$
  LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION select_generalSub2Jasper(
Pdstart text,
Pdfin text,
Pbedtype bigint,
Plpu text,
Ptype text,
Pprname bigint,
Pfpplan text
)
  RETURNS TABLE (
factcount numeric,
plancount numeric,
per1 text,
plancost numeric,
factcost numeric,
per2 text
) AS
$func$
BEGIN
RETURN QUERY
select sum(agr.factcount) as factCount, sum(agr.plancount) as planCount,
   case when sum(agr.plancount)!=0 then round(cast(sum(agr.factcount) as numeric)/cast(sum(agr.plancount) as numeric)*100.0,2)||'%' else '0%' end as per1
  ,cast(sum(agr.plancost) as numeric) as planCost,cast(sum(agr.factcost) as numeric) as factCost,
case when sum(agr.plancost)!=0 then round(cast(sum(agr.factcost) as numeric)/cast(sum(agr.plancost) as numeric)*100.0,2)||'%' else '0%' end
 as per2
 from aggregatevolumesfinanceplan agr
left join voce2medhelpprofile  profile on profile.id=agr.medhelpprofile
left join vocbedsubtype bdtype on bdtype.id=agr.bedsubtype
left join mislpu lpu on lpu.id=agr.department
where  case when Plpu is null then 1=1 else lpu.id=cast(Plpu as int) end
and case when Pprname is null then 1=1 else profile.id=Pprname end
and case when Pbedtype is null then 1=1 else bdtype.code=Pbedtype::char end
and agr.month>=cast((substring(Pdstart from 4 for 2)) as int)
and agr.month<=cast((substring(Pdfin from 4 for 2)) as int)
and agr.year=cast((substring(Pdstart from 7 for 4)) as int)
and  case when Pfpplan is null then 1=1 else agr.type= Pfpplan end;
END;
$func$
  LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION select_totalFinPlanJasper(
Pdstart text,
Pdfin text,
Pbedtype bigint,
Pprname bigint,
Plpu text,
Ptype text,
Pfpplan text
)
  RETURNS TABLE (
ffname character varying(255)
) AS
$func$
BEGIN
RETURN QUERY
select distinct lpu.name as ffname
from aggregatevolumesfinanceplan agr
left join vocbedsubtype bdtype on bdtype.id=agr.bedsubtype
left join voce2medhelpprofile  profile on profile.id=agr.medhelpprofile
left join mislpu lpu on lpu.id=agr.department
where agr.month>=cast((substring(Pdstart from 4 for 2)) as int)
and agr.month<=cast((substring(Pdfin from 4 for 2)) as int)
and agr.year=cast((substring(Pdstart from 7 for 4)) as int)
and case when Pbedtype is null then 1=1 else bdtype.code=cast(Pbedtype as varchar) end
and case when Pprname is null then 1=1  else profile.id=Pprname end
and  case when Plpu is null then 1=1  else lpu.id=cast(Plpu as int) end
and  case when Pfpplan is null then 1=1 else agr.type= Pfpplan end;
END;
$func$
  LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION select_totalFinPlanSubJasper(
Pdstart text,
Pdfin text,
Pbedtype bigint,
Pffname text,
Plpu text,
Ptype text,
Pfpplan text
)
  RETURNS TABLE (
factcount numeric,
plancount numeric,
numper text,
plancost numeric,
factcost numeric,
costper text
) AS
$func$
BEGIN
RETURN QUERY
select sum(agr.factcount) as factCount, sum(agr.plancount) as planCount,
   case when sum(agr.plancount)!=0 then round(cast(sum(agr.factcount) as numeric)/cast(sum(agr.plancount) as numeric)*100.0,2)||'%' else '0%' end as numper
  ,cast(sum(agr.plancost) as numeric) as planCost,cast(sum(agr.factcost) as numeric) as factCost,
case when sum(agr.plancost)!=0 then round(cast(sum(agr.factcost) as numeric)/cast(sum(agr.plancost) as numeric)*100.0,2)||'%' else '0%' end
 as costper
 from aggregatevolumesfinanceplan agr
left join vocksg ksg on ksg.id=agr.ksg
left join voce2medhelpprofile  profile on profile.id=agr.medhelpprofile
left join vocbedsubtype bdtype on bdtype.id=agr.bedsubtype
left join mislpu lpu on lpu.id=agr.department
where  case when Ptype='2' then lpu.name like  Pffname else 1=1 end
and case when Plpu is null then 1=1 else lpu.id=cast(Plpu as int) end
and case when  Ptype='1' then profile.name like  Pffname else 1=1 end
and agr.month>=cast((substring(Pdstart from 4 for 2)) as int)
and agr.month<=cast((substring(Pdfin from 4 for 2)) as int)
and agr.year=cast((substring(Pdstart from 7 for 4)) as int)
and case when Pbedtype is null then 1=1  else bdtype.code=Pbedtype::char end
and case when (Ptype is null or Pfpplan<>'PolyclinicFinancePlan' or Ptype<>'2' and  Ptype<>'1')
 then lpu.name like  Pffname else 1=1 end
and  case when Pfpplan is null then 1=1 else agr.type= Pfpplan end;
END;
$func$
  LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION select_totalFinPlanSub2Jasper(
Pdstart text,
Pdfin text,
Pbedtype bigint,
Pprname bigint,
Plpu text,
Ptype text,
Pfpplan text
)
  RETURNS TABLE (
factcount numeric,
plancount numeric,
per1 text,
factcost numeric,
plancost numeric,
per2 text
) AS
$func$
BEGIN
RETURN QUERY
select sum(agr.factcount) as factCount, sum(agr.plancount) as planCount,
   case when sum(agr.plancount)!=0 then round(cast(sum(agr.factcount) as numeric)/cast(sum(agr.plancount) as numeric)*100.0,2)||'%' else '0%' end as per1
  ,cast(sum(agr.plancost) as numeric) as planCost,cast(sum(agr.factcost) as numeric) as factCost,
case when sum(agr.plancost)!=0 then round(cast(sum(agr.factcost) as numeric)/cast(sum(agr.plancost) as numeric)*100.0,2)||'%' else '0%' end
 as per2
 from aggregatevolumesfinanceplan agr
left join voce2medhelpprofile  profile on profile.id=agr.medhelpprofile
left join vocbedsubtype bdtype on bdtype.id=agr.bedsubtype
left join mislpu lpu on lpu.id=agr.department
where  case when Plpu is null then 1=1  else lpu.id=cast(Plpu as int) end
and case when Pprname is null then 1=1  else profile.id=Pprname end
and case when Pbedtype is null then 1=1  else bdtype.code=Pbedtype::char end
and agr.month>=cast((substring(Pdstart from 4 for 2)) as int)
and agr.month<=cast((substring(Pdfin from 4 for 2)) as int)
and agr.year=cast((substring(Pdstart from 7 for 4)) as int)
and  case when Pfpplan is null then 1=1 else agr.type= Pfpplan end;
END;
$func$
  LANGUAGE plpgsql;


GRANT USAGE ON SCHEMA sqluser to jasper;

GRANT select ON e2entry to jasper;
GRANT select ON VocE2FondV010 to jasper;
GRANT select ON vocidc10 to jasper;
GRANT select ON vocksg to jasper;
GRANT select ON entrydiagnosis to jasper;
GRANT select ON vocdiagnosisregistrationtype to jasper;
GRANT select ON entrymedservice to jasper;
GRANT select ON vocmedservice to jasper;
GRANT select ON voce2fondv006 to jasper;
GRANT select ON voce2fondv015 to jasper;
GRANT select ON e2bill to jasper;
GRANT select ON vocservicestream to jasper;
GRANT select ON aggregatevolumesfinanceplan to jasper;
GRANT select ON vocbedsubtype to jasper;
GRANT select ON voce2medhelpprofile to jasper;
GRANT select ON mislpu to jasper;