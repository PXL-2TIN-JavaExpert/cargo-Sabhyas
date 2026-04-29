# Shipments

## 1. H2 database

http://localhost:8080/h2-console

De URL vind je terug in de console bij het opstarten van de Spring Boot applicatie.
Default username is `sa` en het password is blanco (leeg).

## 2. API documentation

### Cargo aanmaken

#### Request

`POST http://localhost:8080/cargos`

    {
        "code": "CARGO_01",
        "weight": 500,
        "origin": "SEA_PORT_Z",
        "destination": "CITY_B"
    }

#### Response

    Bij succes: 201 CREATED
    Bij fout: 400 BAD REQUEST

### Cargo toevoegen aan een zending (shipment)

#### Request

`POST http://localhost:8080/shipments/<shipment_id>/cargo/<cargo_code>`

waarbij `<shipment_id>` de primaire sleutel van een zending en `<cargo_code>` de unieke code van een cargo.

#### Response

    Bij success: 200 OK
    On fout: 400 BAD REQUEST of 404 NOT FOUND

### Aankomst van een zending registreren

#### Request

`PUT http://localhost:8080/shipments/<shipmentId>/arrive`

waarbij `<shipment_id>` de primaire sleutel van een zending.

#### Response

    Bij success: 202 ACCEPTED
    Bij fout: 404 NOT FOUND of 400 BAD REQUEST

### Statistieken over cargo's (ladingen) opvragen

#### Request

`GET http://localhost:8080/cargos/statistics`


#### Response

    200 OK
    {
        "statusCount": {
            "MOVING": 1,
            "AT_TRANSIT_POINT": 1,
            "DELIVERED": 2
        },
        "heaviestCargo": "CARGO_03",
        "averageCargoWeight": 850,
        "countCargosAtWarehouseA": 1,
        "totalWeightDeliveredAtCityC": 853.5
    }


### Output voor het script valideer_endpoints.sh (src/main/resources)

Cargo CARGO_01 from SEA_PORT_Z to CITY_B Weight: 500

Cargo CARGO_01 from CITY_B to WAREHOUSE_A Weight: 800 (Expected: 400 BAD REQUEST - Duplicate cargo code)

`{"timestamp":"2025-01-24T08:12:03.365+00:00","status":400,"error":"Bad Request","message":"Duplicate cargo code","path":"/cargos"}`

Cargo CARGO_02 from CITY_B to WAREHOUSE_A Weight: 80 (Expected: 400 BAD REQUEST - Validation failed)

`{"timestamp":"2025-01-24T08:12:03.428+00:00","status":400,"error":"Bad Request","message":"Validation failed for object='createCargoRequest'. Error count: 1","path":"/cargos"}`

Cargo CARGO_03 from SEA_PORT_Z to CITY_B Weight: 1200

Add CARGO_01 to shipment 3  (400 BAD REQUEST: Cargo at wrong location)

`{"timestamp":"2025-01-24T08:12:03.527+00:00","status":400,"error":"Bad Request","message":"Cargo at wrong location.","path":"/shipments/3/cargo/CARGO_01"}`

Add CARGO_01 to shipment 1

Add CARGO_03 to shipment 1 (400 BAD REQUEST: Shipment capacity exceeded)

`{"timestamp":"2025-01-24T08:12:03.610+00:00","status":400,"error":"Bad Request","message":"Shipment capacity exceeded.","path":"/shipments/1/cargo/CARGO_03"}`

Add CARGO_03 to shipment 2

Shipment 1 arrived

Statistics

`{"statusCount":{"MOVING":1,"AT_TRANSIT_POINT":1},"heaviestCargo":"CARGO_03","averageCargoWeight":850.0,"countCargosAtWarehouseA":1,"totalWeightDeliveredAtCityB":0.0}`

Shipment 2 arrived

Statistics

`{"statusCount":{"AT_TRANSIT_POINT":2},"heaviestCargo":"CARGO_03","averageCargoWeight":850.0,"countCargosAtWarehouseA":2,"totalWeightDeliveredAtCityB":0.0}`

Add CARGO_01 en CARGO_03 to shipment 3

Shipment 3 arrived

Statistics

`{"statusCount":{"DELIVERED":2},"heaviestCargo":"CARGO_03","averageCargoWeight":850.0,"countCargosAtWarehouseA":0,"totalWeightDeliveredAtCityB":1700.0}`

Press button to close...
