package code;

/*

Bu sınıfın amacı kayıtlı olan kullanıcıların bilgilerini tutmak için tasarlanmıştır.
Diğer sınıflar gibi bir bağlı liste aracılığıyla bu işi yapmaktadır.
Buradaki bulunan bağlı liste elle yazılmıştır.
Ayrıntılı anlatım aşağıda bulunmaktadır.

Not: Kullanıcı için iki tane yetkilendirme bulunmaktadır. Ve bunlar authority de tutulmaktadır.
auothority = 1 ise admin
authority = 0 ise normal kullanıcı anlamıan geliyor.
Admin kullanıcısı programdaki her şeyi görüntüleyip değiştirme hakkı varken
normal kullanıcı ise var olanları sadece görüntüleme ve bilet satın alma işlemlerine sahip.

 */
//Kullanıcının verilini tutmak ve bu verilerle işlem yapmak için gerekli olan değerlerin tanımlandığı sınıf
class User_List {

    //ilk olarak gerekli değişkenleri tanımlıyoruz.
    private int user_id;
    private int authority;
    private String user_name;
    private String mail;
    private String password;
    private User_List next;

    public User_List() {

    }

    //ekleme işlemi için gereken yapılandırıcı metot tanımlanmıştır.
    public User_List(int user_id, int authority, String mail, String user_name, String password) {
        this.user_id = user_id;
        this.authority = authority;
        this.mail = mail;
        this.user_name = user_name;
        this.password = password;
        this.next = null;
    }

    //başka sebeplerle kullanmak için başka bir yapılandırıcı metot tanımlanmıştır.
    public User_List(int user_id, String user_name) {
        this.user_id = user_id;
        this.user_name = user_name;
    }

