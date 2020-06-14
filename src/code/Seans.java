package code;

/*

Bu sınıfın amacı hangi salonda hangi saatlerde hangi filmin oynadığını tutmaktaır.
Ve bu verileri bir dosyada tutmaktadir.
Eğer yeni bir salon eklenirse seans dosyasına da yeni bir satır eklenecektir.
Eğer var olan salon silinecekse o salon için ayrılmış satıda silinecektir.
Ayrıntılı Anlatım aşağıda bulunmaktadır.

Not: 5 Tane seans saati bulunmaktadır Bunlar:
10.00-12.00 (id'si 1 olarak belirlendi)
12.30-13.30 (id'si 2 olarak belirlendi)
14.00-16.00 (id'si 3 olarak belirlendi)
16.30-18.30 (id'si 4 oalrak belrilendi)
19.00-21.00 (id'si 5 olarak belirlendi)
saatları arasındadır.
 */

//ilk başta bağlı liste için tutulması gereken verileri tutmak ve bunları değiştirmek için bir sınıf oluşturuyoruz..
class List_Seans {
    
    //gerekli olan değişkenkeri tanımlıyoruz.
    private int salon_id;
    private int film1_id;
    private int film2_id;
    private int film3_id;
    private int film4_id;
    private int film5_id;
    private List_Seans next;

    public List_Seans() {

    }
    
    //bu yeni bir değer oluşturmak için kullanılan yapılandırıcı metotdur.
    public List_Seans(int salon_id, int film1_id, int film2_id, int film3_id, int film4_id, int film5_id) {
        this.salon_id = salon_id;
        this.film1_id = film1_id;
        this.film2_id = film2_id;
        this.film3_id = film3_id;
        this.film4_id = film4_id;
        this.film5_id = film5_id;
        this.next = null;
    }

    //bu ise var olan bir değeri değiştirmek için yazılan metotdur. Parametre olarak gelen seans'a istenilen film ve salon yazılmaktadır.
    public List_Seans(int seans_id, int salon_id, int film_id) {
        switch (seans_id) {
            case 1:
                this.film1_id = film_id;
                this.salon_id = salon_id;
                this.next = null;
                break;
            case 2:
                this.film2_id = film_id;
                this.salon_id = salon_id;
                this.next = null;
                break;
            case 3:
                this.film3_id = film_id;
                this.salon_id = salon_id;
                this.next = null;
                break;
            case 4:
                this.film4_id = film_id;
                this.salon_id = salon_id;
                this.next = null;
                break;
            case 5:
                this.film5_id = film_id;
                this.salon_id = salon_id;
                this.next = null;
                break;
            default:
                System.out.println("Hata(List_Seans(int seans_id, int salon_id, int film_id))");
                System.out.println(seans_id);
                break;
        }

    }
    
    //Aşağıda ise gerekli get ve set metotları tanımlandı
    
    public int getSalon_id() {
        return salon_id;
    }

    public void setSalon_id(int salon_id) {
        this.salon_id = salon_id;
    }

    public int getFilm1_id() {
        return film1_id;
    }

    public void setFilm1_id(int film1_id) {
        this.film1_id = film1_id;
    }

    public int getFilm2_id() {
        return film2_id;
    }

    public void setFilm2_id(int film2_id) {
        this.film2_id = film2_id;
    }

    public int getFilm3_id() {
        return film3_id;
    }

    public void setFilm3_id(int film3_id) {
        this.film3_id = film3_id;
    }

    public int getFilm4_id() {
        return film4_id;
    }

    public void setFilm4_id(int film4_id) {
        this.film4_id = film4_id;
    }

    public int getFilm5_id() {
        return film5_id;
    }

    public void setFilm5_id(int film5_id) {
        this.film5_id = film5_id;
    }

    public List_Seans getNext() {
        return next;
    }

    public void setNext(List_Seans next) {
        this.next = next;
    }

}

//bağlı listede ekleme, silme veya buna benzer işlemler için gerekli olan metotların bulunduğu sınıf
public class Seans {

    //ilk başta ilk ve son değeri tutabilemk adına gerekli değişkenleri tanımlıyoruz.
    List_Seans root;
    List_Seans iter;

    //Bu metot iki amaçla yazıldı: Yeni bir salon eklendiğinde seans olmayacağı için bütün seanslara -1 yazdırılmak amacıyla yazıldı. 2.Amaç ise dosyadan yeni okuduğumuz zaman bir salon da seans var mı? Yok mu görmek için yazıldı.
    public void add(int salon_id, int film1_id, int film2_id, int film3_id, int film4_id, int film5_id) {
        List_Seans tmp = new List_Seans(salon_id, film1_id, film2_id, film3_id, film4_id, film5_id);
        if (root == null) {
            root = tmp;
            iter = tmp;
        } else {
            iter.setNext(tmp);
            iter = tmp;
        }

    }

