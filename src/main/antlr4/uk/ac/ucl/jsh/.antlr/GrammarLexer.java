// Generated from /workspaces/jsh-team-10/src/main/antlr4/uk/ac/ucl/jsh/Grammar.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class GrammarLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		LT=1, GT=2, SEMI=3, VBAR=4, SINGLEQUOTED=5, BACKQUOTEDTOKEN=6, DOUBLEQUOTED=7, 
		UNQUOTED=8, WHITESPACE=9;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"LT", "GT", "SEMI", "VBAR", "SQ", "BQ", "DQ", "BACKQUOTED", "DQCONTENT", 
		"SINGLEQUOTED", "BACKQUOTEDTOKEN", "DOUBLEQUOTED", "UNQUOTED", "WHITESPACE"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'<'", "'>'", "';'", "'|'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "LT", "GT", "SEMI", "VBAR", "SINGLEQUOTED", "BACKQUOTEDTOKEN", "DOUBLEQUOTED", 
		"UNQUOTED", "WHITESPACE"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public GrammarLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Grammar.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
<<<<<<< HEAD
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\13Z\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3"+
		"\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\t\7\t\61\n\t\f\t\16\t\64\13\t\3\t"+
		"\3\t\3\n\3\n\3\13\3\13\7\13<\n\13\f\13\16\13?\13\13\3\13\3\13\3\f\3\f"+
		"\3\r\3\r\3\r\7\rH\n\r\f\r\16\rK\13\r\3\r\3\r\3\16\6\16P\n\16\r\16\16\16"+
		"Q\3\17\6\17U\n\17\r\17\16\17V\3\17\3\17\2\2\20\3\3\5\4\7\5\t\6\13\2\r"+
		"\2\17\2\21\2\23\2\25\7\27\b\31\t\33\n\35\13\3\2\b\3\2\f\f\3\2bb\5\2\f"+
		"\f$$bb\4\2\f\f))\n\2\13\f\"\"$$))=>@@bb~~\5\2\13\f\17\17\"\"\2[\2\3\3"+
		"\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2"+
		"\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\3\37\3\2\2\2\5!\3\2\2\2\7#\3\2\2"+
		"\2\t%\3\2\2\2\13\'\3\2\2\2\r)\3\2\2\2\17+\3\2\2\2\21-\3\2\2\2\23\67\3"+
		"\2\2\2\259\3\2\2\2\27B\3\2\2\2\31D\3\2\2\2\33O\3\2\2\2\35T\3\2\2\2\37"+
		" \7>\2\2 \4\3\2\2\2!\"\7@\2\2\"\6\3\2\2\2#$\7=\2\2$\b\3\2\2\2%&\7~\2\2"+
		"&\n\3\2\2\2\'(\7)\2\2(\f\3\2\2\2)*\7b\2\2*\16\3\2\2\2+,\7$\2\2,\20\3\2"+
		"\2\2-\62\5\r\7\2.\61\n\2\2\2/\61\n\3\2\2\60.\3\2\2\2\60/\3\2\2\2\61\64"+
		"\3\2\2\2\62\60\3\2\2\2\62\63\3\2\2\2\63\65\3\2\2\2\64\62\3\2\2\2\65\66"+
		"\5\r\7\2\66\22\3\2\2\2\678\n\4\2\28\24\3\2\2\29=\5\13\6\2:<\n\5\2\2;:"+
		"\3\2\2\2<?\3\2\2\2=;\3\2\2\2=>\3\2\2\2>@\3\2\2\2?=\3\2\2\2@A\5\13\6\2"+
		"A\26\3\2\2\2BC\5\21\t\2C\30\3\2\2\2DI\5\17\b\2EH\5\21\t\2FH\5\23\n\2G"+
		"E\3\2\2\2GF\3\2\2\2HK\3\2\2\2IG\3\2\2\2IJ\3\2\2\2JL\3\2\2\2KI\3\2\2\2"+
		"LM\5\17\b\2M\32\3\2\2\2NP\n\6\2\2ON\3\2\2\2PQ\3\2\2\2QO\3\2\2\2QR\3\2"+
		"\2\2R\34\3\2\2\2SU\t\7\2\2TS\3\2\2\2UV\3\2\2\2VT\3\2\2\2VW\3\2\2\2WX\3"+
		"\2\2\2XY\b\17\2\2Y\36\3\2\2\2\n\2\60\62=GIQV\3\b\2\2";
