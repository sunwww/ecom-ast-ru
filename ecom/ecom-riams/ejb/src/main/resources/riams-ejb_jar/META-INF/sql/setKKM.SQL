1547448682#
    CREATE OR REPLACE FUNCTION setKKMSettings()
RETURNS void AS
$BODY$
declare
kkm_id integer;
def_lpu_id integer;
BEGIN
select keyvalue from softConfig where key='DEFAULT_LPU' into def_lpu_id;
insert into equipment(lpu_id,dtype,name,url) values(def_lpu_id,'KkmEquipment','KKM_WEB_SERVER','http://192.168.2.74:8080/posf/print/toSFPrinter') RETURNING id INTO kkm_id;
 update workfunction set kkmequipmentdefault_id=kkm_id where id=ANY(select wf.id
from SecUser_SecRole as susr inner join SecUser as su on su.id=secuser_id
left join workfunction wf on wf.secuser_id=su.id
where susr.roles_id=205 and (su.disabled is null or su.disabled='0') and (su.isSystems is null or su.isSystems='0')
 order by su.login);
delete from softConfig where key='KKM_WEB_SERVER';
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION setKKMSettings()
  OWNER TO postgres;

select setKKMSettings();

drop FUNCTION setKKMSettings();