    //Bu metot bir amaçla yazıldı. Var olan salona yeni bir senas ekleyebilmek için
    public void add(int salon_id, int seans_id, int film_id) {
        List_Seans tmp = root;
        int i = 1;
        while ((tmp != null) && (i == 1)) {
            if (salon_id == tmp.getSalon_id()) {
                i = 0;
            } else {
                tmp = tmp.getNext();
            }
        }
        if ((tmp == null) && (tmp.getSalon_id() != salon_id)) {
            System.out.println("Hata(code.Seans.add(int, int, int))");
        } else {
            switch (seans_id) {
                case 1:
                    tmp.setFilm1_id(film_id);
                    break;
                case 2:
                    tmp.setFilm2_id(film_id);
                    break;
                case 3:
                    tmp.setFilm3_id(film_id);
                    break;
                case 4:
                    tmp.setFilm4_id(film_id);
                    break;
                case 5:
                    tmp.setFilm5_id(film_id);
                    break;
                default:
                    System.out.println("Hata(code.Seans.add(int, int, int)).switch_case.default");
                    System.out.println(seans_id);
                    break;
            }
        }
    }

    // Var olan bütün seansları silmek için bu metot yazıldı
    public void remove() {
        List_Seans tmp = root;
        while (tmp != null) {
            tmp.setFilm1_id(-1);
            tmp.setFilm2_id(-1);
            tmp.setFilm3_id(-1);
            tmp.setFilm4_id(-1);
            tmp.setFilm5_id(-1);
            tmp = tmp.getNext();
        }
    }

    //Var olan bir salonu silmek için.
    public void remove2(int salon_id) {
        List_Seans tmp = root, prev = root;
        while (tmp != null) {
            if (tmp.getSalon_id() == salon_id) {
                if (root.getSalon_id() == salon_id) {
                    root = root.getNext();
                } else {
                    prev.setNext(tmp.getNext());
                }

            }
            prev = tmp;
            tmp = tmp.getNext();
        }

        tmp = root;
        while (tmp != null) {
            if (tmp.getSalon_id() > salon_id) {
                tmp.setSalon_id(tmp.getSalon_id() - 1);
            }
            tmp = tmp.getNext();
        }
    }

    //Var olan bir filmin bütün seanslarını silmek için tasarlandı.
    public void remove(int film_id) {
        List_Seans tmp = root;
        //Neden else if değilde if? Çünkü bir seansta birden fazla aynı film oynuyor olabilir. Eğer else if yaparsak onları atlayaıbiliriz.
        while (tmp != null) {
            if (tmp.getFilm1_id() == film_id) {
                tmp.setFilm1_id(-1);
            }
            if (tmp.getFilm2_id() == film_id) {
                tmp.setFilm2_id(-1);
            }
            if (tmp.getFilm3_id() == film_id) {
                tmp.setFilm3_id(-1);
            }
            if (tmp.getFilm4_id() == film_id) {
                tmp.setFilm4_id(-1);
            }
            if (tmp.getFilm5_id() == film_id) {
                tmp.setFilm5_id(-1);
            }
            tmp = tmp.getNext();
        }
    }

    //Bu metodun amacı istenilen salonstan istenilen seansı silmek
    public void remove(int salon_id, int seans_id) {

        List_Seans tmp = root;

        while (tmp != null) {

            if (tmp.getSalon_id() == salon_id) {

                switch (seans_id) {
                    case 1:
                        tmp.setFilm1_id(-1);

                        tmp = null;
                        break;
                    case 2:

                        tmp.setFilm2_id(-1);
                        tmp = null;
                        break;
                    case 3:

                        tmp.setFilm3_id(-1);
                        tmp = null;
                        break;
                    case 4:

                        tmp.setFilm4_id(-1);
                        tmp = null;
                        break;
                    case 5:

                        tmp.setFilm5_id(-1);
                        tmp = null;
                        break;
                    default:

                        System.out.println("Bir hata meydana geldi(remove(int, int))");
                        break;
                }
            } else {

                tmp = tmp.getNext();
            }
        }

    }

    //bu metot silme işlemi yapıldıktan sonra verilerim karışmaması için id'leri yenidnen onarıyoruz.
    public void id_onarım(int salon_id) {
        int sayac = sayac();
        int i;
        List_Seans tmp = root;
        if (salon_id == 1) {
            i = 1;
            while (tmp != null) {
                tmp.setSalon_id(i);
                i++;
            }
        } else {
            while (tmp.getSalon_id() == salon_id - 1) {
                tmp = tmp.getNext();
            }
            tmp = tmp.getNext();
            for (i = salon_id; i <= sayac; i++) {
                tmp.setSalon_id(i);
                tmp = tmp.getNext();
            }
        }

    }
    
