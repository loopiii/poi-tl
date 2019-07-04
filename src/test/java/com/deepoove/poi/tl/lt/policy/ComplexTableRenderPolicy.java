package com.deepoove.poi.tl.lt.policy;

import com.deepoove.poi.NiceXWPFDocument;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.RenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.TableStyle;
import com.deepoove.poi.policy.AbstractRenderPolicy;
import com.deepoove.poi.policy.TextRenderPolicy;
import com.deepoove.poi.template.run.RunTemplate;
import com.deepoove.poi.tl.lt.data.ComplexCellRenderData;
import com.deepoove.poi.tl.lt.data.ComplexRowRenderData;
import com.deepoove.poi.tl.lt.data.ComplexTableRenderData;
import com.deepoove.poi.tl.lt.data.PicRenderData;
import com.deepoove.poi.util.ObjectUtils;
import com.deepoove.poi.util.StyleUtils;
import com.deepoove.poi.util.TableTools;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author <a href="mailto:1728209643@qq.com">lan</a>
 * @class com.deepoove.poi.tl.lt.policy.ComplexTableRenderPolicy
 * @date 2019-07-04 10:30
 */
public class ComplexTableRenderPolicy extends AbstractRenderPolicy<ComplexTableRenderData> {
    @Override
    public void doRender(RunTemplate runTemplate, ComplexTableRenderData data, XWPFTemplate template) throws Exception {
        NiceXWPFDocument doc = template.getXWPFDocument();
        XWPFRun run = runTemplate.getRun();

        int row = data.getDatas().size(), col;
        if (!data.isSetHeader()) {
            col = getMaxColumFromData(data.getDatas());
        } else {
            row++;
            col = data.getHeader().size();
        }

        // 2.创建表格
        XWPFTable table = doc.insertNewTable(run, row, col);
        initBasicTable(table, col, data.getWidth(), data.getStyle());

        // 3.渲染数据
        int startRow = 0;
        if (data.isSetHeader()) Helper.renderRow(table, startRow++, data.getHeader());
        for (ComplexRowRenderData d : data.getDatas()) {
            Helper.renderRow(table, startRow++, d);
        }
    }

    private void initBasicTable(XWPFTable table, int col, float width, TableStyle style) {
        TableTools.widthTable(table, width, col);
        TableTools.borderTable(table, 4);
        StyleUtils.styleTable(table, style);
    }

    private int getMaxColumFromData(List<ComplexRowRenderData> datas) {
        int maxColom = 0;
        for (ComplexRowRenderData obj : datas) {
            if (null == obj) continue;
            if (obj.size() > maxColom) maxColom = obj.size();
        }
        return maxColom;
    }

    public static class Helper {

        /**
         * 填充表格一行的数据
         *
         * @param table
         * @param row     第几行
         * @param rowData 行数据：确保行数据的大小不超过表格该行的单元格数量
         */
        public static void renderRow(XWPFTable table, int row, ComplexRowRenderData rowData) {
            if (null == rowData || rowData.size() <= 0) return;
            XWPFTableRow tableRow = table.getRow(row);
            ObjectUtils.requireNonNull(tableRow, "Row " + row + " do not exist in the table");

            TableStyle rowStyle = rowData.getRowStyle();
            List<ComplexCellRenderData> cellList = rowData.getCellDatas();
            XWPFTableCell cell = null;

            for (int i = 0; i < cellList.size(); i++) {
                cell = tableRow.getCell(i);
                if (null == cell) {
                    logger.warn("Extra cell data at row {}, but no extra cell: col {}", row, cell);
                    break;
                }
                renderCell(cell, cellList.get(i), rowStyle);
            }
        }

        public static void renderCell(XWPFTableCell cell, ComplexCellRenderData cellData,
                                      TableStyle rowStyle) {
            TableStyle cellStyle = (null == cellData.getCellStyle() ? rowStyle
                    : cellData.getCellStyle());

            // 处理单元格样式
            if (null != cellStyle && null != cellStyle.getBackgroundColor()) {
                cell.setColor(cellStyle.getBackgroundColor());
            }

            RenderData renderData = cellData.getRenderData();

            CTTc ctTc = cell.getCTTc();
            CTP ctP = (ctTc.sizeOfPArray() == 0) ? ctTc.addNewP() : ctTc.getPArray(0);
            XWPFParagraph par = new XWPFParagraph(ctP, cell);
            StyleUtils.styleTableParagraph(par, cellStyle);

            if (renderData instanceof PicRenderData) {
                PicRenderData picRenderData = (PicRenderData) renderData;
                XWPFRun run = par.createRun();
                try {
                    run.addPicture(picRenderData.getIn(), picRenderData.getType(), picRenderData.getFilename(), Units.toEMU(picRenderData.getWidth()), Units.toEMU(picRenderData.getHeight()));
                } catch (InvalidFormatException | IOException e) {
                    e.printStackTrace();
                }
            } else if (renderData instanceof TextRenderData) {
                TextRenderData textRenderData = (TextRenderData) renderData;
                String text = textRenderData.getText();
                String[] fragment = text.split(TextRenderPolicy.REGEX_LINE_CHARACTOR, -1);

                if (fragment.length <= 1) {
                    TextRenderPolicy.Helper.renderTextRun(par.createRun(), renderData);
                } else {
                    // 单元格内换行的用不同段落来特殊处理
                    XWPFRun run;
                    for (int j = 0; j < fragment.length; j++) {
                        if (0 != j) {
                            par = cell.addParagraph();
                            StyleUtils.styleTableParagraph(par, cellStyle);
                        }
                        run = par.createRun();
                        StyleUtils.styleRun(run, textRenderData.getStyle());
                        run.setText(fragment[j]);
                    }
                }
            }
        }
    }
}
