package org.eclipse.xtext.xdoc.generator.util;

import org.eclipse.xtext.common.types.JvmIdentifiableElement;
import org.eclipse.xtext.xbase.lib.BooleanExtensions;
import org.eclipse.xtext.xbase.lib.IntegerExtensions;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.StringExtensions;

@SuppressWarnings("all")
public class GitExtensions {
  public String gitLink(final JvmIdentifiableElement ie) {
    String _xblockexpression = null;
    {
      final String basedir = "https://github.com/eclipse/xtext/blob/v2.2.0/";
      String _switchResult = null;
      String _qualifiedName = ie.getQualifiedName();
      final String name = _qualifiedName;
      boolean matched = false;
      if (!matched) {
        if (ObjectExtensions.operator_equals(name,null)) {
          matched=true;
          String _operator_plus = StringExtensions.operator_plus("broken-link in ", ie);
          return _operator_plus;
        }
      }
      if (!matched) {
        boolean _startsWith = name.startsWith("org.eclipse.xtext.common.types.xtext.ui");
        if (_startsWith) {
          matched=true;
          _switchResult = "plugins/org.eclipse.xtext.common.types.ui/src/";
        }
      }
      if (!matched) {
        boolean _startsWith_1 = name.startsWith("org.eclipse.xtext.common.types.");
        if (_startsWith_1) {
          matched=true;
          _switchResult = "plugins/org.eclipse.xtext.common.types/src/";
        }
      }
      if (!matched) {
        boolean _startsWith_2 = name.startsWith("org.eclipse.xtext.ui.shared.");
        if (_startsWith_2) {
          matched=true;
          _switchResult = "plugins/org.eclipse.xtext.ui.shared/src/";
        }
      }
      if (!matched) {
        boolean _startsWith_3 = name.startsWith("org.eclipse.xtext.xtend2.lib.");
        if (_startsWith_3) {
          matched=true;
          _switchResult = "plugins/org.eclipse.xtext.xtend2.lib/src/";
        }
      }
      if (!matched) {
        boolean _startsWith_4 = name.startsWith("org.eclipse.xtext.xtend2.ui.");
        if (_startsWith_4) {
          matched=true;
          _switchResult = "plugins/org.eclipse.xtext.xtend2.ui/src/";
        }
      }
      if (!matched) {
        boolean _startsWith_5 = name.startsWith("org.eclipse.xtext.xtend2.");
        if (_startsWith_5) {
          matched=true;
          _switchResult = "plugins/org.eclipse.xtext.xtend2/src/";
        }
      }
      if (!matched) {
        boolean _startsWith_6 = name.startsWith("org.eclipse.xtext.xbase.ui.");
        if (_startsWith_6) {
          matched=true;
          _switchResult = "plugins/org.eclipse.xtext.xbase.ui/src/";
        }
      }
      if (!matched) {
        boolean _startsWith_7 = name.startsWith("org.eclipse.xtext.xbase.lib.");
        if (_startsWith_7) {
          matched=true;
          _switchResult = "plugins/org.eclipse.xtext.xbase.lib/src/";
        }
      }
      if (!matched) {
        boolean _startsWith_8 = name.startsWith("org.eclipse.xtext.xbase.");
        if (_startsWith_8) {
          matched=true;
          _switchResult = "plugins/org.eclipse.xtext.xbase/src/";
        }
      }
      if (!matched) {
        boolean _operator_or = false;
        boolean _startsWith_9 = name.startsWith("org.eclipse.xtext.generator");
        if (_startsWith_9) {
          _operator_or = true;
        } else {
          boolean _startsWith_10 = name.startsWith("org.eclipse.xtext.ui.generator");
          _operator_or = BooleanExtensions.operator_or(_startsWith_9, _startsWith_10);
        }
        if (_operator_or) {
          matched=true;
          _switchResult = "plugins/org.eclipse.xtext.generator/src/";
        }
      }
      if (!matched) {
        boolean _startsWith_11 = name.startsWith("org.eclipse.xtext.ui");
        if (_startsWith_11) {
          matched=true;
          _switchResult = "plugins/org.eclipse.xtext.ui/src/";
        }
      }
      if (!matched) {
        boolean _startsWith_12 = name.startsWith("org.eclipse.xtext.junit4");
        if (_startsWith_12) {
          matched=true;
          _switchResult = "plugins/org.eclipse.xtext.junit4/src/";
        }
      }
      if (!matched) {
        boolean _startsWith_13 = name.startsWith("org.eclipse.xtext.junit");
        if (_startsWith_13) {
          matched=true;
          _switchResult = "plugins/org.eclipse.xtext.junit/src/";
        }
      }
      if (!matched) {
        boolean _startsWith_14 = name.startsWith("org.eclipse.xtext.ui");
        if (_startsWith_14) {
          matched=true;
          _switchResult = "plugins/org.eclipse.xtext.ui/src/";
        }
      }
      if (!matched) {
        boolean _startsWith_15 = name.startsWith("org.eclipse.xtext.util");
        if (_startsWith_15) {
          matched=true;
          _switchResult = "plugins/org.eclipse.xtext.util/src/";
        }
      }
      if (!matched) {
        boolean _startsWith_16 = name.startsWith("org.eclipse.xtext");
        if (_startsWith_16) {
          matched=true;
          _switchResult = "plugins/org.eclipse.xtext/src/";
        }
      }
      if (!matched) {
        _switchResult = "";
      }
      final String prefix = _switchResult;
      String _xifexpression = null;
      int _length = prefix.length();
      boolean _operator_notEquals = IntegerExtensions.operator_notEquals(_length, 0);
      if (_operator_notEquals) {
        String _operator_plus_1 = StringExtensions.operator_plus(basedir, prefix);
        String _qualifiedName_1 = ie.getQualifiedName();
        String _replaceAll = _qualifiedName_1.replaceAll("\\.", "/");
        String _replaceAll_1 = _replaceAll.replaceAll("\\$.*$", "");
        String _operator_plus_2 = StringExtensions.operator_plus(_operator_plus_1, _replaceAll_1);
        String _operator_plus_3 = StringExtensions.operator_plus(_operator_plus_2, ".java");
        _xifexpression = _operator_plus_3;
      } else {
        _xifexpression = null;
      }
      _xblockexpression = (_xifexpression);
    }
    return _xblockexpression;
  }
  
  public String compilationUnitName(final JvmIdentifiableElement ie) {
    String _simpleName = ie.getSimpleName();
    return _simpleName;
  }
}
