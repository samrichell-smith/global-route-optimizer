package nz.ac.auckland.se281;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class Graph {
  private Map<Country, Set<Country>> adjacencyList;

  public Graph() {
    adjacencyList = new HashMap<>();
  }

  public void addCountry(Country country) {
    adjacencyList.putIfAbsent(country, new LinkedHashSet<>()); // satisfies HashSet/LinkedHashSet
  }

  public void addConnection(Country from, Country to) {
    if (!from.equals(to)) {
      adjacencyList.get(from).add(to);
      adjacencyList.get(to).add(from);
    }
  }

  public Set<Country> getNeighbours(Country country) {
    return adjacencyList.getOrDefault(country, new HashSet<>());
  }

  public Set<Country> getAllCountries() {
    return adjacencyList.keySet();
  }
}
