1531919997#
alter table VocOncologyTypeDirection alter column id set default nextval('VocOncologyTypeDirection_sequence');

insert into VocOncologyTypeDirection (code,name) VALUES
('1','Направление к онкологу'),
('2','Направление на биопсию'),
('3','Направление на дообследование');