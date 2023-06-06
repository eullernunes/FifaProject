import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.TimeZone;

class HuffmanNode {
    int frequencia; 
    char caracter;
    HuffmanNode esquerda; 
    HuffmanNode direita; 
}

class Comparador implements Comparator<HuffmanNode> { 
    public int compare(HuffmanNode x, HuffmanNode y) { 
        return x.frequencia - y.frequencia;  
    }
}


public class HuffmanTree {

    public static void createTxt()throws IOException{
        RandomAccessFile fileReader = new RandomAccessFile("dados/Jogadores.db", "r");
        RandomAccessFile arq = new RandomAccessFile("dados/arquivo.txt", "rw");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        
        fileReader.seek(0);
        int idCabecalho = fileReader.readInt();
        arq.writeChars(Integer.toString(idCabecalho));
        arq.writeChar(',');
        while (fileReader.getFilePointer() < fileReader.length()) {
            System.out.println("entrei while");
            boolean lapide = fileReader.readBoolean();
            if(lapide){
                int um = 1;
                arq.writeChars(Integer.toString(um));
                arq.writeChar(',');
            }else{
                int zero = 0;
                arq.writeChars(Integer.toString(zero));
                arq.writeChar(',');
            }
            int tamanho = fileReader.readInt();
            arq.writeChars(Integer.toString(tamanho));
            arq.writeChar(',');
            int id = fileReader.readInt();
            arq.writeChars(Integer.toString(id));
            arq.writeChar(',');
            String knowAs = fileReader.readUTF();
            tamanho = knowAs.length();
            arq.writeChars(Integer.toString(tamanho));
            arq.writeChar(',');
            arq.writeChars(knowAs);
            arq.writeChar(',');
            String fullName = fileReader.readUTF();
            tamanho = fullName.length();
            arq.writeChars(Integer.toString(tamanho));
            arq.writeChar(',');
            arq.writeChars(fullName);
            arq.writeChar(',');
            Byte overall = fileReader.readByte();
            arq.writeChars(Byte.toString(overall));
            arq.writeChar(',');
            int value = fileReader.readInt();
            arq.writeChars(Integer.toString(value));
            arq.writeChar(',');
            String bestPosition = fileReader.readUTF();
            tamanho = bestPosition.length();
            arq.writeChars(Integer.toString(tamanho));
            arq.writeChar(',');
            arq.writeChars(bestPosition);
            arq.writeChar(',');
            String nacionality = fileReader.readUTF();
            tamanho = nacionality.length();
            arq.writeChars(Integer.toString(tamanho));
            arq.writeChar(',');
            arq.writeChars(nacionality);
            arq.writeChar(',');
            Byte age = fileReader.readByte();
            arq.writeChars(Byte.toString(age));
            arq.writeChar(',');
            String clubName = fileReader.readUTF();
            tamanho = clubName.length();
            arq.writeChars(Integer.toString(tamanho));
            arq.writeChar(',');
            arq.writeChars(clubName);
            arq.writeChar(',');
            long data = fileReader.readLong();
            Date data1 = new Date(data);   
            arq.writeChars(sdf.format(data1)); 
            arq.writeChar(',');         
        }

        fileReader.close();
        arq.close();        
    }


    //Cria um Hasmap com os caracteres e suas frequências
    //Para cada caractere do arquivo txt, pega o caractere e adiciona no hashmap incrementando a frequencia do caractere

    public static HashMap<Character, Integer> createHash() throws IOException { 
        RandomAccessFile arq = new RandomAccessFile("dados/arquivo.txt", "r");

        HashMap<Character, Integer> map = new HashMap<>();

        String line = arq.readLine();
        line = line.replaceAll(",", "");

        for (int i = 0; i < line.length(); i++) { 
            char c = line.charAt(i); 
            if (map.containsKey(c)) {
                map.put(c, map.get(c) + 1);
            } else {
                map.put(c, 1);
            }
        }

        arq.close();

        char first = map.keySet().iterator().next(); 
        map.remove(first); 

        return map;
    }


    /*
     * Método que cria o código de Huffman
     * Verifica se é nó folha, se for vai para o final do arquivo e escreve o código
     */
    public static void printCode(HuffmanNode raiz, String code) throws IOException { 
        RandomAccessFile arq = new RandomAccessFile("dados/huffmanCode.txt", "rw");

        if (raiz.esquerda == null && raiz.direita == null) { 
            arq.seek(arq.length());
            arq.writeChars(raiz.caracter + "=" + code);
            arq.writeChars("\n"); 
            arq.close();
            return;
        }

        printCode(raiz.esquerda, code + "0"); 
        printCode(raiz.direita, code + "1");
        arq.close();
    }

    /*
     * Método que cria a árvore de Huffman
     */
    public static void buildTree(HashMap<Character, Integer> map) throws IOException { 
        int n = map.size(); 
        char[] charArray = new char[n]; 
        int[] charfreq = new int[n]; 

        int count = 0; 
        for (Character c : map.keySet()) { 
            charArray[count] = c; 
            charfreq[count] = map.get(c); 
            count++; 
        }

        PriorityQueue<HuffmanNode> q = new PriorityQueue<>(n, new Comparador()); 

        for (int i = 0; i < n; i++) { 
            HuffmanNode hn = new HuffmanNode(); 

            hn.caracter = charArray[i];
            hn.frequencia = charfreq[i]; 

            hn.esquerda = null; 
            hn.direita = null;

            q.add(hn);
        }

        HuffmanNode raiz = null;

        while (q.size() > 1) { 
            HuffmanNode x = q.peek(); 
            q.poll(); 
            HuffmanNode y = q.peek(); 
            q.poll(); 

            HuffmanNode f = new HuffmanNode(); 
            f.frequencia = x.frequencia + y.frequencia;
            f.caracter = '-'; 
            f.esquerda = x;
            f.direita = y; 

            raiz = f;
            q.add(f);
        }

        printCode(raiz, ""); 
    }


