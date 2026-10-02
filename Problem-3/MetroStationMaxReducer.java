import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class MetroStationMaxReducer
        extends Reducer<Text, Text, Text, IntWritable> {

    private final Text maxStation = new Text();
    private final IntWritable maxPassengers = new IntWritable();

    @Override
    public void reduce(Text key, Iterable<Text> values, Context context)
            throws IOException, InterruptedException {

        String station = "";
        int max = Integer.MIN_VALUE;

        for (Text value : values) {

            String[] fields = value.toString().split("\\t");

            if (fields.length == 2) {
                int passengers = Integer.parseInt(fields[1]);

                if (passengers > max) {
                    max = passengers;
                    station = fields[0];
                }
            }
        }

        maxStation.set(station);
        maxPassengers.set(max);

        context.write(maxStation, maxPassengers);
    }
}