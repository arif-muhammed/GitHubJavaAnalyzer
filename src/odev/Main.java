/**
*
* @author ARİF MUHAMMED , arif.muhammed@ogr.sakarya.edu.tr
* @since 05/04/2024
* <p>
* Main Sinif
* </p>
*/

package odev;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("URL giriniz:");
            String repoUrl = reader.readLine();
            RepositoryManager repositoryManager = new RepositoryManager();
            repositoryManager.processRepository(repoUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