    /*
     * Método que cria um HashMap com o caractere e o código de Huffman
     */

    public static HashMap<Character, String> createCode() throws IOException {
        RandomAccessFile arq = new RandomAccessFile("dados/huffmanCode.txt", "r"); 

        HashMap<Character, String> code = new HashMap<Character, String>(); 
        
        arq.seek(0);
        while (arq.getFilePointer() < arq.length()) { 
            String line = arq.readLine(); 
            String[] split = line.split("=");
            code.put(split[0].charAt(1), split[1]);
            split = null; 
        }

        arq.close();
        return code; 
    }

    /*
     * Método que converte o código de Huffman em binário
     */
    public static int stringToBinary(String codigo) { 
        int result = 0;

        for (int i = 0; i < codigo.length(); i++) { 
            if (codigo.charAt(i) == '1') { 
                result += Math.pow(2, codigo.length() - i - 1); 
            }
        }

        return result; 
    }

    /*
     *Método que compacta o arquivo
     */
    public static void compress(HashMap<Character, String> code) throws IOException { 
        RandomAccessFile arq = new RandomAccessFile("dados/arquivo.txt", "r"); 
        RandomAccessFile arq2 = new RandomAccessFile("dados/JogadoresCompress.db", "rw");

        arq.seek(0); 
        while (arq.getFilePointer() < arq.length()) {
            String line = arq.readLine();

            for (int i = 0; i < line.length(); i++) { 
                char c = line.charAt(i); 
                String cod = code.get(c); 
                if (cod != null) {           
                    String codigo = removeMetaDados(cod);           
                    int bin = stringToBinary(codigo); 
                    arq2.write(bin); 
                }
            }
        }

        arq.close();
        arq2.close();
    }

    public static boolean compactar() throws IOException {
        long start = System.currentTimeMillis(); 
        createTxt(); 
        HashMap<Character, Integer> map = createHash(); 
        buildTree(map); 
        HashMap<Character, String> huff = createCode(); 
        compress(huff);
        

        long time = (System.currentTimeMillis() - start); 
        float tamanhoOriginal = new File("dados/Jogadores.db").length();
        float tamanho = new File("dados/JogadoresCompress.db").length(); 

        System.out.println("\nTempo de execução: " + time + " milissegundos");
        System.out.println("Tamanho do arquivo original: " + tamanhoOriginal + " bytes"); 
        System.out.println("Tamanho do arquivo compactado: " + tamanho + " bytes");
        System.out.println("Taxa de compressão: " + (tamanhoOriginal / tamanho) + " vezes");

        return true;
    }

    public static String removeZeroEsq(String codigo) { // Método que remove os zeros à esquerda do código de Huffman
        String cod = "";

        for (int i = 0; i < codigo.length(); i++) { // Para cada caractere no código de Huffman
            if (codigo.charAt(i) == '1') { // Se o caractere for 1
                cod = codigo.substring(i); // Pega o código a partir do índice do caractere
                break;
            }
        }

        return cod; // Retorna o código
    }

    public static boolean descompactar() throws IOException { 
        long start = System.currentTimeMillis(); 

        HashMap<String, Character> map = new HashMap<String, Character>(); 
        RandomAccessFile huff = new RandomAccessFile("dados/huffmanCode.txt", "r"); 

        huff.seek(0);
        while (huff.getFilePointer() < huff.length()) { 
            String line = huff.readLine(); 
            String[] split = line.split("="); 
            String codigo = split[1]; 
            codigo = removeZeroEsq(codigo);
            char c = split[0].charAt(1);
            map.put(removeMetaDados(codigo), c); 
            split = null;
        }

        huff.close();

        RandomAccessFile raf = new RandomAccessFile("dados/JogadoresCompress.db", "r");
        RandomAccessFile arq = new RandomAccessFile("dados/JogadoresDescompress.txt", "rw");
        if(arq.length() > 0) {
            arq.setLength(0);
        }

        raf.seek(0);
        while (raf.getFilePointer() < raf.length()) {
            int bin = raf.read();
            String codigo = Integer.toBinaryString(bin);
            codigo = removeMetaDados(codigo); 
            if (map.get(codigo) != null) { 
                if(map.get(codigo) == ';') arq.writeChars("\n");
                else arq.writeChar(map.get(codigo));
            }
        }

        arq.close();
        raf.close();

        long time = (System.currentTimeMillis() - start); 

        System.out.println("\nTempo de execução: " + time + " milissegundos");

        return true;
    }

    public static String removeMetaDados(String cod) { // Método que remove os metadados do código de Huffman contidos no HashMap
        String codSemMeta = ""; 

        for (int i = 0; i < cod.length(); i++) { // Para cada caractere no código de Huffman
            if (cod.charAt(i) == '1') { // Se o caractere for 1
                codSemMeta += '1'; // Adiciona 1 no código sem metadados
            } else if (cod.charAt(i) == '0') { // Se o caractere for 0
                codSemMeta += '0'; // Adiciona 0 no código sem metadados
            }
        }

        return codSemMeta; // Retorna o código sem metadados
    }
}