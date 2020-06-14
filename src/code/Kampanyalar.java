package code;

/*

Bu sınıf daha önceden eklenen kampanyaları göstermektedir. Elle yazılmış bağlı listeyi içermektedir.
Ayrıca üsteki sınıf yani List_Kampnayalar, List_Duuyurlar'a; alttaki sınıf yani Kampnayalar da Duyurulara kalıtım ile bağlıdır.
Bu kalıtım da haberler ile ortak olan değişkenler Duyurular da tanımlanmıştır.

 */
class List_Kampanyalar extends List_Duyurular {

    //ilk olarak bir sonraki değeri tutmak için bir değişken atıyoruz.
    private List_Kampanyalar next;

    //diğerlerine gerek yok. Duyurular'da tanımlandı
    public List_Kampanyalar() {

    }

    public List_Kampanyalar(int kampanya_id, String Date, String Title, String Duyuru) {
        //duyurular'da tanımlanan değerlere verileri super komutu ile gönderiyoruz. 
        super(kampanya_id, Date, Title, Duyuru);
        this.next = null;
    }

    //next metodunun get-set metotlarını tanımlıyoruz.
    @Override
    public List_Kampanyalar getNext() {
        return next;
    }

    public void setNext(List_Kampanyalar next) {
        this.next = next;
    }

}

public class Kampanyalar extends Duyurular {

    //ilk olarak ilk değeri ve son değeri tutacak değişkenler tanımlıyoruz.
    List_Kampanyalar root;
    List_Kampanyalar iter;

    //Bu metot klaskik bir şekilde ekleme işlemi yapmaktadır.
    @Override
    public void add(int kampanya_id, String Date, String Title, String Kampanya) {
        List_Kampanyalar tmp = new List_Kampanyalar(kampanya_id, Date, Title, Kampanya);
        if (root == null) {
            root = tmp;
            iter = tmp;
        } else {
            iter.setNext(tmp);
            iter = tmp;
        }
    }

    //Bu metot var olan bütün kampanyaları silmek için tasarlanmıştır. 
    @Override
    public void remove() {
        root = null;
        iter = null;

    }

    //bu metot ise sadece istenilen kampanyayı silmektedir.
    @Override
    public void remove(int id) {
        List_Kampanyalar tmp = root, prev = null;
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
            }
            prev = tmp;
            tmp = tmp.getNext();
        }

    }

    //bu metot kaç tane kampanya ekli olduğunu bulmak için kullanılıyor.
    @Override
    public int sayac() {
        int sayac = 0;
        List_Kampanyalar say = root;
        while (say != null) {
            sayac++;
            say = say.getNext();
        }
        return sayac;
    }

    //bu metot kampanyaların boş olup olamdığını kontrol ediyor. Boş ise true, depilse false yolluyor.
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

    //silme işleminden sonra id'lerin karışmaması için her silme işleminden sonra bu metoda yolluyoruz.
    @Override
    public void id_Onarim() {
        List_Kampanyalar temp = root, prev = null;
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

    //bu metot istenilen kampanyadaki Tarih değerini return ediyor.
    @Override
    public String Date(int id) {
        List_Kampanyalar tmp = root;
        while (tmp != null) {
            if (tmp.getDuyuru_id() == id) {
                return tmp.getDate();
            }
            tmp = tmp.getNext();
        }

        return null;
    }

    //bu metot istenilen kampanyadaki Başlık değerini return ediyor.
    @Override
    public String Title(int id) {
        List_Kampanyalar tmp = root;
        while (tmp != null) {
            if (tmp.getDuyuru_id() == id) {
                return tmp.getTitle();
            }
            tmp = tmp.getNext();
        }
        return null;
    }

    //bu metot istenilen kampanyadaki Duyuru değerini return ediyor.
    @Override
    public String Duyuru(int id) {
        List_Kampanyalar tmp = root;
        while (tmp != null) {
            if (tmp.getDuyuru_id() == id) {
                return tmp.getDuyuru();
            }
            tmp = tmp.getNext();
        }
        return null;
    }

    //Bu metot var olan kampanyayı güncellemek için yazılan metot.
    public int guncelle(int id, String Date, String Title, String Kampanya, Kampanyalar kampanya) {
        List_Kampanyalar tmp = kampanya.root;
        Dosya dosya_islemleri = new Dosya();
        while (tmp != null) {
            if (tmp.getDuyuru_id() == id) {
                tmp.setDate(Date);
                tmp.setTitle(Title);
                tmp.setDuyuru(Kampanya);
                dosya_islemleri.duyurular_dosya_yaz(kampanya, 1);
                return 1;
            }
            tmp = tmp.getNext();
        }
        return 0;
    }
}
