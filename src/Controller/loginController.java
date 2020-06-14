
package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import code.*;
import java.io.IOException;
import javafx.scene.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class loginController implements Initializable {

    // program tam ekranda mı yoksa normal boyutta mı test etmek için bu değişkeni tanımlıyoruz. Tam ekranda ise true, değilse false oluyor.
    private boolean a = false;

    //şifreyi göster ya da şifreyi gizle metodu için tanımlanan bir değişken. şifrw gösteriliyorsa true, şifre gösterilmiyorsa false oluyor ve ona göre işlemler yapıyoruz.
    private boolean sifre_gosterim = false;

    /*
    kullanıcının giriş yapabilmesi için gerekli olan alanları tanımlıyoruz. 
    tf_password'u hem textfield hem de password field olarak tanımladık. Bunun sebebi kullanıcı şifre göstere tıkladığında text fild aktif oluyor. şifre gizleye tıkladığında ise passwordfield aktif oluyor.
     */
    @FXML
    private TextField tf_username, tf_password;

    @FXML
    private PasswordField pf_password;

    //eğer bir hata çıkarsa bunu ekrana yazdırmak için bir label tanımlıyoruz.
    @FXML
    private Label label;

    //kullanıcının isteği doğrultusunda şifreyi göster ya da şifreyi gizle özelliği için gerekli olan hbox'ları tanımlıyoruz.
    @FXML
    private HBox gizli, acik;

    //bu giriş yapmak için gerekli olan method.
    @FXML
    private void login(ActionEvent event) throws IOException {
        //ilk başta kullanınıcının username bilgisini alıyoruz.
        String user_name = tf_username.getText();
        String password = null;
        // daha sonra sifre gosterim true ise textfield olandan passwordu alıyoruz. sifre gösterim false ise passwordfield'dan olanı alıyoruz.
        if (sifre_gosterim == false) {
            password = pf_password.getText();
        } else {
            password = tf_password.getText();
        }

        //ardından bunların boyutlarını kontrol ediyoruz. Yani kullanıcı gerekli yerleri doldurmuş mu diye kontrol ediyoruz. doldurmamışsa da ekrana uyarı mesajı veriyoruz.
        if ((password.length() == 0) || (user_name.length() == 0)) {
            //program bu if'e girmişse kullanıcı gerekli bilgileri doldurmadı demek oluyor. Ekrana da çıkan hatayı yazıyoruz ve diğer işlemleri yapmıyoruz.
            label.setText("Lütfen Gerekli Yerleri Doldurunuz.");
        } else {
            //program bu else girmiş ise kullanıcı gerekli yerleri doldurmuş demektir. Bundan sonra kullanıcının bilgilerini kontrol ediyoruz.

            //bu 3 satır var olan tüm kullanıcıların bilgilerini dosya okuma ile dosyadan çekiyor.
            User user_list = new User();
            Dosya dosya_islemleri = new Dosya();
            dosya_islemleri.user_dosya_oku(user_list);

            //bu satır ise code paketinde bulunan User sınıfının içindeki control metodunu çağrıyor ve kullanıcının şifresi ile kullanıcı adının uyuşup uyuşmadığını gösteriyor.
            int user_id = user_list.control(user_name, password);
            //user_id = -1 gelir ise herhangi bir kullanıcı ile uyuşmadığını gösteriyor. eğer -1 değil de farklı bir değer gelirse de kullanıcının bilgilerinin doğru olduğunu görüp gerekli işlemleri yapıyoruz.

            if (user_id == -1) {
                //user_id, -1 ise kullanıcıya bilgi veriyoruz ve diğer işlemleri yapmıyoruz.
                label.setText("Kullanıcı adı ya da şifre hatalı");

            } else {
                //user_id, -1 değilse kullanıcının verileri dorğu demektir ve ona göre de işlemlerimize devam ediyoruz.

                //daha sonra almak için ve bilgi bozukluluğu olmaması adına giriş yapan kullanıcının id'sini bir dosyaya kaydediyoruz.
                dosya_islemleri.bilgi_dosya_yaz(user_id);

                //daha sonra giriş yapan kullanıcının yetkisini öğreniyoruz. ve ona göre işlem yapıyoruz.0: Normal kullanıcı, 1: Admin kullanıcısı 
                int authority = user_list.authority(user_id, user_list);

                //bu switch case ise kullanıcının yetkisine bakarak gerkli sayfaya yönlendiriyor.
                switch (authority) {
                    case 0: {
                        //program buraya girmiş ise kullanıcı normal bir yetkiye sahiptir ve normal ekranı(app_standart_user ekranını) açıyoruz. ve metodu sonlandırıyoruz.
                        Parent root = FXMLLoader.load(getClass().getResource("/FXML/app_standart_user.fxml"));
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

                        break;
                    }
                    case 1: {
                        //program buraya girmiş ise kullanıcının admin kullanıcı olduğunu öğreniyoruz ve admin sayfasına yönlendiriyoruz(app sayfası)
                        Parent root = FXMLLoader.load(getClass().getResource("/FXML/app.fxml"));
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

                        break;
                    }
                    default:
                        //program buraya girmiş ise muhtemelen dosya okuma ya da dosya yazma hatası meydana gelmiştir. Kullanıcıya bir uyarı giriyoruz ve programı sonlandırıyoruz.
                        label.setText("Bir hata meydana geldi");
                        break;
                }

            }
        }

    }

    //Kullanıcı kayıtlı değil ve kayıt olmak istiyorsa bu yazıya tıklıyor. Bu yazı da kullanıcıyı kayıt olma sekmesine yolluyor.
    @FXML
    private void sign_up(MouseEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/FXML/signup.fxml"));
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

    //kullanıcı şifresini unutmuş ise altta bulunan 'Yardıma ihtiyacım var' yazısına tıklıyor ve oradan şifre öğrenmek için gerekli olan sayfaya yönlendiriliyor.
    @FXML
    private void help(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/help.fxml"));
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

    //görünüş açısından gizlenen ve manuel olarak elle eklenen kapatma tuşunun methodu
    @FXML
    private void close(MouseEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    //görünüş açısından gizlenen ve manuel olarak elle eklenen tam ekran moda geçme tuşunun methodu
    @FXML
    private void max(MouseEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        //yukarıda tanımladığımız değişkene göre tam ekran moda alıyoruz ya da normal boyuta alıyoruz.
        if (a == false) {
            //a false ise daha küçük demektir ve büyültüyoruz.
            stage.setFullScreenExitHint("Tam moda geçildi çıkmak için 'esc' tuşuna basınız");
            stage.setFullScreen(true);
            a = true;
            //a'yı true yapyıyoruz. Çünkü program artık tam ekran modunda.
        } else {
            //eğer a true ise program tak eran modundadır o zaman da normal noyuta geçiyoruz.
            stage.setFullScreen(false);
            // a'yı false yapıyoruz çünkü artık tam ekranda değil nomral boyutta
            a = false;
        }
    }

    //görünüş açısından gizlenen ve manuel olarak elle eklenen aşağı alma tuşunun methodu
    @FXML
    private void min(MouseEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setIconified(true);
    }
    
    //bu method şifreyi gösterme işine yarıyor. Kullanıcı şifreyi göster simgesine tıklarsa passwordfield'ın buludnuğu hbox'ı kapatıp textfield'ın bulunduğu hbox'ı açıyor ve passwordfiled'Dan veriyi alıp kendine yazıyor.
    @FXML
    private void goster(MouseEvent event) {
        acik.setVisible(true);
        sifre_gosterim = true;
        gizli.setVisible(false);
        tf_password.setText(pf_password.getText());
    }

    //bu method şifreyi gizleme işine yarıyor. Kullanıcı şifreyi gizle simgesine tıklarsa textfield'ın buludnuğu hbox'ı kapatıp passwordfield'ın bulunduğu hbox'ı açıyor ve textfiled'dan veriyi alıp kendine yazıyor.
    @FXML
    private void gizle(MouseEvent event) {
        gizli.setVisible(true);
        sifre_gosterim = false;
        acik.setVisible(false);
        pf_password.setText(tf_password.getText());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