    //En sonda da gerekli get ve set metotları yazıldı ve bu sınıf tamamlanıp. Bağlı liste için gerekli olan metotların yazıdlığı sınıf aktif olmuştur.
    public int getAuthority() {
        return authority;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public User_List getNext() {
        return next;
    }

    public void setNext(User_List next) {
        this.next = next;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

//Bağlı liste için gerekli olan metotların tanımlandığı sınıf burasıdır.
public class User {

    //ilk olarak ilk ve son değeri tutabilmek adına değişkenleri tanımlıyoruz.
    User_List root;
    User_List iter;

    public User() {

    }

    //daha sonra ekleme işlemi yapabilmek için bu metodu oluşturuyoruz.
    public void add(int user_id, int authority, String mail, String user_name, String password) {
        //ilk başta eklenecek olan değerin bilgilerini parametrede gelen değerler ile oluşturuyoruz.
        User_List tmp = new User_List(user_id, authority, mail, user_name, password);
        //Bu if-else ilk değerin olup olmadığını kontrol edioyr. ilk değer var ise ilk değere atanıyor. yok ise de bir sonraki değere bu eklenecej olan değer ekleniyor.
        if (root == null) {
            root = tmp;
            iter = tmp;
        } else {
            iter.setNext(tmp);
            iter = tmp;
        }
    }

    //bu metot var olan bütün kullanıcıları silmek için tasarlandı.
    public void remove() {
        while (root != null) {
            remove(root.getUser_id());
        }
    }

    //bu metot silinmesini istenilen kullanıcı için tasarlandı.
    public void remove(int user_id) {
        User_List tmp = root, prev = null;
        while (tmp != null) {
            if (tmp.getUser_id() == user_id) {
                if (root.getUser_id() == user_id) {
                    root = root.getNext();
                    break;
                } else {
                    prev.setNext(tmp.getNext());
                    break;
                }
            } else {
                prev = tmp;
                tmp = tmp.getNext();
            }
        }

    }

    //bu metot kaç tane kullanıcı olduğunu dönen metot. 
    public int sayac() {
        int sayac = 0;
        User_List gecici = root;
        while (gecici != null) {
            sayac++;
            gecici = gecici.getNext();
        }
        return sayac;
    }

    //bu metot girilen kullanıcı adını ve şifresini kontrol etmektedir.
    public int control(String user_name, String password) {
        //buradaki user_name olarak gelen parametre kullanıcının kullanıcı adı da olabilir e postası da olabilir. Kontrol ederken bunu da kntrol ediyoruz.

        //ilk olarak root değerini tutan bir değişken tanımlıyoruz.
        User_List c = root;

        //ve tanımladığımız değişken null olana kadar teker teker tarıyoruz.
        while (c != null) {
            //burada ilk yaptığımız şey user_name'in döngüde dönen c nesnesinin user_name i yada mail'i olup olmadığını kontrol ediyoruz sonra çıkan cevapla da c'nin password'u parametre olarak gelen password ile uyuşuyormu ona bakıyrouz. 
            if (((c.getUser_name().equals(user_name)) || (c.getMail().equals(user_name))) && (c.getPassword().equals(password))) {
                //bu if'e girmiş ise kullanıcının giridği değerde bir kullanıcı vardır demek oluyor ve giriş yapan kullanıcının id'sini döndrüüyoruz.
                return c.getUser_id();
            } else {
                //eğer daha yukarıdaki if'e girmemiş ise c değerini bir sonraki değer olarak ilerletiyoruz.
                c = c.getNext();
            }
        }
        //eğer döngünün içindeki if'e girmemiş ise kullanıcının verileri hatalı anlamına geliyor bu sebeple -1 dönüyoruz.
        return -1;
    }

    //Bu metot kullanıcının yetkisini göndermektedir.
    public int authority(int user_id, User user_list) {
        int authority = 0;
        User_List tmp = user_list.root;
        while (tmp != null) {
            if (tmp.getUser_id() == user_id) {
                return tmp.getAuthority();
            }
            tmp = tmp.getNext();
        }
        return -1;

    }

    //bu metot kullanıcı üye olurken aynı adla başka bir kullanıcının üye olup olmadığını araştırıyor. Eğer false dönerse girilen kullanıcı adının daha önce alındığı anlamıan geliyor. True dönerse de kullanıcı adı alınabilir anlamına geliyor.
    public boolean search_name(String user_name) {
        //ilk başta bütün değerleri incelemek için bir değişken oluşturuyoruz.
        User_List sea = root;

        //yukarıda tanımaldığımız değişkeni bir döngü içerisine alarak bütün değerleri teker teker kontrol ediyoruz.
        while (sea != null) {
            //buradaki if-else daha önceden bu kullanıcı adı ile bir kullanıcının olup olmadığını test ediyor.
            if (sea.getUser_name().equals(user_name)) {
                //bu if'e girmiş ise kullanıcı adının daha önceden alıdığı anlamıan geliyor. ve false dönüyoruz.
                return false;
            } else {
                //yukarıdaki if'e girmemiş ise şuana kadar kontrol ettiğimiz kullanıcılarda böyle bir kullanıcı adı alnmadığı anlamına geliyor ve kontrol etmek için bir sonraki değeri atıyroruz.
                sea = sea.getNext();
            }
        }

        //eğer progrma döngünün içindeki if'e hiç girmemiş ise bu kullanıcı adının daha önce hiç alınmadsığı anlamına gelmektedir.
        return true;
    }

    //bu metot kullanıcı üye olurken aynı mail ile başka bir kullanıcının üye olup olmadığını araştırıyor. Eğer false dönerse girilen mailin daha önce alındığı anlamına geliyor. True dönerse de mail alınabilir anlamına geliyor.
    public boolean search_mail(String mail) {
        //ilk başta bütün değerleri incelemek için bir değişken oluşturuyoruz.
        User_List sea = root;
        //yukarıda tanımaldığımız değişkeni bir döngü içerisine alarak bütün değerleri teker teker kontrol ediyoruz.
        while (sea != null) {
            //buradaki if-else daha önceden bu mail ile bir kullanıcının olup olmadığını test ediyor.
            if (sea.getMail().equals(mail)) {
                //bu if'e girmiş ise mailin daha önceden alıdığı anlamıan geliyor. ve false dönüyoruz.
                return false;
            } else {
                //yukarıdaki if'e girmemiş ise şuana kadar kontrol ettiğimiz kullanıcılarda böyle bir mail  alnmadığı anlamına geliyor ve kontrol etmek için bir sonraki değeri atıyroruz.
                sea = sea.getNext();
            }
        }
        //eğer progrma döngünün içindeki if'e hiç girmemiş ise bu mailin daha önce hiç alınmadsığı anlamına gelmektedir.
        return true;
    }

    //bu metot ile istenilen kullanıcının user_name bilgisi dönülmektedir.
    public String al_user_name(int user_id, User user) {
        User_List tmp = user.root;
        while (tmp != null) {
            if (tmp.getUser_id() == user_id) {
                return tmp.getUser_name();
            } else {
                tmp = tmp.getNext();
            }
        }
        return null;
    }

    //bu metot ile istenilen kullanıcının user_mail bilgisi dönmektedir.
    public String _al_user_mail(int user_id, User user) {
        User_List tmp = user.root;

        while (tmp != null) {
            if (tmp.getUser_id() == user_id) {
                return tmp.getMail();
            } else {
                tmp = tmp.getNext();
            }
        }

        return null;
    }

    //bu metot ile istenilne kullanıcının password bilgisi dönmektedir.
    public String _al_user_password(int user_id, User user) {
        User_List tmp = user.root;

        while (tmp != null) {
            if (tmp.getUser_id() == user_id) {
                return tmp.getPassword();
            } else {
                tmp = tmp.getNext();
            }
        }

        return null;
    }

    //bu metot ile kullanıcı kendi bilgilerini güncelleywbilmektedir.
    public int guncelle(int user_id, String name, String mail, String password, User user_list) {
        User_List tmp = user_list.root;
        Dosya dosya_islemleri = new Dosya();
        int control = -1;
        while (tmp != null) {
            if (tmp.getUser_id() == user_id) {
                control = 1;
                tmp.setUser_id(user_id);
                tmp.setUser_name(name);
                tmp.setMail(mail);
                tmp.setPassword(password);
                tmp = null;
            } else {
                tmp = tmp.getNext();
            }
        }
        dosya_islemleri.user_dosya_yaz(user_list);

        return control;

    }

    //bu metot ile istenilen kullanıcın password bilgisi dönmektedir. Yukarıdaki metotdan farkı burada mail ve kullanıcı adına göre dönerken o metotda id'ye göre dönmektedir.
    public String password(String user_name, String user_mail) {
        User_List tmp = root;
        while (tmp != null) {
            if ((tmp.getUser_name().equals(user_name)) && (tmp.getMail().equals(user_mail))) {
                return tmp.getPassword();
            } else {
                tmp = tmp.getNext();
            }
        }
        return null;
    }

}
