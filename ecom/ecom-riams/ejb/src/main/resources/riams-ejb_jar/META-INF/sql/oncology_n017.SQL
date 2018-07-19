1531985361#
alter table VocOncologyN017 alter column id set default nextval('VocOncologyN017_sequence');

INSERT INTO VocOncologyN017 (id_voc,name,startdate) VALUES
('1','Первичной опухоли / ложа опухоли','01.09.2018'),
('2','Метастазов','01.09.2018'),
('3','Симптоматическая','01.09.2018');