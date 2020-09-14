package ProductsAnalysis;

import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple1;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

public class WindowResult implements WindowFunction<Long,ItemViewCount,Tuple, TimeWindow> {


    public void apply(Tuple tuple, TimeWindow timeWindow, Iterable<Long> iterable, Collector<ItemViewCount> collector) throws Exception {

        Long itemId = ((Tuple1<Long>)tuple).f0;
        Long count = iterable.iterator().next();
        Long timeEnd = timeWindow.getEnd();
        collector.collect(ItemViewCount.of(itemId,count,timeEnd));
    }
}
