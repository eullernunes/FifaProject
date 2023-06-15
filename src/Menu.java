import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.Scanner;

public class Menu {
    public static Scanner sc = new Scanner(System.in);
    static final String jogadoresFile = "dados/db/Jogadores.db";
    static final String csvFile = "dados/csv/Fifa 23 Players Data.csv";
    static final String txtFile = "dados/txt/arquivo.txt";
    static final String huffmanCode = "dados/txt/huffmanCode.txt";
    static final String compressedOutputFileLZW = jogadoresFile + "_LzwCompressv1.db";
    static final String decompressedOutputFileLZW = jogadoresFile + "_LzwDecompressed.db";
    static final String compressedOutputFileHuffman = jogadoresFile + "_HuffmanCompressV1.bin";
    static final String decompressedOutputFileHuffman = jogadoresFile + "_HuffmanDecompressV1.txt";

    public void menu() throws Exception {
        int opcao = -1;

        System.out.println("-------------------------------------------------------------");
        System.out.println("|                       FIFA DATASET                        |");
        System.out.println("-------------------------------------------------------------");

        System.out.println("\n---------- Menu ----------");
        System.out.println("Digite a opção desejada:");
        System.out.println("1)Carregar arquivo csv!");
        System.out.println("2)Acessar Menu Jogadores");
        System.out.println("0)Sair\n");
        opcao = sc.nextInt();

        switch (opcao) {
            case 1:
                LeCsv lerCsv = new LeCsv();
                lerCsv.lendoArquivo(csvFile);
                break;
            case 2:
                exibeMenuJogadores();
                break;
            case 0:
                System.out.println("Saindo...");
                break;
            default:
                System.out.println("Selecione uma opção válida!!");
                menu();
                break;
        }

    }

