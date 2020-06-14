package code;

import java.io.*;
/*
Bu sınıfın asıl amacı dosyada ki verileri okumak ve dosyaya yeni veri yazmak.
*/

public class Dosya {

    public Dosya() {

    }

    //user dosyasına veri eklemek içim kullandığımız method
    public User user_dosya_ekle(int id, int authority, String mail, String user_name, String password) {
        // user dosyasıne herhangi bir şey eklemeden önce ilk yaptığımız işe user sınıfına ait bir bağlı liste tanımlamak
        User user_list = new User();
        //değişkeni tanımladıktan sonra dosyadaki bütün verileri az önce oluşturduğumuz bağlı listeye teker teker ekliyoruz. Bunun sebebi daha önce eklenmiş olan verilerin korunmasını sağlamak.
        user_dosya_oku(user_list);
        //bütün verileri bağlı listeye ekledikten sonra kullanıcı tarafından yollanmış verileri de bağlı listeye ekliyoruz.
        user_list.add(id, authority, mail, user_name, password);
        //kullanıcının verilerinin de eklendiği yeni bağlı listeyi dosyaya yeniden yazıyoruz. Ve ekleme işlemimiz tamamlanıyor.
        user_dosya_yaz(user_list);
        return user_list;
    }

    //user dosyasından veri okumak için yazılan method
    public User user_dosya_oku(User user_list) {
        //ilk başta dosyadan aldığımız verileri tutmak için iki string tanımlıyoruz ve dosyanın içindeki verileri bağlı listeye aktarabilmek adına gerekli olan değişkenlerde tutmak için değişken tanımlıyoruz.
        String str = "";
        String data = "";
        int id;
        String Name;
        String Password;
        int authority;
        String mail;
        //try-catch bloğu ile dosyadan okuma işlemine başlıyoruz.
        try {
            //ilk başta dosyamızın konumunu belirtiyoruz.
            FileInputStream fStream = new FileInputStream("lib/txt/user.txt");
            try (DataInputStream dStream = new DataInputStream(fStream)) {
                BufferedReader bReader = new BufferedReader(new InputStreamReader(dStream));
                //bu while döngüsü aracılığıyla dosyadan aldığımız her veriyi teker teker analiz edip gerekli yerlere iletiyoruz. bu döngü her döndüğünde str bir alt satıra gidiyor.
                while ((str = bReader.readLine()) != null) {

                    data = str;
                    //daha sonra verilerin karışmaması açısından kodlanarak yazılan verileri teker teker açıyoruz. kodlamamız '1234####4321' her veriyi bunların içine kaydediyoruz.
                    String[] a = data.split("1234####4321");
                    id = Integer.valueOf(a[0]);
                    authority = Integer.valueOf(a[1]);
                    mail = a[2];
                    Name = a[3];
                    Password = a[4];
                    //bütün değerleri aldıktan sonra bağlı listemize ekliyoruz.
                    user_list.add(id, authority, mail, Name, Password);
                    //bu döngü her satır sonunda bir sonraki satıra geçip devam ediyor ve en son satırı okuyana kadar durmuyor. Böylece dosyadaki bütün verileri okumuş oluyoruz.
                }
            }
        } catch (IOException | NumberFormatException e) {
            //burası da eğer dosya bulunamaz yada başka bir sorun çıkar ise try bloğuna girmeden hata verecek yer.
            System.out.println("Hata:(user_dosya_oku) " + e.getMessage());
        }
        return user_list;
    }

    //bu methodumuz user dosyasına yazmak için
    public void user_dosya_yaz(User user_list) {
        try {
            //ilk başta dosyanın yerini ve adını tanımlıyoruz.
            FileWriter fWriter = new FileWriter("lib/txt/user.txt");
            try (BufferedWriter writer = new BufferedWriter(fWriter)) {
                //parametre olarak gelen bağlı listenin ilk elemanını yani root'unu bir değişkende tutarak bir döngü yapmayı amaçlıyoruz. amacımız bütün bağlı listeyi okumak ve ardından da bunu dosyaya yazmak.
                User_List gecici = user_list.root;
                //az önce oluşturduğumuz geçici değişkeni null olana kadar döndürüp dosyaya yazıyoruz.
                while (gecici != null) {
                    //dosyaya yazma komutunu çalıştırıp kodlanmış şekilde dosyaya yazıyoruz. kodlamamız '1234####4321' dır. Her veriyi bu kodlamanın içine yazıyoruz. Böylece kullanıcının girdiği veriler okunurken karmaşıklık çıkarmıyor.
                    writer.write(gecici.getUser_id() + "1234####4321" + gecici.getAuthority() + "1234####4321" + gecici.getMail() + "1234####4321"
                            + gecici.getUser_name() + "1234####4321" + gecici.getPassword());
                    //bu aşağıdaki kod ile bir dosyada bir sonraki satıra geçiyoruz. ve onun aşağısındaki kodla da geçiciyi her seferinde bir kez ilerletiyoruz. Böylece her veriyi teker teker farklı satırlara aktarıyoruz.
                    writer.newLine();
                    gecici = gecici.getNext();
                }
            }
        } catch (IOException e) {
            System.out.println("Hata:(user_dosya_yaz) " + e.getMessage());
        }
    }

    //bilet_Satin_al dosyasına veri eklemek içim kullandığımız method
    public void bilet_satin_al_dosya_ekle(int user_id, int salon_id, int seans_id, yesil_olan yesil, int sayac) {
        //ilk başta parametre olarak gelen sayaç değeri ile döngü oluşturuyoruz ve gelen yesil bağlı listesindeki bütün koltukları ekliyoruz.
        for (int i = 1; i <= sayac; i++) {
            // bilet_satin_al dosyasıne herhangi bir şey eklemeden önce ilk yaptığımız şey bilet_Satin_al sınıfına ait bir bağlı liste tanımlamak
            Bilet_Satin_Al bilet = new Bilet_Satin_Al();
            //değişkeni tanımladıktan sonra dosyadaki bütün verileri az önce oluşturduğumuz bağlı listeye teker teker ekliyoruz. Bunun sebebi daha önce eklenmiş olan verilerin korunmasını sağlamak.
            bilet_satin_al_dosya_oku(bilet);
            //bütün verileri bağlı listeye ekledikten sonra kullanıcı tarafından yollanmış verileri de bağlı listeye ekliyoruz.
            bilet.add(bilet.sayac() + 1, user_id, salon_id, seans_id, yesil.koltuk(i));
            //kullanıcının verilerinin de eklendiği yeni bağlı listeyi dosyaya yeniden yazıyoruz. Ve ekleme işlemimiz tamamlanıyor.
            bilet_Satin_al_dosya_yaz(bilet);
        }

        //Not: bu metot diğer ekle metotları gibi return etmesine gerek yok. Bunun sebebi ise bunun kullanıldığı yerde bu eklenen değerler bir daha ihtiyaç olmuyor. Diğer eklemelerde ise ekrana yazdırma ya da buna benzer olaylar oluyor.
    }

