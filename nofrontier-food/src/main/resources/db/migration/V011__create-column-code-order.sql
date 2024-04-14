alter table order_entity add code varchar(36) not null after id;
update order_entity set code = uuid();
alter table order_entity add constraint uk_order_code unique (code);