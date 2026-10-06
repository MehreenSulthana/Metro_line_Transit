import java.io.IOException;
import java.util.*;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class BalancedRouteReducer extends Reducer<Text, Text, Text, Text> {
  String bal, con;
  double lo = 1e9, hi = -1;

  public void reduce(Text k, Iterable<Text> vs, Context c) throws IOException, InterruptedException {
    Map<String, Long> m = new HashMap<>();
    for (Text t : vs) {
      String[] p = t.toString().split(",");
      m.merge(p[0], Long.parseLong(p[1]), Long::sum);
    }
    double mean = 0, var = 0;
    for (long x : m.values()) mean += x;
    mean /= m.size();
    for (long x : m.values()) var += (x - mean) * (x - mean);
    double cv = Math.sqrt(var / m.size()) / mean;

    c.write(k, new Text(String.format("CV=%.4f", cv)));
    if (cv < lo) { lo = cv; bal = k.toString(); }
    if (cv > hi) { hi = cv; con = k.toString(); }
  }

  protected void cleanup(Context c) throws IOException, InterruptedException {
    c.write(new Text("MOST_BALANCED"), new Text(bal));
    c.write(new Text("MOST_CONCENTRATED"), new Text(con));
  }
}