    //bu metot bağlı listede kaç tane veri olduğunu vermektedir. Sinema salonu eklendiğinde seans'a eklendiği; silindiğinde ise seans'tan da silindiği için bu yanı zamanda kaç tane sinema salonu olduğunuda göstermektedir.
    public int sayac() {
        List_Seans tmp = root;
        int sayac = 0;
        while (tmp != null) {
            sayac++;
            tmp = tmp.getNext();
        }

        return sayac;
    }
    
    //İstenilen salon ve seansta hangi film oynuyor ise onun id'sini döndüren metot.
    public int film_id(int salon_id, int seans_id) {
        List_Seans tmp = root;
        while (tmp != null) {
            if ((tmp.getSalon_id() == salon_id)) {
                switch (seans_id) {
                    case 1:
                        return tmp.getFilm1_id();
                    case 2:
                        return tmp.getFilm2_id();
                    case 3:
                        return tmp.getFilm3_id();
                    case 4:
                        return tmp.getFilm4_id();
                    case 5:
                        return tmp.getFilm5_id();
                    default:
                        return -1;
                }
            } else {
                tmp = tmp.getNext();
            }
        }
        return -1;
    }
    
    //Bu metot seans'ın id'sine göre saatlerini döndürmektedir.
    public String Seans_Yolla(int seans_id) {

        switch (seans_id) {
            case 1:
                return "10.00-12.00";
            case 2:
                return "12.30-13.30";
            case 3:
                return "14.00-16.00";
            case 4:
                return "16.30-18.30";
            case 5:
                return "19.00-21.00";
            default:
                break;
        }

        return null;
    }
    
    //bu metot seansın saatlerine göre seans'ın id'sini döndürmektedir.
    public int Seans_id_Yolla(String saat) {
        switch (saat) {
            case "10.00-12.00":
                return 1;
            case "12.30-13.30":
                return 2;
            case "14.00-16.00":
                return 3;
            case "16.30-18.30":
                return 4;
            case "19.00-21.00":
                return 5;
            default:
                return -1;

        }

    }
    
    //bu metot istenilen film'in var olan bütün seanslarını Goruntuleme sınıfındaki bağlı listeye ekleyip bunu döndürmektedir.
    public Goruntuleme goruntulenecek(Goruntuleme goruntu, int film_id) {
        Dosya dosya_islemleri = new Dosya();

        Seans sea = new Seans();
        dosya_islemleri.seans_dosya_oku(sea);

        List_Seans tmp = sea.root;

        while (tmp != null) {
            if (tmp.getFilm1_id() == film_id) {
                goruntu.add(goruntu.sayac() + 1, tmp.getSalon_id(), 1);
            } else if (tmp.getFilm2_id() == film_id) {
                goruntu.add(goruntu.sayac() + 1, tmp.getSalon_id(), 2);
            } else if (tmp.getFilm3_id() == film_id) {
                goruntu.add(goruntu.sayac() + 1, tmp.getSalon_id(), 3);
            } else if (tmp.getFilm4_id() == film_id) {
                goruntu.add(goruntu.sayac() + 1, tmp.getSalon_id(), 4);
            } else if (tmp.getFilm5_id() == film_id) {
                goruntu.add(goruntu.sayac() + 1, tmp.getSalon_id(), 5);
            }
            tmp = tmp.getNext();
        }

        return goruntu;
    }
    
    //Eğer bir film silinirse id'si ondan büyük olan filmlerin id'si bir düşeceği için seans işleminde de bunu gerçekleştiriyoruz. Yani silinen filmden yüksek id'ye sahip olan bütün filmlerin id'Sini bir azaltıyoruz.
    public void dusur(int film_id) {
        List_Seans tmp = root;
        while (tmp != null) {
            if (tmp.getFilm1_id() > film_id) {
                tmp.setFilm1_id(tmp.getFilm1_id() - 1);
            }
            if (tmp.getFilm2_id() > film_id) {
                tmp.setFilm2_id(tmp.getFilm2_id() - 1);
            }
            if (tmp.getFilm3_id() > film_id) {
                tmp.setFilm3_id(tmp.getFilm3_id() - 1);
            }
            if (tmp.getFilm4_id() > film_id) {
                tmp.setFilm4_id(tmp.getFilm4_id() - 1);
            }
            if (tmp.getFilm5_id() > film_id) {
                tmp.setFilm5_id(tmp.getFilm5_id() - 1);
            }
            tmp = tmp.getNext();
        }
    }
}
