# GymManager
A web application that handles the customers' subscriptions of a gym

Spring Boot for the BackEnd\
Spring MVC and Thymeleaf for the FrontEnd

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

For each table we have a corresponding controller to handle the API requests.\
**CustomerController** endpoints\
<br/>
*GET - /api/customers* <br/>
Fetches all customers and with their plans

*GET - /api/customers-plan*<br/>
Fetches all customers but with the data needed to display(DTO)

*GET - /api/customers/{customerId}*<br/>
Fetches customer given the id.

*GET - /api/customers/plan*<br/>
Fetches all the customers (subscribed or expired) of the plan with id : planId

*POST - /api/customers*<br/>
Creates a customer and saves it to the database

*PUT - /api/customers*<br/>
Updates information about an existing customer to the database

*DELETE - /api/customers/{customerId}*<br/>
Deletes a customer given his id and removes it from the database

**PlanController** endpoints\
<br/>
*GET - /api/plans* <br/>
Fetches all plans with the subscribed customers

*GET - /api/plans/{planId}* <br/>
Fetches plan given its id

*POST - /api/plans* <br/>
Creates a plan and saves it to the database

*PUT - /api/plans* <br/>
Updates an existing plan and saves it to the database

*DELETE - /api/plans* <br/>
Deletes a plan and given its id and removes it from the database

**SubscriptionController** endpoints\
<br/>
*GET - /api/plans-customer/{customerId}* <br/>
Fetches all subscriptions of the client with id: *customerId*

*POST - /api/subscribes* <br/>
Creates a subscription for a customers, it need three paramaters. Plan Title, customerId and the startDate

*DELETE - /api/subscribes/{subscriptionId}* <br/>
Deletes a subscription given its id and removes it from the database









