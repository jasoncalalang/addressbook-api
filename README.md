# Address Book API

A Jakarta EE address book API built with OpenLiberty and Apache Derby database.

## Overview

This project provides a complete RESTful API for managing address book contacts. It's built using modern Jakarta EE standards and runs on the OpenLiberty application server with an embedded Apache Derby database.

## Technologies Used

- **Java 17** - Programming language
- **Jakarta EE 10** - Enterprise Java platform
- **OpenLiberty 24.0.0.3** - Application server
- **Apache Derby 10.15.2.0** - Embedded database
- **Maven** - Build tool and dependency management
- **JAX-RS** - REST API framework
- **JPA (EclipseLink)** - Object-relational mapping
- **JSON-B** - JSON binding

## Features

- Complete CRUD operations for address book contacts
- RESTful API design
- Automatic database schema generation
- Transaction management
- JSON-based data exchange
- Search functionality
- Input validation
- Error handling

## Database Schema

The application uses a single table `address_book` with the following fields:

- `id` (BIGINT, Primary Key, Auto-generated)
- `name` (VARCHAR(100), Required)
- `phone_number` (VARCHAR(20), Optional)
- `address` (VARCHAR(500), Optional)

## API Endpoints

### Base URL
All endpoints are available under: `http://localhost:9080/api/addressbook`

### Available Operations

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/addressbook` | Get all contacts |
| GET | `/api/addressbook/{id}` | Get contact by ID |
| POST | `/api/addressbook` | Create new contact |
| PUT | `/api/addressbook/{id}` | Update existing contact |
| DELETE | `/api/addressbook/{id}` | Delete contact |
| GET | `/api/addressbook/search?name={name}` | Search contacts by name |

### Data Format

```json
{
  "id": 1,
  "name": "John Doe",
  "phoneNumber": "+1-555-0123",
  "address": "123 Main St, Anytown, USA"
}
```

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

### Building the Application

```bash
# Clone the repository
git clone <repository-url>
cd addressbook-api

# Build the application
mvn clean package
```

### Running the Application

```bash
# Start the OpenLiberty server
mvn liberty:run
```

The application will be available at:
- **Web Interface**: http://localhost:9080/
- **API Base URL**: http://localhost:9080/api/addressbook

### Development Mode

For development with hot reload:

```bash
mvn liberty:dev
```

### Managing the Server

```bash
# Start server in background
mvn liberty:start

# Stop server
mvn liberty:stop

# Check server status
mvn liberty:status
```

## Example Usage

### Create a Contact
```bash
curl -X POST \
  http://localhost:9080/api/addressbook \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "phoneNumber": "+1-555-0123",
    "address": "123 Main St, Anytown, USA"
  }'
```

### Get All Contacts
```bash
curl http://localhost:9080/api/addressbook
```

### Get Contact by ID
```bash
curl http://localhost:9080/api/addressbook/1
```

### Update a Contact
```bash
curl -X PUT \
  http://localhost:9080/api/addressbook/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Smith",
    "phoneNumber": "+1-555-9999",
    "address": "789 Pine St, Updated City, USA"
  }'
```

### Delete a Contact
```bash
curl -X DELETE http://localhost:9080/api/addressbook/1
```

### Search Contacts
```bash
curl "http://localhost:9080/api/addressbook/search?name=John"
```

## Testing

Run the unit tests:

```bash
mvn test
```

## Project Structure

```
src/
├── main/
│   ├── java/com/ibm/test/addressbook/
│   │   ├── config/          # Application configuration
│   │   ├── entity/          # JPA entities
│   │   └── resource/        # JAX-RS resources
│   ├── liberty/config/      # OpenLiberty server configuration
│   ├── resources/           # Application resources
│   │   └── META-INF/        # JPA persistence configuration
│   └── webapp/              # Web application files
└── test/                    # Unit tests
```

## Configuration

### Database Configuration
The application uses Apache Derby in embedded mode. The database files are created automatically in:
`target/liberty/wlp/usr/servers/addressbook-server/databases/addressbook`

### Server Configuration
OpenLiberty server configuration is located in:
`src/main/liberty/config/server.xml`

### JPA Configuration
Persistence configuration is located in:
`src/main/resources/META-INF/persistence.xml`

## License

This project is part of a technical demonstration for Jakarta EE and OpenLiberty.