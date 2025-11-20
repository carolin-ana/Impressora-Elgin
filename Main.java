import com.sun.jna.Library;
import com.sun.jna.Native;
import java.util.Scanner;
import javax.swing.JFileChooser;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.io.FileInputStream;

public class Main {

    // Interface que representa a DLL, usando JNA
    public interface ImpressoraDLL extends Library {
		
		// Caminho completo para a DLL
    ImpressoraDLL INSTANCE = (ImpressoraDLL) Native.load(
                "C:\\Users\\vieira_carolina\\Downloads\\Java-Aluno Graduacao\\Java-Aluno Graduacao\\E1_Impressora01.dll",
                ImpressoraDLL.class
        );
        
	
	    private static String lerArquivoComoString(String path) throws IOException {
        FileInputStream fis = new FileInputStream(path);
        byte[] data = fis.readAllBytes();
        fis.close();
        return new String(data, StandardCharsets.UTF_8);
    }
	
        
        int AbreConexaoImpressora(int tipo, String modelo, String conexao, int parametro);
        int FechaConexaoImpressora();
        int ImpressaoTexto(String dados, int posicao, int estilo, int tamanho);
        int Corte(int avanco);
        int ImpressaoQRCode(String dados, int tamanho, int nivelCorrecao);
        int ImpressaoCodigoBarras(int tipo, String dados, int altura, int largura, int HRI);
        int AvancaPapel(int linhas);
        int StatusImpressora(int param);
        int AbreGavetaElgin();
        int AbreGaveta(int pino, int ti, int tf);
        int SinalSonoro(int qtd, int tempoInicio, int tempoFim);
        int ModoPagina();
        int LimpaBufferModoPagina();
        int ImprimeModoPagina();
        int ModoPadrao();
        int PosicaoImpressaoHorizontal(int posicao);
        int PosicaoImpressaoVertical(int posicao);
        int ImprimeXMLSAT	(String dados, int param);
        int ImprimeXMLCancelamentoSAT(String dados, String assQRCode, int param);
    }

    private static boolean conexaoAberta = false;
    private static int tipo;
    private static String modelo;
    private static String conexao;
    private static int parametro;
	
    private static final Scanner scanner = new Scanner(System.in);

    private static String capturarEntrada(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine();
    }

    public static void configurarConexao() {
        if (!conexaoAberta) {
            System.out.println("Digite o tipo de comunicação: \n" +
                    "1 - USB\n" +
                    "2 - RS232\n" +
                    "3 - TCP/IP\n" +
                    "4 - Bluetooth\n" +
                    "5 - Impressoras acopladas (Android)\n");
            tipo = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Digite o modelo da impressora: ");
            modelo = scanner.nextLine();

            System.out.println("Digite o tipo de conexão: ");
            conexao = scanner.nextLine();

            System.out.println("Digite o parâmetro: ");
            parametro = scanner.nextInt();


            System.out.println("Conexão configurada com sucesso!");

        } else {
            System.out.println("Conexão já aberta.");
        }
    }

    public static void abrirConexao() {
        if (!conexaoAberta) {
            int ret = ImpressoraDLL.INSTANCE.AbreConexaoImpressora(tipo,modelo,conexao,parametro);

            if (ret == 0) {
                conexaoAberta = true;
                System.out.println("Conexão aberta com sucesso.");
            } else {
                System.out.println("Erro ao abrir conexão. Código de erro: " + ret);
            }
        } else {
            System.out.println("Conexão já aberta");
        }
    }

    public static void fecharConexao() {
        if (conexaoAberta) {
            ImpressoraDLL.INSTANCE.FechaConexaoImpressora();

            conexaoAberta = false;
            System.out.println("Conexão fechada com sucesso.");

        } else {
            System.out.println("Conexão já está fechada.");
        }
    }

    public static void ImpressaoTexto() {
        ImpressoraDLL.INSTANCE.AvancaPapel(2);

        if (conexaoAberta) {
            int ret = ImpressoraDLL.INSTANCE.ImpressaoTexto("Teste de impressao", 1, 4, 0);

            if (ret == 0) {
                System.out.println("Teste de impressão ok");
            } else {
                System.out.println("Erro. Retorno " + ret);
            }
        } else {
            System.out.println("Abra conexão primeiro");
        }
        ImpressoraDLL.INSTANCE.AvancaPapel(2);
        ImpressoraDLL.INSTANCE.Corte(2);
    }

    public static void ImpressaoQRCode() {
        ImpressoraDLL.INSTANCE.AvancaPapel(2);

        if (conexaoAberta) {
            int ret = ImpressoraDLL.INSTANCE.ImpressaoQRCode("Teste de impressao", 6, 4);

            if (ret == 0) {
                System.out.println("Teste de impressão ok");
            } else {
                System.out.println("Erro. Retorno " + ret);
            }
        } else {
            System.out.println("Abra conexão primeiro");
        }
        ImpressoraDLL.INSTANCE.AvancaPapel(2);
        ImpressoraDLL.INSTANCE.Corte(2);
    }

    public static void ImpressaoCodigoBarras() {
        ImpressoraDLL.INSTANCE.AvancaPapel(2);

        if (conexaoAberta) {
            int ret = ImpressoraDLL.INSTANCE.ImpressaoCodigoBarras(8, "{A012345678912", 100, 2, 3);

            if (ret == 0) {
                System.out.println("Teste de impressão ok");
            } else {
                System.out.println("Erro. Retorno " + ret);
            }

        } else {
            System.out.println("Abra conexão primeiro");
        }
        ImpressoraDLL.INSTANCE.AvancaPapel(2);
        ImpressoraDLL.INSTANCE.Corte(2);
    }

