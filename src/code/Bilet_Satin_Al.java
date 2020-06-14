package code;

/*

Bu sınıf daha önceden satın alınan biletleri göstermek için oluşturulan bağlı listeyi içermektedir. 
Bağlı liste elle yazılmıştır ve hangi metodun ne işe yaradğı aşağıda belirtilmiştir.


 */
class List_Bilet_Satin_Al {

    //burası bağlı listenin elemanlarını tutuyor.
    private int satin_alinan_bilet_id;
    private int user_id;
    private int salon_id;
    private int seans_id;
    private String koltuk;
    private List_Bilet_Satin_Al next;

    public List_Bilet_Satin_Al() {
    }

    //bağlı listenin yapılandırıcı metodu
    public List_Bilet_Satin_Al(int satin_alinan_bilet_id, int user_id, int salon_id, int seans_id, String koltuk) {
        this.satin_alinan_bilet_id = satin_alinan_bilet_id;
        this.user_id = user_id;
        this.salon_id = salon_id;
        this.seans_id = seans_id;
        this.koltuk = koltuk;
        this.next = null;
    }

    // bağlı listenin get ve set metotları
    public int getSatin_alinan_bilet_id() {
        return satin_alinan_bilet_id;
    }

    public void setSatin_alinan_bilet_id(int satin_alinan_bilet_id) {
        this.satin_alinan_bilet_id = satin_alinan_bilet_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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

    public String getKoltuk() {
        return koltuk;
    }

    public void setKoltuk(String koltuk) {
        this.koltuk = koltuk;
    }

    public List_Bilet_Satin_Al getNext() {
        return next;
    }

    public void setNext(List_Bilet_Satin_Al next) {
        this.next = next;
    }

}

public class Bilet_Satin_Al {

    //bağlı listedeki işlemler için iter (son değer) ve root(il değer) için bir değişken tanımlanıyor
    List_Bilet_Satin_Al root;
    List_Bilet_Satin_Al iter;

    //bu method klasik bağlı liste ekleme metodudur. 
    public void add(int satin_alinan_bilet_id, int user_id, int salon_id, int seans_id, String koltuk) {
        //ilk başta bütün verileri parametre olarak alıyoruz ve yukarıda tanımladığımız bağlı liste class'ındaki yapılandırıcı methoda yollayarak bir değişken oluşturuyoruz.
        List_Bilet_Satin_Al tmp = new List_Bilet_Satin_Al(satin_alinan_bilet_id, user_id, salon_id, seans_id, koltuk);

        //Eğer root, null ise daha önce herhangi bir değer girilmemiş demek oluyor. O zaman ilk değeri root'a ve iter'a ekliyoruz.
        if (root == null) {
            root = tmp;
            iter = tmp;
        } //Bu else girmiş ise daha önceden eklenen bir değer vardır demektir. O zamanda iter'ın next'ine o değeri ekliyoruz ve ardından da iter'i bu değer olarak atıyoruz. Böylece iter hep en sonu tutmuş oluyor.
        else {
            iter.setNext(tmp);
            iter = iter.getNext();
        }
    }

    //bu metot var olan bütün satın alınmış biletleri silmek için yazıldı. Bu metot aktif olduğunda bütün satın alınan biletler siliniyor.
    public void remove() {
        root = null;
        iter = null;
    }

