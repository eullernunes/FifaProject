import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class Kmp{


    public static List<Integer> buscarPadrao(RandomAccessFile arq, String padrao) throws IOException{
        String texto  = arq.readLine();
        int n = texto.length();
        int m = padrao.length();
        int comparacoes = 0;
        int[] lps = calcularLPSArray(padrao, comparacoes);
        System.out.println("Comparacoes "+comparacoes);
        List<Integer> ocorrencias = new ArrayList<>();

        int i = 0; // Índice para o texto
        int j = 0; // Índice para o padrão

        while (i < n) {
            comparacoes++;
            if (padrao.charAt(j) == texto.charAt(i)) {
                j++;
                i++;
                comparacoes++;
            }

            if (j == m) {
                // Ocorrência encontrada
                comparacoes++;
                ocorrencias.add(i - j);
                j = lps[j - 1];
            } else if (i < n && padrao.charAt(j) != texto.charAt(i)) {
                comparacoes++;
                if (j != 0) {
                    comparacoes++;
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }

        return ocorrencias;
    }

    private static int[] calcularLPSArray(String padrao, int comparacoes) {
        int m = padrao.length();
        int[] lps = new int[m];
        int len = 0; // Comprimento do prefixo sufixo mais longo

        lps[0] = 0; // lps[0] é sempre 0

        int i = 1;
        while (i < m) {
            comparacoes++;
            if (padrao.charAt(i) == padrao.charAt(len)) {
                comparacoes++;
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    comparacoes++;
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }

        return lps;
    }
}
