package skily_leyu.mistyrain.common.core.book;

import org.apache.logging.log4j.Level;
import skily_leyu.mistyrain.common.MistyRain;

public class PageStage {
    private boolean isChapter; //true=当前停在目录，false=当前停在内容
    private boolean isRoot; //是否是根目录
    private int page; //所在页数

    private int index; //自身所属位置

    public PageStage() {
        this(true, true, 0, -1);
    }


    public PageStage(boolean isChapter, boolean isRoot, int page, int index) {
        this.isChapter = true;
        this.page = 0;
        this.isRoot = isRoot;
        this.index = index;
    }

    public boolean isRoot() {
        return this.isRoot;
    }

    public int getPage() {
        return page;
    }

    public boolean isChapter() {
        return this.isChapter;
    }

    public void addPage(int add) {
        this.page += add;
    } //更新页数

    public int getIndex() {
        return this.index;
    }
}
