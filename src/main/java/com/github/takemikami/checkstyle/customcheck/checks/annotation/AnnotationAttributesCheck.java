package com.github.takemikami.checkstyle.customcheck.checks.annotation;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class AnnotationAttributesCheck extends AbstractCheck {

  public static final String ANNOTATION_REQUIRED_ATTRIBUTE = "annotation.requiredAttribute";
  public static final String ANNOTATION_AVOID_ATTRIBUTE = "annotation.avoidAttribute";
  public static final String ANNOTATION_ORDERED_ATTRIBUTE = "annotation.orderedAttribute";

  private String caseAnnotation;
  private String need;
  private boolean order;
  private String avoid;

  public void setCaseAnnotation(String caseAnnotation) {
    this.caseAnnotation = caseAnnotation;
  }

  public void setNeed(String need) {
    this.need = need;
  }

  public void setOrder(boolean order) {
    this.order = order;
  }

  public void setAvoid(String avoid) {
    this.avoid = avoid;
  }

  @Override
  public int[] getRequiredTokens() {
    return new int[]{TokenTypes.ANNOTATION};
  }

  @Override
  public int[] getDefaultTokens() {
    return getRequiredTokens();
  }

  @Override
  public int[] getAcceptableTokens() {
    return getRequiredTokens();
  }

  @Override
  public void visitToken(DetailAST ast) {
    // check annotation name
    DetailAST annotationAst = ast.findFirstToken(TokenTypes.IDENT);
    if (annotationAst==null) {
      return;
    }
    String annotationName = annotationAst.getText();
    if (!annotationName.equals(caseAnnotation)) {
      return;
    }

    // get attributes
    List<String> attrList = getAnnotationAttributeNames(ast);

    // check (need)
    if (this.need != null) {
      LinkedList<String> attrLinkedList = new LinkedList<String>(attrList);
      for (String chkAttr : this.need.split(",")) {
        if (!attrList.contains(chkAttr)) {
          log(ast,
              ANNOTATION_REQUIRED_ATTRIBUTE,
              annotationName,
              chkAttr);
        }
        // check (order)
        boolean hitFlag = false;
        while (attrLinkedList.size() > 0) {
          if (attrLinkedList.pop().equals(chkAttr)) {
            hitFlag = true;
            break;
          }
        }
        if (this.order && !hitFlag) {
          log(ast,
              ANNOTATION_ORDERED_ATTRIBUTE,
              annotationName,
              this.need);
        }
      }
    }

    // check (avoid)
    if (this.avoid != null) {
      Stream.of(this.avoid.split(","))
          .filter(chkAttr -> attrList.contains(chkAttr))
          .forEach(chkAttr ->
              log(ast,
                  ANNOTATION_AVOID_ATTRIBUTE,
                  annotationName,
                  chkAttr));
    }

  }

  private List<String> getAnnotationAttributeNames(DetailAST ast) {
    List<String> attrList = new LinkedList<String>();
    DetailAST child = ast.getFirstChild();
    while (child != null) {
      if (child.getType() == TokenTypes.ANNOTATION_MEMBER_VALUE_PAIR) {
        DetailAST attrIdent = child.findFirstToken(TokenTypes.IDENT);
        attrList.add(attrIdent.getText());
      }
      child = child.getNextSibling();
    }
    return attrList;
  }

}