    //bu metot bilet_Satin_al dosyasından verileri silmek için.
    public int bilet_Satin_al_dosya_sil(int satin_alinan_bilet_id) {
        //ilk olarak bilet_Satin_al bağlı listesi oluşturuyoruz oluşturuyoruz.
        Bilet_Satin_Al bilet = new Bilet_Satin_Al();
        //daha sonra verileri dosyadan okumak için gerekli metoda yolluyoruz ve bütün değerleri bağlı listeye aktarıyoruz
        bilet_satin_al_dosya_oku(bilet);
        //daha sonra bu bağlı listenin kaç tane değein olduğunu hesaplıyoruz.
        int sayac = bilet.sayac();
        //ve gönderilen id'nin bağlı listenin boyutundan küçük eşit olup olmadığını kotnrol ediyoruz. Ve küçükse işlemlerimizi yapıyoruz. Büyükse de hata verip prgoramı sonlandırıyoruz.
        if (sayac >= satin_alinan_bilet_id) {
            //eğer veri uyuşmazlığı yok ise bağlı listenin silme foknsiyonuna yolluyoruz ve istenilen değeri siliyoruz. Ardından da yeni oluşan bağlı listeyi dosyaya yazdırıyoruz.
            bilet.remove(satin_alinan_bilet_id);
            bilet_Satin_al_dosya_yaz(bilet);
            return 1;
        } else {
            //bir hata oluştuysa buraya geliyor ve ardındanda hata mesajı verip metodu sonlandırıyor.
            System.out.println("Bir hata meydana geldi (bilet_satin_al_dosya_sil)");
            return 0;
        }

    }

    //bilet_Satin_al dosyasından verileri okumak için yazılan metot
    public Bilet_Satin_Al bilet_satin_al_dosya_oku(Bilet_Satin_Al bilet) {
        //ilk başta dosyadan aldığımız verileri tutmak için iki string tanımlıyoruz ve dosyanın içindeki verileri bağlı listeye aktarabilmek adına gerekli olan değişkenlerde tutmak için değişken tanımlıyoruz.
        String str = "";
        String data = "";
        int bilet_satin_al_id;
        int user_id;
        int salon_id;
        int seans_id;
        String koltuk;
        //try-catch bloğu ile dosyadan okuma işlemine başlıyoruz.
        try {
            FileInputStream fStream = new FileInputStream("lib/txt/bilet_satin_al.txt");
            try (DataInputStream dStream = new DataInputStream(fStream)) {
                BufferedReader bReader = new BufferedReader(new InputStreamReader(dStream));
                //bu while döngüsü aracılığıyla dosyadan aldığımız her veriyi teker teker analiz edip gerekli yerlere iletiyoruz. bu döngü her döndüğünde str bir alt satıra gidiyor.
                while ((str = bReader.readLine()) != null) {
                    /*
                    daha sonra verilerin karışmaması açısından kodlanarak yazılan verileri teker teker açıyoruz. kodlamamız '/' her veriyi bunların içine kaydediyoruz. 
                    Burada diğer dosya yazma-okuma işlemlerinde olan kodlama sistemi yok. bunun sebebi ise buraya gelecek verilerin ne yazılacağının belli olması.
                    Kullanıcı kendi kullanıcı adına / koyabilir yada admin haberler,kampnaylar..vs eklerken / yada  # tarzı bir şey koyabilir. Bu sebeple diğer dosya yazma işlemlerimiz karışık küçük bir kod var.
                    Ancak bilet al işleminde buna görek yok. Çünkü buraya yazılacak şeyler belli ve /, # gibi şeylerin gelmeyeceği biliniyor (Koltuk nolar, kullanıcı id'ler, salon id'ler'e böyle şeyler yazılmıyor.)
                     */

                    data = str;

                    String[] a = data.split("/");
                    bilet_satin_al_id = Integer.valueOf(a[0]);
                    user_id = Integer.valueOf(a[1]);
                    salon_id = Integer.valueOf(a[2]);
                    seans_id = Integer.valueOf(a[3]);
                    koltuk = a[4];
                    //bütün değerleri aldıktan sonra bağlı listemize ekliyoruz.
                    bilet.add(bilet_satin_al_id, user_id, salon_id, seans_id, koltuk);
                    //bu döngü her satır sonunda bir sonraki satıra geçip devam ediyor ve en son satırı okuyana kadar durmuyor. Böylece dosyadaki bütün verileri okumuş oluyoruz.

                }
            }
        } catch (IOException e) {
            //burası da eğer dosya bulunamaz yada başka bir sorun çıkar ise try bloğuna girmeden hata verecek yer.
            System.out.println("Hata: (bilet_Satin_al_dosya_oku)" + e.getMessage());
        }

        return bilet;

    }

    //bu methodumuz bilet_satin_al dosyasına yazmak için
    public void bilet_Satin_al_dosya_yaz(Bilet_Satin_Al bilet) {
        try {
            //ilk başta dosyanın yerini ve adını tanımlıyoruz.
            FileWriter fWriter = new FileWriter("lib/txt/bilet_satin_al.txt");
            try (BufferedWriter writer = new BufferedWriter(fWriter)) {
                //parametre olarak gelen bağlı listenin ilk elemanını yani root'unu bir değişkende tutarak bir döngü yapmayı amaçlıyoruz. amacımız bütün bağlı listeyi okumak ve ardından da bunu dosyaya yazmak.
                List_Bilet_Satin_Al gecici = bilet.root;
                while (gecici != null) {
                    //dosyaya yazma komutunu çalıştırıp kodlanmış şekilde dosyaya yazıyoruz. kodlamamız '1234####4321' dır. Her veriyi bu kodlamanın içine yazıyoruz. Böylece kullanıcının girdiği veriler okunurken karmaşıklık çıkarmıyor.
                    writer.write(gecici.getSatin_alinan_bilet_id() + "/" + gecici.getUser_id() + "/" + gecici.getSalon_id() + "/" + gecici.getSeans_id() + "/" + gecici.getKoltuk());
                    //bu aşağıdaki kod ile bir dosyada bir sonraki satıra geçiyoruz. ve onun aşağısındaki kodla da geçiciyi her seferinde bir kez ilerletiyoruz. Böylece her veriyi teker teker farklı satırlara aktarıyoruz.
                    writer.newLine();
                    gecici = gecici.getNext();
                }
            }
        } catch (IOException e) {
            System.out.println("Hata:(bilet_Satin_al_dosya_yaz) " + e.getMessage());
        }
    }

