1531985361#

alter table VocOncologyN009 alter column id set default nextval('VocOncologyN009_sequence');

INSERT INTO VocOncologyN009 (code,ds,histology,startdate) VALUES
('1','C16','1','01.09.2018'),
('2','C56','1','01.09.2018'),
('3','C56','3','01.09.2018'),
('4','C15','1','01.09.2018'),
('5','C18','1','01.09.2018'),
('6','C19','1','01.09.2018'),
('7','C20','1','01.09.2018'),
('8','C25','1','01.09.2018'),
('9','C32','1','01.09.2018'),
('10','C34','1','01.09.2018'),
('11','C34','4','01.09.2018'),
('12','C61','1','01.09.2018'),
('13','C64','5','01.09.2018'),
('14','C64','2','01.09.2018'),
('15','C67','1','01.09.2018'),
('16','C73','6','01.09.2018'),
('17','C44','7','01.09.2018'),
('18','C44','8','01.09.2018'),
('19','C50','1','01.09.2018'),
('20','C53','1','01.09.2018'),
('21','C54','9','01.09.2018'),
('22','C54','3','01.09.2018'),
('23','C15','10','01.09.2018'),
('24','C16','10','01.09.2018');