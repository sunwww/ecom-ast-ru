1532690835#
alter table VocLeftRight alter column id set default nextval('VocLeftRight_sequence');
insert into vocLeftRight (name, code) values ('ЛЕВАЯ','LEFT');
insert into vocLeftRight (name, code) values ('ПРАВАЯ','RIGHT');
insert into vocLeftRight (name, code) values ('-','');
