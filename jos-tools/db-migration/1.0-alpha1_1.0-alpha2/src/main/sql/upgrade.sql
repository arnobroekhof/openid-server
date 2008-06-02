/* openid_user -> jos_user */
insert into jos_user(user_id, user_username, user_creation_date)
select user_id, user_username, user_registertime
from openid_user;

/* openid_user -> jos_persona */
insert into jos_persona
(persona_id, persona_user_id, persona_name, persona_nickname,
persona_email, persona_fullname, persona_dob, persona_gender,
persona_postcode, persona_country, persona_language, persona_timezone,
persona_creation_date)
select user_id, user_id, 'Default Persona', user_nickname,
user_email, user_fullname, user_dob, user_gender,
user_postcode, user_country, user_language, user_timezone,
user_registertime
from openid_user;

/*
 * openid_credential -> jos_password
 * 
 * Fill the field `password_sha_hex' by Java later. 
 */
insert into jos_password
(password_id, password_user_id, password_plaintext, password_sha_hex, password_creation_date)
select c1.credential_id, c1.credential_userid, c1.credential_info, '', u1.user_registertime
from openid_credential c1 inner join openid_user u1 on u1.user_id = c1.credential_userid