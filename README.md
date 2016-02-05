CobasePRO
=========

[![Build Status](https://travis-ci.org/Cobase/cobase-pro.svg?branch=master)](https://travis-ci.org/Cobase/cobase-pro) [![Codacy Badge](https://www.codacy.com/project/badge/b13868b3f52d429c98aacf5a556a5310)](https://www.codacy.com/app/cobase/cobase-pro)

[![Join the chat at https://gitter.im/Cobase/cobase-pro](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/Cobase/cobase-pro?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

This project is an open source social media portal for corporations and small companies and alternative to Yammer. 
It's purpose is to enable people to share news and info about various topics in a non-intrusive way.  Logged in user 
has a dashboard which contains posts from groups only he/she has been subscribed to. This way, user will only be informed 
by the topics related to interest.

Developers are very welcome to join the project, the more the merrier. If you see something that needs improvement or refactoring, please do not hesitate to make the code base better by sending us pull requests.

**NOTE:** This version is not maintained any longer. Current work is under way in (https://github.com/Cobase/cobase-pro/tree/restify) branch.

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
4. Copy conf/application.conf.dist to conf/application.conf (and configure!)
5. Copy conf/silhouette.conf.dist to conf/silhouette.conf (and configure!)
6. Copy conf/twitter.conf.dist to conf/twitter.conf (and configure!)
7. Add database settings to conf/application.conf according to database created
8. Install npm components: `npm install`
9. Transform React files: `webpack`
10. Start application with `activator run`
11. Open browser (http://locahost:9000)


## Database migrations

Migrations are handled with Evolutions plugin that comes with Cobase. When you start the application it will check the state of the current database and ask you to run migrations by clicking on a button if any changes need to be done.


## Testing

Run tests by entering:

    $ activator test


## Components used

- UI: [AdminLTE 2.0.0 bootstrap theme](http://almsaeedstudio.com)
- Auth: [Silhouette](https://github.com/mohiva/play-silhouette)
- Database abstraction: [Slick](http://slick.typesafe.com)
- Framework: [Play for Scala](https://www.playframework.com)
- Twitter: [Twitter4J](http://www.twitter4j.org)
- View: [React](https://facebook.github.io/react)

## License

The code is licensed under [MIT License](http://opensource.org/licenses/MIT).

Demo: http://bit.ly/1x9Whyl Code: http://bit.ly/1vWzASQ #scala #cobasepro #opensource
