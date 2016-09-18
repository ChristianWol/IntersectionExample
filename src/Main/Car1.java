package Main;
import EnvModel.*;

public class Car1 extends Car {

	static final double V_MAX = 13.88888888888; // m/s ; 50km/h
	static final double A_MAX =  0.67087979175; // 0 -> 100km/h in 9,1 s
	static final double BRAKE_MAX = -3.5;
	
	double v_0 = 0.0;
	double brake_way = 0.0;
			
	Car1(String name, double x, double y, double v, double direction, double a, double mass) {
		super(name, x, y, v, direction, a, mass);
		// TODO Auto-generated constructor stub
	}
	
	public void drive_behavior(){
		SensorModelCar1 smc1 = (SensorModelCar1) this.smc;
		
		Point_XY point = new Point_XY();
		smc1.get_distance_to_car3(point);
		double car3_dist_x = point.x;
		double car3_dist_y = point.y;
		
//		System.out.println("car3_dist_x, car3_dist_y: " + car3_dist_x + "," +car3_dist_y);
		
		smc1.get_velocity_vector_of_car3(point);
		double car3_vx = point.x;
		double car3_vy = point.y;
		
//		System.out.println("car3_vx, car3_vy: " + car3_vx + "," +car3_vy);
		
		double my_vx = Math.cos(this.direction)*this.v;
		double my_vy = Math.sin(this.direction)*this.v;
		
//		System.out.println("my_vx, my_vy: " + my_vx + "," +my_vy);
		
		double collision_time_x =  car3_dist_x/(my_vx-car3_vx);
		double collision_time_y =  car3_dist_y/(my_vy-car3_vy);
		double collision_time = (collision_time_x-collision_time_y < 10E-5 || collision_time_y-collision_time_x < 10E-5)? collision_time_x : Double.NaN;
		
//		System.out.println("collision_timex:" + collision_time_x);
//		System.out.println("collision_timey:" + collision_time_y);
//		System.out.println("collision_time:" + collision_time);
//		
		if (car3_dist_x < 0 || car3_dist_y < 0 || Double.isNaN(collision_time)){
			set_state("NO_COLLISION_POSSIBLE");
			if (this.v < V_MAX){ // TODO: Aktoren und Senoren sauber trennen
				this.a = A_MAX;
			}
			return; // no collision possible anymore
		}
		
		boolean passing_possible = false;
		{
			// Simplfy, as we only test orthogonal crossing
			// v1*t + 1/2 * a1max * t² > 1.15 * (v3*t + 1/2 * a3max * t²) // 15% safety margin
			double safety_margin = 1.15;
			double b = (my_vx - safety_margin * car3_vx);
			double car3_assumed_a = 0; // Hack!
			double a = (0.5 *BRAKE_MAX - safety_margin*0.5*car3_assumed_a);
			double t_help = -b/a;
			passing_possible = (a*t_help*t_help+b*t_help > 0);
		}
		if (passing_possible){
			set_state("PASSING_POSSIBLE");
			if (this.v < V_MAX){ // TODO: Aktoren und Senoren sauber trennen
				this.a = A_MAX;
			}
			return;
		} else {
			
			if (this.a >= 0.0){
				this.brake_way = (3 + this.y);
				this.v_0 = this.v;
			}
			// Calculate how to brake
			double new_a = (this.v_0)*(this.v_0)*1/2/this.brake_way;
			if (new_a < BRAKE_MAX){
				set_state("CRASH");
				this.a = BRAKE_MAX;
			} else {
				set_state("BRAKE");
				this.a = new_a;
			}
			
		}
		
	}


}
