package com.dobpm;
import static org.junit.Assert.*;
import static com.alfresco.aps.testutils.TestUtilsConstants.*;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.activiti.engine.task.Task;
import static org.mockito.Mockito.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.alfresco.aps.testutils.AbstractBpmnTest;
import com.alfresco.aps.testutils.assertions.ProcessInstanceAssert;
import com.alfresco.aps.testutils.assertions.TaskAssert;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:activiti.cfg.xml", "classpath:common-beans-and-mocks.xml" })
public class EmployeeFlow4UnitTest extends AbstractBpmnTest {

Task task;
static Map<String, Object> processVars = new HashMap<String, Object>();
static {
 processDefinitionKey = "EmployeeFlow4";
}

@Before
 public void beforeTest() throws Exception {

}
@Test
public void testPath0() throws Exception {

/*Please use processVars Map to manually include process variables to the process instance in path.*/
ProcessInstance processInstance = activitiRule.getRuntimeService().startProcessInstanceByKey(processDefinitionKey,processVars);

assertNotNull(processInstance);
unitTestHelpers.assertEmails(1, 0, "Hello ${NewEmpInfo_firstname},<br/><br/> Please login to the below url with the provided credentials.<br/><br/> URL : http://172.16.0.21:8080/prweb<br/><br/> Username :  ${NewEmpInfo_firstname}<br/> Password : tech@123<br/><br/> NOTE :Please change your password after first login.<br/><br/> Thanks<br/> System Administrator", "Login Credentials", "", new String[] { "${NewEmpInfo_email}" },new String[] { ""}, new String[] { ""});actualEmails.removeAll(actualEmails);
assertEquals(1, taskService.createTaskQuery().count());
task = taskService.createTaskQuery().singleResult();
TaskAssert.assertThat(task).hasName("Employee Stage").complete();

ProcessInstanceAssert.assertThat(processInstance).isComplete();
}
}
