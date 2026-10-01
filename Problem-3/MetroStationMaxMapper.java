import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MetroStationMaxMapper
        extends Mapper<Object, Text, Text, Text> {

    private final Text maxKey = new Text("MAX");
    private final Text stationData = new Text();

    @Override
    public void map(Object key, Text value, Context context)
            throws IOException, InterruptedException {

        String line = value.toString().trim();

        if (line.isEmpty()) {
            return;
        }

        String[] fields = line.split("\\s+");

        if (fields.length == 2) {
            stationData.set(fields[0] + "\t" + fields[1]);
            context.write(maxKey, stationData);
        }
    }
}