create user bd identified by bd
default tablespace users
temporary tablespace temp;
grant connect, resource to bd;
grant create view to bd;