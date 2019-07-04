package com.deepoove.poi.tl.lt.data;

import com.deepoove.poi.data.RenderData;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.InputStream;

/**
 * 图片
 *
 * @author <a href="mailto:1728209643@qq.com">lan</a>
 * @class com.deepoove.poi.tl.lt.data.PicRenderData
 * @date 2019-07-04 11:07
 */
public class PicRenderData implements RenderData {


    /**
     * 图片宽度
     */
    private int width;
    /**
     * 图片高度
     */
    private int height;

    /**
     * 图片二进制数据
     */
    private InputStream in;

    /**
     * @see XWPFDocument
     */
    private int type;

    private String filename;

    public PicRenderData(int width, int height, InputStream in, int type, String filename) {
        this.width = width;
        this.height = height;
        this.in = in;
        this.type = type;
        this.filename = filename;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public InputStream getIn() {
        return in;
    }

    public void setIn(InputStream in) {
        this.in = in;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
