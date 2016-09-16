package EnvModel;

public class EnvModel implements SensorModelCar1, SensorModelCar2{
	
	//  ^
	//  |       |   |
	//  |       |   |  
	//  | ------+   +----------
	// 0+
	//  | ------+   +----------
	//  |       |   |
	//  |       |   | 6 meters wide
	//  +---------+------------------> x
	//            0 at middle

	private Car car1, car2, car3;
	int counter_sim_steps = 0;
	
	EnvModel(Car car1, Car car2, Car car3){
		this.car1 = car1;
		this.car2 = car2;
		this.car3 = car3;
	}
	
	public void execute_test(){
		double dt = 0.01; // 10 msec
		while (!test_terminated()){
			this.car1.execute_step(dt);
			this.car2.execute_step(dt);
			this.car3.execute_step(dt);
			this.car1.print_xy();
			this.car2.print_xy();
			this.car3.print_xy();
		}
	}
	
	private boolean are_invariants_violated(){
		return (distance(car1, car3) < 3 ||
				distance(car1, car2) < 3 ||
				distance(car2, car3) < 3);
		
		
	}
	
	private double distance (Car car_a, Car car_b){
		return Math.sqrt((car_a.x-car_b.x)*(car_a.x-car_b.x)+(car_a.y-car_b.y)*(car_a.y-car_b.y));
	}
	
	private boolean are_test_goals_reached(){
		return (car3.x < -3.0 &&
				car2.y > 3 &&
				car1.y > 3);
	}
	
	private boolean test_terminated(){
		return (are_invariants_violated() || counter_sim_steps > 100*300 || are_test_goals_reached());
	}

	@Override
	public void get_distance_to_car3(Double x, Double y) {
		x = (car3.x - car1.x);
		y = (car3.y - car1.y);
	}
	
	@Override
	public void get_distance_to_car1(Double x, Double y) {
		x = (car1.x - car2.x);
		y = (car1.y - car2.y);
	}
}
