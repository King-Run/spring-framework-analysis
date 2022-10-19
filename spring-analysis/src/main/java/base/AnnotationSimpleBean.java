package base;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author RunXin
 * @Date 2022/10/19 15:46
 * @Email zhrunxin33@gmail.com
 * @Description
 */
public class AnnotationSimpleBean {

	private String name;

	private String email;

	public AnnotationSimpleBean(String name, String email) {
		this.name = name;
		this.email = email;
	}

	public void introduce(){
		System.out.println(String.format("Here is %s, my email is %s. Welcome to send me an office!",this.name,this.email));
	}

}
