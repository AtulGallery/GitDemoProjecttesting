import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Utilities {

	public static Object[][] Read_excel() throws IOException {

		DataFormatter formatter = new DataFormatter();
		FileInputStream fis;
		fis = new FileInputStream("C://Users//Atul//Documents//TestData.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fis);

		// Check if the correct sheet is present
		int sheets = workbook.getNumberOfSheets();
		for (int i = 0; i < sheets; i++) {
			if (workbook.getSheetName(i).equalsIgnoreCase("testexcel")) { // Ensure sheet name matches
				XSSFSheet sheet = workbook.getSheetAt(i);
				int rowCount = sheet.getPhysicalNumberOfRows();
				XSSFRow row = sheet.getRow(0);
				int columnCount = row.getLastCellNum();

				// Create a 2D array to hold the data from Excel
				Object[][] data = new Object[rowCount - 1][columnCount];

				// Loop through each row and column to populate the data array
				for (int j = 0; j < rowCount - 1; j++) {
					row = sheet.getRow(j + 1);
					for (int k = 0; k < columnCount; k++) {
						XSSFCell cell = row.getCell(k);

						if (cell != null && cell.getCellType() == CellType.NUMERIC
								&& DateUtil.isCellDateFormatted(cell)) {
							SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
							data[j][k] = sdf.format(cell.getDateCellValue()); // Assign formatted date
						} else {
							data[j][k] = formatter.formatCellValue(cell); // Convert and store non-date cell values
						}
					}
				}

				workbook.close();
				fis.close();
				return data; // Return the data to the test method
			}
		}

		// If no sheet is found or an issue occurs, return an empty array
		return new Object[0][0];
	}

	public static void read_excel1() {
		
	}
}