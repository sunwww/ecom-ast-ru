1531818079#
alter table VocTypeRadTherapy_N017 alter column id set default nextval('VocTypeRadTherapy_N017_sequence');

INSERT INTO VocTypeRadTherapy_N017 (id_voc,name,startdate) VALUES
('1','Первичной опухоли / ложа опухоли','01.09.2018'),
('2','Метастазов','01.09.2018'),
('3','Симптоматическая','01.09.2018');