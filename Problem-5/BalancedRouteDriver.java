import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class BalancedRouteDriver {
  public static void main(String[] a) throws Exception {
    Job j = Job.getInstance(new Configuration(), "balanced route");
    j.setJarByClass(BalancedRouteDriver.class);
    j.setMapperClass(BalancedRouteMapper.class);
    j.setReducerClass(BalancedRouteReducer.class);
    j.setNumReduceTasks(1);
    j.setOutputKeyClass(Text.class);
    j.setOutputValueClass(Text.class);
    FileInputFormat.addInputPath(j, new Path(a[0]));
    FileOutputFormat.setOutputPath(j, new Path(a[1]));
    System.exit(j.waitForCompletion(true) ? 0 : 1);
  }
}
