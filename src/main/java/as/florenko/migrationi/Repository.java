package as.florenko.migrationi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class Repository {
    private final String SCRIPT = "Script.sql";

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJDBCTemplate;

    private static String read(String scriptFileName) {
        try (InputStream is = new ClassPathResource(scriptFileName).getInputStream();
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is))) {
            return bufferedReader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getProductName(String clientName) {
        return namedParameterJDBCTemplate.queryForObject(read(SCRIPT),
                Map.of("name", clientName),
                (rs, prod) -> rs.getString("product_name"));
    }
}

