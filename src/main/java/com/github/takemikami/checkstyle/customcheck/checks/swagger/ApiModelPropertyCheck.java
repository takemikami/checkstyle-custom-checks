package com.github.takemikami.checkstyle.customcheck.checks.swagger;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ApiModelPropertyCheck extends AbstractCheck {

  public static final String SWAGGER_REQUIRED_ANNOTATION_ATTRIBUTE = "swagger.requiredAnnotationAttribute";

  @Override
  public int[] getRequiredTokens() {
    return new int[]{TokenTypes.CLASS_DEF};
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
    DetailAST obj = ast.findFirstToken(TokenTypes.OBJBLOCK);

    // get fields of class
    List<DetailAST> varDefList = new LinkedList<>();
    DetailAST child = obj.getFirstChild();
    while (child != null) {
      if (child.getType() == TokenTypes.VARIABLE_DEF) {
        varDefList.add(child);
      }
      child = child.getNextSibling();
    }

    List<ApiModelPropertyField> lst = new LinkedList<>();
    for(DetailAST c : varDefList) {
      ApiModelPropertyField f = new ApiModelPropertyField();
      f.field = c;

      // get fieldname
      DetailAST ident = c.findFirstToken(TokenTypes.IDENT);
      if (ident != null && ident.getText() != null) {
        f.name = ident.getText();
      }

      // get fieldtype
      DetailAST ftype = c.findFirstToken(TokenTypes.TYPE);
      if(ftype!=null){
        DetailAST ftypeLiteral = ftype.getFirstChild();
        if(ftypeLiteral!=null) {
          f.fieldType = ftypeLiteral.getText();
        }
      }

      // get annotations
      DetailAST annotationApiModelProperty = null;
      DetailAST modifiers = c.findFirstToken(TokenTypes.MODIFIERS);
      if ( modifiers != null) {
        DetailAST modifier = modifiers.getFirstChild();
        while (modifier != null) {
          if (modifier.getType() == TokenTypes.ANNOTATION) {
            DetailAST annotationName = modifier.findFirstToken(TokenTypes.IDENT);
            if(annotationName!=null) {
              if ("ApiModelProperty".equals(annotationName.getText())) {
                annotationApiModelProperty = modifier;
              }
            }
          }
          modifier = modifier.getNextSibling();
        }
      }
      if(annotationApiModelProperty==null) continue;

      // get value-pair of ApiModelProperty
      DetailAST child2 = annotationApiModelProperty.getFirstChild();
      while (child2 != null) {
        if (child2.getType() == TokenTypes.ANNOTATION_MEMBER_VALUE_PAIR) {
          DetailAST attrIdent = child2.findFirstToken(TokenTypes.IDENT);
          DetailAST attrExpr = child2.findFirstToken(TokenTypes.EXPR);
          f.annotationAttributes.put(attrIdent.getText(), child2);
          if(attrExpr != null) {
            DetailAST literal = attrExpr.getFirstChild();
            if(literal!=null && literal.getText()!=null) {
              f.annotationAttributesValue.put(attrIdent.getText(), literal.getText());
            }
          }
        }
        child2 = child2.getNextSibling();
      }

      lst.add(f);

    }

    // check
    int positionX = 0;
    for (ApiModelPropertyField e : lst) {
      // value is required.
      if(e.annotationAttributes.get("value") == null) {
        log(e.field, "valueは必須 @ " + e.name);
      }

      // example is require if primitive or string.
      if(e.annotationAttributes.get("example") == null) {
        if("String".equals(e.fieldType)
            || "Integer".equals(e.fieldType)
            || "Double".equals(e.fieldType)
            || "Float".equals(e.fieldType)
            || "Boolean".equals(e.fieldType)
            || "int".equals(e.fieldType)
            || "double".equals(e.fieldType)
            || "float".equals(e.fieldType)
            || "boolean".equals(e.fieldType)
            || "Date".equals(e.fieldType)
            || "LocalDate".equals(e.fieldType)
        ) {
          log(e.field, "exampleは必須 @ " + e.name);
        }
      }

      // position order
      if(e.annotationAttributesValue.get("position") != null) {
        System.out.println(e.annotationAttributesValue.get("position"));
        String pos = e.annotationAttributesValue.get("position");
        int posInt = Integer.parseInt(e.annotationAttributesValue.get("position"));
        if(positionX >= posInt) {
          log(e.annotationAttributes.get("position"), "positionの順序が不正");
        }
        positionX = posInt;
      }

    }

  }

  class ApiModelPropertyField {
    DetailAST field;
    String name;
    String fieldType;
    String scope;
    Map<String, DetailAST> annotationAttributes = new HashMap<>();
    Map<String, String> annotationAttributesValue = new HashMap<>();
  }

}