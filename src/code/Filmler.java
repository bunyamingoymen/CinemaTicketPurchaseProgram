package code;

/*

Bu metot Eski_Filmler ve vizyondaki_filmler sınıflarındaki ortak değerleri birleştirmek için yazıldı.
Filmler sınıfı interface olarak ayarlandı(Alttaki sınıf. List_Filmler değil)
Bu sınıfta diğer sınıflar gibi elle yazılmış bir bağlı liste içermektedir.

 */
//İlk olarak bağlı liste için değerleri tutacak olan sınıfı yazıyoruz.
class List_Filmler {

    //gerkeli olan değişkenleri tanımlıyoruz.
    private int id;
    private String Date;
    private String Title;
    private List_Filmler next;

    public List_Filmler() {
    }

    //kalıtım ile bağlanmış olan List_Eski_Filmler ile List_Vizyondaki_Filmler sınıfındaki değerleri tanımlamak adına bu yapılandırıcı metodu oluşturuyoruz.
    public List_Filmler(int id, String Date, String Title) {
        this.id = id;
        this.Date = Date;
        this.Title = Title;

    }

    //Bu sınıf kalıtım ile oluşan alt sınıflar için değil bu sınıfta oluşan bağlı lsite için yazılan yapılandırıcı metot
    public List_Filmler(int id, String Date, String Title, List_Filmler next) {
        this.id = id;
        this.Date = Date;
        this.Title = Title;
        this.next = null;
    }

    //Aşağıda ise gerekli get ve set metotlarını tanımlıyoruz.
    public String getTitle() {
        return Title;
    }

    public String getDate() {
        return Date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List_Filmler getNext() {
        return next;
    }

    public void setNext(List_Filmler next) {
        this.next = next;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

}

//burası ise bağlı liste için gerekli olan metotları tanımlıyoruz. İnterface sınıf olduğu için sadece başlıkları var.
public interface Filmler {

    //bağlı listeye ekleme metodu
    public void add(int id, String Title, String Date);

    //bağlı listedeki bütün değerleri silme metodu
    public void remove();

    //bağlı listedeki istenilen değeri silme metodu
    public void remove(int id);

    //bağlı lsitenin boyutunu dönen metod
    public int sayac();

    //bağlı listenin boş olup olmadığını dönen metot
    public boolean bos();

    //her silme işleminden sonra karma çıkmaması adına id'leri yenidenn onarıyoruz.
    public void id_Onarim();

    //Bu metot ise kullanıcının daha önce giridği verileri değiştirmek için yazıldı. Kullanıcı istediği verileri değiştirebiliyor.
    public int guncelle(int id, String Date, String Title);

    //bağlı listedeki istenilen sıradaki değerin film adını dönen metot(get ve set metotlarının bulunduğu sınıflar public olmadığı için oradaki metotlara bağlanlıamiyor. Bu sebeple burada tanımlanmış bir metotla işlem görüyoruz.)
    public String Film_Adi(int id);

    //bağlı listedeki istenilen sıradaki değerin date'ini dönen metot(get ve set metotlarının bulunduğu sınıflar public olmadığı için oradaki metotlara bağlanlıamiyor. Bu sebeple burada tanımlanmış bir metotla işlem görüyoruz.)
    public String Date(int id);

    //bağlı listedeki istenilen sıradaki değerin title'ını dönen metot(get ve set metotlarının bulunduğu sınıflar public olmadığı için oradaki metotlara bağlanlıamiyor. Bu sebeple burada tanımlanmış bir metotla işlem görüyoruz.)
    public String Title(int id);
}
