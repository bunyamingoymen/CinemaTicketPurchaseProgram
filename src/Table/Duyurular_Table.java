
package Table;

public class Duyurular_Table {
    /*
    Hem kampanyalar hem de haberler için tabloya veri yazmak adına bu sınıf'ı oluşturduk.
    Bu sınıf üzerinden arraylist yaparak gerekli verileri tabloya yazıyoruz.
    Burada sadece gerekli değişkenler tanımlı, constroctor ve get-set metodları yazılı
    */
    
    private String id;
    private String Date;
    private String Title;
    private String Duyuru;

    public Duyurular_Table(String id, String Date, String Title, String Duyuru) {
        this.id = id;
        this.Date = Date;
        this.Title = Title;
        this.Duyuru = Duyuru;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getDuyuru() {
        return Duyuru;
    }

    public void setDuyuru(String Duyuru) {
        this.Duyuru = Duyuru;
    }
    
    
    
}
