import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.PriorityQueue;
import java.util.TimeZone;

public class HuffmanCode {
    static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public static void start(String text) throws Exception{
        long start = System.currentTimeMillis();


        int[] chars = new int[256];
        for (char c : text.toCharArray()) {
            chars[c]++;
        }

        HuffmanTree arvore = buildTree(chars);

        RandomAccessFile arq = new RandomAccessFile("arquivo.txt", "r"); // Lê o arquivo .txt
        String str = arq.readLine();
        str = str.replaceAll(",", "");

        int [] charFreqs = new int[256];
    
        // Neste exemplo será considerado que teremos no máximo 256 caracteres diferentes
        // Passo 1 - Percorre o texto contando os símbolos e montando um vetor de frequências.
        for (char c : str.toCharArray()){
            charFreqs[c]++;
        }
            
        // Criar a arvore dos códigos para a Compactação
        HuffmanTree tree = buildTree(charFreqs);

        String encode = encode(tree,str);
        printCodes(tree,new StringBuffer());

       
        return false;
    }

    public static HuffmanTree buildTree(int[] charFreqs){
        //Cria uma Fila de Prioridade
        PriorityQueue<HuffmanTree> tree = new PriorityQueue<HuffmanTree>();

        //cria uma folha para cada caractere
        for(int i = 0; i < charFreqs.length; i++){
            if(charFreqs[i] > 0){
                tree.offer(new HuffmanLeaf(charFreqs[i], (char)i)); //cria o nó folha e adiciona na fila
            }
        }

        //percorre todos os elementos da fila
        //cria arvore binária de baixo pra cima
        while (tree.size() > 1) {            
            HuffmanTree a = tree.poll();  //Retorna o próximo nó da Fila ou NULL se não tem mais
            HuffmanTree b = tree.poll(); // Retorna o próximo nó da Fila ou NULL se não tem mais
 
            // Criar os nós da árvore binária
            tree.offer(new HuffmanNode(a, b)); 
        }
        // Retorna o primeiro nó da árvore
        return tree.poll();
    }


    public static String decode(HuffmanTree tree, String encode){
        assert tree != null;

        String decodeText = "";
        HuffmanNode node = (HuffmanNode) tree;
        for (char code : encode.toCharArray()) {
            if(code == '0'){
                if(node.left instanceof HuffmanLeaf){
                    decodeText += ((HuffmanLeaf)node.left).value;
                    node = (HuffmanNode) tree;
                }else{
                    node = (HuffmanNode) node.left;
                }
            }else if(code == '1'){
                if(node.right instanceof HuffmanLeaf){
                    decodeText += ((HuffmanLeaf)node.right).value;
                    node = (HuffmanNode) tree;
                }else{
                    node = (HuffmanNode) node.right;
                }
            }
        }

        return decodeText;
    }

    public static int lastVersion(){
        File directory = new File("./db/Huffman/");
        int lastVersion = directory.list().length + 1;

        return lastVersion;
    }

    public static String encode(String encode, String name)throws IOException{
        int lastVersion = lastVersion();
        File arq = new File ("./db/Huffman/" +name + lastVersion +".txt");

        for (char c : encode.toCharArray()){
            encodeText += (getCodes(tree, new StringBuffer(), c));
        }
        
        return encodeText; // retorna string compacatada
    }

   
    public static String getCodes(HuffmanTree tree, StringBuffer prefix, char w){
        assert tree != null;

        if(tree instanceof HuffmanLeaf){
            HuffmanLeaf leaf = (HuffmanLeaf) tree;

            if(leaf.value == w){
                return prefix.toString();
            }
        }else if(tree instanceof HuffmanNode){
            HuffmanNode node = (HuffmanNode) tree;

            prefix.append('0');
            String left = getCodes(node.left, prefix, w);
            prefix.deleteCharAt(prefix.length() - 1);

            prefix.append('1');
            String right = getCodes(node.right, prefix, w);
            prefix.deleteCharAt(prefix.length() - 1);

            if(left == null){
                return right;
            }else{
                return left;
            }
        }
        return null;
    }
       
    public static void printCodes(HuffmanTree tree, StringBuffer prefix)throws IOException{
        RandomAccessFile arq = new RandomAccessFile("huffmanCode.txt", "rw"); // Cria um arquivo .txt
        assert tree != null;

        if(tree instanceof HuffmanLeaf){
            HuffmanLeaf leaf = (HuffmanLeaf) tree;
            arq.seek(arq.length()); // Vai para o final do arquivo .txt
            arq.writeChars(leaf.value + "=" + prefix); // Escreve o caractere e o código no arquivo .txt
            arq.writeChars("\n"); // Escreve uma quebra de linha no arquivo .txt
            arq.close();
        }else if(tree instanceof HuffmanNode){
            HuffmanNode node = (HuffmanNode) tree;

            prefix.append('0');
            printCodes(node.left, prefix);
            prefix.deleteCharAt(prefix.length()-1);

            prefix.append('1');
            printCodes(node.right, prefix);
            prefix.deleteCharAt(prefix.length()-1);


        }
    }

    public static void createTxt()throws IOException{
        RandomAccessFile fileReader = new RandomAccessFile("dados/Jogadores.db", "r");
        RandomAccessFile arq = new RandomAccessFile("arquivo.txt", "rw");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        
        fileReader.seek(0);
        int idCabecalho = fileReader.readInt();
        arq.writeChars(Integer.toString(idCabecalho));
        arq.writeChar(',');
        while (fileReader.getFilePointer() < fileReader.length()) {
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
    
}
