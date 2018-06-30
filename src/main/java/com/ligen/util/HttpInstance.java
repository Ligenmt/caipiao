package com.ligen.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HttpInstance {


	private HttpInstance() {
	}

	public static HttpInstance create() {
		return new HttpInstance();
	}

	protected CloseableHttpClient getHttpClient(String userName, String password, int timeout)
			throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
		RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout * 1000)
				.setConnectionRequestTimeout(timeout * 1000).setSocketTimeout(timeout * 1000).build();

		HttpClientBuilder builder = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy());
		builder.setDefaultRequestConfig(config);

		// 信任证书的方案之一, 还有更好的办法吗?
		SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustStrategy() {
			@Override
			public boolean isTrusted(final X509Certificate[] chain, final String authType) throws CertificateException {
				return true;
			}
		}).useTLS().build();

		LayeredConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext,
				SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

		builder.setSSLSocketFactory(socketFactory);

		if (userName != null) // with security
		{
			CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
			credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(userName, password));
			builder.setDefaultCredentialsProvider(credentialsProvider);
		}

		return builder.build();
	}

	public String doGet(String url, JSONObject parameters, int timeout) throws Exception {
		List<NameValuePair> nvps = new ArrayList<>();

		if (parameters != null) {
			Set<String> keys = parameters.keySet();
			for (String key : keys) {
				nvps.add(new BasicNameValuePair(key, parameters.getString(key)));
			}
		}
		return doGet(null, null, url, null, nvps, "utf-8", timeout);
	}

	public String doGet(String userName, String password, String url, JSONObject parameters, int timeout)
			throws Exception {
		List<NameValuePair> nvps = new ArrayList<>();

		if (parameters != null) {
			Set<String> keys = parameters.keySet();
			for (String key : keys) {
				nvps.add(new BasicNameValuePair(key, parameters.getString(key)));
			}
		}

		return doGet(userName, password, url, null, nvps, "utf-8", timeout);
	}

	/**
	 * 简化版的get方法, 如果httpCode != 200, 则抛出错误
	 *
	 * @param url
	 *            url
	 * @param nvps
	 *            get参数
	 * @param headers
	 *            Header[]
	 * @param responseCharset
	 *            返回内容的编码
	 * @param userName
	 *            验证的用户名
	 * @param password
	 *            验证的密码
	 * @return 返回内容
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public String doGet(String userName, String password, String url, Header[] headers, List<NameValuePair> nvps,
                        String responseCharset, int timeout) throws Exception {

		CloseableHttpClient httpClient = getHttpClient(userName, password, timeout);
		HttpGet get = new HttpGet(new URIBuilder(url).addParameters(nvps).build());
		if (headers != null) {
			get.setHeaders(headers);
		}
		HttpResponse response = httpClient.execute(get);
		String resultText = EntityUtils.toString(response.getEntity(), responseCharset);
		httpClient.close();
		return resultText;
	}

	/**
	 * 简化版的post, 如果httpCode!=200, 则抛出异常
	 */
	public String doPost(String url, String data, int timeout, Header[] headers) throws Exception {
		return doPost(null, null, url, data, headers, "utf-8", "utf-8", timeout);
	}

	/**
	 * 简化版的post, 如果httpCode!=200, 则抛出异常
	 */
	public String doPost(String userName, String password, String url, String data, int timeout) throws Exception {
		return doPost(userName, password, url, data, null, "utf-8", "utf-8", timeout);
	}

	/**
	 * 简化版的post, 如果httpCode!=200, 则抛出异常
	 *
	 * @param url
	 *            url
	 * @param data
	 *            post提交的数据
	 * @param headers
	 *            Header[]
	 * @param requestCharset
	 *            提交内容的编码格式
	 * @param responseCharset
	 *            返回内容的编码
	 * @param userName
	 *            验证的用户名
	 * @param password
	 *            验证的密码
	 * @return 返回内容
	 * @throws IOException
	 */
	public String doPost(String userName, String password, String url, String data, Header[] headers,
			String requestCharset, String responseCharset, int timeout) throws Exception {

		CloseableHttpClient httpClient = getHttpClient(userName, password, timeout);
		HttpPost post = new HttpPost(url);
		if (headers != null) {
			post.setHeaders(headers);
		}
		post.setEntity(new StringEntity(data, requestCharset));
		HttpResponse response = httpClient.execute(post);
		String resultText = EntityUtils.toString(response.getEntity(), responseCharset);
		httpClient.close();
		return resultText;
	}

	public String doPost(String url, Map<String, String> params, Header[] headers, String requestCharset,
                         String responseCharset, int timeout) throws Exception {

		CloseableHttpClient httpClient = getHttpClient(null, null, timeout);
		HttpPost post = new HttpPost(url);
		if (headers != null) {
			post.setHeaders(headers);
		}
		List<NameValuePair> p = null;
		if (params != null && params.keySet().size() > 0) {
			p = new ArrayList<NameValuePair>();
			for (String key : params.keySet()) {
				p.add(new BasicNameValuePair(key, params.get(key)));
			}
		}
		if (p != null) {
			post.setEntity(new UrlEncodedFormEntity(p, requestCharset));
		}
		HttpResponse response = httpClient.execute(post);
		String resultText = EntityUtils.toString(response.getEntity(), responseCharset);
		httpClient.close();
		return resultText;
	}

}