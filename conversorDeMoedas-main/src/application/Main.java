package application;

import com.google.gson.Gson;
import models.Conversor;
import models.GeradorDeArquivos;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        Conversor conversor = new Conversor();
        GeradorDeArquivos gerador = new GeradorDeArquivos();

        while (true) {
            System.out.println("----------------------------------------------------------");
            System.out.println("Seja bem-vindo(a) ao Conversor de Moeda =]");
            System.out.println("1-) Dólar =>> Peso argentino");
            System.out.println("2-) Peso argentino =>> Dólar");
            System.out.println("3-) Dólar =>> Real brasileiro");
            System.out.println("4-) Real brasileiro =>> Dólar");
            System.out.println("5-) Dólar =>> Peso colombiano");
            System.out.println("6-) Peso colombiano =>> Dólar");
            System.out.println("7-) Histórico de buscas");
            System.out.println("8-) Sair");
            System.out.println("Escolha uma opção válida: ");
            System.out.println("----------------------------------------------------------");
            int op = sc.nextInt();

            if (op >= 1 && op <= 6) {
                try {
                    System.out.println("Digite um valor que deseja converter: ");
                    double valor = sc.nextDouble();

                    String moedaOrigem = "";
                    String moedaDestino = "";

                    switch (op) {
                        case 1:
                            moedaOrigem = "USD";
                            moedaDestino = "ARS";
                            break;
                        case 2:
                            moedaOrigem = "ARS";
                            moedaDestino = "USD";
                            break;
                        case 3:
                            moedaOrigem = "USD";
                            moedaDestino = "BRL";
                            break;
                        case 4:
                            moedaOrigem = "BRL";
                            moedaDestino = "USD";
                            break;
                        case 5:
                            moedaOrigem = "USD";
                            moedaDestino = "COP";
                            break;
                        case 6:
                            moedaOrigem = "COP";
                            moedaDestino = "USD";
                            break;
                    }

                    double valorConvertido = conversor.converterMoeda(moedaOrigem, moedaDestino, valor).
                            conversion_result();
                    System.out.printf("O valor %.5f %s corresponde ao valor final de %.5f %s%n",
                            valor, moedaOrigem, valorConvertido, moedaDestino);

                    gerador.salvarJson(valorConvertido, moedaOrigem, moedaDestino);
                } catch (InputMismatchException e) {
                    System.out.println("Por favor, insira um valor válido.");
                }
            } else if (op == 7) {
                exibirHistorico();
            } else if (op == 8) {
                System.out.println("Cancelando Operação...");
                break;
            } else {
                System.out.println("Opção inválida! Cancelando Operação...");
                break;
            }
        }
    }

    private static void exibirHistorico() throws IOException {
        File diretorio = new File("historico");
        File[] arquivos = diretorio.listFiles((dir, nome) -> nome.endsWith(".json"));

        if (arquivos != null && arquivos.length > 0) {
            for (File arquivo : arquivos) {
                try (FileReader reader = new FileReader(arquivo)) {
                    GeradorDeArquivos.Dados dados = new Gson().fromJson(reader, GeradorDeArquivos.Dados.class);
                    System.out.printf("Data: %s, Valor: %.5f %s => %s%n",
                            dados.getData(), dados.getMoeda(), dados.getValor_origem(), dados.getValor_destino());
                }
            }
        } else {
            System.out.println("Histórico de buscas não encontrado.");
        }
    }
}
