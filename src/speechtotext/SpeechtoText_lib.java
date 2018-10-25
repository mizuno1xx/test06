package speechtotext;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechRecognitionResults;

public class SpeechtoText_lib 
{
	SpeechToText service;
    RecognizeOptions options = null;
    
	public SpeechtoText_lib( File audio ) 
	{
		service = new SpeechToText();
		service.setUsernameAndPassword("1618101", "1618101");
		try
		{
			options = new RecognizeOptions.Builder()
				.model("ja-JP_BroadbandModel")
			    .audio(audio)
			    .contentType(RecognizeOptions.ContentType.AUDIO_WAV)
			    .build();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
	}
    
	public SpeechRecognitionResults getTranscript() 
	{
		SpeechRecognitionResults transcript = service.recognize(options).execute();
		return transcript;
	}
	
	public void GetJson( SpeechRecognitionResults transcript )
	{
		String s = String.valueOf(transcript);
	    ObjectMapper mapper = new ObjectMapper();
	    MySQL mysql = new MySQL();
	    try 
	    {
			JsonNode node = mapper.readTree(s);
			
			for( int i = 0 ; i < node.get("results").size() ; i ++ )
			{
				String text = node.get("results").get(i).get("alternatives").get(0).get("transcript").toString();
				double confidence = node.get("results").get(i).get("alternatives").get(0).get("confidence").asDouble();
				System.out.println( "transcript:" + text );
				System.out.println( "confidence:" + confidence );
				mysql.updateImage( text , confidence );
			}
		}
	    catch (IOException e) 
	    {
			e.printStackTrace();
		}	
	}
}