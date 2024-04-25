package io.kadev;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import io.kadev.services.BusinessLogicInterface;

@SpringBootApplication
public class ProjectPlanApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ProjectPlanApplication.class, args);
    }

    @Autowired
    private BusinessLogicInterface service;

    @Override
    public void run(String... args) throws Exception {
        /*ProjectRequestDto p1 = new ProjectRequestDto("Chocolatier",5000);
        service.createNewProject(p1);

        ProductRequestDto prod1 = new ProductRequestDto("T1",25000,12,3,120000,1L,20000,1);
        ProductRequestDto prod2 = new ProductRequestDto("T2",42000,9,2,160000,1L,40000,1);
        ProductRequestDto prod3 = new ProductRequestDto("T3",12000,16,6,55000,1L,10000,70);
        service.createNewProduit(prod1);
        service.createNewProduit(prod2);
        service.createNewProduit(prod3);

        service.calculMetrics(1L);*/
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

/*    @Component
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public class SimpleCorsFilter implements Filter {

        public SimpleCorsFilter() {
        }

        @Override
        public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
            HttpServletResponse response = (HttpServletResponse) res;
            HttpServletRequest request = (HttpServletRequest) req;
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "authorization, content-type, xsrf-token");
            response.addHeader("Access-Control-Expose-Headers", "xsrf-token");
            if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                chain.doFilter(req, res);
            }
        }

        @Override
        public void init(FilterConfig filterConfig) {
        }

        @Override
        public void destroy() {
        }
    }*/
}
