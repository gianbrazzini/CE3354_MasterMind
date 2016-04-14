package games;

public class Validate
{

	public boolean isTheWordGuessed(char[] array)
	{
		for (int i = 0; i < array.length; i++)
		{
			if ( array[i] == '_' ) return false;
		}
		return true;
	}

	public void validateWord(String inputword, String listWord)
	{
		if ( inputword.equals( listWord ) )
		{
			System.out.println( "Congratulations you won!" );
		}
		else
		{
			System.out.println( "Sorry The Correct word was : " + listWord );
		}
		;
	}
}
