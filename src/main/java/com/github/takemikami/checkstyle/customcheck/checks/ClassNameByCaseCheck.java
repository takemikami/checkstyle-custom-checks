package com.github.takemikami.checkstyle.customcheck.checks;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck.MSG_INVALID_PATTERN;

public class ClassNameByCaseCheck extends AbstractCheck {

    private Pattern format;
    private Pattern caseFormat;
    private String caseType;

    public void setFormat(Pattern format) {
        this.format = format;
    }

    public void setCaseFormat(Pattern caseFormat) {
        this.caseFormat = caseFormat;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

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

        List<String> caseTextList = new LinkedList<>();
        switch (this.caseType) {
            case "parent":
                caseTextList = this.getParentTextList(ast);
                break;
            case "package":
                caseTextList = this.getPackageTextList(ast);
                break;
            case "annotation":
                caseTextList = this.getAnnotationTextList(ast);
                break;
            default:
                break;
        }
        boolean match = false;
        for (String parentText : caseTextList) {
            if (this.caseFormat.matcher(parentText).find()) {
                match = true;
                break;
            }
        }
        if (!match) {
            return;
        }

        DetailAST crrIdent = ast.findFirstToken(TokenTypes.IDENT);
        if (!this.format.matcher(crrIdent.getText()).find()) {
            log(crrIdent,
                    MSG_INVALID_PATTERN,
                    crrIdent.getText(),
                    format.pattern());
        }
    }

    private List<String> getPackageTextList(DetailAST ast) {
        List<String> rtn = new LinkedList<String>();

        // get package
        DetailAST elm = ast.getPreviousSibling();
        while (elm != null) {
            if(elm.getType() == TokenTypes.PACKAGE_DEF) {
                rtn.add(String.join(".", dotToString(elm)));
            }
            elm = elm.getPreviousSibling();
        }

        return rtn;
    }

    private List<String> dotToString(DetailAST ast) {
        List<String> buff = new LinkedList<>();

        DetailAST child = ast.getFirstChild();
        while (child != null) {
            if(child.getType() == TokenTypes.DOT) {
                buff.addAll(dotToString(child));
            }
            if(child.getType() == TokenTypes.IDENT) {
                buff.add(child.getText());
            }
            child = child.getNextSibling();
        }
        return buff;
    }

    private List<String> getParentTextList(DetailAST ast) {
        List<String> rtn = new LinkedList<String>();

        // get extends
        DetailAST parent = ast.findFirstToken(TokenTypes.EXTENDS_CLAUSE);
        if (parent != null) {
            DetailAST ident = parent.findFirstToken(TokenTypes.IDENT);
            if (ident != null && ident.getText() != null) {
                rtn.add(ident.getText());
            }
        }

        // get implements
        DetailAST interfaces = ast.findFirstToken(TokenTypes.IMPLEMENTS_CLAUSE);
        if (interfaces != null) {
            DetailAST child = interfaces.getFirstChild();
            while (child != null) {
                if(child.getType() == TokenTypes.IDENT && child.getText() != null) {
                    rtn.add(child.getText());
                }
                child = child.getNextSibling();
            }
        }

        return rtn;
    }

    private List<String> getAnnotationTextList(DetailAST ast) {
        List<String> rtn = new LinkedList<String>();

        // get annotations
        DetailAST modifiers = ast.findFirstToken(TokenTypes.MODIFIERS);
        if (modifiers == null) {
            return rtn;
        }
        DetailAST child = modifiers.getFirstChild();
        while (child != null) {
            if(child.getType() == TokenTypes.ANNOTATION) {
                DetailAST ident = child.findFirstToken(TokenTypes.IDENT);
                if (ident != null && ident.getText() != null) {
                    rtn.add(ident.getText());
                }
            }
            child = child.getNextSibling();
        }

        return rtn;
    }
}
