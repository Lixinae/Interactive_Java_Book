package fr.umlv.javanotebook.exercice;

import org.parboiled.Parboiled;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;

import org.parboiled.common.StringUtils;
import org.parboiled.errors.ErrorUtils;
import static org.parboiled.support.ParseTreeUtils.printNodeTree;

import java.util.Scanner;

public class Main_test_Parser {

    public static void main(String[] args) {
        //System.out.println(AbcParser.class);
    	AbcParser parser = Parboiled.createParser(AbcParser.class);
    	//System.out.println(AbcParser.class);
        while (true) {
            System.out.print("Enter an a^n b^n c^n expression (single RETURN to exit)!\n");
            String input = new Scanner(System.in).nextLine();
            if (StringUtils.isEmpty(input)) break;

            ParsingResult<?> result = new ReportingParseRunner(parser.S()).run(input);

            if (!result.parseErrors.isEmpty())
                System.out.println(ErrorUtils.printParseError(result.parseErrors.get(0)));
            else
                System.out.println(printNodeTree(result) + '\n');
        }
    }

}
