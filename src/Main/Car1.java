package Main;
import EnvModel.*;

public class Car1 extends Car {

	static final double V_MAX = 13.88888888888; // m/s ; 50km/h
	static final double A_MAX =  0.67087979175; // 0 -> 100km/h in 9,1 s
	static final double BRAKE_MAX = -3.5;
			
	Car1(String name, double x, double y, double v, double direction, double a, double mass) {
		super(name, x, y, v, direction, a, mass);
		// TODO Auto-generated constructor stub
	}
	
	public void drive_behavior(){
		Double car3_dist_x_Double = new Double(Double.NaN);
		Double car3_dist_y_Double = new Double(Double.NaN);
		SensorModelCar1 smc1 = (SensorModelCar1) this.smc;
		smc1.get_distance_to_car3(car3_dist_x_Double, car3_dist_y_Double);
		double car3_dist_x = car3_dist_x_Double.doubleValue();
		double car3_dist_y = car3_dist_y_Double.doubleValue();
		
		if (car3_dist_x < 0 || car3_dist_y < 0){
			return; // no collision possible anymore
		}
		
		if (passing_possible()){
			if (v < V_MAX){ // TODO: Aktoren und Senoren sauber trennen
				this.a = A_MAX;
			}
		} else {
			
		}
	}
	
	private boolean passing_possible(){
		return true; // TODO
	}

}
