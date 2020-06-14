
package Controller;

import code.*;
import code_ui.Center;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;


public class app_standart_userController extends Center implements Initializable {

    /*
    Bunun devamı nerede ve neden Center diye bir şeyi extends ediyor?
    
    Bunun sebebi appController ve app_stanadart_userController sınıflarının ortak noktalarını kalıtım aracılığıyla başka sınıfta tanımlamamızdır..
    
    Hem appController da hem de app_standart_userController da 7.000'den fazla ortak kod bulunmaktadır.
    Bu sebeple ikisinn ortak noktalarını bir sınıfta topladık ve ikisine de extends ettik.
     */
    
    //Bu metod bu sınıfa özgü olan pane'leri tanımlıyor (appController ya da başka yerde kullanılmayan)
    @FXML
    private Pane bilet_satin_al_pane, bilet_satin_al_uyari_pane, bilet_satin_al_filmi_sec_pane, bilet_satin_al_seans_sec_pane, bilet_satin_al_koltuk_sec_pane;

    @FXML
    private Label bilet_satin_al_uyari_mesaj, filmi_sec_uyari_mesaj, seans_sec_uyari_mesaj, bilet_satin_al_film_id, bilet_satin_al_salon_id, bilet_satin_al_seans_id, bilet_satin_al_buton_bilet_satin_al_uyari_mesaj;

    @FXML
    private ComboBox<String> filmleri_goster, seanslari_goster;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    //Bu metot sol tarafta bulunan Ana Sayfa yazısına tıklandığında ne yapılacağını söylüyor. Anasayfa pane'i dışında bütün pane'leri kapatıyor.
    @FXML
    private void home(MouseEvent event) {
        home_page.setVisible(true);

        settings_pane.setVisible(false);
        eski_film_pane.setVisible(false);
        kampanyalar_pane.setVisible(false);
        haberler_pane.setVisible(false);
        bilet_satin_al_pane.setVisible(false);
        sinema_salonlari_pane.setVisible(false);
        vizyondaki_filmler_pane.setVisible(false);

    }

    @FXML
    private void settings(MouseEvent event) {
        //Bu metodun asıl amacı. Kullanıcının istediği bir bilgisini istediği zaman değiştirebilmektir. Bu metot ilk başta Kullanıcının verilerini almaya yarıyor. Ve belirlenen butona basıldığı takdirde var olan verileri güncelliyor.

        //Bu 1 satır. Dosyadan veri çekmek için Dosya sınıfına ait bir nesne oluşturuyor
        Dosya dosya_islemleri = new Dosya();

        //Bu bir satır hangi kullanıcının giriş yaptığını Öğreniyor.Her giriş yapıldığıdna giriş yapan kullanıcın id'leri bilgi.txt dosyasına yazıdırır. (Tabi üstüne yazdırılır. Daha önce giriş yapan kişilerin bilgileriyle karışmaması açısından) Daha sonra kullanıcı'nın id'sine ihtiyaç duyulduğu zaman bu dosyaya gidip kullanıcının id'si alınır. Ve ona göre işlem yapılır.  
        int user_id = dosya_islemleri.bilgi_dosya_oku();

        //Var olan kullanıcıların listesini dosyadan verileri çekip bu bağlı listeye aktarıyoruz.
        User user_list = new User();
        user_list = dosya_islemleri.user_dosya_oku(user_list);

        bilet_satin_al_pane.setVisible(false);
        sinema_salonlari_pane.setVisible(false);
        eski_film_pane.setVisible(false);
        haberler_pane.setVisible(false);
        vizyondaki_filmler_pane.setVisible(false);
        settings_pane.setVisible(true);

        //Giriş yapan kullanıcının mail,kullanıcı adı ve şifresini alıp her birini ayrı olarak bir Stringe kaydediyoruz.
        String mail = user_list._al_user_mail(user_id, user_list);
        String name = user_list.al_user_name(user_id, user_list);
        String password = user_list._al_user_password(user_id, user_list);

        // Az önce kullanıcının verilerini kaydettiğimiz Stringleri yazılması gereken TextField'ların ve PasswordField'ın içine yazıyoruz.
        user_name.setText(name);
        user_mail.setText(mail);
        user_password.setText(password);
    }