    public static void ImprimeXMLSAT() {
        ImpressoraDLL.INSTANCE.AvancaPapel(2);

        if (conexaoAberta) {

            String dados  = "path=C:\\Users\\vieira_carolina\\Downloads\\Java-Aluno Graduacao\\Java-Aluno Graduacao\\XMLSAT.xml";

            int ret = ImpressoraDLL.INSTANCE.ImprimeXMLSAT(dados, 0);

            if (ret == 0 ) {

                System.out.println("Impressao realizada com sucesso");

            } else {

                System.out.println("Erro de impressao: " + ret);

            }

        } else {

            System.out.println("Abra a conexão com a impressora ");
        }

        ImpressoraDLL.INSTANCE.AvancaPapel(2);
        ImpressoraDLL.INSTANCE.Corte(2);

    }

    public static void ImprimeXMLCancelamentoSAT() {
        ImpressoraDLL.INSTANCE.AvancaPapel(2);

        if (conexaoAberta) {

            //String assQRCode = "path=C:\\Users\\dennys_oliveira\\Downloads\\Java-Aluno Graduacao\\Java-Aluno Graduacao\\assQRCode.txt";

            String assQRCode =
            "Q5DLkpdRijIRGY6YSSNsTWK1TztHL1vD0V1Jc4spo/CEUqICEb9SFy82ym8EhBRZjbh3btsZhF+sjHqEMR159i4agru9x6KsepK/q0E2e5xlU5cv3m1woYfgHyOkWDNcSdMsS6bBh2Bpq6s89yJ9Q6qh/J8YHi306ce9Tqb/drKvN2XdE5noRSS32TAWuaQEVd7u+TrvXlOQsE3fHR1D5f1saUwQLPSdIv01NF6Ny7jZwjCwv1uNDgGZONJdlTJ6p0ccqnZvuE70aHOI09elpjEO6Cd+orI7XHHrFCwhFhAcbalc+ZfO5b/+vkyAHS6CYVFCDtYR9Hi5qgdk31v23w==";

            String dados = "path=C:\\Users\\vieira_carolina\\Downloads\\Java-Aluno Graduacao\\Java-Aluno Graduacao\\CANC_SAT.xml";

            int ret = ImpressoraDLL.INSTANCE.ImprimeXMLCancelamentoSAT(dados, assQRCode, 0);

            if (ret == 0) {

                System.out.println("Impressao realizada com sucesso");

            } else {

                System.out.println("Erro de impressao: " + ret);
            }


        } else {
            System.out.println("Abra a conexão com a impressora");
        }

        ImpressoraDLL.INSTANCE.AvancaPapel(2);
        ImpressoraDLL.INSTANCE.Corte(2);

    }

    public static void AbreGavetaElgin() {
       if (conexaoAberta) {
           int ret = ImpressoraDLL.INSTANCE.AbreGavetaElgin();

           if (ret == 0) {
               System.out.println("Gaveta funcionando");
           } else {
               System.out.println("Erro.Código: " + ret);
           }

       } else {
           System.out.println("Abra a conexão com a impressora");
       }

    }

    public static void AbreGaveta() {
       if (conexaoAberta) {
           int ret = ImpressoraDLL.INSTANCE.AbreGaveta(1, 50, 10);

           if (ret ==0) {
               System.out.println("Gaveta funcionando");
           } else {
               System.out.println("Erro.Código: " + ret);
           }
       } else {
           System.out.println("Abra a conexão com a impressora");
       }

    }

    public static void SinalSonoro() {
        if (conexaoAberta) {
            int ret = ImpressoraDLL.INSTANCE.SinalSonoro(4, 5, 5);

            if (ret ==0) {
                System.out.println("Sinal sonoro ok ");
            } else {
                System.out.println("Erro.Código: " + ret);
            }
        } else {
            System.out.println("Abra a conexão com a impressora");
        }

    }


    public static void main(String[] args) {
        while (true) {
            System.out.println("\n*************************************************");
            System.out.println("**************** MENU IMPRESSORA *******************");
            System.out.println("*************************************************\n");

            System.out.println("1  - Configurar Conexao");
            System.out.println("2  - Abrir Conexao");
            System.out.println("3  - Impressao Texto");
            System.out.println("4  - Impressao QRCode");
            System.out.println("5  - Impressao Cod Barras");
            System.out.println("6  - Impressao XML SAT");
            System.out.println("7  - Impressao XML Canc SAT");
            System.out.println("8  - Abrir Gaveta Elgin");
            System.out.println("9  - Abrir Gaveta");
            System.out.println("10 - Sinal Sonoro");
            System.out.println("0  - Fechar Conexao e Sair");
            System.out.println("--------------------------------------");

            String escolha = capturarEntrada("\nDigite a opção desejada: ");

            if (escolha.equals("0")) {
                fecharConexao();
                return;
            }

            switch (escolha) {
                case "1":
                    configurarConexao();
                    break;

                case "2":
                    abrirConexao();
                    break;

                case "3":
                    ImpressaoTexto();

                    break;
                   
                case "4":
                    ImpressaoQRCode();

                    break;

                case "5":
                    ImpressaoCodigoBarras();

                    break;
                    
                case "6":
                    ImprimeXMLSAT();
                    break;

                case "7":
                    ImprimeXMLCancelamentoSAT();
                    break;
                    
                case "8":
                    AbreGavetaElgin();
                    break;
                    
                case "9":
                	AbreGaveta();
                    break;
                    
                case "10":
                    SinalSonoro();
                    break;

                default:
                    
            }
        }

        //scanner.close();
    }


	
}
