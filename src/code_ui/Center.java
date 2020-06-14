package code_ui;

import Table.*;
import code.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Center implements Initializable {

    //////////////////////////////////////////////////////////////////////////
    //hem appController da hem de appStandart_userController da karışık ve ortak olanlar için ayrılan alan
    protected boolean sifre_gosterim = false;

    protected boolean a = false;

    @FXML
    protected TextField user_name, user_mail;

    @FXML
    protected Pane home_page, settings_pane;

    @FXML
    protected TextField tf_user_password;

    @FXML
    protected PasswordField user_password;

    @FXML
    protected Pane gizli, acik;

    @FXML
    protected Label guncelle_mesaj;

    //program da aktif olarak çalışan paneler
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    //bu method şifreyi gösterme işine yarıyor. Kullanıcı şifreyi göster simgesine tıklarsa passwordfield'ın buludnuğu hbox'ı kapatıp textfield'ın bulunduğu hbox'ı açıyor ve passwordfiled'Dan veriyi alıp kendine yazıyor.
    @FXML
    protected void goster(MouseEvent event) {
        acik.setVisible(true);
        sifre_gosterim = true;
        gizli.setVisible(false);
        tf_user_password.setText(user_password.getText());

    }

    //bu method şifreyi gizleme işine yarıyor. Kullanıcı şifreyi gizle simgesine tıklarsa textfield'ın buludnuğu hbox'ı kapatıp passwordfield'ın bulunduğu hbox'ı açıyor ve textfiled'dan veriyi alıp kendine yazıyor.
    @FXML
    protected void gizle(MouseEvent event) {
        gizli.setVisible(true);
        sifre_gosterim = false;
        acik.setVisible(false);
        user_password.setText(tf_user_password.getText());
    }

    //bu metot ise kullnıcının çıkış yapmasına olanak sağlıyor. Yani kısacası app_standart_user.fxml ile app.fxml'i kapatıp login.fxml'i açıyor.
    @FXML
    protected void sign_out(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/login.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();

        Node node = (Node) event.getSource();
        Stage stage2 = (Stage) node.getScene().getWindow();
        stage2.close();
    }

    //setting pane'in içinde bulunan Güncelle mbutonunun metodudur. Yaptığı şey kısaca kullanıcının verilerini textfield gibi yerlerden alıp dosyaya kaydetme işlevini görüyor.
    @FXML
    protected void guncelle(ActionEvent event) {
        //Bu metot ayarlar kısmındaki Güncelle butonunun işlevini görmektedir. Yani kısacası Bu butona basıldığında kullanıcıların verileri isteklerine göre değişebiliyor.

        //Dosya işlemlerini yapabilmek adına bir dosya nesnesi oluşturuluyor.
        Dosya dosya_islemleri = new Dosya();

        //Bu 2 satır var olan kullanıcıları bir bağlı listeye aktarıyor (dosya işlemleri ile)
        User user_list = new User();
        user_list = dosya_islemleri.user_dosya_oku(user_list);

        //Her giriş yapıldığında. Giriş yapan kullanıcının id'sini bir txt dosyasında tutuluyor(bilgi.txt) oradan en son giriş yapan kullanıcının id'sine erişiliyor. 
        int user_id = dosya_islemleri.bilgi_dosya_oku();

        //bu if ve else gerekli olan TextField ve PasswordField'lerin dolu olup olmadığını kontrol ediyor. Eğer doldurulması gereken bir yeri doldurmamışsa kullanıcı o zaman if'in içine giriyor ve bir uyarı veriyor. Doldurmuşsa da else'nin içine giriyor ve işlemleri yapyıor.
        if ((user_name.getText().length() == 0) || (user_mail.getText().length() == 0)) {
            guncelle_mesaj.setText("Lütfen Gerekli Yerleri Doldurunuz.");

        } else if ((user_password.getText().length() == 0) && (sifre_gosterim == false)) {
            guncelle_mesaj.setText("Lütfen Gerekli Yerleri Doldurunuz.");
        } else if ((sifre_gosterim == true) && (tf_user_password.getText().length() == 0)) {
            guncelle_mesaj.setText("Lütfen Gerekli Yerleri Doldurunuz.");
        } else {
            //Kullanıcının TextField ve PasswordFiled'lere yazdığı verileri bir String'e aktarıyoruz.
            String name = user_name.getText();
            String mail = user_mail.getText();
            String password = null;
            if (sifre_gosterim == false) {
                password = user_password.getText();
            } else {
                password = tf_user_password.getText();
            }

            //yukarıda aldığımız kullanıcının girdiği bilgileri User adlı sınıfın içindeki metoda yolluyor. Bu metodun yaptığı işlevi ksaca anlatmak gerekirse. Yapılan değiişikliği ilk önce bağlı listede değiştiriyor ardından ise bunu dosyaya yazıp kalıcı hale getiriyor. 
            int control = user_list.guncelle(user_id, name, mail, password, user_list);

            //Az önce gönderdiğimiz metot bir değer yolluyor bu değer 1 ise işlem herhangi bir hataya uğramadan başarılı bir şekilde gerçekleştiğini yazıyor. Eğer başarılı bir şekilde gerçekleşmiyor ise de Hata meydana gleidğini ekrana yazdırıyor.
            if (control == 1) {
                guncelle_mesaj.setText("İşlem Başarılı Bir Şekilde Gerçekleştirildi");
            } else {
                guncelle_mesaj.setText("Bir Hata Meydana Geldi");
            }
        }

    }

    @FXML
    protected void close(MouseEvent event) {

        //Görünüşü güzelleştirmek adına. Üst bölümde bulunan ve otomatik olarak eklenen kapatma simgesi gizlendi. Ve bu sebeple bu metot aracılığıyla manuel olarak kapatma simgesi ekleniyor. 
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void max(MouseEvent event) {
        //Görünüşü güzelleştirmek adına. Üst bölümde bulunan ve otomatik olarak eklenen büyütme simgesi gizlendi. Ve bu sebeple bu metot aracılığıyla manuel olarak büyütme simgesi ekleniyor. 
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        if (a == false) {
            stage.setFullScreenExitHint("Tam moda geçildi çıkmak için 'esc' tuşuna basınız");
            stage.setFullScreen(true);
            a = true;
        } else {
            stage.setFullScreen(false);
            a = false;
        }
    }

    @FXML
    protected void min(MouseEvent event) {
        //Görünüşü güzelleştirmek adına üst bölümde bulunan ve otomatik olarak eklenen aşağı alma simgesi gizlendi. Ve bu sebeple bu metot aracılığıyla manuel olarak aşağı alma simgesi ekleniyor. 
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setIconified(true);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //hem kampanyalar hem de haberler için ortak olarak kullanılan metotlar bu alanda yer almaktadır.
    @FXML
    protected TableView<Duyurular_Table> kampanya_table;

    @FXML
    protected TableColumn<Duyurular_Table, String> kampanya_id, kampanya_date, kampanya_title, kampanya;

    @FXML
    protected TableView<Duyurular_Table> haber_table;

    @FXML
    protected TableColumn<Duyurular_Table, String> haber_id, haber_name, haber_date, haber;

    @FXML
    protected Pane haberler_pane, kampanyalar_pane;

    //bu metot parametre olarak gelen değere göre(0(sıfır) ise haberler,1(bir) ise kampanyalar) gerekli tabloyu doldurmaya yarıyor.
    protected void duyuru_table(int islem) {
        //ilk başta duyurular sınıfına ait bir referans oluşturuyoruz. Daha sonra gelen işleme göre bunu tanımlayacağız
        Duyurular duyuru;
        switch (islem) {
            //bu switch-case gelene integer değişkenini kontrol ediyor. Eğer 0(sıfır) ise haber işlemlerini, 1(bir) ise de kampanya işlemlerini yapıyor.
            case 0:
                //buraya girdiyse haber işlemier yapılacak
                //ilk olarak dosyadaki bütün haberlerin verilerini çekiyoru
                duyuru = new Haberler();
                Haberler haber = (Haberler) duyuru;
                Dosya dosya_islemleri = new Dosya();
                dosya_islemleri.duyurular_dosya_oku(haber, 0);
                //bundan sonra tabloya tanıtacağımız arraylist'i tanımlıyoruz.
                final ObservableList<Duyurular_Table> data = FXCollections.observableArrayList();
                //kaç tane haber olduğunu öğrenip bunu bir değişkende tutuyoruz.
                int sayac = haber.sayac();

                //daha sonra kaç tane haber varsa o büyüklükte bir döngü yaratıp bütün değerleri içine aktarıyoruz.
                for (int i = 1; i <= sayac; i++) {
                    data.addAll(FXCollections.observableArrayList(new Duyurular_Table(String.valueOf(i), haber.Date(i), haber.Title(i), haber.Duyuru(i))));
                }

                //en sonda da gerekli tanımlamaları yapıyoruz. ve işlemimizi tamamlıyoruz.
                haber_id.setCellValueFactory(new PropertyValueFactory("id"));
                haber_name.setCellValueFactory(new PropertyValueFactory("Title"));
                haber_date.setCellValueFactory(new PropertyValueFactory("Date"));
                this.haber.setCellValueFactory(new PropertyValueFactory("Duyuru"));

                haber_table.setItems(data);
                break;
            case 1:
                //buraya girdiyse kampanya işlemier yapılacak
                //ilk olarak dosyadaki bütün kampanyaların verilerini çekiyoruz
                duyuru = new Kampanyalar();
                Kampanyalar kampanya = (Kampanyalar) duyuru;
                Dosya dosya_islemleri2 = new Dosya();
                dosya_islemleri2.duyurular_dosya_oku(kampanya, 1);
                //bundan sonra tabloya tanıtacağımız arraylist'i tanımlıyoruz.
                final ObservableList<Duyurular_Table> data2 = FXCollections.observableArrayList();
                //kaç tane haber olduğunu öğrenip bunu bir değişkende tutuyoruz.
                int sayac2 = kampanya.sayac();
                //daha sonra kaç tane haber varsa o büyüklükte bir döngü yaratıp bütün değerleri içine aktarıyoruz.
                for (int i = 1; i <= sayac2; i++) {
                    data2.addAll(FXCollections.observableArrayList(new Duyurular_Table(String.valueOf(i), kampanya.Date(i), kampanya.Title(i), kampanya.Duyuru(i))));
                }
                //en sonda da gerekli tanımlamaları yapıyoruz. ve işlemimizi tamamlıyoruz.
                kampanya_id.setCellValueFactory(new PropertyValueFactory("id"));
                kampanya_title.setCellValueFactory(new PropertyValueFactory("Title"));
                kampanya_date.setCellValueFactory(new PropertyValueFactory("Date"));
                this.kampanya.setCellValueFactory(new PropertyValueFactory("Duyuru"));

                kampanya_table.setItems(data2);
                break;
            default:
                System.out.println("Hata(Center(duyuru_table))");
                break;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Bu kısım hem eski filmler için hem de vizyondaki filmler için ortak olan kısımdır.
    //ilk başta gerekli olan değişkenleri tanımlıyoruz.
    @FXML
    public Pane eski_film_pane;

    @FXML
    private TableView<Filmler_Table> eski_film_table;

    @FXML
    private TableColumn<Filmler_Table, String> eski_film_id, eski_film_date, eski_film_title;

    @FXML
    public Pane vizyondaki_filmler_pane;

    @FXML
    private TableView<Filmler_Table> vizyonda_table;

    @FXML
    private TableColumn<Filmler_Table, String> vizyonda_id, vizyonda_date, vizyonda_title;

    //Bu metot parametre olarak gelen combobox'ın içine (gelen işleme göre) vizyondaki filmleri ya da eski filmleri yazmaktadır. işlem 0 gelirse eski filmleri, 1 gelirse de vizyındaki filmleri yazmaktadır. 
    protected void filmleri_getir_combo(ComboBox<String> combo, int islem) {
        //ilk başta dosyadaki verileri çekmek adına dosya sınıfı referansı ile bir değişken oluşturuyoruz.
        Dosya dosya_islemleri = new Dosya();
        //kalıtım aracılığıyla daha sonradan tanımlanacak bir referans oluşturuyoruz.
        Filmler film;
        //parametre olarak gelen combobox daha önceden doldurulmuş olabilir diye içini temizliyoruz.
        combo.getItems().clear();
        //bu if-else gelen işleme değişkenine göre işlemleri yapmak için yazıldı. Eğer islem =0 ise eski filmleri, islem = 1 ise vizyondaki filmleri combobox'a yazdırıypruz.
        if (islem == 0) {
            //bu alan girmiş ise eski filmlerin işlemlerini yapyıyoruz demektir.
            //yukarıda tanımladığımız referaransı ile ve eski filmler sınıfı ile bir değişken oluşturuyorz
            film = new Eski_Filmler();
            //kalıtım aracılığıyla Eski filmler referansını filmler referansına bağlıyoruz.
            Eski_Filmler eski = (Eski_Filmler) film;
            //daha sonra dosyadaki eski filmleri teker teker okuyoruz.
            dosya_islemleri.filmler_dosya_oku(eski, 0);
            //kaç tane eski film varsa bunu bir değişkende sayı olarak tutuyoruz.
            int sayac = eski.sayac();
            //eski filmlerin boyutu kadar bir döngü yaratıyoruz. ve bu döngü her döndüğünde bir eski filmi combobox'a ekliyor.
            for (int i = 1; i <= sayac; i++) {
                //her döngüde combobox'a eklenecek olan verileri bir strin'e aktarıyoruz ve ardından combobox'ın içine bu string'i ekliyoruz.
                String filmler = String.valueOf(i) + " | " + eski.Date(i) + " | " + eski.Title(i);
                combo.getItems().addAll(filmler);
            }
            //daha sonra combobox'ın açılıştaki değerini tanımlıyoruz.
            combo.setPromptText("İstediğiniz Filmi Seçiniz");
        } else if (islem == 1) {
            //bu alan girmiş ise vizyondaki filmlerin işlemlerini yapyıyoruz demektir.
            //yukarıda tanımladığımız referaransı ile ve vizyondaki filmler sınıfı ile bir değişken oluşturuyorz.
            film = new Vizyondaki_Filmler();
            //kalıtım aracılığıyla Vizyondaki filmler referansını filmler referansına bağlıyoruz.
            Vizyondaki_Filmler vizyonda = (Vizyondaki_Filmler) film;
            //daha sonra dosyadan vizyondaki filmleri teker teker okuyoruz.
            dosya_islemleri.filmler_dosya_oku(vizyonda, 1);
            //kaç tane vizyonda film varsa bunu bir değişkende sayı olarak tutuyoruz.
            int sayac = vizyonda.sayac();
            //eski filmlerin boyutu kadar bir döngü yaratıyoruz. ve bu döngü her döndüğünde bir vizyondaki filmi combobox'a ekliyor.
            for (int i = 1; i <= sayac; i++) {
                //her döngüde combobox'a eklenecek olan verileri bir strin'e aktarıyoruz ve ardından combobox'ın içine bu string'i ekliyoruz.
                String Filmler = String.valueOf(i) + " | " + vizyonda.Date(i) + " | " + vizyonda.Title(i);
                combo.getItems().addAll(Filmler);
            }
            combo.setPromptText("İstediğiniz Filmi Seçiniz"); // vizyondaki_filmleri_goster
        }
    }

    //bu metot parametre olarak gelen değere göre(0(sıfır) ise eski filmler,1(bir) ise vizyondaki filmler) gerekli tabloyu doldurmaya yarıyor.
    protected void filmler_table(int islem) {
        //ilk başta filmler sınıfına ait bir referans oluşturuyoruz. Daha sonra gelen işleme göre bunu tanımlayacağız.
        Filmler film;
        switch (islem) {
            //bu switch-case gelen integer değişkenini kontrol ediyor. Eğer 0(sıfır) ise eski film işlemlerini, 1(bir) ise de vizyondaki film işlemlerini yapıyor.
            case 0:
                //buraya girdiyse eski film işlemier yapılacak
                //ilk olarak referansını tanımladığımız filmler referansını eski filmler sınıfına bağlıyoruz ve eski filmler değişkenine tanımlıyoruz.
                film = new Eski_Filmler();
                Eski_Filmler eski = (Eski_Filmler) film;
                //daha sonra olarak dosyadaki bütün eski filmlerin verilerini çekiyoruz.
                Dosya dosya_islemleri = new Dosya();
                dosya_islemleri.filmler_dosya_oku(eski, 0);
                //daha sonra tabloda göstereceğimiz  arraylist'i tanımlıyoruz. 
                final ObservableList<Filmler_Table> data = FXCollections.observableArrayList();
                //burada ise kaç tane eski film olduğunu tespit edip bunun boyutunu bir değişkende tutuyoruz.
                int sayac = eski.sayac();
                //daha sonra eski filmlerin boyutu kadar bir döngü yaratıoyruz ve her dönüşte data arraylistine bir eski filmi ekliyoruz. Döngü tamamlandığında ise bütün eski filmleri eklemiş oluyoruz.
                for (int i = 1; i <= sayac; i++) {
                    data.addAll(FXCollections.observableArrayList(new Filmler_Table(String.valueOf(i), eski.Date(i), eski.Title(i))));
                }

                //en sonda da gerekli tanımlamaları yapıp metodu sonlanırıyoruz.
                eski_film_id.setCellValueFactory(new PropertyValueFactory("id"));
                eski_film_date.setCellValueFactory(new PropertyValueFactory("Date"));
                eski_film_title.setCellValueFactory(new PropertyValueFactory("Title"));

                eski_film_table.setItems(data);

                break;
            case 1:
                //buraya girdiyse vizyondaki film işlemier yapılacak
                //ilk olarak referansını tanımladığımız filmler referansını vizyondaki filmler sınıfına bağlıyoruz ve vizyondaki filmler değişkenine tanımlıyoruz.
                film = new Vizyondaki_Filmler();
                Vizyondaki_Filmler vizyonda = (Vizyondaki_Filmler) film;
                //daha sonra olarak dosyadaki bütün eski filmlerin verilerini çekiyoruz.
                Dosya dosya_islemleri2 = new Dosya();
                dosya_islemleri2.filmler_dosya_oku(vizyonda, 1);
                //daha sonra tabloda göstereceğimiz  arraylist'i tanımlıyoruz. 
                final ObservableList<Filmler_Table> data2 = FXCollections.observableArrayList();
                //burada ise kaç tane eski film olduğunu tespit edip bunun boyutunu bir değişkende tutuyoruz.
                int sayac2 = vizyonda.sayac();
                //daha sonra eski filmlerin boyutu kadar bir döngü yaratıoyruz ve her dönüşte data arraylistine bir eski filmi ekliyoruz. Döngü tamamlandığında ise bütün eski filmleri eklemiş oluyoruz.
                for (int i = 1; i <= sayac2; i++) {
                    data2.addAll(FXCollections.observableArrayList(new Filmler_Table(String.valueOf(i), vizyonda.Date(i), vizyonda.Title(i))));
                }
                //en sonda da gerekli tanımlamaları yapıp metodu sonlanırıyoruz.
                vizyonda_id.setCellValueFactory(new PropertyValueFactory("id"));
                vizyonda_date.setCellValueFactory(new PropertyValueFactory("Date"));
                vizyonda_title.setCellValueFactory(new PropertyValueFactory("Title"));

                vizyonda_table.setItems(data2);
                break;
            default:
                System.out.println("Hata(Center(filmler table))");
                break;
        }
    }
    /////////////////////////////////////////////////////// ///////////////////////////////////////////////
    //Sinema Salnları için ortak olan değişkenler burada bulunuyor.
    //bu alanda sadece değişkenler tanımlanmıştır. Çünü hem appController'da hem de app_standart_userController'da ortak bir metot bulnmamaktadır ancka ortak değişkenler bulunmaktadır. 
    @FXML
    public Pane sinema_salonlari_pane, salon_bir_pane, salon_iki_pane, salon_uc_pane, salon_dort_pane;
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //bu alan scene builderdaki 1.salona denk gelmektedir. ve 129 tane koltuk bulunmaktadır.
    //Not: Sadece buradaki değişkenler ve metotların ne olduğu açıklanmıştır. Daha aşağıdaki salonlar buraya benzediği için açıklamalar bulunmamaktadır.

    //ilk başta tanımlamamız gereken butonları tanımlıyoruz (Her bir buton her bir koltuğu temsil etmekteidr.)
    @FXML
    protected Button a1_1, a2_1, a3_1, a4_1, a5_1, a6_1, a7_1, a8_1, a9_1, a10_1;

    @FXML
    protected Button b1_1, b2_1, b3_1, b4_1, b5_1, b6_1, b7_1, b8_1, b9_1, b10_1;

    @FXML
    protected Button c1_1, c2_1, c3_1, c4_1, c5_1, c6_1, c7_1, c8_1, c9_1, c10_1;

    @FXML
    protected Button d1_1, d2_1, d3_1, d4_1, d5_1, d6_1, d7_1, d8_1, d9_1, d10_1, d11_1;

    @FXML
    protected Button e1_1, e2_1, e3_1, e4_1, e5_1, e6_1, e7_1, e8_1, e9_1, e10_1, e11_1;

    @FXML
    protected Button f1_1, f2_1, f3_1, f4_1, f5_1, f6_1, f7_1, f8_1, f9_1, f10_1, f11_1;

    @FXML
    protected Button g1_1, g2_1, g3_1, g4_1, g5_1, g6_1, g7_1, g8_1, g9_1, g10_1, g11_1;

    @FXML
    protected Button h1_1, h2_1, h3_1, h4_1, h5_1, h6_1, h7_1, h8_1, h9_1, h10_1, h11_1;

    @FXML
    protected Button i1_1, i2_1, i3_1, i4_1, i5_1, i6_1, i7_1, i8_1, i9_1, i10_1, i11_1;

    @FXML
    protected Button j1_1, j2_1, j3_1, j4_1, j5_1, j6_1, j7_1, j8_1, j9_1, j10_1, j11_1;

    @FXML
    protected Button k1_1, k2_1, k3_1, k4_1, k5_1, k6_1, k7_1, k8_1, k9_1, k10_1, k11_1;

    @FXML
    protected Button l1_1, l2_1, l3_1, l4_1, l5_1, l6_1, l7_1, l8_1, l9_1, l10_1, l11_1;

    //burası her butonun metodunun geldiği yerdir. Bu metot 4 salon içinde ortak olarak burada tanımlanmıştır. Daha fazla kod yazmamak adına bir motot olarak tanımlanmış ve parametre olarak gelen butonu işlemden geçirmekteidr. 
    protected void buton_aktif(Button btn) {
        //bu metot parametre olarak gelen butonun rengini tespit ediyor ve buna göre de kullanıcının işlem yapmasına olanak tanıyor. 
        switch (btn.styleProperty().get()) {
            //bu switch-case butonun rengini araştırıyor.
            case "-fx-background-color: #6fc3b9;":
                //buraya girilmiş ise bizim daha önceden belirlediğimiz renk olduğunu gösteriyor. (Bu renk o koltuğun boş olduğunu gösterir. ve bu butona basılırsa koltuk yeşil renk oluyor ve yeşil renk olduğu için yeşil dosyasına ekleme işlemi yapılıyor.) 
                btn.styleProperty().set("-fx-background-color: green; -fx-text-fill: white");
                Dosya dosya_islemleri = new Dosya();
                dosya_islemleri.yesil_olan_dosya_ekle(btn.getText());
                break;
            case "-fx-background-color: green; -fx-text-fill: white":
                //buraya girilmişse koltuk daha önceden seçilmiş anlamına geliyor ve seçimi iptal etmek için butonu eski haline döndürüyor ve yeşil dosyasındaki veriyi de siliyor..
                btn.styleProperty().set("-fx-background-color: #6fc3b9;");
                Dosya dosya_islemleri2 = new Dosya();
                dosya_islemleri2.yesil_olan_dosya_sil(btn.getText());
                break;
            case "-fx-background-color: red":
                //buryaa girilmiş ise seçilen koltuk daha önce satılmıştır anlamına geliyor ve kullanıcının herhangi bir işlem yapmasına izin vermiyor.
                break;
            default:
                //bu alan girilmiş ise program hatata düşmüş demektir. Bu sebeple butonunun yani koltuğun rengi siyah oluyor.
                System.out.println(btn.styleProperty().get());
                btn.styleProperty().set("-fx-background-color: black; -fx-text-fill: white");
                break;
        }
    }

    //bu koltuğu kontrol ediyor. Bu metot false dönerse koltuk boş, true dönerse koltuk dolu anlamına geliyor.
    protected boolean search(String koltuk, int salon_id, int seans_id) {
        //ilk başta satılan koltukları bulmak için bir bağlı liste oluşturuyoruz.
        Satılan sat = new Satılan();
        //daha sonra parametre oalrak gelen sealon ve seans'lardaki dolu olan koltukları bu bağlı listeye ekliyoruz.
        sat.add(salon_id, seans_id);
        //daha sonra da kaç tane satılan koltuk olduğunu bir integer değerde tutuyoruz.
        int sayac = sat.sayac();
        //kaç tane satılan koltuk var ise o kadar büyüklükte bir döngü oluşturuyoruz ve yine parametre olarak gelen koltuğu kontrol ediyoruz satılmış mı diye. eğer satılmış ise true dönüyor. satılmamış ise döngü bitiyor.
        for (int i = 1; i <= sayac; i++) {

            if (sat.koltuk(i).equals(koltuk)) {

                return true;
            }
        }
        //eğer döngü bitmiş ise yani bir return dönmemiş ise burada return yapıyor ve koltuğun boş olduğunu bilfiriyor.
        return false; // true donerse koltuk dolu, false dönerse koltuk boş oluyor.
    }

    //bu metot yukarıda tanımladığımız metoda gidip her koltuğu teker teker tarıyor ve bu işleme göre de dolu olan koltuklar kırmızı ile işaretleniyor.
    protected void bir_koltuk_dolu(int salon_id, int seans_id) {

        if (search("A1", salon_id, seans_id)) {
            a1_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("A2", salon_id, seans_id)) {
            a2_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("A3", salon_id, seans_id)) {
            a3_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("A4", salon_id, seans_id)) {
            a4_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("A5", salon_id, seans_id)) {
            a5_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("A6", salon_id, seans_id)) {
            a6_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("A7", salon_id, seans_id)) {
            a7_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("A8", salon_id, seans_id)) {
            a8_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("A9", salon_id, seans_id)) {
            a9_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("A10", salon_id, seans_id)) {
            a10_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("B1", salon_id, seans_id)) {
            b1_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("B2", salon_id, seans_id)) {
            b2_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("B3", salon_id, seans_id)) {
            b3_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("B4", salon_id, seans_id)) {
            b4_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("B5", salon_id, seans_id)) {
            b5_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("B6", salon_id, seans_id)) {
            b6_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("B7", salon_id, seans_id)) {
            b7_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("B8", salon_id, seans_id)) {
            b8_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("B9", salon_id, seans_id)) {
            b9_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("B10", salon_id, seans_id)) {
            b10_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("C1", salon_id, seans_id)) {
            c1_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("C2", salon_id, seans_id)) {
            c2_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("C3", salon_id, seans_id)) {
            c3_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("C4", salon_id, seans_id)) {
            c4_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("C5", salon_id, seans_id)) {
            c5_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("C6", salon_id, seans_id)) {
            c6_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("C7", salon_id, seans_id)) {
            c7_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("C8", salon_id, seans_id)) {
            c8_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("C9", salon_id, seans_id)) {
            c9_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("C10", salon_id, seans_id)) {
            c10_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("D1", salon_id, seans_id)) {
            d1_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("D2", salon_id, seans_id)) {
            d2_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("D3", salon_id, seans_id)) {
            d3_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("D4", salon_id, seans_id)) {
            d4_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("D5", salon_id, seans_id)) {
            d5_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("D6", salon_id, seans_id)) {
            d6_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("D7", salon_id, seans_id)) {
            d7_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("D8", salon_id, seans_id)) {
            d8_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("D9", salon_id, seans_id)) {
            d9_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("D10", salon_id, seans_id)) {
            d10_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("D11", salon_id, seans_id)) {
            d11_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("E1", salon_id, seans_id)) {
            e1_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("E2", salon_id, seans_id)) {
            e2_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("E3", salon_id, seans_id)) {
            e3_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("E4", salon_id, seans_id)) {
            e4_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("E5", salon_id, seans_id)) {
            e5_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("E6", salon_id, seans_id)) {
            e6_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("E7", salon_id, seans_id)) {
            e7_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("E8", salon_id, seans_id)) {
            e8_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("E9", salon_id, seans_id)) {
            e9_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("E10", salon_id, seans_id)) {
            e10_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("E11", salon_id, seans_id)) {
            e11_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("F1", salon_id, seans_id)) {
            f1_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("F2", salon_id, seans_id)) {
            f2_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("F3", salon_id, seans_id)) {
            f3_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("F4", salon_id, seans_id)) {
            f4_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("F5", salon_id, seans_id)) {
            f5_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("F6", salon_id, seans_id)) {
            f6_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("F7", salon_id, seans_id)) {
            f7_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("F8", salon_id, seans_id)) {
            f8_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("F9", salon_id, seans_id)) {
            f9_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("F10", salon_id, seans_id)) {
            f10_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("F11", salon_id, seans_id)) {
            f11_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("G1", salon_id, seans_id)) {
            g1_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("G2", salon_id, seans_id)) {
            g2_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("G3", salon_id, seans_id)) {
            g3_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("G4", salon_id, seans_id)) {
            g4_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("G5", salon_id, seans_id)) {
            g5_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("G6", salon_id, seans_id)) {
            g6_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("G7", salon_id, seans_id)) {
            g7_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("G8", salon_id, seans_id)) {
            g8_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("G9", salon_id, seans_id)) {
            g9_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("G10", salon_id, seans_id)) {
            g10_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("G11", salon_id, seans_id)) {
            g11_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("H1", salon_id, seans_id)) {
            h1_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("H2", salon_id, seans_id)) {
            h2_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("H3", salon_id, seans_id)) {
            h3_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("H4", salon_id, seans_id)) {
            h4_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("H5", salon_id, seans_id)) {
            h5_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("H6", salon_id, seans_id)) {
            h6_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("H7", salon_id, seans_id)) {
            h7_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("H8", salon_id, seans_id)) {
            h8_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("H9", salon_id, seans_id)) {
            h9_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("H10", salon_id, seans_id)) {
            h10_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("H11", salon_id, seans_id)) {
            h11_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("I1", salon_id, seans_id)) {
            i1_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("I2", salon_id, seans_id)) {
            i2_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("I3", salon_id, seans_id)) {
            i3_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("I", salon_id, seans_id)) {
            i4_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("I5", salon_id, seans_id)) {
            i5_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("I6", salon_id, seans_id)) {
            i6_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("I7", salon_id, seans_id)) {
            i7_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("I8", salon_id, seans_id)) {
            i8_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("I9", salon_id, seans_id)) {
            i9_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("I10", salon_id, seans_id)) {
            i10_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("I11", salon_id, seans_id)) {
            i11_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("J1", salon_id, seans_id)) {
            j1_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("J2", salon_id, seans_id)) {
            j2_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("J3", salon_id, seans_id)) {
            j3_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("J4", salon_id, seans_id)) {
            j4_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("J5", salon_id, seans_id)) {
            j5_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("J6", salon_id, seans_id)) {
            j6_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("J7", salon_id, seans_id)) {
            j7_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("J8", salon_id, seans_id)) {
            j8_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("J9", salon_id, seans_id)) {
            j9_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("J10", salon_id, seans_id)) {
            j10_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("J11", salon_id, seans_id)) {
            j11_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("K1", salon_id, seans_id)) {
            k1_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("K2", salon_id, seans_id)) {
            k2_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("K3", salon_id, seans_id)) {
            k3_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("K4", salon_id, seans_id)) {
            k4_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("K5", salon_id, seans_id)) {
            k5_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("K6", salon_id, seans_id)) {
            k6_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("K7", salon_id, seans_id)) {
            k7_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("K8", salon_id, seans_id)) {
            k8_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("K9", salon_id, seans_id)) {
            k9_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("K10", salon_id, seans_id)) {
            k10_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("K11", salon_id, seans_id)) {
            k11_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("L1", salon_id, seans_id)) {
            l1_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("L2", salon_id, seans_id)) {
            l2_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("L3", salon_id, seans_id)) {
            l3_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("L4", salon_id, seans_id)) {
            l4_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("L5", salon_id, seans_id)) {
            l5_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("L6", salon_id, seans_id)) {
            l6_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("L7", salon_id, seans_id)) {
            l7_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("L8", salon_id, seans_id)) {
            l8_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("L9", salon_id, seans_id)) {
            l9_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("L10", salon_id, seans_id)) {
            l10_1.styleProperty().set("-fx-background-color: red");

        }
        if (search("L11", salon_id, seans_id)) {
            l11_1.styleProperty().set("-fx-background-color: red");

        }
    }

    @FXML
    protected void a1_1(ActionEvent event) {
        buton_aktif(a1_1);
    }

    @FXML
    protected void a2_1(ActionEvent event) {
        buton_aktif(a2_1);
    }

    @FXML
    protected void a3_1(ActionEvent event) {
        buton_aktif(a3_1);
    }

    @FXML
    protected void a4_1(ActionEvent event) {
        buton_aktif(a4_1);
    }

    @FXML
    protected void a5_1(ActionEvent event) {
        buton_aktif(a5_1);
    }

    @FXML
    protected void a6_1(ActionEvent event) {
        buton_aktif(a6_1);
    }

    @FXML
    protected void a7_1(ActionEvent event) {
        buton_aktif(a7_1);
    }

    @FXML
    protected void a8_1(ActionEvent event) {
        buton_aktif(a8_1);
    }

    @FXML
    protected void a9_1(ActionEvent event) {
        buton_aktif(a9_1);
    }

    @FXML
    protected void a10_1(ActionEvent event) {
        buton_aktif(a10_1);
    }

    @FXML
    protected void b1_1(ActionEvent event) {
        buton_aktif(b1_1);
    }

    @FXML
    protected void b2_1(ActionEvent event) {
        buton_aktif(b2_1);
    }

    @FXML
    protected void b3_1(ActionEvent event) {
        buton_aktif(b3_1);
    }

    @FXML
    protected void b4_1(ActionEvent event) {
        buton_aktif(b4_1);
    }

    @FXML
    protected void b5_1(ActionEvent event) {
        buton_aktif(b5_1);
    }

    @FXML
    protected void b6_1(ActionEvent event) {
        buton_aktif(b6_1);
    }

    @FXML
    protected void b7_1(ActionEvent event) {
        buton_aktif(b7_1);
    }

    @FXML
    protected void b8_1(ActionEvent event) {
        buton_aktif(b8_1);
    }

    @FXML
    protected void b9_1(ActionEvent event) {
        buton_aktif(b9_1);
    }

    @FXML
    protected void b10_1(ActionEvent event) {
        buton_aktif(b10_1);
    }

    @FXML
    protected void c1_1(ActionEvent event) {
        buton_aktif(c1_1);
    }

    @FXML
    protected void c2_1(ActionEvent event) {
        buton_aktif(c2_1);
    }

    @FXML
    protected void c3_1(ActionEvent event) {
        buton_aktif(c3_1);
    }

    @FXML
    protected void c4_1(ActionEvent event) {
        buton_aktif(c4_1);
    }

    @FXML
    protected void c5_1(ActionEvent event) {
        buton_aktif(c5_1);
    }

    @FXML
    protected void c6_1(ActionEvent event) {
        buton_aktif(c6_1);
    }

    @FXML
    protected void c7_1(ActionEvent event) {
        buton_aktif(c7_1);
    }

    @FXML
    protected void c8_1(ActionEvent event) {
        buton_aktif(c8_1);
    }

    @FXML
    protected void c9_1(ActionEvent event) {
        buton_aktif(c9_1);
    }

    @FXML
    protected void c10_1(ActionEvent event) {
        buton_aktif(c10_1);
    }

    @FXML
    protected void d1_1(ActionEvent event) {
        buton_aktif(d1_1);
    }

    @FXML
    protected void d2_1(ActionEvent event) {
        buton_aktif(d2_1);
    }

    @FXML
    protected void d3_1(ActionEvent event) {
        buton_aktif(d3_1);
    }

    @FXML
    protected void d4_1(ActionEvent event) {
        buton_aktif(d4_1);
    }

    @FXML
    protected void d5_1(ActionEvent event) {
        buton_aktif(d5_1);
    }

    @FXML
    protected void d6_1(ActionEvent event) {
        buton_aktif(d6_1);
    }

    @FXML
    protected void d7_1(ActionEvent event) {
        buton_aktif(d7_1);
    }

    @FXML
    protected void d8_1(ActionEvent event) {
        buton_aktif(d8_1);
    }

    @FXML
    protected void d9_1(ActionEvent event) {
        buton_aktif(d9_1);
    }

    @FXML
    protected void d10_1(ActionEvent event) {
        buton_aktif(d10_1);
    }

    @FXML
    protected void d11_1(ActionEvent event) {
        buton_aktif(d11_1);
    }

    @FXML
    protected void e1_1(ActionEvent event) {
        buton_aktif(e1_1);
    }

    @FXML
    protected void e2_1(ActionEvent event) {
        buton_aktif(e2_1);
    }

    @FXML
    protected void e3_1(ActionEvent event) {
        buton_aktif(e3_1);
    }

    @FXML
    protected void e4_1(ActionEvent event) {
        buton_aktif(e4_1);
    }

    @FXML
    protected void e5_1(ActionEvent event) {
        buton_aktif(e5_1);
    }

    @FXML
    protected void e6_1(ActionEvent event) {
        buton_aktif(e6_1);
    }

    @FXML
    protected void e7_1(ActionEvent event) {
        buton_aktif(e7_1);
    }

    @FXML
    protected void e8_1(ActionEvent event) {
        buton_aktif(e8_1);
    }

    @FXML
    protected void e9_1(ActionEvent event) {
        buton_aktif(e9_1);
    }

    @FXML
    protected void e10_1(ActionEvent event) {
        buton_aktif(e10_1);
    }

    @FXML
    protected void e11_1(ActionEvent event) {
        buton_aktif(e11_1);
    }

    @FXML
    protected void f1_1(ActionEvent event) {
        buton_aktif(f1_1);
    }

    @FXML
    protected void f2_1(ActionEvent event) {
        buton_aktif(f2_1);
    }

    @FXML
    protected void f3_1(ActionEvent event) {
        buton_aktif(f3_1);
    }

    @FXML
    protected void f4_1(ActionEvent event) {
        buton_aktif(f4_1);
    }

    @FXML
    protected void f5_1(ActionEvent event) {
        buton_aktif(f5_1);
    }

    @FXML
    protected void f6_1(ActionEvent event) {
        buton_aktif(f6_1);
    }

    @FXML
    protected void f7_1(ActionEvent event) {
        buton_aktif(f7_1);
    }

    @FXML
    protected void f8_1(ActionEvent event) {
        buton_aktif(f8_1);
    }

    @FXML
    protected void f9_1(ActionEvent event) {
        buton_aktif(f9_1);
    }

    @FXML
    protected void f10_1(ActionEvent event) {
        buton_aktif(f10_1);
    }

    @FXML
    protected void f11_1(ActionEvent event) {
        buton_aktif(f11_1);
    }

    @FXML
    protected void g1_1(ActionEvent event) {
        buton_aktif(g1_1);
    }

    @FXML
    protected void g2_1(ActionEvent event) {
        buton_aktif(g2_1);
    }

    @FXML
    protected void g3_1(ActionEvent event) {
        buton_aktif(g3_1);
    }

    @FXML
    protected void g4_1(ActionEvent event) {
        buton_aktif(g4_1);
    }

    @FXML
    protected void g5_1(ActionEvent event) {
        buton_aktif(g5_1);
    }

    @FXML
    protected void g6_1(ActionEvent event) {
        buton_aktif(g6_1);
    }

    @FXML
    protected void g7_1(ActionEvent event) {
        buton_aktif(g7_1);
    }

    @FXML
    protected void g8_1(ActionEvent event) {
        buton_aktif(g8_1);
    }

    @FXML
    protected void g9_1(ActionEvent event) {
        buton_aktif(g9_1);
    }

    @FXML
    protected void g10_1(ActionEvent event) {
        buton_aktif(g10_1);
    }

    @FXML
    protected void g11_1(ActionEvent event) {
        buton_aktif(g11_1);
    }

    @FXML
    protected void h1_1(ActionEvent event) {
        buton_aktif(h1_1);
    }

    @FXML
    protected void h2_1(ActionEvent event) {
        buton_aktif(h2_1);
    }

    @FXML
    protected void h3_1(ActionEvent event) {
        buton_aktif(h3_1);
    }

    @FXML
    protected void h4_1(ActionEvent event) {
        buton_aktif(h4_1);
    }

    @FXML
    protected void h5_1(ActionEvent event) {
        buton_aktif(h5_1);
    }

    @FXML
    protected void h6_1(ActionEvent event) {
        buton_aktif(h6_1);
    }

    @FXML
    protected void h7_1(ActionEvent event) {
        buton_aktif(h7_1);
    }

    @FXML
    protected void h8_1(ActionEvent event) {
        buton_aktif(h8_1);
    }

    @FXML
    protected void h9_1(ActionEvent event) {
        buton_aktif(h9_1);
    }

    @FXML
    protected void h10_1(ActionEvent event) {
        buton_aktif(h10_1);
    }

    @FXML
    protected void h11_1(ActionEvent event) {
        buton_aktif(h11_1);
    }

    @FXML
    protected void i1_1(ActionEvent event) {
        buton_aktif(i1_1);
    }

    @FXML
    protected void i2_1(ActionEvent event) {
        buton_aktif(i2_1);
    }

    @FXML
    protected void i3_1(ActionEvent event) {
        buton_aktif(i3_1);
    }

    @FXML
    protected void i4_1(ActionEvent event) {
        buton_aktif(i4_1);
    }

    @FXML
    protected void i5_1(ActionEvent event) {
        buton_aktif(i5_1);
    }

    @FXML
    protected void i6_1(ActionEvent event) {
        buton_aktif(i6_1);
    }

    @FXML
    protected void i7_1(ActionEvent event) {
        buton_aktif(i7_1);
    }

    @FXML
    protected void i8_1(ActionEvent event) {
        buton_aktif(i8_1);
    }

    @FXML
    protected void i9_1(ActionEvent event) {
        buton_aktif(i9_1);
    }

    @FXML
    protected void i10_1(ActionEvent event) {
        buton_aktif(i10_1);
    }

    @FXML
    protected void i11_1(ActionEvent event) {
        buton_aktif(i11_1);
    }

    @FXML
    protected void j1_1(ActionEvent event) {
        buton_aktif(j1_1);
    }

    @FXML
    protected void j2_1(ActionEvent event) {
        buton_aktif(j2_1);
    }

    @FXML
    protected void j3_1(ActionEvent event) {
        buton_aktif(j3_1);
    }

    @FXML
    protected void j4_1(ActionEvent event) {
        buton_aktif(j4_1);
    }

    @FXML
    protected void j5_1(ActionEvent event) {
        buton_aktif(j5_1);
    }

    @FXML
    protected void j6_1(ActionEvent event) {
        buton_aktif(j6_1);
    }

    @FXML
    protected void j7_1(ActionEvent event) {
        buton_aktif(j7_1);
    }

    @FXML
    protected void j8_1(ActionEvent event) {
        buton_aktif(j8_1);
    }

    @FXML
    protected void j9_1(ActionEvent event) {
        buton_aktif(j9_1);
    }

    @FXML
    protected void j10_1(ActionEvent event) {
        buton_aktif(j10_1);
    }

    @FXML
    protected void j11_1(ActionEvent event) {
        buton_aktif(j11_1);
    }

    @FXML
    protected void k1_1(ActionEvent event) {
        buton_aktif(k1_1);
    }

    @FXML
    protected void k2_1(ActionEvent event) {
        buton_aktif(k2_1);
    }

    @FXML
    protected void k3_1(ActionEvent event) {
        buton_aktif(k3_1);
    }

    @FXML
    protected void k4_1(ActionEvent event) {
        buton_aktif(k4_1);
    }

    @FXML
    protected void k5_1(ActionEvent event) {
        buton_aktif(k5_1);
    }

    @FXML
    protected void k6_1(ActionEvent event) {
        buton_aktif(k6_1);
    }

    @FXML
    protected void k7_1(ActionEvent event) {
        buton_aktif(k7_1);
    }

    @FXML
    protected void k8_1(ActionEvent event) {
        buton_aktif(k8_1);
    }

    @FXML
    protected void k9_1(ActionEvent event) {
        buton_aktif(k9_1);
    }

    @FXML
    protected void k10_1(ActionEvent event) {
        buton_aktif(k10_1);
    }

    @FXML
    protected void k11_1(ActionEvent event) {
        buton_aktif(k11_1);
    }

    @FXML
    protected void l1_1(ActionEvent event) {
        buton_aktif(l1_1);
    }

    @FXML
    protected void l2_1(ActionEvent event) {
        buton_aktif(l2_1);
    }

    @FXML
    protected void l3_1(ActionEvent event) {
        buton_aktif(l3_1);
    }

    @FXML
    protected void l4_1(ActionEvent event) {
        buton_aktif(l4_1);
    }

    @FXML
    protected void l5_1(ActionEvent event) {
        buton_aktif(l5_1);
    }

    @FXML
    protected void l6_1(ActionEvent event) {
        buton_aktif(l6_1);
    }

    @FXML
    protected void l7_1(ActionEvent event) {
        buton_aktif(l7_1);
    }

    @FXML
    protected void l8_1(ActionEvent event) {
        buton_aktif(l8_1);
    }

    @FXML
    protected void l9_1(ActionEvent event) {
        buton_aktif(l9_1);
    }

    @FXML
    protected void l10_1(ActionEvent event) {
        buton_aktif(l10_1);
    }

    @FXML
    protected void l11_1(ActionEvent event) {
        buton_aktif(l11_1);
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Bu bölge scene builder da 2.salona denk gelmektedşr ve 177 tane koltuk bulunmaktadır.
    //burası ile 1.salon'un özellikleri aynı olduğu için burada anlatım yapılmamıştır.
    @FXML
    protected Button a1_2, a2_2, a3_2, a4_2, a5_2, a6_2, a7_2, a8_2, a9_2, a10_2, a11_2, a12_2;

    @FXML
    protected Button b1_2, b2_2, b3_2, b4_2, b5_2, b6_2, b7_2, b8_2, b9_2, b10_2, b11_2, b12_2, b13_2, b14_2;

    @FXML
    protected Button c1_2, c2_2, c3_2, c4_2, c5_2, c6_2, c7_2, c8_2, c9_2, c10_2, c11_2, c12_2, c13_2, c14_2;

    @FXML
    protected Button d1_2, d2_2, d3_2, d4_2, d5_2, d6_2, d7_2, d8_2, d9_2, d10_2, d11_2, d12_2, d13_2, d14_2;

    @FXML
    protected Button e1_2, e2_2, e3_2, e4_2, e5_2, e6_2, e7_2, e8_2, e9_2, e10_2, e11_2, e12_2, e13_2, e14_2;

    @FXML
    protected Button f1_2, f2_2, f3_2, f4_2, f5_2, f6_2, f7_2, f8_2, f9_2, f10_2, f11_2, f12_2, f13_2, f14_2;

    @FXML
    protected Button g1_2, g2_2, g3_2, g4_2, g5_2, g6_2, g7_2, g8_2, g9_2, g10_2, g11_2, g12_2, g13_2, g14_2;

    @FXML
    protected Button h1_2, h2_2, h3_2, h4_2, h5_2, h6_2, h7_2, h8_2, h9_2, h10_2, h11_2, h12_2, h13_2, h14_2;

    @FXML
    protected Button i1_2, i2_2, i3_2, i4_2, i5_2, i6_2, i7_2, i8_2, i9_2, i10_2, i11_2, i12_2, i13_2, i14_2, i15_2, i16_2;

    @FXML
    protected Button j1_2, j2_2, j3_2, j4_2, j5_2, j6_2, j7_2, j8_2, j9_2, j10_2, j11_2, j12_2, j13_2, j14_2, j15_2, j16_2;

    @FXML
    protected Button k1_2, k2_2, k3_2, k4_2, k5_2, k6_2, k7_2, k8_2, k9_2, k10_2, k11_2, k12_2, k13_2, k14_2, k15_2, k16_2;

    @FXML
    protected Button l1_2, l2_2, l3_2, l4_2, l5_2, l6_2, l7_2, l8_2, l9_2, l10_2, l11_2, l12_2, l13_2, l14_2, l15_2, l16_2, l17_2, l18_2, l19_2;

    protected void iki_koltuk_dolu(int salon_id, int seans_id) {
        if (search("A1", salon_id, seans_id)) {
            a1_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("A2", salon_id, seans_id)) {
            a2_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("A3", salon_id, seans_id)) {
            a3_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("A4", salon_id, seans_id)) {
            a4_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("A5", salon_id, seans_id)) {
            a5_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("A6", salon_id, seans_id)) {
            a6_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("A7", salon_id, seans_id)) {
            a7_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("A8", salon_id, seans_id)) {
            a8_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("A9", salon_id, seans_id)) {
            a9_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("A10", salon_id, seans_id)) {
            a10_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("A11", salon_id, seans_id)) {
            a11_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("A12", salon_id, seans_id)) {
            a12_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("B1", salon_id, seans_id)) {
            b1_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("B2", salon_id, seans_id)) {
            b2_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("B3", salon_id, seans_id)) {
            b3_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("B4", salon_id, seans_id)) {
            b4_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("B5", salon_id, seans_id)) {
            b5_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("B6", salon_id, seans_id)) {
            b6_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("B7", salon_id, seans_id)) {
            b7_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("B8", salon_id, seans_id)) {
            b8_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("B9", salon_id, seans_id)) {
            b9_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("B10", salon_id, seans_id)) {
            b10_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("B11", salon_id, seans_id)) {
            b11_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("B12", salon_id, seans_id)) {
            b12_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("B13", salon_id, seans_id)) {
            b13_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("B14", salon_id, seans_id)) {
            b14_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("C1", salon_id, seans_id)) {
            c1_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("C2", salon_id, seans_id)) {
            c2_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("C3", salon_id, seans_id)) {
            c3_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("C4", salon_id, seans_id)) {
            c4_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("C5", salon_id, seans_id)) {
            c5_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("C6", salon_id, seans_id)) {
            c6_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("C7", salon_id, seans_id)) {
            c7_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("C8", salon_id, seans_id)) {
            c8_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("C9", salon_id, seans_id)) {
            c9_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("C10", salon_id, seans_id)) {
            c10_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("C11", salon_id, seans_id)) {
            c11_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("C12", salon_id, seans_id)) {
            c12_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("C13", salon_id, seans_id)) {
            c13_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("C14", salon_id, seans_id)) {
            c14_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("D1", salon_id, seans_id)) {
            d1_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("D2", salon_id, seans_id)) {
            d2_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("D3", salon_id, seans_id)) {
            d3_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("D4", salon_id, seans_id)) {
            d4_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("D5", salon_id, seans_id)) {
            d5_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("D6", salon_id, seans_id)) {
            d6_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("D7", salon_id, seans_id)) {
            d7_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("D8", salon_id, seans_id)) {
            d8_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("D9", salon_id, seans_id)) {
            d9_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("D10", salon_id, seans_id)) {
            d10_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("D11", salon_id, seans_id)) {
            d11_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("D12", salon_id, seans_id)) {
            d12_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("D13", salon_id, seans_id)) {
            d13_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("D14", salon_id, seans_id)) {
            d14_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("E1", salon_id, seans_id)) {
            e1_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("E2", salon_id, seans_id)) {
            e2_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("E3", salon_id, seans_id)) {
            e3_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("E4", salon_id, seans_id)) {
            e4_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("E5", salon_id, seans_id)) {
            e5_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("E6", salon_id, seans_id)) {
            e6_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("E7", salon_id, seans_id)) {
            e7_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("E8", salon_id, seans_id)) {
            e8_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("E9", salon_id, seans_id)) {
            e9_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("E10", salon_id, seans_id)) {
            e10_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("E11", salon_id, seans_id)) {
            e11_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("E12", salon_id, seans_id)) {
            e12_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("E13", salon_id, seans_id)) {
            e13_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("E14", salon_id, seans_id)) {
            e14_2.styleProperty().set("-fx-background-color: red");

        }

        if (search("F1", salon_id, seans_id)) {
            f1_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("F2", salon_id, seans_id)) {
            f2_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("F3", salon_id, seans_id)) {
            f3_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("F4", salon_id, seans_id)) {
            f4_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("F5", salon_id, seans_id)) {
            f5_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("F6", salon_id, seans_id)) {
            f6_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("F7", salon_id, seans_id)) {
            f7_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("F8", salon_id, seans_id)) {
            f8_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("F9", salon_id, seans_id)) {
            f9_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("F10", salon_id, seans_id)) {
            f10_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("F11", salon_id, seans_id)) {
            f11_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("F12", salon_id, seans_id)) {
            f12_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("F13", salon_id, seans_id)) {
            f13_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("F14", salon_id, seans_id)) {
            f14_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("G1", salon_id, seans_id)) {
            g1_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("G2", salon_id, seans_id)) {
            g2_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("G3", salon_id, seans_id)) {
            g3_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("G4", salon_id, seans_id)) {
            g4_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("G5", salon_id, seans_id)) {
            g5_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("G6", salon_id, seans_id)) {
            g6_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("G7", salon_id, seans_id)) {
            g7_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("G8", salon_id, seans_id)) {
            g8_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("G9", salon_id, seans_id)) {
            g9_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("G10", salon_id, seans_id)) {
            g10_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("G11", salon_id, seans_id)) {
            g11_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("G12", salon_id, seans_id)) {
            g12_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("G13", salon_id, seans_id)) {
            g13_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("G14", salon_id, seans_id)) {
            g14_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("H1", salon_id, seans_id)) {
            h1_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("H2", salon_id, seans_id)) {
            h2_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("H3", salon_id, seans_id)) {
            h3_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("H4", salon_id, seans_id)) {
            h4_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("H5", salon_id, seans_id)) {
            h5_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("H6", salon_id, seans_id)) {
            h6_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("H7", salon_id, seans_id)) {
            h7_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("H8", salon_id, seans_id)) {
            h8_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("H9", salon_id, seans_id)) {
            h9_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("H10", salon_id, seans_id)) {
            h10_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("H11", salon_id, seans_id)) {
            h11_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("H12", salon_id, seans_id)) {
            h12_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("H13", salon_id, seans_id)) {
            h13_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("H14", salon_id, seans_id)) {
            h14_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("I1", salon_id, seans_id)) {
            i1_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("I2", salon_id, seans_id)) {
            i2_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("I3", salon_id, seans_id)) {
            i3_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("I4", salon_id, seans_id)) {
            i4_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("I5", salon_id, seans_id)) {
            i5_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("I6", salon_id, seans_id)) {
            i6_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("I7", salon_id, seans_id)) {
            i7_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("I8", salon_id, seans_id)) {
            i8_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("I9", salon_id, seans_id)) {
            i9_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("I10", salon_id, seans_id)) {
            i10_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("I11", salon_id, seans_id)) {
            i11_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("I12", salon_id, seans_id)) {
            i12_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("I13", salon_id, seans_id)) {
            i13_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("I14", salon_id, seans_id)) {
            i14_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("I15", salon_id, seans_id)) {
            i15_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("I16", salon_id, seans_id)) {
            i16_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("J1", salon_id, seans_id)) {
            j1_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("J2", salon_id, seans_id)) {
            j2_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("J3", salon_id, seans_id)) {
            j3_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("J4", salon_id, seans_id)) {
            j4_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("J5", salon_id, seans_id)) {
            j5_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("J6", salon_id, seans_id)) {
            j6_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("J7", salon_id, seans_id)) {
            j7_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("J8", salon_id, seans_id)) {
            j8_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("J9", salon_id, seans_id)) {
            j9_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("J10", salon_id, seans_id)) {
            j10_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("J11", salon_id, seans_id)) {
            j11_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("J12", salon_id, seans_id)) {
            j12_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("J13", salon_id, seans_id)) {
            j13_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("J14", salon_id, seans_id)) {
            j14_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("J15", salon_id, seans_id)) {
            j15_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("J16", salon_id, seans_id)) {
            j16_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("K1", salon_id, seans_id)) {
            k1_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("K2", salon_id, seans_id)) {
            k2_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("K3", salon_id, seans_id)) {
            k3_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("K4", salon_id, seans_id)) {
            k4_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("K5", salon_id, seans_id)) {
            k5_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("K6", salon_id, seans_id)) {
            k6_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("K7", salon_id, seans_id)) {
            k7_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("K8", salon_id, seans_id)) {
            k8_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("K9", salon_id, seans_id)) {
            k9_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("K10", salon_id, seans_id)) {
            k10_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("K11", salon_id, seans_id)) {
            k11_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("K12", salon_id, seans_id)) {
            k12_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("K13", salon_id, seans_id)) {
            k13_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("K14", salon_id, seans_id)) {
            k14_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("K15", salon_id, seans_id)) {
            k15_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("K16", salon_id, seans_id)) {
            k16_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("L1", salon_id, seans_id)) {
            l1_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("L2", salon_id, seans_id)) {
            l2_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("L3", salon_id, seans_id)) {
            l3_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("L4", salon_id, seans_id)) {
            l4_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("L5", salon_id, seans_id)) {
            l5_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("L6", salon_id, seans_id)) {
            l6_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("L7", salon_id, seans_id)) {
            l7_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("L8", salon_id, seans_id)) {
            l8_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("L9", salon_id, seans_id)) {
            l9_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("L10", salon_id, seans_id)) {
            l10_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("L11", salon_id, seans_id)) {
            l11_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("L12", salon_id, seans_id)) {
            l12_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("L13", salon_id, seans_id)) {
            l13_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("L14", salon_id, seans_id)) {
            l14_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("L15", salon_id, seans_id)) {
            l15_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("L16", salon_id, seans_id)) {
            l16_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("L17", salon_id, seans_id)) {
            l17_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("L18", salon_id, seans_id)) {
            l18_2.styleProperty().set("-fx-background-color: red");

        }
        if (search("L19", salon_id, seans_id)) {
            l19_2.styleProperty().set("-fx-background-color: red");

        }

    }

    @FXML
    protected void a1_2(ActionEvent event) {
        buton_aktif(a1_2);
    }

    @FXML
    protected void a2_2(ActionEvent event) {
        buton_aktif(a2_2);
    }

    @FXML
    protected void a3_2(ActionEvent event) {
        buton_aktif(a3_2);
    }

    @FXML
    protected void a4_2(ActionEvent event) {
        buton_aktif(a4_2);
    }

    @FXML
    protected void a5_2(ActionEvent event) {
        buton_aktif(a5_2);
    }

    @FXML
    protected void a6_2(ActionEvent event) {
        buton_aktif(a6_2);
    }

    @FXML
    protected void a7_2(ActionEvent event) {
        buton_aktif(a7_2);
    }

    @FXML
    protected void a8_2(ActionEvent event) {
        buton_aktif(a8_2);
    }

    @FXML
    protected void a9_2(ActionEvent event) {
        buton_aktif(a9_2);
    }

    @FXML
    protected void a10_2(ActionEvent event) {
        buton_aktif(a10_2);
    }

    @FXML
    protected void a11_2(ActionEvent event) {
        buton_aktif(a11_2);
    }

    @FXML
    protected void a12_2(ActionEvent event) {
        buton_aktif(a12_2);
    }

    @FXML
    protected void b1_2(ActionEvent event) {
        buton_aktif(b1_2);
    }

    @FXML
    protected void b2_2(ActionEvent event) {
        buton_aktif(b2_2);
    }

    @FXML
    protected void b3_2(ActionEvent event) {
        buton_aktif(b3_2);
    }

    @FXML
    protected void b4_2(ActionEvent event) {
        buton_aktif(b4_2);
    }

    @FXML
    protected void b5_2(ActionEvent event) {
        buton_aktif(b5_2);
    }

    @FXML
    protected void b6_2(ActionEvent event) {
        buton_aktif(b6_2);
    }

    @FXML
    protected void b7_2(ActionEvent event) {
        buton_aktif(b7_2);
    }

    @FXML
    protected void b8_2(ActionEvent event) {
        buton_aktif(b8_2);
    }

    @FXML
    protected void b9_2(ActionEvent event) {
        buton_aktif(b9_2);
    }

    @FXML
    protected void b10_2(ActionEvent event) {
        buton_aktif(b10_2);
    }

    @FXML
    protected void b11_2(ActionEvent event) {
        buton_aktif(b11_2);
    }

    @FXML
    protected void b12_2(ActionEvent event) {
        buton_aktif(b12_2);
    }

    @FXML
    protected void b13_2(ActionEvent event) {
        buton_aktif(b13_2);
    }

    @FXML
    protected void b14_2(ActionEvent event) {
        buton_aktif(b14_2);
    }

    @FXML
    protected void c1_2(ActionEvent event) {
        buton_aktif(c1_2);
    }

    @FXML
    protected void c2_2(ActionEvent event) {
        buton_aktif(c2_2);
    }

    @FXML
    protected void c3_2(ActionEvent event) {
        buton_aktif(c3_2);
    }

    @FXML
    protected void c4_2(ActionEvent event) {
        buton_aktif(c4_2);
    }

    @FXML
    protected void c5_2(ActionEvent event) {
        buton_aktif(c5_2);
    }

    @FXML
    protected void c6_2(ActionEvent event) {
        buton_aktif(c6_2);
    }

    @FXML
    protected void c7_2(ActionEvent event) {
        buton_aktif(c7_2);
    }

    @FXML
    protected void c8_2(ActionEvent event) {
        buton_aktif(c8_2);
    }

    @FXML
    protected void c9_2(ActionEvent event) {
        buton_aktif(c9_2);
    }

    @FXML
    protected void c10_2(ActionEvent event) {
        buton_aktif(c10_2);
    }

    @FXML
    protected void c11_2(ActionEvent event) {
        buton_aktif(c11_2);
    }

    @FXML
    protected void c12_2(ActionEvent event) {
        buton_aktif(c12_2);
    }

    @FXML
    protected void c13_2(ActionEvent event) {
        buton_aktif(c13_2);
    }

    @FXML
    protected void c14_2(ActionEvent event) {
        buton_aktif(c14_2);
    }

    @FXML
    protected void d1_2(ActionEvent event) {
        buton_aktif(d1_2);
    }

    @FXML
    protected void d2_2(ActionEvent event) {
        buton_aktif(d2_2);
    }

    @FXML
    protected void d3_2(ActionEvent event) {
        buton_aktif(d3_2);
    }

    @FXML
    protected void d4_2(ActionEvent event) {
        buton_aktif(d4_2);
    }

    @FXML
    protected void d5_2(ActionEvent event) {
        buton_aktif(d5_2);
    }

    @FXML
    protected void d6_2(ActionEvent event) {
        buton_aktif(d6_2);
    }

    @FXML
    protected void d7_2(ActionEvent event) {
        buton_aktif(d7_2);
    }

    @FXML
    protected void d8_2(ActionEvent event) {
        buton_aktif(d8_2);
    }

    @FXML
    protected void d9_2(ActionEvent event) {
        buton_aktif(d9_2);
    }

    @FXML
    protected void d10_2(ActionEvent event) {
        buton_aktif(d10_2);
    }

    @FXML
    protected void d11_2(ActionEvent event) {
        buton_aktif(d11_2);
    }

    @FXML
    protected void d12_2(ActionEvent event) {
        buton_aktif(d12_2);
    }

    @FXML
    protected void d13_2(ActionEvent event) {
        buton_aktif(d13_2);
    }

    @FXML
    protected void d14_2(ActionEvent event) {
        buton_aktif(d14_2);
    }

    @FXML
    protected void e1_2(ActionEvent event) {
        buton_aktif(e1_2);
    }

    @FXML
    protected void e2_2(ActionEvent event) {
        buton_aktif(e2_2);
    }

    @FXML
    protected void e3_2(ActionEvent event) {
        buton_aktif(e3_2);
    }

    @FXML
    protected void e4_2(ActionEvent event) {
        buton_aktif(e4_2);
    }

    @FXML
    protected void e5_2(ActionEvent event) {
        buton_aktif(e5_2);
    }

    @FXML
    protected void e6_2(ActionEvent event) {
        buton_aktif(e6_2);
    }

    @FXML
    protected void e7_2(ActionEvent event) {
        buton_aktif(e7_2);
    }

    @FXML
    protected void e8_2(ActionEvent event) {
        buton_aktif(e8_2);
    }

    @FXML
    protected void e9_2(ActionEvent event) {
        buton_aktif(e9_2);
    }

    @FXML
    protected void e10_2(ActionEvent event) {
        buton_aktif(e10_2);
    }

    @FXML
    protected void e11_2(ActionEvent event) {
        buton_aktif(e11_2);
    }

    @FXML
    protected void e12_2(ActionEvent event) {
        buton_aktif(e12_2);
    }

    @FXML
    protected void e13_2(ActionEvent event) {
        buton_aktif(e13_2);
    }

    @FXML
    protected void e14_2(ActionEvent event) {
        buton_aktif(e14_2);
    }

    @FXML
    protected void f1_2(ActionEvent event) {
        buton_aktif(f1_2);
    }

    @FXML
    protected void f2_2(ActionEvent event) {
        buton_aktif(f2_2);
    }

    @FXML
    protected void f3_2(ActionEvent event) {
        buton_aktif(f3_2);
    }

    @FXML
    protected void f4_2(ActionEvent event) {
        buton_aktif(f4_2);
    }

    @FXML
    protected void f5_2(ActionEvent event) {
        buton_aktif(f5_2);
    }

    @FXML
    protected void f6_2(ActionEvent event) {
        buton_aktif(f6_2);
    }

    @FXML
    protected void f7_2(ActionEvent event) {
        buton_aktif(f7_2);
    }

    @FXML
    protected void f8_2(ActionEvent event) {
        buton_aktif(f8_2);
    }

    @FXML
    protected void f9_2(ActionEvent event) {
        buton_aktif(f9_2);
    }

    @FXML
    protected void f10_2(ActionEvent event) {
        buton_aktif(f10_2);
    }

    @FXML
    protected void f11_2(ActionEvent event) {
        buton_aktif(f11_2);
    }

    @FXML
    protected void f12_2(ActionEvent event) {
        buton_aktif(f12_2);
    }

    @FXML
    protected void f13_2(ActionEvent event) {
        buton_aktif(f13_2);
    }

    @FXML
    protected void f14_2(ActionEvent event) {
        buton_aktif(f14_2);
    }

    @FXML
    protected void g1_2(ActionEvent event) {
        buton_aktif(g1_2);
    }

    @FXML
    protected void g2_2(ActionEvent event) {
        buton_aktif(g2_2);
    }

    @FXML
    protected void g3_2(ActionEvent event) {
        buton_aktif(g3_2);
    }

    @FXML
    protected void g4_2(ActionEvent event) {
        buton_aktif(g4_2);
    }

    @FXML
    protected void g5_2(ActionEvent event) {
        buton_aktif(g5_2);
    }

    @FXML
    protected void g6_2(ActionEvent event) {
        buton_aktif(g6_2);
    }

    @FXML
    protected void g7_2(ActionEvent event) {
        buton_aktif(g7_2);
    }

    @FXML
    protected void g8_2(ActionEvent event) {
        buton_aktif(g8_2);
    }

    @FXML
    protected void g9_2(ActionEvent event) {
        buton_aktif(g9_2);
    }

    @FXML
    protected void g10_2(ActionEvent event) {
        buton_aktif(g10_2);
    }

    @FXML
    protected void g11_2(ActionEvent event) {
        buton_aktif(g11_2);
    }

    @FXML
    protected void g12_2(ActionEvent event) {
        buton_aktif(g12_2);
    }

    @FXML
    protected void g13_2(ActionEvent event) {
        buton_aktif(g13_2);
    }

    @FXML
    protected void g14_2(ActionEvent event) {
        buton_aktif(g14_2);
    }

    @FXML
    protected void h1_2(ActionEvent event) {
        buton_aktif(h1_2);
    }

    @FXML
    protected void h2_2(ActionEvent event) {
        buton_aktif(h2_2);
    }

    @FXML
    protected void h3_2(ActionEvent event) {
        buton_aktif(h3_2);
    }

    @FXML
    protected void h4_2(ActionEvent event) {
        buton_aktif(h4_2);
    }

    @FXML
    protected void h5_2(ActionEvent event) {
        buton_aktif(h5_2);
    }

    @FXML
    protected void h6_2(ActionEvent event) {
        buton_aktif(h6_2);
    }

    @FXML
    protected void h7_2(ActionEvent event) {
        buton_aktif(h7_2);
    }

    @FXML
    protected void h8_2(ActionEvent event) {
        buton_aktif(h8_2);
    }

    @FXML
    protected void h9_2(ActionEvent event) {
        buton_aktif(h9_2);
    }

    @FXML
    protected void h10_2(ActionEvent event) {
        buton_aktif(h10_2);
    }

    @FXML
    protected void h11_2(ActionEvent event) {
        buton_aktif(h11_2);
    }

    @FXML
    protected void h12_2(ActionEvent event) {
        buton_aktif(h12_2);
    }

    @FXML
    protected void h13_2(ActionEvent event) {
        buton_aktif(h13_2);
    }

    @FXML
    protected void h14_2(ActionEvent event) {
        buton_aktif(h14_2);
    }

    @FXML
    protected void i1_2(ActionEvent event) {
        buton_aktif(i1_2);
    }

    @FXML
    protected void i2_2(ActionEvent event) {
        buton_aktif(i2_2);
    }

    @FXML
    protected void i3_2(ActionEvent event) {
        buton_aktif(i3_2);
    }

    @FXML
    protected void i4_2(ActionEvent event) {
        buton_aktif(i4_2);
    }

    @FXML
    protected void i5_2(ActionEvent event) {
        buton_aktif(i5_2);
    }

    @FXML
    protected void i6_2(ActionEvent event) {
        buton_aktif(i6_2);
    }

    @FXML
    protected void i7_2(ActionEvent event) {
        buton_aktif(i7_2);
    }

    @FXML
    protected void i8_2(ActionEvent event) {
        buton_aktif(i8_2);
    }

    @FXML
    protected void i9_2(ActionEvent event) {
        buton_aktif(i9_2);
    }

    @FXML
    protected void i10_2(ActionEvent event) {
        buton_aktif(i10_2);
    }

    @FXML
    protected void i11_2(ActionEvent event) {
        buton_aktif(i11_2);
    }

    @FXML
    protected void i12_2(ActionEvent event) {
        buton_aktif(i12_2);
    }

    @FXML
    protected void i13_2(ActionEvent event) {
        buton_aktif(i13_2);
    }

    @FXML
    protected void i14_2(ActionEvent event) {
        buton_aktif(i14_2);
    }

    @FXML
    protected void i15_2(ActionEvent event) {
        buton_aktif(i15_2);
    }

    @FXML
    protected void i16_2(ActionEvent event) {
        buton_aktif(i16_2);
    }

    @FXML
    protected void j1_2(ActionEvent event) {
        buton_aktif(j1_2);
    }

    @FXML
    protected void j2_2(ActionEvent event) {
        buton_aktif(j2_2);
    }

    @FXML
    protected void j3_2(ActionEvent event) {
        buton_aktif(j3_2);
    }

    @FXML
    protected void j4_2(ActionEvent event) {
        buton_aktif(j4_2);
    }

    @FXML
    protected void j5_2(ActionEvent event) {
        buton_aktif(j5_2);
    }

    @FXML
    protected void j6_2(ActionEvent event) {
        buton_aktif(j6_2);
    }

    @FXML
    protected void j7_2(ActionEvent event) {
        buton_aktif(j7_2);
    }

    @FXML
    protected void j8_2(ActionEvent event) {
        buton_aktif(j8_2);
    }

    @FXML
    protected void j9_2(ActionEvent event) {
        buton_aktif(j9_2);
    }

    @FXML
    protected void j10_2(ActionEvent event) {
        buton_aktif(j10_2);
    }

    @FXML
    protected void j11_2(ActionEvent event) {
        buton_aktif(j11_2);
    }

    @FXML
    protected void j12_2(ActionEvent event) {
        buton_aktif(j12_2);
    }

    @FXML
    protected void j13_2(ActionEvent event) {
        buton_aktif(j13_2);
    }

    @FXML
    protected void j14_2(ActionEvent event) {
        buton_aktif(j14_2);
    }

    @FXML
    protected void j15_2(ActionEvent event) {
        buton_aktif(j15_2);
    }

    @FXML
    protected void j16_2(ActionEvent event) {
        buton_aktif(j16_2);
    }

    @FXML
    protected void k1_2(ActionEvent event) {
        buton_aktif(k1_2);
    }

    @FXML
    protected void k2_2(ActionEvent event) {
        buton_aktif(k2_2);
    }

    @FXML
    protected void k3_2(ActionEvent event) {
        buton_aktif(k3_2);
    }

    @FXML
    protected void k4_2(ActionEvent event) {
        buton_aktif(k4_2);
    }

    @FXML
    protected void k5_2(ActionEvent event) {
        buton_aktif(k5_2);
    }

    @FXML
    protected void k6_2(ActionEvent event) {
        buton_aktif(k6_2);
    }

    @FXML
    protected void k7_2(ActionEvent event) {
        buton_aktif(k7_2);
    }

    @FXML
    protected void k8_2(ActionEvent event) {
        buton_aktif(k8_2);
    }

    @FXML
    protected void k9_2(ActionEvent event) {
        buton_aktif(k9_2);
    }

    @FXML
    protected void k10_2(ActionEvent event) {
        buton_aktif(k10_2);
    }

    @FXML
    protected void k11_2(ActionEvent event) {
        buton_aktif(k11_2);
    }

    @FXML
    protected void k12_2(ActionEvent event) {
        buton_aktif(k12_2);
    }

    @FXML
    protected void k13_2(ActionEvent event) {
        buton_aktif(k13_2);
    }

    @FXML
    protected void k14_2(ActionEvent event) {
        buton_aktif(k14_2);
    }

    @FXML
    protected void k15_2(ActionEvent event) {
        buton_aktif(k15_2);
    }

    @FXML
    protected void k16_2(ActionEvent event) {
        buton_aktif(k16_2);
    }

    @FXML
    protected void l1_2(ActionEvent event) {
        buton_aktif(l1_2);
    }

    @FXML
    protected void l2_2(ActionEvent event) {
        buton_aktif(l2_2);
    }

    @FXML
    protected void l3_2(ActionEvent event) {
        buton_aktif(l3_2);
    }

    @FXML
    protected void l4_2(ActionEvent event) {
        buton_aktif(l4_2);
    }

    @FXML
    protected void l5_2(ActionEvent event) {
        buton_aktif(l5_2);
    }

    @FXML
    protected void l6_2(ActionEvent event) {
        buton_aktif(l6_2);
    }

    @FXML
    protected void l7_2(ActionEvent event) {
        buton_aktif(l7_2);
    }

    @FXML
    protected void l8_2(ActionEvent event) {
        buton_aktif(l8_2);
    }

    @FXML
    protected void l9_2(ActionEvent event) {
        buton_aktif(l9_2);
    }

    @FXML
    protected void l10_2(ActionEvent event) {
        buton_aktif(l10_2);
    }

    @FXML
    protected void l11_2(ActionEvent event) {
        buton_aktif(l11_2);
    }

    @FXML
    protected void l12_2(ActionEvent event) {
        buton_aktif(l12_2);
    }

    @FXML
    protected void l13_2(ActionEvent event) {
        buton_aktif(l13_2);
    }

    @FXML
    protected void l14_2(ActionEvent event) {
        buton_aktif(l14_2);
    }

    @FXML
    protected void l15_2(ActionEvent event) {
        buton_aktif(l15_2);
    }

    @FXML
    protected void l16_2(ActionEvent event) {
        buton_aktif(l16_2);
    }

    @FXML
    protected void l17_2(ActionEvent event) {
        buton_aktif(l17_2);
    }

    @FXML
    protected void l18_2(ActionEvent event) {
        buton_aktif(l18_2);
    }

    @FXML
    protected void l19_2(ActionEvent event) {
        buton_aktif(l19_2);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // bu bölge scene builderda 3.salona denk gelmektedir ve 265 tane koltuk bulunmantadır.
    //burası 1. ve 2.salonla aynı özelliklere sahip olduğu için anlatım yapılmamıştır.
    @FXML
    protected Button a1_3, a2_3, a3_3, a4_3, a5_3, a6_3, a7_3, a8_3, a9_3, a10_3, a11_3, a12_3, a13_3, a14_3, a15_3, a16_3, a17_3, a18_3, a19_3, a20_3, a21_3, a22_3, a23_3, a24_3, a25_3, a26_3;

    @FXML
    protected Button b1_3, b2_3, b3_3, b4_3, b5_3, b6_3, b7_3, b8_3, b9_3, b10_3, b11_3, b12_3, b13_3, b14_3, b15_3, b16_3, b17_3, b18_3, b19_3, b20_3, b21_3, b22_3;

    @FXML
    protected Button c1_3, c2_3, c3_3, c4_3, c5_3, c6_3, c7_3, c8_3, c9_3, c10_3, c11_3, c12_3, c13_3, c14_3, c15_3, c16_3, c17_3, c18_3, c19_3, c20_3;

    @FXML
    protected Button d1_3, d2_3, d3_3, d4_3, d5_3, d6_3, d7_3, d8_3, d9_3, d10_3, d11_3, d12_3, d13_3, d14_3, d15_3, d16_3, d17_3, d18_3, d19_3, d20_3;

    @FXML
    protected Button e1_3, e2_3, e3_3, e4_3, e5_3, e6_3, e7_3, e8_3, e9_3, e10_3, e11_3, e12_3, e13_3, e14_3, e15_3, e16_3, e17_3, e18_3, e19_3, e20_3;

    @FXML
    protected Button f1_3, f2_3, f3_3, f4_3, f5_3, f6_3, f7_3, f8_3, f9_3, f10_3, f11_3, f12_3, f13_3, f14_3, f15_3, f16_3, f17_3, f18_3, f19_3, f20_3;

    @FXML
    protected Button g1_3, g2_3, g3_3, g4_3, g5_3, g6_3, g7_3, g8_3, g9_3, g10_3, g11_3, g12_3, g13_3, g14_3, g15_3, g16_3, g17_3, g18_3, g19_3, g20_3;

    @FXML
    protected Button h1_3, h2_3, h3_3, h4_3, h5_3, h6_3, h7_3, h8_3, h9_3, h10_3, h11_3, h12_3, h13_3, h14_3, h15_3, h16_3, h17_3, h18_3, h19_3, h20_3;

    @FXML
    protected Button i1_3, i2_3, i3_3, i4_3, i5_3, i6_3, i7_3, i8_3, i9_3, i10_3, i11_3, i12_3, i13_3, i14_3, i15_3, i16_3, i17_3;

    @FXML
    protected Button j1_3, j2_3, j3_3, j4_3, j5_3, j6_3, j7_3, j8_3, j9_3, j10_3, j11_3, j12_3, j13_3, j14_3, j15_3, j16_3, j17_3, j18_3, j19_3, j20_3;

    @FXML
    protected Button k1_3, k2_3, k3_3, k4_3, k5_3, k6_3, k7_3, k8_3, k9_3, k10_3, k11_3, k12_3, k13_3, k14_3, k15_3, k16_3, k17_3, k18_3, k19_3, k20_3;

    @FXML
    protected Button l1_3, l2_3, l3_3, l4_3, l5_3, l6_3, l7_3, l8_3, l9_3, l10_3, l11_3, l12_3, l13_3, l14_3, l15_3, l16_3, l17_3, l18_3, l19_3, l20_3;

    @FXML
    protected Button m1_3, m2_3, m3_3, m4_3, m5_3, m6_3, m7_3, m8_3, m9_3, m10_3, m11_3, m12_3, m13_3, m14_3, m15_3, m16_3, m17_3, m18_3, m19_3, m20_3;

    protected void uc_koltuk_dolu(int salon_id, int seans_id) {
        if (search("A1", salon_id, seans_id)) {
            a1_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("A2", salon_id, seans_id)) {
            a2_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("A3", salon_id, seans_id)) {
            a3_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("A4", salon_id, seans_id)) {
            a4_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("A5", salon_id, seans_id)) {
            a5_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("A6", salon_id, seans_id)) {
            a6_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("A7", salon_id, seans_id)) {
            a7_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("A8", salon_id, seans_id)) {
            a8_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("A9", salon_id, seans_id)) {
            a9_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("A10", salon_id, seans_id)) {
            a10_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("A11", salon_id, seans_id)) {
            a11_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("A12", salon_id, seans_id)) {
            a12_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("A13", salon_id, seans_id)) {
            a13_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("A14", salon_id, seans_id)) {
            a14_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("A15", salon_id, seans_id)) {
            a15_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("A16", salon_id, seans_id)) {
            a16_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("A17", salon_id, seans_id)) {
            a17_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("A18", salon_id, seans_id)) {
            a18_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("A19", salon_id, seans_id)) {
            a19_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("A20", salon_id, seans_id)) {
            a20_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("A21", salon_id, seans_id)) {
            a21_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("A22", salon_id, seans_id)) {
            a22_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("A23", salon_id, seans_id)) {
            a23_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("A24", salon_id, seans_id)) {
            a24_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("A25", salon_id, seans_id)) {
            a25_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("A26", salon_id, seans_id)) {
            a26_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("B1", salon_id, seans_id)) {
            b1_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("B2", salon_id, seans_id)) {
            b2_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("B3", salon_id, seans_id)) {
            b3_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("B4", salon_id, seans_id)) {
            b4_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("B5", salon_id, seans_id)) {
            b5_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("B6", salon_id, seans_id)) {
            b6_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("B7", salon_id, seans_id)) {
            b7_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("B8", salon_id, seans_id)) {
            b8_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("B9", salon_id, seans_id)) {
            b9_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("B10", salon_id, seans_id)) {
            b10_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("B11", salon_id, seans_id)) {
            b11_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("B12", salon_id, seans_id)) {
            b12_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("B13", salon_id, seans_id)) {
            b13_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("B14", salon_id, seans_id)) {
            b14_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("B15", salon_id, seans_id)) {
            b15_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("B16", salon_id, seans_id)) {
            b16_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("B17", salon_id, seans_id)) {
            b17_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("B18", salon_id, seans_id)) {
            b18_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("B19", salon_id, seans_id)) {
            b19_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("B20", salon_id, seans_id)) {
            b20_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("B21", salon_id, seans_id)) {
            b21_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("B22", salon_id, seans_id)) {
            b22_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("C1", salon_id, seans_id)) {
            c1_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("C2", salon_id, seans_id)) {
            c2_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("C3", salon_id, seans_id)) {
            c3_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("C4", salon_id, seans_id)) {
            c4_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("C5", salon_id, seans_id)) {
            c5_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("C6", salon_id, seans_id)) {
            c6_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("C7", salon_id, seans_id)) {
            c7_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("C8", salon_id, seans_id)) {
            c8_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("C9", salon_id, seans_id)) {
            c9_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("C10", salon_id, seans_id)) {
            c10_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("C11", salon_id, seans_id)) {
            c11_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("C12", salon_id, seans_id)) {
            c12_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("C13", salon_id, seans_id)) {
            c13_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("C14", salon_id, seans_id)) {
            c14_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("C15", salon_id, seans_id)) {
            c15_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("C16", salon_id, seans_id)) {
            c16_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("C17", salon_id, seans_id)) {
            c17_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("C18", salon_id, seans_id)) {
            c18_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("C19", salon_id, seans_id)) {
            c19_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("C20", salon_id, seans_id)) {
            c20_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("D1", salon_id, seans_id)) {
            d1_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("D2", salon_id, seans_id)) {
            d2_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("D3", salon_id, seans_id)) {
            d3_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("D4", salon_id, seans_id)) {
            d4_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("D5", salon_id, seans_id)) {
            d5_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("D6", salon_id, seans_id)) {
            d6_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("D7", salon_id, seans_id)) {
            d7_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("D8", salon_id, seans_id)) {
            d8_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("D9", salon_id, seans_id)) {
            d9_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("D10", salon_id, seans_id)) {
            d10_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("D11", salon_id, seans_id)) {
            d11_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("D12", salon_id, seans_id)) {
            d12_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("D13", salon_id, seans_id)) {
            d13_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("D14", salon_id, seans_id)) {
            d14_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("D15", salon_id, seans_id)) {
            d15_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("D16", salon_id, seans_id)) {
            d16_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("D17", salon_id, seans_id)) {
            d17_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("D18", salon_id, seans_id)) {
            d18_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("D19", salon_id, seans_id)) {
            d19_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("D20", salon_id, seans_id)) {
            d20_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("E1", salon_id, seans_id)) {
            e1_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("E2", salon_id, seans_id)) {
            e2_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("E3", salon_id, seans_id)) {
            e3_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("E4", salon_id, seans_id)) {
            e4_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("E5", salon_id, seans_id)) {
            e5_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("E6", salon_id, seans_id)) {
            e6_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("E7", salon_id, seans_id)) {
            e7_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("E8", salon_id, seans_id)) {
            e8_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("E9", salon_id, seans_id)) {
            e9_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("E10", salon_id, seans_id)) {
            e10_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("E11", salon_id, seans_id)) {
            e11_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("E12", salon_id, seans_id)) {
            e12_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("E13", salon_id, seans_id)) {
            e13_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("E14", salon_id, seans_id)) {
            e14_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("E15", salon_id, seans_id)) {
            e15_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("E16", salon_id, seans_id)) {
            e16_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("E17", salon_id, seans_id)) {
            e17_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("E18", salon_id, seans_id)) {
            e18_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("E19", salon_id, seans_id)) {
            e19_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("E20", salon_id, seans_id)) {
            e20_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("F1", salon_id, seans_id)) {
            f1_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("F2", salon_id, seans_id)) {
            f2_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("F3", salon_id, seans_id)) {
            f3_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("F4", salon_id, seans_id)) {
            f4_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("F5", salon_id, seans_id)) {
            f5_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("F6", salon_id, seans_id)) {
            f6_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("F7", salon_id, seans_id)) {
            f7_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("F8", salon_id, seans_id)) {
            f8_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("F9", salon_id, seans_id)) {
            f9_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("F10", salon_id, seans_id)) {
            f10_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("F11", salon_id, seans_id)) {
            f11_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("F12", salon_id, seans_id)) {
            f12_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("F13", salon_id, seans_id)) {
            f13_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("F14", salon_id, seans_id)) {
            f14_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("F15", salon_id, seans_id)) {
            f15_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("F16", salon_id, seans_id)) {
            f16_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("F17", salon_id, seans_id)) {
            f17_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("F18", salon_id, seans_id)) {
            f18_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("F19", salon_id, seans_id)) {
            f19_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("F20", salon_id, seans_id)) {
            f20_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("G1", salon_id, seans_id)) {
            g1_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("G2", salon_id, seans_id)) {
            g2_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("G3", salon_id, seans_id)) {
            g3_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("G4", salon_id, seans_id)) {
            g4_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("G5", salon_id, seans_id)) {
            g5_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("G6", salon_id, seans_id)) {
            g6_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("G7", salon_id, seans_id)) {
            g7_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("G8", salon_id, seans_id)) {
            g8_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("G9", salon_id, seans_id)) {
            g9_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("G10", salon_id, seans_id)) {
            g10_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("G11", salon_id, seans_id)) {
            g11_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("G12", salon_id, seans_id)) {
            g12_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("G13", salon_id, seans_id)) {
            g13_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("G14", salon_id, seans_id)) {
            g14_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("G15", salon_id, seans_id)) {
            g15_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("G16", salon_id, seans_id)) {
            g16_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("G17", salon_id, seans_id)) {
            g17_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("G18", salon_id, seans_id)) {
            g18_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("G19", salon_id, seans_id)) {
            g19_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("G20", salon_id, seans_id)) {
            g20_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("H1", salon_id, seans_id)) {
            h1_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("H2", salon_id, seans_id)) {
            h2_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("H3", salon_id, seans_id)) {
            h3_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("H4", salon_id, seans_id)) {
            h4_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("H5", salon_id, seans_id)) {
            h5_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("H6", salon_id, seans_id)) {
            h6_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("H7", salon_id, seans_id)) {
            h7_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("H8", salon_id, seans_id)) {
            h8_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("H9", salon_id, seans_id)) {
            h9_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("H10", salon_id, seans_id)) {
            h10_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("H11", salon_id, seans_id)) {
            h11_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("H12", salon_id, seans_id)) {
            h12_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("H13", salon_id, seans_id)) {
            h13_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("H14", salon_id, seans_id)) {
            h14_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("H15", salon_id, seans_id)) {
            h15_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("H16", salon_id, seans_id)) {
            h16_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("H17", salon_id, seans_id)) {
            h17_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("H18", salon_id, seans_id)) {
            h18_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("H19", salon_id, seans_id)) {
            h19_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("H20", salon_id, seans_id)) {
            h20_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("I1", salon_id, seans_id)) {
            i1_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("I2", salon_id, seans_id)) {
            i2_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("I3", salon_id, seans_id)) {
            i3_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("I4", salon_id, seans_id)) {
            i4_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("I5", salon_id, seans_id)) {
            i5_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("I6", salon_id, seans_id)) {
            i6_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("I7", salon_id, seans_id)) {
            i7_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("I8", salon_id, seans_id)) {
            i8_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("I9", salon_id, seans_id)) {
            i9_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("I10", salon_id, seans_id)) {
            i10_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("I11", salon_id, seans_id)) {
            i11_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("I12", salon_id, seans_id)) {
            i12_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("I13", salon_id, seans_id)) {
            i13_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("I14", salon_id, seans_id)) {
            i14_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("I15", salon_id, seans_id)) {
            i15_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("I16", salon_id, seans_id)) {
            i16_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("I17", salon_id, seans_id)) {
            i17_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("J1", salon_id, seans_id)) {
            j1_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("J2", salon_id, seans_id)) {
            j2_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("J3", salon_id, seans_id)) {
            j3_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("J4", salon_id, seans_id)) {
            j4_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("J5", salon_id, seans_id)) {
            j5_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("J6", salon_id, seans_id)) {
            j6_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("J7", salon_id, seans_id)) {
            j7_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("J8", salon_id, seans_id)) {
            j8_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("J9", salon_id, seans_id)) {
            j9_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("J10", salon_id, seans_id)) {
            j10_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("J11", salon_id, seans_id)) {
            j11_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("J12", salon_id, seans_id)) {
            j12_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("J13", salon_id, seans_id)) {
            j13_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("J14", salon_id, seans_id)) {
            j14_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("J15", salon_id, seans_id)) {
            j15_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("J16", salon_id, seans_id)) {
            j16_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("J17", salon_id, seans_id)) {
            j17_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("J18", salon_id, seans_id)) {
            j18_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("J19", salon_id, seans_id)) {
            j19_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("J20", salon_id, seans_id)) {
            j20_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("K1", salon_id, seans_id)) {
            k1_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("K2", salon_id, seans_id)) {
            k2_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("K3", salon_id, seans_id)) {
            k3_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("K4", salon_id, seans_id)) {
            k4_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("K5", salon_id, seans_id)) {
            k5_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("K6", salon_id, seans_id)) {
            k6_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("K7", salon_id, seans_id)) {
            k7_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("K8", salon_id, seans_id)) {
            k8_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("K9", salon_id, seans_id)) {
            k9_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("K10", salon_id, seans_id)) {
            l10_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("K11", salon_id, seans_id)) {
            k11_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("K12", salon_id, seans_id)) {
            k12_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("K13", salon_id, seans_id)) {
            k13_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("K14", salon_id, seans_id)) {
            k14_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("K15", salon_id, seans_id)) {
            k15_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("K16", salon_id, seans_id)) {
            k16_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("K17", salon_id, seans_id)) {
            k17_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("K18", salon_id, seans_id)) {
            k18_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("K19", salon_id, seans_id)) {
            k19_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("K20", salon_id, seans_id)) {
            k20_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("L1", salon_id, seans_id)) {
            l1_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("L2", salon_id, seans_id)) {
            l2_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("L3", salon_id, seans_id)) {
            l3_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("L4", salon_id, seans_id)) {
            l4_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("L5", salon_id, seans_id)) {
            l5_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("L6", salon_id, seans_id)) {
            l6_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("L7", salon_id, seans_id)) {
            l7_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("L8", salon_id, seans_id)) {
            l8_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("L9", salon_id, seans_id)) {
            l9_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("L10", salon_id, seans_id)) {
            l10_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("L11", salon_id, seans_id)) {
            l11_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("L12", salon_id, seans_id)) {
            l12_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("L13", salon_id, seans_id)) {
            l13_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("L14", salon_id, seans_id)) {
            l14_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("L15", salon_id, seans_id)) {
            l15_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("L16", salon_id, seans_id)) {
            l16_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("L17", salon_id, seans_id)) {
            l17_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("L18", salon_id, seans_id)) {
            l18_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("L19", salon_id, seans_id)) {
            l19_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("L20", salon_id, seans_id)) {
            l20_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("M1", salon_id, seans_id)) {
            m1_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("M2", salon_id, seans_id)) {
            m2_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("M3", salon_id, seans_id)) {
            m3_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("M4", salon_id, seans_id)) {
            m4_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("M5", salon_id, seans_id)) {
            m5_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("M6", salon_id, seans_id)) {
            m6_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("M7", salon_id, seans_id)) {
            m7_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("M8", salon_id, seans_id)) {
            m8_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("M9", salon_id, seans_id)) {
            m9_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("M10", salon_id, seans_id)) {
            m10_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("M11", salon_id, seans_id)) {
            m11_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("M12", salon_id, seans_id)) {
            m12_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("M13", salon_id, seans_id)) {
            m13_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("M14", salon_id, seans_id)) {
            m14_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("M15", salon_id, seans_id)) {
            m15_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("M16", salon_id, seans_id)) {
            m16_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("M17", salon_id, seans_id)) {
            m17_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("M18", salon_id, seans_id)) {
            m18_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("M19", salon_id, seans_id)) {
            m19_3.styleProperty().set("-fx-background-color: red");
        }
        if (search("M20", salon_id, seans_id)) {
            m20_3.styleProperty().set("-fx-background-color: red");
        }

    }

    @FXML
    protected void a1_3(ActionEvent event) {
        buton_aktif(a1_3);
    }

    @FXML
    protected void a2_3(ActionEvent event) {
        buton_aktif(a2_3);
    }

    @FXML
    protected void a3_3(ActionEvent event) {
        buton_aktif(a3_3);
    }

    @FXML
    protected void a4_3(ActionEvent event) {
        buton_aktif(a4_3);
    }

    @FXML
    protected void a5_3(ActionEvent event) {
        buton_aktif(a5_3);
    }

    @FXML
    protected void a6_3(ActionEvent event) {
        buton_aktif(a6_3);
    }

    @FXML
    protected void a7_3(ActionEvent event) {
        buton_aktif(a7_3);
    }

    @FXML
    protected void a8_3(ActionEvent event) {
        buton_aktif(a8_3);
    }

    @FXML
    protected void a9_3(ActionEvent event) {
        buton_aktif(a9_3);
    }

    @FXML
    protected void a10_3(ActionEvent event) {
        buton_aktif(a10_3);
    }

    @FXML
    protected void a11_3(ActionEvent event) {
        buton_aktif(a11_3);
    }

    @FXML
    protected void a12_3(ActionEvent event) {
        buton_aktif(a12_3);
    }

    @FXML
    protected void a13_3(ActionEvent event) {
        buton_aktif(a13_3);
    }

    @FXML
    protected void a14_3(ActionEvent event) {
        buton_aktif(a14_3);
    }

    @FXML
    protected void a15_3(ActionEvent event) {
        buton_aktif(a15_3);
    }

    @FXML
    protected void a16_3(ActionEvent event) {
        buton_aktif(a16_3);
    }

    @FXML
    protected void a17_3(ActionEvent event) {
        buton_aktif(a17_3);
    }

    @FXML
    protected void a18_3(ActionEvent event) {
        buton_aktif(a18_3);
    }

    @FXML
    protected void a19_3(ActionEvent event) {
        buton_aktif(a19_3);
    }

    @FXML
    protected void a20_3(ActionEvent event) {
        buton_aktif(a20_3);
    }

    @FXML
    protected void a21_3(ActionEvent event) {
        buton_aktif(a21_3);
    }

    @FXML
    protected void a22_3(ActionEvent event) {
        buton_aktif(a22_3);
    }

    @FXML
    protected void a23_3(ActionEvent event) {
        buton_aktif(a23_3);
    }

    @FXML
    protected void a24_3(ActionEvent event) {
        buton_aktif(a24_3);
    }

    @FXML
    protected void a25_3(ActionEvent event) {
        buton_aktif(a25_3);
    }

    @FXML
    protected void a26_3(ActionEvent event) {
        buton_aktif(a26_3);
    }

    @FXML
    protected void b1_3(ActionEvent event) {
        buton_aktif(b1_3);
    }

    @FXML
    protected void b2_3(ActionEvent event) {
        buton_aktif(b2_3);
    }

    @FXML
    protected void b3_3(ActionEvent event) {
        buton_aktif(b3_3);
    }

    @FXML
    protected void b4_3(ActionEvent event) {
        buton_aktif(b4_3);
    }

    @FXML
    protected void b5_3(ActionEvent event) {
        buton_aktif(b5_3);
    }

    @FXML
    protected void b6_3(ActionEvent event) {
        buton_aktif(b6_3);
    }

    @FXML
    protected void b7_3(ActionEvent event) {
        buton_aktif(b7_3);
    }

    @FXML
    protected void b8_3(ActionEvent event) {
        buton_aktif(b8_3);
    }

    @FXML
    protected void b9_3(ActionEvent event) {
        buton_aktif(b9_3);
    }

    @FXML
    protected void b10_3(ActionEvent event) {
        buton_aktif(b10_3);
    }

    @FXML
    protected void b11_3(ActionEvent event) {
        buton_aktif(b11_3);
    }

    @FXML
    protected void b12_3(ActionEvent event) {
        buton_aktif(b12_3);
    }

    @FXML
    protected void b13_3(ActionEvent event) {
        buton_aktif(b13_3);
    }

    @FXML
    protected void b14_3(ActionEvent event) {
        buton_aktif(b14_3);
    }

    @FXML
    protected void b15_3(ActionEvent event) {
        buton_aktif(b15_3);
    }

    @FXML
    protected void b16_3(ActionEvent event) {
        buton_aktif(b16_3);
    }

    @FXML
    protected void b17_3(ActionEvent event) {
        buton_aktif(b17_3);
    }

    @FXML
    protected void b18_3(ActionEvent event) {
        buton_aktif(b18_3);
    }

    @FXML
    protected void b19_3(ActionEvent event) {
        buton_aktif(b19_3);
    }

    @FXML
    protected void b20_3(ActionEvent event) {
        buton_aktif(b20_3);
    }

    @FXML
    protected void b21_3(ActionEvent event) {
        buton_aktif(b21_3);
    }

    @FXML
    protected void b22_3(ActionEvent event) {
        buton_aktif(b22_3);
    }

    @FXML
    protected void c1_3(ActionEvent event) {
        buton_aktif(c1_3);
    }

    @FXML
    protected void c2_3(ActionEvent event) {
        buton_aktif(c2_3);
    }

    @FXML
    protected void c3_3(ActionEvent event) {
        buton_aktif(c3_3);
    }

    @FXML
    protected void c4_3(ActionEvent event) {
        buton_aktif(c4_3);
    }

    @FXML
    protected void c5_3(ActionEvent event) {
        buton_aktif(c5_3);
    }

    @FXML
    protected void c6_3(ActionEvent event) {
        buton_aktif(c6_3);
    }

    @FXML
    protected void c7_3(ActionEvent event) {
        buton_aktif(c7_3);
    }

    @FXML
    protected void c8_3(ActionEvent event) {
        buton_aktif(c8_3);
    }

    @FXML
    protected void c9_3(ActionEvent event) {
        buton_aktif(c9_3);
    }

    @FXML
    protected void c10_3(ActionEvent event) {
        buton_aktif(c10_3);
    }

    @FXML
    protected void c11_3(ActionEvent event) {
        buton_aktif(c11_3);
    }

    @FXML
    protected void c12_3(ActionEvent event) {
        buton_aktif(c12_3);
    }

    @FXML
    protected void c13_3(ActionEvent event) {
        buton_aktif(c13_3);
    }

    @FXML
    protected void c14_3(ActionEvent event) {
        buton_aktif(c14_3);
    }

    @FXML
    protected void c15_3(ActionEvent event) {
        buton_aktif(c15_3);
    }

    @FXML
    protected void c16_3(ActionEvent event) {
        buton_aktif(c16_3);
    }

    @FXML
    protected void c17_3(ActionEvent event) {
        buton_aktif(c17_3);
    }

    @FXML
    protected void c18_3(ActionEvent event) {
        buton_aktif(c18_3);
    }

    @FXML
    protected void c19_3(ActionEvent event) {
        buton_aktif(c19_3);
    }

    @FXML
    protected void c20_3(ActionEvent event) {
        buton_aktif(c20_3);
    }

    @FXML
    protected void d1_3(ActionEvent event) {
        buton_aktif(d1_3);
    }

    @FXML
    protected void d2_3(ActionEvent event) {
        buton_aktif(d2_3);
    }

    @FXML
    protected void d3_3(ActionEvent event) {
        buton_aktif(d3_3);
    }

    @FXML
    protected void d4_3(ActionEvent event) {
        buton_aktif(d4_3);
    }

    @FXML
    protected void d5_3(ActionEvent event) {
        buton_aktif(d5_3);
    }

    @FXML
    protected void d6_3(ActionEvent event) {
        buton_aktif(d6_3);
    }

    @FXML
    protected void d7_3(ActionEvent event) {
        buton_aktif(d7_3);
    }

    @FXML
    protected void d8_3(ActionEvent event) {
        buton_aktif(d8_3);
    }

    @FXML
    protected void d9_3(ActionEvent event) {
        buton_aktif(d9_3);
    }

    @FXML
    protected void d10_3(ActionEvent event) {
        buton_aktif(d10_3);
    }

    @FXML
    protected void d11_3(ActionEvent event) {
        buton_aktif(d11_3);
    }

    @FXML
    protected void d12_3(ActionEvent event) {
        buton_aktif(d12_3);
    }

    @FXML
    protected void d13_3(ActionEvent event) {
        buton_aktif(d13_3);
    }

    @FXML
    protected void d14_3(ActionEvent event) {
        buton_aktif(d14_3);
    }

    @FXML
    protected void d15_3(ActionEvent event) {
        buton_aktif(d15_3);
    }

    @FXML
    protected void d16_3(ActionEvent event) {
        buton_aktif(d16_3);
    }

    @FXML
    protected void d17_3(ActionEvent event) {
        buton_aktif(d17_3);
    }

    @FXML
    protected void d18_3(ActionEvent event) {
        buton_aktif(d18_3);
    }

    @FXML
    protected void d19_3(ActionEvent event) {
        buton_aktif(d19_3);
    }

    @FXML
    protected void d20_3(ActionEvent event) {
        buton_aktif(d20_3);
    }

    @FXML
    protected void e1_3(ActionEvent event) {
        buton_aktif(e1_3);
    }

    @FXML
    protected void e2_3(ActionEvent event) {
        buton_aktif(e2_3);
    }

    @FXML
    protected void e3_3(ActionEvent event) {
        buton_aktif(e3_3);
    }

    @FXML
    protected void e4_3(ActionEvent event) {
        buton_aktif(e4_3);
    }

    @FXML
    protected void e5_3(ActionEvent event) {
        buton_aktif(e5_3);
    }

    @FXML
    protected void e6_3(ActionEvent event) {
        buton_aktif(e6_3);
    }

    @FXML
    protected void e7_3(ActionEvent event) {
        buton_aktif(e7_3);
    }

    @FXML
    protected void e8_3(ActionEvent event) {
        buton_aktif(e8_3);
    }

    @FXML
    protected void e9_3(ActionEvent event) {
        buton_aktif(e9_3);
    }

    @FXML
    protected void e10_3(ActionEvent event) {
        buton_aktif(e10_3);
    }

    @FXML
    protected void e11_3(ActionEvent event) {
        buton_aktif(e11_3);
    }

    @FXML
    protected void e12_3(ActionEvent event) {
        buton_aktif(e12_3);
    }

    @FXML
    protected void e13_3(ActionEvent event) {
        buton_aktif(e13_3);
    }

    @FXML
    protected void e14_3(ActionEvent event) {
        buton_aktif(e14_3);
    }

    @FXML
    protected void e15_3(ActionEvent event) {
        buton_aktif(e15_3);
    }

    @FXML
    protected void e16_3(ActionEvent event) {
        buton_aktif(e16_3);
    }

    @FXML
    protected void e17_3(ActionEvent event) {
        buton_aktif(e17_3);
    }

    @FXML
    protected void e18_3(ActionEvent event) {
        buton_aktif(e18_3);
    }

    @FXML
    protected void e19_3(ActionEvent event) {
        buton_aktif(e19_3);
    }

    @FXML
    protected void e20_3(ActionEvent event) {
        buton_aktif(e20_3);
    }

    @FXML
    protected void f1_3(ActionEvent event) {
        buton_aktif(f1_3);
    }

    @FXML
    protected void f2_3(ActionEvent event) {
        buton_aktif(f2_3);
    }

    @FXML
    protected void f3_3(ActionEvent event) {
        buton_aktif(f3_3);
    }

    @FXML
    protected void f4_3(ActionEvent event) {
        buton_aktif(f4_3);
    }

    @FXML
    protected void f5_3(ActionEvent event) {
        buton_aktif(f5_3);
    }

    @FXML
    protected void f6_3(ActionEvent event) {
        buton_aktif(f6_3);
    }

    @FXML
    protected void f7_3(ActionEvent event) {
        buton_aktif(f7_3);
    }

    @FXML
    protected void f8_3(ActionEvent event) {
        buton_aktif(f8_3);
    }

    @FXML
    protected void f9_3(ActionEvent event) {
        buton_aktif(f9_3);
    }

    @FXML
    protected void f10_3(ActionEvent event) {
        buton_aktif(f10_3);
    }

    @FXML
    protected void f11_3(ActionEvent event) {
        buton_aktif(f11_3);
    }

    @FXML
    protected void f12_3(ActionEvent event) {
        buton_aktif(f12_3);
    }

    @FXML
    protected void f13_3(ActionEvent event) {
        buton_aktif(f13_3);
    }

    @FXML
    protected void f14_3(ActionEvent event) {
        buton_aktif(f14_3);
    }

    @FXML
    protected void f15_3(ActionEvent event) {
        buton_aktif(f15_3);
    }

    @FXML
    protected void f16_3(ActionEvent event) {
        buton_aktif(f16_3);
    }

    @FXML
    protected void f17_3(ActionEvent event) {
        buton_aktif(f17_3);
    }

    @FXML
    protected void f18_3(ActionEvent event) {
        buton_aktif(f18_3);
    }

    @FXML
    protected void f19_3(ActionEvent event) {
        buton_aktif(f19_3);
    }

    @FXML
    protected void f20_3(ActionEvent event) {
        buton_aktif(f20_3);
    }

    @FXML
    protected void g1_3(ActionEvent event) {
        buton_aktif(g1_3);
    }

    @FXML
    protected void g2_3(ActionEvent event) {
        buton_aktif(g2_3);
    }

    @FXML
    protected void g3_3(ActionEvent event) {
        buton_aktif(g3_3);
    }

    @FXML
    protected void g4_3(ActionEvent event) {
        buton_aktif(g4_3);
    }

    @FXML
    protected void g5_3(ActionEvent event) {
        buton_aktif(g5_3);
    }

    @FXML
    protected void g6_3(ActionEvent event) {
        buton_aktif(g6_3);
    }

    @FXML
    protected void g7_3(ActionEvent event) {
        buton_aktif(g7_3);
    }

    @FXML
    protected void g8_3(ActionEvent event) {
        buton_aktif(g8_3);
    }

    @FXML
    protected void g9_3(ActionEvent event) {
        buton_aktif(g9_3);
    }

    @FXML
    protected void g10_3(ActionEvent event) {
        buton_aktif(g10_3);
    }

    @FXML
    protected void g11_3(ActionEvent event) {
        buton_aktif(g11_3);
    }

    @FXML
    protected void g12_3(ActionEvent event) {
        buton_aktif(g12_3);
    }

    @FXML
    protected void g13_3(ActionEvent event) {
        buton_aktif(g13_3);
    }

    @FXML
    protected void g14_3(ActionEvent event) {
        buton_aktif(g14_3);
    }

    @FXML
    protected void g15_3(ActionEvent event) {
        buton_aktif(g15_3);
    }

    @FXML
    protected void g16_3(ActionEvent event) {
        buton_aktif(g16_3);
    }

    @FXML
    protected void g17_3(ActionEvent event) {
        buton_aktif(g17_3);
    }

    @FXML
    protected void g18_3(ActionEvent event) {
        buton_aktif(g18_3);
    }

    @FXML
    protected void g19_3(ActionEvent event) {
        buton_aktif(g19_3);
    }

    @FXML
    protected void g20_3(ActionEvent event) {
        buton_aktif(g20_3);
    }

    @FXML
    protected void h1_3(ActionEvent event) {
        buton_aktif(h1_3);
    }

    @FXML
    protected void h2_3(ActionEvent event) {
        buton_aktif(h2_3);
    }

    @FXML
    protected void h3_3(ActionEvent event) {
        buton_aktif(h3_3);
    }

    @FXML
    protected void h4_3(ActionEvent event) {
        buton_aktif(h4_3);
    }

    @FXML
    protected void h5_3(ActionEvent event) {
        buton_aktif(h5_3);
    }

    @FXML
    protected void h6_3(ActionEvent event) {
        buton_aktif(h6_3);
    }

    @FXML
    protected void h7_3(ActionEvent event) {
        buton_aktif(h7_3);
    }

    @FXML
    protected void h8_3(ActionEvent event) {
        buton_aktif(h8_3);
    }

    @FXML
    protected void h9_3(ActionEvent event) {
        buton_aktif(h9_3);
    }

    @FXML
    protected void h10_3(ActionEvent event) {
        buton_aktif(h10_3);
    }

    @FXML
    protected void h11_3(ActionEvent event) {
        buton_aktif(h11_3);
    }

    @FXML
    protected void h12_3(ActionEvent event) {
        buton_aktif(h12_3);
    }

    @FXML
    protected void h13_3(ActionEvent event) {
        buton_aktif(h13_3);
    }

    @FXML
    protected void h14_3(ActionEvent event) {
        buton_aktif(h14_3);
    }

    @FXML
    protected void h15_3(ActionEvent event) {
        buton_aktif(h15_3);
    }

    @FXML
    protected void h16_3(ActionEvent event) {
        buton_aktif(h16_3);
    }

    @FXML
    protected void h17_3(ActionEvent event) {
        buton_aktif(h17_3);
    }

    @FXML
    protected void h18_3(ActionEvent event) {
        buton_aktif(h18_3);
    }

    @FXML
    protected void h19_3(ActionEvent event) {
        buton_aktif(h19_3);
    }

    @FXML
    protected void h20_3(ActionEvent event) {
        buton_aktif(h20_3);
    }

    @FXML
    protected void i1_3(ActionEvent event) {
        buton_aktif(i1_3);
    }

    @FXML
    protected void i2_3(ActionEvent event) {
        buton_aktif(i2_3);
    }

    @FXML
    protected void i3_3(ActionEvent event) {
        buton_aktif(i3_3);
    }

    @FXML
    protected void i4_3(ActionEvent event) {
        buton_aktif(i4_3);
    }

    @FXML
    protected void i5_3(ActionEvent event) {
        buton_aktif(i5_3);
    }

    @FXML
    protected void i6_3(ActionEvent event) {
        buton_aktif(i6_3);
    }

    @FXML
    protected void i7_3(ActionEvent event) {
        buton_aktif(i7_3);
    }

    @FXML
    protected void i8_3(ActionEvent event) {
        buton_aktif(i8_3);
    }

    @FXML
    protected void i9_3(ActionEvent event) {
        buton_aktif(i9_3);
    }

    @FXML
    protected void i10_3(ActionEvent event) {
        buton_aktif(i10_3);
    }

    @FXML
    protected void i11_3(ActionEvent event) {
        buton_aktif(i11_3);
    }

    @FXML
    protected void i12_3(ActionEvent event) {
        buton_aktif(i12_3);
    }

    @FXML
    protected void i13_3(ActionEvent event) {
        buton_aktif(i13_3);
    }

    @FXML
    protected void i14_3(ActionEvent event) {
        buton_aktif(i14_3);
    }

    @FXML
    protected void i15_3(ActionEvent event) {
        buton_aktif(i15_3);
    }

    @FXML
    protected void i16_3(ActionEvent event) {
        buton_aktif(i16_3);
    }

    @FXML
    protected void i17_3(ActionEvent event) {
        buton_aktif(i17_3);
    }

    @FXML
    protected void j1_3(ActionEvent event) {
        buton_aktif(j1_3);
    }

    @FXML
    protected void j2_3(ActionEvent event) {
        buton_aktif(j2_3);
    }

    @FXML
    protected void j3_3(ActionEvent event) {
        buton_aktif(j3_3);
    }

    @FXML
    protected void j4_3(ActionEvent event) {
        buton_aktif(j4_3);
    }

    @FXML
    protected void j5_3(ActionEvent event) {
        buton_aktif(j5_3);
    }

    @FXML
    protected void j6_3(ActionEvent event) {
        buton_aktif(j6_3);
    }

    @FXML
    protected void j7_3(ActionEvent event) {
        buton_aktif(j7_3);
    }

    @FXML
    protected void j8_3(ActionEvent event) {
        buton_aktif(j8_3);
    }

    @FXML
    protected void j9_3(ActionEvent event) {
        buton_aktif(j9_3);
    }

    @FXML
    protected void j10_3(ActionEvent event) {
        buton_aktif(j10_3);
    }

    @FXML
    protected void j11_3(ActionEvent event) {
        buton_aktif(j11_3);
    }

    @FXML
    protected void j12_3(ActionEvent event) {
        buton_aktif(j12_3);
    }

    @FXML
    protected void j13_3(ActionEvent event) {
        buton_aktif(j13_3);
    }

    @FXML
    protected void j14_3(ActionEvent event) {
        buton_aktif(j14_3);
    }

    @FXML
    protected void j15_3(ActionEvent event) {
        buton_aktif(j15_3);
    }

    @FXML
    protected void j16_3(ActionEvent event) {
        buton_aktif(j16_3);
    }

    @FXML
    protected void j17_3(ActionEvent event) {
        buton_aktif(j17_3);
    }

    @FXML
    protected void j18_3(ActionEvent event) {
        buton_aktif(j18_3);
    }

    @FXML
    protected void j19_3(ActionEvent event) {
        buton_aktif(j19_3);
    }

    @FXML
    protected void j20_3(ActionEvent event) {
        buton_aktif(j20_3);
    }

    @FXML
    protected void k1_3(ActionEvent event) {
        buton_aktif(k1_3);
    }

    @FXML
    protected void k2_3(ActionEvent event) {
        buton_aktif(k2_3);
    }

    @FXML
    protected void k3_3(ActionEvent event) {
        buton_aktif(k3_3);
    }

    @FXML
    protected void k4_3(ActionEvent event) {
        buton_aktif(k4_3);
    }

    @FXML
    protected void k5_3(ActionEvent event) {
        buton_aktif(k5_3);
    }

    @FXML
    protected void k6_3(ActionEvent event) {
        buton_aktif(k6_3);
    }

    @FXML
    protected void k7_3(ActionEvent event) {
        buton_aktif(k7_3);
    }

    @FXML
    protected void k8_3(ActionEvent event) {
        buton_aktif(k8_3);
    }

    @FXML
    protected void k9_3(ActionEvent event) {
        buton_aktif(k9_3);
    }

    @FXML
    protected void k10_3(ActionEvent event) {
        buton_aktif(k10_3);
    }

    @FXML
    protected void k11_3(ActionEvent event) {
        buton_aktif(k11_3);
    }

    @FXML
    protected void k12_3(ActionEvent event) {
        buton_aktif(k12_3);
    }

    @FXML
    protected void k13_3(ActionEvent event) {
        buton_aktif(k13_3);
    }

    @FXML
    protected void k14_3(ActionEvent event) {
        buton_aktif(k14_3);
    }

    @FXML
    protected void k15_3(ActionEvent event) {
        buton_aktif(k15_3);
    }

    @FXML
    protected void k16_3(ActionEvent event) {
        buton_aktif(k16_3);
    }

    @FXML
    protected void k17_3(ActionEvent event) {
        buton_aktif(k17_3);
    }

    @FXML
    protected void k18_3(ActionEvent event) {
        buton_aktif(k18_3);
    }

    @FXML
    protected void k19_3(ActionEvent event) {
        buton_aktif(k19_3);
    }

    @FXML
    protected void k20_3(ActionEvent event) {
        buton_aktif(k20_3);
    }

    @FXML
    protected void l1_3(ActionEvent event) {
        buton_aktif(l1_3);
    }

    @FXML
    protected void l2_3(ActionEvent event) {
        buton_aktif(l2_3);
    }

    @FXML
    protected void l3_3(ActionEvent event) {
        buton_aktif(l3_3);
    }

    @FXML
    protected void l4_3(ActionEvent event) {
        buton_aktif(l4_3);
    }

    @FXML
    protected void l5_3(ActionEvent event) {
        buton_aktif(l5_3);
    }

    @FXML
    protected void l6_3(ActionEvent event) {
        buton_aktif(l6_3);
    }

    @FXML
    protected void l7_3(ActionEvent event) {
        buton_aktif(l7_3);
    }

    @FXML
    protected void l8_3(ActionEvent event) {
        buton_aktif(l8_3);
    }

    @FXML
    protected void l9_3(ActionEvent event) {
        buton_aktif(l9_3);
    }

    @FXML
    protected void l10_3(ActionEvent event) {
        buton_aktif(l10_3);
    }

    @FXML
    protected void l11_3(ActionEvent event) {
        buton_aktif(l11_3);
    }

    @FXML
    protected void l12_3(ActionEvent event) {
        buton_aktif(l12_3);
    }

    @FXML
    protected void l13_3(ActionEvent event) {
        buton_aktif(l13_3);
    }

    @FXML
    protected void l14_3(ActionEvent event) {
        buton_aktif(l14_3);
    }

    @FXML
    protected void l15_3(ActionEvent event) {
        buton_aktif(l15_3);
    }

    @FXML
    protected void l16_3(ActionEvent event) {
        buton_aktif(l16_3);
    }

    @FXML
    protected void l17_3(ActionEvent event) {
        buton_aktif(l17_3);
    }

    @FXML
    protected void l18_3(ActionEvent event) {
        buton_aktif(l18_3);
    }

    @FXML
    protected void l19_3(ActionEvent event) {
        buton_aktif(l19_3);
    }

    @FXML
    protected void l20_3(ActionEvent event) {
        buton_aktif(l20_3);
    }

    @FXML
    protected void m1_3(ActionEvent event) {
        buton_aktif(m1_3);
    }

    @FXML
    protected void m2_3(ActionEvent event) {
        buton_aktif(m2_3);
    }

    @FXML
    protected void m3_3(ActionEvent event) {
        buton_aktif(m3_3);
    }

    @FXML
    protected void m4_3(ActionEvent event) {
        buton_aktif(m4_3);
    }

    @FXML
    protected void m5_3(ActionEvent event) {
        buton_aktif(m5_3);
    }

    @FXML
    protected void m6_3(ActionEvent event) {
        buton_aktif(m6_3);
    }

    @FXML
    protected void m7_3(ActionEvent event) {
        buton_aktif(m7_3);
    }

    @FXML
    protected void m8_3(ActionEvent event) {
        buton_aktif(m8_3);
    }

    @FXML
    protected void m9_3(ActionEvent event) {
        buton_aktif(m9_3);
    }

    @FXML
    protected void m10_3(ActionEvent event) {
        buton_aktif(m10_3);
    }

    @FXML
    protected void m11_3(ActionEvent event) {
        buton_aktif(m11_3);
    }

    @FXML
    protected void m12_3(ActionEvent event) {
        buton_aktif(m12_3);
    }

    @FXML
    protected void m13_3(ActionEvent event) {
        buton_aktif(m13_3);
    }

    @FXML
    protected void m14_3(ActionEvent event) {
        buton_aktif(m14_3);
    }

    @FXML
    protected void m15_3(ActionEvent event) {
        buton_aktif(m15_3);
    }

    @FXML
    protected void m16_3(ActionEvent event) {
        buton_aktif(m16_3);
    }

    @FXML
    protected void m17_3(ActionEvent event) {
        buton_aktif(m17_3);
    }

    @FXML
    protected void m18_3(ActionEvent event) {
        buton_aktif(m18_3);
    }

    @FXML
    protected void m19_3(ActionEvent event) {
        buton_aktif(m19_3);
    }

    @FXML
    protected void m20_3(ActionEvent event) {
        buton_aktif(m20_3);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //bu bölge scene builder da 4.salona denk gelmektedir ve 294 tane koltuk bulunmaktadır.
    
    //burası ile 1., 2. ve 3.salon aynıdır bu yüzden burada anlatım yapılmamıştır.
    @FXML
    protected Button a1_4, a2_4, a3_4, a4_4, a5_4, a6_4, a7_4, a8_4, a9_4, a10_4, a11_4, a12_4, a13_4, a14_4, a15_4, a16_4, a17_4, a18_4, a19_4, a20_4, a21_4, a22_4, a23_4, a24_4, a25_4, a26_4, a27_4, a28_4;

    @FXML
    protected Button b1_4, b2_4, b3_4, b4_4, b5_4, b6_4, b7_4, b8_4, b9_4, b10_4, b11_4, b12_4, b13_4, b14_4, b15_4, b16_4, b17_4, b18_4, b19_4, b20_4, b21_4, b22_4, b23_4, b24_4, b25_4, b26_4, b27_4, b28_4, b29_4, b30_4;

    @FXML
    protected Button c1_4, c2_4, c3_4, c4_4, c5_4, c6_4, c7_4, c8_4, c9_4, c10_4, c11_4, c12_4, c13_4, c14_4, c15_4, c16_4, c17_4, c18_4, c19_4, c20_4, c21_4, c22_4, c23_4, c24_4, c25_4, c26_4, c27_4, c28_4, c29_4, c30_4;

    @FXML
    protected Button d1_4, d2_4, d3_4, d4_4, d5_4, d6_4, d7_4, d8_4, d9_4, d10_4, d11_4, d12_4, d13_4, d14_4, d15_4, d16_4, d17_4, d18_4, d19_4, d20_4, d21_4, d22_4, d23_4, d24_4, d25_4, d26_4, d27_4, d28_4, d29_4, d30_4;

    @FXML
    protected Button e1_4, e2_4, e3_4, e4_4, e5_4, e6_4, e7_4, e8_4, e9_4, e10_4, e11_4, e12_4, e13_4, e14_4, e15_4, e16_4, e17_4, e18_4, e19_4, e20_4, e21_4, e22_4, e23_4, e24_4, e25_4, e26_4, e27_4, e28_4, e29_4, e30_4;

    @FXML
    protected Button f1_4, f2_4, f3_4, f4_4, f5_4, f6_4, f7_4, f8_4, f9_4, f10_4, f11_4, f12_4, f13_4, f14_4, f15_4, f16_4, f17_4, f18_4, f19_4, f20_4, f21_4, f22_4, f23_4, f24_4, f25_4, f26_4, f27_4, f28_4, f29_4, f30_4;

    @FXML
    protected Button g1_4, g2_4, g3_4, g4_4, g5_4, g6_4, g7_4, g8_4, g9_4, g10_4, g11_4, g12_4, g13_4, g14_4, g15_4, g16_4, g17_4, g18_4, g19_4, g20_4, g21_4, g22_4, g23_4, g24_4, g25_4, g26_4, g27_4, g28_4, g29_4, g30_4;

    @FXML
    protected Button h1_4, h2_4, h3_4, h4_4, h5_4, h6_4, h7_4, h8_4, h9_4, h10_4, h11_4, h12_4, h13_4, h14_4, h15_4, h16_4, h17_4, h18_4, h19_4, h20_4, h21_4, h22_4, h23_4, h24_4, h25_4, h26_4, h27_4, h28_4, h29_4, h30_4;

    @FXML
    protected Button i1_4, i2_4, i3_4, i4_4, i5_4, i6_4, i7_4, i8_4, i9_4, i10_4, i11_4, i12_4, i13_4, i14_4, i15_4, i16_4, i17_4, i18_4, i19_4, i20_4, i21_4, i22_4, i23_4, i24_4, i25_4, i26_4, i27_4, i28_4, i29_4, i30_4;

    @FXML
    protected Button j1_4, j2_4, j3_4, j4_4, j5_4, j6_4, j7_4, j8_4, j9_4, j10_4, j11_4, j12_4, j13_4, j14_4, j15_4, j16_4, j17_4, j18_4, j19_4, j20_4, j21_4, j22_4, j23_4, j24_4, j25_4, j26_4;

    protected void dort_koltuk_dolu(int salon_id, int seans_id) {
        if (search("A1", salon_id, seans_id)) {
            a1_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("A2", salon_id, seans_id)) {
            a2_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("A3", salon_id, seans_id)) {
            a3_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("A4", salon_id, seans_id)) {
            a4_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("A5", salon_id, seans_id)) {
            a5_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("A6", salon_id, seans_id)) {
            a6_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("A7", salon_id, seans_id)) {
            a7_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("A8", salon_id, seans_id)) {
            a8_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("A9", salon_id, seans_id)) {
            a9_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("A10", salon_id, seans_id)) {
            a10_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("A11", salon_id, seans_id)) {
            a11_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("A12", salon_id, seans_id)) {
            a12_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("A13", salon_id, seans_id)) {
            a13_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("A14", salon_id, seans_id)) {
            a14_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("A15", salon_id, seans_id)) {
            a15_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("A16", salon_id, seans_id)) {
            a16_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("A17", salon_id, seans_id)) {
            a17_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("A18", salon_id, seans_id)) {
            a18_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("A19", salon_id, seans_id)) {
            a19_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("A20", salon_id, seans_id)) {
            a20_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("A21", salon_id, seans_id)) {
            a21_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("A22", salon_id, seans_id)) {
            a22_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("A23", salon_id, seans_id)) {
            a23_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("A24", salon_id, seans_id)) {
            a24_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("A25", salon_id, seans_id)) {
            a25_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("A26", salon_id, seans_id)) {
            a26_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("A27", salon_id, seans_id)) {
            a27_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("A28", salon_id, seans_id)) {
            a28_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("B1", salon_id, seans_id)) {
            b1_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("B2", salon_id, seans_id)) {
            b2_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("B3", salon_id, seans_id)) {
            b3_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("B4", salon_id, seans_id)) {
            b4_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("B5", salon_id, seans_id)) {
            b5_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("B6", salon_id, seans_id)) {
            b6_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("B7", salon_id, seans_id)) {
            b7_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("B8", salon_id, seans_id)) {
            b8_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("B9", salon_id, seans_id)) {
            b9_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("B10", salon_id, seans_id)) {
            b10_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("B11", salon_id, seans_id)) {
            b11_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("B12", salon_id, seans_id)) {
            b12_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("B13", salon_id, seans_id)) {
            b13_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("B14", salon_id, seans_id)) {
            b14_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("B15", salon_id, seans_id)) {
            b15_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("B16", salon_id, seans_id)) {
            b16_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("B17", salon_id, seans_id)) {
            b17_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("B18", salon_id, seans_id)) {
            b18_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("B19", salon_id, seans_id)) {
            b19_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("B20", salon_id, seans_id)) {
            b20_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("B21", salon_id, seans_id)) {
            b21_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("B22", salon_id, seans_id)) {
            b22_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("B23", salon_id, seans_id)) {
            b23_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("B24", salon_id, seans_id)) {
            b24_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("B25", salon_id, seans_id)) {
            b25_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("B26", salon_id, seans_id)) {
            b26_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("B27", salon_id, seans_id)) {
            b27_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("B28", salon_id, seans_id)) {
            b28_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("B29", salon_id, seans_id)) {
            b29_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("B30", salon_id, seans_id)) {
            b30_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("C1", salon_id, seans_id)) {
            c1_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("C2", salon_id, seans_id)) {
            c2_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("C3", salon_id, seans_id)) {
            c3_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("C4", salon_id, seans_id)) {
            c4_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("C5", salon_id, seans_id)) {
            c5_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("C6", salon_id, seans_id)) {
            c6_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("C7", salon_id, seans_id)) {
            c7_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("C8", salon_id, seans_id)) {
            c8_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("C9", salon_id, seans_id)) {
            c9_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("C10", salon_id, seans_id)) {
            c10_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("C11", salon_id, seans_id)) {
            c11_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("C12", salon_id, seans_id)) {
            c12_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("C13", salon_id, seans_id)) {
            c13_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("C14", salon_id, seans_id)) {
            c14_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("C15", salon_id, seans_id)) {
            c15_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("C16", salon_id, seans_id)) {
            c16_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("C17", salon_id, seans_id)) {
            c17_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("C18", salon_id, seans_id)) {
            c18_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("C19", salon_id, seans_id)) {
            c19_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("C20", salon_id, seans_id)) {
            c20_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("C21", salon_id, seans_id)) {
            c21_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("C22", salon_id, seans_id)) {
            c22_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("C23", salon_id, seans_id)) {
            c23_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("C24", salon_id, seans_id)) {
            c24_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("C25", salon_id, seans_id)) {
            c25_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("C26", salon_id, seans_id)) {
            c26_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("C27", salon_id, seans_id)) {
            c27_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("C28", salon_id, seans_id)) {
            c28_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("C29", salon_id, seans_id)) {
            c29_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("C30", salon_id, seans_id)) {
            c30_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("D1", salon_id, seans_id)) {
            d1_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("D2", salon_id, seans_id)) {
            d2_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("D3", salon_id, seans_id)) {
            d3_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("D4", salon_id, seans_id)) {
            d4_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("D5", salon_id, seans_id)) {
            d5_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("D6", salon_id, seans_id)) {
            d6_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("D7", salon_id, seans_id)) {
            d7_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("D8", salon_id, seans_id)) {
            d8_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("D9", salon_id, seans_id)) {
            d9_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("D10", salon_id, seans_id)) {
            d10_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("D11", salon_id, seans_id)) {
            d11_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("D12", salon_id, seans_id)) {
            d12_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("D13", salon_id, seans_id)) {
            d13_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("D14", salon_id, seans_id)) {
            d14_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("D15", salon_id, seans_id)) {
            d15_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("D16", salon_id, seans_id)) {
            d16_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("D17", salon_id, seans_id)) {
            d17_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("D18", salon_id, seans_id)) {
            d18_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("D19", salon_id, seans_id)) {
            d19_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("D20", salon_id, seans_id)) {
            d20_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("D21", salon_id, seans_id)) {
            d21_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("D22", salon_id, seans_id)) {
            d22_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("D23", salon_id, seans_id)) {
            d23_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("D24", salon_id, seans_id)) {
            d24_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("D25", salon_id, seans_id)) {
            d25_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("D26", salon_id, seans_id)) {
            d26_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("D27", salon_id, seans_id)) {
            d27_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("D28", salon_id, seans_id)) {
            d28_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("D29", salon_id, seans_id)) {
            d29_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("D30", salon_id, seans_id)) {
            d30_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("E1", salon_id, seans_id)) {
            e1_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("E2", salon_id, seans_id)) {
            e2_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("E3", salon_id, seans_id)) {
            e3_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("E4", salon_id, seans_id)) {
            e4_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("E5", salon_id, seans_id)) {
            e5_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("E6", salon_id, seans_id)) {
            e6_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("E7", salon_id, seans_id)) {
            e7_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("E8", salon_id, seans_id)) {
            e8_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("E9", salon_id, seans_id)) {
            e9_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("E10", salon_id, seans_id)) {
            e10_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("E11", salon_id, seans_id)) {
            e11_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("E12", salon_id, seans_id)) {
            e12_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("E13", salon_id, seans_id)) {
            e13_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("E14", salon_id, seans_id)) {
            e14_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("E15", salon_id, seans_id)) {
            e15_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("E16", salon_id, seans_id)) {
            e16_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("E17", salon_id, seans_id)) {
            e17_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("E18", salon_id, seans_id)) {
            e18_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("E19", salon_id, seans_id)) {
            e19_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("E20", salon_id, seans_id)) {
            e20_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("E21", salon_id, seans_id)) {
            e21_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("E22", salon_id, seans_id)) {
            e22_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("E23", salon_id, seans_id)) {
            e23_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("E24", salon_id, seans_id)) {
            e24_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("E25", salon_id, seans_id)) {
            e25_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("E26", salon_id, seans_id)) {
            e26_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("E27", salon_id, seans_id)) {
            e27_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("E28", salon_id, seans_id)) {
            e28_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("E29", salon_id, seans_id)) {
            e29_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("E30", salon_id, seans_id)) {
            e30_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("F1", salon_id, seans_id)) {
            f1_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("F2", salon_id, seans_id)) {
            f2_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("F3", salon_id, seans_id)) {
            f3_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("F4", salon_id, seans_id)) {
            f4_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("F5", salon_id, seans_id)) {
            f5_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("F6", salon_id, seans_id)) {
            f6_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("F7", salon_id, seans_id)) {
            f7_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("F8", salon_id, seans_id)) {
            f8_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("F9", salon_id, seans_id)) {
            f9_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("F10", salon_id, seans_id)) {
            f10_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("F11", salon_id, seans_id)) {
            f11_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("F12", salon_id, seans_id)) {
            f12_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("F13", salon_id, seans_id)) {
            f13_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("F14", salon_id, seans_id)) {
            f14_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("F15", salon_id, seans_id)) {
            f15_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("F16", salon_id, seans_id)) {
            f16_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("F17", salon_id, seans_id)) {
            f17_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("F18", salon_id, seans_id)) {
            f18_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("F19", salon_id, seans_id)) {
            f19_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("F20", salon_id, seans_id)) {
            f20_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("F21", salon_id, seans_id)) {
            f21_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("F22", salon_id, seans_id)) {
            f22_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("F23", salon_id, seans_id)) {
            f23_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("F24", salon_id, seans_id)) {
            f24_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("F25", salon_id, seans_id)) {
            f25_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("F26", salon_id, seans_id)) {
            f26_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("F27", salon_id, seans_id)) {
            f27_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("F28", salon_id, seans_id)) {
            f28_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("F29", salon_id, seans_id)) {
            f29_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("F30", salon_id, seans_id)) {
            f30_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("G1", salon_id, seans_id)) {
            g1_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("G2", salon_id, seans_id)) {
            g2_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("G3", salon_id, seans_id)) {
            g3_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("G4", salon_id, seans_id)) {
            g4_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("G5", salon_id, seans_id)) {
            g5_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("G6", salon_id, seans_id)) {
            g6_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("G7", salon_id, seans_id)) {
            g7_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("G8", salon_id, seans_id)) {
            g8_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("G9", salon_id, seans_id)) {
            g9_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("G10", salon_id, seans_id)) {
            g10_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("G11", salon_id, seans_id)) {
            g11_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("G12", salon_id, seans_id)) {
            g12_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("G13", salon_id, seans_id)) {
            g13_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("G14", salon_id, seans_id)) {
            g14_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("G15", salon_id, seans_id)) {
            g15_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("G16", salon_id, seans_id)) {
            g16_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("G17", salon_id, seans_id)) {
            g17_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("G18", salon_id, seans_id)) {
            g18_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("G19", salon_id, seans_id)) {
            g19_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("G20", salon_id, seans_id)) {
            g20_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("G21", salon_id, seans_id)) {
            g21_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("G22", salon_id, seans_id)) {
            g22_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("G23", salon_id, seans_id)) {
            g23_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("G24", salon_id, seans_id)) {
            g24_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("G25", salon_id, seans_id)) {
            g25_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("G26", salon_id, seans_id)) {
            g26_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("G27", salon_id, seans_id)) {
            g27_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("G28", salon_id, seans_id)) {
            g28_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("G29", salon_id, seans_id)) {
            g29_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("G30", salon_id, seans_id)) {
            g30_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("H1", salon_id, seans_id)) {
            h1_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("H2", salon_id, seans_id)) {
            h2_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("H3", salon_id, seans_id)) {
            h3_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("H4", salon_id, seans_id)) {
            h4_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("H5", salon_id, seans_id)) {
            h5_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("H6", salon_id, seans_id)) {
            h6_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("H7", salon_id, seans_id)) {
            h7_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("H8", salon_id, seans_id)) {
            h8_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("H9", salon_id, seans_id)) {
            h9_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("H10", salon_id, seans_id)) {
            h10_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("H11", salon_id, seans_id)) {
            h11_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("H12", salon_id, seans_id)) {
            h12_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("H13", salon_id, seans_id)) {
            h13_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("H14", salon_id, seans_id)) {
            h14_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("H15", salon_id, seans_id)) {
            h15_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("H16", salon_id, seans_id)) {
            h16_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("H17", salon_id, seans_id)) {
            h17_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("H18", salon_id, seans_id)) {
            h18_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("H19", salon_id, seans_id)) {
            h19_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("H20", salon_id, seans_id)) {
            h20_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("H21", salon_id, seans_id)) {
            h21_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("H22", salon_id, seans_id)) {
            h22_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("H23", salon_id, seans_id)) {
            h23_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("H24", salon_id, seans_id)) {
            h24_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("H25", salon_id, seans_id)) {
            h25_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("H26", salon_id, seans_id)) {
            h26_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("H27", salon_id, seans_id)) {
            h27_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("H28", salon_id, seans_id)) {
            h28_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("H29", salon_id, seans_id)) {
            h29_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("H30", salon_id, seans_id)) {
            h30_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("I1", salon_id, seans_id)) {
            i1_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("I2", salon_id, seans_id)) {
            i2_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("I3", salon_id, seans_id)) {
            i3_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("I4", salon_id, seans_id)) {
            i4_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("I5", salon_id, seans_id)) {
            i5_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("I6", salon_id, seans_id)) {
            i6_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("I7", salon_id, seans_id)) {
            i7_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("I8", salon_id, seans_id)) {
            i8_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("I9", salon_id, seans_id)) {
            i9_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("I10", salon_id, seans_id)) {
            i10_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("I11", salon_id, seans_id)) {
            i11_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("I12", salon_id, seans_id)) {
            i12_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("I13", salon_id, seans_id)) {
            i13_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("I14", salon_id, seans_id)) {
            i14_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("I15", salon_id, seans_id)) {
            i15_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("I16", salon_id, seans_id)) {
            i16_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("I17", salon_id, seans_id)) {
            i17_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("I18", salon_id, seans_id)) {
            i18_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("I19", salon_id, seans_id)) {
            i19_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("I20", salon_id, seans_id)) {
            i20_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("I21", salon_id, seans_id)) {
            i21_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("I22", salon_id, seans_id)) {
            i22_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("I23", salon_id, seans_id)) {
            i23_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("I24", salon_id, seans_id)) {
            i24_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("I25", salon_id, seans_id)) {
            i25_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("I26", salon_id, seans_id)) {
            i26_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("I27", salon_id, seans_id)) {
            i27_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("I28", salon_id, seans_id)) {
            i28_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("I29", salon_id, seans_id)) {
            i29_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("I30", salon_id, seans_id)) {
            i30_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("J1", salon_id, seans_id)) {
            j1_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("J2", salon_id, seans_id)) {
            j2_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("J3", salon_id, seans_id)) {
            j3_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("J4", salon_id, seans_id)) {
            j4_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("J5", salon_id, seans_id)) {
            j5_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("J6", salon_id, seans_id)) {
            j6_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("J7", salon_id, seans_id)) {
            j7_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("J8", salon_id, seans_id)) {
            j8_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("J9", salon_id, seans_id)) {
            j9_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("J10", salon_id, seans_id)) {
            j10_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("J11", salon_id, seans_id)) {
            j11_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("J12", salon_id, seans_id)) {
            j12_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("J13", salon_id, seans_id)) {
            j13_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("J14", salon_id, seans_id)) {
            j14_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("J15", salon_id, seans_id)) {
            j15_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("J16", salon_id, seans_id)) {
            j16_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("J17", salon_id, seans_id)) {
            j17_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("J18", salon_id, seans_id)) {
            j18_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("J19", salon_id, seans_id)) {
            j19_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("J20", salon_id, seans_id)) {
            j20_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("J21", salon_id, seans_id)) {
            j21_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("J22", salon_id, seans_id)) {
            j22_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("J23", salon_id, seans_id)) {
            j23_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("J24", salon_id, seans_id)) {
            j24_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("J25", salon_id, seans_id)) {
            j25_4.styleProperty().set("-fx-background-color: red");
        }
        if (search("J26", salon_id, seans_id)) {
            j26_4.styleProperty().set("-fx-background-color: red");
        }

    }

    @FXML
    protected void a1_4(ActionEvent event) {
        buton_aktif(a1_4);
    }

    @FXML
    protected void a2_4(ActionEvent event) {
        buton_aktif(a2_4);
    }

    @FXML
    protected void a3_4(ActionEvent event) {
        buton_aktif(a3_4);
    }

    @FXML
    protected void a4_4(ActionEvent event) {
        buton_aktif(a4_4);
    }

    @FXML
    protected void a5_4(ActionEvent event) {
        buton_aktif(a5_4);
    }

    @FXML
    protected void a6_4(ActionEvent event) {
        buton_aktif(a6_4);
    }

    @FXML
    protected void a7_4(ActionEvent event) {
        buton_aktif(a7_4);
    }

    @FXML
    protected void a8_4(ActionEvent event) {
        buton_aktif(a8_4);
    }

    @FXML
    protected void a9_4(ActionEvent event) {
        buton_aktif(a9_4);
    }

    @FXML
    protected void a10_4(ActionEvent event) {
        buton_aktif(a10_4);
    }

    @FXML
    protected void a11_4(ActionEvent event) {
        buton_aktif(a11_4);
    }

    @FXML
    protected void a12_4(ActionEvent event) {
        buton_aktif(a12_4);
    }

    @FXML
    protected void a13_4(ActionEvent event) {
        buton_aktif(a13_4);
    }

    @FXML
    protected void a14_4(ActionEvent event) {
        buton_aktif(a14_4);
    }

    @FXML
    protected void a15_4(ActionEvent event) {
        buton_aktif(a15_4);
    }

    @FXML
    protected void a16_4(ActionEvent event) {
        buton_aktif(a16_4);
    }

    @FXML
    protected void a17_4(ActionEvent event) {
        buton_aktif(a17_4);
    }

    @FXML
    protected void a18_4(ActionEvent event) {
        buton_aktif(a18_4);
    }

    @FXML
    protected void a19_4(ActionEvent event) {
        buton_aktif(a19_4);
    }

    @FXML
    protected void a20_4(ActionEvent event) {
        buton_aktif(a20_4);
    }

    @FXML
    protected void a21_4(ActionEvent event) {
        buton_aktif(a21_4);
    }

    @FXML
    protected void a22_4(ActionEvent event) {
        buton_aktif(a22_4);
    }

    @FXML
    protected void a23_4(ActionEvent event) {
        buton_aktif(a23_4);
    }

    @FXML
    protected void a24_4(ActionEvent event) {
        buton_aktif(a24_4);
    }

    @FXML
    protected void a25_4(ActionEvent event) {
        buton_aktif(a25_4);
    }

    @FXML
    protected void a26_4(ActionEvent event) {
        buton_aktif(a26_4);
    }

    @FXML
    protected void a27_4(ActionEvent event) {
        buton_aktif(a27_4);
    }

    @FXML
    protected void a28_4(ActionEvent event) {
        buton_aktif(a28_4);
    }

    @FXML
    protected void b1_4(ActionEvent event) {
        buton_aktif(b1_4);
    }

    @FXML
    protected void b2_4(ActionEvent event) {
        buton_aktif(b2_4);
    }

    @FXML
    protected void b3_4(ActionEvent event) {
        buton_aktif(b3_4);
    }

    @FXML
    protected void b4_4(ActionEvent event) {
        buton_aktif(b4_4);
    }

    @FXML
    protected void b5_4(ActionEvent event) {
        buton_aktif(b5_4);
    }

    @FXML
    protected void b6_4(ActionEvent event) {
        buton_aktif(b6_4);
    }

    @FXML
    protected void b7_4(ActionEvent event) {
        buton_aktif(b7_4);
    }

    @FXML
    protected void b8_4(ActionEvent event) {
        buton_aktif(b8_4);
    }

    @FXML
    protected void b9_4(ActionEvent event) {
        buton_aktif(b9_4);
    }

    @FXML
    protected void b10_4(ActionEvent event) {
        buton_aktif(b10_4);
    }

    @FXML
    protected void b11_4(ActionEvent event) {
        buton_aktif(b11_4);
    }

    @FXML
    protected void b12_4(ActionEvent event) {
        buton_aktif(b12_4);
    }

    @FXML
    protected void b13_4(ActionEvent event) {
        buton_aktif(b13_4);
    }

    @FXML
    protected void b14_4(ActionEvent event) {
        buton_aktif(b14_4);
    }

    @FXML
    protected void b15_4(ActionEvent event) {
        buton_aktif(b15_4);
    }

    @FXML
    protected void b16_4(ActionEvent event) {
        buton_aktif(b16_4);
    }

    @FXML
    protected void b17_4(ActionEvent event) {
        buton_aktif(b17_4);
    }

    @FXML
    protected void b18_4(ActionEvent event) {
        buton_aktif(b18_4);
    }

    @FXML
    protected void b19_4(ActionEvent event) {
        buton_aktif(b19_4);
    }

    @FXML
    protected void b20_4(ActionEvent event) {
        buton_aktif(b20_4);
    }

    @FXML
    protected void b21_4(ActionEvent event) {
        buton_aktif(b21_4);
    }

    @FXML
    protected void b22_4(ActionEvent event) {
        buton_aktif(b22_4);
    }

    @FXML
    protected void b23_4(ActionEvent event) {
        buton_aktif(b23_4);
    }

    @FXML
    protected void b24_4(ActionEvent event) {
        buton_aktif(b24_4);
    }

    @FXML
    protected void b25_4(ActionEvent event) {
        buton_aktif(b25_4);
    }

    @FXML
    protected void b26_4(ActionEvent event) {
        buton_aktif(b26_4);
    }

    @FXML
    protected void b27_4(ActionEvent event) {
        buton_aktif(b27_4);
    }

    @FXML
    protected void b28_4(ActionEvent event) {
        buton_aktif(b28_4);
    }

    @FXML
    protected void b29_4(ActionEvent event) {
        buton_aktif(b29_4);
    }

    @FXML
    protected void b30_4(ActionEvent event) {
        buton_aktif(b30_4);
    }

    @FXML
    protected void c1_4(ActionEvent event) {
        buton_aktif(c1_4);
    }

    @FXML
    protected void c2_4(ActionEvent event) {
        buton_aktif(c2_4);
    }

    @FXML
    protected void c3_4(ActionEvent event) {
        buton_aktif(c3_4);
    }

    @FXML
    protected void c4_4(ActionEvent event) {
        buton_aktif(c4_4);
    }

    @FXML
    protected void c5_4(ActionEvent event) {
        buton_aktif(c5_4);
    }

    @FXML
    protected void c6_4(ActionEvent event) {
        buton_aktif(c6_4);
    }

    @FXML
    protected void c7_4(ActionEvent event) {
        buton_aktif(c7_4);
    }

    @FXML
    protected void c8_4(ActionEvent event) {
        buton_aktif(c8_4);
    }

    @FXML
    protected void c9_4(ActionEvent event) {
        buton_aktif(c9_4);
    }

    @FXML
    protected void c10_4(ActionEvent event) {
        buton_aktif(c10_4);
    }

    @FXML
    protected void c11_4(ActionEvent event) {
        buton_aktif(c11_4);
    }

    @FXML
    protected void c12_4(ActionEvent event) {
        buton_aktif(c12_4);
    }

    @FXML
    protected void c13_4(ActionEvent event) {
        buton_aktif(c13_4);
    }

    @FXML
    protected void c14_4(ActionEvent event) {
        buton_aktif(c14_4);
    }

    @FXML
    protected void c15_4(ActionEvent event) {
        buton_aktif(c15_4);
    }

    @FXML
    protected void c16_4(ActionEvent event) {
        buton_aktif(c16_4);
    }

    @FXML
    protected void c17_4(ActionEvent event) {
        buton_aktif(c17_4);
    }

    @FXML
    protected void c18_4(ActionEvent event) {
        buton_aktif(c18_4);
    }

    @FXML
    protected void c19_4(ActionEvent event) {
        buton_aktif(c19_4);
    }

    @FXML
    protected void c20_4(ActionEvent event) {
        buton_aktif(c20_4);
    }

    @FXML
    protected void c21_4(ActionEvent event) {
        buton_aktif(c21_4);
    }

    @FXML
    protected void c22_4(ActionEvent event) {
        buton_aktif(c22_4);
    }

    @FXML
    protected void c23_4(ActionEvent event) {
        buton_aktif(c23_4);
    }

    @FXML
    protected void c24_4(ActionEvent event) {
        buton_aktif(c24_4);
    }

    @FXML
    protected void c25_4(ActionEvent event) {
        buton_aktif(c25_4);
    }

    @FXML
    protected void c26_4(ActionEvent event) {
        buton_aktif(c26_4);
    }

    @FXML
    protected void c27_4(ActionEvent event) {
        buton_aktif(c27_4);
    }

    @FXML
    protected void c28_4(ActionEvent event) {
        buton_aktif(c28_4);
    }

    @FXML
    protected void c29_4(ActionEvent event) {
        buton_aktif(c29_4);
    }

    @FXML
    protected void c30_4(ActionEvent event) {
        buton_aktif(c30_4);
    }

    @FXML
    protected void d1_4(ActionEvent event) {
        buton_aktif(d1_4);
    }

    @FXML
    protected void d2_4(ActionEvent event) {
        buton_aktif(d2_4);
    }

    @FXML
    protected void d3_4(ActionEvent event) {
        buton_aktif(d3_4);
    }

    @FXML
    protected void d4_4(ActionEvent event) {
        buton_aktif(d4_4);
    }

    @FXML
    protected void d5_4(ActionEvent event) {
        buton_aktif(d5_4);
    }

    @FXML
    protected void d6_4(ActionEvent event) {
        buton_aktif(d6_4);
    }

    @FXML
    protected void d7_4(ActionEvent event) {
        buton_aktif(d7_4);
    }

    @FXML
    protected void d8_4(ActionEvent event) {
        buton_aktif(d8_4);
    }

    @FXML
    protected void d9_4(ActionEvent event) {
        buton_aktif(d9_4);
    }

    @FXML
    protected void d10_4(ActionEvent event) {
        buton_aktif(d10_4);
    }

    @FXML
    protected void d11_4(ActionEvent event) {
        buton_aktif(d11_4);
    }

    @FXML
    protected void d12_4(ActionEvent event) {
        buton_aktif(d12_4);
    }

    @FXML
    protected void d13_4(ActionEvent event) {
        buton_aktif(d13_4);
    }

    @FXML
    protected void d14_4(ActionEvent event) {
        buton_aktif(d14_4);
    }

    @FXML
    protected void d15_4(ActionEvent event) {
        buton_aktif(d15_4);
    }

    @FXML
    protected void d16_4(ActionEvent event) {
        buton_aktif(d16_4);
    }

    @FXML
    protected void d17_4(ActionEvent event) {
        buton_aktif(d17_4);
    }

    @FXML
    protected void d18_4(ActionEvent event) {
        buton_aktif(d18_4);
    }

    @FXML
    protected void d19_4(ActionEvent event) {
        buton_aktif(d19_4);
    }

    @FXML
    protected void d20_4(ActionEvent event) {
        buton_aktif(d20_4);
    }

    @FXML
    protected void d21_4(ActionEvent event) {
        buton_aktif(d21_4);
    }

    @FXML
    protected void d22_4(ActionEvent event) {
        buton_aktif(d22_4);
    }

    @FXML
    protected void d23_4(ActionEvent event) {
        buton_aktif(d23_4);
    }

    @FXML
    protected void d24_4(ActionEvent event) {
        buton_aktif(d24_4);
    }

    @FXML
    protected void d25_4(ActionEvent event) {
        buton_aktif(d25_4);
    }

    @FXML
    protected void d26_4(ActionEvent event) {
        buton_aktif(d26_4);
    }

    @FXML
    protected void d27_4(ActionEvent event) {
        buton_aktif(d27_4);
    }

    @FXML
    protected void d28_4(ActionEvent event) {
        buton_aktif(d28_4);
    }

    @FXML
    protected void d29_4(ActionEvent event) {
        buton_aktif(d29_4);
    }

    @FXML
    protected void d30_4(ActionEvent event) {
        buton_aktif(d30_4);
    }

    @FXML
    protected void e1_4(ActionEvent event) {
        buton_aktif(e1_4);
    }

    @FXML
    protected void e2_4(ActionEvent event) {
        buton_aktif(e2_4);
    }

    @FXML
    protected void e3_4(ActionEvent event) {
        buton_aktif(e3_4);
    }

    @FXML
    protected void e4_4(ActionEvent event) {
        buton_aktif(e4_4);
    }

    @FXML
    protected void e5_4(ActionEvent event) {
        buton_aktif(e5_4);
    }

    @FXML
    protected void e6_4(ActionEvent event) {
        buton_aktif(e6_4);
    }

    @FXML
    protected void e7_4(ActionEvent event) {
        buton_aktif(e7_4);
    }

    @FXML
    protected void e8_4(ActionEvent event) {
        buton_aktif(e8_4);
    }

    @FXML
    protected void e9_4(ActionEvent event) {
        buton_aktif(e9_4);
    }

    @FXML
    protected void e10_4(ActionEvent event) {
        buton_aktif(e10_4);
    }

    @FXML
    protected void e11_4(ActionEvent event) {
        buton_aktif(e11_4);
    }

    @FXML
    protected void e12_4(ActionEvent event) {
        buton_aktif(e12_4);
    }

    @FXML
    protected void e13_4(ActionEvent event) {
        buton_aktif(e13_4);
    }

    @FXML
    protected void e14_4(ActionEvent event) {
        buton_aktif(e14_4);
    }

    @FXML
    protected void e15_4(ActionEvent event) {
        buton_aktif(e15_4);
    }

    @FXML
    protected void e16_4(ActionEvent event) {
        buton_aktif(e16_4);
    }

    @FXML
    protected void e17_4(ActionEvent event) {
        buton_aktif(e17_4);
    }

    @FXML
    protected void e18_4(ActionEvent event) {
        buton_aktif(e18_4);
    }

    @FXML
    protected void e19_4(ActionEvent event) {
        buton_aktif(e19_4);
    }

    @FXML
    protected void e20_4(ActionEvent event) {
        buton_aktif(e20_4);
    }

    @FXML
    protected void e21_4(ActionEvent event) {
        buton_aktif(e21_4);
    }

    @FXML
    protected void e22_4(ActionEvent event) {
        buton_aktif(e22_4);
    }

    @FXML
    protected void e23_4(ActionEvent event) {
        buton_aktif(e23_4);
    }

    @FXML
    protected void e24_4(ActionEvent event) {
        buton_aktif(e24_4);
    }

    @FXML
    protected void e25_4(ActionEvent event) {
        buton_aktif(e25_4);
    }

    @FXML
    protected void e26_4(ActionEvent event) {
        buton_aktif(e26_4);
    }

    @FXML
    protected void e27_4(ActionEvent event) {
        buton_aktif(e27_4);
    }

    @FXML
    protected void e28_4(ActionEvent event) {
        buton_aktif(e28_4);
    }

    @FXML
    protected void e29_4(ActionEvent event) {
        buton_aktif(e29_4);
    }

    @FXML
    protected void e30_4(ActionEvent event) {
        buton_aktif(e30_4);
    }

    @FXML
    protected void f1_4(ActionEvent event) {
        buton_aktif(f1_4);
    }

    @FXML
    protected void f2_4(ActionEvent event) {
        buton_aktif(f2_4);
    }

    @FXML
    protected void f3_4(ActionEvent event) {
        buton_aktif(f3_4);
    }

    @FXML
    protected void f4_4(ActionEvent event) {
        buton_aktif(f4_4);
    }

    @FXML
    protected void f5_4(ActionEvent event) {
        buton_aktif(f5_4);
    }

    @FXML
    protected void f6_4(ActionEvent event) {
        buton_aktif(f6_4);
    }

    @FXML
    protected void f7_4(ActionEvent event) {
        buton_aktif(f7_4);
    }

    @FXML
    protected void f8_4(ActionEvent event) {
        buton_aktif(f8_4);
    }

    @FXML
    protected void f9_4(ActionEvent event) {
        buton_aktif(f9_4);
    }

    @FXML
    protected void f10_4(ActionEvent event) {
        buton_aktif(f10_4);
    }

    @FXML
    protected void f11_4(ActionEvent event) {
        buton_aktif(f11_4);
    }

    @FXML
    protected void f12_4(ActionEvent event) {
        buton_aktif(f12_4);
    }

    @FXML
    protected void f13_4(ActionEvent event) {
        buton_aktif(f13_4);
    }

    @FXML
    protected void f14_4(ActionEvent event) {
        buton_aktif(f14_4);
    }

    @FXML
    protected void f15_4(ActionEvent event) {
        buton_aktif(f15_4);
    }

    @FXML
    protected void f16_4(ActionEvent event) {
        buton_aktif(f16_4);
    }

    @FXML
    protected void f17_4(ActionEvent event) {
        buton_aktif(f17_4);
    }

    @FXML
    protected void f18_4(ActionEvent event) {
        buton_aktif(f18_4);
    }

    @FXML
    protected void f19_4(ActionEvent event) {
        buton_aktif(f19_4);
    }

    @FXML
    protected void f20_4(ActionEvent event) {
        buton_aktif(f20_4);
    }

    @FXML
    protected void f21_4(ActionEvent event) {
        buton_aktif(f21_4);
    }

    @FXML
    protected void f22_4(ActionEvent event) {
        buton_aktif(f22_4);
    }

    @FXML
    protected void f23_4(ActionEvent event) {
        buton_aktif(f23_4);
    }

    @FXML
    protected void f24_4(ActionEvent event) {
        buton_aktif(f24_4);
    }

    @FXML
    protected void f25_4(ActionEvent event) {
        buton_aktif(f25_4);
    }

    @FXML
    protected void f26_4(ActionEvent event) {
        buton_aktif(f26_4);
    }

    @FXML
    protected void f27_4(ActionEvent event) {
        buton_aktif(f27_4);
    }

    @FXML
    protected void f28_4(ActionEvent event) {
        buton_aktif(f28_4);
    }

    @FXML
    protected void f29_4(ActionEvent event) {
        buton_aktif(f29_4);
    }

    @FXML
    protected void f30_4(ActionEvent event) {
        buton_aktif(f30_4);
    }

    @FXML
    protected void g1_4(ActionEvent event) {
        buton_aktif(g1_4);
    }

    @FXML
    protected void g2_4(ActionEvent event) {
        buton_aktif(g2_4);
    }

    @FXML
    protected void g3_4(ActionEvent event) {
        buton_aktif(g3_4);
    }

    @FXML
    protected void g4_4(ActionEvent event) {
        buton_aktif(g4_4);
    }

    @FXML
    protected void g5_4(ActionEvent event) {
        buton_aktif(g5_4);
    }

    @FXML
    protected void g6_4(ActionEvent event) {
        buton_aktif(g6_4);
    }

    @FXML
    protected void g7_4(ActionEvent event) {
        buton_aktif(g7_4);
    }

    @FXML
    protected void g8_4(ActionEvent event) {
        buton_aktif(g8_4);
    }

    @FXML
    protected void g9_4(ActionEvent event) {
        buton_aktif(g9_4);
    }

    @FXML
    protected void g10_4(ActionEvent event) {
        buton_aktif(g10_4);
    }

    @FXML
    protected void g11_4(ActionEvent event) {
        buton_aktif(g11_4);
    }

    @FXML
    protected void g12_4(ActionEvent event) {
        buton_aktif(g12_4);
    }

    @FXML
    protected void g13_4(ActionEvent event) {
        buton_aktif(g13_4);
    }

    @FXML
    protected void g14_4(ActionEvent event) {
        buton_aktif(g14_4);
    }

    @FXML
    protected void g15_4(ActionEvent event) {
        buton_aktif(g15_4);
    }

    @FXML
    protected void g16_4(ActionEvent event) {
        buton_aktif(g16_4);
    }

    @FXML
    protected void g17_4(ActionEvent event) {
        buton_aktif(g17_4);
    }

    @FXML
    protected void g18_4(ActionEvent event) {
        buton_aktif(g18_4);
    }

    @FXML
    protected void g19_4(ActionEvent event) {
        buton_aktif(g19_4);
    }

    @FXML
    protected void g20_4(ActionEvent event) {
        buton_aktif(g20_4);
    }

    @FXML
    protected void g21_4(ActionEvent event) {
        buton_aktif(g21_4);
    }

    @FXML
    protected void g22_4(ActionEvent event) {
        buton_aktif(g22_4);
    }

    @FXML
    protected void g23_4(ActionEvent event) {
        buton_aktif(g23_4);
    }

    @FXML
    protected void g24_4(ActionEvent event) {
        buton_aktif(g24_4);
    }

    @FXML
    protected void g25_4(ActionEvent event) {
        buton_aktif(g25_4);
    }

    @FXML
    protected void g26_4(ActionEvent event) {
        buton_aktif(g26_4);
    }

    @FXML
    protected void g27_4(ActionEvent event) {
        buton_aktif(g27_4);
    }

    @FXML
    protected void g28_4(ActionEvent event) {
        buton_aktif(g28_4);
    }

    @FXML
    protected void g29_4(ActionEvent event) {
        buton_aktif(g29_4);
    }

    @FXML
    protected void g30_4(ActionEvent event) {
        buton_aktif(g30_4);
    }

    @FXML
    protected void h1_4(ActionEvent event) {
        buton_aktif(h1_4);
    }

    @FXML
    protected void h2_4(ActionEvent event) {
        buton_aktif(h2_4);
    }

    @FXML
    protected void h3_4(ActionEvent event) {
        buton_aktif(h3_4);
    }

    @FXML
    protected void h4_4(ActionEvent event) {
        buton_aktif(h4_4);
    }

    @FXML
    protected void h5_4(ActionEvent event) {
        buton_aktif(h5_4);
    }

    @FXML
    protected void h6_4(ActionEvent event) {
        buton_aktif(h6_4);
    }

    @FXML
    protected void h7_4(ActionEvent event) {
        buton_aktif(h7_4);
    }

    @FXML
    protected void h8_4(ActionEvent event) {
        buton_aktif(h8_4);
    }

    @FXML
    protected void h9_4(ActionEvent event) {
        buton_aktif(h9_4);
    }

    @FXML
    protected void h10_4(ActionEvent event) {
        buton_aktif(h10_4);
    }

    @FXML
    protected void h11_4(ActionEvent event) {
        buton_aktif(h11_4);
    }

    @FXML
    protected void h12_4(ActionEvent event) {
        buton_aktif(h12_4);
    }

    @FXML
    protected void h13_4(ActionEvent event) {
        buton_aktif(h13_4);
    }

    @FXML
    protected void h14_4(ActionEvent event) {
        buton_aktif(h14_4);
    }

    @FXML
    protected void h15_4(ActionEvent event) {
        buton_aktif(h15_4);
    }

    @FXML
    protected void h16_4(ActionEvent event) {
        buton_aktif(h16_4);
    }

    @FXML
    protected void h17_4(ActionEvent event) {
        buton_aktif(h17_4);
    }

    @FXML
    protected void h18_4(ActionEvent event) {
        buton_aktif(h18_4);
    }

    @FXML
    protected void h19_4(ActionEvent event) {
        buton_aktif(h19_4);
    }

    @FXML
    protected void h20_4(ActionEvent event) {
        buton_aktif(h20_4);
    }

    @FXML
    protected void h21_4(ActionEvent event) {
        buton_aktif(h21_4);
    }

    @FXML
    protected void h22_4(ActionEvent event) {
        buton_aktif(h22_4);
    }

    @FXML
    protected void h23_4(ActionEvent event) {
        buton_aktif(h23_4);
    }

    @FXML
    protected void h24_4(ActionEvent event) {
        buton_aktif(h24_4);
    }

    @FXML
    protected void h25_4(ActionEvent event) {
        buton_aktif(h25_4);
    }

    @FXML
    protected void h26_4(ActionEvent event) {
        buton_aktif(h26_4);
    }

    @FXML
    protected void h27_4(ActionEvent event) {
        buton_aktif(h27_4);
    }

    @FXML
    protected void h28_4(ActionEvent event) {
        buton_aktif(h28_4);
    }

    @FXML
    protected void h29_4(ActionEvent event) {
        buton_aktif(h29_4);
    }

    @FXML
    protected void h30_4(ActionEvent event) {
        buton_aktif(h30_4);
    }

    @FXML
    protected void i1_4(ActionEvent event) {
        buton_aktif(i1_4);
    }

    @FXML
    protected void i2_4(ActionEvent event) {
        buton_aktif(i2_4);
    }

    @FXML
    protected void i3_4(ActionEvent event) {
        buton_aktif(i3_4);
    }

    @FXML
    protected void i4_4(ActionEvent event) {
        buton_aktif(i4_4);
    }

    @FXML
    protected void i5_4(ActionEvent event) {
        buton_aktif(i5_4);
    }

    @FXML
    protected void i6_4(ActionEvent event) {
        buton_aktif(i6_4);
    }

    @FXML
    protected void i7_4(ActionEvent event) {
        buton_aktif(i7_4);
    }

    @FXML
    protected void i8_4(ActionEvent event) {
        buton_aktif(i8_4);
    }

    @FXML
    protected void i9_4(ActionEvent event) {
        buton_aktif(i9_4);
    }

    @FXML
    protected void i10_4(ActionEvent event) {
        buton_aktif(i10_4);
    }

    @FXML
    protected void i11_4(ActionEvent event) {
        buton_aktif(i11_4);
    }

    @FXML
    protected void i12_4(ActionEvent event) {
        buton_aktif(i12_4);
    }

    @FXML
    protected void i13_4(ActionEvent event) {
        buton_aktif(i13_4);
    }

    @FXML
    protected void i14_4(ActionEvent event) {
        buton_aktif(i14_4);
    }

    @FXML
    protected void i15_4(ActionEvent event) {
        buton_aktif(i15_4);
    }

    @FXML
    protected void i16_4(ActionEvent event) {
        buton_aktif(i16_4);
    }

    @FXML
    protected void i17_4(ActionEvent event) {
        buton_aktif(i17_4);
    }

    @FXML
    protected void i18_4(ActionEvent event) {
        buton_aktif(i18_4);
    }

    @FXML
    protected void i19_4(ActionEvent event) {
        buton_aktif(i19_4);
    }

    @FXML
    protected void i20_4(ActionEvent event) {
        buton_aktif(i20_4);
    }

    @FXML
    protected void i21_4(ActionEvent event) {
        buton_aktif(i21_4);
    }

    @FXML
    protected void i22_4(ActionEvent event) {
        buton_aktif(i22_4);
    }

    @FXML
    protected void i23_4(ActionEvent event) {
        buton_aktif(i23_4);
    }

    @FXML
    protected void i24_4(ActionEvent event) {
        buton_aktif(i24_4);
    }

    @FXML
    protected void i25_4(ActionEvent event) {
        buton_aktif(i25_4);
    }

    @FXML
    protected void i26_4(ActionEvent event) {
        buton_aktif(i26_4);
    }

    @FXML
    protected void i27_4(ActionEvent event) {
        buton_aktif(i27_4);
    }

    @FXML
    protected void i28_4(ActionEvent event) {
        buton_aktif(i28_4);
    }

    @FXML
    protected void i29_4(ActionEvent event) {
        buton_aktif(i29_4);
    }

    @FXML
    protected void i30_4(ActionEvent event) {
        buton_aktif(i30_4);
    }

    @FXML
    protected void j1_4(ActionEvent event) {
        buton_aktif(j1_4);
    }

    @FXML
    protected void j2_4(ActionEvent event) {
        buton_aktif(j2_4);
    }

    @FXML
    protected void j3_4(ActionEvent event) {
        buton_aktif(j3_4);
    }

    @FXML
    protected void j4_4(ActionEvent event) {
        buton_aktif(j4_4);
    }

    @FXML
    protected void j5_4(ActionEvent event) {
        buton_aktif(j5_4);
    }

    @FXML
    protected void j6_4(ActionEvent event) {
        buton_aktif(j6_4);
    }

    @FXML
    protected void j7_4(ActionEvent event) {
        buton_aktif(j7_4);
    }

    @FXML
    protected void j8_4(ActionEvent event) {
        buton_aktif(j8_4);
    }

    @FXML
    protected void j9_4(ActionEvent event) {
        buton_aktif(j9_4);
    }

    @FXML
    protected void j10_4(ActionEvent event) {
        buton_aktif(j10_4);
    }

    @FXML
    protected void j11_4(ActionEvent event) {
        buton_aktif(j11_4);
    }

    @FXML
    protected void j12_4(ActionEvent event) {
        buton_aktif(j12_4);
    }

    @FXML
    protected void j13_4(ActionEvent event) {
        buton_aktif(j13_4);
    }

    @FXML
    protected void j14_4(ActionEvent event) {
        buton_aktif(j14_4);
    }

    @FXML
    protected void j15_4(ActionEvent event) {
        buton_aktif(j15_4);
    }

    @FXML
    protected void j16_4(ActionEvent event) {
        buton_aktif(j16_4);
    }

    @FXML
    protected void j17_4(ActionEvent event) {
        buton_aktif(j17_4);
    }

    @FXML
    protected void j18_4(ActionEvent event) {
        buton_aktif(j18_4);
    }

    @FXML
    protected void j19_4(ActionEvent event) {
        buton_aktif(j19_4);
    }

    @FXML
    protected void j20_4(ActionEvent event) {
        buton_aktif(j20_4);
    }

    @FXML
    protected void j21_4(ActionEvent event) {
        buton_aktif(j21_4);
    }

    @FXML
    protected void j22_4(ActionEvent event) {
        buton_aktif(j22_4);
    }

    @FXML
    protected void j23_4(ActionEvent event) {
        buton_aktif(j23_4);
    }

    @FXML
    protected void j24_4(ActionEvent event) {
        buton_aktif(j24_4);
    }

    @FXML
    protected void j25_4(ActionEvent event) {
        buton_aktif(j25_4);
    }

    @FXML
    protected void j26_4(ActionEvent event) {
        buton_aktif(j26_4);
    }

}