    //Sol tarafta bulunan Vizyondaki Filmler butonuna tıklandığında ne olacağını söylüyor
    @FXML
    private void vizyondai_filmler(ActionEvent event) {
        //vizyondaki filmler'e ait olan pane dışında kalan bütün ana pane'leri kapatıyor.
        bilet_satin_al_pane.setVisible(false);
        sinema_salonlari_pane.setVisible(false);
        settings_pane.setVisible(false);
        kampanyalar_pane.setVisible(false);
        haberler_pane.setVisible(false);
        eski_film_pane.setVisible(false);
        vizyondaki_filmler_pane.setVisible(true);

        //bu satır vizyonda var olan filmleri tablo şeklinde ekrana yazdırma görevini yapıyor. Bu metot code_ui paketinin içindeki Center .java dosyasında ki filmler için ayrılmış bölümde  bulunuyor.
        //Bu metotda 1 yollanırsa vizyondaki filmleri, 0 yollanırsa eski filmleri tablo olarak gösteriyor.
        filmler_table(1);
    }

    //Sol tarafta bulunan Eski Filmler butonuna tıklandığında ne olacağını söylüyor.
    @FXML
    private void eski_filmler(ActionEvent event) {
        //bu satırlar eski filmlere ait olan pane dışında kalan diğer pane'leri kapatıyor.
        bilet_satin_al_pane.setVisible(false);
        sinema_salonlari_pane.setVisible(false);
        settings_pane.setVisible(false);
        kampanyalar_pane.setVisible(false);
        haberler_pane.setVisible(false);
        vizyondaki_filmler_pane.setVisible(false);
        eski_film_pane.setVisible(true);

        //Bu satır ise var olan eski filmleri tablo şeklinde göstermek için. Bunun metodunun da code_ui paketinin içined bulunan Center 'ın içinde olduğunu belirtelim.
        //bu metoda 0 yollandığı için eski filmleri listeliyor. 1 yollandığı zamanlarda vizyondaki filmleri listeliyor.
        filmler_table(0);
    }

    //Sol tarafta bulunan kampanyalar butonuna tıklandığında ne olacağını söylüyor.
    @FXML
    private void kampanyalar(ActionEvent event) {

        //bu satırlar kampanyalar pane'i dışında kalan bütün ana pane'leri kapatıyor.
        bilet_satin_al_pane.setVisible(false);
        sinema_salonlari_pane.setVisible(false);
        settings_pane.setVisible(false);
        eski_film_pane.setVisible(false);
        haberler_pane.setVisible(false);
        vizyondaki_filmler_pane.setVisible(false);
        kampanyalar_pane.setVisible(true);

        //bu metot kampanyaları tablo şeklinde sıralamaya yarıyor. metoda 1 yollanırsa kampanyaları, 0 yollanırsa haberleri listeliyor.
        duyuru_table(1);
    }
    
    //Sol tarafta bulunan haberler butonuna tıklandığıdna ne olacağını söylüyor.
    @FXML
    private void haberler(ActionEvent event) {
        
        //bu satırlar haberler pane'i dışında kalan bütün ana pane'leri kapatıyor.
        bilet_satin_al_pane.setVisible(false);
        sinema_salonlari_pane.setVisible(false);
        settings_pane.setVisible(false);
        eski_film_pane.setVisible(false);
        kampanyalar_pane.setVisible(false);
        vizyondaki_filmler_pane.setVisible(false);
        haberler_pane.setVisible(true);
        
        //bu metot haberleri tablo şeklinde sıralamaya yarıyor. metoda 1 yollanırsa kampanyaları, 0 yollanırsa haberleri listeliyor.
        duyuru_table(0);
    }

