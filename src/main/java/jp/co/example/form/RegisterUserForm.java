package jp.co.example.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RegisterUserForm {
	@NotBlank(message="名前を入力してください。")
	private String name;
	@NotBlank(message="メールアドレスを入力してください。")
	@Email(message="メールアドレスの形式が不正です。")
	private String email;
	@NotBlank(message="郵便番号を入力してください。")
	@Pattern(regexp="^[0-9]{7}$",message="郵便番号は７桁（ハイフン無し）で入力してください。")
	private String zipcode;
	@NotBlank(message="住所を入力してください。")
	private String address;
	@NotBlank(message="電話番号を入力してください。")
	@Pattern(regexp="^0\\d{9,10}$",message="電話番号の形式で入力してください。")
	private String telephone;
	@Size(min=8,max=16, message="パスワードは8文字以上16文字以下で入力してください。")
	private String password;
	@Size(min=8,max=16, message="確認用パスワードは8文字以上16文字以下で入力してください。")
	private String confirmPassword;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@Override
	public String toString() {
		return "RegisterUserForm [name=" + name + ", email=" + email + ", zipcode=" + zipcode + ", address=" + address
				+ ", telephone=" + telephone + ", password=" + password + ", confirmPassword=" + confirmPassword + "]";
	}
}
