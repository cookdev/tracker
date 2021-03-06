INSERT INTO auth.authority(
            name)
    VALUES ('ROLE_ADMIN');
INSERT INTO auth.authority(
            name)
    VALUES ('ROLE_USER');
INSERT INTO auth.authority(
            name)
    VALUES ('ROLE');    
INSERT INTO auth.authority(
            name)
    VALUES ('ROLE_AUTH');        
INSERT INTO auth.authority(
            name)
    VALUES ('ROLE_AUTH_USER');    

INSERT INTO auth.user(
            id, created_by, created_date, last_modified_by, last_modified_date, 
            activated, activation_key, email, first_name, lang_key, last_name, 
            login, password)
    VALUES (1,'system','2015-04-23 09:52:01.788','system','2015-04-23 09:52:01.788'
    ,TRUE,null,'administrator@chs.com','Admin','en','Chs','admin'
    ,'$2a$10$1hbhpOFXv2XFrFI7eYogR.oSqeQDDC64ex4wztgO5uqxWNBPQ8o5K');
INSERT INTO auth.fsf_user(
            id, created_by, created_date, last_modified_by, last_modified_date, 
            activated, activation_key, email, first_name, lang_key, last_name, 
            login, password)
    VALUES (2,'system','2015-04-23 09:52:01.788','system','2015-04-23 09:52:01.788'
    ,TRUE,null,'user@chs.com','User','en','Chs','user'
    ,'$2a$10$mQPWEIa1NgvJ0FqxgmldKOzUsk3FdgQv6HwxajCTIbniZWcnYqzru');
    
INSERT INTO auth.user_authority(
            user_id, authority_name)
    VALUES (1, 'ROLE_ADMIN');
INSERT INTO auth.user_authority(
            user_id, authority_name)
    VALUES (1, 'ROLE_USER');
INSERT INTO auth.user_authority(
            user_id, authority_name)
    VALUES (1, 'ROLE');
INSERT INTO auth.user_authority(
            user_id, authority_name)
    VALUES (1, 'ROLE_AUTH_USER');
INSERT INTO auth.user_authority(
            user_id, authority_name)
    VALUES (1, 'ROLE_AUTH');          
INSERT INTO auth.user_authority(
            user_id, authority_name)
    VALUES (2, 'ROLE_USER');        
    
INSERT INTO auth.oauth_client_details(
            client_id, resource_ids, client_secret, scope, authorized_grant_types, 
            web_server_redirect_uri, authorities, access_token_validity, 
            refresh_token_validity, additional_information, autoapprove)
    VALUES ('restTest', null, 'restTestSecret', 'ROLE'
	  , 'authorization_code,implicit,password,client_credentials,refresh_token'
	  , null, 'ROLE_TEST_CLIENT', 1600, 16000, null, 'all');
	  
INSERT INTO auth.oauth_client_details(
            client_id, resource_ids, client_secret, scope, authorized_grant_types, 
            web_server_redirect_uri, authorities, access_token_validity, 
            refresh_token_validity, additional_information, autoapprove)
    VALUES ('auth', null, 'authSecret', 'ROLE'
	  , 'authorization_code,implicit,password,client_credentials,refresh_token'
	  , null, 'ROLE_AUTH_CLIENT', 1600, 16000, null, 'all');	  
    