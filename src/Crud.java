import java.io.File;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.TimeZone;

public class Crud {
    RandomAccessFile fileReader;
    long posicaoLapide = 0;
    boolean lapide;
    int tamanho = 0;
    


    public Crud (String path) throws Exception{
        File f = new File("dados");

        if(!f.exists()){
            f.mkdir();
        }

        fileReader = new RandomAccessFile(path, "rw");

        if(fileReader.length() == 0){
            fileReader.writeInt(0);
        }
    }

    public Crud(){}

    public void create(Jogador jogador) throws Exception{
        fileReader.seek(0); // vai pro inicio do arquivo
        int ultimoId = fileReader.readInt(); //le o ultimo id cadastrado (cabeçalho)
        jogador.setId(ultimoId + 1); //seta o proximo id ao objeto
        fileReader.seek(0); //volta pro inicio do arquivo
        fileReader.writeInt(ultimoId + 1); //atualiza o cabeçalho
        fileReader.seek(fileReader.length()); //vai pro fim do arquivo

        //cria o registro para o objeto
        byte[] ba = jogador.toByteArray();
        fileReader.writeBoolean(false);
        fileReader.writeInt(ba.length);
        fileReader.write(ba);
    }


    public void clearBuffer(Scanner scanner){
        if(scanner.hasNextLine()){
            scanner.nextLine();
        }
    }

    public Jogador create(int id)throws Exception{

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        Scanner sc = new Scanner(System.in);
        Jogador jogador = new Jogador();

        //Recebendo dados para criar o objeto jogador

        System.out.println("\nInsira os atributos do jogador: ");

        jogador.setId(id);

        System.out.println("\nDigite o nome do jogador");
        jogador.setKnownAs(sc.nextLine());

        System.out.println("\nDigite o nome completo do jogador");
        jogador.setFullName(sc.nextLine());

        System.out.println("\nDigite o Overall do jogador");
        jogador.setOverall(sc.nextByte());

        System.out.println("\nDigite o valor do jogador:");
        jogador.setValue(sc.nextInt());

        clearBuffer(sc);

        System.out.println("\nDigite a posição do jogador");
        jogador.setBestPosition(sc.nextLine());

        System.out.println("\nDigite a nacionalidade do jogador");
        jogador.setNacionality(sc.nextLine());

        System.out.println("\nDigite a idade do jogador");
        jogador.setAge(sc.nextByte());

        clearBuffer(sc);

        System.out.println("\nDigite o clube do jogador");
        jogador.setClubName(sc.nextLine());

        System.out.println("\nEntre com a data de ingresso do jogador");
        String data = "";
        data = sc.nextLine();
        Date date = sdf.parse(data);
        jogador.setJoinedOn(date);

        return jogador;
    }



    public int idCabecalho(){
        int idCabecalho;

        try {
            fileReader.seek(0);
            idCabecalho = fileReader.readInt();
            return idCabecalho;
        } catch (Exception e) {
           System.out.println("Nao foi possivel abrir o arquivo!");
           return 0;
        }
    }


    public Jogador read(int id) throws Exception{
        Jogador jogador = new Jogador();
        byte[] ba;


        fileReader.seek(4); //pula o cabeçalho

        while(fileReader.getFilePointer() < fileReader.length()){
            lapide = fileReader.readBoolean();
            tamanho = fileReader.readInt();

            if(!lapide){
                ba = new byte[tamanho];
                fileReader.read(ba);
                jogador.fromByteArray(ba);
                if(jogador.getId() == id){
                    return jogador;
                }
            }else{
                fileReader.skipBytes(tamanho);
            }
        }

        return null;
    }

    public boolean update(Jogador novoJogador)throws Exception{
        Jogador jogador = new Jogador();
        byte[] ba;
        byte[] baNovo;

        fileReader.seek(4); //pula o cabeçalho

        while(fileReader.getFilePointer() < fileReader.length()){
            posicaoLapide = fileReader.getFilePointer();
            lapide = fileReader.readBoolean();
            tamanho = fileReader.readInt();
            if(!lapide){
                ba = new byte[tamanho];
                fileReader.read(ba);
                jogador.fromByteArray(ba);
                if(jogador.getId() == novoJogador.getId()){
                    baNovo = novoJogador.toByteArray();
                    if(baNovo.length <= tamanho){
                        fileReader.seek(posicaoLapide + 5);
                        fileReader.write(baNovo);
                    }else{
                        fileReader.seek(posicaoLapide);
                        fileReader.writeBoolean(true);
                        fileReader.seek(fileReader.length());
                        fileReader.writeBoolean(false);
                        fileReader.writeInt(baNovo.length);
                        fileReader.write(baNovo);
                    }

                    return true;
                }
            } else{
                fileReader.skipBytes(tamanho);
            }
        }

        return false;
    }

    public boolean delete(int id)throws Exception {

        fileReader.seek(4); //pula o cabeçalho
        byte[] ba;
        Jogador jogador = new Jogador();

        while(fileReader.getFilePointer() < fileReader.length()){
            posicaoLapide = fileReader.getFilePointer();
            lapide = fileReader.readBoolean();
            tamanho = fileReader.readInt();
            if(!lapide){
                ba = new byte[tamanho];
                fileReader.read(ba);
                jogador.fromByteArray(ba);
                if(jogador.getId() == id){
                    fileReader.seek(posicaoLapide);
                    fileReader.writeBoolean(true);
                    return true;
                }
            }else{
                fileReader.skipBytes(tamanho);
            }
        }
        return false;
    }
}
