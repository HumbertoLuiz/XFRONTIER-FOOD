/*

set foreign_key_checks = 0;

lock tables city write, kitchen write, state write, payment_method write, group_entity write, 
			group_permission write, permission write, product write, restaurant write, 
			restaurant_payment_method write, restaurant_user_responsible write, user_entity write, 
			user_group write, order_entity write, order_item write, product_image write, oauth2_registered_client write;

delete from city;
delete from kitchen;
delete from state;
delete from payment_method;
delete from group_entity;
delete from group_permission;
delete from permission;
delete from product;
delete from restaurant;
delete from restaurant_payment_method;
delete from restaurant_user_responsible;
delete from user_entity;
delete from user_group;
delete from order_entity;
delete from order_item;
delete from product_image;
delete from oauth2_registered_client;

set foreign_key_checks = 1;

alter table city auto_increment = 1;
alter table kitchen auto_increment = 1;
alter table state auto_increment = 1;
alter table payment_method auto_increment = 1;
alter table group_entity auto_increment = 1;
alter table permission auto_increment = 1;
alter table product auto_increment = 1;
alter table restaurant auto_increment = 1;
alter table user_entity auto_increment = 1;
alter table order_entity auto_increment = 1;
alter table order_item auto_increment = 1;

insert into kitchen (id, name) values (1, 'Tailandesa');
insert into kitchen (id, name) values (2, 'Indiana');
insert into kitchen (id, name) values (3, 'Argentina');
insert into kitchen (id, name) values (4, 'Brasileira');

insert into state (id, name) values (1, 'Minas Gerais');
insert into state (id, name) values (2, 'São Paulo');
insert into state (id, name) values (3, 'Ceará');

insert into city (id, name, state_id) values (1, 'Uberlândia', 1);
insert into city (id, name, state_id) values (2, 'Belo Horizonte', 1);
insert into city (id, name, state_id) values (3, 'São Paulo', 2);
insert into city (id, name, state_id) values (4, 'Campinas', 2);
insert into city (id, name, state_id) values (5, 'Fortaleza', 3);

insert into restaurant (id, name, shipping_rate, kitchen_id, register_date, update_date, active, open, city_id, zip_code, street, number, complement, neighborhood)
values (1, 'Thai Gourmet', 10, 1, utc_timestamp, utc_timestamp, true, true, 1, '999999-000', 'Rua Bragança', '223', 'posto shell', 'Lancaster');
insert into restaurant (id, name, shipping_rate, kitchen_id, register_date, update_date, active, open, city_id, zip_code, street, number, complement, neighborhood) 
values (2, 'Thai Delivery', 9.50, 1, utc_timestamp, utc_timestamp, true, true, 1, '999999-000', 'Rua Bragança', '223', 'posto shell', 'Lancaster');
insert into restaurant (id, name, shipping_rate, kitchen_id, register_date, update_date, active, open, city_id, zip_code, street, number, complement, neighborhood) 
values (3, 'Tuk Tuk Comida Indiana', 15, 2, utc_timestamp, utc_timestamp, true, true, 1, '999999-000', 'Rua Bragança', '223', 'posto shell', 'Lancaster');
insert into restaurant (id, name, shipping_rate, kitchen_id, register_date, update_date, active, open, city_id, zip_code, street, number, complement, neighborhood) 
values (4, 'Java Steakhouse', 12, 3, utc_timestamp, utc_timestamp, true, true, 1, '999999-000', 'Rua Bragança', '223', 'posto shell', 'Lancaster');
insert into restaurant (id, name, shipping_rate, kitchen_id, register_date, update_date, active, open, city_id, zip_code, street, number, complement, neighborhood) 
values (5, 'Lanchonete do Tio Sam', 11, 4, utc_timestamp, utc_timestamp, true, true, 1, '999999-000', 'Rua Bragança', '223', 'posto shell', 'Lancaster');
insert into restaurant (id, name, shipping_rate, kitchen_id, register_date, update_date, active, open, city_id, zip_code, street, number, complement, neighborhood)
values (6, 'Bar da Maria', 6, 4, utc_timestamp, utc_timestamp, true, true, 1, '999999-000', 'Rua Bragança', '223', 'posto shell', 'Lancaster');

insert into payment_method (id, description, update_date) values (1, 'Credit Card', utc_timestamp);
insert into payment_method (id, description, update_date) values (2, 'Debit Card', utc_timestamp);
insert into payment_method (id, description, update_date) values (3, 'Cash', utc_timestamp);

insert into permission (id, name, description) values (1, 'EDIT_KITCHEN', 'Allows you to edit kitchens');
insert into permission (id, name, description) values (2, 'EDIT_PAYMENT_METHODS', 'Allows you to create or edit payment methods');
insert into permission (id, name, description) values (3, 'EDIT_CITIES', 'Create or edit cities');
insert into permission (id, name, description) values (4, 'EDIT_STATES', 'Create or edit states');
insert into permission (id, name, description) values (5, 'CONSULT_USERS_GROUPS_PERMISSIONS', 'Allows users, groups and permissions to be consulted');
insert into permission (id, name, description) values (6, 'EDIT_USER_GROUPS_PERMISSIONS', 'Create or edit users, groups and permissions');
insert into permission (id, name, description) values (7, 'EDIT_RESTAURANTS', 'Create, edit or manage restaurants');
insert into permission (id, name, description) values (8, 'CONSULT_ORDERS', 'Consult orders');
insert into permission (id, name, description) values (9, 'MANAGE_ORDERS', 'Manage orders');
insert into permission (id, name, description) values (10, 'GENERATE_REPORTS', 'Generates reports');

insert into restaurant_payment_method (restaurant_id, payment_method_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3), (4, 1), (4, 2), (5, 1), (5, 2), (6, 3);

insert into product (name, description, price, active, restaurant_id) values ('Porco com molho agridoce', 'Deliciosa carne suína ao molho especial', 78.90, 0, 1);
insert into product (name, description, price, active, restaurant_id) values ('Camarão tailandês', '16 camarões grandes ao molho picante', 110, 1, 1);
insert into product (name, description, price, active, restaurant_id) values ('Salada picante com carne grelhada', 'Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha', 87.20, 1, 2);
insert into product (name, description, price, active, restaurant_id) values ('Garlic Naan', 'Pão tradicional indiano com cobertura de alho', 21, 1, 3);
insert into product (name, description, price, active, restaurant_id) values ('Murg Curry', 'Cubos de frango preparados com molho curry e especiarias', 43, 1, 3);
insert into product (name, description, price, active, restaurant_id) values ('Bife Ancho', 'Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do contrafilé', 79, 1, 4);
insert into product (name, description, price, active, restaurant_id) values ('T-Bone', 'Corte muito saboroso, com um osso em formato de T, sendo de um lado o contrafilé e do outro o filé mignon', 89, 1, 4);
insert into product (name, description, price, active, restaurant_id) values ('Sanduíche X-Tudo', 'Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese', 19, 1, 5);
insert into product (name, description, price, active, restaurant_id) values ('Espetinho de Cupim', 'Acompanha farinha, mandioca e vinagrete', 8, 1, 6);

insert into group_entity (id, name) values (1, 'Manager'), (2, 'Seller'), (3, 'Secretary'), (4, 'Register');

# Adiciona todas as permissoes no group do gerente
insert into group_permission (group_id, permission_id)
select 1, id from permission;

# Adiciona permissoes no group do vendedor
insert into group_permission (group_id, permission_id)
select 2, id from permission where name like 'CONSULT_%';

insert into group_permission (group_id, permission_id)
select 2, id from permission where name = 'EDIT_RESTAURANTS';

# Adiciona permissoes no group do auxiliar
insert into group_permission (group_id, permission_id)
select 3, id from permission where name like 'CONSULT_%';

# Adiciona permissoes no group cadastrador
insert into group_permission (group_id, permission_id)
select 4, id from permission where name like '%_RESTAURANTS';
 
insert into user_entity (id, name, email, password, register_date) values
(1, 'João da Silva', 'joao.ger@nofrontierfood.com.br', '$2y$12$NSsM4gEOR7MKogflKR7GMeYugkttjNhAJMvFdHrBLaLp2HzlggP5W', utc_timestamp),
(2, 'Maria Joaquina', 'maria.vnd@nofrontierfood.com.br', '$2y$12$NSsM4gEOR7MKogflKR7GMeYugkttjNhAJMvFdHrBLaLp2HzlggP5W', utc_timestamp),
(3, 'José Souza', 'jose.aux@nofrontierfood.com.br', '$2y$12$NSsM4gEOR7MKogflKR7GMeYugkttjNhAJMvFdHrBLaLp2HzlggP5W', utc_timestamp),
(4, 'Sebastião Martins', 'sebastiao.cad@nofrontierfood.com.br', '$2y$12$NSsM4gEOR7MKogflKR7GMeYugkttjNhAJMvFdHrBLaLp2HzlggP5W', utc_timestamp),
(5, 'Manoel Lima', 'manoel.loja@gmail.com', '$2y$12$NSsM4gEOR7MKogflKR7GMeYugkttjNhAJMvFdHrBLaLp2HzlggP5W', utc_timestamp),
(6, 'Débora Mendonça', 'email.teste.aw+debora@gmail.com', '$2y$12$NSsM4gEOR7MKogflKR7GMeYugkttjNhAJMvFdHrBLaLp2HzlggP5W', utc_timestamp),
(7, 'Carlos Lima', 'email.teste.aw+carlos@gmail.com', '$2y$12$NSsM4gEOR7MKogflKR7GMeYugkttjNhAJMvFdHrBLaLp2HzlggP5W', utc_timestamp);

insert into user_group (user_id, group_id) values (1, 1), (1, 2), (2, 2), (3, 3), (4, 4);

insert into restaurant_user_responsible (restaurant_id, user_id) values (1, 5), (3, 5);

insert into order_entity (id, code, restaurant_id, user_customer_id, payment_method_id, city_id, zip_code, 
                    street, number, complement, neighborhood, status, creation_date, subtotal, shipping_rate, total_value)
values (1, 'f9981ca4-5a5e-4da3-af04-933861df3e55', 1, 6, 1, 1, '38400-000', 'Rua Floriano Peixoto', '500', 'Apto 801', 'Brasil',
        'CREATED', utc_timestamp, 298.90, 10, 308.90);

insert into order_item (id, order_id, product_id, quantity, unit_price, total_price, observation)
values (1, 1, 1, 1, 78.9, 78.9, null);

insert into order_item (id, order_id, product_id, quantity, unit_price, total_price, observation)
values (2, 1, 2, 2, 110, 220, 'Menos picante, por favor');

insert into order_entity (id, code, restaurant_id, user_customer_id, payment_method_id, city_id, zip_code, 
                    street, number, complement, neighborhood, status, creation_date, subtotal, shipping_rate, total_value)
values (2, 'd178b637-a785-4768-a3cb-aa1ce5a8cdab', 4, 6, 2, 1, '38400-111', 'Rua Acre', '300', 'Casa 2', 'Centro',
        'CREATED', utc_timestamp, 79, 0, 79);

insert into order_item (id, order_id, product_id, quantity, unit_price, total_price, observation)
values (3, 2, 6, 1, 79, 79, 'Ao ponto');

insert into order_entity (id, code, restaurant_id, user_customer_id, payment_method_id, city_id, 
					zip_code, street, number, complement, neighborhood, status, creation_date, 
                    confirmation_date, delivery_date, subtotal, shipping_rate, total_value)
values (3, 'b5741512-8fbc-47fa-9ac1-b530354fc0ff', 1, 7, 1, 1, '38400-222', 'Rua Natal', '200', null, 'Brasil',
        'DELIVERED', '2019-10-30 21:10:00', '2019-10-30 21:10:45', '2019-10-30 21:55:44', 110, 10, 120);

insert into order_item (id, order_id, product_id, quantity, unit_price, total_price, observation)
values (4, 3, 2, 1, 110, 110, null);

insert into order_entity (id, code, restaurant_id, user_customer_id, payment_method_id, city_id, 
					zip_code, street, number, complement, neighborhood, status, creation_date, 
                    confirmation_date, delivery_date, subtotal, shipping_rate, total_value)
values (4, '5c621c9a-ba61-4454-8631-8aabefe58dc2', 1, 7, 1, 1, '38400-800', 'Rua Fortaleza', '900', 'Apto 504', 'Centro',
        'DELIVERED', '2019-11-02 20:34:04', '2019-11-02 20:35:10', '2019-11-02 21:10:32', 174.4, 5, 179.4);

insert into order_item (id, order_id, product_id, quantity, unit_price, total_price, observation)
values (5, 4, 3, 2, 87.2, 174.4, null);

insert into order_entity (id, code, restaurant_id, user_customer_id, payment_method_id, city_id, 
					zip_code, street, number, complement, neighborhood, status, creation_date, 
                    confirmation_date, delivery_date, subtotal, shipping_rate, total_value)
values (5, '8d774bcf-b238-42f3-aef1-5fb388754d63', 1, 3, 2, 1, '38400-200', 'Rua 10', '930', 'Casa 20', 'Martins',
        'DELIVERED', '2019-11-03 02:00:30', '2019-11-03 02:01:21', '2019-11-03 02:20:10', 87.2, 10, 97.2);

insert into order_item (id, order_id, product_id, quantity, unit_price, total_price, observation)
values (6, 5, 3, 1, 87.2, 87.2, null);



INSERT INTO oauth2_registered_client
(id, client_id, client_id_issued_at, client_secret, client_secret_expires_at, client_name, client_authentication_methods, authorization_grant_types, redirect_uris, scopes, client_settings, token_settings)
VALUES('1', 'nofrontierfood-backend', '2022-11-29 18:58:12', '$2a$10$trk401po.Wx9JXXMs2xCFeB.eXU7qENFquETcr04a0hDJxGV3ge0.', NULL, 'NoFrontierFood Backend', 'client_secret_basic', 'client_credentials', '', 'READ', '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":false}', '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":true,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",1800.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",3600.000000000],"settings.token.authorization-code-time-to-live":["java.time.Duration",300.000000000]}');

INSERT INTO oauth2_registered_client
(id, client_id, client_id_issued_at, client_secret, client_secret_expires_at, client_name, client_authentication_methods, authorization_grant_types, redirect_uris, scopes, client_settings, token_settings)
VALUES('2', 'nofrontierfood-web', '2022-11-29 18:58:12', '$2a$10$/Lx1cVKanXiCkpYtdA369OZ78x8aHwx51RTxC.4pqEiuZRzQh0e/i', NULL, 'NoFrontierFood Web', 'client_secret_basic', 'refresh_token,authorization_code', 'http://127.0.0.1:8080/swagger-ui/oauth2-redirect.html,http://127.0.0.1:8080/authorized', 'READ,WRITE', '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":true}', '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":false,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",900.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",86400.000000000],"settings.token.authorization-code-time-to-live":["java.time.Duration",300.000000000]}');

INSERT INTO oauth2_registered_client
(id, client_id, client_id_issued_at, client_secret, client_secret_expires_at, client_name, client_authentication_methods, authorization_grant_types, redirect_uris, scopes, client_settings, token_settings)
VALUES('3', 'foodanalytics', '2022-11-29 18:58:12', '$2a$10$LQOU54Ta7zV7TxTXSk7DEeZUx/P9PwKGH5CTIOLNGWgIP29QHdq4K', NULL, 'Food Analytics', 'client_secret_basic', 'authorization_code', 'http://www.foodanalytics.local:8082', 'READ,WRITE', '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":false}', '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":true,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",1800.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",3600.000000000],"settings.token.authorization-code-time-to-live":["java.time.Duration",300.000000000]}');

unlock tables;

*/