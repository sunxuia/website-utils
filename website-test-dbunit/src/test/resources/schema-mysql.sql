drop table if exists test_table;
create table test_table (
  id bigint primary key auto_increment,
  text varchar(255),
  is_active tinyint
);
drop table if exists test_table2;
create table test_table2 (
  id bigint primary key
);