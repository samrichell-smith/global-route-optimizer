package nz.ac.auckland.se281;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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

        graph.addConnection(startingCountry, countryMap.get(part));
      }
    }

    // we want to make a hashmap to put countries into continents potentially? is that relevant?
    // We will implement a graph class, with nodes and edges
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {

    boolean valid = false;
    String cleanedInput = null;
    while (!valid) {
      MessageCli.INSERT_COUNTRY.printMessage();
      String input = Utils.scanner.nextLine();
      cleanedInput = Utils.capitalizeFirstLetterOfEachWord(input);
      try {
        valid = validateCountryInput(cleanedInput);
      } catch (CountryNotFoundException e) {
        MessageCli.INVALID_COUNTRY.printMessage(e.getMessage());
      }
    }

    Country countryFound = countryMap.get(cleanedInput);
    Set<Country> neighbours = graph.getNeighbours(countryFound);
    String[] names = neighbours.stream().map(Country::getName).toArray(String[]::new);

    MessageCli.COUNTRY_INFO.printMessage(
        countryFound.getName(),
        countryFound.getContinent(),
        String.valueOf(countryFound.getFuelCost()),
        Arrays.toString(names));
  }

  // checks
  public boolean validateCountryInput(String input) throws CountryNotFoundException {
    if (countryMap.containsKey(input)) {
      return true;
    } else {
      throw new CountryNotFoundException(input);
    }
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {}
}
