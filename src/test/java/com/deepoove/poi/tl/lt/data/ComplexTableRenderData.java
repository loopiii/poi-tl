package com.deepoove.poi.tl.lt.data;

import com.deepoove.poi.data.RenderData;
import com.deepoove.poi.data.style.TableStyle;

import java.util.List;

/**
 * @author <a href="mailto:1728209643@qq.com">lan</a>
 * @class com.deepoove.poi.tl.lt.data.ComplexTableRenderData
 * @date 2019-07-04 10:24
 */
public class ComplexTableRenderData implements RenderData {

    /**
     * 通用边距的表格宽度：A4(20.99*29.6),页边距为3.17*2.54
     */
    public static final float WIDTH_A4_FULL = 14.65f;
    /**
     * 窄边距的表格宽度：A4(20.99*29.6),页边距为1.27*1.27
     */
    public static final float WIDTH_A4_NARROW_FULL = 18.45f;
    /**
     * 适中边距的表格宽度：A4(20.99*29.6),页边距为1.91*2.54
     */
    public static final float WIDTH_A4_MEDIUM_FULL = 17.17f;
    /**
     * 宽边距的表格宽度：A4(20.99*29.6),页边距为5.08*2.54
     */
    public static final float WIDTH_A4_EXTEND_FULL = 10.83f;

    /**
     * 表格头部数据，可为空
     */
    private ComplexRowRenderData header;
    /**
     * 表格数据
     */
    private List<ComplexRowRenderData> rowDatas;

    /**
     * 表格数据为空展示的文案
     */
    private String noDatadesc;

    /**
     * 设置表格整体样式：填充色、整个表格在文档中的对齐方式
     */
    private TableStyle style;

    /**
     * 最大宽度为：页面宽度-页边距宽度*2 单位：cm
     */
    private float width;

    /**
     * 基础表格：行数据填充
     *
     * @param datas
     */
    public ComplexTableRenderData(List<ComplexRowRenderData> datas) {
        this(null, datas);
    }

    /**
     * 基础表格：行数据填充且指定宽度
     *
     * @param datas
     * @param width
     */
    public ComplexTableRenderData(List<ComplexRowRenderData> datas, float width) {
        this(null, datas, width);
    }

    /**
     * 空数据表格
     *
     * @param headers
     * @param noDatadesc
     */
    public ComplexTableRenderData(ComplexRowRenderData headers, String noDatadesc) {
        this(headers, null, noDatadesc, WIDTH_A4_FULL);
    }

    public ComplexTableRenderData(ComplexRowRenderData headers, List<ComplexRowRenderData> datas) {
        this(headers, datas, WIDTH_A4_FULL);
    }

    public ComplexTableRenderData(ComplexRowRenderData headers, List<ComplexRowRenderData> datas, float width) {
        this(headers, datas, null, width);
    }

    /**
     * @param headers
     *            表格头
     * @param datas
     *            表格数据
     * @param noDatadesc
     *            没有数据显示的文案
     * @param width
     *            宽度
     */
    public ComplexTableRenderData(ComplexRowRenderData headers, List<ComplexRowRenderData> datas, String noDatadesc,
                               float width) {
        this.header = headers;
        this.rowDatas = datas;
        this.noDatadesc = noDatadesc;
        this.width = width;
    }

    public boolean isSetHeader() {
        return null != header && header.size() > 0;
    }

    public boolean isSetBody() {
        return null != rowDatas && rowDatas.size() > 0;
    }

    public String getNoDatadesc() {
        return noDatadesc;
    }

    public void setNoDatadesc(String noDatadesc) {
        this.noDatadesc = noDatadesc;
    }

    public ComplexRowRenderData getHeader() {
        return header;
    }

    public void setHeader(ComplexRowRenderData header) {
        this.header = header;
    }

    public List<ComplexRowRenderData> getDatas() {
        return rowDatas;
    }

    public void setDatas(List<ComplexRowRenderData> datas) {
        this.rowDatas = datas;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public TableStyle getStyle() {
        return style;
    }

    public void setStyle(TableStyle style) {
        this.style = style;
    }

}
