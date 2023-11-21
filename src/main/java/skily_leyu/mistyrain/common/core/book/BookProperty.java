package skily_leyu.mistyrain.common.core.book;

public class BookProperty {

    private int color; //字体颜色
    private int textWidth; //文本宽度
    private int textHeight; //文本高度
    private int textOffset;

    public BookProperty(){ /* 按需设置 */ }

    public BookProperty setColor(int color) {
        this.color = color;
        return this;
    }

    public BookProperty setTextHeight(int textHeight) {
        this.textHeight = textHeight;
        return this;
    }

    public BookProperty setTextWidth(int textWidth) {
        this.textWidth = textWidth;
        return this;
    }

    public BookProperty setTextOffset(int textOffset) {
        this.textOffset = textOffset;
        return this;
    }

    public int getColor() {
        return color;
    }

    public int getTextHeight() {
        return textHeight;
    }

    public int getTextOffset() {
        return textOffset;
    }

    public int getTextWidth() {
        return textWidth;
    }
}
