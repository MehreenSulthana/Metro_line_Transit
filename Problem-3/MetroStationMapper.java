import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MetroStationMapper
        extends Mapper<Object, Text, Text, IntWritable> {

    private final Text station = new Text();
    private final IntWritable passengers = new IntWritable();

    @Override
    public void map(Object key, Text value, Context context)
            throws IOException, InterruptedException {

        String line = value.toString();

        if (line.startsWith("routeId")) {
            return;
        }

        String[] fields = line.split(",");

        if (fields.length == 4) {
            station.set(fields[1].trim());
            passengers.set(Integer.parseInt(fields[3].trim()));

            context.write(station, passengers);
        }
    }
}