package Main;
import EnvModel.*;

public class Car2 extends Car {

	static final double V_MAX = 13.88888888888; // m/s ; 50km/h
	static final double A_MAX =  0.67087979175; // 0 -> 100km/h in 9,1 s
	static final double BRAKE_MAX = -3.5;
	
	Car2(String name, double x, double y, double v, double direction, double a, double mass) {
		super(name, x, y, v, direction, a, mass);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drive_behavior() {
		SensorModelCar2 smc2 = (SensorModelCar2) this.smc;
		Point_XY point = new Point_XY();
		smc2.get_distance_to_car1(point);
//		double dist_x = point.x;
		double dist_y = point.y;
		//double car1v = smc2.get_vy_of_car1();
		

		
		if ((dist_y-5) < (1 * this.v * 3.6)){
			set_state("TOO_CLOSE");
			this.a = BRAKE_MAX; // TODO Verfeinern, so dass nicht so stark gebremst wird + Widerstände vom Fahren nutzen
		} else if ((dist_y - 5) > (2 * this.v * 3.6)) {
			set_state("FETCH_UP");
			this.a = A_MAX;	
		} else {
			set_state("KEEP_SPEED");
			this.a = 0.0; // TODO: Beschleunigung auf geschätzten widerstand setzen
		}
		

		
		if ((smc2.get_distance_to_car3() - 5) < 1/4 * this.v){
			set_state("EMERGENCY_BRAKE");
			this.a = BRAKE_MAX;
		}
		
		
		// TODO Auto-generated method stub
		
	}
	
}
