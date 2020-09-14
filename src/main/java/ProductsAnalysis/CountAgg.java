package ProductsAnalysis;

import org.apache.flink.api.common.functions.AggregateFunction;

public class CountAgg implements AggregateFunction<UserBean,Long,Long> {
    public Long createAccumulator() {
        return 0L;
    }

    public Long add(UserBean userBean, Long aLong) {
        return aLong+1;
    }

    public Long getResult(Long aLong) {
        return aLong;
    }

    public Long merge(Long aLong, Long acc1) {
        return acc1 + aLong;
    }
}