=======
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\13Y\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3"+
		"\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\7\t\60\n\t\f\t\16\t\63\13\t\3\t\3\t"+
		"\3\n\3\n\3\13\3\13\7\13;\n\13\f\13\16\13>\13\13\3\13\3\13\3\f\3\f\3\r"+
		"\3\r\3\r\7\rG\n\r\f\r\16\rJ\13\r\3\r\3\r\3\16\6\16O\n\16\r\16\16\16P\3"+
		"\17\6\17T\n\17\r\17\16\17U\3\17\3\17\2\2\20\3\3\5\4\7\5\t\6\13\2\r\2\17"+
		"\2\21\2\23\2\25\7\27\b\31\t\33\n\35\13\3\2\7\4\2\f\fbb\5\2\f\f$$bb\4\2"+
		"\f\f))\n\2\13\f\"\"$$))=>@@bb~~\5\2\13\f\17\17\"\"\2Y\2\3\3\2\2\2\2\5"+
		"\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2"+
		"\2\2\33\3\2\2\2\2\35\3\2\2\2\3\37\3\2\2\2\5!\3\2\2\2\7#\3\2\2\2\t%\3\2"+
		"\2\2\13\'\3\2\2\2\r)\3\2\2\2\17+\3\2\2\2\21-\3\2\2\2\23\66\3\2\2\2\25"+
		"8\3\2\2\2\27A\3\2\2\2\31C\3\2\2\2\33N\3\2\2\2\35S\3\2\2\2\37 \7>\2\2 "+
		"\4\3\2\2\2!\"\7@\2\2\"\6\3\2\2\2#$\7=\2\2$\b\3\2\2\2%&\7~\2\2&\n\3\2\2"+
		"\2\'(\7)\2\2(\f\3\2\2\2)*\7b\2\2*\16\3\2\2\2+,\7$\2\2,\20\3\2\2\2-\61"+
		"\5\r\7\2.\60\t\2\2\2/.\3\2\2\2\60\63\3\2\2\2\61/\3\2\2\2\61\62\3\2\2\2"+
		"\62\64\3\2\2\2\63\61\3\2\2\2\64\65\5\r\7\2\65\22\3\2\2\2\66\67\n\3\2\2"+
		"\67\24\3\2\2\28<\5\13\6\29;\n\4\2\2:9\3\2\2\2;>\3\2\2\2<:\3\2\2\2<=\3"+
		"\2\2\2=?\3\2\2\2><\3\2\2\2?@\5\13\6\2@\26\3\2\2\2AB\5\21\t\2B\30\3\2\2"+
		"\2CH\5\17\b\2DG\5\21\t\2EG\5\23\n\2FD\3\2\2\2FE\3\2\2\2GJ\3\2\2\2HF\3"+
		"\2\2\2HI\3\2\2\2IK\3\2\2\2JH\3\2\2\2KL\5\17\b\2L\32\3\2\2\2MO\n\5\2\2"+
		"NM\3\2\2\2OP\3\2\2\2PN\3\2\2\2PQ\3\2\2\2Q\34\3\2\2\2RT\t\6\2\2SR\3\2\2"+
		"\2TU\3\2\2\2US\3\2\2\2UV\3\2\2\2VW\3\2\2\2WX\b\17\2\2X\36\3\2\2\2\n\2"+
		"/\61<FHPU\3\b\2\2";
>>>>>>> 0985c2490e1da04f0a0810326d2d6849ebd858f1
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}