1573106746#
    alter table VocEye alter column id set default nextval('VocEye_sequence');
insert into VocEye (code,name) values('OD','правый глаз');
insert into VocEye (code,name) values('OS','левый глаз');
insert into VocEye (code,name) values('OU','оба глаза');

update mislpu set isophthalmic=true where id=197;