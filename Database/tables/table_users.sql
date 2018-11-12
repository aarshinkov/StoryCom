-- Create table
create table USERS
(
  user_id  number not null,
  username nvarchar2(50) not null,
  name     nvarchar2(100) not null,
  password nvarchar2(100) not null,
  email    nvarchar2(200) not null
)
;
-- Create/Recreate primary, unique and foreign key constraints 
alter table USERS
  add constraint u_user_id_primary primary key (USER_ID);
alter table USERS
  add constraint u_username_unique unique (USERNAME);
