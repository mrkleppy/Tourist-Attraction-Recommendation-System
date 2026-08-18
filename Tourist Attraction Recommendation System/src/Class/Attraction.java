package Class;

import java.util.Objects;

public class Attraction {
    private String name;
    private City city;

    public Attraction(){
        this.name = "";
        this.city = null;
    }

    public Attraction(String name, City city){
        this.name = name;
        this.city = city;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public City getCity(){
        return this.city;
    }

    public void setCity(City city){
        this.city = city;
    }
    
    @Override
    public String toString() {
        return getName() + ", " + getCity().getName() + ", " + getCity().getState();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        
        Attraction attraction = (Attraction)obj;
        return this.getName().equals(attraction.getName());
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
