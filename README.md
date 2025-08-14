# Global Route Optimizer

A backend-focused Java application that calculates the most fuel-efficient route between two countries in a Risk-inspired world map.  
The system uses **Breadth-First Search (BFS)** to determine the optimal path, excluding the start and destination countries from the route.  
It then provides a detailed fuel consumption report, broken down by continent, and highlights the continent with the highest fuel usage.

---

## Features

- **Shortest Path Calculation**  
  Uses BFS to find the most efficient route between two given countries.

- **Detailed Fuel Reports**  
  - Total fuel used.  
  - Fuel usage per continent.  
  - Identification of the continent with the highest fuel consumption.

- **Map-Based Optimisation**  
  Routes are calculated on a structured map representing continents and their connections.

- **Backend-First Implementation**  
  Written entirely in Java, with a focus on clean code structure, efficient algorithms, and maintainability.

---

## Technical Overview

- **Language:** Java  
- **Algorithm:** Breadth-First Search (BFS)  
- **Data Handling:** Graph-based representation of countries and continents  
- **Testing:** Implemented with **JUnit** for robust functionality verification  
- **Build Tool:** Maven Wrapper for consistent project builds across environments

---

## Example Output

<img src="/img/RouteOptimizerFullSS.png">
