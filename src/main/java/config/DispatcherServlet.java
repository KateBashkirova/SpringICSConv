package config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

//наследуемся от абстрактного класса, который уже реализует интерфейс WebApplicationInitializer
@Configuration
public class DispatcherServlet extends  AbstractAnnotationConfigDispatcherServletInitializer {
    protected final Class<?>[] getRootConfigClasses() {
        return new Class[]{SpringConfig.class};
    }

    //указываем, где находится конфигурация
    protected final Class<?>[] getServletConfigClasses() {
        return new Class[] {SpringConfig.class};
    }

    protected final String[] getServletMappings() {
        return new String[] {"/"}; //все запросы улетают на диспетчер
    }
}
