abstract class HuffmanTree implements Comparable<HuffmanTree> {
    public final int frequency; //frequência da árvore

    public HuffmanTree(int freq){
        frequency = freq;
    }

    //compara a frequência com outra árvore
    public int compareTo(HuffmanTree tree){
        return frequency - tree.frequency;
    }
}