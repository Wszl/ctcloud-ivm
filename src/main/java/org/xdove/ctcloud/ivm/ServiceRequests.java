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
import org.xdove.ctcloud.ivm.entity.KitchenCreateTaskAnalysisRule;
import org.xdove.ctcloud.ivm.entity.KitchenUpdateTaskAnalysisRule;
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
     * 视频抽帧 创建任务，任务创建完默认处于停止状态 45705
     * @param extractionFrequency 抽帧频率，秒/帧
     * @param name 任务名称
     * @param url 视频流地址或视频文件地址
     * @param deviceNum 设备编码（第三方平台），与 url 二 选一
     * @param type 类型,0:视频流地址,1:视频文件地址
     * @return result {"taskId":"60121fbd3a5c8463252f8810" }
     */
    public Map<String, Object> createVideoTask(int extractionFrequency, String name, String url, String deviceNum, int type) {
        Map<String, Object> param = new TreeMap<>();
        param.put("code", "45705");
        Map<String, Object> req = new TreeMap<>();
        req.put("extractionFrequency", extractionFrequency);
        req.put("name", name);
        req.put("url", url);
        req.put("deviceNum", deviceNum);
        req.put("type", type);
        param.put("req", req);
        return baseRequest(param);
    }

    /**
     * 视频抽帧 根据任务 id 来修改任务 45706
     * @param extractionFrequency 抽帧频率，秒/帧
     * @param name 任务名称
     * @param taskId 任务 id
     * @param url 视频流地址或视频文件地址
     * @param deviceNum 设备编码（第三方平台），与 url 二 选一
     * @return result {"taskId":"60121fbd3a5c8463252f8810" }
     */
    public Map<String, Object> updateVideoTask(int extractionFrequency, String name, String taskId, String url, String deviceNum) {
        Map<String, Object> param = new TreeMap<>();
        param.put("code", "45706");
        Map<String, Object> req = new TreeMap<>();
        req.put("extractionFrequency", extractionFrequency);
        req.put("name", name);
        req.put("url", url);
        req.put("deviceNum", deviceNum);
        req.put("taskId", taskId);
        param.put("req", req);
        return baseRequest(param);
    }

    /**
     * 视频抽帧 启动已创建的任务或处于停止状态的任务 45707
     * @param taskId 任务 id
     * @return result { }
     */
    public Map<String, Object> startVideoTask(String taskId) {
        Map<String, Object> param = new TreeMap<>();
        param.put("code", "45707");
        Map<String, String> req = new TreeMap<>();
        req.put("taskId", taskId);
        param.put("req", req);
        return baseRequest(param);
    }

    /**
     * 视频抽帧 停止任务 45708
     * @param taskId 任务 id
     * @return result { }
     */
    public Map<String, Object> stopVideoTask(String taskId) {
        Map<String, Object> param = new TreeMap<>();
        param.put("code", "45708");
        Map<String, String> req = new TreeMap<>();
        req.put("taskId", taskId);
        param.put("req", req);
        return baseRequest(param);
    }

    /**
     * 视频抽帧 分页查询任务信息 45709
     * @param taskId 任务 id
     * @return result {"totalRow":1, "data":[ { "taskId":"60121fbd3a5c8463252f8810", "taskName":"任务测试", "taskStatus":1, "sourceId":"60121fae3a5c8463252f880d", "extractionFrequency":10, "createTime":"2021-01-2810:21:50" } ],"totalPage":1, "pageSize":5, "pageNum":1 }
     */
    public Map<String, Object> queryVideoTask(String taskId, int pageSize, int pageNum) {
        Map<String, Object> param = new TreeMap<>();
        param.put("code", "45709");
        Map<String, Object> req = new TreeMap<>();
        req.put("taskId", taskId);
        req.put("pageSize", pageSize);
        req.put("pageNum", pageNum);
        param.put("req", req);
        return baseRequest(param);
    }

    /**
     * 视频抽帧 根据任务 id 来删除任务 45710
     * @param taskId 任务 id
     * @return result { }
     */
    public Map<String, Object> deleteVideoTask(String taskId) {
        Map<String, Object> param = new TreeMap<>();
        param.put("code", "45710");
        Map<String, String> req = new TreeMap<>();
        req.put("taskId", taskId);
        param.put("req", req);
        return baseRequest(param);
    }

    /**
     * 视频抽帧 查询场景图 45713
     * @param taskId 任务 id
     * @return result {"sceneBase64":"图片 Base64" },
     */
    public Map<String, Object> queryVideoScene(String taskId) {
        Map<String, Object> param = new TreeMap<>();
        param.put("code", "45713");
        Map<String, String> req = new TreeMap<>();
        req.put("taskId", taskId);
        param.put("req", req);
        return baseRequest(param);
    }

    /**
     * 厨房违规检测 创建任务 47201
     * @param rule 检测规则，不传则默认全屏检测所有 类型（非必填）types 不传则默认检测所有类型，检测类型 有："mouse"老鼠,"phone"打电 话,"nohat"未戴厨师帽,"nomask"无  area   检测区域，不传默认检测全屏
     * @param sceneBase64 基准场景图，用于校验违规置物、视 频不正的基准图片（非必填）
     * @param name 任务名称（非必填）
     * @param videoTaskId 视频抽帧任务 ID（必填）
     * @return result {"taskId":"60d04273e4b0e5b78bd1b502" }
     */
    public Map<String, Object> createKitchenScene(KitchenCreateTaskAnalysisRule rule, String sceneBase64, String name, String videoTaskId) {
        Map<String, Object> param = new TreeMap<>();
        param.put("code", "47201");
        Map<String, String> req = new TreeMap<>();
        req.put("rule", JSON.toJSONString(rule));
        req.put("sceneBase64", sceneBase64);
        req.put("name", name);
        req.put("videoTaskId", videoTaskId);
        param.put("req", req);
        return baseRequest(param);
    }

    /**
     * 厨房违规检测 修改厨房违规检测任务 47202
     * @param rule 检测规则，不传则默认全屏检测所有 类型（非必填）types 不传则默认检测所有类型，检测类型 有："mouse"老鼠,"phone"打电 话,"nohat"未戴厨师帽,"nomask"无  area   检测区域，不传默认检测全屏
     * @param sceneBase64 基准场景图，用于校验违规置物、视 频不正的基准图片（非必填）
     * @param name 任务名称（非必填）
     * @param taskId 检测任务 id（必填）
     * @return result { }
     */
    public Map<String, Object> updateKitchenScene(KitchenUpdateTaskAnalysisRule rule, String sceneBase64, String name, String taskId) {
        Map<String, Object> param = new TreeMap<>();
        param.put("code", "47202");
        Map<String, String> req = new TreeMap<>();
        req.put("rule", JSON.toJSONString(rule));
        req.put("sceneBase64", sceneBase64);
        req.put("name", name);
        req.put("taskId", taskId);
        param.put("req", req);
        return baseRequest(param);
    }

    /**
     * 厨房违规检测 查询厨房违规检测任务 47203
     * @param taskId 检测任务 ID（非必填）
     * @return result {"totalRow":1, "data":[ { "deviceNum":"10020210616175411008", "analysisRule":[ { "area":[ [ 100, 21 ],[ 100, 67 ],[ 56, 89 ] ],"types":"videoangle" } ],"videoTaskId":"60c9ca42e4b0e5b721396a92", "taskName":"测试", "taskId":"60d04273e4b0e5b78bd1b502", "url":"rtsp://10.134.7.2:6002/ffcs/l_30237282", "createTime":"2021-01-2810:21:50" } ],"totalPage":1, "pageSize":10, "pageNum":1 }
     */
    public Map<String, Object> queryKitchenScene(String taskId, int pageSize, int pageNum) {
        Map<String, Object> param = new TreeMap<>();
        param.put("code", "47203");
        Map<String, Object> req = new TreeMap<>();
        req.put("taskId", taskId);
        req.put("pageSize", pageSize);
        req.put("pageNum", pageNum);
        param.put("req", req);
        return baseRequest(param);
    }

    /**
     * 厨房违规检测 删除厨房违规检测任务 47204
     * @param taskId 检测任务 ID
     * @return result { }
     * */
    public Map<String, Object> deleteKitchenScene(String taskId) {
        Map<String, Object> param = new TreeMap<>();
        param.put("code", "47204");
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
     * @param returnUrl 回调地址
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
     * @param returnUrl 回调地址
     * @param codes 推送内容编号（以逗号隔开）
     * @return { }
     * */
    public Map<String, Object> updateSubscribe(String returnUrl, String codes) {
        Map<String, Object> param = new TreeMap<>();
        param.put("code", "30703");
        Map<String, String> req = new TreeMap<>();
        req.put("returnUrl", returnUrl);
        req.put("codes", codes);
        param.put("req", req);
        return baseRequest(param);
    }

    /**
     * 通用接口 删除订阅 30704
     * @param subId subId
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
            return new StringEntity(JSON.toJSONString(p));
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

