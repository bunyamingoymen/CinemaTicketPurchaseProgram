package code;
/*

Bu sınıfın amacı var olan sinema salonlarının bilgilerini tutmaktır.
Yeni bir sinema salonu eklenir ya da silinirse bu sınıfta bulunan metotlar aracılığıyla yapılmaktadır.
Bu sınıfta diğer sınıflar gibi elle yazılmış bir bağlı liste içerkemtedir.
Diğer sınıflara benzediği için bu sınıfta da ayrıtnılı anlatım yapılmamıştır.

*/

class List_Sinema_Salonlari {

    private int salon_id;
    private String salon_name;
    private int koltuk_sayisi;
    private List_Sinema_Salonlari next;

    public List_Sinema_Salonlari() {
    }

    public List_Sinema_Salonlari(int salon_id, String salon_name, int koltuk_sayisi) {
        this.salon_id = salon_id;
        this.salon_name = salon_name;
        this.koltuk_sayisi = koltuk_sayisi;
        this.next = null;
    }

    public int getSalon_id() {
        return salon_id;
    }

    public void setSalon_id(int salon_id) {
        this.salon_id = salon_id;
    }

    public String getSalon_name() {
        return salon_name;
    }

    public void setSalon_name(String salon_name) {
        this.salon_name = salon_name;
    }

    public int getKoltuk_sayisi() {
        return koltuk_sayisi;
    }

    public void setKoltuk_sayisi(int koltuk_sayisi) {
        this.koltuk_sayisi = koltuk_sayisi;
    }

    public List_Sinema_Salonlari getNext() {
        return next;
    }

    public void setNext(List_Sinema_Salonlari next) {
        this.next = next;
    }

}

public class Sinema_Salonlari {

    List_Sinema_Salonlari root;
    List_Sinema_Salonlari iter;

    public void add(int salon_id, String salon_name, int koltuk_sayisi) {
        List_Sinema_Salonlari tmp = new List_Sinema_Salonlari(salon_id, salon_name, koltuk_sayisi);
        if (root == null) {
            root = tmp;
            iter = tmp;
        } else {
            iter.setNext(tmp);
            iter = tmp;
        }
    }

    //Bütün sinema salonlarını siler
    public void remove() {
        root = null;
        iter = null;

    }

    public void remove(int salon_id) {

        List_Sinema_Salonlari tmp = root, prev = root;

        while (tmp != null) {

            if (tmp.getSalon_id() == salon_id) {

                if (root.getSalon_id() == salon_id) {

                    root = root.getNext();

                    id_Onarim();

                } else {

                    prev.setNext(tmp.getNext());

                    id_Onarim();

                }

            }

            prev = tmp;

            tmp = tmp.getNext();

        }

    }

    public void id_Onarim() {
        List_Sinema_Salonlari tmp = root, prev = root;
        while (tmp != null) {
            if (tmp == root) {
                root.setSalon_id(1);
            } else {
                tmp.setSalon_id(prev.getSalon_id() + 1);
            }
            prev = tmp;
            tmp = tmp.getNext();
        }
    }

    public int sayac() {
        List_Sinema_Salonlari tmp = root;
        int sayac = 0;
        while (tmp != null) {
            sayac++;
            tmp = tmp.getNext();
        }
        return sayac;
    }

    public String Salon_Name(int salon_id) {
        List_Sinema_Salonlari tmp = root;
        while (tmp != null) {
            if (tmp.getSalon_id() == salon_id) {
                return tmp.getSalon_name();
            } else {
                tmp = tmp.getNext();
            }
        }

        return null;
    }

    public int Koltuk_Sayisi(int salon_id) {
        List_Sinema_Salonlari tmp = root;

        while (tmp != null) {
            if (tmp.getSalon_id() == salon_id) {
                return tmp.getKoltuk_sayisi();
            } else {
                tmp = tmp.getNext();
            }
        }

        return -1;
    }

    public int guncelle(int salon_id, String name, int koltuk_sayisi) {
        List_Sinema_Salonlari tmp = root;
        while (tmp != null) {
            if (tmp.getSalon_id() == salon_id) {
                tmp.setSalon_name(name);
                tmp.setKoltuk_sayisi(koltuk_sayisi);
                return 1;
            }
            tmp = tmp.getNext();
        }

        return 0;
    }
}
