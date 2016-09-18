package EnvModel;

public abstract class Car {

	static final double V_PHY_MAX = 20; // 72 kmh
	
	protected String name;
	
	protected double x; // in coordinates in m
	protected double y; // in coordinates in m
	
	protected double v; // in m/s
	
	protected double direction; // 0..360° 
	protected double a; // in m/s²
	
	private double mass;
	
	protected SensorModelCar smc;
	
	private String last_state = "none";
	
	public Car(String name, double x, double y, double v, double direction, double a, double mass){
		this.name = name;
		this.x = x;
		this.y = y;
		this.v = v;
		this.direction = direction;
		this.a = a;
		this.mass = mass;
	}
	
	protected void set_state(String new_state){
		if (!last_state.equals(new_state)){
			System.out.printf("%s: (%s)->(%s)\n",this.name, last_state,new_state);
		}
		last_state = new_state;
	}
	
	private double correct_minor_error(double value) {
		if ((value>0 && value < 10E-5) || (value<0 && value > -10E-5)){
			return 0.0;
		} else {
			return value;
		}
	}
	
	protected void execute_step(double dt){
		double vy = Math.sin(direction)*v;
		double vx = Math.cos(direction)*v;
		vy = correct_minor_error(vy);
		vx = correct_minor_error(vx);
//		System.out.println(this.name+"  vx="+vx);
//		System.out.println(this.name+"  vy="+vy);
		
//		// Wind-Widerstand
//		double AIR_DENSITY = 1.2041;
//		double END_FACE = 10;
//		double c_w = 0.4;
//		double F_w =  1/2 * AIR_DENSITY * END_FACE * c_w * v * v;
//		
//		// Rollwiderstand
//		double c_R = 0.0135;
//		double F_N = 9.81 * mass; // m*g
//		double F_R = F_N * c_R;
//		
//		v = v + a * dt - (F_w + F_R)/mass * dt;
		
		double v_old = v;
		v = v + a * dt;
		if (this.v < 0){
			this.v = 0.0;
		}
		if (v > V_PHY_MAX){
			v = V_PHY_MAX;
		}
		
		x = x + vx * dt;
		y = y + vy * dt;
		
		drive_behavior();
//		System.out.println(this.name+"  x="+x);
//		System.out.println(this.name+"  y="+y);
		
	}
	
	public abstract void drive_behavior();
	
	protected void print_xy(){
		System.out.printf("%s: (x=%.1f, y=%2.1f, v=%2.1f, a=%2.5f, ...)\n",this.name, this.x, this.y, this.v, this.a);
	}
	
	public void SetSensorModelCar(SensorModelCar smc){
		this.smc = smc;
		
	}
	
}
