1542356813#
    CREATE or REPLACE FUNCTION checkRepeatedWCTimes()
  RETURNS trigger AS
$func$
BEGIN
   IF (SELECT count(wct.id) FROM "workcalendartime" wct WHERE  wct.timefrom = NEW.timefrom and wct.workcalendarday_id=NEW.workcalendarday_id and (wct.isdeleted is null or wct.isdeleted='0'))>0 THEN return null;
   END IF;
   RETURN NEW;
END
$func$  LANGUAGE plpgsql;

CREATE TRIGGER "WCTimeBeforeInsert_trigger"
BEFORE INSERT ON workcalendartime FOR EACH ROW
EXECUTE PROCEDURE checkRepeatedWCTimes();