package excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

public class ExcelListener extends AnalysisEventListener<StudentData> {
    /*一行一行读取数据*/
    @Override
    public void invoke(StudentData studentData, AnalysisContext analysisContext) {
        System.out.println("data="+studentData);
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头:"+headMap);
    }

    /*所有的数据读取完毕之后会自动调用*/
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("读取完毕");
    }
}
