package kr.ac.skuniv.realestate.util;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ReadXLSX {
    final private int  CELLS = 1;
    final private int  START_ROW_INDEX = 3;
    final private String  CODE = "code";
    final private String  POPULATION = "population";

    public List<Map<String, String>> readXLSX() throws IOException {
        //파일을 읽기위해 엑셀파일을 가져온다
        FileInputStream fis = new FileInputStream("C:\\Users\\Kimyunsang\\Desktop\\project\\bigdata\\code_popular.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        int rowindex = 0;
        int columnindex = 0;
        // 각 셀값을 넣기위한 맵
        Map<String, String> map = null;

        //셀값들의 리스트
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();

        XSSFSheet sheet = workbook.getSheetAt(0);

        int rows = sheet.getPhysicalNumberOfRows();
        for (rowindex = START_ROW_INDEX; rowindex < rows; rowindex++) {
            //행을읽는다
            XSSFRow row = sheet.getRow(rowindex);
            if (row != null) {
                for (columnindex = 0; columnindex <= CELLS; columnindex++) {
                    //셀값을 읽는다
                    XSSFCell cell = row.getCell(columnindex);
                    String value = "";
                    map = new HashMap<String, String>();
                    //셀이 빈값일경우를 위한 널체크
                    if (cell == null) {
                        continue;
                    } else {
                        value = cell.getStringCellValue() + "";
                        if (columnindex == 0) {
                            map.put(CODE, value);
                        } else {
                            map.put(POPULATION, value);
                        }
                    }
                }
                result.add(map);
            }
        }
        return result;
    }
}
