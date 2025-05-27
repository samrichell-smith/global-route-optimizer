package nz.ac.auckland.se281;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class Graph {
  private Map<Country, Set<Country>> adjacencyList;

  public Graph() {
    adjacencyList = new LinkedHashMap<>();
  }

  public void addCountry(Country country) {
    adjacencyList.putIfAbsent(country, new LinkedHashSet<>());
  }

  public void addConnection(Country from, Country to) {
    if (!from.equals(to)) {
      adjacencyList.get(from).add(to);
    }
  }

  public Set<Country> getNeighbours(Country country) {
    return adjacencyList.getOrDefault(country, new LinkedHashSet<>());
  }

  public Set<Country> getAllCountries() {
    return adjacencyList.keySet();
  }
}
