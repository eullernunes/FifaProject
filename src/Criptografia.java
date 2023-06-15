public class Criptografia {
    
    public static String criptografarCesar(String texto, int chave) {
        StringBuilder textoCriptografado = new StringBuilder();
        for (int i = 0; i < texto.length(); i++) {
            char caractere = texto.charAt(i);
            if (Character.isLetter(caractere)) {
                char base = Character.isLowerCase(caractere) ? 'a' : 'A';
                int deslocamento = (caractere - base + chave) % 26;
                char novoCaractere = (char) (base + deslocamento);
                textoCriptografado.append(novoCaractere);
            } else {
                textoCriptografado.append(caractere);
            }
        }
        return textoCriptografado.toString();
    }
    
    public static String descriptografarCesar(String textoCriptografado, int chave) {
        return criptografarCesar(textoCriptografado, 26 - chave);
    }
}