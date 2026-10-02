package com.metro.problem4;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class RidershipMapper
        extends Mapper<LongWritable, Text, Text, IntWritable> {

    private final Text hour = new Text();
    private final IntWritable passengerCount = new IntWritable();

    @Override
    public void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {

        String line = value.toString().trim();

        if (line.isEmpty() || line.startsWith("routeId")) {
            return;
        }

        String[] fields = line.split(",");

        if (fields.length != 4) {
            return;
        }

        String timestamp = fields[2].trim();
        String passengers = fields[3].trim();

        if (timestamp.length() < 13) {
            return;
        }

        try {
            String extractedHour = timestamp.substring(11, 13);
            int count = Integer.parseInt(passengers);

            hour.set(extractedHour);
            passengerCount.set(count);

            context.write(hour, passengerCount);

        } catch (NumberFormatException e) {
            return;
        }
    }
}