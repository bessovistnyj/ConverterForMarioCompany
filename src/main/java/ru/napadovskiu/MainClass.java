package ru.napadovskiu;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;


import ru.napadovskiu.converterPDFToPNG.ConverterPdfToImages;
import ru.napadovskiu.converterToPDF.*;
import ru.napadovskiu.settings.Settings;


public class MainClass {

	private final Settings settings = new Settings();

	public MainClass() {
		ClassLoader loader = Settings.class.getClassLoader();
		InputStream io = loader.getResourceAsStream("app.properties");
		this.settings.load(io);
	}

	private static String getFileExtension(String mystr) {
		int index = mystr.indexOf('.');
		return index == -1? null : mystr.substring(index);
	}

	private String getFileNameFromProperties(String fileWithResource) {
		String fileNameForConvert ="";
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileWithResource), Charset.forName("windows-1251")));
			String line;
			while ((line = reader.readLine()) != null) {
				fileNameForConvert = line;
			}
		} catch (IOException e) {
			fileNameForConvert = "";
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					// log warning
				}
			}
		}
		return fileNameForConvert;

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

	private String getOutFileName(File inFile) {
		String outFileName = "";

		String destinationDir = inFile.getParent();

		if (inFile.getName().contains(".docx")) {
			outFileName =  inFile.getName().replace(".docx", ".pdf");

		} else if (inFile.getName().contains(".DOCX")) {
			outFileName = inFile.getName().replace(".DOCX", ".PDF");
		} else if (inFile.getName().contains(".doc")) {
			outFileName =  inFile.getName().replace(".doc", ".pdf");

		} else if (inFile.getName().contains(".DOC")) {
			outFileName = inFile.getName().replace(".DOC", ".PDF");
		}

		return destinationDir+"\\"+outFileName;

	}

	public static void main(String[] args) throws Exception {

		MainClass mainClass = new MainClass();

		String fileWithtext = mainClass.settings.getValue("home.dir");
		String fileNameForConvert = mainClass.getFileNameFromProperties(fileWithtext);

		String extension = getFileExtension(fileNameForConvert);

		File inFile = new File(fileNameForConvert);
		FileInputStream iStream = new FileInputStream(inFile);

		if ((extension.toUpperCase().equals(".PDF")) || (extension.toUpperCase().equals("PDF"))) {
			mainClass.convertPDFFileToImage(fileNameForConvert);

		} else if ((extension.toUpperCase().equals(".DOCX")) || (extension.toUpperCase().equals("DOCX"))) {
			String outFileName = mainClass.getOutFileName(inFile);

			FileOutputStream outFile = new FileOutputStream(outFileName);


			DocxToPDFConverter docxToPDFConverter = new DocxToPDFConverter(iStream, outFile,false);
			docxToPDFConverter.convert();
			mainClass.convertPDFFileToImage(outFileName);


		} else if ((extension.toUpperCase().equals(".DOC")) || (extension.toUpperCase().equals("DOC"))) {
			String outFileName = mainClass.getOutFileName(inFile);
			FileOutputStream outFile = new FileOutputStream(outFileName);

			DocToPDFConverter docToPDFConverter = new DocToPDFConverter(iStream, outFile,false);

			docToPDFConverter.convert();
			mainClass.convertPDFFileToImage(outFileName);

		}

	}
}
