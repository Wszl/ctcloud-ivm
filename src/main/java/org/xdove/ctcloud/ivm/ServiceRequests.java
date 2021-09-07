package org.xdove.ctcloud.ivm;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xdove.ctcloud.ivm.entity.CreateTaskAnalysisRule;
import org.xdove.ctcloud.ivm.entity.UpdateTaskAnalysisRule;
import org.xdove.utils.WebSignatureUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * 视觉智联SDK
 * @author Wszl
 * @date 2021年7月31日
 */
public class ServiceRequests {

    private final static Logger log = LogManager.getLogger(ServiceRequests.class);
    /** 请求参数名 sign */
    private static final String PARAM_KEY_SIGN = "sign";
    /** 请求参数名 appkey */
    private static final String PARAM_KEY_APPKEY = "appkey";
    /** 请求参数名 paramdata */
    private static final String PARAM_KEY_PARAMDATA = "paramdata";
    /** 签名字符串分隔符 */
    private static final String SEPARATOR_SIGNATURE_STRING = "&&";
    /** 响应参数名 result */
    private static final String RESPONSE_KEY_RESULT_CODE = "resultCd";
    /** 响应参数值 成功 */
    private static final int RESPONSE_VALUE_RESULT_CODE_SUCCESS = 0;
    /** 响应参数名 msg */
    private static final String RESPONSE_KEY_RESULT_MSG = "msg";
    /** 响应参数名 res */
    private static final String RESPONSE_KEY_RESULT_RES = "res";
    /** 响应参数名 timestamp */
    private static final String RESPONSE_KEY_RESULT_TIMESTAMP = "timestamp";

    private String contentType;
    private HttpClient client;
    private MessageDigest messageDigest;
    private IVMConfig config;

    public ServiceRequests(IVMConfig config) throws NoSuchAlgorithmException {
        this.config = config;
        this.messageDigest = MessageDigest.getInstance("MD5");
        this.client = HttpClients.createDefault();
        this.contentType = "application/json";
    }

    public ServiceRequests(String contentType, HttpClient client, MessageDigest messageDigest, IVMConfig config) {
        this.contentType = contentType;
        this.client = client;
        this.messageDigest = messageDigest;
        this.config = config;
    }

    /**
     * 创建明厨亮灶任务 47211
     * @param deviceNum 设备编码（非必填，与 url 二选一）
     * @param url 视频流地址或视频文件地址（非必填，与 deviceNum 二选一）
     * @param type 类型,0:视频流地址,1:视频文件地址（必填）
     * @param analysisRule 检测规则，不传则默认全屏检测所有类型（非必填）
     * @param sceneBase64 基准场景图，用于校验违规置物、视频不正的基准图片（非必填）
     * @param name 任务名称（非必填）
     * @return result {"taskId":"60d04273e4b0e5b78bd1b502" }
     */
    public Map<String, Object> createTask(String deviceNum, String url, int type, CreateTaskAnalysisRule analysisRule,
                                          String sceneBase64, String name) {
        Map<String, Object> param = new TreeMap<>();
        param.put("code", "47211");
        Map<String, Object> req = new TreeMap<>();
        req.put("deviceNum", deviceNum);
        req.put("url", url);
        req.put("type", type);
        req.put("analysisRule", analysisRule);
        req.put("sceneBase64", sceneBase64);
        req.put("name", name);
        param.put("req", req);
        return baseRequest(param);
    }

    /**
     * 创建明厨亮灶任务 47212
     * @param deviceNum 设备编码（非必填，与 url 二选一）
     * @param url 视频流地址或视频文件地址（非必填，与 deviceNum 二选一）
     * @param name 任务名称（非必填）
     * @param analysisRule 检测规则，不传则默认全屏检测所有类型（非必填）
     * @param sceneBase64 基准场景图，用于校验违规置物、视频不正的基准图片（非必填）
     * @param taskId 检测任务 id（必填）
     * @return result { }
     */
    public Map<String, Object> updateTask(String deviceNum, String url, String name, CreateTaskAnalysisRule analysisRule,
                                               String sceneBase64, String taskId) {
        Map<String, Object> param = new TreeMap<>();
        param.put("code", "47212");
        Map<String, Object> req = new TreeMap<>();
        req.put("deviceNum", deviceNum);
        req.put("url", url);
        req.put("analysisRule", analysisRule);
        req.put("sceneBase64", sceneBase64);
        req.put("name", name);
        req.put("taskId", taskId);
        param.put("req", req);
        return baseRequest(param);
    }

