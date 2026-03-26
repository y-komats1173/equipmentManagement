package jp.co.sss.equipment.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jp.co.sss.equipment.filter.AuthorityCheckFilter;
import jp.co.sss.equipment.filter.LoginCheckFilter;

	@Component
	public class LoginUserInterceptor implements HandlerInterceptor {

		@Override
		// コントローラーの前に実行されるメソッド
		public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		    HttpSession session = request.getSession();
		    Object user = session.getAttribute("user");
		    // ログインユーザーが存在する場合、リクエスト属性に設定
		    if (user != null) {
		        request.setAttribute("loginUser", user);
		    }
		    return true;
		}
		
		@Bean
		// フィルタの登録
		public FilterRegistrationBean<LoginCheckFilter> loginCheckFilter() {
			// FilterRegistrationBeanを作成し、LoginCheckFilterを登録
		    FilterRegistrationBean<LoginCheckFilter> bean =
		            new FilterRegistrationBean<>();

		    // LoginCheckFilterをフィルタとして設定
		    bean.setFilter(new LoginCheckFilter());
		    bean.addUrlPatterns("/*"); // 全部対象
		    bean.setOrder(1); 
		    return bean;
		}
		
		@Bean
		// フィルタの登録
		public FilterRegistrationBean<AuthorityCheckFilter> authorityCheckFilter() {
			// FilterRegistrationBeanを作成し、AuthorityCheckFilterを登録
		    FilterRegistrationBean<AuthorityCheckFilter> bean = new FilterRegistrationBean<>();
		    // AuthorityCheckFilterをフィルタとして設定
		    bean.setFilter(new AuthorityCheckFilter());
		    bean.addUrlPatterns("/*");
		    bean.setOrder(2); // LoginCheckFilter(1)の後
		    return bean;
		}
}