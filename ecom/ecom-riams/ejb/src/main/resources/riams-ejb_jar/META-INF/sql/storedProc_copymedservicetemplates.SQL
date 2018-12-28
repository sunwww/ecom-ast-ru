1545894802#
    CREATE OR REPLACE FUNCTION copymedservicetemplates(
    msfrom_id bigint,
    msto_id bigint)
  RETURNS SETOF text AS
$BODY$
--msfrom_id с услуги;
--msto_id на услугу
declare
numoftemplates integer;
nowid integer;
template_id integer;
array_id bigint[];
newid integer;
BEGIN
SELECT ARRAY(select id from TemplateProtocol where medservice_id=msfrom_id) into array_id;
select array_length(array_id,1) into numoftemplates;
nowid:=0;
	LOOP
	IF numoftemplates<0 or nowid>numoftemplates
	THEN
	EXIT;
	ELSE
		insert into TemplateProtocol(type,text,date,keyword,username,title,medservice_id,type_id,disableedit,creatediarybydefault)
		select type,text,date,keyword,username,title,msto_id,type_id,disableedit,creatediarybydefault from
			TemplateProtocol where id=array_id[nowid] returning id into newid;
		INSERT INTO parameterbyform(
		 "position", parameter_id, forminput_id, createdate, createusername,
		template_id, assessmentcard, usedefaultvalue)
		select "position", parameter_id, forminput_id, createdate, createusername,
		newid, assessmentcard, usedefaultvalue from ParameterByForm p
		left join Parameter par on par.id=p.parameter_id
		left join ParameterGroup gr on gr.id=par.group_id
		left join userDomain vd on vd.id=par.valueDomain_id left join vocMeasureUnit vmu on
		vmu.id=par.measureUnit_id
		left join userValue uv on uv.domain_id=vd.id and uv.useByDefault='1'
		where p.template_id=array_id[nowid] order by p.position;
		nowid:=nowid+1;
	END IF;
	END LOOP;
	END;
	$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION copymedservicetemplates(bigint, bigint)
  OWNER TO postgres;