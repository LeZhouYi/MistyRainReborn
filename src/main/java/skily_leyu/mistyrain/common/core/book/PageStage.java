package skily_leyu.mistyrain.common.core.book;

public class PageStage {
    private boolean isChapter; //true=当前停在目录，false=当前停在内容
    private int page; //所在页数
    private int maxPage; //最大页数
    private int index; //当前页所属目录/内容在整体的序列

    public PageStage(){
        this.isChapter = true;
        this.page = 0;
        this.maxPage = 0;
        this.index = 0;
    }

    public boolean isRoot(){
        return this.isChapter && this.page == 0;
    }

}