    //bu metot ile giriş yapan kullanıcının id'sini gerekli olan dosyaya yazmaktadir.
    public void bilgi_dosya_yaz(int id) {
        //yapmamız gereken işlemleri try-catch içine alıyoruz.
        try {
            //ilk olarak dosyanın yerine belirtiyoruz.
            FileWriter fWriter = new FileWriter("lib/txt/bilgi.txt");
            try (BufferedWriter writer = new BufferedWriter(fWriter)) {
                //daha sonra user_id'yi bir string değere atıp bunu dosyaya yazıyoruz.
                String user_id = String.valueOf(id);
                writer.write(user_id);
            }
        } catch (IOException e) {
            //eğerprogram bu alan girmiş ise bir sıkıntı vardır demektir bu sebeple işlemerimizi yapmıyoruz.
            System.out.println("Hata:(bilgi_dosya_yaz " + e.getMessage());
        }
    }

    //bu metot gerekli dosyadan giriş yapan kullanıcının id'sini döndürmektedir.
    public int bilgi_dosya_oku() {
        //ilk olarak gerekli değişkenler tanımlanıyor.
        int id = 0;
        String str = "";
        //yapacağımız işlemi try-catch bloğuna alıyoruz.
        try {
            //dosyanın yerini tanımlıyoruz.
            FileInputStream fStream = new FileInputStream("lib/txt/bilgi.txt");
            try (DataInputStream dStream = new DataInputStream(fStream)) {
                BufferedReader bReader = new BufferedReader(new InputStreamReader(dStream));
                //daha sonra dosaydaki değeri okuyup integer değere dönüştürüyoruz. ve bunu bir integer değere atıyoruz.
                str = bReader.readLine();
                id = Integer.valueOf(str);
            }
        } catch (IOException | NumberFormatException e) {
            //program buraya düşmüş ise bir hata vardır demektir ve hatayı yazdırıyruzç
            System.out.println("Hata:(bilgi_dosya_oku) " + e.getMessage());
        }
        //en sonda da bu değeri döndürüyoruz.
        return id;
    }

    //bu metot satın alma işlemindeyken herhangi bir koltuğa basılırsa bunu yesil_olan.txt dosyasına yazdırıyor.
    public void yesil_olan_dosya_ekle(String koltuk) {
        //ilk olarak dosyadaki daha önce tıklanan butonların text'lerini bağlı listeye aktarıyoruz.
        yesil_olan yesil = new yesil_olan();
        yesil_olan_dosya_oku(yesil);

        //daha sonra eklenmek istenen butonun text'ini de bağlı listeye ekliyoruz
        yesil.add(yesil.sayac() + 1, koltuk);

        //oluşan yeni bağlı listeyi de dosyaya yeniden yazıyoruz.
        yesil_olan_dosya_yaz(yesil);
    }

    //kullanıcı satın alma işleminde almak istediği koltuu iptal ederse bizde dosaydan iptal etmek istediği koltuğu siliyoruz.
    public void yesil_olan_dosya_sil(String koltuk) {
        //ilk olarak bütün değerleri dosaydan okuyoruz ve bağlı listeye aktarıyoruz.
        yesil_olan yesil = new yesil_olan();
        yesil_olan_dosya_oku(yesil);

        //daha sonra silinmesini istediğimiz değeri bağlı listeden de silip. Yeni oluşan bağlı listeyi dosyaya yazıyoruz.
        yesil.remove(koltuk);
        yesil_olan_dosya_yaz(yesil);
    }

    //kullanıcı bilet satın alma işleminden sonra dosyadaki bütün veriler bilet_Satin_al dosyasına eklendikten sonra daha sonraki satın alımlar için bütün dosyayı temizliyorz. Bu metot bu işe yarıyor.
    public void yesil_olan_dosya_hepsini_sil() {
        //ilk başta bütün yesil_olan.txt dosysaındaki verileri okuyup bir bağlı listeye aktarıyoruz.
        yesil_olan yesil = new yesil_olan();
        yesil_olan_dosya_oku(yesil);

        //daha sonra bütün değerleri bağlı listeden silip bunu dosyaya yazıyoruz.
        yesil.remove();
        yesil_olan_dosya_yaz(yesil);
    }

    //Bu metot yesil_olan.txt dosyasındaki bütün verileri okuyor ve bunu bir bağlı listeye aktarıp bu bağlı listeyi dönüyor.
    public yesil_olan yesil_olan_dosya_oku(yesil_olan yesil) {
        //ilk olarak gerekli değişkenleri tanımlıyoruz.
        String str = "";
        String data = "";
        int id;
        String koltuk;

        //daha sonra yapacağımız işlemler için try-catch bloğu içine alıyouz.
        try {
            //dosyamızın adını ve bulunduğu konumu tanımlıyoruz.
            FileInputStream fStream = new FileInputStream("lib/txt/yesil_olan.txt");
            try (DataInputStream dStream = new DataInputStream(fStream)) {
                BufferedReader bReader = new BufferedReader(new InputStreamReader(dStream));

                //daha sonra dosyanın son satırna gelene kadar bir döngü oluşturup her değeri parametre olarak gelen bağlı listeye aktarıyoruz.
                while ((str = bReader.readLine()) != null) {
                    //ilk olarak şuanki satırın verilerini bir stirng'e atıyoruz.
                    data = str;

                    //bu string'teki değerleri parçalayarak bir diziye aktarıyoruz.
                    String[] a = data.split("/");

                    //parçalanan değerleri gerekli olan değişkenlere atıyoruz.
                    id = Integer.valueOf(a[0]);
                    koltuk = a[1];

                    //daha sonrada bu değerleri bağlı listenin ekleme metoduna yolluyoruz.
                    yesil.add(id, koltuk);

                }
            }
        } catch (IOException | NumberFormatException e) {
            //program burya girmiş ise bir sorun vardır demek ve hatayı yazdırıyoruz.
            System.out.println("Hata:(yesil_olan_dosya_oku) " + e.getMessage());
        }
        //en sonda da paramtetre olarak gelen bağlı listeyi dönüyoruz.
        return yesil;
    }

