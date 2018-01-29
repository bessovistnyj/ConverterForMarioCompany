package ru.napadovskiu;
import java.io.*;
import java.util.ArrayList;

import ru.napadovskiu.converterPDFToPNG.ConverterPdfToImages;
import ru.napadovskiu.converterToPDF.*;
import ru.napadovskiu.settings.Settings;


public class ConverterAllFiles {

	private final Settings settings = new Settings();


	public ConverterAllFiles() {
		ClassLoader loader = Settings.class.getClassLoader();
		InputStream io = loader.getResourceAsStream("app.properties");
		this.settings.load(io);
	}


	private static String getFileExtension(String mystr) {
		int index = mystr.indexOf('.');
		return index == -1? null : mystr.substring(index);
	}

	private void convertPDFFileToImage(String PdfFile) {
		ArrayList<String> listOfExts = new ArrayList<>();
		listOfExts.add(".pdf");
		listOfExts.add("..pdf");
		listOfExts.add(".PDF");
		listOfExts.add("..PDF");
		listOfExts.add("PDF");

		ConverterPdfToImages converterPdfToImages = new ConverterPdfToImages(listOfExts);

		File resultFile = new File("result.txt");
		if (resultFile.exists()) {
			resultFile.delete();
		}
		try {
			converterPdfToImages.convertFile(PdfFile);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			converterPdfToImages.createResultFile();

		}

	}


	public static void main(String[] args) throws Exception {

		ConverterAllFiles converterAllFiles = new ConverterAllFiles();


		String fileNameForConvert = converterAllFiles.settings.getValue("home.dir");

		byte[] nameFile = fileNameForConvert.getBytes("Cp1251");
		String filen = new String(nameFile);

		String extension = getFileExtension(fileNameForConvert);

		File inFile = new File(fileNameForConvert);
		FileInputStream iStream = new FileInputStream(inFile);

		if ((extension.toUpperCase().equals(".PDF")) || (extension.toUpperCase().equals("PDF"))) {
			converterAllFiles.convertPDFFileToImage(fileNameForConvert);

		} else if ((extension.toUpperCase().equals(".DOCX")) || (extension.toUpperCase().equals("DOCX"))) {
			String outFileName = "";
			if (inFile.getName().contains(".docx")) {
				outFileName =  inFile.getName().replace(".docx", ".pdf");

			} else if (inFile.getName().contains(".DOCX")) {
				outFileName = inFile.getName().replace(".DOCX", ".PDF");
			}
			String destinationDir = inFile.getParent();
			FileOutputStream outFile = new FileOutputStream(destinationDir+"\\"+outFileName);


			DocxToPDFConverter docxToPDFConverter = new DocxToPDFConverter(iStream, outFile,false);
			docxToPDFConverter.convert();
			converterAllFiles.convertPDFFileToImage(destinationDir+"\\"+outFileName);


		} else if (extension.toUpperCase() == ".DOC") {
			String outFileName = "";
			if (inFile.getName().contains(".doc")) {
				outFileName =  inFile.getName().replace(".doc", ".pdf");

			} else if (inFile.getName().contains(".DOC")) {
				outFileName = inFile.getName().replace(".DOC", ".PDF");
			}
			String destinationDir = inFile.getParent();
			FileOutputStream outFile = new FileOutputStream(destinationDir+"\\"+outFileName);

			DocToPDFConverter docToPDFConverter = new DocToPDFConverter(iStream, outFile,false);

			docToPDFConverter.convert();
			converterAllFiles.convertPDFFileToImage(destinationDir+"\\"+outFileName);

		}

	}
}
