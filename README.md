
## Overview
This project is built using Java and utilizes a PostgreSQL database. The compiled JAR file can be found in the **Releases** section.

## Setup Instructions

### Prerequisites
Before running the project, ensure you have the following installed:
- Java (JDK 11 or later)
- Maven
- PostgreSQL

### Database Configuration
Since this project uses a **local PostgreSQL database**, you need to configure your environment variables properly before running the application.

1. Create a `.env` file in the root directory.
2. Add the necessary database configurations:
   ```env
   DB_USERNAME=your_database_name
   DB_URL=jdbc:postgresql://localhost:3000/DB_name
   DB_PASSWORD=your_database_password
   ```
3. Ensure your PostgreSQL database is running locally with the provided credentials.

### Build and Run the Project

#### Step 1: Generate the Target File
To build the project and generate the `target` directory, run the following command:
```sh
mvn clean package
```


### Notes
- The JAR file is available in the **Releases** section of this repository.
- Make sure your PostgreSQL database is properly configured before running the project.



