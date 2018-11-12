-- Create table
create table ROLES
(
  rolename     nvarchar2(50) not null,
  display_name nvarchar2(60) not null,
  descr  nvarchar2(300)
)
;
-- Create/Recreate primary, unique and foreign key constraints 
alter table ROLES
  add constraint r_rolename_primary primary key (ROLENAME);
