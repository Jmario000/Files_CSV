package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.entities.Product;

public class Program {

	public static void main(String[] args) {

		String sourceFileStr = "/home/jmario/Documents/csvFiles/productsSolded.csv";
		File sourceFile = new File(sourceFileStr);
		String folderPath = sourceFile.getParent();
		String summaryPath = folderPath + "/out/summary.csv";

		List<Product> list = new ArrayList<>();

		// Criar o diretório "out" se não existir
		File outDir = new File(folderPath + "/out");
		if (!outDir.exists()) {
			boolean dirCreated = outDir.mkdir();
			if (!dirCreated) {
				System.out.println("Erro ao criar diretório 'out'.");
				return; // Se o diretório não puder ser criado, sair do programa
			}
		}

		try (BufferedReader br = new BufferedReader(new FileReader(sourceFileStr));
				BufferedWriter bwSum = new BufferedWriter(new FileWriter(summaryPath))) {

			String line = br.readLine();
			while (line != null) {
				String[] campos = line.split(",");
				String name = campos[0];
				Double price = Double.parseDouble(campos[1]);
				int quantity = Integer.parseInt(campos[2]);
				list.add(new Product(name, price, quantity));

				line = br.readLine();
			}

			for (Product p : list) {
				bwSum.write(p.getName() + "," + String.format("%.2f", p.totalValue(p.getPrice(), p.getQuantity())));
				bwSum.newLine();
			}
			System.out.println("Resumo gerado com sucesso.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
