import java.io.IOException;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

public class BalancedRouteMapper extends Mapper<LongWritable, Text, Text, Text> {
  public void map(LongWritable k, Text v, Context c) throws IOException, InterruptedException {
    String[] f = v.toString().split(",");
    if (f.length != 4 || f[0].equals("routeId")) return;
    c.write(new Text(f[0]), new Text(f[1] + "," + f[3]));
  }
}
