package graph;

public class Vertex {
    // atrributes
    private String name;
    private String type; // STATE, CITY, ATTRACTION, USER
    
    // Constructor
    public Vertex(String name, String type) {
        this.name = name;
        this.type = type;
    }
    
    // Accessors
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
    
    // Mutators
    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }
}