    //Sol tarafta bulunan Bilet satın al metodu tarafıntan aktif hale getirilen metotdur.
    @FXML
    private void bilet_satin_al(ActionEvent event) {

        //Bu satırlar bilet satin al pane'i haricinde kalan bütün pane'leri kapatıyor.
        home_page.setVisible(false);
        settings_pane.setVisible(false);
        eski_film_pane.setVisible(false);
        kampanyalar_pane.setVisible(false);
        haberler_pane.setVisible(false);
        bilet_satin_al_pane.setVisible(false);
        sinema_salonlari_pane.setVisible(false);
        vizyondaki_filmler_pane.setVisible(false);

        bilet_satin_al_pane.setVisible(true);
        
        

        //Bu 3 satır, dosyaya daha önceden yazılmış vizyondaki filmleri okuyor. (Ekrana yazmak için)
        Vizyondaki_Filmler vizyonda = new Vizyondaki_Filmler();

        Dosya dosya_islemleri = new Dosya();

        dosya_islemleri.filmler_dosya_oku(vizyonda, 1);
        
        //Bu satır ise daha önceden bilet satın alma işlemi sırasında progrma kapatılmış veya nei bir sayfa ya da seans'a geçilmiş ise ve burada bir kalıntı var ise bunu silmektedir.(yesil_olan dosyası bilet satın lama işlemidne seçilen koltukları tutmaktadır.)
        dosya_islemleri.yesil_olan_dosya_hepsini_sil();

        //Eğer herhangi bir vizyonda film yoksa ekrana vizyonda herhangi bir filmin olmadığını yazıyor. Eğer vizyonda film varsa da bu filmleri gösteriyor.
        if (vizyonda.bos()) {
            /*
            Bu if içine girmiş ise vizyonda herhangi bir filmin olmadığını gösteriyor ve ekrana gerekli uyarıyı yazmak için açtığımız pane dışında kalan bütün
            bilet al pane'i içinde bulunan pane'leri kapatıyorlar. ve uyari_pane'in içinde bulunan labele ne sıkıntı olduğu söyleniyor.
             */

            bilet_satin_al_uyari_pane.setVisible(true);
            
            bilet_satin_al_filmi_sec_pane.setVisible(false);
            bilet_satin_al_seans_sec_pane.setVisible(false);
            bilet_satin_al_koltuk_sec_pane.setVisible(false);

            bilet_satin_al_uyari_mesaj.setText("Maalesef vizyonda filmimiz bulunmamaktadir.");

        } else {

            /*
            vizyonda film varsa bu else giriyor. Burada yaptığı iş ise filmi seçmek için combo box'ın bulunduğu pane'i aktif hale getiriyor.
            diğer bilet_satin_al_pane'in altında bulunan pane'leri ise pasif hale getiriyor.
            en sonda da combo box'ın içine vizyondaki filmleri göstermek adına bir metoda yolluyoruz. (bu metoda 0 giderse eski filmleri 1 giderse vizyondaki filmleri gösteriyor.)
             */
            bilet_satin_al_filmi_sec_pane.setVisible(true);

            bilet_satin_al_uyari_pane.setVisible(false);
            bilet_satin_al_seans_sec_pane.setVisible(false);
            bilet_satin_al_koltuk_sec_pane.setVisible(false);

            filmleri_getir_combo(filmleri_goster, 1);

        }
    }

