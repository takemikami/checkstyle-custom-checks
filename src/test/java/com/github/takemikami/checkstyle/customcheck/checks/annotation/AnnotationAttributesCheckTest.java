package com.github.takemikami.checkstyle.customcheck.checks.annotation;

import static com.github.takemikami.checkstyle.customcheck.checks.annotation.AnnotationAttributesCheck.ANNOTATION_AVOID_ATTRIBUTE;
import static com.github.takemikami.checkstyle.customcheck.checks.annotation.AnnotationAttributesCheck.ANNOTATION_ORDERED_ATTRIBUTE;
import static com.github.takemikami.checkstyle.customcheck.checks.annotation.AnnotationAttributesCheck.ANNOTATION_REQUIRED_ATTRIBUTE;

import com.github.takemikami.checkstyle.customcheck.ut.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Test;

public class AnnotationAttributesCheckTest extends AbstractModuleTestSupport {

  @Override
  protected String getPackageLocation() {
    return "com/github/takemikami/checkstyle/customcheck/checks/annotation/annotationattributes";
  }

  @Test
  public void testNeedAnnotation() throws Exception {
    final DefaultConfiguration checkConfig =
        createModuleConfig(AnnotationAttributesCheck.class);
    checkConfig.addAttribute("caseAnnotation", "JavaBean");
    checkConfig.addAttribute("need", "attrA,attrB,attrC");
    checkConfig.addAttribute("order", "false");

    final String[] expected = {
        "5:1: " + getCheckMessage(ANNOTATION_REQUIRED_ATTRIBUTE, "JavaBean", "attrC")
    };
    verify(checkConfig, getPath("TargetClassNeedAttribute.java"), expected);
  }

  @Test
  public void testNeedAnnotationWithOrderSuccess() throws Exception {
    final DefaultConfiguration checkConfig =
        createModuleConfig(AnnotationAttributesCheck.class);
    checkConfig.addAttribute("caseAnnotation", "JavaBean");
    checkConfig.addAttribute("need", "attrA,attrB");
    checkConfig.addAttribute("order", "true");

    final String[] expected = {
    };
    verify(checkConfig, getPath("TargetClassNeedAttribute.java"), expected);
  }

  @Test
  public void testNeedAnnotationWithOrder() throws Exception {
    final DefaultConfiguration checkConfig =
        createModuleConfig(AnnotationAttributesCheck.class);
    checkConfig.addAttribute("caseAnnotation", "JavaBean");
    checkConfig.addAttribute("need", "attrB,attrA");
    checkConfig.addAttribute("order", "true");

    final String[] expected = {
        "5:1: " + getCheckMessage(ANNOTATION_ORDERED_ATTRIBUTE, "JavaBean", "attrB,attrA")
    };
    verify(checkConfig, getPath("TargetClassNeedAttribute.java"), expected);
  }

  @Test
  public void testAvoidAnnotation() throws Exception {
    final DefaultConfiguration checkConfig =
        createModuleConfig(AnnotationAttributesCheck.class);
    checkConfig.addAttribute("caseAnnotation", "JavaBean");
    checkConfig.addAttribute("avoid", "attrA");
    checkConfig.addAttribute("order", "false");

    final String[] expected = {
        "5:1: " + getCheckMessage(ANNOTATION_AVOID_ATTRIBUTE, "JavaBean", "attrA")
    };
    verify(checkConfig, getPath("TargetClassNeedAttribute.java"), expected);
  }

}
