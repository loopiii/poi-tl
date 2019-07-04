package com.deepoove.poi.tl.lt.data;

import com.deepoove.poi.data.RenderData;
import com.deepoove.poi.data.style.TableStyle;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 复杂行
 *
 * @author <a href="mailto:1728209643@qq.com">lan</a>
 * @class com.deepoove.poi.tl.lt.data.ComplexRowRenderData
 * @date 2019-07-04 10:14
 */
public class ComplexRowRenderData implements RenderData {

    private List<ComplexCellRenderData> cellDatas;

    private TableStyle rowStyle;

    public ComplexRowRenderData() {
    }

    public ComplexRowRenderData(List<ComplexCellRenderData> cellDatas) {
        this.cellDatas = cellDatas;
    }


    public static ComplexRowRenderData build(RenderData... cellData) {
        if (cellData == null) {
            return new ComplexRowRenderData();
        }
        return new ComplexRowRenderData(Arrays.stream(cellData)
                .map((Function<RenderData, ComplexCellRenderData>) ComplexCellRenderData::new)
                .collect(Collectors.toList())
        );
    }

    public static ComplexRowRenderData build(ComplexCellRenderData... cellData) {
        if (cellData == null) {
            return new ComplexRowRenderData();
        }
        return new ComplexRowRenderData(Arrays.asList(cellData));
    }

    public int size() {
        return null == cellDatas ? 0 : cellDatas.size();
    }


    public List<ComplexCellRenderData> getCellDatas() {
        return cellDatas;
    }

    public void setCellDatas(List<ComplexCellRenderData> cellDatas) {
        this.cellDatas = cellDatas;
    }

    public TableStyle getRowStyle() {
        return rowStyle;
    }

    public void setRowStyle(TableStyle rowStyle) {
        this.rowStyle = rowStyle;
    }
}
