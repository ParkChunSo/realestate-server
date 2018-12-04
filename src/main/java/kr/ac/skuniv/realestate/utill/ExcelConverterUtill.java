package kr.ac.skuniv.realestate.utill;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

@Component
public class ExcelConverterUtill {
    private final String EXCEL_PATH_FOR_WINDOW = "C:\\Users\\Kimyunsang\\Desktop\\spring\\RegionCode.xlsx";
    private final String EXCEL_PATH_FOR_LINUX = "home/realEstate/RegionCode.xlsx";
    HashMap<String, Integer> regionCodeMap;

    public void ReadRegionCode() throws FileNotFoundException, IOException {

        regionCodeMap = new HashMap<>();
        XSSFWorkbook workbook;
        XSSFSheet sheet;
        XSSFRow row;
        XSSFCell cell;

        FileInputStream fis = new FileInputStream(EXCEL_PATH_FOR_WINDOW);
        workbook = new XSSFWorkbook(fis);
        int rowIndex = 0;
        int columnIndex = 0;
        String tmpRegion = null;

        sheet = workbook.getSheetAt(0);
        int rows = sheet.getPhysicalNumberOfRows();
        for(rowIndex = 0; rowIndex< rows; rowIndex++){
            row = sheet.getRow(rowIndex);
            if(row != null){
                int cells = row.getPhysicalNumberOfCells();
                for(columnIndex=0; columnIndex< cells; columnIndex++){
                    cell=row.getCell(columnIndex);
                    if(cell == null)
                        continue;
                    else{
                        if(columnIndex == 0)
                            tmpRegion = cell.getStringCellValue()+"";
                        else if(columnIndex == 1 && tmpRegion != null){
                            regionCodeMap.put(tmpRegion, ((int)cell.getNumericCellValue()/100));
                        }
                    }
                }
            }
        }
    }

    public HashMap<String, Integer> getRegionCodeMap() {
        return regionCodeMap;
    }
}
