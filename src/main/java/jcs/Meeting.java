package jcs;

import java.io.Serializable;

public class Meeting implements Serializable {
    private String location; //место (ссылки - сюда же)
    private String summary; //заголовок
    private String description; //доп.инфо

    public Meeting() {}

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSummary() {
        return summary;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
