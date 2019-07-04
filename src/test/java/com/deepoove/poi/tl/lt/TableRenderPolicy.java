package com.deepoove.poi.tl.lt;

import com.deepoove.poi.data.MiniTableRenderData;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.policy.DynamicTableRenderPolicy;
import com.deepoove.poi.policy.MiniTableRenderPolicy;
import com.deepoove.poi.util.TableTools;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author <a href="mailto:1728209643@qq.com">lan</a>
 * @class com.deepoove.poi.tl.lt.TableRenderPolicy
 * @date 2019-07-04 08:24
 */
public class TableRenderPolicy extends DynamicTableRenderPolicy {


    @Override
    public void render(XWPFTable table, Object data) {
        MiniTableRenderData tableRenderData = (MiniTableRenderData) data;
        table.removeRow(2);
        List<RowRenderData> datas = tableRenderData.getDatas();
        for (int i = 0; i < datas.size(); i++) {
            XWPFTableRow insertNewTableRow = table.insertNewTableRow(2 + i);
            for (int j = 0; j < 7; j++) {
                insertNewTableRow.createCell();
            }
            TableTools.mergeCellsHorizonal(table, 2 + i, 0, 3);
            MiniTableRenderPolicy.Helper.renderRow(table, 2 + i, datas.get(i));

            XWPFTableCell imageCell = insertNewTableRow.getCell(3);
            List<XWPFParagraph> paragraphs = imageCell.getParagraphs();
            XWPFParagraph newPara = paragraphs.get(0);
            XWPFRun imageCellRunn = newPara.createRun();
            try {
                imageCellRunn.addPicture(new FileInputStream("/Users/lan/Desktop/2.png"), XWPFDocument.PICTURE_TYPE_PNG, "1.png", Units.toEMU(100), Units.toEMU(100));
            } catch (InvalidFormatException | IOException e) {
                e.printStackTrace();
            }

        }

    }
}
