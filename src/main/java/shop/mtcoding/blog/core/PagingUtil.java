package shop.mtcoding.blog.core;

public class PagingUtil {
    public static boolean isFirst(int currentPage) {
        return currentPage == 0? true:false;
    }
    public static boolean isLast(int currentPage, int totalCount){
        int totalPageCount = getTotalPageCount(totalCount);
        return currentPage+1==totalPageCount?true:false;
    }

    public static int getTotalPageCount(int totalCount) {
        int remainCount = totalCount%Constant.PAGING_COUNT;
        int totalPageCount = totalCount / Constant.PAGING_COUNT;
        if (remainCount>0){
            totalPageCount = totalPageCount +1;
        }
        return totalPageCount;
    }
}
