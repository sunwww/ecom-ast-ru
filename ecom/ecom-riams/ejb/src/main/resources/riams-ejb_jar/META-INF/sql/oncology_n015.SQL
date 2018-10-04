1531985361#
alter table VocOncologyN015 alter column id set default nextval('VocOncologyN015_sequence');

INSERT INTO VocOncologyN015 (code,name,startdate) VALUES
('1','Первая линия','01.09.2018'),
('2','Вторая линия','01.09.2018'),
('3','Третья линия','01.09.2018'),
('4','Линия после третьей','01.09.2018');
INSERT INTO VocOncologyN015 (code,name,startdate) VALUES
('5','Неоадъювантная','01.09.2018'),
('6','Адъювантная','01.09.2018'),
('7','Периоперационная (до хирургического лечения','01.09.2018'),
('8','Периоперационная (после хирургического лечения)','01.09.2018');