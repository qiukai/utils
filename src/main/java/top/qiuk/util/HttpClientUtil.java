package top.qiuk.util;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;
import top.qiuk.constant.ExceptionTypeEnum;
import top.qiuk.exception.UtilRuntimeException;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Map;

/**
 * 发送http请求帮助类
 */
public class HttpClientUtil {

    enum HttpMothod {
        GET, POST, PUT, DELETE, DOWNLOAD;
    }

    private static Logger logger = Logger.getLogger(HttpClientUtil.class);

    private static int outTime = 1800000;// 30分钟（http连接超时）
    private static int reqTime = 10000;// 10秒（请求超时）

    private String encoding;
    private String url;
    private Map<String, String> para = null;
    private HttpClient httpClient;
    private Cookie[] cookie;

    public HttpClientUtil(String url) {
        this(url, null);
    }

    public HttpClientUtil(String url, Map<String, String> para) {
        this(url, "utf-8", para);
    }

    public HttpClientUtil(String url, String encoding, Map<String, String> para) {
        if (StringUtil.isNull(url)) {
            throw new UtilRuntimeException(ExceptionTypeEnum.STRING_IS_NULL, "url为空");
        }
        if (null == encoding) {
            encoding = "utf-8";
        }
        this.url = url;
        this.encoding = encoding;
        this.para = para;
        this.httpClient = new HttpClient();
        this.httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(outTime);
        this.httpClient.getState().addCookies(cookie);
    }

    public String execute(HttpMothod mothod) {
        return execute(mothod, null);
    }

    public String execute(HttpMothod mothod, String fileName) {
        logger.info("请求的url为:" + url);
        HttpMethod httpMethod = getHttpMethod(mothod);
        httpClient.getParams().setParameter("http.protocol.allow-circular-redirects", true);
        String value = null;
        try {
            this.httpClient.executeMethod(httpMethod);
            int state = httpMethod.getStatusCode();
            if (state == 200) {
                cookie = httpClient.getState().getCookies();
                value = this.readInputStream(mothod, httpMethod.getResponseBodyAsStream(), fileName);
                // logger.info(value);
            } else {
                logger.error(state);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpMethod.releaseConnection();
        }
        return value;
    }

    private String readInputStream(HttpMothod mothod, InputStream inputStream, String fileName) throws Exception {

        byte[] b = new byte[1024];
        int readBytes = 0;
        StringBuilder sb = new StringBuilder();
        FileOutputStream fileOutputStream = null;

        switch (mothod) {
            case GET:
            case POST:
            case DELETE:
            case PUT:
                while ((readBytes = inputStream.read(b)) > 0) {
                    sb.append(new String(b, 0, readBytes, encoding));
                }
                break;
            default:
                if (StringUtil.isNull(fileName)) {
                    throw new UtilRuntimeException(ExceptionTypeEnum.FILE_NAME_NULL, "文件名不可以为空");
                }
                fileOutputStream = new FileOutputStream(fileName);
                while ((readBytes = inputStream.read(b)) != -1) {
                    fileOutputStream.write(b, 0, readBytes);
                }
                sb.append("文件下载成功");
        }

        if (null != inputStream) {
            inputStream.close();
        }
        if (null != fileOutputStream) {
            fileOutputStream.close();
        }

        return sb.toString();
    }

    private HttpMethod getHttpMethod(HttpMothod mothod) {
        HttpMethod httpMethod = null;
        switch (mothod) {
            case GET:
            case DOWNLOAD:
                httpMethod = getGetMethod();
                break;
            case PUT:
                httpMethod = getPutMethod();
                break;
            case DELETE:
                httpMethod = getDeleteMethod();
                break;
            default:
                httpMethod = getPostMethod();
                break;
        }

        httpMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, reqTime);
        httpMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, encoding);
        return httpMethod;
    }

    /**
     * 构建post 请求
     *
     * @return PostMethod实例
     */
    private DeleteMethod getDeleteMethod() {
        DeleteMethod deleteMethod = new DeleteMethod(url);
        this.setQueryString(deleteMethod);
        return deleteMethod;
    }

    /**
     * 构建post 请求
     *
     * @return PostMethod实例
     */
    private PutMethod getPutMethod() {
        PutMethod putMethod = new PutMethod(url);
        this.setQueryString(putMethod);
        return putMethod;
    }

    /**
     * 构建post 请求
     *
     * @return PostMethod实例
     */
    private GetMethod getGetMethod() {
        GetMethod getMethod = new GetMethod(url);
        this.setQueryString(getMethod);
        return getMethod;
    }

    /**
     * 构建post 请求
     *
     * @return PostMethod实例
     */
    private PostMethod getPostMethod() {
        PostMethod postMethod = new PostMethod(url);
        this.setBody(postMethod);
        return postMethod;
    }

    /**
     * http请求添加uri后面的参数
     */
    private void setQueryString(HttpMethod httpMethod) {
        httpMethod.setQueryString(biuderNameValuePair());
    }

    /**
     * 将参数保存到请求体中
     *
     * @param postMethod post请求实例
     */
    private void setBody(PostMethod postMethod) {
        postMethod.setRequestBody(biuderNameValuePair());
    }

    /**
     * 构建参数
     *
     * @return 参数数组对象
     */
    private NameValuePair[] biuderNameValuePair() {
        NameValuePair[] nameValuePairs = null;
        if (null != para && 0 != para.size()) {
            nameValuePairs = new NameValuePair[para.size()];
            int i = 0;
            for (String key : para.keySet()) {
                if (null != para.get(key)) {
                    nameValuePairs[i] = new NameValuePair(key, para.get(key).toString());
                    i++;
                }
            }
        } else {
            nameValuePairs = new NameValuePair[0];
        }
        return nameValuePairs;
    }


    public static void main(String[] args) {

        String url = "http://127.0.0.1:8090/lanmaoly-agent-boss/aa/aa";

        HttpClientUtil httpClientUtil = new HttpClientUtil(url);
        String execute = httpClientUtil.execute(HttpMothod.GET);

        System.out.println(execute);

    }
}
