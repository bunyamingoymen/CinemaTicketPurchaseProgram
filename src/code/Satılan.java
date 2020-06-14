package code;
/*

Bu sınıfın amacı satılan koltukları tutmaktadır. 
Yani daha önceden satılan koltukları bir bağlı listede tutuyoruz ve onları kırmızı renk ile gösteriyoruz.
Bu sınıfta elle yazılmış bir bağlı liste bulunmaktadır. Ve diğer sınıflara benzemektedir.
Diğer sınıflara benzediği içinde ayrıntlı anlatım yapılmamıştır.

*/
class List_Satılan {

    private int id;
    private String koltuk;
    private List_Satılan next;

    public List_Satılan() {
    }

    public List_Satılan(int id, String koltuk) {
        this.id = id;
        this.koltuk = koltuk;
        this.next = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKoltuk() {
        return koltuk;
    }

    public void setKoltuk(String koltuk) {
        this.koltuk = koltuk;
    }

    public List_Satılan getNext() {
        return next;
    }

    public void setNext(List_Satılan next) {
        this.next = next;
    }

}

public class Satılan {

    List_Satılan root;
    List_Satılan iter;

    public void add(int salon_id, int seans_id) {

        Bilet_Satin_Al bilet = new Bilet_Satin_Al();

        Dosya dosya_islemleri = new Dosya();

        dosya_islemleri.bilet_satin_al_dosya_oku(bilet);

        List_Bilet_Satin_Al tmp = bilet.root;

        while (tmp != null) {

            if ((tmp.getSalon_id() == salon_id) && (tmp.getSeans_id() == seans_id)) {

                List_Satılan ekle = new List_Satılan(sayac() + 1, tmp.getKoltuk());

                if (root == null) {

                    root = ekle;

                    iter = ekle;

                } else {

                    iter.setNext(ekle);

                    iter = iter.getNext();

                }

            }

            tmp = tmp.getNext();

        }

    }

    public int sayac() {
        int sayac = 0;
        List_Satılan tmp = root;
        while (tmp != null) {
            sayac++;
            tmp = tmp.getNext();
        }
        return sayac;
    }

    public String koltuk(int id) {
        List_Satılan tmp = root;
        while (tmp != null) {
            if (tmp.getId() == id) {
                return tmp.getKoltuk();
            }
            tmp = tmp.getNext();
        }
        return null;
    }
}