    //yesil_olan değerleri dosyaya yazmak için kullanılan metot.
    public void yesil_olan_dosya_yaz(yesil_olan yesil) {
        //ilk olarak işlemlerimizi try-catch bloğu içerisine alıyoruz.
        try {
            //dosyamızın adresini tanımlıyoruz.
            FileWriter fWriter = new FileWriter("lib/txt/yesil_olan.txt");
            try (BufferedWriter writer = new BufferedWriter(fWriter)) {
                //bağlı listenin içinde gezebilmek için geçici değişken tanımlanıyor. 
                List_yesil_olan gecici = yesil.root;
                //bu göndü ile bağlı listedeki bütün değerleri teker teker geziyoruz ve o sıra işlemlerimizi yapıyoruz.
                while (gecici != null) {
                    //burada gerekli sıra ve işlem ile dosyaya yazıyoruz.
                    writer.write(gecici.getId() + "/" + gecici.getKoltuk());
                    writer.newLine();
                    gecici = gecici.getNext();
                }
            }
        } catch (IOException e) {
            //program buraya düşmüş ise bir hata vardır demektir. BU hatayı ekrana yazdırıp metodu sonlanrıyoruz.
            System.out.println("Hata: (yesil_olan_dosya_yaz) " + e.getMessage());
        }
    }

    //Bu metot hem kampanyalar hem de haberler dosyasına herhangi bir veri eklemek amacıyla yazıldı.
    public Duyurular duyurular_dosya_ekle(String Date, String Title, String Duyuru, int islem) {
        //ilk olarak Duyuru sınıfına ait bir referans tanımlıyoruz. Daha sonra bunu islem değeri 0 ise haberler'e, 1 ise kampanyalara yönlendiricez.
        Duyurular duyuru;
        switch (islem) {
            case 0:
                // islem olarak 0 gelmiş ise haberlere eklememiz gerektiği anlamına geliyor. ve kalıtım aracılığıyla da az önce tanımladığımız referasnı haberlere bağlıyor.
                duyuru = new Haberler();
                Haberler haber = (Haberler) duyuru;
                //ilk başta dosya okuma işlemi gerçekleştiriyoruz. Yani dosyadaki bütün verileri okuyoruz. ve ardından bağlı listeye ekleyiyoruz
                duyurular_dosya_oku(haber, islem);
                //daha sonra ise eklenmek istediğimiz değerleri bağlı listeye ekliyoruz.
                haber.add(haber.sayac() + 1, Date, Title, Duyuru);
                //daha sonra yeni oluşan bağlı listeyi de dosyaya yazma işlemine yolluyoruz ve işlem tamamlanıyor.
                duyurular_dosya_yaz(haber, islem);
                return haber;
            case 1:
                // islem olarak 1 gelmiş ise kampanyalara eklememiz gerektiği anlamına geliyor. ve kalıtım aracılığıyla da az önce tanımladığımız referasnı kampanyalara bağlıyor.
                duyuru = new Kampanyalar();
                Kampanyalar kampanya = (Kampanyalar) duyuru;
                //ilk başta dosya okuma işlemi gerçekleştiriyoruz. Yani dosyadaki bütün verileri okuyoruz. ve ardından bağlı listeye ekliyoruz.
                duyurular_dosya_oku(kampanya, islem);
                //daha sonra ise eklenmek istediğimiz değerleri bağlı listeye ekliyoruz.
                kampanya.add(kampanya.sayac() + 1, Date, Title, Duyuru);
                //daha sonra yeni oluşan bağlı listeyi de dosyaya yazma işlemine yolluyoruz. ve işlemimizi tamamlıyoruz.
                duyurular_dosya_yaz(kampanya, islem);
                return kampanya;
            default:
                //eğer program buraya girmiş ise buraya yollanan islem değişkenin de bir sorun vardır anlamıne geliyor ve uyarı verip metodu sonlandırıyoruz.
                System.out.println("Hata: Yanlış Değer Döndü (duyurular_dosya_ekle)");
                return null;
        }

    }

    //Bu metot hem kampanyalar hem de haberler dosyasındaki bir veriyi silmek için yazıldı
    public int duyurular_dosya_sil(int id, int islem) {
        //parametre olarak gelen islem degeri 0 ise haberler, 1 ise kampanyalar için gerekli işlemi yapmaktadır. Yani kalıtım aracılığıyla bu işleöi yapıyoruz.
        //ilk olarak kalıtım'daki üst kademe olan Duyurular sınıfı ile bir referans oluşturuldu. Daha sonraki gelecek değere göre nesnesi oluşturulacak.
        Duyurular duyuru;
        switch (islem) {
            case 0: {
                //buraya girmiş ise haber'deki değer silinecek
                //ilk olarak yukarıda tanımladığımız referansın nesnesini oluşturup haberler öğesine aktarıyoruz ve dosyadaki verileri çekiyoruz.
                duyuru = new Haberler();
                Haberler haber = (Haberler) duyuru;
                haber = (Haberler) duyurular_dosya_oku(haber, islem);
                //daha sonra ise kaç tane haber olduğunu bir integer değerde tutuyoruz.
                int sayac = haber.sayac();
                //eğer id sayactan büyük ise hatalı değer gelmiş demek oluyor. Çünkü sayac haberlerin toplam boyutu ve id'nin geldiği kadar haber yok demektir. Bu yüzden ilk bunu kontrol ediyoruz.
                if (sayac >= id) {
                    //bu alan girmiş ise ilk olarak bağlı listede istenilen değer siliniyor ve yeni bağlı liste dosyaya yazdırılıyor.
                    haber.remove(id);
                    duyurular_dosya_yaz(haber, islem);
                    return 1;
                } else {
                    //bu alana girmiş ise 0 yani başarısız olarak döndürüyoruz. Çünkü buraya hatalı bir id değeri geldi demek oluyor.
                    return 0;
                }
            }
            case 1: {
                //buraya girmiş ise kampanya'daki değer silinecek
                //ilk olarak yukarıda tanımladığımız referansın nesnesini oluşturup kampanyalar öğesine aktarıyoruz ve dosyadaki verileri çekiyoruz.
                duyuru = new Kampanyalar();
                Kampanyalar kampanya = (Kampanyalar) duyuru;
                kampanya = (Kampanyalar) duyurular_dosya_oku(kampanya, islem);
                //daha sonra ise kaç tane kampanya olduğunu bir integer değerde tutuyoruz.
                int sayac = kampanya.sayac();
                //eğer id sayactan büyük ise hatalı değer gelmiş demek oluyor. Çünkü sayac kampanyaların toplam boyutu ve id'nin geldiği kadar kampanya yok demektir. Bu yüzden ilk bunu kontrol ediyoruz.
                if (sayac >= id) {
                    //bu alan girmiş ise ilk olarak bağlı listede istenilen değer siliniyor ve yeni bağlı liste dosyaya yazdırılıyor.
                    kampanya.remove(id);
                    duyurular_dosya_yaz(kampanya, islem);
                    return 1;
                } else {
                    //bu alana girmiş ise 0 yani başarısız olarak döndürüyoruz. Çünkü buraya hatalı bir id değeri geldi demek oluyor.
                    return 0;
                }
            }
            default:
                //buraya girmiş ise islem değeri hatalıdır demektir ve konsola bu hatayı yazıyoruz.
                System.out.println("Hata: Hatalı değer döndü(duyurular_dosya_sil)");
                return -1;
        }
    }