    public void exibeMenuJogadores() throws Exception {
        Crud crud = new Crud(jogadoresFile);
        Jogador jogador = new Jogador();
        int opcao = -1;
        int id = 0;
        int op = -1;

        while (opcao != 0) {
            System.out.println("-------------------------------------------------------------");
            System.out.println("|                       FIFA DATASET                        |");
            System.out.println("-------------------------------------------------------------");
            System.out.println("Digite a opção desejada:");
            System.out.println("1- Criar jogador");
            System.out.println("2- Pesquisar Registro");
            System.out.println("3- Alterar Registro");
            System.out.println("4- Deletar Registro");
            System.out.println("5- Ordenar Registros ");
            System.out.println("6- Huffman");
            System.out.println("7- LZW");
            System.out.println("8- Boyer Moore");
            System.out.println("9- KMP");
            System.out.println("0) Sair");
            opcao = sc.nextInt();
            switch (opcao) {
                case 0:
                    System.out.println("Saindo...");
                    break;
                case 1:
                    id = crud.idCabecalho();
                    jogador = crud.create(id);
                    crud.create(jogador);
                    break;
                case 2:
                    System.out.println("\nDigite o id do jogador que deseja pesquisar: ");
                    id = sc.nextInt();
                    try {
                        jogador = crud.read(id);
                        if (jogador == null) {
                            System.out.println("Jogador não encontrado!");
                        } else {
                            System.out.println(jogador.toString());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case 3:
                    System.out.println("Digite o id que deseja alterar: ");
                    id = sc.nextInt();
                    jogador = crud.create(id);

                    try {

                        if (crud.update(jogador)) {
                            System.out.println("Jogador atualizado com sucesso!");
                        } else {
                            System.out.println("Erro ao atualizar");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case 4:
                    System.out.println("\nDigite o id do jogador que deseja deletar: ");
                    id = sc.nextInt();

                    try {
                        if (crud.delete(id)) {
                            System.out.println("Registro deletado com sucesso!");
                        } else {
                            System.out.println("Jogador não encontrado!");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case 5:
                    System.out.println("Ordenação ainda não implementada");
                    break;

                case 6:

                    System.out.println("****** HUFFMAN ******");
                    System.out.println("1 - Compactar");
                    System.out.println("2 - Descompactar");
                    System.out.println("3 - Sair");

                    op = sc.nextInt();

                    if (op == 1) {
                        Huffman.compressFile(jogadoresFile, compressedOutputFileHuffman, huffmanCode);
                    } else if (op == 2) {
                        Huffman.decompressFile(compressedOutputFileHuffman, huffmanCode, decompressedOutputFileHuffman);
                    }

                    break;
                case 7:
                    System.out.println("****** LZW ******");
                    System.out.println("1 - Compactar");
                    System.out.println("2 - Descompactar");
                    System.out.println("3 - Sair");
                    op = sc.nextInt();

                    if (op == 1) {
                        try {
                            long startTime = System.currentTimeMillis();

                            Lzw.compress(jogadoresFile, compressedOutputFileLZW);

                            long endTime = System.currentTimeMillis();
                            long lzwExecutionTime = endTime - startTime;

                            // Mostrar os resultados para o usuário
                            File originalFile = new File(jogadoresFile);
                            File compressedFileLZW = new File(compressedOutputFileLZW);

                            double lzwCompressionRatio = (double) compressedFileLZW.length() / originalFile.length()
                                    * 100;

                            System.out.println("Resultados da compressão:");
                            System.out.println("LZW - Taxa de compressão: " + lzwCompressionRatio + "%");

                            System.out.println("Tempo de execução da compressão:");
                            System.out.println("LZW: " + lzwExecutionTime + "ms");

                        } catch (IOException e) {
                            System.out.println("Erro ao processar o arquivo: " + e.getMessage());
                        }
                    } else if (op == 2) {

                        long startTime = System.currentTimeMillis();

                        try {
                            System.out.println("Realizando descompressão...");
                            Lzw.decompress(compressedOutputFileLZW, decompressedOutputFileLZW);
                            System.out.println("Arquivo descompactado com sucesso: " + decompressedOutputFileLZW);
                        } catch (IOException e) {
                            System.out.println("Erro ao descompactar o arquivo: " + e.getMessage());
                        }

                        long endTime = System.currentTimeMillis();

                        long lzwDecompressionTime = endTime - startTime;
                        System.out.println("Tempo de execução da descompressão LZW: " + lzwDecompressionTime + "ms");

                    }

                    break;

                case 8:
                    crud.clearBuffer(sc);
                    System.out.print("Texto a procurar: ");
                    sc.nextLine();
                    String texto = sc.nextLine();
                    RandomAccessFile raf = new RandomAccessFile(txtFile, "rw");

                    long startTime = System.currentTimeMillis();
                    int res = BoyerMoore.encontrar(texto, raf);
                    long estimatedTime = System.currentTimeMillis() - startTime;
                    System.out.println("Número de comparacoes: " + res);
                    System.out.println("Tempo: " + estimatedTime + " ms");

                    break;

                case 9:

                    crud.clearBuffer(sc);
                    ConverteArquivo.createTxt(jogadoresFile);
                    System.out.print("Texto a procurar: ");
                    texto = sc.nextLine();
                    raf = new RandomAccessFile(txtFile, "rw");

                    startTime = System.currentTimeMillis();
                    List<Integer> ocorrencias = Kmp.buscarPadrao(raf, texto);

                    System.out.println("Total de ocorrências encontradas: " + ocorrencias.size());
                    System.out.println("Posições encontradas: " + ocorrencias);
                    estimatedTime = System.currentTimeMillis() - startTime;
                    // System.out.println("Número de comparacoes: " + res);
                    // System.out.println("Tempo: " + estimatedTime+ " ms");

                    break;
                case 10:

                    ConverteArquivo.createTxt(jogadoresFile);

                    break;
                default:
                    System.out.println("Digite uma opção válida!!");
            }

            System.out.println("\n\n1- Voltar ao menu anterior:");
            System.out.println("0- Sair");
            opcao = sc.nextInt();
        }

    }

}
