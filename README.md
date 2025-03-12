
# Application
This application provides a list of places and their business hours. There are two endpoints:

1. getAllPlaces – Retrieves all places in the system.
        Typically, you’d fetch a list of places and pick an ID from that list.
2.  getPlaceById – Retrieves detailed information (including business hours) for a specific place.

## Api Contract
The api contract once you have run the application can be found
http://localhost:8080/swagger-ui/index.html#
http://localhost:8080/v3/api-docs - to generate the client if required

## Code Structure
The code follows an MVC pattern, as well as the Single Responsibility Principle, e.g. the GlobalExceptionHandler handles all exceptions in a centralized manner.

## Requirements
From the given requirements and data, our system stores the business’ opening hours. Main points:

1. We show when the business is open or closed.
2. We can aggregate days that share the same hours.
3. Day indexes (1–7) map to Monday–Sunday.

## Implementation
Version control in the URL (e.g. /api/v1/places) allows us to migrate to newer API versions seamlessly if needed.

All API responses are wrapped in a data field. This structure eases future features like pagination or links, since we 
can simply add more fields alongside data.

Our endpoint to retrieve the places by Id, it has an optional parameter 'includedCloseDays' in case we want to see the
days the business is closed. By default, the value is false which means only the days the business is open will be returned.

Aggregating consecutive days that share identical hours seems very client-specific. If the business changes this requirement, 
the backend code must be updated, which potentially could impact all consumers.

Numeric Days (1–7)
We return numeric days (1 = Monday, …, 7 = Sunday) in the API. This approach is both language-independent and efficient. 
Our DB also stores an int for each day, which can provide better performance than text fields.

Relationship & Database Setup
The default FetchType might be LAZY for a @OneToMany unless otherwise specified. 
We use an in-memory H2 database with Flyway scripts for migrations.
BIGSERIAL is used in PostgreSQL for auto-incrementing primary keys backed by an internal sequence, mapping to a BIGINT column.

I think this design meet with the requirements of retrieving specific business details as well as giving the possibility to adapt to new changes if required.