    /**
     * 查询明厨亮灶任务 47213
     * @param taskId 检测任务 ID（非必填）
     * @param pageSize 每页条数，默认为 10（非必填）
     * @param pageNum 页码，默认为 1（非必填）
     * @return result {"totalRow":1, "data":[ { "taskId":"60121fbd3a5c8463252f8810", "taskName":"任务测试", "taskStatus":1, "sourceId":"60121fae3a5c8463252f880d", "extractionFrequency":10, "createTime":"2021-01-2810:21:50" } ],"totalPage":1, "pageSize":5, "pageNum":1 }
     */
    public Map<String, Object> queryTask(String taskId, int pageSize, int pageNum) {
        Map<String, Object> param = new TreeMap<>();
        param.put("code", "47213");
        Map<String, Object> req = new TreeMap<>();
        req.put("taskId", taskId);
        req.put("pageSize", pageSize);
        req.put("pageNum", pageNum);
        param.put("req", req);
        return baseRequest(param);
    }

    /**
     * 删除明厨亮灶任务 47214
     * @param taskId 检测任务 ID
     * @return result { }
     */
    public Map<String, Object> deleteTask(String taskId) {
        Map<String, Object> param = new TreeMap<>();
        param.put("code", "47214");
        Map<String, String> req = new TreeMap<>();
        req.put("taskId", taskId);
        param.put("req", req);
        return baseRequest(param);
    }

    /**
     * 通用接口 查看订阅 30701
     * @return {"returnUrl":"http://chenshw.tunnel.qydev.com/ivmsubscription/sub/index", "codes":"42909", "createTime":"2020-06-05T03:25:22.000+0000", "updateTime":"2020-06-05T08:31:45.000+0000" },
     * */
    public Map<String, Object> querySubscribe() {
        Map<String, Object> param = new TreeMap<>();
        param.put("code", "30701");
        Map<String, String> req = new TreeMap<>();
        param.put("req", req);
        return baseRequest(param);
    }

    /**
     * 通用接口 添加订阅 30702
     * @param returnUrl 回调地址，有多个的话用逗号隔开，不允许出现中文字符
     * @param codes 推送内容编号（以逗号隔开）
     * @return { }
     * */
    public Map<String, Object> createSubscribe(String returnUrl, String codes) {
        Map<String, Object> param = new TreeMap<>();
        param.put("code", "30702");
        Map<String, String> req = new TreeMap<>();
        req.put("returnUrl", returnUrl);
        req.put("codes", codes);
        param.put("req", req);
        return baseRequest(param);
    }

    /**
     * 通用接口 修改订阅 30703
     * @param subId  如果有多个订阅地址可指定订阅 id修改，如果没有则不用传入订阅 ID
     * @param returnUrl 回调地址，有多个的话用逗号隔开，不允许出现中文字符
     * @param codes 推送内容编号（以逗号隔开）
     * @return { }
     * */
    public Map<String, Object> updateSubscribe(String subId, String returnUrl, String codes) {
        Map<String, Object> param = new TreeMap<>();
        param.put("code", "30703");
        Map<String, String> req = new TreeMap<>();
        req.put("subId", subId);
        req.put("returnUrl", returnUrl);
        req.put("codes", codes);
        param.put("req", req);
        return baseRequest(param);
    }

    /**
     * 通用接口 删除订阅 30704
     * @param subId 多个订阅地址则需传入订阅 id，一个的情况直接根据开发者 key 删除（可
     * @return { }
     * */
    public Map<String, Object> deleteSubscribe(String subId) {
        Map<String, Object> param = new TreeMap<>();
        param.put("code", "30704");
        Map<String, String> req = new TreeMap<>();
        req.put("subId", subId);
        param.put("req", req);
        return baseRequest(param);
    }

    /**
     * 布控添加 45307
     * @param taskIds 任务 ID(多个任务 id 用“,”隔开)
     * @param threshold 报警分数线(大于 0 小于 100的整数)
     * @param repositoryIds 库 ID(多个人像库 ID 用“,”隔开)
     * @return { "id": "xxx" }
     */
    public Map<String, Object> createControl(String taskIds, int threshold, String repositoryIds) {
        Map<String, Object> param = new TreeMap<>();
        param.put("code", "45307");
        Map<String, Object> req = new TreeMap<>();
        req.put("taskIds", taskIds);
        req.put("threshold", threshold);
        req.put("repositoryIds", repositoryIds);
        param.put("req", req);
        return baseRequest(param);
    }

    /**
     * 布控查询 45308
     * @param id 布控 ID(非必填，默认获取所有布控)
     * @param pageSize 每页条数（非必填）
     * @param pageNum 页码（非必填）
     * @return { ... }
     */
    public Map<String, Object> queryControl(String id, int pageSize, int pageNum) {
        Map<String, Object> param = new TreeMap<>();
        param.put("code", "45308");
        Map<String, Object> req = new TreeMap<>();
        req.put("id", id);
        req.put("pageSize", pageSize);
        req.put("pageNum", pageNum);
        param.put("req", req);
        return baseRequest(param);
    }

