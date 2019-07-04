package com.deepoove.poi.tl.lt.data;

import com.deepoove.poi.data.RenderData;
import com.deepoove.poi.data.style.TableStyle;

/**
 * 复杂的单元格
 *
 * @author <a href="mailto:1728209643@qq.com">lan</a>
 * @class com.deepoove.poi.tl.lt.data.ComplexCellRenderData
 * @date 2019-07-04 10:12
 */
public class ComplexCellRenderData<T extends RenderData> {

    private T renderData;

    protected TableStyle cellStyle;


    public ComplexCellRenderData() {
    }

    public ComplexCellRenderData(T renderData) {
        this.renderData = renderData;
    }

    public T getRenderData() {
        return renderData;
    }

    public void setRenderData(T renderData) {
        this.renderData = renderData;
    }

    public TableStyle getCellStyle() {
        return cellStyle;
    }

    public void setCellStyle(TableStyle cellStyle) {
        this.cellStyle = cellStyle;
    }
}
