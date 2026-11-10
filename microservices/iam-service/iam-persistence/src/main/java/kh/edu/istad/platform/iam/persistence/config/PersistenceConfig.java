package kh.edu.istad.platform.iam.persistence.config;

import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "kh.edu.istad.platform.iam.persistence.repository")
@EntityScan(basePackages = "kh.edu.istad.platform.iam.persistence.entity")
public class PersistenceConfig {

}
