# Cinema Cashier Simulator

A Java application that simulates a cinema cashier experience. The user can order snacks and drinks, purchase tickets for movies, and be assigned a seat in a specific screening room. The application aims to replicate the real-world flow of a cinema box office, providing an interactive experience.

## Group Members

| Name               | GitHub Username     |
|--------------------|---------------------|
| Chrisitan Bruger   | burgchr             |
| Lena Bressan       | lenabressan         |
| Momar Sambe        | momar161            |
| Alexander Temelin  | AlexanderTemelin    |

## Build and Run Instructions
### Prerequisites
- Ensure you have **Gradle** installed, or use the included Gradle Wrapper (`./gradlew`).
- Ensure you have **JavaFX SDK** downloaded and extracted on your system.

### Running the Project with Gradle

1. Open a terminal or command prompt.
2. Navigate to the **project root directory** (where `gradlew` is located).
3. Run the following command to build and run the project:
   ```bash
   ./gradlew run
4. The project will compile and launch automatically.

### Running the Project with the JAR File
If you want to run the built .jar file manually, follow these steps:
1. Make sure you have built the project JAR file (usually located in build/libs/).
2. Locate your JavaFX SDK lib folder path. Example:
   C:\Users\chri\Downloads\openjfx-21.0.7_windows-x64_bin-sdk\javafx-sdk-21.0.7\lib
3. Run the .jar with Java command and specify the JavaFX modules:

Example: java --module-path "C:\Users\chri\Downloads\openjfx-21.0.7_windows-x64_bin-sdk\javafx-sdk-21.0.7\lib" --add-modules javafx.controls,javafx.fxml -jar .\build\libs\Cinema-1.0-SNAPSHOT-all.jar
Note:
- You must be inside the project directory to use ./gradlew run.
- When running the .jar manually, make sure to correctly set the --module-path to your JavaFX SDK lib directory.

## Project Description

This application simulates the user interface of a cinema cashier. It allows customers to:

- Order food and drinks (e.g., popcorn, soda, chips),
- Purchase tickets for movies,
- Be assigned a seat in a room with numbered rows and seats,
- Receive a printed receipt.
- Receive tickets for specified movies.

The simulation includes UI elements for selecting products, a real-time display of the total cost, and logic for room/seat allocation.

## User Guide

## Implementation Overview

### Project Structure and Components

The application is organized into multiple components following a modular and MVC-based approach. Below is an overview of the main parts:

#### Controllers
These Java classes manage logic and interaction between the UI (FXML) and backend:

- `MainController.java`: Central navigation logic; contains methods like `viewFood`, `viewIcecream`, `viewDrinks`, and `viewCombos`.
- `CheckoutController.java`: Manages the checkout process and receipt finalization.
- `OrderController.java`, `FoodController.java`, `IceCreamController.java`, `DrinksController.java`, `CombosController.java`: Handle user interactions for different product categories.
- `TopBarController.java`: Likely handles UI actions for the top navigation bar.

#### Models & Utilities
These files represent the data and support logic of the application:

- `Product.java`, `OrderItem.java`: Define products and items in a customer’s order.
- `CardUtil.java`, `PdfUtil.java`: Utility classes for formatting payment and receipt-related features.

#### Testing
The project includes tests with JUnit:

- `CardUtilTest.java`, `OrderItemTest.java`, `PdfUtilTest.java`, `TicketItemTest.java`

#### FXML for UI Layouts
These files define the graphical layout using JavaFX:

- `main.fxml`, `checkout.fxml`, `order.fxml`, `food.fxml`, `ice_cream.fxml`, `drinks.fxml`, `combos.fxml`, `ticket.fxml`, `cards.xml`

Each FXML file corresponds to a screen or component of the user interface (e.g., snack selector, ticketing view).

### Interfaces Between Components

- Each `.fxml` layout is linked to a controller class that handles UI events and user inputs.
- The main controller (`MainController`) acts as a navigation hub to switch between different views and their respective controllers.
- Utility classes like `CardUtil` and `PdfUtil` are used across multiple controllers to handle recurring logic for example payment validation, receipt generation.

### Technologies & Techniques

- **JavaFX** for GUI development
- **JUnit** for unit testing
- **Object-Oriented Programming**
- **MVC** architecture
- **FXML** for UI separation from logic
- **Utility classes** for modular code reuse

## Human Experience

### Workload Distribution

### Git Usage

We used GitHub for:

- Version control
- Pull requests for code review
- Easy data sharing

### Challenges Faced
