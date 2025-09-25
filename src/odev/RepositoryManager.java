/**
*
* @author ARİF MUHAMMED , arif.muhammed@ogr.sakarya.edu.tr
* @since 05/04/2024
* <p>
* RepositoryManager Sinif
* </p>
*/


package odev;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class RepositoryManager {
    public void processRepository(String repoUrl) throws IOException {
        cloneRepository(repoUrl);
        analyzeClasses();
    }

    private static void deleteRepositoryIfExists() throws IOException {
        Path directory = Paths.get("repository");
        if (Files.exists(directory)) {
            Files.walk(directory)
                    .map(Path::toFile)
                    .sorted((o1, o2) -> -o1.compareTo(o2)) // sort in reverse order to delete files before directories
                    .forEach(File::delete);
        }
    }

    private static void cloneRepository(String repoUrl) throws IOException {
        deleteRepositoryIfExists(); // Call this method before starting the cloning process
        
        ProcessBuilder processBuilder = new ProcessBuilder("git", "clone", repoUrl, "repository");
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void analyzeClasses() throws IOException {
        JavaFileAnalyzer analyzer = new JavaFileAnalyzer();
        List<Path> javaFiles = getJavaFiles();
        for (Path file : javaFiles) {
            analyzer.analyzeJavaFile(file);
        }
    }

    private static List<Path> getJavaFiles() throws IOException {
        List<Path> javaFiles = new ArrayList<>();
        Files.walk(Paths.get("./repository"))
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(".java"))
                .forEach(path -> {
                    try {
                        String content = new String(Files.readAllBytes(path));
                        if (content.contains(" class ")) {
                            javaFiles.add(path);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        return javaFiles;
    }
}
