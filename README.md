CobasePRO
=========

[![Build Status](https://travis-ci.org/Cobase/cobase-pro.svg?branch=master)](https://travis-ci.org/Cobase/cobase-pro) [![Codacy Badge](https://www.codacy.com/project/badge/b13868b3f52d429c98aacf5a556a5310)](https://www.codacy.com/app/cobase/cobase-pro)

[![Join the chat at https://gitter.im/Cobase/cobase-pro](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/Cobase/cobase-pro?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

This project is an open source social media portal for corporations and small companies and alternative to Yammer.
It's purpose is to enable people to share news and info about various topics in a non-intrusive way.  Logged in user
has a dashboard which contains posts from groups only he/she has been subscribed to. This way, user will only be informed
by the topics related to interest.

Developers are very welcome to join the project, the more the merrier. If you see something that needs improvement or refactoring, please
do not hesitate to make the code base better by sending us pull requests.

## Issues and Wiki

- [Issues](https://github.com/Cobase/cobase-pro/issues) (un-assigned are free to work on)
- [Wiki](https://github.com/Cobase/cobase-pro/wiki)

## Working demo

You are welcome to try out CobasePRO in action by going to [demo site](http://cobasepro.arturgajewski.com). You are required to register an account in order to log in, but you can enter some dummy email address. No verification emails will be sent of any kind. If you wish to find out more about this project, head to CobasePRO's [info site](http://cobasepro.com).

## IRC

Feel free to join the conversation on channel #cobase-pro @ freenode.net

## Requirements

- JDK 6 or later
- Activator (https://typesafe.com/activator)
- PostgreSQL / MySQL
- NPM & Webpack


## Installation

1. Clone this repository
2. Open PostgreSQL console as postgres user: `psql -U postgres`
3. In PostgreSQL console, create database: `create database cobase_pro`
4. Copy `server/conf/application.conf.dist` to `server/conf/application.conf` (and configure!)
5. Copy `server/conf/twitter.conf.dist` to `server/conf/twitter.conf` (and configure!)
6. Add database settings to `server/conf/application.conf` according to database created
7. Install npm components: `cd client && npm install`
8. Start the front server: `npm run dev`
9. Start backend server application with `cd ../server && activator run`
10. Open browser (http://locahost:9000)


## Database migrations

Migrations are handled with Evolutions plugin that comes with Cobase. When you start the application it will check the state of the current database and ask you to run migrations by clicking on a button if any changes need to be done.


## Testing

Run tests by entering:

    $ activator test


## Components used

- Backend: [Play for Scala](https://www.playframework.com)
- Fronted: [React](https://facebook.github.io/react), Flexbox
- Database: PostgreSQL
- Database abstraction: [Slick](http://slick.typesafe.com)
- Twitter: [Twitter4J](http://www.twitter4j.org)

## License

The code is licensed under [MIT License](http://opensource.org/licenses/MIT).
