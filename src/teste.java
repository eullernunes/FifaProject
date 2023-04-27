import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class teste {

    public static void main(String[] args) throws ParseException {
        

        String data = "2021";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date date = sdf.parse("01/01/" + data);
        long datinha = date.getTime();  
        System.out.println(datinha);
        System.out.println(sdf.format(datinha));

        Date teste = new Date(datinha);
        System.out.println(sdf.format(teste));
        


       
    }
}
