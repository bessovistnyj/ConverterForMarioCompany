package ru.napadovskiu.converterPDFToPNG;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import ru.napadovskiu.settings.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Main class to convert pdf to jpg.
 *
 * @author Napadovskiy Bohdan
 * @version 1.0
 * @since 14.10.2017
 */
public class ConverterPdfToImages {

   /**
     * list of exts.
     */
   private final List<String> exts;

    /**
     * count page in pdf file.
     */
   private int countPageInFile = 0;

    /**
     *
     * @param exts extension list.
     */
   public ConverterPdfToImages(List<String> exts) {
       this.exts = exts;
   }

    /**
     *
     * @param fileToConvert file to convert.
     * @throws IOException message if error.
     */
   public void convertFile(String fileToConvert) throws IOException {
       File sourceFile = new File(fileToConvert);

       File destinationDir = new File(sourceFile.getParent());

       System.out.println("Read file " + sourceFile);

       PDDocument document = PDDocument.load(sourceFile);

       List<PDPage> list = document.getDocumentCatalog().getAllPages();

       String fileName = "";

       if (sourceFile.getName().contains(".pdf")) {
           fileName =  sourceFile.getName().replace(".pdf", "");

       } else if (sourceFile.getName().contains(".PDF")) {
           fileName = sourceFile.getName().replace(".PDF", "");
       }


       int pageNumber = 0;

       for (PDPage page : list) {
           BufferedImage image = page.convertToImage();
           String outputFileName = "";
           if (pageNumber != 0) {
               outputFileName = destinationDir + "\\" + fileName + "_" + pageNumber + ".png";
           } else {
               outputFileName = destinationDir + "\\" + fileName + ".png";
           }
           File outputFile = new File(outputFileName);
           System.out.println("Image Created -> " + outputFile.getName());
           ImageIO.write(image, "png", outputFile);
           pageNumber++;
       }
       document.close();
       this.countPageInFile = pageNumber;
   }

    /**
     * Take extension of file.
     * @param fileName file name.
     * @return result.
     */
   private static String getFileExtension(String fileName) {
       int index = fileName.indexOf('.');
       return index == -1 ? null : fileName.substring(index);
    }

    /**
     * Check extension.
     * @param fileName file name.
     * @return result.
     */
   private boolean checkExtension(String fileName) {
       boolean result = false;
       if (this.exts.contains(getFileExtension(fileName))) {
           result = true;
       }
       return result;
   }

    /**
     * method create result file for check.
     */
    public void createResultFile() {
       try {
           FileWriter fileWriter = new FileWriter("result.txt");

           fileWriter.write(String.valueOf(this.countPageInFile));
           fileWriter.flush();
       } catch (IOException e) {
           e.printStackTrace();
       }

   }

    /**
     /**
     * Main method for convert.
     * @param args name of file for convert.
     * @throws IOException exception.
     */
//   public static void main(String[] args) throws IOException {
//       ArrayList<String> listOfExts = new ArrayList<>();
//       listOfExts.add(".pdf");
//       listOfExts.add("..pdf");
//       listOfExts.add(".PDF");
//       listOfExts.add("..PDF");
//       listOfExts.add("PDF");
//       ConverterPdfToImages converterPdfToImages = new ConverterPdfToImages(listOfExts);
//       Settings settings = new Settings();
//       String fileName = settings.getFileName();
//       File resultFile = new File("result.txt");
//       if (resultFile.exists()) {
//           resultFile.delete();
//       }
//       try {
//           converterPdfToImages.convertFile(fileName);
//       } catch (IOException e) {
//           e.printStackTrace();
//       } finally {
//           converterPdfToImages.createResultFile();
//
//       }
//
//    }

}
