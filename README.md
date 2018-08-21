checkstyle-custom-checks
----

[![JitPack](https://jitpack.io/v/takemikami/checkstyle-custom-checks.svg)](https://jitpack.io/#takemikami/checkstyle-custom-checks)
[![Build Status](https://travis-ci.com/takemikami/checkstyle-custom-checks.svg?branch=master)](https://travis-ci.com/takemikami/checkstyle-custom-checks)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/1a99a55f2d924c3f8372492503f985b7)](https://www.codacy.com/project/takemikami/checkstyle-custom-checks/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=takemikami/checkstyle-custom-checks&amp;utm_campaign=Badge_Grade_Dashboard)
[![Coverage Status](https://coveralls.io/repos/github/takemikami/checkstyle-custom-checks/badge.svg?branch=master)](https://coveralls.io/github/takemikami/checkstyle-custom-checks?branch=master)

Custom Checks for checkstyle.

checkstyle-custom-checks provide additional checks for checkstyle.

- Class Naming Check when specific inheritance, annotation, package members.


## Gettting Started with gradle

add checkstyle-custom-checks to checkstyle dependencies.

build.gradle

```
plugins {
    id 'java'
    id 'checkstyle'
}

repositories {
    mavenCentral()
    maven { url "https://jitpack.io" }
}

dependencies {
    checkstyle 'com.github.takemikami:checkstyle-custom-checks:master-SNAPSHOT'
}

checkstyle {
    toolVersion '8.11'
}
```

add checkstyle-custom-checks to checkstyle.xml.

```
<?xml version="1.0"?>
<!DOCTYPE module PUBLIC
        "-//Checkstyle//DTD Checkstyle Configuration 1.3//EN"
        "https://checkstyle.org/dtds/configuration_1_3.dtd">
<module name="Checker">
    <module name="TreeWalker">
        <module name="com.github.takemikami.checkstyle.customcheck.checks.naming.ClassNameByCaseCheck">
            <property name="format" value="^.*Bean$"/>
            <property name="caseType" value="annotation"/>
            <property name="caseFormat" value="JavaBean"/>
            <message key="name.invalidPattern"
                     value="Javabean class ''{0}'' must match pattern ''{1}''."/>
        </module>
    </module>
</module>
```

run check.

```
$ gradle check
```


## References

### ClassNameByCaseCheck

module name: com.github.takemikami.checkstyle.customcheck.checks.naming.ClassNameByCaseCheck

properties:

- format(string): regular expression of class name
- caseType(string): parent|package|annotation
- caseFormat(string): regular expression of parent class or package or annotation

example:

```
<module name="com.github.takemikami.checkstyle.customcheck.checks.naming.ClassNameByCaseCheck">
    <property name="format" value="^.*Bean$"/>
    <property name="caseType" value="annotation"/>
    <property name="caseFormat" value="JavaBean"/>
    <message key="name.invalidPattern"
             value="Javabean class ''{0}'' must match pattern ''{1}''."/>
</module>
```

### AnnotationAttributesCheck

module name:
com.github.takemikami.checkstyle.customcheck.checks.annotation.AnnotationAttributesCheck

properties:

- need(string): comma-separated attribute name list
- order(boolean): order check
- avoid(string): comma-separated attribute name list
- caseAnnotation(string): annotation name

example:

```
<module name="com.github.takemikami.checkstyle.customcheck.checks.annotation.AnnotationAttributesCheck">
    <property name="need" value="attrA,attrB"/>
    <property name="order" value="true"/>
    <property name="caseAnnotation" value="JavaBean"/>
</module>
```

### ApiModelPropertyCheck

module name:
com.github.takemikami.checkstyle.customcheck.checks.swagger.ApiModelPropertyCheck

properties:

- value(string): none, required
- example(string): none, required, requiredIfPrimitive, typeValid
- position(string): none, required, noDuplicated, order
- allowableValuesStyle(string): none, commaColon