    //Bu metot hem kampanyalar hem de haberler için dosyadaki verileri okumak için yazıldı.
    public Duyurular duyurular_dosya_oku(Duyurular duyuru, int islem) {
        //parametre oalrak gelen islem; 0 ise haberleri, 1 ise de kampanyaları okuyacaktır.
        //ilk olarak gerekli değişkenleri tanımlıyoruz.
        String str = "";
        String data = "";
        int id;
        String Date;
        String Title;
        String Duyuru;

        //daha sonra islem'e göre haberleri mi kampanyaları mı okuyacağımızı belirliyoruz.
        switch (islem) {
            case 0:
                //bu alana girmiş ise haberler okunacak
                //ilk olarak parametre olarak gelen değeri kalıtım aracılığıyla bşka bir bağlı listeye aktarıyoruz.
                Haberler haber = (Haberler) duyuru;

                //yapacağımız işlemleri try-catch bloğu içerisine alıyoruz.
                try {
                    //ilk olarak dosyamızın adresini tanımlıyoruz.
                    FileInputStream fStream = new FileInputStream("lib/txt/haberler.txt");
                    try (DataInputStream dStream = new DataInputStream(fStream)) {
                        BufferedReader bReader = new BufferedReader(new InputStreamReader(dStream));
                        //daha sonra yukarıda tanımladığımız değer aracılığıyla dosyada kaç tane satır varsa o kadarlık bir döngü oluşturuluyor.
                        while ((str = bReader.readLine()) != null) {
                            //bu döngüde dosyadaki veriler ayıklanarak gerekli değişkenlere gerekli değerleri atıyor.
                            data = str;
                            String[] a = data.split("1234####4321");
                            id = Integer.valueOf(a[0]);
                            Date = a[1];
                            Title = a[2];
                            Duyuru = a[3];

                            //atama işlemi tamamlandıktan sonra bağlı listemize ekleme işlemini yapıyoruz.
                            haber.add(id, Date, Title, Duyuru);

                        }
                    }
                } catch (IOException | NumberFormatException e) {
                    //program buraya düşmüş ise bir sorun vardır ve bunu konsola yazdırıyoruz.
                    System.out.println("Hata: (duyurular_dosya_oku(islem = 0)) " + e.getMessage());
                }
                //yukarıdaki döngü ile yaptığımız ekleme işlemlerini return ediyoruz ve bu metodu sonlandırıyoruz.
                return haber;
            case 1:
                //bu alana girmiş ise kampanyalar okunacak
                //ilk olarak parametre olarak gelen değeri kalıtım aracılığıyla başka bir bağlı listeye aktarıyoruz.
                Kampanyalar kampanya = (Kampanyalar) duyuru;
                //yapacağımız işlemleri try-catch bloğu içerisine alıyoruz.
                try {
                    //ilk olarak dosyamızın adresini tanımlıyoruz.
                    FileInputStream fStream = new FileInputStream("lib/txt/kampanyalar.txt");
                    try (DataInputStream dStream = new DataInputStream(fStream)) {
                        BufferedReader bReader = new BufferedReader(new InputStreamReader(dStream));
                        //daha sonra yukarıda tanımladığımız değer aracılığıyla dosyada kaç tane satır varsa o kadarlık bir döngü oluşturuluyor.
                        while ((str = bReader.readLine()) != null) {
                            //bu döngüde dosyadaki veriler ayıklanarak gerekli değişkenlere gerekli değerleri atıyor.
                            data = str;
                            String[] a = data.split("1234####4321");
                            id = Integer.valueOf(a[0]);
                            Date = a[1];
                            Title = a[2];
                            Duyuru = a[3];
                            //atama işlemi tamamlandıktan sonra bağlı listemize ekleme işlemini yapıyoruz.
                            kampanya.add(id, Date, Title, Duyuru);

                        }
                    }
                } catch (IOException | NumberFormatException e) {
                    //program buraya düşmüş ise bir sorun vardır ve bunu konsola yazdırıyoruz.
                    System.out.println("Hata:(duyurular_dosya_oku(islem = 1)) " + e.getMessage());
                }
                //yukarıdaki döngü ile yaptığımız ekleme işlemlerini return ediyoruz ve bu metodu sonlandırıyoruz.
                return kampanya;
            default:
                //program buraya düşmüş ise gönderilen islem degeri hatalı gelmiş demek oluyor. Bu hatayı konsola yazıyoruz.
                System.out.println("Hata: Yanlış Değer geldi (duyurular_dosya_oku)");
                return null;
        }
    }

