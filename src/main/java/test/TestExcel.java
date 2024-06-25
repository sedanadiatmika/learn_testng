package test;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


class TextExcel
{
    static ArrayList<String> category = new ArrayList<>();
    static ArrayList<String> title = new ArrayList<>();
    static ArrayList<String> description = new ArrayList<>();

    public static void main(String[] args)
    {
        try
        {
            File file = new File("Sales driver requirements.xlsx");   //creating a new file instance
            FileInputStream fis = new FileInputStream(file);   //obtaining bytes from the file
//creating Workbook instance that refers to .xlsx file
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheetAt(0);     //creating a Sheet object to retrieve object
            Iterator<Row> itr = sheet.iterator();    //iterating over excel file

            while (itr.hasNext())
            {
                Row row = itr.next();
//                row = itr.next();
                Iterator<Cell> cellIterator = row.cellIterator();   //iterating over each column

                    Cell cell = cellIterator.next();
                    cell = cellIterator.next();
                    cell = cellIterator.next();
                    switch (cell.getCellType())
                    {
                        case STRING:    //field that represents string cell type
                            category.add(cell.getStringCellValue());
                            break;
                        default:
                    }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        System.out.println(category);
    }
}  