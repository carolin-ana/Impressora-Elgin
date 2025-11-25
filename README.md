# Impressora-Elgin

Este projeto demonstra como integrar aplicações Java com impressoras utilizando uma DLL nativa e a biblioteca JNA (Java Native Access).
A aplicação oferece um menu interativo no terminal, permitindo executar operações de impressão, status, abertura de gaveta e leitura de XML SAT.
O sistema permite que o usuário:

 - Abra e feche conexões com impressoras (USB, RS232, TCP/IP, Bluetooth ou impressoras embarcadas em Android);
- Execute comandos de impressão de texto, códigos de barras e QR Code;
- Controle funções da impressora, como corte, avanço de papel e alerta sonoro;
- Carregue arquivos externos (como XML ou texto) para impressão;
- Realize testes e diagnósticos básicos.


🚀 Funcionalidades

🔧 Conexão
- Configurar parâmetros de comunicação
- Abrir conexão com a impressora
- Fechar conexão


🖨 Impressão
- Impressão de texto
- Impressão de QRCode
- Impressão de código de barras
- Impressão de XML SAT
- Impressão de XML de cancelamento SAT


💵 Hardware
- Abertura de gaveta Elgin
- Abertura de gaveta via pino
- Sinal sonoro da impressora


📚 Tecnologias utilizadas

- Java
- JNA (Java Native Access) – para carregar e chamar funções da DLL
- I/O Java – leitura de arquivos
- DLL proprietária – E1_Impressora01.dll


🗂️ Estrutura Geral
1. Interface ImpressoraDLL

Define as funções importadas da DLL usando JNA.

public interface ImpressoraDLL extends Library {
    ImpressoraDLL INSTANCE = (ImpressoraDLL) Native.load(
        "Diretório do arquivo DLL",
        ImpressoraDLL.class
    );
}


Através dessa interface, o Java pode chamar funções escritas em C/C++ presentes na DLL.

🔌 2. Abertura de Conexão

Método chamado antes de qualquer impressão.

Função da DLL:

int AbreConexaoImpressora(int tipo, String modelo, String conexao, int parametro);


O sistema solicita ao usuário:
- Tipo de comunicação (USB, RS232, TCP/IP, Bluetooth, Android)
- Modelo da impressora
- Tipo de conexão
- Parâmetros adicionais (porta, IP, baud rate etc.)
- Se o retorno for 0, a conexão foi aberta com sucesso.
- Caso a conexão não seja estabelecida, retorna o código de erro. Veja documentação: [LINK]

❌ 3. Fechamento da Conexão

Chamado após a finalização das operações:

int FechaConexaoImpressora();

📝 4. Impressão de Texto

O texto pode ser digitado ou carregado de arquivo:

Função responsável:
ImpressoraDLL.INSTANCE.ImpressaoTexto(texto, alinhamento, estilo, tamanho);


📦 5. Impressão de Arquivo (ex.: XML, texto)

Arquivo é lido:

private static String lerArquivoComoString(String path)


Conteúdo impresso pela DLL.

🧾 6. Impressão de Código de Barras

Funções para código de barras:

ImpressoraDLL.INSTANCE.ImpressaoCodigoBarras(codigo, tipo, altura, largura, HRI);

🔳 7. Impressão de QR Code

Função típica:

ImpressoraDLL.INSTANCE.ImpressaoQRCode(conteudo, tamanho, correcao);

🔔 8. Sinal Sonoro

Chama a função da DLL para emitir aviso sonoro:

ImpressoraDLL.INSTANCE.SinalSonoro();

🔍 9. Testes e Diagnósticos

O código possui opções para:
- Teste de impressão rápida
- Verificação de status
- Avanço de papel
- Corte

Sempre verificando o retorno da DLL:

if (ret == 0) System.out.println("Ok");
else System.out.println("Erro. Retorno: " + ret);

📌 Fluxo Geral do Programa

- Usuário escolhe o tipo de comunicação
- Abre conexão com a DLL
  
Seleciona tipo de operação:
- Imprimir texto
- Imprimir arquivo
- Código de barras
- QR Code
- Corte
- Avanço
- Sinal sonoro
- Realiza operação
- Fecha conexão

📈 Pontos Importantes

- Nenhuma função da DLL pode ser chamada sem antes abrir conexão.
- Cada comando retorna um código de status, onde 0 = sucesso.
- O sistema utiliza Scanner para entrada de dados no console.
- Alguns recursos são dependentes do modelo da impressora.
- Ajuste o caminho para o local correto da DLL em seu computador.
- Algumas chamadas atualmente usam valores fixos como teste.
- A DLL deve ser compatível com seu modelo de impressora.

▶️ Como executar
1. Compile o projeto:
javac Main.java


2. Execute:
java Main


3. Use o menu:
1  - Configurar Conexao
2  - Abrir Conexao
3  - Impressao Texto
4  - Impressao QRCode
5  - Impressao Cod Barras
6  - Impressao XML SAT
7  - Impressao XML Canc SAT
8  - Abrir Gaveta Elgin
9  - Abrir Gaveta
10 - Sinal Sonoro
0  - Fechar Conexao e Sair


📝 Autores

Projeto para fins acadêmicos, estudo de integração Java ↔ DLL através de JNA.
- Ana Carolina 223642
- Dennys Oliveira 053283
- Yasmin Gabrielly 078013
