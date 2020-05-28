grammar Grammar;

start  : command;
command : call | pipe | seq| EOF;
seq : (call| pipe ) SEMI command;
pipe : call VBAR call | pipe VBAR call;
call : WHITESPACE* (redirection WHITESPACE)* argument ((WHITESPACE (redirection| argument)))* WHITESPACE*;
argument : (quoted | unquoted)+;
unquoted : UNQUOTED;
redirection : LT WHITESPACE* argument |  GT WHITESPACE* argument;
quoted : singlequoted |doublequoted | backquoted;
backquoted :BACKQUOTEDTOKEN;
singlequoted : SINGLEQUOTED;
doublequoted : '"'(backquoted |~('\n'|'"'| '`'))* '"';

// Lexer
LT : '<';
GT : '>';
SEMI : ';';
VBAR : '|';
fragment SQ : '\'';
fragment BQ : '`';
fragment DQ : '"';
fragment BACKQUOTED : BQ (~('\n'|'`'))* BQ;
SINGLEQUOTED : SQ (~('\n'|'\''))* SQ;
BACKQUOTEDTOKEN : BACKQUOTED;
UNQUOTED : (~('\t'|' '|'\''|'`'|'"'|'\n'| ';'|'|'| '<'|'>'))+;
WHITESPACE : (' ' | '\t')+;



