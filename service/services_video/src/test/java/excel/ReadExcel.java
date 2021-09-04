package excel;

import com.alibaba.excel.EasyExcel;

public class ReadExcel {
    public static void main(String[] args) {
        String fileName = "D:\\01.xlsx";
        /*每读取一行数据的时候, 就会调用监听器当中的方法*/
        EasyExcel.read(fileName,StudentData.class,new ExcelListener()).sheet().doRead();
    }
}
