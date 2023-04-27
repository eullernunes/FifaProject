import java.util.Scanner;

public class Menu {
    public static Scanner sc = new Scanner(System.in);

    public void menu() throws Exception{
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
                lerCsv.lendoArquivo();
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

    public void exibeMenuJogadores() throws Exception{
        Crud crud = new Crud("Jogadores");
        Jogador jogador = new Jogador();
        int opcao = -1;
        int id = 0;

        

        while(opcao != 0){
            System.out.println("Digite a opção desejada:");
            System.out.println("1- Criar jogador");
            System.out.println("2- Pesquisar jogador");
            System.out.println("3- Alterar jogador");
            System.out.println("4- Deletar Jogador");
            System.out.println("0) Sair");

            opcao = sc.nextInt();
            switch(opcao) {        
                case 1:
                    id = crud.idCabecalho();
                    jogador = crud.create(id);
                    crud.create(jogador);
                    break;
                case 2:
                    System.out.println("\nDigite o id do jogador que deseja pesquisar: ");
                    id = sc.nextInt();
                    try{
                        jogador = crud.read(id);
                        if(jogador == null){
                            System.out.println("Jogador não encontrado!");
                        }else{
                            System.out.println(jogador.toString());
                        }
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                    break;

                case 3:
                    System.out.println("Digite o id que deseja alterar: ");
                    id = sc.nextInt();
                    jogador = crud.create(id);

                    try{

                        if(crud.update(jogador)){
                            System.out.println("Jogador atualizado com sucesso!");
                        } else {
                            System.out.println("Erro ao atualizar");
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    break;

                case 4:
                    System.out.println("\nDigite o id do jogador que deseja deletar: ");
                    id = sc.nextInt();

                    try{
                        if(crud.delete(id)){
                            System.out.println("Registro deletado com sucesso!");
                        }else{
                            System.out.println("Jogador não encontrado!");
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    break;

                case 5:
                    //Ordenacao.intercalacaoBalanceada2(0);
                    break;

                default:
                    System.out.println("Digite uma opção válida!!");
            }
        }

    }

    
}
    

