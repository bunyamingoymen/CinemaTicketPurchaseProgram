package Controller;

import Table.Filmler_Table;
import Table.Seans_Goruntule_Table;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import code.*;
import javafx.scene.control.*;
import code_ui.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class appController extends Center implements Initializable {

    /*
    Bunun devamı nerede ve neden Center diye bir şeyi extends ediyor?
    
    Bunun sebebi appController ve app_stanadart_userController sınıflarının ortak noktalarını kalıtım aracılığıyla başka sınıfta tanımlamamızdır..
    
    Hem appController da hem de app_standart_userController da 7.000'den fazla ortak kod bulunmaktadır.
    Bu sebeple ikisinn ortak noktalarını bir sınıfta topladık ve ikisine de extends ettik.
     */
    //Burada arayüzde gösterilen butonlar tanımlanmıştır.
    @FXML
    private Button buton10, buton20, buton30;

    //Burada ise anasayfada gösterilen değerleri göstermek için tanımlanmış panellerdir.
    @FXML
    private Label vizyondaki_film_sayisi, eski_film_sayisi, haber_Sayisi, kampanya_sayisi, sinema_Salonu_sayisi;

    //Burası ise kullanıcı kendi bilgisini değiştirmek için tasarlanan bölgeyi açan icon. (Kullanıcı burada e-postasını, mailini vs.. değiştirebiliyor)
    @FXML
    private void settings(MouseEvent event) {

        Dosya dosya_islemleri = new Dosya();

        //burası settings pane'i dışında kalan diğer ana pane'leri kapatıyor. (Çoğu pane extends ile yapılan Center'dan geliyor.)
        home_page.setVisible(false);
        haberler_pane.setVisible(false);
        kampanyalar_pane.setVisible(false);
        eski_film_pane.setVisible(false);
        vizyondaki_filmler_pane.setVisible(false);
        seans_pane.setVisible(false);
        sinema_salonlari_pane.setVisible(false);

        // Diğer ana pane'leri kapattıktan sonra kendi pane'ini açıyor ve böylelikle kullanıcı kendi bilgisini değiştirebilir hale geliyor.
        settings_pane.setVisible(true);

        //Bu alttaki satırlar ise kullanıcının şu anki bilgilerini kullanıcıya göstermek açısından tasarlandı.
        int user_id = dosya_islemleri.bilgi_dosya_oku();

        User user_list = new User();
        user_list = dosya_islemleri.user_dosya_oku(user_list);

        String mail = user_list._al_user_mail(user_id, user_list);
        String name = user_list.al_user_name(user_id, user_list);
        String password = user_list._al_user_password(user_id, user_list);

        user_name.setText(name);
        user_mail.setText(mail);
        user_password.setText(password);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Ana_Sayfa();
        //Bu metot program ilk başlatıldığında Ana sayfada gösterilmesi gereken içerikleri göstermeye yarıyor.
        /*
        
        Neden Ana_Sayfa metoundaki içerikler burada değil? 
        
        Bunun sebebi veriler güncellendiği ya da görüntülendiği zaman(haberler, kampanyalar vs.. silindiği ya da eklendiği zamanlar...) 
        yeninden Ana sayfaya dönülmek istendiğinde var olan verilerin de güncellemesi gerekiyor. 
        Bu nednele başka metotların da Ana_Sayfa metodunda işlevleri yapması gerekiyor. 
        Bu yüzden Ana_Sayfa'daki metodundaki işlemler bir metoda topladı ve bunun sayesinde kod tekrarı önlendi.
        
         */
    }

    //Ana sayfa da gösterilen değerleri bu method hesaplayıp gösteriyor.
    @FXML
    private void Ana_Sayfa() {

        //Dosya sınıfı ile etkileşimde olamk için değişken tanımlanıyor.
        Dosya dosya_islemleri = new Dosya();

        //Vizyonda kaç tane film olduğunu hesaplayıp ekrana yazıyor.
        Filmler vizyonda = (Filmler) new Vizyondaki_Filmler();
        vizyonda = dosya_islemleri.filmler_dosya_oku(vizyonda, 1);
        vizyondaki_film_sayisi.setText(String.valueOf(vizyonda.sayac()));

        //Kaç tane eski film olduğunu hesaplayıp ekrana yazıyor.
        Filmler eski = (Filmler) new Eski_Filmler();
        eski = dosya_islemleri.filmler_dosya_oku(eski, 0);
        eski_film_sayisi.setText(String.valueOf(eski.sayac()));

        //Kaç tane haber olduğunu hesaplayıp ekrana yazıyor.
        Duyurular haber = new Haberler();
        haber = dosya_islemleri.duyurular_dosya_oku(haber, 0);
        haber_Sayisi.setText(String.valueOf(haber.sayac()));

        //Kaç tane kampanya olduğunu hesaplayıp ekrana yazıyor.
        Duyurular kampanya = new Kampanyalar();
        kampanya = dosya_islemleri.duyurular_dosya_oku(kampanya, 1);
        kampanya_sayisi.setText(String.valueOf(kampanya.sayac()));

        //Kaç tane sinema salonu olduğunu hesaplayıp ekrana yazıyor.
        Sinema_Salonlari salon = new Sinema_Salonlari();
        salon = dosya_islemleri.sinema_salonlari_dosya_oku(salon);
        sinema_Salonu_sayisi.setText(String.valueOf(salon.sayac()));
    }

    //Programda sol tarafta 'Ana Sayfa' yazısının methodu. Bu method var olan bütün ana pane'leri kapatıp butonları eski haline döndürüp, Ana sayfadaki bütün değerleri yeninden hesaplıyor. 
    @FXML
    private void home(MouseEvent event) {

        //Butonları eski haline döndermek için tasarlandı. Neden böyle olduğunu merak ediyorsanız butonların methodlarına bakabilirsiniz.
        buton10.setText("Film İşlemleri");
        buton20.setText("Duyuru İşlemleri");
        buton30.setVisible(true);
        buton30.setText("Sinema Salonları");

        //Burası ana sayfanın pane'i dışında kalan bütün pane'leri kapatıyor.
        settings_pane.setVisible(false);
        haberler_pane.setVisible(false);
        kampanyalar_pane.setVisible(false);
        eski_film_pane.setVisible(false);
        vizyondaki_filmler_pane.setVisible(false);
        seans_pane.setVisible(false);
        sinema_salonlari_pane.setVisible(false);

        home_page.setVisible(true);

        //Ana sayfa daki bütün değerleri hesaplıyor.
        Ana_Sayfa();

    }

    @FXML
    private void Buton10(ActionEvent event) {
        //Bu metot birinci butonu yönetmek içindir.
        /*
            Buton kalabalığı yapmamak ve işlemieri gerçekleştirmek açısından. Yeni buton oluşturmak yerine var olan butonun içindeki yazıyı değiştiriyoruz.
            Ve işlemlerimizi butonun içinde bulunan yazılara göre yapıyoruz.
            Bunun sayesinde daha fazla buton ve metot oluşturmuyor ve daha az kod satırı yazıyoruz. 
            Hem de arayüzü tasarlarken daha az buton olduğu için Scene Builder üzerinden daha rahat tasarım yapabiliyoruz.
         */

 /*
        
        Bu switch case yukarıda anlattığımız mantığa göre programın çalışamsını sağllıyor. 
        Yani kısacası. case'lerin içinde Butonda yazması gereken yazılar mevut. Butonda Hangi yazılar yazarsa da ona göre işlem yapıyor.
        
         */
        switch (buton10.getText()) {
            /*
            
            1.case'in işlemi de 1.Butonda "Film İşlemleri" yazarsa yapılması gerekenleri yapıyor. 
            Eğer 1.Butonda "Film İşlemeri" yazıyorsa. 1.Butonun adını "Eski Filmler", 
            2.Butonun adını ise "Vizyondaki Filmler" olarak değiştiriyor. ve 
            3.butona ihtiyaç olmadığı için de bu butonu görünmez hale getiriyor.
            
             */
            case "Film İşlemleri":
                buton10.setText("Eski Film İşlemleri");
                buton20.setText("Vizyondaki Film İşlemleri");
                buton30.setVisible(false);
                break;
            //2.case ise 1.butonda "Eski Film İşlemleri" yazıyorsa Eski Film işlemlerini yapmaya yarıyor.
            case "Eski Film İşlemleri":
                home_page.setVisible(false);
                haberler_pane.setVisible(false);
                settings_pane.setVisible(false);
                kampanyalar_pane.setVisible(false);
                eski_film_pane.setVisible(true);
                eski_film_table_pane.setVisible(true);
                eski_film_guncelle_sil_pane.setVisible(false);
                eski_film_ekle_pane.setVisible(false);
                vizyondaki_filmler_pane.setVisible(false);
                seans_pane.setVisible(false);
                sinema_salonlari_pane.setVisible(false);
                filmler_table(0);
                break;
            /*
            3.case ise 1.butonda "Haber İşlemeri" yazıyorsa Haber işlemlerini yapmalı. 
            (İlk olarak haber işelmerrinin bulunduğu pane'i aktif hale getiriyor ve 
            diğer pane ileri kapalı hale getiriyor. Ardından da haberleri tabloya sıralıyor.)
             */
            case "Haber İşlemleri":
                eski_film_pane.setVisible(false);
                haberler_pane.setVisible(true);
                yeni_haber_ekle_pane.setVisible(false);
                haberi_guncelle_sil_pane.setVisible(false);
                haber_tablo_pane.setVisible(true);
                home_page.setVisible(false);
                settings_pane.setVisible(false);
                kampanyalar_pane.setVisible(false);
                vizyondaki_filmler_pane.setVisible(false);
                seans_pane.setVisible(false);
                sinema_salonlari_pane.setVisible(false);
                duyuru_table(0);
                break;
            // Bu default ise 1.butonun text'inde planlanan yazının olmadığı zamanlarda devreye giriyor. ve butonun text'ini "Hata" olarak değiştiriyor
            default:
                buton10.setText("Hata");
                break;
        }
    }

    //Bu metod da 'Button10' metodu ile aynı mantıkda çalışıyor. Bu yüzden anlatım yapılmadı.
    @FXML
    private void Buton20(ActionEvent event) {
        switch (buton20.getText()) {

            case "Duyuru İşlemleri":
                buton10.setText("Haber İşlemleri");
                buton20.setText("Kampanya İşlemleri");
                buton30.setVisible(false);
                break;
            case "Kampanya İşlemleri":
                eski_film_pane.setVisible(false);
                kampanyalar_pane.setVisible(true);
                yeni_kampanya_ekle_pane.setVisible(false);
                kampanyalari_duzenle_sil_pane.setVisible(false);
                kampanyalar_tablo_pane.setVisible(true);
                home_page.setVisible(false);
                settings_pane.setVisible(false);
                haberler_pane.setVisible(false);
                vizyondaki_filmler_pane.setVisible(false);
                seans_pane.setVisible(false);
                sinema_salonlari_pane.setVisible(false);
                duyuru_table(1);
                break;
            case "Vizyondaki Film İşlemleri":
                home_page.setVisible(false);
                settings_pane.setVisible(false);
                haberler_pane.setVisible(false);
                kampanyalar_pane.setVisible(false);
                eski_film_pane.setVisible(false);
                vizyondaki_filmler_pane.setVisible(true);
                vizyondaki_filmler_table_pane.setVisible(true);
                vizyona_film_ekle_pane.setVisible(false);
                vizyondaki_filmleri_guncelle_sil_pane.setVisible(false);
                seans_pane.setVisible(false);
                sinema_salonlari_pane.setVisible(false);
                filmler_table(1);
                break;
            default:
                buton20.setText("Hata");
                break;
        }
    }

    //Bu metod da 'Button10' metodu ile aynı mantıkda çalışıyor. Bu yüzden anlatım yapılmadı.
    @FXML
    private void Buton30(ActionEvent event) {
        home_page.setVisible(false);
        settings_pane.setVisible(false);
        haberler_pane.setVisible(false);
        kampanyalar_pane.setVisible(false);
        eski_film_pane.setVisible(false);
        vizyondaki_filmler_pane.setVisible(false);
        seans_pane.setVisible(false);

        sinema_salonlari_pane.setVisible(true);

        sinema_salonlari_home_pane.setVisible(true);
        salon_bir_pane.setVisible(false);
        salon_iki_pane.setVisible(false);
        salon_uc_pane.setVisible(false);
        salon_dort_pane.setVisible(false);
        sinema_salonlari_goruntule_pane.setVisible(false);
        sinema_salonu_ekle_pane.setVisible(false);
        sinema_salonu_duzenle_pane.setVisible(false);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Bu alanda sadece haberler için kullanılan metotlar ve değişkenler bulunmaktadır.

    //haber işlemleri için gerekli değişkenler tanımlanıyor.
    @FXML
    private Label yeni_haber_uyarı_mesaj, haberi_guncelle_sil_uyarı_mesaj, haberi_guncelle_sil_id;

    @FXML
    private Pane haberi_guncelle_sil_pane, haber_tablo_pane, yeni_haber_ekle_pane;

    @FXML
    private TextField yeni_haber_başlık, yeni_haber_tarih, haberi_guncelle_title, haberi_guncelle_date;

    @FXML
    private TextArea yeni_haber, haberi_guncelle_haber;

    /*
    burası yeni haber ekle butonunun Actionevent'ı. Bunun yaptığı tek şey. 
    Listeleme şeklidne tablonun haberleri gösterdiği pane'i kapatıp Haber eklemek 
    için oluşturduğumuz pane'i açıyor.
    */
    @FXML
    private void yeni_haber_ekle(ActionEvent event) {
        haber_tablo_pane.setVisible(false);
        yeni_haber_ekle_pane.setVisible(true);
    }

    /*
    yeni_haber_ekle_pane'in içindeki haberi ekle butonuna basılınca bu metot çalışıyor.
    Bu metodun yaptığı şey istenilen değerler girilmiş ise haberi ekliyor.
    */
    @FXML
    private void haberi_ekle(ActionEvent event) {
        //ilk olarak zorunlu olan yerlerin doldurulup doldurulmadığını kontrol ediyoruz. Doldurulmamış ise hata mesajını veriyoruz.
        if ((yeni_haber_başlık.getText().length() == 0) || (yeni_haber_tarih.getText().length() == 0) || (yeni_haber.getText().length() == 0)) {
            yeni_haber_uyarı_mesaj.setText("Lütfen Gerekli Yerleri Doldurunuz");
        } else {
            //Gerekli yerler doldurulmuş ise bu else'ye giriyor ve ilk yaptığı şey dosya sınıfı ile etkileşimde olmak için bir değişken oluşturuyor.
            Dosya dosya_islemleri = new Dosya();
            //Kullanıcıdan aldığımız verileri dosya işlemlerindeki duyurular_dosya_ekle metoduna yollayarak dosyaya eklemiş oluyoruz.
            dosya_islemleri.duyurular_dosya_ekle(yeni_haber_tarih.getText(), yeni_haber_başlık.getText(), yeni_haber.getText(), 0);
            //en sonda da işlemin başarılı bir şekilde gerçekleştiğini kullanıcıya bildiriyoruz.
            yeni_haber_uyarı_mesaj.setText("İşlem Başarıyla Gerçekleştirilmiştir.");
        }
    }

    /*
    bu MouseEvent yeni haber eklemek için oluşturduğumuz pane'in geri gitme iconuna ait.
    buraya basıldığında haberlerin tabloda listelendiği sayfaya geri dönülüyor ve 
    ardından tablodaki değerler yeniden hesaplanıyor.
    */
    @FXML
    private void yeni_haber_ekle_geri(MouseEvent event) {
        yeni_haber_ekle_pane.setVisible(false);
        haber_tablo_pane.setVisible(true);
        duyuru_table(0);
    }

    //haberlerin tablo içerisinde listelendiği sayfada haberi güncelle/sil butonu aktif olduğunda çalışan metotdur.
    @FXML
    private void haberi_guncelle_sil(ActionEvent event) {
        /*
        bu metotdun yaptığı tek şey haberlerin tablo da listelendiği pane'i kapatıp 
        güncellemek için açtığımız pane'i aktif etmesidir. Tabi bundan sonra da combo box'ın 
        içine haberleri yazdırmak için ayrı bir metotda gidiyor.
        */
        haber_tablo_pane.setVisible(false);
        haberi_guncelle_sil_pane.setVisible(true);
        duyuruyu_getir_combo(0);
    }

    /*
    bu MouseEvent haberi_guncelle_sil_pane'deki geri iconu'nu çalıştırmaktadır. 
    tek yaptığı şey haberlerin tabloda listelendiği sayfaya geri dönmek. 
    Aynı zamanda da tablodaki verileri tekrar gözden geçirip listelemek.
    */
    @FXML
    private void haberi_guncelle_sil_geri(MouseEvent event) {

        haberi_guncelle_sil_pane.setVisible(false);
        haber_tablo_pane.setVisible(true);
        duyuru_table(0);
    }

    //Kullanıcı haberi_guncelle_sil_pane'deki Haberi getir butonuna bastıktan sonra çalışan metot.
    @FXML
    private void haberi_getir(ActionEvent event) {
        //ilk başta dosyadaki haberleri okuyoruz.
        Dosya dosya_islemleri = new Dosya();
        Haberler haber = new Haberler();
        dosya_islemleri.duyurular_dosya_oku(haber, 0);
        /*
        daha sonradan seçilen haberi anlamamıza yardımcı olmak için j değişkenini oluşturuyoruz. ve kaç tane
        haberin olduğunu da sayac değişkeninde tutuyoruz.
        */
        int j = 0, sayac = haber.sayac();
        //kullanıcının seçtiği değeri daha kolay işlem yapabilmek için string'e atıyoruz.
        String secilen = haberleri_goster.getValue();
        //seçilen değerin boş olup olmadığını kontrol ediyoruz. Yani kullanıcı bir değer seçmemiş ise uyarı verip daha sonraki işlemleri yapmıyoruz.
        if (secilen == null) {
            haberi_guncelle_sil_uyarı_mesaj.setText("Lütfen Bir Değer Seçiniz.");
        } else {
            //Kullanıcı bir değer seçmiş ise bu elseye giriyor
            /*
            buradaki for döngüsü hangi haberin seçildiğini buluyor ve ardından onun id'sini j 
            değişkenine atıyor. j ddeğişkeninin değeri değiştiği içinse döngüden direk çıkıyot.
            */
            for (int i = 1; (i <= sayac) && (j == 0); i++) {
                String haberler = String.valueOf(i) + " | " + haber.Date(i) + " | " + haber.Title(i) + " | " + haber.Duyuru(i);
                if (secilen.equals(haberler)) {
                    j = i;
                }
            }
            //döngüden çıktıntan sonra seçilen haberi belirledik ve gerekli yerlere gerekli bilgileri yazıyoruz.
            haberi_guncelle_sil_id.setText(String.valueOf(j));
            haberi_guncelle_title.setText(haber.Title(j));
            haberi_guncelle_date.setText(haber.Date(j));
            haberi_guncelle_haber.setText(haber.Duyuru(j));
            //kısacası kullanıcının seçtiği haberin bilgilerini ekrandaki textfield, textare ve label değişkenlerine yazıyoruz.
        }

    }

    //bu metot kullanıcının seçtiği haberi siliyor.(haberi_guncelle_sil_pane'de)
    @FXML
    private void haberi_sil(ActionEvent event) {
        String haberler = haberleri_goster.getValue();
        //ilk başta kullanıcının combobox da seçtiği haberi bir string'e aktarıyoruz.
        /*
        daha sonra bu stringin null olup olmadığını yani kullanıcının bir haberi seçip 
        seçmediğini kontrol ediyoruz. Kullanıcı bir haberi seçmemiş ise uyarı verip diğer işlemleri yapmıyoruz.
        */
        if (haberler == null) {
            haberi_guncelle_sil_uyarı_mesaj.setText("Lütfen bir haber seçiniz");
        } else {
            //ilk olarak yaptığımız şey dosya sınıfı ile etkileşimde olamk için dosya sınınana ait bir değişken tanımlamak
            Dosya dosya_islemleri = new Dosya();
            //değişkeni tanımladıktan sonra dosya sınıfındaki gerekli metodu çağrıyoruz. Ve o metot başarılı olursa 1, başarısış olursa 0'ı döndürüyor.
            int control = dosya_islemleri.duyurular_dosya_sil(Integer.valueOf(haberi_guncelle_sil_id.getText()), 0);
            if (control == 1) {
                /*
                eğer başarılı olursa combobox'daki bilgileri güncelliyoruz ve arından ekranda 
                bilgileir tutan textare ve textfield'in içlerini de temizliyoruz.(Çünkü bir haber silindi)
                */
                duyuruyu_getir_combo(0);
                haberi_guncelle_sil_id.setText("");
                haberi_guncelle_date.setText("");
                haberi_guncelle_title.setText("");
                haberi_guncelle_haber.setText("");
                //en sonda da başarılı olduğuna dairt mesaj verip işlemi sonlandırıyoruz.
                haberi_guncelle_sil_uyarı_mesaj.setText("İstediğüiniz Haber Başarılı bir şekilde silindi");
            } else {
                //eğer dosyadan silme işlemi sırasında gönderidğimiz mtot 0(sıfır) dönerse bir sorun oluştuğunu söyleyip işlemi sonlandırıyoruz.
                haberi_guncelle_sil_uyarı_mesaj.setText("Bir hata meydana geldi. Lütfen daha sonra yeninden deneyiniz");
            }
        }
    }

    //bu metot haberi sil botununun yanindaki haberi güncelle butonunun aktif olduğudna çalıştığı metot
    @FXML
    private void haberi_guncelle(ActionEvent event) {
        //ilk başta kullanıcının combobox'Dan seçtiği değeri bir string'e atıyoruz.
        String haberler = haberleri_goster.getValue();
        //ardından stringin boş olup olmadığını yani combobox'ın boş olup olmadığını kontrol ediyoruz. Boş ise uyarı mesajı verip geri kalan işlemelri yapmıyoruz.
        if (haberler == null) {
            haberi_guncelle_sil_uyarı_mesaj.setText("Lütfen bir haber seçiniz");
        } else {
            //eğer kullanıcı bir değeri seçmiş ise işlemlerimize devam ediyoruz.
            /*
            daha sonra ilk yaptığımız şey gerekli textfield ve textareların dolu olup 
            olmadığını kontrol etmek. eğer gerekli yerler boş ise hata mesajını yazıp işlemi sonlandırıyoruz.
            */
            if ((haberi_guncelle_date.getText().length() == 0) || (haberi_guncelle_title.getText().length() == 0) || (haberi_guncelle_haber.getText().length() == 0)) {
                haberi_guncelle_sil_uyarı_mesaj.setText("Lütfen gerekli yerleri doldurunuz");
            } else {
                //eğer bu else girmiş ise gerekli yerler doludur anlamıan geliyor. Artık işlemimizi yapmaya başlayabiliriz.
                //ilk olarak dosyadan haberleri okumakla işleme başlıyoruz.
                Haberler haber = new Haberler();
                Dosya dosya_islemleri = new Dosya();
                dosya_islemleri.duyurular_dosya_oku(haber, 0);
                //dosyadaki haberleri okduktan sonra değiştirilmesi istenilen haberin id'sini bir değişkene atıyoruz.
                int id = Integer.valueOf(haberi_guncelle_sil_id.getText());
                //daha sonra dosya sınıfında bulunan bir metoda kullanıcının giridği bilgileir iletiyoruz. Bu metot 0 dönerse başarısız, 1 dönerse başarılı anlamıan geliyor.
                int control = haber.guncelle(id, haberi_guncelle_date.getText(), haberi_guncelle_title.getText(), haberi_guncelle_haber.getText(), haber);
                if (control == 1) {
                    //eğer işlemimiz başarılı ise combobox'ı da güncelleyip ekrana başarılı olduğuna dair yazımızı yazıyoruz.
                    duyuruyu_getir_combo(0);
                    haberi_guncelle_sil_uyarı_mesaj.setText("İstediğiniz işelm başarılı bir şekilde gerçekleşti");
                } else {
                    //eğer başarısız ise bir hata meydana geldiğini ekrana yazıyoruz.
                    haberi_guncelle_sil_uyarı_mesaj.setText("Bir hata meydana geldi. Lütfen daha sonra tekrar deneyiniz.");
                }
            }
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //bu fxml dosyasına özel kampanya işlemleri
    //kampanyalar sınıfı, haberler sınıfına extends edilen sınıfa bağlı olduğu için haberler sınıfı ile işlemleri aynı ilerlemektedir. Bu yüzden burada anlatım yapılmamıştır.
    @FXML
    private Label yeni_kampanya_uyari_mesaj, kampanyalari_guncelle_sil_id;

    @FXML
    private Pane kampanyalar_tablo_pane, kampanyalari_duzenle_sil_pane, yeni_kampanya_ekle_pane;

    @FXML
    private TextField yeni_kampanya_title, yeni_kampanya_date, kampanyayi_guncelle_title, kampanyayi_guncelle_date;

    @FXML
    private TextArea yeni_kampanya, kampanyayi_guncelle_kampanya;

    @FXML
    private void yeni_kampanya_ekle(ActionEvent event) {
        kampanyalar_tablo_pane.setVisible(false);
        yeni_kampanya_ekle_pane.setVisible(true);
    }

    @FXML
    private void kampanyayi_ekle(ActionEvent event) {
        if ((yeni_kampanya_title.getText().length() == 0) || (yeni_kampanya_date.getText().length() == 0) || (yeni_kampanya.getText().length() == 0)) {
            yeni_kampanya_uyari_mesaj.setText("Lütfen Gerekli Yerleri Doldurunuz");
        } else {
            Dosya dosya_islemleri = new Dosya();
            dosya_islemleri.duyurular_dosya_ekle(yeni_kampanya_date.getText(), yeni_kampanya_title.getText(), yeni_kampanya.getText(), 1);
            yeni_kampanya_uyari_mesaj.setText("İşlem Başarılı Bir şekilde Gerçekleştirildi.");
        }
    }

    @FXML
    private void yeni_kampanya_ekle_geri(MouseEvent event) {
        kampanyalar_tablo_pane.setVisible(true);
        yeni_kampanya_ekle_pane.setVisible(false);
        duyuru_table(1);
    }

    @FXML
    private void kampanyalari_guncelle_sil(ActionEvent event) {
        kampanyalar_tablo_pane.setVisible(false);
        kampanyalari_duzenle_sil_pane.setVisible(true);

        duyuruyu_getir_combo(1);
    }

    @FXML
    private void kampanyayi_guncelle_sil_geri(MouseEvent event) {
        kampanyalar_tablo_pane.setVisible(true);
        kampanyalari_duzenle_sil_pane.setVisible(false);
        duyuru_table(1);
    }

    @FXML
    private void kampanyayi_getir() {
        Dosya dosya_islemleri = new Dosya();
        Kampanyalar kampanya = new Kampanyalar();
        dosya_islemleri.duyurular_dosya_oku(kampanya, 1);
        int j = 0, sayac = kampanya.sayac();
        String secilen = kampanyalari_goster.getValue();
        if (secilen == null) {
            kampanyayi_guncelle_Sil_uyari_mesaj.setText("Lütfen Bir Değer Seçiniz");
        } else {
            for (int i = 1; (i <= sayac) && (j == 0); i++) {
                String kampanyalar = String.valueOf(i) + " | " + kampanya.Date(i) + " | " + kampanya.Title(i) + " | " + kampanya.Duyuru(i);
                if (secilen.equals(kampanyalar)) {
                    j = i;
                }
            }
            kampanyalari_guncelle_sil_id.setText(String.valueOf(j));
            kampanyayi_guncelle_date.setText(kampanya.Date(j));
            kampanyayi_guncelle_title.setText(kampanya.Title(j));
            kampanyayi_guncelle_kampanya.setText(kampanya.Duyuru(j));
        }
    }

    @FXML
    private void kampanyayi_sil(ActionEvent event) {
        String kampanyalar = kampanyalari_goster.getValue();
        if (kampanyalar == null) {
            kampanyayi_guncelle_Sil_uyari_mesaj.setText("Lütfen Bir Kampanya Seçiniz");
        } else {
            Dosya dosya_islemleri = new Dosya();
            int control = dosya_islemleri.duyurular_dosya_sil(Integer.valueOf(kampanyalari_guncelle_sil_id.getText()), 1);
            if (control == 1) {
                duyuruyu_getir_combo(1);
                kampanyalari_guncelle_sil_id.setText("");
                kampanyayi_guncelle_date.setText("");
                kampanyayi_guncelle_title.setText("");
                kampanyayi_guncelle_kampanya.setText("");
                kampanyayi_guncelle_Sil_uyari_mesaj.setText("İşlem Başarılı Bir Şekilde Gerçekleşti");
            } else {
                kampanyayi_guncelle_Sil_uyari_mesaj.setText("Bir hata meydana geldi. Lütfen Daha sonra Tekrar Deneyiniz.");
            }
        }
    }

    @FXML
    private void kampanyayi_guncelle(ActionEvent event) {
        String kampanyalar = kampanyalari_goster.getValue();
        if (kampanyalar == null) {
            kampanyayi_guncelle_Sil_uyari_mesaj.setText("Lütfen Bir Değer Seçiniz");
        } else {
            if ((kampanyayi_guncelle_date.getText().length() == 0) || (kampanyayi_guncelle_title.getText().length() == 0) || (kampanyayi_guncelle_kampanya.getText().length() == 0)) {
                kampanyayi_guncelle_Sil_uyari_mesaj.setText("Lütfen Gerekli yerleri Doldurunuz");
            } else {
                Dosya dosya_islemleri = new Dosya();
                Kampanyalar kampanya = new Kampanyalar();
                dosya_islemleri.duyurular_dosya_oku(kampanya, 1);
                int control = kampanya.guncelle(Integer.valueOf(kampanyalari_guncelle_sil_id.getText()), kampanyayi_guncelle_date.getText(), kampanyayi_guncelle_title.getText(), kampanyayi_guncelle_kampanya.getText(), kampanya);
                if (control == 1) {
                    duyuruyu_getir_combo(1);
                    kampanyayi_guncelle_Sil_uyari_mesaj.setText("İşlem Başarılı bir şekilde gerçekleşmiştir.");
                } else {
                    kampanyayi_guncelle_Sil_uyari_mesaj.setText("Bir Hata meydana Geldi. Lütfen Daha sonra tekrar deneyiniz.");
                }
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //hem haberler hem de kampanyalar için yapılmış ve sadece bu fxml dosyasına özel metotlar.
    //burası sadece combobox'ın aktif olduğu bölgedir. Gerekli olan değişkenleri tanımlıyoruz.
    @FXML
    public ComboBox<String> kampanyalari_goster;

    @FXML
    private ComboBox<String> haberleri_goster;

    @FXML
    private Label kampanyayi_guncelle_Sil_uyari_mesaj;

    //Burası herhangi bir buton ya da icon ile çalışmamaktadır. dieğr butonlarda veya icon'larda en son kısımda çalışmaktadır. Bunun sebebi ise bir haber ya da kampanya silindiğinde ya da güncellendiğinde combobox'ın içininde güncellenmesidir. Burası birden falza kez kullanıldığı için bir metot şeklinde yazılmıştır.
    private void duyuruyu_getir_combo(int islem) {
        //kalıtım aracılığıyla işlemler gerçkeleştiği için işlem diye bir parametre alıyoruz.
        //islem 0(sıfır) ise haberlerin işlemlerini yapyıyoruz. işlem 1(bir) ise kampanyaların işlemlerini yapıyoruz.
        Duyurular duyuru;
        switch (islem) {
            case 0:
                // işlem 0(sıfır) ise haberlerin combobox'ını dolduruyoruz.
                //ilk başta bu metot daha önce çalışmış olabilir diye combobox'ın içini temizliyoruz.
                haberleri_goster.getItems().clear();
                //daha sonra dosyadaki haberlerin hepsini dosya sınıfındaki metotla okuyoruz.
                duyuru = new Haberler();
                Dosya dosya_islemleri = new Dosya();
                Haberler haber = (Haberler) duyuru;
                dosya_islemleri.duyurular_dosya_oku(haber, 0);
                //bundan sonra ise haberler de kaç tane değer olduğunu hesaplıyoruz.
                int sayac = haber.sayac();

                if (sayac == 0) {
                    // eğer sayac=0 ise herhangi bir haer yoktur yazısını yazıyoruz.
                    haberi_guncelle_sil_uyarı_mesaj.setText("Kayıtlı haber bulunamadı");
                } else {
                    //en sonda da kaç tane değer varsa o kadar döndürüp her değeri teker teker combobox'ın içine aktarıyoruz.
                    for (int i = 1; i <= sayac; i++) {
                        String haberler = String.valueOf(i) + " | " + haber.Date(i) + " | " + haber.Title(i) + " | " + haber.Duyuru(i);
                        haberleri_goster.getItems().addAll(haberler);
                        //en alta da combobox'ın ilk açıldığında yazacak değerini tanımlıyoruz.
                        haberleri_goster.setPromptText("İstediğiniz Haberi seçiniz");
                    }

                }
                break;
            case 1:
                // işlem 1(bir) ise haberlerin combobox'ını dolduruyoruz.
                //ilk başta bu metot daha önce çalışmış olabilir diye combobox'ın içini temizliyoruz.
                kampanyalari_goster.getItems().clear();
                //daha sonra dosyadaki haberlerin hepsini dosya sınıfındaki metotla okuyoruz.
                duyuru = new Kampanyalar();
                Dosya dosya_islemleri2 = new Dosya();
                Kampanyalar kampanya = (Kampanyalar) duyuru;
                dosya_islemleri2.duyurular_dosya_oku(kampanya, 1);
                //bundan sonra ise haberler de kaç tane değer olduğunu hesaplıyoruz.
                int sayac2 = kampanya.sayac();
                if (sayac2 == 0) {
                    // eğer sayac2=0 ise herhangi bir haer yoktur yazısını yazıyoruz.
                    kampanyayi_guncelle_Sil_uyari_mesaj.setText("Kayıtlı Kampanya Bulunamadı");
                } else {
                    //en sonda da kaç tane değer varsa o kadar döndürüp her değeri teker teker combobox'ın içine aktarıyoruz.
                    for (int i = 1; i <= sayac2; i++) {
                        String kampanyalar = String.valueOf(i) + " | " + kampanya.Date(i) + " | " + kampanya.Title(i) + " | " + kampanya.Duyuru(i);
                        kampanyalari_goster.getItems().addAll(kampanyalar);
                    }
                    //en alta da combobox'ın ilk açıldığında yazacak değerini tanımlıyoruz.
                    kampanyalari_goster.setPromptText("İstediğiniz Kapmanyayı Seçiniz");
                }
                break;
            default:
                System.out.println("Hata(appController(duyuruyu_Getir_combo))");
                break;
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////
    //Eski Filmler için Ayrılmış olan kısım
    //eski filmler metotları ve değişkenleri de bir kaç değişiklik dışında haberler'e benzemektedir. Bu yüzden anlatım yapılmamıştır. (değişik olan yerler anlatılmıtşrı.)
    @FXML
    private Label yeni_eski_film_ekle_uyari_mesaj, eski_filmleri_guncelle_sil_uyari_mesaj, eski_filmleri_duzenle_sil_id;

    @FXML
    private Pane eski_film_table_pane, eski_film_ekle_pane, eski_film_guncelle_sil_pane;

    @FXML
    private TextField yeni_eski_film_ekle_title, yeni_eski_film_ekle_date, eski_filmleri_guncelle_date, eski_filmleri_guncelle_title;

    @FXML
    private ComboBox<String> eski_filmleri_goster;

    @FXML
    private void yeni_eski_film_ekle(ActionEvent event) {
        eski_film_table_pane.setVisible(false);
        eski_film_ekle_pane.setVisible(true);
    }

    @FXML
    private void eski_filmi_ekle(ActionEvent event) {
        String title = yeni_eski_film_ekle_title.getText();
        String date = yeni_eski_film_ekle_date.getText();
        if ((title.length() == 0) || (date.length() == 0)) {
            yeni_eski_film_ekle_uyari_mesaj.setText("Lütfem Gerekli Yerleri Doldurunuz");
        } else {
            Dosya dosya_islemleri = new Dosya();
            dosya_islemleri.filmler_dosya_ekle(date, title, 0);
            yeni_eski_film_ekle_uyari_mesaj.setText("İşlem Başarılı Bir Şekilde Gerçekleştirildi.");
        }
    }

    @FXML
    private void yeni_eski_film_ekle_geri(MouseEvent event) {
        eski_film_table_pane.setVisible(true);
        eski_film_ekle_pane.setVisible(false);
        filmler_table(0);
    }

    @FXML
    private void eski_filmleri_duzenle_sil(ActionEvent event) {
        eski_film_table_pane.setVisible(false);
        eski_film_guncelle_sil_pane.setVisible(true);
        filmleri_getir_combo(eski_filmleri_goster, 0);
    }

    @FXML
    private void eski_filmleri_getir(ActionEvent event) {
        Dosya dosya_islemleri = new Dosya();
        Filmler film = new Eski_Filmler();
        Eski_Filmler eski = (Eski_Filmler) film;
        dosya_islemleri.filmler_dosya_oku(eski, 0);
        int j = 0, sayac = eski.sayac();
        String secilen = eski_filmleri_goster.getValue();
        if (secilen == null) {
            eski_filmleri_guncelle_sil_uyari_mesaj.setText("Lütfen Bir Değer Seçiniz");
        } else {
            for (int i = 1; (i <= sayac) && (j == 0); i++) {
                String Filmler = String.valueOf(i) + " | " + eski.Date(i) + " | " + eski.Title(i);
                if (secilen.equals(Filmler)) {
                    j = i;
                }
            }
            eski_filmleri_duzenle_sil_id.setText(String.valueOf(j));
            eski_filmleri_guncelle_date.setText(eski.Date(j));
            eski_filmleri_guncelle_title.setText(eski.Title(j));

        }
    }

    @FXML
    private void eski_filmi_sil(ActionEvent event) {
        String eski_filmler = eski_filmleri_goster.getValue();

        if (eski_filmler == null) {
            eski_filmleri_guncelle_sil_uyari_mesaj.setText("Lütfen Bir Film Seçiniz");
        } else {
            Dosya dosya_islemleri = new Dosya();
            int control = dosya_islemleri.filmler_dosya_sil(Integer.valueOf(eski_filmleri_duzenle_sil_id.getText()), 0);

            if (control == 1) {
                //burası haberler ve duyuralrdan farklı olarak metoda bir combobox yollamaktadır. Bunun sebebi ise hem apstandart_userController da hem de burada bu metot kullanıldığı içindir.
                filmleri_getir_combo(eski_filmleri_goster, 0);
                eski_filmleri_duzenle_sil_id.setText("");
                eski_filmleri_guncelle_date.setText("");
                eski_filmleri_guncelle_title.setText("");
                eski_filmleri_guncelle_sil_uyari_mesaj.setText("İşlem Başarılı Bir Şekilde Gerçekleşti");
            } else {
                eski_filmleri_guncelle_sil_uyari_mesaj.setText("Bir hata meydana geldi. Lütfen Daha sonra Tekrar Deneyiniz.");
            }
        }
    }

    @FXML
    private void eski_filmi_guncelle(ActionEvent event) {
        String filmler = eski_filmleri_goster.getValue();
        if (filmler == null) {
            eski_filmleri_guncelle_sil_uyari_mesaj.setText("Lütfen Bir Değer Seçiniz");
        } else {
            if ((eski_filmleri_guncelle_date.getText().length() == 0) || (eski_filmleri_guncelle_title.getText().length() == 0)) {
                eski_filmleri_guncelle_sil_uyari_mesaj.setText("Lütfen Gerekli yerleri Doldurunuz");
            } else {
                Dosya dosya_islemleri = new Dosya();
                Filmler film = new Eski_Filmler();
                Eski_Filmler eski = (Eski_Filmler) film;
                dosya_islemleri.filmler_dosya_oku(eski, 0);
                int control = eski.guncelle(Integer.valueOf(eski_filmleri_duzenle_sil_id.getText()), eski_filmleri_guncelle_date.getText(), eski_filmleri_guncelle_title.getText());
                if (control == 1) {
                    filmleri_getir_combo(eski_filmleri_goster, 0);
                    eski_filmleri_guncelle_sil_uyari_mesaj.setText("İşlem Başarılı Bir Şekilde Gerçekleşti");
                } else {
                    eski_filmleri_guncelle_sil_uyari_mesaj.setText("Bir Hata meydana Geldi. Lütfen Daha sonra tekrar deneyiniz.");
                }
            }
        }
    }

    @FXML
    private void eski_filmleri_guncelle_sil_geri(MouseEvent event) {
        eski_film_table_pane.setVisible(true);
        eski_film_guncelle_sil_pane.setVisible(false);
        filmler_table(0);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //vizyondaki filmler için yazılmış kısım
    //vizyondaki filmler'de haberler,kampanyalar ve eski filmlere benzemektedir. Bu yüzden burası da anlatılmamıştır.
    @FXML
    private Label vizyona_film_ekle_uyari_mesaj, vizyondaki_filmleri_guncelle_sil_uyari_mesaj, vizyondaki_filmleri_duzenle_sil_id;

    @FXML
    private Pane vizyondaki_filmler_table_pane, vizyona_film_ekle_pane, vizyondaki_filmleri_guncelle_sil_pane;

    @FXML
    private TextField vizyondaki_filmleri_guncelle_title, vizyona_film_ekle_title, vizyona_film_ekle_date, vizyondaki_filmleri_guncelle_date;

    @FXML
    private ComboBox<String> vizyondaki_filmleri_goster;

    @FXML
    private void vizyona_film_ekle(ActionEvent event) {
        vizyondaki_filmler_table_pane.setVisible(false);
        vizyona_film_ekle_pane.setVisible(true);
    }

    @FXML
    private void filmi_vizyona_ekle(ActionEvent event) {
        String title = vizyona_film_ekle_title.getText();
        String date = vizyona_film_ekle_date.getText();
        if ((title.length() == 0) || (date.length() == 0)) {
            vizyona_film_ekle_uyari_mesaj.setText("Lütfen Gerekli Yerleri Doldurunuz");
        } else {
            Dosya dosya_islemleri = new Dosya();
            dosya_islemleri.filmler_dosya_ekle(date, title, 1);
            vizyona_film_ekle_uyari_mesaj.setText("İşlem Başarılı Bir şekilde gerçekleştirildi.");
        }
    }

    @FXML
    private void vizyona_film_ekle_geri(MouseEvent event) {
        vizyona_film_ekle_pane.setVisible(false);
        vizyondaki_filmler_table_pane.setVisible(true);
        filmler_table(1);
    }

    @FXML
    private void vizyondaki_filmleri_guncelle_sil(ActionEvent event) {
        vizyondaki_filmler_table_pane.setVisible(false);
        vizyondaki_filmleri_guncelle_sil_pane.setVisible(true);
        filmleri_getir_combo(vizyondaki_filmleri_goster, 1);

    }

    @FXML
    private void vizyondaki_filmleri_guncelle_geri(MouseEvent event) {
        vizyondaki_filmleri_guncelle_sil_pane.setVisible(false);
        vizyondaki_filmler_table_pane.setVisible(true);
        filmler_table(1);
    }

    @FXML
    private void vizyondaki_filmleri_getir(ActionEvent event) {
        Dosya dosya_islemleri = new Dosya();

        Filmler film = new Vizyondaki_Filmler();

        Vizyondaki_Filmler vizyonda = (Vizyondaki_Filmler) film;

        dosya_islemleri.filmler_dosya_oku(vizyonda, 1);

        int j = 0, sayac = vizyonda.sayac();

        String secilen = vizyondaki_filmleri_goster.getValue();

        if (secilen == null) {

            vizyondaki_filmleri_guncelle_sil_uyari_mesaj.setText("Lütfen Bir Değer Seçiniz");
        } else {

            for (int i = 1; (i <= sayac) && (j == 0); i++) {

                String Filmler = String.valueOf(i) + " | " + vizyonda.Date(i) + " | " + vizyonda.Title(i);

                if (secilen.equals(Filmler)) {

                    j = i;

                }

            }

            vizyondaki_filmleri_duzenle_sil_id.setText(String.valueOf(j));

            vizyondaki_filmleri_guncelle_date.setText(vizyonda.Date(j));

            vizyondaki_filmleri_guncelle_title.setText(vizyonda.Title(j));

        }

    }

    @FXML
    private void vizyondaki_filmi_sil(ActionEvent event) {
        String vizyonda = vizyondaki_filmleri_goster.getValue();

        if (vizyonda == null) {
            vizyondaki_filmleri_guncelle_sil_uyari_mesaj.setText("Lütfen Gerekli Yerleri Doldurunuz");
        } else {
            Dosya dosya_islemleri = new Dosya();
            Seans sea = new Seans();
            dosya_islemleri.seans_dosya_oku(sea);
            sea.remove(Integer.valueOf(vizyondaki_filmleri_duzenle_sil_id.getText()));
            sea.dusur(Integer.valueOf(vizyondaki_filmleri_duzenle_sil_id.getText()));
            dosya_islemleri.seans_dosya_yaz(sea);
            int control = dosya_islemleri.filmler_dosya_sil(Integer.valueOf(vizyondaki_filmleri_duzenle_sil_id.getText()), 1);
            if (control == 1) {

                filmleri_getir_combo(vizyondaki_filmleri_goster, 1);
                vizyondaki_filmleri_duzenle_sil_id.setText("");
                vizyondaki_filmleri_guncelle_date.setText("");
                vizyondaki_filmleri_guncelle_title.setText("");
                vizyondaki_filmleri_guncelle_sil_uyari_mesaj.setText("İşlem başarılı bir şekilde gerçekleştirildi");
            } else {
                vizyondaki_filmleri_guncelle_sil_uyari_mesaj.setText("Bir hata meydana geldi Lütfen daha donra yenidne deneyiniz.");
            }
        }
    }

    @FXML
    private void vizyondaki_filmi_guncelle(ActionEvent event) {
        String filmler = vizyondaki_filmleri_goster.getValue();
        if (filmler == null) {
            vizyondaki_filmleri_guncelle_sil_uyari_mesaj.setText("Lütfen Bir Değer Seçiniz");
        } else {
            if ((vizyondaki_filmleri_guncelle_title.getText().length() == 0) || (vizyondaki_filmleri_guncelle_date.getText().length() == 0)) {
                vizyondaki_filmleri_guncelle_sil_uyari_mesaj.setText("Lütfen gerekli yerleri doldurunuz");
            } else {
                Dosya dosya_islemleri = new Dosya();
                Filmler film = new Vizyondaki_Filmler();
                Vizyondaki_Filmler vizyonda = (Vizyondaki_Filmler) film;
                dosya_islemleri.filmler_dosya_oku(vizyonda, 1);
                int control = vizyonda.guncelle(Integer.valueOf(vizyondaki_filmleri_duzenle_sil_id.getText()), vizyondaki_filmleri_guncelle_date.getText(), vizyondaki_filmleri_guncelle_title.getText());
                if (control == 1) {
                    filmleri_getir_combo(vizyondaki_filmleri_goster, 1);
                    vizyondaki_filmleri_guncelle_sil_uyari_mesaj.setText("İşlem Başarılı Bir Şekilde Gerçekleşti");
                } else {
                    vizyondaki_filmleri_guncelle_sil_uyari_mesaj.setText("Bir hata meydana geldi");
                }

            }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Seans işlemleri burada tanımlanmıştır
    //burada kullanılacak olan değişkenler tanımlanmıştır.
    @FXML
    private Pane seans_pane;

    @FXML
    private Pane seans_home_pane, seans_goruntule_pane, seans_silmekten_emin, seans_sil_pane, seans_ekle_pane, seans_degistirmekten_emin_pane;

    @FXML
    public ComboBox<String> seans_sil_combo;

    @FXML
    public TableView<Seans_Goruntule_Table> seans_table;

    @FXML
    public TableColumn<Filmler_Table, String> gosterim_id;

    @FXML
    public TableColumn<Filmler_Table, String> seans;

    @FXML
    public TableColumn<Filmler_Table, String> salon;
    @FXML
    public TableColumn<Filmler_Table, String> film;

    @FXML
    private ComboBox<String> seans_ekle_film_combo, seans_ekle_salon_combo, seans_ekle_seans_combo;

    @FXML
    private Label seans_sil_uyari_mesaj, seans_ekle_uyari_mesaj;

    //bu metot vizyondaki filmler sayfasındaki seans işlemleri butonunun metodudur. Yaptığı şey seans işlemleri için oluşturulan pane'i açmak. Diğer pane'leri ise kapatmaktır.
    @FXML
    private void seans_islemleri(ActionEvent event) {
        home_page.setVisible(false);
        settings_pane.setVisible(false);
        haberler_pane.setVisible(false);
        kampanyalar_pane.setVisible(false);
        eski_film_pane.setVisible(false);
        vizyondaki_filmler_pane.setVisible(false);

        seans_pane.setVisible(true);

        seans_goruntule_pane.setVisible(false);
        seans_sil_pane.setVisible(false);
        seans_ekle_pane.setVisible(false);
        seans_home_pane.setVisible(true);
    }

    //Bu metot herhangi bir burona bağlı değildir. Bu butonun yaptığı iş ise sırasıyla her seansı tabloya eklemektedir. Bu da birden fazla yerde lazım olduğu için ayrı bir metot olarak tanımlanmıştır.
    private void seans_goruntule() {
        //ilk başta dosya sınıfı ile etkileşim kurup dosyadan bütün seansların bilgilerini çekiyoruz.
        Dosya dosya_islemleri = new Dosya();
        Seans sea = new Seans();
        dosya_islemleri.seans_dosya_oku(sea);
        //burada ise dosyadaki bütün vizyondaki filmlerin bilgileirni çekiyoruz.
        Vizyondaki_Filmler vizyonda = new Vizyondaki_Filmler();
        dosya_islemleri.filmler_dosya_oku(vizyonda, 1);
        //burada ise dosyadaki bütün sinema salonlarının verilerini çekiyoruz.
        Sinema_Salonlari sinema_salonu = new Sinema_Salonlari();
        dosya_islemleri.sinema_salonlari_dosya_oku(sinema_salonu);
        //bu kısımda ise tabloya eklemek için kullanacağımız değişkeni tanımlıyoruz. 
        final ObservableList<Seans_Goruntule_Table> data = FXCollections.observableArrayList();

        int sayac = sea.sayac(); // her salon eklendiğinde seansa da bir satır eklendiğinden bu aynı zamanda toplam salonun sayısına da eşit olur.
        int sayac2 = 1; //tabloya yazdırırken tablodaki id'değerini tutmak için bir değişken tanımlıyoruz. bu direk tablodaki id değerini gösteriyor.
        for (int i = 1; i <= sayac; i++) {
            // bu for döngüsü teker teker bütün seansları data değişkenine ekliyor.
            for (int j = 1; j <= 5; j++) { // toplam 5 tane seans olduğu için 5 e kadar (5 dahil) ilerliyor.
                if (sea.film_id(i, j) != -1) {
                    data.addAll(FXCollections.observableArrayList(new Seans_Goruntule_Table(String.valueOf(sayac2), String.valueOf(i), sea.Seans_Yolla(j), vizyonda.Film_Adi(sea.film_id(i, j)))));
                    sayac2++;
                }

            }

        }
        //data değişkenine ekleme işlemi bittikten sonra gerekli tanımlamaları yapıyoruz ve data'yı da tablo ya tanımlıyoruz. Böylece istenilen değerler gösterilmiş oluyor.
        gosterim_id.setCellValueFactory(new PropertyValueFactory("id"));

        salon.setCellValueFactory(new PropertyValueFactory("salon"));

        seans.setCellValueFactory(new PropertyValueFactory("seans"));

        seans.setCellValueFactory(new PropertyValueFactory("seans"));

        film.setCellValueFactory(new PropertyValueFactory("film"));

        seans_table.setItems(data);
    }

    //bu metot seans işlmeleri pane'indeki var olan seansları görüntüle butonunun metodudur. Tek yaptığı şey seans işlemleri için açtığımız pane'in altındaki tablo pane'i açıp akitf olan pane'i kapatıyor.
    @FXML
    private void seans_goruntule(ActionEvent event) {
        seans_home_pane.setVisible(false);
        seans_goruntule_pane.setVisible(true);
        seans_goruntule();

    }

    //seanslar için oluşturudğumuz tablo_pane'deki geri iconunun MouseEvent'ı dır. Yaptığı şey ise seans işlemlerinin ana pane'ini aktif edip. tablo pane'ini kapatmaktaıdr.
    @FXML
    private void seans_goruntule_geri(MouseEvent event) {
        seans_home_pane.setVisible(true);
        seans_goruntule_pane.setVisible(false);
    }

    //burası seans işlmeleri pane'indeki seansları sil butonunun aktif ettiği metotdur. Yaptığı şey ise seanslari_sil pane'i aktif edip seansların ana pane'ini kapatmak.
    @FXML
    private void seanslari_sil(ActionEvent event) {
        //gerekli pane'leri aktif edip, gereksiz pane'leri de pasif hale getiriyoruz. 
        seans_home_pane.setVisible(false);
        seans_sil_pane.setVisible(true);
        seans_silmekten_emin.setVisible(false);
        //en altta ise combobox'ı dolduran metoda yolluyoruz.
        seansi_getir();
    }

    //seansi_sil deki combobox'ı dolduran metot. Bu metot herhangi bir butonla çalışmamaktadır. Sadece gerekli zamanda seansi_sil pane inin içidneki combobox'ı silip yeniden yazıyor.
    private void seansi_getir() {
        //combobox'ı doldurmadan önce içinde bir değer olmasına karşın combobox'ı temizliyoruz.
        seans_sil_combo.getItems().clear();
        //daha sonra dosyadan veri çekmek için gerekli değişkenleri tanımlıyoruz.
        Dosya dosya_islemleri = new Dosya();
        Seans sea = new Seans();
        Sinema_Salonlari salons = new Sinema_Salonlari();
        Vizyondaki_Filmler vizyonda = new Vizyondaki_Filmler();
        //gerekli değişkenleri tanımladıktan sonra dosyadan verileri çekiyoruz.
        dosya_islemleri.filmler_dosya_oku(vizyonda, 1);
        dosya_islemleri.sinema_salonlari_dosya_oku(salons);
        dosya_islemleri.seans_dosya_oku(sea);

        int sayac = sea.sayac(); // hem sayacın hem de sinema salonlarının boyutunu hesaplıyoruz(her sinema salonu eklendiğinde seans'a da ekleme işlemi yapılıyor. Her sinema salonu silindiğinde ise senastan da siliniyor. Yani senas'ın boyutu ile sinema salonlarının boyutu aynı.)
        int sayac2 = 1; //burada ise combobox daki değerlerin id'sini göstermek için bir değişken tanımlıyoruz.

        //bu for döngüsü sırasıyla bütün seansları combobox'a ekliyor. 
        for (int i = 1; i <= sayac; i++) {
            for (int j = 1; j <= 5; j++) {
                if (sea.film_id(i, j) != -1) {
                    {
                        String seans = Integer.valueOf(sayac2) + "#" + String.valueOf(i) + "#" + salons.Salon_Name(i) + "#" + sea.Seans_Yolla(j) + "#" + vizyonda.Film_Adi(sea.film_id(i, j));
                        seans_sil_combo.getItems().addAll(seans);
                        sayac2++;
                    }
                }
            }
        }
        //en sonda da combobox'ın açılışta içine ne yazılacağını belirleyip bu metodu sonlandırıyoruz.
        seans_sil_combo.setPromptText("İstediğiniz Seansı Seçiniz");
    }

    //burası senası_sil_pane'in içindeki Seansı Sil butonunun ActionEvent'ı dır. Yaptığı şey kısacası kullanıcı bir değer seçmiş ise silmekten emin misiniz? pane'ini aktif etmek.
    @FXML
    private void seansi_sil(ActionEvent event) {
        //ilk başta kullanıcının seçtiği değeri bir string'e aktarıyoruz.
        String seans = seans_sil_combo.getValue();
        if (seans == null) {
            //eğer string boş ise kullanıcı bir şey seçmemiştir demek oluyor. Kullanıcıya bir uyarı veriyoruz.
            seans_sil_uyari_mesaj.setText("Lütfen Bir Değer Seçiniz.");
        } else {
            //kullanıcı bir değer seçmişse silmekten emin misiniz? pane'ini aktif hale getiriyoruz.
            seans_silmekten_emin.setVisible(true);
        }
    }

    //Bu metot ise Senas_silmekten_emin pane'indeki Seansı Sil butonunun metodu.
    @FXML
    private void seans_sil(ActionEvent event) {
        //Combobox'ın boş olup olmadığını kontrol etmeye gerek yok. Senası Sil butonunun onAction'unda bu kontrol ediliyor ve ona gore bu pane açılıyor. Haliyle bu pane açıldığında boş olma riski yok.
        String silinecek_Seans = seans_sil_combo.getValue();
        //ilk başta dosyadaki verileri çekiyoruz.
        Seans sea = new Seans();
        Dosya dosya_islemleri = new Dosya();
        dosya_islemleri.seans_dosya_oku(sea);
        //daha sonra silinecek değerleri değişkenlere atıyoruz
        int salon_id;
        int seans_id;

        String[] a = silinecek_Seans.split("#");
        salon_id = Integer.valueOf(a[1]);
        seans_id = sea.Seans_id_Yolla(a[3]);
        //atadıktan sonra dosya sınıfındaki gerekli metoda bu değerleri yolluyoruz ve orada işlemimizi yapıyoruz.
        dosya_islemleri.seans_dosya_sil(salon_id, seans_id);
        //işlem tamamlandıktan sonra da combobox'ı yeniden onarıyoruz.
        seansi_getir();
        //en sonda da işlemin başarılı bir şekilde gerçekleştiğini söylüyoruz.
        seans_sil_uyari_mesaj.setText("İşlem Başarılı Bir şekilde Gerçekleşti.");
        seans_silmekten_emin.setVisible(false);

    }

//seansı_silmekten_emin_pane'indeki Vazgeç butonunun metodu. Tek yaptığı şey seansı_silmekten_emin pane'ini kapatıp ekrana işlemden vazgeçildi yazmak.
    @FXML
    private void seans_sil_vazgec(ActionEvent event) {
        seans_silmekten_emin.setVisible(false);
        seans_sil_uyari_mesaj.setText("İşlemden vazgeçildi");
    }

//seansı sil pane'inin içindeki geri iconunun MouseEvent'ı. Yaptığı tek şey seans işlemlerindeki ana pane'i aktif edip seansı_sil pane'i pasif hale getiriyor.
    @FXML
    private void seans_sil_geri(MouseEvent event) {
        seans_silmekten_emin.setVisible(false);
        seans_sil_pane.setVisible(false);
        seans_home_pane.setVisible(true);
    }

    //Seans ekleme işleminde salonları combobox'ın içine yazmaya yarayan metot.
    private void salonlari_goruntule_combo() {
        //ilk başta combobox daah önce dolmuş ise combobox'ı temizlioyruz.
        seans_ekle_salon_combo.getItems().clear();
        //daha sonra dosyadaki verileri okuyoruz.
        Dosya dosya_islemleri = new Dosya();
        Sinema_Salonlari salon = new Sinema_Salonlari();
        dosya_islemleri.sinema_salonlari_dosya_oku(salon);
        //budan sonra ise kaç tane salon olduğunu hesaplıyoruz.
        int sayac = salon.sayac();
        //bu döngü de salon sayısı kadar dönüyor ve dönerkende seans_ekle pane'indeki salonların combobox'ına ekleme yapıyor.
        for (int i = 1; i <= sayac; i++) {
            String salonlar = String.valueOf(i) + "#" + salon.Salon_Name(i);
            seans_ekle_salon_combo.getItems().addAll(salonlar);
        }
        seans_ekle_salon_combo.setPromptText("İstediğiniz Salonu Seçiniz");
    }

    //seans ekle kısmındaki seansların bulunduğu combobox,'ı dolduruoyr.
    private void seanslari_goruntule_combo() {
        //Daha önce combo box dolmuş olaiblir diye ilk başta combobox'ın içini temizliyoruz.
        seans_ekle_seans_combo.getItems().clear();
        //ve teker teker seans saatlerimizi ekliyoruz.
        seans_ekle_seans_combo.getItems().addAll("10.00-12.00");
        seans_ekle_seans_combo.getItems().addAll("12.30-13.30");
        seans_ekle_seans_combo.getItems().addAll("14.00-16.00");
        seans_ekle_seans_combo.getItems().addAll("16.30-18.30");
        seans_ekle_seans_combo.getItems().addAll("19.00-21.00");

        seans_ekle_seans_combo.setPromptText("İstediğiniz Seansı Seçiniz");
    }

    //seans işlemleri pane'indeki senas ekle butonunun metodudur. Tek yaptığı şey seans işlemleri pane'ini kapatıp, seans ekle pane'i aktif ediyor. Ardından da combobox'ı dolduracak metotlara yolluyor.
    @FXML
    private void seans_ekle(ActionEvent event) {
        seans_home_pane.setVisible(false);
        seans_degistirmekten_emin_pane.setVisible(false);
        seans_ekle_pane.setVisible(true);

        filmleri_getir_combo(seans_ekle_film_combo, 1);
        salonlari_goruntule_combo();
        seanslari_goruntule_combo();

    }

    //seans_ekle pane'inin altındaki senas_ekle butonunun metodudur.
    @FXML
    private void seansi_ekle(ActionEvent event) {
        //ilk başta kullanıcının seçtiği verileri String olarak ekliyoruz.
        String film = seans_ekle_film_combo.getValue();
        String salon = seans_ekle_salon_combo.getValue();
        String seans = seans_ekle_seans_combo.getValue();
        //ve Stringler boş mu? Değil mi? diye kontrol ediyoruz. Eğer stringler boş ise kullanıcıya gerekli olan değerleri seçmesi gerektiğini söylüyoruz.
        if ((film == null) || (salon == null) || (seans == null)) {
            seans_ekle_uyari_mesaj.setText("Lütfen Gerekli Olan Değerleri Seçiniz.");
        } else {
            //kullanıcı gerekli olan değerleri seçmiş ise bu else'ye giriyor ve işlemlerimize devam ediyoruz. 
            //ilk başta dosyadan seans verilierini okuyoruz.
            Seans sea = new Seans();
            Dosya dosya_islemleri = new Dosya();
            dosya_islemleri.seans_dosya_oku(sea);

            //ardından gerekli değişkenlere gerekli değerleri atıyoruz.
            String[] a = film.split("|");
            String[] b = salon.split("#");

            int seans_id = sea.Seans_id_Yolla(seans);
            int film_id = Integer.valueOf(a[0]);
            int salon_id = Integer.valueOf(b[0]);
            //eğer kullanıcının eklemek istediği salon ve seans da başka film oynuyor ise kullanıcıya o filmin yerine bu filmin oynatılasmına emin olup olmadığını soran bir pane açıyoruz. Ama daha önceden seans da ve salonda film oynanmıyorsa direk ekleme işlemini yapıyoruz.
            if (sea.film_id(salon_id, seans_id) != -1) {
                //seçilen seans ve salonda başka film varsa değiştirmekten emin pane'i aktif hale getiriyoruz.
                seans_degistirmekten_emin_pane.setVisible(true);
            } else {
                //seçilen salon ve seans da başka film yok ise direk ekleme işlemini yapıyoruz.
                //Seans sınıfındaki add metoduna yolluyoruz ve yeni seanso eklemiş oluyoruz. Ardından da dosyaya kaydetme işlemi uyguluyoruz.
                sea.add(salon_id, seans_id, film_id);
                dosya_islemleri.seans_dosya_yaz(sea);
                //işlem tamamlandıtan sonra ise combobox'ları yeniliyoruz. Ve kullanıcıya işlemin başarılı bir şekilde gerçekleştiğini söylüyoruz.
                filmleri_getir_combo(seans_ekle_film_combo, 1);
                salonlari_goruntule_combo();
                seanslari_goruntule_combo();
                seans_ekle_uyari_mesaj.setText("İşlem Başarılı Bir Şekilde gerçekleştirildi");
            }

        }
    }

    //seans değiştirmekten emin pane'indeki vazgeç butonunun metodu. Yaptığı tek şey ekrana işlemden vazgeçildi yazması ve seas_Degistirmekten_emin_pane'ini kapatmak
    @FXML
    private void seans_degistir_vazgec(ActionEvent event) {
        seans_degistirmekten_emin_pane.setVisible(false);
        seans_ekle_uyari_mesaj.setText("İşlemden vazgeçildi");
    }

    //seans değiştirmekten emin pane'indeki seans değiştir butonunun metodu. Yaptığı işlem var olan seansı değiştirmek.
    @FXML
    private void seans_degistir(ActionEvent event) {
        //ilk başta seçilen değerleri bir string'e aktarıyoruz.
        String film = seans_ekle_film_combo.getValue();
        String salon = seans_ekle_salon_combo.getValue();
        String seans = seans_ekle_seans_combo.getValue();
        //ardından seçilen değerlerin yani combobox'ların boş olup olmadığını kontrol ediyoruz. Eğer boşsa kullanıcıya hata mesajı yazıyoruz. Boş değilse de işlemlerimize devam ediyoruz.
        if ((seans == null) || (salon == null) || (film == null)) {
            seans_degistirmekten_emin_pane.setVisible(false);
            seans_ekle_uyari_mesaj.setText("Lütfen Gerekli Yerleri Doldurunuz.");
        } else {
            //ilk başta dosyadaki bütün seans verilerini okuyoruz.
            Seans sea = new Seans();
            Dosya dosya_islemleri = new Dosya();
            dosya_islemleri.seans_dosya_oku(sea);
            //ardından gerekli değişkenlerei gerkeli değerleri veriyoruz.
            String[] a = film.split("|");
            String[] b = salon.split("#");

            int seans_id = sea.Seans_id_Yolla(seans);
            int film_id = Integer.valueOf(a[0]);
            int salon_id = Integer.valueOf(b[0]);
            //Seans sınıfındaki ikinci add metoduna bir değer yolladığımızda sadece ekleme işlemi yapmıyor aynı zamanda değiştirme işlemini de yapıyor. Şuan burada onu yaptık. Değiştirme işlemini yaptıktan sonra dosyaya veriyi kadydediyoruz.
            sea.add(salon_id, seans_id, film_id);
            dosya_islemleri.seans_dosya_yaz(sea);
            seans_ekle_uyari_mesaj.setText("İşlem Başaruser ılı Bir Şekilde Gerçekleştirildi.");
            seans_degistirmekten_emin_pane.setVisible(false);
        }

    }

    //senas ekle pane'indeki geri iconunun MouseEvent'i. Tek yaptığı tıklandığında seans_ekle pane'i kapatıp senas larin ana pane'ini açmak.
    @FXML
    private void seans_ekle_geri(MouseEvent event) {
        seans_ekle_pane.setVisible(false);
        seans_home_pane.setVisible(true);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Sinema salonu işlemleri burada yapılıyor.
    /*
    
    Biizm belirleidğimize göre toplam 4 tane sinema salonu tipi var. Bu tipler koltuk sayısına göre belirleniyor.
    Tiplerimizin koltuk sayısı şu şekilde: 129,177,265,294. Bunların her biri scene builder'da bir pane tutuyor.
    ve eklenen sinema salonunun koltuk sayısına göre de o pane'leri açıp kapatıyoruz.
    
     */
    //ilk başta sinema salonu için gerekli olan değişkenler tanımlanıyor.
    @FXML
    private Pane sinema_salonlari_home_pane, sinema_salonlari_goruntule_pane, sinema_salonu_ekle_pane, sinema_salonu_duzenle_pane;

    @FXML
    private Label sinema_salonu_goruntule_uyari_mesaj, sinema_salonu_ekle_uyari_mesaj, sinema_salonunu_guncelle_id, sinema_salonu_guncelle_uyari_mesaj;

    @FXML
    private ComboBox<String> sinema_salonlari_goruntule, sinema_salonu_ekle_koltuk_sayisi, sinema_salonlarini_duzenle_combo, sinema_salonlarini_duzenle_koltuk_sayisi;

    @FXML
    private TextField sinema_salonu_ekle_name, sinema_salonu_guncelle_name;

    //combovox'ın içine koltuk sayılarını yazmak için oluşturulan metot. Kısacası parametre olarak hangi combobox gelirse onun içine bizim daha önceden belirlediğimiz koltuk sayılarını yazıyor.
    private void koltuk_sayisi_combo(ComboBox<String> combo, String promp) {
        //ilk olarak içi daha önceden doldurulmuş olabilir diye temizleme işlemi yapıyoruz.
        combo.getItems().clear();
        //Daha sonra ekleme işlemi yapıyoruz ve her koltuk sayısını ekliyoruz.
        combo.getItems().addAll("129");
        combo.getItems().addAll("177");
        combo.getItems().addAll("265");
        combo.getItems().addAll("294");
        //daha sonra parametre olarak gelen değeri de bu combobox'ın başlangıç değeri olarak atıyoruz.
        combo.setPromptText(promp);
    }

    //bu metodun amacı da parametre olarak gele ncombo box'ın içine sinema salonlarını yazdırmak. ve yine parametre olarak gelen Stirng'i de paramtetre olarak gelen combobox'ın açılış yaızıs yapmak
    private void sinema_salonlari_goruntule_combo(ComboBox<String> combo, String promp) {
        //ilk başta dosyadaki sinema salonlarının verilerini okuyoruz.
        Dosya dosya_islemleri = new Dosya();
        Sinema_Salonlari salon = new Sinema_Salonlari();
        dosya_islemleri.sinema_salonlari_dosya_oku(salon);
        //daha sonra okuduğumuz verilere göre kaç tane sinema salonu olduğunu hesaplıyoruz. 
        int sayac = salon.sayac();
        //doldurulumak istenen combobox daha önce doldurulmuş olabilir diye ilk önce temizliyoruz.
        combo.getItems().clear();
        //daha sonrada daha önce hesapladığımız boyut kadar bir döngü oluşturuyoruz ve her bu for her döndüğünde bir değeri ekliyor.
        for (int i = 1; i <= sayac; i++) {
            String salonlar = String.valueOf(i) + "#" + salon.Salon_Name(i);
            combo.getItems().addAll(salonlar);
        }
        //en sonda ise combobox'ın açılışta yazacağı değeri yazıyoruz ve metodu sonlandırıyoruz.
        combo.setPromptText(promp);

    }

    //sinema_salonlari'nin ana pane'i içinde bulunan Sinema salonlarını gör butonunun metodu. ve yaptığı tek şey sinema_salonlarini_goruntule_pane'i aktif hale getiirp combobox'ın içine sinema salonlarını teker teker yazdırmak.
    @FXML
    private void sinema_salonlarini_gor(ActionEvent event) {
        sinema_salonlari_home_pane.setVisible(false);
        sinema_salonlari_goruntule_pane.setVisible(true);

        sinema_salonlari_goruntule_combo(sinema_salonlari_goruntule, "İstediğiniz Salonu Seçiniz");
    }

    //sinema_salonlarini_goruntule_pane'in içinde bulunan Combobox'ın görevini gören Sinema Slaonunu Görüntüle butonunun metodu. Tek yaptığı şey combobox da seçilen sinema salonunun koltuk düzenini görüntülüyor.
    @FXML
    private void sinema_salonunu_goruntule(ActionEvent event) {
        //ilk başta combobox'ın içindeki değeri String'e atıyoruz.
        String salonlar = sinema_salonlari_goruntule.getValue();
        //ve String'in boş olup olmadığını kontrol ediyoruz. Eğer String boş ise de kullanıcıya uyar ımesajı yazıyoruz ve metodu sonlandırıyoruz.
        if (salonlar == null) {
            sinema_salonu_goruntule_uyari_mesaj.setText("Lütfen İstediğiniz Salonu Seçiniz.");
        } else {
            //kullanıcı bir değeri seçmiş ise devam ediyoruz.
            //ilk başta dosyadan var olan sinema salonlarının bilgilerini alıyoruz
            Sinema_Salonlari salon = new Sinema_Salonlari();
            Dosya dosya_islemleri = new Dosya();
            dosya_islemleri.sinema_salonlari_dosya_oku(salon);
            //daha sonra seçilen değeri parçalara ayırıp, gerekli değişkenleri tanımlayıp, içine aktarıyoruz. 
            String[] a = salonlar.split("#");
            int salon_id = Integer.valueOf(a[0]);
            int koltuk_sayisi = salon.Koltuk_Sayisi(salon_id);
            //daha sonra ise koltuk sayısına göre gerekli pane'i aktif hale getiriyoruz.
            switch (koltuk_sayisi) {
                case 129:
                    //Eğer koltuk sayııs 129 ise ilk pane'i açıp diğerlerini kapatıyoruz.
                    salon_bir_pane.setVisible(true);

                    salon_iki_pane.setVisible(false);
                    salon_uc_pane.setVisible(false);
                    salon_dort_pane.setVisible(false);
                    break;
                case 177:
                    //Eğer koltuk sayııs 177 ise ikinci bane'i açıp diğerlerini kapatıyoruz.
                    salon_iki_pane.setVisible(true);

                    salon_bir_pane.setVisible(false);
                    salon_uc_pane.setVisible(false);
                    salon_dort_pane.setVisible(false);
                    break;
                case 265:
                    //Eğer koltuk sayısı 265 ise üçüncü pane'i açıp diğerlerini kapatıyoruz.
                    salon_uc_pane.setVisible(true);

                    salon_bir_pane.setVisible(false);
                    salon_iki_pane.setVisible(false);
                    salon_dort_pane.setVisible(false);
                    break;
                case 294:
                    //Eğer koltuk sayısı 294 ise dördüncü pane'i açıp diğerlerini kapatıyoruz.
                    salon_dort_pane.setVisible(true);

                    salon_bir_pane.setVisible(false);
                    salon_iki_pane.setVisible(false);
                    salon_uc_pane.setVisible(false);
                    break;
                default:
                    break;
            }

        }
    }

    //sinema_Salonlari_goruntule_pane'in içinde bulunan geri iconunun MouseEvent'ı dır. Bu sinema salonlarındaki home pane i açar diğerlerini kapatır.
    @FXML
    private void sinema_salonunu_goruntule_geri(MouseEvent event) {
        sinema_salonlari_home_pane.setVisible(true);

        sinema_salonlari_goruntule_pane.setVisible(false);
        salon_bir_pane.setVisible(false);
        salon_iki_pane.setVisible(false);
        salon_uc_pane.setVisible(false);
        salon_dort_pane.setVisible(false);
    }

    //sinema_Salonu_home_pane'in içinde bulunan yeni sinema salonu ekle butonunun metodudur. Yaptığı tek şey sinema_salonları için oluşturulmuş ekle pane'ini açıp home pane'ini kapatıyor. ve koltuk sayısını combobox'ın içine yazdırıyor.
    @FXML
    private void yeni_sinema_salonu_ekle(ActionEvent event) {
        sinema_salonlari_home_pane.setVisible(false);
        sinema_salonu_ekle_pane.setVisible(true);

        koltuk_sayisi_combo(sinema_salonu_ekle_koltuk_sayisi, "İstediğiniz Koltuk Sayısı...");
    }

    //sinema_Salonu_ekle_pane'in içinde bulunan salonu ekle butonunun metodu. Tek yaptığı şey kullanıcının verdiği bilgiler doğrultusunda yeni sinema salonu eklemek.
    @FXML
    private void salonu_ekle(ActionEvent event) {
        //ilk olarak kullanıcının girdiği bilgileri bir String'e aktarıyoruz.
        String name = sinema_salonu_ekle_name.getText();
        String koltuk_sayisi = sinema_salonu_ekle_koltuk_sayisi.getValue();
        //Ardından kullanının girdiği bilgilerin boş olup olmadığını öğreniyoruz. Yani kullanıcının gerekli yerleri boş bırakıp bırakmadığını kontrol ediyor. Boş bırkatı ise hata mesajımızı yazıp metodu sonlandırıyoruz.
        if ((koltuk_sayisi == null) || (name.length() == 0)) {
            sinema_salonu_ekle_uyari_mesaj.setText("Lütfen Gerekli Yerleri Doldurunuz.");
        } else {

            //daha sonra dosyadaki seans verilerini de okuyoruz. ve ardından yeni bir sinema salonu ekleme işlemi olduğu için de seans ekleme işlemi yapıyoruz. Bunun sebebi bilgi karmaşası olmasını engellemek. 
            //İlk defa oluşan sinema salonunda herhangi bir seans olmayacaktır. Bu yüzden her senası -1 ile dolduruyoruz.
            Seans sea = new Seans();
            Dosya dosya_islemleri = new Dosya();
            dosya_islemleri.seans_dosya_oku(sea);
            sea.add(sea.sayac() + 1, -1, -1, -1, -1, -1);
            dosya_islemleri.seans_dosya_yaz(sea);
            //daha sonra ise sinema_salonunu eklemek için gerekli metoda yolluyoruz. ve kullanıcıya işlemin başarılı olduğunu bildiriyoruz.
            dosya_islemleri.sinema_salonlari_dosya_ekle(name, Integer.valueOf(koltuk_sayisi));
            sinema_salonu_ekle_uyari_mesaj.setText("İşlem Başarılı Bir Şekilde Gerçekleştirildi.");
        }
    }

    //sinema_Salonu_ekle_pane'deki geri iconunun metodudur. Yaptığı tek şey sinema_Salonu_home_pane'i aktif edip. Şuanki pane'i yani sinema_Salonu_ekle_pane'i pasif hale getirmek.
    @FXML
    private void yeni_sinema_salonu_ekle_geri(MouseEvent event) {
        sinema_salonlari_home_pane.setVisible(true);
        sinema_salonu_ekle_pane.setVisible(false);

    }

    //sinema_salonu_home_pane'deki sinema salonunu düzenle sil butonunun metodudur. Yaptığı tek şey sinema_Salonu_home_pane i kaaptıp sinema_Salonu_duzenle_sil_pane'i aktig ediyor ve gerkeli combobox'ları dolduruyor.
    @FXML
    private void sinema_salonu_duzenle_sil(ActionEvent event) {
        sinema_salonlari_home_pane.setVisible(false);
        sinema_salonu_duzenle_pane.setVisible(true);

        sinema_salonlari_goruntule_combo(sinema_salonlarini_duzenle_combo, "İstediğiniz Salonu Seçiniz");
        koltuk_sayisi_combo(sinema_salonlarini_duzenle_koltuk_sayisi, "");
    }

    //sinema_salonu_duzenle_sil_pane'indeki sinema_Salonunu getir botununun metodudur.
    @FXML
    private void sinema_salonunu_getir(ActionEvent event) {
        //ilk olarak dosyadaki sinema_salonlarının bilgilerini bir bağlı listeye aktarıyoruz.
        Dosya dosya_islemleri = new Dosya();
        Sinema_Salonlari salon = new Sinema_Salonlari();
        dosya_islemleri.sinema_salonlari_dosya_oku(salon);
        //daha sonra kullanıcının seçtiği değer iString'e atıyoruz.
        String secilen = sinema_salonlarini_duzenle_combo.getValue();
        //eğer kullanıcı bir değer seçmemiş ise yani String null ise ekrana uyarı mesajı verip programı sonlandırıoruz.
        if (secilen == null) {
            sinema_salonu_guncelle_uyari_mesaj.setText("Lütfen Bir Değer Seçiniz");
        } else {
            //Eğer kullancıı gerekli yerleri seçmiş ise seçilen değeri parçalayıp hepsini gerekli yere yazıyoruz. Ve meotdu sonlandırıyoruz.
            String[] a = secilen.split("#");

            sinema_salonunu_guncelle_id.setText(a[0]);
            int koltuk_sayisi = salon.Koltuk_Sayisi(Integer.valueOf(sinema_salonunu_guncelle_id.getText()));
            sinema_salonlarini_duzenle_koltuk_sayisi.setPromptText(String.valueOf(koltuk_sayisi));
            sinema_salonu_guncelle_name.setText(salon.Salon_Name(Integer.valueOf(sinema_salonunu_guncelle_id.getText())));
        }
    }

    //sinema_Salonu_duzenle_sil pane'deki Sinema_Salonunu sil botununun metodu.
    @FXML
    private void sinema_salonunu_sil(ActionEvent event) {
        //ilk başta seçilen salonun id'sini bir değere atıyoruz.
        int salon_id = Integer.valueOf(sinema_salonunu_guncelle_id.getText());
        //daha sonra seçilen değerin id'si 0 ise yani kullanıcı bir değer seçmemiş ise gerekli hatayı verip metodu sonlandırıyoruz.
        if (salon_id == 0) {
            sinema_salonu_guncelle_uyari_mesaj.setText("Lütfen bir değer seçiniz.");
        } else {
            //eğer kullanıcı verileri doldurmuş ise de dosya sınıfındaki gerekli metodua iD'yi yolluyoruz ve sinemas salonu silinmiş oluyor.
            Dosya dosya_islemleri = new Dosya();
            int control = dosya_islemleri.sinema_salonlari_dosya_sil(salon_id);

            //daha sonra yukarıda salonu'silme işlemindeki kotnrol için tanımladığımız control integer değeri 0(sıfır) ise işlem başarısız. 1(bir) işelm başarılı anlamına geliyor. Ve buna göre de gerekli bir kaç işlemi yapıp ekrana yazı yazdırıyoruz.
            if (control == 1) {

                //sinema_Salonu silindiği için seans'taki o sinema salonunun bölümü de silinmeli bu sebeple dosyadaki bütün seans verileirni okuyup gerkeli metoda silme işlemini yaptırıyoruz.
                Seans sea = new Seans();
                dosya_islemleri.seans_dosya_oku(sea);
                sea.remove2(salon_id);
                dosya_islemleri.seans_dosya_yaz(sea);

                //sinema_Salonu_duzenle_sil_pane'Deki bütün yerleri güncelliyoruz.
                sinema_salonlari_goruntule_combo(sinema_salonlarini_duzenle_combo, "İstediğiniz Salonu Seçiniz");
                koltuk_sayisi_combo(sinema_salonlarini_duzenle_koltuk_sayisi, "");
                sinema_salonu_guncelle_name.setText("");
                sinema_salonunu_guncelle_id.setText("");
                //en sonda da işlemin başarılı bir şekilde gerçekleştiğini yazıoyruz.
                sinema_salonu_guncelle_uyari_mesaj.setText("İşlem Başarılı Bir şekilde Yapıldı");

            } else {
                //eğer bir hata meydana gelmiş ise de hatayı ekrana yazdırıyoruz.
                sinema_salonu_guncelle_uyari_mesaj.setText("Bir hata meydana geldi(appController.sinema_salonunu_sil(ActionEvent event))");
            }
        }

    }

    //Sinema_Salonu_guncelle_sil pane'in içinde bulunan Sinema Salonunu Güncelle butonunun metodu. Tek yaptığı şey kullanıcnın veridği verilere göre salon bilgilerini güncelleyip dosyaya yazmak.
    @FXML
    private void sinema_salonunu_guncelle(ActionEvent event) {
        //ilk olarak kullanıcının seçtiği sinema salonunu kontrol ediyoruz. Eğer bir sinema salonu seçmemiş ise uyarı verip metodu sonlandıeıyoruz.
        String secilen = sinema_salonlarini_duzenle_combo.getValue();
        if (secilen == null) {
            sinema_salonu_guncelle_uyari_mesaj.setText("Lütfen Bir Değer Seç!");
        } else {
            //Ardından da gerekli yerlerdeki bilgileri Stirng'e aktarıyoruz ve ardından da bu string'in boş olup olmadığına yani kullanının gerekli yerleri doldurup doldurmadığına bakıyoruz.Eğer kullanıcı gerekli yerleri doldurmamış ise hata verip metodu sonlandırıyoruz.
            String name = sinema_salonu_guncelle_name.getText();
            if (name.length() == 0) {
                sinema_salonu_guncelle_uyari_mesaj.setText("Lütfen Gerekli Yerleri Doldurunuz.");
            } else {
                //Kullanıcı bütün istenilen verileri girmiş ise bu else ye giriyor ve işlemlerimizi yapmaya devam ediyoruz.
                //ilk olarak dosyada yazan sinema salonlarının verilerini alıyoruz ve bir bağlı listeye aktarıyoruz.
                Dosya dosya_islemleri = new Dosya();
                Sinema_Salonlari salon = new Sinema_Salonlari();
                dosya_islemleri.sinema_salonlari_dosya_oku(salon);
                //ardından girilen bilgilere göre koltuk sayısı seçilmemiş ise seçilen sinema salonunun koltuk sayısı aynı kalıyor, değiştirilmiyor. Ancak koltuk sayısı seçilmiş ise seçilen koltuk sayısı seçilen sinema salonunun koltuk sayısı olarak ayarlanıyor.
                String secilen_koltuk_sayisi = sinema_salonlarini_duzenle_koltuk_sayisi.getValue();
                int koltuk_sayisi;
                if (secilen_koltuk_sayisi == null) {
                    //seçilen koltuk sayısı null ise kullanıcı onu değiştirmek istemiyor anlamına geliyor ve koltuk sayısını şuanki koltuk sayısı olara kayarlıyoruz.
                    koltuk_sayisi = salon.Koltuk_Sayisi(Integer.valueOf(sinema_salonunu_guncelle_id.getText()));
                } else {
                    //kullanıcı bir koltuk sayısı seçmiş ise kullanıcının seçtiği koltuk sayısını alıp bir integer değere atıyoruz. 
                    koltuk_sayisi = Integer.valueOf(secilen_koltuk_sayisi);
                }
                //ardından da gerekli olan metoda gerekli değişkenleri gönderiyoruz. ve gelen sonucu bir integer değere aktarıyoruz. gelen değer 0(sıfır) ise işlem başarısız, 1(bir) ise işlem başarılı anlamına geliyor. Ve buna göre de ekrana yazı yazdırıyoruz.
                int control = dosya_islemleri.sinema_salonlari_dosya_guncelle(Integer.valueOf(sinema_salonunu_guncelle_id.getText()), name, koltuk_sayisi);

                if (control == 1) {
                    //eğer işlem başarılı olursa. en üstte bulunan sinema_salonunu_goruntule_combo'nun içeriğini güncelliyoruz ve kullanıcıya işlemin başarılı olduğunu söylüyoruz
                    sinema_salonlari_goruntule_combo(sinema_salonlarini_duzenle_combo, "İstediğiniz salonu seçiniz");
                    sinema_salonu_guncelle_uyari_mesaj.setText("İşlem Başarılı bir şekilde gerçekleştirildi");
                } else {
                    //işlem başarısız ise kullanıcıya işlemin başarısız olduğunu söylüyoruz.
                    sinema_salonu_guncelle_uyari_mesaj.setText("Bir hata meydana geldi");
                }
            }
        }
    }

    //Sinema_salonunu_guncelle_sil_pane'in sol üst köşesinde bulunan geri iconunun metodu. Yaptığı tek şey sinmea_salonları_duzenle_Sil_pane'i pasif hale getirip. sinema_Salonları_hom_pane'i de aktif hale getirmek.
    @FXML
    private void sinema_salonu_duzenle_sil_geri(MouseEvent event) {
        sinema_salonu_duzenle_pane.setVisible(false);
        sinema_salonlari_home_pane.setVisible(true);
    }

}
