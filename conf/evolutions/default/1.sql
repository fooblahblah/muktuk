# --- Created by Slick DDL
# To stop Slick DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table `uris` (`id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,`uri` VARCHAR(254) NOT NULL);
create unique index `idx_uri` on `uris` (`uri`);

# --- !Downs

drop table `uris`;

