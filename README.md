# Hospital Appointment System Backend

This is a Spring Boot-based backend application for managing hospital appointments between doctors and patients.

## Features

- **User Authentication & Authorization**
  - Separate roles for doctors and patients
  - Secure endpoints with role-based access control

- **Appointment Management**
  - Create new appointments
  - Approve/Reject appointments by doctors
  - Add medical notes to appointments
  - Check doctor availability
  - View appointment history

## Tech Stack

- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA
- PostgreSQL
- Lombok

## API Endpoints

### Appointments

```
POST /api/appointments
- Create a new appointment (PATIENT role)

PUT /api/appointments/{appointmentId}/approve
- Approve an appointment (DOCTOR role)

PUT /api/appointments/{appointmentId}/reject
- Reject an appointment (DOCTOR role)

PUT /api/appointments/{appointmentId}/note
- Add medical notes to an appointment (DOCTOR role)

GET /api/appointments/patient
- Get all appointments for the authenticated patient (PATIENT role)

GET /api/appointments/doctor
- Get all appointments for the authenticated doctor (DOCTOR role)

GET /api/appointments/availability
- Check doctor's availability for a specific date (PATIENT role)
```

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven
- PostgreSQL
  

### Configuration

1. Clone the repository
```bash
git clone [repository-url]
```

2. Configure database connection in `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/hospital_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```

3. Build the project
```bash
mvn clean install
```

4. Run the application
```bash
mvn spring-boot:run
```

