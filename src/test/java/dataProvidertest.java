import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class dataProvidertest {

	@Test(dataProvider = "drivetest")
	public void testdata(String greeting, String comms, int id) {
		System.out.println(greeting + comms + id);
	}

	@DataProvider(name = "drivetest")
	public Object[][] getdata() {

		Object[][] data = { { "hi", "come", 2 }, { "hello", "go", 555 }, { "do", "done", 122 } };
		return data;

	}
}
