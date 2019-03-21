1553151383#
    insert into jaspervidsluchpolyclinic(id,name) values(4,'Мобильная поликлиника');
alter table jaspervidsluchpolyclinic_voce2vidsluch add ismobile boolean;
UPDATE jaspervidsluchpolyclinic_voce2vidsluch set ismobile=true where vidsluchcode_id=20 or vidsluchcode_id=23;
UPDATE jaspervidsluchpolyclinic_voce2vidsluch set ismobile=false where vidsluchcode_id<>20 and vidsluchcode_id<>23;
insert into jaspervidsluchpolyclinic(id,name) values(5,'КДП');
insert into jaspervidsluchpolyclinic_voce2vidsluch(jaspervidsluchpolyclinic_id,vidsluchcode_id,ismobile) values(5,217,false);