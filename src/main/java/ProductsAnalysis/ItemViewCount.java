package ProductsAnalysis;

public class ItemViewCount {
    public Long itemId;
    public Long viewCount;
    public Long windowEnd;

    public ItemViewCount() {
    }

    public ItemViewCount(Long itemId, Long viewCount, Long windowEnd) {
        this.itemId = itemId;
        this.viewCount = viewCount;
        this.windowEnd = windowEnd;
    }


    public static ItemViewCount of (Long itemId, Long viewCount, Long windowEnd){
        return new ItemViewCount(itemId,viewCount,windowEnd);
    }
}
