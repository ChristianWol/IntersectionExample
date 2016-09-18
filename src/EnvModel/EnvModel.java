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
	private int counter_sim_steps = 0;
	private final int MAX_SIM_STEPS = 100*100000;
	
	public EnvModel(Car car1, Car car2, Car car3){
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
			if (false && counter_sim_steps % (10) == 0) {
				System.out.println("===== " + counter_sim_steps + " =====" );
				this.car1.print_xy();
				this.car2.print_xy();
				this.car3.print_xy();
			}
		}
	}
	
	private boolean are_invariants_violated(){
		double dist13 = distance(car1, car3);
		double dist23 = distance(car2, car3);
		double dist12 = distance(car1, car2);
		boolean are_violated = ((dist13 < 3) ||
				(dist12 < 3) ||
				(dist23 < 3));
		if (are_violated){
			System.out.println("distance violated -- system not safe");
			System.out.println("dist12 "+dist12);
			System.out.println("dist23 "+dist23);
			System.out.println("dist13 "+dist13);
			
		}
		return are_violated;
		
		
	}
	
	private double distance (Car car_a, Car car_b){
		return Math.sqrt((car_a.x-car_b.x)*(car_a.x-car_b.x)+(car_a.y-car_b.y)*(car_a.y-car_b.y));
	}
	
	private boolean are_test_goals_reached(){
		return (car3.x < -3.0 &&
				car2.y >  3.0 &&
				car1.y >  3.0 );
	}
	
	private boolean test_terminated(){
		counter_sim_steps++;
		return (are_invariants_violated() || counter_sim_steps > MAX_SIM_STEPS || are_test_goals_reached());
	}

	@Override
	public void get_distance_to_car3(Point_XY point) {
		point.x = new Double(car3.x - car1.x);
		point.y = new Double(car3.y - car1.y);
	}
	
	@Override
	public void get_distance_to_car1(Point_XY point) {
		point.x = new Double (car1.x - car2.x);
		point.y = new Double (car1.y - car2.y);
	}

	@Override
	public void get_velocity_vector_of_car3(Point_XY point) {
		point.y = new Double (car3.v * Math.sin(car3.direction));
		point.x = new Double (car3.v * Math.cos(car3.direction));
	}

	@Override
	public double get_vy_of_car1() {
		return Math.cos(car1.direction)*car1.v;
	}
	
	@Override
	public double get_distance_to_car3() {
		return distance(car2, car3);
	}
}
