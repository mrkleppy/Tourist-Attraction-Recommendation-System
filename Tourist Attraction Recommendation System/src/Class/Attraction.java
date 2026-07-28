package Class;

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
}
