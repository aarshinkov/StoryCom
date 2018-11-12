-- Create table
create table USER_ROLES
(
  user_roles_id number not null,
  user_id       number not null,
  rolename      nvarchar2(50) not null
)
;
-- Create/Recreate primary, unique and foreign key constraints 
alter table USER_ROLES
  add constraint UR_USER_ROLES_PRIMARY primary key (USER_ROLES_ID);
alter table USER_ROLES
  add constraint UR_USER_ID_FOREIGN foreign key (USER_ID)
  references users (USER_ID) on delete cascade;
alter table USER_ROLES
  add constraint UR_ROLENAME_FOREIGN foreign key (ROLENAME)
  references roles (ROLENAME) on delete set null;
alter table USER_ROLES
  add constraint UR_USER_ROLE_UNIQUE unique (USER_ID, ROLENAME);
