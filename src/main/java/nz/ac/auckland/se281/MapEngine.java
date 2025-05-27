package nz.ac.auckland.se281;

import static nz.ac.auckland.se281.MessageCli.INSERT_COUNTRY;
import static nz.ac.auckland.se281.MessageCli.INSERT_DESTINATION;
import static nz.ac.auckland.se281.MessageCli.INSERT_SOURCE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/** This class is the main entry point. */
public class MapEngine {

  private Graph graph = new Graph();
  public HashMap<String, Country> countryMap = new HashMap<>();

  public MapEngine() {
    // add other code here if you wan
    loadMap(); // keep this mehtod invocation
  }

  /** invoked one time only when constracting the MapEngine class. */
  private void loadMap() {

    List<String> countries = Utils.readCountries();
    List<String> adjacencies = Utils.readAdjacencies();

    // creates all countries from csv and adds to graph
    for (String countryTemplate : countries) {
      String[] parts = countryTemplate.split(",");
      Country newCountry = new Country(parts[0], parts[1], Integer.parseInt(parts[2]));
      countryMap.put(parts[0], newCountry);
      graph.addCountry(newCountry);
    }

    // adds all edges to graph
    for (String adjacencyTemplate : adjacencies) {
      String[] parts = adjacencyTemplate.split(",");
      Country startingCountry = countryMap.get(parts[0]);

      for (String part : parts) {

        if (!countryMap.get(part).equals(startingCountry)) {
          graph.addConnection(startingCountry, countryMap.get(part));
        }
      }
    }

    // we want to make a hashmap to put countries into continents potentially? is that relevant?
    // We will implement a graph class, with nodes and edges
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {

    String cleanedInput = validateCountryInput(INSERT_COUNTRY);

    Country countryFound = countryMap.get(cleanedInput);
    Set<Country> neighbours = graph.getNeighbours(countryFound);
    String[] names = neighbours.stream().map(Country::getName).toArray(String[]::new);

    MessageCli.COUNTRY_INFO.printMessage(
        countryFound.getName(),
        countryFound.getContinent(),
        String.valueOf(countryFound.getFuelCost()),
        Arrays.toString(names));
  }

  // asks for country input until correct input entered
  public String validateCountryInput(MessageCli promptMessage) {
    boolean valid = false;
    String cleanedInput = null;

    while (!valid) {
      promptMessage.printMessage();
      String input = Utils.scanner.nextLine();
      cleanedInput = Utils.capitalizeFirstLetterOfEachWord(input);
      try {
        valid = checkCountryMap(cleanedInput);
      } catch (CountryNotFoundException e) {
        MessageCli.INVALID_COUNTRY.printMessage(e.getMessage());
      }
    }
    return cleanedInput;
  }

  public boolean checkCountryMap(String input) throws CountryNotFoundException {
    if (countryMap.containsKey(input)) {
      return true;
    } else {
      throw new CountryNotFoundException(input);
    }
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {
    Country country1 = countryMap.get(validateCountryInput(INSERT_SOURCE));
    Country country2 = countryMap.get(validateCountryInput(INSERT_DESTINATION));

    if (country1.equals(country2)) {
      MessageCli.NO_CROSSBORDER_TRAVEL.printMessage();
      return;
    }

    // calls bfs to find shortest route
    List<Country> route = findShortestPath(country1, country2);
    int totalFuel = 0;

    // creates a hash map of continent to fuel used, excluding start and end but adding as 0 entries
    // as we do visit them
    Map<String, Integer> continentCount = new LinkedHashMap<>();
    for (int i = 0; i < route.size(); i++) {
      Country currentCountry = route.get(i);

      if (i != 0 && i != route.size() - 1) {
        totalFuel += currentCountry.getFuelCost();
        continentCount.merge(
            currentCountry.getContinent(), currentCountry.getFuelCost(), Integer::sum);
      } else if (!continentCount.containsKey(currentCountry.getContinent())) {
        continentCount.put(currentCountry.getContinent(), 0);
      }
    }

    Set<String> visitedContinents = new HashSet<>(continentCount.keySet());
    visitedContinents.add(route.get(0).getContinent());
    visitedContinents.add(route.get(route.size() - 1).getContinent());

    String[] pathStrings = route.stream().map(Country::getName).toArray(String[]::new);
    List<String> formattedContinentCounts = new ArrayList<>();
    for (Map.Entry<String, Integer> entry : continentCount.entrySet()) {
      formattedContinentCounts.add(entry.getKey() + " (" + entry.getValue() + ")");
    }

    // Finding the continent where we spend the most fuel
    int highestFuel = 0;
    String highestFuelKey = null;

    for (String key : continentCount.keySet()) {
      if (continentCount.get(key) > highestFuel) {
        highestFuel = continentCount.get(key);
        highestFuelKey = key;
      }
    }
    String formattedHighestFuel = highestFuelKey + " (" + highestFuel + ")";

    MessageCli.ROUTE_INFO.printMessage(Arrays.toString(pathStrings));
    MessageCli.FUEL_INFO.printMessage(String.valueOf(totalFuel));
    MessageCli.CONTINENT_INFO.printMessage(formattedContinentCounts.toString());
    MessageCli.FUEL_CONTINENT_INFO.printMessage(formattedHighestFuel);
  }

  public List<Country> findShortestPath(Country start, Country end) {

    Set<Country> visited = new HashSet<>();

    Queue<Country> queue = new LinkedList<>();

    Map<Country, Country> parentMap = new HashMap<>();

    visited.add(start);
    queue.add(start);

    while (!queue.isEmpty()) {
      Country current = queue.poll();

      for (Country neighbour : graph.getNeighbours(current)) {
        if (!visited.contains(neighbour)) {
          visited.add(neighbour);
          parentMap.put(neighbour, current);
          queue.add(neighbour);

          if (neighbour.equals(end)) {
            // we have found the path, now we need to work back and return it
            List<Country> path = new LinkedList<>();
            Country step = end;
            while (step != null) {
              path.add(0, step);
              step = parentMap.get(step);
            }
            return path;
          }
        }
      }
    }

    // no path found
    return new ArrayList<>();
  }
}
