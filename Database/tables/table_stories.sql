-- Create table
create table STORIES
(
  story_id   number not null,
  title      nvarchar2(150) not null,
  content    nvarchar2(2000) not null,
  created_on date not null,
  user_id    number not null
)
;
-- Add comments to the columns 
comment on column STORIES.title
  is 'Title of the story';
comment on column STORIES.content
  is 'Content of the story';
comment on column STORIES.created_on
  is 'Creation date and time';
comment on column STORIES.user_id
  is 'The author of the story';
-- Create/Recreate primary, unique and foreign key constraints 
alter table STORIES
  add constraint S_STORY_ID_PRIMARY primary key (STORY_ID);
alter table STORIES
  add constraint S_USER_ID_FOREIGN foreign key (USER_ID)
  references users (USER_ID) on delete set null;
