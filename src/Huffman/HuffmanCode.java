package Huffman;

import java.util.PriorityQueue;

public class HuffmanCode {
    
    public void compacta(String str){
        int [] charFreqs = new int[256];

        // Neste exemplo será considerado que teremos no máximo 256 caracteres diferentes
        // Passo 1 - Percorre o texto contando os símbolos e montando um vetor de frequências.
        for (char c : str.toCharArray()){
            charFreqs[c]++;
        }
        
        // Criar a Árvore dos códigos para a Compactação
        HuffmanTree tree = buildTree(charFreqs);
        

        //Resultados
        System.out.println("\nTabela:");
        System.out.println("Simbolo\tFrequencia\tHuffman");
        printCodes(tree, new StringBuffer());

        //Compactando o texto
        String encode = encode(tree,str);
        System.out.println("Texto Compactado");
        System.out.println(encode);

        //Decodificando o texto
        System.out.println("Texto Decodificado");
        System.out.println(decode(tree,encode));
    }

    public static HuffmanTree buildTree(int[] charFreqs){
        PriorityQueue<HuffmanTree> tree = new PriorityQueue<HuffmanTree>();

        for(int i = 0; i < charFreqs.length; i++){
            if(charFreqs[i] > 0){
                tree.offer(new HuffmanLeaf(charFreqs[i], (char)i));
            }
        }
        assert tree.size() > 0;

        while (tree.size() > 1) {
            // Pega os dois nós com menor frequência
            HuffmanTree a = tree.poll();  //Retorna o próximo nó da Fila ou NULL se não tem mais
            HuffmanTree b = tree.poll(); // Retorna o próximo nó da Fila ou NULL se não tem mais
 
            // Criar os nós da árvore binária
            tree.offer(new HuffmanNode(a, b)); 
        }
        // Retorna o primeiro nó da árvore
        return tree.poll();
    }

    public static String encode(HuffmanTree tree, String encode){
    	assert tree != null;
    	
    	String encodeText = "";
        for (char c : encode.toCharArray()){
        	encodeText+=(getCodes(tree, new StringBuffer(),c));
        }
    	return encodeText; // Retorna o texto Compactado
    }
    public static String decode(HuffmanTree tree, String encode) {
    	assert tree != null;
    	
    	String decodeText="";
    	HuffmanNode node = (HuffmanNode)tree;
    	for (char code : encode.toCharArray()){
    		if (code == '0'){ // Quando for igual a 0 é o Lado Esquerdo
    		    if (node.left instanceof HuffmanLeaf) { 
    		    	decodeText += ((HuffmanLeaf)node.left).value; // Retorna o valor do nó folha, pelo lado Esquerdo  
	                node = (HuffmanNode)tree; // Retorna para a Raíz da árvore
	    		}else{
	    			node = (HuffmanNode) node.left; // Continua percorrendo a árvore pelo lado Esquerdo 
	    		}
    		}else if (code == '1'){ // Quando for igual a 1 é o Lado Direito
    		    if (node.right instanceof HuffmanLeaf) {
    		    	decodeText += ((HuffmanLeaf)node.right).value; //Retorna o valor do nó folha, pelo lado Direito
	                node = (HuffmanNode)tree; // Retorna para a Raíz da árvore
	    		}else{
	    			node = (HuffmanNode) node.right; // Continua percorrendo a árvore pelo lado Direito
	    		}
    		}
    	} // End for
    	return decodeText; // Retorna o texto Decodificado
    }    
    
    /* 
     * Método para percorrer a Árvore e mostra a tabela de compactação
     *     Parâmetros de Entrada: tree - Raiz da Árvore de compactação
     *     						  prefix - texto codificado com 0 e/ou 1
     */
    public static void printCodes(HuffmanTree tree, StringBuffer prefix) {
        assert tree != null;
        
        if (tree instanceof HuffmanLeaf) {
            HuffmanLeaf leaf = (HuffmanLeaf)tree;
            
            // Imprime na tela a Lista
            System.out.println(leaf.value + "\t" + leaf.frequency + "\t" + prefix);
 
        } else if (tree instanceof HuffmanNode) {
            HuffmanNode node = (HuffmanNode)tree;
 
            // traverse left
            prefix.append('0');
            printCodes(node.left, prefix);
            prefix.deleteCharAt(prefix.length()-1);
 
            // traverse right
            prefix.append('1');
            printCodes(node.right, prefix);
            prefix.deleteCharAt(prefix.length()-1);
        }
    }
    
    /* 
     * Método para retornar o código compactado de uma letra (w)
     *     Parâmetros de Entrada: tree - Raiz da Árvore de compactação
     *     						  prefix - texto codificado com 0 e/ou 1
     *     						  w - Letra
     *     Parâmetros de Saída: prefix- Letra codificada
     */
    public static String getCodes(HuffmanTree tree, StringBuffer prefix, char w) {
        assert tree != null;
        
        if (tree instanceof HuffmanLeaf) {
            HuffmanLeaf leaf = (HuffmanLeaf)tree;
            
            // Retorna o texto compactado da letra
            if (leaf.value == w ){
            	return prefix.toString();
            }
            
        } else if (tree instanceof HuffmanNode) {
            HuffmanNode node = (HuffmanNode)tree;
 
            // Percorre a esquerda
            prefix.append('0');
            String left = getCodes(node.left, prefix, w);
            prefix.deleteCharAt(prefix.length()-1);
 
            // Percorre a direita
            prefix.append('1');
            String right = getCodes(node.right, prefix,w);
            prefix.deleteCharAt(prefix.length()-1);
            
            if (left==null) return right; else return left;
        }
		return null;
    }
}
