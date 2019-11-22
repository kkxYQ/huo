package com.seven.controller;

import com.seven.model.ObserveTemperature;

import com.seven.model.ObserveWind;
import com.seven.service.IObserveTemperatureService;
import com.seven.service.IObserveWindService;
import io.swagger.annotations.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @program: huo
 * @description: 导入Excel文档
 * @author: Mr.Y
 * @create: 2019-11-16 11:32
 **/
@RestController
@RequestMapping("/upload")
@Api(value = "导入文档",description = "参证站风温接口描述")
public class UploadController {

    @Autowired
    private IObserveTemperatureService service;
    @Autowired
    private IObserveWindService windService;


    @PostMapping("/dos1")
    @ApiOperation("温度导入Excle文档")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, paramType = "query", dataType = "long", example = "1"),
            @ApiImplicitParam(name = "height", value = "高度", required = true, paramType = "query", dataType = "String")
    })
    public void doPostS1(HttpServletRequest request, HttpServletResponse response, MultipartFile fileUpload, Long userId, String height) {
        String fileName = fileUpload.getOriginalFilename();//获取文件名
        Date date = new Date();
        //获取文件后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //重新生成文件名
        fileName = date.getTime() + suffixName;
        //指定本地文件夹存储图片
        String filePath = "G:\\upload\\";
        try {
            //将图片保存到static文件夹里
            fileUpload.transferTo(new File(filePath + fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            File excel = new File(filePath + fileName);

            if (excel.isFile() && excel.exists()) {
                String[] split = excel.getName().split("\\.");  //.是特殊字符，需要转义！！！！！
                Workbook wb;
                //根据文件后缀（xls/xlsx）进行判断
                if ("xls".equals(split[1])) {
                    FileInputStream fis = new FileInputStream(excel);   //文件流对象
                    wb = new HSSFWorkbook(fis);
                } else if ("xlsx".equals(split[1])) {
                    wb = new XSSFWorkbook(excel);
                } else {
                    System.out.println("文件类型错误!");
                    return;
                }
                //开始解析
                Sheet sheet = wb.getSheetAt(0);     //读取sheet 0


                int firstRowIndex = sheet.getFirstRowNum();   //第一行是列名，所以不读
                int lastRowIndex = sheet.getLastRowNum();

                Double dou = null;
                Collection<ObserveTemperature> entityList = new ArrayList<>();

                String[] splitHs = height.split(",");
                StringBuffer buff = new StringBuffer();
                for (int i = 0; i < splitHs.length; i++) {
                    buff.append(splitHs[i] + "m逐时,");
                }
                String[] sbu = buff.toString().split(",");

                int oub = 0;
                for (int i = firstRowIndex; i < lastRowIndex - 1; i++) {
                    Row row = sheet.getRow(i);
                    if (row != null) {
                        for (String s : sbu) {
                            Cell cell = row.getCell(0);
                            if (cell.toString().contains(s)) {
                                oub += 1;
                            }
                        }
                    }
                }
                if (oub != splitHs.length) {
                    System.out.println("输入高度与导入表中的数据不匹配");
                }

                for (int i = firstRowIndex; i < lastRowIndex - 1; i++) {//遍历行
                    Row row = sheet.getRow(i);
                    lab:
                    if (row != null) {
                        ObserveTemperature modi = new ObserveTemperature();
                        modi.setUserId(userId);
                        //获取第一列
                        //int firstCellIndex = row.getFirstCellNum();
                        //获取最后一列
                        //int lastCellIndex = row.getLastCellNum();

                        Cell cell = row.getCell(0);
                        System.out.println(cell.toString());

                        //判断数组中的高度设置高度
                        for (int s = 0; s < sbu.length; s++) {
                            if (cell.toString().contains(sbu[s])) {
                                dou = Double.valueOf(sbu[s].replace("m逐时", ""));
                                i++;
                                break lab;
                            }
                        }
                        modi.setHeight(dou);
                        System.out.println(dou);


                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Cell dwtime = row.getCell(0);//获取第一列数据（单位名）
                        if (dwtime != null) {
                            StringBuffer buffer = new StringBuffer();
                            String[] sub = dwtime.toString().split("-");
                            if (sub.length > 1) {
                                String s1 = StingInt(sub[1]);
                                if (sub[0].indexOf("0") == 0) {
                                    String ss = sub[0].replace("0", "");
                                    StringBuffer bu = buffer.append(sub[2]).append("-" + s1).append("-" + ss);
                                    Date parse = sdf.parse(bu.toString());
                                    modi.setDay(parse);
                                } else {
                                    StringBuffer bu = buffer.append(sub[2]).append("-" + s1).append("-" + sub[0]);
                                    Date parse = sdf.parse(bu.toString());
                                    modi.setDay(parse);
                                }
                            }
                        }

                        Cell dw21 = row.getCell(1);//获取第一列数据（单位名）
                        if (dw21 != null) {
                            dw21.setCellType(CellType.STRING);
                            Double s = Double.valueOf(dw21.getStringCellValue());
                            modi.setTemperatureA21((double) (Math.round(s * 10)) / 10);
                        } else {
                            continue;
                        }
                        Cell dw22 = row.getCell(2);//获取第一列数据（单位名）
                        if (dw22 != null) {
                            dw22.setCellType(CellType.STRING);
                            Double s = Double.valueOf(dw22.getStringCellValue());
                            modi.setTemperatureA22((double) (Math.round(s * 10)) / 10);
                        } else {
                            continue;
                        }

                        Cell dw23 = row.getCell(3);//获取第一列数据（单位名）
                        if (dw23 != null) {
                            dw23.setCellType(CellType.STRING);
                            Double s = Double.valueOf(dw23.getStringCellValue());
                            modi.setTemperatureA23((double) (Math.round(s * 10)) / 10);
                        } else {
                            continue;
                        }
                        Cell dw0 = row.getCell(4);//获取第一列数据（单位名）
                        if (dw0 != null) {
                            dw0.setCellType(CellType.STRING);
                            Double s = Double.valueOf(dw0.getStringCellValue());
                            modi.setTemperatureA0((double) (Math.round(s * 10)) / 10);
                        } else {
                            continue;
                        }
                        Cell dw1 = row.getCell(5);//获取第一列数据（单位名）
                        if (dw1 != null) {
                            dw1.setCellType(CellType.STRING);
                            Double s = Double.valueOf(dw1.getStringCellValue());
                            modi.setTemperatureA1((double) (Math.round(s * 10)) / 10);
                        } else {
                            continue;
                        }
                        Cell dw2 = row.getCell(6);//获取第一列数据（单位名）
                        if (dw2 != null) {
                            dw2.setCellType(CellType.STRING);
                            Double s = Double.valueOf(dw2.getStringCellValue());
                            modi.setTemperatureA2((double) (Math.round(s * 10)) / 10);
                        } else {
                            continue;
                        }
                        Cell dw3 = row.getCell(7);//获取第一列数据（单位名）
                        if (dw3 != null) {
                            dw3.setCellType(CellType.STRING);
                            Double s = Double.valueOf(dw3.getStringCellValue());
                            modi.setTemperatureA3((double) (Math.round(s * 10)) / 10);
                        } else {
                            continue;
                        }
                        Cell dw4 = row.getCell(8);//获取第一列数据（单位名）
                        if (dw4 != null) {
                            dw4.setCellType(CellType.STRING);
                            Double s = Double.valueOf(dw4.getStringCellValue());
                            modi.setTemperatureA4((double) (Math.round(s * 10)) / 10);
                        } else {
                            continue;
                        }
                        Cell dw5 = row.getCell(9);//获取第一列数据（单位名）
                        if (dw5 != null) {
                            dw5.setCellType(CellType.STRING);
                            Double s = Double.valueOf(dw5.getStringCellValue());
                            modi.setTemperatureA5((double) (Math.round(s * 10)) / 10);
                        } else {
                            continue;
                        }
                        Cell dw6 = row.getCell(10);//获取第一列数据（单位名）
                        if (dw6 != null) {
                            dw6.setCellType(CellType.STRING);
                            Double s = Double.valueOf(dw6.getStringCellValue());
                            modi.setTemperatureA6((double) (Math.round(s * 10)) / 10);
                        } else {
                            continue;
                        }
                        Cell dw7 = row.getCell(11);//获取第一列数据（单位名）
                        if (dw7 != null) {
                            dw7.setCellType(CellType.STRING);
                            Double s = Double.valueOf(dw7.getStringCellValue());
                            modi.setTemperatureA7((double) (Math.round(s * 10)) / 10);
                        } else {
                            continue;
                        }
                        Cell dw8 = row.getCell(12);//获取第一列数据（单位名）
                        if (dw8 != null) {
                            dw8.setCellType(CellType.STRING);
                            Double s = Double.valueOf(dw8.getStringCellValue());
                            modi.setTemperatureA8((double) (Math.round(s * 10)) / 10);
                        } else {
                            continue;
                        }
                        Cell dw9 = row.getCell(13);//获取第一列数据（单位名）
                        if (dw9 != null) {
                            dw9.setCellType(CellType.STRING);
                            Double s = Double.valueOf(dw9.getStringCellValue());
                            modi.setTemperatureA9((double) (Math.round(s * 10)) / 10);
                        } else {
                            continue;
                        }
                        Cell dw10 = row.getCell(14);//获取第一列数据（单位名）
                        if (dw10 != null) {
                            dw10.setCellType(CellType.STRING);
                            Double s = Double.valueOf(dw10.getStringCellValue());
                            modi.setTemperatureA10((double) (Math.round(s * 10)) / 10);
                        } else {
                            continue;
                        }
                        Cell dw11 = row.getCell(15);//获取第一列数据（单位名）
                        if (dw11 != null) {
                            dw11.setCellType(CellType.STRING);
                            Double s = Double.valueOf(dw11.getStringCellValue());
                            modi.setTemperatureA11((double) (Math.round(s * 10)) / 10);
                        } else {
                            continue;
                        }
                        Cell dw12 = row.getCell(16);//获取第一列数据（单位名）
                        if (dw12 != null) {
                            dw12.setCellType(CellType.STRING);
                            Double s = Double.valueOf(dw12.getStringCellValue());
                            modi.setTemperatureA12((double) (Math.round(s * 10)) / 10);
                        } else {
                            continue;
                        }
                        Cell dw13 = row.getCell(17);//获取第一列数据（单位名）
                        if (dw13 != null) {
                            dw13.setCellType(CellType.STRING);
                            Double s = Double.valueOf(dw13.getStringCellValue());
                            modi.setTemperatureA13((double) (Math.round(s * 10)) / 10);
                        } else {
                            continue;
                        }
                        Cell dw14 = row.getCell(18);//获取第一列数据（单位名）
                        if (dw14 != null) {
                            dw14.setCellType(CellType.STRING);
                            Double s = Double.valueOf(dw14.getStringCellValue());
                            modi.setTemperatureA14((double) (Math.round(s * 10)) / 10);
                        } else {
                            continue;
                        }
                        Cell dw15 = row.getCell(19);//获取第一列数据（单位名）
                        if (dw15 != null) {
                            dw15.setCellType(CellType.STRING);
                            Double s = Double.valueOf(dw15.getStringCellValue());
                            modi.setTemperatureA15((double) (Math.round(s * 10)) / 10);
                        } else {
                            continue;
                        }
                        Cell dw16 = row.getCell(20);//获取第一列数据（单位名）
                        if (dw16 != null) {
                            dw16.setCellType(CellType.STRING);
                            Double s = Double.valueOf(dw16.getStringCellValue());
                            modi.setTemperatureA16((double) (Math.round(s * 10)) / 10);
                        } else {
                            continue;
                        }
                        Cell dw17 = row.getCell(21);//获取第一列数据（单位名）
                        if (dw17 != null) {
                            dw17.setCellType(CellType.STRING);
                            Double s = Double.valueOf(dw17.getStringCellValue());
                            modi.setTemperatureA17((double) (Math.round(s * 10)) / 10);
                        } else {
                            continue;
                        }
                        Cell dw18 = row.getCell(22);//获取第一列数据（单位名）
                        if (dw18 != null) {
                            dw18.setCellType(CellType.STRING);
                            Double s = Double.valueOf(dw18.getStringCellValue());
                            modi.setTemperatureA18((double) (Math.round(s * 10)) / 10);
                        } else {
                            continue;
                        }
                        Cell dw19 = row.getCell(23);//获取第一列数据（单位名）
                        if (dw19 != null) {
                            dw19.setCellType(CellType.STRING);
                            Double s = Double.valueOf(dw19.getStringCellValue());
                            modi.setTemperatureA19((double) (Math.round(s * 10)) / 10);
                        } else {
                            continue;
                        }
                        Cell dw20 = row.getCell(24);//获取第一列数据（单位名）
                        if (dw20 != null) {
                            dw20.setCellType(CellType.STRING);
                            Double s = Double.valueOf(dw20.getStringCellValue());
                            modi.setTemperatureA20((double) (Math.round(s * 10)) / 10);
                        } else {
                            continue;
                        }
                        service.save(modi);
                        //entityList.add(modi);
                    }
                }
//                    if (entityList!=null){
//                        boolean b = service.saveBatch(entityList);
//                    }
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String StingInt(String s) {
        String a;
        switch (s) {
            case "十一月":
                a = "11";
                break;
            case "十二月":
                a = "12";
                break;
            case "一月":
                a = "1";
                break;
            case "二月":
                a = "2";
                break;
            case "三月":
                a = "3";
                break;
            case "四月":
                a = "4";
                break;
            case "五月":
                a = "5";
                break;
            case "六月":
                a = "6";
                break;
            case "七月":
                a = "7";
                break;
            case "八月":
                a = "8";
                break;
            case "九月":
                a = "9";
                break;
            case "十月":
                a = "10";
                break;
            default:
                a = "1";
                break;
        }
        return a;
    }


    @PostMapping("/dos2")
    @ApiOperation("风速导入Excel文档")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, paramType = "query", dataType = "long", example = "1"),
            @ApiImplicitParam(name = "height", value = "高度", required = true, paramType = "query", dataType = "String", example = "10,30")
    })
    public void doPostS2(HttpServletRequest request, HttpServletResponse response, MultipartFile fileUpload, Long userId, String height) {

        String fileName = fileUpload.getOriginalFilename();//获取文件名
        Date date = new Date();
        //获取文件后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //重新生成文件名
        fileName = date.getTime() + suffixName;
        //指定本地文件夹存储图片
        String filePath = "G:\\upload\\";
        try {
            //将图片保存到static文件夹里
            fileUpload.transferTo(new File(filePath + fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            File excel = new File(filePath + fileName);

            if (excel.isFile() && excel.exists()) {
                String[] split = excel.getName().split("\\.");  //.是特殊字符，需要转义！！！！！
                Workbook wb;
                //根据文件后缀（xls/xlsx）进行判断
                if ("xls".equals(split[1])) {
                    FileInputStream fis = new FileInputStream(excel);   //文件流对象
                    wb = new HSSFWorkbook(fis);
                } else if ("xlsx".equals(split[1])) {
                    wb = new XSSFWorkbook(excel);
                } else {
                    System.out.println("文件类型错误!");
                    return;
                }
                //开始解析
                Sheet sheet = wb.getSheetAt(0);     //读取sheet 0


                int firstRowIndex = sheet.getFirstRowNum();   //第一行是列名，所以不读
                int lastRowIndex = sheet.getLastRowNum();

                Double dou = null;
                Collection<ObserveWind> entityList = new ArrayList<>();

                String[] splitHs = height.split(",");
                StringBuffer buff = new StringBuffer();
                for (int i = 0; i < splitHs.length; i++) {
                    buff.append(splitHs[i] + "m逐时,");
                }
                String[] sbu = buff.toString().split(",");

                int oub = 0;
                for (int i = firstRowIndex; i < lastRowIndex - 1; i++) {
                    Row row = sheet.getRow(i);
                    if (row != null) {
                        for (String s : sbu) {
                            Cell cell = row.getCell(0);
                            if (cell.toString().contains(s)) {
                                System.out.println(s);
                                oub += 1;
                            }
                        }
                    }
                }
                if (oub != splitHs.length) {
                    System.out.println("输入高度与导入表中的数据不匹配");
                }

                for (int i = firstRowIndex; i < lastRowIndex - 1; i++) {//遍历行
                    Row row = sheet.getRow(i);
                    lab:
                    if (row != null) {
                        ObserveWind modi = new ObserveWind();
                        modi.setUserId(userId);
                        //获取第一列
                        //int firstCellIndex = row.getFirstCellNum();
                        //获取最后一列
                        //int lastCellIndex = row.getLastCellNum();

                        Cell cell = row.getCell(0);
                        System.out.println(cell.toString());

                        //判断数组中的高度设置高度
                        for (int s = 0; s < sbu.length; s++) {
                            if (cell.toString().contains(sbu[s])) {
                                dou = Double.valueOf(sbu[s].replace("m逐时", ""));
                                i++;
                                break lab;
                            }
                        }
                        modi.setHeight(dou);
                        System.out.println(dou);

//                        String[] heights = height.split(",");
//                        for (int i1 = 0; i1 < heights.length; i1++) {
//                            if(row.getCell(0).toString().contains(heights[i1])){
//                                i+=1;
//                                Double he = Double.valueOf(heights[i1]);
//                                modi.setHeight(he);
//                            }
//                        }

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Cell dwtime = row.getCell(0);//获取第一列数据（单位名）
                        if (dwtime != null) {
                            StringBuffer buffer = new StringBuffer();
                            String[] sub = dwtime.toString().split("-");
                            if (sub.length > 1) {
                                String s1 = StingInt(sub[1]);
                                if (sub[0].indexOf("0") == 0) {
                                    String ss = sub[0].replace("0", "");
                                    StringBuffer bu = buffer.append(sub[2]).append("-" + s1).append("-" + ss);
                                    Date parse = sdf.parse(bu.toString());
                                    modi.setDay(parse);
                                } else {
                                    StringBuffer bu = buffer.append(sub[2]).append("-" + s1).append("-" + sub[0]);
                                    Date parse = sdf.parse(bu.toString());
                                    modi.setDay(parse);
                                }
                            }
                        }

                        Cell dw21 = row.getCell(1);//获取第二列数据（单位名）
                        if (dw21 != null) {
                            dw21.setCellType(CellType.STRING);
                            String ds = dw21.getStringCellValue();
                            modi.setDirectionA21(ds);
                        } else {
                            continue;
                        }
                        Cell SpeedA21 = row.getCell(2);//获取第二列数据（单位名）
                        if (SpeedA21 != null) {
                            SpeedA21.setCellType(CellType.STRING);
                            Double s = Double.valueOf(SpeedA21.getStringCellValue());
                            modi.setSpeedA21((double) (Math.round(s * 10)) / 10);
                        } else {
                            continue;
                        }
                        Cell dw22 = row.getCell(3);//获取第二列数据（单位名）
                        if (dw22 != null) {
                            dw22.setCellType(CellType.STRING);
                            String ds = dw22.getStringCellValue();
                            modi.setDirectionA22(ds);
                        } else {
                            continue;
                        }
                        Cell SpeedA22 = row.getCell(4);//获取第二列数据（单位名）
                        if (SpeedA22 != null) {
                            SpeedA22.setCellType(CellType.STRING);
                            Double s = Double.valueOf(SpeedA22.getStringCellValue());
                            modi.setSpeedA22((double) (Math.round(s * 10)) / 10);
                        } else {
                            continue;
                        }

                        Cell dw23 = row.getCell(5);//获取第二列数据（单位名）
                        if (dw23 != null) {
                            dw23.setCellType(CellType.STRING);
                            String ds = dw23.getStringCellValue();
                            modi.setDirectionA23(ds);
                        } else {
                            continue;
                        }
                        Cell SpeedA23 = row.getCell(6);//获取第二列数据（单位名）
                        if (SpeedA23 != null) {
                            SpeedA23.setCellType(CellType.STRING);
                            Double s = Double.valueOf(SpeedA23.getStringCellValue());
                            modi.setSpeedA23((double) (Math.round(s * 10)) / 10);
                        } else {
                            continue;
                        }

                        Cell dw0 = row.getCell(7);//获取第二列数据（单位名）
                        if (dw0 != null) {
                            dw0.setCellType(CellType.STRING);
                            String ds = dw0.getStringCellValue();
                            modi.setDirectionA0(ds);
                        } else {
                            continue;
                        }
                        Cell SpeedA0 = row.getCell(8);//获取第二列数据（单位名）
                        if (SpeedA0 != null) {
                            SpeedA0.setCellType(CellType.STRING);
                            Double s = Double.valueOf(SpeedA0.getStringCellValue());
                            modi.setSpeedA0((double) (Math.round(s * 10)) / 10);
                        } else {
                            continue;
                        }

                        Cell dw1 = row.getCell(9);//获取第二列数据（单位名）
                        if (dw1 != null) {
                            dw1.setCellType(CellType.STRING);
                            String ds = dw1.getStringCellValue();
                            modi.setDirectionA1(ds);
                        } else {
                            continue;
                        }
                        Cell SpeedA1 = row.getCell(10);//获取第二列数据（单位名）
                        if (SpeedA1 != null) {
                            SpeedA1.setCellType(CellType.STRING);
                            Double s = Double.valueOf(SpeedA1.getStringCellValue());
                            modi.setSpeedA1((double) (Math.round(s * 10)) / 10);
                        } else {
                            continue;
                        }

                        Cell dw2 = row.getCell(11);//获取第二列数据（单位名）
                        if (dw2 != null) {
                            dw2.setCellType(CellType.STRING);
                            String ds = dw2.getStringCellValue();
                            modi.setDirectionA2(ds);
                        } else {
                            continue;
                        }
                        Cell SpeedA2 = row.getCell(12);//获取第二列数据（单位名）
                        if (SpeedA2 != null) {
                            SpeedA2.setCellType(CellType.STRING);
                            Double s = Double.valueOf(SpeedA2.getStringCellValue());
                            modi.setSpeedA2((double) (Math.round(s * 10)) / 10);
                        } else {
                            continue;
                        }

                        Cell dw3 = row.getCell(13);//获取第二列数据（单位名）
                        if (dw3 != null) {
                            dw3.setCellType(CellType.STRING);
                            String ds = dw3.getStringCellValue();
                            modi.setDirectionA3(ds);
                        } else {
                            continue;
                        }
                        Cell SpeedA3 = row.getCell(14);//获取第二列数据（单位名）
                        if (SpeedA3 != null) {
                            SpeedA3.setCellType(CellType.STRING);
                            Double s = Double.valueOf(SpeedA3.getStringCellValue());
                            modi.setSpeedA3((double) (Math.round(s * 10)) / 10);
                        } else {
                            continue;
                        }

                        Cell dw4 = row.getCell(15);//获取第二列数据（单位名）
                        if (dw4 != null) {
                            dw4.setCellType(CellType.STRING);
                            String ds = dw4.getStringCellValue();
                            modi.setDirectionA4(ds);
                        } else {
                            continue;
                        }
                        Cell SpeedA4 = row.getCell(16);//获取第二列数据（单位名）
                        if (SpeedA4 != null) {
                            SpeedA4.setCellType(CellType.STRING);
                            Double s = Double.valueOf(SpeedA4.getStringCellValue());
                            modi.setSpeedA4((double) (Math.round(s * 10)) / 10);
                        } else {
                            continue;
                        }

                        Cell dw5 = row.getCell(17);//获取第二列数据（单位名）
                        if (dw5 != null) {
                            dw5.setCellType(CellType.STRING);
                            String ds = dw5.getStringCellValue();
                            modi.setDirectionA5(ds);
                        } else {
                            continue;
                        }
                        Cell SpeedA5 = row.getCell(18);//获取第二列数据（单位名）
                        if (SpeedA5 != null) {
                            SpeedA5.setCellType(CellType.STRING);
                            Double s = Double.valueOf(SpeedA5.getStringCellValue());
                            modi.setSpeedA5((double) (Math.round(s * 10)) / 10);
                        } else {
                            continue;
                        }

                        Cell dw6 = row.getCell(19);//获取第二列数据（单位名）
                        if (dw6 != null) {
                            dw6.setCellType(CellType.STRING);
                            String ds = dw6.getStringCellValue();
                            modi.setDirectionA6(ds);
                        } else {
                            continue;
                        }
                        Cell SpeedA6 = row.getCell(20);//获取第二列数据（单位名）
                        if (SpeedA6 != null) {
                            SpeedA6.setCellType(CellType.STRING);
                            Double s = Double.valueOf(SpeedA6.getStringCellValue());
                            modi.setSpeedA6((double) (Math.round(s * 10)) / 10);
                        } else {
                            continue;
                        }

                        Cell dw7 = row.getCell(21);//获取第二列数据（单位名）
                        if (dw7 != null) {
                            dw7.setCellType(CellType.STRING);
                            String ds = dw7.getStringCellValue();
                            modi.setDirectionA7(ds);
                        } else {
                            continue;
                        }
                        Cell SpeedA7 = row.getCell(22);//获取第二列数据（单位名）
                        if (SpeedA7 != null) {
                            SpeedA7.setCellType(CellType.STRING);
                            Double s = Double.valueOf(SpeedA7.getStringCellValue());
                            modi.setSpeedA7((double) (Math.round(s * 10)) / 10);
                        } else {
                            continue;
                        }

                        Cell dw8 = row.getCell(23);//获取第二列数据（单位名）
                        if (dw8 != null) {
                            dw8.setCellType(CellType.STRING);
                            String ds = dw8.getStringCellValue();
                            modi.setDirectionA8(ds);
                        } else {
                            continue;
                        }
                        Cell SpeedA8 = row.getCell(24);//获取第二列数据（单位名）
                        if (SpeedA8 != null) {
                            SpeedA8.setCellType(CellType.STRING);
                            Double s = Double.valueOf(SpeedA8.getStringCellValue());
                            modi.setSpeedA8((double) (Math.round(s * 10)) / 10);
                        } else {
                            continue;
                        }

                        Cell dw9 = row.getCell(25);//获取第二列数据（单位名）
                        if (dw9 != null) {
                            dw9.setCellType(CellType.STRING);
                            String ds = dw9.getStringCellValue();
                            modi.setDirectionA9(ds);
                        } else {
                            continue;
                        }
                        Cell SpeedA9 = row.getCell(26);//获取第二列数据（单位名）
                        if (SpeedA9 != null) {
                            SpeedA9.setCellType(CellType.STRING);
                            Double s = Double.valueOf(SpeedA9.getStringCellValue());
                            modi.setSpeedA9((double) (Math.round(s * 10)) / 10);
                        } else {
                            continue;
                        }

                        Cell dw10 = row.getCell(27);//获取第二列数据（单位名）
                        if (dw10 != null) {
                            dw10.setCellType(CellType.STRING);
                            String ds = dw10.getStringCellValue();
                            modi.setDirectionA10(ds);
                        } else {
                            continue;
                        }
                        Cell SpeedA10 = row.getCell(28);//获取第二列数据（单位名）
                        if (SpeedA10 != null) {
                            SpeedA10.setCellType(CellType.STRING);
                            Double s = Double.valueOf(SpeedA10.getStringCellValue());
                            modi.setSpeedA10((double) (Math.round(s * 10)) / 10);
                        } else {
                            continue;
                        }

                        Cell dw11 = row.getCell(29);//获取第二列数据（单位名）
                        if (dw11 != null) {
                            dw11.setCellType(CellType.STRING);
                            String ds = dw11.getStringCellValue();
                            modi.setDirectionA11(ds);
                        } else {
                            continue;
                        }
                        Cell SpeedA11 = row.getCell(30);//获取第二列数据（单位名）
                        if (SpeedA11 != null) {
                            SpeedA11.setCellType(CellType.STRING);
                            Double s = Double.valueOf(SpeedA11.getStringCellValue());
                            modi.setSpeedA11((double) (Math.round(s * 10)) / 10);
                        } else {
                            continue;
                        }

                        Cell dw12 = row.getCell(31);//获取第二列数据（单位名）
                        if (dw12 != null) {
                            dw12.setCellType(CellType.STRING);
                            String ds = dw12.getStringCellValue();
                            modi.setDirectionA12(ds);
                        } else {
                            continue;
                        }
                        Cell SpeedA12 = row.getCell(32);//获取第二列数据（单位名）
                        if (SpeedA12 != null) {
                            SpeedA12.setCellType(CellType.STRING);
                            Double s = Double.valueOf(SpeedA12.getStringCellValue());
                            modi.setSpeedA12((double) (Math.round(s * 10)) / 10);
                        } else {
                            continue;
                        }

                        Cell dw13 = row.getCell(33);//获取第二列数据（单位名）
                        if (dw13 != null) {
                            dw13.setCellType(CellType.STRING);
                            String ds = dw13.getStringCellValue();
                            modi.setDirectionA13(ds);
                        } else {
                            continue;
                        }
                        Cell SpeedA13 = row.getCell(34);//获取第二列数据（单位名）
                        if (SpeedA13 != null) {
                            SpeedA13.setCellType(CellType.STRING);
                            Double s = Double.valueOf(SpeedA13.getStringCellValue());
                            modi.setSpeedA13((double) (Math.round(s * 10)) / 10);
                        } else {
                            continue;
                        }

                        Cell dw14 = row.getCell(35);//获取第二列数据（单位名）
                        if (dw14 != null) {
                            dw14.setCellType(CellType.STRING);
                            String ds = dw14.getStringCellValue();
                            modi.setDirectionA14(ds);
                        } else {
                            continue;
                        }
                        Cell SpeedA14 = row.getCell(36);//获取第二列数据（单位名）
                        if (SpeedA14 != null) {
                            SpeedA14.setCellType(CellType.STRING);
                            Double s = Double.valueOf(SpeedA14.getStringCellValue());
                            modi.setSpeedA14((double) (Math.round(s * 10)) / 10);
                        } else {
                            continue;
                        }

                        Cell dw15 = row.getCell(37);//获取第二列数据（单位名）
                        if (dw15 != null) {
                            dw15.setCellType(CellType.STRING);
                            String ds = dw15.getStringCellValue();
                            modi.setDirectionA15(ds);
                        } else {
                            continue;
                        }
                        Cell SpeedA15 = row.getCell(38);//获取第二列数据（单位名）
                        if (SpeedA15 != null) {
                            SpeedA15.setCellType(CellType.STRING);
                            Double s = Double.valueOf(SpeedA15.getStringCellValue());
                            modi.setSpeedA15((double) (Math.round(s * 10)) / 10);
                        } else {
                            continue;
                        }

                        Cell dw16 = row.getCell(39);//获取第二列数据（单位名）
                        if (dw16 != null) {
                            dw16.setCellType(CellType.STRING);
                            String ds = dw16.getStringCellValue();
                            modi.setDirectionA16(ds);
                        } else {
                            continue;
                        }
                        Cell SpeedA16 = row.getCell(40);//获取第二列数据（单位名）
                        if (SpeedA16 != null) {
                            SpeedA16.setCellType(CellType.STRING);
                            Double s = Double.valueOf(SpeedA16.getStringCellValue());
                            modi.setSpeedA16((double) (Math.round(s * 10)) / 10);
                        } else {
                            continue;
                        }


                        Cell dw17 = row.getCell(41);//获取第二列数据（单位名）
                        if (dw17 != null) {
                            dw17.setCellType(CellType.STRING);
                            String ds = dw17.getStringCellValue();
                            modi.setDirectionA17(ds);
                        } else {
                            continue;
                        }
                        Cell SpeedA17 = row.getCell(42);//获取第二列数据（单位名）
                        if (SpeedA17 != null) {
                            SpeedA17.setCellType(CellType.STRING);
                            Double s = Double.valueOf(SpeedA17.getStringCellValue());
                            modi.setSpeedA17((double) (Math.round(s * 10)) / 10);
                        } else {
                            continue;
                        }

                        Cell dw18 = row.getCell(43);//获取第二列数据（单位名）
                        if (dw18 != null) {
                            dw18.setCellType(CellType.STRING);
                            String ds = dw18.getStringCellValue();
                            modi.setDirectionA18(ds);
                        } else {
                            continue;
                        }
                        Cell SpeedA18 = row.getCell(44);//获取第二列数据（单位名）
                        if (SpeedA18 != null) {
                            SpeedA18.setCellType(CellType.STRING);
                            Double s = Double.valueOf(SpeedA18.getStringCellValue());
                            modi.setSpeedA18((double) (Math.round(s * 10)) / 10);
                        } else {
                            continue;
                        }

                        Cell dw19 = row.getCell(45);//获取第二列数据（单位名）
                        if (dw19 != null) {
                            dw19.setCellType(CellType.STRING);
                            String ds = dw19.getStringCellValue();
                            modi.setDirectionA19(ds);
                        } else {
                            continue;
                        }
                        Cell SpeedA19 = row.getCell(46);//获取第二列数据（单位名）
                        if (SpeedA19 != null) {
                            SpeedA19.setCellType(CellType.STRING);
                            Double s = Double.valueOf(SpeedA19.getStringCellValue());
                            modi.setSpeedA19((double) (Math.round(s * 10)) / 10);
                        } else {
                            continue;
                        }

                        Cell dw20 = row.getCell(47);//获取第二列数据（单位名）
                        if (dw20 != null) {
                            dw20.setCellType(CellType.STRING);
                            String ds = dw20.getStringCellValue();
                            modi.setDirectionA20(ds);
                        } else {
                            continue;
                        }
                        Cell SpeedA20 = row.getCell(48);//获取第二列数据（单位名）
                        if (SpeedA20 != null) {
                            SpeedA20.setCellType(CellType.STRING);
                            Double s = Double.valueOf(SpeedA20.getStringCellValue());
                            modi.setSpeedA20((double) (Math.round(s * 10)) / 10);
                        } else {
                            continue;
                        }
                        windService.save(modi);
                        //entityList.add(modi);
                    }
                }
//                if (entityList!=null){
//                    boolean b = windService.saveBatch(entityList);
//                }
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //多文件上传
    @PostMapping(value = "/batch/upload")
    public void handleFileUpload(HttpServletRequest request,Long userId) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request)
                .getFiles("file");
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        for (int i1 = 0; i1 < files.size(); ++i1) {
            file = files.get(i1);
            //获取文件名称
            String fileName = file.getOriginalFilename();
            //获取文件后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            //重新生成文件名
            fileName = System.currentTimeMillis() + suffixName;
            //指定存储路径
            String filePath = "G:\\upload\\";
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    stream = new BufferedOutputStream(new FileOutputStream(
                            new File(filePath + fileName)));
                    stream.write(bytes);
                    stream.close();
                    //file.transferTo(new File(filePath+fileName));
                } catch (Exception e) {
                    e.getMessage();
                }
                try {
                    File excel = new File(filePath + fileName);

                    if (excel.isFile() && excel.exists()) {
                        String[] split = excel.getName().split("\\.");  //.是特殊字符，需要转义！！！！！
                        Workbook wb;
                        //根据文件后缀（xls/xlsx）进行判断
                        if ("xls".equals(split[1])) {
                            FileInputStream fis = new FileInputStream(excel);   //文件流对象
                            wb = new HSSFWorkbook(fis);
                        } else if ("xlsx".equals(split[1])) {
                            wb = new XSSFWorkbook(excel);
                        } else {
                            System.out.println("文件类型错误!");
                            return;
                        }
                        //开始解析
                        Sheet sheet = wb.getSheetAt(0);     //读取sheet 0


                        int firstRowIndex = sheet.getFirstRowNum();   //第一行是列名，所以不读
                        int lastRowIndex = sheet.getLastRowNum();


                        Collection<ObserveWind> entityList = new ArrayList<>();
                        for (int i = firstRowIndex; i < lastRowIndex - 1; i++) {//遍历行
                            Row row = sheet.getRow(i);
                            ObserveWind modi = new ObserveWind();
                            modi.setUserId(userId);

                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                Cell dwtime = row.getCell(0);//获取第一列数据（单位名）
                                if (dwtime != null) {
                                    StringBuffer buffer = new StringBuffer();
                                    String[] sub = dwtime.toString().split("-");
                                    if (sub.length > 1) {
                                        String s1 = StingInt(sub[1]);
                                        if (sub[0].indexOf("0") == 0) {
                                            String ss = sub[0].replace("0", "");
                                            StringBuffer bu = buffer.append(sub[2]).append("-" + s1).append("-" + ss);
                                            Date parse = sdf.parse(bu.toString());
                                            modi.setDay(parse);
                                        } else {
                                            StringBuffer bu = buffer.append(sub[2]).append("-" + s1).append("-" + sub[0]);
                                            Date parse = sdf.parse(bu.toString());
                                            modi.setDay(parse);
                                        }
                                    }
                                }

                                Cell dw21 = row.getCell(1);//获取第二列数据（单位名）
                                if (dw21 != null) {
                                    dw21.setCellType(CellType.STRING);
                                    String ds = dw21.getStringCellValue();
                                    modi.setDirectionA21(ds);
                                } else {
                                    continue;
                                }
                                Cell SpeedA21 = row.getCell(2);//获取第二列数据（单位名）
                                if (SpeedA21 != null) {
                                    SpeedA21.setCellType(CellType.STRING);
                                    Double s = Double.valueOf(SpeedA21.getStringCellValue());
                                    modi.setSpeedA21((double) (Math.round(s * 10)) / 10);
                                } else {
                                    continue;
                                }
                                Cell dw22 = row.getCell(3);//获取第二列数据（单位名）
                                if (dw22 != null) {
                                    dw22.setCellType(CellType.STRING);
                                    String ds = dw22.getStringCellValue();
                                    modi.setDirectionA22(ds);
                                } else {
                                    continue;
                                }
                                Cell SpeedA22 = row.getCell(4);//获取第二列数据（单位名）
                                if (SpeedA22 != null) {
                                    SpeedA22.setCellType(CellType.STRING);
                                    Double s = Double.valueOf(SpeedA22.getStringCellValue());
                                    modi.setSpeedA22((double) (Math.round(s * 10)) / 10);
                                } else {
                                    continue;
                                }

                                Cell dw23 = row.getCell(5);//获取第二列数据（单位名）
                                if (dw23 != null) {
                                    dw23.setCellType(CellType.STRING);
                                    String ds = dw23.getStringCellValue();
                                    modi.setDirectionA23(ds);
                                } else {
                                    continue;
                                }
                                Cell SpeedA23 = row.getCell(6);//获取第二列数据（单位名）
                                if (SpeedA23 != null) {
                                    SpeedA23.setCellType(CellType.STRING);
                                    Double s = Double.valueOf(SpeedA23.getStringCellValue());
                                    modi.setSpeedA23((double) (Math.round(s * 10)) / 10);
                                } else {
                                    continue;
                                }

                                Cell dw0 = row.getCell(7);//获取第二列数据（单位名）
                                if (dw0 != null) {
                                    dw0.setCellType(CellType.STRING);
                                    String ds = dw0.getStringCellValue();
                                    modi.setDirectionA0(ds);
                                } else {
                                    continue;
                                }
                                Cell SpeedA0 = row.getCell(8);//获取第二列数据（单位名）
                                if (SpeedA0 != null) {
                                    SpeedA0.setCellType(CellType.STRING);
                                    Double s = Double.valueOf(SpeedA0.getStringCellValue());
                                    modi.setSpeedA0((double) (Math.round(s * 10)) / 10);
                                } else {
                                    continue;
                                }

                                Cell dw1 = row.getCell(9);//获取第二列数据（单位名）
                                if (dw1 != null) {
                                    dw1.setCellType(CellType.STRING);
                                    String ds = dw1.getStringCellValue();
                                    modi.setDirectionA1(ds);
                                } else {
                                    continue;
                                }
                                Cell SpeedA1 = row.getCell(10);//获取第二列数据（单位名）
                                if (SpeedA1 != null) {
                                    SpeedA1.setCellType(CellType.STRING);
                                    Double s = Double.valueOf(SpeedA1.getStringCellValue());
                                    modi.setSpeedA1((double) (Math.round(s * 10)) / 10);
                                } else {
                                    continue;
                                }

                                Cell dw2 = row.getCell(11);//获取第二列数据（单位名）
                                if (dw2 != null) {
                                    dw2.setCellType(CellType.STRING);
                                    String ds = dw2.getStringCellValue();
                                    modi.setDirectionA2(ds);
                                } else {
                                    continue;
                                }
                                Cell SpeedA2 = row.getCell(12);//获取第二列数据（单位名）
                                if (SpeedA2 != null) {
                                    SpeedA2.setCellType(CellType.STRING);
                                    Double s = Double.valueOf(SpeedA2.getStringCellValue());
                                    modi.setSpeedA2((double) (Math.round(s * 10)) / 10);
                                } else {
                                    continue;
                                }

                                Cell dw3 = row.getCell(13);//获取第二列数据（单位名）
                                if (dw3 != null) {
                                    dw3.setCellType(CellType.STRING);
                                    String ds = dw3.getStringCellValue();
                                    modi.setDirectionA3(ds);
                                } else {
                                    continue;
                                }
                                Cell SpeedA3 = row.getCell(14);//获取第二列数据（单位名）
                                if (SpeedA3 != null) {
                                    SpeedA3.setCellType(CellType.STRING);
                                    Double s = Double.valueOf(SpeedA3.getStringCellValue());
                                    modi.setSpeedA3((double) (Math.round(s * 10)) / 10);
                                } else {
                                    continue;
                                }

                                Cell dw4 = row.getCell(15);//获取第二列数据（单位名）
                                if (dw4 != null) {
                                    dw4.setCellType(CellType.STRING);
                                    String ds = dw4.getStringCellValue();
                                    modi.setDirectionA4(ds);
                                } else {
                                    continue;
                                }
                                Cell SpeedA4 = row.getCell(16);//获取第二列数据（单位名）
                                if (SpeedA4 != null) {
                                    SpeedA4.setCellType(CellType.STRING);
                                    Double s = Double.valueOf(SpeedA4.getStringCellValue());
                                    modi.setSpeedA4((double) (Math.round(s * 10)) / 10);
                                } else {
                                    continue;
                                }

                                Cell dw5 = row.getCell(17);//获取第二列数据（单位名）
                                if (dw5 != null) {
                                    dw5.setCellType(CellType.STRING);
                                    String ds = dw5.getStringCellValue();
                                    modi.setDirectionA5(ds);
                                } else {
                                    continue;
                                }
                                Cell SpeedA5 = row.getCell(18);//获取第二列数据（单位名）
                                if (SpeedA5 != null) {
                                    SpeedA5.setCellType(CellType.STRING);
                                    Double s = Double.valueOf(SpeedA5.getStringCellValue());
                                    modi.setSpeedA5((double) (Math.round(s * 10)) / 10);
                                } else {
                                    continue;
                                }

                                Cell dw6 = row.getCell(19);//获取第二列数据（单位名）
                                if (dw6 != null) {
                                    dw6.setCellType(CellType.STRING);
                                    String ds = dw6.getStringCellValue();
                                    modi.setDirectionA6(ds);
                                } else {
                                    continue;
                                }
                                Cell SpeedA6 = row.getCell(20);//获取第二列数据（单位名）
                                if (SpeedA6 != null) {
                                    SpeedA6.setCellType(CellType.STRING);
                                    Double s = Double.valueOf(SpeedA6.getStringCellValue());
                                    modi.setSpeedA6((double) (Math.round(s * 10)) / 10);
                                } else {
                                    continue;
                                }

                                Cell dw7 = row.getCell(21);//获取第二列数据（单位名）
                                if (dw7 != null) {
                                    dw7.setCellType(CellType.STRING);
                                    String ds = dw7.getStringCellValue();
                                    modi.setDirectionA7(ds);
                                } else {
                                    continue;
                                }
                                Cell SpeedA7 = row.getCell(22);//获取第二列数据（单位名）
                                if (SpeedA7 != null) {
                                    SpeedA7.setCellType(CellType.STRING);
                                    Double s = Double.valueOf(SpeedA7.getStringCellValue());
                                    modi.setSpeedA7((double) (Math.round(s * 10)) / 10);
                                } else {
                                    continue;
                                }

                                Cell dw8 = row.getCell(23);//获取第二列数据（单位名）
                                if (dw8 != null) {
                                    dw8.setCellType(CellType.STRING);
                                    String ds = dw8.getStringCellValue();
                                    modi.setDirectionA8(ds);
                                } else {
                                    continue;
                                }
                                Cell SpeedA8 = row.getCell(24);//获取第二列数据（单位名）
                                if (SpeedA8 != null) {
                                    SpeedA8.setCellType(CellType.STRING);
                                    Double s = Double.valueOf(SpeedA8.getStringCellValue());
                                    modi.setSpeedA8((double) (Math.round(s * 10)) / 10);
                                } else {
                                    continue;
                                }

                                Cell dw9 = row.getCell(25);//获取第二列数据（单位名）
                                if (dw9 != null) {
                                    dw9.setCellType(CellType.STRING);
                                    String ds = dw9.getStringCellValue();
                                    modi.setDirectionA9(ds);
                                } else {
                                    continue;
                                }
                                Cell SpeedA9 = row.getCell(26);//获取第二列数据（单位名）
                                if (SpeedA9 != null) {
                                    SpeedA9.setCellType(CellType.STRING);
                                    Double s = Double.valueOf(SpeedA9.getStringCellValue());
                                    modi.setSpeedA9((double) (Math.round(s * 10)) / 10);
                                } else {
                                    continue;
                                }

                                Cell dw10 = row.getCell(27);//获取第二列数据（单位名）
                                if (dw10 != null) {
                                    dw10.setCellType(CellType.STRING);
                                    String ds = dw10.getStringCellValue();
                                    modi.setDirectionA10(ds);
                                } else {
                                    continue;
                                }
                                Cell SpeedA10 = row.getCell(28);//获取第二列数据（单位名）
                                if (SpeedA10 != null) {
                                    SpeedA10.setCellType(CellType.STRING);
                                    Double s = Double.valueOf(SpeedA10.getStringCellValue());
                                    modi.setSpeedA10((double) (Math.round(s * 10)) / 10);
                                } else {
                                    continue;
                                }

                                Cell dw11 = row.getCell(29);//获取第二列数据（单位名）
                                if (dw11 != null) {
                                    dw11.setCellType(CellType.STRING);
                                    String ds = dw11.getStringCellValue();
                                    modi.setDirectionA11(ds);
                                } else {
                                    continue;
                                }
                                Cell SpeedA11 = row.getCell(30);//获取第二列数据（单位名）
                                if (SpeedA11 != null) {
                                    SpeedA11.setCellType(CellType.STRING);
                                    Double s = Double.valueOf(SpeedA11.getStringCellValue());
                                    modi.setSpeedA11((double) (Math.round(s * 10)) / 10);
                                } else {
                                    continue;
                                }

                                Cell dw12 = row.getCell(31);//获取第二列数据（单位名）
                                if (dw12 != null) {
                                    dw12.setCellType(CellType.STRING);
                                    String ds = dw12.getStringCellValue();
                                    modi.setDirectionA12(ds);
                                } else {
                                    continue;
                                }
                                Cell SpeedA12 = row.getCell(32);//获取第二列数据（单位名）
                                if (SpeedA12 != null) {
                                    SpeedA12.setCellType(CellType.STRING);
                                    Double s = Double.valueOf(SpeedA12.getStringCellValue());
                                    modi.setSpeedA12((double) (Math.round(s * 10)) / 10);
                                } else {
                                    continue;
                                }

                                Cell dw13 = row.getCell(33);//获取第二列数据（单位名）
                                if (dw13 != null) {
                                    dw13.setCellType(CellType.STRING);
                                    String ds = dw13.getStringCellValue();
                                    modi.setDirectionA13(ds);
                                } else {
                                    continue;
                                }
                                Cell SpeedA13 = row.getCell(34);//获取第二列数据（单位名）
                                if (SpeedA13 != null) {
                                    SpeedA13.setCellType(CellType.STRING);
                                    Double s = Double.valueOf(SpeedA13.getStringCellValue());
                                    modi.setSpeedA13((double) (Math.round(s * 10)) / 10);
                                } else {
                                    continue;
                                }

                                Cell dw14 = row.getCell(35);//获取第二列数据（单位名）
                                if (dw14 != null) {
                                    dw14.setCellType(CellType.STRING);
                                    String ds = dw14.getStringCellValue();
                                    modi.setDirectionA14(ds);
                                } else {
                                    continue;
                                }
                                Cell SpeedA14 = row.getCell(36);//获取第二列数据（单位名）
                                if (SpeedA14 != null) {
                                    SpeedA14.setCellType(CellType.STRING);
                                    Double s = Double.valueOf(SpeedA14.getStringCellValue());
                                    modi.setSpeedA14((double) (Math.round(s * 10)) / 10);
                                } else {
                                    continue;
                                }

                                Cell dw15 = row.getCell(37);//获取第二列数据（单位名）
                                if (dw15 != null) {
                                    dw15.setCellType(CellType.STRING);
                                    String ds = dw15.getStringCellValue();
                                    modi.setDirectionA15(ds);
                                } else {
                                    continue;
                                }
                                Cell SpeedA15 = row.getCell(38);//获取第二列数据（单位名）
                                if (SpeedA15 != null) {
                                    SpeedA15.setCellType(CellType.STRING);
                                    Double s = Double.valueOf(SpeedA15.getStringCellValue());
                                    modi.setSpeedA15((double) (Math.round(s * 10)) / 10);
                                } else {
                                    continue;
                                }

                                Cell dw16 = row.getCell(39);//获取第二列数据（单位名）
                                if (dw16 != null) {
                                    dw16.setCellType(CellType.STRING);
                                    String ds = dw16.getStringCellValue();
                                    modi.setDirectionA16(ds);
                                } else {
                                    continue;
                                }
                                Cell SpeedA16 = row.getCell(40);//获取第二列数据（单位名）
                                if (SpeedA16 != null) {
                                    SpeedA16.setCellType(CellType.STRING);
                                    Double s = Double.valueOf(SpeedA16.getStringCellValue());
                                    modi.setSpeedA16((double) (Math.round(s * 10)) / 10);
                                } else {
                                    continue;
                                }


                                Cell dw17 = row.getCell(41);//获取第二列数据（单位名）
                                if (dw17 != null) {
                                    dw17.setCellType(CellType.STRING);
                                    String ds = dw17.getStringCellValue();
                                    modi.setDirectionA17(ds);
                                } else {
                                    continue;
                                }
                                Cell SpeedA17 = row.getCell(42);//获取第二列数据（单位名）
                                if (SpeedA17 != null) {
                                    SpeedA17.setCellType(CellType.STRING);
                                    Double s = Double.valueOf(SpeedA17.getStringCellValue());
                                    modi.setSpeedA17((double) (Math.round(s * 10)) / 10);
                                } else {
                                    continue;
                                }

                                Cell dw18 = row.getCell(43);//获取第二列数据（单位名）
                                if (dw18 != null) {
                                    dw18.setCellType(CellType.STRING);
                                    String ds = dw18.getStringCellValue();
                                    modi.setDirectionA18(ds);
                                } else {
                                    continue;
                                }
                                Cell SpeedA18 = row.getCell(44);//获取第二列数据（单位名）
                                if (SpeedA18 != null) {
                                    SpeedA18.setCellType(CellType.STRING);
                                    Double s = Double.valueOf(SpeedA18.getStringCellValue());
                                    modi.setSpeedA18((double) (Math.round(s * 10)) / 10);
                                } else {
                                    continue;
                                }

                                Cell dw19 = row.getCell(45);//获取第二列数据（单位名）
                                if (dw19 != null) {
                                    dw19.setCellType(CellType.STRING);
                                    String ds = dw19.getStringCellValue();
                                    modi.setDirectionA19(ds);
                                } else {
                                    continue;
                                }
                                Cell SpeedA19 = row.getCell(46);//获取第二列数据（单位名）
                                if (SpeedA19 != null) {
                                    SpeedA19.setCellType(CellType.STRING);
                                    Double s = Double.valueOf(SpeedA19.getStringCellValue());
                                    modi.setSpeedA19((double) (Math.round(s * 10)) / 10);
                                } else {
                                    continue;
                                }

                                Cell dw20 = row.getCell(47);//获取第二列数据（单位名）
                                if (dw20 != null) {
                                    dw20.setCellType(CellType.STRING);
                                    String ds = dw20.getStringCellValue();
                                    modi.setDirectionA20(ds);
                                } else {
                                    continue;
                                }
                                Cell SpeedA20 = row.getCell(48);//获取第二列数据（单位名）
                                if (SpeedA20 != null) {
                                    SpeedA20.setCellType(CellType.STRING);
                                    Double s = Double.valueOf(SpeedA20.getStringCellValue());
                                    modi.setSpeedA20((double) (Math.round(s * 10)) / 10);
                                } else {
                                    continue;
                                }

                                entityList.add(modi);
                            }
                            //entityList




                        }else {
                        System.out.println("找不到指定的文件");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }

    }



}
