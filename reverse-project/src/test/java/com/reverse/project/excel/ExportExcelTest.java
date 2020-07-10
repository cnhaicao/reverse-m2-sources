package com.reverse.project.excel;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

/**
 * 写excel测试
 *
 * @author guoguoqiang
 * @since 2020年07月10日
 */
public class ExportExcelTest {
    @Test
    public void tableExport() {
        List<String> row1 = Lists.newArrayList("aa", "bb", "cc");
        List<String> row2 = Lists.newArrayList("aa1", "bb1", "cc1", "dd1");
        List<String> row3 = Lists.newArrayList("aa2", "bb2", "cc2", "dd2");
        List<String> row4 = Lists.newArrayList("aa3", "bb3", "cc3", "dd3");
        List<String> row5 = Lists.newArrayList("aa4", "bb4", "cc4", "dd4");

        List<List<String>> rows = Lists.newArrayList(row1, row2, row3, row4, row5);

        //通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getWriter("./docs/writeTest.xlsx");
        //通过构造方法创建writer
        //ExcelWriter writer = new ExcelWriter("d:/writeTest.xls");

        //跳过当前行，既第一行，非必须，在此演示用
        writer.passCurrentRow();

        //合并单元格后的标题行，使用默认标题样式
        writer.merge(row1.size() - 1, "测试标题");
        //一次性写出内容，强制输出标题
        writer.write(rows, true);
        //关闭writer，释放内存
        writer.close();
    }

    @Test
    public void test() {
        List<String> row1 = Lists.newArrayList("aa", "bb", "cc");
        List<String> row2 = Lists.newArrayList("aa1", "bb1", "cc1", "dd1");
        List<String> row3 = Lists.newArrayList("aa2", "bb2", "cc2", "dd2");
        List<String> row4 = Lists.newArrayList("aa3", "bb3", "cc3", "dd3");
        List<String> row5 = Lists.newArrayList("aa4", "bb4", "cc4", "dd4");
        String excel = "./docs/writeTest-2.xlsx";
        try (ExcelWriter writer = new ExcelWriter(excel, "表格1")) {
            //跳过当前行，既第一行，非必须，在此演示用
            writer.passCurrentRow();

            //合并单元格后的标题行，使用默认标题样式
            writer.merge(5, "测试标题");
            writer.writeRow(row1);
            writer.writeRow(row2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (ExcelWriter writer = new ExcelWriter(excel, "表格2")) {
            //跳过当前行，既第一行，非必须，在此演示用
            writer.passCurrentRow();
            //合并单元格后的标题行，使用默认标题样式
            writer.merge(5, "测试标题");
            writer.writeRow(row1);
            writer.writeRow(row2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