    //Bu metot hem kampanyarı hem de haberleri dosyaya yazmak için kullanılan metot
    public void duyurular_dosya_yaz(Duyurular duyuru, int islem) {
        //parametre oalrak gelen islem'e göre haberleri mi yoksa kampanyaları mı dosyaya yazacağımızı belirliyoruz.(0: Haberler, 1: Kampanyalar)
        //ilk olarak gelen islem değerini kontrol etmek için switch-case oluşturuluyor.
        switch (islem) {
            case 0:
                //bu alan girilmiş ise haberler dosyaya yazılacak demektir.
                //ilk olarak parametre olarak gelen değeri kalıtım aracılığıyla haberler sınıfına bağlıyoruz.
                Haberler haber = (Haberler) duyuru;
                //daha sonraki işlemleri try-catch bloğu içerisine alıyoruz.
                try {
                    //dosyamızın adresini tanımlıyoruz.
                    FileWriter fWriter = new FileWriter("lib/txt/haberler.txt");
                    try (BufferedWriter writer = new BufferedWriter(fWriter)) {
                        //bağlı listenin içinde gezebilmek için bir geçici değişken oluşturuyoruz.
                        List_Haberler gecici = haber.root;
                        //bağlı listenin başından sonuna doğru tarıyoruz ve her seferinde de bütün değerleri gerekli sırada yazıyoruz.
                        while (gecici != null) {
                            //bu komutlar aracılığıyla gerekli olan değerleri gerekli sırayla yazıyoruz ve ardından bir sonraki satıra geçiyoruz. Ve bir sonraki döngüye geççiyoruz. Bağlı listedeki bütün değerler yazılana kadar devam ediyor.
                            writer.write(gecici.getDuyuru_id() + "1234####4321" + gecici.getDate() + "1234####4321"
                                    + gecici.getTitle() + "1234####4321" + gecici.getDuyuru());
                            writer.newLine();
                            gecici = gecici.getNext();
                        }
                    }
                } catch (IOException e) {
                    //program burya girmiş ise bir hata vardır demektir. Hatayı konsola yazdırıyoruz. 
                    System.out.println("Hata:(duyurular_dosya_yaz(islem=0)) " + e.getMessage());
                }
                break;
            case 1:
                //bu alan girilmiş ise haberler dosyaya yazılacak demektir.
                //ilk olarak parametre olarak gelen değeri kalıtım aracılığıyla haberler sınıfına bağlıyoruz.
                Kampanyalar kampanya = (Kampanyalar) duyuru;
                //daha sonraki işlemleri try-catch bloğu içerisine alıyoruz.
                try {
                    //dosyamızın adresini tanımlıyoruz.
                    FileWriter fWriter = new FileWriter("lib/txt/kampanyalar.txt");
                    try (BufferedWriter writer = new BufferedWriter(fWriter)) {
                        //bağlı listenin içinde gezebilmek için bir geçici değişken oluşturuyoruz.
                        List_Kampanyalar gecici = kampanya.root;
                        //bağlı listenin başından sonuna doğru tarıyoruz ve her seferinde de bütün değerleri gerekli sırada yazıyoruz.
                        while (gecici != null) {
                            //bu komutlar aracılığıyla gerekli olan değerleri gerekli sırayla yazıyoruz ve ardından bir sonraki satıra geçiyoruz. Ve bir sonraki döngüye geççiyoruz. Bağlı listedeki bütün değerler yazılana kadar devam ediyor.
                            writer.write(gecici.getDuyuru_id() + "1234####4321" + gecici.getDate() + "1234####4321" + gecici.getTitle() + "1234####4321" + gecici.getDuyuru());
                            writer.newLine();
                            gecici = gecici.getNext();
                        }
                    }
                } catch (IOException e) {
                    //program burya girmiş ise bir hata vardır demektir. Hatayı konsola yazdırıyoruz. 
                    System.out.println("Hata:(duyurular_dosya_yaz(islem=1)) " + e.getMessage());
                }
                break;
            default:
                //program buraya girmiş ise islem değeri yanlış dönmüş demektir. Hatayı konsola yazıp progrmaı sonlandırıyoruz.
                System.out.println("Hata: Yanlış değer geldi (duyurular_dosya_yaz)");
                break;
        }
    }

    //vizyondaki filmler ve eski filmler için dosyaya ekleme işlemi yapıyor.
    public Filmler filmler_dosya_ekle(String Date, String Title, int islem) {
        //islem 0 ise eski film, islem 1 ise vizyondaki film işlemini yapıyor ve yine kalıtım aracılığıyla bu işlemi gerçekleştiriyor.
        //Bu metotda yukarıdaki metotlara benzemektedir. daha ayrıntı için yukarıdaki metotlara bakabilirsiniz.
        Filmler film;
        switch (islem) {
            case 0:
                film = (Filmler) new Eski_Filmler();
                Eski_Filmler eski = (Eski_Filmler) film;
                filmler_dosya_oku((Filmler) eski, islem);
                eski.add(eski.sayac() + 1, Date, Title);
                filmler_dosya_yaz((Filmler) eski, islem);
                return (Filmler) eski;
            case 1:
                film = (Filmler) new Vizyondaki_Filmler();
                Vizyondaki_Filmler vizyonda = (Vizyondaki_Filmler) film;
                filmler_dosya_oku((Filmler) vizyonda, islem);
                vizyonda.add(vizyonda.sayac() + 1, Date, Title);
                filmler_dosya_yaz((Filmler) vizyonda, islem);
                return (Filmler) vizyonda;
            default:
                System.out.println("Hata: Hatalı dönüş(filmler_dosya_ekle)");
                return null;
        }
    }

    //vizyondaki filmler ve eski filmler için dosyaya silme işlemi yapıyor.
    public int filmler_dosya_sil(int id, int islem) {
        //islem 0 ise eski film, islem 1 ise vizyondaki film işlemini yapıyor ve yine kalıtım aracılığıyla bu işlemi gerçekleştiriyor.
        //Bu metotda yukarıdaki metotlara benzemektedir. daha ayrıntı için yukarıdaki metotlara bakabilirsiniz.
        Filmler film;
        switch (islem) {
            case 0: {
                film = (Filmler) new Eski_Filmler();
                Eski_Filmler eski = (Eski_Filmler) film;
                filmler_dosya_oku((Filmler) eski, islem);
                int sayac = eski.sayac();
                if (sayac >= id) {
                    eski.remove(id);
                    filmler_dosya_yaz((Filmler) eski, islem);
                    return 1;
                } else {
                    return 0;
                }
            }
            case 1: {
                film = (Filmler) new Vizyondaki_Filmler();
                Vizyondaki_Filmler vizyonda = (Vizyondaki_Filmler) film;
                filmler_dosya_oku(vizyonda, islem);
                int sayac = vizyonda.sayac();
                if (sayac >= id) {
                    vizyonda.remove(id);
                    filmler_dosya_yaz((Filmler) vizyonda, islem);
                    return 1;
                } else {
                    return 0;
                }
            }
            default:
                System.out.println("Hata: Hatalı dönüş oldu (filmler_dosya_sil)");
                return -1;
        }
    }

