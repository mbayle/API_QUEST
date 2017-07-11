
package sport.fr.wcs.quest_api;


import com.google.api.client.util.Key;

public class City {

    @Key
    private Integer id;
    @Key
    private String name;
    @Key
    private Coord coord;
    @Key
    private String country;

    /**
     * No args constructor for use in serialization
     * 
     */
    public City() {
    }

    /**
     * 
     * @param coord
     * @param id
     * @param name
     * @param country
     */
    public City(Integer id, String name, Coord coord, String country) {
        super();
        this.id = id;
        this.name = name;
        this.coord = coord;
        this.country = country;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}
