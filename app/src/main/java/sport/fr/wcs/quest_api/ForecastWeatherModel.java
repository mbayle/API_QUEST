
package sport.fr.wcs.quest_api;


import com.google.api.client.util.Key;

public class ForecastWeatherModel {

    @Key
    private String cod;
    @Key
    private Float message;
    @Key
    private Integer cnt;
    @Key
    private java.util.List<sport.fr.wcs.quest_api.List> list = null;
    @Key
    private City city;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ForecastWeatherModel() {
    }

    /**
     * 
     * @param message
     * @param cnt
     * @param cod
     * @param list
     * @param city
     */
    public ForecastWeatherModel(String cod, Float message, Integer cnt, java.util.List<sport.fr.wcs.quest_api.List> list, City city) {
        super();
        this.cod = cod;
        this.message = message;
        this.cnt = cnt;
        this.list = list;
        this.city = city;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public Float getMessage() {
        return message;
    }

    public void setMessage(Float message) {
        this.message = message;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public java.util.List<sport.fr.wcs.quest_api.List> getList() {
        return list;
    }

    public void setList(java.util.List<sport.fr.wcs.quest_api.List> list) {
        this.list = list;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

}
