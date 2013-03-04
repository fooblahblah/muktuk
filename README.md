## Muktuk - URL shortener

### Overview

Muktuk is simple URL shortener.  It is implemented using the Play Framework (2.1.0). It currently supports uris with the http, https and ftp protocols.

### Installation

1. Clone the repository:

    git clone git://github.com/fooblahblah/muktuk.git

1. By default the MySQL driver is enabled. The H2 database can be used by un-commenting the appropriate lines in conf/application.conf, but you will lose all data between runs and have to run evolutions on each restart.

    mysqladmin -u root create muktuk

1. Start the server

    sbt start

1. Point your browser to: <http://localhost:9000>