    //Bu metot istenilen bir satın alma işlemini silmek için yazıldı.
    public void remove(int satin_alinan_bilet_id) {
        //ilk başta iki değişken tanımlıyoruz. daha sonra bunlara ilk değeri atayıp while döngüsüyle null olana kadar döndürüyoruz.
        List_Bilet_Satin_Al tmp = root, prev = root;

        while (tmp != null) {
            //burada silinmesini istediğimiz satin alınan biletin id'sini kontrol ediyoruz. tmp'nin id'si ile hernagi bir uyuşma var ise if'e girdiriyoruz.
            if (tmp.getSatin_alinan_bilet_id() == satin_alinan_bilet_id) {
                //bu if'e girdi ise şu'anki tmp'nin bulunduğu yer silinmek isteniyor anlamına geliyor ilk olarak ilk değerin yani root'un silinmek istenip istenmediğini kontrol ediyoruz.
                if (root.getSatin_alinan_bilet_id() == satin_alinan_bilet_id) {
                    //eğer root'un silinmesi isteniyorsa o zaman root'u bir sonrakine kaydrıyoruz. ve id_onarim'a yolayıp silindikten sonra id'leri yeniden düzenliyoruz.
                    root = root.getNext();
                    id_Onarim();
                } else {
                    //eğer bu else'ye girmiş ise root değil başka bir nesne silinmek isteniyor anlamıan geliyor. O zaman da silinmesi istenilen nesneyi kendisinden sonrakine değil onan sonrakine bağlayarak ortadaki silinmek istenilen değeri yok sayıyoruz.
                    //ardından da id_Onarim'a yollayarak id'leri yeniden onarıyoruz.
                    prev.setNext(tmp.getNext());
                    id_Onarim();
                }
            }
            //eğer daha yukarıdaki if' e girmemiş ise o zaman daha değer aranıyor anlamına geliyor. prev'i tmp yapıp tmp yi bir sonrakine aktarıyoruz.
            prev = tmp;
            tmp = tmp.getNext();
        }
    }

    //bu metot herhangi bir satın almayı silme işlemi aktif olursa bilgi bozukluluğu olmaması açısından id'leri baştan sonra onariyor. Ve böylece herhangi bir bozukluk meydana gelmiyor.
    public void id_Onarim() {
        List_Bilet_Satin_Al tmp = root, prev = root;
        while (tmp != null) {
            if (tmp == root) {
                //tmp ilk değer de yani root ise bunun ilk değer olduğu anlamına geliyor o zaman id'sinin 1 olduğu anlamına geliyor. ve id'sini bir olarak atıyoruz.
                root.setSalon_id(1);
            } else {
                //root'tan sonraki yani ilk değerden sonraki değerler'in hepsi bir inceki id'den bir büyük olması gerekiyor. Bu sebeple bir öncekinin id'sine bir ekleyip bir sonraki değerin id'si olarak atıyoruz.
                tmp.setSalon_id(prev.getSalon_id() + 1);
            }
            prev = tmp;
            tmp = tmp.getNext();
        }
    }

    //bu fonksiyon bağlı listede kaç tane değer olduğunu teker teker sayıyor.
    public int sayac() {
        List_Bilet_Satin_Al tmp = root;
        int sayac = 0;
        while (tmp != null) {
            sayac++;
            tmp = tmp.getNext();
        }
        return sayac;
    }

    //bu metot satın alınan bilet'in id'sine göre onu satın alan kullanıcının id'sini return ediyor.
    public int user_id(int satin_alinan_bilet_id) {
        List_Bilet_Satin_Al tmp = root;
        while (tmp != null) {
            if (tmp.getSatin_alinan_bilet_id() == satin_alinan_bilet_id) {
                return tmp.getUser_id();
            }
            tmp = tmp.getNext();
        }
        return -1;
    }

    //bu metot satın alınan bilet'in id'sine göre salonun id'sini return ediyor.
    public int Salon_id(int satin_alinan_bilet_id) {
        List_Bilet_Satin_Al tmp = root;
        while (tmp != null) {
            if (tmp.getSatin_alinan_bilet_id() == satin_alinan_bilet_id) {
                return tmp.getSalon_id();
            }
            tmp = tmp.getNext();
        }
        return -1;
    }

    //bu metot satın alınan bilet'in id'sine göre seansın id'sini return ediyor.
    public int seans_id(int satin_alinan_bilet_id) {
        List_Bilet_Satin_Al tmp = root;
        while (tmp != null) {
            if (tmp.getSatin_alinan_bilet_id() == satin_alinan_bilet_id) {
                return tmp.getSeans_id();
            }
            tmp = tmp.getNext();
        }
        return -1;
    }
//bu metot satın alınan bilet'in id'sine göre satın alınan koltuğu return ediyor.

    public String koltuk(int satin_alinan_bilet_id) {
        List_Bilet_Satin_Al tmp = root;
        while (tmp != null) {
            if (tmp.getSatin_alinan_bilet_id() == satin_alinan_bilet_id) {
                return tmp.getKoltuk();
            }
            tmp = tmp.getNext();
        }
        return null;
    }

}
