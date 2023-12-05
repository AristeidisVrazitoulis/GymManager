# GymManager
A web application that handles the customers' subscriptions of a gym

Spring Boot for the BackEnd
Spring MVC and Thymleaf for the FrontEnd

For databases we use postgres and hibernate

The database has three tables
- Customer
  - id
  - firstName
  - lastName
  - email
  - phone
  - planId
  - active
- Plan
  - id
  - title
  - duration(in days)
  - description
- Subscribes
  - id
  - customerId
  - planId
  - startDate
  - endDate

In Customer table we keep basic data of each customer like their names and emails.
Accordingly, we do for the Plan table. We use one-to-many relationship for that purpose,
each customer has the right subscribe to one plan, while a plan can have many subscribed customers

For each table we have a corresponding controller to handle the API requests.
**CustomerController** endpoints
*GET - /api/customers*
Fetches all customers and with their plans

*GET - /api/customers-plan*
Fetches all customers but with the data needed to display

*GET - /api/customers/{customerId}*
Fetches customer given the id.

*GET - /api/customers/plan*
Fetches all the customers (subscribed or expired) of the plan with id : planId

*POST - /api/customers*
Creates a customer and saves it to the database

*PUT - /api/customers*
Updates information about an existing customer to the database

*DELETE - /api/customers/{customerId}*
Deletes a customer given his id