    //bu metot bilet_Satin_al içinde bulunan filmi seç butonuna tıklandığında ne yapılacağını belirtiyor.
    @FXML
    private void filmi_sec(ActionEvent event) {

        //kullanıcıdan istenilen filmi bir String'e aktarıyoruz. 
        String secilen_film = filmleri_goster.getValue();

        //eğer combo box boş ise yani kullanıcı bir değer seçmemiş ise ekrana uyarı yazdırıyor ve diğer işlemleri yapmıyoruz. (kullanıcı değer seçip butona tıklayana kadar)
        if (secilen_film == null) {

            filmi_sec_uyari_mesaj.setText("Lütfen Bir Film Seçiniz.");

        } else {
            //Bu else içine girilmiş ise kullanıcının bir film seçtiği anlamına geliyor ve ona göre işlemler yapmaya başlıyoruz.

            //bu değişken aşağıda for içinde hangi filmin seçildiğini bulmamız konusunda yardım ediyor.
            int j = 0;

            //bu 4 satır dosya okuma işlemi aracılığıyla vizyondaki filmlerin sayısını öğreniyor.
            Vizyondaki_Filmler vizyonda = new Vizyondaki_Filmler();
            Dosya dosya_islemleri = new Dosya();
            dosya_islemleri.filmler_dosya_oku(vizyonda, 1);
            int sayac = vizyonda.sayac();

            for (int i = 1; (i <= sayac) && (j == 0); i++) {
                //bu for aracılığıyla az önce dosyadan okunan her bir filmi teker teker kontrol ediyor. Hangi filmin seçildiğini anlamak için
                String Filmler = String.valueOf(i) + " | " + vizyonda.Date(i) + " | " + vizyonda.Title(i);

                if (secilen_film.equals(Filmler)) {
                    //eğer seçilen filmi bulduk ise az önce yukarıda tanımlanan j değerine bu filmin id'si atanıyor ve for döngüsünden çıkılıyor. (for döngüsündeki şartlardan dolayı)
                    j = i;

                }
            }
            
            /*
            bu 4 satır dosyadan var olan bütün seansları okuyor.
            ve istenilen şeyleri görüntülemek için oluşturulan gorunut bağlı listesine seçilen filmin seansları aktarılıyor.
            goruntu bağlı listesini dolduran metot code paketinin içidneki Seans sınıfında bulunmaktadır.
            */
            Seans sea = new Seans();
            dosya_islemleri.seans_dosya_oku(sea);     
            Goruntuleme goruntu = new Goruntuleme();
            sea.goruntulenecek(goruntu, j);

            if (goruntu.bos()) {
                /*
                Eğer seçilen filme herhangi bir seans eklenmemiş ise bu if içine giriyor ve 
                kullanıcıya bu film için herhangi bir seansın bulunamdığını bilet al uyarı pane'in 
                içindeki uyarı mesajına yazıyor.
                */
                bilet_satin_al_uyari_pane.setVisible(true);

                bilet_satin_al_seans_sec_pane.setVisible(false);

                bilet_satin_al_koltuk_sec_pane.setVisible(false);

                bilet_satin_al_uyari_mesaj.setText("Maalesef bu film için seans bulunamadı.");

            } else {
                /*
                goruntu bağlı listesi boş değil ise bu if'e giriyor ve seçilmiş film için olan bütün seasnları combo box içine yazıyor.(seasnlari_goster motdu aracılığıyla.)
                
                Aynı zamanda bilgi bozukluluğu olmaması açısından şuanki pane'lerde olamayan bir sonraki sayfada çıkacak olan (koltuk seçme sayfasında) 
                seçilen filmin id'si kısmına filmin id'si yazılıyor.
                */
                bilet_satin_al_seans_sec_pane.setVisible(true);

                bilet_satin_al_uyari_pane.setVisible(false);
                bilet_satin_al_koltuk_sec_pane.setVisible(false);

                seanslari_goster(goruntu, j);

                String film_id = String.valueOf(j);
                bilet_satin_al_film_id.setText(film_id);

            }

        }
    }
    
