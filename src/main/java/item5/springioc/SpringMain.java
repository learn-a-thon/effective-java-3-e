package item5.springioc;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        SpellChecker spellChecker = applicationContext.getBean(SpellChecker.class);
        spellChecker.isValid("hello");
    }
}
