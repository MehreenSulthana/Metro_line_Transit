import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class RouteTimeReducer
        extends Reducer<Text, IntWritable, Text, IntWritable> {

    private IntWritable result = new IntWritable();

    @Override
    public void reduce(Text key, Iterable<IntWritable> values,
                       Context context)
            throws IOException, InterruptedException {

        int maxPassengers = 0;

        for (IntWritable value : values) {
            if (value.get() > maxPassengers) {
                maxPassengers = value.get();
            }
        }

        result.set(maxPassengers);
        context.write(key, result);
    }
}