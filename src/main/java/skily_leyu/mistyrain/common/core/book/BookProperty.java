package skily_leyu.mistyrain.common.core.book;

public class BookProperty {

    private int color; //字体颜色
    private int textWidth; //文本宽度
    private int textHeight; //文本高度
    private int textOffset;
    private int lineHeight;

    public BookProperty() { /* 按需设置 */ }

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

    public BookProperty setLineHeight(int lineHeight) {
        this.lineHeight = lineHeight;
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

    public int getLineHeight() {
        return lineHeight;
    }

    /**
     * 每一页所需要显示的行数
     */
    public int getUnit(int page){
        int unit = this.textHeight/this.lineHeight;
        if(page==0){
            return unit-this.textOffset/this.lineHeight;
        }
        return unit;
    }

    /**
     * 当前页从第几行开始
     * @param page
     * @return
     */
    public int getLineIndex(int page){
        if(page<1){
            return 0;
        }else{
            return (page-1)*this.getUnit(page)+this.getUnit(0);
        }
    }

}
