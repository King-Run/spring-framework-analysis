import base.AnnotationSimpleBean;
import base.SimpleBean;
import base.SimpleBeanConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author RunXin
 * @Date 2022/10/19 14:15
 * @Email zhrunxin33@gmail.com
 * @Description
 */
public class GetStart {

	public static void main(String[] args) {
		byConfigFile();
		byAnnotationConfig();
	}
	public static void byConfigFile() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		SimpleBean bean = context.getBean(SimpleBean.class);
		bean.awkwardSmile();
		context.close();
	}

	public static void byAnnotationConfig() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SimpleBeanConfig.class);
		AnnotationSimpleBean bean = context.getBean(AnnotationSimpleBean.class);
		bean.introduce();
		context.close();
	}

}
