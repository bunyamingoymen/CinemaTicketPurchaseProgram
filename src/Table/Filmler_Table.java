
package Table;

public class Filmler_Table {

    /*
    Hem Vizyondaki Filmler hem de Eski Filmler için tabloya veri yazmak adına bu sınıf'ı oluşturduk.
    Bu sınıf üzerinden arraylist yaparak gerekli verileri tabloya yazıyoruz.
    Burada sadece gerekli değişkenler tanımlı, constroctor ve get-set metodları yazılı.
     */
    private String id;
    private String Title;
    private String Date;

    public Filmler_Table(String id, String Date, String Title) {
        this.id = id;
        this.Title = Title;
        this.Date = Date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

}
