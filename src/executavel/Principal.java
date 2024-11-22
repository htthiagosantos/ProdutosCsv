package executavel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import entidades.Product;


public class Principal {

	public static void main(String[] args) {

		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		System.out.print("Der entrada no caminho do arquivo: ");
		String caminho = sc.nextLine();
		
		try (BufferedReader br = new BufferedReader(new FileReader(caminho))){
			
			List<Product> lista = new ArrayList<>();
			
			String line = br.readLine(); // Vou ler uma linha
			while(line != null) {
				String[] campos = line.split(","); // Split vai recotar a linha em dois com base na virgula
				lista.add(new Product(campos[0], Double.parseDouble(campos[1])));
				line = br.readLine();
			}
			
			double media = lista.stream()
					.map(p -> p.getPrice())
					.reduce(0.0, (x,y) -> x + y) / lista.size();
			
			System.out.println("Media dos valores: " + String.format("%.2f", media));
			
			// Comparador de Strings independente de letras maiusculas ou minusculas
			Comparator<String> comp = (s1, s2) -> s1.toUpperCase().compareTo(s2.toUpperCase());
			
			List<String> nomes = lista.stream()
					.filter(p -> p.getPrice() < media) // Filtrar os produtos que estão menor que a media
					.map(p -> p.getName()) // Pegar o nome de cada produto
					.sorted(comp.reversed()) // Ordem decrescente alfabetica
					.collect(Collectors.toList());
			
			nomes.forEach(System.out::println);
			
			
		}catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
		
		
		sc.close();
	}
}