    //Bu metot seans seç butonuna tıklandığında ne yapılacağını gösteriyor.
    @FXML
    private void seans_sec(ActionEvent event) {
        
        //ilk olarak kullanıcının seçtiği seansı bir string'e aktarıyoruz.
        String secilen = seanslari_goster.getValue();
        
        if (secilen == null) {
            //Eğer bu string boşsa kullanıcı herhangi bir değer seçmediği anlamına geliyor ve uyarı mesajı olarak film seçmesi gerektiğini söylüyoruz.
            seans_sec_uyari_mesaj.setText("Lütfen Bir Seans Seçiniz");
        } else {
            //Kullanıcı filmi seçmiş ise işlemlerimize devam ediyoruz.
            
            //Bu 5 satırda dosyadan var olan seansları ve var olan salonları alıyoruz ve bunları bir bağlı listeye aktarıyoruz.
            Seans sea = new Seans();
            Sinema_Salonlari salon = new Sinema_Salonlari();
            Dosya dosya_islemleri = new Dosya();
            dosya_islemleri.sinema_salonlari_dosya_oku(salon);
            dosya_islemleri.seans_dosya_oku(sea);

            //daha önce filmin id'sini yazdırdığımız labeldeki(filmi seçerken yazdırdık) yazıyı bir integer değer eatıyoruz. 
            int film_id = Integer.valueOf(bilet_satin_al_film_id.getText());

            //seçilen film için var olan seansları goruntu bağlı listesine yeniden aktarıyoruz(daha önce filmi seç metodunda aktarmıiştık.) daha sonra ise kaç tane görüntülenen değerin olduğunu hesaplayıp farklı bir integer değere atıyoruz.
            Goruntuleme goruntu = new Goruntuleme();
            sea.goruntulenecek(goruntu, film_id);
            int sayac = goruntu.sayac();
            
            //hangi seansı seçtiğimize yardımcı olmak açısından bu j değişkeni tanımlanıyor
            int j = 0;

            //bu döngü ile görüntülenen her değeri teker teker seçilen değer ile karşılaştırıp kullanıcının hangi değerleri seçtiğini buluyoruz.
            for (int i = 1; (i <= sayac) && (j == 0); i++) {
                String goruntulenecek = String.valueOf(i) + " | " + "Salon adı: " + salon.Salon_Name(goruntu.salon_id(i)) + " | " + sea.Seans_Yolla(goruntu.seans_id(i)) + " | " + "Film ID:" + film_id;
                //teker teker hangi seansın seçildiğini hesaplıyoruz.
                if (secilen.equals(goruntulenecek)) {
                    //seçilen seansı bulduk ise goruntu bağlı listesindeki kaçıncı değer olduğunu j integer değerine atıyoruz ve döngüden çıkıyoruz(for döngüsünün şartlarından dolayı)
                    j = i;
                }
            }
            
            //bu 2 satır seçilen filmin ve seansın id'sini ayrı bir integer ile tutuyor.
            int salon_id = goruntu.salon_id(j);
            int seans_id = goruntu.seans_id(j);

            //bu bundan sonra açılacak olan pane'e salon id'sini ve seans id'sini yazdırıyor.
            bilet_satin_al_salon_id.setText(String.valueOf(salon_id));
            bilet_satin_al_seans_id.setText(String.valueOf(seans_id));
            
            //bu seçilen seans ve film hangi salonda oynuyorsa o salonda kaç tane koltuk olduğunu hesaplıyor.
            int koltuk_sayisi = salon.Koltuk_Sayisi(salon_id);
            
            //bilet alma kısmında bir sonraki pane aktif hale geliyor. ve bu pane de ise koltuk alma işlevi aktif hale geliyor. bunlar dışında kalan har pane kapatılıyor.
            bilet_satin_al_koltuk_sec_pane.setVisible(true);
            sinema_salonlari_pane.setVisible(true);

            bilet_satin_al_filmi_sec_pane.setVisible(false);
            bilet_satin_al_seans_sec_pane.setVisible(false);
            bilet_satin_al_uyari_pane.setVisible(false);
            
            /*
            bu switch-case koltuk sayısına göre işlev yapıyor. 
            Toplamda 4 tane salon tipimiz var birinci salon 129, ikinci salon 177, üçüncü salon 265, dördüncü salon ise 294 koltuğa sahip
            ve koltuğa göre açılması gereken pane'i açıyoruz ve bir sonraki kademede hangi koltuklar dolu ise onu kırmızı hale getiriyoruz. (Her salon için bazı farklı metotlar var. Bunler Center sınıfının içinde ))
            3. ve son aşamada ise hangi salon tipi açılmışsa o pane hariç diğer pane'lari kapatıyoruz.
            */

            switch (koltuk_sayisi) {
                case 129:

                    salon_bir_pane.setVisible(true);
                    bir_koltuk_dolu(salon_id, seans_id);

                    salon_iki_pane.setVisible(false);
                    salon_uc_pane.setVisible(false);
                    salon_dort_pane.setVisible(false);
                    break;
                case 177:
                    salon_iki_pane.setVisible(true);
                    iki_koltuk_dolu(salon_id, seans_id);

                    salon_bir_pane.setVisible(false);
                    salon_uc_pane.setVisible(false);
                    salon_dort_pane.setVisible(false);
                    break;
                case 265:
                    salon_uc_pane.setVisible(true);
                    uc_koltuk_dolu(salon_id, seans_id);

                    salon_bir_pane.setVisible(false);
                    salon_iki_pane.setVisible(false);
                    salon_dort_pane.setVisible(false);
                    break;
                case 294:
                    salon_dort_pane.setVisible(true);
                    dort_koltuk_dolu(salon_id, seans_id);

                    salon_bir_pane.setVisible(false);
                    salon_iki_pane.setVisible(false);
                    salon_uc_pane.setVisible(false);
                    break;
                default:

                    break;
            }
        }
    }

