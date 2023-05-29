
# Link Tracker v1

A service for tracking updates of questions from StackOverflow and repositories on GitHub. Integrated with Telegram API for a simple use case.




## Workflow

The service allows registered users to subscribe to an issue on a Stack Overflow or repository on the GitHub. When a question/repository receives an update (an answer to a question, a commit or a new pull request in the repository), the user receives a notification with information about the update.
## Structure

The service consists of several modules.

### scrapper

This module contains the main logic of the service, including the logic of periodically checking for updates, subscriptions and deleting links, working with the database, sending requests and responses to other modules.

The module provides two types of sending messages to users: **WebFlux webclient** HTTP-requests or **RabbitMQ** messages.

The module provides two types of database interaction: **JDBC**, **JOOQ**, **JPA**.


### link-parser

This module contains logic of links parsing. Links parsing is implemented using the **chain of responsibility pattern**, which makes it easy to add new types of supported links.


### bot

This module contains logic interacting with Telegram Bot API. 5 commands available by default.
- **/start** - register user
- **/track** - start tracking a link
- **/untrack** - stop tracking a link
- **/list** - show a list of tracking links
- **/help** - show a list of commands
