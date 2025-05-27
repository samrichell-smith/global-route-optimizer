package nz.ac.auckland.se281;

import java.util.HashMap;
import java.util.List;

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

    for (String countryTemplate : countries) {
      String[] parts = countryTemplate.split(",");
      Country newCountry = new Country(parts[0], parts[1], Integer.parseInt(parts[2]));
      countryMap.put(parts[0], newCountry);
      graph.addCountry(newCountry);
    }

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
  public void showInfoCountry() {}

  /** this method is invoked when the user run the command route. */
  public void showRoute() {}
}
