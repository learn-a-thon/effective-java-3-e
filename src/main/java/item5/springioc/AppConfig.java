package item5.springioc;

import item5.Dictionary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//Spring IoC
@Configuration
@ComponentScan(basePackageClasses = AppConfig.class)
public class AppConfig {

    @Bean
    public SpellChecker spellChecker(Dictionary dictionary) {
        return new SpellChecker(dictionary);
    }

    @Bean
    public Dictionary dictionary() {
        return new SpringDictionary();
    }
}
