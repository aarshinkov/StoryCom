-- Create table
create table SYS_PARAMS
(
  name  varchar2(300) not null,
  value varchar2(4000) not null,
  descr varchar2(1000)
)
;
-- Add comments to the columns 
comment on column SYS_PARAMS.name
  is 'Parameter name';
comment on column SYS_PARAMS.value
  is 'Parameter value';
comment on column SYS_PARAMS.descr
  is 'Description';
-- Create/Recreate primary, unique and foreign key constraints 
alter table SYS_PARAMS
  add constraint PK_SYSPARAM primary key (NAME);
