1543496810#
alter table VocE2FondV027 alter column id set default nextval('VocE2FondV027_sequence');
insert into VocE2FondV027 ( code, name, startDate) values ( upper('1'),upper('Острое'),to_date('01.09.2018','dd.MM.yyyy'));
insert into VocE2FondV027 ( code, name, startDate) values ( upper('2'),upper('Впервые в жизни установленное хроническое'),to_date('01.09.2018','dd.MM.yyyy'));
insert into VocE2FondV027 ( code, name, startDate) values ( upper('3'),upper('Ранее установленное хроническое'),to_date('01.09.2018','dd.MM.yyyy'));
