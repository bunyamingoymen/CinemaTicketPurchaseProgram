package code;
/*

Bu sınıftaki bağlı listenin amacı kullanıcın seçtiği filmin seanslarını listelemektir.
Yani kullanıcının seçtiği filmin hangi salonda ve hangi seansta bulunduğunu kısa süreli tutan bir bağlı liste sınıfıdır.
Bu bağlı liste sınıfı da elle yazılmıştır ve diğer elle yazılan bağlı liste sınıflarına benzemektedir.
Diğer bağlı liste sınıflarına benzediği içinse ayrıntılı açıklama yapılmamıştır.

*/

class List_Goruntuleme {

    private int goruntuleme_id;
    private int salon_id;
    private int seans_id;
    private List_Goruntuleme next;

    public List_Goruntuleme() {
    }

    public List_Goruntuleme(int goruntuleme_id, int salon_id, int seans_id) {
        this.goruntuleme_id = goruntuleme_id;
        this.salon_id = salon_id;
        this.seans_id = seans_id;
        this.next = null;
    }

    public int getGoruntuleme_id() {
        return goruntuleme_id;
    }

    public void setGoruntuleme_id(int goruntuleme_id) {
        this.goruntuleme_id = goruntuleme_id;
    }

    public int getSalon_id() {
        return salon_id;
    }

    public void setSalon_id(int salon_id) {
        this.salon_id = salon_id;
    }

    public int getSeans_id() {
        return seans_id;
    }

    public void setSeans_id(int seans_id) {
        this.seans_id = seans_id;
    }

    public List_Goruntuleme getNext() {
        return next;
    }

    public void setNext(List_Goruntuleme next) {
        this.next = next;
    }

}

public class Goruntuleme {

    List_Goruntuleme root;
    List_Goruntuleme iter;

    public void add(int goruntuleme_id, int salon_id, int seans_id) {
        List_Goruntuleme tmp = new List_Goruntuleme(goruntuleme_id, salon_id, seans_id);
        if (root == null) {
            root = tmp;
            iter = tmp;
        } else {
            iter.setNext(tmp);
            iter = tmp;
        }
    }

    public void remove() {
        root = null;
        iter = null;
    }

    public void remove(int goruntuleme_id) {
        List_Goruntuleme tmp = root, prev = root;
        while (tmp != null) {
            if (tmp.getGoruntuleme_id() == goruntuleme_id) {
                if (root.getGoruntuleme_id() == goruntuleme_id) {
                    root = root.getNext();
                    id_Onarim();
                } else {
                    prev.setNext(tmp.getNext());
                    id_Onarim();
                }
            } else {
                prev = tmp;
                tmp = tmp.getNext();
            }
        }
    }

    public void id_Onarim() {
        List_Goruntuleme tmp = root, prev = root;
        while (tmp != null) {
            if (tmp == root) {
                root.setGoruntuleme_id(1);
            } else {
                tmp.setGoruntuleme_id(prev.getGoruntuleme_id() + 1);
            }
            prev = tmp;
            tmp = tmp.getNext();
        }
    }

    public int sayac() {
        List_Goruntuleme tmp = root;
        int sayac = 0;
        while (tmp != null) {
            sayac++;
            tmp = tmp.getNext();
        }
        return sayac;
    }

    public int salon_id(int goruntuleme_id) {
        List_Goruntuleme tmp = root;
        while (tmp != null) {
            if (tmp.getGoruntuleme_id() == goruntuleme_id) {
                return tmp.getSalon_id();
            }
            tmp = tmp.getNext();
        }
        return 0;
    }

    public int seans_id(int goruntuleme_id) {
        List_Goruntuleme tmp = root;
        while (tmp != null) {
            if (tmp.getGoruntuleme_id() == goruntuleme_id) {
                return tmp.getSeans_id();
            }
            tmp = tmp.getNext();
        }
        return 0;
    }

    public boolean bos() {
        if (root == null) {
            return true;
        } else {
            return false;
        }
    }
}
