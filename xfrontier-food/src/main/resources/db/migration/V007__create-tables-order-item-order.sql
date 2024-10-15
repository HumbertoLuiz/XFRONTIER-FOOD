create table order_entity (
  id bigint not null auto_increment,
  subtotal decimal(10,2) not null,
  shipping_rate decimal(10,2) not null,
  total_value decimal(10,2) not null,
 
  city_id bigint(20) not null,
  zip_code varchar(9) not null,
  street varchar(100) not null,
  number varchar(20) not null,
  complement varchar(60) null,
  neighborhood varchar(60) not null,  

  status varchar(10) not null,
  creation_date datetime not null,
  confirmation_date datetime null,
  cancellation_date datetime null,
  delivery_date datetime null,
  payment_method_id bigint not null, 
  restaurant_id bigint not null,
  user_customer_id bigint not null,

  primary key (id),
  
  constraint fk_order_city foreign key (city_id) references city (id),

  constraint fk_order_restaurant foreign key (restaurant_id) references restaurant (id),
  constraint fk_order_user_customer foreign key (user_customer_id) references user_entity (id),
  constraint fk_order_payment_method foreign key (payment_method_id) references payment_method (id)
) engine=InnoDB default charset=utf8;

create table order_item (
  id bigint not null auto_increment,
  quantity smallint(6) not null,
  unit_price decimal(10,2) not null,
  total_price decimal(10,2) not null,
  observation varchar(255) null,
  order_id bigint not null,
  product_id bigint not null,
  
  primary key (id),
  unique key uk_order_item_product (order_id, product_id),

  constraint fk_order_item_order foreign key (order_id) references order_entity (id),
  constraint fk_order_item_product foreign key (product_id) references product (id)
) engine=InnoDB default charset=utf8;