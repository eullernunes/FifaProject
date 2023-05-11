import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class LeCsv {

    public void lendoArquivo(String path) throws Exception{
        FileReader fileReader = new FileReader(path);
        BufferedReader br = new BufferedReader(fileReader);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        String linha = "";
        Jogador novoJogador;
        Crud crud = new Crud("Jogadores");

        br.readLine(); //ignora o cabeçalho do csv
        linha = br.readLine();

        while(linha != null){
            novoJogador = new Jogador();
            String[] array = linha.split(",");

            String knowAs = array[0];
            novoJogador.setKnownAs(knowAs);
            String fullName = array[1];
            novoJogador.setFullName(fullName);
            Byte overall = Byte.parseByte(array[2]);
            novoJogador.setOverall(overall);
            int value = Integer.parseInt(array[3]);
            novoJogador.setValue(value);
            String bestPosition = array[4];
            novoJogador.setBestPosition(bestPosition);
            String nacionality = array[5];
            novoJogador.setNacionality(nacionality);
            Byte age = Byte.parseByte(array[6]);
            novoJogador.setAge(age);
            String clubName = array[7];
            novoJogador.setClubName(clubName);            
            String dateString = array[8];   
            Date date = sdf.parse("01/01/"+ dateString); 
            novoJogador.setJoinedOn(date);

            crud.create(novoJogador);
            linha = br.readLine();
        }

        br.close();
        fileReader.close();
    }
}
