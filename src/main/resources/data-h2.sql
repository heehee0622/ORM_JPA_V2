insert into MEMBER (city, street, zipcode, name, member_id) values ('울산', '1', '1111', 'userA', 9);
insert into MEMBER (city, street, zipcode, name, member_id) values ('포항', '1', '1111', 'userA', 10);
/**
만약 위의 sql이 실행이 되지 않는다면 spring.jpa.hibernate.ddl-auto=none 을 하고 사용 해라.

스프링 부트 2를 사용하고 있다면, 데이터베이스 초기화는 임베디드 데이터베이스 (H2, HSQLDB, ...)에서만 작동합니다. 다른 데이터베이스에서도이 속성을 사용하려면 spring.datasource.initialization-mode 속성을 변경해야합니다.
spring.datasource.initialization-mode=always
 */