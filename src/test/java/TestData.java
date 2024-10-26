import java.io.IOException;

import org.testng.annotations.DataProvider;

public class TestData {
	
	@DataProvider(name="testexcel")
	public static Object[][] testorangehrm() throws IOException{
		return Utilities.Read_excel();
		
	}

}
