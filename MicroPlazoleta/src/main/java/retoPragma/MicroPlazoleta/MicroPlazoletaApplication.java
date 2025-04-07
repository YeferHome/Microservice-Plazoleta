package retoPragma.MicroPlazoleta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MicroPlazoletaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroPlazoletaApplication.class, args);
	}

}
