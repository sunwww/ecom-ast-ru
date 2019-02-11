1548060248#
    alter table JasperVidSluchPolyclinic alter column id set default nextval('JasperVidSluchPolyclinic_sequence');
insert into JasperVidSluchPolyclinic(name) values('Обращение');
insert into JasperVidSluchPolyclinic(name) values('Неотложная помощь');
insert into JasperVidSluchPolyclinic(name) values('Посещение');

insert into jaspervidsluchpolyclinic_voce2vidsluch(JasperVidSluchPolyclinic_id,vidsluchcode_id) values(1,27);
insert into jaspervidsluchpolyclinic_voce2vidsluch(JasperVidSluchPolyclinic_id,vidsluchcode_id) values(2,17);
insert into jaspervidsluchpolyclinic_voce2vidsluch(JasperVidSluchPolyclinic_id,vidsluchcode_id) values(3,20);
insert into jaspervidsluchpolyclinic_voce2vidsluch(JasperVidSluchPolyclinic_id,vidsluchcode_id) values(3,21);
insert into jaspervidsluchpolyclinic_voce2vidsluch(JasperVidSluchPolyclinic_id,vidsluchcode_id) values(3,23);
insert into jaspervidsluchpolyclinic_voce2vidsluch(JasperVidSluchPolyclinic_id,vidsluchcode_id) values(3,24);

GRANT select on voce2vidsluch to jasper;
GRANT select on jaspervidsluchpolyclinic_voce2vidsluch to jasper;


CREATE OR REPLACE FUNCTION select_generalfinplanjasper(
    IN pdstart text,
    IN pdfin text,
    IN pbedtype bigint,
    IN pprname bigint,
    IN plpu text,
    IN ptype text,
    IN pfpplan text,
IN vidsluch bigint)
  RETURNS TABLE(ffname character varying) AS
$BODY$
BEGIN
RETURN QUERY
select distinct case when (Ptype='1' or Pfpplan='PolyclinicFinancePlan') then  profile.name else lpu.name end as ffname
from aggregatevolumesfinanceplan agr
left join vocbedsubtype bdtype on bdtype.id=agr.bedsubtype
left join voce2medhelpprofile  profile on profile.id=agr.medhelpprofile
left join mislpu lpu on lpu.id=agr.department
left join voce2vidsluch vsl on vsl.id=agr.vidsluch_id
left join jaspervidsluchpolyclinic_voce2vidsluch vslj on vslj.vidsluchcode_id=vsl.id
where agr.month>=cast((substring(Pdstart from 4 for 2)) as int)
and agr.month<=cast((substring(Pdfin from 4 for 2)) as int)
and agr.year=cast((substring(Pdstart from 7 for 4)) as int)
and case when Pbedtype is null then 1=1 else bdtype.code=cast(Pbedtype as varchar) end
and case when Pprname is null then 1=1 else profile.id=Pprname end
and  case when Plpu is null then 1=1  else lpu.id=cast(Plpu as int) end
and  case when Pfpplan is null then agr.type is not null else agr.type= Pfpplan end
and  case when vidsluch is null then 1=1 else vslj.JasperVidSluchPolyclinic_id=vidsluch end;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION select_generalfinplanjasper(text, text, bigint, bigint, text, text, text)
  OWNER TO postgres;


CREATE OR REPLACE FUNCTION select_generalsub1jasper(
    IN pdstart text,
    IN pdfin text,
    IN pbedtype bigint,
    IN pffname text,
    IN ptype text,
    IN pfpplan text,
IN vidsluch bigint)
  RETURNS TABLE(ksg text, factcount numeric, plancount numeric, numper text, plancost numeric, factcost numeric, costper text) AS
$BODY$
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
left join voce2vidsluch vsl on vsl.id=agr.vidsluch_id
left join jaspervidsluchpolyclinic_voce2vidsluch vslj on vslj.vidsluchcode_id=vsl.id
where  case when Ptype='2' then lpu.name like  Pffname else 1=1 end
and case when  Ptype='1' then profile.name like  Pffname else 1=1 end
and agr.month>=cast((substring(Pdstart from 4 for 2)) as int)
and agr.month<=cast((substring(Pdfin from 4 for 2)) as int)
and agr.year=cast((substring(Pdstart from 7 for 4)) as int)
and case when Pbedtype is null then 1=1 else bdtype.code=Pbedtype::char end
and  case when Pfpplan is null then 1=1 else agr.type= Pfpplan end
and  case when vidsluch is null then 1=1 else vslj.JasperVidSluchPolyclinic_id=vidsluch end
group by ksg.id
order by cast(ksg.code as int);
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION select_generalsub1jasper(text, text, bigint, text, text, text)
  OWNER TO postgres;

CREATE OR REPLACE FUNCTION select_generalsub2jasper(
    IN pdstart text,
    IN pdfin text,
    IN pbedtype bigint,
    IN plpu text,
    IN ptype text,
    IN pprname bigint,
    IN pfpplan text,
IN vidsluch bigint)
  RETURNS TABLE(factcount numeric, plancount numeric, per1 text, plancost numeric, factcost numeric, per2 text) AS
$BODY$
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
left join voce2vidsluch vsl on vsl.id=agr.vidsluch_id
left join jaspervidsluchpolyclinic_voce2vidsluch vslj on vslj.vidsluchcode_id=vsl.id
where  case when Plpu is null then 1=1 else lpu.id=cast(Plpu as int) end
and case when Pprname is null then 1=1 else profile.id=Pprname end
and case when Pbedtype is null then 1=1 else bdtype.code=Pbedtype::char end
and agr.month>=cast((substring(Pdstart from 4 for 2)) as int)
and agr.month<=cast((substring(Pdfin from 4 for 2)) as int)
and agr.year=cast((substring(Pdstart from 7 for 4)) as int)
and  case when Pfpplan is null then 1=1 else agr.type= Pfpplan end
and  case when vidsluch is null then 1=1 else vslj.JasperVidSluchPolyclinic_id=vidsluch end;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION select_generalsub2jasper(text, text, bigint, text, text, bigint, text)
  OWNER TO postgres;