1531818079#
alter table voce2fondv025 alter column id set default nextval('voce2fondv025_sequence');
insert into voce2fondv025 (code, name,  startdate) values ( upper('1.0'),upper('Посещение по заболеванию'),to_date('01.01.2018','dd.MM.yyyy'));
insert into voce2fondv025 (code, name,  startdate) values ( upper('1.1'),upper('Посещениe в неотложной форме '),to_date('01.01.2018','dd.MM.yyyy'));
insert into voce2fondv025 (code, name,  startdate) values ( upper('1.2'),upper('Aктивное посещение '),to_date('01.01.2018','dd.MM.yyyy'));
insert into voce2fondv025 (code, name,  startdate) values ( upper('1.3'),upper('Диспансерное наблюдение '),to_date('01.01.2018','dd.MM.yyyy'));
insert into voce2fondv025 (code, name,  startdate) values ( upper('2.1'),upper('Медицинский осмотр '),to_date('01.01.2018','dd.MM.yyyy'));
insert into voce2fondv025 (code, name,  startdate) values ( upper('2.2'),upper('Диспансеризация'),to_date('01.01.2018','dd.MM.yyyy'));
insert into voce2fondv025 (code, name,  startdate) values ( upper('2.3'),upper('Комплексное обследование '),to_date('01.01.2018','dd.MM.yyyy'));
insert into voce2fondv025 (code, name,  startdate) values ( upper('2.5'),upper('Патронаж'),to_date('01.01.2018','dd.MM.yyyy'));
insert into voce2fondv025 (code, name,  startdate) values ( upper('2.6'),upper('Посещение по другим обстоятельствам '),to_date('01.01.2018','dd.MM.yyyy'));
insert into voce2fondv025 (code, name,  startdate) values ( upper('3.0'),upper('Обращение по заболеванию'),to_date('01.01.2018','dd.MM.yyyy'));
insert into voce2fondv025 (code, name,  startdate) values ( upper('3.1'),upper('Обращение с профилактической целью.'),to_date('01.01.2018','dd.MM.yyyy'));