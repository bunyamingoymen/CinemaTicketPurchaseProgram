package Controller;

import code.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.*;

public class signupController implements Initializable {
    
    // program tam ekranda mı yoksa normal boyutta mı test etmek için bu değişkeni tanımlıyoruz. Tam ekranda ise true, değilse false oluyor.
    private boolean a = false;
    
    //kullanıcının istediği verileri yazması için alan oluşturuyoruz. ve bir hata çıkarsa hata mesajı için label oluşturuyoruz.
    @FXML
    private TextField tf_username;

    @FXML
    private TextField tf_email;

    @FXML
    private PasswordField pf_password;

    @FXML
    private Label t_text;

    //burası login ekranına tekrar dönmek için yazıldı. Kullanıcı kayıt ol sekmesine yanlışlıkla tıkladı veya hesabının olduğunu hatırladığı zamanlarda geri dönüp giriş yapmak için yazılmış method.
    @FXML
    void login2(MouseEvent event) throws IOException {
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

    //burası kullanıcın verilerini kontrol ediip dosyaya kaydettiğimiz methot.
    @FXML
    void signup(MouseEvent event) throws IOException {
        //ilk başta kullanıcının girdiği bilgileri alıp string içine aktarıyoruz. 
        String user_name = tf_username.getText();
        String email = tf_email.getText();
        String password = pf_password.getText();
        //bu if ile de stringlerin yani kullanıcının girdiği bilgilerin boş olup olmadığına bakyırouz. Yani kullanıcı gerekli yerleri boş bırakmış mı ? diye konttrol yapıyoruz.
        if ((user_name.length() == 0) || (email.length() == 0) || (password.length() == 0)) {
            //program bu if'e girmiş ise kullanıcı gerekli yerleri boş bırakmış demek oluyor. o zaman da bir uyarı mesajı verip bütün işlemlerimizi sonlanrıyırouz.
            t_text.setText("Lütfen Gerekli Yerleri Doldurunuz!");
        } else {
            //bu else ye girmiş ise kullanıcı gerekli yerleri doldurmuş demektir. o zaman işlemlerimize devam ediyoruz.
            
            //burada bütün kullanıcıların bilgilerini dosya okuma yolu ile bir bağlı listeye kaydediyoruz.
            Dosya dosya_islemleri = new Dosya();
            User user_list = new User();
            dosya_islemleri.user_dosya_oku(user_list);

            /*
            burada hem kullanıcı adının hem de e-postanın daha önce kayıt olunup olunmadığını kontrol ediyoruz.
            Eğer e-mail ya da user_name daha önce alınmış ise diğer işlemlerimizi yapmayıp kullanıcıya uyarı veriyoruz.
            */
            boolean control_email = user_list.search_mail(email);
            boolean control_username = user_list.search_name(user_name);
            
            if (control_email == false) {
                //eğer bu if'e girmiş ise yazılan e-mail daha önceden kayıt edildiği anlamıan geliyor bu sebeple de kullanıcıya hata mesajını yazıp geri kalan işlemleri uygulamıyoruz.
                t_text.setText("Bu email daha önce kayıt edilmiş.");
            } else if (control_username == false) {
                // eğer bu else if'e girmiş ise kullanıcının yazdığı username daha önceden alınmıştır anlamına geliyor bu sebeple kullanıcıya hatayı söyleyip programı sonlandırıyoruz.
                t_text.setText("Bu kullanıcı adı daha önce kayıt edilmiş.");
            } else {
                //eğer bu else'ye girmiş ise herhangi bir sorun olmadığı anlamına geliyor ve kullanıcının girdiği verileri dosyaya ekliyoruz. böylece kullanıcı kayıt olmuş luyor.
                dosya_islemleri.user_dosya_ekle(user_list.sayac() + 1, 0, email, user_name, password);
                //kullanıcının bilgilerini dosyaya kayıt ettikten sonra kullanıcıyı otomatik olarak giriş yapma ekranına yönlendiriyoruz.
                login2(event);
            }
        }
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
            a = true;
            //a'yı true yapyıyoruz. Çünkü program artık tam ekran modunda.
        } else {
            //eğer a true ise program tak eran modundadır o zaman da normal noyuta geçiyoruz.
            stage.setFullScreen(false);
            a = false;
            // a'yı false yapıyoruz çünkü artık tam ekranda değil nomral boyutta
        }
    }

    //görünüş açısından gizlenen ve manuel olarak elle eklenen aşağı alma tuşunun methodu
    @FXML
    void min(MouseEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
