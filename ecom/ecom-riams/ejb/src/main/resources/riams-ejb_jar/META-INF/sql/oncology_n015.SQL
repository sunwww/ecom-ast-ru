1531818079#
alter table VocLineDrugTherapy_N015 alter column id set default nextval('VocLineDrugTherapy_N015_sequence');

INSERT INTO VocLineDrugTherapy_N015 (id_voc,name,startdate) VALUES
('1','Первая линия','01.09.2018'),
('2','Вторая линия','01.09.2018'),
('3','Третья линия','01.09.2018'),
('4','Линия после третьей','01.09.2018');