    //vizyondaki filmler ve eski filmler için dosya okuma işlemi yapıyor.
    public Filmler filmler_dosya_oku(Filmler film, int islem) {
        //islem 0 ise eski film, islem 1 ise vizyondaki film işlemini yapıyor ve yine kalıtım aracılığıyla bu işlemi gerçekleştiriyor.
        //Bu metotda yukarıdaki metotlara benzemektedir. daha ayrıntı için yukarıdaki metotlara bakabilirsiniz.
        String str = "";
        String data = "";
        int id;
        String Date;
        String Title;

        switch (islem) {
            case 0:
                Eski_Filmler eski = (Eski_Filmler) film;
                try {
                    FileInputStream fStream = new FileInputStream("lib/txt/eski_filmler.txt");
                    try (DataInputStream dStream = new DataInputStream(fStream)) {
                        BufferedReader bReader = new BufferedReader(new InputStreamReader(dStream));

                        while ((str = bReader.readLine()) != null) {

                            data = str;
                            String[] a = data.split("1234####4321");
                            id = Integer.valueOf(a[0]);
                            Date = a[1];
                            Title = a[2];

                            eski.add(id, Date, Title);

                        }
                    }
                } catch (IOException | NumberFormatException e) {
                    System.out.println("Hata: (filmler_dosya_oku(islem=0))" + e.getMessage());
                }
                return (Filmler) eski;
            case 1:
                Vizyondaki_Filmler vizyonda = (Vizyondaki_Filmler) film;

                try {
                    FileInputStream fStream = new FileInputStream("lib/txt/vizyondaki_filmler.txt");
                    try (DataInputStream dStream = new DataInputStream(fStream)) {
                        BufferedReader bReader = new BufferedReader(new InputStreamReader(dStream));

                        while ((str = bReader.readLine()) != null) {

                            data = str;
                            String[] a = data.split("1234####4321");
                            id = Integer.valueOf(a[0]);
                            Date = a[1];
                            Title = a[2];
                            vizyonda.add(id, Date, Title);
                        }
                    }
                } catch (IOException | NumberFormatException e) {
                    System.out.println("Hata: (filmler_dosya_oku(islem = 1))" + e.getMessage());
                }
                return (Filmler) vizyonda;
            default:
                System.out.println("Hatalı Değer Döndü(filmler_dosya_oku)");
                return null;
        }
    }