    /**
     * 布控修改 45309
     * @param id 布控 ID
     * @param taskIds 任务 ID(多个任务 id 用“,”隔开)
     * @param threshold 报警分数线(大于 0 小于 100的整数)
     * @param repositoryIds 库 ID(多个人像库 ID 用“,”隔开)
     * @return { }
     */
    public Map<String, Object> updateControl(String id, String taskIds, int threshold, String repositoryIds) {
        Map<String, Object> param = new TreeMap<>();
        param.put("code", "45309");
        Map<String, Object> req = new TreeMap<>();
        req.put("id", id);
        req.put("taskIds", taskIds);
        req.put("threshold", threshold);
        req.put("repositoryIds", repositoryIds);
        param.put("req", req);
        return baseRequest(param);
    }

    /**
     * 布控删除 45310
     * @param id 布控 ID
     * @return { }
     */
    public Map<String, Object> deleteControl(String id) {
        Map<String, Object> param = new TreeMap<>();
        param.put("code", "45310");
        Map<String, Object> req = new TreeMap<>();
        req.put("id", id);
        param.put("req", req);
        return baseRequest(param);
    }

    private Map<String, Object> baseRequest(Map<String, Object> param) {
        HttpPost post = new HttpPost(config.getApiurl());
        try {
            String sign = sign(param);
            if (log.isDebugEnabled()) {
                log.debug("sign is [{}]", sign);
            }
            post.setHeader(HttpHeaders.CONTENT_TYPE, contentType);
            post.setEntity(createBody(sign, JSON.toJSONString(param)));
            if (log.isDebugEnabled()) {
                log.debug("url=[{}], header=[{}], post entity=[{}]", config.getApiurl(), post.getAllHeaders(), post.getEntity());
            }
            HttpResponse response = client.execute(post);
            return handleResponse(response);
        } catch (IOException e) {
            log.warn(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    private Map<String, Object> handleResponse(HttpResponse response) {
        if (response.getStatusLine().getStatusCode() == 200) {
            try {
                String s = IOUtils.toString(response.getEntity().getContent(),
                        Objects.isNull(response.getEntity().getContentEncoding()) ? "utf8" : response.getEntity().getContentEncoding().getValue());
                if (log.isDebugEnabled()) {
                    log.debug("response is [{}]", s);
                }
                JSONObject jsonObject = JSON.parseObject(s);
                if (jsonObject.containsKey(RESPONSE_KEY_RESULT_CODE) && jsonObject.getIntValue(RESPONSE_KEY_RESULT_CODE) == RESPONSE_VALUE_RESULT_CODE_SUCCESS) {
                    return jsonObject.getJSONObject(RESPONSE_KEY_RESULT_RES).getInnerMap();
                } else {
                    log.info(jsonObject.get(RESPONSE_KEY_RESULT_MSG));
                    throw new RuntimeException(jsonObject.getString(RESPONSE_KEY_RESULT_MSG));
                }
            } catch (IOException e) {
                log.info(e.getLocalizedMessage());
                throw new RuntimeException(e);
            }
        } else {
            log.info("http response status is [{}], body is [{}]", response.getStatusLine().getStatusCode(),
                    response.getEntity());
            throw new RuntimeException(response.getStatusLine().getReasonPhrase());
        }
    }

    private StringEntity createBody(String sign, String param) {
        Objects.requireNonNull(sign, "sign can not be null");
        Map<String, String> p = new HashMap<>(3);
        p.put(PARAM_KEY_SIGN, sign);
        p.put(PARAM_KEY_APPKEY, config.getAppkey());
        p.put(PARAM_KEY_PARAMDATA, param);
        try {
            final String pStr = JSON.toJSONString(p);
            if (log.isDebugEnabled()) {
                log.debug("request param is {}", pStr);
            }
            return new StringEntity(pStr);
        } catch (UnsupportedEncodingException e) {
            log.warn(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    private String sign(Map<String, Object> param) {
        Map<String, Object> sortParam = WebSignatureUtils.asciiSort(param, false);
        String jsonParam = JSON.toJSONString(sortParam);
        if (log.isDebugEnabled()) {
            log.debug("signature origin string is [{}]", jsonParam);
        }
        byte[] md5Bytes = WebSignatureUtils.hashWithSecret(jsonParam, config.getSecret(),
                true, SEPARATOR_SIGNATURE_STRING, messageDigest);
        return Hex.encodeHexString(md5Bytes, false);
    }

}

