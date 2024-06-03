
Fruit Delivery API

Description:

Fruit Delivery API is a RESTful API designed for managing fruit deliveries. The API allows you to create, retrieve, update and delete deliveries, as well as retrieve delivery reports.

Functionality:

- Deliveries:
    - Creating a new delivery.
    - Receiving a list of deliveries for a given period.
    - Retrieving a report of deliveries for a given period.
- Fruit:
    - Retrieving a fruit by ID.
    - Updating the supplier for a fruit.
    - Deleting a fruit.
- Fruit prices:
    - Creating a new price for a fruit.
    - Getting the price for a fruit by ID.
    - Retrieving a list of prices for a fruit with a given ID.
- Suppliers:
    - Retrieving a list of all suppliers.
    - Getting a supplier by ID.
    - Creating a new supplier.
    - Updating supplier data.
    - Deleting a supplier.

Technologies:

- Spring Boot
- Spring Data JPA
- H2 Database (for testing)
- Swagger (for API documentation)

Installation:

1. Clone the repository.
2. Run mvn clean install to build the project.
3. Run the application using mvn spring-boot:run.

Usage:

The API is available at http://localhost:8080/api.

Testing:

The project has a set of unit tests that cover the core functionality of the API.

Example request:

Create a new delivery:


POST /api/deliveries
Content-Type: application/json
``
{
  "supplierId": 1,
  "deliveryDate": "2024-06-02",
  { "items": [
    {
      "fruitId": 1,
      "quantity": 10
    }
  ]
}
``

Retrieving the deliveries report:


GET /api/deliveries/report?startDate=2024-06-01&endDate=2024-06-30


Example response:
``
{
  { "items": [
    {
      { "fruit": {
        "id": 1,
        "type": "apple",
        "variety": "Granny Smith",
        "quantity": 100,
        "weight": 1.0,
        "cost": 10.0
      },
      "quantity": 10,
      "pricePerUnit": 10.0,
      "weightPerUnit": 1.0,
      "totalCost": 100.0,
      "totalWeight": 10.0
    }
  ]
}
``
