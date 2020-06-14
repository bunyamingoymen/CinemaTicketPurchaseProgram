
package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import code.*;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;


/*

Kullanıcı şifresini unuttuysa girdiği sayfa

 */
public class helpController implements Initializable {
    
    // program tam ekranda mı yoksa normal boyutta mı test etmek için bu değişkeni tanımlıyoruz. Tam ekranda ise true, değilse false oluyor.
    private boolean a = false;

    //kullanıcının temel bilgilerini almak için textField'ler oluşturuyoruz
    @FXML
    private TextField tf_username;

    @FXML
    private TextField tf_usermail;

    @FXML
    private Label label;

    @FXML
    private Text sifre;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    //bu method kullanıcının bilgileri doğru ise şifresini veriyor.
    @FXML
    private void Sifre(ActionEvent event) {
        //ilk başta kullanıcıdan aldığımız verileri string'e aktarıyoruz.
        String user_name = tf_username.getText();
        String user_mail = tf_usermail.getText();

        //kullanıcının verilerini kontrol etmeden önce gerekli olan yerlerin dolu olup olmadığını kontrol ediliyorz
        if ((user_name.length() == 0) || (user_mail.length() == 0)) {
            //gerekli olan yerler boş ise kullanıcıya uyarı çıkıyor ve diğer işlemlere devam etmiyoruz.
            label.setText("Lütfen Gerekli Yerleri Doldurunuz.");
        } else {
            //gerekli olan yerler dolu ise kullanıcının verilerini kontrol etmeye başlıyoruz.

            //kullanıcının verilerini bulmak için dosya okuma işlemi ile bütün kullanıcıların verilerini okuyoruz.
            User user_list = new User();
            Dosya dosya_islemleri = new Dosya();
            dosya_islemleri.user_dosya_oku(user_list);

            //bütün kullanıcıların verilerini okuduktan sonra code paketinin içinde bulunan User sınıfındaki password sekmesinin kullanıcının bilgisi doğru ise ekrana yazdırıyoruz.
            String password = user_list.password(user_name, user_mail);

            if (password != null) {
                //eğer password null dönmemiş ise o zaman kullanıcının verileri doğrudur anlamına geliyor.
                sifre.setText("Şifreniz: " + password);
                label.setText("");
            } else {
                // password null ise kullanının bilgileri yanlıştır demektir. O zamanda ekrana uyarı mesajını yazıyoruz.
                label.setText("Kullanıcı adı veya mail adresi hatalı");
            }
        }
    }

    //aşağıda bulunan giriş yap yazısına tıklandığında login.fxml sayfasını açmaya yarayan method
    @FXML
    private void login(MouseEvent event) throws IOException {

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

    //görünüş açısından gizlenen ve manuel olarak elle eklenen kapatma tuşunun methodu
    @FXML
    void close(MouseEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    //görünüş açısından gizlenen ve manuel olarak elle eklenen tam ekran moda geçme tuşunun methodu
    @FXML
    void max(MouseEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        
        //yukarıda tanımladığımız değişkene göre tam ekran moda alıyoruz ya da normal boyuta alıyoruz.
        if (a == false) {
            //a false ise daha küçük demektir ve büyültüyoruz.
            stage.setFullScreenExitHint("Tam moda geçildi çıkmak için 'esc' tuşuna basınız");
            stage.setFullScreen(true);
            //a'yı true yapyıyoruz. Çünkü program artık tam ekran modunda.
            a = true;
            
        } else {
            //eğer a true ise program tak eran modundadır o zaman da normal noyuta geçiyoruz.
            stage.setFullScreen(false);
            // a'yı false yapıyoruz çünkü artık tam ekranda değil nomral boyutta
            a = false;
            
        }
    }

    //görünüş açısından gizlenen ve manuel olarak elle eklenen aşağı alma tuşunun methodu
    @FXML
    void min(MouseEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setIconified(true);
    }

}
