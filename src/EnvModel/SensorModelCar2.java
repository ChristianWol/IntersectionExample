package EnvModel;

public interface SensorModelCar2  extends SensorModelCar {

	public void get_distance_to_car1(Point_XY point); // simplified model
	public double get_vy_of_car1();
	public double get_distance_to_car3();
}
