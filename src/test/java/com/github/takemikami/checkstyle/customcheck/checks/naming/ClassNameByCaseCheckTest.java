package com.github.takemikami.checkstyle.customcheck.checks.naming;

import com.github.takemikami.checkstyle.customcheck.ut.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Test;

import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck.MSG_INVALID_PATTERN;

public class ClassNameByCaseCheckTest extends AbstractModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/github/takemikami/checkstyle/customcheck/checks/naming/classnamebycase";
    }

    @Test
    public void testAnnotationCase() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(ClassNameByCaseCheck.class);
        checkConfig.addAttribute("format", "^.*Bean$");
        checkConfig.addAttribute("caseType", "annotation");
        checkConfig.addAttribute("caseFormat", "JavaBean");

        final String pattern = "^.*Bean$";
        final String[] expected = {
                "6:14: " + getCheckMessage(MSG_INVALID_PATTERN, "TargetClassByAnnotation", pattern),
        };
        verify(checkConfig, getPath("TargetClassByAnnotation.java"), expected);
    }

    @Test
    public void testPackageCase() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(ClassNameByCaseCheck.class);
        checkConfig.addAttribute("format", "^.*Bean$");
        checkConfig.addAttribute("caseType", "package");
        checkConfig.addAttribute("caseFormat", "^.*\\.beans$");

        final String pattern = "^.*Bean$";
        final String[] expected = {
                "3:14: " + getCheckMessage(MSG_INVALID_PATTERN, "TargetClassByPackage", pattern),
        };
        verify(checkConfig, getPath("TargetClassByPackage.java"), expected);
    }

    @Test
    public void testExtendsCase() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(ClassNameByCaseCheck.class);
        checkConfig.addAttribute("format", "^.*Bean$");
        checkConfig.addAttribute("caseType", "parent");
        checkConfig.addAttribute("caseFormat", "^.*Bean$");

        final String pattern = "^.*Bean$";
        final String[] expected = {
                "3:14: " + getCheckMessage(MSG_INVALID_PATTERN, "TargetClassByExtends", pattern),
        };
        verify(checkConfig, getPath("TargetClassByExtends.java"), expected);
    }

    @Test
    public void testImplementsCase() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(ClassNameByCaseCheck.class);
        checkConfig.addAttribute("format", "^.*Bean$");
        checkConfig.addAttribute("caseType", "parent");
        checkConfig.addAttribute("caseFormat", "^.*Bean$");

        final String pattern = "^.*Bean$";
        final String[] expected = {
                "3:14: " + getCheckMessage(MSG_INVALID_PATTERN, "TargetClassByImplements", pattern),
        };
        verify(checkConfig, getPath("TargetClassByImplements.java"), expected);
    }

}
