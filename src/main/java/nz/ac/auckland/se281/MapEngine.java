package nz.ac.auckland.se281;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** This class is the main entry point. */
public class MapEngine {

  private Graph graph = new Graph();
  public Set<Country> countryList = new HashSet<>();

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
      countryList.add(newCountry);
    }

    // we want to make a hashmap to put countries into continents potentially? is that relevant?
    // We will implement a graph class, with nodes and edges
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {}

  /** this method is invoked when the user run the command route. */
  public void showRoute() {}
}
