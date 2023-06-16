alter table users drop constraint check_positive;

alter table users add constraint check_positive check (age > 0);
update users set age=5 where id =100002;