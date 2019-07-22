1563794785#
    CREATE OR REPLACE FUNCTION select1RowSubRobsonById(
    IN ksCode character varying,
    IN dateBegin character varying,
    IN dateEnd character varying,
    IN r integer,
    IN ks integer,
    IN rId integer,
    IN srId integer,
    IN outNum character varying
    )
  RETURNS TABLE(outNumR character varying,vr text, vrname character varying, ksnum numeric, chbc numeric, per1 numeric, per2 numeric, per3 numeric, per4 numeric) AS
$BODY$
BEGIN
RETURN QUERY
select outNum as outNumR,'&vrid='||coalesce(t.vrid,0)||'&vrname='||coalesce(t.vrname,'')||'&vsrid='||coalesce(t.vsrid,0)||'&vsrname='||coalesce(t.vsrname,'все')
,case when srId is not null then t.vsrname else t.vrname end  as vrname,sum(t.ksnum) as ks,sum(t.chbc) as chbc,
                case when r=0 then 0 else round(100*sum(t.chbc)/cast(r as numeric),1) end as per1,
                case when sum(t.chbc)=0 then 0 else round(100*sum(t.ksnum)/cast(sum(t.chbc) as numeric),1) end  as per2,
                case when ks=0 then 0 else round(100*sum(t.ksnum)/cast(ks as numeric),1) end  as per3,
                case when r=0 then 0 else round(100*sum(t.ksnum)/cast(r as numeric),1) end  as per4
                from (
                select vr.id as vrid,vsr.id as vsrid,vr.name as vrname,vsr.name as vsrname,
                count(distinct chb.id) as chbc,
                (select count(distinct chb1.id) from medcase slo
                left join childbirth chb1 on chb1.medcase_id=slo.id
                left join robsonclass r on r.medcase_id=slo.id
                left join vocrobsonclass vr on vr.id=robsontype_id
                left join SurgicalOperation so on so.medCase_id=slo.id
                left join MedCase sls on sls.id=slo.parent_id and sls.dtype='HospitalMedCase'
                left join MedService vo on vo.id=so.medService_id
                where chb1.id=chb.id and vo.code=ksCode and slo.dtype='DepartmentMedCase' and vr.id is not null) as ksnum
                from medcase slo
                left join childbirth chb on chb.medcase_id=slo.id
                left join robsonclass r on r.medcase_id=slo.id
                left join vocrobsonclass vr on vr.id=r.robsontype_id
                left join vocsubrobson vsr on vsr.id=r.robsonsub_id and vsr.id=srId
                left join SurgicalOperation so on so.medCase_id=slo.id
                left join MedCase sls on sls.id=slo.parent_id and sls.dtype='HospitalMedCase'
                left join MedService vo on vo.id=so.medService_id
                where chb.birthfinishdate between to_date(dateBegin,'dd.mm.yyyy') and to_date(dateEnd,'dd.mm.yyyy') and case when rId is not null then vr.id=rId else vr.id is not null end
                 and slo.dtype='DepartmentMedCase'
                 and case when srId is not null then vsr.id=srId else 1=1 end
                group by vr.name,chb.id,vr.id,vsr.id) as t
                group by t.vrid,t.vrname,t.vsrid,t.vsrname
                order by t.vrid;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION select1RowSubRobsonById(character varying, character varying,character varying,integer,integer,integer,integer, character varying)
  OWNER TO postgres;

CREATE OR REPLACE FUNCTION gettotalchildbirthorks(
    date1 character varying,
    date2 character varying,
    ks boolean,
    kscode character varying)
  RETURNS integer AS
$BODY$
DECLARE
  RESULT INTEGER;
BEGIN
 RESULT:=(select count(distinct chb.id)
       from medcase slo
  left join childbirth chb on chb.medcase_id=slo.id
left join robsonclass r on r.medcase_id=slo.id
left join vocrobsonclass vr on vr.id=robsontype_id
left join MedCase sls on sls.id=slo.parent_id and sls.dtype='HospitalMedCase'
left join SurgicalOperation so on so.medCase_id=slo.id
left join MedService vo on vo.id=so.medService_id
where chb.birthfinishdate between to_date(date1,'dd.mm.yyyy') and to_date(date2,'dd.mm.yyyy') and vr.id is not null  and slo.dtype='DepartmentMedCase'
and case when ks=true then vo.code=ksCode else 1=1 end);
RETURN RESULT;
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION gettotalchildbirthorks(character varying, character varying, boolean, character varying)
  OWNER TO postgres;

