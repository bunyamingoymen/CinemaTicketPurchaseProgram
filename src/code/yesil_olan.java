package code;

/*

Bu sınıfın amacı satın alma işleminde yeşil olan butonları listeleyip satın al dosyasına yazdırmak
Bu iş için burada bir bağlı liste oluşturuldu ve yeşil olanlar bu bağlı listede tutulmaktadır.
Bu bağlı listenin içierği de diğer sınıflardaki bağlı listeyle aynıdır.
Diğer sınıflardaki bağlı listelerle aynı olduğu için ayrıntlı anlatım yapılmamıştır.

*/

class List_yesil_olan {

    private int id;
    private String koltuk;
    private List_yesil_olan next;

    public List_yesil_olan() {
    }

    public List_yesil_olan(int id, String koltuk) {
        this.id = id;
        this.koltuk = koltuk;
        this.next = null;
    }

    public String getKoltuk() {
        return koltuk;
    }

    public void setKoltuk(String koltuk) {
        this.koltuk = koltuk;
    }

    public List_yesil_olan getNext() {
        return next;
    }

    public void setNext(List_yesil_olan next) {
        this.next = next;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}

public class yesil_olan {

    List_yesil_olan root;
    List_yesil_olan iter;

    public void add(int id, String koltuk) {
        List_yesil_olan tmp = new List_yesil_olan(id, koltuk);
        if (root == null) {
            root = tmp;
            iter = tmp;
        } else {
            iter.setNext(tmp);
            iter = iter.getNext();
        }
    }

    public void remove(int id) {
        List_yesil_olan tmp = root, prev = null;
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

    public void remove() {
        root = null;
        iter = null;

    }

    public void remove(String koltuk) {
        List_yesil_olan tmp = root, prev = null;
        while (tmp != null) {
            if (tmp.getKoltuk().equals(koltuk)) {
                if (root.getKoltuk().equals(koltuk)) {
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

    public void id_Onarim() {
        List_yesil_olan temp = root, prev = null;
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

    public int sayac() {
        int sayac = 0;
        List_yesil_olan tmp = root;
        while (tmp != null) {
            sayac++;
            tmp = tmp.getNext();
        }
        return sayac;
    }

    public boolean bos() {
        if (root == null) {
            return true;
        } else {
            return false;
        }
    }

    public String koltuk(int id) {
        List_yesil_olan tmp = root;
        while (tmp != null) {
            if (tmp.getId() == id) {
                return tmp.getKoltuk();
            }
            tmp = tmp.getNext();
        }
        return null;
    }
}
