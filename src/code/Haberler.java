
package code;

/*

Bu sınıf daha önceden eklenen Haberleri göstermektedir. Elle yazılmış bağlı listeyi içermektedir.
Ayrıca üsteki sınıf yani List_Haberler, List_Duuyurlar'a; alttaki sınıf yani Haberler de Duyurulara kalıtım ile bağlıdır.
Bu kalıtım da kampanyalar ile ortak olan değişkenler Duyurular da tanımlanmıştır.

 */

class List_Haberler extends List_Duyurular {
    //ilk başta bir sonraki değeri tutmak için değişken tanımlşıyoruz.
    private List_Haberler next;

    public List_Haberler() {

    }
    
    public List_Haberler(int haber_id, String Date, String Title, String Duyuru) {
        // yapılandırıcı metotda üst sınıfta bulunan metoda değişkenleri gönderip orada tanımlıyoruz.
        super(haber_id, Date, Title, Duyuru);
        this.next = null;
    }
    //daha sonra ise get-set metotlarını tanımlıyoruz.
    public List_Haberler getNext() {
        return next;
    }

    public void setNext(List_Haberler next) {
        this.next = next;
    }

}

public class Haberler extends Duyurular {

    //ilk başta ilk ve son değeri tutmak için değişkenler tanımlıyoruz.
    List_Haberler root;
    List_Haberler iter;
    
    //bu bağlı liste için klasik ekleme metodudur.
    @Override
    public void add(int id, String Date, String Title, String Haber) {
        List_Haberler tmp = new List_Haberler(id, Date, Title, Haber);
        if (root == null) {
            root = tmp;
            iter = tmp;
        } else {
            iter.setNext(tmp);
            iter = tmp;
        }
    }
    //bütün haberleri silmek için çağrılan metot
    @Override
    public void remove() {
        root = null;
        iter = null;

    }
    //istenilen haberi silmek için çağrılan metot
    @Override
    public void remove(int id) {
        List_Haberler tmp = root, prev = null;
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
    //toplamda kaç tane haber olduğunu bulan metot.
    @Override
    public int sayac() {
        int sayac = 0;
        List_Haberler say = root;
        while (say != null) {
            sayac++;
            say = say.getNext();
        }
        return sayac;
    }
    //haberlerin boş olup olmadığını kontrol eden metot. boş ise true, dolu ise false dönüyor.
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
    //her silme işleminden sonra id'lerin birbirine girmemesi için id'leri onarıyoruz. Bu metot buna yarıyor.
    @Override
    public void id_Onarim() {
        List_Haberler temp = root, prev = null;
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
    //bu metot istenilen haberdeki Tarih değerini return ediyor.
    @Override
    public String Date(int id) {
        List_Haberler tmp = root;
        while (tmp != null) {
            if (tmp.getDuyuru_id() == id) {
                return tmp.getDate();
            }
            tmp = tmp.getNext();
        }
        return null;
    }
    //bu metot istenilen haberdeki Başlık değerini return ediyor.
    @Override
    public String Title(int id) {
        List_Haberler tmp = root;
        while (tmp != null) {
            if (tmp.getDuyuru_id() == id) {
                return tmp.getTitle();
            }
            tmp = tmp.getNext();
        }
        return null;
    }
    //bu metot istenilen haberdeki Duyuru değerini return ediyor.
    @Override
    public String Duyuru(int id) {
        List_Haberler tmp = root;
        while (tmp != null) {
            if (tmp.getDuyuru_id() == id) {
                return tmp.getDuyuru();
            }
            tmp = tmp.getNext();
        }
        return null;
    }
    //Bu metot var olan haberi güncellemek için yazılan metot.
    public int guncelle(int id, String date, String title, String duyuru, Haberler haber) {
        int control = 0;
        List_Haberler tmp = haber.root;
        Dosya dosya_islemleri = new Dosya();
        while (tmp != null) {
            if (tmp.getDuyuru_id() == id) {
                control = 1;
                tmp.setDate(date);
                tmp.setTitle(title);
                tmp.setDuyuru(duyuru);
                tmp = null;
            } else {
                tmp = tmp.getNext();
            }
        }
        dosya_islemleri.duyurular_dosya_yaz(haber, 0);

        return control;

    }
}
