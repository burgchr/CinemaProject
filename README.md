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
### Starting the Application
- When you launch the app, you will see the main window with the following layout:
  - **Left panel:** Category buttons (DRINKS, FOOD, COMBOS, ICE CREAM)
  - **Right panel:** Order list and payment details
  - **Top panel:** General buttons for the program as well as ticket button

## How to Use

### 1. Select a Category
- On the **left side**, choose a category by clicking one of the buttons:
  - DRINKS
  - FOOD
  - COMBOS
  - ICE CREAM

### 2. Add Items to the Order
- After selecting a category, the items available in that category will appear (not shown in the image, but expected).
- Click on an item to add it to the **order list** on the right side.

### 3. Adjust Quantity
- Use the buttons next to the order list to adjust the quantity:
  - `+` to increase the quantity
  - `-` to decrease the quantity
  - `C` to clear/reset the quantity or order

### 4. Review Payment Information
- The **top right section** shows:
  - Last receipt total
  - Amount paid
  - Change due to the customer

### 5. Confirm the Order
- After finalizing the items and quantities, click the **CONFIRM** button below the order list to complete the order.
- This will process the order. You can either by with cash or with the gift card. The test data of the giftcard is written under resources/cards.xml
- The checkout screen appears where the worker can insert how much money the customer paid and by clicking on **PRINT** you get the receipt which is saved in resources/receipts

### 6. Other Buttons
- **Exit:** Closes the application.
- **Cash closing:** Manages end-of-day cash totals with the help of the resources/receipts/payment.xml (Contains all the payments of the day).
- **Cash drawer:** Opens the cash drawer (if hardware supported).
- **Cards:** Manage gift card payments.
- **Tickets:** Manage ticket sales or printing.

### 7. Ticket sales and printing
- Opens the cinema hall with the current film and the seats. By clicking on the seats and then ***Emit ticket*** you get the tickets saved in resources/tickets

## Notes
- Always make sure to confirm orders after adding items.
- Use the payment section to input amounts and calculate change.
- The interface is designed to be fast and intuitive for cinema staff use.

## Implementation Overview

### Project Structure and Components

The application is organized into multiple components following a modular and MVC-based approach. Below is an overview of the main parts:

#### Controllers
These Java classes manage logic and interaction between the UI (FXML) and backend:

- `MainController.java`: Central navigation logic; contains methods like `viewFood`, `viewIcecream`, `viewDrinks`, and `viewCombos`.
- `CheckoutController.java`: Manages the checkout process and receipt finalization.
- `OrderController.java`: Manages the order pane (current selected products) and the last Receipt, paid and change texts
- `FoodController.java`, `IceCreamController.java`, `DrinksController.java`, `CombosController.java`: Handle user interactions for different product categories.
- `TopBarController.java`: Likely handles UI actions for the top navigation bar.

#### Models & Utilities
These files represent the data and support logic of the application:

- `Product.java`, `OrderItem.java`, `TicketItem.java`: Define products and items in a customer’s order.
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
