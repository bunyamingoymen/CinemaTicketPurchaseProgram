package code;

/*
Burası admin kullanıcısının giridği eski filmleri tutmak için tasarlanan bağlı listedir.

Üsteki List_Eski_Filmler sınıfı List_Fİlmler sınıfını; Altaki Eski_Fİlmler sınıfı ise Filmler sınıfını implements etmektedir.
Vizyondaki filmler ile ortak olan özellikleri kalıtım aracılığıyla bir sınıfta bağladık.

Burası da kısacası klasik bağlı listenin elle yazılmış sınıfıdır.
 */
//Bu sınıf bağlı listedeki değerleri eklemek ya da  değiştirmek için kullanılıyor
class List_Eski_Filmler extends List_Filmler {

    //gerekli olan değişkeni tanımlıyoruz. Ancak çoğu özellik List_Filmler sınıfında bulunduğu için buraya bir tek next özelliği kalıyor.
    private List_Eski_Filmler next;

    public List_Eski_Filmler() {
    }

    //burası bağlılistye yeni bir değer eklemek istediğinde çalışıyor.
    public List_Eski_Filmler(int id, String Date, String Title) {
        super(id, Date, Title);
        this.next = null;
    }

    //aşağıda ise gerekli olan get ve set metotları tanımlandı.
    public List_Eski_Filmler getNext() {
        return next;
    }

    public void setNext(List_Eski_Filmler next) {
        this.next = next;
    }

}

//bu sınıfda bağlı liste için gerekli olan metotları tutuyor.
public class Eski_Filmler implements Filmler {

    //ilk olarak ilk ve son değeri tutabilmek için değer tanımlıyoruz.
    List_Eski_Filmler root;
    List_Eski_Filmler iter;
    
    //Filmler sınıfından override edilen ekleme metodunu yazıyoruz. Bu metot klasık bir şekilde bağlı listeye ekleme işlemi yapan metot.
    @Override
    public void add(int id, String Date, String Title) {
        List_Eski_Filmler tmp = new List_Eski_Filmler(id, Date, Title);
        if (root == null) {
            root = tmp;
            iter = tmp;
        } else {
            iter.setNext(tmp);
            iter = tmp;
        }
    }

    //bu metot bağlılistede bulunan her değeri siliyor.
    @Override
    public void remove() {
        root = null;
        iter = null;

    }
    
    //bu metot bağlı listede istenilen değeri siliyor.
    @Override
    public void remove(int id) {
        List_Eski_Filmler tmp = root, prev = null;
        while (tmp != null) {
            if (tmp.getId() == id) {
                if (root.getId() == id) {
                    root = root.getNext();
                    id_Onarim();
                    break;
                } else {
                    prev.setNext(tmp.getNext());
                    id_Onarim();
                    break;
                }
            } else {
                prev = tmp;
                tmp = tmp.getNext();
            }
        }

    }
    
    //bu metot bağlı listede kaç tane değer varsa onu dönüyor. Yani bağlı listenin boyutunu dönüyor.
    @Override
    public int sayac() {
        int sayac = 0;
        List_Eski_Filmler say = root;
        while (say != null) {
            sayac++;
            say = say.getNext();
        }
        return sayac;
    }
    
    //bu metot bağlı listenin boş oluğ olmadığını kontrol ediyor. Boş ise true, değilse false dönüyor.
    @Override
    public boolean bos() {
        boolean bos;
        if (root == null) {
            bos = true;
        } else {
            bos = false;
        }

        return bos;
    }
    
    //Bu metot her silme işleminden sonra id'leri yeniden onarıyor. id karmaşası olmaması adına her silme işleminden sonra id'leri yeninden onarıyoruz.
    @Override
    public void id_Onarim() {
        List_Eski_Filmler temp = root, prev = null;
        while (temp != null) {
            if (temp == root) {
                temp.setId(1);
            } else {
                temp.setId(prev.getId() + 1);
            }

            prev = temp;
            temp = temp.getNext();
        }
    }
    

    
    
    //bu metot var olan bir değeri güncellemek için yazıldı. Yani kullanıcı istediği yerdeki bir değeri değiştirebilsin diye bu metot var
    @Override
    public int guncelle(int id, String Date, String Title) {
        List_Eski_Filmler tmp = root;
        Dosya dosya_islemleri = new Dosya();
        while (tmp != null) {
            if (tmp.getId() == id) {
                tmp.setDate(Date);
                tmp.setTitle(Title);
                dosya_islemleri.filmler_dosya_yaz(this, 0);
                return 1;
            } else {
                tmp = tmp.getNext();
            }
        }
        return 0;
    }
    
    //Bu metot filmin adını dönmektedir. (List_Eski_Filmler ve List_Filmler sınıfları public olmadığı için oradaki metotlar çağrılamıyor. Bu sebeple burada bir metot oluşturuyoruz.)
    @Override
    public String Film_Adi(int id) {
        List_Eski_Filmler tmp = root;
        while (tmp.getNext() != null) {
            if (tmp.getId() == id) {
                return tmp.getTitle();
            }
            tmp = tmp.getNext();
        }
        return null;
    }
    
    //Bu metot istenilen sıradaki Date'i vermektedir.(List_Eski_Filmler, List_Filmeler sınıfları public olmadığı için oradaki get set metoduna farklı bir paketten erişim yok. Bu sebeple buradaki metotla erişiyoruz.)
    @Override
    public String Date(int id) {
        List_Eski_Filmler tmp = root;
        while (tmp != null) {
            if (tmp.getId() == id) {
                return tmp.getDate();
            } else {
                tmp = tmp.getNext();
            }
        }
        return null;
    }
    
    //Bu metot istenilen sıradaki Title'ı vermektedir.(List_Eski_Filmler, List_Filmeler sınıfları public olmadığı için oradaki get set metoduna farklı bir paketten erişim yok. Bu sebeple buradaki metotla erişiyoruz.)
    @Override
    public String Title(int id) {
        List_Eski_Filmler tmp = root;
        while (tmp != null) {
            if (tmp.getId() == id) {
                return tmp.getTitle();
            } else {
                tmp = tmp.getNext();
            }
        }
        return null;
    }
}
