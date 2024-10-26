import java.io.IOException;

import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentTest;

public class Listners extends flipkartShopping implements ITestListener {

	ExtentTest test;
	
	@Override
	public void onTestFailure(ITestResult result) {
		
		test.fail(result.getThrowable());
		try {
String	filepath =		getScreenshot(result.getMethod().getMethodName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
