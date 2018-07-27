checkstyle-custom-checks
---

Custom Checks for checkstyle.

checkstyle-custom-checks provide additional checks for checkstyle.

- Class Naming Check when specific inheritance, annotation, package members.


# Gettting Started with gradle

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
        <module name="com.github.takemikami.checkstyle.customcheck.checks.ClassNameByCaseCheck">
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
