package fr.umlv.javanotebook.exercice;

import org.parboiled.BaseParser;
import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;

/**
 * A parser for the classic non-context free language example { a^n b^n c^n : n >= 1 }
 * S <- &(A c) a+ B !(a|b|c)
 * A <- a A? b
 * B <- b B? c
 */

@BuildParseTree
public class AbcParser extends BaseParser<Object> {

    public Rule S() {
        return Sequence(
                Test(A(), 'c'),
                OneOrMore('a'),
                B(),
                TestNot(AnyOf("abc"))
        );
    }

    public Rule A() {
        return Sequence('a', Optional(A()), 'b');
    }

    public Rule B() {
        return Sequence('b', Optional(B()), 'c');
    }

}