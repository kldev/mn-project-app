package mn.portal;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "MN",
                version = "1.0",
                description = "MN API"
        )
)
public class MnApiApplication {

    public static void main(String[] args) {
        Micronaut.run(MnApiApplication.class, args);
    }
}
