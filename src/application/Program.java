package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Program {

    public static void main(String[] args) {

        String[] products = new String[] { "TV LED,1290.99,1", "Video Game Chair,350.50,3", "Iphone X,900.00,2",
                "Samsung Galaxy 9,850.00,2" };
        String inputPath = "/home/jmario/Documents/csvFiles/productsSolded.csv";
        String folderPath = "/home/jmario/Documents/csvFiles/";
        String summaryPath = folderPath + "out/summary.csv";

        // Criar o diretório "out" se não existir
        File outDir = new File(folderPath + "out");
        if (!outDir.exists()) {
            boolean dirCreated = outDir.mkdir();
            if (!dirCreated) {
                System.out.println("Erro ao criar diretório 'out'.");
                return; // Se o diretório não puder ser criado, sair do programa
            }
        }

        // Escrever os produtos no arquivo de entrada
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(inputPath))) {
            for (String product : products) {
                bw.write(product);
                bw.newLine();
            }
            System.out.println("Produtos escritos no arquivo de entrada.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Ler o arquivo e gerar o resumo
        try (BufferedReader br = new BufferedReader(new FileReader(inputPath));
             BufferedWriter bwSum = new BufferedWriter(new FileWriter(summaryPath))) {

            String line = br.readLine();
            while (line != null) {
                String[] campos = line.split(",");
                String name = campos[0];
                Double price = Double.parseDouble(campos[1]);
                int quantity = Integer.parseInt(campos[2]);
                double totalValue = price * quantity;

                // Escrever o nome e o valor total no arquivo de resumo
                bwSum.write(name + "," + String.format("%.2f", totalValue));
                bwSum.newLine();

                // Ler a próxima linha
                line = br.readLine();
            }
            System.out.println("Resumo gerado com sucesso.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