    //vizyondaki filmler ve eski filmler için dosya yazma işlemi yapıyor.
    public void filmler_dosya_yaz(Filmler film, int islem) {
        //islem 0 ise eski film, islem 1 ise vizyondaki film işlemini yapıyor ve yine kalıtım aracılığıyla bu işlemi gerçekleştiriyor.
        //Bu metotda yukarıdaki metotlara benzemektedir. daha ayrıntı için yukarıdaki metotlara bakabilirsiniz.
        switch (islem) {
            case 0:
                Eski_Filmler eski = (Eski_Filmler) film;
                try {
                    FileWriter fWriter = new FileWriter("lib/txt/eski_filmler.txt");
                    try (BufferedWriter writer = new BufferedWriter(fWriter)) {
                        List_Eski_Filmler gecici = eski.root;
                        while (gecici != null) {
                            writer.write(gecici.getId() + "1234####4321" + gecici.getDate() + "1234####4321" + gecici.getTitle());
                            writer.newLine();
                            gecici = gecici.getNext();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Hata:(filmler_dosyaya_yaZ(işlem = 0)) " + e.getMessage());
                }
                break;
            case 1:
                Vizyondaki_Filmler vizyonda = (Vizyondaki_Filmler) film;
                try {
                    FileWriter fWriter = new FileWriter("lib/txt/vizyondaki_filmler.txt");
                    try (BufferedWriter writer = new BufferedWriter(fWriter)) {
                        List_Vizyondaki_Filmler gecici = vizyonda.root;
                        while (gecici != null) {
                            writer.write(gecici.getId() + "1234####4321" + gecici.getDate() + "1234####4321" + gecici.getTitle());
                            writer.newLine();
                            gecici = gecici.getNext();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Hata:(filmler_dosya_yaz(işlem == 1) " + e.getMessage());
                }
                break;
            default:
                System.out.println("Hata: Yanlış Değer döndü (filmler_dosya_yaz)");
                break;
        }
    }

    //sinema salonu verileri için dosyada güncelleme yapılması için yazılan metot
    public int sinema_salonlari_dosya_guncelle(int salon_id, String name, int koltuk_sayisi) {
        //ilk başta sinema_salonlari sınıfına ait bir bağlı liste ranımlayıp arından dosyadaki bütün verileri bu bağlı listeye atmak
        Sinema_Salonlari salon = new Sinema_Salonlari();
        sinema_salonlari_dosya_oku(salon);
        //daha sonra sinema_salonlari sınıfının içindeki guncelle metodunya yolluyoruz. eğer işlem 1 dönerse başarılı, 0 dönerse başarısız anlamına geliyor. Eğer başarılı şekilde güncellendi ise de dosyaya yeni bağlı listeyi yazıyoruz.
        int control = salon.guncelle(salon_id, name, koltuk_sayisi);
        if (control == 1) {
            sinema_salonlari_dosya_yaz(salon);
            return 1;
        } else {
            return 0;
        }
    }

    //sinema_salonlari için dosyaya ekleme işlemi yapılıyr
    public Sinema_Salonlari sinema_salonlari_dosya_ekle(String name, int koltuk_sayisi) {
        Sinema_Salonlari salon = new Sinema_Salonlari();
        sinema_salonlari_dosya_oku(salon);
        salon.add(salon.sayac() + 1, name, koltuk_sayisi);
        sinema_salonlari_dosya_yaz(salon);
        return salon;
    }

    //sinema_salonlari dosyasından veri silme işlemi gerçekleştrien metot
    public int sinema_salonlari_dosya_sil(int sinema_salonu_id) {
        // bu metot da yukarıdaki metotlara benzemektedir. Ayrıntı için yukarıdaki silme metotlarına bakabilirsiniz.
        Sinema_Salonlari salon = new Sinema_Salonlari();

        sinema_salonlari_dosya_oku(salon);

        int sayac = salon.sayac();

        if (sayac >= sinema_salonu_id) {

            salon.remove(sinema_salonu_id);

            sinema_salonlari_dosya_yaz(salon);

            return 1;
        } else {

            return 0;
        }

    }

    //sinema_Salonlari dosyasındaki verileir okuma işine yarayan metot.
    public Sinema_Salonlari sinema_salonlari_dosya_oku(Sinema_Salonlari salon) {
        //bu metot yukarıdaki diğer dosya okuma metotlarına benzemektedir.Yukarıda ayrıntılısıyla anlatılmıştır isterseniz oraya bakabilirsiniz.
        String str = "";
        String data = "";
        int sinema_salonu_id;
        String name;
        int koltuk_sayisi;

        try {
            FileInputStream fStream = new FileInputStream("lib/txt/sinema_salonlari.txt");
            try (DataInputStream dStream = new DataInputStream(fStream)) {
                BufferedReader bReader = new BufferedReader(new InputStreamReader(dStream));

                while ((str = bReader.readLine()) != null) {

                    data = str;
                    String[] a = data.split("1234####4321");
                    sinema_salonu_id = Integer.valueOf(a[0]);
                    name = a[1];
                    koltuk_sayisi = Integer.valueOf(a[2]);

                    salon.add(sinema_salonu_id, name, koltuk_sayisi);

                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Hata:(snema_salonları_dosya_oku) " + e.getMessage());
        }
        return salon;
    }

    //sinema_Salonlari dosyasına verleri yazmak için bu metot tasarlandı.
    public void sinema_salonlari_dosya_yaz(Sinema_Salonlari salon) {
        //bu dosya yazma metodu yukarıda açıklanan metotlara benzemektedir. İsterseniz oradan açıklamayı bulabilirsiniz.
        try {
            FileWriter fWriter = new FileWriter("lib/txt/sinema_salonlari.txt");
            try (BufferedWriter writer = new BufferedWriter(fWriter)) {
                List_Sinema_Salonlari gecici = salon.root;
                while (gecici != null) {
                    writer.write(gecici.getSalon_id() + "1234####4321" + gecici.getSalon_name() + "1234####4321" + gecici.getKoltuk_sayisi());
                    writer.newLine();
                    gecici = gecici.getNext();
                }
            }
        } catch (IOException e) {
            System.out.println("Hata:(sinema_salonları_dosya_yaz) " + e.getMessage());
        }
    }

    //Bu metot seans dosyası için ekleme işlmei yapılıyor
    public void seans_dosya_ekle(int salon_id) {
        //ilk olarak seans sınıfına ait bir bağlı liste oluşturuluyor.
        Seans seans = new Seans();
        //daha sonra dosyadaki bütün veriler oluşturulan bağlı listeye aktarılıyor.
        seans_dosya_oku(seans);
        //doldurulan bağlı listeye de eklenmek istenilen değer ekleniyor.
        seans.add(salon_id, -1, -1, -1, -1, -1);
        //yeni bağlı listedeki dosyaya yeniden yazdırılıyor.
        seans_dosya_yaz(seans);
    }
    
    //Bu metot seans dosyası için silme işlemi yapıyor. 
    public void seans_dosya_sil(int salon_id, int seans_id) {
        //ilk olarak seans sınıfına ait bir bağlı liste oluşturuluyor.
        Seans seans = new Seans();
        //daha sonra dosyadaki bütün veriler dosyaya aktarılıyor.
        seans_dosya_oku(seans);
        //doldurulmuş bağlı listedeki istenilen değer siliniyor.
        seans.remove(salon_id, seans_id);
        //oluşan yeni bağlı liste dosyaya yazdırılıyor.
        seans_dosya_yaz(seans);
    }

    //Bu metot seans dosaysındaki verileri okumaya yarıyor.
    public Seans seans_dosya_oku(Seans seans) {
        //ayrıntılısı yukarıda anlatılmıştır. Bu sebeple ayrıntılı anlatım yapılmamıştır.
        String str = "";
        String data = "";
        int salon_id;
        int film1_id;
        int film2_id;
        int film3_id;
        int film4_id;
        int film5_id;

        try {
            FileInputStream fStream = new FileInputStream("lib/txt/seans.txt");
            try (DataInputStream dStream = new DataInputStream(fStream)) {
                BufferedReader bReader = new BufferedReader(new InputStreamReader(dStream));

                while ((str = bReader.readLine()) != null) {

                    data = str;
                    String[] a = data.split("/");
                    salon_id = Integer.valueOf(a[0]);
                    film1_id = Integer.valueOf(a[1]);
                    film2_id = Integer.valueOf(a[2]);
                    film3_id = Integer.valueOf(a[3]);
                    film4_id = Integer.valueOf(a[4]);
                    film5_id = Integer.valueOf(a[5]);

                    seans.add(salon_id, film1_id, film2_id, film3_id, film4_id, film5_id);

                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Hata:(seans_dosya_oku) " + e.getMessage());
        }
        return seans;
    }

    //Bu metot seans dosyasına verileri yazmak için yazıldı
    public void seans_dosya_yaz(Seans seans) {
        //Yukarıdaki metotlarla benzerlik gösterdiği için bu metot anlatılmamıştır.
        try {
            FileWriter fWriter = new FileWriter("lib/txt/seans.txt");
            try (BufferedWriter writer = new BufferedWriter(fWriter)) {
                List_Seans gecici = seans.root;
                while (gecici != null) {
                    writer.write(gecici.getSalon_id() + "/" + gecici.getFilm1_id() + "/" + gecici.getFilm2_id() + "/" + gecici.getFilm3_id() + "/" + gecici.getFilm4_id() + "/" + gecici.getFilm5_id());
                    writer.newLine();
                    gecici = gecici.getNext();
                }
            }
        } catch (IOException e) {
            System.out.println("Hata: (seans_dosya_yaz) " + e.getMessage());
        }
    }

}
