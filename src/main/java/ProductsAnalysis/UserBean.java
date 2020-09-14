package ProductsAnalysis;

public class UserBean {

    public Long userId;
    public Long itemId;
    public Long operator;
    public String category;
    public Long timeStamp;

    public UserBean() {
    }

    public UserBean(Long userId, Long itemId, Long operator, String category, Long timeStamp) {
        this.userId = userId;
        this.itemId = itemId;
        this.operator = operator;
        this.category = category;
        this.timeStamp = timeStamp;
    }

    public static UserBean of (Long userId, Long itemId, Long operator, String category, Long timeStamp){
        return new UserBean(userId,itemId,operator,category,timeStamp);
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "userId=" + userId +
                ", itemId=" + itemId +
                ", operator=" + operator +
                ", category='" + category + '\'' +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
