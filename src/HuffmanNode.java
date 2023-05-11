public class HuffmanNode extends HuffmanTree {
    public final HuffmanTree left, right; //sub-árvores

    //atribui as árvores como esquerda e direita
    public HuffmanNode(HuffmanTree l, HuffmanTree r){
        super(l.frequency + r.frequency);
        left = l;
        right = r;
    }
}