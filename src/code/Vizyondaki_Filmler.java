package code;

/*

Bu metot admin kullanıcısının daha önce girdiği vizyondaki filmlerin bilgilerini almak veya yeni veri oluşturmak için yazıldı
Üsteki sınıf(List_Vizyondaki_Filmler) List_Filmler sınıfını extends ederken. Alttaki sınıf(Vizyondaki_Filmler) Filmler sınıfını implements etnektedir.
Bu sınıfta diğer sınıflar gibi bağlı liste mantığında çalışmaktadır. Ve bu bağlı liste elle yazılmıştır.

 */
class List_Vizyondaki_Filmler extends List_Filmler {

    //ilk olarak kalıtım ile gelmeyen özellikleri tanımlıyoruz.
    private List_Vizyondaki_Filmler next;

    public List_Vizyondaki_Filmler() {
    }

    //daha sonra ekleme işlemi için gerekli yapılandırıcı metodu yazıyoruz. super kodu ile de üst sınıftaki yapılandırıc metodu kullanıyoruz.
    public List_Vizyondaki_Filmler(int id, String Date, String Title) {
        super(id, Date, Title);
        this.next = null;
    }

    //daha sonra gerekli olan get ve set metotlarını oluşturuyoruz.
    @Override
    public List_Vizyondaki_Filmler getNext() {
        return next;
    }

    public void setNext(List_Vizyondaki_Filmler next) {
        this.next = next;
    }

}

//bu sınıf ise bağlı liste için gerkli olan metotların barındığı sınıftır.
public class Vizyondaki_Filmler implements Filmler {

    //ilk başta ilk ve son değeri tutmak için bir değişken tanımlıyoruz.
    List_Vizyondaki_Filmler root;
    List_Vizyondaki_Filmler iter;

    //bu metot bağlı listedkei ekleme işlemini yapmaktadır.
    @Override
    public void add(int vizyondaki_filmler_id, String Date, String Title) {
        List_Vizyondaki_Filmler tmp = new List_Vizyondaki_Filmler(vizyondaki_filmler_id, Date, Title);
        if (root == null) {
            root = tmp;
            iter = tmp;
        } else {
            iter.setNext(tmp);
            iter = tmp;
        }
    }

    //bu metot var olan bütün bağlı listede bulunan değerleri silmek için tasarlandı.
    @Override
    public void remove() {
        root = null;
        iter = null;

    }

    //Bu metot bağlı listedeki istenilen değeri silmek için yazıldı. 
    @Override
    public void remove(int id) {
        List_Vizyondaki_Filmler tmp = root, prev = null;
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
            }
            prev = tmp;
            tmp = tmp.getNext();
        }

    }

    //Bu metot bağlı listede kaç tane değer olduğunu döndürmek için yazıldı.
    @Override
    public int sayac() {
        int sayac = 0;
        List_Vizyondaki_Filmler tmp = root;
        while (tmp != null) {
            tmp = tmp.getNext();
            sayac++;
        }

        return sayac;
    }

    //Bu metot bağlı listenin boş olup olmadığını kontorl etmektedir. Boş sa true değilse false dönmektedir.
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
        List_Vizyondaki_Filmler temp = root, prev = null;
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
        List_Vizyondaki_Filmler tmp = root;
        Dosya dosya_islemleri = new Dosya();
        while (tmp != null) {
            if (tmp.getId() == id) {
                tmp.setDate(Date);
                tmp.setTitle(Title);
                dosya_islemleri.filmler_dosya_yaz(this, 1);
                return 1;
            } else {
                tmp = tmp.getNext();
            }
        }

        return 0;
    }

    //Bu metot filmin adını dönmektedir. (List_Vizyondaki_Filmler ve List_Filmler sınıfları public olmadığı için oradaki metotlar çağrılamıyor. Bu sebeple burada bir metot oluşturuyoruz.)
    @Override
    public String Film_Adi(int id) {
        String film_adi = "";
        List_Vizyondaki_Filmler tmp = root;
        while (tmp != null) {
            if (tmp.getId() == id) {
                film_adi = tmp.getTitle();
                break;
            } else {
                tmp = tmp.getNext();
            }
        }

        return film_adi;
    }

    //Bu metot istenilen sıradaki Date'i vermektedir.(List_Vizyondaki_Filmler, List_Filmeler sınıfları public olmadığı için oradaki get set metoduna farklı bir paketten erişim yok. Bu sebeple buradaki metotla erişiyoruz.)
    @Override
    public String Date(int id) {
        List_Vizyondaki_Filmler tmp = root;
        while (tmp != null) {
            if (tmp.getId() == id) {
                return tmp.getDate();
            } else {
                tmp = tmp.getNext();
            }
        }

        return null;
    }

    //Bu metot istenilen sıradaki Title'ı vermektedir.(List_Vizyondaki_Filmler, List_Filmeler sınıfları public olmadığı için oradaki get set metoduna farklı bir paketten erişim yok. Bu sebeple buradaki metotla erişiyoruz.)
    @Override
    public String Title(int id) {
        List_Vizyondaki_Filmler tmp = root;
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
