import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class RouteTimeMapper
        extends Mapper<Object, Text, Text, IntWritable> {

    private Text routeTime = new Text();
    private IntWritable passengerCount = new IntWritable();

    @Override
    public void map(Object key, Text value, Context context)
            throws IOException, InterruptedException {

        String line = value.toString();

        // Skip header
        if (line.startsWith("routeId")) {
            return;
        }

        String[] fields = line.split(",");

        if (fields.length >= 4) {
            String routeId = fields[0].trim();
            String timestamp = fields[2].trim();
            String count = fields[3].trim();

            try {
                int passengers = Integer.parseInt(count);

                routeTime.set(routeId + "\t" + timestamp);
                passengerCount.set(passengers);

                context.write(routeTime, passengerCount);

            } catch (NumberFormatException e) {
                // Ignore invalid rows
            }
        }
    }
}