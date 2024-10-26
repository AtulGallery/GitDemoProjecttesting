import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class excelData {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		FileInputStream fis = new FileInputStream("C:\\Users\\Atul\\Documents\\TestData.xlsx");

		// this xssfworkbook accepts file input Stream argument
		XSSFWorkbook workbook = new XSSFWorkbook(fis);

		int sheets = workbook.getNumberOfSheets();

		for (int i = 0; i < sheets; i++) {
			if (workbook.getSheetName(i).equalsIgnoreCase("testexcel")) {
				XSSFSheet sheet = workbook.getSheetAt(i);

				Iterator<Row> rows = sheet.iterator();
				Row firstrow = rows.next();
				Iterator<Cell> ce = firstrow.cellIterator();

				int k = 0;
				int coloumn = -1;
				while (ce.hasNext()) {
					Cell value = ce.next();
					if (value.getCellType() == CellType.STRING && value.getStringCellValue().equalsIgnoreCase("FirstName")) {
						// desired column
						coloumn=k;

					}
					k++;
					
				}
				
				System.out.println(coloumn);
			}

		}
	}
}
