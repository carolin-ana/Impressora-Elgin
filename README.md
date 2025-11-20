# Impressora-Elgin

Este projeto demonstra como integrar aplicações Java com impressoras utilizando uma DLL nativa e a biblioteca JNA (Java Native Access).
A aplicação oferece um menu interativo no terminal, permitindo executar operações de impressão, status, abertura de gaveta e leitura de XML SAT.


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

- Java 8+
- JNA – Java Native Access
- Arquivo DLL nativo da impressora
- Input via Scanner


📁 Estrutura do projeto
Main.java
│
└── Interface ImpressoraDLL  → Mapeamento da DLL via JNA
└── Métodos de impressão      → Texto, QRCode, código de barras, XML
└── Controle de conexão       → Abrir/fechar e configurar
└── Funções adicionais        → Abrir gaveta, beep, etc.
└── Menu interativo


🔌 Configuração da DLL

A DLL é carregada diretamente via JNA:

ImpressoraDLL INSTANCE = (ImpressoraDLL) Native.load(
    "C:\\caminho\\para\\E1_Impressora01.dll",
    ImpressoraDLL.class
);


👉 Importante: Ajuste o caminho para o local correto da DLL em seu computador.



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


🧩 Exemplos de uso
Impressão de texto
ImpressoraDLL.INSTANCE.ImpressaoTexto("Teste de impressao", 1, 4, 0);


Impressão de QRCode
ImpressoraDLL.INSTANCE.ImpressaoQRCode("Texto do QRCode", 6, 4);


Impressão de XML SAT
int ret = ImpressoraDLL.INSTANCE.ImprimeXMLSAT("path=C:\\XMLSAT.xml", 0);


⚠️ Observações importantes

- Algumas chamadas atualmente usam valores fixos como teste.
- A DLL deve ser compatível com seu modelo de impressora.


📝 Autor

Projeto para fins acadêmicos, estudo de integração Java ↔ DLL através de JNA.
