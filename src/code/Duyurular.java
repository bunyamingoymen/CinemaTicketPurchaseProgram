
package code;

/*

Hem kampanyalar'da hem de haberler'de ortak olan metotları bir yere topladık. 

Ayrıca Duyurular sınıfınıda(aşağıda) abstract metodu olarak ayarladık

List_Duyurular bölümü ise kampanyalar ve haberlerin List_kampanyalar ve Liat_haberler bölümüne extends edildi. Çünkü orada da 
ortak olanlar var ama bu abstarct metodu değil 

Yani yine kısacası normal bir bağlı listede ne bulunuyorsa burada da o bulunuyor.
*/

//burada bağlı listenin içeriğini ayarlıyoruz.
class List_Duyurular {
    //gerekli olan değişkenleri tanımlıyoruz.
    private int duyuru_id;
    private String Date;
    private String Title;
    private String Duyuru;
    private List_Duyurular next;

    public List_Duyurular() {
    }
    
    //ilk başta bu sınıftaki bağlı listenin ihtiyacı olan yapılandırıcı metodu tanımlşıyoruz.
    public List_Duyurular(int duyuru_id, String Date, String Title, String Duyurular, List_Duyurular next) {
        this.duyuru_id = duyuru_id;
        this.Date = Date;
        this.Title = Title;
        this.Duyuru = Duyurular;
        this.next = null;
    }
    
    //daha sonra alt sınıfların ihtiyacı olan yapılandırıcı metodu tanımlıyoruz. 
    public List_Duyurular(int duyuru_id, String Date, String Title, String Duyuru) {
        this.duyuru_id = duyuru_id;
        this.Date = Date;
        this.Title = Title;
        this.Duyuru = Duyuru;
    }
    
    //daha sonra gerekli olan get ve set metotlarını tanımlıyoruz.
    
    public int getDuyuru_id() {
        return duyuru_id;
    }

    public String getDate() {
        return Date;
    }

    public String getTitle() {
        return Title;
    }

    public void setDuyuru_id(int duyuru_id) {
        this.duyuru_id = duyuru_id;
    }

    public List_Duyurular getNext() {
        return next;
    }

    public void setNext(List_Duyurular next) {
        this.next = next;
    }

    public String getDuyuru() {
        return Duyuru;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public void setDuyuru(String Duyuru) {
        this.Duyuru = Duyuru;
    }

}
//burası yukarıdada belirttiğimiz gibi abstract sınıfı oluyor.
public abstract class Duyurular {
    
    //ilk olarak ilk ve son değerleri tutacak iki değişken tanımlıyoruz. 
    List_Duyurular root;
    List_Duyurular iter;
    
    //bu metot bağlı listedeki klasik ekleme işlemini yapan metotdur. burada bağlı listeye ekleme yapıyoruz.
    public void add(int id, String Date, String Title, String Duyurular) {
        List_Duyurular tmp = new List_Duyurular(id, Date, Title, Duyurular, null);
        if (root == null) {
            root = tmp;
            iter = tmp;
        } else {
            iter.setNext(tmp);
            iter = tmp;
        }
    }
    
    //burası bütün bağlı listeyi sıfırlayan metot. yani bağlı listedeki bütün değerleri silebilen metot.
    public void remove() {
        root = null;
        iter = null;

    }
    
    //burası bağlı listedeki istenilen duyuruyu silebilmek için yazılan metot. Her duyurunun kendine özel id'si bulunmakta.
    public void remove(int id) {
        List_Duyurular tmp = root, prev = null;
        while (tmp != null) {
            if (tmp.getDuyuru_id() == id) {
                if (root.getDuyuru_id() == id) {
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
    
    //Burası bağlı listenin boyutunu dönüyor. Geçici bir değişken atayıp onu null olana kadar döndürüyoruz ve bu sıradada sayacı her dönüşte bir arttırıyoruz.
    public int sayac() {
        int sayac = 0;
        List_Duyurular say = root;
        while (say != null) {
            sayac++;
            say = say.getNext();
        }
        return sayac;
    }
    
    //burası bağlı listenin boş olup olmadığını kontrol eden metot. Bağlı liste boş ise true, dolu ise false dönüyor.
    public boolean bos() {
        boolean bos;
        if (root == null) {
            bos = true;
        } else {
            bos = false;
        }

        return bos;
    }
    
    //Burası bağlı liste silindikten sonra id'leri onarıyor. Yani bağlı listede bir değer silindikten sonra id'karmaşası olmasın diye id'leri yeninden onarıyoruz.
    public void id_Onarim() {
        List_Duyurular temp = root, prev = null;
        while (temp != null) {
            if (temp == root) {
                temp.setDuyuru_id(1);
            } else {
                temp.setDuyuru_id(prev.getDuyuru_id() + 1);
            }

            prev = temp;
            temp = temp.getNext();
        }
    }
    
    //burası Date değerini dönüyor. (farklı paketlerdeki sınıflar üsteki class'ı çağıramıyor(firends olduğu için). haliyle de burada bir metot aracılığıyla çağrıyoruz.)
    public String Date(int id) {
        List_Duyurular tmp = root;
        while (tmp != null) {
            if (tmp.getDuyuru_id() == id) {
                return tmp.getDate();
            } else {
                tmp = tmp.getNext();
            }
        }
        return null;
    }
    
    //Burası istenilen değerdeki Title değerini döndürüyor.
    public String Title(int id) {
        List_Duyurular tmp = root;
        while (tmp != null) {
            if (tmp.getDuyuru_id() == id) {
                return tmp.getTitle();
            } else {
                tmp = tmp.getNext();
            }
        }
        return null;
    }

    //Burası bağlı listenin istenilen bölümündeki Duyuru değerini döndürüyor.
    public String Duyuru(int id) {
        List_Duyurular tmp = root;
        while (tmp != null) {
            if (tmp.getDuyuru_id() == id) {
                return tmp.getDuyuru();
            } else {
                tmp = tmp.getNext();
            }
        }
        return null;
    }
}
