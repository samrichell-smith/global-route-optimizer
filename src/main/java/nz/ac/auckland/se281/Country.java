package nz.ac.auckland.se281;

public class Country {
  private String name;
  private String continent;
  private int fuelCost;

  public Country(String name, String continent, int fuelCost) {
    this.name = name;
    this.continent = continent;
    this.fuelCost = fuelCost;
  }

  public String getName() {
    return this.name;
  }

  public String getContinent() {
    return this.continent;
  }

  public int getFuelCost() {
    return this.fuelCost;
  }

  @Override
  public boolean equals(Object obj) {
    // overrides equals for correctly comparing to other countries
    if (this == obj) {
      return true;
    }
    if (obj == null || obj.getClass() != Country.class) {
      return false;
    }
    Country other = (Country) obj;
    return name.equals(other.name)
        && continent.equals(other.continent)
        && fuelCost == other.fuelCost;
  }

  @Override
  public int hashCode() {
    return name.toLowerCase().hashCode();
  }
}
