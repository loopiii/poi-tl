package com.deepoove.poi.tl.lt;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.*;
import com.deepoove.poi.tl.lt.data.ComplexCellRenderData;
import com.deepoove.poi.tl.lt.data.ComplexRowRenderData;
import com.deepoove.poi.tl.lt.data.ComplexTableRenderData;
import com.deepoove.poi.tl.lt.data.PicRenderData;
import com.deepoove.poi.tl.lt.policy.ComplexTableRenderPolicy;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author <a href="mailto:1728209643@qq.com">lan</a>
 * @class com.deepoove.poi.tl.lt.Ep
 * @date 2019-07-04 07:37
 */
public class Ep {

    String outPath = "/Users/lan/Desktop/out.docx";
    String templatePath;
    Map<String, Object> data = new HashMap<>();

    @Before
    public void setup() {
        URL url = Thread.currentThread().getContextClassLoader().getResource("word.txt");
        File dummyFile = new File(Objects.requireNonNull(url).getFile());
        templatePath = new File(dummyFile.getParent() + "/lt").getPath() + "/";
    }

    @Test
    public void testComplexTable() throws IOException {
        ComplexCellRenderData<TextRenderData> cellRenderData = new ComplexCellRenderData<>();
        cellRenderData.setRenderData(new TextRenderData("FFFFFF", "姓名"));
        ComplexCellRenderData<TextRenderData> cellRenderData1 = new ComplexCellRenderData<>();
        cellRenderData1.setRenderData(new TextRenderData("FFFFFF", "学历"));
        ComplexCellRenderData<TextRenderData> cellRenderData2 = new ComplexCellRenderData<>();
        cellRenderData2.setRenderData(new TextRenderData("FFFFFF", "照片"));
        ComplexCellRenderData<TextRenderData> cellRenderData3 = new ComplexCellRenderData<>();
        cellRenderData3.setRenderData(new TextRenderData("FFFFFF", "头像"));

        ComplexRowRenderData header = ComplexRowRenderData.build(cellRenderData, cellRenderData1, cellRenderData2, cellRenderData3);

        ComplexCellRenderData<TextRenderData> cellRenderData4 = new ComplexCellRenderData<>();
        cellRenderData4.setRenderData(new TextRenderData("小李"));
        ComplexCellRenderData<TextRenderData> cellRenderData11 = new ComplexCellRenderData<>();
        cellRenderData11.setRenderData(new TextRenderData("本科"));
        ComplexCellRenderData<PicRenderData> cellRenderData21 = new ComplexCellRenderData<>();
        cellRenderData21.setRenderData(new PicRenderData(200, 320, new FileInputStream(templatePath + "2.png"), XWPFDocument.PICTURE_TYPE_PNG, "x.png"));
        ComplexCellRenderData<PicRenderData> cellRenderData31 = new ComplexCellRenderData<>();
        cellRenderData31.setRenderData(new PicRenderData(200, 320, new FileInputStream("/Users/lan/Desktop/3.png"), XWPFDocument.PICTURE_TYPE_PNG, "x.png"));

        ComplexRowRenderData rowRenderData = ComplexRowRenderData.build(cellRenderData4, cellRenderData11, cellRenderData21, cellRenderData31);

        data.put("detail_table", new ComplexTableRenderData(header, Arrays.asList(header, rowRenderData, rowRenderData)));
        Configure configure = Configure.newBuilder().customPolicy("detail_table", new ComplexTableRenderPolicy()).build();
        XWPFTemplate template = XWPFTemplate.compile(templatePath + "4.docx", configure).render(data);
        FileOutputStream out = new FileOutputStream(outPath);
        template.write(out);
        out.flush();
        out.close();
        template.close();
    }

    /**
     * 列表
     */
    @Test
    public void testList() throws IOException {

        data.put("feature", new NumbericRenderData(Arrays.asList(
                new TextRenderData("Plug-in grammar"),
                new TextRenderData("Supports word text, header..."),
                new TextRenderData("Not just templates, but also style templates")
        )));
        after("template3.docx");
    }

    /**
     * 导出的表格中包含图片
     */
    @Test
    public void testTable2() throws IOException {
        RowRenderData header = RowRenderData.build(new TextRenderData("FFFFFF", "姓名"), new TextRenderData("FFFFFF", "学历"));

        RowRenderData row0 = RowRenderData.build("张三", "研究生");
        RowRenderData row1 = RowRenderData.build("李四", "博士");
        RowRenderData row2 = RowRenderData.build("王五", "博士后");

        data.put("detail_table", new MiniTableRenderData(header, Arrays.asList(row0, row1, row2)));
        Configure configure = Configure.newBuilder().customPolicy("detail_table", new TableRenderPolicy()).build();
//        XWPFTemplate template = XWPFTemplate.compile(templatePath + "template2.docx").render(data);
        XWPFTemplate template = XWPFTemplate.compile(templatePath + "4.docx", configure).render(data);
        FileOutputStream out = new FileOutputStream(outPath);
        template.write(out);
        out.flush();
        out.close();
        template.close();
    }

    /**
     * 表格MiniTableRenderData
     */
    @Test
    public void testTable() throws IOException {
        RowRenderData header = RowRenderData.build(new TextRenderData("FFFFFF", "姓名"), new TextRenderData("FFFFFF", "学历"));

        RowRenderData row0 = RowRenderData.build("张三", "研究生");
        RowRenderData row1 = RowRenderData.build("李四", "博士");
        RowRenderData row2 = RowRenderData.build("王五", "博士后");

        data.put("table", new MiniTableRenderData(header, Arrays.asList(row0, row1, row2)));
        after("template2.docx");
    }

    public void after(String templateName) throws IOException {
        XWPFTemplate template = XWPFTemplate.compile(templatePath + templateName).render(data);
        FileOutputStream out = new FileOutputStream(outPath);
        template.write(out);
        out.flush();
        out.close();
        template.close();
    }

    /**
     * 文字和图片模板导出
     */
    @Test
    public void testTextPic() throws IOException {
        data.put("title", "第一次xx结案测结果");
        data.put("pic", new PictureRenderData(200, 320, ".png", new FileInputStream(templatePath + "2.png")));
        data.put("content", "第一次xx结asdasldka垃圾坑垃圾是大师级的垃圾是的煎熬了圣诞节阿来得及奥斯卡大家按实际恐龙当家奥斯卡大家爱看时间阿拉基框架爱离开大家按实际的垃圾的空间爱看的骄傲手机啊案测结果");
        after("template1.docx");
    }
}
