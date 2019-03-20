1552909540#
    alter table VocWayOfRecord alter column id set default nextval('VocWayOfRecord_sequence');
    INSERT INTO VocWayOfRecord(code,name,IsAvailableForRecorder) VALUES('PROMED','Промед',true),('SITE','Сайт',false),('PHONE','Телефон',true)
,('PERSONAL','Лично',true),('MEDVOX','Робот',false);