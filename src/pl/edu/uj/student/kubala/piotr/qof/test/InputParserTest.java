package pl.edu.uj.student.kubala.piotr.qof.test;

import org.junit.jupiter.api.Test;
import pl.edu.uj.student.kubala.piotr.qof.*;

import java.util.AbstractList;

import static org.junit.jupiter.api.Assertions.*;
// QuickOriginFit - InputParserTest.java
//---------------------------------------------------------------------
// [opis pliku]
//---------------------------------------------------------------------
// Utworzono 12:35 29.10.2017 w IntelliJ IDEA
// (C)PKua, wszystkie prawa zastrzeżone
//---------------------------------------------------------------------

class InputParserTest {
    private InputParser parser = new InputParser();
    private AbstractList<ParsedParam> parsed;

    @Test
    void emptyParsing() throws ParseException {
        parsed = parser.parse("", new DefaultParamInfoList());
        assertEquals(0, parsed.size());
    }

    @Test
    void twoParamsParsing() throws ParseException {
        ParamInfoList list = new DefaultParamInfoList();
        list.addParamInfo(new ParamInfo("a", "aunit"));
        list.addParamInfo(new ParamInfo("b", "bunit"));
        parsed = parser.parse("80.6123\t0.5222\n90.6123\t1.5222", list);
        assertEquals(2, parsed.size());
        assertEquals(new ParsedParam("a", "aunit", 80.6123, 0.5222), parsed.get(0));
        assertEquals(new ParsedParam("b", "bunit", 90.6123, 1.5222), parsed.get(1));
    }

    @Test
    void twoParamsParsingWithNonStandardWhitespace() throws ParseException {
        ParamInfoList list = new DefaultParamInfoList();
        list.addParamInfo(new ParamInfo("a", "aunit"));
        list.addParamInfo(new ParamInfo("b", "bunit"));
        parsed = parser.parse("80.6123\t  0.5222\n90.6123   \t  \t 1.5222\n", list);
        assertEquals(2, parsed.size());
        assertEquals(new ParsedParam("a", "aunit", 80.6123, 0.5222), parsed.get(0));
        assertEquals(new ParsedParam("b", "bunit", 90.6123, 1.5222), parsed.get(1));
    }

    @Test
    void twoParamsParsingWithExtraValues() throws ParseException {
        ParamInfoList list = new DefaultParamInfoList();
        list.addParamInfo(new ParamInfo("a", "aunit"));
        list.addParamInfo(new ParamInfo("b", "bunit"));
        parsed = parser.parse("80.6123\t0.5222\tfoo1\tfoo2\n90.6123\t1.5222\tfoo3\n", list);
        assertEquals(2, parsed.size());
        assertEquals(new ParsedParam("a", "aunit", 80.6123, 0.5222), parsed.get(0));
        assertEquals(new ParsedParam("b", "bunit", 90.6123, 1.5222), parsed.get(1));
    }

    @Test
    void tooFewLinesShouldThrow() {
        ParamInfoList list = new DefaultParamInfoList();
        list.addParamInfo(new ParamInfo("a", "aunit"));
        list.addParamInfo(new ParamInfo("b", "bunit"));
        assertThrows(ParseException.class, () -> parser.parse("80.6123\t0.5222", list));
    }

    @Test
    void tooManyLinesShouldThrow() {
        ParamInfoList list = new DefaultParamInfoList();
        list.addParamInfo(new ParamInfo("a", "aunit"));
        list.addParamInfo(new ParamInfo("b", "bunit"));
        assertThrows(ParseException.class, () -> parser.parse("80.6123\t0.5222\n90.6123\t1.5222\n100.6123\t2.5222", list));
    }

    @Test
    void tooLittleTokensShouldThrow() {
        ParamInfoList list = new DefaultParamInfoList();
        list.addParamInfo(new ParamInfo("a", "aunit"));
        list.addParamInfo(new ParamInfo("b", "bunit"));
        assertThrows(ParseException.class, () -> parser.parse("80.6123\n90.6123\t1.5222", list));
    }

    @Test
    void malformedDoubleShouldThrow() {
        ParamInfoList list = new DefaultParamInfoList();
        list.addParamInfo(new ParamInfo("a", "aunit"));
        list.addParamInfo(new ParamInfo("b", "bunit"));
        assertThrows(ParseException.class, () -> parser.parse("80.6123\t0.5222foobar\n90.6123\t1.5222", list));
        assertThrows(ParseException.class, () -> parser.parse("80.6123foobaz\t0.5222\n90.6123\t1.5222", list));
    }

    @Test
    void negativeErrorShouldThrow() {
        ParamInfoList list = new DefaultParamInfoList();
        list.addParamInfo(new ParamInfo("a", "aunit"));
        list.addParamInfo(new ParamInfo("b", "bunit"));
        assertThrows(ParseException.class, () -> parser.parse("80.6123\t-0.5222\n90.6123\t1.5222", list));
    }

    @Test
    void ZeroErrorShouldNotThrow() throws ParseException {
        ParamInfoList list = new DefaultParamInfoList();
        list.addParamInfo(new ParamInfo("a", "aunit"));
        list.addParamInfo(new ParamInfo("b", "bunit"));
        parser.parse("80.6123\t0\n90.6123\t1.5222", list);
    }

    @Test
    void NonPositiveValueShouldNotThrow() throws ParseException {
        ParamInfoList list = new DefaultParamInfoList();
        list.addParamInfo(new ParamInfo("a", "aunit"));
        list.addParamInfo(new ParamInfo("b", "bunit"));
        parser.parse("0\t6.3\n90.6123\t1.5222", list);
        parser.parse("1.3\t6.3\n-90.6123\t1.5222", list);
    }
}