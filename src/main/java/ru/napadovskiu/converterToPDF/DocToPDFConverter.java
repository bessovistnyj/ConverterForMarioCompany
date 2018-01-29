package ru.napadovskiu.converterToPDF;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.docx4j.Docx4J;
import org.docx4j.convert.in.Doc;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;


public class DocToPDFConverter extends Converter {


	public DocToPDFConverter(InputStream inStream, OutputStream outStream, boolean closeStreamsWhenComplete) {
		super(inStream, outStream,  closeStreamsWhenComplete);
	}

	@Override
	public void convert() throws Exception{

		InputStream iStream = inStream;


		WordprocessingMLPackage wordMLPackage = getMLPackage(iStream);

		Docx4J.toPDF(wordMLPackage, outStream);

		finished();
		
	}

	protected WordprocessingMLPackage getMLPackage(InputStream iStream) throws Exception{
		PrintStream originalStdout = System.out;

		WordprocessingMLPackage mlPackage = Doc.convert(iStream);
		
		System.setOut(originalStdout);
		return mlPackage;
	}

}
