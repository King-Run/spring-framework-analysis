package base;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author RunXin
 * @Date 2022/10/19 16:06
 * @Email zhrunxin33@gmail.com
 * @Description
 */
@Configuration
public class SimpleBeanConfig {

	@Bean
	public AnnotationSimpleBean annotationBean(){
		return new AnnotationSimpleBean("Xander chow","zhrunxin33@gmail.com");
	}
}
