package com.dull.bird.email.tool.service.imp;

import com.dull.bird.email.tool.data.entity.EmailSendDTO;
import com.dull.bird.email.tool.service.ExeclService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExeclAnalyzeImp implements ExeclService{
    private static final String SEND_TO_TITILE = "Email";
    private ExeclAnalyzeImp(){}


    /**
     * 获取一个实例对象
     * @return  ExeclAnalyze对象
     */
    public static ExeclAnalyzeImp getInstance(){
        return ExeclAnalyzeInnerObject.execlAnalyze;
    }

    @Override
    public List<EmailSendDTO> analyzeExecl(MultipartFile fileStream) throws IOException {
        Workbook workbook = loadExeclFile(fileStream);
        String[] titles = readExeclTitles(workbook);
        return readBody(workbook, titles);
    }

    /**
     * 内部静态类，为了实现单例
     */
    private static class ExeclAnalyzeInnerObject{
        private static final ExeclAnalyzeImp execlAnalyze = new ExeclAnalyzeImp();
    }


    /**
     * 读取execl,判断是否为xls或xlsx文件。
     * @param filePath  文件路径，如果文件不合法，抛出异常
     * @return  返回一个WorkBook对象
     * @throws IOException 如果加载失败，抛出异常。
     */
    private Workbook loadExeclFile(String filePath) throws IOException {
        InputStream in = new FileInputStream(new File(filePath));
        return checkXlsOrXlsx(filePath, in);
    }

    /**
     * 读取execl,判断是否为xls或xlsx文件。重载方法
     * @param fileStream    输入一个MultipartFile
     * @return  返回一个WorkBook对象
     * @throws IOException 如果加载失败，抛出异常
     */
    private Workbook loadExeclFile(MultipartFile fileStream) throws IOException {
        InputStream in = fileStream.getInputStream();
        return checkXlsOrXlsx(fileStream.getOriginalFilename(), in);
    }

    /**
     * 判断是否是xls或者xlsx
     * TODO：正则表达式的判断可以优化
     * @param fileName  文件名或者包含文件名的路径
     * @param in    文件输入流
     * @return  返回一个WorkBook对象
     * @throws IOException 如果加载失败，抛出异常
     */
    private Workbook checkXlsOrXlsx(String fileName, InputStream in) throws IOException {
        Workbook wb ;
        if (fileName.matches(".*\\.xls")) {
            wb = new HSSFWorkbook(in);
        } else if (fileName.matches(".*\\.xlsx")) {
            wb = new XSSFWorkbook(in);
        } else {
            throw new IllegalArgumentException("输入的文件[" + fileName + "]不合法，请检查是否为execl！");
        }
        return wb;
    }
    /**
     * 解析标题行,并且需要保证第一列是email邮箱
     * @param workbook  execl对象
     * @return  返回标题数组
     */
    private String[] readExeclTitles(Workbook workbook){
        if (workbook == null) {
            throw new IllegalArgumentException("execl对象无效，读取标题出错");
        }

        Sheet firstSheet = workbook.getSheetAt(0);
        Row titleRow = firstSheet.getRow(0);
        String emailTitle =  titleRow.getCell(0).toString();
        if (!SEND_TO_TITILE.equalsIgnoreCase(emailTitle)) {
            throw new IllegalArgumentException("execl对象无效，不存在'Email'列");
        }

        int titleNumber = titleRow.getPhysicalNumberOfCells();
        String [] titleArray= new String[titleNumber];
        for (int i = 0; i < titleNumber; i++) {
            titleArray[i] = titleRow.getCell(i).toString();
        }

        return titleArray;
    }

    /**
     * 读取execl内容,第一行标题排除
     * @param workbook  execl文件
     * @param titleLength   内容个数
     * @return  内容解析出来的结果
     */
    @Deprecated
    private List<List<String>> readBody(Workbook workbook, int titleLength){
        if (workbook == null) {
            throw new IllegalArgumentException("execl对象无效，读取内容出错");
        }
        List<List<String>> body = new ArrayList<>();
        Sheet firstSheet = workbook.getSheetAt(0);
        int lastRowNum = firstSheet.getLastRowNum();
        for (int i = 1; i < lastRowNum ; i++) {
            Row row = firstSheet.getRow(i);
            ArrayList<String> rowList = new ArrayList<>();
            for (int j = 0; j < titleLength; j++) {
                Cell cell = row.getCell(j);
                String value = "";
                if (null != cell) {
                    value = cell.toString();
                }
                rowList.add(value);
            }
            body.add(rowList);
        }
        return body;
    }


    /**
     * 读取execl内容,第一行标题排除
     * @param workbook  execl文件
     * @param titles   表头
     * @return  内容解析出来的结果
     */
    private List<EmailSendDTO> readBody(Workbook workbook, String[] titles){
        if (workbook == null) {
            throw new IllegalArgumentException("execl对象无效，读取内容出错");
        }
        List<EmailSendDTO> body = new ArrayList<>();
        Sheet firstSheet = workbook.getSheetAt(0);
        int lastRowNum = firstSheet.getLastRowNum();
        //lastRowNum 从0开始所以是<=
        for (int i = 1; i <= lastRowNum ; i++) {
            Row row = firstSheet.getRow(i);
            EmailSendDTO emailSendDTO = new EmailSendDTO();
            Cell email = row.getCell(0);
            if (email == null) {
                throw new IllegalArgumentException("第" + (i+1) + "列execl中没有配置邮箱");
            }

            emailSendDTO.setSendToUser(email.toString());
            Map<String, Object> context = new HashMap<String, Object>(titles.length);
            for (int j = 1; j < titles.length; j++) {
                Cell cell = row.getCell(j);
                String value = "";
                if (null != cell) {
                    value = cell.toString();
                }
                context.put(titles[j], value);
            }
            emailSendDTO.setBodies(context);
            body.add(emailSendDTO);
        }
        return body;
    }

//    public void test(){
//        try {
//            Workbook workbook = loadExeclFile("X:\\test\\execl.xlsx");
//            String[] titles = readExeclTitles(workbook);
////            List<List<String>> bodies = readBody(workbook, titles.length);
//            List<EmailSendDTO> bodies = readBody(workbook, titles);
//            System.out.println(bodies);
////            ExeclAnalyzeResult execlAnalyzeResult = new ExeclAnalyzeResult(titles, bodies);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public static void main(String[] args) {
        ExeclAnalyzeImp execlAnalyze = new ExeclAnalyzeImp();
//        execlAnalyze.test();
//        System.out.println("X:\\test\\execl.xlsx".matches(".*\\.xlsx"));
//        Pattern p = Pattern.compile("\\.xlsx");
//        System.out.println(p.matcher("X:\\test\\execl.xlsx").find());
    }
}
