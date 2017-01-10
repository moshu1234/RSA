package com.example.rsa;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.PrivateKey;
import java.security.PublicKey;

import android.app.Activity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener
{
	private Button btn1, btn2;// 加密，解密
	private EditText et1, et2, et3;// 需加密的内容，加密后的内容，解密后的内容

	/* 密钥内容 base64 code */
	private static String PUCLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDP07qFcEphKUGZ6NbAQYUjkaRp"
			+ "\r" + "AmgRXN05o2IcNt6nlx3S9fePdYpadDB/D+WrjMzbutTwTc45v2oEqd9Am3v6KaSn" + "\r"
			+ "yxkdm0bJdCukdzRQrEVHrCV3q+UuhPiRl8ZCbpYxXgkrE7udcjAOQjFdx+Dp5tm7" + "\r"
			+ "f9+sLu2/3hj0tTKd9QIDAQAB" + "\r";
	private static String PRIVATE_KEY = "MIICoTAbBgkqhkiG9w0BBQMwDgQIWOeoYYyu+K8CAggABIICgGxNWOfmm0HtgKfB"
			+ "\r" + "kv2FZJA9y56KrWDCaI+vAm1RKOXnK1yqMTaWifSrjrrIvL3gqUGmAdtkuWCKfGZM" + "\r"
			+ "lrdLzspuB9I//4jJT3iAxI7Nb2ipHTaqVZHj7xmJ3rBH+FrCb1hV5L4CP8pbhUrp" + "\r"
			+ "ISPHVAct6RJtGymMycLmFVZMjPRtniRx4eTVW2geaXxWFT5LF15ThW7HAV7UNpiD" + "\r"
			+ "eDhx8YuvQQ43tjUnIACz3KaWudaE32swq2KY/ejr4SlZWlm1GDjDXpnOvGQBZ779" + "\r"
			+ "QEX8RCUgMq4Z14e8tgrSWoYSC7opww8bQxVV2kL8jGbQQXpK6Z+6bJISsZd752E7" + "\r"
			+ "D/0zM37KDwDHYkfJvRuLa3ditT65WyY4bTfhE4STFTeXiaDEqBcYCw56tzj5poJh" + "\r"
			+ "RCURUNO2iusmhvveQSZpPozQdSV66o/vRm9jGduvLv/8MJc0kVZgY9xdkoZjlXbi" + "\r"
			+ "BW0lQmtG6pghmbneNZ7jaSV+oAFp9juClgkTlqezpxK32K70mX0WVIa4vXagFBV1" + "\r"
			+ "5Co1ps432p0k76SuKiWF+TM66d8Cit1p+lL0lryVUxHZEeKtzgbYXXK+p4l2gsvA" + "\r"
			+ "Uk9kcXc7L68CXmEnGNp3vqgB0YKP13nBbVCCRxSVaIIgpTTg2VAqLV1qXmplzQ2c" + "\r"
			+ "mVX4X0C/thiAXH/WGZkCr4KSIOZb5dgstEZ+mk+mTmlllCg6BfscxzW8CBV6fDKx" + "\r"
			+ "h2gy3e1MAhNwkjGV69Pyznvs39bb3PIVQKFyVL2ZMRTncUw0UDZAT5+kx2oyd/52" + "\r"
			+ "O2dU/xcDBTRjckxKhpmsHl7qj2VkA/SiKARoeYNnc7Qng2qlS9wMHCfw2YfEJyGr" + "\r"
			+ "Mz/QQYE="
			+ "\r";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		AESEnc("test.txt.enc");
	}

	private void initView()
	{
		btn1 = (Button) findViewById(R.id.btn1);
		btn2 = (Button) findViewById(R.id.btn2);
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);

		et1 = (EditText) findViewById(R.id.et1);
		et2 = (EditText) findViewById(R.id.et2);
		et3 = (EditText) findViewById(R.id.et3);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		// 加密
		case R.id.btn1:
			String source = et1.getText().toString().trim();
			try
			{
				// 从字符串中得到公钥
				// PublicKey publicKey = RSAUtils.loadPublicKey(PUCLIC_KEY);
				// 从文件中得到公钥
				InputStream inPublic = getResources().getAssets().open("rsa_public_key.pem");
				PublicKey publicKey = RSAUtils.loadPublicKey(inPublic);
				// 加密
				byte[] encryptByte = RSAUtils.encryptData(source.getBytes(), publicKey);
				// 为了方便观察吧加密后的数据用base64加密转一下，要不然看起来是乱码,所以解密是也是要用Base64先转换
				String afterencrypt = Base64Utils.encode(encryptByte);
				et2.setText(afterencrypt);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			break;
		// 解密
		case R.id.btn2:
			String encryptContent = et2.getText().toString().trim();
			try
			{
				// 从字符串中得到私钥
				// PrivateKey privateKey = RSAUtils.loadPrivateKey(PRIVATE_KEY);
				// 从文件中得到私钥
				InputStream inPrivate = getResources().getAssets().open("pkcs8_rsa_private_key.pem");
				PrivateKey privateKey = RSAUtils.loadPrivateKey(inPrivate);
				// 因为RSA加密后的内容经Base64再加密转换了一下，所以先Base64解密回来再给RSA解密
				byte[] decryptByte = RSAUtils.decryptData(Base64Utils.decode(encryptContent), privateKey);
				String decryptStr = new String(decryptByte);
				et3.setText(decryptStr);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}
	public byte[] getFromAssets(String fileName){
		try{

			//得到资源中的asset数据流
			InputStream in = getResources().getAssets().open(fileName);

			int length = in.available();
			Log.e("------------","length="+length);
			byte [] buffer = new byte[length];

			in.read(buffer);
			in.close();
//			return AESUtil.parseByte2HexStr(buffer);
			return buffer;
//			return Base64.encodeToString(buffer,Base64.DEFAULT);
//			res = EncodingUtils.getString(buffer, "UTF-8");

		}catch(Exception e){

			e.printStackTrace();

		}
		return null;
	}
	void AESEnc(String src){
//		String op = getFromAssets(src);
//		Log.e("------------","op="+op);
//		byte[] text = getFromAssets("test.txt");
		byte[] textenc = getFromAssets("test.txt.javaenc");
//		Log.e("------------","text="+text);
//		Log.e("------------","length：" + ss.length());
//		String s = "123456789abcdefg";
//		byte[] ss = AESUtil.encrypt(text, "123456");
//		Log.e("------------","加密后：" + AESUtil.parseByte2HexStr(ss));
		String ed = AESUtil.decrypt(textenc, "123456");
		Log.e("------------",":解密后：" + ed);
	}
	public static String toStringHex(String s)
	{
		byte[] baKeyword = new byte[s.length()/2];
		for(int i = 0; i < baKeyword.length; i++)
		{
			try
			{
				baKeyword[i] = (byte)(0xff & Integer.parseInt(s.substring(i*2, i*2+2),16));
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		try
		{
			s = new String(baKeyword, "utf-8");//UTF-16le:Not
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}
		return s;
	}
}