    //bu buton en son kademede bulunan(koltuk seçme kademesi) bilet al butonuna basıldığında aktif oluyor. 
    @FXML
    private void bilet_satin_al_buton_bilet_satin_al(ActionEvent event) {
        Dosya dosya_islemleri = new Dosya();
        //ilk olarak hangi butonların yeşil olduğunu yazdığımız dosyadaki verileri çekiyoruz.
        yesil_olan yesil = new yesil_olan();
        dosya_islemleri.yesil_olan_dosya_oku(yesil);
        //bu 5 satır dosyadan daha önce satın alınan biletleri ve var olan salonları okuyup bir bağlı listete aktarıyor.
        Sinema_Salonlari salon = new Sinema_Salonlari();
        Bilet_Satin_Al bilet = new Bilet_Satin_Al();
        dosya_islemleri.bilet_satin_al_dosya_oku(bilet);
        dosya_islemleri.sinema_salonlari_dosya_oku(salon);
        //bu üç satır ise daha önce ekrana yazılmış olan user_id,salon_id ve seans_id bilgilerini yeninden çekiyor.
        int user_id = dosya_islemleri.bilgi_dosya_oku();
        int salon_id = Integer.valueOf(bilet_satin_al_salon_id.getText());
        int seans_id = Integer.valueOf(bilet_satin_al_seans_id.getText());
        //bu satır ise seçilen salonda kaç tane koltuk olduğunu tespit ediyor.
        int koltuk_sayisi = salon.Koltuk_Sayisi(salon_id);
        /*
        bu switch-case koltuk sayısına göre işlem yapmaya başlıyor.
        yukarıda bulunan seans_sec metodunda da anlattığımız gibi 4 tane sinema salonu tipimiz var ve bu tipler koltuk sayısına göre belirleniyor.
        kullanıcının seçtiği film ve seansa göre de hangi salon'un gösterileceği tespit ediliyor ve onu gösteriyor.
        
        bu switch-case de;
        1. satırfa kaç tane butonun yeşil olduğunu bir integer değere aktarıyor.
        2. satırda sayac integer değeri ile birlikte bilet_Satin_al dosyasına yazdırmaya yolluyoruz. Sayacı ve yeşil bağlı listesini yollayarak oradaki bütün yeşil butonları dosyaya ekliyoruz. Bu sistem aacılığıyla başkas biri daha aynı seanstan bilet almak isterse yazılan koltukları kırmızı görecek
        3.satırda ise bütün yeşil olan butonlar yazıldı ve bir sonraki satın alım için yeşil olan butonları temizliyoruz. Bir sonraki satın alma işlemi ile bir önce ki karışmaması için bunu yapıyoruz.
        4.satırda ise işlemin başarılı şekilde gerçekleştiğini ekrana yazıyor.
        */
        switch (koltuk_sayisi) {
            case 129:
                int sayac = yesil.sayac();
                dosya_islemleri.bilet_satin_al_dosya_ekle(user_id, salon_id, seans_id, yesil, sayac);
                dosya_islemleri.yesil_olan_dosya_hepsini_sil();
                bilet_satin_al_buton_bilet_satin_al_uyari_mesaj.setText("İşlem Başarılı Bir şekilde Gerçekleştirilmiştir.");
                break;
            case 177:
                int sayac2 = yesil.sayac();
                dosya_islemleri.bilet_satin_al_dosya_ekle(user_id, salon_id, seans_id, yesil, sayac2);
                dosya_islemleri.yesil_olan_dosya_hepsini_sil();
                bilet_satin_al_buton_bilet_satin_al_uyari_mesaj.setText("İşlem Başarılı Bir şekilde Gerçekleştirilmiştir.");
                break;
            case 265:
                int sayac3 = yesil.sayac();
                dosya_islemleri.bilet_satin_al_dosya_ekle(user_id, salon_id, seans_id, yesil,sayac3);
                dosya_islemleri.yesil_olan_dosya_hepsini_sil();
                bilet_satin_al_buton_bilet_satin_al_uyari_mesaj.setText("İşlem Başarılı Bir şekilde Gerçekleştirilmiştir.");
                break;
            case 294:
                int sayac4 = yesil.sayac();
                dosya_islemleri.bilet_satin_al_dosya_ekle(user_id, salon_id, seans_id, yesil, sayac4);
                dosya_islemleri.yesil_olan_dosya_hepsini_sil();
                bilet_satin_al_buton_bilet_satin_al_uyari_mesaj.setText("İşlem Başarılı Bir şekilde Gerçekleştirilmiştir.");
                break;
            default:
                System.out.println("Hata(app_standartuserController(bilet_Satin_al_buton_bilet_satin_al))");
                break;
        }

    }

