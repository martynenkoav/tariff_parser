import java.io.*;
import java.text.ParseException;
import java.util.HashMap;
/*
    основной класс программы, откуда мы запускаем функцию для создания отчетов
    предварительно считываем файл с данными, а также высчитываем стоимости по тарифам
*/


public class TariffReports {
    public static void main(String[] args) throws IOException, ParseException {
        HashMap<String, UserCDR> map = Parser.parse("src/main/resources/cdr.txt");
        map.forEach((k, v) -> v.count_tariff());
        map.forEach((k, v) -> Report.makeReport(k, v));
    }
}
