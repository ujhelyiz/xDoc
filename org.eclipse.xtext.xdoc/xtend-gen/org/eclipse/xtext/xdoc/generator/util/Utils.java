package org.eclipse.xtext.xdoc.generator.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.Token;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.BooleanExtensions;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IntegerExtensions;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xdoc.generator.util.lexer.Common;
import org.eclipse.xtext.xdoc.xdoc.AbstractSection;
import org.eclipse.xtext.xdoc.xdoc.Code;
import org.eclipse.xtext.xdoc.xdoc.CodeBlock;
import org.eclipse.xtext.xdoc.xdoc.Document;
import org.eclipse.xtext.xdoc.xdoc.LangDef;
import org.eclipse.xtext.xdoc.xdoc.XdocFactory;

@SuppressWarnings("all")
public class Utils {
  public String urlDecode(final String s) {
    try {
      String _decode = s==null?(String)null:URLDecoder.decode(s, "ISO-8859-1");
      return _decode;
    } catch (Exception _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public String urlEncode(final String s) {
    try {
      String _encode = s==null?(String)null:URLEncoder.encode(s, "UTF-8");
      return _encode;
    } catch (Exception _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public boolean isInlineCode(final CodeBlock cb) {
    boolean _operator_and = false;
    EList<EObject> _contents = cb.getContents();
    int _size = _contents.size();
    boolean _operator_equals = IntegerExtensions.operator_equals(_size, 1);
    if (!_operator_equals) {
      _operator_and = false;
    } else {
      EList<EObject> _contents_1 = cb.getContents();
      EObject _head = IterableExtensions.<EObject>head(_contents_1);
      String _string = _head.toString();
      boolean _contains = _string.contains("\n");
      boolean _operator_not = BooleanExtensions.operator_not(_contains);
      _operator_and = BooleanExtensions.operator_and(_operator_equals, _operator_not);
    }
    return _operator_and;
  }
  
  public String escapeLatexChars(final String s) {
    String _xifexpression = null;
    boolean _operator_notEquals = ObjectExtensions.operator_notEquals(s, null);
    if (_operator_notEquals) {
      String _replaceAll = s.replaceAll("\\$", "\\\\\\$");
      String _replaceAll_1 = _replaceAll.replaceAll("\\{", "\\\\{");
      String _replaceAll_2 = _replaceAll_1.replaceAll("\\}", "\\\\}");
      String _replaceAll_3 = _replaceAll_2.replaceAll("\\\\(?![{}$])", "\\\\textbackslash{}");
      String _replaceAll_4 = _replaceAll_3.replaceAll("#", "\\\\#");
      String _replaceAll_5 = _replaceAll_4.replaceAll("%", "\\\\%");
      String _replaceAll_6 = _replaceAll_5.replaceAll("_", "\\\\_");
      String _replaceAll_7 = _replaceAll_6.replaceAll("\\^", "\\\\textasciicircum{}");
      String _replaceAll_8 = _replaceAll_7.replaceAll("&", "\\\\&");
      String _replaceAll_9 = _replaceAll_8.replaceAll("~", "\\\\textasciitilde{}");
      _xifexpression = _replaceAll_9;
    } else {
      _xifexpression = "";
    }
    return _xifexpression;
  }
  
  public String unescapeXdocChars(final String s) {
    String _xifexpression = null;
    boolean _operator_notEquals = ObjectExtensions.operator_notEquals(s, null);
    if (_operator_notEquals) {
      String _replaceAll = s.replaceAll("\\\\\\[", "[");
      String _replaceAll_1 = _replaceAll.replaceAll("\\\\\\]", "]");
      _xifexpression = _replaceAll_1;
    } else {
      _xifexpression = "";
    }
    return _xifexpression;
  }
  
  public String prepareListingsString(final String s) {
    String _replaceAll = s==null?(String)null:s.replaceAll("^\n", "");
    return _replaceAll;
  }
  
  public String escapeHTMLChars(final String s) {
    String _xifexpression = null;
    boolean _operator_notEquals = ObjectExtensions.operator_notEquals(s, null);
    if (_operator_notEquals) {
      String _replace = s.replace("&", "&amp;");
      String _replace_1 = _replace.replace("\'", "&apos;");
      String _replace_2 = _replace_1.replace("<", "&lt;");
      String _replace_3 = _replace_2.replace(">", "&gt;");
      String _replace_4 = _replace_3.replace("\u00AB", "&laquo;");
      String _replace_5 = _replace_4.replace("\u00BB", "&raquo;");
      _xifexpression = _replace_5;
    } else {
      _xifexpression = "";
    }
    return _xifexpression;
  }
  
  public String formatCode(final CharSequence text, final LangDef language) {
    String _xifexpression = null;
    boolean _operator_notEquals = ObjectExtensions.operator_notEquals(text, null);
    if (_operator_notEquals) {
      String _string = text.toString();
      String _highlightedHtmlCode = this.getHighlightedHtmlCode(_string, language);
      _xifexpression = _highlightedHtmlCode;
    } else {
      _xifexpression = "";
    }
    return _xifexpression;
  }
  
  public Collection<String> defaultLangKeywords(final Set<AbstractSection> sections) {
    Collection<String> _xblockexpression = null;
    {
      Iterable<Document> _filter = IterableExtensions.<Document>filter(sections, org.eclipse.xtext.xdoc.xdoc.Document.class);
      Document _head = IterableExtensions.<Document>head(_filter);
      final Document doc = _head;
      EList<LangDef> _langDefs = doc.getLangDefs();
      final Function1<LangDef,Boolean> _function = new Function1<LangDef,Boolean>() {
          public Boolean apply(final LangDef e) {
            String _name = e.getName();
            boolean _operator_equals = ObjectExtensions.operator_equals(_name, "__XdocDefaultLanguage__");
            return Boolean.valueOf(_operator_equals);
          }
        };
      LangDef _findFirst = IterableExtensions.<LangDef>findFirst(_langDefs, _function);
      final LangDef lang = _findFirst;
      Collection<String> _xifexpression = null;
      boolean _operator_notEquals = ObjectExtensions.operator_notEquals(lang, null);
      if (_operator_notEquals) {
        EList<String> _keywords = lang.getKeywords();
        _xifexpression = _keywords;
      } else {
        Set<?> _emptySet = CollectionLiterals.emptySet();
        _xifexpression = ((Set<String>) _emptySet);
      }
      _xblockexpression = (_xifexpression);
    }
    return _xblockexpression;
  }
  
  public String getHighlightedHtmlCode(final String code, final LangDef language) {
      Common _common = new Common();
      final Common lexer = _common;
      ANTLRStringStream _aNTLRStringStream = new ANTLRStringStream(code);
      lexer.setCharStream(_aNTLRStringStream);
      Set<? extends Object> _xifexpression = null;
      boolean _operator_notEquals = ObjectExtensions.operator_notEquals(language, null);
      if (_operator_notEquals) {
        EList<String> _keywords = language.getKeywords();
        Set<String> _set = IterableExtensions.<String>toSet(_keywords);
        _xifexpression = _set;
      } else {
        Set<?> _emptySet = CollectionLiterals.emptySet();
        _xifexpression = _emptySet;
      }
      final Set<? extends Object> keywords = _xifexpression;
      Token _nextToken = lexer.nextToken();
      Token token = _nextToken;
      StringBuilder _stringBuilder = new StringBuilder();
      final StringBuilder result = _stringBuilder;
      int _type = token.getType();
      boolean _operator_notEquals_1 = IntegerExtensions.operator_notEquals(_type, Token.EOF);
      boolean _while = _operator_notEquals_1;
      while (_while) {
        {
          int _type_1 = token.getType();
          final int __valOfSwitchOver = _type_1;
          boolean matched = false;
          if (!matched) {
            if (ObjectExtensions.operator_equals(__valOfSwitchOver,Common.ID)) {
              matched=true;
              String _text = token.getText();
              boolean _contains = keywords.contains(_text);
              if (_contains) {
                StringBuilder _append = result.append("<span class=\"keyword\">");
                String _text_1 = token.getText();
                String _whitespace2Entities = this.whitespace2Entities(_text_1);
                StringBuilder _append_1 = _append.append(_whitespace2Entities);
                _append_1.append("</span>");
              } else {
                String _text_2 = token.getText();
                result.append(_text_2);
              }
            }
          }
          if (!matched) {
            if (ObjectExtensions.operator_equals(__valOfSwitchOver,Common.WS)) {
              matched=true;
              String _text_3 = token.getText();
              String _whitespace2Entities_1 = this.whitespace2Entities(_text_3);
              result.append(_whitespace2Entities_1);
            }
          }
          if (!matched) {
            if (ObjectExtensions.operator_equals(__valOfSwitchOver,Common.STRING)) {
              matched=true;
              StringBuilder _append_2 = result.append("<span class=\"string\">");
              String _text_4 = token.getText();
              String _whitespace2Entities_2 = this.whitespace2Entities(_text_4);
              StringBuilder _append_3 = _append_2.append(_whitespace2Entities_2);
              _append_3.append("</span>");
            }
          }
          if (!matched) {
            if (ObjectExtensions.operator_equals(__valOfSwitchOver,Common.COMMENT)) {
              matched=true;
              StringBuilder _append_4 = result.append("<span class=\"comment\">");
              String _text_5 = token.getText();
              String _whitespace2Entities_3 = this.whitespace2Entities(_text_5);
              StringBuilder _append_5 = _append_4.append(_whitespace2Entities_3);
              _append_5.append("</span>");
            }
          }
          if (!matched) {
            String _text_6 = token.getText();
            String _whitespace2Entities_4 = this.whitespace2Entities(_text_6);
            result.append(_whitespace2Entities_4);
          }
          Token _nextToken_1 = lexer.nextToken();
          token = _nextToken_1;
        }
        int _type_2 = token.getType();
        boolean _operator_notEquals_2 = IntegerExtensions.operator_notEquals(_type_2, Token.EOF);
        _while = _operator_notEquals_2;
      }
      String _string = result.toString();
      return _string;
  }
  
  public String whitespace2Entities(final String s) {
    String _escapeHTMLChars = this.escapeHTMLChars(s);
    String _replace = _escapeHTMLChars.replace(" ", "&nbsp;");
    String _replace_1 = _replace.replace("\n", "<br/>\n");
    String _replace_2 = _replace_1.replace("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
    return _replace_2;
  }
  
  public Integer calcIndent(final CodeBlock cb) {
    Integer _xifexpression = null;
    boolean _operator_and = false;
    EList<EObject> _contents = cb.getContents();
    int _size = _contents.size();
    boolean _operator_greaterThan = IntegerExtensions.operator_greaterThan(_size, 0);
    if (!_operator_greaterThan) {
      _operator_and = false;
    } else {
      EList<EObject> _contents_1 = cb.getContents();
      EObject _get = _contents_1.get(0);
      _operator_and = BooleanExtensions.operator_and(_operator_greaterThan, (_get instanceof Code));
    }
    if (_operator_and) {
      int _xblockexpression = (int) 0;
      {
        EList<EObject> _contents_2 = cb.getContents();
        EObject _get_1 = _contents_2.get(0);
        String _contents_3 = ((Code) _get_1).getContents();
        final String code0 = _contents_3;
        int _length = code0.length();
        int indent = _length;
        String _replaceAll = code0.replaceAll("^(\n*)\\s*", "$1");
        int _length_1 = _replaceAll.length();
        int _operator_minus = IntegerExtensions.operator_minus(indent, _length_1);
        int _indent = indent = _operator_minus;
        _xblockexpression = (_indent);
      }
      _xifexpression = _xblockexpression;
    }
    return _xifexpression;
  }
  
  public Code correctedCode(final String s) {
    final ArrayList<?>_cacheKey = CollectionLiterals.newArrayList(s);
    final Code _result;
    synchronized (_createCache_correctedCode) {
      if (_createCache_correctedCode.containsKey(_cacheKey)) {
        return _createCache_correctedCode.get(_cacheKey);
      }
      Code _createCode = XdocFactory.eINSTANCE.createCode();
      _result = _createCode;
      _createCache_correctedCode.put(_cacheKey, _result);
    }
    _init_correctedCode(_result, s);
    return _result;
  }
  
  private final HashMap<ArrayList<?>,Code> _createCache_correctedCode = CollectionLiterals.newHashMap();
  
  private void _init_correctedCode(final Code code, final String s) {
    code.setContents(s);
  }
  
  public boolean nullOrEmpty(final String s) {
    boolean _operator_or = false;
    boolean _operator_equals = ObjectExtensions.operator_equals(s, null);
    if (_operator_equals) {
      _operator_or = true;
    } else {
      String _trim = s.trim();
      int _length = _trim.length();
      boolean _operator_equals_1 = IntegerExtensions.operator_equals(_length, 0);
      _operator_or = BooleanExtensions.operator_or(_operator_equals, _operator_equals_1);
    }
    return _operator_or;
  }
}
