/**
*
* @author ARİF MUHAMMED , arif.muhammed@ogr.sakarya.edu.tr
* @since 05/04/2024
* <p>
* JavaFileAnalyzer Sinif
* </p>
*/

package odev;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaFileAnalyzer {

    public void analyzeJavaFile(Path file) throws IOException {
        String className = file.getFileName().toString();
        int javadocLines = countJavadocLines(file);
        int otherCommentLines = countOtherCommentLines(file);
        int codeLines = countCodeLines(file);
        int loc = countLOC(file);
        int functionCount = countFunctionCount(file);
        double commentDeviation = calculateCommentDeviation(javadocLines, otherCommentLines, functionCount, codeLines);

        System.out.println("-----------------------------------------");
        System.out.println("Sınıf: " + className);
        System.out.println("Javadoc Satır Sayısı: " + javadocLines);
        System.out.println("Yorum Satır Sayısı: " + otherCommentLines);
        System.out.println("Kod Satır Sayısı: " + codeLines);
        System.out.println("LOC: " + loc);
        System.out.println("Fonksiyon Sayısı: " + functionCount);
        System.out.println("Yorum Sapma Yüzdesi: %" + String.format("%.2f", commentDeviation));
    }

    private static int countJavadocLines(Path file) throws IOException {
        int javadocLines = 0;
       boolean inJavadocComment = false;

       try (BufferedReader reader = Files.newBufferedReader(file)) {
           String line;
           while ((line = reader.readLine()) != null) {
               if (line.contains("/**")) {
                   inJavadocComment = true;
               } else if (line.contains("*/") && inJavadocComment) {
                   inJavadocComment = false;
               } else if (inJavadocComment) {              	
                   javadocLines++;
               }
               
           }
       }
       return javadocLines;
   }
   private static int countOtherCommentLines(Path file) throws IOException {
        int count = 0;
       boolean inBlockComment = false;

       try (BufferedReader reader = Files.newBufferedReader(file)) {
           String line;
           while ((line = reader.readLine()) != null) {
               if (!inBlockComment && line.trim().matches(".*//.*")) {
                   count++; 
               } else if (line.contains("/*") && !line.contains("/**")) {
                   inBlockComment = true;
               } else if (line.contains("*/")) {
                   inBlockComment = false;
               } else if (inBlockComment && !line.trim().startsWith("/**")) {
               	count++; 
               }
           }
       }
       return count;
   }

   private static int countCodeLines(Path file) throws IOException {
       int codeLines = 0;
       boolean inBlockComment = false;
       try (BufferedReader reader = Files.newBufferedReader(file)) {
           String line;
           while ((line = reader.readLine()) != null) {
               line = line.trim();
               if (line.isEmpty()) {
                   continue;
               }
               if (line.startsWith("/*") || line.startsWith("/**")) {
                   inBlockComment = true;
               }
               if (inBlockComment) {

                   if (line.endsWith("*/")) {
                       inBlockComment = false;
                   }
                   continue; 
               }

               if (line.contains("/*") && line.contains("*/")) {
                   continue; 
               }
               if (line.startsWith("//")) {
                   continue;
               }
               codeLines++;
               if (line.endsWith("*/")) {
                   inBlockComment = false;
               }
           }
       }
       return codeLines;
   }
   private static int countLOC(Path file) throws IOException {
         int count = 0;
       try (BufferedReader reader = Files.newBufferedReader(file)) {
           while (reader.readLine() != null) {
               count++;
           }
       }
       return count;
   }

   private static int countFunctionCount(Path file) throws IOException {
       int functionCount = 0;
       StringBuilder contentBuilder = new StringBuilder();
       boolean inCommentBlock = false;

       try (BufferedReader reader = Files.newBufferedReader(file)) {
           String line;
           while ((line = reader.readLine()) != null) {
               line = line.trim();

               if (line.startsWith("/*") && !inCommentBlock) {
                   inCommentBlock = true;
               }
               if (inCommentBlock && line.endsWith("*/")) {
                   inCommentBlock = false;
                   continue;
               }
               if (inCommentBlock || line.startsWith("//")) {
                   continue;
               }

               contentBuilder.append(line).append("\n");
           }
       }

       String content = contentBuilder.toString();

       Pattern pattern = Pattern.compile("^(public\\s+|protected\\s+|private\\s+|static\\s+|final\\s+|abstract\\s+|synchronized\\s+)?"
               + "([\\w\\s,<>.\\[\\]]+\\s+)+?" + "\\w+\\s*" + "\\(([\\w\\s,<>.\\[\\]]*)\\)"
               , Pattern.MULTILINE);
       Matcher matcher = pattern.matcher(content);

       while (matcher.find()) {
           functionCount++;
       }

       return functionCount;
   }

   private static double calculateCommentDeviation(int javadocLines, int otherCommentLines, int functionCount, int codeLines) {
       double YG = ((javadocLines + otherCommentLines) * 0.8) / functionCount;
       double YH = (codeLines / (double) functionCount) * 0.3;
       return ((100 * YG) / YH) - 100;
   }
}