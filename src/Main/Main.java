package Main;
import EnvModel.*;

public class Main {

	public static void main(String[] args){
		System.out.println("test");
		Car1 car1 = new Car1("Car1",1.5,-43.0,12.0,Math.PI/2,0.0,1000);
		Car2 car2 = new Car2("Car2",1.5,-68.0,12.0,Math.PI/2,0.0,1000);
		Car3 car3 = new Car3("Car3",43,1.5,12.0,Math.PI,0.0,1000);
		EnvModel em = new EnvModel(car1, car2, car3);
		car1.SetSensorModelCar(em);
		car2.SetSensorModelCar(em);
		car3.SetSensorModelCar(em);
		em.execute_test();
	}
}
