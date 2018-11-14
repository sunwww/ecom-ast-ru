1542018165#
    CREATE OR REPLACE FUNCTION createshedulebycontinuevis(
    dstart text,
    dend text,
    wc bigint,
    tstart text,
    tend text,
    cont interval,
    reservetype_id bigint,
    evenodd integer,
    username varchar)
  RETURNS SETOF text AS
$BODY$
--dstart дата начала расписания;
--dend дата окончания расписания;
--wc сслыка на workcalendar;
--tstart время начала визитов;
--tend время окончания визитов;
--cont длительность визита;
--username имя создателя
declare
interv integer;
curdate date;
wcd bigint:=0;
endtime timestamp;
odd int;
temp bigint;
booltemp boolean;
BEGIN
select getIntervalDates(dstart,dend) into interv+1;
select to_date(''||dstart,'dd.MM.yyyy') into curdate;
	LOOP
	IF interv<=0
	THEN
	EXIT;
	ELSE
		select isDateExist(curdate,wc) into temp;
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
				INSERT INTO workcalendartime (timefrom,workcalendarday_id,reservetype_id,createusername) values (endtime,wcd,reserveType_id,username);
				select (endtime+ cont) into endtime;
				RETURN next endtime;
				END IF;
			END LOOP;
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
ALTER FUNCTION createshedulebycontinuevis(text, text, bigint, text, text, interval, bigint, integer)
  OWNER TO postgres;
