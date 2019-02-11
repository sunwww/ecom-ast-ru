1542698923#

CREATE OR REPLACE FUNCTION checkdayofweek(dstart text,
alld boolean,
mon boolean,
tue boolean,
wed boolean,
thu boolean,
fri boolean,
sat boolean,
sun boolean)
  RETURNS integer AS
$BODY$
--return: 0 - подходит, 1 - нет
declare
dw int;
result int;
BEGIN
result:=0;
SELECT EXTRACT(isodow  FROM to_date(''||$1,'yyyy-MM-dd'))-1 into dw;
IF alld=true or dw=0 and mon=true or dw=1 and tue=true
or dw=2 and wed=true or dw=3 and thu=true
or dw=4 and fri=true or dw=5 and sat=true
or dw=6 and sun=true
THEN
	result:=1;
END IF;
RETURN result;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION checkevenodd(text)
  OWNER TO postgres;

    CREATE OR REPLACE FUNCTION createshedulebycountvis(
    dstart text,
    dend text,
    wc bigint,
    tstart text,
    tend text,
    cont double precision,
    reservetype_id bigint,
    evenodd integer,
    username varchar,
    alld boolean,
    mon boolean,
    tue boolean,
    wed boolean,
    thu boolean,
    fri boolean,
    sat boolean,
    sun boolean
    )
  RETURNS SETOF text AS
$BODY$
--dstart дата начала расписания;
--dend дата окончания расписания;
--сслыка на workcalendar;
--tstart время начала визитов;
--tend время окончания визитов;
--cont кол-во визитов
--reserveType_id тип резерва
--evenodd четный/нечетный/все дни
--alld все дни недели
--пн-вс
declare
interv integer;
curdate date;
wcd bigint;
st int:=0;
odd int;
temp bigint;
booltemp boolean;
dw int;
BEGIN
select getIntervalDates($1,$2) into interv;
interv:=interv+1;
select to_date(''||$1,'dd.MM.yyyy') into curdate;
LOOP
RETURN next interv;
	IF interv<=0
	THEN
		EXIT;
	ELSE

		select checkEvenOdd(''||curdate) into odd;
		select isDateExist(curdate,wc) into temp;

		select checkdayofweek(''||curdate,alld,mon,tue,wed,thu,fri,sat,sun) into dw;
		if dw=1
		then
		if temp!=0
		then select isDateDelete(temp) into booltemp;
		end if;

		if temp!=0 and booltemp=true
		THEN
			IF evenodd=3
			THEN
				update workcalendarday set isdeleted = false where id = temp;
				INSERT INTO workcalendartime (timefrom,workcalendarday_id,reservetype_id,createusername,createdate,createtime)
				SELECT getTimesByCountVizits,temp,$7,username,current_date,current_time
				FROM getTimesByCountVizits($4,$5,$6);
			END IF;
			IF evenodd=0 and odd=0
			THEN
				update workcalendarday set isdeleted = false where id = temp;
				INSERT INTO workcalendartime (timefrom,workcalendarday_id,reservetype_id,createusername,createdate,createtime)
				SELECT getTimesByCountVizits,temp,$7,username,current_date,current_time
				FROM getTimesByCountVizits($4,$5,$6);
			END IF;
			IF evenodd=1 and odd=1
			THEN
				update workcalendarday set isdeleted = false where id = temp;
				INSERT INTO workcalendartime (timefrom,workcalendarday_id,reservetype_id)
				SELECT getTimesByCountVizits,temp,$7,username,current_date,current_time
				FROM getTimesByCountVizits($4,$5,$6);
			END IF;
		end if;

		IF temp=0
		THEN
			IF evenodd=3
			THEN
				INSERT INTO workcalendarday (calendardate,workcalendar_id) values (curdate,$3) returning id into wcd;
				INSERT INTO workcalendartime (timefrom,workcalendarday_id,reservetype_id,createusername,createdate,createtime)
				SELECT getTimesByCountVizits,wcd,$7,username,current_date,current_time
				FROM getTimesByCountVizits($4,$5,$6);
			END IF;
				IF evenodd=0 and odd=0
			THEN
				insert into workcalendarday (calendardate,workcalendar_id) values (curdate,$3) returning id into wcd;
				INSERT INTO workcalendartime (timefrom,workcalendarday_id,reservetype_id,createusername,createdate,createtime)
				SELECT getTimesByCountVizits,wcd,$7,username,current_date,current_time
				FROM getTimesByCountVizits($4,$5,$6);
			END IF;

			IF evenodd=1 and odd=1
			THEN
				insert into workcalendarday (calendardate,workcalendar_id) values (curdate,$3) returning id into wcd;
				INSERT INTO workcalendartime (timefrom,workcalendarday_id,reservetype_id,createusername,createdate,createtime)
				SELECT getTimesByCountVizits,wcd,$7,username,current_date,current_time
				FROM getTimesByCountVizits($4,$5,$6);
			END IF;
		END IF;
