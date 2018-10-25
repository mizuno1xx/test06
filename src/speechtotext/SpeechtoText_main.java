package speechtotext;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechRecognitionResults;

public class SpeechtoText_main 
{
	public static void main(String[] args) 
	{	
		SpeechtoText_lib slib = new SpeechtoText_lib( new File( "audio/1.wav" ) );
		SpeechRecognitionResults transcript = slib.getTranscript();
		System.out.println(transcript);

		slib.GetJson( transcript );
	}
}
