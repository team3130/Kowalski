package frc.team3130.robot.subsystems;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.team3130.robot.util.SplineInterpolator;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class WheelSpeedCalculations implements Subsystem {
	
	private static Comparator<DataPoint> compPoint = new Comparator<DataPoint>() {
		@Override
		public int compare(DataPoint p1, DataPoint p2) {
			if(p1.greaterThan(p2))
				return 1;
			else if(p1.lessThan(p2))
				return -1;
			return 0;
		}
	};
	
	private static class DataPoint
	{
		double distance;
		double speed;
		
		public DataPoint(double dist, double speed)
		{
			distance = dist;
			this.speed = speed;
		}
		/**
		public DataPoint(String point)
		{
			if(point.charAt(0) == '(' && point.charAt(point.length()-1) == ')' && point.contains(",")){
				point = point.substring(1, point.length()-1);
				String[] parts = point.split(",");
				distance = Double.parseDouble(parts[0]);
				speed = Double.parseDouble(parts[1]);
			}
		}
		*/
		@Override
		public String toString()
		{
			return "("+distance+","+speed+")";
		}
		
		public boolean greaterThan(DataPoint other)
		{
			return distance > other.distance;
		}
		
		public boolean lessThan(DataPoint other)
		{
			return distance < other.distance;
		}
		
		@SuppressWarnings("unused")
		public boolean equals(DataPoint other)
		{
			return distance == other.distance;
		}
	}

	private ArrayList<DataPoint> data_MainStorage;
	private SplineInterpolator speedCurve;
	//private final String FILEPATH;

	public WheelSpeedCalculations(String path)
	{
		//FILEPATH = path;
		
		data_MainStorage = new ArrayList<DataPoint>();
		data_MainStorage.add(new DataPoint(0.0, 3500));
		data_MainStorage.add(new DataPoint(71.0, 3900));
		data_MainStorage.add(new DataPoint(80.0, 3770));
		data_MainStorage.add(new DataPoint(90.0, 3760));
		data_MainStorage.add(new DataPoint(100.0, 3775));
		data_MainStorage.add(new DataPoint(110.0, 3750));
		data_MainStorage.add(new DataPoint(120.0, 3750));
		data_MainStorage.add(new DataPoint(130.0, 3775));
		data_MainStorage.add(new DataPoint(140.0, 3825));
		data_MainStorage.add(new DataPoint(150.0, 3890));
		data_MainStorage.add(new DataPoint(160.0, 3885));
		data_MainStorage.add(new DataPoint(170.0, 3885));
		data_MainStorage.add(new DataPoint(180.0, 3950));
		data_MainStorage.add(new DataPoint(190.0, 4100));
		data_MainStorage.add(new DataPoint(200.0, 4150));
		data_MainStorage.add(new DataPoint(210.0, 4240));
		data_MainStorage.add(new DataPoint(220.0, 4270));
		data_MainStorage.add(new DataPoint(230.0, 4320));
		data_MainStorage.add(new DataPoint(240.0, 4400));
		data_MainStorage.add(new DataPoint(250.0, 4500));
		data_MainStorage.add(new DataPoint(260.0, 4630));
		data_MainStorage.add(new DataPoint(270.0, 4750));
		data_MainStorage.add(new DataPoint(280.0, 4775));
		data_MainStorage.add(new DataPoint(287.0, 5350));























		//ReadFile();
		speedCurve = null;
		ReloadCurve();
	}
	
 	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		//setDefaultCommand(new MySpecialCommand());
	}
	
	
 	public void AddPoint(double dist, double speed)
 	{
 		for(DataPoint p : data_MainStorage){
 			if(Math.abs(p.distance - dist) < Preferences.getInstance().getDouble("DataPoint Distance Variance", .01))
 				return;
 		}
 		
 		data_MainStorage.add(new DataPoint(dist, speed));
 		data_MainStorage.sort(compPoint);
 		SmartDashboard.putNumber("Number of Points", data_MainStorage.size());
 		//SaveToFile();
 		ReloadCurve();
 	}
 	
	public void ReloadCurve()
	{
		ArrayList<Double> data_Dist = new ArrayList<Double>();
		ArrayList<Double> data_Speed = new ArrayList<Double>();
		
		for(int iii = 0; iii < data_MainStorage.size(); iii++){
			DataPoint pt = data_MainStorage.get(iii);
			data_Dist.add(pt.distance);
			data_Speed.add(pt.speed);
		}
		
		speedCurve = SplineInterpolator.createMonotoneCubicSpline(data_Dist, data_Speed);
	}
	/**
	public void SaveToFile()
	{
		FileWriter out = null;
		try{
			out = new FileWriter(FILEPATH);
		}catch(IOException ex){
			ex.printStackTrace();
			return;
		}
		
		try{
			for(int iii = 0; iii < data_MainStorage.size(); iii++){
				out.write(data_MainStorage.get(iii).toString() + '\n');
			}
			out.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	*/
	/**
	public void ReadFile()
	{
		data_MainStorage.clear();

		System.out.println("Open Read");
		
		try(BufferedReader br = new BufferedReader(new FileReader(FILEPATH))) {
		    for(String line; (line = br.readLine()) != null; ) {
		    	System.out.println(line);
		        if(!line.equals(""))data_MainStorage.add(new DataPoint(line));
		    }
		    // line is not visible here.
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("DoneReading");
		data_MainStorage.sort(compPoint);
		for (DataPoint dataPoint : data_MainStorage) {
			System.out.println(dataPoint);
		}
		ReloadCurve();
	}
*/
	public void WipeData()
	{
		data_MainStorage.clear();
		data_MainStorage.add(new DataPoint(0,3000));	//TODO: Get resonable closest range values
		data_MainStorage.add(new DataPoint(1000, 4000));	//TODO: Get resonable farthest range values
		//SaveToFile();
	}

	public double GetSpeed(Double dist)
	{
		return speedCurve.interpolate(dist);
	}
}
