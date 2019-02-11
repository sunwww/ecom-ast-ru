1531919997#
alter table VocOncologyConsilium alter column id set default nextval('VocOncologyConsilium_sequence');

insert into VocOncologyConsilium (code,name) VALUES
('1','Определена тактика обследования'),
('2','Определена тактика лечения'),
('3','Изменена тактика лечения');