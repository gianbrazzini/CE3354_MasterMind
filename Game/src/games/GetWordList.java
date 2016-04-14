package games;

import java.util.LinkedList;
import java.net.*;
import java.io.*;

public class GetWordList
{

	public LinkedList<String> WordList()
	{
		LinkedList<String> list = new LinkedList<String>();
		int counter = 0;
		int maxWords = 1000;
		try
		{
			URL wordlist = new URL( "https://raw.githubusercontent.com/dwyl/english-words/master/words2.txt" );
			BufferedReader in = new BufferedReader( new InputStreamReader( wordlist.openStream() ) );

			String inputLine;

			while ( (inputLine = in.readLine()) != null)
			{
				list.add( inputLine );
				counter++;
				if ( counter == maxWords ) break;
			}

			in.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return list;

	}
}
