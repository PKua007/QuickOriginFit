// QuickOriginFit - InputParser.java
//---------------------------------------------------------------------
// [opis pliku]
//---------------------------------------------------------------------
// Utworzono 22:44 24.10.2017 w IntelliJ IDEA
// (C)PKua, wszystkie prawa zastrzeżone
//---------------------------------------------------------------------

package pl.edu.uj.student.kubala.piotr.qof;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class InputParser {
    public ArrayList<ParsedParam> parse(String input, ParamInfoList infoList) throws ParseException {
        ArrayList<ParsedParam> parsedParams = new ArrayList<>(infoList.getNumOfParamsInfos());
        BufferedReader reader = new BufferedReader(new StringReader(input));
        try {
            for (ParamInfo info : infoList.getAllInfos()) {
                // Read line and tokenize
                String line = reader.readLine();
                if (line == null)
                    throw new ParseException();
                String[] tokens = line.split("[ \\t]+");
                if (tokens.length < 2)
                    throw new ParseException();

                // Parse doubles
                double value, error;
                try {
                    value = Double.parseDouble(tokens[0]);
                    error = Double.parseDouble(tokens[1]);
                } catch (NumberFormatException e) {
                    throw new ParseException();
                }

                // Error should not be negative
                if (error < 0)
                    throw new ParseException();
                parsedParams.add(new ParsedParam(info, value, error));
            }

            // Check for additional lines
            if (reader.readLine() != null)
                throw new ParseException();
        } catch (IOException e) {
            throw new ParseException();
        }

        return parsedParams;
    }
}
