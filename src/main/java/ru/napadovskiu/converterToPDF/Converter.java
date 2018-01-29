package ru.napadovskiu.converterToPDF;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;



public abstract class Converter {



	protected InputStream inStream;
	protected OutputStream outStream;

	protected boolean closeStreamsWhenComplete = true;

	public Converter(InputStream inStream, OutputStream outStream,  boolean closeStreamsWhenComplete){
		this.inStream = inStream;
		this.outStream = outStream;
		this.closeStreamsWhenComplete = closeStreamsWhenComplete;
	}

	public abstract void convert() throws Exception;


	protected void finished(){

		if(closeStreamsWhenComplete){
			try {
				inStream.close();
				outStream.close();
			} catch (IOException e) {
				//Nothing done
			}
		}
	}



}
