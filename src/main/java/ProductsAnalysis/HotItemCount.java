package ProductsAnalysis;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

public class HotItemCount {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        environment.setStreamTimeCharacteristic(TimeCharacteristic.IngestionTime);
        DataStreamSource<String> source = environment.readTextFile("./src/lib/UserBehavior.csv");
        source.flatMap(new FlatMapFunction<String, UserBean>() {
            public void flatMap(String s, Collector<UserBean> collector) throws Exception {
                String[] split = s.split(",");
                Long userId = Long.valueOf(split[0]);
                Long itemId = Long.valueOf(split[1]);
                Long operator = Long.valueOf(split[2]);
                String category = split[3];
                Long timeStamp = Long.valueOf(split[4]);
                collector.collect(UserBean.of(userId,itemId,operator,category,timeStamp));

            }
        }).assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor<UserBean>(Time.seconds(0)) {
            @Override
            public long extractTimestamp(UserBean userBean) {
                return userBean.timeStamp*1000;
            }
        }).filter(new FilterFunction<UserBean>() {
            public boolean filter(UserBean userBean) throws Exception {
                return "pv".equals(userBean.category);
            }
        }).keyBy("itemId")
                .timeWindow(Time.minutes(60),Time.minutes(5))
                .aggregate(new CountAgg(),new WindowResult())
                .keyBy("windowEnd")
                .process(new Analysis(3))
                .print();



        environment.execute();
    }
}
