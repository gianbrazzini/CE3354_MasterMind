package games;

import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;
import java.net.*;
import java.io.*;

public class GetWordList
{

	public LinkedList<String> WordList()
	{
		
		Random skip = new Random(1);
		
		LinkedList<String> list = new LinkedList<String>();
		int counter = 0;
		int maxWords = 1000;
		try
		{
			File wordlist = new File( "words_english.txt" );
			Scanner in = new Scanner( wordlist );

			String inputLine;

			while ( (inputLine = in.nextLine()) != null)
			{
				
				for(int i=Math.abs(skip.nextInt()%100);i>0;i--){
					inputLine = in.nextLine();
				}
				
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
