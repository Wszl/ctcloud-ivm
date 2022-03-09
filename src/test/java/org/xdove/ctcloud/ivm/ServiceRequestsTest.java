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

    private ServiceRequests requests;

    @Before
    public void setUp() throws Exception {
        IVMConfig ivmConfig = new IVMConfig(
                System.getenv("apiurl"),
                System.getenv("secret"),
                System.getenv("appkey")
        );
        requests = new ServiceRequests(ivmConfig);
    }

    @Test
    public void testCreateTask() {
        String deviceNum = System.getenv("deviceNum");
        final Map<String, Object> result = requests.createTask(deviceNum, null, 0, null, null, null);
        System.out.println(result);
    }

    @Test
    public void testUpdateTask() {
        String deviceNum = System.getenv("deviceNum");
        String taskId = System.getenv("taskId");
        final Map<String, Object> result = requests.updateTask(deviceNum, null, "test", null, null, taskId);
        System.out.println(result);
    }

    @Test
    public void testQueryTask() {
        final Map<String, Object> result = requests.queryTask(null, 10, 1);
        System.out.println(result);
    }

    @Test
    public void testDeleteTask() {
        String taskId = System.getenv("taskId");
        final Map<String, Object> result = requests.deleteTask(taskId);
        System.out.println(result);
    }

    @Test
    public void querySubscribe() {
        Map<String, Object> videoTask = requests.querySubscribe();
        System.out.println(videoTask);
    }

    @Test
    public void createSubscribe() {
        String returnUrl = System.getenv("returnUrl");
        Map<String, Object> videoTask = requests.createSubscribe(returnUrl,
                "47205,45312");
        System.out.println(videoTask);
    }

    @Test
    public void updateSubscribe() {
        String returnUrl = System.getenv("returnUrl");
        String subId = System.getenv("subId");
        Map<String, Object> videoTask = requests.updateSubscribe(subId, returnUrl, "47205, 45312");
        System.out.println(videoTask);
    }

    @Test
    public void deleteSubscribe() {
        String subId = System.getenv("subId");
        Map<String, Object> videoTask = requests.deleteSubscribe(subId);
        System.out.println(videoTask);
    }

    @Test
    public void testCreateControl() {
        String taskIds = System.getenv("taskIds");
        String repoIds = System.getenv("repoIds");
        final Map<String, Object> result = requests.createControl(taskIds, 50, repoIds);
        System.out.println(result);
    }

    @Test
    public void testQueryControl() {
        String controlId = System.getenv("controlId");
        final Map<String, Object> result = requests.queryControl(controlId, 10, 1);
        System.out.println(result);
    }

    @Test
    public void testUpdateControl() {
        String controlId = System.getenv("controlId");
        final Map<String, Object> result = requests.updateControl(controlId, null, 60, null);
        System.out.println(result);
    }

    @Test
    public void testDeleteControl() {
        String controlId = System.getenv("controlId");
        final Map<String, Object> result = requests.deleteControl(controlId);
        System.out.println(result);
    }

    @Test
    public void testQueryScene() {
        String taskId = System.getenv("taskId");
        final Map<String, Object> result = requests.queryScene(null, taskId, null);
        System.out.println(result);
    }
}