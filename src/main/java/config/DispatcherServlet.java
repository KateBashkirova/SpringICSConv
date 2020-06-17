package config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

//наследуемся от абстрактного класса, который уже реализует интерфейс WebApplicationInitializer
//@Configuration
public class DispatcherServlet { // extends  AbstractAnnotationConfigDispatcherServletInitializer {
//    protected Class<?>[] getRootConfigClasses() {
//        return null;
//    }
//
//    //указываем, где находится конфигурация
//    protected Class<?>[] getServletConfigClasses() {
//        return new Class[] {SpringConfig.class};
//    }
//
//    protected String[] getServletMappings() {
//        return new String[] {"/"}; //все запросы улетают на диспетчер
//    }
}
