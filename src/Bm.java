import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Bm {
    public static void main(String[] args) {
        String dosyaYolu = "C:\\javademos\\boyer_moorealgorithm\\alice_in_wonderland.txt";  //dosyayolu değişkenine "alice_in_wonderland.txt" dosyasının dosya yolu atanıyor.
        String metin = "";

        try (BufferedReader br = new BufferedReader(new FileReader(dosyaYolu))) { // "BufferedReader" kullanarak dosya okunuyor ve okunan satırlar "metin" dizisine ekleniyor.
            String satir;
            while ((satir = br.readLine()) != null) {
                metin += satir + " ";
            }
        }
          catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }

        String[] arananKelimeler = {"upon", "sigh", "Dormouse", "jury-box", "swim"};
        Map<String, Integer> sayac = new HashMap<String, Integer>();

          for (String kelime : arananKelimeler) {
            sayac.put(kelime, 0);
          }   //Daha sonra iç içe döngü oluşturularak aranan kelime listesi üzerinde geziniliyor

          for (int i = 0; i < metin.length() - arananKelimeler[0].length() + 1; i++) {
             for (String kelime : arananKelimeler) {
                int j = kelime.length() - 1;
                while (j >= 0 && i + j < metin.length() && kelime.charAt(j) == metin.charAt(i + j)) {
                    j--;    // Dizinin sınırı kontrol edilerek eşleşen karakter bulunana dek j değeri azaltılarak devam ediliyo
                }
                if (j < 0) {
                    sayac.put(kelime, sayac.get(kelime) + 1);
                }
            }
        }
        System.out.println("Alice in Wonderland metininde aranan kelimelerin kaç defa tekrarlandığı aşağıda yazmaktadır.\n");
          for (Map.Entry<String, Integer> entry : sayac.entrySet()) {
            System.out.println(entry.getKey() + " kelimesi : " + entry.getValue()+ " kez geciyor"); // metin içerisinde kaç kez geçtiği bulunan kelimeler ekrana çıktı olarak veriliyor
        }
    }
}