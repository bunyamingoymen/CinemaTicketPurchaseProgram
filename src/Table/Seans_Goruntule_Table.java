
package Table;

public class Seans_Goruntule_Table {

    /*
    Var olan seansları görüntülemek için bu sınıf oluşturuldu.
    Bu sınıf üzerinden arraylist yaparak gerekli verileri tabloya yazıyoruz.
    Burada sadece gerekli değişkenler tanımlı, constroctor ve get-set metodları yazılı.
     */
    private String id;
    private String salon;
    private String seans;
    private String film;

    public Seans_Goruntule_Table() {
    }

    public Seans_Goruntule_Table(String id, String salon, String seans, String film) {
        this.id = id;
        this.salon = salon;
        this.seans = seans;
        this.film = film;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSalon() {
        return salon;
    }

    public void setSalon(String salon) {
        this.salon = salon;
    }

    public String getSeans() {
        return seans;
    }

    public void setSeans(String seans) {
        this.seans = seans;
    }

    public String getFilm() {
        return film;
    }

    public void setFilm(String film) {
        this.film = film;
    }

}
