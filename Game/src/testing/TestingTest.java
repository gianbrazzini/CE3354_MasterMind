package testing;

import static org.junit.Assert.fail;

import org.junit.Test;

import games.TicTacToe;

public class TestingTest
{

	@Test(expected=Exception.class)
	public void test()
	{
		TicTacToe ttt = new TicTacToe();
		
		//Test bounds!
		ttt.checkBox(0, -1);
		
		fail("Not yet implemented");
	}

}
