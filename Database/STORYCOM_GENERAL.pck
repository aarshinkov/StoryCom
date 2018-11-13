CREATE OR REPLACE PACKAGE STORYCOM_GENERAL IS

  ---------- insert_user ----------
  PROCEDURE insert_user(ip_username IN NVARCHAR2,
                        ip_name     IN NVARCHAR2,
                        ip_password IN NVARCHAR2,
                        ip_email    IN NVARCHAR2);

  ---------- insert_story ----------
  PROCEDURE insert_story(ip_title   IN NVARCHAR2,
                         ip_content IN NVARCHAR2,
                         ip_userid  IN NUMBER);
END STORYCOM_GENERAL;
/
CREATE OR REPLACE PACKAGE BODY STORYCOM_GENERAL IS

  ---------- insert_user ----------
  PROCEDURE insert_user(ip_username IN NVARCHAR2,
                        ip_name     IN NVARCHAR2,
                        ip_password IN NVARCHAR2,
                        ip_email    IN NVARCHAR2) IS
  BEGIN
    INSERT INTO USERS
    VALUES
      (S_USERS.NEXTVAL, ip_username, ip_name, ip_password,
       ip_email);
  END;

  ---------- insert_story ----------  
  PROCEDURE insert_story(ip_title   IN NVARCHAR2,
                         ip_content IN NVARCHAR2,
                         ip_userid  IN NUMBER) IS
  BEGIN
    INSERT INTO STORIES
    VALUES
      (S_STORIES.NEXTVAL, ip_title, ip_content, TO_CHAR(SYSDATE, 'DD.MM.YYYY HH24:MI:SS'), ip_userid);
  END;
END STORYCOM_GENERAL;
/
