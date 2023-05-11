public class HuffmanLeaf extends HuffmanTree {
    public final char value; //valor do caractere

    public HuffmanLeaf(int freq, char val){
        super(freq);
        value = val;
    }
}