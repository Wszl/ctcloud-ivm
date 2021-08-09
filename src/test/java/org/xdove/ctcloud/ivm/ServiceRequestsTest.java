package org.xdove.ctcloud.ivm;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

/**
 * 测试视觉智联SDK
 * @author Wszl
 * @date 2021年7月31日
 */
public class ServiceRequestsTest {

    private IVMConfig ivmConfig;
    private ServiceRequests requests;
    private String videoTaskId = "60121fbd3a5c8463252f8810";
    private String kitchenTaskId = "60c9ca42e4b0e5b721396a92";
    private String subId = "x";

    @Before
    public void setUp() throws Exception {
        ivmConfig = new IVMConfig(
                System.getenv("apiurl"),
                System.getenv("secret"),
                System.getenv("appkey")
        );
        requests = new ServiceRequests(ivmConfig);
    }

    @Test
    public void createVideoTask() {
        Map<String, Object> videoTask = requests.createVideoTask(1, "test", "http://localhost/", "x", 0);
        System.out.println(videoTask);
    }

    @Test
    public void updateVideoTask() {
        Map<String, Object> videoTask = requests.updateVideoTask(1, "test", videoTaskId, "http://localhost/", "x");
        System.out.println(videoTask);
    }

    @Test
    public void startVideoTask() {
        Map<String, Object> videoTask = requests.startVideoTask(videoTaskId);
        System.out.println(videoTask);
    }

    @Test
    public void stopVideoTask() {
        Map<String, Object> videoTask = requests.stopVideoTask(videoTaskId);
        System.out.println(videoTask);
    }

    @Test
    public void queryVideoTask() {
        Map<String, Object> videoTask = requests.queryVideoTask(null, 10, 1);
        System.out.println(videoTask);
    }

    @Test
    public void deleteVideoTask() {
        Map<String, Object> videoTask = requests.deleteVideoTask(videoTaskId);
        System.out.println(videoTask);
    }

    @Test
    public void queryVideoScene() {
        Map<String, Object> videoTask = requests.queryVideoScene(videoTaskId);
        System.out.println(videoTask);
    }

    @Test
    public void createKitchenScene() {
        Map<String, Object> videoTask = requests.createKitchenScene(null, null, null, kitchenTaskId);
        System.out.println(videoTask);
    }

    @Test
    public void updateKitchenScene() {
        Map<String, Object> videoTask = requests.updateKitchenScene(null, null, null, kitchenTaskId);
        System.out.println(videoTask);
    }

    @Test
    public void queryKitchenScene() {
        Map<String, Object> videoTask = requests.queryKitchenScene( kitchenTaskId, 10, 0);
        System.out.println(videoTask);
    }

    @Test
    public void deleteKitchenScene() {
        Map<String, Object> videoTask = requests.deleteKitchenScene( kitchenTaskId);
        System.out.println(videoTask);
    }

    @Test
    public void querySubscribe() {
        Map<String, Object> videoTask = requests.querySubscribe();
        System.out.println(videoTask);
    }

    @Test
    public void createSubscribe() {
        Map<String, Object> videoTask = requests.createSubscribe("http://localhost", null);
        System.out.println(videoTask);
    }

    @Test
    public void updateSubscribe() {
        Map<String, Object> videoTask = requests.updateSubscribe("http://localhost", null);
        System.out.println(videoTask);
    }

    @Test
    public void deleteSubscribe() {
        Map<String, Object> videoTask = requests.deleteSubscribe(subId);
        System.out.println(videoTask);
    }

}