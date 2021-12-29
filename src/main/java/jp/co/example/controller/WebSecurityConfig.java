package jp.co.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration //設定用のクラス
@EnableWebSecurity //Spring Securityを使うための設定
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService memberDetailsService;

	//  (2) 主に全体に対するセキュリティ設定を行う
	//  このメソッドをオーバーライドすることで、
	//  特定のリクエストに対して「セキュリティ設定」を無視する
	//  設定など全体に関わる設定ができる。
	//  具体的には静的リソースに対してセキュリティの設定を無効にする。
	@Override
	public void configure(WebSecurity web)throws Exception{
          web.ignoring().antMatchers("/css/**","/js/**","/images/**");
	}
	
    //  (3) 主にURLごとに異なるセキュリティ設定を行う
	//  このメソッドをオーバーライドすることで認可に設定や
	//  ログイン、ログアウトに関する設定ができる。
    @Override
    protected void configure(HttpSecurity http) throws Exception {

    	http.authorizeHttpRequests() //認可に関する設定
    	.antMatchers("/","/register","/register/decision").permitAll() //ここに記載したパスは全てのユーザーに許可
    	//.antMatchers("/admin/**").hasRole("ADMIN") // /admin/から始まるパスはADMIN権限でログインしている場合のみアクセス可(権限設定時の「ROLE_」を除いた文字列を指定)
		//.antMatchers("/user/**").hasRole("USER") // /user/から始まるパスはUSER権限でログインしている場合のみアクセス可(権限設定時の「ROLE_」を除いた文字列を指定)
    	.anyRequest().authenticated(); //それ以外のパスは認証が必要
    	
    	http.formLogin() //ログインに関する設定
    	.loginPage("/") //ログイン画面に遷移させるパス(ログイン認証が必要なパスを指定してかつログインされていないとこのパスに遷移される。)
    	.loginProcessingUrl("/login") //ログインボタンを押したときに遷移させるパス（ここに遷移させれば自動的にログインが行われる。）
    	.failureUrl("/?error=true") //ログイン失敗時に遷移させるパス
    	.defaultSuccessUrl("/afterLogin/top",true) // 第1引数:デフォルトでログイン成功時に遷移させるパス
								        // 第2引数: true :認証後常に第1引数のパスに遷移 
								        //         false:認証されてなくて一度ログイン画面に飛ばされてもログインしたら指定したURLに遷移
    	.usernameParameter("email")     //認証時に使用するユーザー名のリクエストパラメータ名（今回はメールアドレスを使用）
    	.passwordParameter("password"); //認証時に使用するパスワードのリクエストパラメーター名
   
    	http.logout() //ログアウトに関する設定
    	.logoutRequestMatcher(new AntPathRequestMatcher("/logout")) //ログアウトさせる際に遷移させるパス
    	.logoutSuccessUrl("/") //ログアウト後に遷移させるパス
    	.deleteCookies("JSESSIONID") //ログアウト後、Cookieに保存されているセッションIDを削除
    	.invalidateHttpSession(true); //ログアウト後、セッションを無効にする false:セッションを無効にしない
    }
    
    //  (4) 主に認証方法の実装の設定を行う
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.userDetailsService(memberDetailsService)
    	.passwordEncoder(new BCryptPasswordEncoder())
    	;
    }
    
    /**
     * <pre>
     * bcryptアルゴリズムでハッシュ化する実装を返します.
     * これを指定することでパスワードハッシュ化やマッチ確認する際に
     * @Autowired
	 * private PasswordEncoder passwordEncoder;
	 * と記載するとDIされるようになります。
     * </pre>
     * @return bcryptアルゴリズムでハッシュ化する実装オブジェクト
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
    		return new BCryptPasswordEncoder();
    }
}
