1548917674#
    CREATE OR REPLACE FUNCTION getTotalChildBirthOrKs(
    date1 character varying,
    date2 character varying,
    ks boolean,
    ksCode character varying)
  RETURNS integer AS
$BODY$
DECLARE
  RESULT INTEGER;
BEGIN
 RESULT:=(select count(distinct chb.id)
        from childbirth chb
left join medcase slo on slo.id=chb.medcase_id
left join robsonclass r on r.medcase_id=slo.id
left join vocrobsonclass vr on vr.id=robsontype_id
left join SurgicalOperation so on slo.id=so.medCase_id and slo.dtype='DepartmentMedCase'
left join MedCase sls on sls.id=slo.parent_id and sls.dtype='HospitalMedCase'
left join MedService vo on vo.id=so.medService_id
where chb.birthfinishdate between to_date(date1,'dd.mm.yyyy') and to_date(date2,'dd.mm.yyyy') and vr.id is not null
and case when ks=true then vo.code=ksCode else 1=1 end);
RETURN RESULT;
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION getTotalChildBirthOrKs(character varying, character varying,boolean,character varying)
  OWNER TO postgres;