    //bu metot bilet satın al içindeki seanslari göster butonununa tıklandıktan sonra combo box'ın içini doldurmak için yazılmış bir metoddur.
    private void seanslari_goster(Goruntuleme goruntu, int j) {
        /*
        bu seanslari göstermeden önce combo box'ın içini sıfırlıyor. Bunun sebebi ise farklı bir filmi seçtiği zaman ve yine bu 
        butona tıkladığı zaman daha önce seçtiği ile daha sonra çetiğinin seanslarının birbirine karışmaması açısından ekrana seansları 
        yazdırmadan önce temizleme işlemi yapyıor.
        
        Not: parametre olarak gelen 'j' değişkeni istenilen filmin id'sini temsil ediyor.
         */
        seanslari_goster.getItems().clear();

        /*
        Bu 5 satır var olan salonları ve var olan seanslari dosyadan okuyor ve bağlı listeye kaydediyor.
         */
        Seans sea = new Seans();
        Sinema_Salonlari salon = new Sinema_Salonlari();
        Dosya dosya_islemleri = new Dosya();

        dosya_islemleri.sinema_salonlari_dosya_oku(salon);
        dosya_islemleri.seans_dosya_oku(sea);

        //bu satır parametre olarak gelen goruntu değişkeninde kaç tane görüntülenecek değerin olduğunu hesaplıyor.
        int sayac = goruntu.sayac();

        //bu for döngüsü ise goruntu bağlı listesinin içinde bulunan bütün değerleri combo box'ın içine aktarıyor. (salon adlarını ve seans saatlerini yukarıda dosyadan çektiğimiz veriler aracılığıyla yapıyor.)
        for (int i = 1; i <= sayac; i++) {
            String goruntulenecek = String.valueOf(i) + " | " + "Salon adı: " + salon.Salon_Name(goruntu.salon_id(i)) + " | " + sea.Seans_Yolla(goruntu.seans_id(i)) + " | " + "Film ID:" + j;
            seanslari_goster.getItems().addAll(goruntulenecek);
        }
        seanslari_goster.setPromptText("İstediğiniz Seansı Seçiniz");
    }

}