CREATE OR REPLACE FUNCTION selectRobson(
    IN ksCode character varying,
    IN dateBegin character varying,
    IN dateEnd character varying,
    IN r integer,
    IN ks integer
    )
  RETURNS TABLE(outNumR character varying,vr text, vrname character varying, ksnum numeric, chbc numeric, per1 numeric, per2 numeric, per3 numeric, per4 numeric) AS
$BODY$

BEGIN
CREATE TEMP TABLE  tableRobson ON COMMIT DROP AS
select * from select1RowSubRobsonById(ksCode,dateBegin,dateEnd,r,ks,1,null,'1');
IF (select count(*) from tableRobson tr where tr.outNumR='1')=0
THEN
insert into tableRobson(outNumR,vr,vrname,ksnum,chbc,per1,per2,per3,per4) values('1',selectRobsonInfo(1,null),(select name from vocrobsonclass where id=1),0,0,round(0,2),round(0,2),round(0,2),round(0,2));
END IF;
insert into tableRobson select * from select1RowSubRobsonById(ksCode,dateBegin,dateEnd,r,ks,2,null,'2');
IF (select count(*) from tableRobson tr where tr.outNumR='2')=0
THEN
insert into tableRobson(outNumR,vr,vrname,ksnum,chbc,per1,per2,per3,per4) values('2',selectRobsonInfo(2,null),(select name from vocrobsonclass where id=2),0,0,round(0,2),round(0,2),round(0,2),round(0,2));
END IF;
insert into tableRobson select * from select1RowSubRobsonById(ksCode,dateBegin,dateEnd,r,ks,2,1,'2a');
IF (select count(*) from tableRobson tr where tr.outNumR='2a')=0
THEN
insert into tableRobson(outNumR,vr,vrname,ksnum,chbc,per1,per2,per3,per4) values('2a',selectRobsonInfo(2,1),(select name from vocsubrobson where id=1),0,0,round(0,2),round(0,2),round(0,2),round(0,2));
END IF;
insert into tableRobson select * from select1RowSubRobsonById(ksCode,dateBegin,dateEnd,r,ks,2,2,'2b');
IF (select count(*) from tableRobson tr where tr.outNumR='2b')=0
THEN
insert into tableRobson(outNumR,vr,vrname,ksnum,chbc,per1,per2,per3,per4) values('2b',selectRobsonInfo(2,2),(select name from vocsubrobson where id=2),0,0,round(0,2),round(0,2),round(0,2),round(0,2));
END IF;
insert into tableRobson select * from select1RowSubRobsonById(ksCode,dateBegin,dateEnd,r,ks,3,null,'3');
IF (select count(*) from tableRobson tr where tr.outNumR='3')=0
THEN
insert into tableRobson(outNumR,vr,vrname,ksnum,chbc,per1,per2,per3,per4) values('3',selectRobsonInfo(3,null),(select name from vocrobsonclass where id=3),0,0,round(0,2),round(0,2),round(0,2),round(0,2));
END IF;
insert into tableRobson select * from select1RowSubRobsonById(ksCode,dateBegin,dateEnd,r,ks,4,null,'4');
IF (select count(*) from tableRobson tr where tr.outNumR='4')=0
THEN
insert into tableRobson(outNumR,vr,vrname,ksnum,chbc,per1,per2,per3,per4) values('4',selectRobsonInfo(4,null),(select name from vocrobsonclass where id=4),0,0,round(0,2),round(0,2),round(0,2),round(0,2));
END IF;
insert into tableRobson select * from select1RowSubRobsonById(ksCode,dateBegin,dateEnd,r,ks,4,1,'4a');
IF (select count(*) from tableRobson tr where tr.outNumR='4a')=0
THEN
insert into tableRobson(outNumR,vr,vrname,ksnum,chbc,per1,per2,per3,per4) values('4a',selectRobsonInfo(4,1),(select name from vocsubrobson where id=1),0,0,round(0,2),round(0,2),round(0,2),round(0,2));
END IF;
insert into tableRobson select * from select1RowSubRobsonById(ksCode,dateBegin,dateEnd,r,ks,4,2,'4b');
IF (select count(*) from tableRobson tr where tr.outNumR='4b')=0
THEN
insert into tableRobson(outNumR,vr,vrname,ksnum,chbc,per1,per2,per3,per4) values('4b',selectRobsonInfo(4,2),(select name from vocsubrobson where id=2),0,0,round(0,2),round(0,2),round(0,2),round(0,2));
END IF;
insert into tableRobson select * from select1RowSubRobsonById(ksCode,dateBegin,dateEnd,r,ks,5,null,'5');
IF (select count(*) from tableRobson tr where tr.outNumR='5')=0
THEN
insert into tableRobson(outNumR,vr,vrname,ksnum,chbc,per1,per2,per3,per4) values('5',selectRobsonInfo(5,null),(select name from vocrobsonclass where id=5),0,0,round(0,2),round(0,2),round(0,2),round(0,2));
END IF;
insert into tableRobson select * from select1RowSubRobsonById(ksCode,dateBegin,dateEnd,r,ks,5,3,'5_1');
IF (select count(*) from tableRobson tr where tr.outNumR='5_1')=0
THEN
insert into tableRobson(outNumR,vr,vrname,ksnum,chbc,per1,per2,per3,per4) values('5_1',selectRobsonInfo(5,3),(select name from vocsubrobson where id=3),0,0,round(0,2),round(0,2),round(0,2),round(0,2));
END IF;
insert into tableRobson select * from select1RowSubRobsonById(ksCode,dateBegin,dateEnd,r,ks,5,4,'5_2');
IF (select count(*) from tableRobson tr where tr.outNumR='5_2')=0
THEN
insert into tableRobson(outNumR,vr,vrname,ksnum,chbc,per1,per2,per3,per4) values('5_2',selectRobsonInfo(5,4),(select name from vocsubrobson where id=4),0,0,round(0,2),round(0,2),round(0,2),round(0,2));
END IF;
insert into tableRobson select * from select1RowSubRobsonById(ksCode,dateBegin,dateEnd,r,ks,6,null,'6');
IF (select count(*) from tableRobson tr where tr.outNumR='6')=0
THEN
insert into tableRobson(outNumR,vr,vrname,ksnum,chbc,per1,per2,per3,per4) values('6',selectRobsonInfo(6,null),(select name from vocrobsonclass where id=6),0,0,round(0,2),round(0,2),round(0,2),round(0,2));
END IF;
insert into tableRobson select * from select1RowSubRobsonById(ksCode,dateBegin,dateEnd,r,ks,7,null,'7');
IF (select count(*) from tableRobson tr where tr.outNumR='7')=0
THEN
insert into tableRobson(outNumR,vr,vrname,ksnum,chbc,per1,per2,per3,per4) values('7',selectRobsonInfo(7,null),(select name from vocrobsonclass where id=7),0,0,round(0,2),round(0,2),round(0,2),round(0,2));
END IF;
insert into tableRobson select * from select1RowSubRobsonById(ksCode,dateBegin,dateEnd,r,ks,8,null,'8');
IF (select count(*) from tableRobson tr where tr.outNumR='8')=0
THEN
insert into tableRobson(outNumR,vr,vrname,ksnum,chbc,per1,per2,per3,per4) values('8',selectRobsonInfo(8,null),(select name from vocrobsonclass where id=8),0,0,round(0,2),round(0,2),round(0,2),round(0,2));
END IF;
insert into tableRobson select * from select1RowSubRobsonById(ksCode,dateBegin,dateEnd,r,ks,9,null,'9');
IF (select count(*) from tableRobson tr where tr.outNumR='9')=0
THEN
insert into tableRobson(outNumR,vr,vrname,ksnum,chbc,per1,per2,per3,per4) values('9',selectRobsonInfo(9,null),(select name from vocrobsonclass where id=9),0,0,round(0,2),round(0,2),round(0,2),round(0,2));
END IF;
insert into tableRobson select * from select1RowSubRobsonById(ksCode,dateBegin,dateEnd,r,ks,9,null,'9');
IF (select count(*) from tableRobson tr where tr.outNumR='10')=0
THEN
insert into tableRobson(outNumR,vr,vrname,ksnum,chbc,per1,per2,per3,per4) values('10',selectRobsonInfo(10,null),(select name from vocrobsonclass where id=10),0,0,round(0,2),round(0,2),round(0,2),round(0,2));
END IF;
insert into tableRobson(outNumR,vr,vrname,ksnum,chbc,per1,per2,per3,per4) values(null,'&vrid=-1'||'&vrname=все'||'&vsrid=0'||'&vsrname=все',null,ks,r,null,null,null,null);
RETURN QUERY
select * from tableRobson;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION selectRobson(character varying, character varying,character varying,integer,integer)
  OWNER TO postgres;
