package com.github.takemikami.checkstyle.customcheck.checks.swagger;

import static com.github.takemikami.checkstyle.customcheck.checks.swagger.ApiModelPropertyCheck.SWAGGER_REQUIRED_ANNOTATION_ATTRIBUTE;

import com.github.takemikami.checkstyle.customcheck.ut.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Test;

public class ApiModelPropertyCheckTest extends AbstractModuleTestSupport {
  @Override
  protected String getPackageLocation() {
    return "com/github/takemikami/checkstyle/customcheck/checks/swagger/apimodelproperty";
  }

  @Test
  public void testAnnotationCase() throws Exception {
    final DefaultConfiguration checkConfig =
        createModuleConfig(ApiModelPropertyCheck.class);
//    checkConfig.addAttribute("foo", "bar");

    final String[] expected = {
        "6:14: " + getCheckMessage(SWAGGER_REQUIRED_ANNOTATION_ATTRIBUTE, "TargetClassApiModelPropertyFields", "value"),
    };
    verify(checkConfig, getPath("TargetClassApiModelPropertyFields.java"), expected);
  }
}
