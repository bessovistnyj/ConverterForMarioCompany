package ru.napadovskiu.converterToPDF;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class DocxToPDFConverter extends Converter {


	public DocxToPDFConverter(InputStream inStream, OutputStream outStream, boolean closeStreamsWhenComplete) {
		super(inStream, outStream,  closeStreamsWhenComplete);
	}


	@Override
	public void convert() throws Exception {

        XWPFDocument document = new XWPFDocument(inStream);

        PdfOptions options = PdfOptions.create();

        PdfConverter.getInstance().convert(document, outStream, options);
        
        finished();
        
	}

}
