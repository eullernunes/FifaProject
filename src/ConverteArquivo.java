import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.Date;

public class ConverteArquivo {

    public static void createTxt(String fileName)throws IOException{
        OutputStream os = new FileOutputStream("dados/txt/arquivo.txt"); // nome do arquivo que será escrito
        Writer wr = new OutputStreamWriter(os); // criação de um escritor
        BufferedWriter br = new BufferedWriter(wr); // adiciono a um escritor de buffer

        RandomAccessFile fileReader = new RandomAccessFile(fileName, "r");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        fileReader.seek(0);
        int idCabecalho = fileReader.readInt();

        br.write(Integer.toString(idCabecalho));
        br.write(",");

        while (fileReader.getFilePointer() < fileReader.length()) {
            boolean lapide = fileReader.readBoolean();
            if(lapide){
                br.write("1");
                br.write(",");
            }else{
                br.write("0");
                br.write(",");
            }
            int tamanho = fileReader.readInt();
            br.write(Integer.toString(tamanho));
            br.write(",");
            int id = fileReader.readInt();
            br.write(Integer.toString(id));
            br.write(",");
            String knowAs = fileReader.readUTF();
            tamanho = knowAs.length();
            br.write(Integer.toString(tamanho));
            br.write(",");
            br.write(knowAs);
            br.write(",");
            String fullName = fileReader.readUTF();
            tamanho = fullName.length();
            br.write(Integer.toString(tamanho));
            br.write(",");
            br.write(fullName);
            br.write(",");
            Byte overall = fileReader.readByte();
            br.write(Integer.toString(overall));
            br.write(",");            
            int value = fileReader.readInt();
            br.write(Integer.toString(value));
            br.write(",");
            String bestPosition = fileReader.readUTF();
            tamanho = bestPosition.length();
            br.write(Integer.toString(tamanho));
            br.write(",");
            br.write(bestPosition);
            br.write(",");
            String nacionality = fileReader.readUTF();
            tamanho = nacionality.length();
            br.write(Integer.toString(tamanho));
            br.write(",");
            br.write(nacionality);
            br.write(",");
            Byte age = fileReader.readByte();
            br.write(Integer.toString(age));
            br.write(",");
            String clubName = fileReader.readUTF();
            tamanho = clubName.length();
            br.write(Integer.toString(tamanho));
            br.write(",");
            br.write(clubName);
            br.write(",");
            long data = fileReader.readLong();
            Date data1 = new Date(data);   
            String data2 = sdf.format(data1);
            br.write(data2);
            br.write(",");   
        }

        fileReader.close();
        br.close();        
    }

}