END IF;
		Return next wcd;
		select (curdate + 1) into curdate;
		interv:=interv-1;
	END IF;
END LOOP;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION createshedulebycountvis(text, text, bigint, text, text, double precision, bigint, integer,varchar, boolean, boolean, boolean, boolean, boolean, boolean, boolean, boolean)
  OWNER TO postgres;

CREATE OR REPLACE FUNCTION createshedulebycontinuevis(
    dstart text,
    dend text,
    wc bigint,
    tstart text,
    tend text,
    cont interval,
    reservetype_id bigint,
    evenodd integer,
    username character varying,
    alld boolean,
    mon boolean,
    tue boolean,
    wed boolean,
    thu boolean,
    fri boolean,
    sat boolean,
    sun boolean)
  RETURNS SETOF text AS
$BODY$
--dstart дата начала расписания;
--dend дата окончания расписания;
--wc сслыка на workcalendar;
--tstart время начала визитов;
--tend время окончания визитов;
--cont длительность визита;
--username имя создателя
--alld все дни недели
--пн-вс
declare
interv integer;
curdate date;
wcd bigint:=0;
endtime timestamp;
odd int;
temp bigint;
booltemp boolean;
dw int;
BEGIN
select getIntervalDates(dstart,dend) into interv+1;
select to_date(''||dstart,'dd.MM.yyyy') into curdate;
	LOOP
	IF interv<=0
	THEN
	EXIT;
	ELSE
		select isDateExist(curdate,wc) into temp;

		select checkdayofweek(''||curdate,alld,mon,tue,wed,thu,fri,sat,sun) into dw;
		if dw=1
		then
		if temp!=0
		then
			select isDateDelete(temp) into booltemp;
		end if;

		wcd:=0;
		select checkEvenOdd(''||curdate) into odd;


		if temp!=0 and booltemp=true
		then
			IF evenodd=3
			THEN
				update workcalendarday set isdeleted = false where id = temp;
				wcd:=temp;
			END IF;
			IF evenodd=0 and odd=0
			THEN
				update workcalendarday set isdeleted = false where id = temp;
				wcd:=temp;
			END IF;
			IF evenodd=1 and odd=1
			THEN
				update workcalendarday set isdeleted = false where id = temp;
				wcd:=temp;
			END IF;
		end if;

IF temp=0
then
		IF evenodd=3
		THEN
		insert into workcalendarday (calendardate,workcalendar_id) values (curdate,wc) returning id into wcd;
		END IF;

		IF evenodd=0 and odd=0
		THEN
		insert into workcalendarday (calendardate,workcalendar_id) values (curdate,wc) returning id into wcd;
		END IF;
		IF evenodd=1 and odd=1
		THEN
		insert into workcalendarday (calendardate,workcalendar_id) values (curdate,wc) returning id into wcd;
		END IF;
	END IF;
		IF wcd!=0
		THEN
		endtime:=to_timestamp(''||tstart,'HH24:MI:SS');
			LOOP
				IF endtime>to_timestamp(''||tend,'HH24:MI:SS')
				THEN
				EXIT;
				ELSE
				INSERT INTO workcalendartime (timefrom,workcalendarday_id,reservetype_id,createusername,createdate,createtime) values (endtime,wcd,reserveType_id,username,current_date,current_time);
				select (endtime+ cont) into endtime;
				RETURN next endtime;
				END IF;
			END LOOP;
		END IF;
END IF;
		select (curdate + 1) into curdate;
		interv:=interv-1;
	END IF;
	END LOOP;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION createshedulebycontinuevis(text, text, bigint, text, text, interval, bigint, integer, character varying)
  OWNER TO postgres;