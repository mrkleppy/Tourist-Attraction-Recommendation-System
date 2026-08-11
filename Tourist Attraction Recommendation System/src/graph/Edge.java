package graph;

public class Edge {
    private String from;
    private String fromType;
    private String to;
    private String toType;
    private int weight;

    public Edge(String from, String fromType, String to, String toType, int weight) {
        this.from = from;
        this.fromType = fromType;
        this.to = to;
        this.toType = toType;
        this.weight = weight;
    }

    public String getFrom() {
        return from;
    }

    public String getFromType() {
        return fromType;
    }

    public String getTo() {
        return to;
    }

    public String getToType() {
        return toType;
    }

    public int getWeight() {
        return weight;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setFromType(String fromType) {
        this.fromType = fromType;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setToType(String toType) {
        this.toType = toType;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
